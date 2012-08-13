package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Perfil;

@Entity
@SequenceGenerator(name="sequence", sequenceName="parametrosDoSistema_sequence", allocationSize=1)
public class ParametrosDoSistema extends AbstractModel implements Serializable
{
	@Transient private static final long serialVersionUID = 7274995467895468424L;
	

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
    @Temporal(TemporalType.DATE)
    private Date proximaVersao;
    
    private Boolean enviarEmail = false;
    private Boolean atualizadoSucesso;
    private Boolean compartilharColaboradores;
    private Boolean compartilharCandidatos;

    // Forçar caixa alta no módulo externo
    private Boolean upperCase = false;
    
    private Long atualizaPapeisIdsAPartirDe;

	@ManyToOne
    private Perfil perfilPadrao;

    private String emailDoSuporteTecnico;
    private String camposCandidatoVisivel;
    private String camposCandidatoObrigatorio;
    private String camposCandidatoTabs;
    private boolean autenticacao;
    
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
				.append("atualizadorPath", this.atualizadorPath).toString();
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

	public Long getAtualizaPapeisIdsAPartirDe() {
		return atualizaPapeisIdsAPartirDe;
	}
	public void setAtualizaPapeisIdsAPartirDe(Long atualizaPapeisIdsAPartirDe) {
		this.atualizaPapeisIdsAPartirDe = atualizaPapeisIdsAPartirDe;
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

	public String getCamposCandidatoVisivel() {
		return camposCandidatoVisivel;
	}
	public void setCamposCandidatoVisivel(String camposCandidatoVisivel) {
		this.camposCandidatoVisivel = camposCandidatoVisivel;
	}
	public String getCamposCandidatoObrigatorio() {
		return camposCandidatoObrigatorio;
	}
	public void setCamposCandidatoObrigatorio(String camposCandidatoObrigatorio) {
		this.camposCandidatoObrigatorio = camposCandidatoObrigatorio;
	}
	public String getCamposCandidatoTabs() {
		return camposCandidatoTabs;
	}
	public void setCamposCandidatoTabs(String camposCandidatoTabs) {
		this.camposCandidatoTabs = camposCandidatoTabs;
	}
	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}
	public void setCompartilharColaboradores(Boolean compartilharColaboradores) {
		this.compartilharColaboradores = compartilharColaboradores;
	}
	public Boolean getCompartilharCandidatos() {
		return compartilharCandidatos;
	}
	public void setCompartilharCandidatos(Boolean compartilharCandidatos) {
		this.compartilharCandidatos = compartilharCandidatos;
	}
	public Date getProximaVersao() {
		return proximaVersao;
	}
	public void setProximaVersao(Date proximaversao) {
		this.proximaVersao = proximaversao;
	}
	public boolean verificaRemprot() {
		return this.proximaVersao == null || new Date().getTime() - this.proximaVersao.getTime() > 0;
	}
	public boolean isAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(boolean autenticacao) {
		this.autenticacao = autenticacao;
	}
}