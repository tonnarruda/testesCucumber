package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.util.DateUtil;

@Component
public class HistoricoExtintorManagerImpl extends GenericManagerImpl<HistoricoExtintor, HistoricoExtintorDao> implements HistoricoExtintorManager
{
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	HistoricoExtintorManagerImpl(HistoricoExtintorDao historicoExtintorDao) {
			setDao(historicoExtintorDao);
	}

	public HistoricoExtintor save(HistoricoExtintor historicoExtintor) 
	{
		if (historicoExtintor.getHoraString() != null)
			historicoExtintor.setData(DateUtil.montaDataByStringComHora(DateUtil.formataDiaMesAno(historicoExtintor.getData()),historicoExtintor.getHoraString()));
		
		return getDao().save(historicoExtintor);
	}
	
	public void update(HistoricoExtintor historicoExtintor) 
	{
		if (historicoExtintor.getHoraString() != null)
			historicoExtintor.setData(DateUtil.montaDataByStringComHora(DateUtil.formataDiaMesAno(historicoExtintor.getData()),historicoExtintor.getHoraString()));
		
		getDao().update(historicoExtintor);
	}
	
	public Collection<HistoricoExtintor> findByExtintor(Long extintorId) 
	{
		return getDao().findByExtintor(extintorId);
	}

	public void removeByExtintor(Long extintorId) 
	{
		getDao().removeByExtintor(extintorId);
	}

	public Collection<HistoricoExtintor> findAllHistoricosAtuais(Integer page, Integer pagingSize, Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId) 
	{
		return getDao().findAllHistoricosAtuais(page, pagingSize, data, localizacao, extintorTipo, estabelecimentoId, empresaId);
	}
	
	public Integer countAllHistoricosAtuais(Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId) 
	{
		return getDao().countAllHistoricosAtuais(data, localizacao, extintorTipo, estabelecimentoId, empresaId);
	}

	public void insertNewHistoricos(Long[] extintorsCheck, HistoricoExtintor historicoTemp) throws Exception 
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			Extintor extintor;
			HistoricoExtintor historicoExtintor;
			for (Long extintorId : extintorsCheck) 
			{
				extintor = new Extintor();
				extintor.setId(extintorId);
				
				historicoExtintor = new HistoricoExtintor();
				historicoExtintor.setId(null);
				historicoExtintor.setExtintor(extintor);
				
				historicoExtintor.setData(DateUtil.montaDataByStringComHora(DateUtil.formataDiaMesAno(historicoTemp.getData()),historicoTemp.getHoraString()));
				historicoExtintor.setEstabelecimento(historicoTemp.getEstabelecimento());
				historicoExtintor.setLocalizacao(historicoTemp.getLocalizacao());
				
				save(historicoExtintor);
			}
			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}