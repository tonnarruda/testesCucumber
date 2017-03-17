package com.fortes.rh.business.desenvolvimento;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.dao.desenvolvimento.TurmaAvaliacaoTurmaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.util.CollectionUtil;

@Component
public class TurmaAvaliacaoTurmaManagerImpl extends GenericManagerImpl<TurmaAvaliacaoTurma, TurmaAvaliacaoTurmaDao> implements TurmaAvaliacaoTurmaManager
{
	@Autowired private TurmaManager turmaManager;
	@Autowired private AvaliacaoTurmaManager avaliacaoTurmaManager;
	@Autowired private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	@Autowired
	TurmaAvaliacaoTurmaManagerImpl(TurmaAvaliacaoTurmaDao dao) {
		setDao(dao);
	}
	
	public void salvarAvaliacaoTurmas(Long turmaId, Long[] avaliacaoTurmaIds) 
	{
		removeByTurma(turmaId, avaliacaoTurmaIds);
		
		Collection<AvaliacaoTurma> avaliacoesTurmas = avaliacaoTurmaManager.findByTurma(turmaId);
		Long[] AvaliacaoTurmaIdsOld = new CollectionUtil<AvaliacaoTurma>().convertCollectionToArrayIds(avaliacoesTurmas);
		
		if (avaliacaoTurmaIds != null)
		{
			for (Long avaliacaoTurmaId : avaliacaoTurmaIds) 
			{
				if(!Arrays.asList(AvaliacaoTurmaIdsOld).contains(avaliacaoTurmaId)) {
					TurmaAvaliacaoTurma turmaAvaliacaoTurma = new TurmaAvaliacaoTurma();
					turmaAvaliacaoTurma.setProjectionAvaliacaoTurmaId(avaliacaoTurmaId);
					turmaAvaliacaoTurma.setProjectionTurmaId(turmaId);
					turmaAvaliacaoTurma.setLiberada(false);
					getDao().save(turmaAvaliacaoTurma);
				}
			}
		}
	}

	public boolean verificaAvaliacaoliberada(Long turmaId) 
	{
		return getDao().verificaAvaliacaoliberada(turmaId);
	}

	public void updateLiberada(Long turmaId, Long avaliacaoTurmaId, Boolean liberada, Long empresaId) 
	{
		if (liberada) {
			Turma turma = turmaManager.findByIdProjection(turmaId);
			AvaliacaoTurma avaliacaoTurma = avaliacaoTurmaManager.findByIdProjection(avaliacaoTurmaId);
			gerenciadorComunicacaoManager.enviarAvisoEmailLiberacao(turma, avaliacaoTurma, empresaId);
		}
		getDao().updateLiberada(turmaId, avaliacaoTurmaId, liberada);
	}
	
	public void removeByTurma(Long turmaId, Long[] avaliacaoTurmaIdsQueNaoDevemSerRemovidas)
	{
		getDao().removeByTurma(turmaId, avaliacaoTurmaIdsQueNaoDevemSerRemovidas);
	}	
}