package com.fortes.rh.security.spring.aop;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManager;
import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.MetodoInterceptado;

public class AtributosDaAuditoria implements Auditavel {

	private AbstractModel entidade;
	private boolean fetchMode;
	private String modulo;
	private String dadosGerados;
	private Object resultado;
	private String chave;
	private GenericManager<AbstractModel> manager;
	private Object[] parametros;
	private String operacao;
	
	private final MetodoInterceptado metodoInterceptado;
	
	/**
	 * Construtor na qual precisa receber o <code>MethodInvocation</code> do
	 * método que será auditado para carregar os dados necessários.
	 */
	public AtributosDaAuditoria(MetodoInterceptado metodo) {
		this.metodoInterceptado = metodo;
	}

	/**
	 * Invoca o método auditado e carrega os atributos necessários para auditoria.
	 */
	public AtributosDaAuditoria carrega() throws Throwable {
		this.carregaAtributosBasicos();
		this.resultado = metodoInterceptado.processa();
		this.dadosGerados = this.geraDados();
		return this;
	}
	/**
	 * Carrega os atributos.
	 */
	private void carregaAtributosBasicos() {
		this.fetchMode = metodoInterceptado.isFetchMode();
		this.parametros = metodoInterceptado.getParametros();
		this.manager = this.getManager();
		this.chave = this.carregaChave();
		this.modulo = metodoInterceptado.getModulo();
		this.operacao = metodoInterceptado.getOperacao();
	}
	/**
	 * Retorna o Manager auditado.
	 */
	@SuppressWarnings("unchecked")
	private GenericManager<AbstractModel> getManager() {
		return (GenericManager<AbstractModel>) metodoInterceptado.getComponente();
	}
	/**
	 * Carrega a chave da auditoria.
	 */
	private String carregaChave() {
		this.carregaEntidadeSeNecessario();
		return StringUtils.defaultIfEmpty(this.procuraChaveNaEntidade(), "");
	}
	/**
	 * Procura pela Chave da Auditoria nos campos da entidade.
	 */
	private String procuraChaveNaEntidade() {
		return new ProcuraChaveNaEntidade(this.entidade).procura();
	}
	/**
	 * Carrega a entidade se necessário.
	 */
	private void carregaEntidadeSeNecessario() {
		boolean entidadeNaoCarregada = (entidade == null);
		if (entidadeNaoCarregada) {
			if (fetchMode) {
				Long id = this.getId();
				this.entidade = (AbstractModel) manager.findEntidadeComAtributosSimplesById(id);
				return;
			}
			this.entidade = this.getEntidade();
		}
	}
	/**
	 * Retorna a entidade a ser auditada.
	 */
	private AbstractModel getEntidade() {
		return (AbstractModel) this.parametros[0];
	}
	/**
	 * Retorna o ID da entidade a ser auditada.
	 */
	private Long getId() {
		if (this.isId())
			return (Long) this.parametros[0];
		return null;
	}
	/**
	 * Verifica se o parametro passado para o metodo auditado é um ID (Long).
	 */
	private boolean isId() {
		boolean isId = false;
		if (parametros != null 
				&& parametros.length > 0) {
			Object id = parametros[0];
			isId = (id instanceof Long);
		}
		return isId;
	}
	/**
	 * Gera os dados para auditoria.
	 */
	private String geraDados() {
		return new GeraDadosAuditados(parametros, resultado).gera();
	}
	/**
	 * Verifica se eh necessário consultar a entidade pelo ID.
	 */
	protected boolean isFetchMode() {
		return metodoInterceptado.isFetchMode();
	}

	public String getChave() {
		return this.chave;
	}
	public String getDados() {
		return this.dadosGerados;
	}
	public String getOperacao() {
		return this.operacao;
	}
	public Object getResultado() {
		return this.resultado;
	}
	public String getModulo() {
		return this.modulo;
	}
	
}
