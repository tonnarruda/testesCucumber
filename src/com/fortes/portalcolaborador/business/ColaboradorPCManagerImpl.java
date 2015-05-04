package com.fortes.portalcolaborador.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fortes.business.GenericManagerImpl;
import com.fortes.portalcolaborador.dao.ColaboradorPCDao;
import com.fortes.portalcolaborador.model.ColaboradorPC;
import com.fortes.portalcolaborador.model.HistoricoColaboradorPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorPCManagerImpl extends GenericManagerImpl<ColaboradorPC, ColaboradorPCDao> implements ColaboradorPCManager 
{
	public void enfileirarComHistoricos(URLTransacaoPC uRLTransacaoPC, Long empresaId, Long... colaboradoresIds)
	{
		if(empresaId != null || LongUtil.arrayIsNotEmpty(colaboradoresIds))
		{
			TransacaoPCManager transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
			HistoricoColaboradorManager historicoColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBeanOld("historicoColaboradorManager");
			
			Collection<HistoricoColaborador> historicosMontados = historicoColaboradorManager.findHistoricosConfirmados(empresaId, colaboradoresIds);
			Collection<ColaboradorPC> colaboradoresPC = montaColaboradorPCComHistoricos(historicosMontados);
			
			for (ColaboradorPC colaboradorPC : colaboradoresPC) 
				transacaoPCManager.enfileirar(uRLTransacaoPC, colaboradorPC.toJson());
		}
	}
	
	private Collection<ColaboradorPC> montaColaboradorPCComHistoricos(Collection<HistoricoColaborador> historicosColaborador) 
	{
		ColaboradorPC colaboradorPC = null;
		HistoricoColaboradorPC historicoColaboradorPC;
		Set<ColaboradorPC> colaboradorPCs = new HashSet<ColaboradorPC>();
		
		for (HistoricoColaborador historico : historicosColaborador) 
		{
			if(historico.getColaborador() != null && historico.getColaborador().getPessoal() != null && historico.getColaborador().getPessoal().getCpf() != null )
			{
				if(colaboradorPC == null || !colaboradorPC.getCpf().equals(historico.getColaborador().getPessoal().getCpf()))
				{
					colaboradorPC = new ColaboradorPC(historico.getColaborador());
					colaboradorPC.setHistoricosPc(new ArrayList<HistoricoColaboradorPC>());
				}
				
				historicoColaboradorPC = new HistoricoColaboradorPC(historico);
				colaboradorPC.getHistoricosPc().add(historicoColaboradorPC);
				
				colaboradorPCs.add(colaboradorPC);
			}
		}
		
		return colaboradorPCs;
	}
}
