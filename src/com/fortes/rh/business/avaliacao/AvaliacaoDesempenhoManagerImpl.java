package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
import com.fortes.rh.model.avaliacao.Avaliacao;
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
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.Mail;
import com.fortes.web.tags.CheckBox;

public class AvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<AvaliacaoDesempenho, AvaliacaoDesempenhoDao> implements AvaliacaoDesempenhoManager
{
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private PerguntaManager perguntaManager;
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private QuestionarioManager questionarioManager;
	private ColaboradorManager colaboradorManager;
	private AvaliacaoManager avaliacaoManager;
	private Mail mail;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao) 
	{
		return getDao().findAllSelect(empresaId, ativa, tipoModeloAvaliacao);
	}

	public void clonar(Long avaliacaoDesempenhoId, Long... empresasIds) throws Exception 
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getDao().findByIdProjection(avaliacaoDesempenhoId);
		Long avaliacaoId = avaliacaoDesempenho.getAvaliacao().getId();
		boolean liberada = avaliacaoDesempenho.isLiberada();
		Collection<ColaboradorQuestionario> participantes = colaboradorQuestionarioManager.findByAvaliacaoDesempenho(avaliacaoDesempenhoId, null);
		
		for (Long empresaId : empresasIds)
		{
			AvaliacaoDesempenho avaliacaoDesempenhoClone = (AvaliacaoDesempenho) avaliacaoDesempenho.clone();
			
			// se for para outra empresa o modelo deve ser clonado
			if (empresaId != avaliacaoDesempenhoClone.getAvaliacao().getEmpresa().getId())
			{
				Empresa empresa = new Empresa();
				empresa.setId(empresaId);
				
				Avaliacao avaliacao = (Avaliacao) avaliacaoManager.findById(avaliacaoId).clone();
				avaliacao.setEmpresa(empresa);
				avaliacao.setTitulo(avaliacao.getTitulo() + "(Clone)");
				avaliacao.setId(null);
				avaliacaoManager.save(avaliacao);
				
				perguntaManager.clonarPerguntas(avaliacaoId, null, avaliacao);
				
				avaliacaoDesempenhoClone.setAvaliacao(avaliacao);
			}
			
			avaliacaoDesempenhoClone.setId(null);
			avaliacaoDesempenhoClone.setTitulo(avaliacaoDesempenhoClone.getTitulo() + " (Clone)");
			avaliacaoDesempenhoClone.setLiberada(false);
			
			save(avaliacaoDesempenhoClone);

			// só clona os participantes se for para a mesma empresa
			if (empresaId.equals(avaliacaoDesempenho.getAvaliacao().getEmpresa().getId()))
				colaboradorQuestionarioManager.clonar(participantes, avaliacaoDesempenhoClone, liberada);
		}
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
		Integer qtdAvaliadores = colaboradorQuestionarioManager.getQtdavaliadores(avaliacaoDesempenho.getId());
		
		for (Long avaliadoId : avaliadosIds)
		{
			Collection<Pergunta> perguntas = perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoDesempenho.getAvaliacao().getId(), null, null, agruparPorAspectos);
			Long[] perguntasIds = new CollectionUtil<Pergunta>().convertCollectionToArrayIds(perguntas);
			Map<Long, Integer> pontuacoesMaximasPerguntas = perguntaManager.getPontuacoesMaximas(perguntasIds); 
			Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
			
			Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findByAvaliadoAndAvaliacaoDesempenho(avaliadoId, avaliacaoDesempenho.getId());
			
			if (colaboradorRespostas.isEmpty())
				continue;
			
			Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(avaliadoId, avaliacaoDesempenho.getId());
			Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(avaliadoId, avaliacaoDesempenho.getId());
			percentuaisDeRespostas.addAll(percentuaisDeRespostasMultiplas);
			
			resultadoQuestionarios.addAll(questionarioManager.montaResultadosAvaliacaoDesempenho(perguntas, pontuacoesMaximasPerguntas, respostas, avaliadoId, colaboradorRespostas, percentuaisDeRespostas, avaliacaoDesempenho, qtdAvaliadores));        	
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
	
	public Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId) 
	{
		return getDao().findIdsAvaliacaoDesempenho(avaliacaoId);
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

	public Collection<CheckBox> populaCheckBox(Long empresaId, boolean ativa, char tipoModeloAvaliacao) {
		try
		{
			Collection<AvaliacaoDesempenho> avaliacoesDesempenho = getDao().findAllSelect(empresaId, ativa, tipoModeloAvaliacao);
			return CheckListBoxUtil.populaCheckListBox(avaliacoesDesempenho, "getId", "getTitulo");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}
}
