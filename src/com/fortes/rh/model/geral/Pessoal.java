/* Autor: Igo Coelho
 * Data: 26/05/2006 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.rh.model.captacao.CertificadoMilitar;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.captacao.TituloEleitoral;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
public class Pessoal implements Serializable
{
	@Column(length=11)
	private String cpf;
	@Column(length=11)
	private String pis;
	@Column(length=15)
	private String rg;
	@Column(length=15)
	private String rgOrgaoEmissor;
	@ManyToOne
	private Estado rgUf;
	@Temporal(TemporalType.DATE)
	private Date rgDataExpedicao;
	@Embedded
	private TituloEleitoral tituloEleitoral = new TituloEleitoral();
	@Embedded
	private CertificadoMilitar certificadoMilitar = new CertificadoMilitar();
	@Embedded
	private Ctps ctps = new Ctps();
	@Column(length=100)
	private String naturalidade;
	@Column(length=60)
	private String pai;
	@Column(length=60)
	private String mae;
	@Column(length=40)
	private String conjuge;
	@Column(length=100)
	private String profissaoPai;
	@Column(length=100)
	private String profissaoMae;
	@Column(length=100)
	private String profissaoConjuge;

	private boolean conjugeTrabalha = false;

	@Column(length=100)
	private String indicadoPor;
	@Column(length=100)
	private String parentesAmigos;
	private int qtdFilhos = 0;
	private char sexo = 'M';
	private char deficiencia = '0';
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@Column(length=5)
	private String escolaridade;
	@Column(length=5)
	private String estadoCivil;
	
	@Transient
	private int idade;
	
	//Usado no iReport
	@NaoAudita
	public String getEscolaridadeDic()
	{
		Escolaridade escolarMap = new Escolaridade();
		return (String) escolarMap.get(this.escolaridade);
	}
	@NaoAudita
	public String getSexoDic()
	{
		Sexo sexoMap = new Sexo();
		return (String) sexoMap.get(((Character)this.sexo).toString());
	}
	@NaoAudita
	public String getEstadoCivilDic()
	{
		EstadoCivil estadoCivilMap = new EstadoCivil();
		return (String) estadoCivilMap.get(this.estadoCivil);
	}

	public int getIdade()
	{
		return DateUtil.calcularIdade(this.dataNascimento, new Date());
	}
	@NaoAudita
	public String getDataNascimentoFormatada()
	{
		if (this.dataNascimento != null)
			return DateUtil.formataDate(this.dataNascimento, "dd/MM/yyyy") + " (" + DateUtil.calcularIdade(this.dataNascimento, new Date()) + " anos)";
		else
			return "";
	}
	@NaoAudita
	public String getCpfFormatado()
	{
		return StringUtil.criarMascaraCpf(this.cpf);
	}

	public String getConjuge()
	{
		return conjuge;
	}

	public void setConjuge(String conjuge)
	{
		this.conjuge = conjuge;
	}

	public boolean isConjugeTrabalha()
	{
		return conjugeTrabalha;
	}

	public void setConjugeTrabalha(boolean conjugeTrabalha)
	{
		this.conjugeTrabalha = conjugeTrabalha;
	}

	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public Date getDataNascimento()
	{
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}

	public String getEstadoCivil()
	{
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil)
	{
		this.estadoCivil = estadoCivil;
	}

	public String getEstadoCivilDescricao()
	{
		return (String) (new EstadoCivil()).get(estadoCivil);
	}

	public char getSexo()
	{
		return sexo;
	}

	public void setSexo(char sexo)
	{
		this.sexo = sexo;
	}

	public String getSexoDescricao()
	{
		return (String) (new Sexo()).get(sexo);
	}

	public String getEscolaridade()
	{
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade)
	{
		this.escolaridade = escolaridade;
	}

	public String getEscolaridadeDescricao()
	{
		return (String) (new Escolaridade()).get(escolaridade);
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getParentesAmigos() {
		return parentesAmigos;
	}

	public void setParentesAmigos(String parentesAmigos) {
		this.parentesAmigos = parentesAmigos;
	}

	public String getProfissaoMae() {
		return profissaoMae;
	}

	public void setProfissaoMae(String profissaoMae) {
		this.profissaoMae = profissaoMae;
	}

	public String getProfissaoPai() {
		return profissaoPai;
	}

	public void setProfissaoPai(String profissaoPai) {
		this.profissaoPai = profissaoPai;
	}

	public int getQtdFilhos() {
		return qtdFilhos;
	}
	@NaoAudita
	public String getQtdFilhosString() {
		return StringUtil.valueOf(qtdFilhos);
	}

	public void setQtdFilhos(int qtdFilhos) {
		this.qtdFilhos = qtdFilhos;
	}

	public String getRg()
	{
		return rg;
	}

	public void setRg(String rg)
	{
		this.rg = rg;
	}

	public Date getRgDataExpedicao()
	{
		return rgDataExpedicao;
	}
	@NaoAudita
	public String getRgDataExpedicaoFormatada()
	{
		return DateUtil.formataDiaMesAno(this.rgDataExpedicao);
	}

	public void setRgDataExpedicao(Date rgDataExpedicao)
	{
		this.rgDataExpedicao = rgDataExpedicao;
	}

	public String getRgOrgaoEmissor()
	{
		return rgOrgaoEmissor;
	}

	public void setRgOrgaoEmissor(String rgOrgaoEmissor)
	{
		this.rgOrgaoEmissor = rgOrgaoEmissor;
	}

	public Estado getRgUf()
	{
		return rgUf;
	}

	public void setRgUf(Estado rgUf)
	{
		this.rgUf = rgUf;
	}
	
	@NaoAudita
	public void setRgUfSigla(String sigla){
		if(this.rgUf == null)
			rgUf = new Estado();
		
		this.rgUf.setSigla(sigla);
	}

	public String getProfissaoConjuge()
	{
		return profissaoConjuge;
	}

	public void setProfissaoConjuge(String profissaoConjuge)
	{
		this.profissaoConjuge = profissaoConjuge;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("qtdFilhos", this.qtdFilhos).append("cpf", this.cpf)
				.append("escolaridadeDescricao",
						this.getEscolaridadeDescricao())
				.append("pai", this.pai).append("conjugeTrabalha",
						this.conjugeTrabalha).append("sexoDescricao",
						this.getSexoDescricao())
				.append("conjuge", this.conjuge).append("profissaoConjuge",
						this.profissaoConjuge).append("escolaridade",
						this.escolaridade).append("naturalidade",
						this.naturalidade).append("dataNascimento",
						this.dataNascimento).append("rg", this.rg).append(
						"mae", this.mae)
				.append("estadoCivil", this.estadoCivil).append(
						"estadoCivilDescricao", this.getEstadoCivilDescricao())
				.append("profissaoPai", this.profissaoPai).append("sexo",
						this.sexo).append("profissaoMae", this.profissaoMae)
				.append("parentesAmigos", this.parentesAmigos).toString();
	}

	public String getPis()
	{
		return pis;
	}

	public void setPis(String pis)
	{
		this.pis = pis;
	}

	public CertificadoMilitar getCertificadoMilitar()
	{
		return certificadoMilitar;
	}

	public void setCertificadoMilitar(CertificadoMilitar certificadoMilitar)
	{
		this.certificadoMilitar = certificadoMilitar;
	}

	public Ctps getCtps()
	{
		return ctps;
	}

	public void setCtps(Ctps ctps)
	{
		this.ctps = ctps;
	}

	public TituloEleitoral getTituloEleitoral()
	{
		return tituloEleitoral;
	}

	public void setTituloEleitoral(TituloEleitoral tituloEleitoral)
	{
		this.tituloEleitoral = tituloEleitoral;
	}

	public char getDeficiencia()
	{
		return deficiencia;
	}

	public void setDeficiencia(char deficiencia)
	{
		this.deficiencia = deficiencia;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}

	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}

	public String getDeficienciaDescricao()
	{
		String retorno = "";
		Deficiencia deficienciaMap = new Deficiencia();
		retorno = (String)deficienciaMap.get(this.deficiencia);

		if(retorno == null)
			return "";
		else
			return retorno;
	}
	@NaoAudita
	public String getDiaMesDataNascimento()
	{
		String diaMes = "-";
		if (this.dataNascimento != null)
		{
			Integer tmp=0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.dataNascimento);

			tmp = calendar.get(Calendar.DAY_OF_MONTH);
			diaMes = (tmp<10?"0"+tmp : tmp.toString()) + "/";
			tmp = calendar.get(Calendar.MONTH)+1;
			diaMes += (tmp<10?"0"+tmp : tmp.toString());
		}
		return diaMes;
	}
	
	public String getMesNascimentoExtenso(){
		String mes = "-";
		if (this.dataNascimento != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.dataNascimento);
			mes = DateUtil.getNomeMesCompleto(calendar.get(Calendar.MONTH)+1);
		}
		return mes;
	}
}