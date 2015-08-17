package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class EstabelecimentoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private EstabelecimentoManager estabelecimentoManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;

	private Collection<Estado> ufs = null;
	private Collection<Cidade> cidades = new ArrayList<Cidade>();

	private Estabelecimento estabelecimento;

	private String digitoVerificador;
	private boolean integradoAC;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(estabelecimento != null && estabelecimento.getId() != null)
			estabelecimento = (Estabelecimento) estabelecimentoManager.findById(estabelecimento.getId());

		if (ufs == null)
			ufs = estadoManager.findAll(new String[]{"sigla"});

		integradoAC = getEmpresaSistema().isAcIntegra();
		if (integradoAC)
			addActionMessage("A manutenção no cadastro de estabelecimento deve ser realizada no Fortes Pessoal.");

	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		if(estabelecimento == null || estabelecimento.getEmpresa() == null || !getEmpresaSistema().getId().equals(estabelecimento.getEmpresa().getId()))
		{
			estabelecimento = null;
			addActionError("O Estabelecimento solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		if (estabelecimento.getEndereco() != null && estabelecimento.getEndereco().getUf() != null && estabelecimento.getEndereco().getUf().getId() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{estabelecimento.getEndereco().getUf().getId()}, new String[]{"nome"});

		digitoVerificador = estabelecimentoManager.calculaDV(estabelecimento.getCnpj());

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		estabelecimento.setEmpresa(getEmpresaSistema());
		
		if(StringUtils.isBlank(estabelecimento.getCodigoAC()))
			estabelecimento.setCodigoAC(null);
		
		estabelecimentoManager.save(estabelecimento);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		String[] key = new String[]{"id","empresa.id"};
		Object[] values = new Object[]{estabelecimento.getId(), getEmpresaSistema().getId()};

		if(estabelecimentoManager.verifyExists(key, values))
		{
			if(StringUtils.isBlank(estabelecimento.getCodigoAC()))
				estabelecimento.setCodigoAC(null);

			estabelecimentoManager.update(estabelecimento);
			return Action.SUCCESS;
		}
		else
		{
			addActionError("O Estabelecimento solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.INPUT;
		}
	}

	public Object getModel()
	{
		return getEstabelecimento();
	}

	public Estabelecimento getEstabelecimento()
	{
		if(estabelecimento == null)
		{
			estabelecimento = new Estabelecimento();
			estabelecimento.setEmpresa(getEmpresaSistema());
		}
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

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public Collection<Estado> getUfs()
	{
		return ufs;
	}

	public void setUfs(Collection<Estado> ufs)
	{
		this.ufs = ufs;
	}

	public Collection<Cidade> getCidades()
	{
		return cidades;
	}

	public void setCidades(Collection<Cidade> cidades)
	{
		this.cidades = cidades;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public String getDigitoVerificador()
	{
		return digitoVerificador;
	}

	public void setDigitoVerificador(String digitoVerificador)
	{
		this.digitoVerificador = digitoVerificador;
	}
}