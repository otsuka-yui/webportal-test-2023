package com.example.demo.task;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * タスク管理機能を表す。
 * 
 * <p>本機能は、タスク管理に関わるCRUDを実現します。
 * 
 * <p>入力チェックを実施し、正しいデータ項目のみ処理します。
 * クライアント側でも入力チェックを実施することを推奨します。
 * 
 * @author 情報太郎
 *
 */
@Controller
public class TaskController {

	/* タスク管理の業務ロジッククラス */
	@Autowired
	private TaskService taskService;

	/**
	 * ログイン中のユーザに紐づく、タスク一覧画面を表示します。
	 * 
	 * <p>本機能は、タスク管理機能の一覧機能を提供します。
	 * 
	 * @param principal ログイン中のユーザ情報を格納(null不可)
	 * @param model Viewに値を渡すオブジェクト(null不可)
	 * @return タスク一覧画面へのパス(null不可)
	 */
	@GetMapping("/task")
	public String getTaskList(Principal principal, Model model) {

		TaskEntity taskEntity = taskService.selectAll(principal.getName());
		model.addAttribute("taskEntity", taskEntity);

		return "task/list";
	}


	/**
	 * 入力されたタスクをDBへ登録します。
	 * 
	 * <p>本機能は、タスク管理機能の登録機能を提供します。
	 * 
	 * @param title タスク内容の文字列を格納(null不可)
	 * @param limit 期限日の文字列を格納(null不可)
	 * @param principal ログイン中のユーザ情報を格納(null不可)
	 * @param model Viewに値を渡すオブジェクト(null不可)
	 * @return タスク一覧画面へのパス(null不可)
	 */
	@PostMapping("/task/insert")
	public String insertTask(@RequestParam(name = "title") String title,
			                 @RequestParam(name = "limit") String limit,
			Principal principal, Model model) {
		
		// 入力チェック
		boolean isValid = taskService.validate(title, limit);
		if (!isValid) {
			model.addAttribute("errorMessage", "入力項目に不備があります");
			return getTaskList(principal, model);
		}

		// 実行結果を取得
		boolean isSuccess = taskService.insert(principal.getName(), title, limit);
		if (isSuccess) {
			model.addAttribute("message", "正常に登録されました");
		} else {
			model.addAttribute("errorMessage", "登録できませんでした。再度登録し直してください");
		}
		
		return getTaskList(principal, model);
	}

	/**
	 * 指定されたタスクIDをDBから削除します。
	 * 
	 * <p>本機能は、タスク管理機能の削除機能を提供します。
	 * 
	 * @param id タスクIDの文字列を格納(null不可)
	 * @param principal ログイン中のユーザ情報を格納(null不可)
	 * @param model Viewに値を渡すオブジェクト(null不可)
	 * @return タスク一覧画面へのパス(null不可)
	 */
	@PostMapping("/task/delete")
	public String deleteTask(@RequestParam("id") String id, Principal principal, Model model) {
		
		boolean isValid = taskService.validate(id);
		if(!isValid) {
			return "index";
		}
		
		boolean isSuccess = taskService.delete(id);
		if (isSuccess) {
			model.addAttribute("message", "正常に削除されました");
		} else {
			model.addAttribute("errorMessage", "削除できませんでした。再度登録し直してください");
		}
		return getTaskList(principal, model);
	}
	
	/**
	 * 指定されたタスクIDの状態を完了に変更します。
	 * 
	 * <p>本機能は、タスク管理機能の状態変更機能を提供します。
	 * 
	 * @param id タスクIDの文字列を格納(null不可)
	 * @param principal ログイン中のユーザ情報を格納(null不可)
	 * @param model Viewに値を渡すオブジェクト(null不可)
	 * @return タスク一覧画面へのパス(null不可)
	 */
	@PostMapping("/task/complate")
	public String complateTask(@RequestParam("id") String id, Principal principal, Model model) {
		
		boolean isValid = taskService.validate(id);
		if(!isValid) {
			return "index";
		}
		
		boolean isSuccess = taskService.complate(id);
		if (isSuccess) {
			model.addAttribute("message", "正常に更新されました");
		} else {
			model.addAttribute("errorMessage", "更新できませんでした。再度登録し直してください");
		}
		return getTaskList(principal, model);
	}

	/**
	 * ログイン中のユーザに紐づく、タスクを全件CSVファイルに出力します。
	 * 
	 * <p>本機能は、タスク管理機能のCSV出力機能を提供します。
	 * 
	 * @param principal ログイン中のユーザ情報を格納(null不可)
	 * @param model Viewに値を渡すオブジェクト(null不可)
	 * @return CSVファイル
	 */
	@PostMapping("/task/csv")
	public ResponseEntity<byte[]> getTaskCsv(Principal principal, Model model) {

		// CSVファイルをサーバ上に作成
		ResponseEntity<byte[]> csv = taskService.taskListCsvOut(principal.getName());

		// CSVファイルを端末へ送信
		return csv;
	}
	
}

