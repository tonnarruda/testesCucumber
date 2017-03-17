package com.fortes.rh.web.action.geral;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

@SuppressWarnings( { "serial" })
public class HistoricoBeneficioEditAction extends MyActionSupport
{
	@Autowired private HistoricoBeneficioManager historicoBeneficioManager;
	@Autowired private BeneficioManager beneficioManager;
	private HistoricoBeneficio historicoBeneficio;
	private Beneficio beneficio;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if (historicoBeneficio != null && historicoBeneficio.getId() != null)
		{
			historicoBeneficio = historicoBeneficioManager.findByHistoricoId(historicoBeneficio.getId());
			beneficio = beneficioManager.findBeneficioById(historicoBeneficio.getBeneficio().getId());
		}
		else
			beneficio = beneficioManager.findBeneficioById(beneficio.getId());
	}

	private boolean verificaEmpresa() throws Exception
	{
		String[] key = new String[]{"id","empresa.id"};
		Object[] values = new Object[]{beneficio.getId(), getEmpresaSistema().getId()};

		return beneficioManager.verifyExists(key, values);
	}

	private String prepareVerificaEmpresa() throws Exception
	{
		prepare();
		if(!verificaEmpresa())
		{
			beneficio = null;
			addActionError("O Benefício solicitado não existe na empresa " + getEmpresaSistema().getNome() + ".");
			return Action.ERROR;
		}
		else
			return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		return prepareVerificaEmpresa();
	}

	public String prepareUpdate() throws Exception
	{
		return prepareVerificaEmpresa();
	}

	public String insert() throws Exception
	{
		if(verificaEmpresa())
		{
			String[] key = new String[]{"data"};
			Object[] values = new Object[]{historicoBeneficio.getData()};

			if(!historicoBeneficioManager.verifyExists(key, values))
			{
				historicoBeneficio.setBeneficio(beneficio);
				historicoBeneficioManager.save(historicoBeneficio);
				return Action.SUCCESS;
			}
			else
			{
				addActionError("Já existe um histórico com essa data, favor selecionar outra.");
				prepareInsert();
				return Action.INPUT;
			}
		}
		else
		{
			addActionError("O Benefício solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		if(verificaEmpresa())
		{
			String[] key = new String[]{"data"};
			Object[] values = new Object[]{historicoBeneficio.getData()};
			Boolean existeData = historicoBeneficioManager.verifyExists(key, values);

			String[] key2 = new String[]{"id","data"};
			Object[] values2 = new Object[]{historicoBeneficio.getId(),historicoBeneficio.getData()};
			Boolean editando = historicoBeneficioManager.verifyExists(key2, values2);

			if(editando || !existeData)
			{
				historicoBeneficio.setBeneficio(beneficio);
				historicoBeneficioManager.update(historicoBeneficio);
				return Action.SUCCESS;
			}
			else
			{
				addActionError("Já existe um histórico com essa data, favor selecionar outra.");
				prepareUpdate();
				return Action.INPUT;
			}
		}
		else
		{
			addActionError("O Benefício solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			prepareUpdate();
			return Action.INPUT;
		}
	}

	public HistoricoBeneficio getHistoricoBeneficio()
	{
		if (historicoBeneficio == null)
			historicoBeneficio = new HistoricoBeneficio();
		return historicoBeneficio;
	}

	public void setHistoricoBeneficio(HistoricoBeneficio historicoBeneficio)
	{
		this.historicoBeneficio = historicoBeneficio;
	}

	public Beneficio getBeneficio()
	{
		return beneficio;
	}

	public void setBeneficio(Beneficio beneficio)
	{
		this.beneficio = beneficio;
	}
}