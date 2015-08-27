package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.ProntuarioManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.ProntuarioRelatorio;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ProntuarioEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private ProntuarioManager prontuarioManager;
	private ColaboradorManager colaboradorManager;

	private Collection<Prontuario> prontuarios;
	private Collection<Colaborador> colaboradors;

	private Prontuario prontuario;
	private Colaborador colaborador;

	private String colaboradorNome;

	//Relatório
	private boolean filtroRelatorio;
	private ProntuarioRelatorio dataSource;
	private Map<String, Object> parametros;

	
	private void prepare() throws Exception
	{
		if(prontuario != null && prontuario.getId() != null)
			prontuario = (Prontuario) prontuarioManager.findById(prontuario.getId());

		if(colaborador != null && colaborador.getId() != null)
			colaboradorNome = colaboradorManager.getNome(colaborador.getId());
	}

	public String list() throws Exception
	{
		prontuarios = prontuarioManager.findByColaborador(colaborador);

		if(colaborador != null)
		{
			colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, false, null, null, getEmpresaSistema().getId());
			if(colaborador.getId() != null)
				colaboradorNome = colaboradorManager.getNome(colaborador.getId());
		}

		if (filtroRelatorio)
			return "successRelatorio";
		else
			return SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			prontuarioManager.remove(prontuario.getId());
			addActionMessage("Prontuário excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Erro ao excluir Prontuário.");
			e.printStackTrace();
		}

		list();

		return Action.SUCCESS;
	}

	public String prepareRelatorioProntuario() throws Exception
	{
		return SUCCESS;
	}

	public String relatorioProntuario() throws Exception
	{
		try
		{
			dataSource = prontuarioManager.findRelatorioProntuario(getEmpresaSistema(), colaborador);
			parametros = RelatorioUtil.getParametrosRelatorio("Prontuário", getEmpresaSistema(), null);

		} catch(ColecaoVaziaException e)
		{
			list();
			addActionMessage(e.getMessage());
			return INPUT;
		}

		return SUCCESS;
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

	public String insert() throws Exception
	{
		setUsuarioResponsavel();
		prontuario.setColaborador(colaborador);
		prontuarioManager.save(prontuario);
		list();

		return SUCCESS;
	}

	public String update() throws Exception
	{
		setUsuarioResponsavel();
		prontuario.setColaborador(colaborador);
		prontuarioManager.update(prontuario);
		list();

		return SUCCESS;
	}

	private void setUsuarioResponsavel()
	{
		Usuario usuarioResponsavel = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		prontuario.setUsuario(usuarioResponsavel);
	}

	public Prontuario getProntuario()
	{
		if(prontuario == null)
			prontuario = new Prontuario();
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario)
	{
		this.prontuario = prontuario;
	}

	public void setProntuarioManager(ProntuarioManager prontuarioManager)
	{
		this.prontuarioManager = prontuarioManager;
	}

	public Collection<Prontuario> getProntuarios()
	{
		return prontuarios;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public String getColaboradorNome()
	{
		return colaboradorNome;
	}

	public boolean isFiltroRelatorio()
	{
		return filtroRelatorio;
	}

	public void setFiltroRelatorio(boolean relatorio)
	{
		this.filtroRelatorio = relatorio;
	}

	public ProntuarioRelatorio getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
}