package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.NaturezaLesaoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.TipoAcidente;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
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
	
	public String list() throws Exception
	{
		if (!validaPeriodo())
			return SUCCESS;

		cats = catManager.findAllSelect(getEmpresaSistema().getId(), inicio, fim, estabelecimentosCheck, nomeBusca, areasCheck);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
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
			catManager.update(cat);
			
			addActionSuccess("Ficha de investigação de acidente atualizada com sucesso.");
		
		} catch (Exception e) {
			addActionError("Não foi possível atualizar a ficha de investigação de acidente.");
			e.printStackTrace();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
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
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), false, null, null);

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

	public String imprimirFichaInvestigacaoAcidente() throws Exception
	{
		try
		{
			cat = catManager.findByIdProjection(cat.getId());
			
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
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
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
}