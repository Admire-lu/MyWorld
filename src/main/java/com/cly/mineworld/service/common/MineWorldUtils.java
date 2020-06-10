package com.cly.mineworld.service.common;

import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;

public class MineWorldUtils {

	public static ResultVo createResultVo(String result,String message,JSONObject jsonData) {
		ResultVo vo = new ResultVo();
		vo.setResult(result);
		vo.setMessage(message);
		vo.setJsonData(jsonData);
		return vo;
	}
}
