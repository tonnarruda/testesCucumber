package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.List;

import net.vidageek.mirror.dsl.Matcher;
import net.vidageek.mirror.dsl.Mirror;

import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.ChaveNaEntidade;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoCarregaEntidadeAuditoria;
import com.fortes.security.auditoria.MetodoInterceptado;

public abstract class AuditorCallbackImpl implements AuditorCallback {

	private AbstractModel entidade;
	private AbstractModel entidadeAntesDaAtualizacao;

	private String modulo;
	private String operacao;
	private String dados;
	private String chave;
	
	protected void inicializa(MetodoInterceptado metodo) {
		modulo = metodo.getModulo();
		operacao = metodo.getOperacao();
		dados = geraDadosAuditados();
		chave = getChaveNaEntidade();
	}
	
	protected String getChaveNaEntidade() {
		return new ChaveNaEntidade(entidade).procura();
	}

	protected String geraDadosAuditados() {
		return new DadosAuditados(null, entidade).gera();
	}
	
	protected AbstractModel carregaEntidade(MetodoInterceptado metodo) {
		List<Method> metodos = new Mirror().on(metodo.getComponente().getClass().getInterfaces()[0]).reflectAll().methodsMatching(new Matcher<Method>() {

			@Override
			public boolean accepts(Method method) {
				return method.isAnnotationPresent(MetodoCarregaEntidadeAuditoria.class);
			}
		});
		
		String metodoParaAuditoria = metodos.isEmpty() ? "findEntidadeComAtributosSimplesById" : metodos.get(0).getName();
		return (AbstractModel)new Mirror().on(metodo.getComponente()).invoke().method(metodoParaAuditoria).withArgs((Long) metodo.getParametros()[0]);
	}

	// Get's e Set's
	public AbstractModel getEntidade() {
		return entidade;
	}

	public void setEntidade(AbstractModel entidade) {
		this.entidade = entidade;
	}

	public AbstractModel getEntidadeAntesDaAtualizacao() {
		return entidadeAntesDaAtualizacao;
	}

	public void setEntidadeAntesDaAtualizacao(AbstractModel entidadeAntesDaAtualizacao) {
		this.entidadeAntesDaAtualizacao = entidadeAntesDaAtualizacao;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}

	public String getChave() {
		return chave;
	}
	
	public void setChave(String chave) {
		this.chave = chave;
	}

}
