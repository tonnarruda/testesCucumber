/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class ConhecimentoEditAction extends MyActionSupportEdit implements ModelDriven
{
	//	managers
	private ConhecimentoManager conhecimentoManager = null;
	private AreaOrganizacionalManager areaOrganizacionalManager = null;
	private CompetenciaManager competenciaManager = null;
	private CursoManager cursoManager;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;

	private String[] areasCheck;
	private Long[] cursosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();

	//	POJOs
	private Conhecimento conhecimento;
	private AreaOrganizacional areaOrganizacional;
	private Collection<AreaOrganizacional> areaOrganizacionals;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(conhecimento != null && conhecimento.getId() != null)
			conhecimento = conhecimentoManager.findByIdProjection(conhecimento.getId());

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
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, conhecimento.getAreaOrganizacionals(), "getId");
		cursosCheckList = CheckListBoxUtil.marcaCheckListBox(cursosCheckList, conhecimento.getCursos(), "getId");
		
		return Action.SUCCESS;
	}
	
	public void montaConhecimento() {
		conhecimento.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		conhecimento.setCursos(cursoManager.populaCursos(cursosCheck));
		conhecimento.setEmpresa(getEmpresaSistema());
		
		Collection<CriterioAvaliacaoCompetencia> criterios = new ArrayList<CriterioAvaliacaoCompetencia>();
		for (CriterioAvaliacaoCompetencia criterio : conhecimento.getCriteriosAvaliacaoCompetencia()) {
			if (!criterio.getDescricao().equals("")) {
				criterio.setConhecimento(conhecimento);
				criterios.add(criterio);
			}
		}
		
		conhecimento.setCriteriosAvaliacaoCompetencia(criterios);
	}
	
	public String insert() throws Exception
	{
		if (competenciaManager.existeNome(conhecimento.getNome(), null, null, getEmpresaSistema().getId()))
		{
			addActionMessage("Já existe um Conhecimento, Habilidade ou Atitude com o nome \"" + conhecimento.getNome() +"\".");
			prepareInsert();
			return Action.INPUT;
		}
		
		montaConhecimento();
		conhecimentoManager.save(conhecimento);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (competenciaManager.existeNome(conhecimento.getNome(), conhecimento.getId(), Competencia.CONHECIMENTO, getEmpresaSistema().getId()))
		{
			addActionMessage("Já existe um Conhecimento, Habilidade ou Atitude com o nome \"" + conhecimento.getNome() +"\".");
			prepareUpdate();
			return Action.INPUT;
		}
		
		criterioAvaliacaoCompetenciaManager.removeByCompetencia(conhecimento.getId(), Competencia.CONHECIMENTO, conhecimento.getCriteriosAvaliacaoCompetencia());
		
		montaConhecimento();
		conhecimentoManager.update(conhecimento);
		
		return Action.SUCCESS;
	}

	public Conhecimento getConhecimento()
	{
		if(conhecimento == null)
			conhecimento = new Conhecimento();
		return conhecimento;
	}

	public void setConhecimento(Conhecimento conhecimento)
	{
		this.conhecimento=conhecimento;
	}

	public Object getModel()
	{
		return getConhecimento();
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager=conhecimentoManager;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public AreaOrganizacionalManager getAreaOrganizacionalManager()
	{
		return areaOrganizacionalManager;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(
			Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public ConhecimentoManager getConhecimentoManager()
	{
		return conhecimentoManager;
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

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public void setCursosCheck(Long[] cursosCheck)
	{
		this.cursosCheck = cursosCheck;
	}

	public void setCriterioAvaliacaoCompetenciaManager(
			CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager) {
		this.criterioAvaliacaoCompetenciaManager = criterioAvaliacaoCompetenciaManager;
	}
}