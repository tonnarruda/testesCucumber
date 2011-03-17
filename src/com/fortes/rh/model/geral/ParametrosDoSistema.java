package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.sesmt.Exame;

@Entity
@SequenceGenerator(name="sequence", sequenceName="parametrosDoSistema_sequence", allocationSize=1)
public class ParametrosDoSistema extends AbstractModel implements Serializable
{
	@Transient private static final long serialVersionUID = 7274995467895468424L;
	
	@Lob
    private String mailNaoAptos;
    @Column(length=150)
    private String appUrl;
    @Column(length=100)
    private String appContext;
    @Column(length=20)
    private String appVersao;
    @Column(length=100)
    private String emailSmtp;
    private int emailPort;
    @Column(length=50)
    private String emailUser;
    @Column(length=50)
    private String emailPass;
    @Column(length=10)
    private String codEmpresaSuporte;
    @Column(length=10)
    private String codClienteSuporte;
    @Column(length=150)
    private String atualizadorPath;
    @Column(length=50)
    private String servidorRemprot;
    @Column(length=20)
    private String acVersaoWebServiceCompativel;
    @Lob
    private String modulos;
    private boolean campoExtraColaborador;
    

    // dias antes da pesquisa em que será enviado lembrete para quem ainda não respondeu
    @Column(length=20)
    private String diasLembretePesquisa;
    
    // dias antes da avaliação de período de experiência para enviar lembrete 
    @Column(length=20)
    private String diasLembretePeriodoExperiencia;
    
    private Boolean enviarEmail = false;
    private Boolean emailCandidatoNaoApto = false;

    private Boolean atualizadoSucesso;

    // Forçar caixa alta no módulo externo
    private Boolean upperCase = false;
    
    // Exibir aba Documentos no módulo externo
    private Boolean exibirAbaDocumentos = false;
    
    private Long atualizaPapeisIdsAPartirDe;

	@ManyToOne
    private Perfil perfilPadrao;
    @OneToOne
    private Exame exame; // Exame ASO

    private String emailDoSuporteTecnico;
    
	public String getDiasLembretePesquisa()
	{
		return diasLembretePesquisa;
	}
	public void setDiasLembretePesquisa(String diasLembretePesquisa)
	{
		this.diasLembretePesquisa = diasLembretePesquisa;
	}

	public String getAppContext()
	{
		return appContext;
	}
	public void setAppContext(String appContext)
	{
		this.appContext = appContext;
	}
	public String getAppUrl()
	{
		return appUrl;
	}
	public void setAppUrl(String appUrl)
	{
		this.appUrl = appUrl;
	}
	public String getAtualizadorPath()
	{
		return atualizadorPath;
	}
	public void setAtualizadorPath(String atualizadorPath)
	{
		this.atualizadorPath = atualizadorPath;
	}
	public String getAppVersao()
	{
		return appVersao;
	}
	public void setAppVersao(String appVersao)
	{
		this.appVersao = appVersao;
	}
	public String getServidorRemprot()
	{
		return servidorRemprot;
	}
	public void setServidorRemprot(String servidorRemprot)
	{
		this.servidorRemprot = servidorRemprot;
	}
	public String getEmailPass()
	{
		return emailPass;
	}
	public void setEmailPass(String emailPass)
	{
		this.emailPass = emailPass;
	}
	public int getEmailPort()
	{
		return emailPort;
	}
	public void setEmailPort(int emailPort)
	{
		this.emailPort = emailPort;
	}
	public String getEmailSmtp()
	{
		return emailSmtp;
	}
	public void setEmailSmtp(String emailSmtp)
	{
		this.emailSmtp = emailSmtp;
	}
	public String getEmailUser()
	{
		return emailUser;
	}
	public void setEmailUser(String emailUser)
	{
		this.emailUser = emailUser;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("appUrl", this.appUrl)
				.append("id", this.getId())
				.append("emailPort", this.emailPort)
				.append("emailUser",this.emailUser)
				.append("emailPass",this.emailPass)
				.append("emailSmtp", this.emailSmtp)
				.append("appVersao",this.appVersao)
				.append("appContext", this.appContext)
				.append("servidorRemprot", this.servidorRemprot)
				.append("mailNaoAptos", this.mailNaoAptos)
				.append("atualizadorPath", this.atualizadorPath).toString();
	}
	public String getMailNaoAptos()
	{
		return mailNaoAptos;
	}
	public void setMailNaoAptos(String mailNaoAptos)
	{
		this.mailNaoAptos = mailNaoAptos;
	}
	public Perfil getPerfilPadrao()
	{
		return perfilPadrao;
	}
	public void setPerfilPadrao(Perfil perfilPadrao)
	{
		this.perfilPadrao = perfilPadrao;
	}
	public Boolean getAtualizadoSucesso()
	{
		return atualizadoSucesso;
	}
	public void setAtualizadoSucesso(Boolean atualizadoSucesso)
	{
		this.atualizadoSucesso = atualizadoSucesso;
	}
	public Boolean getEnviarEmail()
	{
		return enviarEmail;
	}
	public void setEnviarEmail(Boolean enviarEmail)
	{
		this.enviarEmail = enviarEmail;
	}
	public String getAcVersaoWebServiceCompativel()
	{
		return acVersaoWebServiceCompativel;
	}
	public void setAcVersaoWebServiceCompativel(String acVersaoWebServiceCompativel)
	{
		this.acVersaoWebServiceCompativel = acVersaoWebServiceCompativel;
	}

	public void setProjectionPerfilPadraoId(Long id)
	{
		if (this.perfilPadrao == null)
			this.perfilPadrao = new Perfil();
		this.perfilPadrao.setId(id);
	}

	public void setProjectionPerfilPadraoNome(String nome)
	{
		if (this.perfilPadrao == null)
			this.perfilPadrao = new Perfil();
		this.perfilPadrao.setNome(nome);
	}
	public Boolean getUpperCase()
	{
		return upperCase;
	}
	public void setUpperCase(Boolean capitalizarCampos)
	{
		this.upperCase = capitalizarCampos;
	}
	public String getModulos()
	{
		return modulos;
	}
	public void setModulos(String modulos)
	{
		this.modulos = modulos;
	}
	public Exame getExame() {
		return exame;
	}
	public void setExame(Exame exame) {
		this.exame = exame;
	}
	public Long getAtualizaPapeisIdsAPartirDe() {
		return atualizaPapeisIdsAPartirDe;
	}
	public void setAtualizaPapeisIdsAPartirDe(Long atualizaPapeisIdsAPartirDe) {
		this.atualizaPapeisIdsAPartirDe = atualizaPapeisIdsAPartirDe;
	}
	public String getDiasLembretePeriodoExperiencia() {
		return diasLembretePeriodoExperiencia;
	}
	public void setDiasLembretePeriodoExperiencia(String diasLembretePeriodoExperiencia) {
		this.diasLembretePeriodoExperiencia = diasLembretePeriodoExperiencia;
	}
	public Boolean getExibirAbaDocumentos() {
		return exibirAbaDocumentos;
	}
	public void setExibirAbaDocumentos(Boolean exibirAbaDocumentos) {
		this.exibirAbaDocumentos = exibirAbaDocumentos;
	}
	/**
	 * Verifica se o envio de e-mail está habilitado na aplicação.
	 */
	public boolean isEnvioDeEmailHabilitado() {
		return (enviarEmail != null && enviarEmail);
	}
	public String getEmailDoSuporteTecnico() {
		return emailDoSuporteTecnico;
	}
	public void setEmailDoSuporteTecnico(String emailDoSuporteTecnico) {
		this.emailDoSuporteTecnico = emailDoSuporteTecnico;
	}
	public boolean isCampoExtraColaborador() {
		return campoExtraColaborador;
	}
	public void setCampoExtraColaborador(boolean campoExtraColaborador) {
		this.campoExtraColaborador = campoExtraColaborador;
	}
	public String getCodEmpresaSuporte() {
		return codEmpresaSuporte;
	}
	public void setCodEmpresaSuporte(String codEmpresaSuporte) {
		this.codEmpresaSuporte = codEmpresaSuporte;
	}
	public String getCodClienteSuporte() {
		return codClienteSuporte;
	}
	public void setCodClienteSuporte(String codClienteSuporte) {
		this.codClienteSuporte = codClienteSuporte;
	}
	public Boolean getEmailCandidatoNaoApto() {
		return emailCandidatoNaoApto;
	}
	public void setEmailCandidatoNaoApto(Boolean emailCandidatoNaoApto) {
		this.emailCandidatoNaoApto = emailCandidatoNaoApto;
	}
}