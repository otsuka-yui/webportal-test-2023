package com.example.demo.prime;

import org.springframework.stereotype.Service;

@Service
public class PrimeService {

	/**
	 * 与えられた整数以下の全ての素数を検出し、文字列として返すメソッド
	 * ※素数は1より大きい整数で、その数自体と1以外の整数で割り切れない数
	 * 
	 * @param input 検出対象の整数を文字列形式で指定する
	 * @return 入力された整数以下の素数を改行区切りで含んだ文字列が返却される
	 *         素数が存在しない場合は空の文字列が返される
	 * @throws NumberFormatException 入力が整数に変換できない場合
	 */
	public String exec(String input) {
	    // 出力文字列の初期化
	    String ans = "";

	    int num = 0;
	    
	    try {
	    	// 入力文字列を整数に変換
	    	num = Integer.parseInt(input);
	    } catch(NumberFormatException e) {
	    	throw new IllegalArgumentException();
	    }
	    
	    if(num < 1) {
	    	throw new IllegalArgumentException();
	    }
	    
	    // 2から入力された数までの各数について素数かどうかを判定
	    for (int i = 2; i <= num; i++) {
	        if (isPrime(i)) {
	            // 素数の場合、出力文字列に追加（改行付き）
	            ans += i + "\r\n";
	        }
	    }

	    // 結果を返す
	    return ans;
	}

	/**
	 * 指定された整数が素数であるかどうかを判定するメソッド
	 * 
	 * @param num 判定対象の整数を指定
	 * @return 指定された整数が素数の場合は true、そうでない場合は false を返却する
	 */
	public boolean isPrime(int num) {
	    // 1以下の数は素数ではない
	    if (num <= 1) {
	        return false;
	    }

	    // 2から平方根までの各数で割り切れるかどうかを判定
	    for (int i = 2; i <= Math.sqrt(num); i++) {
	        if (num % i == 0) {
	            // 割り切れる場合、素数ではない
	            return false;
	        }
	    }

	    // 割り切れない場合、素数である
	    return true;
	}
}
