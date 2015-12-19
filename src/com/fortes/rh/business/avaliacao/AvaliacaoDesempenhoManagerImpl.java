package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.CheckBox;

public class AvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<AvaliacaoDesempenho, AvaliacaoDesempenhoDao> implements AvaliacaoDesempenhoManager
{
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private PerguntaManager perguntaManager;
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private QuestionarioManager questionarioManager;
	private AvaliacaoManager avaliacaoManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ColaboradorManager colaboradorManager;
	private ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager;
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager;
	
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao) 
	{
		return getDao().findAllSelect(empresaId, ativa, tipoModeloAvaliacao);
	}

	public void clonar(Long avaliacaoDesempenhoId, boolean clonarParticipantes, Long... empresasIds) throws Exception 
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getDao().findByIdProjection(avaliacaoDesempenhoId);
		Long avaliacaoId = avaliacaoDesempenho.getAvaliacao().getId();
		
		for (Long empresaId : empresasIds)
		{
			AvaliacaoDesempenho avaliacaoDesempenhoClone = (AvaliacaoDesempenho) avaliacaoDesempenho.clone();
			
			// se for para outra empresa o modelo deve ser clonado
			if (!empresaId.equals(avaliacaoDesempenhoClone.getAvaliacao().getEmpresa().getId()))
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
			
			if (clonarParticipantes)
				colaboradorQuestionarioManager.clonarParticipantes(avaliacaoDesempenho, avaliacaoDesempenhoClone);
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

	public void liberarOrBloquear(AvaliacaoDesempenho avaliacaoDesempenho, boolean liberar) throws Exception
	{
		if (!liberar) {
			Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findRespondidasByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
			if (!colaboradorQuestionarios.isEmpty())
				throw new AvaliacaoRespondidaException("Não foi possível bloquear, pois já existem respostas para essa avaliação.");
			else if ( configuracaoCompetenciaAvaliacaoDesempenhoManager.existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenho.getId()) )
				configuracaoCompetenciaAvaliacaoDesempenhoManager.removeByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		}
		
		getDao().liberarOrBloquear(avaliacaoDesempenho.getId(), liberar);
	}

	public void remover(Long avaliacaoDesempenhoId) throws Exception 
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findRespondidasByAvaliacaoDesempenho(avaliacaoDesempenhoId);
		if (!colaboradorQuestionarios.isEmpty())
			throw new AvaliacaoRespondidaException("Não foi possível excluir, pois já existem respostas para essa avaliação");
		
		colaboradorQuestionarioManager.excluirColaboradorQuestionarioByAvaliacaoDesempenho(avaliacaoDesempenhoId);
		participanteAvaliacaoDesempenhoManager.removeNotIn(null, avaliacaoDesempenhoId, null);
		remove(avaliacaoDesempenhoId);
	}

	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds)
	{
		return getDao().findByAvaliador(avaliadorId, liberada, empresasIds);
	}
	
	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Collection<ResultadoAvaliacaoDesempenho> montaResultado(Collection<Long> avaliadosIds, AvaliacaoDesempenho avaliacaoDesempenho, boolean agruparPorAspectos, boolean desconsiderarAutoAvaliacao) throws ColecaoVaziaException
	{
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		Integer qtdAvaliadores = null;
		
		for (Long avaliadoId : avaliadosIds)
		{
			qtdAvaliadores = colaboradorQuestionarioManager.getQtdAvaliadores(avaliacaoDesempenho.getId(), avaliadoId, desconsiderarAutoAvaliacao);
			
			Collection<Pergunta> perguntas = perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoDesempenho.getAvaliacao().getId(), null, null, agruparPorAspectos);
			Long[] perguntasIds = new CollectionUtil<Pergunta>().convertCollectionToArrayIds(perguntas);
			Map<Long, Integer> pontuacoesMaximasPerguntas = perguntaManager.getPontuacoesMaximas(perguntasIds); 
			Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
			
			Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findByAvaliadoAndAvaliacaoDesempenho(avaliadoId, avaliacaoDesempenho.getId(), desconsiderarAutoAvaliacao);
			
			if (colaboradorRespostas.isEmpty())
				continue;
			
			Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(avaliadoId, avaliacaoDesempenho.getId(), desconsiderarAutoAvaliacao);
			Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(avaliadoId, avaliacaoDesempenho.getId(), desconsiderarAutoAvaliacao);
			percentuaisDeRespostas.addAll(percentuaisDeRespostasMultiplas);
			
			resultadoQuestionarios.addAll(questionarioManager.montaResultadosAvaliacaoDesempenho(perguntas, pontuacoesMaximasPerguntas, respostas, avaliadoId, colaboradorRespostas, percentuaisDeRespostas, avaliacaoDesempenho, qtdAvaliadores, desconsiderarAutoAvaliacao));        	
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

	public void enviarLembrete(Long avaliacaoDesempenhoId, Empresa empresa)
	{
		gerenciadorComunicacaoManager.enviarLembreteAvaliacaoDesempenho(avaliacaoDesempenhoId, empresa);
	}
	
	public void enviarLembreteAoLiberar(Long avaliacaoDesempenhoId, Empresa empresa)
	{
		gerenciadorComunicacaoManager.enviarLembreteResponderAvaliacaoDesempenhoAoLiberar(avaliacaoDesempenhoId, empresa);
	}
	
	public Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId) 
	{
		return getDao().findIdsAvaliacaoDesempenho(avaliacaoId);
	}
	
	public Integer findCountTituloModeloAvaliacao(Integer page,Integer pagingSize, Date periodoInicial, Date periodoFinal,Long empresaId, String nomeBusca, Long avaliacaoId, Boolean liberada) 
	{
		return getDao().findCountTituloModeloAvaliacao(page, pagingSize, periodoInicial, periodoFinal, empresaId, nomeBusca, avaliacaoId, liberada);
	}

	public Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String nomeBusca, Long avaliacaoId, Boolean liberada) 
	{
		return getDao().findTituloModeloAvaliacao(page, pagingSize, periodoInicial, periodoFinal, empresaId, nomeBusca, avaliacaoId, liberada);
	}

	public Collection<CheckBox> populaCheckBox(Long empresaId, boolean ativa) {
		try
		{
			Collection<AvaliacaoDesempenho> avaliacoesDesempenho = getDao().findAllSelect(empresaId, ativa, null);
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

	public void liberarEmLote(String[] avaliacoesCheck, Empresa empresa) throws Exception 
	{
		if(avaliacoesCheck != null)
		{
			String fortesException = "";
			for (String avaliacaoId : avaliacoesCheck) 
			{
				if(StringUtils.isNotEmpty(avaliacaoId))
				{
					Long adId = new Long(avaliacaoId);
					Collection<Colaborador> avaliados = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(adId, true, null);
					Collection<Colaborador> avaliadores = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(adId, false, null);

					AvaliacaoDesempenho  avaliacaoDesempenho = getDao().findByIdProjection(adId);
					
					colaboradorQuestionarioManager.desassociarParticipantes(avaliacaoDesempenho);
					
					if (avaliados.isEmpty() || avaliadores.isEmpty())
					{
						fortesException += "- " + avaliacaoDesempenho.getTitulo() + "<br /> ";
						continue;
					}
					
					if (!avaliacaoDesempenho.isPermiteAutoAvaliacao() && avaliados.size() == 1 && avaliadores.size() == 1)
					{
						if (((Colaborador)avaliados.toArray()[0]).equals((Colaborador)avaliadores.toArray()[0]))
							fortesException += "- " + avaliacaoDesempenho.getTitulo() + "<br /> ";
						
						continue;
					}

					if(fortesException.isEmpty())
						liberarOrBloquear(avaliacaoDesempenho, true);
				}
			}
			
			if(!fortesException.isEmpty())
				throw new FortesException(fortesException);
		}
	}
	
	public Collection<AvaliacaoDesempenho> findAvaliacaoDesempenhoBloqueadaComConfiguracaoCompetencia(Long configuracaoNivelCompetenciaFaixaSalarialId) {
		return getDao().findAvaliacaoDesempenhoBloqueadaComConfiguracaoCompetencia(configuracaoNivelCompetenciaFaixaSalarialId);
	}
	
	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public ColaboradorManager getColaboradorManager() {
		return colaboradorManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setParticipanteAvaliacaoDesempenhoManager(ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager) {
		this.participanteAvaliacaoDesempenhoManager = participanteAvaliacaoDesempenhoManager;
	}

	public void setConfiguracaoCompetenciaAvaliacaoDesempenhoManager(ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager) {
		this.configuracaoCompetenciaAvaliacaoDesempenhoManager = configuracaoCompetenciaAvaliacaoDesempenhoManager;
	}
}
