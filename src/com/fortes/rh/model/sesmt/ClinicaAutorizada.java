package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoClinica;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="clinicaautorizada_sequence", allocationSize=1)
public class ClinicaAutorizada extends AbstractModel implements Serializable
{
	@Column(length=44)
    private String nome;
	@Column(length=80)
	private String endereco;
	@Column(length=20)
    private String crm;
	@Column(length=14)
    private String cnpj;
	@Column(length=50)
	private String outro;
	@Column(length=5)
    private String tipo;
	@Column(length=10)
	private String telefone;
	@Column(length=45)
	private String horarioAtendimento;

    @Temporal(TemporalType.DATE)
    private Date data;

    @Temporal(TemporalType.DATE)
    private Date dataInativa;

	@ManyToOne
	private Empresa empresa;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Exame> exames;

	@Transient
	private TipoClinica tipoClinicas = new TipoClinica();

	public String getCnpj()
	{
		return cnpj;
	}
	public void setCnpj(String cnpj)
	{
		this.cnpj = cnpj;
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
	public String getTipo()
	{
		return tipo;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getTipoDescricao()
	{
		return (String)tipoClinicas.get(getTipo());
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public Date getDataInativa()
	{
		return dataInativa;
	}
	public void setDataInativa(Date dataInativa)
	{
		this.dataInativa = dataInativa;
	}
	public Collection<Exame> getExames()
	{
		return exames;
	}
	public void setExames(Collection<Exame> exames)
	{
		this.exames = exames;
	}
	public String getEndereco()
	{
		return endereco;
	}
	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getHorarioAtendimento() {
		return horarioAtendimento;
	}
	public void setHorarioAtendimento(String horarioAtendimento) {
		this.horarioAtendimento = horarioAtendimento;
	}
	public String getOutro() {
		return outro;
	}
	public void setOutro(String outro) {
		this.outro = outro;
	}

}