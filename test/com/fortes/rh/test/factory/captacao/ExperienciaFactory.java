package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.Experiencia;

public class ExperienciaFactory
{
	public static Experiencia getEntity()
	{
		Experiencia experiencia = new Experiencia();
		experiencia.setEmpresa("empresa");
		experiencia.setDataAdmissao(new Date());
		experiencia.setNomeMercado("nomeMercado");

		return experiencia;
	}

	public static Experiencia getEntity(long id) {
		Experiencia exp = getEntity();
		exp.setId(id);
		return exp;
	}
	
	public static Experiencia getEntity(String nome) {
		Experiencia exp = getEntity();
		exp.setNomeMercado(nome);
		return exp;
	}
}
