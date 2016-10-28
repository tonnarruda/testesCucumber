package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.AgendaDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.util.DateUtil;

@Component
public class AgendaManagerImpl extends GenericManagerImpl<Agenda, AgendaDao> implements AgendaManager
{
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	AgendaManagerImpl(AgendaDao fooDao) {
		setDao(fooDao);
	}

	public Collection<Agenda> findByPeriodo(Date dataIni, Date dataFim, Long empresaId, Estabelecimento estabelecimento)
	{
		return getDao().findByPeriodo(dataIni, dataFim, empresaId, estabelecimento);
	}

	public Agenda findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void save(Agenda agenda, Integer qtdPeriodo, Integer periodicidade, Integer tipoPeriodo) throws Exception
	{
		Collection<Agenda> agendasExistentes = findAgendasExistentes(agenda, qtdPeriodo, periodicidade, tipoPeriodo);
		
		for (int i = 1; i <= qtdPeriodo; i++)
		{
			Agenda agendaTmp = (Agenda) agenda.clone();
			agendaTmp.setId(null);
			
			//altera as datas das proximas agendas
			if(i > 1)
				agendaTmp.setNovaData(agendaTmp.getData(), periodicidade, tipoPeriodo);
			
			// salva apenas se não existir agenda nesta data para o mesmo evento no mesmo estabelecimento
			if (!agendasExistentes.contains(agendaTmp))
				getDao().save(agendaTmp);
			
			agenda = agendaTmp;
		}
	}
	
	private Collection<Agenda> findAgendasExistentes(Agenda agenda, Integer qtdPeriodo, Integer periodicidade, Integer tipoPeriodo)
	{
		Integer periodicidadeFim = qtdPeriodo * periodicidade; // incremento para o fim do período
		
		Date dataIni = agenda.getData();
		Date dataFim = DateUtil.incrementa(agenda.getData(), periodicidadeFim, tipoPeriodo);
		
		return findByPeriodoEvento(dataIni, dataFim, agenda.getEstabelecimento(), agenda.getEvento());
	}

	public Collection<Agenda> findByPeriodoEvento(Date dataIni, Date dataFim, Estabelecimento estabelecimento, Evento evento)
	{
		return getDao().findByPeriodoEvento(dataIni, dataFim, estabelecimento, evento);
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void deleteByEstabelecimento(Long[] estabelecimentoIds) throws Exception {
		getDao().deleteByEstabelecimento(estabelecimentoIds);
	}
}
