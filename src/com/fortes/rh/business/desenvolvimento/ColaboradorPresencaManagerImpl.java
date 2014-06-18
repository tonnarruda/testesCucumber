package com.fortes.rh.business.desenvolvimento;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;

public class ColaboradorPresencaManagerImpl extends GenericManagerImpl<ColaboradorPresenca, ColaboradorPresencaDao> implements ColaboradorPresencaManager
{
	
	private ColaboradorTurmaManager colaboradorTurmaManager;

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Collection<ColaboradorPresenca> findPresencaByTurma(Long id)
	{
		return getDao().findPresencaByTurma(id);
	}

	public boolean existPresencaByTurma(Long turmaId)
	{
		return !getDao().existPresencaByTurma(turmaId).isEmpty();
	}

	public void updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca) throws Exception
	{
		if (presenca)
		{
			DiaTurma diaTurma = new DiaTurma();
			diaTurma.setId(diaTurmaId);
			ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
			colaboradorTurma.setId(colaboradorTurmaId);
			
			ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca(colaboradorTurma, diaTurma, true);
			getDao().save(colaboradorPresenca);
		}
		else
			getDao().remove(diaTurmaId, colaboradorTurmaId);
		
	}

	public void marcarTodos(Long diaTurmaId, Long turmaId)
	{
		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(diaTurmaId);
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaSemPresenca(turmaId, diaTurmaId);
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
		{
			getDao().save(new ColaboradorPresenca(colaboradorTurma, diaTurma, true));
		}
	}

	public void removeByDiaTurma(Long diaTurmaId) throws Exception
	{
		getDao().remove(diaTurmaId, null);
	}

	public Integer qtdDiaPresentesTurma(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds)
	{
		return getDao().qtdDiaPresentesTurma(dataIni, dataFim, empresaIds, cursoIds, areasIds);
	}

	public String calculaFrequencia(Long colaboradorTurmaId, Integer qtdDias)
	{
		if(qtdDias.equals(0))
			return "0,00";
		
		Integer qtdPresenca = getDao().getCount(new String[]{"colaboradorTurma.id"}, new Object[]{colaboradorTurmaId});
		
		Double resultado = qtdPresenca.doubleValue() / qtdDias.doubleValue();
		
//		NumberFormat formata = new DecimalFormat("#0.00");
		DecimalFormat formata = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		formata.applyPattern("#0.00");
		return formata.format(resultado * 100);
	}

	public Collection<ColaboradorTurma> preparaLinhaEmBranco(Collection<ColaboradorTurma> colaboradorTurmas, int qtdMaxLinha, Long estabelecimentoId)
	{
		if(colaboradorTurmas.size() < qtdMaxLinha)
		{
			int linhasEmBranco = qtdMaxLinha - colaboradorTurmas.size();

			for(int i = 0; i < linhasEmBranco; i++)
			{
				ColaboradorTurma colaboradorTurmaAux = new ColaboradorTurma();
				colaboradorTurmaAux.setProjectionEstabelecimentoId(estabelecimentoId);
				colaboradorTurmas.add(colaboradorTurmaAux);
			}
		}
		
		return colaboradorTurmas;
	}

	public void removeByColaboradorTurma(Long[] colaboradorTurmaIds)
	{
		getDao().removeByColaboradorTurma(colaboradorTurmaIds);
	} 
	
}