package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class ConfiguracaoNivelCompetenciaManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetencia, ConfiguracaoNivelCompetenciaDao> implements ConfiguracaoNivelCompetenciaManager {
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;

	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId) {
		return getDao().findByFaixa(faixaSalarialId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId) {
		return getDao().findByCandidato(candidatoId);
	}

	public void saveCompetencias(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId) {
		if (candidatoId == null)
			getDao().deleteConfiguracaoByFaixa(faixaSalarialId);
		else
			getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId);

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) {
			if (configuracaoNivelCompetencia.getCompetenciaId() != null) {
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				if (candidatoId != null)
					configuracaoNivelCompetencia.setCandidatoIdProjection(candidatoId);

				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	public void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) {
		if (configuracaoNivelCompetenciaColaborador.getId() != null) {
			configuracaoNivelCompetenciaColaboradorManager.update(configuracaoNivelCompetenciaColaborador);
			getDao().deleteByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		} else
			configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.save(configuracaoNivelCompetenciaColaborador);

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) {
			if (configuracaoNivelCompetencia.getCompetenciaId() != null) {
				configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	public Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId) {
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos = getDao().findByCandidato(candidatoId);

		if (!niveisCompetenciaFaixaSalariaisSalvos.isEmpty()) {
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);
			for (ConfiguracaoNivelCompetencia nivelCompetenciaCandidato : niveisCompetenciaFaixaSalariaisSalvos) {
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) {
					if (nivelCompetenciaCandidato.getCompetenciaId().equals(configuracaoNivelCompetencia.getCompetenciaId())
							&& nivelCompetenciaCandidato.getTipoCompetencia().equals(configuracaoNivelCompetencia.getTipoCompetencia())) {
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

	public Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds) {
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(competenciasIds, false);
		Collection<ConfiguracaoNivelCompetencia> configuracaoAbaixos = new ArrayList<ConfiguracaoNivelCompetencia>();

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) {
			if (configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() != null
					&& configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() < configuracaoNivelCompetencia.getNivelCompetencia().getOrdem())
				configuracaoAbaixos.add(configuracaoNivelCompetencia);
		}

		return configuracaoAbaixos;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Long empresaId, Long[] competenciasIds) 
	{
		Collection<ConfiguracaoNivelCompetenciaVO> vos = new ArrayList<ConfiguracaoNivelCompetenciaVO>();

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(competenciasIds, true);
		Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId);

		Map<String, String> competenciaNiveis = new HashMap<String, String>();
		for (ConfiguracaoNivelCompetencia competencia : configuracaoNivelCompetencias) 
			competenciaNiveis.put(competencia.getCompetenciaDescricao(), competencia.getNivelCompetencia().getDescricao());

		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
		for (Map.Entry<String, String> competenciaNivel : competenciaNiveis.entrySet()) 
		{
			for (NivelCompetencia nivel : niveis) 
			{
				boolean isConfiguracaoFaixa = competenciaNivel.getValue().equals(nivel.getDescricao());
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaNivel.getKey(), nivel.getOrdem() + " - " + nivel.getDescricao(), isConfiguracaoFaixa, false));
			}
		}

		Long idColaboradorAnterior = null;
		ConfiguracaoNivelCompetenciaVO vo = null;
		
		for (ConfiguracaoNivelCompetencia configNivelCompetenciaColaborador : configuracaoNivelCompetencias) 
		{
			if(configNivelCompetenciaColaborador.isColaborador())
			{
				if(!configNivelCompetenciaColaborador.getColaboradorId().equals(idColaboradorAnterior))
				{
					vo = new ConfiguracaoNivelCompetenciaVO(configNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome(), new ArrayList<MatrizCompetenciaNivelConfiguracao>(matrizModelo));
					vos.add(vo);
				}
				
				vo.getMatrizes().add(new MatrizCompetenciaNivelConfiguracao(configNivelCompetenciaColaborador.getCompetenciaDescricao(), configNivelCompetenciaColaborador.getNivelCompetenciaColaborador().getOrdem() + " - " + configNivelCompetenciaColaborador.getNivelCompetenciaColaborador().getDescricao(), false, true));
				idColaboradorAnterior = configNivelCompetenciaColaborador.getColaboradorId();
			}
		}
		
		return vos;
	}
}
