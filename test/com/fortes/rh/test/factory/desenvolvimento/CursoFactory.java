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

	public static Curso getEntity(Long id)
	{
		Curso curso = getEntity();
		curso.setId(id);
		return curso;
	}
	
	public static Curso getEntity(Long id, String nome)
	{
		Curso curso = getEntity();
		curso.setId(id);
		curso.setNome(nome);
		return curso;
	}
}
