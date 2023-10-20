package com.example.demo.task;

import java.util.Date;

import lombok.Data;

/**
 * 1件分のタスク情報です。
 * 
 * <p>各データ構造については、データベース定義をご覧ください。
 * @author 情報太郎
 */
@Data
public class TaskData {

	/**
	 * タスクID：主キー、SQLにて自動採番
	 */
	private int id;

	/**
	 * ユーザID（メールアドレス）： Userテーブルの主キーと紐づく、ログイン情報から取得
	 */
	private String userId;

	/**
	 * 件名：必須入力
	 */
	private String title;

	/**
	 * 期限日：必須入力
	 */
	private Date limitday;
	
	/**
	 * 完了フラグ：デフォルト値は、false(未完了)
	 */
	private boolean isComplate;

}

