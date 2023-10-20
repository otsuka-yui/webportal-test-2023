package com.example.demo.bmi;

/**
 * BMI情報を格納するためのエンティティクラスです。
 * <p>
 * このクラスはBMI値、画像のパス、コメントを保持します。
 * </p>
 * @author 情報太郎
 */
public class BmiData {
	
	/** BMI値(小数点第3位まで) */
	private String ans;
	
	/** 画像のパス */
	private String path;
	
	/** コメント */
	private String comment;

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
