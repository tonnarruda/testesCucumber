package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;

public class TurmaManagerImpl extends GenericManagerImpl<Turma, TurmaDao> implements TurmaManager
{
	private DiaTurmaManager diaTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private CursoManager cursoManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;
	private FaturamentoMensalManager faturamentoMensalManager;

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public void removeCascade(Long id) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.find(new String[]{"turma.id"}, new Object[]{id});
			aproveitamentoAvaliacaoCursoManager.removeByTurma(id);
			turmaTipoDespesaManager.removeByTurma(id);
			//	Remove todos os relacionamentos com Questionario/Resposta na turma
			colaboradorQuestionarioManager.removeByColaboradorETurma(null, id);
			
			if(colaboradoresTurmas.size() > 0)
			{
				CollectionUtil<ColaboradorTurma> cc = new CollectionUtil<ColaboradorTurma>();
				Long[] colaboradorTurmaIds = cc.convertCollectionToArrayIds(colaboradoresTurmas);
				colaboradorPresencaManager.removeByColaboradorTurma(colaboradorTurmaIds);
				colaboradorTurmaManager.remove(colaboradorTurmaIds);
			}

			diaTurmaManager.deleteDiasTurma(id);

			remove(id);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}

	public Turma findByIdProjection(Long turmaId)
	{
		return getDao().findByIdProjection(turmaId);
	}

	public void salvarTurmaDiasCusto(Turma turma, String[] diasCheck, String despesaJSON) throws Exception
	{
		save(turma);
		
		diaTurmaManager.saveDiasTurma(turma, diasCheck);
		
		if (!StringUtil.isBlank(despesaJSON))
			turmaTipoDespesaManager.save(despesaJSON, turma.getId());
	}
	
	public void salvarTurmaDiasCustosColaboradoresAvaliacoes(Turma turma, String[] dias, String custos, Collection<ColaboradorTurma> colaboradorTurmas, Long[] avaliacaoTurmasCheck) throws Exception 
	{
		salvarTurmaDiasCusto(turma, dias, custos);

		TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager = (TurmaAvaliacaoTurmaManager) SpringUtil.getBean("turmaAvaliacaoTurmaManager");
		turmaAvaliacaoTurmaManager.salvarAvaliacaoTurmas(turma.getId(), avaliacaoTurmasCheck);
		
		if (colaboradorTurmas != null)
		{
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
			{
				if (colaboradorTurma != null)
				{
					colaboradorTurma.setTurma(turma);
					colaboradorTurmaManager.save(colaboradorTurma);
				}
			}
		}
	}

	public void updateTurmaDias(Turma turma, String[] diasCheck) throws Exception
	{
		boolean result = false;
		
		try
		{
			update(turma);
			result = colaboradorPresencaManager.existPresencaByTurma(turma.getId());
			if(!result)
			{
				diaTurmaManager.saveDiasTurma(turma, diasCheck);
			}
		}
		catch(Exception e)
		{
			throw e;
		}

	}
	
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId) 
	{	
		Collection<Turma> turmas = getDao().findByEmpresaOrderByCurso(empresaOrigemId);
		
		Long cursoIdTmp = 0L;
		Curso cursoClonado = null;
		
		for (Turma turma : turmas) 
		{
			Turma turmaClonada = (Turma) turma.clone();
			turmaClonada.setEmpresaId(empresaDestinoId);
			turmaClonada.setId(null);
			turmaClonada.setTurmaAvaliacaoTurmas(null);
			
			if(cursoIdTmp.equals(turma.getCurso().getId()))
			{
				turmaClonada.setCurso(cursoClonado);
			}
			else
			{
				cursoClonado = cursoManager.saveClone((Curso) turma.getCurso().clone(), empresaDestinoId);
				turmaClonada.setCurso(cursoClonado);
			}
			
			getDao().save(turmaClonada);
			cursoIdTmp = turma.getCurso().getId();

			diaTurmaManager.clonarDiaTurmasDeTurma(turma, turmaClonada);
		}
		
		Collection<Curso> cursosSemTurma = cursoManager.findCursosSemTurma(empresaOrigemId);
		
		for (Curso curso : cursosSemTurma) 
			cursoManager.saveClone((Curso) curso.clone(), empresaDestinoId);
	}

	public Collection<Turma> getTurmaFinalizadas(Long cursoId)
	{
		return getDao().getTurmaFinalizadas(cursoId);
	}

	public Map<String, Object> getParametrosRelatorio(Empresa empresa, Map<String, Object> parametros)
	{
		Map<String, Object> parametrosRelatorio = RelatorioUtil.getParametrosRelatorio("Relatório de Realização de Curso", empresa, null);

		parametros.putAll(parametrosRelatorio);

		return parametros;
	}

	public Map<String, Object> getParametrosRelatorioSemTreinamento(Empresa empresa, Map<String, Object> parametros)
	{
		Map<String, Object> parametrosRelatorio = RelatorioUtil.getParametrosRelatorio("Colaboradores que não realizaram o Treinamento", empresa, null);

		parametros.putAll(parametrosRelatorio);

		return parametros;
	}

	public List filtroRelatorioByAreas(LinkedHashMap parametros)
	{
		return getDao().filtroRelatorioByAreas(parametros);
	}

	public List filtroRelatorioByColaborador(LinkedHashMap parametros)
	{
		return getDao().filtroRelatorioByColaborador(parametros);
	}

	public Collection<Turma> findAllSelect(Long cursoId)
	{
		return getDao().findAllSelect(cursoId);
	}

	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId)
	{
		return getDao().findTurmas(page, pagingSize, cursoId);
	}

	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, char realizada)
	{
		Collection<Turma> turmas = getDao().findPlanosDeTreinamento(page, pagingSize, cursoId, dataIni, dataFim, realizadaValue(realizada));
		for (Turma turma : turmas)
		{
			turma.setQtdPessoas(colaboradorTurmaManager.getCount(turma.getId(), null, null, null));
		}

		return  turmas;
	}

	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, char realizada)
	{
		return getDao().countPlanosDeTreinamento(cursoId, dataIni, dataFim, realizadaValue(realizada));
	}

	public void updateRealizada(Long turmaId, boolean realizada)throws Exception
	{
		getDao().updateRealizada(turmaId, realizada);
	}

	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, char realizada, Long[] empresaIds)
	{
		return getDao().findByFiltro(dataPrevIni, dataPrevFim, realizadaValue(realizada), empresaIds);
	}
	
	public Collection<Turma> findByCursos(Long[] cursoIds)
	{
		return getDao().findByCursos(cursoIds);
		
	}

	public Boolean realizadaValue(char realizada)
	{
		if(realizada == 'S')
			return true;
		else if(realizada == 'N')
			return false;

		return null;
	}

	public Collection<Turma> findByIdProjection(Long[] ids)
	{
		return getDao().findByIdProjection(ids);
	}

	public boolean verificaAvaliacaoDeTurmaRespondida(Long turmaId)
	{
		Collection<ColaboradorQuestionario> respondidas = colaboradorQuestionarioManager.findRespondidasByColaboradorETurma(null, turmaId, null);

		return (respondidas == null) ? false : (respondidas.size() > 0);
	}
	
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long[] empresaIds) 
	{
		return getDao().quantidadeParticipantesPrevistos(dataIni, dataFim, empresaIds);
	}
	
	public Collection<Turma> findByTurmasPeriodo(Long[] turmasCheck, Date dataIni, Date dataFim, Boolean realizada) 
	{
		return getDao().findByTurmasPeriodo(turmasCheck,  dataIni, dataFim, realizada);
	}

	public void updateCusto(Long turmaId, double totalCusto) 
	{
		getDao().updateCusto(turmaId, totalCusto);
	}

	public Double somaCustosNaoDetalhados(Date dataIni, Date dataFim, Long[] empresaIds) 
	{
		return getDao().somaCustosNaoDetalhados(dataIni, dataFim, empresaIds);
	}

	public Double getPercentualInvestimento(Date dataIni, Date dataFim, Long[] empresaIds) 
	{
		double percentual = 0.0;
		
		Double faturamentoPeriodo = faturamentoMensalManager.somaByPeriodo(dataIni, dataFim, empresaIds);
		Double custos = getDao().somaCustos(dataIni, dataFim, empresaIds);
		
		if (faturamentoPeriodo > 0)
			percentual = (custos / faturamentoPeriodo) * 100;
		
		return percentual;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) 
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public void setDiaTurmaManager(DiaTurmaManager diaTurmaManager)
	{
		this.diaTurmaManager = diaTurmaManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
	
	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}
	
	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}
	
	public void setTurmaTipoDespesaManager(TurmaTipoDespesaManager turmaTipoDespesaManager) {
		this.turmaTipoDespesaManager = turmaTipoDespesaManager;
	}

	public void setFaturamentoMensalManager(FaturamentoMensalManager faturamentoMensalManager) {
		this.faturamentoMensalManager = faturamentoMensalManager;
	}

	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager)
	{
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}
}