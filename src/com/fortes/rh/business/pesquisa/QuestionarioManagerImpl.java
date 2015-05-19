package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.MathUtil;
import com.fortes.rh.util.SpringUtil;

public class QuestionarioManagerImpl extends GenericManagerImpl<Questionario, QuestionarioDao> implements QuestionarioManager
{
    private PerguntaManager perguntaManager;
    private RespostaManager respostaManager;
    private AspectoManager aspectoManager;
    private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
    private ColaboradorManager colaboradorManager;
    private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

    public Questionario findByIdProjection(Long questionarioId)
    {
        return getDao().findByIdProjection(questionarioId);
    }

    public boolean checarPesquisaLiberadaByQuestionario(Questionario questionario)
    {
        return getDao().checarQuestionarioLiberado(questionario);
    }

    public Questionario clonarQuestionario(Questionario questionario, Long empresaId)
    {
        Questionario questionarioClonado = (Questionario) questionario.clone();

        questionarioClonado.setTitulo(questionario.getTitulo() + " (Clone)");
        questionarioClonado.setLiberado(false);
        
        if (empresaId != null)
        	questionarioClonado.getEmpresa().setId(empresaId);
        	
        questionarioClonado = save(questionarioClonado);

        return questionarioClonado;
    }

    public String montaUrlVoltar(Long questionarioId)
    {
        String urlVoltar = "";
        Long id = null;

        PesquisaManager pesquisaManager = (PesquisaManager) SpringUtil.getBean("pesquisaManager");
        EntrevistaManager entrevistaManager = (EntrevistaManager) SpringUtil.getBean("entrevistaManager");
        FichaMedicaManager fichaMedicaManager = (FichaMedicaManager) SpringUtil.getBean("fichaMedicaManager");

        if((id = pesquisaManager.getIdByQuestionario(questionarioId)) != null)
            urlVoltar = "../pesquisa/prepareUpdate.action?pesquisa.id=" + id;
        else if((id = entrevistaManager.getIdByQuestionario(questionarioId)) != null)
            urlVoltar = "../entrevista/prepareUpdate.action?entrevista.id=" + id;
        else if((id = fichaMedicaManager.getIdByQuestionario(questionarioId)) != null)
            urlVoltar = "../../sesmt/fichaMedica/prepareUpdate.action?fichaMedica.id=" + id;

        return urlVoltar;
    }

    public void liberarQuestionario(Long questionarioId, Empresa empresa) throws Exception
    {
        getDao().liberarQuestionario(questionarioId);
        ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
        Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(questionarioId);

        Questionario questionario = getDao().findByIdProjection(questionarioId);
        gerenciadorComunicacaoManager.enviaEmailQuestionarioLiberado(empresa, questionario, colaboradorQuestionarios);
    }

	public void enviaEmailNaoRespondida(Empresa empresa, Long questionarioId) throws Exception 
	{
        ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionarioEmpresaRespondida(questionarioId, false, null, empresa.getId());
		Questionario questionario = getDao().findByIdProjection(questionarioId);
		gerenciadorComunicacaoManager.enviaEmailQuestionario(empresa, questionario, colaboradorQuestionarios);
	}

    public void aplicarPorAspecto(Long questionarioId, boolean aplicarPorAspecto) throws Exception
    {
        try
        {
            getDao().aplicarPorAspecto(questionarioId, aplicarPorAspecto);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("Não foi possível aplicar esta pesquisa");
        }
    }

    public Collection<QuestionarioRelatorio> getQuestionarioRelatorio(Questionario questionario)
    {
        Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(questionario.getId());

        QuestionarioRelatorio questionarioRelatorio = new QuestionarioRelatorio();
        questionarioRelatorio.setQuestionario(questionario);
        questionarioRelatorio.setPerguntas(perguntas);

        Collection<QuestionarioRelatorio> questionarioRelatorios = new ArrayList<QuestionarioRelatorio>();
        questionarioRelatorios.add(questionarioRelatorio);

        return questionarioRelatorios;
    }

    @SuppressWarnings("unchecked")
	public Questionario findResponderQuestionario(Questionario questionario)
    {
        questionario = findByIdProjection(questionario.getId());

        Collection<Pergunta> perguntas = perguntaManager.findByQuestionarioAspectoPergunta(questionario.getId(), null, null, questionario.isAplicarPorAspecto());

        questionario.setPerguntas(perguntas);

        Long[] idsDasPerguntas = new CollectionUtil<Pergunta>().convertCollectionToArrayIds(perguntas);

        Collection<Resposta> respostas = respostaManager.findInPerguntaIds(idsDasPerguntas);

        for (Pergunta pergunta: perguntas)
        {
            if(pergunta.getRespostas() == null){
                pergunta.setRespostas(new ArrayList<Resposta>());
            }

            // REFATORAR: Este laço está muito ruim porque para cada pergunta ele é executado novamente.
            for (Iterator iter = respostas.iterator(); iter.hasNext();)
            {
                Resposta resposta = (Resposta) iter.next();
                if(resposta.getPergunta().equals(pergunta)){
                    pergunta.getRespostas().add(resposta);
                }
            }
        }

        if (questionario.isAplicarPorAspecto())
        {
            ordenaPesquisa(questionario);
        }

        return questionario;

    }

    /**
     *
     * @param pesquisa a ser ordenada. Aqui a pesquisa já deve vir ordenada por aspecto. Só que esta ordenação
     *        joga para o final as perguntas que não possuem aspecto. Este método tira do  final  e  coloca no
     *        começo estas perguntas sem aspecto.
     * @return uma pesquisa ordenada considerando os aspectos de cada pergunta e as perguntas sem aspecto.
     */
    private void ordenaPesquisa(Questionario questionario)
    {
        Collection<Pergunta> perguntas = questionario.getPerguntas();
        Collection<Pergunta> perguntasOrdenadas = new ArrayList<Pergunta>();

        int ordemPergunta = 1;

        for (Pergunta pergunta : perguntas)
        {
            if ( pergunta.getAspecto() == null || pergunta.getAspecto().getId() == null)
            {
                pergunta.setOrdem(ordemPergunta);
                perguntasOrdenadas.add(pergunta);
                ordemPergunta++;
            }
        }

        for (Pergunta perguntaAspecto : perguntas)
        {
            if (perguntaAspecto.getAspecto() != null && perguntaAspecto.getAspecto().getNome() != null && !perguntaAspecto.getAspecto().getNome().trim().equals(""))
            {
                perguntaAspecto.setOrdem(ordemPergunta);
                perguntasOrdenadas.add(perguntaAspecto);
                ordemPergunta++;
            }
        }

        questionario.setPerguntas(perguntasOrdenadas);

    }

    public void setPerguntaManager(PerguntaManager perguntaManager)
    {
        this.perguntaManager = perguntaManager;
    }

    public void setRespostaManager(RespostaManager respostaManager)
    {
        this.respostaManager = respostaManager;
    }

    public void enviaLembreteDeQuestionarioNaoLiberado()
    {
    	gerenciadorComunicacaoManager.enviaLembreteDeQuestionarioNaoLiberado();
    }

    public Collection<Questionario> findQuestionarioPorUsuario(Long usuarioId)
    {
        return getDao().findQuestionarioPorUsuario(usuarioId);
    }
    public void updateQuestionario(Questionario questionario)
    {
        Questionario questionarioTmp = getDao().findByIdProjection(questionario.getId());

        questionarioTmp.setTitulo(questionario.getTitulo());
        questionarioTmp.setCabecalho(questionario.getCabecalho());
        questionarioTmp.setDataInicio(questionario.getDataInicio());
        questionarioTmp.setDataFim(questionario.getDataFim());
        questionarioTmp.setAnonimo(questionario.isAnonimo());
        questionarioTmp.setLiberado(questionario.isLiberado());
        questionarioTmp.setAplicarPorAspecto(questionario.isAplicarPorAspecto());
        questionarioTmp.setFichaMedica(null);//evitando exceção com relacionamento bidirecional, provavelmente bug do Hibernate

        update(questionarioTmp);
    }

    public void removerPerguntasDoQuestionario(Long questionarioId)
    {
        perguntaManager.removerPerguntasDoQuestionario(questionarioId);
        aspectoManager.removerAspectosDoQuestionario(questionarioId);
    }

    public void setAspectoManager(AspectoManager aspectoManager)
    {
        this.aspectoManager = aspectoManager;
    }

    //TODO Refatorar consultas grandes como banco da vega esta exibindo "could not execute query" quando marcamos áreas organizacionais 
    public Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Questionario questionario) throws Exception
    {
    	ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");

    	boolean existeRespostaSemCargo = colaboradorRespostaManager.existeRespostaSemCargo(perguntasIds);
    	if (cargosIds != null && cargosIds.length > 0 && existeRespostaSemCargo)
    		throw new Exception("Existem respostas sem a informação dos cargos dos colaboradores que a responderam. Provavelmente, elas foram realizadas em versões anteriores à versão 1.1.116.128, que passa a fazer esse registro. Nesse caso, o filtro por cargos não é recomendado.");
    	
        Collection<ResultadoQuestionario> resultadoQuestionarios = null;
        Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
        Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findInPerguntaIds(perguntasIds, estabelecimentosIds, areasIds, cargosIds, periodoIni, periodoFim, desligamento, turmaId, questionario, null);

        if(colaboradorRespostas.isEmpty())
        	throw new Exception("Nenhuma pergunta foi respondida.");
        
        Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(perguntasIds, estabelecimentosIds, areasIds, cargosIds, periodoIni, periodoFim, desligamento, turmaId, null);
       
        if(questionario.isAnonimo() && questionario.getTipo() == TipoQuestionario.PESQUISA ) {
        	ParametrosDoSistemaManager parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBean("parametrosDoSistemaManager");
        	ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
        	
        	questionario.setTotalColab(colaboradorQuestionarioManager.countByQuestionarioRespondido(questionario.getId()));
        	
        	boolean possuiQuantidadeInvalidaDeColaboradoresQueResponderam = colaboradorRespostaManager.verificaQuantidadeColaboradoresQueResponderamPesquisaAnonima(perguntasIds, estabelecimentosIds, areasIds, cargosIds, questionario.getId(), parametrosDoSistema.getQuantidadeColaboradoresRelatorioPesquisaAnonima());
        	
        	if( parametrosDoSistema.getInibirGerarRelatorioPesquisaAnonima() && possuiQuantidadeInvalidaDeColaboradoresQueResponderam)
            	throw new Exception("Não é possível gerar o relatório porque a pesquisa é anônima e possui respostas de até " + parametrosDoSistema.getQuantidadeColaboradoresRelatorioPesquisaAnonima() + " colaborador(es).");
        } else	
        	questionario.setTotalColab(countColaborador(colaboradorRespostas));

        Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(perguntasIds, estabelecimentosIds, areasIds, cargosIds, periodoIni, periodoFim, desligamento, turmaId, null);
        percentuaisDeRespostas.addAll(calculaPercentualRespostasMultiplas);
        
    	resultadoQuestionarios = montaResultadosQuestionarios(perguntas, respostas, colaboradorRespostas, percentuaisDeRespostas, questionario.isAnonimo());
       
        return resultadoQuestionarios;
    }
    
    public Collection<ResultadoQuestionario> montaResultadosQuestionarios(Collection<Pergunta> perguntas, Collection<Resposta> respostas, Collection<ColaboradorResposta> colaboradorRespostas, Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas, boolean anonimo)
    {
    	Collection<ResultadoQuestionario> resultadoQuestionarios = new ArrayList<ResultadoQuestionario>();
		
    	for (Pergunta pergunta: perguntas)
    	{
    		ResultadoQuestionario resultadoQuestionario = new ResultadoQuestionario();
    		resultadoQuestionario.setPergunta(calculaMedia(colaboradorRespostas, pergunta));
    		resultadoQuestionario.setColabRespostas(montaColaboradorReposta(colaboradorRespostas, pergunta));
    		resultadoQuestionario.setRespostas(montaRespostas(respostas, pergunta, percentuaisDeRespostas));
    		resultadoQuestionarios.add(resultadoQuestionario);
    	}
		
    	for (ResultadoQuestionario resultadoQuestionario : resultadoQuestionarios)
    	{
    		if(!resultadoQuestionario.getPergunta().getTipo().equals(TipoPergunta.SUBJETIVA))
    			resultadoQuestionario.montaColabRespostasDistinct();
    	}
        
        for (ResultadoQuestionario resultadoQuestionario : resultadoQuestionarios)
        {
        	if(!resultadoQuestionario.getPergunta().getTipo().equals(TipoPergunta.SUBJETIVA))
        		resultadoQuestionario.montaComentarioPesquisaDistinct();
        }
        
        return resultadoQuestionarios;
	}
    
    /**
     * Monta resultados da avaliação de desempenho
     * @see avaliacaoDesempenhoManager.montaResultado()
     */
    public Collection<ResultadoAvaliacaoDesempenho> montaResultadosAvaliacaoDesempenho(Collection<Pergunta> perguntas, Map<Long, Integer> pontuacoesMaximasPerguntas, Collection<Resposta> respostas, Long avaliadoId, Collection<ColaboradorResposta> colaboradorRespostas, Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas, AvaliacaoDesempenho avaliacaoDesempenho, Integer qtdAvaliadores, boolean desconsiderarAutoAvaliacao)
	{
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		
		String avaliadoNome = colaboradorManager.getNome(avaliadoId);
		Double mediaPeformance = colaboradorQuestionarioManager.getMediaPeformance(avaliadoId, avaliacaoDesempenho.getId(), desconsiderarAutoAvaliacao);
		AvaliacaoManager avaliacaoManager = (AvaliacaoManager) SpringUtil.getBean("avaliacaoManager");
		String obsAvaliadores = avaliacaoManager.montaObsAvaliadores(colaboradorRespostas);
				
		for (Pergunta pergunta: perguntas)
		{
			perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, avaliadoNome);
			ResultadoAvaliacaoDesempenho resultadoQuestionario = new ResultadoAvaliacaoDesempenho(avaliadoId, avaliadoNome, mediaPeformance);
			pergunta.setAspecto(calculaPontuacaoAspecto(colaboradorRespostas, pergunta, pontuacoesMaximasPerguntas, avaliadoId, qtdAvaliadores));
			resultadoQuestionario.setPergunta(calculaMedia(colaboradorRespostas, pergunta));
			resultadoQuestionario.setColabRespostas(montaColaboradorReposta(colaboradorRespostas, pergunta));
			resultadoQuestionario.setRespostas(montaRespostas(respostas, pergunta, percentuaisDeRespostas));
			resultadoQuestionarios.add(resultadoQuestionario);
		}
		
		for (ResultadoAvaliacaoDesempenho resultadoQuestionario : resultadoQuestionarios)
		{
			Integer tipoPergunta = resultadoQuestionario.getPergunta().getTipo();
			
			//obtem respostas distintas para o colaborador
			if(!tipoPergunta.equals(TipoPergunta.SUBJETIVA))
				resultadoQuestionario.montaColabRespostasDistinct();
		}        	
		
		for (ResultadoAvaliacaoDesempenho resultadoQuestionario : resultadoQuestionarios)
		{
			//configura os comentarios para as respostas do loop anterior
			if(!resultadoQuestionario.getPergunta().getTipo().equals(TipoPergunta.SUBJETIVA))
				resultadoQuestionario.montaComentarioDistinct();
			resultadoQuestionario.setObsAvaliadores(obsAvaliadores);
		}
		
		return resultadoQuestionarios;
	}

	public Integer countColaborador(Collection<ColaboradorResposta> colaboradorRespostas)
	{
		Map<Long, String> distinct = new HashMap<Long, String>();
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
			if(colaboradorResposta.getColaboradorQuestionario() != null && colaboradorResposta.getColaboradorQuestionario().getId() != null)
				distinct.put(colaboradorResposta.getColaboradorQuestionario().getId(), "");
		}
		
		return distinct.keySet().size();
	}

	public Pergunta calculaMedia(Collection<ColaboradorResposta> colaboradorRespostas, Pergunta pergunta)
    {
        if(pergunta.getTipo().equals(TipoPergunta.NOTA))
        {
            Double media = new Double(0.0);
            int cont = 0;
            for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
            {
                if(colaboradorResposta.getPergunta().getId().equals(pergunta.getId()))
                {
                	if(colaboradorResposta.getValor() != null)
                	{
                		media += colaboradorResposta.getValor();
                		cont++;                		
                	}
                }
            }

            pergunta.setMedia(MathUtil.formataValor(media / cont));
        }
        return pergunta;
    }
	
	public Aspecto calculaPontuacaoAspecto(Collection<ColaboradorResposta> colaboradorRespostas, Pergunta pergunta, Map<Long, Integer> pontuacoesMaximasPerguntas, Long avaliadoId, Integer qtdAvaliadores)
	{
		Aspecto aspecto;
		if (pergunta.getAspecto() != null)
			aspecto = (Aspecto) pergunta.getAspecto().clone();
		else
			aspecto = new Aspecto("Sem aspecto");
		
		int pontuacaoObtida = 0;
		int pontuacaoMaxima = 0;
		List<Long> pontuacaoPergunta = new ArrayList<Long>();
	
		
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
        {
        	String aspectoColaboradorResposta = colaboradorResposta.getPergunta().getAspecto()!= null ? colaboradorResposta.getPergunta().getAspecto().getNome() : "Sem aspecto";  
        	Long perguntaId = colaboradorResposta.getPergunta().getId();
        	
        	if(avaliadoId.equals(colaboradorResposta.getColaboradorQuestionario().getColaborador().getId()) && aspecto.getNome().equals(aspectoColaboradorResposta))
        	{
        		
        		Resposta resposta = colaboradorResposta.getResposta();
				
				int peso = colaboradorResposta.getPergunta().getPeso() == null ? 0 : colaboradorResposta.getPergunta().getPeso();
				int pesoResposta = 0;
				
				if (colaboradorResposta.getPergunta().getTipo() == TipoPergunta.SUBJETIVA)
					continue;
				
				else if (colaboradorResposta.getPergunta().getTipo() == TipoPergunta.OBJETIVA || colaboradorResposta.getPergunta().getTipo() == TipoPergunta.MULTIPLA_ESCOLHA)
					pesoResposta = resposta.getPeso() == null ? 0 : resposta.getPeso();
				
				else if (colaboradorResposta.getPergunta().getTipo() == TipoPergunta.NOTA)
					pesoResposta = colaboradorResposta.getValor() == null ? 0 : colaboradorResposta.getValor();
				
				pontuacaoObtida += (peso * pesoResposta);
				if (pontuacoesMaximasPerguntas!=null && !pontuacaoPergunta.contains(perguntaId))
				{
					pontuacaoMaxima += pontuacoesMaximasPerguntas.get(perguntaId)*peso;
					pontuacaoPergunta.add(perguntaId);
				}
        	}
        }
        aspecto.setPontuacaoObtida(pontuacaoObtida);
        aspecto.setPontuacaoMaxima(pontuacaoMaxima*qtdAvaliadores);
        
        return aspecto;
    }

    public Collection<ColaboradorResposta> montaColaboradorReposta(Collection<ColaboradorResposta> colaboradorRespostas, Pergunta pergunta)
    {
        Collection<ColaboradorResposta> retorno = new ArrayList<ColaboradorResposta>();

        for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
        {
            if(colaboradorResposta.getPergunta().getId().equals(pergunta.getId()))
                retorno.add(colaboradorResposta);
        }

        return retorno;
    }

    public Collection<Resposta> montaRespostas(Collection<Resposta> respostas, Pergunta pergunta, Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas)
    {
        Collection<Resposta> retorno = new ArrayList<Resposta>();

        for (Resposta resposta : respostas)
        {
            if(resposta.getPergunta().getId().equals(pergunta.getId()))
            {
                for (QuestionarioResultadoPerguntaObjetiva resultadoObjetiva : calculaPercentualRespostas)
                {
                    if(resposta.getId().equals(resultadoObjetiva.getRespostaId()))
                    {
                        resposta.setQtdRespostas(resultadoObjetiva.getQtdRespostas());
                        resposta.setLegenda(resultadoObjetiva.getQtdPercentualRespostas() + "% (" + resultadoObjetiva.getQtdRespostas() + ")");
                        resposta.setTexto(resposta.getTexto() + " (" + resultadoObjetiva.getQtdPercentualRespostas() + "% " + resultadoObjetiva.getQtdRespostas() + ")");
                    }
                }

                retorno.add(resposta);
            }
        }

        return retorno;
    }

    public Collection<PerguntaFichaMedica> montaImpressaoFichaMedica(Long id, Long colaboradorQuestionarioId, Map<String, Object> parametros)
    {
        Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(id);

        ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");
        FichaMedicaManager fichaMedicaManager = (FichaMedicaManager) SpringUtil.getBean("fichaMedicaManager");
        Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();

        if(colaboradorQuestionarioId != null)
        {
        	ColaboradorQuestionario colaboradorQuestionario = colaboradorQuestionarioManager.findByIdColaboradorCandidato(colaboradorQuestionarioId);
        	montaParametros(colaboradorQuestionario, parametros);
        	colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario, id);
        }

        FichaMedica fichaMedica = fichaMedicaManager.findByQuestionario(id);
    	parametros.put("TITULO", fichaMedica.getQuestionario().getTitulo());
        parametros.put("RODAPE", fichaMedica.getRodape());
        parametros.put("CABECALHOFICHAMEDICA", fichaMedica.getQuestionario().getCabecalho());

        Collection<PerguntaFichaMedica> perguntasFormatadas = montaPerguntasComRespostas(perguntas, colaboradorRespostas, false, false);

        return perguntasFormatadas;
    }

	public Collection<PerguntaFichaMedica> montaImpressaoAvaliacaoRespondida(Long colaboradorQuestionarioId, Map<String, Object> parametros)
    {
		ColaboradorQuestionario colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionarioId);
    	Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(colaboradorQuestionario.getAvaliacao().getId());
    	
    	ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	
		colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario, colaboradorQuestionario.getAvaliacao().getId());
    	
    	parametros.put("TITULO", "Avaliação do Período de Experiência - " + colaboradorQuestionario.getAvaliacao().getTitulo());
    	parametros.put("RODAPE", colaboradorQuestionario.getObservacao());
    	parametros.put("COLABORADOR", colaboradorQuestionario.getColaborador().getNome());
    	parametros.put("PERFORMANCE", colaboradorQuestionario.getPerformanceFormatada());
    	
    	Collection<PerguntaFichaMedica> perguntasFormatadas = montaPerguntasComRespostas(perguntas, colaboradorRespostas, false, false);
    	
    	return perguntasFormatadas;
    }

	public Collection<PerguntaFichaMedica> montaPerguntasComRespostas(Collection<Pergunta> perguntas, Collection<ColaboradorResposta> colaboradorRespostas, boolean exibeNumeroQuestao, boolean quebraLinha) 
	{
		Collection<PerguntaFichaMedica> perguntasFormatadas = new ArrayList<PerguntaFichaMedica>();
    	for (Pergunta pergunta : perguntas)
    	{
    		StringBuilder perg = new StringBuilder();
    		StringBuilder coment = new StringBuilder();
    		
    		perguntaManager.montaImpressaoPergunta(pergunta, colaboradorRespostas, perg, coment, exibeNumeroQuestao, quebraLinha);
    		
    		// Campos pergunta e comentario estão no mesmo campo texto, devido a problema de layout.
    		if (coment.length() > 0)
    			coment.insert(0, "\n");
    		
    		PerguntaFichaMedica perguntaFichaMedica = new PerguntaFichaMedica(perg.toString(), coment.toString(), pergunta.getTipo());
    		perguntasFormatadas.add(perguntaFichaMedica);
    	}
		return perguntasFormatadas;
	}
    
    private void montaParametros(ColaboradorQuestionario colaboradorQuestionario, Map<String, Object> parametros)
    {
        parametros.put("DATA", DateUtil.formataDiaMesAno(colaboradorQuestionario.getRespondidaEm()));

        EstadoCivil estadoCivil = new EstadoCivil();

        if(colaboradorQuestionario.getCandidato() != null && colaboradorQuestionario.getCandidato().getId() != null)
        {
            Candidato candidato = colaboradorQuestionario.getCandidato();
            parametros.put("NOME", candidato.getNome());
            parametros.put("ESTADO_CIVIL", estadoCivil.get(candidato.getPessoal().getEstadoCivil()));
            parametros.put("NASCIMENTO", DateUtil.formataDiaMesAno(candidato.getPessoal().getDataNascimento()));
            parametros.put("RG", candidato.getPessoal().getRg());
            parametros.put("SEXO", Sexo.getDescSexoParaFicha(candidato.getPessoal().getSexo()));
        }
        else if(colaboradorQuestionario.getColaborador() != null && colaboradorQuestionario.getColaborador().getId() != null)
        {
            Colaborador colaborador = colaboradorQuestionario.getColaborador();
            parametros.put("NOME", colaborador.getNome());
            parametros.put("ESTADO_CIVIL", estadoCivil.get(colaborador.getPessoal().getEstadoCivil()));
            parametros.put("NASCIMENTO", DateUtil.formataDiaMesAno(colaborador.getPessoal().getDataNascimento()));
            parametros.put("RG", colaborador.getPessoal().getRg());
            parametros.put("SEXO", Sexo.getDescSexoParaFicha(colaborador.getPessoal().getSexo()));

            Colaborador colaboradorHistorico = colaboradorManager.findByIdHistoricoProjection(colaborador.getId());
            parametros.put("MATRICULA", colaboradorHistorico.getMatricula());
            parametros.put("CARGO", colaboradorHistorico.getHistoricoColaborador().getFaixaSalarial().getDescricao());
        }
    }

	public Collection<Questionario> findQuestionarioNaoLiberados(Date time) {
		return getDao().findQuestionarioNaoLiberados(time);
	}

	public Collection<Questionario> findQuestionario(Long colaboradorId) 
	{
		return getDao().findQuestionario(colaboradorId);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) 
	{
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	
    public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
    {
        this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
    }
}