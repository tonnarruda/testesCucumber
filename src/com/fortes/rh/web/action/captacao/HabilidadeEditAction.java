package com.fortes.rh.web.action.captacao;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class HabilidadeEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private HabilidadeManager habilidadeManager;
	private CompetenciaManager competenciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager = null;
	private CursoManager cursoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;

	private Habilidade habilidade;
	private Collection<Habilidade> habilidades;
	
	private String[] areasCheck;
	private Long[] cursosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	
	private AreaOrganizacional areaOrganizacional;
	private Collection<AreaOrganizacional> areaOrganizacionals;

	private void prepare() throws Exception
	{
		if(habilidade != null && habilidade.getId() != null)
			habilidade = (Habilidade) habilidadeManager.findByIdProjection(habilidade.getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		cursosCheckList = cursoManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, habilidade.getAreaOrganizacionals(), "getId");
		cursosCheckList = CheckListBoxUtil.marcaCheckListBox(cursosCheckList, habilidade.getCursos(), "getId");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if (competenciaManager.existeNome(habilidade.getNome(), null, null, getEmpresaSistema().getId()))
		{
			addActionMessage("Já existe um Conhecimento, Habilidade ou Atitude com o nome \"" + habilidade.getNome() +"\".");
			prepareInsert();
			return Action.INPUT;
		}
		
		habilidade.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		habilidade.setCursos(cursoManager.populaCursos(cursosCheck));
		habilidade.setEmpresa(getEmpresaSistema());
		habilidadeManager.save(habilidade);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (competenciaManager.existeNome(habilidade.getNome(), habilidade.getId(), Competencia.HABILIDADE, getEmpresaSistema().getId()))
		{
			addActionMessage("Já existe um Conhecimento, Habilidade ou Atitude com o nome \"" + habilidade.getNome() +"\".");
			prepareUpdate();
			return Action.INPUT;
		}
		
		habilidade.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		habilidade.setCursos(cursoManager.populaCursos(cursosCheck));
		habilidade.setEmpresa(getEmpresaSistema());
		habilidadeManager.update(habilidade);
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome","observacao"};
		String[] sets = new String[]{"id","nome","observacao"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(habilidadeManager.getCount(keys, values));
		habilidades = habilidadeManager.findToList(properties, sets, keys, values,getPage(), getPagingSize(), orders);

		return Action.SUCCESS;
	}
	
	public String delete() throws Exception
	{
		if(!configuracaoNivelCompetenciaManager.existeConfiguracaoNivelCompetencia(habilidade.getId(), TipoCompetencia.HABILIDADE)){
			habilidadeManager.remove(habilidade.getId());
			addActionMessage("Habilidade excluída com sucesso.");
			return Action.SUCCESS;
		}
		else{
			addActionWarning("Não é possível excluir esta habilidade pois possui dependência com o nível de competencia do cargo/faixa salarial.");
			return Action.INPUT;
		}
	}
	
	public Habilidade getHabilidade()
	{
		if(habilidade == null)
			habilidade = new Habilidade();
		return habilidade;
	}

	public void setHabilidade(Habilidade habilidade)
	{
		this.habilidade = habilidade;
	}

	public void setHabilidadeManager(HabilidadeManager habilidadeManager)
	{
		this.habilidadeManager = habilidadeManager;
	}
	
	public Collection<Habilidade> getHabilidades()
	{
		return habilidades;
	}

	public AreaOrganizacionalManager getAreaOrganizacionalManager()
	{
		return areaOrganizacionalManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public void setCompetenciaManager(CompetenciaManager competenciaManager)
	{
		this.competenciaManager = competenciaManager;
	}

	public void setCursosCheck(Long[] cursosCheck)
	{
		this.cursosCheck = cursosCheck;
	}

	public void setCursosCheckList(Collection<CheckBox> cursosCheckList)
	{
		this.cursosCheckList = cursosCheckList;
	}
	
	public Collection<CheckBox> getCursosCheckList()
	{
		return cursosCheckList;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
	
}