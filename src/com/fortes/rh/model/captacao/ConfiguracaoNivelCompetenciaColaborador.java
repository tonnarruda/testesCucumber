package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoNivelCompetenciaColaborador_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetenciaColaborador extends AbstractModel implements Serializable
{
	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne
	private Colaborador avaliador;
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@Temporal(TemporalType.DATE)
	private Date data;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="configuracaoNivelCompetenciaColaborador")
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias;
	
	public ConfiguracaoNivelCompetenciaColaborador()
	{
	}
	
	public Colaborador getColaborador() 
	{
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador) 
	{
		this.colaborador = colaborador;
	}
	
	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}
	
	public void setFaixaSalarial(FaixaSalarial faixaSalarial) 
	{
		this.faixaSalarial = faixaSalarial;
	}
	
	public Date getData() 
	{
		return data;
	}

	public void setData(Date data) 
	{
		this.data = data;
	}
	
	public void setProjectionColaboradorId(Long colaboradorId)
	{
		inicializarColaborador();
		colaborador.setId(colaboradorId);
	}

	private void inicializarColaborador() {
		if (colaborador == null)
			colaborador = new Colaborador();
	}
	
	public void setProjectionColaboradorNome(String nome)
	{
		inicializarColaborador();
		colaborador.setNome(nome);
	}
	
	public void setProjectionCargoId(Long cargoId)
	{
		inicializarFaixaSalarial();
		
		if (faixaSalarial.getCargo() == null)
			faixaSalarial.setCargo(new Cargo());
		
		faixaSalarial.getCargo().setId(cargoId);
	}

	public void setProjectionCargoNome(String nome)
	{
		inicializarFaixaSalarial();
		
		if (faixaSalarial.getCargo() == null)
			faixaSalarial.setCargo(new Cargo());
		
		faixaSalarial.getCargo().setNome(nome);
	}
	
	public void setProjectionFaixaSalarialId(Long faixaId)
	{
		inicializarFaixaSalarial();
		
		faixaSalarial.setId(faixaId);
	}

	public void setProjectionFaixaSalarialNome(String nome)
	{
		inicializarFaixaSalarial();
		
		faixaSalarial.setNome(nome);
	}
	
	public void inicializarFaixaSalarial()
	{
		if (faixaSalarial == null)
			faixaSalarial = new FaixaSalarial();
	}

	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias()
	{
		return configuracaoNivelCompetencias;
	}

	public void setConfiguracaoNivelCompetencias(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias)
	{
		this.configuracaoNivelCompetencias = configuracaoNivelCompetencias;
	}

	public Colaborador getAvaliador()
	{
		return avaliador;
	}

	public void setAvaliador(Colaborador avaliador)
	{
		this.avaliador = avaliador;
	}
}
