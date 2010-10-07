/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="empresa_sequence", allocationSize=1)
public class Empresa extends AbstractModel implements Serializable
{
	@Column(length=15)
	private String nome;
	@Column(length=20)
	private String cnpj;
	@Column(length=60)
	private String razaoSocial;
	@Column(length=12)
	private String codigoAC;
	@Column(length=120)
    private String emailRemetente;
	@Column(length=120)
    private String emailRespSetorPessoal;
	@Column(length=120)
	private String emailRespRH;

	@Column(length=20)
    private String cnae;
	@Column(length=10)
    private String grauDeRisco;
	@Column(length=100)
    private String representanteLegal;
	@Column(length=100)
    private String nitRepresentanteLegal;
	@Column(length=50)
    private String horarioTrabalho;
	@Column(length=100)
    private String endereco;

	private boolean exibirSalario;
    private boolean acIntegra;
    @Column(length=120)
    private String acUrlSoap;
    @Column(length=120)
    private String acUrlWsdl;
    @Column(length=100)
    private String acUsuario;
    @Column(length=30)
    private String acSenha;
    @Column(length=400)
	private String mensagemModuloExterno;
    private int maxCandidataCargo;
    @Column(length=200)
    private String logoUrl;
    @Column(length=200)
    private String atividade;

    @ManyToOne
	private Estado uf;
	@ManyToOne
	private Cidade cidade;

	//projection
	public void setProjectionCidadeNome(String cidadeNome)
	{
		if(cidade == null)
			cidade = new Cidade();

		cidade.setNome(cidadeNome);
	}

	public boolean isAcIntegra()
	{
		return acIntegra;
	}

	public void setAcIntegra(boolean acIntegra)
	{
		this.acIntegra = acIntegra;
	}

	public String getAcSenha()
	{
		return acSenha;
	}

	public void setAcSenha(String acSenha)
	{
		this.acSenha = acSenha;
	}

	public String getAcUrlSoap()
	{
		return acUrlSoap;
	}

	public void setAcUrlSoap(String acUrlSoap)
	{
		this.acUrlSoap = acUrlSoap;
	}

	public String getAcUrlWsdl()
	{
		return acUrlWsdl;
	}

	public void setAcUrlWsdl(String acUrlWsdl)
	{
		this.acUrlWsdl = acUrlWsdl;
	}

	public String getAcUsuario()
	{
		return acUsuario;
	}

	public void setAcUsuario(String acUsuario)
	{
		this.acUsuario = acUsuario;
	}

	public String getEmailRemetente()
	{
		return emailRemetente;
	}

	public void setEmailRemetente(String emailRemetente)
	{
		this.emailRemetente = emailRemetente;
	}

	public String getEmailRespSetorPessoal()
	{
		return emailRespSetorPessoal;
	}

	public void setEmailRespSetorPessoal(String emailRespSetorPessoal)
	{
		this.emailRespSetorPessoal = emailRespSetorPessoal;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getCnpj()
	{
		return cnpj;
	}

	public void setCnpj(String cnpj)
	{
		this.cnpj = cnpj;
	}


	public String getRazaoSocial()
	{
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial)
	{
		this.razaoSocial = razaoSocial;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId())
				.append("nome", this.nome)
				.append("cnpj",this.cnpj)
				.append("codigoAC",this.codigoAC)
				.append("emailRemetente",this.emailRemetente)
				.append("emailRespSetorPessoal",this.emailRespSetorPessoal)
				.append("emailRespRH",this.emailRespRH)
				.append("acIntegra", this.acIntegra)
				.append("acUrlWsdl",this.acUrlWsdl)
				.append("acUrlSoap", this.acUrlSoap)
				.append("acUsuario", this.acUsuario)
				.append("acSenha", this.acSenha)
				.append("maxCandidataCargo", this.maxCandidataCargo)
				.append("razaoSocial", this.razaoSocial).toString();
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}

	public String getCnae()
	{
		return cnae;
	}

	public void setCnae(String cnae)
	{
		this.cnae = cnae;
	}

	public String getEndereco()
	{
		return endereco;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}

	public String getGrauDeRisco()
	{
		return grauDeRisco;
	}

	public void setGrauDeRisco(String grauDeRisco)
	{
		this.grauDeRisco = grauDeRisco;
	}

	public String getHorarioTrabalho()
	{
		return horarioTrabalho;
	}

	public void setHorarioTrabalho(String horarioTrabalho)
	{
		this.horarioTrabalho = horarioTrabalho;
	}

	public String getRepresentanteLegal()
	{
		return representanteLegal;
	}

	public void setRepresentanteLegal(String representanteLegal)
	{
		this.representanteLegal = representanteLegal;
	}

	public String getNitRepresentanteLegal()
	{
		return nitRepresentanteLegal;
	}

	public void setNitRepresentanteLegal(String nitRepresentanteLegal)
	{
		this.nitRepresentanteLegal = nitRepresentanteLegal;
	}

	public int getMaxCandidataCargo()
	{
		return maxCandidataCargo;
	}

	public void setMaxCandidataCargo(int maxCandidataCargo)
	{
		this.maxCandidataCargo = maxCandidataCargo;
	}

	public String getLogoUrl()
	{
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl)
	{
		this.logoUrl = logoUrl;
	}

	public String getEmailRespRH()
	{
		return emailRespRH;
	}

	public void setEmailRespRH(String emailRespRH)
	{
		this.emailRespRH = emailRespRH;
	}

	public boolean isExibirSalario()
	{
		return exibirSalario;
	}

	public void setExibirSalario(boolean exibirSalario)
	{
		this.exibirSalario = exibirSalario;
	}

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}

	public Estado getUf()
	{
		return uf;
	}

	public void setUf(Estado uf)
	{
		this.uf = uf;
	}

	public String getAtividade()
	{
		return atividade;
	}

	public void setAtividade(String atividade)
	{
		this.atividade = atividade;
	}

	public String getMensagemModuloExterno() {
		return mensagemModuloExterno;
	}

	public void setMensagemModuloExterno(String mensagemModuloExterno) {
		this.mensagemModuloExterno = mensagemModuloExterno;
	}
}