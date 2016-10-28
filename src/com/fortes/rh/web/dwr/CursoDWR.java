package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.opensymphony.webwork.dispatcher.SessionMap;

@Component
public class CursoDWR
{
	@Autowired
	private CursoManager cursoManager;
	@Autowired
	private EmpresaManager empresaManager;
	@Autowired
	private AvaliacaoCursoManager avaliacaoCursoManager;
	@Autowired
	private DocumentoAnexoManager documentoAnexoManager;

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
	public Map<Long,String> getCursosByEmpresasIds(Long[] empresasIds) throws Exception
	{
		Collection<Curso> cursos = new ArrayList<Curso>();
		
		if(LongUtil.arrayIsNotEmpty(empresasIds))
			cursos = cursoManager.findAllByEmpresasParticipantes(empresasIds);
		
		return new CollectionUtil<Curso>().convertCollectionToMap(cursos,"getId","getEmpresaNomeMaisNome");
	}
	
	public Collection<AvaliacaoCurso> getAvaliacaoCursos(Long[] cursosIds)
	{
		if (cursosIds.length == 0)
			return null;
		
		return avaliacaoCursoManager.findByCursos(cursosIds);
	}
	
	public Curso findDadosBasicosById(Long cursoId){
		return cursoManager.findByIdProjection(cursoId);
	}
	
	@SuppressWarnings("unchecked")
	public Map<Long,String> getDocumentoAnexos(Long cursoId) 
	{
		Collection<DocumentoAnexo> documentoAnexos = documentoAnexoManager.getDocumentoAnexoByOrigemId(null, 'U', cursoId);
		return new CollectionUtil<DocumentoAnexo>().convertCollectionToMap(documentoAnexos,"getId","getDescricao");
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

	public void setDocumentoAnexoManager(DocumentoAnexoManager documentoAnexoManager) {
		this.documentoAnexoManager = documentoAnexoManager;
	}
}
