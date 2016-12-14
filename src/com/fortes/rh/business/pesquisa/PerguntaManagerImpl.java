package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class PerguntaManagerImpl extends GenericManagerImpl<Pergunta, PerguntaDao> implements PerguntaManager
{
	private RespostaManager respostaManager;
	private AspectoManager aspectoManager;
	private PlatformTransactionManager transactionManager;

	public Collection<Pergunta> getPerguntasRespostaByQuestionario(Long questionarioId)
	{
		Collection<Pergunta> perguntas = getDao().findByQuestionario(questionarioId);

		return associarPerguntasRespostas(perguntas);
	}

	public Collection<Pergunta> getPerguntasRespostaByQuestionarioAspecto(Long questionarioId, Long[] aspectosIds)
	{
		Collection<Pergunta> perguntas = getDao().findByQuestionarioAspecto(questionarioId, aspectosIds);

		return associarPerguntasRespostas(perguntas);
	}

	private Collection<Pergunta> associarPerguntasRespostas(Collection<Pergunta> perguntas)
	{
		CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
		Long[] perguntaIds = clu.convertCollectionToArrayIds(perguntas);

		Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntaIds);

		Collection<Pergunta> perguntaRetornos = new ArrayList<Pergunta>();
		Pergunta perguntaTmp;

		Collection<Long> respostasVerificadas = new ArrayList<Long>();

		for (Pergunta pergunta : perguntas)
		{
			perguntaTmp = (Pergunta) pergunta.clone();
			perguntaTmp.setRespostas(new ArrayList<Resposta>());

			for (Resposta resposta : respostas)
			{
				if(respostasVerificadas.contains(resposta.getId()))
					continue;

				if(pergunta.equals(resposta.getPergunta()))
				{
					perguntaTmp.getRespostas().add(resposta);
					respostasVerificadas.add(resposta.getId());
				}
//				else
//					break;	//	Não cobre essa linha por BUG do Coverage
			}

			perguntaRetornos.add(perguntaTmp);
		}

		return perguntaRetornos;
	}

	public Collection<Pergunta> getPerguntasSemAspecto(Collection<Pergunta> perguntas)
	{
		Collection<Pergunta> retorno = new ArrayList<Pergunta>();
		int ordem = 1;

		for (Pergunta pergunta : perguntas)
		{
			if(pergunta.getAspecto() == null || pergunta.getAspecto().getId() == null)
			{
				pergunta.setOrdem(ordem);
				retorno.add(pergunta);
				ordem++;
			}
		}

		return (retorno.size() > 0) ? retorno : null ;
	}

	private String[] limpaRespostas(String[] respostas)
	{
		CollectionUtil<String> cluRespostas = new CollectionUtil<String>();

		Collection<String> colRespostas = new ArrayList<String>();
		colRespostas = cluRespostas.convertArrayToCollection(respostas);

		Collection<String> colRespostasRetorno = new ArrayList<String>();

		for (String resposta : colRespostas)
		{
			if(!resposta.trim().equals(""))
				colRespostasRetorno.add(resposta);
		}

		String[] retorno = new String[colRespostasRetorno.size()];

		for (int i = 0; i < retorno.length; i++)
		{
			retorno[i] = (String) colRespostasRetorno.toArray()[i];
		}

		return retorno;
	}

	private String[] validarRespostas(String[] respostas) throws Exception
	{
		String[] respostasSalvar = null;
		Boolean passou = false;

		if (respostas != null)
		{
			respostasSalvar = limpaRespostas(respostas);

			if(respostasSalvar.length > 1)
				passou = true;
		}

		if (!passou)
			throw new Exception("A pergunta deve conter no mínimo duas respostas.");

		return respostasSalvar;
	}

	public Pergunta salvarPergunta(Pergunta pergunta, String[] respostas, Integer[] pesoRespostaObjetiva, Integer ordemSugerida) throws Exception
	{
		String[] respostasSalvar = null;

		if(pergunta.getTipo().equals(TipoPergunta.OBJETIVA) || pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
			respostasSalvar = validarRespostas(respostas);

		try
		{
			if(pergunta.getTipo() == TipoPergunta.SUBJETIVA)
				pergunta.setComentario(false);

			saveAspecto(pergunta);

			if(ordemSugerida == null)
				pergunta.setOrdem(getUltimaOrdenacao(pergunta.getQuestionario().getId()) + 1);
			else
			{
				pergunta.setOrdem(ordemSugerida);
				
				Long questionarioOrAvaliacaoId = pergunta.getQuestionarioOrAvaliacaoId();

				if(getDao().existsOrdem(questionarioOrAvaliacaoId, pergunta.getOrdem()))
					getDao().reposicionarPerguntas(questionarioOrAvaliacaoId, pergunta.getOrdem(), '+');
			}

			Pergunta perguntaRetorno = getDao().save(pergunta);

			if(respostasSalvar != null)
				perguntaRetorno.setRespostas(respostaManager.salvarRespostas(perguntaRetorno.getId(), respostasSalvar, pesoRespostaObjetiva));

			return perguntaRetorno;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível salvar.");
		}
	}

	public int getUltimaOrdenacao(Long questionarioId) throws Exception
	{
		return getDao().getUltimaOrdenacao(questionarioId);
	}

	public void removerPergunta(Pergunta pergunta) throws Exception
	{
		try
		{
			Collection<Resposta> respostas = respostaManager.findByPergunta(pergunta.getId());

			CollectionUtil<Resposta> clu = new CollectionUtil<Resposta>();
			Long[] respostasId = clu.convertCollectionToArrayIds(respostas);

			if(respostasId.length > 0)
				respostaManager.remove(respostasId);

			pergunta = getDao().findByIdProjection(pergunta.getId());

			ajustaReferencias(pergunta);
			Long questionarioOrAvaliacaoId = pergunta.getQuestionarioOrAvaliacaoId();

			boolean ultimo = getUltimaOrdenacao(questionarioOrAvaliacaoId) == pergunta.getOrdem();

			getDao().remove(pergunta);

			if(!ultimo)
				getDao().reposicionarPerguntas(questionarioOrAvaliacaoId,pergunta.getOrdem(),'-');
		} 
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível excluir, pois já existem itens respondidos.");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível excluir este item.");
		}
	}

	private void ajustaReferencias(Pergunta pergunta)
	{
		if(pergunta.getAspecto() == null || pergunta.getAspecto().getId() == null)
			pergunta.setAspecto(null);
		if(pergunta.getQuestionario() == null || pergunta.getQuestionario().getId() == null)
			pergunta.setQuestionario(null);
		if(pergunta.getAvaliacao() == null || pergunta.getAvaliacao().getId() == null)
			pergunta.setAvaliacao(null);
	}

	public Pergunta findByIdProjection(Long perguntaId)
	{
		return getDao().findByIdProjection(perguntaId);
	}

	public void atualizarPergunta(Pergunta pergunta, String[] respostas, Integer[] pesoRespostaObjetiva) throws Exception
	{		
		try
		{
			String[] respostasSalvar = null;

			if(pergunta.getTipo().equals(TipoPergunta.OBJETIVA) || pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
				respostasSalvar = validarRespostas(respostas);

			saveAspecto(pergunta);
			
			if(pergunta.getTipo() == TipoPergunta.SUBJETIVA)
				pergunta.setComentario(false);

			getDao().updateAndReorder(pergunta);

			if(respostas != null)
			{
				Collection<Resposta> respostasCadastradas = respostaManager.findByPergunta(pergunta.getId());

				CollectionUtil<Resposta> clu = new CollectionUtil<Resposta>();
				Long[] respostasId = clu.convertCollectionToArrayIds(respostasCadastradas);

				if(respostasId.length > 0)
					respostaManager.remove(respostasId);

				if(respostasSalvar != null)
					respostaManager.salvarRespostas(pergunta.getId(), respostasSalvar, pesoRespostaObjetiva);
			}
		} 
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível atualizar, pois já existem itens respondidos.");
		} 
	}

	private void saveAspecto(Pergunta pergunta)
	{
		if(pergunta.getAspecto() == null || pergunta.getAspecto().getNome() == null)
			pergunta.setAspecto(null);
		else
		{
			if(pergunta.getAvaliacao() != null)
				pergunta.setAspecto(aspectoManager.saveByAvaliacao(pergunta.getAspecto(), pergunta.getAvaliacao()));
			else
				pergunta.setAspecto(aspectoManager.saveOrGetAspectoByNome(pergunta.getAspecto().getNome(),pergunta.getQuestionario().getId()));
		}
	}

	public Pergunta findPergunta(Long perguntaId)
	{
		Pergunta perguntaRetorno = getDao().findByIdProjection(perguntaId);

		if(perguntaRetorno.getTipo().equals(TipoPergunta.OBJETIVA) || perguntaRetorno.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
		{
			Collection<Resposta> respostas = respostaManager.findByPergunta(perguntaId);
			perguntaRetorno.setRespostas(respostas);
		}

		return perguntaRetorno;
	}

	public void reordenarPergunta(Pergunta pergunta, char sinal) throws Exception
	{
		pergunta = getDao().findByIdProjection(pergunta.getId());
		Long perguntaImediataId;

		if(sinal == '-')
		{
			perguntaImediataId = findByOrdem(pergunta.getQuestionario().getId(),pergunta.getOrdem()-1);

			if(pergunta.getOrdem() == 1 || perguntaImediataId == null)
				throw new Exception("Não é possivel efetuar esta ordenação");

			getDao().reordenaPergunta(perguntaImediataId, '+');
			getDao().reordenaPergunta(pergunta.getId(), '-');
		}
		else if(sinal == '+')
		{
			perguntaImediataId = findByOrdem(pergunta.getQuestionario().getId(),pergunta.getOrdem()+1);

			if(perguntaImediataId == null)
				throw new Exception("Não é possivel efetuar esta ordenação");

			getDao().reordenaPergunta(perguntaImediataId, '-');
			getDao().reordenaPergunta(pergunta.getId(), '+');
		}
	}

	public void alterarPosicao(Pergunta pergunta, int novaPosicao) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try
		{
			pergunta = getDao().findByIdProjection(pergunta.getId());

			if(!getDao().reposicionarPerguntas(pergunta.getQuestionario().getId(),pergunta.getOrdem(),'-'))
				throw new Exception();

			int total = getDao().getTotalPerguntas(pergunta.getQuestionario().getId());

			if (novaPosicao < total)
			{
				if(!getDao().reposicionarPerguntas(pergunta.getQuestionario().getId(),novaPosicao,'+'))
					throw new Exception();
			}

			getDao().updateOrdem(pergunta.getId(), novaPosicao);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível reposicionar esta pergunta.");
		}
	}

	public void embaralharPerguntas(Long questionarioId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try
		{
			Collection<Pergunta> perguntas = getDao().findByQuestionario(questionarioId);
			Pergunta perguntaTmp = (Pergunta) perguntas.toArray()[0];

			int ultimaOrdem = getUltimaOrdenacao(perguntaTmp.getQuestionario().getId());

			Long[] perguntasIds = new CollectionUtil<Pergunta>().convertCollectionToArrayIds(perguntas);
			Collection<Integer> novaOrdenacao = new ArrayList<Integer>();

			for (int i=1;i<=perguntasIds.length;i++)
			{
				int ordemAleatoria = 1 + ((int)((ultimaOrdem)*Math.random()));

				if(ordemAleatoria <= ultimaOrdem && !novaOrdenacao.contains(ordemAleatoria))
					novaOrdenacao.add(ordemAleatoria);
				else
					i--;
			}

			int indice = 0;
			for (Integer ordem : novaOrdenacao)
			{
				getDao().updateOrdem(perguntasIds[indice],ordem);
				indice++;
			}

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível efetuar o embaralhamento");
		}
	}

	public void salvarPerguntasByOrdem(Collection<Pergunta> perguntas) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try
		{
			for (Pergunta pergunta : perguntas)
			{
				getDao().updateOrdem(pergunta.getId(), pergunta.getOrdem());
			}

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível aplicar, ordenado por aspecto.");
		}

	}

	private Long findByOrdem(Long questionarioId, int ordem)
	{
		return getDao().findIdByOrdem(questionarioId,ordem);
	}

	public Long findUltimaPerguntaObjetiva(Long questionarioId)
	{
		return getDao().findUltimaPerguntaObjetiva(questionarioId);
	}

	public Collection<CheckBox> populaCheckOrderTexto(Long questionarioId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Pergunta> perguntas = getDao().findByQuestionario(questionarioId);
			checks = CheckListBoxUtil.populaCheckListBox(perguntas, "getId", "getOrdemMaisTexto", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<Pergunta> findByQuestionarioAspectoPergunta(Long questionarioId, Long[] aspectosIds, Long[] perguntasIds, boolean agruparPorAspectos)
	{
		return getDao().findByQuestionarioAspectoPergunta(questionarioId, aspectosIds, perguntasIds, agruparPorAspectos);
	}

	public Collection<Pergunta> modificarOrdens(Collection<Pergunta> perguntas, int qtdPosicoes)
	{
		Collection<Pergunta> retorno = new ArrayList<Pergunta>();

		for (Pergunta pergunta : perguntas)
		{
			Pergunta perguntaTmp = (Pergunta) pergunta.clone();
			perguntaTmp.setOrdem(pergunta.getOrdem() + qtdPosicoes);
			retorno.add(perguntaTmp);
		}

		return retorno;
	}

	public void clonarPerguntas(Long questionarioId, Questionario questionarioClonado, Avaliacao avaliacaoClonada)
	{
		Collection<Pergunta> perguntas = getPerguntasRespostaByQuestionario(questionarioId);
		
		//Clona Aspectos das perguntas
		HashMap<Long, Aspecto> aspectosClonados = aspectoManager.clonarAspectos(questionarioId, questionarioClonado, avaliacaoClonada);

		if (perguntas != null && !perguntas.isEmpty())
    	{
	    	for (Pergunta pergunta : perguntas)
			{
				Pergunta perguntaClonada = new Pergunta();
				perguntaClonada = (Pergunta) pergunta.clone();
				perguntaClonada.setId(null);
				perguntaClonada.setQuestionario(questionarioClonado);
				perguntaClonada.setAvaliacao(avaliacaoClonada);

				//set aspecto clonado ou null
				if (perguntaClonada.getAspecto() == null || perguntaClonada.getAspecto().getId() == null)
					perguntaClonada.setAspecto(null);
				else
					perguntaClonada.setAspecto(aspectosClonados.get(perguntaClonada.getAspecto().getId()));

				perguntaClonada = save(perguntaClonada);

				Collection<Resposta> respostas = pergunta.getRespostas();

				respostaManager.clonarResposta(perguntaClonada, respostas);
			}
    	}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void setRespostaManager(RespostaManager respostaManager)
	{
		this.respostaManager = respostaManager;
	}


	public void setAspectoManager(AspectoManager aspectoManager)
	{
		this.aspectoManager = aspectoManager;
	}

	public void removerPerguntasDoQuestionario(Long questionarioId)
	{
		Collection<Long> perguntaIds = findPerguntasDoQuestionario(questionarioId);

		if (perguntaIds.size() > 0)
		{
			respostaManager.removerRespostasDasPerguntas(perguntaIds);
			getDao().removerPerguntasDoQuestionario(questionarioId);
		}
	}
	
	public void removerPerguntasAspectosDaAvaliacao(Long avaliacaoId) 
	{
		respostaManager.removerRespostasDasPerguntasDaAvaliacao(avaliacaoId);
		getDao().removerPerguntasDaAvaliacao(avaliacaoId);
		aspectoManager.removerAspectosDaAvaliacao(avaliacaoId);
	}

	public Collection<Long> findPerguntasDoQuestionario(Long questionarioId)
	{
		return getDao().findPerguntasDoQuestionario(questionarioId);
	}

	public Collection<Pergunta> findByQuestionario(Long questionarioId)
	{
		return getDao().findByQuestionario(questionarioId);
	}

	public void montaImpressaoPergunta(Pergunta pergunta, Collection<ColaboradorResposta> colaboradorRespostas, StringBuilder textoPergunta, StringBuilder textoComentario, boolean exibeNumeroQuestao, boolean quebraLinha)
	{
		StringBuilder textoPerguntaTmp = new StringBuilder("<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">");
		
		if(exibeNumeroQuestao)
			textoPerguntaTmp.append(StringUtil.replaceXml(pergunta.getOrdemMaisTexto()));
		else
			textoPerguntaTmp.append(StringUtil.replaceXml(pergunta.getTexto()));
		
		textoPerguntaTmp.append("</style>  ");
		
		if(quebraLinha)
			textoPerguntaTmp.append("\n");
		
		StringBuilder textoComentarioTmp = new StringBuilder();
		
		switch (pergunta.getTipo())
		{
			case TipoPergunta.OBJETIVA:
				montaImpressapPerguntaObjetiva(pergunta, colaboradorRespostas, textoPerguntaTmp, textoComentarioTmp, quebraLinha);							
				break;
			case TipoPergunta.MULTIPLA_ESCOLHA:
				montaImpressapPerguntaObjetiva(pergunta, colaboradorRespostas, textoPerguntaTmp, textoComentarioTmp, quebraLinha);							
				break;
			case TipoPergunta.SUBJETIVA:
				montaImpressapPerguntaSubjetiva(pergunta, colaboradorRespostas, textoPerguntaTmp, quebraLinha);							
				break;
			case TipoPergunta.NOTA:
				montaImpressapPerguntaNota(pergunta, colaboradorRespostas, textoPerguntaTmp, textoComentarioTmp, quebraLinha);							
				break;
		}
		
		textoPergunta.append(textoPerguntaTmp.toString());
		if(pergunta.isComentario())
		{
			textoComentario.append(pergunta.getTextoComentario() + " " + textoComentarioTmp.toString());
			if(quebraLinha)
				textoComentario.append("\n");
		}
	}
	
	private void montaImpressapPerguntaNota(Pergunta pergunta, Collection<ColaboradorResposta> colaboradorRespostas, StringBuilder textoPerguntaTmp, StringBuilder textoComentarioTmp, boolean quebraLinha)
	{
		for (ColaboradorResposta respostaColaborador : colaboradorRespostas)
		{
			if(respostaColaborador.getPergunta().getId().equals(pergunta.getId()))
			{
				if(respostaColaborador.getValor() != null)
				{
					if(quebraLinha)
						textoPerguntaTmp.append("     Nota: " + respostaColaborador.getValor() + "\n");
					else
						textoPerguntaTmp.append(respostaColaborador.getValor());
				}
				if(pergunta.isComentario() && respostaColaborador.getComentario() != null)
					textoComentarioTmp.append("  " + respostaColaborador.getComentario());
				
				break;
			}
		}
	}

	private void montaImpressapPerguntaSubjetiva(Pergunta pergunta, Collection<ColaboradorResposta> colaboradorRespostas, StringBuilder textoPerguntaTmp, boolean quebraLinha)
	{
		for (ColaboradorResposta respostaColaborador : colaboradorRespostas)
		{
			if(respostaColaborador.getPergunta().getId().equals(pergunta.getId()))
			{
				if(respostaColaborador.getComentario() != null)
				{
					if(quebraLinha)
						textoPerguntaTmp.append("Resp.: " + respostaColaborador.getComentario() + "\n");
					else
						textoPerguntaTmp.append(respostaColaborador.getComentario());
				}
				
				break;
			}
		}
	}

	private void montaImpressapPerguntaObjetiva(Pergunta pergunta, Collection<ColaboradorResposta> colaboradorRespostas, StringBuilder textoPerguntaTmp, StringBuilder textoComentarioTmp, boolean quebraLinha)
	{
		String comentario = "";
		
		for (Resposta resposta: pergunta.getRespostas())
		{
			String marcada = " ";
			
			for (ColaboradorResposta respostaColaborador: colaboradorRespostas)
			{
				if (resposta.getId().equals(respostaColaborador.getResposta().getId()))
				{
					marcada = "x";
					if (pergunta.isComentario() && respostaColaborador.getComentario() != null && StringUtils.isEmpty(comentario))
						comentario = respostaColaborador.getComentario();
					
					break;
				}
			}
			
			String resp = "(" + marcada + ")" + resposta.getTexto() + "     ";
			
			if(quebraLinha)
				textoPerguntaTmp.append("     " + resp + "\n");
			else
				textoPerguntaTmp.append(resp);
		}
		
		textoComentarioTmp.append(comentario);
	}
	
	public void setAvaliadoNaPerguntaDeAvaliacaoDesempenho(Pergunta pergunta, String avaliadoNome)
	{
			String perguntaTexto = pergunta.getTexto();
			if (perguntaTexto != null)
				pergunta.setTexto(perguntaTexto.replaceAll("#AVALIADO#", avaliadoNome));
	}

	public Collection<Pergunta> getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(Long questionarioId, boolean ordenarPorAspecto) {
		
		Collection<Pergunta> perguntas = getDao().findByQuestionarioAgrupadoPorAspecto(questionarioId, ordenarPorAspecto);

		return associarPerguntasRespostas(perguntas);
	}

	public Map<Long, Integer> getPontuacoesMaximas(Long[] perguntasIds) {
		return getDao().getPontuacoesMaximas(perguntasIds);
	}
	
	public Collection<ColaboradorResposta> getColaboradorRespostasDasPerguntas(Collection<Pergunta> perguntas) 
	{
		Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
		for (Pergunta pergunta : perguntas)
		{
			// desagrupando os colaboradorRespostas que vieram agrupados por pergunta
			if (pergunta.getColaboradorRespostas() != null)
			{
				colaboradorRespostas.addAll(pergunta.getColaboradorRespostas());
			
				setComentariosDasRespostasMultiplaEscolha(pergunta.getColaboradorRespostas());
			}
		}
		return colaboradorRespostas;
	}
	
	// O comentário de resposta multipla só vem na primeira e precisa ser replicado para todas (problema no modelo)
	private void setComentariosDasRespostasMultiplaEscolha(Collection<ColaboradorResposta> colabRespostas) {
		
		String comentario = null;
		
		if (!colabRespostas.isEmpty()) {
			
			ColaboradorResposta primeiroColabResposta = ((ColaboradorResposta)colabRespostas.toArray()[0]);
			
			if (primeiroColabResposta.getPergunta().getTipo() == TipoPergunta.MULTIPLA_ESCOLHA 
					&& primeiroColabResposta.getPergunta().isComentario())
			{
				comentario = primeiroColabResposta.getComentario();
			
				for (ColaboradorResposta colabResposta : colabRespostas)
						colabResposta.setComentario(comentario);
			}
		}
	}
}