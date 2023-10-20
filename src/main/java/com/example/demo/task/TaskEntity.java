package com.example.demo.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 複数件のタスク情報を保持します。
 * 
 * <p>DBとController間を本クラスでモデル化します。<br>
 * DBからタスク情報が取得できない場合は、リストが空となります。
 * <p><strong>リストにnullは含まれません</strong>
 * 
 * @author 情報太郎
 */
@Data
public class TaskEntity {

	/** タスク情報のリスト */
	private List<TaskData> taskList = new ArrayList<TaskData>();
	
	/** エラーメッセージ(表示用) */
	private String errorMessage;

}