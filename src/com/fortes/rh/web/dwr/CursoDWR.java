package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.util.CollectionUtil;

public class CursoDWR
{
	private CursoManager cursoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;

	@SuppressWarnings("rawtypes")
	public Map getCursosByEmpresa(Long empresaId)throws Exception
	{
		Collection<Curso> cursos = cursoManager.findAllSelect(empresaId);
		return new CollectionUtil<Curso>().convertCollectionToMap(cursos,"getId","getNome");
	}
	
	public Collection<AvaliacaoCurso> getAvaliacaoCursos(Long[] cursosIds)
	{
		if (cursosIds.length == 0)
			return null;
		
		return avaliacaoCursoManager.findByCursos(cursosIds);
	}
	
	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager) {
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}
}
