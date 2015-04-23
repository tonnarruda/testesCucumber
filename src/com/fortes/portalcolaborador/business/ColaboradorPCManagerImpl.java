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
import com.fortes.rh.util.SpringUtil;

public class ColaboradorPCManagerImpl extends GenericManagerImpl<ColaboradorPC, ColaboradorPCDao> implements ColaboradorPCManager 
{
	public void enfileirarColaboradoresPCComHistoricos(Long colaboradorId, Long empresaId)
	{
		TransacaoPCManager transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
		HistoricoColaboradorManager historicoColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBeanOld("historicoColaboradorManager");
		
		Collection<HistoricoColaborador> historicosMontados = historicoColaboradorManager.getHistoricosConfirmados(colaboradorId, empresaId);
		Collection<ColaboradorPC> colaboradorPCs = montaColaboradorPCComHistoricos(historicosMontados);
		
		for (ColaboradorPC colabPC : colaboradorPCs) 
			transacaoPCManager.enfileirar(URLTransacaoPC.COLABORADOR_ATUALIZAR, colabPC.toJson());
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
