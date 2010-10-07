package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.Formacao;

public class FormacaoFactory
{
	public static Formacao getEntity()
	{
		Formacao formacao = new Formacao();
		formacao.setConclusao("conclusao");
		formacao.setCurso("curso");
		formacao.setLocal("local");
		formacao.setSituacao('c');
		formacao.setTipo('m');

		return formacao;
	}
}
