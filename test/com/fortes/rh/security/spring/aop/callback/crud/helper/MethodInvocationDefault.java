package com.fortes.rh.security.spring.aop.callback.crud.helper;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;
public class MethodInvocationDefault<M> implements MethodInvocation {
	
	private String metodo;
	private Class<M> classManager;
	private Object[] params;
	private M manager;
	
	public MethodInvocationDefault(String metodo, Class<M> classManager, Object[] params, M manager) {
		super();
		this.metodo = metodo;
		this.classManager = classManager;
		this.params = params;
		this.manager = manager;
	}
	
	public Method getMethod() {
		return new Mirror().on(getClassManager()).reflect().method(getMetodo()).withAnyArgs();
	}
	public Object[] getArguments() {
		return getParams();
	}
	public AccessibleObject getStaticPart() {
		return null;
	}
	public M getThis() {
		return getManager();
	}
	public Object proceed() throws Throwable {
		return null;
	}

	// Gets e Sets
	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public Class<M> getClassManager() {
		return classManager;
	}

	public void setClassManager(Class<M> classManager) {
		this.classManager = classManager;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public M getManager() {
		return manager;
	}

	public void setManager(M manager) {
		this.manager = manager;
	}
}
