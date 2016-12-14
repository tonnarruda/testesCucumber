package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.StatusLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="lnt_sequence", allocationSize=1)
public class Lnt extends AbstractModel implements Serializable
{
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Empresa> empresas;
	@Temporal(TemporalType.DATE)
	private Date dataFinalizada;
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<AreaOrganizacional> areasOrganizacionais;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="lnt")
	private Collection<CursoLnt> cursoLnts;
	
	@Transient
	private boolean selected;//usado na listagem de colaboradorTurma.
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataFinalizada()
	{
		return dataFinalizada;
	}
	
	public void setDataFinalizada(Date dataFinalizada)
	{
		this.dataFinalizada = dataFinalizada;
	}

	public Collection<AreaOrganizacional> getAreasOrganizacionais() {
		return areasOrganizacionais;
	}
	
	public void setAreasOrganizacionais(Collection<AreaOrganizacional> areasOrganizacionais)
	{
		this.areasOrganizacionais = areasOrganizacionais;
	}
	
	@NaoAudita
	public String getPeriodoFormatado()
	{
		return DateUtil.formataDiaMesAno(dataInicio) + " a " + DateUtil.formataDiaMesAno(dataFim);
	}

	@NaoAudita
	public Character getStatus()
	{
		return StatusLnt.getStatus(dataInicio, dataFim, dataFinalizada);
	}

	public Collection<CursoLnt> getCursoLnts()
	{
		return cursoLnts;
	}

	public void setCursoLnts(Collection<CursoLnt> cursoLnts)
	{
		this.cursoLnts = cursoLnts;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	@SuppressWarnings("static-access")
	@NaoAudita
	public boolean getPrazoLntVencida() {
		if(dataFim == null)
			return false;
		
		int ts = new DateUtil().diferencaEntreDatas(dataFim, new Date(), false);
		
		return 0 < ts;
	}
	
	public boolean getFinalizada(){
		return dataFinalizada != null;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
