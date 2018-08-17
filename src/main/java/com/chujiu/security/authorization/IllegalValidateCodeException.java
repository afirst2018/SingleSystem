package com.chujiu.security.authorization;

import org.springframework.security.core.AuthenticationException;


/**
 *  自定义实现，验证码错误异常
 *
 * @author suliang
 */
public class IllegalValidateCodeException extends AuthenticationException {
    //~ Constructors ===================================================================================================
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a <code>IllegalValidateCodeException</code> with the specified
     * message.
     *
     * @param msg the detail message.
     */
    public IllegalValidateCodeException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code IllegalValidateCodeException}, making use of the {@code extraInformation}
     * property of the superclass.
     *
     * @param msg the detail message
     * @param extraInformation additional information such as the username.
     */
    @Deprecated
    public IllegalValidateCodeException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }

    /**
     * Constructs a {@code IllegalValidateCodeException} with the specified message and root cause.
     *
     * @param msg the detail message.
     * @param t root cause
     */
    public IllegalValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }
}