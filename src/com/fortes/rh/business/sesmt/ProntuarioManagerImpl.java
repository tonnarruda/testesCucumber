package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.dao.sesmt.ProntuarioDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.relatorio.ProntuarioRelatorio;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.util.CollectionUtil;

public class ProntuarioManagerImpl extends GenericManagerImpl<Prontuario, ProntuarioDao> implements ProntuarioManager
{
	private ColaboradorManager colaboradorManager;
	private RealizacaoExameManager realizacaoExameManager;
	private FichaMedicaManager fichaMedicaManager;
	private SolicitacaoExameManager solicitacaoExameManager;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;

	public Collection<Prontuario> findByColaborador(Colaborador colaborador)
	{
		return getDao().findByColaborador(colaborador);
	}

	public ProntuarioRelatorio findRelatorioProntuario(Empresa empresa, Colaborador colaborador) throws ColecaoVaziaException
	{
		colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), null);

		Collection<Prontuario> prontuarios = findByColaborador(colaborador);
		CollectionUtil<Prontuario> collectionUtil = new CollectionUtil<Prontuario>();
		prontuarios = collectionUtil.sortCollectionDate(prontuarios, "data", "asc");

		Collection<RealizacaoExame> realizacaoExames = realizacaoExameManager.findRealizadosByColaborador(empresa.getId(), colaborador.getId());

		Collection<FichaMedica> fichasMedicas = fichaMedicaManager.findByColaborador(empresa.getId(), colaborador.getId());
		
		Collection<SolicitacaoExame> consultas = solicitacaoExameManager.findByCandidatoOuColaborador(TipoPessoa.COLABORADOR, colaborador.getId(), MotivoSolicitacaoExame.CONSULTA);
		Collection<SolicitacaoExame> atestadosExternos  = solicitacaoExameManager.findByCandidatoOuColaborador(TipoPessoa.COLABORADOR, colaborador.getId(), MotivoSolicitacaoExame.ATESTADO);
		
		Collection<ColaboradorAfastamento> afastamentos = colaboradorAfastamentoManager.findByColaborador(colaborador.getId());
		
		ProntuarioRelatorio prontuarioRelatorio = new ProntuarioRelatorio(colaborador, prontuarios, realizacaoExames, fichasMedicas, consultas, atestadosExternos, afastamentos);
		
		if (prontuarioRelatorio.getPossuiResultados())
			return prontuarioRelatorio;
		else
			throw new ColecaoVaziaException("O colaborador informado não possui registros de prontuário.");
	}
	
	public Integer findQtdByEmpresa(Long empresaId, Date dataIni, Date dataFim) {
		return getDao().findQtdByEmpresa(empresaId, dataIni, dataFim);
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setRealizacaoExameManager(RealizacaoExameManager realizacaoExameManager)
	{
		this.realizacaoExameManager = realizacaoExameManager;
	}

	public void setFichaMedicaManager(FichaMedicaManager fichaMedicaManager)
	{
		this.fichaMedicaManager = fichaMedicaManager;
	}

	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager) {
		this.solicitacaoExameManager = solicitacaoExameManager;
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager) {
		this.colaboradorAfastamentoManager = colaboradorAfastamentoManager;
	}
}