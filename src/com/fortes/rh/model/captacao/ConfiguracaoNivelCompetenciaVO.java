package com.fortes.rh.model.captacao;

import java.util.Collection;

/**
 * Utilizado para gerar Relatorio de configurações de nivel de experiencia
 */
public class ConfiguracaoNivelCompetenciaVO
{
	private String nome;
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias ;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isConfiguracaoFaixa() {
		return nome == null;
	}
	
	public boolean isConfiguracaoColaboradorOuCandidato() {
		return nome != null;
	}

	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias() {
		return configuracaoNivelCompetencias;
	}

	public void setConfiguracaoNivelCompetencias(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias) {
		this.configuracaoNivelCompetencias = configuracaoNivelCompetencias;
	}
}