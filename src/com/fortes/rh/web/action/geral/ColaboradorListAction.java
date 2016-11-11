package com.fortes.rh.web.action.geral;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.AreaColaboradorException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaboradorIntegraAC;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorJsonVO;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ReportColumn;
import com.fortes.rh.model.json.ColaboradorJson;
import com.fortes.rh.model.json.Hash;
import com.fortes.rh.model.ws.TPeriodoGozo;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.EmpresaUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.ibm.icu.util.Calendar;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired
	private ColaboradorManager colaboradorManager = null;
	@Autowired
	private EstabelecimentoManager estabelecimentoManager;
	@Autowired
	private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired
	private CargoManager cargoManager;
	@Autowired
	private EmpresaManager empresaManager;
	@Autowired
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	@Autowired
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	@Autowired
	private ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager;
	@Autowired
	private UsuarioManager usuarioManager;
	
	private Collection<Colaborador> colaboradors = null;
	private Colaborador colaborador;
	private String codigoACBusca;
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
	private FaixaSalarial faixaSalarial = new FaixaSalarial();
	private Empresa empresa;
	
	private Map situacaos = new SituacaoColaborador();
	private Map situacaosIntegraAC = new SituacaoColaboradorIntegraAC();
	private String situacao;
	
	private Map<String,String> vinculos = new Vinculo();
	private String vinculo;
	
	private String[] dataCalculos;
	private String dataCalculo;
	private String anoDosRendimentos;

	private Collection<String> dinamicColumns;
	private Collection<String> dinamicProperts;

	private String dinColumns = "";
	private String dinProperties = "";
	private String dinPropertiesGroup = "";

	private Map statusRetornoACs = new StatusRetornoAC();
	private boolean integraAc;
	private boolean agruparPorTempoServico;
	private Character enviadoParaAC; // 1 - Todos ; 2 - Não Enviados ; 3 - Enviados
	
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] areaOrganizacionalsCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();
	private Collection<Empresa> empresas;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private Long[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private Long[] colaboradoresCheck;
	private Collection<CheckBox> colaboradoresCheckList = new ArrayList<CheckBox>();

	private Collection<String> colunasMarcadas = new ArrayList<String>();
	private Collection<ReportColumn> colunas = new ArrayList<ReportColumn>();

	private boolean exibirNomeComercial;
	private boolean habilitaCampoExtra;
	
	private char exibir = ' ';
	
	private Integer tempoServico;
	private Integer[] tempoServicoIni;
	private Integer[] tempoServicoFim;
	private int mes;
	private Map meses = DateUtil.getMesesComoOpcoesParaSelect();
	
	//relatorio de admitidos
	private Date dataIni;
	private Date dataFim;
	private boolean exibirSomenteAtivos;

	private Long[] empresaIds;//repassado para o DWR
	private Long[] empresasPermitidas;

	private CamposExtras camposExtras = new CamposExtras();
	private Map sexos = new Sexo();
	private String sexo;
	private String deficiencia;

	private ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico;
	private String colunasJson;
	private String orderField;
	
	private InputStream reportInputStream;

	private String reportFilter;
	private String reportTitle;
	private Boolean compartilharColaboradores;
	
	private Collection<AutoCompleteVO> data;
	private String descricao;

	private ByteArrayInputStream byteArrayInputStream;
	private String mesAno;
	private Date mesAnoDate;
	private String dataInicioGozo;
	private String dataFimGozo;
	private Long colaboradorLogadoId;

	private boolean imprimirFeriasGozadas;
	private Collection<TPeriodoGozo> periodosGozo;

	private String json;
	private String baseCnpj;
	private Long colaboradorId;
	private String colaboradorMatricula;
	
	private boolean agruparPorArea;

	private enum Nomenclatura {
		ENVIADO_FP("Enviado Fortes Pessoal"),
		CODIGO_FP ("Cód. Fortes Pessoal");
		
		private String descricao;
		
		Nomenclatura(String descricao){
			this.descricao = descricao; 
		}

		public String getDescricao()
		{
			return descricao;
		}
	}
	
	public String find() throws Exception
	{
		data = colaboradorManager.getAutoComplete(descricao, getEmpresaSistema().getId());
		json = StringUtil.toJSON(data, null);
		
		return Action.SUCCESS;
	}
	
	public String colaboradoresPorArea()
	{
		try {
			Collection<ColaboradorJsonVO> colaboradores = colaboradorManager.getColaboradoresJsonVO(new Long[]{ 59L });
			json = StringUtil.toJSON(colaboradores, new String[]{"id"});
		} catch (Exception e) {
			json = "error";
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String list() throws Exception{
		colaboradorLogadoId = colaboradorManager.findByUsuario(getUsuarioLogado().getId()); 
				
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, getEmpresaSistema().getId());
		areasList = areaOrganizacionalManager.montaFamilia(areaOrganizacionalsTmp);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasList = cu1.sortCollectionStringIgnoreCase(areasList, "descricao");
		estabelecimentosList = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		cargosList = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, getEmpresaSistema().getId());
		if(StringUtils.isNotBlank(cpfBusca)) 
			cpfBusca = StringUtil.removeMascara(cpfBusca);
		if(situacao == null) 
			situacao = "A";
		
		Long[] areasIdsPorResponsavel = null;
		if(getUsuarioLogado().getId() != 1L && !SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_COLAB_VER_TODOS"})){
			areasIdsPorResponsavel = areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(getUsuarioLogado(), getEmpresaSistema().getId());
			if(areasIdsPorResponsavel.length == 0)
				areasIdsPorResponsavel = new Long[]{-1L};//não vai achar nenhum colaborador
		}
		
		Map<String, Object> parametros = montaParametros(areasIdsPorResponsavel);
		setTotalSize(colaboradorManager.getCountComHistoricoFuturoSQL(parametros, getUsuarioLogado().getId()));
		
		colaboradors = colaboradorManager.findComHistoricoFuturoSQL(getPage(), getPagingSize(), parametros, getUsuarioLogado().getId());
		if (colaboradors == null || colaboradors.size() == 0)
			addActionMessage("Não existem colaboradores a serem listados.");
		integraAc = getEmpresaSistema().isAcIntegra();
		
		return Action.SUCCESS;
	}

	private Map<String, Object> montaParametros(Long[] areasIdsPorResponsavel) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigoACBusca", codigoACBusca);
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("nomeComercialBusca", nomeComercialBusca);
		parametros.put("cpfBusca", cpfBusca);
		parametros.put("matriculaBusca", matriculaBusca);
		parametros.put("empresaId", getEmpresaSistema().getId());
		parametros.put("areaId", areaOrganizacional.getId());
		parametros.put("areasIdsPorResponsavel", areasIdsPorResponsavel);
		parametros.put("estabelecimentoId", estabelecimento.getId());
		parametros.put("cargoId", cargo.getId());
		parametros.put("faixaSalarialId", faixaSalarial.getId());
		parametros.put("situacao", situacao);
		return parametros;
	}
	
	private void geraComprovante(Comprovante comprovante) throws Exception {

		try {
			colaborador = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
			colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
			
			if(colaborador.getCodigoAC() == null || "".equals(colaborador.getCodigoAC()))
				throw new IntegraACException("Este colaborador não está integrado com o Fortes Pessoal ou não possui código Fortes Pessoal.");
			
			String recibo = comprovante.gera();

			byte[] reciboBytes = Base64.decodeBase64(recibo.getBytes()); 

			byteArrayInputStream = new ByteArrayInputStream(reciboBytes);

			ArquivoUtil.geraPdfByBytes(ServletActionContext.getResponse(), reciboBytes, comprovante.nomeDoArquivo());

		} catch (Exception e) {
			if(e instanceof IntegraACException)
				addActionWarning(e.getMessage());
			else
				addActionError(e.getMessage());
			
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public String prepareReciboDeFerias() 
	{
		try {
			preparaRecibo("seu recibo de férias");
			if ( getActionWarnings().size() == 0 )
				dataCalculos = colaboradorManager.getDatasPeriodoDeGozoPorEmpregado(colaborador);
		} catch (Exception e) {
			addActionError("Houve um erro inesperado: "+e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String reciboDeFerias()
	{
		try {
			geraComprovante(new ReciboDeFerias());
		} 
		catch (Exception e) 
		{
			prepareReciboDeFerias();
			return Action.INPUT;
		}
        
		return Action.SUCCESS;
	}
	
	public String prepareReciboPagamento()
	{
		try {
			preparaRecibo("seu recibo de pagamento");
		} catch (Exception e) {
			addActionError("Houve um erro inesperado: "+e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String reciboPagamento() 
	{
		try {
			geraComprovante(new ReciboPagamento());
		} 
		catch (Exception e) 
		{
			prepareReciboPagamento();
			return Action.INPUT;
		}
        
		return Action.SUCCESS;
	}
	
	public String prepareReciboDeDecimoTerceiro() 
	{
		try {
			preparaRecibo("seu recibo de décimo terceiro");
			if (getActionWarnings().size() == 0)
				dataCalculos = colaboradorManager.getDatasDecimoTerceiroPorEmpregado(colaborador);
		} catch (Exception e) {
			addActionError("Houve um erro inesperado: "+e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String reciboDeDecimoTerceiro() throws Exception
	{
		try {
			geraComprovante(new ReciboDecimoTerceiro());
		} 
		catch (Exception e) 
		{
			addActionError("Houve um erro inesperado: "+e.getMessage());
			prepareReciboDeDecimoTerceiro();
			return Action.INPUT;
		}
        
		return Action.SUCCESS;
	}
	
	public String prepareDeclaracaoRendimentos() throws Exception
	{
		try {
			preparaRecibo("sua declaração de rendimentos");
		} catch (Exception e) {
			addActionError("Houve um erro inesperado: "+e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String declaracaoRendimentos() throws Exception
	{
		try {
			geraComprovante(new DeclaracaoDeRendimento());
		} 
		catch (Exception e) 
		{
			prepareDeclaracaoRendimentos();
			addActionError("Houve um erro inesperado: "+e.getMessage());
			return Action.INPUT;
		}
        
		return Action.SUCCESS;
	}
	
	public String prepareReciboPagamentoComplementar()
	{
	
		try {
			preparaRecibo("seu recibo de complemento da folha com encargos");
		} catch (Exception e) {
			addActionError("Houve um erro inesperado: "+e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String reciboPagamentoComplementar()
	{
		try {
			geraComprovante(new ReciboPagamentoComplementar());
		} 
		catch (Exception e) 
		{
			prepareReciboPagamentoComplementar();
			return Action.INPUT;
		}
        
		return Action.SUCCESS;
	}
	
	public String prepareReciboPagamentoAdiantamentoDeFolha() 
	{
		try {
			preparaRecibo("seu recibo de adiantamento de folha");
		} catch (Exception e) {
			addActionError("Houve um erro inesperado: "+e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String reciboPagamentoAdiantamentoDeFolha() throws Exception
	{
		try {
			geraComprovante(new ReciboPagamentoAdiantamentoDeFolha());
		} 
		catch (Exception e) 
		{
			prepareReciboPagamentoAdiantamentoDeFolha();
			return Action.INPUT;
		}
        
		return Action.SUCCESS;
	}

	private void preparaRecibo(String descricaoRecibo) throws Exception {
		colaborador = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		
		if(!getEmpresaSistema().isAcIntegra()) {
			addActionWarning("Esta empresa não está integrada com o Fortes Pessoal.");
		} else if(colaborador == null) {
			addActionWarning("Sua conta de usuário não está vinculada à um colaborador.");
		} else if(!colaborador.getEmpresa().getId().equals(getEmpresaSistema().getId())) {
			addActionWarning("Só é possível solicitar " + descricaoRecibo + " pela empresa a qual você foi contratado(a). Acesse a empresa <strong>" + colaborador.getEmpresaNome() + "</strong> para solicitar seu recibo.");
			colaborador = null;
		} else if(colaborador.getCodigoAC() == null || "".equals(colaborador.getCodigoAC()))
			addActionWarning("Este colaborador não está integrado com o Fortes Pessoal ou não possui código Fortes Pessoal.");
	}
	
	public String delete() throws Exception
	{
		try 
		{
			if (getUsuarioLogado().getId().equals(1L))
				colaboradorManager.removeComDependencias(colaborador.getId());
			else
				colaboradorManager.remove(colaborador, getEmpresaSistema());
		} 
		catch (InvocationTargetException e) {
			addActionError(e.getTargetException().getMessage());
			return "error";
		}
		addActionSuccess("Colaborador excluído com sucesso.");
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
		boolean verTodasAreas = SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"});
		boolean podeGerarRelatorioColaborador = verTodasAreas || (areaOrganizacionalManager.findAllListAndInativasByUsuarioId(getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null).size() > 0); 
		
		if(!podeGerarRelatorioColaborador){
			setActionMsg("Usuário sem permissão de gerar este relatório, pois o mesmo não é gestor de área organizacional e não possui em seu perfil a configuração para \"Visualizar todas as Áreas Organizacionais\".");
			return "semPermissaoDeVerAreaOrganizacional";			
		}

		Long usuarioId = SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession());
		prepareEmpresas("ROLE_REL_LISTA_COLAB");
		
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraColaborador();
		
		configuracaoRelatorioDinamico = configuracaoRelatorioDinamicoManager.findByUsuario(usuarioId);
		if(configuracaoRelatorioDinamico == null)
			configuracaoRelatorioDinamico = new ConfiguracaoRelatorioDinamico();
		
		montaColunas();
		colunasJson = StringUtil.toJSON(colunas, null);

		return Action.SUCCESS;
	}

	private void montaColunas() {
		boolean existeEmpresaIntegradaAc = empresaManager.checkEmpresaIntegradaAc();
		
		colunas = ReportColumn.getColumns(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"EXIBIR_SALARIO_CONTRATUAL_REL_COLAB"}));

		if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_COMPROU_SESMT"}) ){
			colunas.add(new ReportColumn("Função", "funcaoNome", "fun.nome", 100, false));
			colunas.add(new ReportColumn("Ambiente", "ambienteNome", "amb.nome", 100, false));
		}
		
		if (existeEmpresaIntegradaAc){
			colunas.add(new ReportColumn(Nomenclatura.ENVIADO_FP.getDescricao(), "enviadoParaAC", "naoIntegraAc", 25, false));	
			colunas.add(new ReportColumn(Nomenclatura.CODIGO_FP.getDescricao(), "codigoACRelatorio", "co.codigoAC", 10, false));
		}
		
		if (habilitaCampoExtra){
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
			
			for (ConfiguracaoCampoExtra configuracaoCampoExtra : configuracaoCampoExtras)
			{
				String orderField = "ce." + configuracaoCampoExtra.getNome();
				String nomeExtra = "camposExtras." + configuracaoCampoExtra.getNome();
				if(!configuracaoCampoExtra.getTipo().equals("texto") && !configuracaoCampoExtra.getTipo().equals("textolongo"))
					nomeExtra = "camposExtras." + configuracaoCampoExtra.getNome() + "String";
					
				colunas.add(new ReportColumn(configuracaoCampoExtra.getTitulo(), nomeExtra, orderField, configuracaoCampoExtra.getSize(), false));
			}
		}
	}

	public String relatorioDinamico() throws Exception{
		try	{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			areaOrganizacionalsCheck = areaOrganizacionalManager.filtraPermitidas(areaOrganizacionalsCheck, (empresa.getId() == null ? getEmpresaSistema().getId() : empresa.getId()));
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
			Collection<Long> cargos = LongUtil.arrayLongToCollectionLong(cargosCheck);

			camposExtras.setId(1l);
			
			String nomeRelatorio = "modeloDinamico.jrxml";
			
			if(agruparPorTempoServico){
				nomeRelatorio = "modeloDinamicoAgrupadoTempoServico.jrxml";
				colaboradores = getcolaboradoresByFiltros(estabelecimentos, areas, cargos, " co.dataAdmissao desc, " + orderField);
				colaboradores = colaboradorManager.montaTempoServico(colaboradores, tempoServicoIni, tempoServicoFim, ReportColumn.getpropertyByOrderField(colunas, orderField));
			}else{
				colaboradores = getcolaboradoresByFiltros(estabelecimentos, areas, cargos, orderField);
			}

			if(colaboradores.isEmpty())
				throw new Exception("SEM_DADOS");
			
			JRDataSource dataSource = setupRelatorio();
			
			montaColunas();
            
            Collection<ReportColumn> colunasMarcadasRedimensionadas = ReportColumn.resizeColumns(colunas, colunasMarcadas);
            
            DynamicReport dynamicReport = montaRelatorioDinamico(nomeRelatorio, dataSource, colunasMarcadasRedimensionadas);
			
            geraRelatorioDinamico(dataSource, dynamicReport);
            
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			if(e.getMessage().equals("SEM_DADOS"))
				addActionMessage("Não existem dados para o filtro informado.");
			else
				addActionError("Não foi possível gerar o relatório.");
				
			prepareRelatorioDinamico();
 			return Action.INPUT;
		}
	}

	private JRDataSource setupRelatorio()
	{
		JRDataSource dataSource = new JRBeanCollectionDataSource(colaboradores);

		String filtro = "Estabelecimentos: " + estabelecimentoManager.nomeEstabelecimentos(LongUtil.arrayStringToArrayLong(estabelecimentosCheck),  empresa.getId());
		filtro += "\nÁreas Organizacionais: " + areaOrganizacionalManager.nomeAreas(LongUtil.arrayStringToArrayLong(areaOrganizacionalsCheck));
		
		parametros = RelatorioUtil.getParametrosRelatorio("Listagem de Colaboradores", getEmpresaSistema(), filtro);
		parametros.put("TOTALREGISTROS", colaboradores.size());
		
		return dataSource;
	}

	private DynamicReport montaRelatorioDinamico(String nomeRelatorio, JRDataSource dataSource, Collection<ReportColumn> colunasMarcadasRedimensionadas) throws JRException, IOException
	{
		Font SansSerif = new Font(8, "SansSerif", true);
		
		Style headerStyle = new Style();
		headerStyle.setBlankWhenNull(true);
		headerStyle.setFont(SansSerif);
		headerStyle.setBorderBottom(new Border(0.5f, Border.BORDER_STYLE_SOLID));
		
		Style detailStyle = new Style();
		detailStyle.setBlankWhenNull(true);
		detailStyle.setFont(SansSerif);
		detailStyle.setVerticalAlign(VerticalAlign.TOP);
		detailStyle.setOverridesExistingStyle(true);
		
		Style columnStyle = new Style();
		columnStyle.setFont(SansSerif);
		columnStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		columnStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		if(agruparPorTempoServico){
			headerStyle.setPaddingLeft(10);
			columnStyle.setPaddingLeft(10);
		}
		
		Style oddDetailStyle = new Style();
		oddDetailStyle.setBackgroundColor(new Color(238, 238, 238));
		oddDetailStyle.setFont(SansSerif);
		
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTemplateFile("../../WEB-INF/report/" + nomeRelatorio, true, true, true, true);
		drb.setDetailHeight(15);
		drb.setHeaderHeight(10);
		drb.setMargins(15, 20, 30, 15);
		drb.setDefaultStyles(null, null, headerStyle, detailStyle);
		drb.setColumnsPerPage(1);
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		drb.setUseFullPageWidth(true);
		drb.setColumnSpace(4);
		drb.setOddRowBackgroundStyle(oddDetailStyle);
		drb.setPrintBackgroundOnOddRows(true);
		
		boolean integradaAc = true;
		if (empresa != null && empresa.getId() != null)
			integradaAc = empresaManager.checkEmpresaIntegradaAc(empresa.getId());
		
		AbstractColumn aCol;
		for (ReportColumn coluna : colunasMarcadasRedimensionadas)
		{
			if(!integradaAc && (coluna.getName().equals(Nomenclatura.ENVIADO_FP.getDescricao()) || coluna.getName().equals(Nomenclatura.CODIGO_FP.getDescricao())))
				continue;
			
		    aCol = ColumnBuilder.getNew()
		    					.setColumnProperty(coluna.getProperty(), String.class.getName())
		    					.setTitle(coluna.getName())
		    					.setWidth(coluna.getSize() + 30)
		    					.setStyle(columnStyle)
		    					.build();
		    
		    drb.addColumn(aCol);
		}

		if(agruparPorTempoServico)
		{
		    Style styleGroup = new Style();
		    styleGroup.setHorizontalAlign(HorizontalAlign.LEFT);
		    styleGroup.setVerticalAlign(VerticalAlign.MIDDLE);
		    styleGroup.setFont(SansSerif);

		    AbstractColumn columnTempoServico = ColumnBuilder.getNew()
		            .setColumnProperty("tempoServicoString", String.class.getName())
		            .setTitle("Tempo de Serviço")
		            .setWidth(new Integer(100))
		            .setStyle(styleGroup)
		            .build();

		    drb.addColumn(columnTempoServico);
		    
		    GroupBuilder gb  = new GroupBuilder();
		    DJGroup g = gb.setCriteriaColumn((PropertyColumn) columnTempoServico)
		    		.setGroupLayout(GroupLayout.VALUE_IN_HEADER)
		    		.setAllowFooterSplit(true)
		    		.setStartInNewPage(true)
		    		.setDefaultColumnHeaderStyle(styleGroup)
		    		.setDefaultFooterVariableStyle(styleGroup)
		    		.setDefaultHeaderVariableStyle(styleGroup)
		    		.build();
		    
		    g.setName("Tempo de Serviço");
		    g.setDefaulFooterVariableStyle(styleGroup);
		    g.setDefaulHeaderVariableStyle(styleGroup);
		    g.setDefaultColumnHeaederStyle(styleGroup);
		    
		    drb.addGroup(g);
		}
		
		return drb.build();		
	}

	private void geraRelatorioDinamico(JRDataSource dataSource, DynamicReport report) throws JRException, IOException
	{
		JasperReport jreport = DynamicJasperHelper.generateJasperReport(report, new ClassicLayoutManager(), parametros);
		JasperPrint jprint = JasperFillManager.fillReport(jreport, parametros, dataSource);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment ; filename=relatorioDinamico.pdf");
		
		byte[] output = JasperExportManager.exportReportToPdf(jprint);
		response.setContentLength(output.length);
		
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(output);
		ouputStream.flush();
		ouputStream.close();
	}

	private Collection<Colaborador> getcolaboradoresByFiltros(Collection<Long> estabelecimentos, Collection<Long> areas,Collection<Long> cargos, String order) 
	{
		return colaboradorManager.findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentos, areas, cargos, camposExtras, order, 
				dataIni, dataFim, sexo, deficiencia, tempoServicoIni, tempoServicoFim, situacao, enviadoParaAC, EmpresaUtil.empresasSelecionadas(empresa.getId(),empresasPermitidas));
	}
	
	public String relatorioDinamicoXLS() throws Exception
	{
		try
		{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			areaOrganizacionalsCheck = areaOrganizacionalManager.filtraPermitidas(areaOrganizacionalsCheck, (empresa.getId() == null ? getEmpresaSistema().getId() : empresa.getId()));
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
			Collection<Long> cargos = LongUtil.arrayLongToCollectionLong(cargosCheck);
			camposExtras.setId(1l);
			
			montaColunas();
			dinamicColumns = new ArrayList<String>();
			dinamicProperts = new ArrayList<String>();
			
			if(agruparPorTempoServico)
			{
				colaboradores = getcolaboradoresByFiltros(estabelecimentos, areas, cargos, " co.dataAdmissao desc, " + orderField);
				colaboradores = colaboradorManager.montaTempoServico(colaboradores, tempoServicoIni, tempoServicoFim, ReportColumn.getpropertyByOrderField(colunas, orderField));
				dinamicColumns.add("Tempo de serviço");  
				dinamicProperts.add("tempoServicoString");
			}
			else 
			{
				colaboradores = getcolaboradoresByFiltros(estabelecimentos, areas, cargos, orderField);
			}
			
			if(colaboradores.isEmpty())
				throw new Exception("SEM_DADOS");
			
			reportFilter = "Emitido em: " + DateUtil.formataDiaMesAno(new Date());
			reportTitle = configuracaoRelatorioDinamico.getTitulo();
			
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
			prepareRelatorioDinamico();
			
			if(e.getMessage() != null && e.getMessage().equals("SEM_DADOS"))
				setActionMsg("Não existem dados para o filtro informado.");
			else
				setActionMsg("Não foi possível gerar o relatório.");
			
			return Action.INPUT;
		}
	}
	
	public String relatorioAniversariantesXls()
	{
			String retorno = relatorioAniversariantes();
			
			if(retorno.equals(SUCCESS))
			{
				if (mes > 0) {
					if(exibir == 'A')
						return exibirNomeComercial?"sucessoAreaNomeComercial":"sucessoArea";
					else
						return exibirNomeComercial?"sucessoCargoNomeComercial":"sucessoCargo";
				} else {
					if(exibir == 'A')
						return exibirNomeComercial?"sucessoAreaNomeComercialTodosMeses":"sucessoAreaTodosMeses";
					else
						return exibirNomeComercial?"sucessoCargoNomeComercialTodosMeses":"sucessoCargoTodosMeses";
				}
			}
			
			return Action.INPUT;
	}
	
	
	public String relatorioAniversariantes()
	{
		try
		{
			boolean exibirCargo = (exibir == 'C');
			boolean exibirArea = (exibir == 'A');
			
			empresaIds = empresaManager.selecionaEmpresa(empresa, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_ANIVERSARIANTES");
			colaboradors = colaboradorManager.findAniversariantes(empresaIds, mes, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck));
			
			if (mes > 0) {
				reportTitle="Aniversariantes do mês: " + meses.get(mes);
			} else {
				reportTitle="Aniversariantes";
			}
			
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), null);
			parametros.put("EXIBIR_NOME_COMERCIAL", exibirNomeComercial);
			parametros.put("EXIBIR_CARGO", exibirCargo);
			parametros.put("EXIBIR_AREA", exibirArea);
			parametros.put("TODOS_MESES", mes == 0 ? true : false);
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
			colaboradors = colaboradorManager.findAdmitidos(empresaIds, vinculo, dataIni, dataFim, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), exibirSomenteAtivos);
			
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
			addActionError("Não foi possível gerar o relatório");
			e.printStackTrace();
			prepareRelatorioAdmitidos();

			return Action.INPUT;
		}
		
		return SUCCESS;
	}
	
	public String prepareRelatorioFormacaoEscolar() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_AREAORGANIZACIONAL");

		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);//usado pelo DWR
		
		empresa = getEmpresaSistema();
		
		return SUCCESS;
	}
	
	public String imprimeRelatorioFormacaoEscolar() throws Exception{
		try{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areasCheck);
			Collection<Long> cargos = LongUtil.arrayLongToCollectionLong(cargosCheck);
				
			colaboradores = colaboradorManager.findFormacaoEscolar(empresa.getId(), estabelecimentos, areas, cargos);
			colaboradores = colaboradorManager.ordenaPorEstabelecimentoArea(colaboradores, empresa.getId());
			empresa = empresaManager.findById(empresa.getId());

			parametros = areaOrganizacionalManager.getParametrosRelatorio("Relatório de Formação Escolar", empresa, null);

			return Action.SUCCESS;
		}catch (Exception e)	{
			setActionErr("Não foi possível gerar o relatório");
			e.printStackTrace();
			prepareRelatorioFormacaoEscolar();

			return Action.INPUT;
		}
	}
	
	public String prepareRelatorioFerias()
	{
		try {
			areasList = colaboradorManager.defineAreasPermitidasParaUsuario(getEmpresaSistema().getId(), getUsuarioLogado().getId(), SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}));
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AreaColaboradorException)
				addActionWarning(e.getMessage());
			else
				addActionError("Não foi possível visualizar esta tela.");
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String imprimeRelatorioFerias()
	{
		try
		{
			String[] colaboradoresCodigosACs = colaboradorManager.findCodigosACByIds(colaboradoresCheck);
			periodosGozo = colaboradorManager.getFerias(getEmpresaSistema(), colaboradoresCodigosACs, dataInicioGozo, dataFimGozo);
			
			prepareCabecalhoRelatorioFerias();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof ColecaoVaziaException)
				addActionMessage(e.getMessage());
			else
				addActionError("Não foi possível gerar o relatório.");
			
			prepareRelatorioFerias();
			return Action.INPUT;
		}
		
		String retorno = SUCCESS; 
		if(imprimirFeriasGozadas)
			retorno += "ComFeriasGozadas";
		
		return retorno;
	}

	public String getColaboradoresJson(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest req = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String token = req.getHeader(Hash.getNomeParametro());
		if(token != null && token.equals(Hash.getValorHash())){
			Collection<ColaboradorJson> colaboradoresJson = colaboradorManager.getColaboradoresJson(baseCnpj, colaboradorId, colaboradorMatricula);
			json = StringUtil.toJSON(colaboradoresJson, new String[]{});
		}
		else{
			json = "token inválido";
		}
		return Action.SUCCESS;
	}

	public String prepareRelatorioAniversariantesPorTempoDeEmpresa()
	{
		prepareEmpresas("ROLE_REL_ANIVERSARIANTES_POR_TEMPO_DE_EMPRESA");
		Calendar hoje = Calendar.getInstance();
		mes = hoje.get(Calendar.MONTH) + 1;
		
		return SUCCESS;
	}

	public String relatorioAniversariantesPorTempoDeEmpresa()
	{
		try{
			populaRelatorioAniversariantesPorTempoDeEmpresa();
			
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), null);
			parametros.put("EXIBIR_NOME_COMERCIAL", exibirNomeComercial);
			parametros.put("TODOS_OS_MESES", mes == 0 ? true : false);
		}
		catch (ColecaoVaziaException e){
			addActionMessage(e.getMessage());
			prepareRelatorioAniversariantesPorTempoDeEmpresa();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
			return Action.INPUT;
		}
		catch (Exception e){
			addActionError("Erro ao gerar relatório. </br>" + e.getMessage() );
			e.printStackTrace();
			return Action.INPUT;
		}
		if(agruparPorArea)
			return "successAgrupadoPorArea";
		
		return SUCCESS;
	}
	
	public String relatorioAniversariantesPorTempoDeEmpresaXLS(){
		try{
			populaRelatorioAniversariantesPorTempoDeEmpresa();
			montaColunasRelatorioAniversarianteTempoServicoXLS();
			return Action.SUCCESS;
		}
		catch (ColecaoVaziaException e){
			addActionMessage(e.getMessage());
			prepareRelatorioAniversariantesPorTempoDeEmpresa();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
			return Action.INPUT;
		}
		catch (Exception e){
			addActionError("Erro ao gerar relatório. </br>" + e.getMessage() );
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	private void montaColunasRelatorioAniversarianteTempoServicoXLS() {
		dinColumns = "Empresa - Área Organizacional,";
		dinProperties = "areaOrganizacional.nomeComEmpresa,";
		
		if(agruparPorArea)
			dinPropertiesGroup = "areaOrganizacional.id,";
		
		if(mes == 0){
			dinColumns += "Mês,";
			dinProperties += "mesAdmissaoPorExtenso,";	
			dinPropertiesGroup += "mesAdmissaoPorExtenso";
		}else if(dinPropertiesGroup.length() > 0)
			dinPropertiesGroup = dinPropertiesGroup.substring(0, dinPropertiesGroup.length() - 1);
		
		dinColumns += "Data,Tempo de Empresa,Matrícula,";
		dinProperties += "diaMesDataAdmissao,tempoDeServicoComAno,matricula,";
		
		if(exibirNomeComercial){
			dinColumns += "Nome Comercial,";
			dinProperties += "nomeComercial,";
		}else{
			dinColumns += "Nome,";
			dinProperties += "nome,";
		}
		
		dinColumns += "Cargo,Estabelecimento";
		dinProperties += "faixaSalarial.descricao,estabelecimento.nome";
	}

	private void populaRelatorioAniversariantesPorTempoDeEmpresa()
			throws Exception {
		colaboradors = colaboradorManager.findAniversariantesPorTempoDeEmpresa(mes, agruparPorArea, EmpresaUtil.empresasSelecionadas(empresa.getId(),empresasPermitidas), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck));
		if (mes > 0) 
			reportTitle="Aniversariantes por tempo de empresa do mês: " + meses.get(mes);
		else 
			reportTitle="Aniversariantes por tempo de empresa";
	}

	private void prepareCabecalhoRelatorioFerias()
	{
		reportTitle = "Relatório de Férias";
	
		boolean existeDataInicioGozo = !(dataInicioGozo == null || "".equals(dataInicioGozo) || "  /  /    ".equals(dataInicioGozo));
		boolean existeDataFimGozo = !(dataFimGozo == null || "".equals(dataFimGozo) || "  /  /    ".equals(dataFimGozo));
		
		if(existeDataInicioGozo && existeDataFimGozo)
			reportFilter = "Período informado: " + dataInicioGozo + "   a   " +  dataFimGozo;
		else{
			if(!existeDataInicioGozo)
				dataInicioGozo = "     -     ";
			
			if(!existeDataFimGozo)
				dataFimGozo = DateUtil.formataDiaMesAno(new Date());
			
			if(existeDataInicioGozo || existeDataFimGozo)
				reportFilter = "Período informado: " + dataInicioGozo + "   a   " +  dataFimGozo;
		}
		
		parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);
		parametros.put("POSSUIPERIODO", existeDataInicioGozo || existeDataFimGozo);
	}
	
	// Início INNER CLASS
	interface Comprovante {
		public String gera() throws Exception;
		public String nomeDoArquivo();
	}
	
	class ReciboDeFerias implements Comprovante {
		public String gera() throws Exception {
			return colaboradorManager.getAvisoReciboDeFerias(colaborador, dataInicioGozo, dataFimGozo);
		}
		
		public String nomeDoArquivo() {
			return "aviso_ferias_" + dataInicioGozo+"_"+dataFimGozo.replace("/", "");
		}
	}
	
	class ReciboPagamento implements Comprovante {
		public String gera() throws Exception {
			return colaboradorManager.getReciboPagamento(colaborador, mesAnoDate);
		}
		
		public String nomeDoArquivo() {
			return "recibo_pagamento" + mesAno.replace("/", "");
		}
	}
	
	class ReciboDecimoTerceiro implements Comprovante {
		public String gera() throws Exception {
			return colaboradorManager.getReciboDeDecimoTerceiro(colaborador, dataCalculo);
		}
		
		public String nomeDoArquivo() {
			return "recibo_decimo_terceiro_" + dataCalculo.replace("/", "_");
		}
	}
	
	class DeclaracaoDeRendimento implements Comprovante {
		public String gera() throws Exception {
			return colaboradorManager.getDeclaracaoRendimentos(colaborador, anoDosRendimentos);
		}
		
		public String nomeDoArquivo() {
			return "declaracao_rendimentos_" + anoDosRendimentos;
		}
	}
	
	class ReciboPagamentoComplementar implements Comprovante {
		public String gera() throws Exception {
			return colaboradorManager.getReciboDePagamentoComplementar(colaborador, mesAnoDate);
		}
		
		public String nomeDoArquivo() {
			return "recibo_complemento_folha_encargos_"+mesAno;
		}
	}
	
	class ReciboPagamentoAdiantamentoDeFolha implements Comprovante {
		public String gera() throws Exception {
			return colaboradorManager.getReciboPagamentoAdiantamentoDeFolha(colaborador, mesAnoDate);
		}
		
		public String nomeDoArquivo() {
			return "recibo_adiantamento_folha_"+mesAno;
		}
	}
	// Fim INNER CLASS
	
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

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras() {
		return configuracaoCampoExtras;
	}

	public void setConfiguracaoCampoExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras) {
		this.configuracaoCampoExtras = configuracaoCampoExtras;
	}

	public ConfiguracaoCampoExtraManager getConfiguracaoCampoExtraManager() {
		return configuracaoCampoExtraManager;
	}

	public Collection<String> getColunasMarcadas() {
		return colunasMarcadas;
	}

	public void setColunasMarcadas(Collection<String> colunasMarcadas) {
		this.colunasMarcadas = colunasMarcadas;
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

	public String getDeficiencia() {
		return deficiencia;
	}

	public void setDeficiencia(String deficiencia) {
		this.deficiencia = deficiencia;
	}
	
	public void setEnviadoParaAC(Character enviadoParaAC)
	{
		this.enviadoParaAC = enviadoParaAC;
	}
	
	public Character getEnviadoParaAC()
	{
		return enviadoParaAC;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
		this.mesAnoDate = DateUtil.criarDataMesAno(mesAno);
	}
	
	public ByteArrayInputStream getByteArrayInputStream() {
		return byteArrayInputStream;
	}

	public void setByteArrayInputStream(ByteArrayInputStream byteArrayInputStream) {
		this.byteArrayInputStream = byteArrayInputStream;
	}

	public FaixaSalarial getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}
	
	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}
	
	public void setCargosCheck(Long[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Map<String, String> getVinculos() {
		return vinculos;
	}

	public void setVinculos(Map<String, String> vinculos) {
		this.vinculos = vinculos;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public String getCodigoACBusca()
	{
		return codigoACBusca;
	}

	public void setCodigoACBusca(String codigoACBusca)
	{
		this.codigoACBusca = codigoACBusca;
	}
	
	public void setDataCalculo(String dataCalculo) {
		this.dataCalculo = dataCalculo.replaceAll("([0-9]{2}/[0-9]{2}/[0-9]{4}).*", "$1");
	}

	public String getDataCalculo() {
		return dataCalculo;
	}
	
	public String getAnoDosRendimentos() {
		return anoDosRendimentos;
	}

	public void setAnoDosRendimentos(String anoDosRendimentos) {
		this.anoDosRendimentos = anoDosRendimentos.replace(" ", "");
	}

	public String[] getDataCalculos() {
		return dataCalculos;
	}

	public void setEmpresasPermitidas(Long[] empresasPermitidas) {
		this.empresasPermitidas = empresasPermitidas;
	}

	public void setDataInicioGozo(String dataInicioGozo) {
		this.dataInicioGozo = dataInicioGozo;
	}

	public void setDataFimGozo(String dataFimGozo) {
		this.dataFimGozo = dataFimGozo;
	}

	public Long getColaboradorLogadoId(){
		if (colaboradorLogadoId == null)
			colaboradorLogadoId = 0L;
		
		return colaboradorLogadoId;
	}

	public Date getMesAnoDate() {
		return mesAnoDate;
	}

	public Collection<CheckBox> getColaboradoresCheckList()
	{
		return colaboradoresCheckList;
	}

	public void setColaboradoresCheckList(Collection<CheckBox> colaboradoresCheckList)
	{
		this.colaboradoresCheckList = colaboradoresCheckList;
	}

	public void setColaboradoresCheck(Long[] colaboradoresCheck)
	{
		this.colaboradoresCheck = colaboradoresCheck;
	}
	
	public Long[] getAreasIds()
	{
		return new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areasList);
	}

	public void setImprimirFeriasGozadas(boolean imprimirFeriasGozadas)
	{
		this.imprimirFeriasGozadas = imprimirFeriasGozadas;
	}

	public Collection<TPeriodoGozo> getPeriodosGozo()
	{
		return periodosGozo;
	}

	public String getBaseCnpj() {
		return baseCnpj;
	}

	public void setBaseCnpj(String baseCnpj) {
		this.baseCnpj = baseCnpj;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}

	public boolean isAgruparPorArea() {
		return agruparPorArea;
	}

	public void setAgruparPorArea(boolean agruparPorArea) {
		this.agruparPorArea = agruparPorArea;
	}

	public String getDinColumns() {
		return dinColumns;
	}

	public String getDinProperties() {
		return dinProperties;
	}

	public String getDinPropertiesGroup() {
		return dinPropertiesGroup;
	}

	public void setColaboradorMatricula(String colaboradorMatricula) {
		this.colaboradorMatricula = colaboradorMatricula;
	}
}