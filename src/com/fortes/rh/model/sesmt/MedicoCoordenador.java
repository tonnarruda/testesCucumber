package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.model.type.File;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="medicocoordenador_sequence", allocationSize=1)
public class MedicoCoordenador extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;

	@Temporal(TemporalType.DATE)
	private Date inicio;
	@Temporal(TemporalType.DATE)
	private Date fim;
	
	@Column(length=20)
	private String crm;
	@Column(length=100)
	private String registro;
	@Column(length=100)
	private String especialidade;
	
	@Column(length=15)
	private String nit;

	private File assinaturaDigital;

	@ManyToOne
	private Empresa empresa;
	
	//Utilizado no relatório de PPP
	@Transient
	private Date dataDesligamento;

    public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(projectionEmpresaId);
	}
    
    public String getPeriodoFormatado()
	{
		String periodo = "";
		if (inicio != null)
			periodo += DateUtil.formataDiaMesAno(inicio);
		if (fim != null)
			periodo += " - " + DateUtil.formataDiaMesAno(fim);

		return periodo;
	}
    
    public String getPeriodoRelatorio()
	{
    	String dataFim = "__/__/___";
    	if(dataDesligamento != null)
    		dataFim = DateUtil.formataDiaMesAno(dataDesligamento);
    	
		return DateUtil.formataDiaMesAno(this.inicio) + " a " + (this.fim != null ? DateUtil.formataDiaMesAno(this.fim) : dataFim);
	}

    public String getCrmRegistro()
    {
    	StringBuilder retorno = new StringBuilder("");
    	if (StringUtils.isNotBlank(crm))
    		retorno.append("CRM " + crm);
    	if (StringUtils.isNotBlank(registro))
    	{
    		if (!retorno.equals(""))
    			retorno.append(" - ");

    		retorno.append("Registro Medicina do Trabalho " + registro);
    	}
    	return retorno.toString();
    }
	
	public String getCrm()
	{
		return crm;
	}
	public void setCrm(String crm)
	{
		this.crm = crm;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getRegistro()
	{
		return registro;
	}

	public void setRegistro(String registro)
	{
		this.registro = registro;
	}

	public String getEspecialidade()
	{
		return especialidade;
	}

	public void setEspecialidade(String especialidade)
	{
		this.especialidade = especialidade;
	}

	public File getAssinaturaDigital()
	{
		return assinaturaDigital;
	}

	public void setAssinaturaDigital(File assinaturaDigital)
	{
		this.assinaturaDigital = assinaturaDigital;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}
}