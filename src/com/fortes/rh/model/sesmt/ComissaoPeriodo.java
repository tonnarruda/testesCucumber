package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.ChaveDaAuditoria;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissaoperiodo_sequence", allocationSize=1)
public class ComissaoPeriodo extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date aPartirDe;
	
	@Transient
	private Date fim;

	@ManyToOne
	private Comissao comissao;

	@OneToMany(mappedBy="comissaoPeriodo")
	private Collection<ComissaoMembro> comissaoMembros;
	
	@Transient
	private boolean permitirExcluir;

	public ComissaoPeriodo()
	{
	}

	public ComissaoPeriodo(Date aPartirDe)
	{
		this.aPartirDe = aPartirDe;
	}

	@NaoAudita
	@ChaveDaAuditoria
	public String getPeriodoFormatado()
	{
		String periodoFmt = "";
		if (aPartirDe != null)
			periodoFmt += DateUtil.formataDiaMesAno(aPartirDe);
		if (fim != null)
			periodoFmt += " a " + DateUtil.formataDiaMesAno(fim);

		return periodoFmt;
	}

	public void addMembro(ComissaoMembro comissaoMembro)
	{
		if (comissaoMembros == null)
			comissaoMembros = new ArrayList<ComissaoMembro>();

		comissaoMembros.add(comissaoMembro);
	}

	public void setProjectionComissaoId(Long comissaoId)
	{
		if (comissao == null)
			comissao = new Comissao();

		comissao.setId(comissaoId);
	}
	
	public void setProjectionComissaoDataIni(Date comissaoDataIni)
	{
		if (comissao == null)
			comissao = new Comissao();
		
		comissao.setDataIni(comissaoDataIni);
	}
	
	public void setProjectionComissaoDataFim(Date comissaoDataFim)
	{
		if (comissao == null)
			comissao = new Comissao();
		
		comissao.setDataFim(comissaoDataFim);
	}

	public Comissao getComissao()
	{
		return comissao;
	}

	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public Collection<ComissaoMembro> getComissaoMembros()
	{
		return comissaoMembros;
	}

	public void setComissaoMembros(Collection<ComissaoMembro> comissaoMembros)
	{
		this.comissaoMembros = comissaoMembros;
	}

	public Date getaPartirDe() {
		return aPartirDe;
	}

	public void setaPartirDe(Date aPartirDe) {
		this.aPartirDe = aPartirDe;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public boolean isPermitirExcluir() {
		return permitirExcluir;
	}

	public void setPermitirExcluir(boolean permitirExcluir) {
		this.permitirExcluir = permitirExcluir;
	}
}