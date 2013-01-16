package com.fortes.rh.business.cargosalario;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.ReajusteIndiceDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.MathUtil;

public class ReajusteIndiceManagerImpl extends GenericManagerImpl<ReajusteIndice, ReajusteIndiceDao> implements ReajusteIndiceManager
{
	private IndiceManager indiceManager;
	
	public void insertReajustes(Long tabelaReajusteColaboradorId, Long[] indicesIds, char dissidioPor, Double valorDissidio) throws Exception 
	{
		Collection<Indice> indices = indiceManager.findComHistoricoAtual(indicesIds);
		ReajusteIndice reajuste;
		
		for (Indice indice : indices) 
		{
			reajuste = new ReajusteIndice();
			reajuste.setIndice(indice);
			reajuste.setProjectionTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
			reajuste.setValorAtual(indice.getIndiceHistoricoAtual().getValor());
			reajuste.setValorProposto(MathUtil.calculaDissidio(dissidioPor, valorDissidio, indice.getIndiceHistoricoAtual().getValor()));
			
			getDao().save(reajuste);
		}
	}
	
	public Collection<Indice> findPendentes(Empresa empresa) 
	{
		return getDao().findPendentes(empresa);
	}
	
	public Collection<ReajusteIndice> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId) 
	{
		return getDao().findByTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
	}
	
	public ReajusteIndice findByIdProjection(Long reajusteIndiceId) 
	{
		return getDao().findByIdProjection(reajusteIndiceId);
	}
	
	public void updateValorProposto(Long reajusteIndiceId, Double valorAtual, char dissidioPor, Double valorDissidio) throws Exception 
	{
		getDao().updateValorProposto(reajusteIndiceId, MathUtil.calculaDissidio(dissidioPor, valorDissidio, valorAtual));
	}
	
	public void setIndiceManager(IndiceManager indiceManager) 
	{
		this.indiceManager = indiceManager;
	}
}