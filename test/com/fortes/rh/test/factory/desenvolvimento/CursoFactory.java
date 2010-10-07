package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.Curso;

public class CursoFactory
{
	public static Curso getEntity()
	{
		Curso curso = new Curso();
		curso.setId(null);
		curso.setCargaHoraria(0);
		curso.setNome("curso");
		curso.setEmpresa(null);

		return curso;
	}

	public static Curso getEntity(long id)
	{
		Curso curso = getEntity();
		curso.setId(id);
		return curso;
	}
}
