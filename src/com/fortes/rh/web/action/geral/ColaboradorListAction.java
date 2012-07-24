package com.fortes.rh.web.action.geral;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Mes;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaboradorIntegraAC;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.rh.model.geral.DynaRecord;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ReportColumn;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.ibm.icu.util.Calendar;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorManager colaboradorManager = null;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CargoManager cargoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager;
	
	private Collection<Colaborador> colaboradors = null;
	private Colaborador colaborador;
	private String nomeBusca;
	private String nomeComercialBusca;
	private String cpfBusca;
	private String matriculaBusca;
	private Collection<Colaborador> colaboradores;
	private int pageAnt;
	private Collection<Estabelecimento> estabelecimentosList = new ArrayList<Estabelecimento>();
	private Collection<AreaOrganizacional> areasList = new ArrayList<AreaOrganizacional>();
	private Collection<Cargo> cargosList = new ArrayList<Cargo>();
	private Estabelecimento estabelecimento = new Estabelecimento();
	private AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
	private Cargo cargo = new Cargo();
	private Empresa empresa;
	
	private Map situacaos = new SituacaoColaborador();
	private Map situacaosIntegraAC = new SituacaoColaboradorIntegraAC();
	private String situacao;

	private Collection<String> dinamicColumns;
	private Collection<String> dinamicProperts;

	private Map statusRetornoACs = new StatusRetornoAC();
	private boolean integraAc;
	private boolean agruparPorTempoServico;
	
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] areaOrganizacionalsCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();
	private Collection<Empresa> empresas;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();

	private Collection<String> colunasMarcadas = new ArrayList<String>();
	private Collection<ReportColumn> colunas = new ArrayList<ReportColumn>();

	private boolean exibirNomeComercial;
	private boolean habilitaCampoExtra;
	
	private char exibir = ' ';
	
	private Integer tempoServico;
	private Integer[] tempoServicoIni;
	private Integer[] tempoServicoFim;
	private int mes;
	private Map meses = new Mes();
	
	//relatorio de admitidos
	private Date dataIni;
	private Date dataFim;
	private boolean exibirSomenteAtivos;

	private Long[] empresaIds;//repassado para o DWR

	private CamposExtras camposExtras = new CamposExtras();;
	private Map sexos = new Sexo();
	private String sexo;

	private Collection<DynaRecord> dataSource;
	private ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico;
	private String colunasJson;
	private String orderField;
	
	private InputStream reportInputStream;

	private String reportFilter;
	private String reportTitle;
	private Boolean compartilharColaboradores;
	
	private Collection<AutoCompleteVO> data;
	private String descricao;
	private String json;
	
	public String find() throws Exception
	{
		data = colaboradorManager.getAutoComplete(descricao, getEmpresaSistema().getId());
		json = StringUtil.toJSON(data, null);
		
		return Action.SUCCESS;
	}
	
	public String list() throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllListAndInativas(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null);
		areasList = areaOrganizacionalManager.montaFamilia(areaOrganizacionalsTmp);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasList = cu1.sortCollectionStringIgnoreCase(areasList, "descricao");

		estabelecimentosList = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		cargosList = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nomeMercado");

		if(StringUtils.isNotBlank(cpfBusca))
			cpfBusca = StringUtil.removeMascara(cpfBusca);

		if(situacao == null)
			situacao = "A";
		
		Long[] areasIdsPorResponsavel = null;
		
		if(getUsuarioLogado().getId() != 1L && !SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_COLAB_VER_TODOS"}))
		{
			areasIdsPorResponsavel = areaOrganizacionalManager.findIdsAreasResponsaveis(getUsuarioLogado(), getEmpresaSistema().getId());
			
			if(areasIdsPorResponsavel.length == 0)
				areasIdsPorResponsavel = new Long[]{-1L};//não vai achar nenhum colaborador
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("nomeComercialBusca", nomeComercialBusca);
		parametros.put("cpfBusca", cpfBusca);
		parametros.put("matriculaBusca", matriculaBusca);
		parametros.put("empresaId", getEmpresaSistema().getId());
		parametros.put("areaId", areaOrganizacional.getId());
		parametros.put("areasIdsPorResponsavel", areasIdsPorResponsavel);
		parametros.put("estabelecimentoId", estabelecimento.getId());
		parametros.put("cargoId", cargo.getId());
		parametros.put("situacao", situacao);
		
		//BACALHAU, refatorar outra consulta que ta com HQL, essa é em SQL...ajustar size ta pegando o tamanho da lista
		setTotalSize(colaboradorManager.getCountComHistoricoFuturoSQL(parametros));
		colaboradors = colaboradorManager.findComHistoricoFuturoSQL(getPage(), getPagingSize(), parametros);
		
		if (colaboradors == null || colaboradors.size() == 0)
			addActionMessage("Não existem colaboradores a serem listados.");
		
		integraAc = getEmpresaSistema().isAcIntegra();
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			colaboradorManager.remove(colaborador, getEmpresaSistema());
		} catch (InvocationTargetException e) {
			addActionError(e.getTargetException().getMessage());
			return "error";
		}
		addActionMessage("Colaborador excluído com sucesso.");
		return Action.SUCCESS;
	}

	public String prepareRelatorioAniversariantes()
	{
		prepareEmpresas("ROLE_REL_ANIVERSARIANTES");
		
		Calendar hoje = Calendar.getInstance();
		mes = hoje.get(Calendar.MONTH) + 1;
		
		return SUCCESS;
	}

	private void prepareEmpresas(String role)
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), role);
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();
	}
	
	public String prepareRelatorioDinamico()
	{
		Long usuarioId = SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession());
		prepareEmpresas("ROLE_REL_LISTA_COLAB");
		
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);//usado pelo DWR
		
		empresa = getEmpresaSistema();
		
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraColaborador();
		
		configuracaoRelatorioDinamico = configuracaoRelatorioDinamicoManager.findByUsuario(usuarioId);
		if(configuracaoRelatorioDinamico == null)
			configuracaoRelatorioDinamico = new ConfiguracaoRelatorioDinamico();
		
		montaColunas();
		colunasJson = StringUtil.toJSON(colunas, null);

		return Action.SUCCESS;
	}

	private void montaColunas() {
		colunas = ReportColumn.getColumns();

		if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_COMPROU_SESMT"}) )
		{
			colunas.add(new ReportColumn("Função", "funcaoNome", "fun.nome", 100, false));
			colunas.add(new ReportColumn("Ambiente", "ambienteNome", "amb.nome", 100, false));
		}
		
		if(habilitaCampoExtra)
		{
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
			
			for (ConfiguracaoCampoExtra configuracaoCampoExtra : configuracaoCampoExtras)
			{
				String orderField = "ce." + configuracaoCampoExtra.getNome();
				String nomeExtra = "camposExtras." + configuracaoCampoExtra.getNome();
				if(!configuracaoCampoExtra.getTipo().equals("texto"))
					nomeExtra = "camposExtras." + configuracaoCampoExtra.getNome() + "String";
					
				colunas.add(new ReportColumn(configuracaoCampoExtra.getTitulo(), nomeExtra, orderField, configuracaoCampoExtra.getSize(), false));
			}
		}
		
	}

	public String relatorioDinamico() throws Exception
	{
		try
		{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
			camposExtras.setId(1l);
			
			if(agruparPorTempoServico)
				orderField = " co.dataAdmissao desc, " + orderField;
			
			Collection<Colaborador> colaboradores = colaboradorManager.findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentos, areas, camposExtras, empresa.getId(), orderField, dataIni, dataFim, sexo, tempoServicoIni, tempoServicoFim);

			if(colaboradores.isEmpty())
				throw new Exception("SEM_DADOS");
			
			Context cx = Context.enter();
	        try {
	            cx.setLanguageVersion(Context.VERSION_1_7);
	            Scriptable scope = cx.initStandardObjects();
	            
	            String xml = ArquivoUtil.getReportSource("relatorioDinamico.jrxml");
	            if(agruparPorTempoServico)
	            	xml = ArquivoUtil.getReportSource("relatorioDinamicoAgrupado.jrxml");
	            
	            xml = xml.replaceAll("<\\?.*\\s+<!.*\\s+<!.*\\s+", "");
	            
	            StringBuilder sb = new StringBuilder();
	            sb.append("    var xml = " + xml + ";");
	           
	            int posicaoX = 0;
	            int valueWidth = 0;
	            int valueX = 0;

	            montaColunas();
	            String filtro = "Estabelecimentos: " + estabelecimentoManager.nomeEstabelecimentos(LongUtil.arrayStringToArrayLong(estabelecimentosCheck));
	            filtro += "\nÁreas Organizacionais: " + areaOrganizacionalManager.nomeAreas(LongUtil.arrayStringToArrayLong(areaOrganizacionalsCheck));
	            
	            parametros = RelatorioUtil.getParametrosRelatorio(configuracaoRelatorioDinamico.getTitulo(), getEmpresaSistema(), filtro);
	            
	            Collection<ReportColumn> colunasMarcadasRedimensionadas = ReportColumn.resizeColumns(colunas, colunasMarcadas);
	            
	            int count = 1;
	            for (ReportColumn coluna : colunasMarcadasRedimensionadas)
	            {
            		parametros.put("TITULO" + (count), coluna.getName());
            		
	            	valueWidth = coluna.getSize();
		            valueX = posicaoX;
		            			
		            if(agruparPorTempoServico)
		            	valueX += 18;
		            
		            sb.append(DynaRecord.montaEval("columnHeader", "width", count, valueWidth));
		            sb.append(DynaRecord.montaEval("columnHeader", "x", count, valueX));
		            sb.append(DynaRecord.montaEval("detail", "width", count, valueWidth));
		            sb.append(DynaRecord.montaEval("detail", "x", count, valueX));
		            
		            posicaoX += valueWidth + ReportColumn.getSpace();
		            count++;
				}
	            	            
				sb.append("    obj = xml.toXMLString();");
	            Object result = cx.evaluateString(scope, sb.toString(),	"MySource", 1,	null);

	            String relatorioDinamico = "" +
	            		"<?xml version=\"1.0\" encoding=\"UTF-8\"  ?>" +
	            		"<!DOCTYPE jasperReport PUBLIC \"//JasperReports//DTD Report Design//EN\" \"http://jasperreports.sourceforge.net/dtds/jasperreport.dtd\">" +
	            		result.toString();
	            
	            reportInputStream = new ByteArrayInputStream(relatorioDinamico.getBytes());
	            dataSource = colaboradorManager.preparaRelatorioDinamico(colaboradores, colunasMarcadas, tempoServicoIni, tempoServicoFim);

	        }
	        finally 
	        {
	            Context.exit();
	        }
		
			return Action.SUCCESS;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			if(e.getMessage().equals("SEM_DADOS"))
				addActionMessage("Não existem dados para o filtro informado.");
			else
				addActionMessage("Não foi possível gerar o relatório.");
				
			prepareRelatorioDinamico();
 			return Action.INPUT;
		}
	}
	
	public String relatorioDinamicoXLS() throws Exception
	{
		try
		{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
			camposExtras.setId(1l);
			
			if(agruparPorTempoServico)
				orderField = " co.dataAdmissao desc, " + orderField;
			
			colaboradores = colaboradorManager.findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentos, areas, camposExtras, empresa.getId(), orderField, dataIni, dataFim, sexo, null, null);
			
			if(colaboradores.isEmpty())
				throw new Exception("SEM_DADOS");
			
			reportFilter = "Emitido em: " + DateUtil.formataDiaMesAno(new Date());
			reportTitle = configuracaoRelatorioDinamico.getTitulo();
			
			dinamicColumns = new ArrayList<String>();
			dinamicProperts = new ArrayList<String>();
			montaColunas();
			
			if(agruparPorTempoServico){
				colaboradores = colaboradorManager.insereGrupoPorTempoServico(colaboradores, tempoServico);
				dinamicColumns.add("Tempo de serviço");  
				dinamicProperts.add("intervaloTempoServico");
			}
			
			for (String marcada : colunasMarcadas) 
			{
				for (ReportColumn coluna : colunas) 
				{
					if(marcada.equals(coluna.getProperty()))
					{
						dinamicColumns.add(coluna.getName());  
						dinamicProperts.add(coluna.getProperty());
						break;
					}
				}
			}
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			if(e.getMessage().equals("SEM_DADOS"))
				addActionMessage("Não existem dados para o filtro informado.");
			else
				addActionMessage("Não foi possível gerar o relatório.");
			
			prepareRelatorioDinamico();
			return Action.INPUT;
		}
	}
	
	
	public String relatorioAniversariantes()
	{
		try
		{
			boolean exibirCargo = (exibir == 'C');
			boolean exibirArea = (exibir == 'A');
			
			empresaIds = empresaManager.selecionaEmpresa(empresa, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_ANIVERSARIANTES");
			colaboradors = colaboradorManager.findAniversariantes(empresaIds, mes, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck));
			
			parametros = RelatorioUtil.getParametrosRelatorio("Aniversariantes do mês: " + meses.get(mes), getEmpresaSistema(), null);
			parametros.put("EXIBIR_NOME_COMERCIAL", exibirNomeComercial);
			parametros.put("EXIBIR_CARGO", exibirCargo);
			parametros.put("EXIBIR_AREA", exibirArea);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioAniversariantes();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioAniversariantes();

			return Action.INPUT;
		}
		return SUCCESS;
	}

	public String prepareRelatorioAdmitidos()
	{
		prepareEmpresas("ROLE_REL_ADMITIDOS");
		return SUCCESS;
	}
	
	public String relatorioAdmitidos()
	{
		try
		{
			empresaIds = empresaManager.selecionaEmpresa(empresa, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_ADMITIDOS");
			colaboradors = colaboradorManager.findAdmitidos(empresaIds, dataIni, dataFim, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), exibirSomenteAtivos);
			
			reportFilter = "Período : " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
			reportTitle = "Relatório de Admitidos";
			
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);
			parametros.put("SOMENTE_ATIVOS", exibirSomenteAtivos);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioAdmitidos();

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioAdmitidos();

			return Action.INPUT;
		}
		
		return SUCCESS;
	}
	
	public Collection getColaboradors()
	{
		return colaboradors;
	}

	public Colaborador getColaborador()
	{
		if(colaborador == null)
			colaborador = new Colaborador();
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador=colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager=colaboradorManager;
	}

	public String getCpfBusca()
	{
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca)
	{
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores)
	{
		this.colaboradores = colaboradores;
	}

	public int getPageAnt()
	{
		return pageAnt;
	}

	public void setPageAnt(int pageAnt)
	{
		this.pageAnt = pageAnt;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Map getSituacaos()
	{
		return situacaos;
	}

	public String getMatriculaBusca()
	{
		return matriculaBusca;
	}

	public void setMatriculaBusca(String matriculaBusca)
	{
		this.matriculaBusca = StringUtil.retiraAcento(matriculaBusca);
	}

	public String getSituacao()
	{
		return situacao;
	}

	public void setSituacao(String situacao)
	{
		this.situacao = situacao;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Collection<AreaOrganizacional> getAreasList()
	{
		return areasList;
	}

	public void setAreasList(Collection<AreaOrganizacional> areasList)
	{
		this.areasList = areasList;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public Collection<Cargo> getCargosList()
	{
		return cargosList;
	}

	public void setCargosList(Collection<Cargo> cargosList)
	{
		this.cargosList = cargosList;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<Estabelecimento> getEstabelecimentosList()
	{
		return estabelecimentosList;
	}

	public void setEstabelecimentosList(Collection<Estabelecimento> estabelecimentosList)
	{
		this.estabelecimentosList = estabelecimentosList;
	}

	public String formPrint() throws Exception
	{
		return Action.SUCCESS;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public boolean isExibirNomeComercial()
	{
		return exibirNomeComercial;
	}

	public void setExibirNomeComercial(boolean exibirNomeComercial)
	{
		this.exibirNomeComercial = exibirNomeComercial;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public int getMes()
	{
		return mes;
	}

	public void setMes(int mes)
	{
		this.mes = mes;
	}

	public Map getMeses()
	{
		return meses;
	}

	public char getExibir() {
		return exibir;
	}

	public void setExibir(char exibir) {
		this.exibir = exibir;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean isExibirSomenteAtivos() {
		return exibirSomenteAtivos;
	}

	public void setExibirSomenteAtivos(boolean exibirSomenteAtivos) {
		this.exibirSomenteAtivos = exibirSomenteAtivos;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	public Map getStatusRetornoACs() 
	{
		return statusRetornoACs;
	}

	public void setStatusRetornoACs(Map statusRetornoACs) 
	{
		this.statusRetornoACs = statusRetornoACs;
	}

	public boolean isIntegraAc() {
		return integraAc;
	}

	public Collection<CheckBox> getAreaOrganizacionalsCheckList() {
		return areaOrganizacionalsCheckList;
	}

	public void setAreaOrganizacionalsCheckList(Collection<CheckBox> areaOrganizacionalsCheckList) {
		this.areaOrganizacionalsCheckList = areaOrganizacionalsCheckList;
	}

	public String[] getAreaOrganizacionalsCheck() {
		return areaOrganizacionalsCheck;
	}

	public void setAreaOrganizacionalsCheck(String[] areaOrganizacionalsCheck) {
		this.areaOrganizacionalsCheck = areaOrganizacionalsCheck;
	}

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
	}

	public void setHabilitaCampoExtra(boolean habilitaCampoExtra) {
		this.habilitaCampoExtra = habilitaCampoExtra;
	}

	public ParametrosDoSistemaManager getParametrosDoSistemaManager() {
		return parametrosDoSistemaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras() {
		return configuracaoCampoExtras;
	}

	public void setConfiguracaoCampoExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras) {
		this.configuracaoCampoExtras = configuracaoCampoExtras;
	}

	public ConfiguracaoCampoExtraManager getConfiguracaoCampoExtraManager() {
		return configuracaoCampoExtraManager;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public Collection<String> getColunasMarcadas() {
		return colunasMarcadas;
	}

	public void setColunasMarcadas(Collection<String> colunasMarcadas) {
		this.colunasMarcadas = colunasMarcadas;
	}

	public Collection<DynaRecord> getDataSource() {
		return dataSource;
	}

	public void setDataSource(Collection<DynaRecord> dataSource) {
		this.dataSource = dataSource;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}


	public Collection<ReportColumn> getColunas() {
		return colunas;
	}

	public void setColunas(Collection<ReportColumn> colunas) {
		this.colunas = colunas;
	}


	public void setConfiguracaoRelatorioDinamicoManager(ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager) {
		this.configuracaoRelatorioDinamicoManager = configuracaoRelatorioDinamicoManager;
	}

	public ConfiguracaoRelatorioDinamico getConfiguracaoRelatorioDinamico() {
		return configuracaoRelatorioDinamico;
	}

	public void setConfiguracaoRelatorioDinamico(ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico) {
		this.configuracaoRelatorioDinamico = configuracaoRelatorioDinamico;
	}

	public String getColunasJson() {
		return colunasJson;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public Map getSituacaosIntegraAC() {
		return situacaosIntegraAC;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public InputStream getReportInputStream() {
		return reportInputStream;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public String getNomeComercialBusca() {
		return nomeComercialBusca;
	}

	public void setNomeComercialBusca(String nomeComercialBusca) {
		this.nomeComercialBusca = nomeComercialBusca;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public String getDinamicColumns() {
		return StringUtil.converteCollectionToString(dinamicColumns);
	}

	public String getDinamicProperts() {
		return StringUtil.converteCollectionToString(dinamicProperts);
	}

	public String getJson() {
		return json;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Map getSexos() {
		return sexos;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public boolean isAgruparPorTempoServico() {
		return agruparPorTempoServico;
	}

	public void setAgruparPorTempoServico(boolean agruparPorTempoServico) {
		this.agruparPorTempoServico = agruparPorTempoServico;
	}

	public Integer getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(Integer tempoServico) {
		this.tempoServico = tempoServico;
	}

	public Integer[] getTempoServicoIni() {
		return tempoServicoIni;
	}

	public void setTempoServicoIni(Integer[] tempoServicoIni) {
		this.tempoServicoIni = tempoServicoIni;
	}

	public Integer[] getTempoServicoFim() {
		return tempoServicoFim;
	}

	public void setTempoServicoFim(Integer[] tempoServicoFim) {
		this.tempoServicoFim = tempoServicoFim;
	}
}