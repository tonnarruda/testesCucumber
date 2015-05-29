package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.util.MathUtil;
import com.fortes.rh.util.SalarioUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="faixasalarialhistorico_sequence", allocationSize=1)
public class FaixaSalarialHistorico extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private Indice indice;
	@ManyToOne
	private ReajusteFaixaSalarial reajusteFaixaSalarial;
	@Temporal(TemporalType.DATE)
    private Date data;
	private Integer tipo;
	private Double valor;
	private Double quantidade;
	private Integer status;

	@Transient
	private String obsReajuste;

	public FaixaSalarialHistorico()
	{
	}

	public FaixaSalarialHistorico(Long id, Date data, Integer status, Integer tipo, Double valor, Double quantidade, Long indiceId, String indiceNome, Double indiceHistoricoValor, Date indiceHistoricoData, Double indiceHistoricoValorAtual, String faixaSalarialCodigoAC)
	{
		this.setId(id);
		this.setData(data);
		this.setStatus(status);
		this.setTipo(tipo);
		this.setValor(valor);
		this.setQuantidade(quantidade);
		this.setProjectionIndiceId(indiceId);
		this.setProjectionIndiceHistoricoValor(indiceHistoricoValor);
		this.setProjectionIndiceHistoricoValorAtual(indiceHistoricoValorAtual);
		this.setProjectionIndiceHistoricoData(indiceHistoricoData);
		this.setProjectionHistoricoFaixaSalarial(this);
		this.setProjectionIndiceNome(indiceNome);
		this.setProjectionFaixaSalarialCodigoAC(faixaSalarialCodigoAC);
	}

	public FaixaSalarialHistorico(Long id, Date data, Integer status, Integer tipo, Double valor, Double quantidade, Long indiceId, String indiceNome, Double indiceHistoricoValor, Date indiceHistoricoData)
	{
		this.setId(id);
		this.setData(data);
		this.setStatus(status);
		this.setTipo(tipo);
		this.setValor(valor);
		this.setQuantidade(quantidade);
		this.setProjectionIndiceId(indiceId);
		this.setProjectionIndiceHistoricoValor(indiceHistoricoValor);
		this.setProjectionIndiceHistoricoData(indiceHistoricoData);
		this.setProjectionHistoricoFaixaSalarial(this);
		this.setProjectionIndiceNome(indiceNome);
	}

	public FaixaSalarialHistorico(Long id, Date data, Integer tipo, Double valor, Double quantidade, Long indiceId, String indiceNome,
			Double indiceHistoricoValor, Date indiceHistoricoData, Long cargoId, String cargoNome, Long faixaSalarialId, String faixaSalarialNome)
	{
		this.setId(id);
		this.setData(data);
		this.setTipo(tipo);
		this.setValor(valor);
		this.setQuantidade(quantidade);
		this.setProjectionIndiceId(indiceId);
		this.setProjectionIndiceNome(indiceNome);
		this.setProjectionIndiceIndiceHistoricoValor(indiceHistoricoValor);
		this.setProjectionIndiceIndiceHistoricoData(indiceHistoricoData);
		this.setProjectionHistoricoFaixaSalarial(this);
		this.setProjectionCargoId(cargoId);
		this.setProjectionCargoNome(cargoNome);
		this.setProjectionFaixaSalarialId(faixaSalarialId);
		this.setProjectionFaixaSalarialNome(faixaSalarialNome);
	}

	//findHistoricoAtual
	public FaixaSalarialHistorico(Long id, Date data, Integer tipo, Double valor, Double quantidade, Long indiceId, Long faixaSalarialId)
	{
		this.setId(id);
		this.setData(data);
		this.setTipo(tipo);
		this.setValor(valor);
		this.setQuantidade(quantidade);
		this.setProjectionIndiceId(indiceId);
		this.setProjectionHistoricoFaixaSalarial(this);
		this.setProjectionFaixaSalarialId(faixaSalarialId);
	}

	//Projections
	public void setProjectionHistoricoFaixaSalarial(FaixaSalarialHistorico projectionHistoricoFaixaSalarial)
	{
		iniciaFaixaSalarial();

		this.getFaixaSalarial().setFaixaSalarialHistoricoAtual(projectionHistoricoFaixaSalarial);
	}

	public void setProjectionIndiceHistoricoValor(Double projectionIndiceHistoricoValor)
	{
		if(this.indice == null)
			this.indice = new Indice();
		if(this.indice.getIndiceHistoricoAtual() == null)
			this.indice.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indice.getIndiceHistoricoAtual().setValor(projectionIndiceHistoricoValor);
	}

	public void setProjectionIndiceHistoricoValorAtual(Double projectionIndiceHistoricoValorAtual)
	{
		if(this.indice == null)
			this.indice = new Indice();
		if(this.indice.getIndiceHistoricoAtual() == null)
			this.indice.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indice.getIndiceHistoricoAtual().setValorAtual((projectionIndiceHistoricoValorAtual));
	}

	public void setProjectionIndiceHistoricoData(Date projectionIndiceHistoricoData)
	{
		if(this.indice == null)
			this.indice = new Indice();
		if(this.indice.getIndiceHistoricoAtual() == null)
			this.indice.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indice.getIndiceHistoricoAtual().setData(projectionIndiceHistoricoData);
	}

	public void setProjectionIndiceIndiceHistoricoValor(Double projectionIndiceHistoricoValor)
	{
		if(this.indice == null)
			this.indice = new Indice();

		if(this.indice.getIndiceHistoricoAtual() == null)
			this.indice.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indice.getIndiceHistoricoAtual().setValor(projectionIndiceHistoricoValor);
	}

	public void setProjectionIndiceIndiceHistoricoData(Date projectionIndiceHistoricoData)
	{
		if(this.indice == null)
			this.indice = new Indice();

		if(this.indice.getIndiceHistoricoAtual() == null)
			this.indice.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indice.getIndiceHistoricoAtual().setData(projectionIndiceHistoricoData);
	}

	public void setProjectionFaixaSalarialId(Long projectionFaixaSalarialId)
    {
    	iniciaFaixaSalarial();
    	this.faixaSalarial.setId(projectionFaixaSalarialId);
    }

	public void setProjectionFaixaSalarialNome(String projectionFaixaSalarialNome)
    {
    	iniciaFaixaSalarial();
    	this.faixaSalarial.setNome(projectionFaixaSalarialNome);
    }
	
	public void setProjectionFaixaSalarialNomeACPessoal(String projectionFaixaSalarialNomeACPessoal)
	{
		iniciaFaixaSalarial();
		this.faixaSalarial.setNomeACPessoal(projectionFaixaSalarialNomeACPessoal);
	}

	public void setProjectionFaixaSalarialCodigoAC(String projectionFaixaSalarialCodigoAC)
	{
		iniciaFaixaSalarial();
		this.faixaSalarial.setCodigoAC(projectionFaixaSalarialCodigoAC);
	}

	private void iniciaFaixaSalarial() 
	{
		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
	}

    public void setProjectionIndiceId(Long projectionIndiceId)
    {
    	if(this.indice == null)
    		this.indice = new Indice();

    	this.indice.setId(projectionIndiceId);
    }

    public void setProjectionIndiceNome(String projectionIndiceNome)
    {
    	if(this.indice == null)
    		this.indice = new Indice();

    	this.indice.setNome(projectionIndiceNome);
    }

    public void setProjectionCargoId(Long projectionCargoId)
    {
    	iniciaFaixaSalarial();

    	if(this.faixaSalarial.getCargo() == null)
    		this.faixaSalarial.setCargo(new Cargo());

    	this.faixaSalarial.getCargo().setId(projectionCargoId);
    }

    public void setProjectionCargoNome(String projectionCargoNome)
    {
    	iniciaFaixaSalarial();

    	if(this.faixaSalarial.getCargo() == null)
    		this.faixaSalarial.setCargo(new Cargo());

    	this.faixaSalarial.getCargo().setNome(projectionCargoNome);
    }

    public Object clone()
	{
		try
		{
			FaixaSalarialHistorico clone = (FaixaSalarialHistorico) super.clone();

			if(this.indice != null)
				clone.setIndice((Indice) this.indice.clone());
			else
				clone.setIndice(new Indice());

			if(this.indice != null && this.indice.getIndiceHistoricoAtual() != null)
				clone.getIndice().setIndiceHistoricoAtual((IndiceHistorico) this.indice.getIndiceHistoricoAtual().clone());

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

    public String getTipoSalarioDescricao()
    {
    	if (this.tipo != null)
    		return TipoAplicacaoIndice.getDescricao(this.tipo);
    	return "";
    }

    @NaoAudita
    public String getDescricaoIndice()
    {
    	if(this.tipo == TipoAplicacaoIndice.INDICE) {
    		String nomeDoIndice = this.getNomeDoIndice();
    		Double valorDoIndice = getValorDoIndice();

    		return this.quantidade + "x " + nomeDoIndice + " (" + MathUtil.formataValor(valorDoIndice) + ")";
    	}
    	else
    		return "";
    }
    @NaoAudita
	private Double getValorDoIndice() {
		if (this.indice != null && indice.getValorDoHistoricoAtual() != null)
			return this.indice.getValorDoHistoricoAtual();
		return new Double(0);
	}
    
    @NaoAudita
	private String getNomeDoIndice() {
		if (this.indice != null)
			return this.indice.getNome();
		return "";
	}

	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public FaixaSalarial getFaixaSalarial()
	{
		return faixaSalarial;
	}
	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}
	public Double getQuantidade()
	{
		return quantidade;
	}
	public void setQuantidade(Double quantidade)
	{
		this.quantidade = quantidade;
	}
	public Integer getTipo()
	{
		return tipo;
	}
	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}
	public Double getValor()
	{
		if (this.getTipo() == null)
			return null;
		return SalarioUtil.getValor(this.getTipo(), this.getFaixaSalarial(), this.getIndice(), this.quantidade, this.valor);
	}
	/**
	 * Retorna o valor sem utilização da classe SalárioUtil.
	 */
	public Double getValorReal()
	{
		return this.valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public Indice getIndice()
	{
		return indice;
	}
	public void setIndice(Indice indice)
	{
		this.indice = indice;
	}

	public String getDescricao()
	{
		String nomeDoCargo = this.getNomeDoCargoDaFaixaSalarial();
		String nomeDaFaixa = this.getNomeDaFaixaSalarial();
		
		return nomeDoCargo + " " + nomeDaFaixa;
	}

	public String getObsReajuste()
	{
		return obsReajuste;
	}

	public void setObsReajuste(String obsReajuste)
	{
		this.obsReajuste = obsReajuste;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}
	@NaoAudita
	public String getNomeDoCargoDaFaixaSalarial() {
		FaixaSalarial faixa = this.getFaixaSalarial();
		if (faixa != null)
			return faixa.getNomeDoCargo();
		return "";
	}
	@NaoAudita
	private String getNomeDaFaixaSalarial() {
		FaixaSalarial faixa = this.getFaixaSalarial();
		if (faixa != null)
			return faixa.getNome();
		return "";
	}

	public ReajusteFaixaSalarial getReajusteFaixaSalarial() {
		return reajusteFaixaSalarial;
	}

	public void setReajusteFaixaSalarial(ReajusteFaixaSalarial reajusteFaixaSalarial) {
		this.reajusteFaixaSalarial = reajusteFaixaSalarial;
	}
}