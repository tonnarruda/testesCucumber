package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class DNTListAction extends MyActionSupportList
{
	private DNTManager DNTManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;

	private Collection<DNT> dnts;
	private Collection<ColaboradorTurma> colaboradorTurmas;
	private Collection<Colaborador> colaboradors;
	private Collection<AreaOrganizacional> areas;
	private AreaOrganizacional areaFiltro;

	private Collection<Estabelecimento> estabelecimentos;
	private Estabelecimento estabelecimento;

	private DNT dnt;

	private Usuario responsavelArea = null;
	private Collection<AreaOrganizacional> areasResponsavel = null;
	private boolean gestor;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		if (gestor)
		{
			listDetalhes();
			return "gestor";
		}

		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"data desc"};

		setTotalSize(DNTManager.getCount(keys, values));
		dnts = DNTManager.find(getPage(), getPagingSize(), keys, values, orders);

		if(!msgAlert.equals(""))
			addActionError(msgAlert);

		return Action.SUCCESS;
	}

	//TODO metodo imundo BACALHAU (metodo com muitos ifs)
	public String listDetalhes() throws Exception
	{
		if(!msgAlert.equals(""))
			addActionMessage(msgAlert);

		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		areas = null;

		if (gestor)
		{
			responsavelArea = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
			Colaborador colaborador = colaboradorManager.findByUsuario(responsavelArea, getEmpresaSistema().getId());
			areasResponsavel = areaOrganizacionalManager.find(new String[]{"responsavel.id"}, new Object[]{colaborador.getId()});

			if (areasResponsavel.isEmpty())
			{
				addActionMessage("Apenas os Responsáveis por Áreas Organizacionais tem acesso ao Preenchimento da DNT!");
				return Action.SUCCESS;
			}
		}

		if (areasResponsavel != null && !areasResponsavel.isEmpty())
		{
			dnt = DNTManager.getUltimaDNT(getEmpresaSistema().getId());
			areas = areasResponsavel;
			areas = areaOrganizacionalManager.montaFamilia(areas);
			areas = new CollectionUtil<AreaOrganizacional>().sortCollectionStringIgnoreCase(areas, "descricao");

			if (areaFiltro == null || areaFiltro.getId() == null)
				areaFiltro = (AreaOrganizacional) areas.toArray()[0];
		}

		if (dnt == null)
		{
			setActionMsg("Não existe nenhuma DNT no sistema.");
			return Action.SUCCESS;
		}
		else
		{
			dnt = DNTManager.findById(dnt.getId());
			if (areas == null)
			{
				areas = areaOrganizacionalManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()});
				areas = new CollectionUtil<AreaOrganizacional>().sortCollectionStringIgnoreCase(areas, "descricao");
			}
			if (areaFiltro == null)
				return Action.SUCCESS;

			areaFiltro = areaOrganizacionalManager.findById(areaFiltro.getId());
		}

		//Primeira vez que um gestor vem na tela não mostra nada, ele tem que usar o filtro
		if(areaFiltro == null || areaFiltro.getId() == null || estabelecimento == null || estabelecimento.getId() == null )
			return Action.SUCCESS;

		return verificaColaboradorTurma();
	}

	private String verificaColaboradorTurma() 
	{
		if (dnt.getEmpresa().getId().equals(getEmpresaSistema().getId()) && areaFiltro.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			colaboradors = colaboradorManager.findByAreaEstabelecimento(areaFiltro.getId(), estabelecimento.getId());

			if(colaboradors == null || colaboradors.isEmpty())
				return Action.INPUT;

			colaboradorTurmas = colaboradorTurmaManager.findByDNTColaboradores(dnt, colaboradors);

			boolean temColaboradorTurma = false;
			Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
			colaboradores.addAll(colaboradors);

			for (Colaborador colab : colaboradores)
			{
				for (ColaboradorTurma ct : colaboradorTurmas)
				{
					if(colab.getId().equals(ct.getColaborador().getId()))
						temColaboradorTurma = true;
				}

				if(!temColaboradorTurma)
					colaboradors.remove(colab);

				temColaboradorTurma = false;
			}
			return Action.SUCCESS;
		}
		else
		{
			addActionError("Você não pode acessar uma DNT de outra empresa!");
			return Action.ERROR;
		}
	}

	public String delete() throws Exception
	{
		dnt = DNTManager.findById(dnt.getId());

		if (dnt.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			DNTManager.remove(dnt);
			addActionMessage("DNT excluída com sucesso!");
		}
		else
			addActionError("Não pode excluir uma DNT de outra empresa!");

		return list();
	}

	@SuppressWarnings("unused")
	public Collection<ColaboradorTurma> getColabTurmas(Long colabId)
	{
		Collection<ColaboradorTurma> result = new ArrayList<ColaboradorTurma>();

		for (ColaboradorTurma ct : colaboradorTurmas)
		{
			if (colabId.equals(ct.getColaborador().getId()))
				result.add(ct);
		}

		return result;
	}

	public Collection<DNT> getDnts()
	{
		return dnts;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public DNT getDnt()
	{
		if(dnt == null)
			dnt = new DNT();
		return dnt;
	}

	public void setDnt(DNT dnt)
	{
		this.dnt = dnt;
	}

	public void setDNTManager(DNTManager DNTManager)
	{
		this.DNTManager = DNTManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradors(Collection<Colaborador> colaboradors)
	{
		this.colaboradors = colaboradors;
	}

	public Collection<AreaOrganizacional> getAreas()
	{
		return areas;
	}

	public void setAreas(Collection<AreaOrganizacional> areas)
	{
		this.areas = areas;
	}

	public AreaOrganizacional getAreaFiltro()
	{
		return areaFiltro;
	}

	public void setAreaFiltro(AreaOrganizacional areaFiltro)
	{
		this.areaFiltro = areaFiltro;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public boolean isGestor()
	{
		return gestor;
	}

	public void setGestor(boolean gestor)
	{
		this.gestor = gestor;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}
}