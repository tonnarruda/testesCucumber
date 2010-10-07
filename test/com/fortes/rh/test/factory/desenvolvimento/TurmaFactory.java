package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.Turma;

public class TurmaFactory
{
	public static Turma getEntity()
	{
		Turma turma = new Turma();
		turma.setId(null);
		turma.setColaboradorTurmas(null);
		turma.setCurso(null);
		turma.setCusto(200D);
		turma.setEmpresa(null);
		turma.setDescricao("descricao");
		return turma;
	}

	public static Turma getEntity(long id)
	{
		Turma turma = getEntity();
		turma.setId(id);
		return turma;
	}
}
