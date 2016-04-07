package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaManagerImpl extends GenericManagerImpl<NivelCompetencia, NivelCompetenciaDao> implements NivelCompetenciaManager
{
	public Collection<NivelCompetencia> findAllSelect(Long empresaId, Long nivelCompetenciaHistoricoId, Date data)
	{
		return getDao().findAllSelect(empresaId, nivelCompetenciaHistoricoId, data);
	}
	
	public Collection<NivelCompetencia> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public void validaLimite(Long empresaId) throws Exception 
	{
		if (getDao().findToList(new String[] {"id"}, new String[] {"id"}, new String[] {"empresa.id"}, new Long[] {empresaId}).size() >= 10)
			throw new FortesException("Não é permitido cadastrar mais que 10(dez) níveis de competência.");
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long cargoId, Long empresaId) 
	{
		return getDao().findByCargoOrEmpresa(cargoId, empresaId);
	}

	public Double getPontuacaoObtidaByConfiguracoesNiveisCompetencia(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaMarcados) 
	{
		Double pontuacao = 0.0;
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaMarcados) {
			if(configuracaoNivelCompetencia.getNivelCompetencia() != null && configuracaoNivelCompetencia.getNivelCompetencia().getId() != null) {
				Double pontuacaoDaCompetencia = 0.0;
				if ( configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios() != null && configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios().size() > 0 ) {
					Double soma = 0.0;
					Integer qtdCriteriosMarcados = 0;
					for (ConfiguracaoNivelCompetenciaCriterio criterioAvaliacaoCompetencia : configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios()) {
						if(criterioAvaliacaoCompetencia.getNivelCompetencia() != null && criterioAvaliacaoCompetencia.getNivelCompetencia().getOrdem() != null){
							soma+=criterioAvaliacaoCompetencia.getNivelCompetencia().getOrdem();
							qtdCriteriosMarcados++;
						}
					}
					if(qtdCriteriosMarcados != 0)
						pontuacaoDaCompetencia += soma / qtdCriteriosMarcados;
				} else {
					pontuacaoDaCompetencia += configuracaoNivelCompetencia.getNivelCompetencia().getOrdem();
				}
				
				pontuacao += pontuacaoDaCompetencia * (configuracaoNivelCompetencia.getPesoCompetencia()==null?1:configuracaoNivelCompetencia.getPesoCompetencia());
			}
		}
		
		return pontuacao;
	}

	public int getOrdemMaxima(Long empresaId, Long nivelCompetenciaHistoricoId) 
	{
		return getDao().getOrdemMaxima(empresaId, nivelCompetenciaHistoricoId);
	}
	
	public Double getOrdemMaximaByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) 
	{
		return getDao().getOrdemMaximaByNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
	}

	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual) 
	{
		return getDao().existePercentual(nivelCompetenciaId, empresaId, percentual);
	}

	public boolean existeNivelCompetenciaSemPercentual(Long empresaId) 
	{
		return getDao().existeNivelCompetenciaSemPercentual(empresaId);
	}

}
