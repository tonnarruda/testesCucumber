package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.FichaMedicaDao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Questionario;

@Component
public class FichaMedicaManagerImpl extends GenericManagerImpl<FichaMedica, FichaMedicaDao> implements FichaMedicaManager
{
	private final String EMPRESA_INVALIDA = "A Avaliação solicitada não existe nesta empresa.";

	@Autowired private QuestionarioManager questionarioManager;
	@Autowired private PerguntaManager perguntaManager;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	@Autowired
	FichaMedicaManagerImpl(FichaMedicaDao dao) {
		setDao(dao);
	}
	
	public Collection<FichaMedica> findToListByEmpresa(Long empresaId, int page, int pagingSize)
	{
		return getDao().findToList(empresaId, page, pagingSize);
	}

	public void delete(Long fichaMedicaId, Long empresaId) throws Exception
	{
		verificarEmpresaValida(fichaMedicaId, empresaId);

		FichaMedica fichaMedica = getDao().findByIdProjection(fichaMedicaId);

		//Verifica se existem colaboradores cadastrados na avaliacao
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(fichaMedica.getQuestionario().getId());

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
			questionarioManager.removerPerguntasDoQuestionario(fichaMedica.getQuestionario().getId());
			getDao().remove(fichaMedica);
			questionarioManager.remove(fichaMedica.getQuestionario().getId());

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}
	}

	public FichaMedica findByIdProjection(Long fichaMedicaId)
	{
		try
		{
			return getDao().findByIdProjection(fichaMedicaId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void update(FichaMedica fichaMedica, Questionario questionario, Long empresaId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			verificarEmpresaValida(fichaMedica.getId(), empresaId);
			getDao().update(fichaMedica);

			questionarioManager.updateQuestionario(questionario);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível editar esta Ficha Médica de Curso.");
		}
	}

	public void verificarEmpresaValida(Long fichaMedicaId, Long empresaId) throws Exception
	{
		if(!getDao().verificaEmpresaDoQuestionario(fichaMedicaId, empresaId))
			throw new Exception(EMPRESA_INVALIDA);
	}

	public FichaMedica save(FichaMedica fichaMedica, Questionario questionario, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			questionario.setEmpresa(empresa);
			questionario.setTipo(TipoQuestionario.FICHAMEDICA);
			questionario = questionarioManager.save(questionario);

			fichaMedica.setQuestionario(questionario);
			getDao().save(fichaMedica);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);

			throw e;
		}

		return fichaMedica;
	}

	public void clonarFichaMedica(Long fichaMedicaId, Long... empresasIds) throws Exception
	{
		FichaMedica fichaMedicaClonada;
		FichaMedica fichaMedica = findByIdProjection(fichaMedicaId);
		Questionario questionario = fichaMedica.getQuestionario();

		try
		{
			for (Long empresaId : empresasIds)
			{
				Questionario questionarioClonado = questionarioManager.clonarQuestionario(questionario, empresaId);
		    	fichaMedicaClonada = (FichaMedica) fichaMedica.clone();
		    	fichaMedicaClonada.setId(null);
		    	fichaMedicaClonada.setQuestionario(questionarioClonado);
		    	save(fichaMedicaClonada);
	
		    	perguntaManager.clonarPerguntas(questionario.getId(), questionarioClonado, null);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public Long getIdByQuestionario(Long questionarioId)
	{
		return getDao().getIdByQuestionario(questionarioId);
	}

	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public Collection<FichaMedica> findAllSelect(Long empresaId, Boolean ativa)
	{
		return getDao().findAllSelect(empresaId, ativa);
	}

	public Collection<FichaMedica> findByColaborador(Long empresaId, Long colaboradorId)
	{
		return getDao().findByColaborador(empresaId, colaboradorId);
	}

	public FichaMedica findByQuestionario(Long questionarioId)
	{
		return getDao().findByQuestionario(questionarioId);
	}
}