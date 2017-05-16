package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaCriterioManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
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
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
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
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaCriterioManager configuracaoNivelCompetenciaCriterioManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	
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
			if(!empresaId.equals(avaliacaoDesempenho.getEmpresa().getId()) ){
 				Empresa empresa = new Empresa();
 				empresa.setId(empresaId);
				avaliacaoDesempenhoClone.setEmpresa(empresa);
 				
				if( avaliacaoDesempenhoId != null){
					
					Avaliacao avaliacao = (Avaliacao) avaliacaoManager.findById(avaliacaoId).clone();
					avaliacao.setEmpresa(empresa);
					avaliacao.setTitulo(avaliacao.getTitulo() + "(Clone)");
					avaliacao.setId(null);
					avaliacaoManager.save(avaliacao);
					
					perguntaManager.clonarPerguntas(avaliacaoId, null, avaliacao);
					
					avaliacaoDesempenhoClone.setAvaliacao(avaliacao);
				}
 			}
			if(!(avaliacaoDesempenhoClone.getAvaliacao() != null && avaliacaoDesempenhoClone.getAvaliacao().getId() != null))
				avaliacaoDesempenhoClone.setAvaliacao(null);
			
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
			
			resultadoQuestionarios.addAll(questionarioManager.montaResultadosAvaliacaoDesempenho(perguntas, pontuacoesMaximasPerguntas, respostas, avaliadoId, colaboradorRespostas, percentuaisDeRespostas, avaliacaoDesempenho, qtdAvaliadores, desconsiderarAutoAvaliacao, false));        	
		}
		
		if (resultadoQuestionarios.isEmpty())
			throw new ColecaoVaziaException("Nenhuma avaliação foi respondida para os colaboradores informados.");

        return resultadoQuestionarios;
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
			return CheckListBoxUtil.populaCheckListBox(avaliacoesDesempenho, "getId", "getTitulo", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public void liberarEmLote(String[] avaliacoesCheck) throws Exception 
	{
		if(avaliacoesCheck != null)
		{
			String fortesException = "";
			for (String avaliacaoId : avaliacoesCheck) 
			{
				if(StringUtils.isNotEmpty(avaliacaoId))
				{
					Long adId = new Long(avaliacaoId);
					Collection<Colaborador> avaliados = participanteAvaliacaoDesempenhoManager.findColaboradoresParticipantes(adId, TipoParticipanteAvaliacao.AVALIADO);
					Collection<Colaborador> avaliadores = participanteAvaliacaoDesempenhoManager.findColaboradoresParticipantes(adId, TipoParticipanteAvaliacao.AVALIADOR);

					AvaliacaoDesempenho  avaliacaoDesempenho = getDao().findByIdProjection(adId);
					
					if (avaliados.isEmpty() || avaliadores.isEmpty())
					{
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

	public ResultadoAvaliacaoDesempenho getResultadoAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho, Long avaliadoId, Long empresaId) 
	{
		Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDoColaborador = configuracaoNivelCompetenciaManager.findCompetenciasAndPesos(avaliacaoDesempenho.getId(), avaliadoId);
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho();
		
		if(configNiveisCompetenciasDoColaborador.size() == 0)
			return resultadoAvaliacaoDesempenho;

		resultadoAvaliacaoDesempenho.setColaborador(colaboradorManager.findByIdHistoricoAtual(avaliadoId, true));
		resultadoAvaliacaoDesempenho.setAvaliacaoPermiteAutoAvaliacao(avaliacaoDesempenho.isPermiteAutoAvaliacao());
		Collection<Competencia> competencias = new ArrayList<Competencia>();
		
		ConfiguracaoNivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial = ((ConfiguracaoNivelCompetencia) (configNiveisCompetenciasDoColaborador.toArray()[0])).getConfiguracaoNivelCompetenciaFaixaSalarial();
		Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDaFaixaSalarial = configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaFaixaSalarial(nivelCompetenciaFaixaSalarial.getId());
		Double ordemMaxima = nivelCompetenciaManager.getOrdemMaximaByNivelCompetenciaHistoricoId(nivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId()); 
		
		Collection<NivelCompetencia> niveisCompetencias = nivelCompetenciaManager.findAllSelect(empresaId, nivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), null);
		
		Competencia competencia;
		Integer pesoAvaliador, somaPesoAvaliadores;
		Double pesoPorPontuacaoObtidaAvaliadorAcumulado;
		Double pontuacaoObtidaColaborador = 0.0;
		Double pesoPorPontuacaoAutoAvaliacao = 0.0;
		Integer somaPesosCompetenciasDaAutoAvaliacao = 0;
		
		for (ConfiguracaoNivelCompetencia cncFaixa : configNiveisCompetenciasDaFaixaSalarial){
			pesoPorPontuacaoObtidaAvaliadorAcumulado = 0.0;
			somaPesoAvaliadores = 0;
			
			for (ConfiguracaoNivelCompetencia cncColaborador : configNiveisCompetenciasDoColaborador){
				if(cncFaixa.getCompetenciaId().equals(cncColaborador.getCompetenciaId()) && cncFaixa.getTipoCompetencia().equals(cncColaborador.getTipoCompetencia()) ){

					pesoAvaliador = cncColaborador.getAvaliadorPeso() != null ? cncColaborador.getAvaliadorPeso() : 1;
					Double pontuacaoDaCompetencia = 0.0;
					Collection<ConfiguracaoNivelCompetenciaCriterio> cncCriterios = configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(cncColaborador.getId(), nivelCompetenciaFaixaSalarial.getId());
					
					if ( cncCriterios != null && cncCriterios.size() > 0 ) {
						Double soma = 0.0;
						for (ConfiguracaoNivelCompetenciaCriterio criterioAvaliacaoCompetencia : cncCriterios) {
							soma+=criterioAvaliacaoCompetencia.getNivelCompetencia().getOrdem();
						}
						
						pontuacaoDaCompetencia += soma / cncCriterios.size();
					} else 
						pontuacaoDaCompetencia += cncColaborador.getNivelCompetenciaColaborador().getOrdem();
					
					pontuacaoObtidaColaborador = pontuacaoDaCompetencia * cncFaixa.getPesoCompetencia();
					pesoPorPontuacaoObtidaAvaliadorAcumulado += pesoAvaliador * pontuacaoObtidaColaborador;
					somaPesoAvaliadores += pesoAvaliador;
				
					if(avaliacaoDesempenho.isPermiteAutoAvaliacao() && cncColaborador.getAvaliadorId().equals(avaliadoId)) {
						pesoPorPontuacaoAutoAvaliacao += pontuacaoObtidaColaborador;
						somaPesosCompetenciasDaAutoAvaliacao+=cncFaixa.getPesoCompetencia();
					}
				}
			}
			
			if(somaPesoAvaliadores != 0){
				competencia = new Competencia(cncFaixa.getCompetenciaDescricao(), (pesoPorPontuacaoObtidaAvaliadorAcumulado/(somaPesoAvaliadores*ordemMaxima*cncFaixa.getPesoCompetencia()))*100,
						cncFaixa.getPesoCompetencia(), cncFaixa.getNivelCompetencia().getDescricao());
				competencia.setCompetenciaAbaixoDoNivelExigido(competencia.getPerformance() < ((cncFaixa.getNivelCompetencia().getOrdem()/ordemMaxima)*100));
				setDescricaoNivelCompetencia(competencia, niveisCompetencias, ordemMaxima);
				competencias.add(competencia);
			}
		}
		
		if ( avaliacaoDesempenho.isPermiteAutoAvaliacao() )
			resultadoAvaliacaoDesempenho.setPerformanceAutoAvaliacao(( pesoPorPontuacaoAutoAvaliacao / (ordemMaxima*somaPesosCompetenciasDaAutoAvaliacao) ) * 100);
		
		resultadoAvaliacaoDesempenho.setCompetencias(competencias);

		Double produtividade = participanteAvaliacaoDesempenhoManager.findByAvalDesempenhoIdAbadColaboradorId(avaliacaoDesempenho.getId(), avaliadoId, TipoParticipanteAvaliacao.AVALIADO);
		
		if(produtividade != null)
			resultadoAvaliacaoDesempenho.setProdutividade(produtividade * 10);
		
		return resultadoAvaliacaoDesempenho;
	}
	
	public void setDescricaoNivelCompetencia(Competencia competencia, Collection<NivelCompetencia> niveisCompetencias, Double ordemMaxima){
		Double ordemObtida = (competencia.getPerformance()*ordemMaxima)/100;
		
		for (NivelCompetencia nivelCompetencia : niveisCompetencias) {
			if(new Double(nivelCompetencia.getOrdem()) <= ordemObtida)
				competencia.setDescricaoNivelCompetencia(nivelCompetencia.getDescricao());
		}
	}

	public void saveOrUpdateRespostaAvDesempenho(Usuario usuario, Empresa empresa, Colaborador colaborador, ColaboradorQuestionario colaboradorQuestionario, AvaliacaoDesempenho avaliacaoDesempenho, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Collection<Pergunta> perguntas, Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais) throws FortesException {
		Collection<ColaboradorResposta> colaboradorRespostasDasPerguntas = new ArrayList<ColaboradorResposta>();
		if(colaboradorQuestionario.getAvaliacao()!=null)
			colaboradorRespostasDasPerguntas = perguntaManager.getColaboradorRespostasDasPerguntas(perguntas);	

		colaboradorRespostaManager.update(colaboradorRespostasDasPerguntas, colaboradorQuestionario, usuario.getId(), empresa.getId(), niveisCompetenciaFaixaSalariais);
		if (colaboradorQuestionario.getAvaliacao()==null || colaboradorQuestionario.getAvaliacao().isAvaliarCompetenciasCargo()) {
			ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = null;
			if(colaboradorQuestionario.getConfiguracaoNivelCompetenciaColaborador() != null && colaboradorQuestionario.getConfiguracaoNivelCompetenciaColaborador().getId() != null){
				configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findById(colaboradorQuestionario.getConfiguracaoNivelCompetenciaColaborador().getId());
				if(configuracaoNivelCompetenciaColaborador == null)
					throw new FortesException("As configurações do nível de competência foram removidas ou não existem.");
			}else{
				configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
				configuracaoNivelCompetenciaColaborador.setData(new Date());
				configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
				configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(colaboradorQuestionario);
				configuracaoNivelCompetenciaColaborador.setAvaliador(colaboradorQuestionario.getAvaliador());
				configuracaoNivelCompetenciaColaborador.setFaixaSalarial(colaborador.getFaixaSalarial());

				if(configuracaoNivelCompetenciaFaixaSalarial == null)
					configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(colaborador.getFaixaSalarial().getId(), avaliacaoDesempenho.getInicio()));
				else
					configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
			}
			
			if(configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial() != null && configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getId() != null){
				configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
				colaboradorQuestionario.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
			}
			colaboradorQuestionarioManager.update(colaboradorQuestionario);
		}
	}

	public Collection<AvaliacaoDesempenho> findComCompetencia(Long empresaId) {
		return getDao().findComCompetencia(empresaId);
	}

	public boolean isExibiNivelCompetenciaExigido(Long avaliacaoDesempenhoId) {
		return getDao().isExibiNivelCompetenciaExigido(avaliacaoDesempenhoId)   ;
	}
	
	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
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
	
	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
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

	public void setConfiguracaoNivelCompetenciaManager( ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setNivelCompetenciaManager( NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}
	
	public void setConfiguracaoNivelCompetenciaCriterioManager( ConfiguracaoNivelCompetenciaCriterioManager configuracaoNivelCompetenciaCriterioManager ) {
		this.configuracaoNivelCompetenciaCriterioManager = configuracaoNivelCompetenciaCriterioManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager) {
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}

	@Override
	public Collection<AvaliacaoDesempenho> findByCncfId(Long configuracaoNivelCompetenciaFaixaSalarialId) {
		return getDao().findByCncfId(configuracaoNivelCompetenciaFaixaSalarialId);
	}

	public Collection<AvaliacaoDesempenho> findByModelo(Long modeloId) {
		return getDao().findByModelo(modeloId);
	}
}
