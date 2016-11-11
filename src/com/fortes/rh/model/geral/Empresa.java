/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
import com.fortes.rh.model.dicionario.FormulaTurnover;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.ChaveDaAuditoria;
import com.fortes.security.auditoria.NaoAudita;

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
	@Column(length=20)
	private String cnae2;
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
	@Column(length=9)
	private String telefone;
	@Column(length=2)
	private String ddd;
	
	private boolean solPessoalExibirSalario;
	private boolean solPessoalExibirColabSubstituido;
	private boolean solPessoalObrigarDadosComplementares;
	private boolean solPessoalReabrirSolicitacao;
	
	private boolean exibirSalario;
    private boolean acIntegra;
    private boolean obrigarAmbienteFuncao;
    private boolean codigoTruCurso; 
    private boolean solicitarConfirmacaoDesligamento;
    private boolean criarUsuarioAutomaticamente;
    
    private char verificaParentesco = 'N';
    @Column(length=400)
	private String mensagemModuloExterno;
    private int maxCandidataCargo;
    @Column(length=200)
    private String logoUrl;
    @Column(length=200)
    private String atividade;
    @Column(length=200)
    private String logoCertificadoUrl;

    @OneToMany(mappedBy="empresa", cascade=CascadeType.ALL)
    private Collection<Cartao> cartoes;
    
    @Column(length=3)
    private String grupoAC;
    @Column(length=30)
	private String senhaPadrao;
   
    @OneToMany(mappedBy="empresa")
	private Collection<UsuarioEmpresa> usuarioEmpresas;
    @ManyToOne
	private Estado uf;
	@ManyToOne
	private Cidade cidade;
	private boolean campoExtraColaborador;
	private boolean campoExtraCandidato;
	private boolean exibirLogoEmpresaPpraLtcat;
	
    private String mailNaoAptos;
    
    private boolean turnoverPorSolicitacao;
    private int formulaTurnover;
    
    private char controlaRiscoPor = 'A'; // A - Ambiente ; F - Função
    
    private int controlarVencimentoCertificacaoPor;

    @ManyToMany(mappedBy="empresasParticipantes")
    private Collection<Curso> cursos;

    private boolean considerarSabadoNoAbsenteismo;
    private boolean considerarDomingoNoAbsenteismo;
    private boolean processoExportacaoAC;
    private boolean mostrarPerformanceAvalDesempenho;
    private boolean notificarSomentePeriodosConfigurados;
    
    
	private String procedimentoEmCasoDeAcidente;
	
	private String termoDeResponsabilidade;
	
	@OneToMany(mappedBy="empresa")
	private Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> configuracaoCamposExtrasVisiveisObrigadotorios;
    
	//projection
	public void setProjectionCidadeNome(String cidadeNome)
	{
		if (cidade == null)
			cidade = new Cidade();

		cidade.setNome(cidadeNome);
	}

	public Empresa() {
		super();
	}
	
	public Empresa(Long id) {
		super();
		setId(id);
	}

	public void setProjectionUfSigla(String ufSigla)
	{
		if (uf == null)
			uf = new Estado();
		
		uf.setSigla(ufSigla);
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
	
	public String getEnderecoCidadeUf()
	{
		String end = endereco; 
		if (this.cidade != null)
			end += " - " + this.cidade.getNome();
		if (this.uf != null)
			end += " - " + this.uf.getSigla();
			
		return  end;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}
	
	public String getNomeCodigoAc()
	{
		String nome = this.nome; 
		if (this.codigoAC != null)
			nome += " - " + this.codigoAC.trim();
		
		return  nome;
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

	public String[] getArrayEmailRespRH()
	{
		return emailRespRH.split(";");
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

	public String getMailNaoAptos() {
		return mailNaoAptos;
	}

	public void setMailNaoAptos(String mailNaoAptos) {
		this.mailNaoAptos = mailNaoAptos;
	}

	public String getEmailRespLimiteContrato() {
		return emailRespLimiteContrato;
	}

	public void setEmailRespLimiteContrato(String emailRespLimiteContrato) {
		this.emailRespLimiteContrato = emailRespLimiteContrato;
	}

	public boolean isTurnoverPorSolicitacao() {
		return turnoverPorSolicitacao;
	}

	public void setTurnoverPorSolicitacao(boolean turnoverPorSolicitacao) {
		this.turnoverPorSolicitacao = turnoverPorSolicitacao;
	}

	public boolean isObrigarAmbienteFuncao() {
		return obrigarAmbienteFuncao;
	}

	public void setObrigarAmbienteFuncao(boolean obrigarAmbienteFuncao) {
		this.obrigarAmbienteFuncao= obrigarAmbienteFuncao;
	}

	public char getVerificaParentesco() {
		return verificaParentesco;
	}

	public void setVerificaParentesco(char verificaParentesco) {
		this.verificaParentesco = verificaParentesco;
	}

	public char getControlaRiscoPor() {
		return controlaRiscoPor;
	}

	public void setControlaRiscoPor(char controlaRiscoPor) {
		this.controlaRiscoPor = controlaRiscoPor;
	}

	public boolean isControlarVencimentoPorCertificacao()
	{
		return controlarVencimentoCertificacaoPor == FiltroControleVencimentoCertificacao.CERTIFICACAO.getOpcao();
	}
	
	public boolean isControlarVencimentoPorCurso()
	{
		return controlarVencimentoCertificacaoPor == FiltroControleVencimentoCertificacao.CURSO.getOpcao();
	}

	public int getControlarVencimentoCertificacaoPor() {
		return controlarVencimentoCertificacaoPor;
	}
	
	public void setControlarVencimentoCertificacaoPor(int controlarVencimentoCertificacaoPor) {
		this.controlarVencimentoCertificacaoPor = controlarVencimentoCertificacaoPor;
	}

	public boolean isCodigoTruCurso() {
		return codigoTruCurso;
	}

	public void setCodigoTruCurso(boolean codigoTruCurso) {
		this.codigoTruCurso = codigoTruCurso;
	}

	public Collection<UsuarioEmpresa> getUsuarioEmpresas() {
		return usuarioEmpresas;
	}

	public void setUsuarioEmpresas(Collection<UsuarioEmpresa> usuarioEmpresas) {
		this.usuarioEmpresas = usuarioEmpresas;
	}
	
	public boolean isExibirLogoEmpresaPpraLtcat()
	{
		return exibirLogoEmpresaPpraLtcat;
	}
	
	public void setExibirLogoEmpresaPpraLtcat(boolean exibirLogoEmpresaPpraLtcat)
	{
		this.exibirLogoEmpresaPpraLtcat = exibirLogoEmpresaPpraLtcat;
	}

	public Collection<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos) {
		this.cursos = cursos;
	}

	public boolean isSolPessoalExibirSalario() {
		return solPessoalExibirSalario;
	}

	public void setSolPessoalExibirSalario(boolean solPessoalExibirSalario) {
		this.solPessoalExibirSalario = solPessoalExibirSalario;
	}

	public boolean isSolPessoalExibirColabSubstituido() {
		return solPessoalExibirColabSubstituido;
	}

	public void setSolPessoalExibirColabSubstituido(
			boolean solPessoalExibirColabSubstituido) {
		this.solPessoalExibirColabSubstituido = solPessoalExibirColabSubstituido;
	}

	public boolean isSolPessoalObrigarDadosComplementares() {
		return solPessoalObrigarDadosComplementares;
	}

	public void setSolPessoalObrigarDadosComplementares(
			boolean solPessoalObrigarDadosComplementares) {
		this.solPessoalObrigarDadosComplementares = solPessoalObrigarDadosComplementares;
	}

	public boolean isSolPessoalReabrirSolicitacao() {
		return solPessoalReabrirSolicitacao;
	}

	public void setSolPessoalReabrirSolicitacao(boolean solPessoalReabrirSolicitacao) {
		this.solPessoalReabrirSolicitacao = solPessoalReabrirSolicitacao;
	}

	public int getFormulaTurnover() {
		return formulaTurnover;
	}

	public void setFormulaTurnover(int formulaTurnover) {
		this.formulaTurnover = formulaTurnover;
	}
	
	public String getFormulaTurnoverDescricao() {
		FormulaTurnover formula = new FormulaTurnover();
		if (!formula.containsKey(formulaTurnover))
			return "";
		
		return formula.get(formulaTurnover);
	}

	public boolean isSolicitarConfirmacaoDesligamento() {
		return solicitarConfirmacaoDesligamento;
	}

	public void setSolicitarConfirmacaoDesligamento(boolean solicitarConfirmacaoDesligamento) {
		this.solicitarConfirmacaoDesligamento = solicitarConfirmacaoDesligamento;
	}
		
	public String getCnae2() {
		return cnae2;
	}

	public void setCnae2(String cnae2) {
		this.cnae2 = cnae2;
	}

	public boolean isConsiderarSabadoNoAbsenteismo() {
		return considerarSabadoNoAbsenteismo;
	}

	public void setConsiderarSabadoNoAbsenteismo(boolean considerarSabadoNoAbsenteismo) {
		this.considerarSabadoNoAbsenteismo = considerarSabadoNoAbsenteismo;
	}

	public boolean isConsiderarDomingoNoAbsenteismo() {
		return considerarDomingoNoAbsenteismo;
	}

	public void setConsiderarDomingoNoAbsenteismo(boolean considerarDomingoNoAbsenteismo) {
		this.considerarDomingoNoAbsenteismo = considerarDomingoNoAbsenteismo;
	}

	public boolean isProcessoExportacaoAC() {
		return processoExportacaoAC;
	}

	public void setProcessoExportacaoAC(boolean processoExportacaoAC) {
		this.processoExportacaoAC = processoExportacaoAC;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
	@NaoAudita
	public String getDDDTelefoneFormatado()
	{
		String result = "";
		
		if (StringUtils.isNotBlank(ddd))
			result += "(" + ddd + ") ";
		
		if (StringUtils.isNotBlank(telefone))
			result += StringUtil.criarMascaraTelefone(telefone);

		return result;
	} 

	public boolean isMostrarPerformanceAvalDesempenho() {
		return mostrarPerformanceAvalDesempenho;
	}

	public void setMostrarPerformanceAvalDesempenho(boolean mostrarPerformanceAvalDesempenho) {
		this.mostrarPerformanceAvalDesempenho = mostrarPerformanceAvalDesempenho;
	}

	public boolean isNotificarSomentePeriodosConfigurados() {
		return notificarSomentePeriodosConfigurados;
	}

	public void setNotificarSomentePeriodosConfigurados(
			boolean notificarSomentePeriodosConfigurados) {
		this.notificarSomentePeriodosConfigurados = notificarSomentePeriodosConfigurados;
	}

	public String getProcedimentoEmCasoDeAcidente() {
		return procedimentoEmCasoDeAcidente;
	}

	public void setProcedimentoEmCasoDeAcidente(String procedimentoEmCasoDeAcidente) {
		this.procedimentoEmCasoDeAcidente = procedimentoEmCasoDeAcidente;
	}

	public String getTermoDeResponsabilidade() {
		return termoDeResponsabilidade;
	}

	public void setTermoDeResponsabilidade(String termoDeResponsabilidade) {
		this.termoDeResponsabilidade = termoDeResponsabilidade;
	}

	public boolean isCriarUsuarioAutomaticamente() {
		return criarUsuarioAutomaticamente;
	}

	public void setCriarUsuarioAutomaticamente(boolean criarUsuarioAutomaticamente) {
		this.criarUsuarioAutomaticamente = criarUsuarioAutomaticamente;
	}

	public String getSenhaPadrao() {
		return (senhaPadrao == null ? "" : senhaPadrao);
	}

	public void setSenhaPadrao(String senhaPadrao) {
		this.senhaPadrao = senhaPadrao;
	}

	public Collection<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(Collection<Cartao> cartoes) {
		this.cartoes = cartoes;
	}
	
	public Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> getConfiguracaoCamposExtrasVisiveisObrigadotorios() {
		return configuracaoCamposExtrasVisiveisObrigadotorios;
	}

	public void setConfiguracaoCamposExtrasVisiveisObrigadotorios(Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> configuracaoCamposExtrasVisiveisObrigadotorios) {
		this.configuracaoCamposExtrasVisiveisObrigadotorios = configuracaoCamposExtrasVisiveisObrigadotorios;
	}
}