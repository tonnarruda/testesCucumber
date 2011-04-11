package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;
import com.fortes.rh.util.DateUtil;

public class RealizacaoExameManagerImpl extends GenericManagerImpl<RealizacaoExame, RealizacaoExameDao> implements RealizacaoExameManager
{
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;

	public Collection<ExameAnualRelatorio> getRelatorioAnual(Long estabelecimentoId, Date data)
	{
		Date inicio = DateUtil.incrementaAno(data, -1);

		Collection<ExameAnualRelatorio> examesAnuals = new ArrayList<ExameAnualRelatorio>();

		Collection<Object[]> lista = getDao().getRelatorioAnual(estabelecimentoId, inicio, data);

		ExameAnualRelatorio exameAnual;
		Map<String, ExameAnualRelatorio> examesMap = new LinkedHashMap<String, ExameAnualRelatorio>();

		if(lista != null && !lista.isEmpty())
		{
			for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
			{
				Object[] exameRealizado = it.next();

				String chave =  exameRealizado[1].toString();

				if(examesMap.containsKey(chave))
				{
					exameAnual = examesMap.get(chave);
					exameAnual.addTotalExame();
					
					if(exameRealizado[3].equals(ResultadoExame.ANORMAL.toString()))
						exameAnual.addTotalExameAnormal();

					examesMap.put(chave, exameAnual);
				}
				else
				{
					exameAnual = new ExameAnualRelatorio();
					exameAnual.setExameId((Long) exameRealizado[0]);
					exameAnual.setExameMotivo((String) exameRealizado[1]);
					exameAnual.setExameNome((String) exameRealizado[2]);

					exameAnual.addTotalExame();

					if(exameRealizado[3].equals(ResultadoExame.ANORMAL.toString()))
						exameAnual.setTotalExameAnormal(1F);

					examesMap.put(chave, exameAnual);
				}
			}
		}

		for (String chave : examesMap.keySet())
		{
			exameAnual = examesMap.get(chave);
			exameAnual.calculaAnormaisPorTotal();
			examesAnuals.add(exameAnual);
		}
		
		calculaExamesPrevistos(examesMap);
		
		return examesAnuals;
	}

	private void calculaExamesPrevistos(Map<String, ExameAnualRelatorio> examesMap) 
	{
		ExameAnualRelatorio periodicos = examesMap.get(MotivoSolicitacaoExame.PERIODICO);
		
		if (periodicos != null)
		{
			ExameAnualRelatorio admissionais = examesMap.get(MotivoSolicitacaoExame.ADMISSIONAL);		
			ExameAnualRelatorio demissionais = examesMap.get(MotivoSolicitacaoExame.DEMISSIONAL);
			float totalAdmissoes = (admissionais != null ? admissionais.getTotalExame() : 0F);
			float totalDemissoes = (demissionais != null ? demissionais.getTotalExame() : 0F);
			
			periodicos.calculaExamesPrevistos(totalAdmissoes, totalDemissoes);
		}
	}

	public Collection<RealizacaoExame> findRealizadosByColaborador(Long empresaId, Long colaboradorId)
	{
		return getDao().findRealizadosByColaborador(empresaId, colaboradorId);
	}

	public void save(SolicitacaoExame solicitacaoExame, String[] selectResultados, String[] observacoes)
	{
		Collection<ExameSolicitacaoExame> exameSolicitacaoExames = exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId());

		int i=0;
		for (ExameSolicitacaoExame exameSolicitacaoExame : exameSolicitacaoExames)
		{
			RealizacaoExame realizacaoExame = new RealizacaoExame();
			realizacaoExame.setData(solicitacaoExame.getData());
			realizacaoExame.setResultado(selectResultados[i]);
			realizacaoExame.setObservacao(observacoes[i]);

			i++;

			this.save(realizacaoExame);

			if (exameSolicitacaoExame.getRealizacaoExame() != null && exameSolicitacaoExame.getRealizacaoExame().getId() != null)
				remove(exameSolicitacaoExame.getRealizacaoExame());

			exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);

			if (exameSolicitacaoExame.getClinicaAutorizada() != null && exameSolicitacaoExame.getClinicaAutorizada().getId() == null)
				exameSolicitacaoExame.setClinicaAutorizada(null); // remove objeto transiente

			exameSolicitacaoExameManager.update(exameSolicitacaoExame);
		}
	}
	
	public void save(ExameSolicitacaoExame exameSolicitacaoExame, Date solicitacaoExameData, String resultadoExame, String observacao)
	{
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setData(solicitacaoExameData);
		realizacaoExame.setResultado(resultadoExame);
		realizacaoExame.setObservacao(observacao);

		this.save(realizacaoExame);

		if (exameSolicitacaoExame.getRealizacaoExame() != null && exameSolicitacaoExame.getRealizacaoExame().getId() != null)
			remove(exameSolicitacaoExame.getRealizacaoExame());

		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);

		if (exameSolicitacaoExame.getClinicaAutorizada() != null && exameSolicitacaoExame.getClinicaAutorizada().getId() == null)
			exameSolicitacaoExame.setClinicaAutorizada(null); // remove objeto transiente

		exameSolicitacaoExameManager.update(exameSolicitacaoExame);
	}
	
	public void marcarResultadoComoNormal(Collection<Long> realizacaoExameIds)
	{
		if (!realizacaoExameIds.isEmpty())
			getDao().marcarResultadoComoNormal(realizacaoExameIds);
	}

	public void setExameSolicitacaoExameManager(ExameSolicitacaoExameManager exameSolicitacaoExameManager)
	{
		this.exameSolicitacaoExameManager = exameSolicitacaoExameManager;
	}

	public Collection<Long> findIdsBySolicitacaoExame(long solicitacaoExameId)
	{
		return getDao().findIdsBySolicitacaoExame(solicitacaoExameId);
	}

	@Override
	public void remove(Long[] realizacaoExameIds)
	{
		if (realizacaoExameIds != null && realizacaoExameIds.length > 0)
		getDao().remove(realizacaoExameIds);
	}
}