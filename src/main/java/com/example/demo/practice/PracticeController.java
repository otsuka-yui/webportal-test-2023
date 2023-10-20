package com.example.demo.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * アルゴリズム道場機能を表す。
 *
 * <p>本機能は、Java言語によるアルゴリズムの練習ための
 * 簡易な入出力や基礎操作を復習するための機能です。
 *
 * @author 情報太郎
 *
 */
@Controller
public class PracticeController {
	
	@Autowired
	private PracticeService practiceService;

	/**
	 * アルゴリズム道場画面を表示します。
	 * 
	 * @return 郵便番号入力画面へのパス
	 */
	@GetMapping("/dojo")
	public String goDojo() {
		return "practice/dojo";
	}

	/**
	 * Level0の処理を行います。
	 * 
	 * @return アルゴリズム道場画面へのパス
	 */
	@PostMapping("/level0")
	public String level0() {
		String local = "jouhou";
		String domain = "hcs.ac.jp";

		String result = practiceService.level0(local, domain);
		System.out.println(result);
		
		return "practice/dojo";
	}

	/**
	 * Level1の処理を行います。
	 * 
	 * @param model HTMLに値を渡すオブジェクト
	 * @return アルゴリズム道場結果画面へのパス
	 */
	@PostMapping("/level1")
	public String level1(Model model) {
		String input = "180 2";

		int result = practiceService.level1(input);
		model.addAttribute("ans", result);

		return "practice/result";
	}

	/**
	 * Level2の処理を行います。
	 * 
	 * @param model HTMLに値を渡すオブジェクト
	 * @return アルゴリズム道場結果画面へのパス
	 */
	@PostMapping("/level2")
	public String level2(Model model) {
		String input = "namae";

		String result = practiceService.level2(input);
		model.addAttribute("ans", result);

		return "practice/result";
	}

	/**
	 * Level3の処理を行います。
	 * 
	 * @param input 入力文字列
	 * @param model HTMLに値を渡すオブジェクト
	 * @return アルゴリズム道場結果画面へのパス
	 */
	@PostMapping("/level3")
	public String level3(@RequestParam(name = "level3") String input, Model model) {

		String result = practiceService.level3(input);
		model.addAttribute("ans", result);

		return "practice/result";
	}

	/**
	 * Level4の処理を行います。
	 * 
	 * @param input 入力文字列
	 * @param model HTMLに値を渡すオブジェクト
	 * @return アルゴリズム道場結果画面へのパス
	 */
	@PostMapping("/level4")
	public String level4(@RequestParam(name = "level4") String input, Model model) {

		String result = practiceService.level4(input);
		model.addAttribute("ans", result);

		return "practice/result";
	}
	
}
