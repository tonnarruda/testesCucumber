/* Autor: Igo Coelho
 * Data:19/09/2006 */
package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="faixasalarial_sequence", allocationSize=1)
public class FaixaSalarial extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=30)
	private String nome;
	@Column(length=30)
	private String nomeACPessoal;
	@ManyToOne
	private Cargo cargo;
	@Column(length=12)
	private String codigoAC;

	@OneToMany(mappedBy="faixaSalarial",fetch=FetchType.LAZY)
	private Collection<FaixaSalarialHistorico> faixaSalarialHistoricos;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Certificacao> certificacaos;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="faixaSalarial")
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias;

	@Transient
	private String descricao;
	@Transient
	private int qtdColaboradores;
	@Transient
	private int qtdVagasAbertas;
	@Transient
	private int qtdContratados;
	@Transient
	private FaixaSalarialHistorico faixaSalarialHistoricoAtual;
	@Transient
	private String empresaCodigoAC;
	@Transient
	private String empresaGrupoAC;
	@Transient
	private Collection<Competencia> competencias;
	@Transient
	private String areaOrganizacionalNome;
	@Transient
	private String projectionEstabelecimentoNome;

	public FaixaSalarial()
	{
	}
	

	public FaixaSalarial(Long id, Long historicoFaixaId, Date historicoFaixaData, Integer historicoFaixaTipo, Double historicoFaixaValor, Double historicoFaixaQuantidade, Long indiceId, Double historicoIndiceValor, Date historicoIndiceData)
	{
		setId(id);
		setHistoricoFaixaId(historicoFaixaId);
		setHistoricoFaixaData(historicoFaixaData);
		setHistoricoFaixaTipo(historicoFaixaTipo);
		setHistoricoFaixaValor(historicoFaixaValor);
		setHistoricoFaixaQuantidade(historicoFaixaQuantidade);
		setIndiceId(indiceId);
		setHistoricoIndiceValor(historicoIndiceValor);
		setHistoricoIndiceData(historicoIndiceData);
	}

	public FaixaSalarial(Long id, String nome, Long cargoId, String cargoNome, Long historicoFaixaId, Date historicoFaixaData, Integer historicoFaixaTipo, Double historicoFaixaValor, Double historicoFaixaQuantidade, Long indiceId, Double historicoIndiceValor, Date historicoIndiceData)
	{
		this(id, historicoFaixaId, historicoFaixaData, historicoFaixaTipo, historicoFaixaValor, historicoFaixaQuantidade, indiceId, historicoIndiceValor, historicoIndiceData);
		setNome(nome);
		setProjectionCargoId(cargoId);
		setNomeCargo(cargoNome);
	}

	//qtdColaboradoresPorCargoFaixa
	public FaixaSalarial (Long id, String nome, String nomeCargo)
	{
		setId(id);
		setNome(nome);
		setNomeCargo(nomeCargo);
	}
	
	public FaixaSalarial (String cargoNome, String faixaNome, Integer qtdColaboradores)	{
		setCargo(new Cargo());
		getCargo().setNome(cargoNome);
		setNome(faixaNome);
		setQtdColaboradores(qtdColaboradores);
	}
	
	public FaixaSalarial (String cargoNome, String faixaNome, Integer qtdColaboradores, String areaOrganizacionalNome)	{
		setCargo(new Cargo());
		getCargo().setNome(cargoNome);
		setNome(faixaNome);
		setQtdColaboradores(qtdColaboradores);
		setAreaOrganizacionalNome(areaOrganizacionalNome);
	}
	
	public FaixaSalarial (String cargoNome, String faixaNome, String projectionEstabelecimentoNome, Integer qtdColaboradores)	{
		setCargo(new Cargo());
		getCargo().setNome(cargoNome);
		setNome(faixaNome);
		setQtdColaboradores(qtdColaboradores);
		setProjectionEstabelecimentoNome(projectionEstabelecimentoNome);
	}
	
	public FaixaSalarial (String cargoNome, String faixaNome, Integer qtdColaboradores, String projectionEstabelecimentoNome, String areaOrganizacionalNome)	{
		setCargo(new Cargo());
		getCargo().setNome(cargoNome);
		setNome(faixaNome);
		setQtdColaboradores(qtdColaboradores);
		setAreaOrganizacionalNome(areaOrganizacionalNome);
		setProjectionEstabelecimentoNome(projectionEstabelecimentoNome);
	}

	//findComHistoricoAtualByEmpresa
	public FaixaSalarial (Long cargoId, String cargoNome, Long idFaixa, String nomeFaixa, Long historicoFaixaId, Date historicoFaixaData, Double historicoFaixaValor, Integer historicoFaixaTipo )
	{
		setProjectionCargoId(cargoId);
		setNomeCargo(cargoNome);
		setId(idFaixa);
		setNome(nomeFaixa);
		setHistoricoFaixaId(historicoFaixaId);
		setHistoricoFaixaData(historicoFaixaData);
		setHistoricoFaixaValor(historicoFaixaValor);
		setHistoricoFaixaTipo(historicoFaixaTipo);
	}

	// Projections
	public void setHistoricoFaixaId(Long historicoFaixaId)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialHistoricoAtual.setId(historicoFaixaId);
	}

	public void setHistoricoFaixaData(Date historicoFaixaData)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialHistoricoAtual.setData(historicoFaixaData);
	}

	public void setHistoricoFaixaTipo(Integer historicoFaixaTipo)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(historicoFaixaTipo != null)
			this.faixaSalarialHistoricoAtual.setTipo(historicoFaixaTipo);
	}

	public void setHistoricoFaixaValor(Double historicoFaixaValor)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialHistoricoAtual.setValor(historicoFaixaValor);
	}

	public String getHistoricoFaixaValorFormatado()
	{
    	NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    	
    	return df.format( (faixaSalarialHistoricoAtual.getValorReal() == null ? 0 : faixaSalarialHistoricoAtual.getValorReal()) );
	}
	
	public void setHistoricoFaixaQuantidade(Double historicoFaixaQuantidade)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialHistoricoAtual.setQuantidade(historicoFaixaQuantidade);
	}

	public void setIndiceId(Long indiceId)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(this.faixaSalarialHistoricoAtual.getIndice() == null)
			this.faixaSalarialHistoricoAtual.setIndice(new Indice());

		this.faixaSalarialHistoricoAtual.getIndice().setId(indiceId);
	}

	public void setHistoricoIndiceId(Long historicoIndiceId)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(this.faixaSalarialHistoricoAtual.getIndice() == null)
			this.faixaSalarialHistoricoAtual.setIndice(new Indice());

		if(this.faixaSalarialHistoricoAtual.getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialHistoricoAtual.getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialHistoricoAtual.getIndice().getIndiceHistoricoAtual().setId(historicoIndiceId);
	}

	public void setHistoricoIndiceValor(Double historicoIndiceValor)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(this.faixaSalarialHistoricoAtual.getIndice() == null)
			this.faixaSalarialHistoricoAtual.setIndice(new Indice());

		if(this.faixaSalarialHistoricoAtual.getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialHistoricoAtual.getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialHistoricoAtual.getIndice().getIndiceHistoricoAtual().setValor(historicoIndiceValor);
	}

	public void setHistoricoIndiceData(Date historicoIndiceData)
	{
		if(this.faixaSalarialHistoricoAtual == null)
			this.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(this.faixaSalarialHistoricoAtual.getIndice() == null)
			this.faixaSalarialHistoricoAtual.setIndice(new Indice());

		if(this.faixaSalarialHistoricoAtual.getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialHistoricoAtual.getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialHistoricoAtual.getIndice().getIndiceHistoricoAtual().setData(historicoIndiceData);
	}

	// Fim das Projections

	public Object clone()
	{
		try
		{
			FaixaSalarial clone = (FaixaSalarial) super.clone();
			clone.setCargo(this.cargo);
			clone.setFaixaSalarialHistoricoAtual((FaixaSalarialHistorico) this.faixaSalarialHistoricoAtual.clone());

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}
	public void setProjectionCargoId(Long cargoId)
	{
		iniciaCargo();
		this.cargo.setId(cargoId);
	}

	public void setNomeCargo(String nomeCargo)
	{
		iniciaCargo();
		this.cargo.setNome(nomeCargo);
	}
	
	public void setAtivoCargo(boolean ativoCargo)
	{
		iniciaCargo();
		this.cargo.setAtivo(ativoCargo);
	}

	private void iniciaCargo() 
	{
		if(this.cargo == null) this.cargo = new Cargo();
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = StringUtils.defaultIfEmpty(codigoAC, null);
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public String getDescricao()
	{
		descricao = this.cargo.getNome() + " " + this.nome;
		return descricao;
	}
	
	public String getDescricaoComStatus()
	{
		descricao = this.cargo.getNome() + " " + this.nome + (this.cargo.isAtivo() ? " (Ativo)" : " (Inativo)");
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("nome", this.nome)
			.append("cargo", this.cargo)
			.append("id", this.getId())
			.append("descricao", this.descricao)
			.append("codigoAC",this.codigoAC)
			.toString();
	}

	public Collection<FaixaSalarialHistorico> getFaixaSalarialHistoricos()
	{
		return faixaSalarialHistoricos;
	}

	public void setFaixaSalarialHistoricos(Collection<FaixaSalarialHistorico> faixaSalarialHistoricos)
	{
		this.faixaSalarialHistoricos = faixaSalarialHistoricos;
	}

	public FaixaSalarialHistorico getFaixaSalarialHistoricoAtual()
	{
		return faixaSalarialHistoricoAtual;
	}

	public void setFaixaSalarialHistoricoAtual(FaixaSalarialHistorico faixaSalarialHistoricoAtual)
	{
		this.faixaSalarialHistoricoAtual = faixaSalarialHistoricoAtual;
	}

	public Collection<Certificacao> getCertificacaos()
	{
		return certificacaos;
	}

	public void setCertificacaos(Collection<Certificacao> certificacaos)
	{
		this.certificacaos = certificacaos;
	}
	
	public String getNomeDoCargo() {
		if (this.cargo != null && this.cargo.getNome() != null)
			return this.cargo.getNome();
		return "";
	}
	
	public String getNomesDeCargoEFaixa()
	{
		String nomesDeCargoEFaixa = "";
		String nomeCargo = getCargo().getNome();
		String nomeFaixa = getNome();

		if (this.cargo != null && this.cargo.getNome() != null && this.getNome() != null)
			nomesDeCargoEFaixa = nomeCargo + "/" + nomeFaixa;

		return nomesDeCargoEFaixa;
	}
	
	public String getNomeDeCargoEFaixa()
	{
		String nomesDeCargoEFaixa = "";
		String nomeCargo = getCargo().getNome();
		String nomeFaixa = getNome();
		
		if (this.cargo != null && this.cargo.getNome() != null && this.getNome() != null)
			nomesDeCargoEFaixa = nomeCargo + " " + nomeFaixa;
		
		return nomesDeCargoEFaixa;
	}


	public String getNomeACPessoal()
	{
		return nomeACPessoal;
	}

	public void setNomeACPessoal(String nomeACPessoal)
	{
		this.nomeACPessoal = nomeACPessoal;
	}

	public String getEmpresaCodigoAC() {
		return empresaCodigoAC;
	}

	public void setEmpresaCodigoAC(String empresaCodigoAC) {
		this.empresaCodigoAC = empresaCodigoAC;
	}

	public String getEmpresaGrupoAC() {
		return empresaGrupoAC;
	}

	public void setEmpresaGrupoAC(String empresaGrupoAC) {
		this.empresaGrupoAC = empresaGrupoAC;
	}

	public int getQtdVagasAbertas() {
		return qtdVagasAbertas;
	}

	public void setQtdVagasAbertas(int qtdVagasAbertas) {
		this.qtdVagasAbertas = qtdVagasAbertas;
	}

	public int getQtdContratados() {
		return qtdContratados;
	}

	public void setQtdContratados(int qtdContratados) {
		this.qtdContratados = qtdContratados;
	}

	public void setEmpresaNome(String empresaNome) {
		
		iniciaCargo();
		if(this.cargo.getEmpresa() == null)
			this.cargo.setEmpresa(new Empresa());
		
		this.cargo.getEmpresa().setNome(empresaNome);
	}

	public Collection<Competencia> getCompetencias()
	{
		return competencias;
	}

	public void setCompetencias(Collection<Competencia> competencias)
	{
		this.competencias = competencias;
	}

	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias() {
		return configuracaoNivelCompetencias;
	}

	public void setConfiguracaoNivelCompetencias(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias) 
	{
		this.configuracaoNivelCompetencias = configuracaoNivelCompetencias;
	}

	public int getQtdColaboradores() 
	{
		return qtdColaboradores;
	}

	public void setQtdColaboradores(int qtdColaboradores) 
	{
		this.qtdColaboradores = qtdColaboradores;
	}


	public String getAreaOrganizacionalNome() {
		return areaOrganizacionalNome;
	}


	public void setAreaOrganizacionalNome(String areaOrganizacionalNome) {
		this.areaOrganizacionalNome = areaOrganizacionalNome;
	}


	public String getProjectionEstabelecimentoNome() {
		return projectionEstabelecimentoNome;
	}


	public void setProjectionEstabelecimentoNome(
			String projectionEstabelecimentoNome) {
		this.projectionEstabelecimentoNome = projectionEstabelecimentoNome;
	}

}