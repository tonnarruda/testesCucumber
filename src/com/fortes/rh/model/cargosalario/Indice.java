package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;
import com.fortes.security.auditoria.NaoAudita;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="indice_sequence", allocationSize=1)
public class Indice extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=40)
	private String nome;
	@Column(length=12)
	private String codigoAC;
	@OneToMany(mappedBy="indice",fetch=FetchType.LAZY)
	private Collection<IndiceHistorico> indiceHistoricos;
	@OneToMany(mappedBy="indice",fetch=FetchType.LAZY)
	private Collection<ReajusteIndice> reajusteIndices;
	@Column(length=3)
    private String grupoAC;

	@Transient
	private IndiceHistorico indiceHistoricoAtual;

    public Indice()
	{
	}

    public Indice(Long id, String nome, Long indiceHistoricoId, Date indiceHistoricoData, Double indiceHistoricoValor)
    {
    	this.setId(id);
    	this.setNome(nome);
    	this.setProjectionIndiceHistoricoId(indiceHistoricoId);
    	this.setProjectionIndiceHistoricoData(indiceHistoricoData);
    	this.setProjectionIndiceHistoricoValor(indiceHistoricoValor);
    }

	private void setProjectionIndiceHistoricoValor(Double indiceHistoricoValor)
	{
		if(this.indiceHistoricoAtual == null)
			this.setIndiceHistoricoAtual(new IndiceHistorico());

		this.getIndiceHistoricoAtual().setValor(indiceHistoricoValor);
	}

	private void setProjectionIndiceHistoricoId(Long indiceHistoricoId)
	{
    	if(this.indiceHistoricoAtual == null)
    		this.setIndiceHistoricoAtual(new IndiceHistorico());

    	this.getIndiceHistoricoAtual().setId(indiceHistoricoId);
	}

	private void setProjectionIndiceHistoricoData(Date indiceHistoricoData)
	{
		if(this.indiceHistoricoAtual == null)
			this.setIndiceHistoricoAtual(new IndiceHistorico());

		this.getIndiceHistoricoAtual().setData(indiceHistoricoData);
	}

	public Object clone()
	{
		try
		{
			Indice clone = (Indice) super.clone();
			if(this.indiceHistoricoAtual != null)
				clone.setIndiceHistoricoAtual((IndiceHistorico) this.indiceHistoricoAtual.clone());

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public String getIdentificadorToJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("id", gson.toJsonTree(getId()));
		
		return jsonObject.toString();
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}
	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}
	@ChaveDaAuditoria
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	@NaoAudita
	public Collection<IndiceHistorico> getIndiceHistoricos()
	{
		return indiceHistoricos;
	}
	public void setIndiceHistoricos(Collection<IndiceHistorico> indiceHistoricos)
	{
		this.indiceHistoricos = indiceHistoricos;
	}
	@NaoAudita
	public IndiceHistorico getIndiceHistoricoAtual()
	{
		return indiceHistoricoAtual;
	}
	public void setIndiceHistoricoAtual(IndiceHistorico indiceHistoricoAtual)
	{
		this.indiceHistoricoAtual = indiceHistoricoAtual;
	}
	@NaoAudita
	public Double getValorDoHistoricoAtual() {
		if (getIndiceHistoricoAtual() != null)
			return this.getIndiceHistoricoAtual().getValor();
		return new Double(0);
	}

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public Collection<ReajusteIndice> getReajusteIndices() {
		return reajusteIndices;
	}

	public void setReajusteIndices(Collection<ReajusteIndice> reajusteIndices) {
		this.reajusteIndices = reajusteIndices;
	}
}