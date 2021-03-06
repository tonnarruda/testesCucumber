package com.fortes.rh.model.avaliacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaocompetenciaavaliacaodesempenho_sequence", allocationSize=1)//TODO utilizamos a sequence do questionario para não alterar as consultas, Francisco
public class ConfiguracaoCompetenciaAvaliacaoDesempenho extends AbstractModel implements Serializable, Cloneable
{
	@Transient private static final long serialVersionUID = 1L;

	@ManyToOne
    private Colaborador avaliador;
	
	@ManyToOne
	private AvaliacaoDesempenho avaliacaoDesempenho;
	
	@Column(name="competencia_id")
	private Long competenciaId;  
	
	@Column
	private Character tipoCompetencia;

	@ManyToOne
	private ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial;
	
	@Transient
	private String competenciaDescricao;
	
	@Transient
	private boolean possuiResposta;
	
	public Colaborador getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Colaborador avaliador) {
		this.avaliador = avaliador;
	}

	public Long getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(Long competenciaId) {
		this.competenciaId = competenciaId;
	}

	public Character getTipoCompetencia() {
		return tipoCompetencia;
	}

	public void setTipoCompetencia(Character tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial() {
		return configuracaoNivelCompetenciaFaixaSalarial;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarial(
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) {
		this.configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarial;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}
	
	public void setProjectionAvaliacaoDesempenhoId(Long avaliacaoId){
		inicializaAvaliacaoDesempenho();
		this.avaliacaoDesempenho.setId(avaliacaoId);
	}
	
	public void setProjectionAvaliacaoDesempenhoTitulo(String avaliacaoDesempenhoTitulo){
		inicializaAvaliacaoDesempenho();
		this.avaliacaoDesempenho.setTitulo(avaliacaoDesempenhoTitulo);
	}
	
	public void setProjectionAvaliacaoDesempenhoDataInicio(Date dataInicio){
		inicializaAvaliacaoDesempenho();
		this.avaliacaoDesempenho.setInicio(dataInicio);
	}
	
	public void setProjectionAvaliacaoDesempenhoDataFim(Date dataFim){
		inicializaAvaliacaoDesempenho();
		this.avaliacaoDesempenho.setFim(dataFim);
	}

	private void inicializaAvaliacaoDesempenho() {
		if ( this.avaliacaoDesempenho == null)
			this.avaliacaoDesempenho = new AvaliacaoDesempenho();
	}
	
	public void setProjectionConfiguracaoNivelCompetenciaFaixaSalarialId(Long configuracaoNivelCompetenciaFaixaSalarialId){
		if ( this.configuracaoNivelCompetenciaFaixaSalarial == null)
			this.configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		
		this.configuracaoNivelCompetenciaFaixaSalarial.setId(configuracaoNivelCompetenciaFaixaSalarialId);
	}
	
	public void setProjectionAvaliadorId(Long avaliadorId){
		if ( this.avaliador == null)
			this.avaliador = new Colaborador();
		
		this.avaliador.setId(avaliadorId);
	}

	public String getCompetenciaDescricao() {
		return competenciaDescricao;
	}

	public void setCompetenciaDescricao(String competenciaDescricao) {
		this.competenciaDescricao = competenciaDescricao;
	}

	public boolean isPossuiResposta() {
		return possuiResposta;
	}

	public void setPossuiResposta(boolean possuiResposta) {
		this.possuiResposta = possuiResposta;
	}
}