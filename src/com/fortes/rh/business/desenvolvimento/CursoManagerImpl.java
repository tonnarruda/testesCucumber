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
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class CursoManagerImpl extends GenericManagerImpl<Curso, CursoDao> implements CursoManager
{
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private PlatformTransactionManager transactionManager;
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
	
	public IndicadorTreinamento montaIndicadoresTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds, Long[] estabelecimentosIds, boolean considerarDiaTurmaCompreendidoNoPeriodo)
	{
		IndicadorTreinamento indicadorTreinamento;
		if(considerarDiaTurmaCompreendidoNoPeriodo){
			indicadorTreinamento = getDao().findIndicadorTreinamentoCustos(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cursoIds);
			indicadorTreinamento.setSomaHorasRatiada(getDao().findQtdHorasRatiada(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cursoIds));
			indicadorTreinamento.setTotalHorasTreinamento(getDao().findCargaHorariaTreinamentoRatiada(cursoIds, empresaIds, estabelecimentosIds, areasIds, dataIni, dataFim, true));
		}
		else{
			indicadorTreinamento = getDao().findIndicadorHorasTreinamentos(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cursoIds);
			indicadorTreinamento.setTotalHorasTreinamento(getDao().findCargaHorariaTotalTreinamento(cursoIds, empresaIds, estabelecimentosIds, areasIds, dataIni, dataFim, true));
		}
		
		indicadorTreinamento.setDataIni(dataIni);
		indicadorTreinamento.setDataFim(dataFim);

		Double qtdHoras = indicadorTreinamento.getSomaHoras();
		Double qtdHorasRatiada = indicadorTreinamento.getSomaHorasRatiada();
		Double somaCustos = indicadorTreinamento.getSomaCustos();

		indicadorTreinamento.setCustoMedioHora( qtdHoras > 0 ? somaCustos / qtdHoras : 0);
		
		Double custoPerCapita = 0d;
		Double horasPerCapita = 0d;
		Double percentualFrequencia = 0d;
		Double percentualInvestimento = 0d;
		
		if (indicadorTreinamento.getQtdColaboradoresFiltrados() > 0)
			custoPerCapita = (somaCustos / indicadorTreinamento.getQtdColaboradoresFiltrados());

		indicadorTreinamento.setCustoPerCapita(custoPerCapita);

		Integer qtdAtivos = colaboradorManager.getCountAtivosQualquerStatus(dataFim, empresaIds, areasIds, estabelecimentosIds);

		if (qtdAtivos != null && qtdAtivos > 0)
			horasPerCapita = qtdHorasRatiada / qtdAtivos;

		indicadorTreinamento.setHorasPerCapita(horasPerCapita);
		
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBean("colaboradorTurmaManager");
		percentualFrequencia = colaboradorTurmaManager.percentualFrequencia(dataIni, dataFim, empresaIds, cursoIds, areasIds, estabelecimentosIds);
		indicadorTreinamento.setPercentualFrequencia(percentualFrequencia);
		
		TurmaManager turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		percentualInvestimento = turmaManager.getPercentualInvestimento(somaCustos, dataIni, dataFim, empresaIds);
		indicadorTreinamento.setPercentualInvestimento(percentualInvestimento);
		
		return indicadorTreinamento;
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
	
	public boolean existeAvaliacaoAlunoDeTipoNotaOuPorcentagemRespondida(Long cursoId) 
	{
		return getDao().existeAvaliacaoAlunoRespondida(cursoId, TipoAvaliacaoCurso.NOTA); // Esse tipo pode ser também PERCENTUAL
	}
	
	public boolean existeAvaliacaoAlunoDeTipoAvaliacaoRespondida(Long cursoId)
	{
		return getDao().existeAvaliacaoAlunoRespondida(cursoId, TipoAvaliacaoCurso.AVALIACAO);
	}
	
	public Collection<Curso> somaDespesasPorCurso(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds) 
	{
		return getDao().somaDespesasPorCurso(dataIni, dataFim, empresaIds, cursoIds);
	}

	public void clonar(Long id, Long empresaSistemaId, Long[] empresasIds, String novoTituloCursoClonado) throws Exception {
		if (empresasIds != null && empresasIds.length > 0)
			for (Long empresaId : empresasIds)
				clonarCurso(id, empresaId, novoTituloCursoClonado);
		else
			clonarCurso(id, empresaSistemaId, novoTituloCursoClonado);
	}

	private void clonarCurso(Long id, Long empresaId, String novoTituloCursoClonado) throws Exception{
		Empresa empresa = new Empresa();
		empresa.setId(empresaId);

		Curso curso = (Curso) getDao().findById(id).clone();

		CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>();
		curso.setAvaliacaoCursos(collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, collectionUtil.convertCollectionToArrayIdsString(curso.getAvaliacaoCursos())));
		
		if(novoTituloCursoClonado == null || novoTituloCursoClonado.isEmpty())
			curso.setNome(curso.getNome() + " (Clone)");
		else
			curso.setNome(novoTituloCursoClonado);

		curso.setEmpresa(empresa);
		curso.setId(null);
		curso.setCertificacaos(null);
		curso.setAtitudes(null);
		curso.setHabilidades(null);
		curso.setConhecimentos(null);
		curso.setTurmas(null);
		curso.setEmpresasParticipantes(null);
		getDao().save(curso);
	}

	public Collection<Curso> findByEmpresaIdAndCursosId(Long empresaId,	Long... cursosIds) 
	{
		return getDao().findByEmpresaIdAndCursosId( empresaId, cursosIds);
	}
	
	public boolean existePresenca(Long cursoId) {
		return getDao().existePresenca(cursoId);
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

	public Collection<Curso> findByHistoricoFuncaoId(Long historicoFuncaoId) {
		return getDao().findByHistoricoFuncaoId(historicoFuncaoId);
	}

	public Collection<CheckBox> populaCheckListCurso(Long empresaId) {
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Curso> cursos = findAllByEmpresasParticipantes(empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}
}