package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.util.CollectionUtil;

public class DntDWR
{
	private TurmaManager turmaManager;

	public Map getTurmas(String cursoIdString)
	{
		Collection<Turma> turmas = new ArrayList<Turma>();

		Long cursoId = Long.parseLong((cursoIdString.replace(".","")).replace(",",""));

		turmas = turmaManager.findToList(new String[]{"id", "descricao"}, new String[]{"id", "descricao"}, new String[]{"curso.id"}, new Object[]{cursoId}, new String[]{"descricao"});

		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricao");
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

}
