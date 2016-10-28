package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.SpringUtil;

@Component
public class PesquisaManagerImpl extends GenericManagerImpl<Pesquisa, PesquisaDao> implements PesquisaManager
{
	private final String EMPRESA_INVALIDA = "A Pesquisa solicitada não existe nesta empresa.";

	private QuestionarioManager questionarioManager;
	private PerguntaManager perguntaManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	
	@Autowired
	PesquisaManagerImpl(PesquisaDao dao) {
		setDao(dao);
	}

	public Collection<Pesquisa> findToListByEmpresa(Long empresaId, int page, int pagingSize, String questionarioTitulo, char questionarioLiberado)
	{
		Boolean liberado;
		switch(questionarioLiberado)
   		{
   			case 'L': liberado = true; break;
   			case 'N': liberado = false; break;
   			default: liberado = null;
   		}
		
		return getDao().findToList(empresaId, page, pagingSize, questionarioTitulo, liberado);
	}

	public void delete(Long pesquisaId, Long empresaId) throws Exception
	{
		verificarEmpresaValida(pesquisaId, empresaId);

//		Retorna a pesquisa com id do questionário.
		Pesquisa pesquisa = findByIdProjection(pesquisaId);

//		Verifica se existem colaboradores cadastrados na pesquisa.
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(pesquisa.getQuestionario().getId());

//		Se existir ele retorna uma exceção para a action informando que não é possível excluir a pesquisa.
		if (colaboradorQuestionarios.size() > 0)
			throw new Exception("Não foi possível excluir a pesquisa. Pesquisa possui colaboradores.");
		
		ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");  
		colaboradorRespostaManager.removeByQuestionarioId(pesquisa.getQuestionario().getId());
		
		questionarioManager.removerPerguntasDoQuestionario(pesquisa.getQuestionario().getId());
		getDao().removerPesquisaDoQuestionario(pesquisa.getQuestionario().getId());
		questionarioManager.remove(pesquisa.getQuestionario().getId());
	}

	public Pesquisa findByIdProjection(Long pesquisaId)
	{
		try
		{
			return getDao().findByIdProjection(pesquisaId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void update(Pesquisa pesquisa, Questionario questionario, Long empresaId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			verificarEmpresaValida(pesquisa.getId(), empresaId);
			getDao().update(pesquisa);

			questionarioManager.updateQuestionario(questionario);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível editar esta Pesquisa.");
		}
	}

	public void verificarEmpresaValida(Long pesquisaId, Long empresaId) throws Exception
	{
		if(!getDao().verificaEmpresaDoQuestionario(pesquisaId, empresaId))
			throw new Exception(EMPRESA_INVALIDA);
	}

	public Pesquisa save(Pesquisa pesquisa, Questionario questionario, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			questionario.setEmpresa(empresa);
			questionario.setTipo(TipoQuestionario.PESQUISA);
			questionario = questionarioManager.save(questionario);

			pesquisa.setQuestionario(questionario);
			pesquisa = getDao().save(pesquisa);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);

			throw e;
		}

		return pesquisa;
	}

	public Pesquisa clonarPesquisa(Long pesquisaId, Long[] empresasIds) throws Exception
	{
		Pesquisa pesquisaClonada = null;

		try
		{
			Pesquisa pesquisa = findByIdProjection(pesquisaId);
	    	Questionario questionario = pesquisa.getQuestionario();
	    	
	    	if(empresasIds != null && empresasIds.length > 0)
	    	{
	    		for (Long empresaId : empresasIds) 
	    		{
	    			Questionario questionarioClonado = questionarioManager.clonarQuestionario(questionario, empresaId);
	    			pesquisaClonada = clonarPesquisaQuestionario(pesquisa, questionario, questionarioClonado);
				}
	    	}
	    	else
	    	{
	    		Questionario questionarioClonado = questionarioManager.clonarQuestionario(questionario, null);
	    		pesquisaClonada = clonarPesquisaQuestionario(pesquisa, questionario, questionarioClonado);
	    	}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível clonar a pesquisa, devido a um erro interno.<br><br> Entre em contato com o administrador do sistema.");
		}

    	return pesquisaClonada;
	}

	private Pesquisa clonarPesquisaQuestionario(Pesquisa pesquisa, Questionario questionario, Questionario questionarioClonado)
	{
		Pesquisa pesquisaClonada;
		
		pesquisaClonada = (Pesquisa) pesquisa.clone();
		pesquisaClonada.setId(null);
		pesquisaClonada.setQuestionario(questionarioClonado);
		save(pesquisaClonada);
		
		perguntaManager.clonarPerguntas(questionario.getId(), questionarioClonado, null);
		return pesquisaClonada;
	}

	public Pesquisa findParaSerRespondida(Long pesquisaId)
	{
		Pesquisa pesquisa = getDao().findById(pesquisaId);
		Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(pesquisaId);

		pesquisa.getQuestionario().setPerguntas(perguntas);

		return pesquisa;
	}

	public boolean existePesquisaParaSerRespondida(String colaboradorCodigoAC, Long empresaId) 
	{
		return getDao().existePesquisaParaSerRespondida(colaboradorCodigoAC, empresaId);
	}

	public Pesquisa findByQuestionario(Long questionarioId)
	{
		return getDao().findByQuestionario(questionarioId);
	}
	
	public Long getIdByQuestionario(Long questionarioId)
	{
		return getDao().getIdByQuestionario(questionarioId);
	}
	
	public Integer getCount(Long empresaId, String questionarioTitulo)
	{
		return getDao().getCount(empresaId, questionarioTitulo);
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

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}
}