package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissao_sequence", allocationSize=1)
public class Comissao extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date dataIni;

	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@ManyToOne
	private Eleicao eleicao;
	
	@OneToMany(mappedBy="comissao")
	private Collection<ComissaoPeriodo> comissaoPeriodos;

	@OneToMany(mappedBy="comissao")
	private Collection<ComissaoReuniao> comissaoReunioes;
	
	private String ataPosseTexto1;
	private String ataPosseTexto2;
	
	public Comissao() {
		super();
	}

	public Comissao(Date dataIni, Date dataFim) {
		super();
		this.dataIni = dataIni;
		this.dataFim = dataFim;
	}
	

	@NaoAudita
	public String getPeriodoFormatado()
	{
		String periodoFmt = "";
		if (dataIni != null)
			periodoFmt += DateUtil.formataDiaMesAno(dataIni);
		if (dataFim != null)
			periodoFmt += " a " + DateUtil.formataDiaMesAno(dataFim);

		return periodoFmt;
	}

	//Projections
	public void setProjectionEleicaoId(Long eleicaoId)
	{
		if(eleicao == null)
			eleicao = new Eleicao();
		eleicao.setId(eleicaoId);
	}
	
    public void setProjectionEstabelecimentoId(Long estabelecimentoId)
    {
    	if (eleicao == null)
    		eleicao = new Eleicao();
    	eleicao.setProjectionEstabelecimentoId(estabelecimentoId);
    }
    
    public void setProjectionEstabelecimentoNome(String estabelecimentoNome)
    {
    	if (eleicao == null)
    		eleicao = new Eleicao();
    	eleicao.setProjectionEstabelecimentoNome(estabelecimentoNome);
    }

    public void setProjectionEleicaoDescricao(String eleicaoDescricao)
    {
    	if (eleicao == null)
    		eleicao = new Eleicao();
    	eleicao.setDescricao(eleicaoDescricao);
    }
    
    public void setProjectionEleicaoPosse(Date eleicaoPosse)
    {
    	if (eleicao == null)
    		eleicao = new Eleicao();
    	eleicao.setPosse(eleicaoPosse);
    }

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public Eleicao getEleicao()
	{
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public String getAtaPosseTexto1() {
		return ataPosseTexto1;
	}

	public void setAtaPosseTexto1(String ataPosseTexto1) {
		this.ataPosseTexto1 = ataPosseTexto1;
	}

	public String getAtaPosseTexto2() {
		return ataPosseTexto2;
	}

	public void setAtaPosseTexto2(String ataPosseTexto2) {
		this.ataPosseTexto2 = ataPosseTexto2;
	}

	public Collection<ComissaoPeriodo> getComissaoPeriodos() {
		return comissaoPeriodos;
	}

	public void setComissaoPeriodos(Collection<ComissaoPeriodo> comissaoPeriodos) {
		this.comissaoPeriodos = comissaoPeriodos;
	}

	public Collection<ComissaoReuniao> getComissaoReunioes() {
		return comissaoReunioes;
	}

	public void setComissaoReunioes(Collection<ComissaoReuniao> comissaoReunioes) {
		this.comissaoReunioes = comissaoReunioes;
	}
}