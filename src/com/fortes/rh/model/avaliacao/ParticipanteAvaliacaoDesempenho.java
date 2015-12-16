package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;

@Entity
@SequenceGenerator(name="sequence", sequenceName="participanteavaliacaodesempenho_sequence", allocationSize=1)//TODO utilizamos a sequence do questionario para não alterar as consultas, Francisco
public class ParticipanteAvaliacaoDesempenho extends AbstractModel implements Serializable, Cloneable
{
	@Transient private static final long serialVersionUID = 1L;

	@ManyToOne
    private Colaborador colaborador;
	
	@ManyToOne
	private AvaliacaoDesempenho avaliacaoDesempenho;
	
	private char tipo;

	private Double produtividade;
	
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public void setColaboradorId(Long id) {
		if ( colaborador == null )
			colaborador =  new Colaborador();
		colaborador.setId(id);
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	
	public String getProdutividadeString() {
		if (produtividade == null)
			return "";
		else 
			return produtividade.toString().replace(".", ",");
	}

	public Double getProdutividade() {
		return produtividade;
	}

	public void setProdutividade(Double produtividade) {
		this.produtividade = produtividade;
	}
	
	public void setProjectionAvaliacaoDesempenhoId(Long id) {
		if (this.avaliacaoDesempenho == null)
			this.avaliacaoDesempenho = new AvaliacaoDesempenho();
		
		this.avaliacaoDesempenho.setId(id);
	}
	
	public void setProjectionColaboradorId(Long id) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setId(id);
	}

	public void setProjectionColaboradorNome(String nome) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setNome(nome);
	}
	
	public void setProjectionColaboradorNomeComercial(String nomeComercial) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setNomeComercial(nomeComercial);
	}
	
	public void setProjectionColaboradorFaixaId(Long id) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if(this.getColaborador().getFaixaSalarial() == null)
			this.colaborador.setFaixaSalarial(new FaixaSalarial());
		
		this.colaborador.getFaixaSalarial().setId(id);
	}
	
	public void setProjectionColaboradorFaixaNome(String nome) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if(this.getColaborador().getFaixaSalarial() == null)
			this.colaborador.setFaixaSalarial(new FaixaSalarial());
		
		this.colaborador.getFaixaSalarial().setNome(nome);
	}
	
	public void setProjectionColaboradorFaixaCargoNome(String nome) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if(this.getColaborador().getFaixaSalarial() == null)
			this.colaborador.setFaixaSalarial(new FaixaSalarial());
		
		if(this.getColaborador().getFaixaSalarial().getCargo() == null)
			this.colaborador.getFaixaSalarial().setCargo(new Cargo());
		
		this.colaborador.getFaixaSalarial().getCargo().setNome(nome);
	}
	
	public void setProjectionColaboradorAreaOrganizacionalNome(String nome) {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if(this.getColaborador().getAreaOrganizacional() == null)
			this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		
		this.colaborador.getAreaOrganizacional().setNome(nome);
	}
}
