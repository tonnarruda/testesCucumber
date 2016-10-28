package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.dao.pesquisa.EntrevistaDao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;

@Component
public class EntrevistaManagerImpl extends GenericManagerImpl<Entrevista, EntrevistaDao> implements EntrevistaManager
{
	private final String EMPRESA_INVALIDA = "A Entrevista solicitada não existe nesta empresa.";

	private QuestionarioManager questionarioManager;
	private PerguntaManager perguntaManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	@Autowired
	EntrevistaManagerImpl(EntrevistaDao dao) {
		setDao(dao);
	}
	
	public Collection<Entrevista> findToListByEmpresa(Long empresaId, int page, int pagingSize)
	{
		return getDao().findToList(empresaId, page, pagingSize);
	}

	public void delete(Long entrevistaId, Long empresaId) throws Exception
	{
		verificarEmpresaValida(entrevistaId, empresaId);

		//Retorna a entrevista com id do questionário.
		Entrevista entrevista = getDao().findByIdProjection(entrevistaId);

		//Verifica se existem colaboradores cadastrados na entrevista.
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(entrevista.getQuestionario().getId());

		//Se existir ele retorna uma exceção para a action informando que não é possível excluir a entrevista.
		if (colaboradorQuestionarios.size() > 0)
			throw new Exception("Não foi possível excluir a entrevista. Entrevista possui colaboradores.");

		questionarioManager.removerPerguntasDoQuestionario(entrevista.getQuestionario().getId());
		getDao().remove(entrevista);
		questionarioManager.remove(entrevista.getQuestionario().getId());
	}

	public Entrevista findByIdProjection(Long entrevistaId)
	{
		try
		{
			return getDao().findByIdProjection(entrevistaId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void update(Entrevista entrevista, Questionario questionario, Long empresaId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			verificarEmpresaValida(entrevista.getId(), empresaId);
			getDao().update(entrevista);

			questionarioManager.updateQuestionario(questionario);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível editar esta Entrevista.");
		}
	}

	public void verificarEmpresaValida(Long entrevistaId, Long empresaId) throws Exception
	{
		if(!getDao().verificaEmpresaDoQuestionario(entrevistaId, empresaId))
			throw new Exception(EMPRESA_INVALIDA);
	}

	public Entrevista save(Entrevista entrevista, Questionario questionario, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			questionario.setEmpresa(empresa);
			questionario.setTipo(TipoQuestionario.ENTREVISTA);
			questionario = questionarioManager.save(questionario);

			entrevista.setQuestionario(questionario);
			entrevista = getDao().save(entrevista);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);

			throw e;
		}

		return entrevista;
	}
	
	public void clonarEntrevista(Long entrevistaId, Long... empresasIds) throws Exception
	{
		Entrevista entrevista = findByIdProjection(entrevistaId);
		
		for (Long empresaId : empresasIds)
		{
			Empresa empresa = new Empresa();
			empresa.setId(empresaId);
			
	    	Questionario questionario = (Questionario) questionarioManager.findById(entrevista.getQuestionario().getId()).clone();
	    	questionario = entrevista.getQuestionario();
	    	questionario.setEmpresa(empresa);
	    	Questionario questionarioClonado = questionarioManager.clonarQuestionario(questionario, empresaId);
	    	
	    	Entrevista entrevistaClonada = (Entrevista) entrevista.clone();
	    	entrevistaClonada.setId(null);
	    	entrevistaClonada.setQuestionario(questionarioClonado);
	    	save(entrevistaClonada);

	    	perguntaManager.clonarPerguntas(questionario.getId(), questionarioClonado, null);
		}
	}

	public Entrevista findParaSerRespondida(Long entrevistaId)
	{
		Entrevista entrevista = getDao().findById(entrevistaId);
		Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(entrevistaId);

		entrevista.getQuestionario().setPerguntas(perguntas);

		return entrevista;
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

	public Collection<Entrevista> findAllSelect(Long empresaId, Boolean ativa)
	{
		return getDao().findAllSelect(empresaId, ativa);
	}
}