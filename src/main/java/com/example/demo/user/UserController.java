package com.example.demo.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

/**
 * ユーザ管理機能を表す。
 *
 * <p>本機能は、ユーザ管理に関わるCRUDを実現します。
 *
 * <p>入力チェックを実施し、正しいデータ項目のみ処理します。
 * クライアント側でも入力チェックを実施することを推奨します。
 *
 * @author 情報太郎
 *
 */
@Log4j2
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * ユーザ一覧画面を表示します。
	 *
	 * <p>本機能は、ユーザ管理機能の一覧機能を提供します。
	 * <p><strong>この機能は管理者もしくは上位ロールのユーザのみが利用できます</strong>
	 *
	 * @param model {@code Model} オブジェクト
	 * @return ユーザーリスト表示用のビュー名
	 */
	@GetMapping("/user/list")
	public String getUserList(Model model) {

		UserEntity userEntity = userService.getUserList();
		model.addAttribute("userEntity", userEntity);

		return "user/list";
	}

	/**
	 *【管理者】ユーザ追加画面を表示します。
	 *
	 * <p><strong>この機能は管理者ロールのユーザのみが利用できます</strong>
	 *
	 * @param userForm 入力値(null不可)
	 * @param model Viewに値を渡すオブジェクト(null不可)
	 * @return ユーザ追加画面(null不可)
	 */
	@GetMapping("/user/insert")
	public String getUserCreate(UserForm userForm, Model model) {
		// 前回の入力内容を設定
		model.addAttribute("userForm", userForm);

		return "user/insert";
	}

	/**
	 * 【管理者】ユーザ追加画面を表示します。
	 *
	 * <p>本機能は、ユーザ管理機能の追加機能を提供します。
	 * <p><strong>この機能は管理者ロールのユーザのみが利用できます</strong>
	 * @param form ユーザーのフォームデータ
	 * @param bindingResult 入力値のバインディング結果
	 * @param principal ログインユーザーのプリンシパルオブジェクト
	 * @param model {@code Model} オブジェクト
	 * @return ユーザーリスト表示用のビュー名
	 */
	@PostMapping("/user/insert")
	public String createUser(@Validated UserForm form, BindingResult bindingResult, 
			Principal principal, Model model) {

		// 入力チェックでエラーの場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return getUserCreate(form, model);
		}

		boolean isSuceess = userService.insertOne(form);
		if (isSuceess) {
			model.addAttribute("message", "ユーザを登録しました");
		} else {
			model.addAttribute("errorMessage", "ユーザ登録に失敗しました。操作をやり直してください。");
		}

		return getUserList(model);
	}

	/**
	 * 【管理者】ユーザ詳細画面を表示します。
	 * @param updateUserForm ユーザー情報の更新フォーム
	 * @param model {@code Model} オブジェクト
	 * @return ユーザー詳細表示用のビュー名
	 */
	@GetMapping("/user/detail")
	public String getUserDetail(UpdateUserForm updateUserForm, Model model) {

		boolean isEmpty = userService.getUser(updateUserForm);
		if(isEmpty) {
			// ユーザ情報の取得ができない場合は、一覧画面へ戻る
			model.addAttribute("errorMessage", "情報の取得に失敗しました。操作をやり直してください。");
			return getUserList(model);
		}
		model.addAttribute("UpdateUserForm", updateUserForm);

		return "user/detail";
	}

	/**
	 * 【管理者】ユーザ詳細画面を表示します。
	 *
 	 * <p>本機能は、ユーザ管理機能の更新機能を提供します。
	 * <p><strong>この機能は管理者ロールのユーザのみが利用できます</strong>
	 *
	 * @param updateUserForm ユーザー情報の更新フォーム
	 * @param bindingResult 入力値のバインディング結果を保持するオブジェクト
	 * @param model {@code Model} オブジェクト
	 * @return ユーザー一覧表示用のビュー名
	 */
	@PostMapping("/user/update")
	public String updateUser(@Validated UpdateUserForm updateUserForm,
			BindingResult bindingResult, Model model) {

		// 入力チェックでエラーの場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return getUserDetail(updateUserForm, model);
		}

		boolean isSuceess = userService.updateOne(updateUserForm);
		if (isSuceess) {
			model.addAttribute("message", "ユーザを更新しました");
		} else {
			model.addAttribute("errorMessage", "ユーザ更新に失敗しました。操作をやり直してください。");
		}

		return getUserList(model);
	}
	
	/**
	 * 【管理者】ユーザ削除処理を実行します。
	 *
 	 * <p>本機能は、ユーザ管理機能の削除機能を提供します。
	 * <p><strong>この機能は管理者ロールのユーザのみが利用できます</strong>
	 * @param userId ユーザーID
	 * @param model {@code Model} オブジェクト
	 * @return ユーザー一覧表示用のビュー名
	 */
	@PostMapping("/user/delete")
	public String deleteUser(@RequestParam(name = "userId") String userId, Model model) {
		boolean isSuceess = userService.deleteOne(userId);
		if (isSuceess) {
			model.addAttribute("message", "ユーザを更新しました");
		} else {
			model.addAttribute("errorMessage", "ユーザ更新に失敗しました。操作をやり直してください。");
		}

		return getUserList(model);
	}
	
	/**
	 * ユーザー削除画面を表示するためのコントローラーメソッドです。
	 * @param model {@code Model} オブジェクト
	 * @return ユーザー削除画面のビュー名
	 */
	@GetMapping("/user/delete")
	public String getDeleteUser(Model model) {

		UserEntity userEntity = userService.getUserList();
		model.addAttribute("userEntity", userEntity);
		
		return "user/delete";
	}
	
	/**
	 * 選択した複数のユーザーを一括削除するためのコントローラーメソッドです。
	 * @param users 削除対象のユーザーIDのリスト（カンマ区切り）
	 * @param model {@code Model} オブジェクト
	 * @return ユーザーリスト画面のビュー名
	 */
	@PostMapping("/user/delete/bulk")
	public String deleteBulkUser(@RequestParam(name = "users") String users, Model model) {
		boolean isSuceess = userService.delete(users);
		if (isSuceess) {
			model.addAttribute("message", "選択したユーザを削除しました");
		} else {
			model.addAttribute("errorMessage", "ユーザ一括削除に失敗しました。操作をやり直してください。");
		}
		
		return getUserList(model);
	}

}
