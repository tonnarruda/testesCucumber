package com.fortes.rh.security.spring.aop.callback;

import com.fortes.business.GenericManager;
import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidade;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class AuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Object[] dadosAnteriores = null;
		Object dadosAtualizados = null;
		AbstractModel objeto = (AbstractModel) metodo.getParametros()[0];
		String chave = null;
		
		if (metodo.getOperacao().equals("Inserção")) {
			dadosAtualizados = objeto;
			chave = new ProcuraChaveNaEntidade(objeto).procura();
		
		} else if (metodo.getOperacao().equals("Atualização")) {
			dadosAnteriores = new Object[] { carregaEntidade(metodo, objeto.getId()) };
			dadosAtualizados = objeto;
			chave = new ProcuraChaveNaEntidade(objeto).procura();

		} else if (metodo.getOperacao().equals("Remoção")) {
			objeto = carregaEntidade(metodo, objeto.getId());
			dadosAnteriores = new Object[] { objeto };
			chave = new ProcuraChaveNaEntidade(objeto).procura();
		}
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(dadosAnteriores, dadosAtualizados).gera();
			
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), chave, dados);
	}
	
	@SuppressWarnings("unchecked")
	private AbstractModel carregaEntidade(MetodoInterceptado metodo, Long id) {
		GenericManager<AbstractModel> manager = (GenericManager<AbstractModel>) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(id);
	}
}
