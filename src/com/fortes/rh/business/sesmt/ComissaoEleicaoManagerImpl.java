package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoEleicaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@Component
public class ComissaoEleicaoManagerImpl extends GenericManagerImpl<ComissaoEleicao, ComissaoEleicaoDao> implements ComissaoEleicaoManager
{
	@Autowired private PlatformTransactionManager transactionManager;
	
	@Autowired
	ComissaoEleicaoManagerImpl(ComissaoEleicaoDao fooDao) {
		setDao(fooDao);
	}
	
	public Collection<ComissaoEleicao> findByEleicao(Long eleicaoId)
	{
		return getDao().findByEleicao(eleicaoId);
	}

	public void save(String[] colaboradorsCheck, Eleicao eleicao) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			CollectionUtil<Colaborador> util = new CollectionUtil<Colaborador>();
			Collection<Colaborador> colaboradores = util.convertArrayStringToCollection(Colaborador.class, colaboradorsCheck);
			Collection<ComissaoEleicao> comissaoEleicaos = findByEleicao(eleicao.getId());

			for (Colaborador colaborador : colaboradores)
			{
				ComissaoEleicao comissaoEleicao = new ComissaoEleicao(eleicao, colaborador);
				
				if(!comissaoEleicaos.contains(comissaoEleicao))
					save(comissaoEleicao);
			}

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}

	public void updateFuncao(String[] comissaoEleicaoIds, String[] funcaoComissaos) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			if(comissaoEleicaoIds != null)
			{
				Long[] ids = LongUtil.arrayStringToArrayLong(comissaoEleicaoIds);
				for (int i = 0; i < ids.length; i++)
				{
					getDao().updateFuncao(ids[i], funcaoComissaos[i]);
				}
			}
			
			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}	
	}

	public void removeByEleicao(Long eleicaoId)
	{
		getDao().removeByEleicao(eleicaoId);
	}
}