package com.itheima.erp.exception;
/**
 * 定义一个异常，用于抛出错误信息
 * @author KeminaPera
 *
 */
public class ErpException extends RuntimeException {

	private static final long serialVersionUID = -7043845143111918914L;

	public ErpException(String message) {
		super(message);
	}
}
