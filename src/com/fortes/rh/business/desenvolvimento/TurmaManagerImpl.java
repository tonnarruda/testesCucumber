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
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.RelatorioUtil;

public class TurmaManagerImpl extends GenericManagerImpl<Turma, TurmaDao> implements TurmaManager
{
	private DiaTurmaManager diaTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private CursoManager cursoManager;

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

	public void setColaboradorTurmaManager(
			ColaboradorTurmaManager colaboradorTurmaManager) {
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

	public void salvarTudo(Turma turma, String[] diasCheck) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			save(turma);
			diaTurmaManager.saveDiasTurma(turma, diasCheck);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}

	}

	public void updateTudo(Turma turma, String[] diasCheck) throws Exception
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
			turmaClonada.setAvaliacaoTurmas(null);
			
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

	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager)
	{
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}

	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, char realizada)
	{
		Collection<Turma> turmas = getDao().findPlanosDeTreinamento(page, pagingSize, cursoId, dataIni, dataFim, realizadaValue(realizada));
		for (Turma turma : turmas)
		{
			turma.setQtdPessoas(colaboradorTurmaManager.getCount(turma.getId()));
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

	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, char realizada, Long empresaId)
	{
		return getDao().findByFiltro(dataPrevIni, dataPrevFim, realizadaValue(realizada), empresaId);
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
	
//	public Collection<Colaborador> enviarEmailParticipantes(Long turmaId) {
//		getDao().findByIdProjection(turmaId)
//		return null;
//	}

	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}
	
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long empresaId) 
	{
		return getDao().quantidadeParticipantesPrevistos(dataIni, dataFim, empresaId);
	}
	
	public Collection<Turma> findByTurmasPeriodo(Long[] turmasCheck, Date dataIni, Date dataFim, Boolean realizada) 
	{
		return getDao().findByTurmasPeriodo(turmasCheck,  dataIni, dataFim, realizada);
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}
}