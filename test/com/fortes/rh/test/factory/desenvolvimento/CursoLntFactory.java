package com.fortes.rh.test.factory.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;

public class CursoLntFactory
{
	public static CursoLnt getEntity()
	{
		CursoLnt cursoLnt = new CursoLnt();
		cursoLnt.setId(null);
		cursoLnt.setNomeNovoCurso("nomeNovoCursoLnt");
		return cursoLnt;
	}

	public static CursoLnt getEntity(Long id)
	{
		CursoLnt cursoLnt = getEntity();
		cursoLnt.setId(id);

		return cursoLnt;
	}

	public static Collection<CursoLnt> getCollection()
	{
		Collection<CursoLnt> cursoLnts = new ArrayList<CursoLnt>();
		cursoLnts.add(getEntity());

		return cursoLnts;
	}
	
	public static Collection<CursoLnt> getCollection(Long id)
	{
		Collection<CursoLnt> cursoLnts = new ArrayList<CursoLnt>();
		cursoLnts.add(getEntity(id));
		
		return cursoLnts;
	}

	public static CursoLnt getEntity(Curso curso, Lnt lnt)
	{
		CursoLnt cursoLnt = getEntity();
		cursoLnt.setCurso(curso);
		cursoLnt.setLnt(lnt);

		return cursoLnt;
	}

	public static CursoLnt getEntity(Long id, String nomeNovoCurso) {
		CursoLnt cursoLnt = getEntity(id);
		cursoLnt.setNomeNovoCurso(nomeNovoCurso);
		
		return cursoLnt;
	}
}
