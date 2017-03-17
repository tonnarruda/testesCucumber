package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoServicoManager;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ExtintorManutencaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	@Autowired private ExtintorManutencaoServicoManager extintorManutencaoServicoManager;
	@Autowired private ExtintorManutencaoManager extintorManutencaoManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private ExtintorManager extintorManager;

	private Extintor extintor;
	private ExtintorManutencao extintorManutencao;
	private Estabelecimento estabelecimento;

	private Collection<ExtintorManutencao> extintorManutencaos;
	private Collection<Extintor> extintors;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<ExtintorManutencaoServico> extintorManutencaoServicos;

	private String[] servicoChecks;

	private Map motivos = new MotivoExtintorManutencao();
	private Map parametros;

	// Filtro da listagem
	private Long estabelecimentoId;
	private Long extintorId;
	private String localizacao;
	private Date inicio;
	private Date fim;
	private boolean somenteSemRetorno;

	private void prepare() throws Exception
	{
		extintorManutencaoServicos = extintorManutencaoServicoManager.findAll();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		if(getExtintorManutencao().getId() != null)
		{
			extintorManutencao = extintorManutencaoManager.findByIdProjection(extintorManutencao.getId());
			estabelecimento = extintorManutencao.getExtintor().getUltimoHistorico().getEstabelecimento();
			extintors = extintorManager.findAllComHistAtual(true, estabelecimento.getId(), null);
		}
	}

	public String prepareInsert() throws Exception
	{
		Date hoje = new Date();
		extintorManutencao = getExtintorManutencao();
		extintorManutencao.setSaida(hoje);

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
			extintorManutencaoManager.saveOrUpdate(extintorManutencao, servicoChecks);
			addActionMessage("Manutenção gravada com sucesso.");

			extintors = extintorManager.findAllComHistAtual(true, estabelecimento.getId(), null);

			extintorManutencao = null;
			servicoChecks = null;
			prepareInsert();

			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage("Não foi possível gravar a manutenção.");
			prepare();
			return INPUT;
		}
	}

	public String update() throws Exception
	{
		try
		{
			extintorManutencaoManager.saveOrUpdate(extintorManutencao, servicoChecks);
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage("Não foi possível gravar a manutenção.");
			prepare();
			return INPUT;
		}
	}

	public String list()
	{
		prepareList(getPage(), getPagingSize());

		return SUCCESS;
	}

	private void prepareList(int page, int pagingSize) 
	{
		if (extintorId != null && extintorId == -1L)
			extintorId = null;

		if(estabelecimentoId != null)
			extintors = extintorManager.findAllComHistAtual(true, estabelecimentoId, null);
		
		setTotalSize(extintorManutencaoManager.getCount(getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim, somenteSemRetorno));
		extintorManutencaos = extintorManutencaoManager.findAllSelect(page, pagingSize, getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim, somenteSemRetorno, localizacao);
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
	}

	public String delete() throws Exception
	{
		extintorManutencaoManager.remove(new Long[]{extintorManutencao.getId()});

		return SUCCESS;
	}

	public String imprimirListaManutencaoDeExtintores() {

		try {
		
			prepareList(0, 0);

			if(extintorManutencaos.isEmpty()){
				throw new Exception ("Não existe informações com os filtros selecionados para geração do relatório");
			}
						
			String nomeEstabelecimento = "Todos";
			String nomeExtintor = "Todos";
			String periodo = "";
			
			if(estabelecimentoId != null){
				estabelecimento = estabelecimentoManager.findById(estabelecimentoId);
				nomeEstabelecimento = estabelecimento.getNome();
			}
				
			if(extintorId != null){
				extintor = extintorManager.findById(extintorId);
				nomeExtintor = extintor.getDescricao();
			}
		
			if (inicio != null && fim != null){
				periodo = "Período: " + DateUtil.formataDiaMesAno(inicio) + " à " + DateUtil.formataDiaMesAno(fim);
			}
						
			String filtro = "Estabelecimento: " + nomeEstabelecimento +
			"\n" + "Extintor: " + nomeExtintor +
			"\n" + periodo;
			
			parametros = RelatorioUtil.getParametrosRelatorio("Listagem de Manutenções de Extintores", getEmpresaSistema(), filtro);
			
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
	
	public ExtintorManutencao getExtintorManutencao()
	{
		if(extintorManutencao == null)
			extintorManutencao = new ExtintorManutencao();
		return extintorManutencao;
	}

	public void setExtintorManutencao(ExtintorManutencao extintorManutencao)
	{
		this.extintorManutencao = extintorManutencao;
	}

	public Collection<Extintor> getExtintors()
	{
		return extintors;
	}

	public void setExtintors(Collection<Extintor> extintors)
	{
		this.extintors = extintors;
	}

	public Collection<ExtintorManutencao> getExtintorManutencaos()
	{
		return extintorManutencaos;
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

	public String[] getServicoChecks()
	{
		return servicoChecks;
	}

	public void setServicoChecks(String[] servicoChecks)
	{
		this.servicoChecks = servicoChecks;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public Collection<ExtintorManutencaoServico> getExtintorManutencaoServicos()
	{
		return extintorManutencaoServicos;
	}

	public boolean isSomenteSemRetorno()
	{
		return somenteSemRetorno;
	}

	public void setSomenteSemRetorno(boolean somenteSemRetorno)
	{
		this.somenteSemRetorno = somenteSemRetorno;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Map getMotivos()
	{
		return motivos;
	}

	public void setExtintor(Extintor extintor) {
		this.extintor = extintor;
	}

	public Map getParametros() {
		return parametros;
	}

	public Extintor getExtintor() {
		return extintor;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
}