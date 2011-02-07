package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

public class AvaliacaoTurmaFactory {

	public static AvaliacaoTurma getEntity()
	{
		AvaliacaoTurma avaliacaoTurma = new AvaliacaoTurma();
		avaliacaoTurma.setId(null);
		
		return avaliacaoTurma;
	}

	public static AvaliacaoTurma getEntity(Long id)
	{
		AvaliacaoTurma avaliacaoTurma = getEntity();
		avaliacaoTurma.setId(id);

		return avaliacaoTurma;
	}

	public static Collection<AvaliacaoTurma> getCollection()
	{
		Collection<AvaliacaoTurma> avaliacoesTurma = new ArrayList<AvaliacaoTurma>();
		avaliacoesTurma.add(getEntity());

		return avaliacoesTurma;
	}
	
	public static Collection<AvaliacaoTurma> getCollection(Long id)
	{
		Collection<AvaliacaoTurma> avaliacoesTurma = new ArrayList<AvaliacaoTurma>();
		avaliacoesTurma.add(getEntity(id));
		
		return avaliacoesTurma;
	}
}
