package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.Questionario;

public class QuestionarioFactory
{
	public static Questionario getEntity()
	{
		Questionario questionario = new Questionario();

		questionario.setId(null);
		questionario.setCabecalho("cabecalho");
		questionario.setColaboradorQuestionarios(null);
		questionario.setPerguntas(null);

		return questionario;
	}

	public static Questionario getEntity(Long id)
	{
		Questionario questionario = getEntity();
		questionario.setId(id);

		return questionario;
	}
	
	public static Questionario getEntity(Long id, String titulo)
	{
		Questionario questionario = getEntity();
		questionario.setId(id);
		questionario.setTitulo(titulo);

		return questionario;
	}

	public static Collection<Questionario> getCollection()
	{
		Collection<Questionario> questionarios = new ArrayList<Questionario>();
		questionarios.add(getEntity(1L));

		return questionarios;
	}
}
