package com.example.demo.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ユーザ作成画面の入力値を保持するクラスです。
 *
 * <p>各項目のデータ仕様は基本設計書を参照してください。
 *
 * @author 情報太郎
 */
@Data
public class UserForm {

	/** ユーザID（メールアドレス）*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String userId;

	/** パスワード */
	@Min(value = 6, message = "{min_check}")
	private String password;

	/** ユーザ名 */
	@Length(min = 2, max = 50, message = "{length_check}")
	private String username;

	/** 権限 */
	@NotBlank(message = "{require_check}")
	private String role;

}

