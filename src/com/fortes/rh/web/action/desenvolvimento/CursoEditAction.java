package com.fortes.rh.web.action.desenvolvimento;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class CursoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private CursoManager cursoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private CursoLntManager cursoLntManager;

	private Curso curso;
	private boolean codigoTRUCurso;
	private boolean compartilharCursos;
	private boolean cursoCompartilhado;
	private String nomeCursoBusca;
	private int page;
	private Long cursoLntId;
	private Long lntId;
	
	private String[] avaliacaoCursoCheck;
	private Collection<CheckBox> avaliacaoCursoCheckList = new ArrayList<CheckBox>();
	private Long[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private boolean avaliacaoAlunoRespondida;
	private boolean existeFrequencia;
	private boolean existeTurmaRealizada;

	private Collection<Empresa> empresas;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(curso != null && curso.getId() != null){
			curso = (Curso) cursoManager.findById(curso.getId());
		} else if(cursoLntId != null) {
			CursoLnt cursoLnt = cursoLntManager.findById(cursoLntId);
			curso.setNome(cursoLnt.getNomeNovoCurso());
			curso.setCargaHoraria(cursoLnt.getCargaHoraria());
		}
			
		codigoTRUCurso = getEmpresaSistema().isCodigoTruCurso();
		Collection<AvaliacaoCurso> avaliacoes = avaliacaoCursoManager.findAll(new String[]{"titulo"});
		avaliacaoCursoCheckList = CheckListBoxUtil.populaCheckListBox(avaliacoes, "getId", "getTitulo", null);
		
		empresas = empresaManager.findEmpresasPermitidas(true, getEmpresaSistema().getId(), getUsuarioLogado().getId());
		empresas.remove(getEmpresaSistema());
		empresasCheckList = CheckListBoxUtil.populaCheckListBox(empresas, "getId","getNome", null);
		
		compartilharCursos = parametrosDoSistemaManager.findById(1L).getCompartilharCursos();
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		avaliacaoCursoCheckList = CheckListBoxUtil.marcaCheckListBox(avaliacaoCursoCheckList, curso.getAvaliacaoCursos(), "getId");
		empresasCheckList = CheckListBoxUtil.marcaCheckListBox(empresasCheckList, curso.getEmpresasParticipantes(), "getId");
		
		avaliacaoAlunoRespondida = cursoManager.existeAvaliacaoAlunoDeTipoNotaOuPorcentagemRespondida(curso.getId()) || cursoManager.existeAvaliacaoAlunoDeTipoAvaliacaoRespondida(curso.getId()) ;
		existeFrequencia = cursoManager.existePresenca(curso.getId());
		existeTurmaRealizada =  cursoManager.existeTurmaRealizada(curso.getId());
				
		if (curso != null && !cursoManager.existeEmpresasNoCurso(getEmpresaSistema().getId(), curso.getId())){
			addActionWarning("O curso solicitado não existe ou não esta compartilhado para a empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		} else if(!curso.getEmpresa().equals(getEmpresaSistema())){
			cursoCompartilhado = true;
			addActionMessage("Este curso foi compartilhado pela empresa " + curso.getEmpresa().getNome() +".");
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>();
		curso.setAvaliacaoCursos(collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, avaliacaoCursoCheck));

		Collection<Empresa> empresasParticipantes = new CollectionUtil<Empresa>().convertArrayLongToCollection(Empresa.class, empresasCheck);
		
		curso.setEmpresasParticipantes(empresasParticipantes);
		curso.setEmpresa(getEmpresaSistema());
		if (curso.getPeriodicidade() == null) {
			curso.setPeriodicidade(0);
		}
		cursoManager.save(curso);
		
		if(cursoLntId != null) {
			CursoLnt cursoLnt = cursoLntManager.findById(cursoLntId);
			cursoLnt.setCurso(curso);
			cursoLnt.setNomeNovoCurso(curso.getNome());
			cursoLntManager.update(cursoLnt);
			
			lntId = cursoLnt.getLnt().getId();
			return "success_lnt";
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception{
		try{
			Collection<Empresa> empresasParticipantes = new CollectionUtil<Empresa>().convertArrayLongToCollection(Empresa.class, empresasCheck);
			
			if(!curso.getEmpresa().equals(getEmpresaSistema())){
				empresasParticipantes.add(getEmpresaSistema());
			}
			curso.setEmpresasParticipantes(empresasParticipantes);
		
			if (curso.getPeriodicidade() == null) {
				curso.setPeriodicidade(0);
			}
			cursoManager.update(curso, getEmpresaSistema(), avaliacaoCursoCheck);
		} catch (Exception e) {
			addActionError("Erro ao editar curso");
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}


	public Object getModel()
	{
		return getCurso();
	}

	public Curso getCurso()
	{
		if(curso == null)
			curso = new Curso();
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public Collection<CheckBox> getAvaliacaoCursoCheckList()
	{
		return avaliacaoCursoCheckList;
	}

	public void setAvaliacaoCursoCheck(String[] avaliacaoCursoCheck)
	{
		this.avaliacaoCursoCheck = avaliacaoCursoCheck;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public String getNomeCursoBusca()
	{
		return nomeCursoBusca;
	}

	public void setNomeCursoBusca(String nomeCursoBusca)
	{
		this.nomeCursoBusca = nomeCursoBusca;
	}

	public boolean isCodigoTRUCurso() {
		return codigoTRUCurso;
	}

	public void setCodigoTRUCurso(boolean codigoTRUCurso) {
		this.codigoTRUCurso = codigoTRUCurso;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheck(Long[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public boolean isCompartilharCursos() {
		return compartilharCursos;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
	
	public boolean isCursoCompartilhado()
	{
		return cursoCompartilhado;
	}
	
	public boolean isAvaliacaoAlunoRespondida()
	{
		return avaliacaoAlunoRespondida;
	}

	public boolean isExisteFrequencia() {
		return existeFrequencia;
	}

	public Long getCursoLntId() {
		return cursoLntId;
	}

	public void setCursoLntId(Long cursoLntId) {
		this.cursoLntId = cursoLntId;
	}

	public void setCursoLntManager(CursoLntManager cursoLntManager) {
		this.cursoLntManager = cursoLntManager;
	}

	public Long getLntId() {
		return lntId;
	}

	public void setLntId(Long lntId) {
		this.lntId = lntId;
	}
	
	public boolean isExisteTurmaRealizada() {
		return existeTurmaRealizada;
	}

	public void setExisteTurmaRealizada(boolean existeTurmaRealizada) {
		this.existeTurmaRealizada = existeTurmaRealizada;
	}
}