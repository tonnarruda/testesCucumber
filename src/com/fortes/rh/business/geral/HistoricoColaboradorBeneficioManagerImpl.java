package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.HistoricoColaboradorBeneficioDao;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.util.DateUtil;

@Component
public class HistoricoColaboradorBeneficioManagerImpl extends GenericManagerImpl<HistoricoColaboradorBeneficio, HistoricoColaboradorBeneficioDao> implements HistoricoColaboradorBeneficioManager
{

	private Collection<HistoricoBeneficio> historicoBeneficios = new ArrayList<HistoricoBeneficio>();
	@Autowired private HistoricoBeneficioManager historicoBeneficioManager;
	@Autowired private PlatformTransactionManager transactionManager;

	@Autowired
	HistoricoColaboradorBeneficioManagerImpl(HistoricoColaboradorBeneficioDao dao) {
		setDao(dao);
	}
	
	public List filtroRelatorioByColaborador(LinkedHashMap filtro)
	{
		setHistoricoBeneficios(historicoBeneficioManager.getHistoricos());
		List retorno = getDao().filtroRelatorioByColaborador(filtro);
		retorno = setHistoricosEntrePeriodos(retorno, filtro);
		return retorno;
	}

	public List filtroRelatorioByAreas(LinkedHashMap filtro)
	{
		setHistoricoBeneficios(historicoBeneficioManager.getHistoricos());
		List retorno = getDao().filtroRelatorioByAreasEstabelecimentos(filtro);
		retorno = setHistoricosEntrePeriodos(retorno, filtro);
		return retorno;
	}


	@SuppressWarnings("unchecked")
	public List setHistoricosEntrePeriodos(List historicoColaboradorBeneficios, LinkedHashMap parametros)
	{
		List retorno = new LinkedList<HistoricoColaboradorBeneficio>();

		Date dataIni = (Date) parametros.get("dataIni");
		Date dataFim = (Date) parametros.get("dataFim");

		for (Iterator<Object[]> it = historicoColaboradorBeneficios.iterator(); it.hasNext();)
		{
			Object[] beneficio = it.next();

			clonarEntrePeriodos(beneficio, retorno, dataFim);
		}

		//remove beneficios com data fora do filtro informado pelo usuario
		retorno = filtraBeneficioByPeriodo(retorno, dataIni, dataFim);

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List filtraBeneficioByPeriodo(List historicoColaboradorBeneficios, Date dateIni, Date dateFim)
	{
		List retorno = new LinkedList();

		for (Iterator<Object[]> it = historicoColaboradorBeneficios.iterator(); it.hasNext();)
		{
			Object[] beneficio = it.next();
			if(DateUtil.diferencaEntreDatas((Date)beneficio[2], dateIni, false) <= 0 && DateUtil.diferencaEntreDatas((Date)beneficio[2], dateFim, false) > 0)
				retorno.add(beneficio);
		}

		return retorno;
	}

	//clona no período do beneficio
	@SuppressWarnings("unchecked")
	public void clonarEntrePeriodos(Object[] beneficio, List retorno, Date dataFim)
	{
		Date dataDe = new Date();
		Date dataAte = new Date();
		dataDe = (Date) beneficio[2];
		//até a data do desligamento do colaborador
		dataAte = (Date) beneficio[5];

		// até a dataAte = data do proximo historico, qndo tem
		if (dataAte == null)
			dataAte = (Date) beneficio[3];

		// até a data do final da consulta
		if (dataAte == null)
			dataAte = dataFim;

		Object[] beneficioBase = beneficio.clone();

		while(DateUtil.diferencaEntreDatas(dataDe, dataAte, false) > 0)
		{
			Object[] beneficioClonado = beneficioBase.clone();
			beneficioClonado[2] = dataDe;
			beneficioClonado[6] = getValorBeneficio((String)beneficioClonado[1], dataDe);
			retorno.add(beneficioClonado);
			dataDe = DateUtil.setaMesPosterior(dataDe);
		}
	}

	public HistoricoColaboradorBeneficio getHistoricoByColaboradorData(Long id, Date data)
	{
		return getDao().getHistoricoByColaboradorData(id, data);
	}

	public Double getValorBeneficio(String beneficioNome, Date data)
	{
		Double retorno = 0D;
		Integer menorDiferenca = null;

		for (HistoricoBeneficio historico : this.historicoBeneficios)
		{
			if(beneficioNome.equals(historico.getBeneficio().getNome()))
			{
				if(menorDiferenca == null)
				{
					menorDiferenca = DateUtil.diferencaEntreDatas(historico.getData(), data, false);
					retorno = historico.getValor() - (historico.getValor() * historico.getParaColaborador()/100);
					continue;
				}

				if(DateUtil.diferencaEntreDatas(historico.getData(), data, false) >= 0 && DateUtil.diferencaEntreDatas(historico.getData(), data, false) < menorDiferenca)
				{
					menorDiferenca = DateUtil.diferencaEntreDatas(historico.getData(), data, false);
					retorno = historico.getValor() - (historico.getValor() * historico.getParaColaborador()/100);
				}
			}
		}

		return retorno;
	}

	public HistoricoColaboradorBeneficio getUltimoHistorico(Long colaboradorId)
	{
		return getDao().getUltimoHistorico(colaboradorId);
	}

	public void setHistoricoBeneficios(Collection<HistoricoBeneficio> historicoBeneficios)
	{
		this.historicoBeneficios = historicoBeneficios;
	}

	public void updateDataAteUltimoHistorico(Long historicoId, Date dataAte)
	{
		getDao().updateDataAteUltimoHistorico(historicoId, dataAte);
	}

	public HistoricoColaboradorBeneficio saveHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio) throws Exception
	{
		HistoricoColaboradorBeneficio retorno = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try
		{
			HistoricoColaboradorBeneficio ultimoHistorico = getUltimoHistorico(historicoColaboradorBeneficio.getColaborador().getId());

			if(ultimoHistorico != null && ultimoHistorico.getId() != null)
				updateDataAteUltimoHistorico(ultimoHistorico.getId(), historicoColaboradorBeneficio.getData());

			retorno = getDao().save(historicoColaboradorBeneficio);

			if (retorno != null && retorno.getId() != null)
				transactionManager.commit(status);
			else
				throw new Exception("Não foi possível inserir histórico de benefícios para este colaborador.");

		}catch(Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
		return retorno;
	}

	public void deleteHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try
		{
			getDao().remove(historicoColaboradorBeneficio);

			HistoricoColaboradorBeneficio ultimoHistorico = getUltimoHistorico(historicoColaboradorBeneficio.getColaborador().getId());

			if(ultimoHistorico != null && ultimoHistorico.getId() != null)
				updateDataAteUltimoHistorico(ultimoHistorico.getId(), null);

			transactionManager.commit(status);

		}catch(Exception e)
		{
			transactionManager.rollback(status);
			throw new Exception("Não foi possível deletar este histórico de benefícios.");
		}
	}
}