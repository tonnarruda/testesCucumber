package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;

@Component
public class ExameSolicitacaoExameManagerImpl extends GenericManagerImpl<ExameSolicitacaoExame, ExameSolicitacaoExameDao> implements ExameSolicitacaoExameManager
{
	@Autowired
	ExameSolicitacaoExameManagerImpl(ExameSolicitacaoExameDao exameSolicitacaoExameDao) {
		setDao(exameSolicitacaoExameDao);
	}

	public void removeAllBySolicitacaoExame(Long solicitacaoExameId)
	{
		getDao().removeAllBySolicitacaoExame(solicitacaoExameId);
	}

	public void save(SolicitacaoExame solicitacaoExame, String[] exameIds, String[] selectClinicas, Integer[] periodicidades)
	{
		if (exameIds != null && exameIds.length > 0 && selectClinicas != null && selectClinicas.length > 0)
		{
			if(exameIds.length != selectClinicas.length)
				selectClinicas = Arrays.copyOf(selectClinicas, exameIds.length);

			for (int i=0; i<exameIds.length; i++)
			{
				ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();

				Exame exameTmp = new Exame();
				exameTmp.setId(Long.valueOf(exameIds[i]));

				ClinicaAutorizada clinicaAutorizadaTmp = null;

				if (StringUtils.isNotBlank(selectClinicas[i]))
				{
					clinicaAutorizadaTmp = new ClinicaAutorizada();
					clinicaAutorizadaTmp.setId(Long.valueOf(selectClinicas[i]));
				}

				exameSolicitacaoExame.setExame(exameTmp);
				exameSolicitacaoExame.setClinicaAutorizada(clinicaAutorizadaTmp);
				exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
				
				if (periodicidades != null && i < periodicidades.length && periodicidades[i] != null)
					exameSolicitacaoExame.setPeriodicidade(periodicidades[i]);

				getDao().save(exameSolicitacaoExame);
			}
		}
	}

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long[] solicitacaoExameIds)
	{
		if(solicitacaoExameIds == null || solicitacaoExameIds.length == 0)
			return new ArrayList<ExameSolicitacaoExame>();

		return getDao().findBySolicitacaoExame(solicitacaoExameIds);
	}

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId, Boolean asoPadrao)
	{
		return getDao().findBySolicitacaoExame(solicitacaoExameId, asoPadrao);
	}

	public boolean verificaExisteResultado(Collection<ExameSolicitacaoExame> exameSolicitacaoExames)
	{
		boolean retorno = false;
		if (exameSolicitacaoExames != null)
		{
			for (ExameSolicitacaoExame exameSolicitacaoExame : exameSolicitacaoExames)
			{
				if (exameSolicitacaoExame.getRealizacaoExame() != null && StringUtils.isNotBlank(exameSolicitacaoExame.getRealizacaoExame().getResultado())
							&& exameSolicitacaoExame.getRealizacaoExame().getResultado().contains("NORMAL"))
				{
					return true;
				}
			}
		}
		return retorno;
	}
	
	public ExameSolicitacaoExame findDataSolicitacaoExame(Long colaboradorId, Long candidatoId, Long exameId)
	{
		return getDao().findDataSolicitacaoExame(colaboradorId, candidatoId, exameId);
	}

	public ExameSolicitacaoExame findIdColaboradorOUCandidato(Long solicitacaoExameId, Long exameId)
	{
		return getDao().findIdColaboradorOUCandidato(solicitacaoExameId, exameId);
	}

}