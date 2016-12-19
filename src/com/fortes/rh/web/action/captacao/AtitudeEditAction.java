package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AtitudeEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private CursoManager cursoManager;
	@Autowired private AtitudeManager atitudeManager;
	@Autowired private CompetenciaManager competenciaManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	@Autowired private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	
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

	public void montaAtitude() {
		atitude.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		atitude.setCursos(cursoManager.populaCursos(cursosCheck));
		atitude.setEmpresa(getEmpresaSistema());
		
		Collection<CriterioAvaliacaoCompetencia> criterios = new ArrayList<CriterioAvaliacaoCompetencia>();
		for (CriterioAvaliacaoCompetencia criterio : atitude.getCriteriosAvaliacaoCompetencia()) {
			if (!criterio.getDescricao().equals("")) {
				criterio.setAtitude(atitude);
				criterios.add(criterio);
			}
		}
		
		if ( atitude.getId() != null )
			criterioAvaliacaoCompetenciaManager.removeByCompetencia(atitude.getId(), Competencia.ATITUDE, atitude.getCriteriosAvaliacaoCompetencia());
		
		atitude.setCriteriosAvaliacaoCompetencia(criterios);
	}
	
	public String insert() throws Exception
	{
		if (competenciaManager.existeNome(atitude.getNome(), null, null, getEmpresaSistema().getId()))
		{
			addActionWarning("Já existe um conhecimento, habilidade ou atitude com o nome \"" + atitude.getNome() +"\".");
			prepareInsert();
			return Action.INPUT;
		}
		
		montaAtitude();
		atitudeManager.save(atitude);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (competenciaManager.existeNome(atitude.getNome(), atitude.getId(), Competencia.ATITUDE, getEmpresaSistema().getId()))
		{
			addActionWarning("Já existe um conhecimento, habilidade ou atitude com o nome \"" + atitude.getNome() +"\".");
			prepareInsert();
			return Action.INPUT;
		}
		
		montaAtitude();
		atitudeManager.update(atitude);
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[] { "id", "nome", "observacao"};
		String[] sets = new String[] { "id", "nome", "observacao"};
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
			try {
				criterioAvaliacaoCompetenciaManager.removeByCompetencia(atitude.getId(), TipoCompetencia.ATITUDE, null);
				atitudeManager.remove(atitude.getId());
				
				addActionSuccess("Atitude excluída com sucesso.");
				return Action.SUCCESS;
			} catch (Exception e) {
				addActionMessage("Não foi possível excluir a atitude.");
				return Action.INPUT;
			}
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

	public Collection<Atitude> getAtitudes()
	{
		return atitudes;
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
}