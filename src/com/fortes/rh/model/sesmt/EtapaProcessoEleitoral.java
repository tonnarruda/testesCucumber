package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

/**
 * @author Tiago Lopes */
@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="etapaprocessoeleitoral_sequence", allocationSize=1)
public class EtapaProcessoEleitoral extends AbstractModel implements Serializable
{
	private String nome;
	private String prazoLegal;
	private Integer prazo = 0;

	@ManyToOne
	private Empresa empresa;

	@ManyToOne
	private Eleicao eleicao;

	@Temporal(TemporalType.DATE)
	private Date data;

	public String getPrazoFormatado()
	{
		String dias = " dias ", prazoFmt = "";

		if (prazo == 1 || prazo == -1)
			dias = " dia ";

		if (prazo < 0)
			prazoFmt = -prazo + dias + "antes da posse";
		else if (prazo > 0)
			prazoFmt = prazo + dias + "depois da posse";
		else
			prazoFmt = "No dia da posse";

		return prazoFmt;
	}

	public String getDataDiaSemana()
	{
		String diaSemana = "-";
		if (data!= null)
			diaSemana = DateUtil.getDiaSemanaDescritivo(data);

		return diaSemana;
	}

	public String getAntesOuDepois()
	{
		String antesOuDepois = "";
		if (prazo != 0)
			antesOuDepois = prazo < 0 ? "antes" : "depois";

		return antesOuDepois;
	}

	/**
	 * Calcula e retorna a data formatada, com os dias em relação à posse.
	 */
	public String getDataFormatadaPrazo()
	{
		String dataFmt = "";
		if (eleicao != null && eleicao.getPosse() != null && this.data != null)
		{
			this.prazo = DateUtil.diferencaEntreDatas(eleicao.getPosse(),data, false);
			dataFmt = DateUtil.formataDiaMesAno(data) + " "
				+ DateUtil.getDiaSemanaDescritivo(data).substring(0,3).toLowerCase()
				+ " (" + getPrazoFormatado() + ")";
		}
		return dataFmt;
	}

	public void setProjectionEleicaoId(Long eleicaoId)
	{
		if (eleicao == null)
			eleicao = new Eleicao();

		eleicao.setId(eleicaoId);
	}
	public void setProjectionEleicaoPosse(Date eleicaoPosse)
	{
		if (eleicao == null)
			eleicao = new Eleicao();

		eleicao.setPosse(eleicaoPosse);
	}

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public Integer getPrazo()
	{
		return prazo;
	}
	public void setPrazo(Integer prazo)
	{
		this.prazo = prazo;
	}
	public String getPrazoLegal()
	{
		return prazoLegal;
	}
	public void setPrazoLegal(String prazoLegal)
	{
		this.prazoLegal = prazoLegal;
	}
	public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public Eleicao getEleicao()
	{
		return eleicao;
	}
	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	/**
	 * Calcula a Data ou Prazo a partir da data de posse da eleição.
	 *
	 * 	- Quando a data não existe, é calculada a partir do prazo
	 *  - Quando a data foi inserida/modificada manualmente, o prazo é recalculado.
	 * @param eleicaoPosse
	 * @param recalcularPrazo
	 *
	 * */
	public void setDataPrazoCalculada(Date eleicaoPosse, boolean recalcularPrazo)
	{
		if (eleicaoPosse != null)
		{
			if (recalcularPrazo)
			{
				prazo = DateUtil.diferencaEntreDatas(eleicaoPosse, data, false);
			}
			else
			{
				Calendar dataCalculada = Calendar.getInstance();
				dataCalculada.setTime(eleicaoPosse);
				dataCalculada.add(Calendar.DAY_OF_YEAR, prazo);
				data = dataCalculada.getTime();
			}
		}
	}
}