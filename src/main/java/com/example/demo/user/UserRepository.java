package com.example.demo.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ユーザ管理に関わるDBアクセスを実現するクラスです。
 *
 * <p>以下の処理を行います。
 * <ul>
 * <li>全件取得</li>
 * <li>検索</li>
 * <li>追加</li>
 * <li>削除</li>
 * <li>更新</li>
 * </ul>
 * <p>処理が継続できない場合は、呼び出し元へ例外をスローします。<br>
 * <strong>呼び出し元では適切な例外処理を行ってください。</strong>
 *
 * @author 情報太郎
 */
@Repository
public class UserRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM m_user WHERE user_id = :userId";

	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO m_user(user_id, encrypted_password, user_name, role, enabled) VALUES(:userId, :password, :username, :role, :enabled)";

	/** SQL 1件更新(パスワードあり) */
	private static final String SQL_UPDATE_ONE_WITH_PASSWORD = "UPDATE m_user SET encrypted_password = :password, user_name = :username, role = :role, enabled = :enabled WHERE user_id = :userId";

	/** SQL 1件更新(パスワードなし) */
	private static final String SQL_UPDATE_ONE_WITHOUT_PASSWORD = "UPDATE m_user SET user_name = :username, role = :role, enabled = :enabled WHERE user_id = :userId";

	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM m_user WHERE user_id = :userId";

	/** 予想更新件数(ハードコーディング防止用) */
	private static final int EXPECTED_UPDATE_COUNT = 1;

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	/**
	 * 全ユーザを取得します。
	 *
	 * <p>全レコードを取得するため、
	 * <strong>取得データ量に注意して利用してください。</strong>
	 *
	 * @return 全ユーザレコードの配列
	 */
	public List<Map<String, Object>> selectAll() {
		// パラメータを格納するためのマップを作成
		Map<String, Object> params = new HashMap<>();

		// 埋め込む値が存在しないため、空のMapを生成
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL, params);
		
		// 取得したユーザーデータのリストを返す
		return resultList;
	}

	/**
	 * 指定されたユーザーIDのユーザーデータを取得するメソッドです。
	 * ユーザーIDは引数として受け取ります。
	 * @param userId 取得するユーザーデータのユーザーID
	 * @return 指定されたユーザーIDのユーザーデータのリスト
	 */
	public List<Map<String, Object>> selectOne(String userId) {
		// パラメータを格納するためのマップを作成
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
	
		// データベースのクエリを実行し、結果を取得
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, params);
	
		// 取得したユーザーデータのリストを返す
		return resultList;
	}

	/**
	 * ユーザ情報を１件登録します。
	 *
	 * <p>更新件数が異常な場合は例外が発生します。
	 *
	 * @param userData 挿入するユーザーデータ
	 * @exception SQLException 挿入が失敗した場合にスローされます
	 */
	public void insert(UserData userData) throws SQLException {
		// パラメータを格納するためのマップを作成
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userData.getUserId());
		params.put("password", userData.getPassword());
		params.put("username", userData.getUser_name());
		params.put("role", userData.getRole());
		params.put("enabled", userData.isEnabled());

		// データベースの挿入を実行し、挿入された行数を取得
		int updateRow = jdbc.update(SQL_INSERT_ONE, params);
		
		// 挿入された行数が期待される行数と一致しない場合、例外をスロー
		if (updateRow != EXPECTED_UPDATE_COUNT) {
			throw new SQLException("更新に失敗しました :" + updateRow);
		}
	}

	/**
	 * ユーザ情報を１件更新します。
	 *
	 * <p>この処理では指定ユーザのパスワードも変更します。<br>
	 * 更新件数が異常な場合は例外が発生します。
	 *
	 * @param userData 更新するユーザーデータ
	 * @throws SQLException 更新が失敗した場合にスローされます
	 */
	public void updateWithPassword(UserData userData) throws SQLException {
		// パラメータを格納するためのマップを作成
		Map<String, Object> params = new HashMap<>();
		params.put("password", userData.getPassword());
		params.put("userId", userData.getUserId());
		params.put("role", userData.getRole());
		params.put("username", userData.getUser_name());
		params.put("enabled", userData.isEnabled());

		// データベースの更新を実行し、更新された行数を取得
		int updateRow = jdbc.update(SQL_UPDATE_ONE_WITH_PASSWORD, params);
		// 更新された行数が期待される行数と一致しない場合、例外をスロー
		if (updateRow != EXPECTED_UPDATE_COUNT) {
			throw new SQLException("更新に失敗しました :" + updateRow);
		}
	}

	/**
	 * ユーザ情報を１件更新します。
	 *
	 * <p>この処理では指定ユーザのパスワードを<strong>変更しません</strong><br>
	 * 更新件数が異常な場合は例外が発生します。
	 *
	 * @param userData ユーザ情報
	 * @throws SQLException 更新が失敗した場合にスローされます
	 */
	public void updateWithoutPassword(UserData userData) throws SQLException {
		// パラメータを格納するためのマップを作成
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userData.getUserId());
		params.put("role", userData.getRole());
		params.put("username", userData.getUser_name());
		params.put("enabled", userData.isEnabled());

		// データベースの更新を実行し、削除された行数を取得
		int updateRow = jdbc.update(SQL_UPDATE_ONE_WITHOUT_PASSWORD, params);
		
		// 更新された行数が期待される行数と一致しない場合、例外をスロー
		if (updateRow != EXPECTED_UPDATE_COUNT) {
			throw new SQLException("更新に失敗しました :" + updateRow);
		}
	}

	/**
	 * 指定されたユーザーIDのデータをデータベースから削除するメソッドです。
	 * ユーザーIDは引数として受け取ります。
	 * @param userId 削除するデータのユーザーID
	 * @throws SQLException 削除が失敗した場合にスローされます
	 */
	public void delete(String userId) throws SQLException {
		// パラメータを格納するためのマップを作成
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		// データベースの更新を実行し、削除された行数を取得
		int updateRow = jdbc.update(SQL_DELETE_ONE, params);

		// 削除された行数が期待される行数と一致しない場合、例外をスロー
		if (updateRow != EXPECTED_UPDATE_COUNT) {
			throw new SQLException("更新に失敗しました :" + updateRow);
		}
	}

}
