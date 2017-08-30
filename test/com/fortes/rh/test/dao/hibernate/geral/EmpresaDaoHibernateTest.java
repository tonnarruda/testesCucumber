package com.fortes.rh.test.dao.hibernate.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.GrupoACFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class EmpresaDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Empresa>
{
	@Autowired private EmpresaDao empresaDao;
	@Autowired private CidadeDao cidadeDao;
	@Autowired private EstadoDao estadoDao;
	@Autowired private QuestionarioDao questionarioDao;
	@Autowired private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private UsuarioDao usuarioDao;
	@Autowired private UsuarioEmpresaDao usuarioEmpresaDao;
	@Autowired private PerfilDao perfilDao;
	@Autowired private PapelDao papelDao;
	@Autowired private GrupoACDao grupoACDao;
	@Autowired private LntDao lntDao;

	public Empresa getEntity()
	{
		Empresa empresa = new Empresa();

		empresa.setId(null);
		empresa.setCnpj("0000000000");
		empresa.setNome("nome da empresa");
		empresa.setRazaoSocial("razao social");

		return empresa;
	}

	public GenericDao<Empresa> getGenericDao()
	{
		return empresaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	@Test
	public void testFindByCodigo()
	{	
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresaDao.save(empresa);

		Empresa emp = empresaDao.findByCodigo("001122", "XXX");

		assertNotNull(emp);
		assertEquals(emp.getCodigoAC(), "001122");
		assertEquals(emp.getGrupoAC(), "XXX");
	}
	
	@Test
	public void testFindByCodigoSemGrupoAC()
	{	
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setCodigoAC("001122");
		empresa2.setGrupoAC(null);
		empresaDao.save(empresa2);
		
		Empresa emp = empresaDao.findByCodigo("001122", null);
		
		assertNotNull(emp);
		assertEquals(emp.getCodigoAC(), "001122");
	}
	
	@Test
	public void testFindByCodigoSemGrupoACEmpresaDuplicada()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa.setGrupoAC(null);
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setCodigoAC("001122");
		empresa2.setGrupoAC(null);
		empresaDao.save(empresa2);
		
		Empresa emp = empresaDao.findByCodigo("001122", null);
		
		assertNotNull(emp);
		assertEquals(emp.getCodigoAC(), "001122");
	}

	@Test
	public void testFindByCodigoEmpresaNaoEncontrada()
	{	
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setCodigoAC("001122");
		empresa2.setGrupoAC(null);
		empresaDao.save(empresa2);
		
		Empresa emp = empresaDao.findByCodigo("001120", null);
		
		assertNull(emp);
	}
	
	@Test
	public void testUpdateCampoExtra()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCampoExtraColaborador(true);
		empresaDao.save(empresa);
		
		empresaDao.updateCampoExtra(empresa.getId(), false, true, false);
		Empresa emp = empresaDao.findByIdProjection(empresa.getId());
		assertEquals(false, emp.isCampoExtraColaborador());
		assertEquals(true, emp.isCampoExtraCandidato());

		empresaDao.updateCampoExtra(empresa.getId(), true, false, false);
		emp = empresaDao.findByIdProjection(empresa.getId());
		assertEquals(true, emp.isCampoExtraColaborador());
		assertEquals(false, emp.isCampoExtraCandidato());
	}
	
	@Test
	public void testVerifyExistsCnpj()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCnpj("00123456");
		empresa = empresaDao.save(empresa);
		
		assertEquals(1, empresaDao.verifyExistsCnpj(empresa.getCnpj()).size());
	}
	
	@Test
	public void testGetIntegracaoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa.setAcIntegra(true);
		empresa = empresaDao.save(empresa);
		
		assertEquals(true, empresaDao.getIntegracaoAC(empresa.getId()));
	}
	
	@Test
	public void testFindByUsuarioPermissao()
	{
		Usuario joao = UsuarioFactory.getEntity();
		joao.setAcessoSistema(true);
		usuarioDao.save(joao);
		
		Papel papel1 = new Papel();
		papel1.setCodigo("ROLE_REL_ANIV");
		papelDao.save(papel1);

		Papel papel2 = new Papel();
		papel2.setCodigo("ROLE_REL_XX");
		papelDao.save(papel2);
		
		Collection<Papel> papeis1 = new ArrayList<Papel>();
		papeis1.add(papel1);
		papeis1.add(papel2);

		Collection<Papel> papeis2 = new ArrayList<Papel>();
		papeis2.add(papel2);
		
		Perfil perfilAdmin = new Perfil();
		perfilAdmin.setNome("Admin");
		perfilAdmin.setPapeis(papeis1);
		perfilDao.save(perfilAdmin);
		
		Perfil perfilCargo = new Perfil();
		perfilCargo.setNome("Admin");
		perfilCargo.setPapeis(papeis2);
		perfilDao.save(perfilCargo);
		
		Empresa vega = EmpresaFactory.getEmpresa();
		vega.setNome("vega");
		vega = empresaDao.save(vega);
		
		Empresa urbana = EmpresaFactory.getEmpresa();
		urbana.setNome("urbana");
		urbana = empresaDao.save(urbana);
		
		UsuarioEmpresa usuarioEmpresa1 = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa1.setEmpresa(vega);
		usuarioEmpresa1.setUsuario(joao);
		usuarioEmpresa1.setPerfil(perfilAdmin);
		usuarioEmpresaDao.save(usuarioEmpresa1);

		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa2.setEmpresa(urbana);
		usuarioEmpresa2.setUsuario(joao);
		usuarioEmpresa2.setPerfil(perfilCargo);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		Collection<Empresa> empresas = empresaDao.findByUsuarioPermissao(joao.getId(), new String[]{"ROLE_REL_ANIV", "ROLE_REL_XX"});
		assertEquals(2, empresas.size());
		assertEquals(vega, empresas.toArray()[1]);
	}
	
	@Test
	public void testRemoveEmpresaPadrao()
	{
		String[] sqls = new String[]{"INSERT INTO empresa(ID,NOME,CNPJ,RAZAOSOCIAL,codigoAC,acintegra,emailRemetente,emailRespSetorPessoal,maxcandidatacargo,logourl,exibirsalario) VALUES (998855920,'EmpresaTesteDao','00000000','EmpresaTesteDao',null,false,'rh@empresapadrao.com.br','sp@empresapadrao.com.br', 5,'fortes.gif',true);", 
									"INSERT INTO auditoria (id, empresa_id, dados) values(nextval('auditoria_sequence'), 998855920, 'TESTE teste da empresa');"};
		JDBCConnection.executeQuery(sqls);
		
		empresaDao.removeEmpresaPadrao(998855920L);
		
		Empresa retorno = empresaDao.findByIdProjection(998855920L);
		assertEquals(null, retorno);
		
		String qtdTabelasComEmpresa = JDBCConnection.executeQuery("select count(table_name) from information_schema.columns as col where col.column_name = 'empresa_id' and col.table_schema = 'public';");
		//se esse quebrar, provavelmente tem que inserir uma linha de delete com removeEmpresaPadrao
		assertEquals("Se esse quebrar, provavelmente tem que inserir uma linha de delete em EmpresaDaoHibernate.removeEmpresaPadrao", "63", qtdTabelasComEmpresa);
	}
	
	@Test
	public void testFindCidade()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade.setNome("Palmacia");
		cidadeDao.save(cidade);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCidade(cidade);
		empresa = empresaDao.save(empresa);
		
		assertEquals("Palmacia", empresaDao.findCidade(empresa.getId()));
	}
	
	@Test
	public void testFindEmailsEmpresa()
	{
		Empresa fox = EmpresaFactory.getEmpresa();
		fox.setEmailRespRH("fox@gmail.com");
		empresaDao.save(fox);
		
		Empresa ente = EmpresaFactory.getEmpresa();
		ente.setEmailRespRH("fox@gmail.com");
		empresaDao.save(ente);
		
		assertEquals(ente.getEmailRespRH(), empresaDao.findEmailsEmpresa(ente.getId()).getEmailRespRH());
	}
	
	@Test
	public void testFindDistinctEmpresaByQuestionario()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Colaborador  colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Colaborador  colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);
		
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2MesmaEmpresa = new ColaboradorQuestionario();
		colaboradorQuestionario2MesmaEmpresa.setQuestionario(questionario);
		colaboradorQuestionario2MesmaEmpresa.setColaborador(colaborador2);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2MesmaEmpresa);
		
		assertEquals(1,empresaDao.findDistinctEmpresaByQuestionario(questionario.getId()).size());
	}
	
	@Test
	public void testCheckEmpresaCodACGrupoAC()
	{
		GrupoAC grupo = new GrupoAC("017", "Teste");
		grupoACDao.save(grupo);
		
		Empresa empresa = EmpresaFactory.getEmpresa(3025L);
		empresa.setCodigoAC("1141");
		empresa.setGrupoAC("017");
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(3026L);
		empresa2.setCodigoAC("1141");
		empresa2.setGrupoAC("017");
		
		assertEquals(true, empresaDao.checkEmpresaCodACGrupoAC(empresa2));
	}
	
	@Test
	public void testCheckEmpresaIntegradaAc()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(3025L);
		empresa.setAcIntegra(false);
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(3026L);
		empresa2.setAcIntegra(true);
		empresaDao.save(empresa2);
		
		assertTrue(empresaDao.checkEmpresaIntegradaAc());
	}
	
	@Test
	public void testCheckEmpresaIntegradaAcComEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(3025L);
		empresa.setAcIntegra(false);
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(3026L);
		empresa2.setAcIntegra(true);
		empresaDao.save(empresa2);
		
		assertFalse(empresaDao.checkEmpresaIntegradaAc(empresa.getId()));
		assertTrue(empresaDao.checkEmpresaIntegradaAc(empresa2.getId()));
	}
	
	@Test
	public void testFindComCodigoAC() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001");
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		assertTrue("A consulta também traz empresas já cadastrados no banco",empresaDao.findComCodigoAC().size() >= 1);
	}
	
	@Test
	public void testControlaRiscoPorAmbiente() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		empresaDao.save(empresa);
		
		assertTrue(empresaDao.isControlaRiscoPorAmbiente(empresa.getId()));
	}
	
	@Test
	public void testGetCnae() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCnae("cnae1");
		empresa.setCnae2("cnae2");
		empresaDao.save(empresa);
		
		Empresa retorno = empresaDao.getCnae(empresa.getId());
		assertEquals("cnae1", retorno.getCnae());
		assertEquals("cnae2", retorno.getCnae2());
	}
	
	@Test
	public void testUpdateCodigoAC()
	{
		GrupoAC grupoAC = GrupoACFactory.getEntity();
		grupoAC.setCodigo("100");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC(null);
		empresa.setGrupoAC(null);
		empresaDao.save(empresa);
		
		empresa.setCodigoAC("0005");
		empresa.setGrupoAC("100");
		empresaDao.updateCodigoAC(empresa.getId(), empresa.getCodigoAC(), empresa.getGrupoAC());
		
		Empresa retorno = empresaDao.findByIdProjection(empresa.getId());
		
		assertEquals("Codigo AC", empresa.getCodigoAC(), retorno.getCodigoAC());
		assertEquals("Grupo AC", empresa.getGrupoAC(), retorno.getGrupoAC());
	}
	
	@Test
	public void testFindByGruposAC()
	{
		GrupoAC grupoAC1 = GrupoACFactory.getEntity();
		grupoAC1.setCodigo("111");
		grupoACDao.save(grupoAC1);
		
		GrupoAC grupoAC2 = GrupoACFactory.getEntity();
		grupoAC2.setCodigo("222");
		grupoACDao.save(grupoAC2);
		
		GrupoAC grupoAC3 = GrupoACFactory.getEntity();
		grupoAC3.setCodigo("333");
		grupoACDao.save(grupoAC3);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1.setGrupoAC(grupoAC1.getCodigo());
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setGrupoAC(grupoAC2.getCodigo());
		empresaDao.save(empresa2);
		
		Empresa empresa3 = EmpresaFactory.getEmpresa();
		empresa3.setGrupoAC(grupoAC3.getCodigo());
		empresaDao.save(empresa3);
		
		Collection<Empresa> empresas = empresaDao.findByGruposAC(new String[]{grupoAC1.getCodigo(), grupoAC3.getCodigo()});
		
		assertEquals(2, empresas.size());
		assertEquals("111", ((Empresa) empresas.toArray()[0]).getGrupoAC());
		assertEquals("333", ((Empresa) empresas.toArray()[1]).getGrupoAC());
	}

	@Test
	public void testGetCodigoGrupoAC()
	{
		GrupoAC grupoAC = GrupoACFactory.getEntity();
		grupoAC.setCodigo("777");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("7777");
		empresa.setGrupoAC("777");
		empresaDao.save(empresa);

		assertEquals("777", empresaDao.getCodigoGrupoAC(empresa.getId()));
	}
	
	@Test
	public void testEmProcessoExportacaoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		assertFalse(empresaDao.emProcessoExportacaoAC(empresa.getId()));
		
		empresa.setProcessoExportacaoAC(true);
		empresaDao.update(empresa);
		
		assertTrue(empresaDao.emProcessoExportacaoAC(empresa.getId()));
	}
	
	@Test
	public void testFindAreasIdsByLntId() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Collection<Empresa> empresas = Arrays.asList(empresa1, empresa2);
		
		Lnt lnt = LntFactory.getEntity(null, "LNT 1", new Date(), DateUtil.incrementaDias(new Date(), 1), null);
		lnt.setEmpresas(empresas);
		lntDao.save(lnt);
		
		Collection<Empresa> retorno = empresaDao.findByLntId(lnt.getId());
		
		assertEquals(lnt.getEmpresas().size(), retorno.size());
	}
	
	@Test
	public void testFindAreasIdsByLntIdRetornaCollectionVazia() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Collection<Empresa> empresas = Arrays.asList(empresa1, empresa2);
		
		Lnt lnt = LntFactory.getEntity(null, "LNT 1", new Date(), DateUtil.incrementaDias(new Date(), 1), null);
		lnt.setEmpresas(empresas);
		lntDao.save(lnt);
		
		Lnt lnt2 = LntFactory.getEntity(null, "LNT 2", new Date(), DateUtil.incrementaDias(new Date(), 1), null);
		lntDao.save(lnt2);
		
		Collection<Empresa> retorno = empresaDao.findByLntId(lnt2.getId());
		
		assertEquals(0, retorno.size());
	}
}