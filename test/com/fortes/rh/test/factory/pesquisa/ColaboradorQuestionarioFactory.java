package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public class ColaboradorQuestionarioFactory
{
	public static ColaboradorQuestionario getEntity()
	{
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();

		colaboradorQuestionario.setId(null);

		return colaboradorQuestionario;
	}

	public static ColaboradorQuestionario getEntity(Long id)
	{
		ColaboradorQuestionario colaboradorQuestionario = getEntity();
		colaboradorQuestionario.setId(id);

		return colaboradorQuestionario;
	}

	public static Collection<ColaboradorQuestionario> getCollection()
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(getEntity(1L));

		return colaboradorQuestionarios;
	}
}
