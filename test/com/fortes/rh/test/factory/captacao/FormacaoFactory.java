package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

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

	public static Formacao getEntity(long id) {
		Formacao entity = getEntity();
		entity.setId(id);
		return entity;
	}
	
	public static Formacao getEntity(Long id, Colaborador colaborador) {
		Formacao formacao = getEntity();
		formacao.setId(id);
		formacao.setColaborador(colaborador);
		return formacao;
	}
}
