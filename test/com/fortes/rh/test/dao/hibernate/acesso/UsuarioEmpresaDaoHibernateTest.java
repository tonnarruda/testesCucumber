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
import com.fortes.rh.test.factory.acesso.PapelFactory;
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

		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, null);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		assertEquals(usuarioEmpresa, usuarioEmpresaDao.findAllBySelectUsuarioEmpresa(empresa.getId()).toArray()[0]);
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, null);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);
		
		assertEquals(usuarioEmpresa, usuarioEmpresaDao.findByUsuario(usuario.getId()).toArray()[0]);
	}
	
	public void testFindUsuarioResponsavelAreaOrganizacional()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador responsavelArea = ColaboradorFactory.getEntity("João", empresa, usuario, null);
		colaboradorDao.save(responsavelArea);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, responsavelArea, null, null);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador responsavelArea2 = ColaboradorFactory.getEntity("João", empresa, usuario2, null);
		colaboradorDao.save(responsavelArea2);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(null, responsavelArea2, null, null);
		areaOrganizacionalDao.save(areaOrganizacional2);

		Usuario usuarioFora = UsuarioFactory.getEntity();
		usuarioDao.save(usuarioFora);
		
		Colaborador responsavelAreaFora = ColaboradorFactory.getEntity("João", empresa, usuarioFora, null);
		colaboradorDao.save(responsavelAreaFora);
		
		AreaOrganizacional areaOrganizacionalFora = AreaOrganizacionalFactory.getEntity(null, responsavelAreaFora, null, null);
		areaOrganizacionalDao.save(areaOrganizacionalFora);
		
		Collection<Long> areasIds = Arrays.asList(areaOrganizacional.getId(), areaOrganizacional2.getId());
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaDao.findUsuarioResponsavelAreaOrganizacional(areasIds);
		
		assertEquals(2, usuarioEmpresas.size());
	}
	
	public void testFindUsuarioCoResponsavelAreaOrganizacional()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador coResponsavelArea = ColaboradorFactory.getEntity("João", empresa, usuario, null);
		colaboradorDao.save(coResponsavelArea);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, null, coResponsavelArea, null);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador coResponsavelArea2 = ColaboradorFactory.getEntity("João", empresa, usuario2, null);
		colaboradorDao.save(coResponsavelArea2);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(null, null, coResponsavelArea2, null);
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		Usuario usuarioFora = UsuarioFactory.getEntity();
		usuarioDao.save(usuarioFora);
		
		Colaborador coResponsavelAreaFora = ColaboradorFactory.getEntity("João", empresa, usuarioFora, null);
		colaboradorDao.save(coResponsavelAreaFora);
		
		AreaOrganizacional areaOrganizacionalFora = AreaOrganizacionalFactory.getEntity(null, null, coResponsavelAreaFora, null);
		areaOrganizacionalDao.save(areaOrganizacionalFora);
		
		Collection<Long> areasIds = Arrays.asList(areaOrganizacional.getId(), areaOrganizacional2.getId());
		
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

		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, null);
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
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		assertEquals(true, usuarioEmpresaDao.findPerfisEmpresas().size() >= 1);
	}
	
	public void testfindUsuariosAtivo()
	{
		Empresa fortes = EmpresaFactory.getEmpresa();
		empresaDao.save(fortes);

		Empresa fox = EmpresaFactory.getEmpresa();
		empresaDao.save(fox);

		Usuario leo = UsuarioFactory.getEntity(null, true);
		usuarioDao.save(leo);

		UsuarioEmpresa usuarioEmpresaLeo = UsuarioEmpresaFactory.getEntity(null, leo, fox, null);
		usuarioEmpresaDao.save(usuarioEmpresaLeo);

		Usuario mel = UsuarioFactory.getEntity(null, true);
		usuarioDao.save(mel);
		
		UsuarioEmpresa usuarioEmpresaMel = UsuarioEmpresaFactory.getEntity(null, mel, fortes, null);
		usuarioEmpresaDao.save(usuarioEmpresaMel);

		Usuario teo = UsuarioFactory.getEntity(null, false);
		usuarioDao.save(teo);
		
		UsuarioEmpresa usuarioEmpresaTeo = UsuarioEmpresaFactory.getEntity(null, teo, fox, null);
		usuarioEmpresaDao.save(usuarioEmpresaTeo);

		Collection<Usuario> usuarios = Arrays.asList(leo, mel, teo);
		
		assertEquals(1, usuarioEmpresaDao.findUsuariosAtivo(LongUtil.collectionToCollectionLong(usuarios), fox.getId()).size());
	}
	
	public void testFindByColaboradorId()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Usuario usuario1 = UsuarioFactory.getEntity(null, true);
		usuarioDao.save(usuario1);
		
		Usuario usuario2 = UsuarioFactory.getEntity(null, false);
		usuarioDao.save(usuario2);
		
		UsuarioEmpresa usuarioEmpresa1 = UsuarioEmpresaFactory.getEntity(null, usuario1, empresa1, null);
		usuarioEmpresaDao.save(usuarioEmpresa1);
		
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity(null, usuario1, empresa2, null);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		UsuarioEmpresa usuarioEmpresa3 = UsuarioEmpresaFactory.getEntity(null, usuario2, empresa2, null);
		usuarioEmpresaDao.save(usuarioEmpresa3);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity("João", empresa1, usuario1, null);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity("João", empresa1, usuario2, null);
		colaboradorDao.save(colaborador2);
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaDao.findByColaboradorId(colaborador1.getId());
		
		assertEquals(2, usuarioEmpresas.size());
		assertEquals(colaborador1.getUsuario(), ((UsuarioEmpresa)usuarioEmpresas.toArray()[0]).getUsuario());
	}
	
	public void testFindUsuariosByEmpresaRoleSetorPessoal()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Papel papel1 = PapelFactory.getEntity(null, "ROLE_VISUALIZAR_PENDENCIA_AC");
		papelDao.save(papel1);

		Papel papel2 = PapelFactory.getEntity(null, "TESTE");
		papelDao.save(papel2);
		
		Collection<Papel> papeis = Arrays.asList(papel1, papel2);
		
		Perfil perfil1 = new Perfil();
		perfil1.setPapeis(papeis);
		perfil1 = perfilDao.save(perfil1);

		Perfil perfil2 = new Perfil();
		perfil2.setPapeis(papeis);
		perfil2 = perfilDao.save(perfil2);
		
		Empresa empresa = EmpresaFactory.getEmpresa(null, null, "01020304", grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);
		
		Usuario usuario1 = UsuarioFactory.getEntity(null, true);
		usuario1 = usuarioDao.save(usuario1);

		Usuario usuario2 = UsuarioFactory.getEntity(null, true);
		usuario2 = usuarioDao.save(usuario2);
		
		UsuarioEmpresa usuarioEmpresa1 = UsuarioEmpresaFactory.getEntity(null, usuario1, empresa, perfil1);
		usuarioEmpresa1 = usuarioEmpresaDao.save(usuarioEmpresa1);

		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity(null, usuario2, empresa, perfil2);
		usuarioEmpresa2 = usuarioEmpresaDao.save(usuarioEmpresa2);
		
		UsuarioEmpresa usuarioEmpresa3 = UsuarioEmpresaFactory.getEntity(null, usuario2, empresa, null);
		usuarioEmpresa3= usuarioEmpresaDao.save(usuarioEmpresa3);
		
		// por codigoAC
		assertEquals(1, usuarioEmpresaDao.findUsuariosByEmpresaRole(empresa.getCodigoAC(), grupoAC.getCodigo(), null, "ROLE_VISUALIZAR_PENDENCIA_AC", usuario1.getId()).size());
		
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
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaDao.findUsuariosByEmpresaRole(empresa.getId(), "xx");
		assertEquals(1, usuarioEmpresas.size());
	}
	
	public void testContainsRole() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		criaUsuarioEmpresa(usuario, empresa, "aaa");
		
		assertTrue(usuarioEmpresaDao.containsRole(usuario.getId(), empresa.getId(), "aaa"));
	}
	
	public void testContainsRoleReturnFalse() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		criaUsuarioEmpresa(usuario, empresa, "xxx");
		
		assertFalse(usuarioEmpresaDao.containsRole(usuario.getId(), empresa.getId(), "x"));
	}
	
	private void criaUsuarioEmpresa(Usuario usuario, Empresa empresa, String role){
		Papel papel = new Papel();
		papel.setCodigo(role);
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