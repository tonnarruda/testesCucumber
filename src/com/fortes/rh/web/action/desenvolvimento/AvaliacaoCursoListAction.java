package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AvaliacaoCursoListAction extends MyActionSupportList
{
	@Autowired private AvaliacaoCursoManager avaliacaoCursoManager;
	@Autowired private CursoManager cursoManager;
	@Autowired private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	private Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
	private AvaliacaoCurso avaliacaoCurso;
	private Map<Character, String> tipos = new TipoAvaliacaoCurso();
	
	private Collection<Curso> cursos;
	private Collection<Turma> turmas;
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios;
	
	private Long[] cursosCheck;
	private Long[] turmasCheck;
	private Long[] avaliacaoCursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> turmasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> avaliacaoCursosCheckList = new ArrayList<CheckBox>();
	
	private Map<String,Object> parametros = new HashMap<String, Object>();
	
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
		try{
			colaboradorQuestionarios = colaboradorQuestionarioManager.findForRankingPerformanceAvaliacaoCurso(cursosCheck, turmasCheck, avaliacaoCursosCheck);

			if(colaboradorQuestionarios.isEmpty())
				throw new FortesException("Não existe dados para o filtro selecionado.");
			
			parametros = RelatorioUtil.getParametrosRelatorio("Ranking das Avaliações dos Alunos", getEmpresaSistema(), "");
		}
		catch (Exception e)
		{
			if (e instanceof FortesException)
				addActionMessage(e.getMessage());
			else
				addActionError(e.getMessage());
			
			e.printStackTrace();
			prepareRelatorioRankingAvaliacaoAluno();
			return Action.INPUT;
		}
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

	public Map<Character, String> getTipos()
	{
		return tipos;
	}

	public Collection<Curso> getCursos() {
		return cursos;
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

	public Long[] getCursosCheck() {
		return cursosCheck;
	}

	public void setCursosCheck(Long[] cursosCheck) {
		this.cursosCheck = cursosCheck;
	}

	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public Long[] getTurmasCheck() {
		return turmasCheck;
	}

	public void setTurmasCheck(Long[] turmasCheck) {
		this.turmasCheck = turmasCheck;
	}

	public Long[] getAvaliacaoCursosCheck() {
		return avaliacaoCursosCheck;
	}

	public void setAvaliacaoCursosCheck(Long[] avaliacaoCursosCheck) {
		this.avaliacaoCursosCheck = avaliacaoCursosCheck;
	}

	public Collection<CheckBox> getTurmasCheckList() {
		return turmasCheckList;
	}

	public Collection<CheckBox> getAvaliacaoCursosCheckList() {
		return avaliacaoCursosCheckList;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}
}