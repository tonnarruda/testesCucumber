package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;

public class BairroManagerImpl extends GenericManagerImpl<Bairro, BairroDao> implements BairroManager
{
	private PlatformTransactionManager transactionManager;
	private SolicitacaoManager solicitacaoManager;
	
	public Collection<Bairro> list(int page, int pagingSize, Bairro bairro)
	{
		return getDao().list(page, pagingSize, bairro);
	}
	
	@TesteAutomatico
	public Integer getCount(Bairro bairro)
	{
		return getDao().getCount(bairro);
	}

	public boolean existeBairro(Bairro bairro)
	{
		return getDao().existeBairro(bairro);
	}

	public Collection<Bairro> findAllSelect(Long... cidadeIds)
	{
		return getDao().findAllSelect(cidadeIds);
	}

	public Collection<Bairro> getBairrosBySolicitacao(Long solicitacaoId)
	{
		return getDao().getBairrosBySolicitacao(solicitacaoId);
	}

	public Collection<Bairro> getBairrosByIds(Long[] bairrosIds)
	{
		return getDao().getBairrosByIds(bairrosIds);
	}

	public void migrarRegistros(Bairro bairro, Bairro bairroDestino)throws Exception
	{		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try
		{
			bairro = findByIdProjection(bairro.getId());
			bairroDestino = findByIdProjection(bairroDestino.getId());
	
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			CandidatoManager candidatoManager = (CandidatoManager) SpringUtil.getBean("candidatoManager");
			
			colaboradorManager.migrarBairro(bairro.getNome(), bairroDestino.getNome());
			candidatoManager.migrarBairro(bairro.getNome(), bairroDestino.getNome());
			solicitacaoManager.migrarBairro(bairro.getId(), bairroDestino.getId());
			
			getDao().remove(bairro.getId());
			
			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}
	}

	public Bairro findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<Bairro> findByCidade(Cidade cidade)
	{
		if(cidade != null && cidade.getId() != null)
			return findAllSelect(cidade.getId());
		
		return new ArrayList<Bairro>();
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public String getArrayBairros()
	{
		Collection<String> bairros = getDao().findBairrosNomes();
		if(bairros == null || bairros.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(bairros);
	}
}