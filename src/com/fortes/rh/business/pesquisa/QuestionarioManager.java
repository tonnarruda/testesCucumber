package com.fortes.rh.business.pesquisa;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;

//@Modulo("Pesquisa de Questionários")
public interface QuestionarioManager extends GenericManager<Questionario>
{
	// utilizados nos Resultados de Questionarios
	public Integer countColaborador(Collection<ColaboradorResposta> colaboradorRespostas);
	public Pergunta calculaMedia(Collection<ColaboradorResposta> colaboradorRespostas, Pergunta pergunta);
	public Collection<ColaboradorResposta> montaColaboradorReposta(Collection<ColaboradorResposta> colaboradorRespostas, Pergunta pergunta);
	public Collection<Resposta> montaRespostas(Collection<Resposta> respostas, Pergunta pergunta, Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas);
	
    public Collection<ResultadoQuestionario> montaResultadosQuestionarios(Collection<Pergunta> perguntas, Collection<Resposta> respostas, Collection<ColaboradorResposta> colaboradorRespostas, Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas, boolean anonimo);
    public Collection<ResultadoAvaliacaoDesempenho> montaResultadosAvaliacaoDesempenho(Collection<Pergunta> perguntas, Map<Long, Integer> pontuacoesMaximasPerguntas, Collection<Resposta> respostas, Long avaliadoId, Collection<ColaboradorResposta> colaboradorRespostas, Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas, AvaliacaoDesempenho avaliacaoDesempenho, Integer qtdAvaliadores);
	
	//	
	Questionario findByIdProjection(Long questionarioId);
	boolean checarPesquisaLiberadaByQuestionario(Questionario questionario);
	Questionario clonarQuestionario(Questionario questionario, Long empresaId);
	String montaUrlVoltar(Long questionarioId);
	void aplicarPorAspecto(Long questionarioId, boolean aplicarPorAspecto) throws Exception;
	Collection<QuestionarioRelatorio> getQuestionarioRelatorio(Questionario questionario);
	
	//@Audita(operacao="Liberação de Pesquisa", fetchEntity=true)
	void liberarQuestionario(Long questionarioId, Empresa empresa) throws Exception;
	
	void enviaEmailQuestionarioLiberado(Empresa empresa, ParametrosDoSistema parametros, Long questionarioId, Collection<ColaboradorQuestionario> colaboradorQuestionarios) throws Exception;
	Questionario findResponderQuestionario(Questionario questionario);
	void enviaLembreteDeQuestionarioNaoLiberada();
	Collection<Questionario> findQuestionarioPorUsuario(Long usuarioId);
	void updateQuestionario(Questionario questionario);
	void removerPerguntasDoQuestionario(Long questionarioId);
	Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Date periodoIni, Date periodoFim, Long turmaId, Questionario questionario)throws Exception;
	Collection<PerguntaFichaMedica> montaImpressaoFichaMedica(Long id, Long colaboradorQuestionarioId, Map<String, Object> parametros);
	public Collection<PerguntaFichaMedica> montaImpressaoAvaliacaoRespondida(Long colaboradorQuestionarioId, Map<String, Object> parametros);
	public Collection<Questionario> findQuestionario(Long colaboradorId);
	public void enviaEmailNaoRespondida(Empresa empresa, Long questionarioId) throws Exception;
	public Collection<PerguntaFichaMedica> montaPerguntasComRespostas(Collection<Pergunta> perguntas, Collection<ColaboradorResposta> colaboradorRespostas);
	public Collection<Questionario> findQuestionarioByTurmaRealizadaPorUsuario(Long usuarioId);
}