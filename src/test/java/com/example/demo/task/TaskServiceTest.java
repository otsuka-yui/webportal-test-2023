package com.example.demo.task;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

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
class TaskServiceTest {
	@Autowired
	private TaskService target;

	// モック対象クラスを定義
	@SpyBean
	private NamedParameterJdbcTemplate mock;

	@Test
	void SelectAllメソッドのモック値にList型のMapを設定() throws ParseException {
		// 0.Mock
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", 0);
//		map.put("user_id", "admin");
//		map.put("title", "このレコードは消さないこと");
//		map.put("limitday", "2022-11-11");
//		map.put("complate", true);
//
//		List<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
//		returnValue.add(map);
//		doReturn(returnValue).when(mock).queryForList(anyString(), anyMap());
//
//		// 1.Ready
//		int id = 1;
//		String user_id = "admin";
//		String title = "このレコードは消さないこと";
//		String exday = "2022/11/11";
//		Date exlimitday = format.parse(exday);
//		boolean complate = true;
//
//		// 2.Do
//		TaskEntity result = target.selectAll(user_id);
//
//		// 3.Check
//		assertEquals(id, result.getTaskList().get(0).getId());
//		assertEquals(user_id, result.getTaskList().get(0).getUserId());
//		assertEquals(title, result.getTaskList().get(0).getTitle());
//		assertEquals(exlimitday, result.getTaskList().get(0).getLimitday());
//		assertEquals(complate, result.getTaskList().get(0).isComplate());
//
//		// 4.Log
//		log.info("結果：" + result);
	}

	@Test
	void testInsert() {
		fail("まだ実装されていません");
	}

	@Test
	void testDelete() {
		fail("まだ実装されていません");
	}

	@Test
	void testComplate() {
		fail("まだ実装されていません");
	}

	@Test
	void testTaskListCsvOut() {
		fail("まだ実装されていません");
	}

	@Test
	void testGenerateHeader() {
		fail("まだ実装されていません");
	}

	@Test
	void testGetFile() {
		fail("まだ実装されていません");
	}

	@Test
	void testValidateStringString() {
		fail("まだ実装されていません");
	}

	@Test
	void testValidateString() {
		fail("まだ実装されていません");
	}

	@Test
	void testRefillToData() {
		fail("まだ実装されていません");
	}

	@Test
	void testMappingSelectResult() {
		fail("まだ実装されていません");
	}

}
