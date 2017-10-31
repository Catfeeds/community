package com.tongwii.bean;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class TongWIIResult implements Serializable {
	public static final String SUCCESS_INFO = "操作成功";
	public static final String ERROR_INFO = "操作失败";
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
	 */
	public void successResult() {
		this.status = HttpStatus.OK.value();
		this.info = SUCCESS_INFO;
	}

	/**
	 * 发送处理成功消息
	 *
	 * @param info 成功提示
	 */
	public void successResult(String info) {
		this.status = HttpStatus.OK.value();
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
	 * 发送处理失败消息
	 *
	 */
	public void errorResult() {
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		this.info = ERROR_INFO;
	}

	/**
	 * 发送处理失败消息
	 *
	 * @param info 失败提示
	 */
	public void errorResult(String info) {
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
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
