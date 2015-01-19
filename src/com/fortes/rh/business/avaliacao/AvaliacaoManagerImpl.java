package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;

public class AvaliacaoManagerImpl extends GenericManagerImpl<Avaliacao, AvaliacaoDao> implements AvaliacaoManager
{
	private RespostaManager respostaManager;
	private PerguntaManager perguntaManager;
	private MensagemManager mensagemManager;
	private QuestionarioManager questionarioManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	
	public Collection<Avaliacao> findAllSelect(Integer page, Integer pagingSize, Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo) 
	{	
		return getDao().findAllSelect(page, pagingSize, empresaId, ativo, modeloAvaliacao, titulo);
	}

	public Integer getCount(Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo) 
	{	
		return getDao().getCount(empresaId, ativo, modeloAvaliacao, titulo);
	}
	
	public Collection<QuestionarioRelatorio> getQuestionarioRelatorio(Avaliacao avaliacao) {
		
		Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(avaliacao.getId());
		
		//trata as perguntas para substituir #AVALIADO# por AVALIADO (para impressão)
		for (Pergunta pergunta : perguntas)
			perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, "AVALIADO");

        QuestionarioRelatorio questionarioRelatorio = new QuestionarioRelatorio();
        questionarioRelatorio.setAvaliacaoExperiencia(avaliacao);
        questionarioRelatorio.setPerguntas(perguntas);

        Collection<QuestionarioRelatorio> questionarioRelatorios = new ArrayList<QuestionarioRelatorio>();
        questionarioRelatorios.add(questionarioRelatorio);

        return questionarioRelatorios;
	}

	public void enviaLembrete() 
	{
		gerenciadorComunicacaoManager.enviaMensagemLembretePeriodoExperiencia();
	}
	
	public String montaObsAvaliadores(Collection<ColaboradorResposta> colaboradorRespostas)
	{
		String obs = "";//importante para decisão do relatório
		Map<Long, String> obsAvaliadoresMap = new HashMap<Long, String>();
		
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
			if(colaboradorResposta.getColaboradorQuestionario() != null && StringUtils.isNotBlank(colaboradorResposta.getColaboradorQuestionario().getObservacao()))
			{
				if(!obsAvaliadoresMap.containsKey(colaboradorResposta.getColaboradorQuestionario().getId()))
				{
					obsAvaliadoresMap.put(colaboradorResposta.getColaboradorQuestionario().getId(), colaboradorResposta.getColaboradorQuestionario().getObservacao());
					obs += colaboradorResposta.getColaboradorQuestionario().getNomeComercialAvaliadoOuAvalidor() + ": " + colaboradorResposta.getColaboradorQuestionario().getObservacao() + "\n\n";
				}
			}
		}
		
		return obs;
	}
	
	public Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] areaIds, Date periodoIni, Date periodoFim, Avaliacao avaliacao, Long empresaId) throws Exception
    {
        Collection<ResultadoQuestionario> resultadoQuestionarios = new ArrayList<ResultadoQuestionario>();
        Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
        Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findInPerguntaIdsAvaliacao(perguntasIds, areaIds, periodoIni, periodoFim, empresaId);

        if(colaboradorRespostas.isEmpty())
        	throw new Exception("Nenhuma pergunta foi respondida.");
        
        Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(perguntasIds, null, areaIds, null, periodoIni, periodoFim, false, null, empresaId);
        
        avaliacao.setTotalColab(questionarioManager.countColaborador(colaboradorRespostas)); 
        Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(perguntasIds, null, areaIds, null, periodoIni, periodoFim, false, null, avaliacao.getTotalColab(), empresaId);
        percentuaisDeRespostas.addAll(calculaPercentualRespostasMultiplas);
        
        resultadoQuestionarios = questionarioManager.montaResultadosQuestionarios(perguntas, respostas, colaboradorRespostas, percentuaisDeRespostas, false);
        
        return resultadoQuestionarios;
    }
	
	public Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId, Long... perguntaIds) {

		return getDao().getPontuacaoMaximaDaPerformance(avaliacaoId, perguntaIds);
	}

	public void setPerguntaManager(PerguntaManager perguntaManager) {
		this.perguntaManager = perguntaManager;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager) {
		this.questionarioManager = questionarioManager;
	}

	public void setRespostaManager(RespostaManager respostaManager) {
		this.respostaManager = respostaManager;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager) {
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public void clonar(Long id, Long... empresasIds)
	{
		if (empresasIds != null && empresasIds.length > 0)
		{
			for (Long empresaId : empresasIds)
			{
				Empresa empresa = new Empresa();
				empresa.setId(empresaId);
				
				Avaliacao avaliacao = (Avaliacao) getDao().findById(id).clone();
				
				if(!empresaId.equals(avaliacao.getEmpresa().getId()))
					avaliacao.setPeriodoExperiencia(periodoExperienciaManager.clonarPeriodoExperiencia(avaliacao.getPeriodoExperiencia().getId(), empresa));
				
				avaliacao.setEmpresa(empresa);
				avaliacao.setTitulo(avaliacao.getTitulo() + " (Clone)");
				avaliacao.setId(null);
				save(avaliacao);
				
				perguntaManager.clonarPerguntas(id, null, avaliacao);
			}
		}
		else
		{
			Avaliacao avaliacao = (Avaliacao) getDao().findById(id).clone();
			avaliacao.setTitulo(avaliacao.getTitulo() + " (Clone)");
			avaliacao.setId(null);
			save(avaliacao);
			
			perguntaManager.clonarPerguntas(id, null, avaliacao);
		}
	}
	
	public Collection<Avaliacao> findPeriodoExperienciaIsNull(char acompanhamentoExperiencia, Long empresaId) 
	{
		return getDao().findPeriodoExperienciaIsNull(acompanhamentoExperiencia, empresaId);
	}

	public Collection<Avaliacao> findAllSelectComAvaliacaoDesempenho(Long empresaId, boolean ativa) 
	{
		return getDao().findAllSelectComAvaliacaoDesempenho(empresaId, ativa);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
	
	@Override
	public void remove(Long avaliacaoId) 
	{
		Integer qtdColaboradorAvalizacaoRespondida = colaboradorRespostaManager.countColaboradorAvaliacaoRespondida(avaliacaoId);
		if(qtdColaboradorAvalizacaoRespondida == 0)
			colaboradorPeriodoExperienciaAvaliacaoManager.removeByAvaliacao(avaliacaoId);

		perguntaManager.removerPerguntasAspectosDaAvaliacao(avaliacaoId);
		mensagemManager.removeByAvaliacaoId(avaliacaoId);
		
		super.remove(avaliacaoId);
	}

	public void setColaboradorPeriodoExperienciaAvaliacaoManager(ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager) 
	{
		this.colaboradorPeriodoExperienciaAvaliacaoManager = colaboradorPeriodoExperienciaAvaliacaoManager;
	}

	public void setMensagemManager(MensagemManager mensagemManager) {
		this.mensagemManager = mensagemManager;
	}
	
	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager){
		this.periodoExperienciaManager = periodoExperienciaManager;
	}
}