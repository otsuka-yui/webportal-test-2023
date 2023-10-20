package com.example.demo.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ユーザ情報を管理するクラスです。
 * 
 * <p>DBとController間を本クラスでモデル化します。<br>
 * DBからタスク情報が取得できない場合は、リストが空となります。
 * <p><strong>リストにnullは含まれません</Strong>
 */
@Data
public class UserEntity {

	/** ユーザ情報のリスト */
	private List<UserData> userlist = new ArrayList<UserData>();

	/** エラーメッセージ(表示用) */
	private String errorMessage;
	
}
