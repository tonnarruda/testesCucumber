package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
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
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.MathUtil;
import com.fortes.rh.util.SpringUtil;

public class QuestionarioManagerImpl extends GenericManagerImpl<Questionario, QuestionarioDao> implements QuestionarioManager
{
    private ParametrosDoSistemaManager parametrosDoSistemaManager;
    private PerguntaManager perguntaManager;
    private RespostaManager respostaManager;
    private AspectoManager aspectoManager;
    private Mail mail;
    private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
    private ColaboradorManager colaboradorManager;

    public Questionario findByIdProjection(Long questionarioId)
    {
        return getDao().findByIdProjection(questionarioId);
    }

    public boolean checarPesquisaLiberadaByQuestionario(Questionario questionario)
    {
        return getDao().checarQuestionarioLiberado(questionario);
    }

    public Questionario clonarQuestionario(Questionario questionario)
    {
        Questionario questionarioClonado = (Questionario) questionario.clone();

        questionarioClonado.setTitulo(questionario.getTitulo() + " (Clone)");
        questionarioClonado.setLiberado(false);
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
        ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
        getDao().liberarQuestionario(questionarioId);

        ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
        Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(questionarioId);

        enviaEmailQuestionarioLiberado(empresa, parametros, questionarioId, colaboradorQuestionarios);
    }

    public void enviaEmailQuestionarioLiberado(Empresa empresa, ParametrosDoSistema parametros, Long questionarioId, Collection<ColaboradorQuestionario> colaboradorQuestionarios) throws Exception
    {
        if (colaboradorQuestionarios != null && !colaboradorQuestionarios.isEmpty())
        {
            Questionario questionario = getDao().findByIdProjection(questionarioId);
            String label = TipoQuestionario.getDescricao(questionario.getTipo());

            StringBuilder corpo = new StringBuilder();
            corpo.append("Existe uma nova " + label + " para ser respondida por você.<br><br>");
            corpo.append("Titulo: " + questionario.getTitulo() + "<br>");
            corpo.append("Período: "+ DateUtil.formataDiaMesAno(questionario.getDataInicio()) + " a " + DateUtil.formataDiaMesAno(questionario.getDataFim()) + "<br><br>");
            corpo.append("Acesse o Fortes RH em: <br>");
            corpo.append("<a href=\"" + parametros.getAppUrl() + "\">Fortes RH</a><br><br>");
            corpo.append("Copyright© by Fortes Informática LTDA<br>");
            corpo.append("http://www.fortesinformatica.com.br");

            for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios)
            {
                try
                {
                    mail.send(empresa, parametros, "[Fortes RH] Nova " + label, corpo.toString(), colaboradorQuestionario.getColaborador().getContato().getEmail());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

	public void enviaEmailNaoRespondida(Empresa empresa, Long questionarioId) throws Exception 
	{
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
        ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionarioEmpresaRespondida(questionarioId, false, empresa.getId());

		enviaEmailQuestionarioLiberado(empresa, parametros, questionarioId, colaboradorQuestionarios);
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

    public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
    {
        this.parametrosDoSistemaManager = parametrosDoSistemaManager;
    }

    public void setMail(Mail mail)
    {
        this.mail = mail;
    }

    public void setRespostaManager(RespostaManager respostaManager)
    {
        this.respostaManager = respostaManager;
    }

    public void enviaLembreteDeQuestionarioNaoLiberada()
    {
    	Collection<Integer> diasLembretePesquisa = parametrosDoSistemaManager.getDiasLembretePesquisa();

        for (Integer diaLembretePesquisa : diasLembretePesquisa)
        {
        	Calendar data = Calendar.getInstance();
        	data.setTime(new Date());
        	data.add(Calendar.DAY_OF_MONTH, +diaLembretePesquisa);

	        Collection<Questionario> questionarios = getDao().findQuestionarioNaoLiberados(data.getTime());

	        for (Questionario questionario : questionarios)
	        {
	            try
	            {
	                String label = TipoQuestionario.getDescricao(questionario.getTipo());

	                StringBuilder corpo = new StringBuilder();
	                corpo.append("ATENÇÃO:<br>");
	                corpo.append("a " + label + questionario.getTitulo() + " está prevista para iniciar no dia " + DateUtil.formataDiaMesAno(questionario.getDataInicio())+".<br>") ;
	                corpo.append("Você ainda precisa liberá-la para que os colaboradores possam respondê-la.") ;

	                mail.send(questionario.getEmpresa(), "[Fortes RH] Lembrete de " + label + " não Liberada", corpo.toString(), null, questionario.getEmpresa().getEmailRespRH());
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
        }
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
    public Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Date periodoIni, Date periodoFim, Long turmaId, Questionario questionario) throws Exception
    {
        ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");
        Collection<ResultadoQuestionario> resultadoQuestionarios = null;
        Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
        Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findInPerguntaIds(perguntasIds, estabelecimentosIds, areaIds, periodoIni, periodoFim, turmaId, questionario);

        if(colaboradorRespostas.isEmpty())
        	throw new Exception("Nenhuma pergunta foi respondida.");

        Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(perguntasIds, estabelecimentosIds, areaIds, periodoIni, periodoFim, turmaId);
        
        if(questionario.isAnonimo())
        	questionario.setTotalColab(colaboradorQuestionarioManager.countByQuestionarioRespondido(questionario.getId()));
        else
        	questionario.setTotalColab(countColaborador(colaboradorRespostas)); 
        
        Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(perguntasIds, estabelecimentosIds, areaIds, periodoIni, periodoFim, turmaId, questionario.getTotalColab());
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
        		resultadoQuestionario.montaComentarioDistinct();
        }
        
        return resultadoQuestionarios;
	}
    
    /**
     * Monta resultados da avaliação de desempenho
     * @see avaliacaoDesempenhoManager.montaResultado()
     */
    public Collection<ResultadoAvaliacaoDesempenho> montaResultadosAvaliacaoDesempenho(Collection<Pergunta> perguntas, Collection<Resposta> respostas, Long avaliadoId, Collection<ColaboradorResposta> colaboradorRespostas, Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas, boolean anonima)
	{
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		
		String avaliadoNome = colaboradorManager.getNome(avaliadoId);
		
		for (Pergunta pergunta: perguntas)
		{
			perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, avaliadoNome);
			
			ResultadoAvaliacaoDesempenho resultadoQuestionario = new ResultadoAvaliacaoDesempenho(avaliadoId, avaliadoNome);
			resultadoQuestionario.setPergunta(calculaMedia(colaboradorRespostas, pergunta));
			resultadoQuestionario.setColabRespostas(montaColaboradorReposta(colaboradorRespostas, pergunta));
			resultadoQuestionario.setRespostas(montaRespostas(respostas, pergunta, percentuaisDeRespostas));
			resultadoQuestionarios.add(resultadoQuestionario);
		}
		
		for (ResultadoAvaliacaoDesempenho resultadoQuestionario : resultadoQuestionarios)
		{
			if(!resultadoQuestionario.getPergunta().getTipo().equals(TipoPergunta.SUBJETIVA))
				resultadoQuestionario.montaColabRespostasDistinct();
		}        	
		
		for (ResultadoAvaliacaoDesempenho resultadoQuestionario : resultadoQuestionarios)
		{
			if(!resultadoQuestionario.getPergunta().getTipo().equals(TipoPergunta.SUBJETIVA))
				resultadoQuestionario.montaComentarioDistinct();
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

        
        Collection<PerguntaFichaMedica> perguntasFormatadas = new ArrayList<PerguntaFichaMedica>();
        for (Pergunta pergunta : perguntas)
        {
            StringBuilder perg = new StringBuilder();
            StringBuilder coment = new StringBuilder();

            perguntaManager.montaImpressaoPergunta(pergunta, colaboradorRespostas, perg, coment);

            // Campos pergunta e comentario estão no mesmo campo texto, devido a problema de layout.
            if (coment.length() > 0)
            	coment.insert(0, "\n");

            PerguntaFichaMedica perguntaFichaMedica = new PerguntaFichaMedica(perg.toString(), coment.toString(), pergunta.getTipo());
            perguntasFormatadas.add(perguntaFichaMedica);
        }

        return perguntasFormatadas;
    }

	public Collection<PerguntaFichaMedica> montaImpressaoAvaliacaoRespondida(Long colaboradorQuestionarioId, Map<String, Object> parametros)
    {
		ColaboradorQuestionario colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionarioId);
    	Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(colaboradorQuestionario.getAvaliacao().getId());
    	
    	ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	
		colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario, colaboradorQuestionario.getAvaliacao().getId());
    	
    	parametros.put("TITULO", colaboradorQuestionario.getAvaliacao().getTitulo());
    	parametros.put("RODAPE", colaboradorQuestionario.getObservacao());
    	parametros.put("COLABORADOR", colaboradorQuestionario.getColaborador().getNome());
    	
    	
    	Collection<PerguntaFichaMedica> perguntasFormatadas = new ArrayList<PerguntaFichaMedica>();
    	for (Pergunta pergunta : perguntas)
    	{
    		StringBuilder perg = new StringBuilder();
    		StringBuilder coment = new StringBuilder();
    		
    		perguntaManager.montaImpressaoPergunta(pergunta, colaboradorRespostas, perg, coment);
    		
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

    public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
    {
        this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
    }

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Questionario> findQuestionario(Long colaboradorId) 
	{
		return getDao().findQuestionario(colaboradorId);
	}
}