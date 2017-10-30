package com.tongwii.core;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一API响应结果封装
 */
public final class Result {
    private String code;
    private String message;
    private Object data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Result() {
    }

    public Result(final String code) {
        this(code, null, null);
    }

    public Result(final String code, final String message) {
        this(code, message, null);
    }

    public Result(final String code, final String message, final Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Success result result.
     *
     * @return the result
     */
    public static Result successResult() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getStatusMessage());
    }

    /**
     * Success result result.
     *
     * @param message the message
     * @return the result
     */
    public static Result successResult(final String message) {
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    /**
     * Success result result.
     *
     * @param object the object
     * @return the result
     */
    public static Result successResult(final Object object) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getStatusMessage(), object);
    }


    /**
     * Fail result result.
     *
     * @return the result
     */
    public static Result failResult() {
        return new Result(ResultCode.FAIL.getCode(), ResultCode.FAIL.getStatusMessage());
    }

    /**
     * Fail result result.
     *
     * @param message the message
     * @return the result
     */
    public static Result failResult(final String message) {
        return new Result(ResultCode.FAIL.getCode(), message);
    }


    /**
     * Fail result result.
     *
     * @param object the object
     * @return the result
     */
    public static Result failResult(final Object object) {
        return new Result(ResultCode.FAIL.getCode(), ResultCode.FAIL.getStatusMessage(), object);
    }

    /**
     * Fail result result.
     *
     * @return the result
     */
    public static Result errorResult() {
        return new Result(ResultCode.ERROR.getCode(), ResultCode.ERROR.getStatusMessage());
    }

    /**
     * Fail result result.
     *
     * @param message the message
     * @return the result
     */
    public static Result errorResult(final String message) {
        return new Result(ResultCode.ERROR.getCode(), message);
    }


    /**
     * Fail result result.
     *
     * @param object the object
     * @return the result
     */
    public static Result errorResult(final Object object) {
        return new Result(ResultCode.ERROR.getCode(), ResultCode.ERROR.getStatusMessage(), object);
    }

    /**
     * Unauthorized result.
     *
     * @param message the message
     * @return the result
     */
    public static Result unauthorized (final String message ) {
        return new Result(ResultCode.UNAUTHORIZED.getCode(), message);
    }

    /**
     * Unavailable result.
     *
     * @param message the message
     * @return the result
     */
    public static Result unavailable (final String message ) {
        return new Result(ResultCode.NOT_FOUND.getCode(), message);
    }



    /**
     * 给 Result 添加内容
     * <pre>
     *     Result.successResult()
     *                   .add( "username", "披荆斩棘" )
     *                   .add( "password", "123456" )
     *                   .add( "ip", "localhost" );
     *
     *     Result{statusCode='200', statusMessage='success', filterFields='*', responseContent={password=123456, ip=localhost, username=披荆斩棘}}
     * </pre>
     *
     * @param key   : <code>String</code>类型
     * @param value : <code>Object</code>类型
     * @return <code>this</code>
     */
    public Result add (final String key, final Object value ) {
        if ( null == this.data ) {
            this.data = new HashMap< String, Object >();
            Map< String, Object > content = ( Map< String, Object > ) this.data;
            content.put( key, value );
            return this;
        }
        if ( ! ( this.data instanceof Map ) ) {
            return this;
        }
        ( ( Map ) this.data ).put( key, value );
        return this;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public enum ResultCode {
        SUCCESS("200", "请求成功"),//成功
        ERROR("500", "请求出错"),//服务器内部错误
        FAIL("400", "请求失败"),//失败
        UNAUTHORIZED("401", "身份验证失败"),//未认证（签名错误）
        NOT_FOUND("404", "请求页面不存在");//接口不存在


        private String code;
        private String statusMessage;

        public String getStatusMessage () {
            return statusMessage;
        }

        public String getCode() {
            return code;
        }

        ResultCode(String code, String statusMessage) {
            this.code = code;
            this.statusMessage = statusMessage;
        }
    }
}
