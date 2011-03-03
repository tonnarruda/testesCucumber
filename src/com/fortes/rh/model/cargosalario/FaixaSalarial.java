/* Autor: Igo Coelho
 * Data:19/09/2006 */
package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.desenvolvimento.Certificacao;

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

	@Transient
	private String descricao;
	@Transient
	private FaixaSalarialHistorico faixaSalarialHistoricoAtual;

	@OneToMany(mappedBy="faixaSalarial",fetch=FetchType.LAZY)
	private Collection<FaixaSalarialHistorico> faixaSalarialHistoricos;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Certificacao> certificacaos;

	@Transient
	private String empresaCodigoAC;
	@Transient
	private String empresaGrupoAC;

	public FaixaSalarial()
	{
	}

	public FaixaSalarial(Long id, Long historicoFaixaId, Date historicoFaixaData, Integer historicoFaixaTipo, Double historicoFaixaValor,
		   Double historicoFaixaQuantidade, Long indiceId, Double historicoIndiceValor, Date historicoIndiceData)
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

	public FaixaSalarial (Long id, String nome, String nomeCargo){
		setId(id);
		setNome(nome);
		setNomeCargo(nomeCargo);
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
		if(this.cargo == null)
			this.cargo = new Cargo();
		this.cargo.setId(cargoId);
	}

	public void setNomeCargo(String nomeCargo)
	{
		if(this.cargo == null)
			this.cargo = new Cargo();
		this.cargo.setNome(nomeCargo);
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
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
	
}