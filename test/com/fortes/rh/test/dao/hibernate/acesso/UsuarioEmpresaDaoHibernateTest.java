package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.LongUtil;

public class UsuarioEmpresaDaoHibernateTest extends GenericDaoHibernateTest<UsuarioEmpresa>
{
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;
	private UsuarioEmpresaDao usuarioEmpresaDao;
	private PapelDao papelDao;
	private PerfilDao perfilDao;
	private GrupoACDao grupoACDao;
	private ColaboradorDao colaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;

	public UsuarioEmpresa getEntity()
	{
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();

		usuarioEmpresa.setId(null);
		return usuarioEmpresa;
	}

	public void testFindAllBySelectUsuarioEmpresa()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		assertEquals(usuarioEmpresa, usuarioEmpresaDao.findAllBySelectUsuarioEmpresa(empresa.getId()).toArray()[0]);
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);
		
		assertEquals(usuarioEmpresa, usuarioEmpresaDao.findByUsuario(usuario.getId()).toArray()[0]);
	}
	
	public void testFindUsuarioResponsavelAreaOrganizacional()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		Colaborador responsavelArea = ColaboradorFactory.getEntity();
		responsavelArea.setUsuario(usuario);
		responsavelArea.setEmpresa(empresa);
		colaboradorDao.save(responsavelArea);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavelArea);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		Colaborador responsavelArea2 = ColaboradorFactory.getEntity();
		responsavelArea2.setUsuario(usuario2);
		responsavelArea2.setEmpresa(empresa);
		colaboradorDao.save(responsavelArea2);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setResponsavel(responsavelArea2);
		areaOrganizacionalDao.save(areaOrganizacional2);

		Usuario usuarioFora = UsuarioFactory.getEntity();
		usuarioDao.save(usuarioFora);
		Colaborador responsavelAreaFora = ColaboradorFactory.getEntity();
		responsavelAreaFora.setUsuario(usuarioFora);
		responsavelAreaFora.setEmpresa(empresa);
		colaboradorDao.save(responsavelAreaFora);
		AreaOrganizacional areaOrganizacionalFora = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalFora.setResponsavel(responsavelAreaFora);
		areaOrganizacionalDao.save(areaOrganizacionalFora);
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacional.getId());
		areasIds.add(areaOrganizacional2.getId());
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaDao.findUsuarioResponsavelAreaOrganizacional(areasIds);
		
		assertEquals(2, usuarioEmpresas.size());
	}
	
	public void testFindUsuarioCoResponsavelAreaOrganizacional()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador coResponsavelArea = ColaboradorFactory.getEntity();
		coResponsavelArea.setUsuario(usuario);
		coResponsavelArea.setEmpresa(empresa);
		colaboradorDao.save(coResponsavelArea);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCoResponsavel(coResponsavelArea);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador coResponsavelArea2 = ColaboradorFactory.getEntity();
		coResponsavelArea2.setUsuario(usuario2);
		coResponsavelArea2.setEmpresa(empresa);
		colaboradorDao.save(coResponsavelArea2);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setCoResponsavel(coResponsavelArea2);
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		Usuario usuarioFora = UsuarioFactory.getEntity();
		usuarioDao.save(usuarioFora);
		
		Colaborador coResponsavelAreaFora = ColaboradorFactory.getEntity();
		coResponsavelAreaFora.setUsuario(usuarioFora);
		coResponsavelAreaFora.setEmpresa(empresa);
		colaboradorDao.save(coResponsavelAreaFora);
		
		AreaOrganizacional areaOrganizacionalFora = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalFora.setCoResponsavel(coResponsavelAreaFora);
		areaOrganizacionalDao.save(areaOrganizacionalFora);
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacional.getId());
		areasIds.add(areaOrganizacional2.getId());
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaDao.findUsuarioCoResponsavelAreaOrganizacional(areasIds);
		
		assertEquals(2, usuarioEmpresas.size());
	}

	public void testFindAllBySelectUsuarioEmpresaNull()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		assertTrue(usuarioEmpresaDao.findAllBySelectUsuarioEmpresa(empresa.getId()).isEmpty());
	}

	public void testRemoveAllUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		usuarioEmpresaDao.removeAllUsuario(usuario);

		assertNull(usuarioEmpresaDao.findById(usuarioEmpresa.getId(), null));
	}
	
	public void testFindPerfisEmpresas()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Perfil perfil = new Perfil();
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setPerfil(perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		assertEquals(true, usuarioEmpresaDao.findPerfisEmpresas().size() >= 1);
	}
	
	public void testfindUsuariosAtivo()
	{
		Empresa fortes = EmpresaFactory.getEmpresa();
		empresaDao.save(fortes);

		Empresa fox = EmpresaFactory.getEmpresa();
		empresaDao.save(fox);

		Usuario leo = UsuarioFactory.getEntity();
		leo.setAcessoSistema(true);
		usuarioDao.save(leo);

		UsuarioEmpresa usuarioEmpresaLeo = new UsuarioEmpresa();
		usuarioEmpresaLeo.setUsuario(leo);
		usuarioEmpresaLeo.setEmpresa(fox);
		usuarioEmpresaDao.save(usuarioEmpresaLeo);

		Usuario mel = UsuarioFactory.getEntity();
		mel.setAcessoSistema(true);
		usuarioDao.save(mel);
		
		UsuarioEmpresa usuarioEmpresaMel = new UsuarioEmpresa();
		usuarioEmpresaMel.setUsuario(mel);
		usuarioEmpresaMel.setEmpresa(fortes);
		usuarioEmpresaDao.save(usuarioEmpresaMel);

		Usuario teo = UsuarioFactory.getEntity();
		teo.setAcessoSistema(false);
		usuarioDao.save(teo);
		
		UsuarioEmpresa usuarioEmpresaTeo = new UsuarioEmpresa();
		usuarioEmpresaTeo.setUsuario(teo);
		usuarioEmpresaTeo.setEmpresa(fox);
		usuarioEmpresaDao.save(usuarioEmpresaTeo);

		Collection<Usuario> usuarios = Arrays.asList(leo, mel, teo);
		
		assertEquals(1, usuarioEmpresaDao.findUsuariosAtivo(LongUtil.collectionToCollectionLong(usuarios), fox.getId()).size());
	}
	
	public void testFindUsuariosByEmpresaRoleSetorPessoal()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Papel papel1 = new Papel();
		papel1.setCodigo("ROLE_VISUALIZAR_PENDENCIA_AC");
		papel1 = papelDao.save(papel1);

		Papel papel2 = new Papel();
		papel2.setCodigo("TESTE");
		papel2 = papelDao.save(papel2);
		
		Collection<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel1);
		papeis.add(papel2);
		
		Perfil perfil1 = new Perfil();
		perfil1.setPapeis(papeis);
		perfil1 = perfilDao.save(perfil1);

		Perfil perfil2 = new Perfil();
		perfil2.setPapeis(papeis);
		perfil2 = perfilDao.save(perfil2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("01020304");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);
		
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuario1 = usuarioDao.save(usuario1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2 = usuarioDao.save(usuario2);
		
		UsuarioEmpresa usuarioEmpresa1 = new UsuarioEmpresa();
		usuarioEmpresa1.setUsuario(usuario1);
		usuarioEmpresa1.setEmpresa(empresa);
		usuarioEmpresa1.setPerfil(perfil1);
		usuarioEmpresa1 = usuarioEmpresaDao.save(usuarioEmpresa1);

		UsuarioEmpresa usuarioEmpresa2 = new UsuarioEmpresa();
		usuarioEmpresa2.setUsuario(usuario2);
		usuarioEmpresa2.setEmpresa(empresa);
		usuarioEmpresa2.setPerfil(perfil2);
		usuarioEmpresa2 = usuarioEmpresaDao.save(usuarioEmpresa2);
		
		UsuarioEmpresa usuarioEmpresa3 = new UsuarioEmpresa();
		usuarioEmpresa3.setUsuario(usuario2);
		usuarioEmpresa3.setEmpresa(empresa);
		usuarioEmpresa3= usuarioEmpresaDao.save(usuarioEmpresa3);
		
		// por codigoAC
		assertEquals(1, usuarioEmpresaDao.findUsuariosByEmpresaRole(empresa.getCodigoAC(), "XXX", null, "ROLE_VISUALIZAR_PENDENCIA_AC", usuario1.getId()).size());
		
		// por empresaId
		assertEquals(2, usuarioEmpresaDao.findUsuariosByEmpresaRole(null, null, empresa.getId(), "ROLE_VISUALIZAR_PENDENCIA_AC", null).size());
	}
	
	public void testFindUsuariosByEmpresaRole()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Papel papel = new Papel();
		papel.setCodigo("xx");
		papelDao.save(papel);
		
		Collection<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);
		
		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setPerfil(perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaDao.findUsuariosByEmpresaRole(empresa.getId(), "xx");
		assertEquals(1, usuarioEmpresas.size());
		
	}

	public GenericDao<UsuarioEmpresa> getGenericDao()
	{
		return usuarioEmpresaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setUsuarioEmpresaDao(UsuarioEmpresaDao usuarioEmpresaDao)
	{
		this.usuarioEmpresaDao = usuarioEmpresaDao;
	}

	public void setPapelDao(PapelDao papelDao)
	{
		this.papelDao = papelDao;
	}

	public void setPerfilDao(PerfilDao perfilDao)
	{
		this.perfilDao = perfilDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

}