package com.fortes.rh.test.factory.desenvolvimento;

import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;

public class CertificacaoFactory
{
	public static Certificacao getEntity()
	{
		Certificacao certificacao = new Certificacao();
		certificacao.setId(null);
		certificacao.setNome("Teste");
		certificacao.setPeriodicidade(1);
		return certificacao;
	}

	public static Certificacao getEntity(long id)
	{
		Certificacao certificacao = getEntity();
		certificacao.setId(id);
		return certificacao;
	}
	
	public static Certificacao getEntity(Empresa empresa, Integer periodicidade, Collection<Curso> cursos)
	{
		Certificacao certificacao = getEntity();
		certificacao.setEmpresa(empresa);
		certificacao.setPeriodicidade(1);
		certificacao.setCursos(cursos);
		return certificacao;
	}
}
