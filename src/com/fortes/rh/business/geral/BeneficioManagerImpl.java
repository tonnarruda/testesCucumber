package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;

public class BeneficioManagerImpl extends GenericManagerImpl<Beneficio, BeneficioDao> implements BeneficioManager
{
	private PlatformTransactionManager transactionManager;
	private HistoricoBeneficioManager historicoBeneficioManager;

	public Beneficio findBeneficioById(Long id)
	{
		return getDao().findBeneficioById(id);
	}

	public void saveHistoricoBeneficio(Beneficio beneficio, HistoricoBeneficio historico) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			beneficio = save(beneficio);
			historico.setBeneficio(beneficio);
			historicoBeneficioManager.save(historico);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			beneficio.setId(null);
			historico = null;
			throw e;
		}

	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void setHistoricoBeneficioManager(HistoricoBeneficioManager historicoBeneficioManager)
	{
		this.historicoBeneficioManager = historicoBeneficioManager;
	}

	public Collection<Beneficio> getBeneficiosByHistoricoColaborador(Long historicoId)
	{
		Collection<Beneficio> beneficios = new ArrayList<Beneficio>();

		List<Long> beneficioIds = getDao().getBeneficiosByHistoricoColaborador(historicoId);

		Beneficio beneficio;

		for (Long beneficioId : beneficioIds)
		{
			beneficio = new Beneficio();
			beneficio.setId(beneficioId);
			beneficios.add(beneficio);
		}

		return beneficios;
	}
}