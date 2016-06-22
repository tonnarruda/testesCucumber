package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="ordemDeServico_sequence", allocationSize=1)
public class OrdemDeServico extends AbstractModel implements Serializable
{
	@ManyToOne(fetch=FetchType.LAZY)
	private Colaborador colaborador;
	
	private String nomeColaborador;
	
	private Date dataAdmisaoColaborador;
	
	private String codigoCBO;
	
	private String nomeFuncao;
	
	private String nomeEmpresa;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private Double revisao; 
	
	@Lob
	private String atividades;
	@Lob
	private String riscos;
	@Lob
	private String epis;
	@Lob
	private String medidasPreventivas;
	@Lob
	private String treinamentos;
	@Lob
	private String normasInternas;
	@Lob
	private String procedimentoEmCasoDeAcidente;
	@Lob
	private String termoDeResponsabilidade;
	@Lob
	private String informacoesAdicionais;
	
	private boolean impressa;
	
	private boolean imprimirInfoAdicionais;
	
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public String getNomeColaborador() {
		return nomeColaborador;
	}
	
	public void setNomeColaborador(String nomeColaborador) {
		this.nomeColaborador = nomeColaborador;
	}
	
	public Date getDataAdmisaoColaborador() {
		return dataAdmisaoColaborador;
	}
	
	@NaoAudita
	public String getDataAdmisaoColaboradorFormatada()
	{
		return DateUtil.formataDate(this.dataAdmisaoColaborador, "dd/MM/yyyy");
	}
	
	public void setDataAdmisaoColaborador(Date dataAdmisaoColaborador) {
		this.dataAdmisaoColaborador = dataAdmisaoColaborador;
	}
	
	public String getCodigoCBO() {
		return codigoCBO;
	}
	
	public void setCodigoCBO(String codigoCBO) {
		this.codigoCBO = codigoCBO;
	}
	
	public String getNomeFuncao() {
		return nomeFuncao;
	}
	
	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatada() {
		if(this.data != null){
			return DateUtil.formataDate(this.data, "dd/MM/yyyy");
		}
		return "";
	}
	
	public Double getRevisao() {
		return revisao;
	}
	
	public void setRevisao(Double revisao) {
		this.revisao = revisao;
	}
	
	public String getAtividades() {
		return atividades;
	}
	
	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}
	
	public String getRiscos() {
		return riscos;
	}
	
	public void setRiscos(String riscos) {
		this.riscos = riscos;
	}
	
	public String getEpis() {
		return epis;
	}
	
	public void setEpis(String epis) {
		this.epis = epis;
	}
	
	public String getMedidasPreventivas() {
		return medidasPreventivas;
	}
	
	public void setMedidasPreventivas(String medidasPreventivas) {
		this.medidasPreventivas = medidasPreventivas;
	}
	
	public String getTreinamentos() {
		return treinamentos;
	}
	
	public void setTreinamentos(String treinamentos) {
		this.treinamentos = treinamentos;
	}
	
	public String getNormasInternas() {
		return normasInternas;
	}
	
	public void setNormasInternas(String normasInternas) {
		this.normasInternas = normasInternas;
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
	
	public void setColaboradorId(Long colaboradorId){
		if(colaborador == null)
			colaborador = new Colaborador();
		
		colaborador.setId(colaboradorId);
	}

	public String getInformacoesAdicionais() {
		return informacoesAdicionais;
	}

	public void setInformacoesAdicionais(String informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}

	public boolean isImpressa() {
		return impressa;
	}

	public void setImpressa(boolean impressa) {
		this.impressa = impressa;
	}

	public boolean isImprimirInfoAdicionais() {
		return imprimirInfoAdicionais;
	}

	public void setImprimirInfoAdicionais(boolean imprimirInfoAdicionais) {
		this.imprimirInfoAdicionais = imprimirInfoAdicionais;
	}
}
