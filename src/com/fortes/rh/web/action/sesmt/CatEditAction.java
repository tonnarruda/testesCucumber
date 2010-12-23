package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;

public class CatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private CatManager catManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;

	private Colaborador colaborador;
	private Cat cat;
	private Collection<Cat> cats = null;

	private Date inicio;
	private Date fim;
	private String nomeBusca;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<Colaborador> colaboradors;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	public String list() throws Exception
	{
		if (!validaPeriodo())
			return SUCCESS;

		cats = catManager.findAllSelect(getEmpresaSistema().getId(), inicio, fim, estabelecimentosCheck, nomeBusca);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

		return SUCCESS;
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
		catManager.remove(cat.getId());

		return SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(cat != null && cat.getId() != null)
			cat = (Cat) catManager.findById(cat.getId());

	}

	public String prepareInsert() throws Exception
	{
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
		catManager.save(cat);
		return SUCCESS;
	}

	public String update() throws Exception
	{
		catManager.update(cat);
		return SUCCESS;
	}

	public String filtrarColaboradores() throws Exception
	{
		colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), false);

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
			parametros = RelatorioUtil.getParametrosRelatorio("CAT's", getEmpresaSistema(), getPeriodoFormatado());
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioCats();
			return INPUT;
		}
		
		return SUCCESS;
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
}