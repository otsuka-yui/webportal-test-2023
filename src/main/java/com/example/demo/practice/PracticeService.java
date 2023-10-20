package com.example.demo.practice;

import org.springframework.stereotype.Service;

@Service
public class PracticeService {

	/**
	 * 引数に渡された文字列を結合する
	 * 
	 * @param local メールアドレスのローカル部
	 * @param domain メールアドレスのドメイン部
	 * @return メールアドレス
	 */
	public String level0(String local, String domain) {
		String result = local + "@" + domain;
		return result;
	}

	/**
	 * 毎月増加する人数(X)とYカ月後の引数で総人口を求める
	 * 
	 * ・A 村が毎月増加する人数 X
	 * ・何か月後の人口を求めるかを表す Y
	 * 
	 * @param input 半角スペースを間にもつXとYの文字列（例：180 2）
	 * @return 総人口
	 */
	public int level1(String input) {
		String[] inputList = input.split(" ");
		
		if(inputList.length < 2) {
			// 半角スペースが存在しない場合はエラー
			return 0;
		}
		
		try {
			int x = Integer.parseInt(inputList[0]);
			int y = Integer.parseInt(inputList[1]);
			int result = 100 + (x * y);
			return result;
		} catch (NumberFormatException e) {
			// 数値に変換できない場合はエラー
			return 0;
		}
	}

	/**
	 * 引数に渡される文字列の最後の文字を出力する
	 * 
	 * @param input 文字列
	 * @return 渡された引数文字列の最後の1文字
	 */
	public String level2(String input) {
		
		if(input == null) {
			// nullの場合は例外をスロー
			throw new IllegalArgumentException();
		}
		
		if(input.length() < 1) {
			// 空文字の場合は空文字を返却
			return "";
		}
		
		String result = input.substring(input.length() - 1);
		return result;
	}

	/**
	 * 引数で渡された文字列の文字列変換を行う
	 * 
	 * @param input 入力文字列
	 * @return 変換後の文字列
	 */
	public String level3(String input) {

		if(input.contains("a")) {
			input = input.replaceAll("a", "");
		}
		
		if(input.contains("A")) {
			input = input.replaceAll("A", "");
		}
		
		if(input.contains("i")) {
			input = input.replaceAll("i", "");
		}
		
		if(input.contains("I")) {
			input = input.replaceAll("I", "");
		}
		
		if(input.contains("u")) {
			input = input.replaceAll("u", "");
		}
		
		if(input.contains("U")) {
			input = input.replaceAll("U", "");
		}
		
		if(input.contains("e")) {
			input = input.replaceAll("e", "");
		}

		if(input.contains("E")) {
			input = input.replaceAll("E", "");
		}

		if(input.contains("o")) {
			input = input.replaceAll("o", "");
		}

		if(input.contains("O")) {
			input = input.replaceAll("O", "");
		}
		
		return input;
		
	}

	/**
	 * ○×ゲームの結果を判定する
	 * 
	 * @param input 入力文字列
	 * @return ○×ゲームの結果文字列
	 */
	public String level4(String input) {

		String[] s = input.split("");
		
		if(s.length < 5 || s.length > 5) {
			throw new IllegalArgumentException();
		}

		int oCount = 0;
		int xCount = 0;

		for (int i = 0; i < 5; i++) {
			// 3つ以上並んでいるか
			if (oCount > 2 || xCount > 2) {
				break;
			}

			// カウンター
			if (s[i].equals("o")) {
				if (xCount > 0) {
					xCount = 0;
				}
				oCount++;
			} else if(s[i].equals("x")){
				if (oCount > 0) {
					oCount = 0;
				}
				xCount++;
			} else {
				throw new IllegalArgumentException();
			}
		}

		String result = "";
		if (oCount > 2) {
			result = "o";
		} else if (xCount > 2) {
			result = "x";
		} else {
			result = "draw";
		}

		return result;
	}
	
}
