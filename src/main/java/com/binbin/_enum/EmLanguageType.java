package com.binbin._enum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 语言类型枚举
 * @author: jiaobin
 * @time: 2019年1月17日
 */
public enum EmLanguageType {

	/**
	 * 这里是选择列表
	 */
	ENGLISH(1, "英语"),
	CHINESE(2, "中文"),
	;

	private Integer code;
	private String name;

	private EmLanguageType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public static Map<Integer, String> listEnum() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		EmLanguageType[] enumList = values();
		for (EmLanguageType enumType : enumList) {
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
