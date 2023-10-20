package com.example.demo.bmi;

import org.springframework.stereotype.Service;

/**
 * BMI計算の業務ロジックを提供するサービスクラスです
 * <p>
 * このクラスは身長と体重を受け取り、BMI情報を計算して返すビジネスロジックを提供します
 * </p>
 * @see <a href="https://keisan.casio.jp/exec/system/1161228732">BMIと適正体重 - 高精度計算サイト - Keisan</a>
 * @author 情報太郎
 */
@Service
public class BmiService {

	/**
	 * 与えられた身長と体重からBMI（ボディマス指数）を計算し、
	 * 小数点第二位まで切り捨てて返すメソッド
	 * ※BMIは体重を身長の二乗で割ることで算出可能
	 * 
	 * @param height 計算に使用する身長をセンチメートルで指定
	 * @param weigh 計算に使用する体重をキログラムで指定
	 * @return 計算されたBMIを小数点第二位まで切り捨てた文字列が返却される
	 * 
	 * @throws NumberFormatException 入力が数値に変換できない場合に発生する
	 * @throws ArithmeticException 身長が0の場合に発生する
	 */
	public String calc(String height, String weigh) {
		
		try {
			// 身長をセンチメートルからメートルへ返還
			double m = Double.parseDouble(height) / 100;
			// BMIを計算
			double bmi = Double.parseDouble(weigh) / (Integer.parseInt(height) * m);
			// 小数点第二位まで切り捨て
			String ans = String.format("%.3f", bmi * 100);
			return ans;
		} catch (NumberFormatException | ArithmeticException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 与えられたBMI値に基づいて体重の状態を判定し、コメントを返すメソッド
	 * BMI（ボディマス指数）に基づいて以下の範囲で判定を行う
	 * - 16未満: 痩せすぎ
	 * - 16以上17未満: 痩せ
	 * - 17以上18.49未満: 痩せぎみ
	 * - 18.49以上24.99未満: 普通体重
	 * - 24.99以上29.99未満: 前肥満
	 * - 29.99以上34.99未満: 肥満(1度)
	 * - 34.99以上39.99未満: 肥満(2度)
	 * - 39.99以上: 肥満(3度)
	 * 
	 * @param ans 判定対象のBMI値を文字列で指定する
	 * @return BMI値に基づく体重の状態コメントが返却される
	 * @throws NumberFormatException 入力が数値に変換できない場合に発生する
	 */
	public String judge(String ans) {
		double bmi = 0;
		
		try {
			// 文字列型から浮動小数点型へ変換
			bmi = Double.parseDouble(ans);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		// BMI値に基づいて体重の状態を判定
		String comment;
		if(bmi < 16) {
			comment = "痩せすぎ";
		} else if(bmi <= 16.99) {
			comment = "痩せ";
		} else if(bmi <= 18.49) {
			comment = "痩せぎみ";
		} else if(bmi <= 24.99) {
			comment = "普通体重";
		} else if(bmi <= 29.99) {
			comment = "前肥満";
		} else if(bmi <= 34.99) {
			comment = "肥満(1度)";
		} else if(bmi <= 39.99) {
			comment = "肥満(2度)";
		} else {
			comment = "肥満(3度)";
		}
		
		// 判定結果を返す
		return comment;
	}
	
	/**
	 * 与えられたBMI値に基づいて適切な画像のパスを返すメソッド
	 * BMI（ボディマス指数）に基づいて以下の範囲で画像のパスを判定を行う
	 * - 18.50未満: "gari.png"（痩せすぎの画像）
	 * - 18.50以上25.00未満: "normal.png"（普通体重の画像）
	 * - 25.00以上: "puni.png"（肥満の画像）
	 * 
	 * @param ans 判定対象のBMI値を文字列で指定する
	 * @return BMI値に基づく適切な画像のパスが返却される
	 * @throws NumberFormatException 入力が数値に変換できない場合に発生する
	 */
	public String img(String ans) {
		double bmi = 0;
		
		try {
			// 文字列型から浮動小数点型へ変換
			bmi = Double.parseDouble(ans);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		// BMI値に基づいて適切な画像のパスを判定
		String path;
		if(bmi < 18.50) {
			path = "/img/bmi/gari.png";
		} else if(bmi < 25.00) {
			path = "/img/bmi/normal.png";
		} else {
			path = "/img/bmi/puni.png";
		}
		
		// 画像のパスを返す
		return path;
	}
	
	/**
	 * 身長と体重を受け取り、BMI情報を計算して返すメソッドです
	 * @param height 身長（単位：センチメートル）
	 * @param weight 体重（単位：キログラム）
	 * @return BMI情報を格納したBmiEntityオブジェクト
	 */
	public BmiData exec(String height, String weigh) {
		
		// BMI情報を格納するクラスを生成
		BmiData entity = new BmiData();
		
		// 計算結果を格納
		String ans = calc(height, weigh);
		entity.setAns(ans);
		
		// コメントを格納
		String comment = judge(ans);
		entity.setComment(comment);
		
		// 画像パスを格納
		String img = img(ans);
		entity.setPath(img);
		
		return entity;
	}
}
