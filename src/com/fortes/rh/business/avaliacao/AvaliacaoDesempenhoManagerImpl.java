package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.Mail;

public class AvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<AvaliacaoDesempenho, AvaliacaoDesempenhoDao> implements AvaliacaoDesempenhoManager
{
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private PerguntaManager perguntaManager;
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private QuestionarioManager questionarioManager;
	private ColaboradorManager colaboradorManager;
	private Mail mail;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao) {
		return getDao().findAllSelect(empresaId, ativa, tipoModeloAvaliacao);
	}

	public void clonar(Long avaliacaoDesempenhoId) throws Exception 
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getDao().findByIdProjection(avaliacaoDesempenhoId);
		boolean liberada = avaliacaoDesempenho.isLiberada();
		
		avaliacaoDesempenho.setId(null);
		avaliacaoDesempenho.setTitulo(avaliacaoDesempenho.getTitulo() + " (Clone)");
		avaliacaoDesempenho.setLiberada(false);
		
		save(avaliacaoDesempenho);
		
		Collection<ColaboradorQuestionario> participantes = colaboradorQuestionarioManager.findByAvaliacaoDesempenho(avaliacaoDesempenhoId, null);
		colaboradorQuestionarioManager.clonar(participantes, avaliacaoDesempenho, liberada);
	}
	
	public void gerarAutoAvaliacoes(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> participantes)
	{
		AvaliacaoDesempenho avaliacaoDesempenhoTemp = null;
		
		for (Colaborador colaborador : participantes) 
		{
			avaliacaoDesempenhoTemp = new AvaliacaoDesempenho();
			avaliacaoDesempenhoTemp.setTitulo(avaliacaoDesempenho.getTitulo() + " - " + colaborador.getNome());
			avaliacaoDesempenhoTemp.setInicio(avaliacaoDesempenho.getInicio());
			avaliacaoDesempenhoTemp.setFim(avaliacaoDesempenho.getFim());
			avaliacaoDesempenhoTemp.setAvaliacao(avaliacaoDesempenho.getAvaliacao());
			avaliacaoDesempenhoTemp.setAnonima(false);
			avaliacaoDesempenhoTemp.setPermiteAutoAvaliacao(true);
			save(avaliacaoDesempenhoTemp);
			
			ColaboradorQuestionario colaboradorQuestionarioAvaliado = new ColaboradorQuestionario(avaliacaoDesempenhoTemp);
			colaboradorQuestionarioAvaliado.setAvaliadoOuAvaliador(colaborador.getId(), true);
			colaboradorQuestionarioManager.save(colaboradorQuestionarioAvaliado);

			ColaboradorQuestionario colaboradorQuestionarioAvaliador = new ColaboradorQuestionario(avaliacaoDesempenhoTemp);
			colaboradorQuestionarioAvaliador.setAvaliadoOuAvaliador(colaborador.getId(), false);
			colaboradorQuestionarioManager.save(colaboradorQuestionarioAvaliador);
		}
	}
	
	public AvaliacaoDesempenho findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void liberar(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception
	{
		avaliacaoDesempenho = getDao().findByIdProjection(avaliacaoDesempenho.getId());
		colaboradorQuestionarioManager.associarParticipantes(avaliacaoDesempenho);
		getDao().liberarOrBloquear(avaliacaoDesempenho.getId(), true);
	}

	public void bloquear(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception 
	{
		colaboradorQuestionarioManager.desassociarParticipantes(avaliacaoDesempenho);
		getDao().liberarOrBloquear(avaliacaoDesempenho.getId(), false);
	}

	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long empresaId)
	{
		return getDao().findByAvaliador(avaliadorId, liberada, empresaId);
	}
	
	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Collection<ResultadoAvaliacaoDesempenho> montaResultado(Collection<Long> avaliadosIds, AvaliacaoDesempenho avaliacaoDesempenho, boolean agruparPorAspectos) throws ColecaoVaziaException
	{
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		
		for (Long avaliadoId : avaliadosIds)
		{
			Collection<Pergunta> perguntas = perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoDesempenho.getAvaliacao().getId(), null, null, agruparPorAspectos);
			Long[] perguntasIds = new CollectionUtil<Pergunta>().convertCollectionToArrayIds(perguntas);
			Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
			
			Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findByAvaliadoAndAvaliacaoDesempenho(avaliadoId, avaliacaoDesempenho.getId());
			
			if (colaboradorRespostas.isEmpty())
				continue;
			
			Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(avaliadoId, avaliacaoDesempenho.getId());
			Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(avaliadoId, avaliacaoDesempenho.getId());
			percentuaisDeRespostas.addAll(percentuaisDeRespostasMultiplas);
			
			resultadoQuestionarios.addAll(questionarioManager.montaResultadosAvaliacaoDesempenho(perguntas, respostas, avaliadoId, colaboradorRespostas, percentuaisDeRespostas, avaliacaoDesempenho.isAnonima()));        	
		}
		
		if (resultadoQuestionarios.isEmpty())
			throw new ColecaoVaziaException("Nenhuma avaliação foi respondida para os colaboradores informados.");

        return resultadoQuestionarios;
	}

	public void setPerguntaManager(PerguntaManager perguntaManager) {
		this.perguntaManager = perguntaManager;
	}

	public void setRespostaManager(RespostaManager respostaManager) {
		this.respostaManager = respostaManager;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager) {
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager) {
		this.questionarioManager = questionarioManager;
	}

	public void enviarLembrete(Long id, Empresa empresa)
	{
		Collection<Colaborador> avaliadores = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(id, false, false);
    	ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
		
        for (Colaborador avaliador : avaliadores)
        {
            try
            {
                StringBuilder corpo = new StringBuilder();
                corpo.append("ATENÇÃO:<br>");
                corpo.append("Existe Avaliação de Desempenho para ser respondida.<br>Por favor acesse <a href=\" "+ parametros.getAppUrl() + "\">RH</a>") ;

                mail.send(empresa, parametros, "[RH] Lembrete responder Avaliação de Desempenho", corpo.toString(), avaliador.getContato().getEmail());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
	}
	
	public Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Long empresaId, String nomeBusca, Long avaliacaoId) 
	{
		return getDao().findTituloModeloAvaliacao(empresaId, nomeBusca, avaliacaoId);
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}
