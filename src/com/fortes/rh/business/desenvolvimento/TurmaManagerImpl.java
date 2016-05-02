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
import com.fortes.model.type.File;
import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.thread.certificaColaboradorThread;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;

public class TurmaManagerImpl extends GenericManagerImpl<Turma, TurmaDao> implements TurmaManager
{
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private FaturamentoMensalManager faturamentoMensalManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private PlatformTransactionManager transactionManager;
	private DiaTurmaManager diaTurmaManager;
	private CursoManager cursoManager;

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public void removeCascade(Long turmaId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.find(new String[]{"turma.id"}, new Object[]{turmaId});
			aproveitamentoAvaliacaoCursoManager.removeByTurma(turmaId);
			turmaTipoDespesaManager.removeByTurma(turmaId);
			//	Remove todos os relacionamentos com Questionario/Resposta na turma
			colaboradorQuestionarioManager.removeByColaboradorETurma(null, turmaId);
			
			if(colaboradoresTurmas.size() > 0)
			{
				for (ColaboradorTurma colaboradorTurma : colaboradoresTurmas) 
					colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId(), true);
				
				CollectionUtil<ColaboradorTurma> cc = new CollectionUtil<ColaboradorTurma>();
				Long[] colaboradorTurmaIds = cc.convertCollectionToArrayIds(colaboradoresTurmas);
				colaboradorPresencaManager.removeByColaboradorTurma(colaboradorTurmaIds);
				colaboradorTurmaManager.remove(colaboradorTurmaIds);
			}

			diaTurmaManager.deleteDiasTurma(turmaId);
			
			TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager = (TurmaAvaliacaoTurmaManager) SpringUtil.getBean("turmaAvaliacaoTurmaManager");
			turmaAvaliacaoTurmaManager.removeByTurma(turmaId, null);
			
			TurmaDocumentoAnexoManager turmaDocumentoAnexoManager = (TurmaDocumentoAnexoManager) SpringUtil.getBean("turmaDocumentoAnexoManager");
			turmaDocumentoAnexoManager.removeByTurma(turmaId);

			remove(turmaId);

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

	public void inserir(Turma turma, String[] dias, String custos, Long[] avaliacaoTurmaIds, Long[] documentoAnexoIds, String[] horasIni, String[] horasFim) throws Exception
	{
		salvarTurmaDiasCusto(turma, dias, horasIni, horasFim, custos);

		TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager = (TurmaAvaliacaoTurmaManager) SpringUtil.getBean("turmaAvaliacaoTurmaManager");
		TurmaDocumentoAnexoManager turmaDocumentoAnexoManager = (TurmaDocumentoAnexoManager) SpringUtil.getBean("turmaDocumentoAnexoManager");
		turmaAvaliacaoTurmaManager.salvarAvaliacaoTurmas(turma.getId(), avaliacaoTurmaIds);
		turmaDocumentoAnexoManager.salvarDocumentoAnexos(turma.getId(), documentoAnexoIds);
	}

	public void atualizar(Turma turma, String[] dias, String[] horasIni, String[] horasFim, String[] colaboradorTurma, String[] selectPrioridades, Long[] avaliacaoTurmaIds, Long[] documentoAnexoIds,
			boolean atualizaAvaliacaoEDocumentoAnexos, boolean validarCertificacao) throws Exception
	{
		colaboradorTurmaManager.saveUpdate(colaboradorTurma, selectPrioridades, validarCertificacao);

		updateTurmaDias(turma, dias, horasIni, horasFim);
		
		TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager = (TurmaAvaliacaoTurmaManager) SpringUtil.getBean("turmaAvaliacaoTurmaManager");
		TurmaDocumentoAnexoManager turmaDocumentoAnexoManager = (TurmaDocumentoAnexoManager) SpringUtil.getBean("turmaDocumentoAnexoManager");
		
		if(atualizaAvaliacaoEDocumentoAnexos) {
			turmaAvaliacaoTurmaManager.salvarAvaliacaoTurmas(turma.getId(), avaliacaoTurmaIds);
			turmaDocumentoAnexoManager.salvarDocumentoAnexos(turma.getId(), documentoAnexoIds);
		}
		verificaAprovacaoByTurma(turma.getId());
		
		if(validarCertificacao && colaboradorTurma == null)
			verificaCertificacaoByColaboradorTurmaId(turma);
	}
	
	private void verificaAprovacaoByTurma(Long turmaId) {
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaId(turmaId);
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
			colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), turmaId, colaboradorTurma.getCurso().getId());
		}
	}
	
	private void verificaCertificacaoByColaboradorTurmaId(Turma turma){
		Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByTurmaId(turma.getId());
		if(turma.getRealizada()){
			CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBean("certificacaoManager");
			for (ColaboradorTurma colaboradorTurma : colaboradoresTurmas) 
				new certificaColaboradorThread(colaboradorCertificacaoManager, colaboradorTurma.getId(), certificacaoManager).start();
		}
		else{
			for (ColaboradorTurma colaboradorTurma : colaboradoresTurmas) 
				colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId(), false);
		}
	}

	public void salvarTurmaDiasCusto(Turma turma, String[] diasCheck, String[] horasIni, String[] horasFim, String despesaJSON) throws Exception
	{
		save(turma);
		
		diaTurmaManager.saveDiasTurma(turma, diasCheck, horasIni, horasFim);
		
		if (!StringUtil.isBlank(despesaJSON))
			turmaTipoDespesaManager.save(despesaJSON, turma.getId());
	}
	
	public void salvarTurmaDiasCustosColaboradoresAvaliacoes(Turma turma, String[] dias, String custos, Collection<ColaboradorTurma> colaboradorTurmas, Long[] avaliacaoTurmasCheck, String[] horasIni, String[] horasFim) throws Exception 
	{
		salvarTurmaDiasCusto(turma, dias, horasIni, horasFim, custos);

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

	public void updateTurmaDias(Turma turma, String[] diasCheck, String[] horasIni, String[] horasFim) throws Exception
	{
		boolean result = false;
		
		try
		{
			update(turma);
			result = colaboradorPresencaManager.existPresencaByTurma(turma.getId());
			if(!result)
			{
				diaTurmaManager.saveDiasTurma(turma, diasCheck, horasIni, horasFim);
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

	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, char realizada, Long empresaId)
	{
		empresaId = (empresaId == null || empresaId == -1l) ? null : empresaId;
		
		Collection<Turma> turmas = getDao().findPlanosDeTreinamento(page, pagingSize, cursoId, dataIni, dataFim, realizadaValue(realizada), empresaId);
		for (Turma turma : turmas)
		{
			turma.setQtdPessoas(colaboradorTurmaManager.getCount(turma.getId(), null, null, null, null));
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

	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, char realizada, Long[] empresaIds, Long[] cursoIds)
	{
		return getDao().findByFiltro(dataPrevIni, dataPrevFim, realizadaValue(realizada), empresaIds, cursoIds);
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
	
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds) 
	{
		return getDao().quantidadeParticipantesPrevistos(dataIni, dataFim, empresasIds, cursosIds);
	}
	
	public Integer quantidadeParticipantesPresentes(Date dataIni, Date dataFim, Long[] empresasIds, Long[] areasIds, Long[] cursosIds, Long[] estabelecimentosIds) 
	{
		return getDao().quantidadeParticipantesPresentes(dataIni, dataFim, empresasIds, areasIds, cursosIds, estabelecimentosIds);
	}
	
	public Collection<Turma> findByTurmasPeriodo(Long[] turmasCheck, Date dataIni, Date dataFim, Boolean realizada) 
	{
		return getDao().findByTurmasPeriodo(turmasCheck,  dataIni, dataFim, realizada);
	}

	public void updateCusto(Long turmaId, double totalCusto) 
	{
		getDao().updateCusto(turmaId, totalCusto);
	}

	public Double somaCustosNaoDetalhados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds) 
	{
		return getDao().somaCustosNaoDetalhados(dataIni, dataFim, empresaIds, cursoIds);
	}

	public Double getPercentualInvestimento(Double somaCustos, Date dataIni, Date dataFim, Long[] empresaIds) 
	{
		double percentual = 0.0;
		
		Double faturamentoPeriodo = faturamentoMensalManager.somaByPeriodo(dataIni, dataFim, empresaIds);
		
		if (faturamentoPeriodo > 0)
			percentual = (somaCustos / faturamentoPeriodo) * 100;
		
		return percentual;
	}

	public Turma setAssinaturaDigital(boolean manterAssinaturaDigital, Turma turma, File assinaturaDigital, String local)
	{
		if(!manterAssinaturaDigital)
		{
			String assinatura = ArquivoUtil.salvaArquivo(assinaturaDigital, local);
	
			if(assinaturaDigital != null && !assinatura.equals(""))
				turma.setAssinaturaDigitalUrl(assinatura);
			else
				turma.setAssinaturaDigitalUrl(null);
		}
		
		return turma;
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

	public void setColaboradorCertificacaoManager(
			ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}
}