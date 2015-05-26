package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;

public class SolicitacaoExameEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private SolicitacaoExameManager solicitacaoExameManager;
	private ColaboradorManager colaboradorManager;
	private CandidatoManager candidatoManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private ExameManager exameManager;
	private ClinicaAutorizadaManager clinicaAutorizadaManager;
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;
	private RealizacaoExameManager realizacaoExameManager;

	private SolicitacaoExame solicitacaoExame;
	private Collection<Exame> exames;
	private Collection<ClinicaAutorizada> clinicaAutorizadas;
	private Collection<MedicoCoordenador> medicoCoordenadors;
	private Collection<Colaborador> colaboradors;
	private Collection<Candidato> candidatos;
	private Colaborador colaborador = new Colaborador();
	private Candidato candidato = new Candidato();
	private Collection<ExameSolicitacaoExame> exameSolicitacaoExames;
	private Map<String, String> motivos;
	
	private char examesPara;
	private String nomeBusca;
	private String vinculo = "TODOS";
	private boolean primeiraExecucao;
	
	private String matriculaBusca;

	//Selecionar exames e clinicas
	private Object[][] listaExames;
	private String[] examesId;
	private String[] clinicasId;
	private String[] selectClinicas;
	private Integer[] periodicidades;

	private Date solicitacaoExameData;
	//Resultados
	private Object[][] listaExamesResultados;
	private String[] observacoes;
	private String[] selectResultados;
	private Date[] datasRealizacaoExames;

	private boolean gravarEImprimir = false;

	//impressão
	private Collection<SolicitacaoExameRelatorio> dataSource;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	
	//Relatório de Atendimentos Médicos
	private Date inicio;
	private Date fim;
	private Collection<SolicitacaoExame> solicitacaoExames;
	private boolean agruparPorMotivo;
	private boolean ordenarPorNome;
	private boolean showFilter;
	
	private String[] motivosCheck;
	private Collection<CheckBox> motivosCheckList = new ArrayList<CheckBox>();
	
	private Integer ordemAnterior;

	private Date dataAnterior;
	
	private char situacao;
	
	//p/ não deixar repetido (hardcoded) no ftl
	public String getMotivoDEMISSIONAL()
	{
		return MotivoSolicitacaoExame.DEMISSIONAL; 
	}
	public String getMotivoCONSULTA()
	{
		return MotivoSolicitacaoExame.CONSULTA; 
	}
	public String getMotivoATESTADO()
	{
		return MotivoSolicitacaoExame.ATESTADO;
	}
	public String getMotivoSOLICITACAOEXAME()
	{
		return MotivoSolicitacaoExame.SOLICITACAOEXAME;
	}

	private void prepare() throws Exception
	{
		Date hoje = new Date();
		motivos = MotivoSolicitacaoExame.getInstance();

		if(solicitacaoExame != null && solicitacaoExame.getId() != null)
		{
			solicitacaoExame = solicitacaoExameManager.findByIdProjection(solicitacaoExame.getId());
			colaborador = solicitacaoExame.getColaborador();
			candidato = solicitacaoExame.getCandidato();
			
			ordemAnterior = solicitacaoExame.getOrdem();
			dataAnterior = solicitacaoExame.getData();
		}

		medicoCoordenadors = medicoCoordenadorManager.findByEmpresa(getEmpresaSistema().getId());
		clinicaAutorizadas = clinicaAutorizadaManager.findByDataEmpresa(getEmpresaSistema().getId(), hoje, true);

		if (colaborador != null && colaborador.getId() != null)
			exames = exameManager.findPriorizandoExameRelacionado(getEmpresaSistema().getId(), colaborador.getId());
		else if (candidato != null && candidato.getId() != null)
			exames = exameManager.findByEmpresaComAsoPadrao(getEmpresaSistema().getId());

		if (exames != null)
		{
			listaExames = new Object[exames.size()][4];
			int i = 0;

			for (Exame exame : exames)
			{
				Collection<ClinicaAutorizada> clinicas = clinicaAutorizadaManager.findByExame(getEmpresaSistema().getId(), exame.getId(), hoje);
				if (clinicas == null || clinicas.isEmpty())
				{
					ClinicaAutorizada clinicaTmp = new ClinicaAutorizada();
					clinicaTmp.setNome("Nenhuma clínica");
					clinicas.add(clinicaTmp);
				}
				listaExames[i][0] = exame;
				listaExames[i][1] = clinicas;
				listaExames[i][2] = exame.getPeriodicidade();
				listaExames[i][3] = true;
				i++;
			}
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		showFilter = true;
		return SUCCESS;
	}

	public String filtroSolicitacaoExames() throws Exception
	{
		prepare();
		showFilter = true;
		solicitacaoExame = new SolicitacaoExame();
		solicitacaoExame.setData(new Date());
		solicitacaoExame.setMedicoCoordenador(solicitacaoExameManager.setMedicoByQuantidade(medicoCoordenadors));

		if (examesPara == 'C')
		{
			colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
			colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), false, null);
			if (colaboradors == null || colaboradors.isEmpty())
				addActionMessage("Nenhum colaborador para o filtro informado.");
		}
		else
		{
			candidato.setPessoalCpf(StringUtil.removeMascara(candidato.getPessoal().getCpf()));
			candidatos = candidatoManager.findByNomeCpfAllEmpresas(candidato);
			if (candidatos == null || candidatos.isEmpty())
				addActionMessage("Nenhum candidato para o filtro informado.");
		}

		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		examesId = exameManager.findBySolicitacaoExame(solicitacaoExame.getId());
		exameSolicitacaoExames = exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), null);
		boolean temResultado = exameSolicitacaoExameManager.verificaExisteResultado(exameSolicitacaoExames);

		if (temResultado)
			addActionMessage("Atenção: Este atendimento já possui resultados gravados. A edição implicará na perda dos resultados.");

		return SUCCESS;
	}

	public String insert()
	{
		try
		{   
			if (examesPara == 'C')
			{				
				solicitacaoExame.setColaborador(colaborador);
				solicitacaoExame.setCandidato(null);
			}
			else
			{
				solicitacaoExame.setColaborador(null);				
				solicitacaoExame.setCandidato(candidato);
			}
			
			addAsoPadrao();
			solicitacaoExame.setEmpresa(getEmpresaSistema());
			solicitacaoExameManager.ajustaOrdem(null, solicitacaoExame.getData(), null, solicitacaoExame.getOrdem());
			solicitacaoExameManager.save(solicitacaoExame, examesId, selectClinicas, periodicidades);
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar solicitação/atendimento médico.");
			e.printStackTrace();
			return INPUT;
		}

		addActionMessage("Solicitação/Atendimento Médico gravado com sucesso.");
		return SUCCESS;
	}
	
	public void addAsoPadrao() throws Exception
	{
		if(!solicitacaoExame.getMotivo().equals(getMotivoATESTADO()) && !solicitacaoExame.getMotivo().equals(getMotivoCONSULTA()) && !solicitacaoExame.getMotivo().equals(getMotivoSOLICITACAOEXAME()))
		{
			CollectionUtil<Exame> cuExames = new CollectionUtil<Exame>();
			String[] examespadraoIds = cuExames.convertCollectionToArrayIdsString(exameManager.findByAsoPadrao());
			examesId = StringUtil.appendAllDistinct(examespadraoIds, examesId);		
		}
	}

	public String update() throws Exception
	{
		try
		{
			addAsoPadrao();
			solicitacaoExameManager.ajustaOrdem(dataAnterior, solicitacaoExame.getData(), ordemAnterior, solicitacaoExame.getOrdem());
			solicitacaoExameManager.update(solicitacaoExame, examesId, selectClinicas, periodicidades);
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar solicitação/atendimento médico.");
			e.printStackTrace();
			return INPUT;
		}

		addActionMessage("Solicitação/Atendimento Médico gravado com sucesso.");
		return SUCCESS;
	}

	public String imprimirSolicitacaoExames() throws Exception
	{
		try
		{
			dataSource = new ArrayList<SolicitacaoExameRelatorio>();
			dataSource.add(new SolicitacaoExameRelatorio());//Francisco Barroso, precisa para o details funcionar
			
			parametros = RelatorioUtil.getParametrosRelatorio("SERVIÇO DE MEDICINA OCUPACIONAL", getEmpresaSistema(), "SOLICITAÇÃO DE EXAMES");
			parametros.put("CIDADE", getEmpresaSistema().getCidade().getNome());
			parametros.put("COLECAO_MATRIZ", solicitacaoExameManager.imprimirSolicitacaoExames(solicitacaoExame.getId()));
			return SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			return INPUT;
		}
	}
	
	public String prepareRelatorioAtendimentosMedicos()
	{
		motivosCheckList = MotivoSolicitacaoExame.montaCheckListBox();
		medicoCoordenadors = medicoCoordenadorManager.findByEmpresa(getEmpresaSistema().getId());
		
		return SUCCESS;
	}
	
	public String relatorioAtendimentosMedicos()
	{
		try
		{
			solicitacaoExames = solicitacaoExameManager.getRelatorioAtendimentos(inicio, fim, solicitacaoExame, getEmpresaSistema(), agruparPorMotivo, ordenarPorNome, MotivoSolicitacaoExame.getMarcados(motivosCheck), situacao);
			
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Atendimentos Médicos", getEmpresaSistema(), "Período: " + DateUtil.formataDiaMesAno(inicio) + " - " + DateUtil.formataDiaMesAno(fim));
			parametros.put("TOTAL", solicitacaoExames.size());
			
			return (agruparPorMotivo ? "SUCCESS_agrupaMotivo" : SUCCESS);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioAtendimentosMedicos();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			prepareRelatorioAtendimentosMedicos();
			e.printStackTrace();
			return INPUT;
		}
	}

	// Realização de Exames
	public String prepareResultados()
	{
		if (solicitacaoExame != null && solicitacaoExame.getId() != null)
		{
			exameSolicitacaoExames = exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), null);

			prepareDataSolicitacaoExame();

			listaExamesResultados = new Object[exameSolicitacaoExames.size()][2];

			int i = 0;
			for (ExameSolicitacaoExame exameSolicitacaoExame : exameSolicitacaoExames)
			{
				if (exameSolicitacaoExame.getRealizacaoExame() == null)
					exameSolicitacaoExame.setRealizacaoExame(new RealizacaoExame());
				if (StringUtils.isBlank(exameSolicitacaoExame.getRealizacaoExame().getResultado()))
					exameSolicitacaoExame.getRealizacaoExame().setResultado(ResultadoExame.NAO_REALIZADO.toString());
				if (exameSolicitacaoExame.getRealizacaoExame().getObservacao() == null)
					exameSolicitacaoExame.getRealizacaoExame().setObservacao("");
				if (exameSolicitacaoExame.getRealizacaoExame().getData() == null)
					exameSolicitacaoExame.getRealizacaoExame().setData(solicitacaoExame.getData() == null ? new Date() : solicitacaoExame.getData());

				listaExamesResultados[i][0] = exameSolicitacaoExame.getExame();
				listaExamesResultados[i][1] = exameSolicitacaoExame.getRealizacaoExame(); // resultado e observação
				i++;
			}
		}
		else
		{
			addActionError("Solicitação/Atendimento Médico inválido.");
			return INPUT;
		}

		return SUCCESS;
	}

	public String saveResultados()
	{
		try {
			solicitacaoExame.setData(solicitacaoExameData);
			realizacaoExameManager.save(solicitacaoExame, selectResultados, observacoes, datasRealizacaoExames);
			prepareResultados();
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar os resultados.");
			e.printStackTrace();
			return INPUT;
		}

		addActionSuccess("Resultados gravados com sucesso.");
		return SUCCESS;
	}

	private void prepareDataSolicitacaoExame()
	{
		for (ExameSolicitacaoExame exameSolicitacaoExame : exameSolicitacaoExames)
		{
			if (exameSolicitacaoExame.getSolicitacaoExame() != null && exameSolicitacaoExame.getSolicitacaoExame().getData() != null)
			{
				solicitacaoExameData = exameSolicitacaoExame.getSolicitacaoExame().getData();
				break;
			}
		}
	}

	public Object getModel()
	{
		return getSolicitacaoExame();
	}
	public SolicitacaoExame getSolicitacaoExame()
	{
		if(solicitacaoExame == null)
			solicitacaoExame = new SolicitacaoExame();
		return solicitacaoExame;
	}
	public void setSolicitacaoExame(SolicitacaoExame solicitacaoExame)
	{
		this.solicitacaoExame = solicitacaoExame;
	}
	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager)
	{
		this.solicitacaoExameManager = solicitacaoExameManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	public String getMatriculaBusca()
	{
		return matriculaBusca;
	}
	public void setMatriculaBusca(String matriculaBusca)
	{
		this.matriculaBusca = matriculaBusca;
	}
	public String getNomeBusca()
	{
		return nomeBusca;
	}
	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}
	public Collection<MedicoCoordenador> getMedicoCoordenadors()
	{
		return medicoCoordenadors;
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
	public char getExamesPara()
	{
		return examesPara;
	}
	public void setExamesPara(char examesPara)
	{
		this.examesPara = examesPara;
	}
	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}
	public Collection<Candidato> getCandidatos()
	{
		return candidatos;
	}
	public void setCandidatos(Collection<Candidato> candidatos)
	{
		this.candidatos = candidatos;
	}
	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}
	public Collection<Exame> getExames()
	{
		return exames;
	}
	public void setExameManager(ExameManager exameManager)
	{
		this.exameManager = exameManager;
	}
	public void setClinicaAutorizadaManager(ClinicaAutorizadaManager clinicaAutorizadaManager)
	{
		this.clinicaAutorizadaManager = clinicaAutorizadaManager;
	}
	public Collection<ClinicaAutorizada> getClinicaAutorizadas()
	{
		return clinicaAutorizadas;
	}
	public Object[][] getListaExames()
	{
		return listaExames;
	}
	public void setListaExames(Object[][] listaExames)
	{
		this.listaExames = listaExames;
	}
	public String[] getClinicasId()
	{
		return clinicasId;
	}
	public void setClinicasId(String[] clinicasId)
	{
		this.clinicasId = clinicasId;
	}
	public String[] getExamesId()
	{
		return examesId;
	}
	public void setExamesId(String[] examesId)
	{
		this.examesId = examesId;
	}
	public String[] getSelectClinicas()
	{
		return selectClinicas;
	}
	public void setSelectClinicas(String[] selectClinicas)
	{
		this.selectClinicas = selectClinicas;
	}
	public Collection<SolicitacaoExameRelatorio> getDataSource()
	{
		return dataSource;
	}
	public Map<String, Object> getParametros()
	{
		return parametros;
	}
	public boolean getGravarEImprimir() {
		return gravarEImprimir;
	}
	public void setGravarEImprimir(boolean gravarEImprimir) {
		this.gravarEImprimir = gravarEImprimir;
	}
	public void setExameSolicitacaoExameManager(ExameSolicitacaoExameManager exameSolicitacaoExameManager) {
		this.exameSolicitacaoExameManager = exameSolicitacaoExameManager;
	}
	public Collection<ExameSolicitacaoExame> getExameSolicitacaoExames() {
		return exameSolicitacaoExames;
	}

	public Map<String, String> getMotivos() {
		return motivos;
	}

	public void setMotivos(Map<String, String> motivos) {
		this.motivos = motivos;
	}

	public Object[][] getListaExamesResultados() {
		return listaExamesResultados;
	}

	public void setListaExamesResultados(Object[][] listaExamesResultados) {
		this.listaExamesResultados = listaExamesResultados;
	}

	public String[] getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String[] observacoes) {
		this.observacoes = observacoes;
	}

	public String[] getSelectResultados() {
		return selectResultados;
	}

	public void setSelectResultados(String[] selectResultados) {
		this.selectResultados = selectResultados;
	}

	public void setRealizacaoExameManager(RealizacaoExameManager realizacaoExameManager) {
		this.realizacaoExameManager = realizacaoExameManager;
	}

	public Date getSolicitacaoExameData() {
		return solicitacaoExameData;
	}

	public void setSolicitacaoExameData(Date solicitacaoExameData) {
		this.solicitacaoExameData = solicitacaoExameData;
	}

	public Integer[] getPeriodicidades() {
		return periodicidades;
	}

	public void setPeriodicidades(Integer[] periodicidades) {
		this.periodicidades = periodicidades;
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

	public Collection<SolicitacaoExame> getSolicitacaoExames() {
		return solicitacaoExames;
	}

	public boolean isShowFilter() {
		return showFilter;
	}

	public boolean isAgruparPorMotivo() {
		return agruparPorMotivo;
	}

	public void setAgruparPorMotivo(boolean agruparPorMotivo) {
		this.agruparPorMotivo = agruparPorMotivo;
	}
	
	
	public boolean isOrdenarPorNome() {
		return ordenarPorNome;
	}

	public void setOrdenarPorNome(boolean ordenarPorNome) {
		this.ordenarPorNome = ordenarPorNome;
	}

	public Collection<CheckBox> getMotivosCheckList()
	{
		return motivosCheckList;
	}

	public void setMotivosCheck(String[] motivosCheck)
	{
		this.motivosCheck = motivosCheck;
	}

	public Date[] getDatasRealizacaoExames() {
		return datasRealizacaoExames;
	}

	public void setDatasRealizacaoExames(Date[] datasRealizacaoExames) {
		this.datasRealizacaoExames = datasRealizacaoExames;
	}

	public Integer getOrdemAnterior() {
		return ordemAnterior;
	}

	public void setOrdemAnterior(Integer ordemAnterior) {
		this.ordemAnterior = ordemAnterior;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public boolean isPrimeiraExecucao()
	{
		return primeiraExecucao;
	}
	
	public void setPrimeiraExecucao(boolean primeiraExecucao)
	{
		this.primeiraExecucao = primeiraExecucao;
	}

	
	public Date getDataAnterior()
	{
		return dataAnterior;
	}

	
	public void setDataAnterior(Date dataAnterior)
	{
		this.dataAnterior = dataAnterior;
	}

	public String getExameAsosJson() {
		return StringUtil.toJSON(exameManager.findByAsoPadrao(), null);
	}
	public char getSituacao() {
		return situacao;
	}
	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}
}