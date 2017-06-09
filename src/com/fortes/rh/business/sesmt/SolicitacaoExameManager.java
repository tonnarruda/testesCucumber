package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.security.auditoria.MetodoCarregaEntidadeAuditoria;

public interface SolicitacaoExameManager extends GenericManager<SolicitacaoExame>
{
	Integer getCount(Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, String[] examesCheck, ResultadoExame resultadoExame);
	Collection<SolicitacaoExame> findAllSelect(Integer page, Integer pagingSize, Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, String[] examesCheck, ResultadoExame resultadoExame);
	SolicitacaoExame save(SolicitacaoExame solicitacaoExame, String[] exameIds, String[] selectClinicas, Integer[] periodicidades) throws Exception;
	Collection<SolicitacaoExameRelatorio> imprimirSolicitacaoExames(Long solicitacaoExameId) throws ColecaoVaziaException;
	void update(SolicitacaoExame solicitacaoExame, String[] examesId, String[] selectClinicas, Integer[] periodicidades);
	Collection<SolicitacaoExame> findByCandidatoOuColaborador(TipoPessoa vinculo, Long candidatoOuColaboradorId, String motivo);
	void transferirColaboradorToCandidato(Long empresaId, Long candidatoId, Long colaboradorId);
	Collection<SolicitacaoExame> getRelatorioAtendimentos(Date inicio, Date fim, SolicitacaoExame solicitacaoExame, Empresa empresa, boolean agruparPorMotivo, boolean ordenarPorNome, String[] motivos, char situacao) throws ColecaoVaziaException;
	AsoRelatorio montaRelatorioAso(Empresa empresa, SolicitacaoExame solicitacaoExame, String imprimirAsoComRiscoPor) throws ColecaoVaziaException;
	MedicoCoordenador setMedicoByQuantidade(Collection<MedicoCoordenador> medicoCoordenadors);
	@MetodoCarregaEntidadeAuditoria
	SolicitacaoExame findByIdProjection(Long id);
	void removeByCandidato(Long candidatoId);
	void removeByColaborador(Long colaboradorId);
	Integer findProximaOrdem(Date data);
	void ajustaOrdemDoList(Date data, Integer ordem) throws Exception;
	void ajustaOrdem(Date dataAnterior, Date dataAtual, Integer ordemAnterior, Integer novaOrdem) throws Exception;
}