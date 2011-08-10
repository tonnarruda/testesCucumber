package com.fortes.rh.web.action.captacao;


import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.web.action.MyActionSupportList;
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
	
	private NivelCompetencia nivelCompetencia;
	private FaixaSalarial faixaSalarial;
	private Candidato candidato;
	private Colaborador colaborador;
	private Solicitacao solicitacao;
	private Date data;
	private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
	
	private Collection<NivelCompetencia> nivelCompetencias;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSugeridos;
	private Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores;

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
		
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByColaborador(configuracaoNivelCompetenciaColaborador.getId());
		
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
}