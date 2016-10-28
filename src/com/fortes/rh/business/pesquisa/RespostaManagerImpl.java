package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.util.SpringUtil;

@Component
public class RespostaManagerImpl extends GenericManagerImpl<Resposta, RespostaDao> implements RespostaManager
{
	@Autowired
	RespostaManagerImpl(RespostaDao dao) {
		setDao(dao);
	}
	
	public Collection<Resposta> findByPergunta(Long perguntaId)
	{
		return getDao().findByPergunta(perguntaId);
	}

//	public Collection<Resposta> clonarRespostasGenericas(Collection<Resposta> respostasGenericas, Pesquisa pesquisaClonada)
//	{
//		Collection<Resposta> retornoRespostaGenericas = new ArrayList<Resposta>();
//
//		for (Resposta resposta : respostasGenericas)
//		{
//			Resposta respostaClonada = new Resposta();
//			respostaClonada = (Resposta) resposta.clone();
//
//			respostaClonada.setId(null);
////			respostaClonada.setPesquisa(pesquisaClonada);
//
//			save(respostaClonada);
//			retornoRespostaGenericas.add(respostaClonada);
//		}
//
//		return retornoRespostaGenericas;
//	}

	public Collection<Resposta> findInPerguntaIds(Long[] perguntaIds)
	{
		if(perguntaIds != null && perguntaIds.length > 0)
			return getDao().findInPerguntaIds(perguntaIds);
		else
			return new ArrayList<Resposta>();
	}

	public Collection<Resposta> salvarRespostas(Long perguntaId, String[] respostaRetorno, Integer[] pesoRespostaObjetiva)
	{
		Collection<Resposta> retorno = new ArrayList<Resposta>();
		int ordem = 1;

		for (String textoResposta : respostaRetorno)
		{
			if(!textoResposta.trim().equals(""))
			{
				Resposta respostaTemp = new Resposta();
				respostaTemp.setProjectionPerguntaId(perguntaId);
				respostaTemp.setOrdem(ordem);
				respostaTemp.setTexto(textoResposta);

				if(pesoRespostaObjetiva != null)
					respostaTemp.setPeso(pesoRespostaObjetiva[ordem - 1]);
				
				retorno.add(getDao().save(respostaTemp));

				ordem++;
			}
		}

		return retorno;
	}

	public Collection<Resposta> findRespostasSugeridas(Long questionarioId)
	{
		PerguntaManager perguntaManager = (PerguntaManager) SpringUtil.getBean("perguntaManager");
		Long ultimaPerguntaObjetivaId = perguntaManager.findUltimaPerguntaObjetiva(questionarioId);

		return getDao().findByPergunta(ultimaPerguntaObjetivaId);
	}

	public void clonarResposta(Pergunta perguntaClonada, Collection<Resposta> respostas)
	{
		for (Resposta resposta : respostas)
		{
			Resposta respostaClonada = (Resposta) resposta.clone();
			respostaClonada.setId(null);
			respostaClonada.setPergunta(perguntaClonada);

			save(respostaClonada);
		}
	}

	public void removerRespostasDasPerguntas(Collection<Long> perguntaIds)
	{
		getDao().removerRespostasDasPerguntas(perguntaIds);
	}
	
	public void removerRespostasDasPerguntasDaAvaliacao(Long avaliacaoId)
	{
		getDao().removerRespostasDasPerguntasDaAvaliacao(avaliacaoId);
	}

 }