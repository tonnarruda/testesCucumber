package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class FichaMedicaListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private FichaMedicaManager fichaMedicaManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private CandidatoManager candidatoManager;
	private ColaboradorRespostaManager colaboradorRespostaManager; 

	private Collection<FichaMedica> fichaMedicas = new ArrayList<FichaMedica>();
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
	private Collection<Candidato> candidatos = new ArrayList<Candidato>();

	private FichaMedica fichaMedica;
	private Colaborador colaborador;
	private ColaboradorQuestionario colaboradorQuestionario;

	private String voltarPara = "";
	private Date dataIni;
	private Date dataFim;
	private Character vinculo;
	private String nomeBusca;
	private String cpfBusca;
	private String matriculaBusca;
	private String actionMsg;
	
	private Candidato candidato;

	public String list() throws Exception
	{
		if(StringUtils.isNotBlank(actionMsg))
			addActionMessage(actionMsg);

		fichaMedicas = fichaMedicaManager.findToListByEmpresa(getEmpresaSistema().getId(), getPage(), getPagingSize());

		return Action.SUCCESS;
	}

	public String listPreenchida() throws Exception
	{
		if(StringUtils.isNotBlank(actionMsg))
			addActionMessage(actionMsg);
		
		colaboradorQuestionarios = colaboradorQuestionarioManager.findFichasMedicas(vinculo, dataIni, dataFim, nomeBusca, cpfBusca, matriculaBusca);
		
		return Action.SUCCESS;
	}
	
	public String deletePreenchida() throws Exception
	{
		colaboradorRespostaManager.removeFicha(colaboradorQuestionario.getId());
		addActionMessage("Ficha médica excluída com sucesso.");

		return Action.SUCCESS;
	}
	
	public String prepareInsertFicha() throws Exception
	{
		if(StringUtils.isNotBlank(actionMsg))
			addActionMessage(actionMsg);
			
		candidatos = candidatoManager.findByNomeCpf(candidato, getEmpresaSistema().getId());
		fichaMedicas = fichaMedicaManager.findAllSelect(getEmpresaSistema().getId(), true); 
			
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			fichaMedicaManager.delete(fichaMedica.getId(), getEmpresaSistema().getId());
			setActionMsg("Ficha Médica excluída com sucesso.");
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
		}

		return list();
	}

	public String clonarFichaMedica() throws Exception
	{
		try
		{
			fichaMedica = fichaMedicaManager.clonarFichaMedica(fichaMedica.getId());
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível clonar o modelo de ficha médico, devido a um erro interno.<br><br> Entre em contato com o administrador do sistema.");
			list();
			return Action.INPUT;
		}
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		return colaboradorQuestionario;
	}

	public String getVoltarPara()
	{
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara)
	{
		this.voltarPara = voltarPara;
	}

	public Collection<FichaMedica> getFichaMedicas()
	{
		return fichaMedicas;
	}

	public void setFichaMedicas(Collection<FichaMedica> fichaMedicas)
	{
		this.fichaMedicas = fichaMedicas;
	}

	public FichaMedica getFichaMedica()
	{
		return fichaMedica;
	}

	public void setFichaMedica(FichaMedica fichaMedica)
	{
		this.fichaMedica = fichaMedica;
	}

	public void setFichaMedicaManager(FichaMedicaManager fichaMedicaManager)
	{
		this.fichaMedicaManager = fichaMedicaManager;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return colaboradorQuestionarios;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public Collection<Candidato> getCandidatos()
	{
		return candidatos;
	}

	public Character getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(Character vinculo)
	{
		this.vinculo = vinculo;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public String getCpfBusca()
	{
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca)
	{
		this.cpfBusca = cpfBusca;
	}

	public String getMatriculaBusca()
	{
		return matriculaBusca;
	}

	public void setMatriculaBusca(String matriculaBusca)
	{
		this.matriculaBusca = matriculaBusca;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager)
	{
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public String getActionMsg()
	{
		return actionMsg;
	}

	public void setActionMsg(String actionMsg)
	{
		this.actionMsg = actionMsg;
	}
}