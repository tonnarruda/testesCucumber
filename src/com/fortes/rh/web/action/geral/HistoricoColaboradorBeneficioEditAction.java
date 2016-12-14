package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.HistoricoColaboradorBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class HistoricoColaboradorBeneficioEditAction extends MyActionSupportEdit
{
	private HistoricoColaboradorBeneficioManager historicoColaboradorBeneficioManager;
	private ColaboradorManager colaboradorManager;
	private BeneficioManager beneficioManager;

	private HistoricoColaboradorBeneficio historicoColaboradorBeneficio;
	private Colaborador colaborador;

	private String[] beneficiosCheck;
	private Collection<CheckBox> beneficiosCheckList = new ArrayList<CheckBox>();
	private String msgAlert;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(historicoColaboradorBeneficio != null && historicoColaboradorBeneficio.getId() != null)
		{
			historicoColaboradorBeneficio = (HistoricoColaboradorBeneficio) historicoColaboradorBeneficioManager.findById(historicoColaboradorBeneficio.getId());
			historicoColaboradorBeneficio.setDataMesAno(DateUtil.formataMesAno(historicoColaboradorBeneficio.getData()));
			colaborador = historicoColaboradorBeneficio.getColaborador();
		}else{
			colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		}
		beneficiosCheckList = CheckListBoxUtil.populaCheckListBox(beneficioManager.find(new String[]{"empresa.id"} , new Object[]{getEmpresaSistema().getId()},new String[]{"nome"}), "getId", "getNome", null);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		HistoricoColaboradorBeneficio ultimoHistorico = historicoColaboradorBeneficioManager.getUltimoHistorico(colaborador.getId());

		if(ultimoHistorico != null)
		{
			Collection<Beneficio> beneficiosTmp = beneficioManager.getBeneficiosByHistoricoColaborador(ultimoHistorico.getId());
			beneficiosCheckList = CheckListBoxUtil.marcaCheckListBox(beneficiosCheckList, beneficiosTmp, "getId");
		}

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{

		prepare();

		if (!isUltimoHistorico(historicoColaboradorBeneficio))
		{
			msgAlert = "Não é possível editar este histórico, pois já foi inserido um histórico após este.";
			return Action.ERROR;
		}

		for (Iterator iter = historicoColaboradorBeneficio.getBeneficios().iterator(); iter.hasNext();)
		{
			Beneficio beneficioTmp = (Beneficio) iter.next();

			for(CheckBox cb : beneficiosCheckList)
			{
				if(cb.getId().equals(beneficioTmp.getId())){
					cb.setSelecionado(true);
					break;
				}
			}
		}

		return Action.SUCCESS;
	}

	private boolean isUltimoHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio)
	{
		HistoricoColaboradorBeneficio ultimoHistorico = historicoColaboradorBeneficioManager.getUltimoHistorico(historicoColaboradorBeneficio.getColaborador().getId());

		if (ultimoHistorico == null || ultimoHistorico.getId() == null || ultimoHistorico.getId().equals(historicoColaboradorBeneficio.getId()))
			return true;

		return false;
	}

	private boolean dataJaCadastrada()
	{
		HistoricoColaboradorBeneficio historico = historicoColaboradorBeneficioManager.getHistoricoByColaboradorData(historicoColaboradorBeneficio.getColaborador().getId(), historicoColaboradorBeneficio.getData());

		if (historico == null || historico.getId() == null || historico.getId().equals(historicoColaboradorBeneficio.getId()))
			return false;

		return true;
	}

	private boolean dataMenorQueUltimoHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio)
	{
		HistoricoColaboradorBeneficio historico = historicoColaboradorBeneficioManager.getUltimoHistorico(historicoColaboradorBeneficio.getColaborador().getId());

		if (historico == null || historico.getId() == null || historico.getId().equals(historicoColaboradorBeneficio.getId()) || historico.getData().compareTo(historicoColaboradorBeneficio.getData())<0)
			return false;

		return true;
	}

	public String insert() throws Exception
	{
		historicoColaboradorBeneficio.setColaborador(colaborador);
		historicoColaboradorBeneficio.setData(DateUtil.criarDataMesAno(historicoColaboradorBeneficio.getDataMesAno()));

		if (dataJaCadastrada() || dataMenorQueUltimoHistorico(historicoColaboradorBeneficio))
    	{
    		addActionError("Já existe um histórico cadastrado com esta data ou com data maior do que a selecionada. Favor escolher outra data.");
    		prepareInsert();
    		return Action.ERROR;
    	}

		setarBeneficios();

		historicoColaboradorBeneficioManager.saveHistorico(historicoColaboradorBeneficio);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		historicoColaboradorBeneficio.setColaborador(colaborador);
		historicoColaboradorBeneficio.setData(DateUtil.criarDataMesAno(historicoColaboradorBeneficio.getDataMesAno()));

		if (dataJaCadastrada() || dataMenorQueUltimoHistorico(historicoColaboradorBeneficio))
    	{
			addActionError("Já existe um histórico cadastrado com esta data ou com data maior do que a selecionada. Favor escolher outra data.");
    		prepareUpdate();
    		return Action.ERROR;
    	}

		setarBeneficios();

		historicoColaboradorBeneficioManager.update(historicoColaboradorBeneficio);

		return Action.SUCCESS;
	}

	private void setarBeneficios()
	{
		if (beneficiosCheck != null)
		{
			Collection<Beneficio> beneficiosTmp = new ArrayList<Beneficio>();

			for (int i = 0; i < beneficiosCheck.length; i++)
			{
				Long beneficioIdTmp = Long.parseLong(beneficiosCheck[i]);
				Beneficio beneficioTemp = new Beneficio();
				beneficioTemp.setId(beneficioIdTmp);
				beneficiosTmp.add(beneficioTemp);
			}
			historicoColaboradorBeneficio.setBeneficios(beneficiosTmp);
		}
	}

	public HistoricoColaboradorBeneficio getHistoricoColaboradorBeneficio()
	{
		if(historicoColaboradorBeneficio == null)
			historicoColaboradorBeneficio = new HistoricoColaboradorBeneficio();
		return historicoColaboradorBeneficio;
	}

	public void setHistoricoColaboradorBeneficio(HistoricoColaboradorBeneficio historicoColaboradorBeneficio)
	{
		this.historicoColaboradorBeneficio = historicoColaboradorBeneficio;
	}

	public void setHistoricoColaboradorBeneficioManager(HistoricoColaboradorBeneficioManager historicoColaboradorBeneficioManager)
	{
		this.historicoColaboradorBeneficioManager = historicoColaboradorBeneficioManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<CheckBox> getBeneficiosCheckList()
	{
		return beneficiosCheckList;
	}

	public void setBeneficioManager(BeneficioManager beneficioManager)
	{
		this.beneficioManager = beneficioManager;
	}

	public void setBeneficiosCheckList(Collection<CheckBox> beneficiosCheckList)
	{
		this.beneficiosCheckList = beneficiosCheckList;
	}

	public String[] getBeneficiosCheck()
	{
		return beneficiosCheck;
	}

	public void setBeneficiosCheck(String[] beneficiosCheck)
	{
		this.beneficiosCheck = beneficiosCheck;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

}