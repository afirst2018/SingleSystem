package com.octo.captcha.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public abstract class AbstractCaptchaService implements CaptchaService {
	protected CaptchaStore store;
	protected CaptchaEngine engine;
	protected Logger logger;

	protected AbstractCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine) {
		if(captchaEngine != null && captchaStore != null) {
			this.engine = captchaEngine;
			this.store = captchaStore;
			this.logger = LoggerFactory.getLogger(this.getClass());
			this.logger.info("Init " + this.store.getClass().getName());
			this.store.initAndStart();
		} else {
			throw new IllegalArgumentException("Store or gimpy can\'t be null");
		}
	}

	public Object getChallengeForID(String ID) throws CaptchaServiceException {
		return this.getChallengeForID(ID, Locale.getDefault());
	}

	public Object getChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
		Captcha captcha;
		if(!this.store.hasCaptcha(ID)) {
			captcha = this.generateAndStoreCaptcha(locale, ID);
		} else {
			captcha = this.store.getCaptcha(ID);
			if(captcha == null) {
				captcha = this.generateAndStoreCaptcha(locale, ID);
			} else if(captcha.hasGetChalengeBeenCalled().booleanValue()) {
				captcha = this.generateAndStoreCaptcha(locale, ID);
			}
		}

		Object challenge = this.getChallengeClone(captcha);
		captcha.disposeChallenge();
		return challenge;
	}

	public String getQuestionForID(String ID, Locale locale) throws CaptchaServiceException {
		Captcha captcha;
		if(!this.store.hasCaptcha(ID)) {
			captcha = this.generateAndStoreCaptcha(locale, ID);
		} else {
			captcha = this.store.getCaptcha(ID);
			if(captcha == null) {
				captcha = this.generateAndStoreCaptcha(locale, ID);
			} else if(locale != null) {
				Locale storedlocale = this.store.getLocale(ID);
				if(!locale.equals(storedlocale)) {
					captcha = this.generateAndStoreCaptcha(locale, ID);
				}
			}
		}

		return captcha.getQuestion();
	}

	public String getQuestionForID(String ID) throws CaptchaServiceException {
		return this.getQuestionForID(ID, Locale.getDefault());
	}

	public Boolean validateResponseForID(String ID, Object response) throws CaptchaServiceException {
		if(!this.store.hasCaptcha(ID)) {
			// update by sul 注释异常抛出，临时处理 sessionid变化，验证码失效时，页面报500错误，以后可考虑优化，不在此处以这种方式处理
			// throw new CaptchaServiceException("Invalid ID, could not validate unexisting or already validated captcha");
			return false;
		} else {
			Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
			this.store.removeCaptcha(ID);
			return valid;
		}
	}

	protected Captcha generateAndStoreCaptcha(Locale locale, String ID) {
		Captcha captcha = this.engine.getNextCaptcha(locale);
		this.store.storeCaptcha(ID, captcha, locale);
		return captcha;
	}

	protected abstract Object getChallengeClone(Captcha var1);
}
