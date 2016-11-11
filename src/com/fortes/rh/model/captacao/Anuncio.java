package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.security.auditoria.NaoAudita;

@Entity
@SequenceGenerator(name = "sequence", sequenceName = "anuncio_sequence", allocationSize = 1)
public class Anuncio extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String titulo;
    
    private String cabecalho;
    
    private String informacoes;

    @OneToOne
    private Solicitacao solicitacao;

    private boolean mostraConhecimento;
    private boolean mostraBeneficio;
    private boolean mostraSalario;
    private boolean mostraCargo;
    private boolean mostraSexo;
    private boolean mostraIdade;
    private boolean exibirModuloExterno;
    
    @Temporal(TemporalType.DATE)
	private Date dataPrevisaoEncerramento;
   
    @Transient
    private CandidatoSolicitacao candidatoSolicitacao;
    @Transient
    private Integer qtdAvaliacoes;
    @Transient
    private Integer qtdAvaliacoesRespondidas;

	public Anuncio() 
	{
	}
	
	public Anuncio(Long id, String titulo, Long solicitacaoId, Integer solicitacaoQtde, Long candidatoSolicitacaoId, Integer qtdAvaliacoes, Integer qtdAvaliacoesRespondidas) 
	{
		this.setId(id);
		this.setTitulo(titulo);
		this.setProjectionSolicitacaoId(solicitacaoId);
		this.setProjectionSolicitacaoQtde(solicitacaoQtde);
		this.setProjectionCandidatoSolicitacaoId(candidatoSolicitacaoId);
		this.setQtdAvaliacoes(qtdAvaliacoes);
		this.setQtdAvaliacoesRespondidas(qtdAvaliacoesRespondidas);
	}

	public Anuncio(Long id, String titulo, Long solicitacaoId, String solicitacaoEmpresaNome, Integer solicitacaoQtde, Long candidatoSolicitacaoId, Integer qtdAvaliacoes, Integer qtdAvaliacoesRespondidas) 
	{
		this(id, titulo, solicitacaoId, solicitacaoQtde, candidatoSolicitacaoId, qtdAvaliacoes, qtdAvaliacoesRespondidas);
		this.setProjectionSolicitacaoEmpresaNome(solicitacaoEmpresaNome);
	}

	public String getCabecalho()
	{
		return cabecalho;
	}
	
	@NaoAudita
	public String getCabecalhoFormatado()
	{
		if (this.cabecalho != null )
			this.cabecalho = cabecalho.replaceAll("\n", "<br>");
			
		return cabecalho;
	}
	
	public void setProjectionSolicitacaoId(Long solicitacaoId)
	{
		if(this.solicitacao == null)
			this.solicitacao = new Solicitacao();
		
		this.solicitacao.setId(solicitacaoId);
	}
	
	private void setProjectionSolicitacaoQtde(Integer solicitacaoQtde) 
	{
		if(this.solicitacao == null)
			this.solicitacao = new Solicitacao();
		
		this.solicitacao.setQuantidade(solicitacaoQtde);
	}

	private void setProjectionSolicitacaoEmpresaNome(String solicitacaoEmpresaNome) 
	{
		if(this.solicitacao == null)
			this.solicitacao = new Solicitacao();
		
		if(this.solicitacao.getEmpresa() == null)
			this.solicitacao.setEmpresa(new Empresa());
		
		this.solicitacao.getEmpresa().setNome(solicitacaoEmpresaNome);
	}
	
	private void setProjectionCandidatoSolicitacaoId(Long candidatoSolicitacaoId) 
	{
		if(this.candidatoSolicitacao == null)
			this.candidatoSolicitacao = new CandidatoSolicitacao();
		
		this.candidatoSolicitacao.setId(candidatoSolicitacaoId);
	}
	
	public void setCabecalho(String cabecalho)
	{
		this.cabecalho = cabecalho;
	}
	public String getInformacoes()
	{
		return informacoes;
	}
	public void setInformacoes(String informacoes)
	{
		this.informacoes = informacoes;
	}
	public boolean isMostraBeneficio()
	{
		return mostraBeneficio;
	}
	public void setMostraBeneficio(boolean mostraBeneficio)
	{
		this.mostraBeneficio = mostraBeneficio;
	}
	public boolean isMostraCargo()
	{
		return mostraCargo;
	}
	public void setMostraCargo(boolean mostraCargo)
	{
		this.mostraCargo = mostraCargo;
	}
	public boolean isMostraConhecimento()
	{
		return mostraConhecimento;
	}
	public void setMostraConhecimento(boolean mostraConhecimento)
	{
		this.mostraConhecimento = mostraConhecimento;
	}
	public boolean isMostraIdade()
	{
		return mostraIdade;
	}
	public void setMostraIdade(boolean mostraIdade)
	{
		this.mostraIdade = mostraIdade;
	}
	public boolean isMostraSalario()
	{
		return mostraSalario;
	}
	public void setMostraSalario(boolean mostraSalario)
	{
		this.mostraSalario = mostraSalario;
	}
	public boolean isMostraSexo()
	{
		return mostraSexo;
	}
	public void setMostraSexo(boolean mostraSexo)
	{
		this.mostraSexo = mostraSexo;
	}
	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("mostraBeneficio", this.mostraBeneficio).append(
						"solicitacao", this.solicitacao).append("cabecalho",
						this.cabecalho).append("id", this.getId()).append(
						"mostraConhecimento", this.mostraConhecimento).append(
						"mostraCargo", this.mostraCargo).append("mostraIdade",
						this.mostraIdade).append("mostraSexo", this.mostraSexo)
				.append("mostraSalario", this.mostraSalario).append("titulo",
						this.titulo).append("informacoes", this.informacoes)
				.toString();
	}
	public boolean isExibirModuloExterno() {
		return exibirModuloExterno;
	}

	public void setExibirModuloExterno(boolean exibirModuloExterno) {
		this.exibirModuloExterno = exibirModuloExterno;
	}
	
	public Date getDataPrevisaoEncerramento() {
		return dataPrevisaoEncerramento;
	}

	public void setDataPrevisaoEncerramento(Date dataPrevisaoEncerramento) {
		this.dataPrevisaoEncerramento = dataPrevisaoEncerramento;
	}

	public CandidatoSolicitacao getCandidatoSolicitacao() {
		return candidatoSolicitacao;
	}

	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao) {
		this.candidatoSolicitacao = candidatoSolicitacao;
	}

	public Integer getQtdAvaliacoes() {
		return qtdAvaliacoes;
	}

	public void setQtdAvaliacoes(Integer qtdAvaliacoes) {
		this.qtdAvaliacoes = qtdAvaliacoes;
	}

	public Integer getQtdAvaliacoesRespondidas() {
		return qtdAvaliacoesRespondidas;
	}

	public void setQtdAvaliacoesRespondidas(Integer qtdAvaliacoesRespondidas) {
		this.qtdAvaliacoesRespondidas = qtdAvaliacoesRespondidas;
	}
}