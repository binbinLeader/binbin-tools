package com.binbin._enum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 接口模式枚举
 * @author: jiaobin
 * @time: 2019年1月17日
 */
public enum EmToolsType {

	/**
	 * 这里是选择列表
	 */
	CALC_PHONE_USE_TIME(1, "手机电量时长计算"),
	EXIT(88, "输入'exit bye see you'任意单词, 退出彬彬工具箱"),
	;

	private Integer code;
	private String name;

	private EmToolsType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public static Map<Integer, String> listEnum() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		EmToolsType[] enumList = values();
		for (EmToolsType enumType : enumList) {
			map.put(enumType.getCode(), enumType.getName());
		}
		return map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
