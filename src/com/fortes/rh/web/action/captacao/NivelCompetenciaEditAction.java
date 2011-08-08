package com.fortes.rh.web.action.captacao;


import java.util.Collection;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.NivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class NivelCompetenciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private NivelCompetenciaManager nivelCompetenciaManager;
	private NivelCompetenciaFaixaSalarialManager nivelCompetenciaFaixaSalarialManager; 
	private FaixaSalarialManager faixaSalarialManager;
	private CandidatoManager candidatoManager;
	
	private NivelCompetencia nivelCompetencia;
	private FaixaSalarial faixaSalarial;
	private Candidato candidato;
	
	private Collection<NivelCompetencia> nivelCompetencias;
	private Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais;
	private Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariaisSalvos;
	private Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariaisSugeridos;

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
		
		niveisCompetenciaFaixaSalariaisSalvos = nivelCompetenciaFaixaSalarialManager.findByFaixa(faixaSalarial.getId());
		
		return Action.SUCCESS;
	}
	
	public String prepareCompetenciasByCandidato()
	{
		candidato = candidatoManager.findByCandidatoId(candidato.getId());
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());

		niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(faixaSalarial.getCargo().getId(), getEmpresaSistema().getId());
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		
		niveisCompetenciaFaixaSalariaisSugeridos = nivelCompetenciaFaixaSalarialManager.findByFaixa(faixaSalarial.getId());
		niveisCompetenciaFaixaSalariaisSalvos = nivelCompetenciaFaixaSalarialManager.findByCandidato(candidato.getId());
		
		return Action.SUCCESS;
	}
	
	public String saveCompetenciasByFaixa()
	{
		try
		{
			nivelCompetenciaFaixaSalarialManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), null);
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
			nivelCompetenciaFaixaSalarialManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId());
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

	public Collection<NivelCompetenciaFaixaSalarial> getNiveisCompetenciaFaixaSalariais() 
	{
		return niveisCompetenciaFaixaSalariais;
	}

	public void setNiveisCompetenciaFaixaSalariais(Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais) 
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

	public void setNivelCompetenciaFaixaSalarialManager(NivelCompetenciaFaixaSalarialManager nivelCompetenciaFaixaSalarialManager) {
		this.nivelCompetenciaFaixaSalarialManager = nivelCompetenciaFaixaSalarialManager;
	}

	public Collection<NivelCompetenciaFaixaSalarial> getNiveisCompetenciaFaixaSalariaisSalvos() {
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

	public Collection<NivelCompetenciaFaixaSalarial> getNiveisCompetenciaFaixaSalariaisSugeridos() {
		return niveisCompetenciaFaixaSalariaisSugeridos;
	}
}