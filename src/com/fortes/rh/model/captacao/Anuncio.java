package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.NaoAudita;

@Entity
@SequenceGenerator(name = "sequence", sequenceName = "anuncio_sequence", allocationSize = 1)
public class Anuncio extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String titulo;
    @Lob
    private String cabecalho;
    @Lob
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
    private boolean responderAvaliacaoModuloExterno;
   
    @Transient
    private boolean candidatoCadastrado;

	public void setProjectionSolicitacaoId(Long projectionSolicitacaoId)
	{
		if(this.solicitacao == null)
			this.solicitacao = new Solicitacao();
		
		this.solicitacao.setId(projectionSolicitacaoId);
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

	public boolean isCandidatoCadastrado() {
		return candidatoCadastrado;
	}

	public void setCandidatoCadastrado(boolean candidatoCadastrado) {
		this.candidatoCadastrado = candidatoCadastrado;
	}

	public boolean isResponderAvaliacaoModuloExterno() {
		return responderAvaliacaoModuloExterno;
	}

	public void setResponderAvaliacaoModuloExterno(boolean responderAvaliacaoModuloExterno) {
		this.responderAvaliacaoModuloExterno = responderAvaliacaoModuloExterno;
	}
}