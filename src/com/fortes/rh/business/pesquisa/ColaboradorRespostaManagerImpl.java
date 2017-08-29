package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.RespostaQuestionario;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.ConverterUtil;

public class ColaboradorRespostaManagerImpl extends GenericManagerImpl<ColaboradorResposta, ColaboradorRespostaDao> implements ColaboradorRespostaManager
{
    private PlatformTransactionManager transactionManager;
    private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
    private HistoricoColaboradorManager historicoColaboradorManager;
    private QuestionarioManager questionarioManager;
    private ColaboradorManager colaboradorManager;
    private CandidatoManager candidatoManager; // usado na auditoria
    private AvaliacaoManager avaliacaoManager;
    private UsuarioManager usuarioManager ;
    private NivelCompetenciaManager nivelCompetenciaManager; 
    private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;

	public List<Object[]> countRespostas(Long perguntaId, Long[] estabelecimentosIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long turmaId)
    {
        Long[] perguntasIds = new Long[]{perguntaId};

        return getDao().countRespostas(perguntasIds, estabelecimentosIds, areasIds, null, periodoIni, periodoFim, false, turmaId, null, null, null);
    }

    public Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Questionario questionario, Long empresaId)
    {
        return getDao().findInPerguntaIds(perguntasIds, estabelecimentosIds, areasIds, cargosIds, periodoIni, periodoFim, desligamento, turmaId, questionario, empresaId);
    }

    public Collection<ColaboradorResposta> findInPerguntaIdsAvaliacao(Long[] perguntasIds, Long[] areasIds,  Date periodoIni, Date periodoFim, Long[] empresasIds, Character tipoModeloAvaliacao, Long[] avaliacoesDesempenhoCheck)
    {
    	return getDao().findInPerguntaIdsAvaliacao(perguntasIds, areasIds, periodoIni, periodoFim, empresasIds, tipoModeloAvaliacao, avaliacoesDesempenhoCheck);
    }

    public void salvaQuestionarioRespondido(String respostas, Questionario questionario, Long colaboradorId, Long turmaId, char vinculo, Date respondidaEm, Long colaboradorQuestionarioId, boolean inserirFichaMedica) throws Exception
    {
    	Long candidatoId = null;
    	AreaOrganizacional areaOrganizacional = null;
    	Cargo cargo = null;
    	Estabelecimento estabelecimento = null;

    	if(questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && vinculo == 'A')
    		candidatoId = colaboradorId;
    	else
    	{
    		HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaboradorId);
    		areaOrganizacional = historicoColaborador.getAreaOrganizacional();
    		cargo = historicoColaborador.getFaixaSalarial().getCargo();
    		estabelecimento = historicoColaborador.getEstabelecimento();
    	}
    	
        String[] perguntasRespostas = respostas.split("¨");
        questionario = questionarioManager.findByIdProjection(questionario.getId());
        ColaboradorResposta colaboradorResposta = null;

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try
        {
        	if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA))
        		colaboradorManager.respondeuEntrevista(colaboradorId);

        	ColaboradorQuestionario colaboradorQuestionario = null;
	        if(!inserirFichaMedica)
	        {
		        if(colaboradorQuestionarioId == null)
		        {
	        		if(questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && vinculo == 'A')
		        		colaboradorQuestionario = colaboradorQuestionarioManager.findByQuestionarioCandidato(questionario.getId(), candidatoId);
		        	else
		        		colaboradorQuestionario = colaboradorQuestionarioManager.findByQuestionario(questionario.getId(), colaboradorId, turmaId);
		        }else
		        	colaboradorQuestionario = colaboradorQuestionarioManager.findById(colaboradorQuestionarioId);
            }
        
        	if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA) || questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA) || questionario.verificaTipo(TipoQuestionario.FICHAMEDICA))
        	{
        		if(colaboradorQuestionario != null && colaboradorQuestionario.getId() != null)
        		{
        			getDao().removeByColaboradorQuestionario(colaboradorQuestionario.getId());
        			colaboradorQuestionarioManager.remove(colaboradorQuestionario.getId());
        		}

        		if(questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && vinculo == 'A')
        			colaboradorQuestionario = montaColaboradorQuestionarioCandidato(questionario, candidatoId);        			
        		else
        			colaboradorQuestionario = montaColaboradorQuestionario(questionario, colaboradorId);
        		
        		if(questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA) && turmaId != null)
        		{
        			Turma turmaTmp = new Turma();
        			turmaTmp.setId(turmaId);
        			colaboradorQuestionario.setTurma(turmaTmp);
        		}
        	}

        	// Na resposta de Fichas Médicas é permitido mudar a data da resposta
        	if (questionario.verificaTipo(TipoQuestionario.FICHAMEDICA))
        	{
        		colaboradorQuestionario.setRespondidaEm(respondidaEm);
        	}
        	else  //Nos outros casos, seta a data de hoje
	    		if(!colaboradorQuestionario.getRespondida()) //Caso ainda não tenha sido respondida, seta a data da resposta
	    			colaboradorQuestionario.setRespondidaEm(new Date());
        	
    		colaboradorQuestionario.setRespondida(true);

       		if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA) || questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA) || questionario.verificaTipo(TipoQuestionario.FICHAMEDICA))
        		colaboradorQuestionario = colaboradorQuestionarioManager.save(colaboradorQuestionario);
        	else if(questionario.verificaTipo(TipoQuestionario.PESQUISA))
        		colaboradorQuestionarioManager.update(colaboradorQuestionario);

            salvaRespostas(questionario, perguntasRespostas, estabelecimento, areaOrganizacional, cargo, colaboradorResposta, colaboradorQuestionario);

            transactionManager.commit(status);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            transactionManager.rollback(status);
            throw new IntegraACException(e.getMessage());
        }
    }

	private void salvaRespostas(Questionario questionario, String[] perguntasRespostas, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Cargo cargo, ColaboradorResposta colaboradorResposta, ColaboradorQuestionario colaboradorQuestionario)
	{
		Collection<Long> respostaMultiplaEscolhaIds = new ArrayList<Long>();
		boolean multiplaEscolha = false;

		for(int i=0;i<perguntasRespostas.length;i++)
		{	
		    if(perguntasRespostas[i].substring(0,2).equals("PG"))//É uma pergunta
		    {
		        colaboradorResposta = new ColaboradorResposta();
		        Pergunta pergunta = new Pergunta();
		        pergunta.setId(Long.parseLong(perguntasRespostas[i].substring(2)));

		        colaboradorResposta.setPergunta(pergunta);
		    }
		    else if(perguntasRespostas[i].substring(0,1).equals("R"))//É uma resposta
		    {
		        if(perguntasRespostas[i].substring(1,2).equals("O"))//Objetiva
		        {
		            Resposta resposta = new Resposta();
		            resposta.setId(Long.parseLong(perguntasRespostas[i].substring(2)));
		            colaboradorResposta.setResposta(resposta);
		        }
		        else if(perguntasRespostas[i].substring(1,2).equals("M"))//Multipla escolha
		        {
		        	respostaMultiplaEscolhaIds.add(Long.parseLong(perguntasRespostas[i].substring(2)));
		        	multiplaEscolha = true;
		        }
		        else if(perguntasRespostas[i].substring(1,2).equals("N"))//Nota
		        {
		        	String nota = perguntasRespostas[i].substring(2);
		        	if(StringUtils.isNotBlank(nota))
		        		colaboradorResposta.setValor(Integer.parseInt(nota));
		        	else	
		        		colaboradorResposta.setValor(null);
		        }
		        else if(perguntasRespostas[i].substring(1,2).equals("S"))//Subjetiva
		        {
		            colaboradorResposta.setComentario(perguntasRespostas[i].substring(2));
		        }
		        else if(perguntasRespostas[i].substring(1,2).equals("C"))//Comentário da Objetiva
		        {
		            colaboradorResposta.setComentario(perguntasRespostas[i].substring(2));
		        }
		    }

		    //primeiro if: A próxima é uma pergunta, então salva o atual
		    if(((i+1) < perguntasRespostas.length && perguntasRespostas[i+1].substring(0,2).equals("PG")) || (i+1) >= perguntasRespostas.length)
		    {
		    	if(multiplaEscolha)
		    	{
		    		for (Long multiplaEscolhaId : respostaMultiplaEscolhaIds)
					{
			            salvaColaboradorRespostaMultiplaEscolha(questionario, estabelecimento, areaOrganizacional, cargo, colaboradorResposta, colaboradorQuestionario, multiplaEscolhaId);		    		
					}
		    		
		    		respostaMultiplaEscolhaIds = new ArrayList<Long>();
		    		multiplaEscolha = false;
		    	}
		    	else
		    	{
		    		saveColaboradorResposta(questionario, estabelecimento, areaOrganizacional, cargo, colaboradorResposta, colaboradorQuestionario);		    		
		    	}
		    }
		}
	}

	private void salvaColaboradorRespostaMultiplaEscolha(Questionario questionario, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Cargo cargo,
			ColaboradorResposta colaboradorResposta, ColaboradorQuestionario colaboradorQuestionario, Long multiplaEscolhaId)
	{
		Resposta resposta = new Resposta();
		resposta.setId(multiplaEscolhaId);
       
		ColaboradorResposta colaboradorRespostaMultipla = new ColaboradorResposta();
		colaboradorRespostaMultipla.setComentario(colaboradorResposta.getComentario());
		colaboradorRespostaMultipla.setPergunta(colaboradorResposta.getPergunta());
		colaboradorRespostaMultipla.setResposta(resposta);
		
		saveColaboradorResposta(questionario, estabelecimento, areaOrganizacional, cargo, colaboradorRespostaMultipla, colaboradorQuestionario);
	}

	private void saveColaboradorResposta(Questionario questionario, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Cargo cargo, ColaboradorResposta colaboradorResposta, ColaboradorQuestionario colaboradorQuestionario)
	{
		colaboradorResposta.setAreaOrganizacional(areaOrganizacional);
		colaboradorResposta.setCargo(cargo);
		colaboradorResposta.setEstabelecimento(estabelecimento);

		if( !questionario.isAnonimo())
		    colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		
		save(colaboradorResposta);
	}

	private ColaboradorQuestionario montaColaboradorQuestionario(Questionario questionario, Long colaboradorId)
	{
		ColaboradorQuestionario colaboradorQuestionario;
		Colaborador colaborador = new Colaborador();
		colaborador.setId(colaboradorId);

		colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);

		return colaboradorQuestionario;
	}
	
	private ColaboradorQuestionario montaColaboradorQuestionarioCandidato(Questionario questionario, Long candidatoId)
	{
		ColaboradorQuestionario colaboradorQuestionario;
		Candidato candidato = new Candidato();
		candidato.setId(candidatoId);
		
		colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setCandidato(candidato);
		colaboradorQuestionario.setQuestionario(questionario);
		
		return colaboradorQuestionario;
	}

    public Collection<ColaboradorResposta> findRespostasColaborador(Long colaboradorQuestionarioId, Boolean aplicarPorAspecto)
    {
        Collection<ColaboradorResposta> colaboradorRespostas = getDao().findRespostasColaborador(colaboradorQuestionarioId, aplicarPorAspecto);
        int contador = 1;
        Collection<ColaboradorResposta> colaboradorRespostaOrdenadas = new ArrayList<ColaboradorResposta>();

        if (aplicarPorAspecto)
        {
            for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
            {
                if (colaboradorResposta.getPergunta().getAspecto() == null || colaboradorResposta.getPergunta().getAspecto().getNome() == null)
                {
                    colaboradorResposta.getPergunta().setOrdem(contador);
                    colaboradorRespostaOrdenadas.add(colaboradorResposta);
                    contador++;
                }
            }

            for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
            {
                if (colaboradorResposta.getPergunta().getAspecto() != null && colaboradorResposta.getPergunta().getAspecto().getNome() != null)
                {
                    colaboradorResposta.getPergunta().setOrdem(contador);
                    colaboradorRespostaOrdenadas.add(colaboradorResposta);
                    contador++;
                }
            }

            return colaboradorRespostaOrdenadas;
        }
        else
        {
            return colaboradorRespostas;
        }
    }
    
    public ColaboradorQuestionarioManager getColaboradorQuestionarioManager()
	{
		return this.colaboradorQuestionarioManager;
	}

    public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
    {
        this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

	public Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId, Long colaboradorQuestionarioId)
	{
		return getDao().findByQuestionarioColaborador(questionarioId, colaboradorId, turmaId, colaboradorQuestionarioId);
	}

	public Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId, Long colaboradorQuestionarioId)
	{
		return getDao().findByQuestionarioCandidato(questionarioId, candidatoId, colaboradorQuestionarioId);
	}

	public void removeFicha(Long colaboradorQuestionarioId) throws Exception
	{
    	getDao().removeByColaboradorQuestionario(colaboradorQuestionarioId);
    	colaboradorQuestionarioManager.remove(colaboradorQuestionarioId);
	}
	
	public void removeFichas(Long[] colaboradorQuestionarioIds) 
	{
		if(colaboradorQuestionarioIds != null && colaboradorQuestionarioIds.length > 0)
		{
			getDao().removeByColaboradorQuestionario(colaboradorQuestionarioIds);
			colaboradorQuestionarioManager.remove(colaboradorQuestionarioIds);
		}
	}

	public Collection<ColaboradorResposta> findByColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario, Long questionarioId)
	{
		Collection<ColaboradorResposta> colaboradorRespostas = null; 
		
		if(colaboradorQuestionario.getCandidato() != null && colaboradorQuestionario.getCandidato().getId() != null)
			colaboradorRespostas = findByQuestionarioCandidato(questionarioId, colaboradorQuestionario.getCandidato().getId(), colaboradorQuestionario.getId());
		else if(colaboradorQuestionario.getColaborador() != null && colaboradorQuestionario.getColaborador().getId() != null)
			colaboradorRespostas = findByQuestionarioColaborador(questionarioId, colaboradorQuestionario.getColaborador().getId(), null, colaboradorQuestionario.getId());

		return colaboradorRespostas;
	}
	
	public Collection<ColaboradorResposta> findByColaboradorQuestionario(Long id)
	{
		Collection<ColaboradorResposta> colaboradorRespostas = getDao().findByColaboradorQuestionario(id);
		
		return colaboradorRespostas;
	}
	
	/**
	 * @param usuarioLogadoId : O ID do usuario logado eh utilizado na auditoria.
	 * @param candidatoId : O ID do candidato logado no modulo externo eh utilizado na auditoria.
	 */
	public void save(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario, Long usuarioLogadoId, Long candidatoId)
	{
		ajustaEntidadesNull(colaboradorQuestionario);
		colaboradorQuestionario.setRespondida(true);
		saveRespostas(colaboradorRespostas, colaboradorQuestionario);
		colaboradorQuestionarioManager.save(colaboradorQuestionario);
		
		if (colaboradorQuestionario.getAvaliacao() != null)	{
			calculaPerformance(colaboradorQuestionario, null, null);
			colaboradorQuestionarioManager.update(colaboradorQuestionario);
		}
	}

	private void ajustaEntidadesNull(ColaboradorQuestionario colaboradorQuestionario)
	{
		if(colaboradorQuestionario.getColaborador() != null && colaboradorQuestionario.getColaborador().getId() == null)
			colaboradorQuestionario.setColaborador(null);
		if(colaboradorQuestionario.getCandidato() != null && colaboradorQuestionario.getCandidato().getId() == null)
			colaboradorQuestionario.setCandidato(null);
		if(colaboradorQuestionario.getAvaliador() != null && colaboradorQuestionario.getAvaliador().getId() == null)
			colaboradorQuestionario.setAvaliador(null);
		if(colaboradorQuestionario.getConfiguracaoNivelCompetenciaColaborador() != null && colaboradorQuestionario.getConfiguracaoNivelCompetenciaColaborador().getId() == null)
			colaboradorQuestionario.setConfiguracaoNivelCompetenciaColaborador(null);
	}

	private void saveRespostas(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario)
	{
		AreaOrganizacional areaOrganizacional = null;
		Estabelecimento estabelecimento = null;
		if(colaboradorQuestionario.getColaborador() != null && colaboradorQuestionario.getColaborador().getId() != null)
		{
			HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaboradorQuestionario.getColaborador().getId());
			
			if(historicoColaborador != null)
			{
				if(historicoColaborador.getAreaOrganizacional() != null && historicoColaborador.getAreaOrganizacional().getId() != null)
					areaOrganizacional = historicoColaborador.getAreaOrganizacional();
				if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getId() != null)
					estabelecimento = historicoColaborador.getEstabelecimento();
			}
		}
		
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
			if (colaboradorResposta.temResposta() || (colaboradorResposta.temPergunta() && colaboradorResposta.getPergunta().getTipo() == TipoPergunta.SUBJETIVA))
			{
				colaboradorResposta.setAreaOrganizacional(areaOrganizacional);
				colaboradorResposta.setEstabelecimento(estabelecimento);
				colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
				
				getDao().save(colaboradorResposta);
			}
		}
	}

	//PELO AMOR DE DEUS SE BULIR NESTE MÉTODO VERIFICAR CÁCULO DO MÉTODO calculaPerformance DE prepareResponderAvaliacaoDesempenho.ftl
	public void calculaPerformance(ColaboradorQuestionario colaboradorQuestionario, Long empresaId, Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasMarcados)
	{
		Long colaboradorQuestionarioId = colaboradorQuestionario.getId();
		
		Collection<Long> perguntasIdsComPesoNulo = new ArrayList<Long>();
		Collection<ColaboradorResposta> colaboradorRespostas = getDao().findByColaboradorQuestionario(colaboradorQuestionarioId);
		int pontuacaoMaximaQuestionario = calculaPontuacaoMaximaQuestionario(colaboradorQuestionario, colaboradorRespostas, perguntasIdsComPesoNulo);
		
		Double pontuacaoNivelCompetenciaObtida = 0.0;
		int pontuacaoMaximaNivelcompetencia = 0;
		if(configNiveisCompetenciasMarcados != null && !configNiveisCompetenciasMarcados.isEmpty())
		{
			Colaborador colaborador = colaboradorManager.findColaboradorByDataHistorico(colaboradorQuestionario.getColaborador().getId(), new Date());
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial =  configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(colaborador.getFaixaSalarial().getId(), colaboradorQuestionario.getRespondidaEm());
			
			pontuacaoNivelCompetenciaObtida = nivelCompetenciaManager.getPontuacaoObtidaByConfiguracoesNiveisCompetencia(configNiveisCompetenciasMarcados);
			
			int pontuacaoMaximaNivelCompetencia = nivelCompetenciaManager.getOrdemMaxima(empresaId, configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId());
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaMarcado : configNiveisCompetenciasMarcados) 
				if(configuracaoNivelCompetenciaMarcado.getNivelCompetencia() != null && configuracaoNivelCompetenciaMarcado.getNivelCompetencia().getId() != null)
					pontuacaoMaximaNivelcompetencia += configuracaoNivelCompetenciaMarcado.getPesoCompetencia() * pontuacaoMaximaNivelCompetencia;
		}
		
		if(pontuacaoMaximaQuestionario != 0 || pontuacaoMaximaNivelcompetencia != 0 )//A performance vai ter que ficar nula, pois não posso dividir por zero
		{
			int pontuacaoObtida = 0;
			for (ColaboradorResposta colaboradorResposta : colaboradorRespostas) //for colaboradorRespostas separado devido contagem da multipla escolha (não juntar for de colaboradorRespostas)
			{
				Pergunta pergunta = colaboradorResposta.getPergunta();
				Resposta resposta = colaboradorResposta.getResposta();
	
				if((perguntasIdsComPesoNulo.size() > 0 && perguntasIdsComPesoNulo.contains(pergunta.getId())) || pergunta.getTipo() == TipoPergunta.SUBJETIVA)
					continue;
				
				int pesoResposta = 0;
	
				if (pergunta.getTipo() == TipoPergunta.OBJETIVA || pergunta.getTipo() == TipoPergunta.MULTIPLA_ESCOLHA)
					pesoResposta = resposta.getPeso() == null ? 0 : resposta.getPeso();
				else if (pergunta.getTipo() == TipoPergunta.NOTA)
					pesoResposta = colaboradorResposta.getValor() == null ? 0 : colaboradorResposta.getValor();
	
				int peso = pergunta.getPeso() == null ? 0 : pergunta.getPeso();
	
				pontuacaoObtida += (peso * pesoResposta);
			}
	
			if (pontuacaoObtida < 0)
				pontuacaoObtida = 0;
	
			Double pontuacaoMaxima = (double)pontuacaoMaximaQuestionario + (double)pontuacaoMaximaNivelcompetencia;
			Double performanceQuestionario = (double)pontuacaoObtida / pontuacaoMaxima;
	
			colaboradorQuestionario.setPerformance(performanceQuestionario);
			colaboradorQuestionario.setPerformanceNivelCompetencia((double) pontuacaoNivelCompetenciaObtida / pontuacaoMaxima);
		}
	}

	public int calculaPontuacaoMaximaQuestionario(ColaboradorQuestionario colaboradorQuestionario, Collection<ColaboradorResposta> colaboradorRespostas, Collection<Long> perguntasIdsComPesoNulo) {
		if(perguntasIdsComPesoNulo == null)
			perguntasIdsComPesoNulo = new ArrayList<Long>();
		
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
			if(colaboradorResposta.getPergunta() != null && colaboradorResposta.getPergunta().getOrdem() == null)
				perguntasIdsComPesoNulo.add(colaboradorResposta.getPergunta().getId());
		
		int pontuacaoMaximaQuestionario = 0;
		if(colaboradorQuestionario.getAvaliacao() != null && colaboradorQuestionario.getAvaliacao().getId() != null)
			pontuacaoMaximaQuestionario = avaliacaoManager.getPontuacaoMaximaDaPerformance(colaboradorQuestionario.getAvaliacao().getId(), new CollectionUtil<Long>().convertCollectionToArrayLong(perguntasIdsComPesoNulo));
		return pontuacaoMaximaQuestionario;
	}

	/**
	 * @param usuarioLogadoId : O ID do usuario logado eh utilizado na auditoria.
	 */
	public void update(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario, Long usuarioLogadoId, Long empresaId, Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaMarcados)
	{
		ajustaEntidadesNull(colaboradorQuestionario);
		if(!colaboradorQuestionario.isRespondidaParcialmente())
			colaboradorQuestionario.setRespondida(true);
		getDao().removeByColaboradorQuestionario(colaboradorQuestionario.getId());
		saveRespostas(colaboradorRespostas, colaboradorQuestionario);

		calculaPerformance(colaboradorQuestionario, empresaId, niveisCompetenciaMarcados);

		colaboradorQuestionarioManager.update(colaboradorQuestionario);
	}
	
    public Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long[] empresasIds, Character tipoModeloAvaliacao, Long[] avaliacoesDesempenhoId)
    {
    	List<Object[]> countRespostas = getDao().countRespostas(perguntasIds, estabelecimentosIds, areasIds, cargosIds, periodoIni, periodoFim, desligamento, turmaId, empresasIds, tipoModeloAvaliacao, avaliacoesDesempenhoId);
    	
    	Collection<QuestionarioResultadoPerguntaObjetiva> resultadosObjetivas = processaRespostas(countRespostas);
    	
    	return resultadosObjetivas;
    }

	public Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao)
    {
        List<Object[]> countRespostas = getDao().countRespostas(avaliadoId, avaliacaoDesempenhoId, desconsiderarAutoAvaliacao);

        Collection<QuestionarioResultadoPerguntaObjetiva> resultadosObjetivas = processaRespostas(countRespostas);

        return resultadosObjetivas;
    }

	private Collection<QuestionarioResultadoPerguntaObjetiva> processaRespostas(List<Object[]> countRespostas) {
		Collection<QuestionarioResultadoPerguntaObjetiva> resultadosObjetivas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
		
		for (int i = 0; i < countRespostas.size(); i++)
		{
			Object[] qtdResposta = (Object[])countRespostas.get(i);
			
			if(qtdResposta[1] != null && qtdResposta[3] != null && qtdResposta[4] != null)
			{
				QuestionarioResultadoPerguntaObjetiva resultado = new QuestionarioResultadoPerguntaObjetiva();
				resultado.setQtdRespostas((Integer)qtdResposta[1]);
				resultado.setRespostaId((Long)qtdResposta[3]);
				resultado.setQtdPercentualRespostas(ConverterUtil.convertDoubleToString((resultado.getQtdRespostas() / new Double((Integer)qtdResposta[4])) * 100.0));
				resultadosObjetivas.add(resultado);
			}
		}
		return resultadosObjetivas;
	}
	
	public Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultipla(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long[] empresasIds, Character tipoModeloAvaliacao, Long[] avaliacoesDesempenhoId)
	{
    	List<Object[]> countRespostas = getDao().countRespostasMultiplas(perguntasIds, estabelecimentosIds, areasIds, cargosIds, periodoIni, periodoFim, desligamento, turmaId, empresasIds, tipoModeloAvaliacao, avaliacoesDesempenhoId);
        Collection<QuestionarioResultadoPerguntaObjetiva> resultadosMultiplaEscolha = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
        Map<Long, Integer> totalRespostasPorQuestao = new LinkedHashMap<Long, Integer>();
        
        Long perguntaId;
        for (int i = 0; i < countRespostas.size(); i++)
        {
        	Object[] qtdResposta = (Object[])countRespostas.get(i);

        	if(qtdResposta[1] != null && qtdResposta[2] != null)
        	{
	        	perguntaId = (Long)qtdResposta[2];
	        	if(totalRespostasPorQuestao.containsKey(perguntaId))
	        		totalRespostasPorQuestao.put(perguntaId, (totalRespostasPorQuestao.get(perguntaId) + (Integer)qtdResposta[1]));
	        	else
	        		totalRespostasPorQuestao.put(perguntaId, (Integer)qtdResposta[1]);
        	}
        }

        for (int i = 0; i < countRespostas.size(); i++)
        {
            Object[] qtdResposta = (Object[])countRespostas.get(i);
            
            if(qtdResposta[1] != null && qtdResposta[2] != null && qtdResposta[3] != null)
            {
	            QuestionarioResultadoPerguntaObjetiva resultado = new QuestionarioResultadoPerguntaObjetiva();
	            resultado.setQtdRespostas((Integer)qtdResposta[1]);
	            resultado.setRespostaId((Long)qtdResposta[3]);
	            resultado.setQtdPercentualRespostas(ConverterUtil.convertDoubleToString((resultado.getQtdRespostas() / new Double(totalRespostasPorQuestao.get((Long)qtdResposta[2]))) * 100.0));
	            resultadosMultiplaEscolha.add(resultado);
            }
        }

        return resultadosMultiplaEscolha;
	}

	public Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultipla(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao)
	{
		List<Object[]> countRespostas = getDao().countRespostasMultiplas(avaliadoId, avaliacaoDesempenhoId, desconsiderarAutoAvaliacao);

		Collection<QuestionarioResultadoPerguntaObjetiva> resultadosObjetivas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();

		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByColaboradorAndAvaliacaoDesempenho(avaliadoId, avaliacaoDesempenhoId, true, desconsiderarAutoAvaliacao);
		int totalColaboradores = colaboradorQuestionarios.size();


		for (int i = 0; i < countRespostas.size(); i++)
		{
			Object[] qtdResposta = (Object[])countRespostas.get(i);

			if(qtdResposta[1] != null && qtdResposta[3] != null)
			{
				QuestionarioResultadoPerguntaObjetiva resultado = new QuestionarioResultadoPerguntaObjetiva();
				resultado.setQtdRespostas((Integer)qtdResposta[1]);
				resultado.setRespostaId((Long)qtdResposta[3]);
				resultado.setQtdPercentualRespostas(ConverterUtil.convertDoubleToString((resultado.getQtdRespostas() / new Double(totalColaboradores)) * 100.0));
				resultadosObjetivas.add(resultado);
			}
		}

		return resultadosObjetivas;
	}

	public Collection<RespostaQuestionario> findRespostasAvaliacaoDesempenho(Long colaboradorQuestionarioId) 
	{
		Collection<RespostaQuestionario> respostas = getDao().findRespostasAvaliacaoDesempenho(colaboradorQuestionarioId);
		String comentarioPorPergunta = null;
		Long perguntaId = null;
		
		for (RespostaQuestionario respostaQuestionarioVO : respostas) {
			if (StringUtils.isNotBlank(respostaQuestionarioVO.getColaboradorRespostaComentario())) {
				perguntaId = respostaQuestionarioVO.getPerguntaId();
				comentarioPorPergunta = respostaQuestionarioVO.getColaboradorRespostaComentario();
			}
			
			if (respostaQuestionarioVO.getPerguntaId().equals(perguntaId))
				respostaQuestionarioVO.setComentarioResposta(comentarioPorPergunta);
		}
		
		return respostas;
	}

	public Usuario findUsuarioParaAuditoria(Long usuarioId)
	{
		return usuarioManager.findEntidadeComAtributosSimplesById(usuarioId);
	}

	public Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao)
	{
		return getDao().findByAvaliadoAndAvaliacaoDesempenho(avaliadoId, avaliacaoDesempenhoId, desconsiderarAutoAvaliacao);
	}

	public Integer countColaboradorAvaliacaoRespondida(Long avaliacaoId) 
	{
		return getDao().countColaboradorAvaliacaoRespondida(avaliacaoId);
	}
	
	public boolean existeRespostaSemCargo(Long[] perguntasIds) 
	{
		return getDao().existeRespostaSemCargo(perguntasIds);
	}
	
	public Collection<ColaboradorResposta> findPerguntasRespostasByColaboradorQuestionario(Long colaboradorQuestionarioId, boolean agruparPorAspecto) 
	{
		return getDao().findPerguntasRespostasByColaboradorQuestionario(colaboradorQuestionarioId, agruparPorAspecto);
	}
	
	public void removeByQuestionarioId(Long questionarioId) 
	{
		getDao().removeByQuestionarioId(questionarioId);
	}
	
	public boolean verificaQuantidadeColaboradoresQueResponderamPesquisaAnonima(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long questionarioId, int quantidadeColaboradoresRelatorioPesquisaAnonima)
	{
		return getDao().verificaQuantidadeColaboradoresQueResponderamPesquisaAnonima(perguntasIds, estabelecimentosIds, areasIds, cargosIds, questionarioId, quantidadeColaboradoresRelatorioPesquisaAnonima);
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) 
	{
		this.avaliacaoManager = avaliacaoManager;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}
	
	public QuestionarioManager getQuestionarioManager()
	{
		return this.questionarioManager;
	}
	
	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}
	
	public ColaboradorManager getColaboradorManager()
	{
		return this.colaboradorManager;
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager)
	{
		this.usuarioManager = usuarioManager;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) 
	{
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public CandidatoManager getCandidatoManager() {
		return candidatoManager;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(
			ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager) {
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}
}