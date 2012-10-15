package com.fortes.rh.business.desenvolvimento;

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
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;

public class CursoManagerImpl extends GenericManagerImpl<Curso, CursoDao> implements CursoManager
{
	private PlatformTransactionManager transactionManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private ColaboradorManager colaboradorManager;

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

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

	public IndicadorTreinamento montaIndicadoresTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds)
	{
		IndicadorTreinamento indicadorTreinamento = findIndicadorHorasTreinamentos(dataIni, dataFim, empresaIds);
		indicadorTreinamento.setDataIni(dataIni);
		indicadorTreinamento.setDataFim(dataFim);
		
		Double somaHoras = indicadorTreinamento.getSomaHoras();
		Double qtdHoras = (indicadorTreinamento.getSomaHoras() != null ? indicadorTreinamento.getSomaHoras() : 0);
		Double custoPerCapita = 0d;
		Double horasPerCapita = 0d;
		Double percentualFrequencia = 0d;
		Double percentualInvestimento = 0d;
		
		Double somaCustos = getDao().somaCustosTreinamentos(dataIni, dataFim, empresaIds);
		somaCustos = somaCustos == null ? 0 : somaCustos;
		indicadorTreinamento.setSomaCustos(somaCustos);
		
		
		if (somaHoras != null && somaHoras > 0)
			indicadorTreinamento.setCustoMedioHora( somaCustos / somaHoras );
		
		Integer qtdInscritos = getDao().findQtdColaboradoresInscritosTreinamentos(dataIni, dataFim, empresaIds);

		if (qtdInscritos != null && qtdInscritos > 0)
			custoPerCapita = (somaCustos / qtdInscritos);

		indicadorTreinamento.setCustoPerCapita(custoPerCapita);
		indicadorTreinamento.setQtdColaboradoresInscritos(qtdInscritos);

		Integer qtdAtivos = colaboradorManager.getCountAtivos(dataIni, dataFim, empresaIds);

		if (qtdAtivos != null && qtdAtivos > 0)
			horasPerCapita = qtdHoras / qtdAtivos;

		indicadorTreinamento.setHorasPerCapita(horasPerCapita);
		
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBean("colaboradorTurmaManager");
		percentualFrequencia = colaboradorTurmaManager.percentualFrequencia(dataIni, dataFim, empresaIds);
		indicadorTreinamento.setPercentualFrequencia(percentualFrequencia);
		
		TurmaManager turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		percentualInvestimento = turmaManager.getPercentualInvestimento(dataIni, dataFim, empresaIds);
		indicadorTreinamento.setPercentualInvestimento(percentualInvestimento);
		
		return indicadorTreinamento;
	}
	
	public IndicadorTreinamento findIndicadorHorasTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds)
	{
		double somaHoras = 0.0;
		
		Collection<IndicadorTreinamento> indicadoresPorCurso = getDao().findIndicadorHorasTreinamentos(dataIni, dataFim, empresaIds);
		
		for (IndicadorTreinamento indicadorTreinamento : indicadoresPorCurso) {
			somaHoras += indicadorTreinamento.getSomaHoras();
		}
		
		return new IndicadorTreinamento(null, somaHoras);
	}
	
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds)
	{
		return getDao().findQtdColaboradoresInscritosTreinamentos(dataIni, dataFim, empresaIds);
	}

	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId)
	{
		Integer resultado = getDao().findSomaColaboradoresPrevistosTreinamentos(dataIni, dataFim, empresaId);
		return (resultado != null ? resultado : new Integer(0));
	}

	public Integer countTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Boolean realizado)
	{
		return getDao().countTreinamentos(dataIni, dataFim, empresaIds, realizado);
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
		
		if(avaliacaoCursoIds == null || avaliacaoCursoIds.length == 0)
			avaliacaoCursoIds = null;
		
		try
		{
			aproveitamentoAvaliacaoCursoManager.remove(curso.getId(), avaliacaoCursoIds);

			CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>();
			curso.setAvaliacaoCursos(collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, avaliacaoCursoIds));
			curso.setEmpresa(empresa);
			update(curso);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw e;
		}

	}
	
	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId)
	{
		return getDao().findByFiltro(page, pagingSize, curso, empresaId);
	}

	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
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

	public String somaCargaHoraria(Collection<Turma> turmas) {
		
		Integer totalCargaHoraria = 0;
		
		for (Turma turma : turmas) {
			totalCargaHoraria += (turma.getCurso().getCargaHoraria() == null ? 0 : turma.getCurso().getCargaHoraria());
		}
		
		return Curso.formataCargaHorariaMinutos(totalCargaHoraria, "");
	}
}