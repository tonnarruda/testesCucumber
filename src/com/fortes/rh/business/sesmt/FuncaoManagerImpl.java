package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.exception.PppRelatorioException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteRisco;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;
import com.ibm.icu.util.Calendar;

@Component
@SuppressWarnings("unchecked")
public class FuncaoManagerImpl extends GenericManagerImpl<Funcao, FuncaoDao> implements FuncaoManager
{
	private ColaboradorManager colaboradorManager;
	private CatManager catManager;
	private RiscoMedicaoRiscoManager  riscoMedicaoRiscoManager;
	private EpiManager epiManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private EngenheiroResponsavelManager engenheiroResponsavelManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	
	HistoricoFuncaoManager historicoFuncaoManager = null;
	
	@Autowired
	FuncaoManagerImpl(FuncaoDao funcaoDao) {
			setDao(funcaoDao);
	}
	
	// TODO refatorar esse código, não devia estar nesse manager
	public Collection<PppRelatorio> populaRelatorioPpp(Colaborador colaborador, Empresa empresa, Date data, String nit, String cnae, String responsavel, String observacoes, String[] respostas) throws Exception
	{
		HistoricoColaboradorManager historicoColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBean("historicoColaboradorManager"); 
		historicoFuncaoManager = (HistoricoFuncaoManager) SpringUtil.getBean("historicoFuncaoManager");
		Collection<HistoricoColaborador> historicosDoColaboradors = historicoColaboradorManager.findByColaboradorData(colaborador.getId(),data);
		
		if(historicosDoColaboradors == null || historicosDoColaboradors.isEmpty())
			throw new PppRelatorioException("Não existem dados para gerar o relatório.");
		
		historicosDoColaboradors = historicoColaboradorManager.filtraHistoricoColaboradorParaPPP(historicosDoColaboradors); 
				
		this.validarPpp(historicosDoColaboradors, empresa);
		
		Collection<Cat> cats = catManager.findCatsColaboradorByDate(colaborador,data);
		colaborador = colaboradorManager.findById(colaborador.getId());
		colaborador.getEmpresa().setCnae(cnae);
		
		HistoricoColaborador ultimoHistorico = ((HistoricoColaborador) historicosDoColaboradors.toArray()[historicosDoColaboradors.size()-1]);
		Estabelecimento estabelecimento = ultimoHistorico.getEstabelecimento();

		historicosDoColaboradors = historicoColaboradorManager.inserirPeriodos(historicosDoColaboradors);

		Collection<PppFatorRisco> pppFatorRiscos = this.populaFatoresDeRiscos(data, historicosDoColaboradors);
		Collection<HistoricoColaborador> historicosColaboradorFuncao = historicoColaboradorManager.findDistinctFuncao(historicosDoColaboradors);
		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoManager.findHistoricoFuncaoColaborador(historicosColaboradorFuncao,data, colaborador.getDataDesligamento());
		Collection<EngenheiroResponsavel> engenheirosResponsaveis = engenheiroResponsavelManager.getEngenheirosAteData(colaborador, data);
		Collection<MedicoCoordenador> medicosCoordenadores = medicoCoordenadorManager.getMedicosAteData(data, colaborador);
		
		PppRelatorio pppRelatorio = new PppRelatorio(colaborador, estabelecimento, data);
		pppRelatorio.setRespostas(respostas);
		pppRelatorio.setCats(cats);
		pppRelatorio.setHistoricos(historicosDoColaboradors);
		pppRelatorio.setHistoricoFuncaos(historicoFuncaos);
		pppRelatorio.setFatoresRiscosDistinct(pppFatorRiscos);
		pppRelatorio.setNit(nit);
		pppRelatorio.setResponsavel(responsavel);
		pppRelatorio.setObservacoes(observacoes);
		
		pppRelatorio.setEngenheirosResponsaveis(engenheirosResponsaveis);
		pppRelatorio.setMedicosCoordenadores(medicosCoordenadores);
		
		Collection<PppRelatorio> relatorios = new ArrayList<PppRelatorio>();
		relatorios.add(pppRelatorio);
		
		return relatorios;
	}

	private Collection<PppFatorRisco> populaFatoresDeRiscos(Date data, Collection<HistoricoColaborador> historicosDoColaborador) {
		
		List<HistoricoColaborador> hist = new CollectionUtil<HistoricoColaborador>().convertCollectionToList(historicosDoColaborador);
		
		List<PppFatorRisco> pppFatorRiscos = new ArrayList<PppFatorRisco>();
		
		for (int i=0; i < hist.size(); i++)
		{
			Date dataHistColaboradorIni = hist.get(i).getData();
			Date dataFim = data;
			
			if (i+1 < hist.size())
				dataFim = hist.get(i+1).getData();
			
			List<DadosAmbienteRisco> dadosAmbientesRiscos = historicoAmbienteManager.findDadosNoPeriodo(hist.get(i).getAmbiente().getId(), dataHistColaboradorIni, dataFim);
			
			for (DadosAmbienteRisco dadosAmbienteRisco : dadosAmbientesRiscos) 
			{
				Date dataAmbienteIni = dadosAmbienteRisco.getHistoricoAmbienteData();
				Date dataAmbienteFim = getProxDataHistorico(dadosAmbientesRiscos, dataAmbienteIni);
				
				if(dataAmbienteFim == null)//historico do ambiente ficou aberto, pega a data do historico colaborador
					dataAmbienteFim = dataFim;
				
				Collection<Epi> epis = epiManager.findByRiscoAmbiente(dadosAmbienteRisco.getRiscoId(), dadosAmbienteRisco.getAmbienteId(), dataAmbienteIni); // pegando EPIs do risco
				
				List<RiscoMedicaoRisco> riscoMedicaoRiscos = riscoMedicaoRiscoManager.getByRiscoPeriodo(dadosAmbienteRisco.getRiscoId(), dadosAmbienteRisco.getAmbienteId(), dataAmbienteIni, dataAmbienteFim);
				
				int posicao = 0;
				for (int j=0; j < riscoMedicaoRiscos.size(); j++) 
				{
					RiscoMedicaoRisco riscoMedicaoRiscoTemp = riscoMedicaoRiscos.get(j);
					Date dataMedicao = riscoMedicaoRiscoTemp.getMedicaoRisco().getData();
					
					// A princípio, o período inicia com o histórico de ambiente, até o fim deste.
					Date pppFatorRiscoIni = dataAmbienteIni;
					Date pppFatorRiscoFim = dataAmbienteFim;
					
					posicao++;
					
					// no início do período prevalece a data mais recente: 
					// 		histórico do ambiente, data da medição ou histórico do colaborador
					if (dataMedicao.compareTo(pppFatorRiscoIni) == 1)
						pppFatorRiscoIni = dataMedicao;
					if (dataHistColaboradorIni.compareTo(pppFatorRiscoIni) == 1)
						pppFatorRiscoIni = dataHistColaboradorIni;
					
					if (posicao == 1)
					{
						if (riscoMedicaoRiscos.size() == 1)//só existe uma medição para esse risco
						{
							pppFatorRiscoFim = dataAmbienteFim;
						}
						else // há uma próxima medição
						{
							pppFatorRiscoFim = riscoMedicaoRiscos.get(j+1).getMedicaoRisco().getData();
						}
					}
					else if (posicao < riscoMedicaoRiscos.size()) //no meio deles
					{
						pppFatorRiscoFim = riscoMedicaoRiscos.get(j+1).getMedicaoRisco().getData();
					}
					else if (posicao == riscoMedicaoRiscos.size()) //no fim
					{
						pppFatorRiscoFim = dataAmbienteFim;
					}
					
					if(pppFatorRiscoIni.before(pppFatorRiscoFim))
					{
						PppFatorRisco tmp = new PppFatorRisco(pppFatorRiscoIni, pppFatorRiscoFim, dadosAmbienteRisco.getRiscoId(), dadosAmbienteRisco.getRiscoTipo(), dadosAmbienteRisco.getRiscoDescricao(), riscoMedicaoRiscoTemp.getMedicaoRisco().getId(), riscoMedicaoRiscoTemp.getIntensidadeMedida(), riscoMedicaoRiscoTemp.getTecnicaUtilizada(), dadosAmbienteRisco.isEpcEficaz(), epis);
						tmp.setDataHistoricoAmbiente(dadosAmbienteRisco.getHistoricoAmbienteData());
						tmp.setDataDesligamento(hist.get(i).getColaborador().getDataDesligamento());
						pppFatorRiscos.add(tmp);
					}
					
					
				}
			}
		}
		

		// Relacionando datas de Histórico dos Ambientes e Riscos
		HashMap<Date, Collection<Long>> riscosPorHistoricoAmbiente = this.relacionarRiscosPorHistoricoAmbiente(pppFatorRiscos); 
		
		List<PppFatorRisco> pppFatorRiscos2 = new ArrayList<PppFatorRisco>();

		// tratando os fatores riscos repetidos 
		for (int i=0; i< pppFatorRiscos.size(); i++) 
		{
			PppFatorRisco pppFatorRiscoInicial = pppFatorRiscos.get(i);
			Long riscoId = pppFatorRiscoInicial.getRisco().getId();
			
			if (pppFatorRiscoInicial.isFlagRemover())
				continue;
			
			for (int j = i+1; j < pppFatorRiscos.size(); j++)
			{
				PppFatorRisco pppFatorRiscoTmp = pppFatorRiscos.get(j);
				Date histAmbData = pppFatorRiscoTmp.getDataHistoricoAmbiente();
				
				// se o risco deixou de existir nessa data
				if (!riscosPorHistoricoAmbiente.get(histAmbData).contains(riscoId) && !pppFatorRiscoInicial.isDataFinalJaSetada())
				{
					pppFatorRiscoInicial.setDataFim(pppFatorRiscoTmp.getDataInicio());
					break;
				}
				else
				{
					if (pppFatorRiscoTmp.isFlagRemover())
						continue;
					
					if (riscoId.equals(pppFatorRiscoTmp.getRisco().getId()))
					{
						if (pppFatorRiscoTmp.equalsMedicao(pppFatorRiscoInicial)) //juntando os períodos 
						{
							pppFatorRiscoInicial.setDataFim(pppFatorRiscoTmp.getDataFim());
							pppFatorRiscoTmp.setFlagRemover(true);
						}
						
						// o fator risco anterior terá mais data final modificada
						pppFatorRiscoInicial.setDataFinalJaSetada(true);
					}
				}
			}
		}
		
		// remover os "flagados" e deixar datas em aberto se a data final do PPP for HOJE.
		Date hoje=Calendar.getInstance().getTime();
		for (PppFatorRisco pppFatorRisco : pppFatorRiscos) 
		{
			if (!pppFatorRisco.isFlagRemover())
			{
				if (DateUtil.equals(hoje, pppFatorRisco.getDataFim()))
					pppFatorRisco.setDataFim(null);
	
				pppFatorRiscos2.add(pppFatorRisco);
			}
		}
		
		if (pppFatorRiscos2 == null || pppFatorRiscos2.isEmpty())
			pppFatorRiscos2.add(new PppFatorRisco());
		
		return pppFatorRiscos2;
	}

	private HashMap<Date, Collection<Long>> relacionarRiscosPorHistoricoAmbiente(List<PppFatorRisco> pppFatorRiscos) 
	{
		HashMap<Date, Collection<Long>> riscosPorHistoricoAmbiente = new HashMap<Date, Collection<Long>>();
		for (PppFatorRisco pppFatorRisco : pppFatorRiscos) 
		{
			Date historicoAmbienteData = pppFatorRisco.getDataHistoricoAmbiente();
			
			if (riscosPorHistoricoAmbiente.get(historicoAmbienteData) == null)
				riscosPorHistoricoAmbiente.put(historicoAmbienteData, new TreeSet<Long>());
			
			riscosPorHistoricoAmbiente.get(historicoAmbienteData).add(pppFatorRisco.getRisco().getId());
		}
		
		return riscosPorHistoricoAmbiente; 
	}

	private Date getProxDataHistorico(List<DadosAmbienteRisco> historicoAmbientesRiscos, Date dataAmbienteIni) 
	{
		for (DadosAmbienteRisco dadosAmbienteRisco : historicoAmbientesRiscos) 
		{
			if(dadosAmbienteRisco.getHistoricoAmbienteData().compareTo(dataAmbienteIni) > 0)
				return dadosAmbienteRisco.getHistoricoAmbienteData();
		}
		
		return null;
	}
	
	private void validarPpp(Collection<HistoricoColaborador> historicos, Empresa empresa) throws PppRelatorioException 
	{
		PppRelatorioException pppRelatorioException = new PppRelatorioException();
		
		this.validaHistoricos(historicos, pppRelatorioException);
		this.validaAmbientes(historicos, pppRelatorioException, empresa);
		this.validaFuncoes(historicos, pppRelatorioException);
		
		if (pppRelatorioException.isAtivo())
			throw pppRelatorioException;
	}
	
	private void validaHistoricos(Collection<HistoricoColaborador> historicosDoColaborador, PppRelatorioException pppRelatorioException)
	{
		for (HistoricoColaborador historicoColaborador : historicosDoColaborador)
		{
			if (historicoColaborador.getAmbiente() == null)
				pppRelatorioException.addHistoricoSemAmbiente(historicoColaborador.getData(), historicoColaborador.getColaborador().getId());
			if (historicoColaborador.getFuncao() == null)
				pppRelatorioException.addHistoricoSemFuncao(historicoColaborador.getData(), historicoColaborador.getColaborador().getId());
		}
	}

	private void validaAmbientes(Collection<HistoricoColaborador> historicosDoColaborador, PppRelatorioException pppRelatorioException, Empresa empresa) 
	{
		for (HistoricoColaborador historicoColaborador : historicosDoColaborador)
		{
			if (historicoColaborador.getAmbiente() != null)
			{
				HistoricoAmbiente historicoAmbiente = historicoAmbienteManager.findUltimoHistoricoAteData(historicoColaborador.getAmbiente().getId(), historicoColaborador.getData());
				
				if (historicoAmbiente == null)
					pppRelatorioException.addHistoricoSemHistoricoAmbiente(historicoColaborador.getData(), historicoColaborador.getAmbiente().getId());
				else if (historicoAmbiente.getRiscoAmbientes() != null && historicoAmbiente.getRiscoAmbientes().size() > 0)
					validaMedicaoNoHistoricoAmbiente(pppRelatorioException, historicoColaborador.getAmbiente(), historicoColaborador.getData(), empresa);
			}
		}
	}
	
	private void validaFuncoes(Collection<HistoricoColaborador> historicosDoColaborador, PppRelatorioException pppRelatorioException) 
	{
		for (HistoricoColaborador historicoColaborador : historicosDoColaborador)
		{
			if (historicoColaborador.getFuncao() != null)
			{
				HistoricoFuncao historicoFuncao = historicoFuncaoManager.findUltimoHistoricoAteData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData());
				
				if (historicoFuncao == null)
					pppRelatorioException.addHistoricoSemHistoricoFuncao(historicoColaborador.getData(), historicoColaborador.getFuncao().getId(), historicoColaborador.getFaixaSalarial().getCargo().getId());
			}
		}
	}

	private void validaMedicaoNoHistoricoAmbiente(PppRelatorioException pppRelatorioException, Ambiente ambiente, Date historicoAmbienteData, Empresa empresa) 
	{
		MedicaoRisco medicaoRisco = riscoMedicaoRiscoManager.findUltimaAteData(ambiente.getId(), historicoAmbienteData);
		if (medicaoRisco == null)
			pppRelatorioException.addAmbienteSemMedicao(ambiente, historicoAmbienteData, empresa);
	}

	public Integer getCount(Long cargoId)
	{
		return getDao().getCount(cargoId);
	}

	public Collection<Funcao> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(0, 0, cargoId);
	}

	public Collection<Funcao> findByCargo(int page, int pagingSize, Long cargoId)
	{
		return getDao().findByCargo(page, pagingSize, cargoId);
	}

	public Collection<Funcao> findByEmpresa(Long empresaId)
	{
		return getDao().findByEmpresa(empresaId);
	}

	public void removeFuncaoAndHistoricosByCargo(Long cargoId)
	{
		Collection<Funcao> funcaos = findByCargo(cargoId);

		if(funcaos.size() > 0)
		{
			HistoricoFuncaoManager historicoFuncaoManager = (HistoricoFuncaoManager) SpringUtil.getBean("historicoFuncaoManager");

			CollectionUtil<Funcao> funcaoUtil = new CollectionUtil<Funcao>();
			Long[] funcaoIds = funcaoUtil.convertCollectionToArrayIds(funcaos);
			historicoFuncaoManager.removeByFuncoes(funcaoIds);
			remove(funcaoIds);
		}
	}
	
	public void removeFuncao(Funcao funcao)
	{
		RiscoFuncaoManager riscoFuncaoManager = (RiscoFuncaoManager) SpringUtil.getBean("riscoFuncaoManager");
		riscoFuncaoManager.removeByFuncao(funcao.getId());
		
		HistoricoFuncaoManager historicoFuncaoManager = (HistoricoFuncaoManager) SpringUtil.getBean("historicoFuncaoManager");
		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoManager.findByFuncao(funcao.getId());
		for (HistoricoFuncao historicoFuncao : historicoFuncaos)
			historicoFuncaoManager.remove(historicoFuncao.getId());
		
		remove(funcao);
	}

	public Collection<Long> getIdsFuncoes(Collection<HistoricoColaborador> historicosColaborador)
	{
		Collection<Long> idFuncaos = new HashSet<Long>();

		for (HistoricoColaborador historicoColaborador : historicosColaborador)
		{
			if (historicoColaborador.getFuncao() != null && !idFuncaos.contains(historicoColaborador.getFuncao().getId()))
				idFuncaos.add(historicoColaborador.getFuncao().getId());
		}

		return idFuncaos;
	}

	public Collection<Funcao> findFuncaoByFaixa(Long faixaId)
	{
		return getDao().findFuncaoByFaixa(faixaId);
	}

	public Funcao findByIdProjection(Long funcaoId)
	{
		return getDao().findByIdProjection(funcaoId);
	}

	public Collection<Funcao> findFuncoesDoAmbiente(Long ambienteId, Date data) 
	{
		return getDao().findFuncoesDoAmbiente(ambienteId, data);
	}
	
	public Collection<QtdPorFuncaoRelatorio> getQtdColaboradorByFuncao(Long empresaId, Long estabelecimentoId, Date data, char tipoAtivo) {
		
		Map<Long, QtdPorFuncaoRelatorio> mapQtdPorFuncaoRelatorio = new HashMap<Long, QtdPorFuncaoRelatorio>();  
		Collection<QtdPorFuncaoRelatorio> qtdPorFuncaoRelatorios = new ArrayList<QtdPorFuncaoRelatorio>();  
		Collection<Object[]> totalHomensMulheresPorFuncao = getDao().getQtdColaboradorByFuncao(empresaId, estabelecimentoId, data, tipoAtivo); 
		
		for (Object[] HomensMulheresDeUmaFuncao : totalHomensMulheresPorFuncao) {
			
			if(mapQtdPorFuncaoRelatorio.containsKey((Long)HomensMulheresDeUmaFuncao[0])){
				QtdPorFuncaoRelatorio qtdPorFuncaoRelatorioTmp = mapQtdPorFuncaoRelatorio.get((Long)HomensMulheresDeUmaFuncao[0]);
				qtdPorFuncaoRelatorioTmp.setQtdHomesAndMulheres((Integer) HomensMulheresDeUmaFuncao[2], (Integer) HomensMulheresDeUmaFuncao[3]);
			} else {
				QtdPorFuncaoRelatorio qtdPorFuncaoRelatorioTmp = new QtdPorFuncaoRelatorio((Long)HomensMulheresDeUmaFuncao[0], (String)HomensMulheresDeUmaFuncao[1], (Integer) HomensMulheresDeUmaFuncao[2], (Integer) HomensMulheresDeUmaFuncao[3]);
				mapQtdPorFuncaoRelatorio.put(qtdPorFuncaoRelatorioTmp.getFuncaoId(), qtdPorFuncaoRelatorioTmp);
				qtdPorFuncaoRelatorios.add(qtdPorFuncaoRelatorioTmp);
			}
				
		}
		
		return qtdPorFuncaoRelatorios;
	}
	
	public Collection<CheckBox> populaCheckBox() {
		try
		{
			Collection<Funcao> funcoesTmp = getDao().findAll();
			return CheckListBoxUtil.populaCheckListBox(funcoesTmp, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setCatManager(CatManager catManager) {
		this.catManager = catManager;
	}

	public void setHistoricoAmbienteManager(
			HistoricoAmbienteManager historicoAmbienteManager) {
		this.historicoAmbienteManager = historicoAmbienteManager;
	}

	public void setRiscoMedicaoRiscoManager(
			RiscoMedicaoRiscoManager riscoMedicaoRiscoManager) {
		this.riscoMedicaoRiscoManager = riscoMedicaoRiscoManager;
	}

	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}

	public void setEngenheiroResponsavelManager(EngenheiroResponsavelManager engenheiroResponsavelManager) {
		this.engenheiroResponsavelManager = engenheiroResponsavelManager;
	}

	public void setMedicoCoordenadorManager(MedicoCoordenadorManager medicoCoordenadorManager) {
		this.medicoCoordenadorManager = medicoCoordenadorManager;
	}

	public Collection<Long> findFuncaoAtualDosColaboradores(Date data, Long estabelecimentoId)
	{
		return getDao().findFuncaoAtualDosColaboradores(data, estabelecimentoId);
	}

	public Collection<String> findColaboradoresSemFuncao(Date data, Long estabelecimentoId)
	{
		return getDao().findColaboradoresSemFuncao(data, estabelecimentoId);
	}
}