package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.UsuarioAjudaESocialManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.NaturezaLesaoManager;
import com.fortes.rh.business.sesmt.TestemunhaManager;
import com.fortes.rh.business.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoManager;
import com.fortes.rh.business.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoManager;
import com.fortes.rh.business.sesmt.eSocialTabelas.DescricaoNaturezaLesaoManager;
import com.fortes.rh.business.sesmt.eSocialTabelas.ParteCorpoAtingidaManager;
import com.fortes.rh.business.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoManager;
import com.fortes.rh.business.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.IniciativaCat;
import com.fortes.rh.model.dicionario.Lateralidade;
import com.fortes.rh.model.dicionario.OrgaoDeClasse;
import com.fortes.rh.model.dicionario.TelaAjudaESocial;
import com.fortes.rh.model.dicionario.TipoAcidente;
import com.fortes.rh.model.dicionario.TipoCat;
import com.fortes.rh.model.dicionario.TipoInscricao;
import com.fortes.rh.model.dicionario.TipoLocalAcidente;
import com.fortes.rh.model.dicionario.TipoRegistrador;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;
import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.rh.web.ws.AcPessoalClientSistema;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

public class CatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private CatManager catManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EpiManager epiManager;
	private AmbienteManager ambienteManager;
	private NaturezaLesaoManager naturezaLesaoManager;
	private TestemunhaManager testemunhaManager;
	private CodificacaoAcidenteTrabalhoManager codificacaoAcidenteTrabalhoManager;
	private SituacaoGeradoraAcidenteTrabalhoManager situacaoGeradoraAcidenteTrabalhoManager;
	private ParteCorpoAtingidaManager parteCorpoAtingidaManager;
	private AgenteCausadorAcidenteTrabalhoManager agenteCausadorAcidenteTrabalhoManager;
	private SituacaoGeradoraDoencaProfissionalManager situacaoGeradoraDoencaProfissionalManager;
	private DescricaoNaturezaLesaoManager descricaoNaturezaLesaoManager;
	private EstadoManager estadoManager;
	private AcPessoalClientSistema acPessoalClientSistema;
	private EmpresaManager empresaManager;
	private CidadeManager cidadeManager;
	private UsuarioAjudaESocialManager usuarioAjudaESocialManager;

	private Colaborador colaborador;
	private Cat cat;
	private Collection<Cat> cats = null;

	private Date inicio;
	private Date fim;
	private String nomeBusca;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	
	private String[] estabelecimentosCheck;
	private String[] areasCheck;
	private Collection<Colaborador> colaboradors;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private String[] episChecked;
	private Collection<CheckBox> episCheckList = new HashSet<CheckBox>();

	private Collection<Ambiente> ambientes;
	private Collection<NaturezaLesao> naturezaLesaos;
	
	private Map<Integer, String> tipoAcidentes;
	
	private boolean exibirAssinatura1;
	private String assinatura1;
	private boolean exibirAssinatura2;
	private String assinatura2;
	private boolean exibirAssinatura3;
	private String assinatura3;
	private boolean exibirAssinatura4;
	private String assinatura4;
	private boolean exibirFotoAcidente;
	
	private boolean manterFoto;
	private File fotoAcidente;
	private Map<Integer, String> tiposInscricao = TipoInscricao.getInstanceCAT();
	private Collection<CodificacaoAcidenteTrabalho> codificacoesAcidenteTrabalho = new ArrayList<CodificacaoAcidenteTrabalho>();
	private Collection<SituacaoGeradoraAcidenteTrabalho> situacoesGeradoraAcidenteTrabalho = new ArrayList<SituacaoGeradoraAcidenteTrabalho>();
	private Collection<ParteCorpoAtingida> partesCorpoAtingida = new ArrayList<ParteCorpoAtingida>();
	private Collection<AgenteCausadorAcidenteTrabalho> agentesCausadoresAcidenteTrabalho = new ArrayList<AgenteCausadorAcidenteTrabalho>();
	private Collection<SituacaoGeradoraDoencaProfissional> situacoesGeradorasDoencaProfissional = new ArrayList<SituacaoGeradoraDoencaProfissional>();
	private Collection<DescricaoNaturezaLesao> descricoesNaturezaLesoes = new ArrayList<DescricaoNaturezaLesao>();
	private Collection<Estado> estados;
	private LinkedHashMap<Long, String> orgaosDeClasse = new OrgaoDeClasse();
	private LinkedHashMap<Long, String> tiposLocalAcidente = new TipoLocalAcidente();
	private LinkedHashMap<Long, String> iniciativasCat = new IniciativaCat();
	private LinkedHashMap<Long, String> tiposCat = new TipoCat();
	@SuppressWarnings("unchecked")
	private LinkedHashMap<Long, String> lateralidades = new Lateralidade();
	@SuppressWarnings("unchecked")
	private LinkedHashMap<Long, String> tiposRegistradores = new TipoRegistrador();
	
	private Collection<Cidade> cidades;
	
	private String[] partesCorpoAtingidaSelecionados;
	private Long[] agentesCausadoresAcidenteTrabalhoSelecionados;
	private Long[] situacoesGeradoraDoencaProfissionalSelecionados;
	
	private boolean aderiuAoESocial;
	
	public String list() throws Exception
	{
		if (!validaPeriodo())
			return SUCCESS;

		cats = catManager.findAllSelect(getEmpresaSistema().getId(), inicio, fim, estabelecimentosCheck, nomeBusca, areasCheck);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome", null);
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);

		prepareAssinaturas();
		
		return SUCCESS;
	}

	private void prepareAssinaturas() 
	{
		
        if(!exibirAssinatura1 && assinatura1 == null)
        {
        	exibirAssinatura1 = true;
        	assinatura1 = "Responsável SESMT";
        }
        if(!exibirAssinatura2 && assinatura2 == null)
        {
        	exibirAssinatura2 = true;
        	assinatura2 = "Presidente da CIPA";
        }
        if(!exibirAssinatura3 && assinatura3 == null)
        {
        	exibirAssinatura3 = true;
        	assinatura3 = "Chefia";
        }
        if(!exibirAssinatura4 && assinatura4 == null)
        {
        	exibirAssinatura4 = true;
        	assinatura4 = "Testemunha";
        }
	}

	private boolean validaPeriodo()
	{
		if (inicio != null && fim != null)
		{
			if (fim.before(inicio))
			{
				addActionError("Data final anterior à data inicial do período.");
				return false;
			}
		}
		return true;
	}

	public String delete() throws Exception
	{
		try {
			catManager.remove(cat.getId());
			addActionSuccess("Ficha de investigação de acidente excluída com sucesso.");
		
		} catch (Exception e) {
			addActionError("Não foi possível excluir essa ficha de investigação de acidente.");
			e.printStackTrace();
			return Action.INPUT;
		}
		

		return SUCCESS;
	}

	private void prepare() throws Exception
	{
		Boolean epiAtivo = true;
		
		if(cat != null && cat.getId() != null) {
			cat = (Cat) catManager.findById(cat.getId());
			epiAtivo = null;
		}

		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), epiAtivo);
		
		ambientes = ambienteManager.findByEmpresa(getEmpresaSistema().getId());
		naturezaLesaos = naturezaLesaoManager.findAllSelect(getEmpresaSistema().getId());
		tipoAcidentes = new TipoAcidente();
		
		//eSocial
		codificacoesAcidenteTrabalho = codificacaoAcidenteTrabalhoManager.findAllSelect();
		situacoesGeradoraAcidenteTrabalho = situacaoGeradoraAcidenteTrabalhoManager.findAllSelect();
		partesCorpoAtingida = parteCorpoAtingidaManager.findAllSelect();
		agentesCausadoresAcidenteTrabalho = agenteCausadorAcidenteTrabalhoManager.findAllSelect(); 
		situacoesGeradorasDoencaProfissional = situacaoGeradoraDoencaProfissionalManager.findAll();
		descricoesNaturezaLesoes = descricaoNaturezaLesaoManager.findAllSelect();
		estados = estadoManager.findAll(new String[]{"sigla"});
		
		if (cat != null && cat.getEndereco() != null && cat.getEndereco().getUf() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{cat.getEndereco().getUf().getId()}, new String[]{"nome"});
		else
			cidades = new ArrayList<Cidade>();
		
		Empresa empresa = empresaManager.findByIdProjection(getEmpresaSistema().getId());
		if(empresa.isAcIntegra())
			aderiuAoESocial = acPessoalClientSistema.isAderiuAoESocial(empresa);
		aderiuAoESocial = true;
		
		setExibeDialogAJuda(!usuarioAjudaESocialManager.verifyExists(new String[]{"usuario.id", "telaAjuda"}, new Object[]{getUsuarioLogado().getId(), TelaAjudaESocial.EDICAO_CAT}));
		setTelaAjuda(TelaAjudaESocial.EDICAO_CAT);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return SUCCESS;
	}

	private void beforeInsertUpdate() throws Exception
	{
		catManager.setFoto(cat, manterFoto, fotoAcidente, "sesmt");
		
		CollectionUtil<Epi> cUtil = new CollectionUtil<Epi>();
		Collection<Epi> epis = cUtil.convertArrayStringToCollection(Epi.class, episChecked);
		
		cat.setEpis(epis);
		
		if (cat.getAmbienteColaborador() == null || cat.getAmbienteColaborador().getId() == null)
			cat.setAmbienteColaborador(null);

		if (cat.getFuncaoColaborador() == null || cat.getFuncaoColaborador().getId() == null)
			cat.setFuncaoColaborador(null);
		
		if (cat.getNaturezaLesao() == null || cat.getNaturezaLesao().getId() == null)
			cat.setNaturezaLesao(null);
	}
	
	public String prepareUpdate() throws Exception
	{
		prepare();
		episCheckList = CheckListBoxUtil.marcaCheckListBox(episCheckList, cat.getEpis(), "getId");
		
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			beforeInsertUpdate();
			validatestemunha(cat);
			catManager.ajustaEntidade(cat,partesCorpoAtingidaSelecionados,agentesCausadoresAcidenteTrabalhoSelecionados,situacoesGeradoraDoencaProfissionalSelecionados);
			catManager.save(cat);
			
			addActionSuccess("Ficha de investigação de acidente cadastrada com sucesso.");
		
		} catch (Exception e) {
			addActionError("Não foi possível cadastrar a ficha de investigação de acidente.");
			e.printStackTrace();
			return Action.INPUT;
		}
		
		return SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			beforeInsertUpdate();
			validatestemunha(cat);
			catManager.ajustaEntidade(cat,partesCorpoAtingidaSelecionados,agentesCausadoresAcidenteTrabalhoSelecionados,situacoesGeradoraDoencaProfissionalSelecionados);
			catManager.update(cat);
			
			addActionSuccess("Ficha de investigação de acidente atualizada com sucesso.");
		} catch (Exception e) {
			addActionError("Não foi possível atualizar a ficha de investigação de acidente.");
			e.printStackTrace();
			return Action.INPUT;
		}
		
		prepare();
		return Action.SUCCESS;
	}
	
	private void validatestemunha(Cat cat) {
		if(cat.getTestemunha1() == null || cat.getTestemunha1().getNome() == null || cat.getTestemunha1().getNome().isEmpty()){
			if(cat.getTestemunha1() != null && cat.getTestemunha1().getId() != null)
				testemunhaManager.removeTestemunha(cat.getTestemunha1().getId(), "testemunha1");
			cat.setTestemunha1(null);
		}
		if(cat.getTestemunha2() == null || cat.getTestemunha2().getNome() == null || cat.getTestemunha2().getNome().isEmpty()){
			if(cat.getTestemunha2() != null && cat.getTestemunha2().getId() != null)
				testemunhaManager.removeTestemunha(cat.getTestemunha2().getId(), "testemunha2");
			cat.setTestemunha2(null);
		}
	}

	public String showFoto() throws Exception
	{
		if (cat.getFotoUrl() != null && !cat.getFotoUrl().equals(""))
		{
			java.io.File file = ArquivoUtil.getArquivo(cat.getFotoUrl(),"sesmt");
			HttpServletResponse response = ServletActionContext.getResponse();
			
			ArquivoUtil.showFile(file, response);
		}
		
		return Action.SUCCESS;
	}

	public String filtrarColaboradores() throws Exception
	{
		colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, false, null, null, getEmpresaSistema().getId());

		if (colaboradors == null || colaboradors.isEmpty())
			addActionMessage("Nenhum colaborador para o filtro informado.");

		prepare();

		return SUCCESS;
	}
	
	public String relatorioCats() throws Exception
	{
		try
		{
			if (!validaPeriodo())
				return INPUT;
			
			cats = catManager.findRelatorioCats(getEmpresaSistema().getId(), inicio, fim, estabelecimentosCheck, nomeBusca);
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Registros de CAT's", getEmpresaSistema(), getPeriodoFormatado());
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioCats();
			return INPUT;
		}
		
		return SUCCESS;
	}

	public String imprimirCat() throws Exception
	{
		try
		{
			cat = catManager.findByIdProjectionDetalhada(cat.getId());
			
			parametros = RelatorioUtil.getParametrosRelatorio("Ficha de Investigacao de Acidente", getEmpresaSistema(), "Comissão Interna de Prevenção de Acidentes");
			
			String pathImg = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separatorChar;
			parametros.put("IMG_DIR", pathImg);
			parametros.put("FOTO_URL", ArquivoUtil.getPathAnexo("sesmt") + java.io.File.separatorChar + cat.getFotoUrl());
			
			configuraImpressaoInformacoes();
			
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}
	
	public String imprimirFichaInvestigacaoAcidente() throws Exception
	{
		try
		{
			cat = catManager.findByIdProjectionSimples(cat.getId());
			
			parametros = RelatorioUtil.getParametrosRelatorio("Ficha de Investigacao de Acidente", getEmpresaSistema(), "Comissão Interna de Prevenção de Acidentes");
			
			String pathImg = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separatorChar;
			parametros.put("IMG_DIR", pathImg);
			parametros.put("FOTO_URL", ArquivoUtil.getPathAnexo("sesmt") + java.io.File.separatorChar + cat.getFotoUrl());
			
			configuraImpressaoInformacoes();
			
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}

	private void configuraImpressaoInformacoes() 
	{
		int count = 0;
		if(exibirAssinatura1)
			parametros.put("ASS" + (++count), assinatura1);
		if(exibirAssinatura2)
			parametros.put("ASS" + (++count), assinatura2);
		if(exibirAssinatura3)
			parametros.put("ASS" + (++count), assinatura3);
		if(exibirAssinatura4)
			parametros.put("ASS" + (++count), assinatura4);
		
		parametros.put("EXIBIR_FOTO", exibirFotoAcidente);
	}
	
	private String getPeriodoFormatado()
	{
		String periodoFormatado = "-";
		if (inicio != null && fim != null)
			periodoFormatado = "Período: " + DateUtil.formataDiaMesAno(inicio) + " - " + DateUtil.formataDiaMesAno(fim);

		return periodoFormatado;
	}
	
	
	public String prepareRelatorioCats() throws Exception
	{
		cats = catManager.findAll();
		
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome", null);
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		return SUCCESS;
	}

	public Cat getCat()
	{
		if(cat == null)
			cat = new Cat();
		return cat;
	}

	public void setCat(Cat cat)
	{
		this.cat = cat;
	}

	public void setCatManager(CatManager catManager)
	{
		this.catManager = catManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Cat> getCats()
	{
		return cats;
	}

	public void setCats(Collection<Cat> cats)
	{
		this.cats = cats;
	}

	public Date getFim()
	{
		return fim;
	}

	public void setFim(Date fim)
	{
		this.fim = fim;
	}

	public Date getInicio()
	{
		return inicio;
	}

	public void setInicio(Date inicio)
	{
		this.inicio = inicio;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
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

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}

	public void setEpisChecked(String[] episChecked) {
		this.episChecked = episChecked;
	}

	public Collection<CheckBox> getEpisCheckList() {
		return episCheckList;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager) {
		this.ambienteManager = ambienteManager;
	}

	public Collection<Ambiente> getAmbientes() {
		return ambientes;
	}

	public void setNaturezaLesaoManager(NaturezaLesaoManager naturezaLesaoManager) {
		this.naturezaLesaoManager = naturezaLesaoManager;
	}

	public Collection<NaturezaLesao> getNaturezaLesaos() {
		return naturezaLesaos;
	}

	public Map<Integer, String> getTipoAcidentes() {
		return tipoAcidentes;
	}

	public boolean isExibirAssinatura1() {
		return exibirAssinatura1;
	}

	public void setExibirAssinatura1(boolean exibirAssinatura1) {
		this.exibirAssinatura1 = exibirAssinatura1;
	}

	public String getAssinatura1() {
		return assinatura1;
	}

	public void setAssinatura1(String assinatura1) {
		this.assinatura1 = assinatura1;
	}

	public boolean isExibirAssinatura2() {
		return exibirAssinatura2;
	}

	public void setExibirAssinatura2(boolean exibirAssinatura2) {
		this.exibirAssinatura2 = exibirAssinatura2;
	}

	public String getAssinatura2() {
		return assinatura2;
	}

	public void setAssinatura2(String assinatura2) {
		this.assinatura2 = assinatura2;
	}

	public boolean isExibirAssinatura3() {
		return exibirAssinatura3;
	}

	public void setExibirAssinatura3(boolean exibirAssinatura3) {
		this.exibirAssinatura3 = exibirAssinatura3;
	}

	public String getAssinatura3() {
		return assinatura3;
	}

	public void setAssinatura3(String assinatura3) {
		this.assinatura3 = assinatura3;
	}

	public boolean isExibirAssinatura4() {
		return exibirAssinatura4;
	}

	public void setExibirAssinatura4(boolean exibirAssinatura4) {
		this.exibirAssinatura4 = exibirAssinatura4;
	}

	public String getAssinatura4() {
		return assinatura4;
	}

	public void setAssinatura4(String assinatura4) {
		this.assinatura4 = assinatura4;
	}

	public boolean isManterFoto() {
		return manterFoto;
	}

	public void setManterFoto(boolean manterFoto) {
		this.manterFoto = manterFoto;
	}

	public File getFotoAcidente() {
		return fotoAcidente;
	}

	public void setFotoAcidente(File fotoAcidente) {
		this.fotoAcidente = fotoAcidente;
	}

	
	public void setExibirFotoAcidente(boolean exibirFotoAcidente)
	{
		this.exibirFotoAcidente = exibirFotoAcidente;
	}

	public void setTestemunhaManager(TestemunhaManager testemunhaManager) {
		this.testemunhaManager = testemunhaManager;
	}

	public Map<Integer, String> getTiposInscricao() {
		return tiposInscricao;
	}

	public void setCodificacaoAcidenteTrabalhoManager(CodificacaoAcidenteTrabalhoManager codificacaoAcidenteTrabalhoManager) {
		this.codificacaoAcidenteTrabalhoManager = codificacaoAcidenteTrabalhoManager;
	}

	public Collection<CodificacaoAcidenteTrabalho> getCodificacoesAcidenteTrabalho() {
		return codificacoesAcidenteTrabalho;
	}

	public Collection<SituacaoGeradoraAcidenteTrabalho> getSituacoesGeradoraAcidenteTrabalho() {
		return situacoesGeradoraAcidenteTrabalho;
	}

	public void setSituacoesGeradoraAcidenteTrabalho(
			Collection<SituacaoGeradoraAcidenteTrabalho> situacoesGeradoraAcidenteTrabalho) {
		this.situacoesGeradoraAcidenteTrabalho = situacoesGeradoraAcidenteTrabalho;
	}

	public void setSituacaoGeradoraAcidenteTrabalhoManager(
			SituacaoGeradoraAcidenteTrabalhoManager situacaoGeradoraAcidenteTrabalhoManager) {
		this.situacaoGeradoraAcidenteTrabalhoManager = situacaoGeradoraAcidenteTrabalhoManager;
	}

	public void setParteCorpoAtingidaManager(
			ParteCorpoAtingidaManager parteCorpoAtingidaManager) {
		this.parteCorpoAtingidaManager = parteCorpoAtingidaManager;
	}

	public Collection<ParteCorpoAtingida> getPartesCorpoAtingida() {
		return partesCorpoAtingida;
	}

	public void setPartesCorpoAtingida(
			Collection<ParteCorpoAtingida> partesCorpoAtingida) {
		this.partesCorpoAtingida = partesCorpoAtingida;
	}

	public void setSituacaoGeradoraDoencaProfissionalManager(
			SituacaoGeradoraDoencaProfissionalManager situacaoGeradoraDoencaProfissionalManager) {
		this.situacaoGeradoraDoencaProfissionalManager = situacaoGeradoraDoencaProfissionalManager;
	}

	public void setAgenteCausadorAcidenteTrabalhoManager(
			AgenteCausadorAcidenteTrabalhoManager agenteCausadorAcidenteTrabalhoManager) {
		this.agenteCausadorAcidenteTrabalhoManager = agenteCausadorAcidenteTrabalhoManager;
	}

	public Collection<AgenteCausadorAcidenteTrabalho> getAgentesCausadoresAcidenteTrabalho() {
		return agentesCausadoresAcidenteTrabalho;
	}

	public Collection<SituacaoGeradoraDoencaProfissional> getSituacoesGeradorasDoencaProfissional() {
		return situacoesGeradorasDoencaProfissional;
	}

	public Collection<DescricaoNaturezaLesao> getDescricoesNaturezaLesoes() {
		return descricoesNaturezaLesoes;
	}

	public void setDescricoesNaturezaLesoes(
			Collection<DescricaoNaturezaLesao> descricoesNaturezaLesoes) {
		this.descricoesNaturezaLesoes = descricoesNaturezaLesoes;
	}

	public void setDescricaoNaturezaLesaoManager(
			DescricaoNaturezaLesaoManager descricaoNaturezaLesaoManager) {
		this.descricaoNaturezaLesaoManager = descricaoNaturezaLesaoManager;
	}

	public Collection<Estado> getEstados() {
		return estados;
	}

	public void setEstados(Collection<Estado> estados) {
		this.estados = estados;
	}

	public void setEstadoManager(EstadoManager estadoManager) {
		this.estadoManager = estadoManager;
	}

	public LinkedHashMap<Long, String> getOrgaosDeClasse() {
		return orgaosDeClasse;
	}

	public void setOrgaosDeClasse(LinkedHashMap<Long, String> orgaosDeClasse) {
		this.orgaosDeClasse = orgaosDeClasse;
	}

	public LinkedHashMap<Long, String> getTiposLocalAcidente() {
		return tiposLocalAcidente;
	}

	public void setTiposLocalAcidente(LinkedHashMap<Long, String> tiposLocalAcidente) {
		this.tiposLocalAcidente = tiposLocalAcidente;
	}

	public LinkedHashMap<Long, String> getIniciativasCat() {
		return iniciativasCat;
	}

	public void setIniciativasCat(LinkedHashMap<Long, String> iniciativasCat) {
		this.iniciativasCat = iniciativasCat;
	}

	public LinkedHashMap<Long, String> getTiposCat() {
		return tiposCat;
	}

	public void setTiposCat(LinkedHashMap<Long, String> tiposCat) {
		this.tiposCat = tiposCat;
	}

	public void setSituacoesGeradoraDoencaProfissionalSelecionados(Long[] situacoesGeradoraDoencaProfissionalSelecionados) {
		this.situacoesGeradoraDoencaProfissionalSelecionados = situacoesGeradoraDoencaProfissionalSelecionados;
	}

	public void setAgentesCausadoresAcidenteTrabalhoSelecionados(Long[] agentesCausadoresAcidenteTrabalhoSelecionados) {
		this.agentesCausadoresAcidenteTrabalhoSelecionados = agentesCausadoresAcidenteTrabalhoSelecionados;
	}

	public boolean isAderiuAoESocial() {
		return aderiuAoESocial;
	}

	public void setAderiuAoESocial(boolean aderiuAoESocial) {
		this.aderiuAoESocial = aderiuAoESocial;
	}

	public void setAcPessoalClientSistema(
			AcPessoalClientSistema acPessoalClientSistema) {
		this.acPessoalClientSistema = acPessoalClientSistema;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setCidadeManager(CidadeManager cidadeManager) {
		this.cidadeManager = cidadeManager;
	}

	public Collection<Cidade> getCidades() {
		return cidades;
	}

	public void setUsuarioAjudaESocialManager(
			UsuarioAjudaESocialManager usuarioAjudaESocialManager) {
		this.usuarioAjudaESocialManager = usuarioAjudaESocialManager;
	}

	public LinkedHashMap<Long, String> getLateralidades() {
		return lateralidades;
	}

	public void setLateralidades(LinkedHashMap<Long, String> lateralidades) {
		this.lateralidades = lateralidades;
	}

	public void setPartesCorpoAtingidaSelecionados(String[] partesCorpoAtingidaSelecionados) {
		this.partesCorpoAtingidaSelecionados = partesCorpoAtingidaSelecionados;
	}

	public LinkedHashMap<Long, String> getTiposRegistradores() {
		return tiposRegistradores;
	}

	public void setTiposRegistradores(LinkedHashMap<Long, String> tiposRegistradores) {
		this.tiposRegistradores = tiposRegistradores;
	}
}