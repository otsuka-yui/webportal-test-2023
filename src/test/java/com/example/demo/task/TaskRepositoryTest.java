package com.example.demo.task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class TaskRepositoryTest {
	@Autowired
	private TaskRepository target;

	// モック対象クラスを定義
	@SpyBean
	private NamedParameterJdbcTemplate mock;

	@Test
	void FindAllメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.findAll(userId));

		// 4.log
		log.info("結果：SQLException");
	}

	@Test
	void FindAllメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.findAll(userId));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void Saveメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		TaskData taskdata = new TaskData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.save(taskdata));

		// 4.log
		log.info("結果：SQLException");
	}

	@Test
	void Saveメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		TaskData taskdata = new TaskData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.save(taskdata));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void deleteメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		int id = 0;
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.delete(id));

		// 4.log
		log.info("結果：SQLException");
	}

	@Test
	void deleteメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		int id = 0;
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.delete(id));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void Updateメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		int id = 0;
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.update(id));

		// 4.log
		log.info("結果：SQLException");
	}
	
	@Test
	void Updateメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		int id = 0;
		//int excepted = 1;
		
		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.update(id));
		
		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void FileOutメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;
		
		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.fileOut(userId));
		
		// 4.log
		log.info("結果：SQLException");
	}

//	@Test
//	void FileOutメソッドにモック値1を設定し出力() {
//		// 0.Mock
//		int returnValue = 1;
//		doReturn(returnValue).when(mock).query(anyString(), anyMap(), any());
//
//		// 1.Ready
//		String userId = "johotaro";
//		//int excepted = 1;
//
//		// 2.Do 3.Check
//		assertDoesNotThrow(() -> target.fileOut(userId));
//
//		// 4.log
//		log.info("結果：SQLExceptionではない");
//	}

}
