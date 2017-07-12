package com.tongwii.bean;

import com.tongwii.constant.ResultConstants;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * 处理返回结果
 *
 */
public class TongWIIResult implements Serializable {
	/**
	 * 处理结果状态
	 */
	private int status;
	/**
	 * 处理结果
	 */
	private String info;
	/**
	 * 处理结果数据
	 */
	private Object data;

	public TongWIIResult() {
	}

	public TongWIIResult(int status, String info, Object data) {
		this.status = status;
		this.info = info;
		this.data = data;
	}

	/**
	 * 发送处理成功消息
	 *
	 * @param info 成功提示
	 */
	public void successResult(String info) {
		this.status = ResultConstants.SUCCESS;
		this.info = info;
	}

	/**
	 * 发送成功消息，带数据
	 *
	 * @param info
	 * @param object
	 */
	public void successResult(String info, Object object) {
		successResult(info);
		this.data = object;
	}

	/**
	 * 发送成功消息，带数据，提供字段排除字段功能
	 *
	 * @param info
	 * @param object
	 * @param excludes 需要排除的字段
	 */
	public void successResult(String info, Object object, final String[] excludes) {
		successResult(info);
		final JsonConfig jsonConfig = new JsonConfig();
		if (excludes != null) {
			jsonConfig.setExcludes(excludes);
		}
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		final JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);

		this.data = jsonObject;
	}

	/**
	 * 发送处理失败消息
	 *
	 * @param info 失败提示
	 */
	public void errorResult(String info) {
		this.status = ResultConstants.ERROR;
		this.info = info;
	}

	/**
	 * 发送处理失败消息，带数据
	 *
	 * @param info
	 * @param data
	 */
	public void errorResult(String info, Object data) {
		errorResult(info);
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
