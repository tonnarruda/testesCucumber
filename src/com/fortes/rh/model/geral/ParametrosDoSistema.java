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
import com.fortes.rh.util.DateUtil;

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
    @Column(length=100)
    private String emailRemetente;
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
    @Column(length=200)
    private String caminhoBackup;
    @Lob
    private String horariosBackup;
    @Temporal(TemporalType.DATE)
    private Date proximaVersao;
    
    private Boolean enviarEmail = true;
    private Boolean atualizadoSucesso;
    private Boolean compartilharColaboradores;
    private Boolean compartilharCandidatos;
    private Boolean compartilharCursos;
    private boolean inibirGerarRelatorioPesquisaAnonima;
    private int quantidadeColaboradoresRelatorioPesquisaAnonima;
    private boolean bancoConsistente;
    private int quantidadeConstraints;
    private boolean versaoAcademica;

    private Integer sessionTimeout;
    private Integer tamanhoMaximoUpload;
    
    // Forçar caixa alta no módulo externo
    private Boolean upperCase = false;
    private char telaInicialModuloExterno;
    
	@ManyToOne
    private Perfil perfilPadrao;

    private String emailDoSuporteTecnico;
    
    private String camposCandidatoVisivel;
    private String camposCandidatoObrigatorio;
    private String camposCandidatoTabs;
    
    private String camposColaboradorVisivel;
    private String camposColaboradorObrigatorio;
    private String camposColaboradorTabs;
    
    private String camposCandidatoExternoVisivel;
    private String camposCandidatoExternoObrigatorio;
    private String camposCandidatoExternoTabs;
    
    private boolean autenticacao;
    private boolean tls;
    private Integer modulosPermitidosSomatorio = 0;
    private boolean autorizacaoGestorNaSolicitacaoPessoal;
    
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
		return this.proximaVersao == null || proximaVersao.before(DateUtil.criarDataMesAno(new Date()));
	}
	public boolean isAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(boolean autenticacao) {
		this.autenticacao = autenticacao;
	}
	public boolean isTls() {
		return tls;
	}
	public void setTls(boolean tls) {
		this.tls = tls;
	}
	public Integer getSessionTimeout()
	{
		return sessionTimeout;
	}
	public void setSessionTimeout(Integer sessionTimeout)
	{
		this.sessionTimeout = sessionTimeout;
	}
	public String getEmailRemetente() {
		return emailRemetente;
	}
	public void setEmailRemetente(String emailRemetente) {
		this.emailRemetente = emailRemetente;
	}
	
	public String getCaminhoBackup()
	{
		return caminhoBackup;
	}
	
	public void setCaminhoBackup(String caminhoBackup)
	{
		this.caminhoBackup = caminhoBackup;
	}
	
	public String getHorariosBackup()
	{
		return horariosBackup;
	}
	
	public void setHorariosBackup(String horariosBackup)
	{
		this.horariosBackup = horariosBackup;
	}

	public Boolean getCompartilharCursos()
	{
		return compartilharCursos;
	}

	public void setCompartilharCursos(Boolean compartilharCursos)
	{
		this.compartilharCursos = compartilharCursos;
	}
	
	public char getTelaInicialModuloExterno()
	{
		return telaInicialModuloExterno;
	}
	
	public void setTelaInicialModuloExterno(char telaInicialModuloExterno)
	{
		this.telaInicialModuloExterno = telaInicialModuloExterno;
	}
	
	public int getQuantidadeColaboradoresRelatorioPesquisaAnonima() 
	{
		return quantidadeColaboradoresRelatorioPesquisaAnonima;
	}
	
	public void setQuantidadeColaboradoresRelatorioPesquisaAnonima(int quantidadeColaboradoresRelatorioPesquisaAnonima) 
	{
		this.quantidadeColaboradoresRelatorioPesquisaAnonima = quantidadeColaboradoresRelatorioPesquisaAnonima;
	}
	
	public boolean isInibirGerarRelatorioPesquisaAnonima() 
	{
		return inibirGerarRelatorioPesquisaAnonima;
	}
	
	public void setInibirGerarRelatorioPesquisaAnonima(	boolean inibirGerarRelatorioPesquisaAnonima) 
	{
		this.inibirGerarRelatorioPesquisaAnonima = inibirGerarRelatorioPesquisaAnonima;
	}
	
	public boolean isBancoConsistente() {
		return bancoConsistente;
	}
	
	public void setBancoConsistente(boolean bancoConsistente) {
		this.bancoConsistente = bancoConsistente;
	}
	
	public int getQuantidadeConstraints() {
		return quantidadeConstraints;
	}
	
	public void setQuantidadeConstraints(int quantidadeConstraints) {
		this.quantidadeConstraints = quantidadeConstraints;
	}
	
	public boolean isVersaoAcademica() {
		return versaoAcademica;
	}
	
	public void setVersaoAcademica(boolean versaoAcademica) {
		this.versaoAcademica = versaoAcademica;
	}
	
	public Integer getTamanhoMaximoUpload() {
		return tamanhoMaximoUpload;
	}
	
	public void setTamanhoMaximoUpload(Integer tamanhoMaximoUpload) {
		this.tamanhoMaximoUpload = tamanhoMaximoUpload;
	}

	public Integer getModulosPermitidosSomatorio() {
		return modulosPermitidosSomatorio;
	}
	
	public void setModulosPermitidosSomatorio(Integer modulosPermitidosSomatorio) {
		this.modulosPermitidosSomatorio = modulosPermitidosSomatorio;
	}
	
	public String getCamposColaboradorVisivel() {
		return camposColaboradorVisivel;
	}
	
	public void setCamposColaboradorVisivel(String camposColaboradorVisivel) {
		this.camposColaboradorVisivel = camposColaboradorVisivel;
	}
	
	public String getCamposColaboradorObrigatorio() {
		return camposColaboradorObrigatorio;
	}
	
	public void setCamposColaboradorObrigatorio(String camposColaboradorObrigatorio) {
		this.camposColaboradorObrigatorio = camposColaboradorObrigatorio;
	}
	public String getCamposColaboradorTabs() {
		return camposColaboradorTabs;
	}
	
	public void setCamposColaboradorTabs(String camposColaboradorTabs) {
		this.camposColaboradorTabs = camposColaboradorTabs;
	}
	
	public String getCamposCandidatoExternoVisivel() {
		return camposCandidatoExternoVisivel;
	}
	
	public void setCamposCandidatoExternoVisivel(
			String camposCandidatoExternoVisivel) {
		this.camposCandidatoExternoVisivel = camposCandidatoExternoVisivel;
	}
	
	public String getCamposCandidatoExternoObrigatorio() {
		return camposCandidatoExternoObrigatorio;
	}
	
	public void setCamposCandidatoExternoObrigatorio(
			String camposCandidatoExternoObrigatorio) {
		this.camposCandidatoExternoObrigatorio = camposCandidatoExternoObrigatorio;
	}
	
	public String getCamposCandidatoExternoTabs() {
		return camposCandidatoExternoTabs;
	}
	
	public void setCamposCandidatoExternoTabs(String camposCandidatoExternoTabs) {
		this.camposCandidatoExternoTabs = camposCandidatoExternoTabs;
	}
	public boolean isAutorizacaoGestorNaSolicitacaoPessoal() {
		return autorizacaoGestorNaSolicitacaoPessoal;
	}
	public void setAutorizacaoGestorNaSolicitacaoPessoal(boolean autorizacaoGestorNaSolicitacaoPessoal) {
		this.autorizacaoGestorNaSolicitacaoPessoal = autorizacaoGestorNaSolicitacaoPessoal;
	}
}