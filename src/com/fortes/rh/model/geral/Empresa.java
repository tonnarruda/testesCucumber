/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="empresa_sequence", allocationSize=1)
public class Empresa extends AbstractModel implements Serializable
{
	@Column(length=15)
	@ChaveDaAuditoria
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
	@Column(length=120)
	private String emailRespLimiteContrato;

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
	private boolean exibirDadosAmbiente;
    private boolean acIntegra;
    @Column(length=400)
	private String mensagemModuloExterno;
    private int maxCandidataCargo;
    @Column(length=200)
    private String logoUrl;
    @Column(length=200)
    private String atividade;
    @Column(length=200)
    private String logoCertificadoUrl;
    @Column(length=200)
    private String imgAniversarianteUrl;
    @Column(length=300)
    private String mensagemCartaoAniversariante;
    private boolean enviarEmailAniversariante;
    @Column(length=3)
    private String grupoAC;
   
    @ManyToOne
	private Estado uf;
	@ManyToOne
	private Cidade cidade;
	private boolean campoExtraColaborador;
	private boolean campoExtraCandidato;
	private Boolean emailCandidatoNaoApto = false;
	@Lob
    private String mailNaoAptos;
    @OneToOne
    private Exame exame; // Exame ASO
    
    private boolean turnoverPorSolicitacao;
	
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

	public boolean isExibirDadosAmbiente() {
		return exibirDadosAmbiente;
	}

	public void setExibirDadosAmbiente(boolean exibirDadosAmbiente) {
		this.exibirDadosAmbiente = exibirDadosAmbiente;
	}

	public void setLogoCertificadoUrl(String logoCertificadoUrl)
	{
		this.logoCertificadoUrl = logoCertificadoUrl;
	}

	public String getLogoCertificadoUrl() 
	{
		return logoCertificadoUrl;
	}

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public boolean isCampoExtraColaborador() {
		return campoExtraColaborador;
	}

	public void setCampoExtraColaborador(boolean campoExtraColaborador) {
		this.campoExtraColaborador = campoExtraColaborador;
	}

	public boolean isCampoExtraCandidato() {
		return campoExtraCandidato;
	}

	public void setCampoExtraCandidato(boolean campoExtraCandidato) {
		this.campoExtraCandidato = campoExtraCandidato;
	}

	public Boolean getEmailCandidatoNaoApto() {
		return emailCandidatoNaoApto;
	}

	public void setEmailCandidatoNaoApto(Boolean emailCandidatoNaoApto) {
		this.emailCandidatoNaoApto = emailCandidatoNaoApto;
	}

	public String getMailNaoAptos() {
		return mailNaoAptos;
	}

	public void setMailNaoAptos(String mailNaoAptos) {
		this.mailNaoAptos = mailNaoAptos;
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public String getEmailRespLimiteContrato() {
		return emailRespLimiteContrato;
	}

	public void setEmailRespLimiteContrato(String emailRespLimiteContrato) {
		this.emailRespLimiteContrato = emailRespLimiteContrato;
	}

	public String getImgAniversarianteUrl() {
		return imgAniversarianteUrl;
	}

	public void setImgAniversarianteUrl(String imgAniversarianteUrl) {
		this.imgAniversarianteUrl = imgAniversarianteUrl;
	}

	public String getMensagemCartaoAniversariante() {
		return mensagemCartaoAniversariante;
	}

	public void setMensagemCartaoAniversariante(String mensagemCartaoAniversariante) {
		this.mensagemCartaoAniversariante = mensagemCartaoAniversariante;
	}

	public boolean isEnviarEmailAniversariante() {
		return enviarEmailAniversariante;
	}

	public void setEnviarEmailAniversariante(boolean enviarEmailAniversariante) {
		this.enviarEmailAniversariante = enviarEmailAniversariante;
	}

	public boolean isTurnoverPorSolicitacao() {
		return turnoverPorSolicitacao;
	}

	public void setTurnoverPorSolicitacao(boolean turnoverPorSolicitacao) {
		this.turnoverPorSolicitacao = turnoverPorSolicitacao;
	}
}