package com.fortes.portalcolaborador.business;

import com.fortes.business.GenericManagerImpl;
import com.fortes.portalcolaborador.business.operacao.Operacao;
import com.fortes.portalcolaborador.dao.MovimentacaoOperacaoPCDao;
import com.fortes.portalcolaborador.model.AbstractAdapterPC;
import com.fortes.portalcolaborador.model.MovimentacaoOperacaoPC;

public class MovimentacaoOperacaoPCManagerImpl extends GenericManagerImpl<MovimentacaoOperacaoPC, MovimentacaoOperacaoPCDao> implements MovimentacaoOperacaoPCManager 
{
	
	public void enfileirar(Class<? extends Operacao> operacao, AbstractAdapterPC adapterPC, boolean isEmpresaIntegraAC)
	{
		if(isEmpresaIntegraAC)
		{
			MovimentacaoOperacaoPC movimentacaoOperacaoPC = new MovimentacaoOperacaoPC();
			movimentacaoOperacaoPC.setOperacao(operacao);
			movimentacaoOperacaoPC.setParametros(adapterPC.toJson());
			
			save(movimentacaoOperacaoPC);		
		}
	}

	public void enfileirar(Class<? extends Operacao> operacao, String parametros, boolean isEmpresaIntegraAC)
	{
		if(isEmpresaIntegraAC)
		{
			MovimentacaoOperacaoPC movimentacaoOperacaoPC = new MovimentacaoOperacaoPC();
			movimentacaoOperacaoPC.setOperacao(operacao);
			movimentacaoOperacaoPC.setParametros(parametros);
			
			save(movimentacaoOperacaoPC);
		}
	}

	public void processarOperacoes()
	{
		GerenciadorMovimentacaoOperacao.getInstancia().processarOperacoes();
	}
}
