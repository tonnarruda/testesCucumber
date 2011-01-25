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
	
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa) {
		return getDao().findAllSelect(empresaId, ativa);
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

	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada)
	{
		return getDao().findByAvaliador(avaliadorId, liberada);
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
                corpo.append("Existe Avaliação de Desempenho para ser respondida.<br>Por favor acesse <a href=\" "+ parametros.getAppUrl() + "\">Fortes RH</a>") ;

                mail.send(empresa, parametros, "[Fortes RH] Lembrete responder Avaliação de Desempenho", corpo.toString(), avaliador.getContato().getEmail());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
