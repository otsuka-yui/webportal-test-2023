package com.example.demo.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
class UserServiceTest {
	@Autowired
	private UserService target;

	// モック対象クラスを定義
	@SpyBean
	private NamedParameterJdbcTemplate mock;
	
	@Test
	void GetUserListメソッドのモック値にList型のMapを設定() {
		 // 0.Mock
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", "gon");
        
        map.put("enabled", false);
        
        List<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
        returnValue.add(map);
        doReturn(returnValue).when(mock).queryForList(anyString(), anyMap());
        
        // 1.Ready
        String expected = "gon";
        
        // 2.Do
        UserEntity result = target.getUserList();
        
        // 3.Check
        assertEquals(expected, result.getUserlist().get(0).getUserId());

        // 4.Log
        log.info("結果：" + result);
	}

	@Test
	void InsertOneメソッドにモック値0を設定しfalseを出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UserForm userForm = new UserForm();
		userForm.setUserId("gon");
		userForm.setPassword("123456");
		userForm.setUsername("ごん");
		userForm.setRole("a");
		
		//int excepted = 1;

		// 2.Do 3.Check
		boolean result = target.insertOne(userForm);
		assertEquals(false, result);
		// 4.log
		log.info("結果：" + result);
	}
	
	@Test
	void InsertOneメソッドにモック値1を設定しSQLExceptionを出力されない() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		UserForm userForm = new UserForm();
		userForm.setUserId("gon");
		userForm.setPassword("123456");
		userForm.setUsername("ごん");
		userForm.setRole("a");
		//int excepted = 1;
		
		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.insertOne(userForm));
		
		// 4.log
		log.info("結果：SQLExceptionが発生しない");
	}

	@Test
	void GetUserメソッドにモック値1を設定しSQLExceptionを出力されない() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UpdateUserForm updateUserForm = new UpdateUserForm();
		//int excepted = 1;
		updateUserForm.setUserId("taro@xxx.co.jp");
		updateUserForm.setUser_name("ごん");
		updateUserForm.setRole("ROLE_ADMIN");
		updateUserForm.setEnabled("true");

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.getUser(updateUserForm));

		// 4.log
		log.info("結果：SQLExceptionが発生しない");
	}

	@Test
	void UpdateOneメソッドにモック値0を設定しfalseを出力() {
		// パスワードが空の場合

		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		UpdateUserForm updateUserForm = new UpdateUserForm();
		updateUserForm.setPassword("");
		//int excepted = 1;
		
		// 2.Do 3.Check
		boolean result = target.updateOne(updateUserForm);
		assertEquals(false, result);
		// 4.log
		log.info("結果：" + result);
	}

	@Test
	void UpdateOneメソッドにモック値1を設定しSQLExceptionが出力されない() {
		// パスワードが空の場合
		
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		UpdateUserForm updateUserForm = new UpdateUserForm();
		updateUserForm.setPassword("");
		//int excepted = 1;

		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.updateOne(updateUserForm));

		// 4.log
		log.info("結果：SQLExceptionが発生しない");
	}
	
	@Test
	void UpdateOneメソッドにモック値1を設定しSQLExceptionを出力されない() {
		// パスワードを変更する場合
		
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		UpdateUserForm updateUserForm = new UpdateUserForm();
		updateUserForm.setPassword("123456");
		//int excepted = 1;
		
		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.updateOne(updateUserForm));
		
		// 4.log
		log.info("結果：SQLExceptionが発生しない");
	}

	@Test
	void DeleteOneメソッドにモック値0を設定しfalseを出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String userId = "";
		//int excepted = 1;

		// 2.Do 3.Check
		boolean result = target.deleteOne(userId);
		assertEquals(false, result);
		// 4.log
		log.info("結果：" + result);
	}
	
	@Test
	void DeleteOneメソッドにモック値1を設定し出力されない() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		String userId = "johotaro";
		//int excepted = 1;
		
		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.deleteOne(userId));
		
		// 4.log
		log.info("結果：SQLExceptionが発生しない");
	}

	@Test
	void Deleteメソッドにモック値0を設定しfalseを出力() {
		// 0.Mock
		int returnValue = 0;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());

		// 1.Ready
		String users = "johotaro,johohanako";
		//int excepted = 1;

		// 2.Do 3.Check
		boolean result = target.delete(users);
		assertEquals(false, result);
		// 4.log
		log.info("結果：" + result);
	}
	
	@Test
	void Deleteメソッドにモック値1を設定しSQLExceptionを出力されない() {
		// 0.Mock
		int returnValue = 1;
		doReturn(returnValue).when(mock).update(anyString(), anyMap());
		
		// 1.Ready
		String users = "johotaro,johohanako";
		//int excepted = 1;
		
		// 2.Do 3.Check
		assertDoesNotThrow(() -> target.delete(users));
		
		// 4.log
		log.info("結果：SQLExceptionが発生しない");
	}

}
