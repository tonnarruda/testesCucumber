package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public class AproveitamentoAvaliacaoCursoManagerImpl extends GenericManagerImpl<AproveitamentoAvaliacaoCurso, AproveitamentoAvaliacaoCursoDao> implements AproveitamentoAvaliacaoCursoManager
{
	private PlatformTransactionManager transactionManager;

	public void saveNotas(Long[] colaboradorTurmaIds, String[] notas, AvaliacaoCurso avaliacaoCurso) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			for (int i=0; i<colaboradorTurmaIds.length; i++)
			{
				Double valor = 0.0;
//				if(notas[i] == null || !notas[i].equals("") )
//				{
					if (!notas[i].equals(""))
						valor = Double.parseDouble(notas[i].replace(',', '.'));
					
					ColaboradorTurma colabTurma = new ColaboradorTurma();
					colabTurma.setId(colaboradorTurmaIds[i]);
					
					AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso(colabTurma, avaliacaoCurso, valor);
					
					saveOrUpdate(aproveitamento);
//				}
//				else
//				{
//					remove(colaboradorTurmaIds[i], avaliacaoCurso);
//				}
			}

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}

	private void remove(Long colaboradorTurmaId, AvaliacaoCurso avaliacaoCurso) {

		AproveitamentoAvaliacaoCurso aproveitamento = getDao().findByColaboradorTurmaAvaliacaoId(colaboradorTurmaId, avaliacaoCurso.getId());
		
		if (aproveitamento != null)
			getDao().remove(aproveitamento);
	}

	private void saveOrUpdate(AproveitamentoAvaliacaoCurso aproveitamento)
	{
		AproveitamentoAvaliacaoCurso resultado = getDao().findByColaboradorTurmaAvaliacaoId(aproveitamento.getColaboradorTurma().getId(), aproveitamento.getAvaliacaoCurso().getId());
		if (resultado == null)
			getDao().save(aproveitamento);
		else
		{
			aproveitamento.setId(resultado.getId());
			getDao().update(aproveitamento);
		}
	}

	public Collection<AproveitamentoAvaliacaoCurso> findNotas(Long avaliacaoId, Long[] colaboradoresIds)
	{
		return getDao().findNotas(avaliacaoId, colaboradoresIds);
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<Long> find(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado)
	{
		return getDao().find(id, qtdAvaliacao, wherePor, aprovado);
	}

	public Collection<Long> find(Long[] cursoIds, Integer qtdAvaliacao, boolean aprovado)
	{
		return getDao().find(cursoIds, qtdAvaliacao, aprovado);
	}

	public Collection<Long> findColaboradores(Long id, Integer qtdAvaliacao, String wherePor, boolean aprovado)
	{
		return getDao().findColaboradores(id, qtdAvaliacao, wherePor, aprovado);
	}

	public Collection<Long> findAprovadosComAvaliacao(Collection<Long> cursoIds, Date dataIni, Date dataFim)
	{
		return getDao().findAprovadosComAvaliacao(cursoIds, dataIni, dataFim);
	}

	public Collection<Long> findReprovados(Date dataIni, Date dataFim, Long empresaId)
	{
		return getDao().findReprovados(dataIni, dataFim, empresaId);
	}

	public void remove(Long cursoId, String[] avaliacaoCursoIds)
	{
		getDao().remove(cursoId, avaliacaoCursoIds);
	}

	public void removeByTurma(Long turmaId)
	{
		getDao().removeByTurma(turmaId);
	}

	public void saveNotas(ColaboradorTurma colaboradorTurma, String[] notas, Long[] avaliacaoCursoIds)
	{
		for (int i = 0; i < notas.length; i++)
		{
			if(!notas[i].equals(""))
			{
				Double valor = Double.parseDouble(notas[i].replace(',', '.'));
				
				AvaliacaoCurso avaliacaoCurso = new AvaliacaoCurso();
				avaliacaoCurso.setId(avaliacaoCursoIds[i]);
				AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso(colaboradorTurma, avaliacaoCurso, valor);
				saveOrUpdate(aproveitamento);
			}
		}
	}
		/**
	 * retorna ColaboradorTurma com id,nota */
	public Collection<ColaboradorTurma> findColaboradorTurma(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado) {
		
		return getDao().findColaboradorTurma(id, qtdAvaliacao, wherePor, aprovado);
	}

	public Collection<AproveitamentoAvaliacaoCurso> findByColaboradorCurso(Long colaboradorId, Long cursoId) {
		return getDao().findByColaboradorCurso(colaboradorId, cursoId);
	}
}