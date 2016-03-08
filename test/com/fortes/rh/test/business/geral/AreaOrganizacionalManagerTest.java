package com.fortes.rh.test.business.geral;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientLotacao;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class AreaOrganizacionalManagerTest extends MockObjectTestCase
{
	private AreaOrganizacionalManagerImpl areaOrganizacionalManager = new AreaOrganizacionalManagerImpl();
	private Mock acPessoalClientLotacao = null;
	private Mock areaOrganizacionalDao = null;
	Mock parametrosDoSistemaManager;
	Mock colaboradorManager;
	Mock transactionManager;
	Mock historicoColaboradorManager;
	Mock cargoManager;

    protected void setUp() throws Exception
    {
        super.setUp();

        areaOrganizacionalDao = new Mock(AreaOrganizacionalDao.class);
        acPessoalClientLotacao = new Mock(AcPessoalClientLotacao.class);

        areaOrganizacionalManager.setDao((AreaOrganizacionalDao) areaOrganizacionalDao.proxy());
		areaOrganizacionalManager.setAcPessoalClientLotacao((AcPessoalClientLotacao) acPessoalClientLotacao.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        areaOrganizacionalManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);

		colaboradorManager = new Mock(ColaboradorManager.class);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		
		cargoManager = new Mock(CargoManager.class);
		MockSpringUtil.mocks.put("cargoManager", cargoManager);
		
		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }

    public void testPopulaAreas()
    {
    	String[] areasCheck = {"1","2"};
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	AreaOrganizacional a1 = new AreaOrganizacional();
    	a1.setId(1L);
    	AreaOrganizacional a2 = new AreaOrganizacional();
    	a2.setId(2L);

    	areas.add(a1);
    	areas.add(a2);

    	Collection<AreaOrganizacional> areasTmp = areaOrganizacionalManager.populaAreas(areasCheck);

    	assertTrue(areasTmp.size() == 2);
    	assertEquals(areas, areasTmp);

    }
    
    public void testBind()
    {
    	AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(2L);

    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(2L);
    	
    	TAreaOrganizacional tAreaOrganizacional = new TAreaOrganizacional();
    	tAreaOrganizacional.setNome("Area do ac");
    	tAreaOrganizacional.setId(2L);
    	tAreaOrganizacional.setCodigo("00102");
    	tAreaOrganizacional.setAreaMaeCodigo("001");
    	tAreaOrganizacional.setEmpresaCodigo("002");
    	tAreaOrganizacional.setGrupoAC("1");
    	
    	areaOrganizacionalDao.expects(atLeastOnce()).method("findAreaOrganizacionalByCodigoAc").with(eq(tAreaOrganizacional.getAreaMaeCodigo()), eq(tAreaOrganizacional.getEmpresaCodigo()), eq(tAreaOrganizacional.getGrupoAC())).will(returnValue(areaMae));
    	
    	Exception exp = null;
    	try {
			areaOrganizacionalManager.bind(areaOrganizacional, tAreaOrganizacional);
		} catch (Exception e) {
			exp = e;
		}
    	
    	assertNull(exp);
    }

    public void testVerificaMaternidade()
    {
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	areaOrganizacionalDao.expects(atLeastOnce()).method("verificaMaternidade").with(eq(areaOrganizacional.getId()), ANYTHING).will(returnValue(true));

    	assertTrue(areaOrganizacionalManager.verificaMaternidade(areaOrganizacional.getId(), null));
    }

    public void testFindAllList()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{eq(0), eq(0), eq(null), eq(null), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{1L})}).will(returnValue(areas));

    	assertEquals(areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, 1L), areas);
    }

    public void testFindAllListComIdUsuario()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{eq(0), eq(0), eq(1L), eq(null), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{1L})}).will(returnValue(areas));

    	assertEquals(areaOrganizacionalManager.findAllList(1L,1L, AreaOrganizacional.TODAS, null), areas);
    }

    public void testFindAllListComPaginacao()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{eq(1), eq(15), eq(null), eq(""), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{1L})}).will(returnValue(areas));

    	assertEquals(areaOrganizacionalManager.findAllList(1, 15, "", 1L, AreaOrganizacional.TODAS), areas);
    }

    public void testPopulaCheckOrderDescricao()
	{
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

		areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING, ANYTHING}).will(returnValue(areas));

		assertEquals(areaOrganizacionalManager.populaCheckOrderDescricao(1L), new ArrayList<CheckBox>());

		areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING, ANYTHING});

		assertEquals(areaOrganizacionalManager.populaCheckOrderDescricao(1L), new ArrayList<CheckBox>());

	}
    
    public void testPopulaCheckOrderDescricaoArrayEmpresa()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	
    	areaOrganizacionalDao.expects(once()).method("findByEmpresasIds").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(areas));
    	
    	assertEquals(new ArrayList<CheckBox>(), areaOrganizacionalManager.populaCheckOrderDescricao(new Long[]{1L}));
    }

    public void testGetDistinctAreaMae()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	Collection<AreaOrganizacional> todasAreas = new ArrayList<AreaOrganizacional>();

    	AreaOrganizacional area1 = new AreaOrganizacional();
    	area1.setId(1L);

    	AreaOrganizacional area2 = new AreaOrganizacional();
    	area2.setId(2L);

    	AreaOrganizacional area3 = new AreaOrganizacional();
    	area3.setId(3L);

    	AreaOrganizacional area4 = new AreaOrganizacional();
    	area4.setId(4L);

    	AreaOrganizacional area5 = new AreaOrganizacional();
    	area5.setId(5L);

    	areas.add(area1);
    	areas.add(area2);
    	areas.add(area3);

    	todasAreas.add(area1);
    	todasAreas.add(area2);
    	todasAreas.add(area3);
    	todasAreas.add(area4);
    	todasAreas.add(area5);

    	Collection<AreaOrganizacional> retorno = areaOrganizacionalManager.getDistinctAreaMae(todasAreas, areas);

    	assertEquals(3, retorno.size());
    }
    
    public void testGetAncestrais()
    {
    	Collection<AreaOrganizacional> todasAreas = new ArrayList<AreaOrganizacional>();
    	
    	AreaOrganizacional avo = new AreaOrganizacional();
    	avo.setId(1L);
    	
    	AreaOrganizacional mae = new AreaOrganizacional();
    	mae.setAreaMae(avo);
    	mae.setId(2L);
    	
    	AreaOrganizacional filha = new AreaOrganizacional();
    	filha.setAreaMae(mae);
    	filha.setId(3L);
    	
    	AreaOrganizacional vizinha = new AreaOrganizacional();
    	vizinha.setId(4L);

    	AreaOrganizacional babau = new AreaOrganizacional();
    	babau.setAreaMae(vizinha);
    	babau.setId(7L);
    	
    	AreaOrganizacional irma = new AreaOrganizacional();
    	irma.setAreaMae(mae);
    	irma.setId(5L);
    	
    	todasAreas.add(avo);
    	todasAreas.add(mae);
    	todasAreas.add(filha);
    	todasAreas.add(vizinha);
    	todasAreas.add(babau);
    	todasAreas.add(irma);
    	
    	Collection<AreaOrganizacional> retorno = areaOrganizacionalManager.getAncestrais(todasAreas, filha.getId());
    	
    	assertEquals(3, retorno.size());
    }

    public void	testFindAreasPossiveis()
    {
    	Collection<AreaOrganizacional> areasFilhas = new ArrayList<AreaOrganizacional>();
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	AreaOrganizacional af1 = new AreaOrganizacional();
    	af1.setId(1L);

    	AreaOrganizacional af2 = new AreaOrganizacional();
    	af2.setId(2L);
    	af2.setAreaMae(af1);

    	AreaOrganizacional af3 = new AreaOrganizacional();
    	af3.setId(3L);
    	af3.setAreaMae(af1);

    	areasFilhas.add(af2);
    	areasFilhas.add(af3);

    	AreaOrganizacional a1 = new AreaOrganizacional();
    	a1.setId(1L);

    	AreaOrganizacional a2 = new AreaOrganizacional();
    	a2.setId(2L);
    	a2.setAreaMae(a1);

    	areas.add(a2);

    	areaOrganizacionalDao.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(af1));

    	assertEquals(areasFilhas.size(), areaOrganizacionalManager.findAreasPossiveis(areas, 1L).size());

    }

    public void testGetAreaOrganizacional()
    {
    	AreaOrganizacional a1 = new AreaOrganizacional();
    	a1.setId(1L);

    	AreaOrganizacional a2 = new AreaOrganizacional();
    	a2.setId(2L);

    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	areas.add(a1);
    	areas.add(a2);

    	assertEquals(a1.getId(), areaOrganizacionalManager.getAreaOrganizacional(areas, a1.getId()).getId());
    }

    public void testGetAreaOrganizacionalComNull()
    {
    	AreaOrganizacional a1 = new AreaOrganizacional();
    	a1.setId(1L);

    	AreaOrganizacional a2 = new AreaOrganizacional();
    	a2.setId(2L);

    	AreaOrganizacional a3 = new AreaOrganizacional();
    	a3.setId(3L);

    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	areas.add(a1);
    	areas.add(a2);

    	AreaOrganizacional retorno = areaOrganizacionalManager.getAreaOrganizacional(areas, a3.getId());

    	assertNull(retorno);
    }

    public void testGetAreaMaeByAreas()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	AreaOrganizacional areaMae = new AreaOrganizacional();
    	areaMae.setId(2L);

    	AreaOrganizacional areaTeste = new AreaOrganizacional();
    	areaTeste.setId(3L);

    	areas.add(areaMae);
    	areas.add(areaTeste);

    	AreaOrganizacional area = new AreaOrganizacional();
    	area.setId(1L);
    	area.setAreaMae(areaMae);

    	assertEquals(area.getAreaMae(), areaOrganizacionalManager.getAreaMae(areas, area).getAreaMae());
    }

    public void testFindAreaOrganizacionalCodigoAc()
    {
       	AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
    	a1.setId(1L);
    	a1.setCodigoAC("01");

    	areaOrganizacionalDao.expects(once()).method("findAreaOrganizacionalCodigoAc").with(eq(a1.getId())).will(returnValue(a1));

    	AreaOrganizacional retorno = areaOrganizacionalManager.findAreaOrganizacionalCodigoAc(a1.getId());

    	assertEquals(a1.getCodigoAC(), retorno.getCodigoAC());
    	assertEquals(a1.getId(), retorno.getId());

    }

    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	areaOrganizacionalDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<AreaOrganizacional>()));

    	assertNotNull(areaOrganizacionalManager.findByCargo(cargo.getId()));
    }

    public void testMontaFamilia() throws Exception
    {
    	Collection<AreaOrganizacional> areas = preparaCollection();

    	Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areas);

    	assertEquals(5, areaOrganizacionals.size());
    	for (AreaOrganizacional areaTmp : areaOrganizacionals)
		{
			if(areaTmp.getId().equals(1L))
			{
				assertEquals(areaTmp.getDescricao(), "Area Bisavo > Area Avo > Area Mae > Area 1");
			}
			else if(areaTmp.getId().equals(2L))
			{
				assertEquals(areaTmp.getDescricao(), "Area 2");
			}
		}
    }

	private Collection<AreaOrganizacional> preparaCollection()
	{
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	AreaOrganizacional area1 = new AreaOrganizacional();
    	area1.setNome("Area 1");
    	area1.setId(1L);

    	AreaOrganizacional area2 = new AreaOrganizacional();
    	area2.setNome("Area 2");
    	area2.setId(2L);

    	AreaOrganizacional areaMae = new AreaOrganizacional();
    	areaMae.setNome("Area Mae");
    	areaMae.setId(3L);

    	AreaOrganizacional areaAvo = new AreaOrganizacional();
    	areaAvo.setNome("Area Avo");
    	areaAvo.setId(4L);

    	AreaOrganizacional areaBisavo = new AreaOrganizacional();
    	areaBisavo.setNome("Area Bisavo");
    	areaBisavo.setId(5L);

    	areaAvo.setAreaMae(areaBisavo);
    	areaMae.setAreaMae(areaAvo);
    	area1.setAreaMae(areaMae);

    	areas.add(area1);
    	areas.add(area2);
    	areas.add(areaMae);
    	areas.add(areaAvo);
    	areas.add(areaBisavo);
		return areas;
	}

    public void testInsertLotacaoAC() throws Exception
    {
    	AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(2L);
    	
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	areaOrganizacional.setAreaMae(areaMae);
    	areaOrganizacional.setResponsavel(null);
    	areaOrganizacional.setCodigoAC("001");

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);

    	areaOrganizacionalDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
    	areaOrganizacionalDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
    	
    	areaOrganizacionalManager.insert(areaOrganizacional, empresa);
    	assertEquals("001", areaOrganizacional.getCodigoAC());
    }
    
    public void testInsertLotacaoACIntegrado() throws Exception
    {
    	AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(1L);
    	
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	areaOrganizacional.setAreaMae(areaMae);
    	areaOrganizacional.setResponsavel(null);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);

    	areaOrganizacionalDao.expects(once()).method("findAreaOrganizacionalCodigoAc").with(ANYTHING);
    	acPessoalClientLotacao.expects(once()).method("criarLotacao").with(ANYTHING,ANYTHING).will(returnValue("001"));
    	areaOrganizacionalDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
    	areaOrganizacionalDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
    	
    	areaOrganizacionalManager.insert(areaOrganizacional, empresa);
    	assertEquals("001", areaOrganizacional.getCodigoAC());
    }

    public void testMontaAllSelect()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	Collection<AreaOrganizacional> areas = preparaCollection();
    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[] {eq(0), eq(0), eq(null), eq(null), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})}).will(returnValue(areas));

    	assertEquals(5, areaOrganizacionalManager.montaAllSelect(empresa.getId()).size());
    }

    public void testMontaAllSelectException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[] {eq(0), eq(0), eq(null), eq(null), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})}).will(returnValue(null));

    	assertTrue(areaOrganizacionalManager.montaAllSelect(empresa.getId()).isEmpty());
    }

    public void testFindAllSelectOrderDescricao() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[] {eq(0), eq(0), eq(null), eq(null), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})}).will(returnValue(preparaCollection()));

    	assertEquals(5, areaOrganizacionalManager.findAllSelectOrderDescricao(empresa.getId(), AreaOrganizacional.TODAS, null, false).size());
    }
    
    public void testFindAllSelectOrderDescricaoSomenteFolhas() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[] {eq(0), eq(0), eq(null), eq(null), eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})}).will(returnValue(preparaCollection()));

    	assertEquals(2, areaOrganizacionalManager.findAllSelectOrderDescricao(empresa.getId(), AreaOrganizacional.TODAS, null, true).size());
    }

    public void testGetNaoFamilia() throws Exception
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

    	AreaOrganizacional areaMae = new AreaOrganizacional();
    	areaMae.setId(1L);

    	AreaOrganizacional area = new AreaOrganizacional();
    	area.setAreaMae(areaMae);
    	area.setId(2L);

    	AreaOrganizacional areaSemFamilia = new AreaOrganizacional();
    	areaSemFamilia.setId(3L);

    	areas.add(area);
    	areas.add(areaMae);
    	areas.add(areaSemFamilia);

    	areaOrganizacionalDao.expects(once()).method("findByIdProjection").with(eq(area.getId())).will(returnValue(area));
    	assertEquals(2, areaOrganizacionalManager.getNaoFamilia(areas, area.getId()).size());
    }

    public void testGetAreasByAreaInteresse()
    {
    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse(1L);

    	Collection<Long> areasIds = new ArrayList<Long>();
    	areasIds.add(1L);
    	areasIds.add(2L);

    	areaOrganizacionalDao.expects(once()).method("findAreaIdsByAreaInteresse").with(eq(areaInteresse.getId())).will(returnValue(areasIds));

    	assertEquals(2, areaOrganizacionalManager.getAreasByAreaInteresse(areaInteresse.getId()).size());
    }

    public void testSet()
    {
    	areaOrganizacionalManager.setAcPessoalClientLotacao(null);
    }

	public void testGetParametrosRelatorio()
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setAppVersao("1.01.1");

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa");
		empresa.setLogoUrl("logo");

		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));

		Map<String, Object> parametros = areaOrganizacionalManager.getParametrosRelatorio("Relatório Teste", empresa, "");

		Cabecalho cabecalho = (Cabecalho) parametros.get("CABECALHO");
		assertEquals("xxx" + File.separatorChar, parametros.get("SUBREPORT_DIR"));
		assertEquals(empresa.getNome(), cabecalho.getNomeEmpresa());
		assertEquals("", cabecalho.getLogoUrl());//Arquivo nao existe
	}

	public void testDeleteLotacaoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		Long[] areaIds = new Long[] {areaOrganizacional.getId()};

		areaOrganizacionalDao.expects(once()).method("findAreaOrganizacionalCodigoAc").with(eq(areaOrganizacional.getId())).will(returnValue(areaOrganizacional));
		areaOrganizacionalDao.expects(once()).method("remove").with(eq(areaIds)).isVoid();
		areaOrganizacionalDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientLotacao.expects(once()).method("deleteLotacao").with(eq(areaOrganizacional), eq(empresa)).will(returnValue(true));

		Exception exception = null;
		try
		{
			areaOrganizacionalManager.deleteLotacaoAC(areaOrganizacional, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testDeleteLotacaoAcComExcecao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		Long[] areaIds = new Long[] {areaOrganizacional.getId()};

		areaOrganizacionalDao.expects(once()).method("findAreaOrganizacionalCodigoAc").with(eq(areaOrganizacional.getId())).will(returnValue(areaOrganizacional));
		areaOrganizacionalDao.expects(once()).method("remove").with(eq(areaIds)).isVoid();
		areaOrganizacionalDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientLotacao.expects(once()).method("deleteLotacao").with(eq(areaOrganizacional), eq(empresa)).will(throwException(new IntegraACException("Erro")));

		Exception exception = null;
		try
		{
			areaOrganizacionalManager.deleteLotacaoAC(areaOrganizacional, empresa);
		}
		catch (IntegraACException e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testFindAreaOrganizacionalByCodigoAc()
	{
		String areaCodigoAC = "001";
		String empresaCodigoAC = "002";

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setCodigoAC(areaCodigoAC);

		areaOrganizacionalDao.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(areaCodigoAC), eq(empresaCodigoAC), eq("XXX")).will(returnValue(areaOrganizacional));

		AreaOrganizacional retorno = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(areaCodigoAC, empresaCodigoAC, "XXX");

		assertEquals(areaCodigoAC, retorno.getCodigoAC());
	}

	public void testGetDistinctAreas()
	{
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);

		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(areaOrganizacional1);
		areas.add(areaOrganizacional2);

		AreaOrganizacionalManagerImpl.getDistinctAreas(areas);
	}
	
	public void testSetMatriarca()
	{
		AreaOrganizacional areaVo = AreaOrganizacionalFactory.getEntity(1L);
		areaVo.setNome("areaVo");
		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(2L);
		areaMae.setNome("areaMae");
		AreaOrganizacional areaTia = AreaOrganizacionalFactory.getEntity(3L);
		areaTia.setNome("areaTia");
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(4L);
		area.setNome("area");
		AreaOrganizacional areaPaiVizim = AreaOrganizacionalFactory.getEntity(5L);
		areaPaiVizim.setNome("areaPaiVizim");
		AreaOrganizacional areaVizim = AreaOrganizacionalFactory.getEntity(6L);
		areaVizim.setNome("areaVizim");
		AreaOrganizacional areaSolitaria = AreaOrganizacionalFactory.getEntity(7L);
		areaSolitaria.setNome("areaSolitaria");
		
		areaMae.setAreaMae(areaVo);
		areaTia.setAreaMae(areaVo);
		area.setAreaMae(areaMae);
		areaVizim.setAreaMae(areaPaiVizim);
		
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(areaVo);
		areas.add(areaMae);
		areas.add(areaTia);
		areas.add(area);
		areas.add(areaPaiVizim);
		areas.add(areaVizim);
		areas.add(areaSolitaria);
		
		assertEquals(areaVo.getId(), areaOrganizacionalManager.getMatriarca(areas, area, null).getId());
		assertEquals(areaPaiVizim.getId(), areaOrganizacionalManager.getMatriarca(areas, areaVizim, null).getId());
		assertEquals(areaSolitaria.getId(), areaOrganizacionalManager.getMatriarca(areas, areaSolitaria, null).getId());
	}
	
	public void testSetMatriarca2()
	{
		AreaOrganizacional areaVo = AreaOrganizacionalFactory.getEntity(1L);
		areaVo.setNome("areaVo");
		AreaOrganizacional areaVo2 = AreaOrganizacionalFactory.getEntity(9L);
		areaVo2.setNome("areaVo2");
		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(2L);
		areaMae.setNome("areaMae");
		AreaOrganizacional areaTia = AreaOrganizacionalFactory.getEntity(3L);
		areaTia.setNome("areaTia");
		AreaOrganizacional areaFilinha = AreaOrganizacionalFactory.getEntity(8L);
		areaFilinha.setNome("areaFilinha");
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(4L);
		area.setNome("area");
		AreaOrganizacional areaPaiVizim = AreaOrganizacionalFactory.getEntity(5L);
		areaPaiVizim.setNome("areaPaiVizim");
		AreaOrganizacional areaVizim = AreaOrganizacionalFactory.getEntity(6L);
		areaVizim.setNome("areaVizim");
		AreaOrganizacional areaSolitaria = AreaOrganizacionalFactory.getEntity(7L);
		areaSolitaria.setNome("areaSolitaria");
		
		areaMae.setAreaMae(areaVo);
		areaTia.setAreaMae(areaVo);
		area.setAreaMae(areaMae);
		areaFilinha.setAreaMae(area);
		areaVizim.setAreaMae(areaPaiVizim);
		areaVizim.setAreaMae(areaVo2);
		
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(areaFilinha);
		areas.add(areaVo);
		areas.add(areaMae);
		areas.add(areaTia);
		areas.add(area);
		areas.add(areaPaiVizim);
		areas.add(areaVizim);
		areas.add(areaSolitaria);
		
		assertEquals(areaMae.getId(), areaOrganizacionalManager.getMatriarca(areas, areaFilinha, areaVo.getId()).getId());
		assertEquals(areaMae.getId(), areaOrganizacionalManager.getMatriarca(areas, area, areaVo.getId()).getId());
		assertEquals(areaTia.getId(), areaOrganizacionalManager.getMatriarca(areas, areaTia, areaVo.getId()).getId());
		assertEquals(areaSolitaria.getId(), areaOrganizacionalManager.getMatriarca(areas, areaSolitaria, areaVo.getId()).getId());
	}

	public void testEditarLotacaoAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		areaOrganizacionalDao.expects(once()).method("update").with(ANYTHING);
		areaOrganizacionalDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientLotacao.expects(once()).method("criarLotacao").with(ANYTHING, ANYTHING);

		Exception exception = null;
		try
		{
			areaOrganizacionalManager.update(areaOrganizacional, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testEditarLotacaoAcComExcecao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		areaOrganizacionalDao.expects(once()).method("update").with(ANYTHING);
		areaOrganizacionalDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));

		acPessoalClientLotacao.expects(once()).method("criarLotacao").with(ANYTHING, ANYTHING).will(throwException(new Exception("Erro")));

		Exception exception = null;
		try
		{
			areaOrganizacionalManager.update(areaOrganizacional, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testFindQtdColaboradorPorArea()
	{
		areaOrganizacionalDao.expects(once()).method("findQtdColaboradorPorArea").will(returnValue(new ArrayList<AreaOrganizacional>()));
		assertEquals(0,areaOrganizacionalManager.findQtdColaboradorPorArea(1L, new Date()).size());
	}
	
	public void testSincronizar()
	{
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional1.setNome("área1");
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional2.setNome("área2");
		areaOrganizacional2.setAreaMae(areaOrganizacional1);
		AreaOrganizacional areaOrganizacional3 = AreaOrganizacionalFactory.getEntity(3L);
		areaOrganizacional3.setNome("área3");
		areaOrganizacional3.setAreaMae(areaOrganizacional2);
		
		areas.add(areaOrganizacional1);
		areas.add(areaOrganizacional2);
		areas.add(areaOrganizacional3);
		
		AreaOrganizacional areaNova10 = AreaOrganizacionalFactory.getEntity(10L);
		AreaOrganizacional areaNova11 = AreaOrganizacionalFactory.getEntity(11L);
		areaOrganizacional2.setAreaMae(areaNova10);
		areaOrganizacional3.setAreaMae(areaNova11);
		
		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		areaOrganizacionalDao.expects(once()).method("findSincronizarAreas").will(returnValue(areas));
		areaOrganizacionalDao.expects(atLeastOnce()).method("save");
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		Map<Long, Long> areaIds = new  HashMap<Long, Long>();
		areaIds.put(areaOrganizacional1.getId(), areaNova10.getId());
		
		Long empresaOrigemId=1L;
		Empresa empresaDestino=EmpresaFactory.getEmpresa();
		List<String> mensagens = new ArrayList<String>();
		
		areaOrganizacionalManager.sincronizar(empresaOrigemId, empresaDestino, areaIds, mensagens);
		
		assertEquals(3, areaIds.size());
	}
	
	public void testSetFamiliaAreasComExamesPrevistos() throws Exception
	{
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	areas.add(AreaOrganizacionalFactory.getEntity(1L));
    	AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(2L);
    	area2.setAreaMaeId(1L);
    	areas.add(area2);
    	
    	ExamesPrevistosRelatorio examesPrevistos1 = new ExamesPrevistosRelatorio();
    	examesPrevistos1.setAreaOrganizacional(area2);
    	Collection<ExamesPrevistosRelatorio> examesPrevistosRelatorios = new ArrayList<ExamesPrevistosRelatorio>();
    	examesPrevistosRelatorios.add(examesPrevistos1);
    	
    	Long empresaId=1L;
    	
		areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{eq(0),eq(0),eq(null),eq(null), ANYTHING, ANYTHING, eq(new Long[]{empresaId})}).will(returnValue(areas));
		
		assertEquals(1,areaOrganizacionalManager.setFamiliaAreas(examesPrevistosRelatorios, empresaId).size());
	} 
	
	public void testSelecionaFamilia()
	{
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(2L);
		areasIds.add(5L);
		areasIds.add(8L);
		
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		
		AreaOrganizacional administracao = AreaOrganizacionalFactory.getEntity(1L);
		
		AreaOrganizacional financeiro = AreaOrganizacionalFactory.getEntity(2L);
		financeiro.setAreaMae(administracao);
		AreaOrganizacional caixa = AreaOrganizacionalFactory.getEntity(3L);
		caixa.setAreaMae(financeiro);
		
		AreaOrganizacional desenvolvimento = AreaOrganizacionalFactory.getEntity(4L);
		desenvolvimento.setAreaMae(administracao);		
		AreaOrganizacional java = AreaOrganizacionalFactory.getEntity(5L);
		java.setAreaMae(desenvolvimento);
		AreaOrganizacional suporteJava = AreaOrganizacionalFactory.getEntity(6L);
		suporteJava.setAreaMae(java);
		
		AreaOrganizacional contabil = AreaOrganizacionalFactory.getEntity(7L);
		
		AreaOrganizacional comercial = AreaOrganizacionalFactory.getEntity(8L);
		AreaOrganizacional vendedor = AreaOrganizacionalFactory.getEntity(9L);
		vendedor.setAreaMae(comercial);

		areas.add(administracao);
		areas.add(financeiro);
		areas.add(caixa);
		areas.add(desenvolvimento);
		areas.add(java);
		areas.add(suporteJava);
		areas.add(contabil);
		areas.add(comercial);
		areas.add(vendedor);
		
		Long[] areasIdsJaConfiguradas = areaOrganizacionalManager.selecionaFamilia(areas, areasIds);
		assertEquals(8, areasIdsJaConfiguradas.length);
	}
	
	public void testFindIdsAreasFilhas() 
	{
		Usuario usuarioLogado = UsuarioFactory.getEntity(1L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setNome("areaAvo");
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(2L);
		areaMae1.setNome("areaMae1");
		areaMae1.setAreaMae(areaAvo);
		
		AreaOrganizacional areaMae2 = AreaOrganizacionalFactory.getEntity(3L);
		areaMae2.setNome("areaMae2");
		areaMae2.setAreaMae(areaAvo);
		
		AreaOrganizacional areaFilha1 = AreaOrganizacionalFactory.getEntity(4L);
		areaFilha1.setNome("areaFilha1");
		areaFilha1.setAreaMae(areaMae1);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity(5L);
		areaFilha2.setNome("areaFilha2");
		areaFilha2.setAreaMae(areaMae2);
		
		areaOrganizacionalDao.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").with(eq(usuarioLogado.getId()), eq(empresa.getId())).will(returnValue(new Long[]{areaAvo.getId()}));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaAvo.getId()))).will(returnValue(Arrays.asList(areaMae1.getId(), areaMae2.getId())));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaMae1.getId(), areaMae2.getId()))).will(returnValue(Arrays.asList(areaFilha1.getId(), areaFilha2.getId())));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaFilha1.getId(), areaFilha2.getId()))).will(returnValue(new ArrayList<Long>()));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaFilha1.getId(), areaFilha2.getId()))).will(returnValue(new ArrayList<Long>()));
		
		Long[] areaIds = areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(usuarioLogado, empresa.getId());
		CollectionUtil<Long> cUtil = new CollectionUtil<Long>();
		Collection<Long> areasIds = cUtil.convertArrayToCollection(areaIds);
		
		assertEquals(Arrays.asList(areaMae1.getId(), areaMae2.getId(), areaFilha1.getId(), areaFilha2.getId(), areaAvo.getId()), areasIds);
	}
	
	public void testFindAllSelectOrderDescricaoByUsuarioId() throws Exception 
	{
		Usuario usuarioLogado = UsuarioFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setUsuario(usuarioLogado);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setNome("areaAvo");
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(2L);
		areaMae1.setNome("areaMae");
		areaMae1.setAreaMae(areaAvo);
		
		AreaOrganizacional areaFilha1 = AreaOrganizacionalFactory.getEntity(3L);
		areaFilha1.setNome("areaFilha1");
		areaFilha1.setResponsavel(colaborador);
		areaFilha1.setAreaMae(areaMae1);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity(4L);
		areaFilha2.setNome("areaFilha2");
		areaFilha2.setAreaMae(areaMae1);
		
		AreaOrganizacional areaInativa = AreaOrganizacionalFactory.getEntity(5L);
		areaFilha1.setResponsavel(colaborador);
		areaInativa.setNome("areaInativa");
		areaInativa.setAtivo(false);
		
		areaOrganizacionalDao.expects(once()).method("findAreasDoResponsavelCoResponsavel").with(eq(usuarioLogado.getId()), eq(empresa.getId()), eq(true), eq(Arrays.asList(areaInativa.getId()))).will(returnValue(Arrays.asList(areaAvo, areaInativa)));
		
		Collection<AreaOrganizacional> areasIds = null;
		Exception exception = null;
		
		try {
			areasIds = areaOrganizacionalManager.findAllSelectOrderDescricaoByUsuarioId(empresa.getId(), usuarioLogado.getId(), true, areaInativa.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
		assertEquals(2, areasIds.size());
	}
	
	public void testFindAreasByUsuarioResponsavel() 
	{
		Usuario usuarioLogado = UsuarioFactory.getEntity(1L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setNome("areaAvo");
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(2L);
		areaMae1.setNome("areaMae1");
		areaMae1.setAreaMae(areaAvo);
		
		AreaOrganizacional areaMae2 = AreaOrganizacionalFactory.getEntity(3L);
		areaMae2.setNome("areaMae2");
		areaMae2.setAreaMae(areaAvo);
		
		AreaOrganizacional areaFilha1 = AreaOrganizacionalFactory.getEntity(4L);
		areaFilha1.setNome("areaFilha1");
		areaFilha1.setAreaMae(areaMae1);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity(5L);
		areaFilha2.setNome("areaFilha2");
		areaFilha2.setAreaMae(areaMae2);
		
		Collection<AreaOrganizacional> todasAsAreas = Arrays.asList(areaMae1, areaMae2, areaFilha1, areaFilha2, areaAvo);
		
		areaOrganizacionalDao.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").with(eq(usuarioLogado.getId()), eq(empresa.getId())).will(returnValue(new Long[]{areaAvo.getId()}));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaAvo.getId()))).will(returnValue(Arrays.asList(areaMae1.getId(), areaMae2.getId())));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaMae1.getId(), areaMae2.getId()))).will(returnValue(Arrays.asList(areaFilha1.getId(), areaFilha2.getId())));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaFilha1.getId(), areaFilha2.getId()))).will(returnValue(new ArrayList<Long>()));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(Arrays.asList(areaFilha1.getId(), areaFilha2.getId()))).will(returnValue(new ArrayList<Long>()));
		areaOrganizacionalDao.expects(once()).method("findAreas").with(eq(new Long[] {areaMae1.getId(), areaMae2.getId(), areaFilha1.getId(), areaFilha2.getId(), areaAvo.getId()})).will(returnValue(todasAsAreas));
		
		Collection<AreaOrganizacional> areasIds = null;
		Exception exception = null;
		
		try {
			areasIds = areaOrganizacionalManager.findAreasByUsuarioResponsavel(usuarioLogado, empresa.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
		assertEquals(5, areasIds.size());
	}
	
	public void testGetEmailsResponsaveis() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Contato contato = new Contato();
		contato.setEmail("responsavel@gmail.com");
		
		Colaborador responsavel = ColaboradorFactory.getEntity();
		responsavel.setContato(contato);
		
		Contato contato2 = new Contato();
		contato2.setEmail("responsavel2@gmail.com");
		
		Colaborador responsavel2 = ColaboradorFactory.getEntity();
		responsavel2.setContato(contato2);
		
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setEmpresa(empresa);
		areaAvo.setNome("areaAvo");
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(2L);
		areaMae1.setEmpresa(empresa);
		areaMae1.setNome("areaMae1");
		areaMae1.setEmailsNotificacoes("ru@gmail.com; sam@gmail.com");
		areaMae1.setResponsavel(responsavel);
		areaMae1.setAreaMae(areaAvo);
		
		AreaOrganizacional areaMae2 = AreaOrganizacionalFactory.getEntity(3L);
		areaMae2.setEmpresa(empresa);
		areaMae2.setNome("areaMae2");
		areaMae2.setResponsavel(responsavel);
		areaMae2.setAreaMae(areaAvo);
		
		AreaOrganizacional areaFilha1 = AreaOrganizacionalFactory.getEntity(4L);
		areaFilha1.setEmpresa(empresa);
		areaFilha1.setNome("areaFilha1");
		areaFilha1.setResponsavel(responsavel2);
		areaFilha1.setAreaMae(areaMae1);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity(5L);
		areaFilha2.setEmpresa(empresa);
		areaFilha2.setNome("areaFilha2");
		areaFilha2.setResponsavel(responsavel2);
		areaFilha2.setAreaMae(areaMae2);
		
		Collection<AreaOrganizacional> todasAsAreas = Arrays.asList(areaMae1, areaMae2, areaFilha1, areaFilha2, areaAvo);
		areaOrganizacionalDao.expects(once()).method("findAllList").with(new Constraint[]{eq(0),eq(0),eq(null),eq(null), ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()})}).will(returnValue(todasAsAreas));
		
		assertEquals(4, areaOrganizacionalManager.getEmailsResponsaveis(areaFilha1.getId(), empresa.getId(), AreaOrganizacional.RESPONSAVEL, null).length);
	}
	
	public void testGetEmailsResponsaveisPassandoCollection() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Contato contato = new Contato();
		contato.setEmail("responsavel@gmail.com");
		
		Colaborador responsavel = ColaboradorFactory.getEntity();
		responsavel.setContato(contato);
		
		Contato contato2 = new Contato();
		contato2.setEmail("responsavel2@gmail.com");
		
		Colaborador responsavel2 = ColaboradorFactory.getEntity();
		responsavel2.setContato(contato2);
		
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setEmpresa(empresa);
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(2L);
		areaMae1.setEmpresa(empresa);
		areaMae1.setEmailsNotificacoes("ru@gmail.com; sam@gmail.com");
		areaMae1.setResponsavel(responsavel);
		areaMae1.setCoResponsavel(responsavel2);
		areaMae1.setAreaMae(areaAvo);
		
		AreaOrganizacional areaMae2 = AreaOrganizacionalFactory.getEntity(3L);
		areaMae2.setEmpresa(empresa);
		areaMae2.setResponsavel(responsavel);
		areaMae2.setAreaMae(areaAvo);
		
		AreaOrganizacional areaFilha1 = AreaOrganizacionalFactory.getEntity(4L);
		areaFilha1.setEmpresa(empresa);
		areaFilha1.setResponsavel(responsavel2);
		areaFilha1.setAreaMae(areaMae1);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity(5L);
		areaFilha2.setEmpresa(empresa);
		areaFilha2.setResponsavel(responsavel2);
		areaFilha2.setAreaMae(areaMae2);
		
		Collection<AreaOrganizacional> todasAsAreas = Arrays.asList(areaMae1, areaMae2, areaFilha1, areaFilha2, areaAvo);
		
		assertEquals(4, areaOrganizacionalManager.getEmailsResponsaveis(areaFilha1.getId(), todasAsAreas, AreaOrganizacional.RESPONSAVEL).length);
		assertEquals(3, areaOrganizacionalManager.getEmailsResponsaveis(areaFilha1.getId(), todasAsAreas, AreaOrganizacional.CORRESPONSAVEL).length);
	}
	
	public void testFiltraPermitidasComRoleVerAreas()
	{
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		Long empresaId = 1L;
		
		assertNull(areaOrganizacionalManager.filtraPermitidas(null, empresaId));
	}
	
	public void testFiltraPermitidasSemRoleVerAreasComAreas()
	{
		MockSecurityUtil.roles = new String[]{};
		Long empresaId = 1L;
		String[] areasIds = {"1","2"};
		
		assertEquals(areasIds,  areaOrganizacionalManager.filtraPermitidas(areasIds, empresaId));
	}
	
	public void testFiltraPermitidasSemRoleVerComAreasDoResponsavel()
	{
		MockSecurityUtil.roles = new String[]{};
		Long usuarioId = 1L;
		Long empresaId = 1L;
		String[] areasIds = {};
		String[] areasPermitidas = {"1","2","3"};
		Long[] areasIdsdoResponsavel = {1L, 2L, 3L};
		Collection<Long> areasIdsCollection = Arrays.asList(new Long[] {1L,2L,3L});
		
		areaOrganizacionalDao.expects(once()).method("findIdsAreasFilhas").with(eq(areasIdsCollection)).will(returnValue(new ArrayList<Long>()));
		areaOrganizacionalDao.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").with(eq(usuarioId), eq(empresaId)).will(returnValue(areasIdsdoResponsavel));
		
		String[] areasRetonadas = areaOrganizacionalManager.filtraPermitidas(areasIds, empresaId);
		
		assertEquals(areasPermitidas.length, areasRetonadas.length );
		assertEquals(areasPermitidas[0], areasRetonadas[0]);
		assertEquals(areasPermitidas[1], areasRetonadas[1]);
		assertEquals(areasPermitidas[2], areasRetonadas[2]);
	}
	
	public void testFiltraPermitidasSemRoleVerSemAreasDoResponsavel()
	{
		MockSecurityUtil.roles = new String[]{};
		Long usuarioId = 1L;
		Long empresaId = 1L;
		String[] areasIds = {};
		String[] areasPermitidas = {"-1"};
		Long[] areasIdsdoResponsavel = {};
		
		areaOrganizacionalDao.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").with(eq(usuarioId), eq(empresaId)).will(returnValue(areasIdsdoResponsavel));
		
		String[] areasRetonadas = areaOrganizacionalManager.filtraPermitidas(areasIds, empresaId);
		assertEquals(areasPermitidas.length, areasRetonadas.length );
		assertEquals(areasPermitidas[0], areasRetonadas[0]);
	}
	
	public void testGetEmailResponsavel(){
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmailResponsavel("gestor@gmail.com");
		areaOrganizacionalDao.expects(once()).method("findByIdProjection").with(eq(areaOrganizacional.getId())).will(returnValue(areaOrganizacional));
		Exception exception = null;
		try {
			assertEquals("gestor@gmail.com", areaOrganizacionalManager.getEmailResponsavel(areaOrganizacional.getId()));
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
		
		
	}
	
}