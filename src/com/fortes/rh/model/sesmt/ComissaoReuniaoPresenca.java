package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissaoreuniaopresenca_sequence", allocationSize=1)
public class ComissaoReuniaoPresenca extends AbstractModel implements Serializable
{
	private Boolean presente = false;
	
	@Column(length=100)
	private String justificativaFalta;

	@ManyToOne
	private ComissaoReuniao comissaoReuniao;

	@ManyToOne
	private Colaborador colaborador;

	@Transient
	private Boolean desligado = false;
	
	public ComissaoReuniaoPresenca()
	{
	}

	public ComissaoReuniaoPresenca(Colaborador colaborador, Long comissaoReuniaoId)
	{
		this.colaborador = colaborador;
		setProjectionComissaoReuniaoId(comissaoReuniaoId);
	}
	
	public ComissaoReuniaoPresenca(Long colaboradorId, String colaboradorNome, Long comissaoReuniaoId, String comissaoReuniaoTipo, Date comissaoReuniaoData, Boolean presente, String justificativaFalta)
	{
		setProjectionColaboradorId(colaboradorId);
		setProjectionColaboradorNome(colaboradorNome);
		setProjectionComissaoReuniaoId(comissaoReuniaoId);
		setProjectionComissaoReuniaoTipo(comissaoReuniaoTipo);
		setProjectionComissaoReuniaoData(comissaoReuniaoData);
		setPresente(presente);
		setJustificativaFalta(justificativaFalta);
	}
	
	public ComissaoReuniaoPresenca(Long colaboradorId, String colaboradorNome, Boolean presente, String justificativaFalta)
	{
		setProjectionColaboradorId(colaboradorId);
		setProjectionColaboradorNome(colaboradorNome);
		setPresente(presente);
		setJustificativaFalta(justificativaFalta);
	}
	
	//Projection
	public void setProjectionComissaoReuniaoId(Long comissaoReuniaoId)
	{
		newComissaoReuniao();
		comissaoReuniao.setId(comissaoReuniaoId);
	}
	public void setProjectionColaboradorId(Long colaboradorId)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();

		colaborador.setId(colaboradorId);
	}
	public void setProjectionColaboradorNome(String colaboradorNome)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();

		colaborador.setNome(colaboradorNome);
	}

	public void setProjectionComissaoReuniaoData(Date comissaoReuniaoData)
	{
		newComissaoReuniao();
		comissaoReuniao.setData(comissaoReuniaoData);
	}
	public void setProjectionComissaoReuniaoHora(String comissaoReuniaoHora)
	{
		newComissaoReuniao();
		comissaoReuniao.setHorario(comissaoReuniaoHora);
	}
	public void setProjectionComissaoReuniaoDescricao(String comissaoReuniaoDescricao)
	{
		newComissaoReuniao();
		comissaoReuniao.setDescricao(comissaoReuniaoDescricao);
	}
	public void setProjectionComissaoReuniaoTipo(String comissaoReuniaoTipo)
	{
		newComissaoReuniao();
		comissaoReuniao.setTipo(comissaoReuniaoTipo);
	}

	private void newComissaoReuniao()
	{
		if (this.comissaoReuniao == null)
			this.comissaoReuniao = new ComissaoReuniao();
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public ComissaoReuniao getComissaoReuniao()
	{
		return comissaoReuniao;
	}

	public void setComissaoReuniao(ComissaoReuniao comissaoReuniao)
	{
		this.comissaoReuniao = comissaoReuniao;
	}

	public String getJustificativaFalta()
	{
		return justificativaFalta;
	}

	public void setJustificativaFalta(String justificativaFalta)
	{
		this.justificativaFalta = justificativaFalta;
	}

	public Boolean getPresente()
	{
		return presente;
	}

	public void setPresente(Boolean presente)
	{
		this.presente = presente;
	}

	@Override
	public boolean equals(Object comissaoReuniaoPresenca)
	{
		if (comissaoReuniaoPresenca == null || !(comissaoReuniaoPresenca instanceof ComissaoReuniaoPresenca))
			return false;

		if (super.equals(comissaoReuniaoPresenca))
				return true;

		Colaborador colaborador = ((ComissaoReuniaoPresenca)comissaoReuniaoPresenca).getColaborador();
		if (colaborador == null || this.colaborador == null)
			return false;

		return this.colaborador.equals(colaborador);
	}

	public Boolean getDesligado() {
		return desligado;
	}

	public void setDesligado(Boolean desligado) {
		this.desligado = desligado;
	}
}