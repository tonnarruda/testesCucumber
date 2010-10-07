package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;

public class AvaliacaoCursoFactory
{
	public static AvaliacaoCurso getEntity()
	{
		AvaliacaoCurso avaliacaoCurso = new AvaliacaoCurso();
		avaliacaoCurso.setId(null);
		avaliacaoCurso.setTitulo("Avaliação 1");

		return avaliacaoCurso;
	}

	public static AvaliacaoCurso getEntity(long id)
	{
		AvaliacaoCurso avaliacaoCurso = getEntity();
		avaliacaoCurso.setId(id);
		return avaliacaoCurso;
	}
}
