package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class ColaboradorOcorrenciaEditAction extends MyActionSupportList
{
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ColaboradorManager colaboradorManager;
	private OcorrenciaManager ocorrenciaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	private ColaboradorOcorrencia colaboradorOcorrencia;
	private Colaborador colaborador;
	private HistoricoColaborador historicoColab;
	private Collection<Ocorrencia> ocorrencias;
	private Ocorrencia ocorrencia;

	private Collection<ColaboradorOcorrencia> colaboradorOcorrencias;
	private Collection<Colaborador> colaboradors;

	public String prepare() throws Exception
	{
		if(colaboradorOcorrencia != null && colaboradorOcorrencia.getId() != null)
		{
			colaboradorOcorrencia = (ColaboradorOcorrencia) colaboradorOcorrenciaManager.findById(colaboradorOcorrencia.getId());
			colaborador = colaboradorOcorrencia.getColaborador();
		}
		else if (colaborador != null && colaborador.getId() != null)
			colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());

		ocorrencias = ocorrenciaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao asc"});

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

		if (! colaboradorOcorrencia.getColaborador().getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionError("A Ocorrência solicitada não existe na empresa " + getEmpresaSistema().getNome() + ".");

			list();

			return INPUT;
		}

		return SUCCESS;
	}

	public String insertOrUpdate() throws Exception
	{
		String msg = null;
		try
		{
			HistoricoColaborador primeiroHistorico = historicoColaboradorManager.getPrimeiroHistorico(colaborador.getId());
			if(primeiroHistorico.getData().compareTo(colaboradorOcorrencia.getDataIni()) == 1)
			{
				msg = "Não é permitido inserir Ocorrência antes da data da primeira situação do colaborador: (" + DateUtil.formataDiaMesAno(primeiroHistorico.getData()) + ")";
				throw new Exception();
			}
			
			colaboradorOcorrencia.setColaborador(colaborador);
			boolean jaExisteOcorrenciaNoMesmoDia = colaboradorOcorrenciaManager.verifyExistsMesmaData(colaboradorOcorrencia.getId(), colaboradorOcorrencia.getColaborador().getId(), colaboradorOcorrencia.getOcorrencia().getId(), getEmpresaSistema().getId(), colaboradorOcorrencia.getDataIni());

			if (jaExisteOcorrenciaNoMesmoDia)
			{
				throw new Exception("Já existe esta ocorrência na mesma data para esse colaborador.");
			}

			ocorrencia = ocorrenciaManager.findById(colaboradorOcorrencia.getOcorrencia().getId());
			colaboradorOcorrencia.setOcorrencia(ocorrencia);

			colaboradorOcorrenciaManager.saveColaboradorOcorrencia(colaboradorOcorrencia, getEmpresaSistema());
			
			list();

			return SUCCESS;
		}
		catch (IntegraACException e)
		{
			e.printStackTrace();
			addActionError("Cadastro não pôde ser realizado no AC Pessoal.");

			prepare();

			return INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(msg == null)
				addActionError("Cadastro não pôde ser realizado.");
			else
				addActionError(msg);

			prepare();

			return INPUT;
		}
	}

	public String list() throws Exception
	{
		if (colaborador!= null)
		{
			colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), true);
			
			if (colaborador.getId()!= null)
			{
				colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
				setTotalSize(colaboradorOcorrenciaManager.getCount(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()}));
				colaboradorOcorrencias = colaboradorOcorrenciaManager.findProjection(getPage(), getPagingSize(), colaborador.getId());
			}
		}
		
		return Action.SUCCESS;
	
	}

	public String delete() throws Exception
	{
		try
		{
			colaboradorOcorrenciaManager.remove(colaboradorOcorrencia, getEmpresaSistema());
			addActionMessage("Ocorrência do Colaborador removida com sucesso.");
		}
		catch (IntegraACException ie)
		{
			addActionError("A ocorrência deste colaborador não pôde ser removida no AC Pessoal.");
			ie.printStackTrace();
		}
		catch (Exception e)
		{
			addActionError("A ocorrência deste colaborador não pôde ser removida.");
			e.printStackTrace();
		}

		return list();
	}

	public ColaboradorOcorrencia getColaboradorOcorrencia()
	{
		if(colaboradorOcorrencia == null)
			colaboradorOcorrencia = new ColaboradorOcorrencia();
		return colaboradorOcorrencia;
	}

	public void setColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia)
	{
		this.colaboradorOcorrencia = colaboradorOcorrencia;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager)
	{
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager)
	{
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public Collection<Ocorrencia> getOcorrencias()
	{
		return ocorrencias;
	}

	public void setOcorrencias(Collection<Ocorrencia> ocorrencias)
	{
		this.ocorrencias = ocorrencias;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Ocorrencia getOcorrencia()
	{
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia)
	{
		this.ocorrencia = ocorrencia;
	}

	public Collection<ColaboradorOcorrencia> getColaboradorOcorrencias()
	{
		return colaboradorOcorrencias;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public HistoricoColaborador getHistoricoColab()
	{
		return historicoColab;
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}
}