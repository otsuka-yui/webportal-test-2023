package com.example.demo.user;

import lombok.Data;

/**
 * 1件分のユーザ情報です。
 *
 * <p>各項目のデータ構造については、データベース定義をご覧ください。
 * @author 情報太郎
 */
@Data
public class UserData {

	/**
	 * ユーザID（メールアドレス）
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String userId;

	/**
	 * パスワード
	 * 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	private String password;

	/**
	 * アカウント有効性
	 * - 有効 : true
	 * - 無効 : false
	 * ユーザ作成時はtrue固定
	 */
	private boolean enabled;

	/**
	 * ユーザ名
	 * 必須入力
	 */
	private String user_name;

	/**
	 * 権限
	 * - 管理 : "ROLE_ADMIN"
	 * - 上位： "ROLE_TOP"
	 * - 一般 : "ROLE_GENERAL"
	 * 必須入力
	 */
	private String role;
}
