package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;

public class ColaboradorTurmaFactory
{
	public static ColaboradorTurma getEntity()
	{
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		return colaboradorTurma;
	}

	public static ColaboradorTurma getEntity(long id)
	{
		ColaboradorTurma colaboradorTurma = getEntity();
		colaboradorTurma.setId(id);
		return colaboradorTurma;
	}

	public static ColaboradorTurma getEntity(Long id, Colaborador colaborador)
	{
		ColaboradorTurma colaboradorTurma = getEntity();
		colaboradorTurma.setId(id);
		colaboradorTurma.setColaborador(colaborador);
		return colaboradorTurma;
	}

	public static ColaboradorTurma getEntity(Colaborador colaborador, Curso curso, Turma turma)
	{
		ColaboradorTurma colaboradorTurma = getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setTurma(turma);
		return colaboradorTurma;
	}
	
	public static ColaboradorTurma getEntity(String nomeColaborador, boolean aprovado, String motivoReprovacao)
	{
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setColaboradorNome(nomeColaborador);
		colaboradorTurma.setAprovado(aprovado);
		colaboradorTurma.setMotivoReprovacao(motivoReprovacao);
		return colaboradorTurma;
	}
}