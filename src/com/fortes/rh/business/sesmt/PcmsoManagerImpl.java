package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class PcmsoManagerImpl implements PcmsoManager
{
	@Autowired private AgendaManager agendaManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private RealizacaoExameManager realizacaoExameManager;
	@Autowired private CatManager catManager;
	@Autowired private HistoricoFuncaoManager historicoFuncaoManager;
	@Autowired private FuncaoManager funcaoManager;
	@Autowired private RiscoAmbienteManager riscoAmbienteManager;
	@Autowired private HistoricoAmbienteManager historicoAmbienteManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private EmpresaManager empresaManager;
	@Autowired private ComposicaoSesmtManager composicaoSesmtManager;

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
		msg.append(montaTabelaAnualExames(pcmso, dataIni, dataFim, estabelecimento, exibirExames));
		msg.append(montaTabelaCats(pcmso, dataIni, dataFim, estabelecimento, exibirAcidentes));
		msg.append(montaEpisFuncao(pcmso, dataFim, estabelecimento, exibirEpis));
		msg.append(montaRiscosAmbientais(pcmso, dataFim, estabelecimento, exibirRiscos));
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
				return "Não existem funções para os colaboradores: " + StringUtil.converteCollectionToString(colaboradores) + "<br>";

			Collection<Long> funcaoIds = funcaoManager.findFuncaoAtualDosColaboradores(data, estabelecimento.getId());
			Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoManager.findEpis(funcaoIds, data);
			
			if (historicoFuncaos == null || historicoFuncaos.isEmpty())
				return "Não existem históricos de funções até a data final do período informado.<br>";
			
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
				return "Não existe ambiente para os colaboradores: " + StringUtil.converteCollectionToString(colaboradores) + "<br>";
			
			Collection<Long> ambienteIds = riscoAmbienteManager.findAmbienteAtualDosColaboradores(data, estabelecimento.getId());
			Collection<HistoricoAmbiente> historicoAmbiente = historicoAmbienteManager.findRiscosAmbientes(ambienteIds, data);
			
			if (historicoAmbiente == null || historicoAmbiente.isEmpty())
				return "Não existem históricos de ambientes até a data final do período informado.<br>";
			
			pcmso.setHistoricoAmbientes(historicoAmbiente);
		}
		
		return "";
	}

	private String montaTabelaCats(PCMSO pcmso, Date dataIni, Date dataFim, Estabelecimento estabelecimento, boolean exibirAcidentes) throws ColecaoVaziaException
	{
		if (exibirAcidentes)
		{
			Collection<CatRelatorioAnual> cats = catManager.getRelatorioCat(estabelecimento.getId(), dataIni, dataFim);
			
			if (!cats.isEmpty())
				pcmso.setCats(cats);
		}
		
		return "";
	}

	private String montaTabelaAnualExames(PCMSO pcmso, Date dataIni, Date dataFim, Estabelecimento estabelecimento, boolean exibirExames) throws ColecaoVaziaException
	{
		if (exibirExames)
		{
			Collection<ExameAnualRelatorio> exames = realizacaoExameManager.getRelatorioExame(estabelecimento.getId(), dataIni, dataFim);
			
			if (exames.isEmpty())
				return "Não existem dados de exames para o período informado.<br>";
			
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
				return "Não existem dados de área organizacional para o período informado.<br>";
			
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
			Collection<AreaOrganizacional> areasTodas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
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
				return "Não existem dados da agenda para o período informado.<br>";
				
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
}