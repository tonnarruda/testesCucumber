package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManagerImpl;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

public class EpiManagerTest extends MockObjectTestCase
{
	private EpiManagerImpl epiManager = new EpiManagerImpl();
	private Mock epiDao = null;
	private Mock transactionManager;
	private Mock epiHistoricoManager;
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        epiDao = new Mock(EpiDao.class);
        epiManager.setDao((EpiDao) epiDao.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
		epiManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

		epiHistoricoManager = new Mock(EpiHistoricoManager.class);
		epiManager.setEpiHistoricoManager((EpiHistoricoManager) epiHistoricoManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		epiManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		epiManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
    }

	public void testFindByIdProjection() throws Exception
	{
		Epi epi = new Epi();
		epi.setId(1L);

		epiDao.expects(once()).method("findByIdProjection").with(eq(epi.getId())).will(returnValue(epi));

		assertEquals(epi, epiManager.findByIdProjection(epi.getId()));
	}
	
	public void testPopulaCheckToEpi() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Long empresaId = empresa.getId();
		
		Collection<Epi> epis = new ArrayList<Epi>();
		Collection<CheckBox> ids = new ArrayList<CheckBox>();
		
		epiDao.expects(once()).method("find").with(eq(new String[]{"empresa.id"}),eq(new Object[]{empresaId}),eq(new String[]{"nome"}) ).will(returnValue(epis));
		
		assertEquals(ids, epiManager.populaCheckToEpi(empresaId));
	}

	public void testPopulaCheckToEpiException() throws Exception
	{
		epiDao.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING ).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals(new ArrayList<Epi>(), epiManager.populaCheckToEpi(1L));
	}

	public void testSaveEpi() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Epi epi = new Epi();
		epi.setId(1L);
		epi.setEmpresa(empresa);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		epiHistorico.setEpi(epi);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		epiDao.expects(once()).method("save").with(ANYTHING).will(returnValue(epi));
		epiHistoricoManager.expects(once()).method("save").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		epiManager.saveEpi(epi, epiHistorico);

	}

	public void testSaveEpiException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Epi epi = new Epi();
		epi.setId(1L);
		epi.setEmpresa(empresa);

		EpiHistorico epiHistorico = null;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		epiDao.expects(once()).method("save").with(ANYTHING).will(returnValue(epi));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exception = null;

		try
		{
			epiManager.saveEpi(epi, epiHistorico);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testFindImprimirFicha()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FichaEpiRelatorio fichaEpiRelatorio = new FichaEpiRelatorio(colaborador, empresa);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		fichaEpiRelatorio.getColaborador().setAreaOrganizacional(areaOrganizacional);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(areaOrganizacional);

		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaborador.getId())).will(returnValue(colaborador));
		areaOrganizacionalManager.expects(once()).method("findAllList").with(eq(empresa.getId()), eq(AreaOrganizacional.TODAS)).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));

		FichaEpiRelatorio fichaEpiRelatorioTmp = epiManager.findImprimirFicha(empresa, colaborador);
		assertNotNull(fichaEpiRelatorioTmp);
		assertEquals(fichaEpiRelatorio.getColaborador().getNome(), fichaEpiRelatorioTmp.getColaborador().getNome());
	}
	
	public void testFindEpisDoAmbiente()
	{
		Long ambienteId = 2L;
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(new Epi());
		epiDao.expects(once()).method("findEpisDoAmbiente").will(returnValue(epis));
		assertEquals(1, epiManager.findEpisDoAmbiente(ambienteId, new Date()).size());
	}
}