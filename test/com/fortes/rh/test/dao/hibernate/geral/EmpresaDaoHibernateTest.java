package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class EmpresaDaoHibernateTest extends GenericDaoHibernateTest<Empresa>
{
	private EmpresaDao empresaDao;
	private CidadeDao cidadeDao;
	private EstadoDao estadoDao;
	private QuestionarioDao questionarioDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private ColaboradorDao colaboradorDao;
	private UsuarioDao usuarioDao;
	private UsuarioEmpresaDao usuarioEmpresaDao;
	private PerfilDao perfilDao;
	private PapelDao papelDao;

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

	public void testFindByCodigo()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa = empresaDao.save(empresa);

		Empresa emp = empresaDao.findByCodigo("001122");

		assertNotNull(emp);
		assertEquals(emp.getCodigoAC(), "001122");
	}
	
	public void testVerifyExistsCnpj()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCnpj("00123456");
		empresa = empresaDao.save(empresa);
		
		assertEquals(1, empresaDao.verifyExistsCnpj(empresa.getCnpj()).size());
	}
	
	public void testGetIntegracaoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa.setAcIntegra(true);
		empresa = empresaDao.save(empresa);
		
		assertEquals(true, empresaDao.getIntegracaoAC(empresa.getId()));
	}
	
	public void testFindExibirSalarioById()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setExibirSalario(false);
		empresa = empresaDao.save(empresa);
		
		boolean exibir = empresaDao.findExibirSalarioById(empresa.getId());
		
		assertFalse(exibir);
	}
	
	public void testFindByUsuarioPermissao()
	{
		Usuario joao = UsuarioFactory.getEntity();
		joao.setAcessoSistema(true);
		usuarioDao.save(joao);
		
		Papel papel1 = new Papel();
		papel1.setCodigo("ROLE_REL_ANIV");
		papelDao.save(papel1);

		Papel papel2 = new Papel();
		papel2.setCodigo("ROLE_REL_CARGO");
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
		
		Collection<Empresa> empresas = empresaDao.findByUsuarioPermissao(joao.getId(), "ROLE_REL_ANIV");
		assertEquals(1, empresas.size());
		assertEquals(vega, empresas.toArray()[0]);
	}
	
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

	public void setCidadeDao(CidadeDao cidadeDao)
	{
		this.cidadeDao = cidadeDao;
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao) {
		this.questionarioDao = questionarioDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public void setUsuarioEmpresaDao(UsuarioEmpresaDao usuarioEmpresaDao)
	{
		this.usuarioEmpresaDao = usuarioEmpresaDao;
	}

	public void setPerfilDao(PerfilDao perfilDao)
	{
		this.perfilDao = perfilDao;
	}

	public void setPapelDao(PapelDao papelDao)
	{
		this.papelDao = papelDao;
	}
}