package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class ExameListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ExameManager exameManager;
	private SolicitacaoExameManager solicitacaoExameManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private ColaboradorManager colaboradorManager;
	private CandidatoManager candidatoManager;

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ClinicaAutorizadaManager clinicaAutorizadaManager;

	private Collection<Exame> exames;

	private Exame exame;

	// Relatório Impressão de ASOs
	private char emitirPara = ' ';
	private Colaborador colaborador = new Colaborador();
	private Candidato candidato = new Candidato();
	private Collection<Colaborador> colaboradors;
	private Collection<Candidato> candidatos;
	private Collection<MedicoCoordenador> medicoCoordenadors;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private AsoRelatorio asoRelatorio;

	//	Relatório de Exames Previstos
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] examesCheck;
	private Collection<CheckBox> examesCheckList = new ArrayList<CheckBox>();
	private String[] colaboradoresCheck;
	private Collection<CheckBox> colaboradoresCheckList = new ArrayList<CheckBox>();

	private Date data;
	
	// Relatório de Exames Realizados
	private Date inicio;
	private Date fim;
	private Collection<ClinicaAutorizada> clinicas;
	private String motivo = "";
	private String resultado = "";
	private String nomeEstabelecimento = "";
	private Map<String, String> motivos;
	private ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
	
	private Collection<ExamesRealizadosRelatorio> examesRealizados;
	private Collection<ExamesPrevistosRelatorio> colecaoExamesPrevistos;

	private SolicitacaoExame solicitacaoExame;

	private boolean agruparPorArea;
	private boolean imprimirAfastados = false;

	private String vinculo;
	private boolean relatorioExamesPrevistosResumido;
	private String nomeBusca;


	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(exameManager.getCount(keys, values));
		exames = exameManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception 
	{
		if(exame.getId() == null)
			return list();

		try
		{
			Exame exameTmp = exameManager.findByIdProjection(exame.getId());

			if(exameTmp == null || !getEmpresaSistema().equals(exameTmp.getEmpresa()))
			{
				addActionError("O Exame solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			}
			else
			{
				exameManager.remove(new Long[]{exame.getId()});
				addActionMessage("Exame excluído com sucesso.");
			}
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir este Exame.");
			e.printStackTrace();
		}

		return list();
	}

	public String prepareImprimirAso()
	{
		return SUCCESS;
	}

	public String filtroImprimirAso()
	{
		medicoCoordenadors = medicoCoordenadorManager.findByEmpresa(getEmpresaSistema().getId());

		if (emitirPara == 'C')
		{
			colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
			colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), false);
			if (colaboradors == null || colaboradors.isEmpty())
				addActionMessage("Nenhum colaborador para o filtro informado.");
		}
		else
		{
			candidato.setPessoalCpf(StringUtil.removeMascara(candidato.getPessoal().getCpf()));
			candidatos = candidatoManager.findByNomeCpf(candidato, getEmpresaSistema().getId());
			if (candidatos == null || candidatos.isEmpty())
				addActionMessage("Nenhum candidato para o filtro informado.");
		}

		return SUCCESS;
	}

	public String imprimirAso()
	{
		try
		{
			asoRelatorio = solicitacaoExameManager.montaRelatorioAso(getEmpresaSistema(), solicitacaoExame);
			parametros = RelatorioUtil.getParametrosRelatorio("SERVIÇO DE MEDICINA OCUPACIONAL", getEmpresaSistema(), "ATESTADO DE SAÚDE OCUPACIONAL - ASO");
		} catch (ColecaoVaziaException e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
			return INPUT;
		}

		return SUCCESS;
	}

	public String prepareRelatorioExamesPrevistos()
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		colaboradoresCheckList = colaboradorManager.populaCheckBox(getEmpresaSistema().getId());
		colaboradoresCheckList = CheckListBoxUtil.marcaCheckListBox(colaboradoresCheckList, colaboradoresCheck);
		
		examesCheckList = exameManager.populaCheckBox(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.marcaCheckListBox(examesCheckList, examesCheck);
		
		return SUCCESS;
	}

	public String relatorioExamesPrevistos() throws Exception
	{
		Long[] areasIds=null, estabelecimentosIds=null, colaboradoresIds=null, examesIds = null;
		if (this.areasCheck != null && this.areasCheck.length > 0)
			 areasIds = LongUtil.arrayStringToArrayLong(this.areasCheck);

		if (this.estabelecimentosCheck != null && this.estabelecimentosCheck.length > 0){
			estabelecimentosIds = LongUtil.arrayStringToArrayLong(this.estabelecimentosCheck);
			nomeEstabelecimento = estabelecimentoManager.nomeEstabelecimentos(estabelecimentosIds);
		}
		
		if (this.examesCheck != null && this.examesCheck.length > 0)
			examesIds = LongUtil.arrayStringToArrayLong(this.examesCheck);

		if (this.colaboradoresCheck != null && this.colaboradoresCheck.length > 0)
			colaboradoresIds = LongUtil.arrayStringToArrayLong(this.colaboradoresCheck);

		try
		{
			colecaoExamesPrevistos = exameManager.findRelatorioExamesPrevistos(getEmpresaSistema().getId(), data, examesIds, estabelecimentosIds, areasIds, colaboradoresIds, agruparPorArea, imprimirAfastados);
			parametros = RelatorioUtil.getParametrosRelatorio("Exames Previstos até " + DateUtil.formataDiaMesAno(data), getEmpresaSistema(), nomeEstabelecimento );
			
			return agruparPorArea ? "successAguparPorArea" : SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioExamesPrevistos();
			
			return INPUT;
		}
	}
	
	public String prepareRelatorioExamesRealizados()
	{
		Date hoje = new Date();
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		examesCheckList = exameManager.populaCheckBox(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.marcaCheckListBox(examesCheckList, examesCheck);
		
		motivos = MotivoSolicitacaoExame.getInstance();
		
		clinicas = clinicaAutorizadaManager.findClinicasAtivasByDataEmpresa(getEmpresaSistema().getId(), hoje);
		
		return SUCCESS;
	}
	
	public String relatorioExamesRealizados()
	{
		Long[] estabelecimentosIds = null, examesIds = null;

		if (this.estabelecimentosCheck != null && this.estabelecimentosCheck.length > 0){
			estabelecimentosIds = LongUtil.arrayStringToArrayLong(this.estabelecimentosCheck);
			nomeEstabelecimento = estabelecimentoManager.nomeEstabelecimentos(estabelecimentosIds);
		}
			
		if (this.examesCheck != null && this.examesCheck.length > 0)
			examesIds = LongUtil.arrayStringToArrayLong(this.examesCheck);

		try
		{
			if (relatorioExamesPrevistosResumido)
				examesRealizados = exameManager.findRelatorioExamesRealizadosResumido(getEmpresaSistema().getId(), inicio, fim, clinicaAutorizada, examesIds);
			else
				examesRealizados = exameManager.findRelatorioExamesRealizados(getEmpresaSistema().getId(), nomeBusca, inicio, fim, motivo, resultado, clinicaAutorizada.getId(), examesIds, estabelecimentosIds, vinculo);

			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Exames Realizados", getEmpresaSistema(), "Período: " + DateUtil.formataDiaMesAno(inicio) + " - " + DateUtil.formataDiaMesAno(fim) + "\n" + nomeEstabelecimento);
			
			return relatorioExamesPrevistosResumido ? "successRelatResumido" : SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioExamesRealizados();
			
			return INPUT;
		}
	}

	public Collection<Exame> getExames() {
		return exames;
	}

	public Exame getExame(){
		if(exame == null){
			exame = new Exame();
		}
		return exame;
	}

	public void setExame(Exame exame){
		this.exame=exame;
	}

	public void setExameManager(ExameManager exameManager){
		this.exameManager=exameManager;
	}

	public void setExames(Collection<Exame> exames)
	{
		this.exames = exames;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setMedicoCoordenadorManager(MedicoCoordenadorManager medicoCoordenadorManager)
	{
		this.medicoCoordenadorManager = medicoCoordenadorManager;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public char getEmitirPara()
	{
		return emitirPara;
	}

	public void setEmitirPara(char emitirPara)
	{
		this.emitirPara = emitirPara;
	}

	public Collection<Candidato> getCandidatos()
	{
		return candidatos;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public Collection<MedicoCoordenador> getMedicoCoordenadors()
	{
		return medicoCoordenadors;
	}

	public AsoRelatorio getAsoRelatorio()
	{
		return asoRelatorio;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public String[] getColaboradoresCheck()
	{
		return colaboradoresCheck;
	}

	public void setColaboradoresCheck(String[] colaboradoresCheck)
	{
		this.colaboradoresCheck = colaboradoresCheck;
	}

	public String[] getExamesCheck()
	{
		return examesCheck;
	}

	public void setExamesCheck(String[] examesCheck)
	{
		this.examesCheck = examesCheck;
	}

	public Collection<CheckBox> getColaboradoresCheckList()
	{
		return colaboradoresCheckList;
	}

	public Collection<CheckBox> getExamesCheckList()
	{
		return examesCheckList;
	}

	public Collection<ExamesPrevistosRelatorio> getColecaoExamesPrevistos()
	{
		return colecaoExamesPrevistos;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Collection<ClinicaAutorizada> getClinicas() {
		return clinicas;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Map<String, String> getMotivos() {
		return motivos;
	}

	public void setClinicaAutorizadaManager(ClinicaAutorizadaManager clinicaAutorizadaManager) {
		this.clinicaAutorizadaManager = clinicaAutorizadaManager;
	}

	public ClinicaAutorizada getClinicaAutorizada() {
		return clinicaAutorizada;
	}

	public void setClinicaAutorizada(ClinicaAutorizada clinicaAutorizada) {
		this.clinicaAutorizada = clinicaAutorizada;
	}

	public Collection<ExamesRealizadosRelatorio> getExamesRealizados() {
		return examesRealizados;
	}

	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager) {
		this.solicitacaoExameManager = solicitacaoExameManager;
	}

	public void setSolicitacaoExame(SolicitacaoExame solicitacaoExame) {
		this.solicitacaoExame = solicitacaoExame;
	}

	public boolean isAgruparPorArea() {
		return agruparPorArea;
	}

	public void setAgruparPorArea(boolean agruparPorArea) {
		this.agruparPorArea = agruparPorArea;
	}
	
	public boolean isImprimirAfastados() {
		return imprimirAfastados;
	}

	public void setImprimirAfastados(boolean imprimirAfastados) {
		this.imprimirAfastados = imprimirAfastados;
	}

	public String getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(String vinculo)
	{
		this.vinculo = vinculo;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public void setRelatorioExamesPrevistosResumido(boolean relatorioExamesPrevistosResumido) {
		this.relatorioExamesPrevistosResumido = relatorioExamesPrevistosResumido;
	}
}