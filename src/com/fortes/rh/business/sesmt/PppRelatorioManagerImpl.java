package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
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
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.ibm.icu.util.Calendar;

@SuppressWarnings("unchecked")
public class PppRelatorioManagerImpl implements PppRelatorioManager
{
	private ColaboradorManager colaboradorManager;
	private CatManager catManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private EpiManager epiManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private EngenheiroResponsavelManager engenheiroResponsavelManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private EmpresaManager empresaManager;
	private boolean controlaRiscoPorAmbiente;
	
	public Collection<PppRelatorio> populaRelatorioPpp(Colaborador colaborador, Empresa empresa, Date data, String nit, String cnae, String responsavel, String observacoes, String[] respostas, Long empresaId) throws Exception
	{
		Collection<HistoricoColaborador> historicosDoColaboradors = historicoColaboradorManager.findByColaboradorData(colaborador.getId(),data);
		
		if(historicosDoColaboradors == null || historicosDoColaboradors.isEmpty())
			throw new PppRelatorioException("Não existem dados para gerar o relatório.");
		
		historicosDoColaboradors = historicoColaboradorManager.filtraHistoricoColaboradorParaPPP(historicosDoColaboradors); 
		controlaRiscoPorAmbiente = empresaManager.isControlaRiscoPorAmbiente(empresaId);
		
		this.validarPpp(historicosDoColaboradors, empresa);
		
		Collection<Cat> cats = catManager.findCatsColaboradorByDate(colaborador,data);
		colaborador = colaboradorManager.findById(colaborador.getId());
		colaborador.getEmpresa().setCnae(cnae);
		
		HistoricoColaborador ultimoHistorico = ((HistoricoColaborador) historicosDoColaboradors.toArray()[historicosDoColaboradors.size()-1]);
		Estabelecimento estabelecimento = ultimoHistorico.getEstabelecimento();

		historicosDoColaboradors = historicoColaboradorManager.inserirPeriodos(historicosDoColaboradors);

		Collection<PppFatorRisco> pppFatorRiscos = this.populaFatoresDeRiscos(data, historicosDoColaboradors);
		Collection<HistoricoColaborador> historicosColaboradorFuncao = historicoColaboradorManager.findDistinctFuncao(historicosDoColaboradors);
		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoManager.findHistoricoFuncaoColaborador(historicosColaboradorFuncao, data, colaborador.getDataDesligamento());
		Collection<EngenheiroResponsavel> engenheirosResponsaveis = engenheiroResponsavelManager.getEngenheirosAteData(colaborador, data);
		Collection<MedicoCoordenador> medicosCoordenadores = medicoCoordenadorManager.getMedicosAteData(data, colaborador);
		atualizaNomeFuncaoByData(historicosDoColaboradors, data);
		
		return Arrays.asList(new PppRelatorio(colaborador, estabelecimento, data, respostas, cats, historicosDoColaboradors,
				historicoFuncaos, pppFatorRiscos, nit, responsavel, observacoes, engenheirosResponsaveis, medicosCoordenadores));
	}
	
	private void atualizaNomeFuncaoByData(Collection<HistoricoColaborador> historicosDoColaboradors, Date data) {
		Date ultimaDataFuncao;
		for (HistoricoColaborador historicoColaborador : historicosDoColaboradors) {
			if(historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getHistoricoFuncaos() != null){
				ultimaDataFuncao = null;
				for (HistoricoFuncao historicoFuncao : historicoColaborador.getFuncao().getHistoricoFuncaos()) {
					if(historicoFuncao.getData().getTime() <= data.getTime()){
						if(ultimaDataFuncao == null)
							ultimaDataFuncao =  historicoFuncao.getData();
						
						if(historicoFuncao.getData().getTime() >= ultimaDataFuncao.getTime())
							historicoColaborador.getFuncao().setNome(historicoFuncao.getFuncaoNome());
					}
				}
			}
		}
	}

	private Collection<PppFatorRisco> populaFatoresDeRiscos(Date data, Collection<HistoricoColaborador> historicosDoColaborador){
		Collection<PppFatorRisco> pppFatoresRiscos = new ArrayList<PppFatorRisco>();
		
		pppFatoresRiscos.addAll(populaFatoresDeRiscosAmbienteOuFuncao(data, historicosDoColaborador));
		
		if (pppFatoresRiscos.isEmpty())
			return Arrays.asList(new PppFatorRisco());
		
		Collections.sort((List<PppFatorRisco>)pppFatoresRiscos, new Comparator<PppFatorRisco>() {
			@Override
			public int compare(PppFatorRisco pppFatorRisco1, PppFatorRisco pppFatorRisco2) {
				int i = pppFatorRisco1.getRisco().getDescricao().compareTo(pppFatorRisco2.getRisco().getDescricao());
				
				if(i == 0)
					i = pppFatorRisco1.getDataInicio().compareTo(pppFatorRisco2.getDataInicio());
				
				return i;
			}
		});

		return pppFatoresRiscos;
	}
	
	private Collection<PppFatorRisco> populaFatoresDeRiscosAmbienteOuFuncao(Date data, Collection<HistoricoColaborador> historicosDoColaborador) {
		
		List<HistoricoColaborador> hist = new CollectionUtil<HistoricoColaborador>().convertCollectionToList(historicosDoColaborador);
		List<PppFatorRisco> pppFatorRiscos = new ArrayList<PppFatorRisco>();
		
		for (int i=0; i < hist.size(); i++)
		{
			Date dataHistColaboradorIni = hist.get(i).getData();
			Date dataFim = data;
			
			if (i+1 < hist.size())
				dataFim = hist.get(i+1).getData();
			
			List<DadosAmbienteOuFuncaoRisco> dadosAmbientesOuFuncoesRiscos = new ArrayList<>();
			if(controlaRiscoPorAmbiente)
				dadosAmbientesOuFuncoesRiscos = historicoAmbienteManager.findDadosNoPeriodo(hist.get(i).getAmbiente().getId(), dataHistColaboradorIni, dataFim);
			else
				dadosAmbientesOuFuncoesRiscos = historicoFuncaoManager.findDadosNoPeriodo(hist.get(i).getFuncao().getId(), dataHistColaboradorIni, dataFim);
			
			for (DadosAmbienteOuFuncaoRisco dadosAmbienteOuFuncaoRisco : dadosAmbientesOuFuncoesRiscos){
				Date dataAmbienteOuFuncaoIni = dadosAmbienteOuFuncaoRisco.getHistoricoAmbienteOuFuncaoData();
				Date dataAmbienteOuFuncaoFim = getProxDataHistorico(dadosAmbientesOuFuncoesRiscos, dataAmbienteOuFuncaoIni);
				
				if(dataAmbienteOuFuncaoFim == null)//historico do ambiente ficou aberto, pega a data do historico colaborador
					dataAmbienteOuFuncaoFim = dataFim;
				
				Collection<Epi> epis = epiManager.findByRiscoAmbienteOuFuncao(dadosAmbienteOuFuncaoRisco.getRiscoId(), dadosAmbienteOuFuncaoRisco.getAmbienteOuFuncaoId(), dataAmbienteOuFuncaoIni, controlaRiscoPorAmbiente); // pegando EPIs do risco
				List<RiscoMedicaoRisco> riscoMedicaoRiscos = riscoMedicaoRiscoManager.getByRiscoPeriodo(dadosAmbienteOuFuncaoRisco.getRiscoId(), dadosAmbienteOuFuncaoRisco.getAmbienteOuFuncaoId(), dataAmbienteOuFuncaoIni, dataAmbienteOuFuncaoFim, controlaRiscoPorAmbiente);
				
				int posicao = 0;
				for (int j=0; j < riscoMedicaoRiscos.size(); j++){
					RiscoMedicaoRisco riscoMedicaoRiscoTemp = riscoMedicaoRiscos.get(j);
					Date dataMedicao = riscoMedicaoRiscoTemp.getMedicaoRisco().getData();
					
					// A princípio, o período inicia com o histórico de ambiente, até o fim deste.
					Date pppFatorRiscoIni = dataAmbienteOuFuncaoIni;
					Date pppFatorRiscoFim = dataAmbienteOuFuncaoFim;
					posicao++;
					
					// no início do período prevalece a data mais recente: 
					// histórico do ambiente, data da medição ou histórico do colaborador
					if (dataMedicao.compareTo(pppFatorRiscoIni) == 1)
						pppFatorRiscoIni = dataMedicao;
					if (dataHistColaboradorIni.compareTo(pppFatorRiscoIni) == 1)
						pppFatorRiscoIni = dataHistColaboradorIni;
					
					if (posicao == 1 && riscoMedicaoRiscos.size() == 1)//só existe uma medição para esse risco
						pppFatorRiscoFim = dataAmbienteOuFuncaoFim;
					else if (posicao < riscoMedicaoRiscos.size()) //no meio deles
						pppFatorRiscoFim = riscoMedicaoRiscos.get(j+1).getMedicaoRisco().getData();
					else if (posicao == riscoMedicaoRiscos.size()) //no fim
						pppFatorRiscoFim = dataAmbienteOuFuncaoFim;
					
					if(pppFatorRiscoIni.before(pppFatorRiscoFim))
						pppFatorRiscos.add(new PppFatorRisco(pppFatorRiscoIni, pppFatorRiscoFim, dadosAmbienteOuFuncaoRisco.getRiscoId(), dadosAmbienteOuFuncaoRisco.getRiscoTipo(), dadosAmbienteOuFuncaoRisco.getRiscoDescricao(), riscoMedicaoRiscoTemp.getMedicaoRisco().getId(), riscoMedicaoRiscoTemp.getIntensidadeMedida(), 
								riscoMedicaoRiscoTemp.getTecnicaUtilizada(), dadosAmbienteOuFuncaoRisco.isEpcEficaz(), epis, dadosAmbienteOuFuncaoRisco.getHistoricoAmbienteOuFuncaoData(), hist.get(i).getColaborador().getDataDesligamento()));
				}
			}
		}

		return relacionaDataFatoresDeRisco(pppFatorRiscos);
	}

	private Collection<PppFatorRisco> relacionaDataFatoresDeRisco(List<PppFatorRisco> pppFatorRiscos) {
		
		HashMap<Date, Collection<Long>> riscosPorHistoricoAmbiente = this.relacionarRiscosPorHistoricoAmbienteOuFuncao(pppFatorRiscos); 

		// tratando os fatores riscos repetidos 
		for (int i=0; i< pppFatorRiscos.size(); i++){
			PppFatorRisco pppFatorRiscoInicial = pppFatorRiscos.get(i);
			Long riscoId = pppFatorRiscoInicial.getRisco().getId();
			
			if (pppFatorRiscoInicial.isFlagRemover())
				continue;
			
			for (int j = i+1; j < pppFatorRiscos.size(); j++){
				PppFatorRisco pppFatorRiscoTmp = pppFatorRiscos.get(j);
				Date histAmbOuFuncData = pppFatorRiscoTmp.getDataHistoricoAmbienteOuFuncao();
				
				// se o risco deixou de existir nessa data
				if (!riscosPorHistoricoAmbiente.get(histAmbOuFuncData).contains(riscoId) && !pppFatorRiscoInicial.isDataFinalJaSetada()){
					pppFatorRiscoInicial.setDataFim(pppFatorRiscoTmp.getDataInicio());
					break;
				}else{
					if (pppFatorRiscoTmp.isFlagRemover())
						continue;
					
					if (riscoId.equals(pppFatorRiscoTmp.getRisco().getId())){
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
		
		return ajustaFatoresDeRiscoEDatasEmAberto(pppFatorRiscos);
	}

	private Collection<PppFatorRisco> ajustaFatoresDeRiscoEDatasEmAberto(List<PppFatorRisco> pppFatorRiscos) {
		List<PppFatorRisco> pppFatorRiscosTmp = new ArrayList<PppFatorRisco>();
		Date hoje=Calendar.getInstance().getTime();

		// remover os "flagados" e deixar datas em aberto se a data final do PPP for HOJE.
		for (PppFatorRisco pppFatorRisco : pppFatorRiscos) 
		{
			if (!pppFatorRisco.isFlagRemover())
			{
				if (DateUtil.equals(hoje, pppFatorRisco.getDataFim()))
					pppFatorRisco.setDataFim(null);
	
				pppFatorRiscosTmp.add(pppFatorRisco);
			}
		}
	
		return pppFatorRiscosTmp;
	}

	private HashMap<Date, Collection<Long>> relacionarRiscosPorHistoricoAmbienteOuFuncao(List<PppFatorRisco> pppFatorRiscos) 
	{
		HashMap<Date, Collection<Long>> riscosPorHistoricoAmbienteOuFuncao = new HashMap<Date, Collection<Long>>();
		for (PppFatorRisco pppFatorRisco : pppFatorRiscos) 
		{
			Date historicoAmbienteOuFuncaoData = pppFatorRisco.getDataHistoricoAmbienteOuFuncao();
			
			if (riscosPorHistoricoAmbienteOuFuncao.get(historicoAmbienteOuFuncaoData) == null)
				riscosPorHistoricoAmbienteOuFuncao.put(historicoAmbienteOuFuncaoData, new TreeSet<Long>());
			
			riscosPorHistoricoAmbienteOuFuncao.get(historicoAmbienteOuFuncaoData).add(pppFatorRisco.getRisco().getId());
		}
		
		return riscosPorHistoricoAmbienteOuFuncao; 
	}

	private Date getProxDataHistorico(List<DadosAmbienteOuFuncaoRisco> historicoAmbienteFuncaoRiscos, Date dataAmbienteOuFuncaoIni) 
	{
		for (DadosAmbienteOuFuncaoRisco dadosAmbienteOuFuncaoRisco : historicoAmbienteFuncaoRiscos) 
		{
			if(dadosAmbienteOuFuncaoRisco.getHistoricoAmbienteOuFuncaoData().compareTo(dataAmbienteOuFuncaoIni) > 0)
				return dadosAmbienteOuFuncaoRisco.getHistoricoAmbienteOuFuncaoData();
		}
		
		return null;
	}
	
	private void validarPpp(Collection<HistoricoColaborador> historicos, Empresa empresa) throws PppRelatorioException 
	{
		PppRelatorioException pppRelatorioException = new PppRelatorioException();
		
		this.validaHistoricos(historicos, pppRelatorioException);
		
		if(controlaRiscoPorAmbiente)
			this.validaAmbientes(historicos, pppRelatorioException, empresa);
		else
			this.validaFuncoes(historicos, pppRelatorioException, empresa);
		
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
	
	private void validaFuncoes(Collection<HistoricoColaborador> historicosDoColaborador, PppRelatorioException pppRelatorioException, Empresa empresa) 
	{
		for (HistoricoColaborador historicoColaborador : historicosDoColaborador)
		{
			if (historicoColaborador.getFuncao() != null)
			{
				HistoricoFuncao historicoFuncao = historicoFuncaoManager.findUltimoHistoricoAteData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData());
				
				if (historicoFuncao == null)
					pppRelatorioException.addHistoricoSemHistoricoFuncao(historicoColaborador.getData(), historicoColaborador.getFuncao().getId(), historicoColaborador.getFaixaSalarial().getCargo().getId());
				else if (historicoFuncao.getRiscoFuncaos() != null && historicoFuncao.getRiscoFuncaos().size() > 0)
					validaMedicaoNoHistoricoFuncao(pppRelatorioException, historicoColaborador.getFuncao(), historicoColaborador.getData(), empresa);
			}
		}
	}
	
	private void validaMedicaoNoHistoricoAmbiente(PppRelatorioException pppRelatorioException, Ambiente ambiente, Date historicoAmbienteData, Empresa empresa) 
	{
		MedicaoRisco medicaoRisco = riscoMedicaoRiscoManager.findUltimaAteData(ambiente.getId(), historicoAmbienteData, controlaRiscoPorAmbiente);
		if (medicaoRisco == null)
			pppRelatorioException.addAmbienteSemMedicao(ambiente, historicoAmbienteData);
	}
	
	private void validaMedicaoNoHistoricoFuncao(PppRelatorioException pppRelatorioException, Funcao funcao, Date historicoFuncaoData, Empresa empresa) 
	{
		MedicaoRisco medicaoRisco = riscoMedicaoRiscoManager.findUltimaAteData(funcao.getId(), historicoFuncaoData, controlaRiscoPorAmbiente);
		if (medicaoRisco == null)
			pppRelatorioException.addFuncaoSemMedicao(funcao, historicoFuncaoData);
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setCatManager(CatManager catManager) {
		this.catManager = catManager;
	}

	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager) {
		this.historicoAmbienteManager = historicoAmbienteManager;
	}

	public void setRiscoMedicaoRiscoManager(RiscoMedicaoRiscoManager riscoMedicaoRiscoManager) {
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

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager) {
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public void setHistoricoColaboradorManager(
			HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}