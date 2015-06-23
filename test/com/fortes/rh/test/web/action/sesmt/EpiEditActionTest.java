package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.sesmt.EpiEditAction;

public class EpiEditActionTest extends MockObjectTestCase
{
	private EpiEditAction action;
	private Mock epiManager;
	private Mock tipoEpiManager;
	private Mock epiHistoricoManager;
	private Mock colaboradorManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        epiManager = new Mock(EpiManager.class);
        tipoEpiManager = new Mock(TipoEPIManager.class);
        epiHistoricoManager = new Mock(EpiHistoricoManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);

        action = new EpiEditAction();
        action.setEpiManager((EpiManager) epiManager.proxy());
        action.setTipoEPIManager((TipoEPIManager) tipoEpiManager.proxy());
        action.setEpiHistoricoManager((EpiHistoricoManager) epiHistoricoManager.proxy());
        action.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
        epiManager = null;
        action = null;
        tipoEpiManager = null;
        super.tearDown();
    }

    public void testPrepareUpdate() throws Exception
    {
    	Epi epi = new Epi();
    	epi.setId(1L);
    	epi.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setEpi(epi);

    	TipoEPI tipoEPI = new TipoEPI();
    	tipoEPI.setId(1L);

    	Collection<TipoEPI> collection = new ArrayList<TipoEPI>();
    	collection.add(tipoEPI);
    	action.setTipos(collection);

    	Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();

    	epiHistoricoManager.expects(once()).method("find").with(ANYTHING,ANYTHING).will(returnValue(epiHistoricos));
    	tipoEpiManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING}).will(returnValue(collection));
    	epiManager.expects(once()).method("findById").with(eq(epi.getId())).will(returnValue(epi));
    	epiManager.expects(once()).method("findFabricantesDistinctByEmpresa").with(eq(epi.getEmpresa().getId())).will(returnValue(""));
    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getTipos(),collection);
     }

    public void testPrepareUpdateEmpresaErrada() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(789L);

    	Epi epi = new Epi();
    	epi.setId(1L);
    	epi.setEmpresa(empresa);
    	action.setEpi(epi);

    	TipoEPI tipoEPI = new TipoEPI();
    	tipoEPI.setId(1L);

    	Collection<TipoEPI> collection = new ArrayList<TipoEPI>();
    	collection.add(tipoEPI);
    	action.setTipos(collection);

    	Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();

    	epiHistoricoManager.expects(once()).method("find").with(ANYTHING,ANYTHING).will(returnValue(epiHistoricos));
    	tipoEpiManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING}).will(returnValue(collection));
    	epiManager.expects(once()).method("findById").with(eq(epi.getId())).will(returnValue(epi));
    	epiManager.expects(once()).method("findFabricantesDistinctByEmpresa").with(ANYTHING).will(returnValue(""));
    	assertEquals(action.prepareUpdate(), "error");
    	assertEquals(action.getTipos(),collection);
    	assertTrue(action.getMsgAlert()!= null && !action.getMsgAlert().equals(""));
     }

    public void testUpdate() throws Exception
    {
    	Epi epi = new Epi();
    	epi.setId(1L);
    	epi.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setEpi(epi);

    	epiManager.expects(once()).method("findByIdProjection").with(eq(epi.getId())).will(returnValue(epi));
    	epiManager.expects(once()).method("update").with(eq(epi));

    	assertEquals(action.update(), "success");
    }

    public void testUpdateEmpresaErrada() throws Exception
    {
    	Empresa empresaErrada = new Empresa();
    	empresaErrada.setId(9999L);
    	Epi epi = new Epi();
    	epi.setId(1L);
    	epi.setEmpresa(empresaErrada);

    	action.setEpi(epi);

    	epiManager.expects(once()).method("findByIdProjection").with(eq(epi.getId())).will(returnValue(epi));
    	epiManager.expects(never()).method("update").with(ANYTHING);

    	assertEquals(action.update(), "error");
    	assertTrue(action.getMsgAlert()!= null && !action.getMsgAlert().equals(""));
    }

    public void testPrepareInsert() throws Exception
    {
       	TipoEPI tipoEPI = new TipoEPI();
       	tipoEPI.setId(1L);

    	Epi epi = new Epi();
    	epi.setId(1L);
    	epi.setTipoEPI(tipoEPI);
    	action.setEpi(epi);

    	Collection<TipoEPI> collection = new ArrayList<TipoEPI>();
    	collection.add(tipoEPI);
    	action.setTipos(collection);

    	epiManager.expects(once()).method("findById").with(eq(epi.getId())).will(returnValue(epi));
    	tipoEpiManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING}).will(returnValue(collection));
    	epiManager.expects(once()).method("findFabricantesDistinctByEmpresa").with(ANYTHING).will(returnValue(""));
    	assertEquals(action.getTipos(),collection);
    	assertEquals(action.prepareInsert(), "success");
    }

    public void testInsert() throws Exception
    {
    	Epi epi = new Epi();
    	epi.setId(1L);
    	action.setEpi(epi);

    	EpiHistorico epiHistorico = new EpiHistorico();
    	epiHistorico.setId(1L);
    	action.setEpiHistorico(epiHistorico);

    	epiManager.expects(once()).method("saveEpi").with(ANYTHING, ANYTHING);

    	assertEquals(action.insert(), "success");
    	assertEquals(action.getEpi(), epi);
    }

    public void testInsertException() throws Exception
    {
    	TipoEPI tipoEPI = new TipoEPI();
       	tipoEPI.setId(1L);

    	Epi epi = new Epi();
    	epi.setId(1L);
    	action.setEpi(epi);

    	EpiHistorico epiHistorico = new EpiHistorico();
    	epiHistorico.setId(1L);
    	action.setEpiHistorico(epiHistorico);

    	Collection<TipoEPI> collection = new ArrayList<TipoEPI>();
    	collection.add(tipoEPI);
    	action.setTipos(collection);

    	epiManager.expects(once()).method("saveEpi").with(ANYTHING, ANYTHING).will(throwException(new Exception("Erro")));

    	epiManager.expects(once()).method("findById").with(eq(epi.getId())).will(returnValue(epi));
    	epiManager.expects(once()).method("findFabricantesDistinctByEmpresa").with(ANYTHING).will(returnValue(""));
    	tipoEpiManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING}).will(returnValue(collection));

    	assertEquals(action.insert(), "input");
    }


    public void testPrepareImprimirFicha()
    {
    	assertEquals("success",action.prepareImprimirFicha());
    }

    public void testFiltroImprimirFicha()
    {
    	Long empresaId = 1L;
		String cpf = "123.456.789-10";

		StringUtil.removeMascara(cpf);
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(new Constraint[]{ANYTHING, eq(empresaId), ANYTHING, eq(null), eq(null)}).will(returnValue(new ArrayList<Colaborador>()));

		assertEquals("success", action.filtroImprimirFicha());
    }
    
    public void testImprimirFicha()
    {
    	action.setImprimirVerso(true);
    	epiManager.expects(once()).method("findImprimirFicha").with(ANYTHING, ANYTHING).will(returnValue(new FichaEpiRelatorio()));
    	assertEquals("success",action.imprimirFicha());
    	assertNotNull(action.getDataSourceFichaEpi());
    }

    public void testGetSet() throws Exception
    {
    	action.setEpi(null);

    	assertTrue(action.getEpi() instanceof Epi);
    	action.getEpiHistoricos();
    	action.setEpiHistoricos(null);
    	action.getEpiHistorico();
    	action.getDataHoje();
    	action.getColaborador();
    	action.getColaboradors();
    	action.setColaborador(null);
    	action.getParametros();
    	action.getFichaEpiRelatorio();
    	action.isImprimirVerso();
    }
}
