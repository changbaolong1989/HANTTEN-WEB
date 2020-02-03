package com.hd.common.util;

import java.util.Locale;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;

@Lazy(false)
@Component("springUtils")
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext;

	private SpringUtils() {
	}

	@Resource
	private MessageSource messageSource;

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		Assert.hasText(name);

		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> type) {
		Assert.notNull(type);

		return applicationContext.getBean(type);
	}

	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);

		return applicationContext.getBean(name, type);
	}

	public static String getMessage(String code, Object... args) {
		Assert.hasText(code);
		return applicationContext.getMessage(code, args, Locale.CHINA);
	}

	@Override
    public void destroy() throws Exception {
		applicationContext = null;
	}

}