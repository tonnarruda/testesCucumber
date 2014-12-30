package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorperiodoexperienciaavaliacao_sequence", allocationSize=1)
public class ColaboradorPeriodoExperienciaAvaliacao extends AbstractModel implements Serializable
{
    @ManyToOne(fetch=FetchType.LAZY)
    private Colaborador colaborador;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private PeriodoExperiencia periodoExperiencia;

    @ManyToOne(fetch=FetchType.LAZY)
    private Avaliacao avaliacao;
    
    private char tipo;

    @Transient
    public static final char TIPO_COLABORADOR = 'C';
    @Transient
    public static final char TIPO_GESTOR = 'G';
    
    public ColaboradorPeriodoExperienciaAvaliacao() {
	}
    
    public ColaboradorPeriodoExperienciaAvaliacao(Colaborador colaborador, Avaliacao avaliacao, PeriodoExperiencia periodoExperiencia, char tipo) {
    	this.colaborador = colaborador;
    	this.periodoExperiencia = periodoExperiencia;
    	this.avaliacao = avaliacao;
    	this.tipo = tipo;
    }
    
    public ColaboradorPeriodoExperienciaAvaliacao(Long colaboradorId, String colaboradorNome, String colaboradorEmail, Date colaboradorDataAdmissao, String empresaEmailRemetente,
    		Long avaliacaoId, String avaliacaoTitulo, Integer peridoExperienciaDias, Long empresaId, String faixaSalarialNome, String cargoNome)
    {
    	if (colaborador == null)
    		colaborador = new Colaborador();
    	if (colaborador.getContato() == null)
    		colaborador.setContato(new Contato());
    	if (colaborador.getEmpresa() == null)
    		colaborador.setEmpresa(new Empresa());
    	
    	if (periodoExperiencia == null)
    		periodoExperiencia = new PeriodoExperiencia();
    	if (avaliacao == null)
    		avaliacao = new Avaliacao();
    	
    	colaborador.setId(colaboradorId);
    	colaborador.setNome(colaboradorNome);
    	colaborador.setDataAdmissao(colaboradorDataAdmissao);
    	colaborador.getContato().setEmail(colaboradorEmail);
    	colaborador.getEmpresa().setId(empresaId);
    	colaborador.getEmpresa().setEmailRemetente(empresaEmailRemetente);
    	colaborador.setCargoNomeProjection(cargoNome);
    	colaborador.setFaixaSalarialNomeProjection(faixaSalarialNome);
    	
    	avaliacao.setId(avaliacaoId);
    	avaliacao.setTitulo(avaliacaoTitulo);
    	periodoExperiencia.setDias(peridoExperienciaDias);
    }

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public PeriodoExperiencia getPeriodoExperiencia() {
		return periodoExperiencia;
	}

	public void setPeriodoExperiencia(PeriodoExperiencia periodoExperiencia) {
		this.periodoExperiencia = periodoExperiencia;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
}