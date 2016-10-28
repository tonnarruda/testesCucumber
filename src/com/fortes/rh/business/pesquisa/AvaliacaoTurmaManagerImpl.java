package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;

@Component
public class AvaliacaoTurmaManagerImpl extends GenericManagerImpl<AvaliacaoTurma, AvaliacaoTurmaDao> implements AvaliacaoTurmaManager
{
	private final String EMPRESA_INVALIDA = "A Avaliação solicitada não existe nesta empresa.";

	private QuestionarioManager questionarioManager;
	private PerguntaManager perguntaManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	@Autowired
	AvaliacaoTurmaManagerImpl(AvaliacaoTurmaDao avaliacaoTurmaDao) {
		setDao(avaliacaoTurmaDao);
	}
	
	public Collection<AvaliacaoTurma> findToListByEmpresa(Long empresaId, int page, int pagingSize)
	{
		return getDao().findToList(empresaId, page, pagingSize);
	}

	public void delete(Long avaliacaoTurmaId, Long empresaId) throws Exception
	{
		verificarEmpresaValida(avaliacaoTurmaId, empresaId);

		AvaliacaoTurma avaliacaoTurma = getDao().findByIdProjection(avaliacaoTurmaId);

		//Verifica se existem colaboradores cadastrados na avaliacao
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(avaliacaoTurma.getQuestionario().getId());

		//Se existir ele retorna uma exceção para a action informando que não é possível excluir
		if (colaboradorQuestionarios.size() > 0)
		{
			throw new Exception("Não foi possível excluir esta Avaliação, pois esta possui colaboradores.");
		}

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			questionarioManager.removerPerguntasDoQuestionario(avaliacaoTurma.getQuestionario().getId());
			getDao().remove(avaliacaoTurma);
			questionarioManager.remove(avaliacaoTurma.getQuestionario().getId());

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}
	}

	public AvaliacaoTurma findByIdProjection(Long avaliacaoTurmaId)
	{
		try
		{
			return getDao().findByIdProjection(avaliacaoTurmaId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void update(AvaliacaoTurma avaliacaoTurma, Questionario questionario, Long empresaId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			verificarEmpresaValida(avaliacaoTurma.getId(), empresaId);
			getDao().update(avaliacaoTurma);

			questionarioManager.updateQuestionario(questionario);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível editar esta Avaliação de Curso.");
		}
	}

	public void verificarEmpresaValida(Long avaliacaoTurmaId, Long empresaId) throws Exception
	{
		if(!getDao().verificaEmpresaDoQuestionario(avaliacaoTurmaId, empresaId))
			throw new Exception(EMPRESA_INVALIDA);
	}

	public AvaliacaoTurma save(AvaliacaoTurma avaliacaoTurma, Questionario questionario, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			questionario.setEmpresa(empresa);
			questionario.setTipo(TipoQuestionario.AVALIACAOTURMA);
			questionario = questionarioManager.save(questionario);

			avaliacaoTurma.setQuestionario(questionario);
			getDao().save(avaliacaoTurma);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);

			throw e;
		}

		return avaliacaoTurma;
	}

	public void clonarAvaliacaoTurma(Long avaliacaoTurmaId, Long... empresasIds) throws Exception
	{
		AvaliacaoTurma avaliacaoTurmaClonado;
		AvaliacaoTurma avaliacaoTurma = findByIdProjection(avaliacaoTurmaId);
		Questionario questionario = avaliacaoTurma.getQuestionario();

		try
		{
			for (Long empresaId : empresasIds)
			{
		    	Questionario questionarioClonado = questionarioManager.clonarQuestionario(questionario, empresaId);
		    	avaliacaoTurmaClonado = (AvaliacaoTurma) avaliacaoTurma.clone();
		    	avaliacaoTurmaClonado.setId(null);
		    	avaliacaoTurmaClonado.setQuestionario(questionarioClonado);
		    	save(avaliacaoTurmaClonado);

		    	perguntaManager.clonarPerguntas(questionario.getId(), questionarioClonado, null);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void setPerguntaManager(PerguntaManager perguntaManager)
	{
		this.perguntaManager = perguntaManager;
	}

	public Long getIdByQuestionario(Long questionarioId)
	{
		return getDao().getIdByQuestionario(questionarioId);
	}

	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Collection<AvaliacaoTurma> findAllSelect(Boolean ativa, Long... empresaId)
	{
		return getDao().findAllSelect(ativa, empresaId);
	}

	public Collection<AvaliacaoTurma> findByTurma(Long turmaId) {
		return getDao().findByTurma(turmaId);
	}
}