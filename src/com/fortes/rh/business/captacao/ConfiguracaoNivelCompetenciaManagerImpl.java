package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class ConfiguracaoNivelCompetenciaManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetencia, ConfiguracaoNivelCompetenciaDao> implements ConfiguracaoNivelCompetenciaManager
{
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	
	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId) 
	{
		return getDao().findByFaixa(faixaSalarialId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId) {
		return getDao().findByCandidato(candidatoId);
	}
	
	public void saveCompetencias(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId) 
	{
		if(candidatoId == null)
			getDao().deleteConfiguracaoByFaixa(faixaSalarialId);
		else
			getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais)
		{
			if (configuracaoNivelCompetencia.getCompetenciaId() != null)
			{
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				if(candidatoId != null)
					configuracaoNivelCompetencia.setCandidatoIdProjection(candidatoId);
				
				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}
	
	public void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		if (configuracaoNivelCompetenciaColaborador.getId() != null)
		{
			configuracaoNivelCompetenciaColaboradorManager.update(configuracaoNivelCompetenciaColaborador);
			getDao().deleteByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		}
		else
			configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.save(configuracaoNivelCompetenciaColaborador);
		
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) 
		{
			if (configuracaoNivelCompetencia.getCompetenciaId() != null)
			{
				configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	public Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId) 
	{
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos = getDao().findByCandidato(candidatoId);
		
		if(!niveisCompetenciaFaixaSalariaisSalvos.isEmpty())
		{
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);
			for (ConfiguracaoNivelCompetencia nivelCompetenciaCandidato : niveisCompetenciaFaixaSalariaisSalvos)
			{
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) 
				{
					if(nivelCompetenciaCandidato.getCompetenciaId().equals(configuracaoNivelCompetencia.getCompetenciaId()) && nivelCompetenciaCandidato.getTipoCompetencia().equals(configuracaoNivelCompetencia.getTipoCompetencia()))
					{
						nivelCompetenciaCandidato.setCompetenciaDescricao(configuracaoNivelCompetencia.getCompetenciaDescricao());
						break;
					}
				}				
			}
		}
		
		return niveisCompetenciaFaixaSalariaisSalvos;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long configuracaoNivelCompetenciaColaboradorId) {
		return getDao().findByColaborador(configuracaoNivelCompetenciaColaboradorId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId) {
		return getDao().findCompetenciaByFaixaSalarial(faixaId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Long[] competenciasIds) {
		return getDao().findCompetenciaColaborador(competenciasIds, false);
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds) 
	{
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(competenciasIds, false);
		Collection<ConfiguracaoNivelCompetencia> configuracaoAbaixos = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias)
		{
			if(configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() != null && configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() < configuracaoNivelCompetencia.getNivelCompetencia().getOrdem())
				configuracaoAbaixos.add(configuracaoNivelCompetencia);
		}
		
		return configuracaoAbaixos;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Long empresaId) 
	{
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias =  getDao().findCompetenciaColaborador(null, true);
		Collection<NivelCompetencia> nivelCompetencias = nivelCompetenciaManager.findAllSelect(empresaId);
		Collection<ConfiguracaoNivelCompetenciaVO> configuracaoNivelCompetenciaVOs = new ArrayList<ConfiguracaoNivelCompetenciaVO>();
		ConfiguracaoNivelCompetenciaVO configuracaoNivelCompetenciaVO = null;
		
		String nomeColaboradorAnterior = "";
		
//		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciaFaixaSalarials = new ArrayList<ConfiguracaoNivelCompetencia>();
//		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia: configuracaoNivelCompetencias) 
//			if(configuracaoNivelCompetencia.isFaixaSalarial())
//				configuracaoNivelCompetenciaFaixaSalarials.add(configuracaoNivelCompetencia);
//			
//		ConfiguracaoNivelCompetenciaVO configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaVO();
//		configuracaoNivelCompetenciaFaixaSalarial.setConfiguracaoNivelCompetencias(configuracaoNivelCompetenciaFaixaSalarials);
			

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia: configuracaoNivelCompetencias) 
		{
			String nomeColaboradorAtual = "";
			if(configuracaoNivelCompetencia.isColaboradorOuCandidato())
				nomeColaboradorAtual = configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome();
			
			if (nomeColaboradorAnterior == null || !nomeColaboradorAnterior.equals(nomeColaboradorAtual))
			{
				configuracaoNivelCompetenciaVO = new ConfiguracaoNivelCompetenciaVO();
				configuracaoNivelCompetenciaVO.setNivelCompetencias(nivelCompetencias);
				configuracaoNivelCompetenciaVO.setNome(nomeColaboradorAtual);
				configuracaoNivelCompetenciaVO.setConfiguracaoNivelCompetencias(new ArrayList<ConfiguracaoNivelCompetencia>());
				
				configuracaoNivelCompetenciaVOs.add(configuracaoNivelCompetenciaVO);
			}
			
			configuracaoNivelCompetenciaVO.getConfiguracaoNivelCompetencias().add(configuracaoNivelCompetencia);
			
			nomeColaboradorAnterior = nomeColaboradorAtual;
		}
		
//		ConfiguracaoNivelCompetencia c1 = new ConfiguracaoNivelCompetencia();
//		c1.set
//		ConfiguracaoNivelCompetencia c1 = new ConfiguracaoNivelCompetencia();
//		ConfiguracaoNivelCompetencia c1 = new ConfiguracaoNivelCompetencia();
//		ConfiguracaoNivelCompetencia c1 = new ConfiguracaoNivelCompetencia();
//		
//		configuracaoNivelCompetenciaVO = new ConfiguracaoNivelCompetenciaVO();
//		configuracaoNivelCompetenciaVO.setNome("joao");
//		configuracaoNivelCompetenciaVOs.add(configuracaoNivelCompetenciaVO);
//		
//		configuracaoNivelCompetenciaVO = new ConfiguracaoNivelCompetenciaVO();
//		configuracaoNivelCompetenciaVO.setNome(null);
//		configuracaoNivelCompetenciaVOs.add(configuracaoNivelCompetenciaVO);
//		
//		configuracaoNivelCompetenciaVO = new ConfiguracaoNivelCompetenciaVO();
//		configuracaoNivelCompetenciaVO.setNome(null);
//		configuracaoNivelCompetenciaVOs.add(configuracaoNivelCompetenciaVO);

		return configuracaoNivelCompetenciaVOs;
	}
}
