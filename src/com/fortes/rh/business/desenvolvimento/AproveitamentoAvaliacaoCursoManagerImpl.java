package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

@Component
public class AproveitamentoAvaliacaoCursoManagerImpl extends GenericManagerImpl<AproveitamentoAvaliacaoCurso, AproveitamentoAvaliacaoCursoDao> implements AproveitamentoAvaliacaoCursoManager
{
	@Autowired private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	@Autowired private ColaboradorTurmaManager colaboradorTurmaManager;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private CertificacaoManager certificacaoManager;
	
	@Autowired
	AproveitamentoAvaliacaoCursoManagerImpl(AproveitamentoAvaliacaoCursoDao dao) {
		setDao(dao);
	}

	public void saveNotas(String[] colabTurmaId_notas, AvaliacaoCurso avaliacaoCurso, boolean controlaVencimentoPorCertificacao) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{
			for (String colabTurmaId_nota : colabTurmaId_notas)	{
				String colaboradorTurmaId = colabTurmaId_nota.split("_")[0];
				
				Double valor = null;
				if (colabTurmaId_nota.split("_").length > 1)
					valor = Double.parseDouble((colabTurmaId_nota.split("_")[1]).replace(',', '.'));
				
				ColaboradorTurma colabTurma = new ColaboradorTurma();
				colabTurma.setId(new Long(colaboradorTurmaId));
				
				AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso(colabTurma, avaliacaoCurso, valor);
				saveOrUpdate(aproveitamento, colaboradorTurmaManager, controlaVencimentoPorCertificacao);
			}
			
			transactionManager.commit(status);
			
		}catch (Exception e){
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}

	private void saveOrUpdate(AproveitamentoAvaliacaoCurso aproveitamento, ColaboradorTurmaManager colaboradorTurmaManager, boolean controlaVencimentoPorCertificacao){
		ColaboradorTurma colaboradorTurma = colaboradorTurmaManager.findByProjection(aproveitamento.getColaboradorTurma().getId());
		AproveitamentoAvaliacaoCurso resultado = getDao().findByColaboradorTurmaAvaliacaoId(aproveitamento.getColaboradorTurma().getId(), aproveitamento.getAvaliacaoCurso().getId());
		
		if (resultado == null){
			getDao().save(aproveitamento);
		}else{
			aproveitamento.setId(resultado.getId());
			getDao().update(aproveitamento);
		}

		getDao().getHibernateTemplateByGenericDao().flush();
		boolean colaboradorTurmaAprovado = colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
		colaboradorTurma.setAprovado(colaboradorTurmaAprovado);
		checaCertificacao(controlaVencimentoPorCertificacao, colaboradorTurma, colaboradorTurmaManager);
	}

	private void checaCertificacao(boolean controlaVencimentoPorCertificacao, ColaboradorTurma colaboradorTurma, ColaboradorTurmaManager colaboradorTurmaManager) throws DataAccessException {
		if(controlaVencimentoPorCertificacao){	
			getDao().getHibernateTemplateByGenericDao().flush();
			if(colaboradorTurma.isAprovado())
				colaboradorCertificacaoManager.certificaColaborador(colaboradorTurma.getId(), null, null, certificacaoManager);
			else
				colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId());
		}
	}

	public Collection<AproveitamentoAvaliacaoCurso> findNotas(Long avaliacaoId, Long[] colaboradoresTurmaIds)
	{
		 return getDao().findNotas(avaliacaoId, colaboradoresTurmaIds);
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
	
	public void removeByColaboradorTurma(Long colaboradorTurmaId)
	{
		getDao().removeByColaboradorTurma(colaboradorTurmaId);
	}

	public void saveNotas(ColaboradorTurma colaboradorTurma, String[] notas, Long[] avaliacaoCursoIds, boolean controlaVencimentoPorCertificacao)
	{
		for (int i = 0; i < notas.length; i++)
		{
			if(!notas[i].equals(""))
			{
				Double valor = Double.parseDouble(notas[i].replace(',', '.'));
				
				AvaliacaoCurso avaliacaoCurso = new AvaliacaoCurso();
				avaliacaoCurso.setId(avaliacaoCursoIds[i]);
				AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso(colaboradorTurma, avaliacaoCurso, valor);
				saveOrUpdate(aproveitamento, colaboradorTurmaManager, controlaVencimentoPorCertificacao);
				getDao().getHibernateTemplateByGenericDao().flush();
			}
		}
	}
		/**
	 * retorna ColaboradorTurma com id,nota */
	public Collection<ColaboradorTurma> findColaboradorTurma(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado) 
	{
		return getDao().findColaboradorTurma(id, qtdAvaliacao, wherePor, aprovado);
	}

	public Collection<AproveitamentoAvaliacaoCurso> findByColaboradorCurso(Long colaboradorId, Long cursoId) 
	{
		return getDao().findByColaboradorCurso(colaboradorId, cursoId);
	}
}