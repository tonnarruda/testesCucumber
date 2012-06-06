package com.fortes.security.auditoria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audita {
	
	String operacao();
	
	boolean fetchEntity() default false;

	Class<? extends AuditorCallback> auditor() default EmptyAuditorCallbackImpl.class;
	
}
