package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.HashMap;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.ReajusteFaixaSalarialDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.util.MathUtil;

public class ReajusteFaixaSalarialManagerImpl extends GenericManagerImpl<ReajusteFaixaSalarial, ReajusteFaixaSalarialDao> implements ReajusteFaixaSalarialManager
{
	private FaixaSalarialManager faixaSalarialManager;
	
	public void insertReajustes(Long tabelaReajusteColaboradorId, Long[] faixasSalariaisIds, char dissidioPor, double valorDissidio) throws Exception 
	{
		Collection<FaixaSalarial> faixasSalariais = faixaSalarialManager.findComHistoricoAtual(faixasSalariaisIds);
		ReajusteFaixaSalarial reajuste;
		
		for (FaixaSalarial faixaSalarial : faixasSalariais) 
		{
			reajuste = new ReajusteFaixaSalarial();
			reajuste.setFaixaSalarial(faixaSalarial);
			reajuste.setProjectionTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
			
			reajuste.setTipoAtual(faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo());
			reajuste.setTipoProposto(faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo());
			
			reajuste.setValorAtual(faixaSalarial.getFaixaSalarialHistoricoAtual().getValor());
			reajuste.setValorProposto(MathUtil.calculaDissidio(dissidioPor, valorDissidio, faixaSalarial.getFaixaSalarialHistoricoAtual().getValor()));
			
			getDao().save(reajuste);
		}
	}
	
	public Collection<ReajusteFaixaSalarial> findByFiltros(HashMap<Object, Object> parametros) 
	{
		return  getDao().findByTabelaReajusteCargoFaixa((Long) parametros.get("tabela"), (Collection<Long>) parametros.get("cargos"), (Collection<Long>) parametros.get("faixaSalarials"));
	}
	
	public void updateValorProposto(Long reajusteFaixaSalarialId, Double valorAtual, char dissidioPor, Double valorDissidio) throws Exception 
	{
		getDao().updateValorProposto(reajusteFaixaSalarialId, MathUtil.calculaDissidio(dissidioPor, valorDissidio, valorAtual));
	}
	
	public Collection<ReajusteFaixaSalarial> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId) 
	{
		return getDao().findByTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
	}
	
	public ReajusteFaixaSalarial findByIdProjection(Long id) 
	{
		return getDao().findByIdProjection(id);
	}
	
	public boolean verificaPendenciasPorFaixa(Long faixaSalarialId) 
	{
		return getDao().verificaPendenciasPorFaixa(faixaSalarialId);
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) 
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}


}