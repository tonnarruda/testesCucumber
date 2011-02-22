package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.EventoAgenda;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.captacao.relatorio.ProdutividadeRelatorio;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings({"deprecation","unchecked"})
public class HistoricoCandidatoManagerImpl extends GenericManagerImpl<HistoricoCandidato, HistoricoCandidatoDao> implements HistoricoCandidatoManager
{
	private EtapaSeletivaManager etapaSeletivaManager;
	private PlatformTransactionManager transactionManager;
	private CandidatoManager candidatoManager;

	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager)
	{
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<HistoricoCandidato> findByCandidato(Candidato candidato)
	{
		return getDao().findByCandidato(candidato);
	}

	public Collection<SolicitacaoHistoricoColaborador> montaMapaHistorico(Collection<HistoricoCandidato> historicoCandidatos)
	{
		LinkedHashMap<Solicitacao, Collection<HistoricoCandidato>> mapHistoricos = new LinkedHashMap<Solicitacao, Collection<HistoricoCandidato>>();

		for(HistoricoCandidato historicoTemp : historicoCandidatos)
		{
			//se solicitação ja é uma chave
			if(mapHistoricos.containsKey(historicoTemp.getCandidatoSolicitacao().getSolicitacao()))
			{
				Collection<HistoricoCandidato> historicos = mapHistoricos.get(historicoTemp.getCandidatoSolicitacao().getSolicitacao());
				if(historicos != null)
				{
					historicos.add(historicoTemp);
					mapHistoricos.put(historicoTemp.getCandidatoSolicitacao().getSolicitacao(), historicos);
				}
			}
			else
			{
				Collection<HistoricoCandidato> historicos = new ArrayList<HistoricoCandidato>();
				historicos.add(historicoTemp);
				mapHistoricos.put(historicoTemp.getCandidatoSolicitacao().getSolicitacao(), historicos);
			}
		}

		return montaCollectionHistorico(mapHistoricos);
	}

	private Collection<SolicitacaoHistoricoColaborador> montaCollectionHistorico(LinkedHashMap<Solicitacao, Collection<HistoricoCandidato>> mapHistoricos)
	{
		Collection<SolicitacaoHistoricoColaborador> historicos = new ArrayList<SolicitacaoHistoricoColaborador>();
		Collection<Solicitacao> solicitacaoKeys = mapHistoricos.keySet();

		for (Solicitacao solicitacao : solicitacaoKeys)
		{
			SolicitacaoHistoricoColaborador temp = new SolicitacaoHistoricoColaborador();
			temp.setSolicitacao(solicitacao);
			temp.setHistoricos(mapHistoricos.get(solicitacao));

			historicos.add(temp);
		}

		return historicos;
	}

	public Collection<HistoricoCandidato> findByCandidato(Collection<CandidatoSolicitacao> candidatos)
	{
		return getDao().findByCandidato(candidatos);
	}

	public Collection<HistoricoCandidato> findList(CandidatoSolicitacao candidatoSolicitacao)
	{
		return getDao().findList(candidatoSolicitacao);
	}

	public Collection<HistoricoCandidato> findByPeriodo(Map parametros)
	{
		return getDao().findByPeriodo(parametros);
	}

	public Collection<ProdutividadeRelatorio> getProdutividade(String ano, Long empresaId)
	{
		Collection<ProdutividadeRelatorio> resultado = new ArrayList<ProdutividadeRelatorio>();

		Map parametros = new HashMap();
		parametros.put("dataIni", new Date(Integer.valueOf(ano)-1900,0,1));
		parametros.put("dataFim", new Date(Integer.valueOf(ano)-1900,11,31));

		Collection<HistoricoCandidato> historicoCandidatos = findByPeriodo(parametros);

		for (EtapaSeletiva etapa : etapaSeletivaManager.find(new String[]{"empresa.id"},new Object[]{empresaId},new String[]{"ordem"}))
		{
			ProdutividadeRelatorio pr = new ProdutividadeRelatorio();
			pr.setEtapa(etapa);

			resultado.add(pr);
		}

		for (HistoricoCandidato hc : historicoCandidatos)
		{
			EtapaSeletiva etapaSeletiva = hc.getEtapaSeletiva();
			int mesEtapa = hc.getData().getMonth();
			for (ProdutividadeRelatorio pr : resultado)
			{
				if(pr.getEtapa().getId().equals(etapaSeletiva.getId()))
				{
					if(mesEtapa == 0 )
						pr.setQtdJan(pr.getQtdJan() + 1);
					else if(mesEtapa == 1 )
						pr.setQtdFev(pr.getQtdFev() + 1);
					else if(mesEtapa == 2 )
						pr.setQtdMar(pr.getQtdMar() + 1);
					else if(mesEtapa == 3 )
						pr.setQtdAbr(pr.getQtdAbr() + 1);
					else if(mesEtapa == 4 )
						pr.setQtdMai(pr.getQtdMai() + 1);
					else if(mesEtapa == 5 )
						pr.setQtdJun(pr.getQtdJun() + 1);
					else if(mesEtapa == 6 )
						pr.setQtdJul(pr.getQtdJul() + 1);
					else if(mesEtapa == 7 )
						pr.setQtdAgo(pr.getQtdAgo() + 1);
					else if(mesEtapa == 8 )
						pr.setQtdSet(pr.getQtdSet() + 1);
					else if(mesEtapa == 9 )
						pr.setQtdOut(pr.getQtdOut() + 1);
					else if(mesEtapa == 10 )
						pr.setQtdNov(pr.getQtdNov() + 1);
					else if(mesEtapa == 11 )
						pr.setQtdDez(pr.getQtdDez() + 1);
				}
			}
		}

		return resultado;
	}

//	public Collection<ProcessoSeletivoRelatorio> processaProcessoSeletivo(String ano, Long cargoId, Long[] etapas, Long empresaId)
//	{
//		Collection<EtapaSeletiva> etapaSeletivas = etapaSeletivaManager.findByIdProjection(etapas,empresaId);
//
//		Date inicio = DateUtil.criarDataMesAno(1, 1, Integer.parseInt(ano));
//		Date fim    = DateUtil.criarDataMesAno(31, 12, Integer.parseInt(ano));
//
//		EtapaSeletiva primeiraEtapa = etapaSeletivaManager.findPrimeiraEtapa(empresaId);
//		Collection<HistoricoCandidato> historicosPrimeiraEtapa = getDao().findByCargoEtapaPeriodo(cargoId, primeiraEtapa, inicio, fim);
//
//		Collection<ProcessoSeletivoRelatorio> relatorio = prepareRelatorioProcessoSeletivo(etapaSeletivas);
//		relatorio = carregaInformacao(relatorio, historicosPrimeiraEtapa);
//
//		return relatorio;
//	}
//
//	/**
//	 * Monta a estrutura inical do relatório de processo seletivo setando apenas as etapas seletivas
//	 *
//	 * @author Igo Coelho e Jonathas Lima
//	 * @since 06/09/2007
//	 * @param etapaSeletivas - Lista de etapas selecionadas pelo usuário para geração do relatório
//	 * @return Coleção de objetos ProcessoSeletivoRelatorio com apenas as etapas seletivas setadas
//	 */
//	private Collection<ProcessoSeletivoRelatorio> prepareRelatorioProcessoSeletivo(Collection<EtapaSeletiva> etapaSeletivas)
//	{
//		Collection<ProcessoSeletivoRelatorio> result = new ArrayList<ProcessoSeletivoRelatorio>();
//
//		for (EtapaSeletiva etapa : etapaSeletivas)
//		{
//			ProcessoSeletivoRelatorio rel = new ProcessoSeletivoRelatorio();
//			rel.setEtapa(etapa);
//			result.add(rel);
//		}
//
//		return result;
//	}
//
//	private Collection<ProcessoSeletivoRelatorio> carregaInformacao(Collection<ProcessoSeletivoRelatorio> relatorio, Collection<HistoricoCandidato> historicosPrimeiraEtapa)
//	{
//		for (int i = 0; i < 12; i++)
//		{
//			Collection<CandidatoSolicitacao> candidatosDoMes = obterCandidatosDoMes(historicosPrimeiraEtapa, i);
//			Collection<HistoricoCandidato> historicosDoMes = findByCandidato(candidatosDoMes);
//
//			relatorio = processaMesRelatorioProcessoSeletivo(relatorio, historicosDoMes, i);
//		}
//
//		return relatorio;
//	}
//
//	/**
//	 * Busca na coleção e retorna o ID de todos os candidatos quem tem algum histórico no mês informado.
//	 *
//	 * @author Igo Coelho e Jonathas Lima
//	 * @since 06/09/2007
//	 * @param historicosPrimeiraEtapa - Todos os históricos dos candidatos da primeira etapa seletiva do ano informado
//	 * @param mes - Mês para extração dos históricos julgando que janeiro é igula a 0 e dezembro igual a 11
//	 * @return Array com os ids dos candidatoSolicitacao do mês informado
//	 */
//	private Collection<CandidatoSolicitacao> obterCandidatosDoMes(Collection<HistoricoCandidato> historicosPrimeiraEtapa, int mes)
//	{
//		Collection<CandidatoSolicitacao> result = new ArrayList<CandidatoSolicitacao>();
//
//		for (HistoricoCandidato hc : historicosPrimeiraEtapa)
//		{
//			if (hc.getData().getMonth() == mes)
//				result.add(hc.getCandidatoSolicitacao());
//		}
//
//		return result;
//	}
//
//	private Collection<ProcessoSeletivoRelatorio> processaMesRelatorioProcessoSeletivo(Collection<ProcessoSeletivoRelatorio> relatorio, Collection<HistoricoCandidato> historicosDoMes, int mes)
//	{
//		for (HistoricoCandidato hm : historicosDoMes)
//		{
//			relatorio = setCandidatoRelatorio(hm, relatorio, mes);
//		}
//
//		return relatorio;
//	}
//
//	private Collection<ProcessoSeletivoRelatorio> setCandidatoRelatorio(HistoricoCandidato hm, Collection<ProcessoSeletivoRelatorio> relatorio,int mes)
//	{
//		for(ProcessoSeletivoRelatorio ps : relatorio)
//		{
//			if(ps.getEtapa().getId().equals(hm.getEtapaSeletiva().getId()))
//			{
//				switch (mes)
//				{
//					case 0:
//						ps.setQtdJan();
//						if(hm.isApto())
//							ps.setQtdAprovJan();
//						break;
//					case 1:
//						ps.setQtdFev();
//						if(hm.isApto())
//							ps.setQtdAprovFev();
//						break;
//					case 2:
//						ps.setQtdMar();
//						if(hm.isApto())
//							ps.setQtdAprovMar();
//						break;
//					case 3:
//						ps.setQtdAbr();
//						if(hm.isApto())
//							ps.setQtdAprovAbr();
//						break;
//					case 4:
//						ps.setQtdMai();
//						if(hm.isApto())
//							ps.setQtdAprovMai();
//						break;
//					case 5:
//						ps.setQtdJun();
//						if(hm.isApto())
//							ps.setQtdAprovJun();
//						break;
//					case 6:
//						ps.setQtdJul();
//						if(hm.isApto())
//							ps.setQtdAprovJul();
//						break;
//					case 7:
//						ps.setQtdAgo();
//						if(hm.isApto())
//							ps.setQtdAprovAgo();
//						break;
//					case 8:
//						ps.setQtdSet();
//						if(hm.isApto())
//							ps.setQtdAprovSet();
//						break;
//					case 9:
//						ps.setQtdOut();
//						if(hm.isApto())
//							ps.setQtdAprovOut();
//						break;
//					case 10:
//						ps.setQtdNov();
//						if(hm.isApto())
//							ps.setQtdAprovNov();
//						break;
//					case 11:
//						ps.setQtdDez();
//						if(hm.isApto())
//							ps.setQtdAprovDez();
//						break;
//				}
//			}
//		}
//
//		return relatorio;
//	}

	public void save(HistoricoCandidato historicoCandidato, String[] candidatosCheck) throws Exception
	{
		CandidatoSolicitacao candidatoSol = null;

		for (String candidatoId: candidatosCheck)
		{
			candidatoSol = new CandidatoSolicitacao();
			candidatoSol.setId(Long.parseLong(candidatoId));

			HistoricoCandidato hist = (HistoricoCandidato) historicoCandidato.clone();
			hist.setCandidatoSolicitacao(candidatoSol);

			save(hist);
		}
	}

	public void saveHistoricos(HistoricoCandidato historicoCandidato, String[] candidatosCheck, boolean blacklist) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			save(historicoCandidato, candidatosCheck);
			candidatoManager = (CandidatoManager) SpringUtil.getBean("candidatoManager");
			candidatoManager.setBlackList(historicoCandidato, candidatosCheck, blacklist);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}

	}
	
	public boolean updateAgenda(Long id, Date data, String horaIni, String horaFim, String observacao)
	{
		return getDao().updateAgenda(id, data, horaIni, horaFim, observacao);
	}

	public HistoricoCandidato findByIdProjection(Long historicoId)
	{
		return getDao().findByIdProjection(historicoId);
	}
	
	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public Collection<ProcessoSeletivoRelatorio> relatorioProcessoSeletivo(String ano, Long cargoId, Long[] etapaIds)
	{
		Collection<ProcessoSeletivoRelatorio> processosSeletivos = etapaSeletivaManager.montaProcessosSeletivos(etapaIds);
		Collection<HistoricoCandidato> historicoCandidatos = getDao().findQtdParticipantes(ano, cargoId, etapaIds);
		
		for (ProcessoSeletivoRelatorio processoSeletivoRelatorio : processosSeletivos)
		{
			for (HistoricoCandidato historicoCandidato : historicoCandidatos)
			{
				if(processoSeletivoRelatorio.getEtapa().equals(historicoCandidato.getEtapaSeletiva()))
					processoSeletivoRelatorio.addQtdParticipantes(historicoCandidato.getHistoricoMes(), historicoCandidato.getQtdHistoricos(), historicoCandidato.isApto());
			}
		}
		
		return processosSeletivos;
	}

	public String[] findResponsaveis() 
	{
		return getDao().findResponsaveis();
	}

	public Collection<EventoAgenda> getEventos(String responsavel, Long empresaId) 
	{
		Collection<HistoricoCandidato> historicoCandidatos = getDao().getEventos(responsavel, empresaId);
		Collection<EventoAgenda> eventos = new ArrayList<EventoAgenda>();
		
		for (HistoricoCandidato hc : historicoCandidatos)
		{
			String data = DateUtil.formataDate(hc.getData(), "yyyy-MM-dd");
			eventos.add(new EventoAgenda(hc.getId(),
					hc.getEtapaSeletiva().getNome() + "<br>Cand.: " 
					+ "<a href=\"javascript:popup('../candidato/infoCandidato.action?candidato.id="+hc.getCandidatoSolicitacao().getCandidato().getId()+"', 580, 750)\">" + hc.getCandidatoSolicitacao().getCandidato().getNome() + "</a>" 
					+ "<br>Resp.: " + hc.getResponsavel(),
					hc.getObservacao(),
					data + "T" + hc.getHoraIni(),
					data + "T" + hc.getHoraFim()));
		}
		
		return eventos;
	}
}