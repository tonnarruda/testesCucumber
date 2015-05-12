package com.fortes.rh.dao.pesquisa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.relatorio.RespostaQuestionarioVO;

public interface ColaboradorRespostaDao extends GenericDao<ColaboradorResposta>
{
	List<Object[]> countRespostas(Long[] perguntaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long empresaId, Character tipoModeloAvaliacao);

	Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Questionario questionario, Long empresaId);

	Collection<ColaboradorResposta> findInPerguntaIdsAvaliacao(Long[] perguntasIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long empresaId, Character tipoModeloAvaliacao);

	Collection<ColaboradorResposta> findRespostasColaborador(Long colaboradorQuestionarioId, Boolean aplicarPorAspecto);

	void removeByColaboradorQuestionario(Long colaboradorQuestionarioId);
	void removeByColaboradorQuestionario(Long[] colaboradorQuestionarioIds);

	Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId, Long colaboradorQuestionarioId);
	
	Collection<ColaboradorResposta> findByColaboradorQuestionario(Long colaboradorQuestionarioId);

	Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId, Long colaboradorQuestionarioId);

	List<Object[]> countRespostasMultiplas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long empresaId);
	
	Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);

	List<Object[]> countRespostas(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);
	
	List<Object[]> countRespostasMultiplas(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);

	Collection<RespostaQuestionarioVO> findRespostasAvaliacaoDesempenho(Long colaboradorQuestionarioId);

	Integer countColaboradorAvaliacaoRespondida(Long avaliacaoId);

	boolean existeRespostaSemCargo(Long[] perguntasIds);

	Collection<ColaboradorResposta> findPerguntasRespostasByColaboradorQuestionario(Long colaboradorQuestionarioId, boolean agruparPorAspecto);

	void removeByQuestionarioId(Long questionarioId);
	
	boolean verificaQuantidadeColaboradoresQueResponderamPesquisaAnonima(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long questionarioId, int quantidadeColaboradoresRelatorioPesquisaAnonima);
}