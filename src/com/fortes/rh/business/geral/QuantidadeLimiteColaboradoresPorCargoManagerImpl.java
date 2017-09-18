package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.exception.LimiteColaboradorExcedidoException;
import com.fortes.rh.model.cargosalario.Cargo;
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
	
	public void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, final AreaOrganizacional areaOrganizacional) 
	{
		IterableUtils.forEach(quantidadeLimiteColaboradoresPorCargos, new Closure<QuantidadeLimiteColaboradoresPorCargo>() {

			@Override
			public void execute(QuantidadeLimiteColaboradoresPorCargo quantidadeLimiteColaboradoresPorCargo) {
				if(quantidadeLimiteColaboradoresPorCargo != null)//javascrip pode mandar obj null dentro do array
				{
					quantidadeLimiteColaboradoresPorCargo.setAreaOrganizacional(areaOrganizacional);
					getDao().save(quantidadeLimiteColaboradoresPorCargo);				
				}
				
			}
		});
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByArea(Long areaId) 
	{
		return getDao().findByEntidade(areaId, AreaOrganizacional.class);
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByCargo(Long cargoId) 
	{
		return getDao().findByEntidade(cargoId, Cargo.class);
	}
	
	public void updateLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional) 
	{
		getDao().deleteByArea(areaOrganizacional.getId());
		saveLimites(quantidadeLimiteColaboradoresPorCargos, areaOrganizacional);
	}

	public void deleteByArea(Long... areaIds) 
	{
		getDao().deleteByArea(areaIds);
	}

	public void deleteByCargo(Long cargoId)
	{
		getDao().deleteByCargo(cargoId);
	}

	public void validaLimite(Long areaId, Long faixaId, Long empresaId, Long colaboradorId) throws LimiteColaboradorExcedidoException{
		QuantidadeLimiteColaboradoresPorCargo configuracaoLimite = qtdLimiteColaboradorPorCargo(areaId, faixaId, empresaId, colaboradorId);
		
		if(configuracaoLimite != null && configuracaoLimite.getQtdColaboradoresCadastrados() >= configuracaoLimite.getLimite())
			throw new LimiteColaboradorExcedidoException("Não foi possível concluir esta operação.<br />Limite de colaboradores cadastrados para o cargo <strong>"+ configuracaoLimite.getCargo().getNome() +"</strong> foi excedido!");
	}
	
	public QuantidadeLimiteColaboradoresPorCargo qtdLimiteColaboradorPorCargo(Long areaId, Long faixaId, Long empresaId, Long colaboradorId) 
	{
		FaixaSalarial faixa = faixaSalarialManager.findByFaixaSalarialId(faixaId);
		
		Collection<AreaOrganizacional> areaOrganizacionais = new ArrayList<AreaOrganizacional>();
		
		try {
			areaOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, AreaOrganizacional.ATIVA, areaId, Boolean.FALSE);
		} catch (Exception e) {e.printStackTrace();}
		
		Collection<Long> areasIds = new ArrayList<Long>();
		
		for (AreaOrganizacional area: areaOrganizacionais) 
		{
			if(area.getId().equals(areaId))
			{
				areasIds = area.getDescricaoIds();
				break;
			}
		}
		
		QuantidadeLimiteColaboradoresPorCargo configuracaoLimite = getDao().findLimite(faixa.getCargo().getId(), areasIds);
		
		if(configuracaoLimite != null)//verifica se existe limite configurado para a familia de area e o cargo
		{
			Collection<AreaOrganizacional> descendentes = areaOrganizacionalManager.findAreasPossiveis(areaOrganizacionais, configuracaoLimite.getAreaOrganizacional().getId());
			
			Collection<Long> cargosIdsTmp = new ArrayList<Long>();
			cargosIdsTmp.add(faixa.getCargo().getId());
		
			configuracaoLimite.setQtdColaboradoresCadastrados(colaboradorManager.countAtivosPeriodo(new Date(), Arrays.asList(empresaId), null, LongUtil.collectionToCollectionLong(descendentes), cargosIdsTmp, null, false, colaboradorId, false));
			configuracaoLimite.setCargo(faixa.getCargo());
			
			return configuracaoLimite;
		}
		
		return null;
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
		Collection<AreaOrganizacional> areaOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, AreaOrganizacional.ATIVA, null, false);
		Collection<QuantidadeLimiteColaboradoresPorCargo> limites = getDao().findByEmpresa(empresaId);
		Date hoje = new Date();
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
					Collection<AreaOrganizacional> descendentes = areaOrganizacionalManager.findAreasPossiveis(areaOrganizacionais, limite.getAreaOrganizacional().getId());
					limite.setQtdColaboradoresCadastrados(colaboradorManager.countAtivosPeriodo(hoje, Arrays.asList(empresaId), null, LongUtil.collectionToCollectionLong(descendentes), Arrays.asList(limite.getCargo().getId()), null, false, null, false));
					limite.setDescricao(configuracao.getDescricao());
					limite.setAreaOAreaOrganizacionalContratoDescricao(limite.getAreaOrganizacional().getDescricao() +" ("+limite.getDescricao() + ") ");
					if (limite.getLimite() < limite.getQtdColaboradoresCadastrados()) {
						limite.getCargo().setNome("*" + limite.getCargo().getNome());
					}
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
