package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.EmpresaUtil;
import com.opensymphony.webwork.dispatcher.SessionMap;

public class CursoDWR
{
	private CursoManager cursoManager;
	private EmpresaManager empresaManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;

	@SuppressWarnings("unchecked")
	public Map<Long,String> getCursosByEmpresa(Long empresaId) throws Exception
	{
		Collection<Curso> cursos = cursoManager.findAllSelect(empresaId);
		return new CollectionUtil<Curso>().convertCollectionToMap(cursos,"getId","getNome");
	}

	@SuppressWarnings("unchecked")
	public Map<Long,String> getCursosByEmpresasParticipantes(Long[] empresasIds, String role, HttpServletRequest request) throws Exception
	{
		Collection<Curso> cursos;
		
		if (empresasIds != null && empresasIds.length > 0)
		{
			cursos = cursoManager.findAllByEmpresasParticipantes(empresasIds);
		}
		else
		{
			
			Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, SecurityUtil.getIdUsuarioLoged(new SessionMap(request)), role);
			cursos = cursoManager.findAllByEmpresasParticipantes(new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas));
		}
		
		return new CollectionUtil<Curso>().convertCollectionToMap(cursos,"getId","getNome");
	}
	
	@SuppressWarnings("unchecked")
	public Map<Long,String> getCursosByEmpresasParticipantes(Long[] empresasIds, Long empresaId, HttpServletRequest request) throws Exception
	{
		Collection<Curso> cursos;
		cursos = cursoManager.findAllByEmpresasParticipantes(EmpresaUtil.empresasSelecionadas(empresaId, empresasIds));		
		
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

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}
