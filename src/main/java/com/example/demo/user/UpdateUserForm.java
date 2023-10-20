package com.example.demo.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserForm {

	/** ユーザID（メールアドレス）*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String userId;

	/** 名前 */
	@NotBlank(message = "{require_check}")
	@Length(min = 2, max = 50, message = "{length_check}")
	private String user_name;

	/** パスワード(平文) */
	@Pattern(regexp = "^$|.{6,}", message = "{pattern_check}")
	private String password;

	/** 権限 */
	@Pattern(regexp = "^(ROLE_ADMIN|ROLE_TOP|ROLE_GENERAL)$")
	private String role;

	/** 有効性 */
	@Pattern(regexp = "^(true|false)$")
	private String enabled;

}