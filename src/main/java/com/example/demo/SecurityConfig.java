package com.example.demo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * セキュリティの設定を行うための設定クラスです。
 * {@code @EnableWebSecurity} を付与し、{@code @Configuration} アノテーションを持ちます。
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	/** ユーザーIDとパスワードとアカウントの有効性を取得するSQL */
	private static final String USER_SQL = "SELECT user_id, encrypted_password as password, enabled FROM m_user WHERE user_id = ?";

	/** ユーザーIDと権限を取得するSQL */
	private static final String ROLE_SQL = "SELECT user_id, role FROM m_user WHERE user_id = ?";

	/**
	 * パスワードのエンコードに使用するPasswordEncoderを提供します。
	 * @return PasswordEncoderオブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * WebSecurityをカスタマイズするためのWebSecurityCustomizerを提供します。
	 * @return WebSecurityCustomizerオブジェクト
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {

		return new WebSecurityCustomizer() {

			@Override
			public void customize(WebSecurity web) {
				// 静的リソースへのアクセスには、セキュリティを適用しない
				web.ignoring().requestMatchers("/css/∗∗", "/img/∗∗", "/h2-console/∗∗");
			}
		};
	}

	/**
	 * セキュリティフィルターチェーンを構成し、セキュリティ設定を行います。
	 * @param http HttpSecurityオブジェクト
	 * @return SecurityFilterChainオブジェクト
	 * @throws Exception 例外が発生した場合
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// ログイン不要ページの設定
		http.authorizeHttpRequests().requestMatchers("/css/**","/js/**").permitAll() // cssへアクセス許可
				.requestMatchers("/login").permitAll() // ログインページは直リンクOK
				.requestMatchers("/signup").permitAll() // 新規ユーザー登録画面は直リンクOK
				.requestMatchers("/user/list").hasAnyAuthority("ROLE_ADMIN","ROLE_TOP")// ユーザ一覧機能は管理権限ユーザと上位ユーザに許可
				.requestMatchers("/user/**").hasAuthority("ROLE_ADMIN") // ユーザ管理機能は管理権限ユーザに許可
				.requestMatchers("/h2-console/**").permitAll() // XXX h2-console使用時は有効にする.
				.anyRequest().authenticated(); // それ以外は直リンク禁止

		//ログイン処理
		http.formLogin().loginProcessingUrl("/login") // ログイン処理のパス
				.loginPage("/login") // ログインページの指定
				.failureUrl("/login") // ログイン失敗時の遷移先
				.usernameParameter("user_id") // ログインページのユーザID
				.passwordParameter("password") // ログインページのパスワード
				.defaultSuccessUrl("/", true); // ログイン成功後の遷移先

		//ログアウト処理
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout") //ログアウト処理のパス
				.logoutSuccessUrl("/login"); //ログアウト成功後の遷移先

		// (開発用)CSRF対策 無効設定
		// XXX h2-console使用時は有効にする.
		http.csrf().disable();
		http.headers().frameOptions().disable();

		return http.build();
	}

	/**
	 * ユーザー情報の管理を行うUserDetailsManagerを生成します。
	 * @param dataSource データソース
	 * @return UserDetailsManagerオブジェクト
	 */
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery(USER_SQL);
        users.setAuthoritiesByUsernameQuery(ROLE_SQL);
        return users;
    }

}
