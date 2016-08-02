package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
public class TEmpregado implements Serializable
{
	private Integer id;
	private String codigoAC;
	private String codigoACDestino;
	private String empresaCodigoAC;
	private String nome;
	private String nomeComercial;
	private String matricula;
	private String dataAdmissao;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cpf;
	private String pis;
	private String sexo;
	private String dataNascimento;
	private String escolaridade;
	private String estadoCivil;
	private String conjuge;
	private String pai;
	private String mae;
	private String deficiencia;
	private String ddd;
	private String foneFixo;
	private String foneCelular;
	private String email;
	private String ufSigla;
	private String cidadeCodigoAC;
	private String identidadeNumero;
	private String identidadeOrgao;
	private String identidadeUF;
	private String identidadeDataExpedicao;
	private String habilitacaoNumero;
	private String habilitacaoEmissao;
	private String habilitacaoVencimento;
	private String habilitacaoCategoria;
	private String tituloNumero;	
	private String tituloZona;	
	private String tituloSecao;	
	private String certificadoMilitarNumero;	
	private String certificadoMilitarTipo;	
	private String certificadoMilitarSerie;	
	private String ctpsNumero;	
	private String ctpsSerie;	
	private String ctpsDV;	
	private String ctpsUFSigla;	
	private String ctpsDataExpedicao;
	private String grupoAC;
	private String tipoAdmissao;
	private String chaveAC_RH;
	private String foto;
	private String retiraFoto;
	private Integer vinculo;
	private Integer categoria;
	private String expAgenteNocivo; 
	
	public TEmpregado() {
		super();
	}

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public String getBairro()
	{
		return bairro;
	}
	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}
	public String getCep()
	{
		return cep;
	}
	public void setCep(String cep)
	{
		this.cep = cep;
	}
	public String getCertificadoMilitarNumero()
	{
		return certificadoMilitarNumero;
	}
	public void setCertificadoMilitarNumero(String certificadoMilitarNumero)
	{
		this.certificadoMilitarNumero = certificadoMilitarNumero;
	}
	public String getCertificadoMilitarSerie()
	{
		return certificadoMilitarSerie;
	}
	public void setCertificadoMilitarSerie(String certificadoMilitarSerie)
	{
		this.certificadoMilitarSerie = certificadoMilitarSerie;
	}
	public String getCertificadoMilitarTipo()
	{
		return certificadoMilitarTipo;
	}
	public void setCertificadoMilitarTipo(String certificadoMilitarTipo)
	{
		this.certificadoMilitarTipo = certificadoMilitarTipo;
	}
	public String getCidadeCodigoAC()
	{
		return cidadeCodigoAC;
	}
	public void setCidadeCodigoAC(String cidadeCodigoAC)
	{
		this.cidadeCodigoAC = cidadeCodigoAC;
	}
	public String getCodigoAC()
	{
		return codigoAC;
	}
	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}
	public String getComplemento()
	{
		return complemento;
	}
	public void setComplemento(String complemento)
	{
		this.complemento = complemento;
	}
	public String getConjuge()
	{
		return conjuge;
	}
	public void setConjuge(String conjuge)
	{
		this.conjuge = conjuge;
	}
	public String getCpf()
	{
		return cpf;
	}
	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}
	public String getCtpsDV()
	{
		return ctpsDV;
	}
	public void setCtpsDV(String ctpsDV)
	{
		this.ctpsDV = ctpsDV;
	}
	public String getCtpsNumero()
	{
		return ctpsNumero;
	}
	public void setCtpsNumero(String ctpsNumero)
	{
		this.ctpsNumero = ctpsNumero;
	}
	public String getCtpsSerie()
	{
		return ctpsSerie;
	}
	public void setCtpsSerie(String ctpsSerie)
	{
		this.ctpsSerie = ctpsSerie;
	}
	public String getCtpsUFSigla()
	{
		return ctpsUFSigla;
	}
	public void setCtpsUFSigla(String ctpsUFSigla)
	{
		this.ctpsUFSigla = ctpsUFSigla;
	}
	public String getDdd()
	{
		return ddd;
	}
	public void setDdd(String ddd)
	{
		this.ddd = ddd;
	}
	public String getDeficiencia()
	{
		return deficiencia;
	}
	public void setDeficiencia(String deficiencia)
	{
		this.deficiencia = deficiencia;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEscolaridade()
	{
		return escolaridade;
	}
	public void setEscolaridade(String escolaridade)
	{
		this.escolaridade = escolaridade;
	}
	public String getEstadoCivil()
	{
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil)
	{
		this.estadoCivil = estadoCivil;
	}
	public String getFoneCelular()
	{
		return foneCelular;
	}
	public void setFoneCelular(String foneCelular)
	{
		this.foneCelular = foneCelular;
	}
	public String getFoneFixo()
	{
		return foneFixo;
	}
	public void setFoneFixo(String foneFixo)
	{
		this.foneFixo = foneFixo;
	}
	public String getHabilitacaoCategoria()
	{
		return habilitacaoCategoria;
	}
	public void setHabilitacaoCategoria(String habilitacaoCategoria)
	{
		this.habilitacaoCategoria = habilitacaoCategoria;
	}
	public String getHabilitacaoNumero()
	{
		return habilitacaoNumero;
	}
	public void setHabilitacaoNumero(String habilitacaoNumero)
	{
		this.habilitacaoNumero = habilitacaoNumero;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getIdentidadeNumero()
	{
		return identidadeNumero;
	}
	public void setIdentidadeNumero(String identidadeNumero)
	{
		this.identidadeNumero = identidadeNumero;
	}
	public String getIdentidadeOrgao()
	{
		return identidadeOrgao;
	}
	public void setIdentidadeOrgao(String identidadeOrgao)
	{
		this.identidadeOrgao = identidadeOrgao;
	}
	public String getIdentidadeUF()
	{
		return identidadeUF;
	}
	public void setIdentidadeUF(String identidadeUF)
	{
		this.identidadeUF = identidadeUF;
	}
	public String getLogradouro()
	{
		return logradouro;
	}
	public void setLogradouro(String logradouro)
	{
		this.logradouro = logradouro;
	}
	public String getMae()
	{
		return mae;
	}
	public void setMae(String mae)
	{
		this.mae = mae;
	}
	public String getMatricula()
	{
		return matricula;
	}
	public void setMatricula(String matricula)
	{
		this.matricula = matricula;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getNumero()
	{
		return numero;
	}
	public void setNumero(String numero)
	{
		this.numero = numero;
	}
	public String getPai()
	{
		return pai;
	}
	public void setPai(String pai)
	{
		this.pai = pai;
	}
	public String getPis()
	{
		return pis;
	}
	public void setPis(String pis)
	{
		this.pis = pis;
	}
	public String getSexo()
	{
		return sexo;
	}
	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}
	public String getTituloNumero()
	{
		return tituloNumero;
	}
	public void setTituloNumero(String tituloNumero)
	{
		this.tituloNumero = tituloNumero;
	}
	public String getTituloSecao()
	{
		return tituloSecao;
	}
	public void setTituloSecao(String tituloSecao)
	{
		this.tituloSecao = tituloSecao;
	}
	public String getTituloZona()
	{
		return tituloZona;
	}
	public void setTituloZona(String tituloZona)
	{
		this.tituloZona = tituloZona;
	}
	public String getUfSigla()
	{
		return ufSigla;
	}
	public void setUfSigla(String ufSigla)
	{
		this.ufSigla = ufSigla;
	}
	public String getEmpresaCodigoAC()
	{
		return empresaCodigoAC;
	}
	public void setEmpresaCodigoAC(String empresaCodigoAC)
	{
		this.empresaCodigoAC = empresaCodigoAC;
	}
	public String getDataAdmissao()
	{
		return dataAdmissao;
	}
	public void setDataAdmissao(String dataAdmissao)
	{
		this.dataAdmissao = dataAdmissao;
	}
	
	public String getDataNascimento()
	{
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}
	public String getIdentidadeDataExpedicao()
	{
		return identidadeDataExpedicao;
	}
	public void setIdentidadeDataExpedicao(String identidadeDataExpedicao)
	{
		this.identidadeDataExpedicao = identidadeDataExpedicao;
	}
	public String getHabilitacaoEmissao()
	{
		return habilitacaoEmissao;
	}
	public void setHabilitacaoEmissao(String habilitacaoEmissao)
	{
		this.habilitacaoEmissao = habilitacaoEmissao;
	}
	public String getHabilitacaoVencimento()
	{
		return habilitacaoVencimento;
	}
	public void setHabilitacaoVencimento(String habilitacaoVencimento)
	{
		this.habilitacaoVencimento = habilitacaoVencimento;
	}
	public String getCtpsDataExpedicao()
	{
		return ctpsDataExpedicao;
	}
	public void setCtpsDataExpedicao(String ctpsDataExpedicao)
	{
		this.ctpsDataExpedicao = ctpsDataExpedicao;
	}


	public Date getCtpsDataExpedicaoFormatada()
	{
		return DateUtil.montaDataByString(this.ctpsDataExpedicao);
	}
	public Date getHabilitacaoVencimentoFormatada()
	{
		return DateUtil.montaDataByString(this.habilitacaoVencimento);
	}
	public Date getHabilitacaoEmissaoFormatada()
	{
		return DateUtil.montaDataByString(this.habilitacaoEmissao);
	}	
	public Date getIdentidadeDataExpedicaoFormatada()
	{
		return DateUtil.montaDataByString(this.identidadeDataExpedicao);
	}
	public Date getDataNascimentoFormatada()
	{
		return DateUtil.montaDataByString(this.dataNascimento);
	}
	
	public Date getDataAdmissaoFormatada()
	{
		return DateUtil.montaDataByString(this.dataAdmissao);
	}
	public String getNomeComercial()
	{
		return nomeComercial;
	}
	public void setNomeComercial(String nomeComercial)
	{
		this.nomeComercial = nomeComercial;
	}

	public Integer getVinculo() {
		return vinculo;
	}

	public void setVinculo(Integer vinculo) {
		this.vinculo = vinculo;
	}

	public String getTipoAdmissao() {
		return tipoAdmissao;
	}

	public void setTipoAdmissao(String tipoAdmissao) {
		this.tipoAdmissao = tipoAdmissao;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public String getChaveAC_RH() {
		return chaveAC_RH;
	}

	public void setChaveAC_RH(String chaveACRH) {
		chaveAC_RH = chaveACRH;
	}

	public String getCodigoACDestino() {
		return codigoACDestino;
	}

	public void setCodigoACDestino(String codigoACDestino) {
		this.codigoACDestino = codigoACDestino;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getRetiraFoto() {
		return retiraFoto;
	}

	public void setRetiraFoto(String retiraFoto) {
		this.retiraFoto = retiraFoto;
	}
	
	public String getExpAgenteNocivo()
	{
		return expAgenteNocivo;
	}

	public void setExpAgenteNocivo(String expAgenteNocivo)
	{
		this.expAgenteNocivo = expAgenteNocivo;
	}
}