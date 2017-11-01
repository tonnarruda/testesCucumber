package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.TipoAcidente;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;
import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;
import com.fortes.rh.util.CnpjUtil;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="cat_sequence", allocationSize=1)
public class Cat extends AbstractModel implements Serializable
{
    @ManyToOne
    private Colaborador colaborador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Ambiente ambienteColaborador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Funcao funcaoColaborador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private NaturezaLesao naturezaLesao;
    
    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<Epi> epis;
    
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @Column(length=5)
    private String horario;
    
    @Column(length=5)
    private String horasTrabalhadasAntesAcidente;
    
    @Column(length=100)
    private String parteAtingida;
    
    @Column(length=100)
    private String local;
    
    @Column(length=100)
    private String fonteLesao;
    
    private Integer tipoAcidente;
    private boolean foiTreinadoParaFuncao;
    private boolean usavaEPI;
    private boolean emitiuCAT;
    
    @Column(length=20)
    private String numeroCat;
    private boolean gerouAfastamento;
    private Integer qtdDiasAfastado;
    
    @Lob
    private String observacao;//descricao do acidente
    
    @Lob
    private String conclusao;
    
    private String fotoUrl;
    private Integer qtdDiasDebitados;
    private boolean limitacaoFuncional;
    @Lob
    private String obsLimitacaoFuncional;
    
    @OneToOne (fetch=FetchType.LAZY, cascade= {CascadeType.ALL, CascadeType.REMOVE})
    private Testemunha testemunha1;

    @OneToOne (fetch=FetchType.LAZY, cascade= CascadeType.ALL)
    private Testemunha testemunha2;
    
    @OneToOne (fetch=FetchType.LAZY)
    private CodificacaoAcidenteTrabalho codificacaoAcidenteTrabalho;
    @OneToOne (fetch=FetchType.LAZY)
    private SituacaoGeradoraAcidenteTrabalho situacaoGeradoraAcidenteTrabalho;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<ParteAtingida> partesAtingida;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<AgenteCausadorAcidenteTrabalho> agentesCausadoresAcidenteTrabalho;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<SituacaoGeradoraDoencaProfissional> situacoesGeradoraDoencaProfissional;
    
    @Temporal(TemporalType.DATE)
    private Date dataObito;
    @Temporal(TemporalType.DATE)
    private Date dataCatOrigem;

    private Long tipoRegistardor;
    private Integer tipoInscricao;
    private Long tipo;
    private Boolean obito;
    private Boolean comunicouPolicia;
    private Long iniciatCAT;
    private Long tipoLocal;
    private String cnpjLocalAcidente;
    
    @Column(length=11)
    private String cpfRegistardor;
    @Column(length=12)
    private String cnpjRegistardor;

    @Transient
    private String obitoString;
    @Transient
    private String comunicouPoliciaString;
    
    @Embedded
	private Endereco endereco = new Endereco();

    @Embedded
    private Atestado atestado = new Atestado(); 
    
    public Cat()
	{
	}

    public Cat(Long id, Date data, String numeroCat, String observacao, Boolean gerouAfastamento, Long colaboradorId, String colaboradorMatricula, String colaboradorNome, String estabelecimentoNome, Long areaOrganizacionalId)
	{
		setId(id);
		this.data = data;
		this.numeroCat = numeroCat;
		this.observacao = observacao;
		this.gerouAfastamento = gerouAfastamento;
		
		this.colaborador = new Colaborador();
		setColaboradorId(colaboradorId);
		colaborador.setNome(colaboradorNome);
		colaborador.setMatricula(colaboradorMatricula);
		colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
		colaborador.setAreaOrganizacionalId(areaOrganizacionalId);
	}
    
    public Cat(Cat cat, String colaboradorNome, String ambienteNome, String funcaoNome, String naturezaLesaoDescricao, String empresaNome, String empresaRazaoSocial, String empresaEndereco, String empresaCidadeNome, String empresaUfSigla)
	{
		setId(cat.getId());
		this.data = cat.getData();
		this.horario = cat.getHorario();
		this.local = cat.getLocal();
		this.emitiuCAT = cat.isEmitiuCAT();
		this.numeroCat = cat.getNumeroCat();
		this.gerouAfastamento = cat.getGerouAfastamento();
		this.foiTreinadoParaFuncao = cat.isFoiTreinadoParaFuncao();
		this.usavaEPI = cat.isUsavaEPI();
		this.tipoAcidente = cat.getTipoAcidente();
		this.parteAtingida = cat.getParteAtingida();
		this.fonteLesao = cat.getFonteLesao();
		this.qtdDiasAfastado = cat.getQtdDiasAfastado();
		this.observacao = cat.getObservacao();
		this.conclusao = cat.getConclusao();
		this.epis = cat.getEpis();
		this.fotoUrl = cat.getFotoUrl();
		this.qtdDiasDebitados = cat.getQtdDiasDebitados();
		this.limitacaoFuncional = cat.isLimitacaoFuncional();
		this.obsLimitacaoFuncional = cat.getObsLimitacaoFuncional();
		
		this.colaborador = new Colaborador();
		colaborador.setNome(colaboradorNome);
		
		Empresa empresa = new Empresa();
		empresa.setNome(empresaNome);
		empresa.setRazaoSocial(empresaRazaoSocial);
		empresa.setEndereco(empresaEndereco);
		empresa.setProjectionCidadeNome(empresaCidadeNome);
		colaborador.setEmpresa(empresa);
		
		Estado uf = new Estado();
		uf.setSigla(empresaUfSigla);
		empresa.setUf(uf);
		
		this.ambienteColaborador = new Ambiente();
		ambienteColaborador.setNome(ambienteNome);

		this.funcaoColaborador = new Funcao();
		funcaoColaborador.setNome(funcaoNome);

		this.naturezaLesao = new NaturezaLesao();
		naturezaLesao.setDescricao(naturezaLesaoDescricao);
	}
    
    public Cat(Cat cat, String empresaRazaoSocial, String empresaCnpj, String estabComplementoCnpj, String empresaCnae, String empresaEndereco, String empresaCidadeNome, String empresaUfSigla, String empresaDDD, String empresaTelefone,
			String colaboradorNome, String colaboradorMae, Date colaboradorDataNascimento, Character colaboradorSexo, String colaboradorEstadoCivil, String colaboradorCtpsNumero,
			String colaboradorCtpsSerie, Date colaboradorCtpsDataExpedicao, Estado colaboradorCtpsUf, String colaboradorRg, Date colaboradorRgDataExpedicao, String colaboradorRgOrgaoEmissor,
			Estado colaboradorRgUf, String colaboradorPis, Double colaboradorSalario,
			String colaboradorLogradouro, String colaboradorNumero, String colaboradorBairro, String colaboradorCep, Cidade colaboradorCidade, Estado colaboradorUf, 
			String colaboradorDdd, String colaboradorFoneFixo, String cargoNomeMercado, String faixaSalarialCodigoCbo, String naturezaLesaoDescricao )
    {
		setId(cat.getId());
		this.data = cat.getData();
		this.horario = cat.getHorario();
		this.local = cat.getLocal();
		this.emitiuCAT = cat.isEmitiuCAT();
		this.numeroCat = cat.getNumeroCat();
		this.gerouAfastamento = cat.getGerouAfastamento();
		this.foiTreinadoParaFuncao = cat.isFoiTreinadoParaFuncao();
		this.usavaEPI = cat.isUsavaEPI();
		this.tipoAcidente = cat.getTipoAcidente();
		this.parteAtingida = cat.getParteAtingida();
		this.fonteLesao = cat.getFonteLesao();
		this.qtdDiasAfastado = cat.getQtdDiasAfastado();
		this.observacao = cat.getObservacao();
		this.conclusao = cat.getConclusao();
		this.epis = cat.getEpis();
		this.fotoUrl = cat.getFotoUrl();
		this.qtdDiasDebitados = cat.getQtdDiasDebitados();
		this.limitacaoFuncional = cat.isLimitacaoFuncional();
		this.obsLimitacaoFuncional = cat.getObsLimitacaoFuncional();
		
		this.testemunha1 = cat.getTestemunha1();
		this.testemunha2 = cat.getTestemunha2();
		
		this.colaborador = new Colaborador();
		colaborador.setNome(colaboradorNome);
		colaborador.setSalario(colaboradorSalario);
		
		colaborador.setEnderecoLogradouro(colaboradorLogradouro);
		colaborador.setEnderecoNumero(colaboradorNumero);
		colaborador.setEnderecoBairro(colaboradorBairro);
		colaborador.setEnderecoCep(colaboradorCep);
		colaborador.setEnderecoCidadeId(colaboradorCidade.getId());
		colaborador.setEnderecoCidadeNome(colaboradorCidade.getNome());
		colaborador.setEnderecoUfId(colaboradorUf.getId());
		colaborador.setEnderecoUfSigla(colaboradorUf.getSigla());
		colaborador.setContatoDdd(colaboradorDdd);
		colaborador.setContatoFoneFixo(colaboradorFoneFixo);

		Cargo cargo =  new Cargo();
		cargo.setNomeMercado(cargoNomeMercado);
		FaixaSalarial faixaSalarial = new FaixaSalarial();
		faixaSalarial.setCodigoCbo(faixaSalarialCodigoCbo);
		faixaSalarial.setCargo(cargo);
		colaborador.setFaixaSalarial(faixaSalarial);
		
		Pessoal pessoal = new Pessoal();
		pessoal.setMae(colaboradorMae);
		pessoal.setDataNascimento(colaboradorDataNascimento);
		pessoal.setSexo(colaboradorSexo);
		pessoal.setEstadoCivil(new EstadoCivil().get(colaboradorEstadoCivil).toString());
		pessoal.setRg(colaboradorRg);
		pessoal.setRgDataExpedicao(colaboradorRgDataExpedicao);
		pessoal.setRgOrgaoEmissor(colaboradorRgOrgaoEmissor);
		pessoal.setPis(colaboradorPis);
		pessoal.setRgUf(colaboradorRgUf);
		
		Ctps ctps = new Ctps();
		ctps.setCtpsNumero(colaboradorCtpsNumero);
		ctps.setCtpsSerie(colaboradorCtpsSerie);
		ctps.setCtpsDataExpedicao(colaboradorCtpsDataExpedicao);
		ctps.setCtpsUf(colaboradorCtpsUf);
		pessoal.setCtps(ctps);
		colaborador.setPessoal(pessoal);
		
		String cnpj = CnpjUtil.formata(StringUtils.defaultString(empresaCnpj) + StringUtils.defaultString(estabComplementoCnpj), Boolean.TRUE);
		
		Empresa empresa = new Empresa();
		empresa.setCnae(empresaCnae);
		empresa.setCnpj(cnpj);
		empresa.setRazaoSocial(empresaRazaoSocial);
		empresa.setEndereco(empresaEndereco);
		empresa.setProjectionCidadeNome(empresaCidadeNome);
		empresa.setDdd(empresaDDD);
		empresa.setTelefone(empresaTelefone);
		colaborador.setEmpresa(empresa);
		
		Estado uf = new Estado();
		uf.setSigla(empresaUfSigla);
		empresa.setUf(uf);
		
		this.naturezaLesao = new NaturezaLesao();
		naturezaLesao.setDescricao(naturezaLesaoDescricao);
	}

	public void setColaboradorId(Long colaboradorId)
	{
		if(colaborador == null)
			colaborador = new Colaborador();

		colaborador.setId(colaboradorId);
	}

    public void setNomeComercial(String nomeComercial)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();

    	colaborador.setNomeComercial(nomeComercial);
    }

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	
	public Date getData()
	{
		return data;
	}
	
	public void setData(Date data)
	{
		this.data = data;
	}
	
	public String getNumeroCat()
	{
		return numeroCat;
	}
	
	public void setNumeroCat(String numeroCat)
	{
		this.numeroCat = numeroCat;
	}
	
	public String getObservacao()
	{
		return observacao;
	}
	
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getGerouAfastamentoFormatado() 
	{
		return gerouAfastamento ? "Sim" : "Não";
	}
	
	public boolean getGerouAfastamento() 
	{
		return gerouAfastamento;
	}

	public void setGerouAfastamento(boolean gerouAfastamento) 
	{
		this.gerouAfastamento = gerouAfastamento;
	}

	public String getDataFormatada()
	{
		String dataFmt = "";
		if (data != null)
			dataFmt += DateUtil.formataDiaMesAno(data);

		return dataFmt;
	}

	public NaturezaLesao getNaturezaLesao() 
	{
		return naturezaLesao;
	}

	public void setNaturezaLesao(NaturezaLesao naturezaLesao) 
	{
		this.naturezaLesao = naturezaLesao;
	}

	public Collection<Epi> getEpis() 
	{
		return epis;
	}
	
	public String getEpisFormatado() 
	{
		StringBuffer retorno = new StringBuffer();
		if(epis == null)
			return "";
		else
		{
			for (Epi epi : epis) 
			{
				retorno.append(", " + epi.getNome());
			}		
			
			return retorno.length() >= 2 ? retorno.substring(2) : "";			
		}
	}

	public void setEpis(Collection<Epi> epis) 
	{
		this.epis = epis;
	}

	public String getHorario() 
	{
		return horario;
	}

	public void setHorario(String horario) 
	{
		this.horario = horario;
	}

	public String getParteAtingida() 
	{
		return parteAtingida;
	}

	public void setParteAtingida(String parteAtingida) 
	{
		this.parteAtingida = parteAtingida;
	}

	public Integer getTipoAcidente() 
	{
		return tipoAcidente;
	}
	
	public String getTipoAcidenteDescricao() 
	{
		return new TipoAcidente().get(tipoAcidente);
	}

	public void setTipoAcidente(Integer tipoAcidente) 
	{
		this.tipoAcidente = tipoAcidente;
	}

	public boolean isFoiTreinadoParaFuncao() 
	{
		return foiTreinadoParaFuncao;
	}

	public void setFoiTreinadoParaFuncao(boolean foiTreinadoParaFuncao) 
	{
		this.foiTreinadoParaFuncao = foiTreinadoParaFuncao;
	}

	public boolean isUsavaEPI() 
	{
		return usavaEPI;
	}

	public void setUsavaEPI(boolean usavaEPI) 
	{
		this.usavaEPI = usavaEPI;
	}

	public boolean isEmitiuCAT() 
	{
		return emitiuCAT;
	}

	public void setEmitiuCAT(boolean emitiuCAT) 
	{
		this.emitiuCAT = emitiuCAT;
	}

	public Integer getQtdDiasAfastado() 
	{
		return qtdDiasAfastado;
	}

	public void setQtdDiasAfastado(Integer qtdDiasAfastado) 
	{
		this.qtdDiasAfastado = qtdDiasAfastado;
	}

	public String getConclusao() 
	{
		return conclusao;
	}

	public void setConclusao(String conclusao) 
	{
		this.conclusao = conclusao;
	}

	public String getFonteLesao() {
		return fonteLesao;
	}

	public void setFonteLesao(String fonteLesao) {
		this.fonteLesao = fonteLesao;
	}

	public Ambiente getAmbienteColaborador() {
		return ambienteColaborador;
	}

	public void setAmbienteColaborador(Ambiente ambienteColaborador) {
		this.ambienteColaborador = ambienteColaborador;
	}

	public Funcao getFuncaoColaborador() {
		return funcaoColaborador;
	}

	public void setFuncaoColaborador(Funcao funcaoColaborador) {
		this.funcaoColaborador = funcaoColaborador;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public Integer getQtdDiasDebitados() {
		return qtdDiasDebitados;
	}

	public void setQtdDiasDebitados(Integer qtdDiasDebitados) {
		this.qtdDiasDebitados = qtdDiasDebitados;
	}

	public boolean isLimitacaoFuncional() {
		return limitacaoFuncional;
	}

	public void setLimitacaoFuncional(boolean limitacaoFuncional) {
		this.limitacaoFuncional = limitacaoFuncional;
	}

	public String getObsLimitacaoFuncional() {
		return obsLimitacaoFuncional;
	}

	public void setObsLimitacaoFuncional(String obsLimitacaoFuncional) {
		this.obsLimitacaoFuncional = obsLimitacaoFuncional;
	}

	public Testemunha getTestemunha1() {
		return testemunha1;
	}

	public void setTestemunha1(Testemunha testemunha1) {
		this.testemunha1 = testemunha1;
	}

	public Testemunha getTestemunha2() {
		return testemunha2;
	}

	public void setTestemunha2(Testemunha testemunha2) {
		this.testemunha2 = testemunha2;
	}

	public Long getTipoRegistardor() {
		return tipoRegistardor;
	}

	public void setTipoRegistardor(Long tipoRegistardor) {
		this.tipoRegistardor = tipoRegistardor;
	}

	public Integer getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(Integer tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public String getHorasTrabalhadasAntesAcidente() {
		return horasTrabalhadasAntesAcidente;
	}

	public void setHorasTrabalhadasAntesAcidente(
			String horasTrabalhadasAntesAcidente) {
		this.horasTrabalhadasAntesAcidente = horasTrabalhadasAntesAcidente;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Date getDataObito() {
		return dataObito;
	}

	public void setDataObito(Date dataObito) {
		this.dataObito = dataObito;
	}

	public Boolean getObito() {
		return obito;
	}

	public void setObito(Boolean obito) {
		this.obito = obito;
	}

	public Boolean getComunicouPolicia() {
		return comunicouPolicia;
	}

	public void setComunicouPolicia(Boolean comunicouPolicia) {
		this.comunicouPolicia = comunicouPolicia;
	}

	public Long getIniciatCAT() {
		return iniciatCAT;
	}

	public void setIniciatCAT(Long iniciatCAT) {
		this.iniciatCAT = iniciatCAT;
	}

	public Long getTipoLocal() {
		return tipoLocal;
	}

	public void setTipoLocal(Long tipoLocal) {
		this.tipoLocal = tipoLocal;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCnpjLocalAcidente() {
		return cnpjLocalAcidente;
	}

	public void setCnpjLocalAcidente(String cnpjLocalAcidente) {
		this.cnpjLocalAcidente = cnpjLocalAcidente;
	}

	public Atestado getAtestado() {
		return atestado;
	}

	public void setAtestado(Atestado atestado) {
		this.atestado = atestado;
	}

	public Collection<AgenteCausadorAcidenteTrabalho> getAgentesCausadoresAcidenteTrabalho() {
		return agentesCausadoresAcidenteTrabalho;
	}

	public void setAgentesCausadoresAcidenteTrabalho(Collection<AgenteCausadorAcidenteTrabalho> agentesCausadoresAcidenteTrabalho) {
		this.agentesCausadoresAcidenteTrabalho = agentesCausadoresAcidenteTrabalho;
	}

	public Collection<SituacaoGeradoraDoencaProfissional> getSituacoesGeradoraDoencaProfissional() {
		return situacoesGeradoraDoencaProfissional;
	}

	public void setSituacoesGeradoraDoencaProfissional(Collection<SituacaoGeradoraDoencaProfissional> situacoesGeradoraDoencaProfissional) {
		this.situacoesGeradoraDoencaProfissional = situacoesGeradoraDoencaProfissional;
	}

	public CodificacaoAcidenteTrabalho getCodificacaoAcidenteTrabalho() {
		return codificacaoAcidenteTrabalho;
	}

	public void setCodificacaoAcidenteTrabalho(CodificacaoAcidenteTrabalho codificacaoAcidenteTrabalho) {
		this.codificacaoAcidenteTrabalho = codificacaoAcidenteTrabalho;
	}

	public SituacaoGeradoraAcidenteTrabalho getSituacaoGeradoraAcidenteTrabalho() {
		return situacaoGeradoraAcidenteTrabalho;
	}

	public void setSituacaoGeradoraAcidenteTrabalho(SituacaoGeradoraAcidenteTrabalho situacaoGeradoraAcidenteTrabalho) {
		this.situacaoGeradoraAcidenteTrabalho = situacaoGeradoraAcidenteTrabalho;
	}

	public Date getDataCatOrigem() {
		return dataCatOrigem;
	}

	public void setDataCatOrigem(Date dataCatOrigem) {
		this.dataCatOrigem = dataCatOrigem;
	}

	public void setObitoString(String obitoString) {
		if(obitoString.equals("true"))
			this.obito = true;
		else if(obitoString.equals("false"))
			this.obito = false;
		else
			this.obito = null;
	}

	public void setComunicouPoliciaString(String comunicouPoliciaString) {
		if(comunicouPoliciaString.equals("true"))
			this.comunicouPolicia = true;
		else if(comunicouPoliciaString.equals("false"))
			this.comunicouPolicia = false;
		else
			this.comunicouPolicia = null;
	}

	public String getObitoString() {
		if(obito != null)
			return obito.toString();
		else 
			return null;
	}

	public String getComunicouPoliciaString() {
		if(comunicouPolicia != null)
			return comunicouPolicia.toString();
		else 
			return null;
	}

	public Collection<ParteAtingida> getPartesAtingida() {
		return partesAtingida;
	}

	public void setPartesAtingida(Collection<ParteAtingida> partesAtingida) {
		this.partesAtingida = partesAtingida;
	}

	public String getCpfRegistardor() {
		return cpfRegistardor;
	}

	public void setCpfRegistardor(String cpfRegistardor) {
		this.cpfRegistardor = cpfRegistardor;
	}

	public String getCnpjRegistardor() {
		return cnpjRegistardor;
	}

	public void setCnpjRegistardor(String cnpjRegistardor) {
		this.cnpjRegistardor = cnpjRegistardor;
	}
}