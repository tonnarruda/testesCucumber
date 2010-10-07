package com.fortes.rh.model.ws;

import java.util.Date;

import com.fortes.rh.util.DateUtil;


public class TCandidato
{
	private Long empresaId;
	private String nome;
	private Date nascimento;
	private String cpf;
	private String colocacao;
	private String sexo;
	private Long cidadeId;
	private String bairro;
	private Long[] cargos;
	private String curriculo;
	private String indicadoPor;

	public String getIndicadoPor() {
		return indicadoPor;
	}
	public void setIndicadoPor(String indicadoPor) {
		this.indicadoPor = indicadoPor;
	}
	public String getBairro()
	{
		return bairro;
	}
	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}
	public Long[] getCargos()
	{
		return cargos;
	}
	public void setCargos(Long[] cargos)
	{
		this.cargos = cargos;
	}
	public Long getCidadeId()
	{
		return cidadeId;
	}
	public void setCidadeId(Long cidadeId)
	{
		this.cidadeId = cidadeId;
	}
	public String getColocacao()
	{
		return colocacao;
	}
	public void setColocacao(String colocacao)
	{
		this.colocacao = colocacao;
	}
	public String getCpf()
	{
		return cpf;
	}
	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}
	public String getCurriculo()
	{
		return curriculo;
	}
	public void setCurriculo(String curriculo)
	{
		this.curriculo = curriculo;
	}
	public Long getEmpresaId()
	{
		return empresaId;
	}
	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}
	public Date getNascimento()
	{
		return nascimento;
	}
	public void setNascimento(Date nascimento)
	{
		this.nascimento = nascimento;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getSexo()
	{
		return sexo;
	}
	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}
}