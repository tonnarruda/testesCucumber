package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
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
	
	@Column(name="competencia_id", nullable=false)
	private Long competenciaId;
	
	@Column
	private Character tipoCompetencia;

	private FaixaSalarial faixaSalarial;
	
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

	public FaixaSalarial getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}
}