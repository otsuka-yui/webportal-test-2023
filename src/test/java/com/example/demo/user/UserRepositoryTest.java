package com.example.demo.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
class UserRepositoryTest {
	@Autowired
	private UserRepository target;

	// モック対象クラスを定義
	@SpyBean
	private NamedParameterJdbcTemplate mock;

	@Test
	void SelectAllメソッドのモック値にList型のMapを設定() {
		// 0.Mock
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", "gon");
		List<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
		returnValue.add(map);

		doReturn(returnValue).when(mock).queryForList(anyString(), anyMap());

		// 1.Ready
		Map<String, Object> readyMap = new HashMap<String, Object>();
		readyMap.put("user_id", "gon");
		List<Map<String, Object>> expected = new ArrayList<Map<String, Object>>();
		expected.add(readyMap);

		// 2.Do
		List<Map<String, Object>> result = target.selectAll();

		// 3.Check
		assertEquals(expected.get(0).get("user_id"), result.get(0).get("user_id"));

		// 4.Log
		log.info("結果：" + result.get(0).get("user_id"));
	}

//	@Test
//	void SelectOneメソッドにモック値0を設定しSQLExceptionを出力() {
//		// 0.Mock
//		int returnValue = 0;
//		doReturn(returnValue).when(mock).update(anyString(), anyMap());
//
//		// 1.Ready
//		String userId = "johotaro";
//		//int excepted = 1;
//
//		// 2.Do 3.Check
//		assertThrows(SQLException.class, () -> target.selectOne(userId));
//
//		// 4.log
//		log.info("結果：SQLException");
//	}

	@Test
	void SelectOneメソッドにモック値1を設定しSQLExceptionを出力しない() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.selectOne(userId));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void Insertメソッドにモック値0を設定しSQLExceptionを出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserData userData = new UserData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.insert(userData));

		// 4.log
		log.info("結果：SQLException");
	}
	
	@Test
	void Insertメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserData userData = new UserData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.insert(userData));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void UpdateWithPasswordメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserData userData = new UserData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.updateWithPassword(userData));

		// 4.log
		log.info("結果：SQLException");
	}

	@Test
	void UpdateWithPasswordメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserData userData = new UserData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.updateWithPassword(userData));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void UpdateWithoutPasswordメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserData userData = new UserData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.updateWithoutPassword(userData));

		// 4.log
		log.info("結果：SQLException");
	}

	@Test
	void UpdateWithoutPasswordメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserData userData = new UserData();
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.updateWithoutPassword(userData));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

	@Test
	void Deleteメソッドにモック値0を設定し出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;

		// 2.Do 3.Check
		assertThrows(SQLException.class, () -> target.delete(userId));

		// 4.log
		log.info("結果：SQLException");
	}

	@Test
	void Deleteメソッドにモック値1を設定し出力() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.delete(userId));

		// 4.log
		log.info("結果：SQLExceptionではない");
	}

}
