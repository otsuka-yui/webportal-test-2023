package com.example.demo.task;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.WebConfig;

/**
 * タスク機能の業務ロジックを処理します。
 * 
 * <p>
 * 本クラスは以下の処理を主に行います。
 * <ul>
 * <li>データの相互変換</li>
 * <li>例外処理</li>
 * <li>Repositoryクラスへの問い合わせ</li>
 * <li>Controllerクラスへのリターン</li>
 * </ul>
 * 
 * @author 情報太郎
 */
@Transactional
@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	/**
	 * ユーザIDに合致するタスク一覧を取得します。
	 * 
	 * <p>
	 * DBエラーが発生した場合は、空のタスク一覧を設定して呼び出し元へ返却します。
	 * 
	 * @param userId ユーザID(null不可)
	 * @return タスク一覧
	 */
	public TaskEntity selectAll(String userId) {
		List<Map<String, Object>> resultSet;
		resultSet = taskRepository.findAll(userId);
		TaskEntity taskEntity = mappingSelectResult(resultSet);
		return taskEntity;
	}

	/**
	 * タスクを保存します。
	 * 
	 * <p>
	 * DBエラーが発生した場合は、呼び出し元に失敗の通知を行います。
	 * 
	 * @param userId   ユーザID(null不可)
	 * @param title    タイトル(null不可)
	 * @param limitday 期限日(null不可)
	 * @return 成功可否
	 */
	public boolean insert(String userId, String title, String limitday) {
		// TaskData型へ詰め替える
		TaskData taskData = refillToData(userId, title, limitday);

		try {
			taskRepository.save(taskData);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * タスクを削除します。
	 * 
	 * <p>
	 * DBエラーが発生した場合は、呼び出し元に失敗の通知を行います。
	 * 
	 * @param id タスクID
	 * @return 成功可否
	 */
	public boolean delete(String id) {
		int i = Integer.parseInt(id);
		try {
			taskRepository.delete(i);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * タスクを完了状態にします。
	 * 
	 * <p>
	 * DBエラーが発生した場合は、呼び出し元に失敗の通知を行います。
	 * 
	 * @param id タスクID
	 * @return 成功可否
	 */
	public boolean complate(String id) {
		int i = Integer.parseInt(id);
		try {
			taskRepository.update(i);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * タスクをCSVファイルに出力します。
	 * 
	 * <p>
	 * 条件に合致するタスクがない場合は、空のファイルが出力されます。
	 * 
	 * @param userId ユーザID(null不可)
	 */
	public ResponseEntity<byte[]> taskListCsvOut(String userId) {

		// サーバに一時的にCSVファイルを生成
		taskRepository.fileOut(userId);

		try {
			// CSVファイルをサーバから読み込み
			byte[] bytes = getFile(WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV);
			// CSVファイルのダウンロード用ヘッダー情報設定
			HttpHeaders header = generateHeader();
			return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public HttpHeaders generateHeader() {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_TASK_CSV);
		return header;
	}
	
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}

	public boolean validate(String comment, String limitday) {
		// nullチェック、必須チェック、50文字超過チェック
		if (comment == null || comment.isBlank() || comment.length() > 50) {
			return false;
		}

		// nullチェック、必須チェック
		if (limitday == null || limitday.isBlank()) {
			return false;
		}

		// 日付形式チェック(SimpleDateFomatの変換可否を利用)
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.parse(limitday);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

		
	public boolean validate(String id) {
		// 数値チェック(1桁～Intの最大値)
		Pattern p = Pattern.compile("^\\d{1,9}$");
		if (id.isBlank()) {
			return false;
		} else if (!p.matcher(id).find()) {
			return false;
		}
		return true;
	}


	public TaskData refillToData(String userId, String title, String limitDay) {
		TaskData taskData = new TaskData();
		taskData.setUserId(userId);
		taskData.setTitle(title);
		taskData.setComplate(false);

		// String型からDate型へ変換する
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			taskData.setLimitday(format.parse(limitDay));
		} catch (ParseException e) {
			// 何もしない（入力チェック済みのため、変換エラーは起こり得ない）
		}

		return taskData;
	}

	public TaskEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {
		TaskEntity entity = new TaskEntity();

		for (Map<String, Object> map : resultList) {
			TaskData data = new TaskData();
			data.setId((Integer) map.get("id"));
			data.setUserId((String) map.get("user_id"));
			data.setTitle((String) map.get("title"));
			data.setLimitday((Date) map.get("limitday"));
			data.setComplate((boolean) map.get("complate"));

			entity.getTaskList().add(data);
		}
		return entity;
	}
}