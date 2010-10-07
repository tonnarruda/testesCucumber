package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoServicoManager;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.web.action.MyActionSupportList;

public class ExtintorManutencaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private ExtintorManutencaoManager extintorManutencaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ExtintorManager extintorManager;

	private ExtintorManutencao extintorManutencao;
	private Estabelecimento estabelecimento;

	private Collection<ExtintorManutencao> extintorManutencaos;
	private Collection<Extintor> extintors;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<ExtintorManutencaoServico> extintorManutencaoServicos;
	private ExtintorManutencaoServicoManager extintorManutencaoServicoManager;

	private String[] servicoChecks;

	private Map motivos = new MotivoExtintorManutencao();

	// Filtro da listagem
	private Long estabelecimentoId;
	private Long extintorId;
	private Date inicio;
	private Date fim;
	private boolean somenteSemRetorno;

	private void prepare() throws Exception
	{
		extintorManutencaoServicos = extintorManutencaoServicoManager.findAll();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		if(getExtintorManutencao().getId() != null)
		{
			extintorManutencao = extintorManutencaoManager.findById(extintorManutencao.getId());
			estabelecimento = extintorManutencao.getExtintor().getEstabelecimento();
			extintors = extintorManager.findByEstabelecimento(estabelecimento.getId(), true);
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

			extintors = extintorManager.findByEstabelecimento(estabelecimento.getId(), true);

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

	public String list() throws Exception
	{
		if (extintorId != null && extintorId == -1L)
			extintorId = null;

		setTotalSize(extintorManutencaoManager.getCount(getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim, somenteSemRetorno));
		extintorManutencaos = extintorManutencaoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), estabelecimentoId, extintorId, inicio, fim, somenteSemRetorno);
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		return SUCCESS;
	}

	public String delete() throws Exception
	{
		extintorManutencaoManager.remove(new Long[]{extintorManutencao.getId()});

		return SUCCESS;
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

	public void setExtintorManutencaoManager(ExtintorManutencaoManager extintorManutencaoManager)
	{
		this.extintorManutencaoManager = extintorManutencaoManager;
	}
	public void setExtintorManager(ExtintorManager extintorManager)
	{
		this.extintorManager = extintorManager;
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

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setExtintorManutencaoServicoManager(ExtintorManutencaoServicoManager extintorManutencaoServicoManager)
	{
		this.extintorManutencaoServicoManager = extintorManutencaoServicoManager;
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
}