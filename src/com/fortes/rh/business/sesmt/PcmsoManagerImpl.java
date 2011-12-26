package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.CatRelatorioAnual;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;
import com.fortes.rh.model.sesmt.relatorio.PCMSO;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;


public class PcmsoManagerImpl implements PcmsoManager
{
	private AgendaManager agendaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private RealizacaoExameManager realizacaoExameManager;
	private CatManager catManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private FuncaoManager funcaoManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private EmpresaManager empresaManager;
	private ComposicaoSesmtManager composicaoSesmtManager;

	public Collection<PCMSO> montaRelatorio(Date dataIni, Date dataFim, Estabelecimento estabelecimento, Long empresaId, boolean exibirAgenda, boolean exibirDistColaboradorSetor, boolean exibirRiscos, boolean exibirEpis, boolean exibirExames, boolean exibirAcidentes, boolean exibirComposicaoSesmt) throws Exception
	{
		if(dataIni.compareTo(dataFim) == 1)
			throw new ColecaoVaziaException("Data inicial maior que data final.");
		
		Collection<PCMSO> pcmsos = new ArrayList<PCMSO>();
		
		estabelecimento = estabelecimentoManager.findComEnderecoById(estabelecimento.getId());
		Empresa empresa = empresaManager.findByIdProjection(empresaId);

		PCMSO pcmso = new PCMSO(exibirAgenda, exibirDistColaboradorSetor, exibirRiscos, exibirEpis, exibirExames, exibirAcidentes, exibirComposicaoSesmt);
		pcmso.setEmpresa(empresa);
		pcmso.setEstabelecimento(estabelecimento);
		
		
		StringBuilder msg = new StringBuilder("");
		msg.append(montaAgenda(pcmso, dataIni, dataFim, estabelecimento, exibirAgenda));
		msg.append(montaColaboradoresPorSetor(pcmso, dataFim, empresaId, estabelecimento, exibirDistColaboradorSetor));
		msg.append(montaTabelaAnualExames(pcmso, dataIni, estabelecimento, exibirExames));
		msg.append(montaTabelaAnualCats(pcmso, dataIni, estabelecimento, exibirAcidentes));
		msg.append(montaEpisFuncao(pcmso, dataIni, estabelecimento, exibirEpis));
		msg.append(montaRiscosAmbientais(pcmso, dataIni, estabelecimento, exibirRiscos));
		msg.append(montaComposicaoSesmt(pcmso, dataFim, empresaId, exibirComposicaoSesmt));
		
		if(!msg.toString().equals(""))
			throw new ColecaoVaziaException(msg.toString());
		
		pcmsos.add(pcmso);
		
		return pcmsos;
	}

	private String montaEpisFuncao(PCMSO pcmso, Date data, Estabelecimento estabelecimento, boolean exibirEpis) throws ColecaoVaziaException
	{
		if (exibirEpis)
		{
			Collection<String> colaboradores = funcaoManager.findColaboradoresSemFuncao(data, estabelecimento.getId());
			if(!colaboradores.isEmpty())
				return "Não existem Funções para os Colaboradores: " + StringUtil.converteCollectionToString(colaboradores) + "<br>";

			Collection<Long> funcaoIds = funcaoManager.findFuncaoAtualDosColaboradores(data, estabelecimento.getId());
			Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoManager.findEpis(funcaoIds, data);
			
			if (historicoFuncaos.isEmpty())
				return "Não existem dados de EPIs para o período informado.<br>";
			
			pcmso.setHistoricoFuncaos(historicoFuncaos);
		}

		return "";
	}
	
	private String montaRiscosAmbientais (PCMSO pcmso, Date data, Estabelecimento estabelecimento, boolean exibirRiscos) throws ColecaoVaziaException
	{
		if (exibirRiscos)
		{
			Collection<String> colaboradores = riscoAmbienteManager.findColaboradoresSemAmbiente(data, estabelecimento.getId());
			if(!colaboradores.isEmpty())
				return "Não existe Ambiente para os Colaboradores: " + StringUtil.converteCollectionToString(colaboradores) + "<br>";
			
			Collection<Long> funcaoIds = riscoAmbienteManager.findAmbienteAtualDosColaboradores(data, estabelecimento.getId());
			Collection<HistoricoAmbiente> historicoAmbiente = historicoAmbienteManager.findRiscosAmbientes(funcaoIds, data);
			
			if (historicoAmbiente.isEmpty())
				return "Não existem dados de EPIs para o período informado.<br>";
			
			pcmso.setHistoricoAmbientes(historicoAmbiente);
		}
		
		return "";
	}

	private String montaTabelaAnualCats(PCMSO pcmso, Date data, Estabelecimento estabelecimento, boolean exibirAcidentes) throws ColecaoVaziaException
	{
		if (exibirAcidentes)
		{
			Collection<CatRelatorioAnual> cats = catManager.getRelatorioAnual(estabelecimento.getId(), data);
			
			if (!cats.isEmpty())
				pcmso.setCats(cats);
		}
		
		return "";
	}

	private String montaTabelaAnualExames(PCMSO pcmso, Date data, Estabelecimento estabelecimento, boolean exibirExames) throws ColecaoVaziaException
	{
		if (exibirExames)
		{
			Collection<ExameAnualRelatorio> exames = realizacaoExameManager.getRelatorioAnual(estabelecimento.getId(), data);
			
			if (exames.isEmpty())
				return "Não existem dados de Exames para o período informado.<br>";
			
			pcmso.setExames(exames);
		}
		
		return "";
	}

	public String montaColaboradoresPorSetor(PCMSO pcmso, Date data, Long empresaId, Estabelecimento estabelecimento, boolean exibirDistColaboradorSetor) throws Exception 
	{
		if (exibirDistColaboradorSetor)
		{
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findQtdColaboradorPorArea(estabelecimento.getId(), data);
	
			if (areaOrganizacionals.isEmpty())
				return "Não existem dados de Área Organizacional para o período informado.<br>";
			
			areaOrganizacionals = getAreasComFamilia(areaOrganizacionals, empresaId);
			
			pcmso.setSetores(areaOrganizacionals);
		}
		
		return "";
	}
	
	private Collection<AreaOrganizacional> getAreasComFamilia(Collection<AreaOrganizacional> areaOrganizacionals, Long empresaId)
	{
		Collection<AreaOrganizacional> areasComFamilia = new ArrayList<AreaOrganizacional>();

		try
		{
			Collection<AreaOrganizacional> areasTodas = areaOrganizacionalManager.findAllListAndInativa(empresaId, AreaOrganizacional.TODAS, null);
			areasTodas = areaOrganizacionalManager.montaFamilia(areasTodas);

			for (AreaOrganizacional areaOrganizacional : areaOrganizacionals) 
			{
				if(areaOrganizacional != null && areaOrganizacional.getId() != null)
				{
					AreaOrganizacional areaTmp = areaOrganizacionalManager.getAreaOrganizacional(areasTodas, areaOrganizacional.getId());
					areaTmp.setColaboradorCount(areaOrganizacional.getColaboradorCount());
					areasComFamilia.add(areaTmp);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		CollectionUtil<AreaOrganizacional> collectionUtil = new CollectionUtil<AreaOrganizacional>();
		
		return collectionUtil.sortCollectionStringIgnoreCase(areasComFamilia, "descricao");
	}
	
	public String montaAgenda(PCMSO pcmso, Date dataIni, Date dataFim, Estabelecimento estabelecimento, boolean exibirAgenda) throws ColecaoVaziaException
	{
		if (exibirAgenda)
		{
			Collection<Agenda> agendas = agendaManager.findByPeriodo(dataIni, dataFim, null, estabelecimento); 
			
			if(agendas.isEmpty())
				return "Não existem dados da Agenda para o período informado.<br>";
				
			Evento eventoTodosMeses = new Evento();
			eventoTodosMeses.setNome("naoMeApague");
			
			int difEntreDatas = DateUtil.mesesEntreDatas(dataIni, dataFim); 
			
			for (int i = 0; i <= difEntreDatas; i++)
			{
				Agenda agenda = new Agenda();
				if(i == 0)
					agenda.setData(dataIni);
				else
					agenda.setData(DateUtil.incrementaMes(dataIni, 1));
					
				agenda.setEvento(eventoTodosMeses);
				agendas.add(agenda);
				
				dataIni = agenda.getData();
			}
			
			pcmso.setAgendas(agendas);
		}
		return "";
	}
	
	private String montaComposicaoSesmt(PCMSO pcmso, Date data, Long empresaId, boolean exibirComposicaoSesmt)
	{
		if (exibirComposicaoSesmt)
		{
			ComposicaoSesmt composicaoSesmt = composicaoSesmtManager.findByData(empresaId, data);
			pcmso.setComposicaoSesmts(Arrays.asList(composicaoSesmt));
		}
		return "";
	}
	
	public void setAgendaManager(AgendaManager agendaManager)
	{
		this.agendaManager = agendaManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setRealizacaoExameManager(RealizacaoExameManager realizacaoExameManager) {
		this.realizacaoExameManager = realizacaoExameManager;
	}

	public void setCatManager(CatManager catManager) {
		this.catManager = catManager;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}

	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager) {
		this.historicoAmbienteManager = historicoAmbienteManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setComposicaoSesmtManager(ComposicaoSesmtManager composicaoSesmtManager) {
		this.composicaoSesmtManager = composicaoSesmtManager;
	}
}