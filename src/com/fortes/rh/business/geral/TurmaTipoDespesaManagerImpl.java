package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.util.StringUtil;

@Component
public class TurmaTipoDespesaManagerImpl extends GenericManagerImpl<TurmaTipoDespesa, TurmaTipoDespesaDao> implements TurmaTipoDespesaManager
{
	@Autowired
	TurmaTipoDespesaManagerImpl(TurmaTipoDespesaDao dao) {
		setDao(dao);
	}
	
	public void save(Map<Long, String> despesas, Long turmaId) 
	{
		TurmaTipoDespesa turmaTipoDespesa;
		Double valor;
		
		for (Map.Entry<Long, String> despesa : despesas.entrySet())
		{
			valor = Double.parseDouble(despesa.getValue());
			
			if (valor > 0)
			{
				turmaTipoDespesa = new TurmaTipoDespesa();
				turmaTipoDespesa.setProjectionTipoDespesaId(despesa.getKey());
				turmaTipoDespesa.setDespesa(valor);
				turmaTipoDespesa.setProjectionTurmaId(turmaId);
				save(turmaTipoDespesa);
			}
		}
	}

	public void removeByTurma(Long turmaId) 
	{
		getDao().removeByTurma(turmaId);
	}
	
	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId) 
	{
		return getDao().findTipoDespesaTurma(turmaId);
	}

	@SuppressWarnings("unchecked")
	public void save(String json, Long turmaId) 
	{
		Collection<TurmaTipoDespesa> despesas = new ArrayList<TurmaTipoDespesa>();
		try {
			despesas = (Collection<TurmaTipoDespesa>) StringUtil.simpleJSONtoArrayJava(json, TurmaTipoDespesa.class);
		} catch (JSONException e) {e.printStackTrace();}
	
		getDao().removeByTurma(turmaId);
		
		Turma turma = new Turma();
		turma.setId(turmaId);
		
		for (TurmaTipoDespesa turmaTipoDespesa : despesas) 
		{
			TurmaTipoDespesa despesa = new TurmaTipoDespesa(turma, turmaTipoDespesa.getDespesa(), turmaTipoDespesa.getTipoDespesaId());
			getDao().save(despesa);
		}
	}

	public Collection<TipoDespesa> somaDespesasPorTipo(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds) 
	{
		return getDao().somaDespesasPorTipo(dataIni, dataFim, empresaIds, cursoIds);
	}
}
