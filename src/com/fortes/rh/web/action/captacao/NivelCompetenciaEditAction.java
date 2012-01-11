package com.fortes.rh.web.action.captacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class NivelCompetenciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager; 
	private FaixaSalarialManager faixaSalarialManager;
	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private SolicitacaoManager solicitacaoManager;
	
	private NivelCompetencia nivelCompetencia;
	private FaixaSalarial faixaSalarial;
	private Candidato candidato;
	private Colaborador colaborador;
	private Solicitacao solicitacao;
	private Date data;
	private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
	
	private Collection<FaixaSalarial> faixaSalarials;
	private Collection<NivelCompetencia> nivelCompetencias;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSugeridos;
	private Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores;
	private Collection<Solicitacao> solicitacoes;

	private String[] competenciasCheck;
	private Collection<CheckBox> competenciasCheckList = new ArrayList<CheckBox>();
	private Map<String,Object> parametros;

	private Collection<ConfiguracaoNivelCompetenciaVO> configuracaoNivelCompetenciaVOs;
	
	private void prepare() throws Exception
	{
		if(nivelCompetencia != null && nivelCompetencia.getId() != null)
			nivelCompetencia = (NivelCompetencia) nivelCompetenciaManager.findById(nivelCompetencia.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert()
	{
		try
		{
			nivelCompetenciaManager.validaLimite(getEmpresaSistema().getId());
			nivelCompetencia.setEmpresa(getEmpresaSistema());
			nivelCompetenciaManager.save(nivelCompetencia);
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		nivelCompetenciaManager.update(nivelCompetencia);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			nivelCompetenciaManager.remove(nivelCompetencia.getId());
			addActionMessage("Nível de Competência excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este Nível de Competência.");
		}

		return list();
	}
	
	public String prepareCompetenciasByFaixa()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(faixaSalarial.getCargo().getId(), null);
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId());
		
		return Action.SUCCESS;
	}
	
	public String prepareCompetenciasByCandidato()
	{
		candidato = candidatoManager.findByCandidatoId(candidato.getId());
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());

		niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(faixaSalarial.getCargo().getId(), getEmpresaSistema().getId());
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		
		niveisCompetenciaFaixaSalariaisSugeridos = configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId());
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByCandidato(candidato.getId());
		
		return Action.SUCCESS;
	}
	
	public String saveCompetenciasByFaixa()
	{
		try
		{
			configuracaoNivelCompetenciaManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), null);
			addActionMessage("Níveis de Competência da Faixa Salarial salvos com sucesso.");
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}

		prepareCompetenciasByFaixa();
		return Action.SUCCESS;
	}
	
	public String saveCompetenciasByCandidato()
	{
		try
		{
			configuracaoNivelCompetenciaManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId());
			addActionMessage("Níveis de Competência do Candidato salvos com sucesso.");
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		prepareCompetenciasByCandidato();
		return Action.SUCCESS;
	}
	
	public String visualizarCandidato()
	{
		solicitacoes = solicitacaoManager.findAllByCandidato(candidato.getId());
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.getCompetenciasCandidato(candidato.getId(), getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}
	
	public String listCompetenciasColaborador()
	{
		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
		configuracaoNivelCompetenciaColaboradores = configuracaoNivelCompetenciaColaboradorManager.findByColaborador(colaborador.getId()); 
		
		return Action.SUCCESS;	
	}
	
	public void prepareCompetenciasColaborador()
	{
		niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(faixaSalarial.getCargo().getId(), getEmpresaSistema().getId());
		
		if (niveisCompetenciaFaixaSalariais.isEmpty())
			addActionMessage("Não existem competências (conhecimentos, habilidades ou atitudes) cadastradas para o cargo.");
		
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		niveisCompetenciaFaixaSalariaisSugeridos = configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId());
	}
	
	public String prepareInsertCompetenciasColaborador()
	{
		configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaborador.setData(new Date());
		
		colaborador = colaboradorManager.findById(colaborador.getId());
		faixaSalarial = colaborador.getHistoricoColaborador().getFaixaSalarial();
		
		prepareCompetenciasColaborador();
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdateCompetenciasColaborador()
	{
		configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId());
		colaborador = configuracaoNivelCompetenciaColaborador.getColaborador();
		faixaSalarial = configuracaoNivelCompetenciaColaborador.getFaixaSalarial();
		
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		
		prepareCompetenciasColaborador();
		
		return Action.SUCCESS;
	}
	
	public String saveCompetenciasColaborador()
	{
		try
		{
			configuracaoNivelCompetenciaColaboradorManager.checarHistoricoMesmaData(configuracaoNivelCompetenciaColaborador);
			
			configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
			addActionMessage("Níveis de Competência do Colaborador salvos com sucesso.");
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		if (configuracaoNivelCompetenciaColaborador.getId() != null)
			prepareUpdateCompetenciasColaborador();
		else
			prepareInsertCompetenciasColaborador();
		
		return Action.SUCCESS;
	}
	
	public String deleteCompetenciasColaborador()
	{
		try
		{
			configuracaoNivelCompetenciaManager.removeConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}

		listCompetenciasColaborador();
		return Action.SUCCESS;
	}
	
	public String prepareRelatorioCompetenciasColaborador()
	{
		faixaSalarials = faixaSalarialManager.findAllSelectByCargo(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}
	
	public String imprimirRelatorioCompetenciasColaborador()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório Colaboradores com nível de competência inferior ao exigido", getEmpresaSistema(), "Cargo/Faixa: " + faixaSalarial.getDescricao());
		niveisCompetenciaFaixaSalariais = configuracaoNivelCompetenciaManager.findColaboradorAbaixoNivel(LongUtil.arrayStringToArrayLong(competenciasCheck), faixaSalarial.getId());
		
		if(niveisCompetenciaFaixaSalariais.isEmpty())
		{
			prepareRelatorioCompetenciasColaborador();
			faixaSalarial = null;

			addActionMessage("Não existem dados para o filtro informado.");
			return Action.INPUT;			
		}
		
		return Action.SUCCESS;
	}
	
	public String imprimirMatrizCompetenciasColaborador()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		parametros = RelatorioUtil.getParametrosRelatorio("Matriz comparativa Cargo x Colaborador", getEmpresaSistema(), "Cargo/Faixa: " + faixaSalarial.getDescricao());
		parametros.put("ENTIDADE", "Colaborador");
		configuracaoNivelCompetenciaVOs = configuracaoNivelCompetenciaManager.montaRelatorioConfiguracaoNivelCompetencia(getEmpresaSistema().getId(), faixaSalarial.getId(), LongUtil.arrayStringToArrayLong(competenciasCheck));
		
		if(configuracaoNivelCompetenciaVOs.isEmpty())
		{
			prepareRelatorioCompetenciasColaborador();
			faixaSalarial = null;
			
			addActionMessage("Não existem dados para o filtro informado.");
			return Action.INPUT;			
		}
		
		return Action.SUCCESS;
	}
	public String imprimirMatrizCompetenciasCandidatos()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		parametros = RelatorioUtil.getParametrosRelatorio("Matriz comparativa Cargo x Candidato", getEmpresaSistema(), "Cargo/Faixa: " + faixaSalarial.getDescricao());
		parametros.put("ENTIDADE", "Candidato");
		configuracaoNivelCompetenciaVOs = configuracaoNivelCompetenciaManager.montaMatrizCompetenciaCandidato(getEmpresaSistema().getId(), faixaSalarial.getId(), solicitacao.getId());
		
		return Action.SUCCESS;
	}
	
	public NivelCompetencia getNivelCompetencia()
	{
		if(nivelCompetencia == null)
			nivelCompetencia = new NivelCompetencia();
		return nivelCompetencia;
	}

	public void setNivelCompetencia(NivelCompetencia nivelCompetencia)
	{
		this.nivelCompetencia = nivelCompetencia;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager)
	{
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}
	
	public Collection<NivelCompetencia> getNivelCompetencias()
	{
		return nivelCompetencias;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariais() 
	{
		return niveisCompetenciaFaixaSalariais;
	}

	public void setNiveisCompetenciaFaixaSalariais(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais) 
	{
		this.niveisCompetenciaFaixaSalariais = niveisCompetenciaFaixaSalariais;
	}

	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}
	
	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSalvos() {
		return niveisCompetenciaFaixaSalariaisSalvos;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSugeridos() {
		return niveisCompetenciaFaixaSalariaisSugeridos;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public ConfiguracaoNivelCompetenciaColaborador getConfiguracaoNivelCompetenciaColaborador() {
		return configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaborador(
			ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) {
		this.configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(
			ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public Collection<ConfiguracaoNivelCompetenciaColaborador> getConfiguracaoNivelCompetenciaColaboradores() {
		return configuracaoNivelCompetenciaColaboradores;
	}

	public void setConfiguracaoNivelCompetenciaColaboradores(
			Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores) {
		this.configuracaoNivelCompetenciaColaboradores = configuracaoNivelCompetenciaColaboradores;
	}

	public Collection<FaixaSalarial> getFaixaSalarials() {
		return faixaSalarials;
	}

	public void setCompetenciasCheck(String[] competenciasCheck) {
		this.competenciasCheck = competenciasCheck;
	}

	public Collection<CheckBox> getCompetenciasCheckList() {
		return competenciasCheckList;
	}

	public Collection<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Collection<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
	
	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> getConfiguracaoNivelCompetenciaVOs() {
		return configuracaoNivelCompetenciaVOs;
	}
}