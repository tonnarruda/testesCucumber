package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.dicionario.TipoRiscoSistema;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

public class SolicitacaoExameManagerImpl extends GenericManagerImpl<SolicitacaoExame, SolicitacaoExameDao> implements SolicitacaoExameManager
{
	private PlatformTransactionManager transactionManager;
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;
	private RealizacaoExameManager realizacaoExameManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, String[] examesCheck, ResultadoExame resultadoExame)
	{
		Long[] exameIds = LongUtil.arrayStringToArrayLong(examesCheck);
		return getDao().getCount(empresaId, dataIni, dataFim, vinculo, nomeBusca, matriculaBusca, motivo, exameIds, resultadoExame);
	}

	public Collection<SolicitacaoExame> findAllSelect(Integer page, Integer pagingSize, Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, String[] examesCheck, ResultadoExame resultadoExame)
	{
		Long[] exameIds = LongUtil.arrayStringToArrayLong(examesCheck);
		return getDao().findAllSelect(page, pagingSize, empresaId, dataIni, dataFim, vinculo, nomeBusca, matriculaBusca, motivo, exameIds, resultadoExame);
	}

	public Collection<SolicitacaoExame> findByCandidatoOuColaborador(TipoPessoa vinculo, Long candidatoOuColaboradorId, String motivo)
	{
		return getDao().findByCandidatoOuColaborador(vinculo, candidatoOuColaboradorId, motivo);
	}

	public SolicitacaoExame save(SolicitacaoExame solicitacaoExame, String[] exameIds, String[] selectClinicas, Integer[] periodicidades) throws Exception
	{
		save(solicitacaoExame);
		exameSolicitacaoExameManager.save(solicitacaoExame, exameIds, selectClinicas, periodicidades);
		
		return solicitacaoExame;
	}

	public void update(SolicitacaoExame solicitacaoExame, String[] exameIds, String[] selectClinicas, Integer[] periodicidades)
	{
		update(solicitacaoExame);

		// guarda os ids de RealizacaoExame
		Collection<Long> colecaoIds = realizacaoExameManager.findIdsBySolicitacaoExame(solicitacaoExame.getId());
		Long[] realizacaoExameIds = new Long[colecaoIds.size()];
		colecaoIds.toArray(realizacaoExameIds);

		// remove os relacionamentos e exames realizados
		exameSolicitacaoExameManager.removeAllBySolicitacaoExame(solicitacaoExame.getId());
		realizacaoExameManager.remove(realizacaoExameIds);

		exameSolicitacaoExameManager.save(solicitacaoExame, exameIds, selectClinicas, periodicidades);
	}

	@Override
	public void remove(Long solicitacaoExameId) throws PersistenceException
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			// guarda os ids de RealizacaoExame
			Collection<Long> colecaoIds = realizacaoExameManager.findIdsBySolicitacaoExame(solicitacaoExameId);
			Long[] realizacaoExameIds = new Long[colecaoIds.size()];
			colecaoIds.toArray(realizacaoExameIds);

			// remove os relacionamentos
			exameSolicitacaoExameManager.removeAllBySolicitacaoExame(solicitacaoExameId);

			// remove exames realizados
			realizacaoExameManager.remove(realizacaoExameIds);

			// remove solicitação
			getDao().remove(solicitacaoExameId);
			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<SolicitacaoExameRelatorio> imprimirSolicitacaoExames(Long solicitacaoExameId) throws ColecaoVaziaException
	{
		CollectionUtil<SolicitacaoExameRelatorio> collectionUtil = new CollectionUtil<SolicitacaoExameRelatorio>();

		Collection<SolicitacaoExameRelatorio> collection = getDao().findImprimirSolicitacaoExames(solicitacaoExameId);
		if (collection == null || collection.isEmpty())
			throw new ColecaoVaziaException("Solicitação de exames não encontrada.");

		// coleção distinta pelo nome da clínica
		Collection<SolicitacaoExameRelatorio> solicitacaoExameRelatorios = collectionUtil.distinctCollection(collection);

		// agrupar por clinica
		Long i = 0L;
		for (SolicitacaoExameRelatorio solicitacaoExameRelatorio : solicitacaoExameRelatorios )
		{
			solicitacaoExameRelatorio.setExamesCollection(new ArrayList<Exame>());
			for (SolicitacaoExameRelatorio solicitacaoExameRelatorio2 : collection)
			{
				if (solicitacaoExameRelatorio.getClinicaNome().equals(solicitacaoExameRelatorio2.getClinicaNome()))
				{
					solicitacaoExameRelatorio.addExame(solicitacaoExameRelatorio2.getExameNome());
					solicitacaoExameRelatorio.contaExames();
					solicitacaoExameRelatorio.getExamesCollection().add(new Exame(i++, solicitacaoExameRelatorio2.getExameNome()));
				}
			}
		}

		return solicitacaoExameRelatorios;
	}
	
	public Collection<SolicitacaoExame> getRelatorioAtendimentos(Date inicio, Date fim, SolicitacaoExame solicitacaoExame, Empresa empresa, boolean agruparPorMotivo, boolean ordenarPorNome, String[] motivos, char situacao) throws ColecaoVaziaException
	{
		Collection<SolicitacaoExame> resultado = getDao().findAtendimentosMedicos(inicio, fim, motivos, solicitacaoExame.getMedicoCoordenador(), empresa.getId(), agruparPorMotivo, ordenarPorNome, situacao);
		
		if (resultado == null || resultado.isEmpty())
			throw new ColecaoVaziaException("Não existe Atendimento Médico para os filtros informados.");
		
		return resultado;
	}

	public AsoRelatorio montaRelatorioAso(Empresa empresa, SolicitacaoExame solicitacaoExame, String considerarRiscoPor) throws ColecaoVaziaException 
	{
		if (solicitacaoExame == null || solicitacaoExame.getId() == null)
			throw new ColecaoVaziaException("Solicitação/Atendimento médico inválido.");
		
		solicitacaoExame = getDao().findById(solicitacaoExame.getId());
		AsoRelatorio asoRelatorio = new AsoRelatorio(solicitacaoExame, empresa);

		asoRelatorio.setExames(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), false));
		asoRelatorio.setExamesPadrao(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), true));
		
		if(solicitacaoExame.getColaborador() != null && solicitacaoExame.getColaborador().getId() != null)
		{
			HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtual(solicitacaoExame.getColaborador().getId());
			if (historicoColaborador == null) {
				historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(solicitacaoExame.getColaborador().getId());
			}

			if (historicoColaborador != null && historicoColaborador.getFuncao() != null && asoRelatorio != null && asoRelatorio.getColaborador() != null){
				HistoricoFuncaoManager historicoFuncaoManager = (HistoricoFuncaoManager) SpringUtil.getBean("historicoFuncaoManager");
				HistoricoFuncao historicoFuncao = historicoFuncaoManager.findByFuncaoAndData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData());
				if(historicoFuncao != null && historicoFuncao.getFuncaoNome() != null && !"".equals(historicoFuncao.getFuncaoNome()))
					historicoColaborador.getFuncao().setNome(historicoFuncao.getFuncaoNome());
				
				asoRelatorio.getColaborador().setFuncao(historicoColaborador.getFuncao());
			}

			configuraRiscos(empresa, solicitacaoExame, considerarRiscoPor, asoRelatorio, historicoColaborador);
		}
		
		return asoRelatorio;
	}

	private void configuraRiscos(Empresa empresa, SolicitacaoExame solicitacaoExame, String considerarRiscoPor, AsoRelatorio asoRelatorio, HistoricoColaborador historicoColaborador)
	{
		Collection<Risco> riscos = null;
		if (considerarRiscoPor.equals(TipoRiscoSistema.AMBIENTE) && historicoColaborador != null && historicoColaborador.getAmbiente() != null && historicoColaborador.getAmbiente().getId() != null)
		{
			riscos = riscoAmbienteManager.findRiscosByAmbienteData(historicoColaborador.getAmbiente().getId(), solicitacaoExame.getData());
			asoRelatorio.formataRiscos(riscos);
		}
		else if(considerarRiscoPor.equals(TipoRiscoSistema.FUNCAO) && historicoColaborador != null && historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getId() != null)
		{
			riscos = riscoFuncaoManager.findRiscosByFuncaoData(historicoColaborador.getFuncao().getId(), solicitacaoExame.getData());
			asoRelatorio.formataRiscos(riscos);
		}
		else if(considerarRiscoPor.equals(TipoRiscoSistema.AMBIENTE_FUNCAO) && historicoColaborador != null && 
				(historicoColaborador.getAmbiente() != null && historicoColaborador.getAmbiente().getId() != null)  && 
				(historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getId() != null))
		{
			Set<Risco> riscosAmbienteFuncao = new HashSet<Risco>( riscoAmbienteManager.findRiscosByAmbienteData(historicoColaborador.getAmbiente().getId(), solicitacaoExame.getData()) );
			riscosAmbienteFuncao.addAll( riscoFuncaoManager.findRiscosByFuncaoData(historicoColaborador.getFuncao().getId(), solicitacaoExame.getData()) );
			
			asoRelatorio.formataRiscos(new ArrayList<Risco>(riscosAmbienteFuncao));
		}
	}
	
	public void transferirColaboradorToCandidato(Long empresaId, Long candidatoId, Long colaboradorId)
	{
		if (empresaId != null && candidatoId != null && colaboradorId != null)
			getDao().transferirColaboradorToCandidato(empresaId, candidatoId, colaboradorId);
	}
	
	
	public void vincularSolicitacaoExameAoColaborador(Long candidatoSolicitacaoId ,Long colaboradorId)
	{
		getDao().vincularSolicitacaoExameAoColaborador(candidatoSolicitacaoId, colaboradorId);
	}

	public void setRealizacaoExameManager(RealizacaoExameManager realizacaoExameManager)
	{
		this.realizacaoExameManager = realizacaoExameManager;
	}
	
	public void setExameSolicitacaoExameManager(ExameSolicitacaoExameManager exameSolicitacaoExameManager)
	{
		this.exameSolicitacaoExameManager = exameSolicitacaoExameManager;
	}

	public MedicoCoordenador setMedicoByQuantidade(Collection<MedicoCoordenador> medicoCoordenadors) 
	{
		if(medicoCoordenadors != null && medicoCoordenadors.size() == 1)
			return (MedicoCoordenador) medicoCoordenadors.toArray()[0];
		else
			return null;
	}

	@Override
	public SolicitacaoExame findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void removeByCandidato(Long candidatoId) {
		getDao().removeByCandidato(candidatoId);
	}

	public void removeByColaborador(Long colaboradorId) {
		getDao().removeByColaborador(colaboradorId);
	}

	public Integer findProximaOrdem(Date data) 
	{
		return getDao().findProximaOrdem(data);
	}

	public void ajustaOrdemDoList(Date data, Integer ordem) throws Exception{
		getDao().ajustaOrdem(data, ordem, null, -1);
	}
	
	public void ajustaOrdem(Date dataAnterior, Date dataAtual, Integer ordemAnterior, Integer novaOrdem) throws Exception
	{
		if (dataAnterior != null && dataAtual != dataAnterior){
			getDao().ajustaOrdem(dataAtual, novaOrdem, null, 1);
			getDao().ajustaOrdem(dataAnterior, ordemAnterior, null, -1);
		} else {
			if (ordemAnterior != null)
			{
				if (ordemAnterior < novaOrdem)
					getDao().ajustaOrdem(dataAtual, ordemAnterior, novaOrdem, -1);
				
				else if (ordemAnterior > novaOrdem)
					getDao().ajustaOrdem(dataAtual, novaOrdem, ordemAnterior, 1);
			}else{
				getDao().ajustaOrdem(dataAtual, novaOrdem, ordemAnterior, 1);
			}
		}
	}

	public void setRiscoFuncaoManager(RiscoFuncaoManager riscoFuncaoManager) {
		this.riscoFuncaoManager = riscoFuncaoManager;
	}
}