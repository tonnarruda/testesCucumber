package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.util.LongUtil;

public class QuantidadeLimiteColaboradoresPorCargoManagerImpl extends GenericManagerImpl<QuantidadeLimiteColaboradoresPorCargo, QuantidadeLimiteColaboradoresPorCargoDao> implements QuantidadeLimiteColaboradoresPorCargoManager
{
	private ColaboradorManager colaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ConfiguracaoLimiteColaboradorManager configuracaoLimiteColaboradorManager;
	
	public void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional) 
	{
		for (QuantidadeLimiteColaboradoresPorCargo limite : quantidadeLimiteColaboradoresPorCargos) 
		{
			if(limite != null)//javascrip pode mandar obj null dentro do array
			{
				limite.setAreaOrganizacional(areaOrganizacional);
				getDao().save(limite);				
			}
		}
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByArea(Long areaId) 
	{
		return getDao().findByArea(areaId);
	}

	public void updateLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional) 
	{
		getDao().deleteByArea(areaOrganizacional.getId());
		saveLimites(quantidadeLimiteColaboradoresPorCargos, areaOrganizacional);
	}

	public void deleteByArea(Long areaId) 
	{
		getDao().deleteByArea(areaId);
	}

	public void validaLimite(Long areaId, Long faixaId, Date data, Long empresaId) throws LimiteColaboradorExceditoException 
	{
		FaixaSalarial faixa = faixaSalarialManager.findByFaixaSalarialId(faixaId);
		
		Collection<AreaOrganizacional> areaOrganizacionais = new ArrayList<AreaOrganizacional>();
		
		try {areaOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, AreaOrganizacional.ATIVA);
		} catch (Exception e) {e.printStackTrace();}
		
		Collection<Long> areasIds = new ArrayList<Long>();
		
		for (AreaOrganizacional area: areaOrganizacionais) {
			if(area.getId().equals(areaId))
				areasIds = area.getDescricaoIds();
		}
		
		QuantidadeLimiteColaboradoresPorCargo configuracaoLimite = getDao().findLimite(faixa.getCargo().getId(), areasIds);
		
		if(configuracaoLimite != null)//verifica se existe limite configurado para a familia de area e o cargo
		{
			Collection<AreaOrganizacional> descendentes = areaOrganizacionalManager.findAreasPossiveis(areaOrganizacionais, configuracaoLimite.getAreaOrganizacional().getId());
			
			Collection<Long> cargosIdsTmp = new ArrayList<Long>();
			cargosIdsTmp.add(faixa.getCargo().getId());
		
			Integer colaboradoresAtivos = colaboradorManager.countAtivosPeriodo(data, empresaId, null, LongUtil.collectionToCollectionLong(descendentes), cargosIdsTmp);
			
			if(colaboradoresAtivos >= configuracaoLimite.getLimite())
				throw new LimiteColaboradorExceditoException("Limite de colaboradores cadastrados para o cargo \""+ faixa.getCargo().getNome() +"\" foi excedido!");
		}
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByEmpresa(Long empresaId) throws Exception 
	{
		Collection<ConfiguracaoLimiteColaborador> configuracaos = configuracaoLimiteColaboradorManager.findAllSelect(empresaId);
		Collection<AreaOrganizacional> areaOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, AreaOrganizacional.ATIVA);
		Collection<QuantidadeLimiteColaboradoresPorCargo> limites = getDao().findByEmpresa(empresaId);
		
		for (QuantidadeLimiteColaboradoresPorCargo limite : limites) 
		{
			for (AreaOrganizacional area : areaOrganizacionais) 
			{
				if(area.getId().equals(limite.getAreaOrganizacional().getId()))
				{
					limite.setAreaOrganizacional(area);
					break;
				}
			}			

			for (ConfiguracaoLimiteColaborador configuracao : configuracaos) 
			{
				if(configuracao.getAreaOrganizacional().getId().equals(limite.getAreaOrganizacional().getId()))
				{
					limite.setDescricao(configuracao.getDescricao());
					break;
				}				
			}
		}
		
		return limites;
	}

	public void setConfiguracaoLimiteColaboradorManager(ConfiguracaoLimiteColaboradorManager configuracaoLimiteColaboradorManager) {
		this.configuracaoLimiteColaboradorManager = configuracaoLimiteColaboradorManager;
	}
}
