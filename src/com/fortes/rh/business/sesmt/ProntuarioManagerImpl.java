package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class ProntuarioManagerImpl extends GenericManagerImpl<Prontuario, ProntuarioDao> implements ProntuarioManager
{
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private RealizacaoExameManager realizacaoExameManager;
	@Autowired private FichaMedicaManager fichaMedicaManager;
	@Autowired private SolicitacaoExameManager solicitacaoExameManager;
	@Autowired private ColaboradorAfastamentoManager colaboradorAfastamentoManager;

	@Autowired
	ProntuarioManagerImpl(ProntuarioDao prontuarioDao) {
		setDao(prontuarioDao);
	}
	
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
}