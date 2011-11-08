package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoItemManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ExtintorInspecaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private ExtintorInspecaoManager extintorInspecaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ExtintorManager extintorManager;
	private Extintor extintor;

	private ExtintorInspecao extintorInspecao;
	private Estabelecimento estabelecimento;

	private Collection<ExtintorInspecao> extintorInspecaos;
	private Collection<Extintor> extintors;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<ExtintorInspecaoItem> extintorInspecaoItems;
	private Map<String, Object> parametros;
	
	private String[] itemChecks;
	private char regularidade; 
	
	private String empresasResponsaveis;

	private ExtintorInspecaoItemManager extintorInspecaoItemManager;

	// Filtro da listagem
	private Long estabelecimentoId;
	private Long extintorId;
	private String localizacao;
	private Date inicio;
	private Date fim;

	private void prepare() throws Exception
	{
		extintorInspecaoItems = extintorInspecaoItemManager.findAll();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		empresasResponsaveis = extintorInspecaoManager.getEmpresasResponsaveis(getEmpresaSistema().getId());

		if(extintorInspecao != null && extintorInspecao.getId() != null)
		{
			extintorInspecao = extintorInspecaoManager.findByIdProjection(extintorInspecao.getId());
			estabelecimento = extintorInspecao.getExtintor().getHistoricoExtintor().getEstabelecimento();
			extintors = extintorManager.findByEstabelecimento(estabelecimento.getId(), true);
		}
	}

	public String prepareInsert() throws Exception
	{
		Date hoje = new Date();
		extintorInspecao = getExtintorInspecao();
		extintorInspecao.setData(hoje);

		prepare();

		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			extintorInspecaoManager.saveOrUpdate(extintorInspecao, itemChecks);
			addActionMessage("Inspeção gravada com sucesso.");

			extintors = extintorManager.findByEstabelecimento(estabelecimento.getId(), true);

			extintorInspecao = null;
			itemChecks = null;
			prepareInsert();

			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage("Não foi possível gravar a inspeção.");
			prepare();
			return INPUT;
		}
	}

	public String update() throws Exception
	{
		try
		{
			extintorInspecaoManager.saveOrUpdate(extintorInspecao, itemChecks);
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage("Não foi possível gravar a inspeção.");
			prepare();
			return INPUT;
		}
	}

	public String list() 
	{
		if (extintorId != null && extintorId == -1L)
			extintorId = null;
		
		setTotalSize(extintorInspecaoManager.getCount(getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim, regularidade));
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		extintorInspecaos = extintorInspecaoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim, regularidade, localizacao);
	
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		extintorInspecaoManager.remove(new Long[]{extintorInspecao.getId()});

		return SUCCESS;
	}
	
	public String imprimirListaInspecaoDeExtintores() {

		try {
		
			list();

			if(extintorInspecaos.isEmpty()){
				throw new Exception ("Não existe informações com os filtros selecionados para geração do relatório");
			}
						
			String nomeEstabelecimento = "Todos";
			String nomeExtintor = "Todos";
			String nomeRegularidade = "Todos";
			String periodo = "";
			
			if(estabelecimentoId != null){
				estabelecimento = estabelecimentoManager.findById(estabelecimentoId);
				nomeEstabelecimento = estabelecimento.getNome();
			}
				
			if(extintorId != null){
				extintor = extintorManager.findById(extintorId);
				nomeExtintor = extintor.getDescricao();
			}
			
			if(regularidade == '1'){
				nomeRegularidade = "Regular";
			}
			if(regularidade == '2'){
				nomeRegularidade = "Irregular";
			}
		
			if (inicio != null && fim != null){
				periodo = "Período: " + DateUtil.formataDiaMesAno(inicio) + " á " + DateUtil.formataDiaMesAno(fim);
			}
						
			String filtro = "Estabelecimento: " + nomeEstabelecimento +
			"\n" + "Extintor: " + nomeExtintor +
			"\n" + "Regularidade: " + nomeRegularidade + 
			"\n" + periodo;
			
			parametros = RelatorioUtil.getParametrosRelatorio("Listagem de Inspeção de Extintores", getEmpresaSistema(), filtro);
			
		}
		
		catch (Exception e)
			{
				addActionMessage(e.getMessage());
				e.printStackTrace();
				list();
				return Action.INPUT;
			}
		
		return Action.SUCCESS;

	}


	public ExtintorInspecao getExtintorInspecao()
	{
		if(extintorInspecao == null)
			extintorInspecao = new ExtintorInspecao();
		return extintorInspecao;
	}

	public void setExtintorInspecao(ExtintorInspecao extintorInspecao)
	{
		this.extintorInspecao = extintorInspecao;
	}

	public void setExtintorInspecaoManager(ExtintorInspecaoManager extintorInspecaoManager)
	{
		this.extintorInspecaoManager = extintorInspecaoManager;
	}
	public Collection<Extintor> getExtintors()
	{
		return extintors;
	}

	public Collection<ExtintorInspecao> getExtintorInspecaos()
	{
		return extintorInspecaos;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public Long getEstabelecimentoId()
	{
		return estabelecimentoId;
	}

	public void setEstabelecimentoId(Long estabelecimentoId)
	{
		this.estabelecimentoId = estabelecimentoId;
	}

	public Long getExtintorId()
	{
		return extintorId;
	}

	public void setExtintorId(Long extintorId)
	{
		this.extintorId = extintorId;
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

	public Collection<ExtintorInspecaoItem> getExtintorInspecaoItems()
	{
		return extintorInspecaoItems;
	}

	public String[] getItemChecks()
	{
		return itemChecks;
	}

	public void setItemChecks(String[] itemChecks)
	{
		this.itemChecks = itemChecks;
	}

	public void setExtintorInspecaoItemManager(ExtintorInspecaoItemManager extintorInspecaoItemManager)
	{
		this.extintorInspecaoItemManager = extintorInspecaoItemManager;
	}

	public void setExtintorManager(ExtintorManager extintorManager)
	{
		this.extintorManager = extintorManager;
	}

	public String getEmpresasResponsaveis()
	{
		return empresasResponsaveis;
	}

	public char getRegularidade() {
		return regularidade;
	}

	public void setRegularidade(char regularidade) {
		this.regularidade = regularidade;
	}

	public Extintor getExtintor() {
		return extintor;
	}

	public void setExtintor(Extintor extintor) {
		this.extintor = extintor;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
}