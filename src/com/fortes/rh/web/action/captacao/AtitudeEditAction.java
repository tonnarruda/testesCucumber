package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AtitudeEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager = null;
	private CompetenciaManager competenciaManager;
	private AtitudeManager atitudeManager;
	private CursoManager cursoManager;
	
	private Atitude atitude;
	private Collection<Atitude> atitudes;
	
	private String[] areasCheck;
	private Long[] cursosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	
	private AreaOrganizacional areaOrganizacional;
	private Collection<AreaOrganizacional> areaOrganizacionals;


	private void prepare() throws Exception
	{
		if(atitude != null && atitude.getId() != null)
			atitude = (Atitude) atitudeManager.findByIdProjection(atitude.getId());
	
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
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, atitude.getAreaOrganizacionals(), "getId");
		cursosCheckList = CheckListBoxUtil.marcaCheckListBox(cursosCheckList, atitude.getCursos(), "getId");

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if (competenciaManager.existeNome(atitude.getNome(), null, null, getEmpresaSistema().getId()))
		{
			addActionMessage("Já existe um Conhecimento, Habilidade ou Atitude com o nome \"" + atitude.getNome() +"\".");
			prepareInsert();
			return Action.INPUT;
		}
		
		atitude.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		atitude.setCursos(cursoManager.populaCursos(cursosCheck));
		atitude.setEmpresa(getEmpresaSistema());
		atitudeManager.save(atitude);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (competenciaManager.existeNome(atitude.getNome(), atitude.getId(), Competencia.ATITUDE, getEmpresaSistema().getId()))
		{
			addActionMessage("Já existe um Conhecimento, Habilidade ou Atitude com o nome \"" + atitude.getNome() +"\".");
			prepareInsert();
			return Action.INPUT;
		}
		
		atitude.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		atitude.setCursos(cursoManager.populaCursos(cursosCheck));
		atitude.setEmpresa(getEmpresaSistema());
		atitudeManager.update(atitude);
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[] { "id", "nome", "observacao" };
		String[] sets = new String[] { "id", "nome", "observacao" };
		String[] keys = new String[] { "empresa.id" };
		Object[] values = new Object[] { getEmpresaSistema().getId() };
		String[] orders = new String[] { "nome" };
		
		setTotalSize(atitudeManager.getCount(keys, values));
		atitudes = atitudeManager.findToList(properties, sets, keys, values, getPage(), getPagingSize(), orders);
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if(!configuracaoNivelCompetenciaManager.existeConfiguracaoNivelCompetencia(atitude.getId(), TipoCompetencia.ATITUDE)){
			atitudeManager.remove(atitude.getId());
			addActionMessage("Atitude excluída com sucesso.");
			return Action.SUCCESS;
		}
		else{
			addActionWarning("Não é possível excluir esta atitude pois possui dependência com o nível de competencia do cargo/faixa salarial.");
			return Action.INPUT;
		}
	}

	public Atitude getAtitude()
	{
		if (atitude == null)
			atitude = new Atitude();
		return atitude;
	}

	public void setAtitude(Atitude atitude)
	{
		this.atitude = atitude;
	}

	public void setAtitudeManager(AtitudeManager atitudeManager)
	{
		this.atitudeManager = atitudeManager;
	}

	public Collection<Atitude> getAtitudes()
	{
		return atitudes;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
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

	public Collection<CheckBox> getCursosCheckList()
	{
		return cursosCheckList;
	}

	public void setCursosCheckList(Collection<CheckBox> cursosCheckList)
	{
		this.cursosCheckList = cursosCheckList;
	}

	public void setCursosCheck(Long[] cursosCheck)
	{
		this.cursosCheck = cursosCheck;
	}
	
	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
}
