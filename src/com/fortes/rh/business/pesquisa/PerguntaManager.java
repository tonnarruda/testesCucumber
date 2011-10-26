package com.fortes.rh.business.pesquisa;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.callback.PerguntaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.web.tags.CheckBox;

public interface PerguntaManager extends GenericManager<Pergunta>
{
	@Audita(operacao="Inserção", auditor=PerguntaAuditorCallbackImpl.class)
	Pergunta salvarPergunta(Pergunta pergunta, String[] respostas, Integer[] pesoRespostaObjetiva, Integer ordemSugerida) throws Exception;
	@Audita(operacao="Atualização", auditor=PerguntaAuditorCallbackImpl.class)
	void atualizarPergunta(Pergunta pergunta, String[] respostaObjetiva, Integer[] pesoRespostaObjetiva) throws Exception;
	@Audita(operacao="Remoção", auditor=PerguntaAuditorCallbackImpl.class)
	void removerPergunta(Pergunta pergunta) throws Exception;

	Collection<Pergunta> getPerguntasRespostaByQuestionario(Long questionarioId);
	Collection<Pergunta> getPerguntasRespostaByQuestionarioAspecto(Long questionarioId, Long[] aspectosIds);
	Collection<Pergunta> getPerguntasSemAspecto(Collection<Pergunta> perguntas);
	Pergunta findByIdProjection(Long perguntaId);
	Pergunta findPergunta(Long perguntaId);
	Long findUltimaPerguntaObjetiva(Long questionarioId);
	void reordenarPergunta(Pergunta pergunta, char sinal) throws Exception;
	int getUltimaOrdenacao(Long questionarioId) throws Exception;
	void alterarPosicao(Pergunta pergunta, int novaPosicao) throws Exception;
	void embaralharPerguntas(Long pesquisaId) throws Exception;
	void salvarPerguntasByOrdem(Collection<Pergunta> perguntas) throws Exception;
	Collection<CheckBox> populaCheckOrderTexto(Long questionarioId);
	Collection<Pergunta> findByQuestionarioAspectoPergunta(Long questionarioId, Long[] aspectosIds, Long[] perguntasIds, boolean agruparPorAspectos);
	Collection<Pergunta> modificarOrdens(Collection<Pergunta> perguntas, int qtdPosicoes);
	void clonarPerguntas(Long questionarioId, Questionario questionarioClonado, Avaliacao avaliacaoClonada);
	void removerPerguntasDoQuestionario(Long questionarioId);
	Collection<Long> findPerguntasDoQuestionario(Long questionarioId);
	Collection<Pergunta> findByQuestionario(Long questionarioId);
	void montaImpressaoPergunta(Pergunta pergunta, Collection<ColaboradorResposta> colaboradorRespostas, StringBuilder textoPergunta, StringBuilder textoComentario);
	void setAvaliadoNaPerguntaDeAvaliacaoDesempenho(Pergunta pergunta, String avaliadoNome);
	Collection<Pergunta> getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(Long questionarioId, boolean ordenarPorAspecto);
	Map<Long, Integer> getPontuacoesMaximas(Long[] perguntasIds);
}