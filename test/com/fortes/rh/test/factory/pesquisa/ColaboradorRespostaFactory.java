package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;

public class ColaboradorRespostaFactory
{
	public static ColaboradorResposta getEntity()
	{
		ColaboradorResposta colaboradorResposta = new ColaboradorResposta();

		colaboradorResposta.setComentario("comentario");
		colaboradorResposta.setValor(1);
		colaboradorResposta.setColaboradorQuestionario(null);
		colaboradorResposta.setAreaOrganizacional(null);
		colaboradorResposta.setPergunta(null);
		colaboradorResposta.setResposta(null);

		return colaboradorResposta;
	}

	public static ColaboradorResposta getEntity(Long id)
	{
		ColaboradorResposta colaboradorResposta = getEntity();
		colaboradorResposta.setId(id);

		return colaboradorResposta;
	}

	public static Collection<ColaboradorResposta> getCollection()
	{
		Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
		colaboradorRespostas.add(getEntity(1L));

		return colaboradorRespostas;
	}
	
	public static ColaboradorResposta getEntity(Long id, Pergunta pergunta, Resposta resposta, Integer valor, ColaboradorQuestionario colaboradorQuestionario)
	{
		ColaboradorResposta colaboradorResposta = getEntity();
		colaboradorResposta.setId(id);
		colaboradorResposta.setValor(valor);
		colaboradorResposta.setPergunta(pergunta);
		colaboradorResposta.setResposta(resposta);
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);

		return colaboradorResposta;
	}
}
