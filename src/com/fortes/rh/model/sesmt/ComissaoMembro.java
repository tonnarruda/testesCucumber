package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissaomembro_sequence", allocationSize=1)
public class ComissaoMembro extends AbstractModel implements Serializable
{
	@Column(length=1)
	private String funcao = FuncaoComissao.SECRETARIO;
	@Column(length=1)
	private String tipo = TipoMembroComissao.ELEITO;

	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne
	private ComissaoPeriodo comissaoPeriodo;

	@Temporal(TemporalType.DATE)
	private Date dataEnt;

	@Transient
	private final FuncaoComissao funcaoMap = FuncaoComissao.getInstance();
	@Transient
	private final TipoMembroComissao tipoMap = TipoMembroComissao.getInstance();
	
	public ComissaoMembro()
	{
	}

	public ComissaoMembro(Colaborador colaborador, ComissaoPeriodo comissaoPeriodo)
	{
		super();
		this.colaborador = colaborador;
		this.comissaoPeriodo = comissaoPeriodo;
	}
	
	public ComissaoMembro(String colaboradorNome, String colaboradorNomeComercial, String tipo, String funcao)
	{
		this.colaborador = new Colaborador();
		this.colaborador.setNome(colaboradorNome);
		this.tipo = tipo;
		this.funcao = funcao;
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

	public void setProjectionComissaoPeriodoId(Long comissaoPeriodoId)
	{
		if (this.comissaoPeriodo == null)
			this.comissaoPeriodo = new ComissaoPeriodo();

		comissaoPeriodo.setId(comissaoPeriodoId);
	}
	
	public void setProjectionComissaoPeriodoAPartirDe(Date comissaoPeriodoAPartirDe)
	{
		if (this.comissaoPeriodo == null)
			this.comissaoPeriodo = new ComissaoPeriodo();
		
		comissaoPeriodo.setaPartirDe(comissaoPeriodoAPartirDe);
	}

	@NaoAudita
	public String getFuncaoDic()
	{
		return funcaoMap.get(this.funcao).toString();
	}
	@NaoAudita
	public String getTipoDic()
	{
		return tipoMap.get(this.tipo).toString();
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public ComissaoPeriodo getComissaoPeriodo()
	{
		return comissaoPeriodo;
	}
	public void setComissaoPeriodo(ComissaoPeriodo comissaoPeriodo)
	{
		this.comissaoPeriodo = comissaoPeriodo;
	}
	public String getFuncao()
	{
		return funcao;
	}
	public void setFuncao(String funcao)
	{
		this.funcao = funcao;
	}
	public String getTipo()
	{
		return tipo;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof ComissaoMembro))
			return false;

		ComissaoMembro comissaoMembro = (ComissaoMembro)object;
		if (comissaoMembro.getColaborador() != null && comissaoMembro.getComissaoPeriodo() != null && this.colaborador != null && this.comissaoPeriodo != null)
		{
			return this.colaborador.getId().equals(comissaoMembro.getColaborador().getId())
					&& this.comissaoPeriodo.getId().equals(comissaoMembro.getComissaoPeriodo().getId());
		}

		return false;
	}

	public Date getDataEnt() {
		return dataEnt;
	}

	public void setDataEnt(Date dataEnt) {
		this.dataEnt = dataEnt;
	}
}