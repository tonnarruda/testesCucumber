package com.fortes.rh.test.factory.desenvolvimento;

import java.util.Date;

import com.fortes.rh.model.desenvolvimento.Curso;
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
	
	public static Turma getEntity(Date dataPrevIni, Date dataPrevFim, Curso curso)
	{
		Turma turma = getEntity();
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setCurso(curso);
		return turma;
	}

}
