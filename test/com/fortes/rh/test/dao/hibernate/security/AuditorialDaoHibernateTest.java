package com.fortes.rh.test.dao.hibernate.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class AuditorialDaoHibernateTest extends GenericDaoHibernateTest<Auditoria>
{
	private AuditoriaDao auditoriaDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	
	public Auditoria getEntity()
	{
		Auditoria auditoria = new Auditoria();
		
		auditoria.setId(null);
		auditoria.setDados("dados");
		
		return auditoria;
	}

	public GenericDao<Auditoria> getGenericDao()
	{
		return auditoriaDao;
	}

	public void setAuditoriaDao(AuditoriaDao auditoriaDao)
	{
		this.auditoriaDao = auditoriaDao;
	}

	public void testFindEntidade()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Auditoria auditoria = new Auditoria();
		auditoria.setEmpresa(empresa);
		auditoria.setEntidade("entidade");
		auditoria = auditoriaDao.save(auditoria);
		
		Map auditoriaMap = auditoriaDao.findEntidade(empresa.getId());
		assertEquals(2, auditoriaMap.size());
	}
	
	public void testProjectionFindById()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Auditoria auditoria = new Auditoria();
		auditoria.setUsuario(usuario);
		auditoria.setEmpresa(empresa);
		auditoria.setEntidade("entidade");
		auditoria = auditoriaDao.save(auditoria);
		
		assertEquals(auditoria, auditoriaDao.projectionFindById(auditoria.getId(), empresa.getId()));
	}

	public void testList()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Auditoria auditoria = new Auditoria();
		auditoria.setUsuario(usuario);
		auditoria.setEmpresa(empresa);
		auditoria.setEntidade("entidade");
		auditoria = auditoriaDao.save(auditoria);
		
		Map parametros = new HashMap();
		
		assertEquals(1, auditoriaDao.list(0, 10, parametros, empresa.getId()).size());

		auditoria.setDados("teste __#baBau#__");
		auditoriaDao.save(auditoria);
		parametros.put("dados", "#babau#");
		assertEquals(1, auditoriaDao.list(0, 10, parametros, empresa.getId()).size());
	}
	
	@SuppressWarnings("unchecked")
	public void testGetCount()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Auditoria auditoria = new Auditoria();
		auditoria.setUsuario(usuario);
		auditoria.setEmpresa(empresa);
		auditoria.setEntidade("entidade");
		auditoria.setData(new Date());
		auditoria.setOperacao("editar");
		auditoria = auditoriaDao.save(auditoria);
		
		Map parametros = new HashMap();
		parametros.put("dataIni", auditoria.getData());
		parametros.put("dataFim", auditoria.getData());
		parametros.put("usuarioId", auditoria.getUsuario().getId());
		parametros.put("operacao", auditoria.getOperacao());
		parametros.put("entidade", auditoria.getEntidade());
		
		assertEquals(new Integer(1), auditoriaDao.getCount(parametros, empresa.getId()));
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}
}