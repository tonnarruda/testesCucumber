package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AvaliacaoCursoListAction extends MyActionSupportList
{
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private CursoManager cursoManager;

	private Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
	private AvaliacaoCurso avaliacaoCurso;
	private Map<Character, String> tipos = new TipoAvaliacaoCurso();
	
	private Collection<Curso> cursos;
	private Collection<Turma> turmas;
	
	private String[] cursosCheck;
	private String[] turmasCheck;
	private String[] avaliacaoCursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> turmasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> avaliacaoCursosCheckList = new ArrayList<CheckBox>();
	
	private Date periodoIni;
	private Date periodoFim;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String titulo = avaliacaoCurso != null ? avaliacaoCurso.getTitulo() : null;
		avaliacaoCursos = avaliacaoCursoManager.buscaFiltro(titulo);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoCursoManager.remove(avaliacaoCurso.getId());
		addActionSuccess("Avaliação excluída com sucesso.");

		return Action.SUCCESS;
	}
	
	public String prepareRelatorioRankingAvaliacaoAluno()
	{
		cursosCheckList = cursoManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}
	
	public String relatorioRankingAvaliacaoAluno()
	{
		return Action.SUCCESS;
	}

	public Collection<AvaliacaoCurso> getAvaliacaoCursos()
	{
		return avaliacaoCursos;
	}

	public AvaliacaoCurso getAvaliacaoCurso()
	{
		if (avaliacaoCurso == null)
		{
			avaliacaoCurso = new AvaliacaoCurso();
		}
		return avaliacaoCurso;
	}

	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso)
	{
		this.avaliacaoCurso = avaliacaoCurso;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public Map<Character, String> getTipos()
	{
		return tipos;
	}

	public Date getPeriodoIni() {
		return periodoIni;
	}

	public void setPeriodoIni(Date periodoIni) {
		this.periodoIni = periodoIni;
	}

	public Date getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(Date periodoFim) {
		this.periodoFim = periodoFim;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}

	public Collection<Curso> getCursos() {
		return cursos;
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

	public String[] getCursosCheck() {
		return cursosCheck;
	}

	public void setCursosCheck(String[] cursosCheck) {
		this.cursosCheck = cursosCheck;
	}

	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public String[] getTurmasCheck() {
		return turmasCheck;
	}

	public void setTurmasCheck(String[] turmasCheck) {
		this.turmasCheck = turmasCheck;
	}

	public String[] getAvaliacaoCursosCheck() {
		return avaliacaoCursosCheck;
	}

	public void setAvaliacaoCursosCheck(String[] avaliacaoCursosCheck) {
		this.avaliacaoCursosCheck = avaliacaoCursosCheck;
	}

	public Collection<CheckBox> getTurmasCheckList() {
		return turmasCheckList;
	}

	public Collection<CheckBox> getAvaliacaoCursosCheckList() {
		return avaliacaoCursosCheckList;
	}
}