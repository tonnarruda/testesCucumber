package com.fortes.rh.business.desenvolvimento;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.dao.desenvolvimento.TurmaAvaliacaoTurmaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

public class TurmaAvaliacaoTurmaManagerImpl extends GenericManagerImpl<TurmaAvaliacaoTurma, TurmaAvaliacaoTurmaDao> implements TurmaAvaliacaoTurmaManager
{
	private TurmaManager turmaManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	public void salvarAvaliacaoTurmas(Long turmaId, Long[] avaliacaoTurmaIds) 
	{
		if (avaliacaoTurmaIds != null)
		{
			for (Long avaliacaoTurmaId : avaliacaoTurmaIds) 
			{
				TurmaAvaliacaoTurma turmaAvaliacaoTurma = new TurmaAvaliacaoTurma();
				turmaAvaliacaoTurma.setProjectionAvaliacaoTurmaId(avaliacaoTurmaId);
				turmaAvaliacaoTurma.setProjectionTurmaId(turmaId);
				turmaAvaliacaoTurma.setLiberada(false);
				getDao().save(turmaAvaliacaoTurma);
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

	public void setTurmaManager(TurmaManager turmaManager) {
		this.turmaManager = turmaManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager) {
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}
}