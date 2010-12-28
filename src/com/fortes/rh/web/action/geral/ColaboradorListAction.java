package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Mes;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
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
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("unchecked")
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
	
	private Collection<Colaborador> colaboradors = null;
	private Colaborador colaborador;
	private String nomeBusca;
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
	private String situacao;

	private Map statusRetornoACs = new StatusRetornoAC();
	private Integer statusRetornoAC;
	private boolean integraAc;
	
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
	
	private int mes;
	private Map meses = new Mes();
	
	//relatorio de admitidos
	private Date dataIni;
	private Date dataFim;
	private boolean exibirSomenteAtivos;

	private Long[] empresaIds;//repassado para o DWR

	private CamposExtras camposExtras = new CamposExtras();;

	private Collection<DynaRecord> dataSource;

	public String list() throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllList(getEmpresaSistema().getId(), AreaOrganizacional.TODAS);
		areasList = areaOrganizacionalManager.montaFamilia(areaOrganizacionalsTmp);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasList = cu1.sortCollectionStringIgnoreCase(areasList, "descricao");

		estabelecimentosList = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		cargosList = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nomeMercado");

		if(StringUtils.isNotBlank(cpfBusca))
			cpfBusca = StringUtil.removeMascara(cpfBusca);

		if(situacao == null)
			situacao = "A";

		Map parametros = new HashMap();
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("cpfBusca", cpfBusca);
		parametros.put("matriculaBusca", matriculaBusca);
		parametros.put("empresaId", getEmpresaSistema().getId());
		parametros.put("areaId", areaOrganizacional.getId());
		parametros.put("estabelecimentoId", estabelecimento.getId());
		parametros.put("cargoId", cargo.getId());
		parametros.put("situacao", situacao);
		parametros.put("statusRetornoAC", statusRetornoAC);

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
		colaboradorManager.remove(colaborador, getEmpresaSistema());
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
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), role);
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();
	}
	
	public String prepareRelatorioDinamico()
	{
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_CAD_COLABORADOR");
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);//usado pelo DWR
		
		empresa = getEmpresaSistema();
		
		habilitaCampoExtra = parametrosDoSistemaManager.findByIdProjection(1L).isCampoExtraColaborador();

		montaColunas();
		
		return Action.SUCCESS;
	}

	private void montaColunas() {
		colunas = ReportColumn.getColumns();
		if(habilitaCampoExtra)
		{
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativo"}, new Object[]{true}, new String[]{"ordem"});
			
			for (ConfiguracaoCampoExtra configuracaoCampoExtra : configuracaoCampoExtras)
			{
				String nomeExtra = "camposExtras." + configuracaoCampoExtra.getNome();
				if(!configuracaoCampoExtra.getTipo().equals("texto"))
					nomeExtra = "camposExtras." + configuracaoCampoExtra.getNome() + "String";
					
				colunas.add(new ReportColumn(configuracaoCampoExtra.getTitulo(), nomeExtra, configuracaoCampoExtra.getSize(), false));
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

			Collection<Colaborador> colaboradores = colaboradorManager.findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentos, areas, camposExtras, empresa.getId());

			Context cx = Context.enter();
	        try {
	            cx.setLanguageVersion(Context.VERSION_1_7);
	            Scriptable scope = cx.initStandardObjects();
	            
	            String xml = ArquivoUtil.getReportSource("relatorioDinamico.jrxml");
	            xml = xml.replaceAll("<\\?.*\\s+<!.*\\s+<!.*\\s+", "");
	            
	            StringBuilder sb = new StringBuilder();
	            sb.append("    var xml = " + xml + ";");
	           
	            int posicaoX = 0;
	            int valueWidth = 0;
	            int valueX = 0;

	            montaColunas();
	            parametros = RelatorioUtil.getParametrosRelatorio("Listagem de Colaboradores", getEmpresaSistema(), null);
	            
	            Collection<ReportColumn> colunasMarcadasRedimensionadas = ReportColumn.resizeColumns(colunas, colunasMarcadas);

	            int count = 1;
	            for (ReportColumn coluna : colunasMarcadasRedimensionadas)
	            {
            		parametros.put("TITULO" + (count), coluna.getName());
            		
	            	valueWidth = coluna.getSize();
		            valueX = posicaoX;
		            			        
		            sb.append(DynaRecord.montaEval("columnHeader", "width", count, valueWidth));
		            sb.append(DynaRecord.montaEval("columnHeader", "x", count, valueX));
		            sb.append(DynaRecord.montaEval("detail", "width", count, valueWidth));
		            sb.append(DynaRecord.montaEval("detail", "x", count, valueX));
		            
		            posicaoX += valueWidth + ReportColumn.getSpace();
		            count++;
				}
	            	            
				sb.append("    obj = xml.toXMLString();");
	            Object result = cx.evaluateString(scope, sb.toString(),	"MySource", 1,	null);

	            String relatoriDinamico = "" +
	            		"<?xml version=\"1.0\" encoding=\"UTF-8\"  ?>" +
	            		"<!DOCTYPE jasperReport PUBLIC \"//JasperReports//DTD Report Design//EN\" \"http://jasperreports.sourceforge.net/dtds/jasperreport.dtd\">" +
	            		result.toString();
	            
	            File arquivo = new File();
	            arquivo.setBytes(relatoriDinamico.getBytes());
	            arquivo.setName("deumnomeai.jrxml");
	            
	            String pasta = ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/") + java.io.File.separator;
	            pasta = pasta.replace("\\", "/").replace("%20", " ");
	            pasta = pasta.replace('/', java.io.File.separatorChar);
	            
	            dataSource = colaboradorManager.preparaRelatorioDinamico(colaboradores, colunasMarcadas);

	            FileUtil.bytesToFile(arquivo.getBytes(), pasta + arquivo.getName());
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
			addActionMessage("Não foi possível gerar o relatório");
			
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
			
			String filtro = "Período : " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
			
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Admitidos ", getEmpresaSistema(), filtro);
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

	public void setStatusRetornoAC(Integer statusRetornoAC) {
		this.statusRetornoAC = statusRetornoAC;
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

	
}