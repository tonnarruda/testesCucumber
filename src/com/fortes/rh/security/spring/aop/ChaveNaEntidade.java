package com.fortes.rh.security.spring.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.vidageek.mirror.dsl.Matcher;
import net.vidageek.mirror.dsl.Mirror;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("deprecation")
public class ChaveNaEntidade {

	private final AbstractModel entidade;

	public ChaveNaEntidade(AbstractModel entidade) {
		this.entidade = entidade;
	}
	
	/**
	 * Procura a chave da entidade.
	 */
	public String procura() {
		// procura entre os campos
		String chave = this.procuraNosCampos();
		if (chave == null)
			chave = this.procuraNosGetters(); // caso contrário procura nos getters
		return chave;
	}
	/**
	 * Procura Chave nos campos da entidade.
	 */
	private String procuraNosCampos() {
		List<Field> fields = new Mirror()
								.on(entidade.getClass())
								.reflectAll().fieldsMatching(new Matcher<Field>() {
									public boolean accepts(Field field) {
										return (field.getAnnotation(ChaveDaAuditoria.class) != null);
									}
								});
		
		if (fields.isEmpty()){
			return null;
		}
		
		return (String) new Mirror()
						.on(entidade)
						.get().field(fields.get(0)); // first field
	}
	/**
	 * Procura Chave nos métodos getters da entidade.
	 */
	private String procuraNosGetters() {
		List<Method> methods = new Mirror()
								.on(entidade.getClass())
								.reflectAll().methodsMatching(new Matcher<Method>() {
									public boolean accepts(Method method) {
										return (method.getAnnotation(ChaveDaAuditoria.class) != null);
									}
								});
		
		if (methods.isEmpty())
			return null;
		
		return (String) new Mirror().on(entidade)
							.invoke().method(methods.get(0)) // first method
							.withoutArgs();
	}
	
}
