package com.fortes.rh.model.dicionario;

import com.fortes.business.GenericManager;
import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidade;
import com.fortes.security.auditoria.MetodoInterceptado;


public enum OperacaoAuditoria
{
	INSERCAO("Inserção") {
		public GeraDadosAuditados getDadosAuditados(MetodoInterceptado metodo) {
			AbstractModel dadosAtualizados = (AbstractModel) metodo.getParametros()[0];
			return new GeraDadosAuditados(null, dadosAtualizados, new ProcuraChaveNaEntidade(dadosAtualizados).procura());
		}
	},
	
	ATUALIZACAO("Atualização") {
		public GeraDadosAuditados getDadosAuditados(MetodoInterceptado metodo) {
			AbstractModel objeto = (AbstractModel) metodo.getParametros()[0]; 
			Object[] dadosAnteriores = new Object[] { carregaEntidade(metodo, objeto.getId()) };
			AbstractModel dadosAtualizados = objeto;

			return new GeraDadosAuditados(dadosAnteriores, dadosAtualizados, new ProcuraChaveNaEntidade(dadosAtualizados).procura());
		}
	},
	
	REMOCAO("Remoção") {
		public GeraDadosAuditados getDadosAuditados(MetodoInterceptado metodo) {
			AbstractModel objeto = (AbstractModel) metodo.getParametros()[0];
			objeto = carregaEntidade(metodo, objeto.getId());
			Object[] dadosAnteriores = new Object[] { objeto };

			return new GeraDadosAuditados(dadosAnteriores, null, new ProcuraChaveNaEntidade(objeto).procura());
		}
	};
	
	private final String descricao;
	
	OperacaoAuditoria(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public GeraDadosAuditados getDadosAuditados(MetodoInterceptado metodo) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static AbstractModel carregaEntidade(MetodoInterceptado metodo, Long id) {
		GenericManager<AbstractModel> manager = (GenericManager<AbstractModel>) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(id);
	}
}