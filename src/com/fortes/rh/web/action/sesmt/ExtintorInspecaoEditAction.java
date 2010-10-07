package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoItemManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.web.action.MyActionSupportList;

public class ExtintorInspecaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private ExtintorInspecaoManager extintorInspecaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ExtintorManager extintorManager;

	private ExtintorInspecao extintorInspecao;
	private Estabelecimento estabelecimento;

	private Collection<ExtintorInspecao> extintorInspecaos;
	private Collection<Extintor> extintors;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<ExtintorInspecaoItem> extintorInspecaoItems;

	private String[] itemChecks;

	private String empresasResponsaveis;

	private ExtintorInspecaoItemManager extintorInspecaoItemManager;

	// Filtro da listagem
	private Long estabelecimentoId;
	private Long extintorId;
	private Date inicio;
	private Date fim;

	private void prepare() throws Exception
	{
		extintorInspecaoItems = extintorInspecaoItemManager.findAll();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		empresasResponsaveis = extintorInspecaoManager.getEmpresasResponsaveis(getEmpresaSistema().getId());

		if(extintorInspecao != null && extintorInspecao.getId() != null)
		{
			extintorInspecao = extintorInspecaoManager.findById(extintorInspecao.getId());
			estabelecimento = extintorInspecao.getExtintor().getEstabelecimento();
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

	public String list() throws Exception
	{
		if (extintorId != null && extintorId == -1L)
			extintorId = null;

		setTotalSize(extintorInspecaoManager.getCount(getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim));
		extintorInspecaos = extintorInspecaoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim);
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		return SUCCESS;
	}

	public String delete() throws Exception
	{
		extintorInspecaoManager.remove(new Long[]{extintorInspecao.getId()});

		return SUCCESS;
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
}