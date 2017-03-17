package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoManager;
import com.fortes.rh.business.sesmt.HistoricoExtintorManager;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.model.sesmt.relatorio.ManutencaoAndInspecaoRelatorio;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ExtintorEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	@Autowired private ExtintorManager extintorManager;
	@Autowired private HistoricoExtintorManager historicoExtintorManager;
	@Autowired private ExtintorManutencaoManager extintorManutencaoManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private ExtintorInspecaoManager extintorInspecaoManager;

	private Extintor extintor;
	private Collection<Extintor> extintors;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<HistoricoExtintor> historicoExtintores;

	private Map<String,String> tipos = new TipoExtintor();

	private char ativo = 'T';
	private String tipoBusca = "";
	private Integer numeroBusca;
	private String fabricantes = "";
	private String localizacoes = "";
	
	//filtro do relatorio
	private Date dataVencimento;
	private boolean inspecaoVencida;
	private boolean cargaVencida;
	private boolean testeHidrostaticoVencido;
	
	private ExtintorInspecao extintorInspecao;
	private ExtintorManutencao extintorManutencao;
	private HistoricoExtintor historicoExtintor;
	
	private Date dataHidro;
	private Date dataRecarga;
	
	private Collection<ManutencaoAndInspecaoRelatorio> dataSource;
	private Map<String, Object> parametros;	
	
	public String prepare() throws Exception
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		fabricantes = extintorManager.getFabricantes(getEmpresaSistema().getId());
		localizacoes = extintorManager.getLocalizacoes(getEmpresaSistema().getId());

		if (extintor != null && extintor.getId() != null) 
		{
			extintor = (Extintor) extintorManager.findById(extintor.getId());
			historicoExtintores = historicoExtintorManager.findByExtintor(extintor.getId());
		}

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			extintor.setEmpresa(getEmpresaSistema());
			extintorManager.save(extintor);
			
			cadastrarHistorico();
			cadastrarInspecao();
			cadastrarManutencoes();

			addActionMessage("Extintor gravado com sucesso.");
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível gravar o extintor.");
			return INPUT;
		}
	}

	private void cadastrarHistorico() throws Exception 
	{
		historicoExtintor.setExtintor(extintor);
		historicoExtintor.setData(new Date());
		historicoExtintorManager.save(historicoExtintor);
	}
	
	private void cadastrarInspecao() throws Exception 
	{
		if(extintorInspecao != null && extintorInspecao.getData() != null)
		{
			extintorInspecao.setExtintor(extintor);
			extintorInspecaoManager.saveOrUpdate(extintorInspecao, null);
		}
	}

	private void cadastrarManutencoes() throws Exception 
	{
		extintorManutencao = new ExtintorManutencao();
		extintorManutencao.setExtintor(extintor);
		extintorManutencao.setSaida(dataHidro);
		
		if (dataHidro != null && dataRecarga != null && dataHidro.equals(dataRecarga))
			extintorManutencaoManager.saveOrUpdate(extintorManutencao, new String[] { "1", "3" });
		else
		{
			if (dataHidro != null)
			{
				extintorManutencaoManager.save(extintorManutencao, new String[] { "3" });
				extintorManutencao = null;
			}
			if (dataRecarga != null)
			{
				extintorManutencao = new ExtintorManutencao();
				extintorManutencao.setExtintor(extintor);
				extintorManutencao.setSaida(dataRecarga);
				extintorManutencaoManager.save(extintorManutencao, new String[] { "1" });
			}
		}
	}

	public String update() throws Exception
	{
		try
		{
			extintor.setEmpresa(getEmpresaSistema());
			extintorManager.update(extintor);
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage("Não foi possível gravar o extintor.");
			prepare();
			return INPUT;
		}
	}

	public String list() throws Exception
	{
		setTotalSize(extintorManager.getCount(getEmpresaSistema().getId(), tipoBusca, numeroBusca, ativo));
		extintors = extintorManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), tipoBusca, numeroBusca, ativo);

		return SUCCESS;
	}
	
	public String imprimirLista() throws Exception
	{
		extintors = extintorManager.findAllSelect(0, 0, getEmpresaSistema().getId(), tipoBusca, numeroBusca, ativo);
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Extintores", getEmpresaSistema(),"Ativos: " + BooleanUtil.getDescricao(ativo));
		if (extintors.isEmpty()) 
		{
			addActionMessage("Não existem dados para o filtro informado");
			list();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		extintorManager.remove(extintor.getId());

		list();
		return SUCCESS;
	}

	public String prepareRelatorio() throws Exception
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		return SUCCESS;
	}
	
	public String relatorioManutencaoAndInspecao() throws Exception
	{		
		try
		{
			dataSource = extintorManager.relatorioManutencaoAndInspecao(historicoExtintor.getEstabelecimento().getId(), dataVencimento, inspecaoVencida, cargaVencida, testeHidrostaticoVencido);
			parametros = RelatorioUtil.getParametrosRelatorio("Extintores - Manutenção e Inspeção", getEmpresaSistema(), extintorManager.montaLabelFiltro(historicoExtintor.getEstabelecimento().getId(), dataVencimento));
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorio();
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	public Extintor getExtintor()
	{
		if(extintor == null)
			extintor = new Extintor();
		return extintor;
	}

	public void setExtintor(Extintor extintor)
	{
		this.extintor = extintor;
	}

	public Collection<Extintor> getExtintors()
	{
		return extintors;
	}

	public Map<String,String> getTipos()
	{
		return tipos;
	}

	public void setTipos(Map<String,String> tipos)
	{
		this.tipos = tipos;
	}

	public char getAtivo()
	{
		return ativo;
	}

	public void setAtivo(char ativo)
	{
		this.ativo = ativo;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public Integer getNumeroBusca()
	{
		return numeroBusca;
	}

	public void setNumeroBusca(Integer numeroBusca)
	{
		this.numeroBusca = numeroBusca;
	}

	public String getTipoBusca()
	{
		return tipoBusca;
	}

	public void setTipoBusca(String tipoBusca)
	{
		this.tipoBusca = tipoBusca;
	}

	public String getFabricantes()
	{
		return fabricantes;
	}

	public String getLocalizacoes()
	{
		return localizacoes;
	}

	public Date getDataVencimento()
	{
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento)
	{
		this.dataVencimento = dataVencimento;
	}

	public boolean isCargaVencida()
	{
		return cargaVencida;
	}

	public void setCargaVencida(boolean cargaVencida)
	{
		this.cargaVencida = cargaVencida;
	}

	public boolean isInspecaoVencida()
	{
		return inspecaoVencida;
	}

	public void setInspecaoVencida(boolean inspecaoVencida)
	{
		this.inspecaoVencida = inspecaoVencida;
	}

	public boolean isTesteHidrostaticoVencido()
	{
		return testeHidrostaticoVencido;
	}

	public void setTesteHidrostaticoVencido(boolean testeHidrostaticoVencido)
	{
		this.testeHidrostaticoVencido = testeHidrostaticoVencido;
	}

	public Collection<ManutencaoAndInspecaoRelatorio> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public ExtintorInspecao getExtintorInspecao() {
		return extintorInspecao;
	}

	public void setExtintorInspecao(ExtintorInspecao extintorInspecao) {
		this.extintorInspecao = extintorInspecao;
	}
	
	public void setDataHidro(Date dataHidro) {
		this.dataHidro = dataHidro;
	}

	public void setDataRecarga(Date dataRecarga) {
		this.dataRecarga = dataRecarga;
	}

	public Collection<HistoricoExtintor> getHistoricoExtintores() {
		return historicoExtintores;
	}

	public HistoricoExtintor getHistoricoExtintor() {
		return historicoExtintor;
	}

	public void setHistoricoExtintor(HistoricoExtintor historicoExtintor) {
		this.historicoExtintor = historicoExtintor;
	}
}