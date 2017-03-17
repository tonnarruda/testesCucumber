package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class MedicoCoordenadorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	@Autowired private MedicoCoordenadorManager medicoCoordenadorManager;

	private Collection<MedicoCoordenador> medicoCoordenadors;

	private MedicoCoordenador medicoCoordenador;

	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa"};
		Object[] values = new Object[]{getEmpresaSistema()};
		String[] orders = new String[]{"nome"};

		setTotalSize(medicoCoordenadorManager.getCount(keys, values));
		medicoCoordenadors = medicoCoordenadorManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		try
		{
			medicoCoordenadorManager.remove(new Long[]{medicoCoordenador.getId()});
			addActionMessage("Médico Coordenador excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir este Médico Coordenador.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection<MedicoCoordenador> getMedicoCoordenadors() {
		return medicoCoordenadors;
	}


	public MedicoCoordenador getMedicoCoordenador(){
		if(medicoCoordenador == null){
			medicoCoordenador = new MedicoCoordenador();
		}
		return medicoCoordenador;
	}

	public void setMedicoCoordenador(MedicoCoordenador medicoCoordenador){
		this.medicoCoordenador=medicoCoordenador;
	}
}