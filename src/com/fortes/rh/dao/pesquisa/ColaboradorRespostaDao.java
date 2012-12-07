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

//	int getQtdComentarios(Long idPergunta);
//
//	Collection<ColaboradorResposta> findByPesquisa(Pesquisa pesquisa);
//
//	Collection<ColaboradorResposta> getRespostasByColaborador(Long colaboradorId, Collection<Pergunta> perguntas);
//
//	ColaboradorResposta getRespostasColaboradorByIdPergunta(Long colaboradorId, Long perguntaId);
//
//	Collection<ColaboradorResposta> getRespostasByColaboradorPerguntas(Long colaboradorId, Collection<Pergunta> perguntas);
//
//	Collection<ColaboradorResposta> getRespostasByColaboradoresPerguntas(Long[] colaboradores, Collection<Pergunta> perguntas);
//
//	void removeRespostas(Colaborador colaborador, Collection<Pergunta> perguntas);

	List<Object[]> countRespostas(Long[] perguntaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, Long turmaId, Long empresaId);

	Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, Long turmaId, Questionario questionario, Long empresaId);

	Collection<ColaboradorResposta> findRespostasColaborador(Long colaboradorQuestionarioId, Boolean aplicarPorAspecto);

	void removeByColaboradorQuestionario(Long colaboradorQuestionarioId);
	void removeByColaboradorQuestionario(Long[] colaboradorQuestionarioIds);

	Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId);
	
	Collection<ColaboradorResposta> findByColaboradorQuestionario(Long colaboradorQuestionarioId);

	Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId);

	List<Object[]> countRespostasMultiplas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, Long turmaId, Long empresaId);
	
	Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);

	List<Object[]> countRespostas(Long avaliadoId, Long avaliacaoDesempenhoId);
	
	List<Object[]> countRespostasMultiplas(Long avaliadoId, Long avaliacaoDesempenhoId);

	Collection<RespostaQuestionarioVO> findRespostasAvaliacaoDesempenho(Long colaboradorQuestionarioId);
}