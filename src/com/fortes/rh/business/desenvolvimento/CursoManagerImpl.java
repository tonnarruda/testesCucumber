package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class CursoManagerImpl extends GenericManagerImpl<Curso, CursoDao> implements CursoManager
{
	private PlatformTransactionManager transactionManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private ColaboradorManager colaboradorManager;

	public Curso findByIdProjection(Long cursoId)
	{
		return getDao().findByIdProjection(cursoId);
	}
	
	public Collection<Curso> findByIdProjection(Long[] cursoIds)
	{
		return getDao().findByIdProjection(cursoIds);
	}

	public Collection<Curso> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Curso> findCursosSemTurma(Long empresaId)
	{
		return getDao().findCursosSemTurma(empresaId);
	}

	public String getConteudoProgramatico(Long id)
	{
		return getDao().getConteudoProgramatico(id);
	}

	public Collection<Curso> findByCertificacao(Long certificacaoId)
	{
		return getDao().findByCertificacao(certificacaoId);
	}

	public Collection<Curso> findAllByEmpresasParticipantes(Long... empresasIds) 
	{
		return getDao().findAllByEmpresasParticipantes(empresasIds);
	}
	
	public IndicadorTreinamento montaIndicadoresTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds)
	{
		IndicadorTreinamento indicadorTreinamento = findIndicadorHorasTreinamentos(dataIni, dataFim, empresaIds, cursoIds);
		indicadorTreinamento.setDataIni(dataIni);
		indicadorTreinamento.setDataFim(dataFim);
		
		Double somaHoras = indicadorTreinamento.getSomaHoras();
		Double qtdHoras = (indicadorTreinamento.getSomaHoras() != null ? indicadorTreinamento.getSomaHoras() : 0);
		Double custoPerCapita = 0d;
		Double horasPerCapita = 0d;
		Double percentualFrequencia = 0d;
		Double percentualInvestimento = 0d;
		
		Double somaCustos = getDao().somaCustosTreinamentos(dataIni, dataFim, empresaIds, cursoIds);
		somaCustos = somaCustos == null ? 0 : somaCustos;
		indicadorTreinamento.setSomaCustos(somaCustos);
		
		if (somaHoras != null && somaHoras > 0)
			indicadorTreinamento.setCustoMedioHora( somaCustos / somaHoras );
		
		Integer qtdInscritos = getDao().findQtdColaboradoresInscritosTreinamentos(dataIni, dataFim, empresaIds, cursoIds);

		if (qtdInscritos != null && qtdInscritos > 0)
			custoPerCapita = (somaCustos / qtdInscritos);

		indicadorTreinamento.setCustoPerCapita(custoPerCapita);
		indicadorTreinamento.setQtdColaboradoresInscritos(qtdInscritos);

		Integer qtdAtivos = colaboradorManager.getCountAtivos(dataIni, dataFim, empresaIds);

		if (qtdAtivos != null && qtdAtivos > 0)
			horasPerCapita = qtdHoras / qtdAtivos;

		indicadorTreinamento.setHorasPerCapita(horasPerCapita);
		
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBean("colaboradorTurmaManager");
		percentualFrequencia = colaboradorTurmaManager.percentualFrequencia(dataIni, dataFim, empresaIds, cursoIds);
		indicadorTreinamento.setPercentualFrequencia(percentualFrequencia);
		
		TurmaManager turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		percentualInvestimento = turmaManager.getPercentualInvestimento(dataIni, dataFim, empresaIds);
		indicadorTreinamento.setPercentualInvestimento(percentualInvestimento);
		
		return indicadorTreinamento;
	}
	
	public IndicadorTreinamento findIndicadorHorasTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds)
	{
		double somaHoras = 0.0;
		
		Collection<IndicadorTreinamento> indicadoresPorCurso = getDao().findIndicadorHorasTreinamentos(dataIni, dataFim, empresaIds, cursoIds);
		
		for (IndicadorTreinamento indicadorTreinamento : indicadoresPorCurso) {
			somaHoras += indicadorTreinamento.getSomaHoras();
		}
		
		return new IndicadorTreinamento(null, somaHoras);
	}
	
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursosIds)
	{
		return getDao().findQtdColaboradoresInscritosTreinamentos(dataIni, dataFim, empresaIds, cursosIds);
	}

	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId)
	{
		Integer resultado = getDao().findSomaColaboradoresPrevistosTreinamentos(dataIni, dataFim, empresaId);
		return (resultado != null ? resultado : new Integer(0));
	}

	public Integer countTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Boolean realizado)
	{
		return getDao().countTreinamentos(dataIni, dataFim, empresaIds, cursoIds, realizado);
	}

	public Collection<Long> findComAvaliacao(Long empresaId, Date dataIni, Date dataFim)
	{
		return getDao().findComAvaliacao(empresaId, dataIni, dataFim);
	}

	public void update(Curso curso, Empresa empresa, String[] avaliacaoCursoIds) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		if (avaliacaoCursoIds == null || avaliacaoCursoIds.length == 0)
			avaliacaoCursoIds = null;
		
		try {
			aproveitamentoAvaliacaoCursoManager.remove(curso.getId(), avaliacaoCursoIds);
			CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>();
			curso.setAvaliacaoCursos(collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, avaliacaoCursoIds));
			update(curso);
			transactionManager.commit(status);
		
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}
	}

	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId)
	{
		return getDao().findByFiltro(page, pagingSize, curso, empresaId);
	}

	public Integer getCount(Curso curso, Long empresaId)
	{
		return getDao().getCount(curso, empresaId);
	}

	public Curso saveClone(Curso curso, Long empresaId)
	{
		curso.setId(null);
		curso.setEmpresaId(empresaId);
		return (Curso) getDao().save(curso);
	}

	public String somaCargaHoraria(Collection<Turma> turmas)
	{
		Integer totalCargaHoraria = 0;
		for (Turma turma : turmas) {
			totalCargaHoraria += (turma.getCurso().getCargaHoraria() == null ? 0 : turma.getCurso().getCargaHoraria());
		}
		return Curso.formataCargaHorariaMinutos(totalCargaHoraria, "");
	}

	public Collection<CheckBox> populaCheckOrderDescricao(Long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Curso> cursos = findToList(new String[]{"id", "nome"},new String[]{"id", "nome"},new String[]{"empresa.id"},new Object[]{empresaId},new String[]{"nome"});
			CollectionUtil<Curso> cu1 = new CollectionUtil<Curso>();
			cursos = cu1.sortCollectionStringIgnoreCase(cursos, "nome");

			checks = CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}


	public Collection<Curso> populaCursos(Long[] cursosCheckIds)
	{
		Collection<Curso> cursos = new ArrayList<Curso>();

		if(cursosCheckIds != null && cursosCheckIds.length > 0)
		{
			Curso curso;
			for (Long cursoId: cursosCheckIds)
			{
				curso = new Curso();
				curso.setId(cursoId);

				cursos.add(curso);
			}
		}

		return cursos;
	}
	
	public Collection<Empresa> findAllEmpresasParticipantes(Long cursoId) 
	{
		Collection<Empresa> empresas = getDao().findEmpresasParticipantes(cursoId);
		empresas.add(getDao().findEmpresaByCurso(cursoId));
		
		return empresas;
	}
	
	public boolean existeEmpresasNoCurso(Long empresaId, Long cursoId) 
	{
		return getDao().existeEmpresasNoCurso(empresaId, cursoId);
	}

	public Collection<Curso> findByCompetencia(Long conhecimentoId, Character tipoCompetencia)
	{
		return getDao().findByCompetencia(conhecimentoId, tipoCompetencia);
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
}