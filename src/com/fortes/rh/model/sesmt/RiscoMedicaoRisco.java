package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="riscomedicaorisco_sequence", allocationSize=1)
public class RiscoMedicaoRisco extends AbstractModel implements Serializable
{
	
	private String descricaoPpra = "";
	
	
	private String descricaoLtcat = "";
	
	@Column(length=100)
	private String tecnicaUtilizada = "";
	
	@Column(length=20)
	private String intensidadeMedida = "";
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Risco risco;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private MedicaoRisco medicaoRisco;

	@Transient
	private boolean epcEficaz;
	@Transient
	private boolean naoAdicionar;
	@Transient
	private String medidaDeSeguranca;
	@Transient
	private Character periodicidadeExposicao;
	
	public RiscoMedicaoRisco() {
	}
	
	public RiscoMedicaoRisco(MedicaoRisco medicaoRisco2, Risco risco2) 
	{
		this.medicaoRisco = medicaoRisco2;
		this.risco = risco2;
	}
	
	public RiscoMedicaoRisco(String descricaoPpra, String descricaoLtcat, String tecnicaUtilizada, String intensidadeMedida, String riscoDescricao, String riscoGrupoRisco, Risco risco, Date medicaoData, String medidaDeSeguranca, Character periodicidadeExposicao) 
	{
		this.descricaoPpra = descricaoPpra;
		this.descricaoLtcat = descricaoLtcat;
		this.tecnicaUtilizada = tecnicaUtilizada;
		this.intensidadeMedida = intensidadeMedida;
		this.risco = risco;
		risco.setDescricao(riscoDescricao);
		risco.setGrupoRisco(riscoGrupoRisco);
		this.medicaoRisco = new MedicaoRisco();
		this.medicaoRisco.setData(medicaoData);
		this.medidaDeSeguranca = medidaDeSeguranca;
		this.periodicidadeExposicao = periodicidadeExposicao;
	}
	
	//RiscoMedicaoRisco(rm.tecnicaUtilizada, rm.intensidadeMedida, m.data)
	public RiscoMedicaoRisco(String tecnicaUtilizada, String intensidadeMedida, Long medicaoId, Date medicaoData)
	{
		this.tecnicaUtilizada = tecnicaUtilizada;
		this.intensidadeMedida = intensidadeMedida;
		this.medicaoRisco = new MedicaoRisco();
		this.medicaoRisco.setId(medicaoId);
		this.medicaoRisco.setData(medicaoData);
	}
	
	//new RiscoMedicaoRisco(rm.tecnicaUtilizada, rm.intensidadeMedida, r.descricao, r.grupoRisco, ra.epcEficaz)
	public RiscoMedicaoRisco(Long id, String tecnicaUtilizada, String intensidadeMedida, Date medicaoData, Ambiente ambiente, Risco risco, String riscoDescricao, String riscoGrupoRisco, boolean epcEficaz) 
	{
		setId(id);
		this.tecnicaUtilizada = tecnicaUtilizada;
		this.intensidadeMedida = intensidadeMedida;
		this.risco = risco;
		this.risco.setDescricao(riscoDescricao);
		this.risco.setGrupoRisco(riscoGrupoRisco);
		this.epcEficaz = epcEficaz;
		this.medicaoRisco = new MedicaoRisco();
		this.medicaoRisco.setData(medicaoData);
		this.medicaoRisco.setAmbiente(ambiente);
	}

	public RiscoMedicaoRisco(Long riscoId, String riscoDescricao, String intensidadeMedida , String tecnicaUtilizada, String descricaoPpra, String descricaoLtcat ) 
	{
		if (risco == null)
			risco = new Risco();
		
		risco.setId(riscoId);
		risco.setDescricao(riscoDescricao);
		
		this.intensidadeMedida = intensidadeMedida;
		this.tecnicaUtilizada = tecnicaUtilizada;
		this.descricaoPpra = descricaoPpra;
		this.descricaoLtcat = descricaoLtcat;
	}
	
	public String getDescricaoPpra() {
		return descricaoPpra;
	}

	public void setDescricaoPpra(String descricaoPpra) {
		this.descricaoPpra = descricaoPpra;
	}

	public String getDescricaoLtcat() {
		return descricaoLtcat;
	}

	public void setDescricaoLtcat(String descricaoLtcat) {
		this.descricaoLtcat = descricaoLtcat;
	}

	public String getTecnicaUtilizada() {
		return tecnicaUtilizada;
	}

	public void setTecnicaUtilizada(String tecnicaUtilizada) {
		this.tecnicaUtilizada = tecnicaUtilizada;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	public MedicaoRisco getMedicaoRisco() {
		return medicaoRisco;
	}

	public void setMedicaoRisco(MedicaoRisco medicaoRisco) {
		this.medicaoRisco = medicaoRisco;
	}

	public String getIntensidadeMedida() {
		return intensidadeMedida;
	}

	public void setIntensidadeMedida(String intensidadeMedida) {
		this.intensidadeMedida = intensidadeMedida;
	}

	/**
	 * atributo <b>Transiente</b>
	 */
	public boolean isEpcEficaz() {
		return epcEficaz;
	}

	public void setEpcEficaz(boolean epcEficaz) {
		this.epcEficaz = epcEficaz;
	}

	public boolean isNaoAdicionar() {
		return naoAdicionar;
	}

	public void setNaoAdicionar(boolean naoAdicionar) {
		this.naoAdicionar = naoAdicionar;
	}

	public String getMedidaDeSeguranca()
	{
		return medidaDeSeguranca;
	}

	public void setMedidaDeSeguranca(String medidaDeSeguranca)
	{
		this.medidaDeSeguranca = medidaDeSeguranca;
	}

	public String getPeriodicidadeExposicao() {
		Map<Character, String> periodicidades = new HashMap<Character, String>();
		periodicidades.put('C',"Contínua");
		periodicidades.put('I',"Intermitente");
		periodicidades.put('E',"Eventual");
		
		return periodicidades.get(periodicidadeExposicao);
	}

	public void setPeriodicidadeExposicao(Character periodicidadeExposicao) {
		this.periodicidadeExposicao = periodicidadeExposicao;
	}
}
