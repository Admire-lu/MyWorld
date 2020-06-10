package com.cly.mineworld.service.vo.common;

import net.sf.json.JSONObject;

/**
 * 控制器返回Vo
 * @author william
 *
 */
public class ResultVo {

	private String result;
	private String message;
	private JSONObject jsonData;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JSONObject getJsonData() {
		return jsonData;
	}
	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}
}
