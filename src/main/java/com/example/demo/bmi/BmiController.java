package com.example.demo.bmi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * BMI計算を行うためのコントローラクラスです。
 * <p>
 * このクラスはSpring MVCフレームワークでリクエストを処理し、BMI計算の画面表示を担当します。
 * </p>
 * 
 * <p>入力チェックを実施し、正しい郵便番号のみ処理します。<br>
 * クライアント側でも入力チェックを実施することを推奨します。
 * 
 * @author 情報太郎
*/
@Controller
public class BmiController {

	/** BMI計算の業務ロジッククラス */
	@Autowired
	private BmiService bmiService;
	
	/**
	 * BMI入力画面へのリクエストハンドラです。
	 * @return BMI入力画面のパス
	 */
	@GetMapping("/bmi")
	public String getBmi() {
		// 画面のみを返却
		return "bmi/input";
	}
	
	/**
	 * BMIを計算し、結果を表示するためのリクエストハンドラです。
	 * @param model モデルオブジェクト
	 * @param height 身長（単位：センチメートル）
	 * @param weight 体重（単位：キログラム）
	 * @return BMIの計算結果を表示する画面のパス
	 */
	@PostMapping("/bmi")
	public String postBmi(Model model, @RequestParam(name = "cm") String height, 
			@RequestParam(name = "kg") String weight) {

		// データを取得
		BmiData entity = bmiService.exec(height, weight);
		// データをモデルオブジェクトに設定
		model.addAttribute("bmi", entity);
		// 画面を返却
		return "bmi/result";
	}
}
