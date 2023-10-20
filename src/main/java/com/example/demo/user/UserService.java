package com.example.demo.user;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザーサービスクラスです。
 * ユーザー関連の操作を提供します。
 * @author 情報太郎
 */
@Transactional
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザーリストを取得するメソッドです。
	 * @return ユーザーリストのエンティティ
	 */
	public UserEntity getUserList() {
		// データベースから全てのユーザーデータを取得
		List<Map<String, Object>> resultList;
		resultList = userRepository.selectAll();

		// 取得したユーザーデータをエンティティにマッピング
		UserEntity userEntity = mappingSelectResult(resultList);

		// ユーザーリストのエンティティを返す
		return userEntity;
	}

	/**
	 * ユーザーフォームのデータをデータベースに挿入するメソッドです。
	 * ユーザーフォームは引数として受け取ります。
	 * @param userForm 挿入するユーザーフォームのデータ
	 * @return 挿入が成功した場合はtrue、失敗した場合はfalse
	 */
	public boolean insertOne(UserForm userForm) {
		// ユーザーフォームのデータをユーザーデータに変換
		UserData userData = refillToData(userForm);
		try {
			// データベースにユーザーデータを挿入
			userRepository.insert(userData);
		} catch (SQLException e) {
			// 挿入時にエラーが発生した場合はfalseを返す
			return false;
		}
		// 挿入が成功した場合はtrueを返す
		return true;
	}

	/**
	 * ユーザーデータを取得し、指定されたユーザーフォームに更新します。
	 * ユーザーIDと更新するユーザーフォームを引数として受け取ります。
	 * @param userId ユーザーデータを取得するユーザーID
	 * @param updateUserForm 更新するユーザーフォーム
	 */
	public boolean getUser(UpdateUserForm updateUserForm) {
		// データベースから指定されたユーザーIDのユーザーデータを取得
		List<Map<String, Object>> resultList = userRepository.selectOne(updateUserForm.getUserId());
		// 取得したユーザーデータを指定されたユーザーフォームにマッピング
		boolean result = mappingUpdateResult(resultList, updateUserForm);
		return result;
	}

	/**
	 * ユーザーフォームのデータを更新し、データベースに反映するメソッドです。
	 * 更新するユーザーフォームのデータを引数として受け取ります。
	 * @param userDataForm 更新するユーザーフォームのデータ
	 * @return 更新が成功した場合はtrue、失敗した場合はfalse
	 */
	public boolean updateOne(UpdateUserForm userDataForm) {
		// ユーザーフォームのデータをユーザーデータに変換
		UserData userData = refillToData(userDataForm);
		try {
			// パスワードが空であるかどうかで条件分岐
			if (userData.getPassword().isBlank()) {
				// パスワードが空の場合はパスワードを更新せずにユーザーデータを更新
				userRepository.updateWithoutPassword(userData);
			} else {
				// パスワードが空でない場合はパスワードを含めてユーザーデータを更新
				userRepository.updateWithPassword(userData);
			}
		} catch (SQLException e) {
			// 更新時にエラーが発生した場合はfalseを返す
			return false;
		}
		// 更新が成功した場合はtrueを返す
		return true;
	}

	/**
	 * 指定されたユーザーIDのユーザーデータを削除するメソッドです。
	 * ユーザーIDを引数として受け取ります。
	 * @param userId 削除するユーザーデータのユーザーID
	 * @return 削除が成功した場合はtrue、失敗した場合はfalse
	 */
	public boolean deleteOne(String userId) {
		try {
			// 指定されたユーザーIDのユーザーデータを削除
			userRepository.delete(userId);
		} catch (SQLException e) {
			// 削除時にエラーが発生した場合はfalseを返す
			return false;
		}
		// 削除が成功した場合はtrueを返す
		return true;
	}

	/**
	 * 複数のユーザーを一括で削除するメソッドです。
	 * ユーザーIDのリストをカンマ区切りの文字列として受け取ります。
	 * @param users 削除するユーザーIDのリスト（カンマ区切りの文字列）
	 * @return 削除が成功した場合はtrue、失敗した場合はfalse
	 */
	public boolean delete(String users) {
		// カンマ区切りの文字列をユーザーIDのリストに変換
		List<String> userList = Arrays.asList(users.split(","));

		try {
			// 各ユーザーIDを順にループして削除処理を実行
			for (String user : userList) {
				userRepository.delete(user);
			}
		} catch (SQLException e) {
			// 削除時にエラーが発生した場合はfalseを返す
			return false;
		}

		// 削除が成功した場合はtrueを返す
		return true;
	}

	private boolean mappingUpdateResult(List<Map<String, Object>> resultList, UpdateUserForm updateUserForm) {
		// ユーザ情報が空であるかをフラグで管理
		boolean isEmpty = true;
		
		for (Map<String, Object> map : resultList) {
			updateUserForm.setUserId((String) map.get("user_id"));
			updateUserForm.setUser_name((String) map.get("user_name"));
			updateUserForm.setRole((String) map.get("role"));
			updateUserForm.setEnabled(String.valueOf(map.get("enabled")));
			isEmpty = false;
		}
		return isEmpty;
	}

	private UserData refillToData(UpdateUserForm updateUserForm) {
		UserData userData = new UserData();
		userData.setUserId(updateUserForm.getUserId());
		// パスワードは暗号化する
		final String encryptedPassword = passwordEncoder.encode(updateUserForm.getPassword());
		userData.setPassword(encryptedPassword);
		userData.setUser_name(updateUserForm.getUser_name());
		userData.setRole(updateUserForm.getRole());
		// boolean型へ変換
		boolean isEnabled = Boolean.valueOf(updateUserForm.getEnabled());
		userData.setEnabled(isEnabled);

		return userData;
	}

	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		UserEntity entity = new UserEntity();

		for (Map<String, Object> map : resultList) {
			UserData data = new UserData();
			data.setUserId((String) map.get("user_id"));
			data.setUser_name((String) map.get("user_name"));
			data.setRole((String) map.get("role"));
			data.setEnabled((boolean) map.get("enabled"));

			entity.getUserlist().add(data);
		}
		return entity;
	}

	private UserData refillToData(UserForm userForm) {
		UserData userData = new UserData();
		userData.setUserId(userForm.getUserId());
		// パスワードは暗号化する
		final String encryptedPassword = passwordEncoder.encode(userForm.getPassword());
		userData.setPassword(encryptedPassword);
		userData.setUser_name(userForm.getUsername());
		userData.setRole(userForm.getRole());
		// ユーザ作成時は、デフォルト有効とする
		userData.setEnabled(true);
		return userData;
	}

}