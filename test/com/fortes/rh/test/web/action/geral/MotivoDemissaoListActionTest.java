package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.web.action.geral.MotivoDemissaoListAction;

public class MotivoDemissaoListActionTest extends MockObjectTestCase
{
	private MotivoDemissaoListAction action;
	private Mock manager;
	private Mock colaboradorManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new MotivoDemissaoListAction();
        manager = new Mock(MotivoDemissaoManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);

        action.setMotivoDemissaoManager((MotivoDemissaoManager) manager.proxy());
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testPrepareRelatorioMotivoDemissao() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);
    	
       	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	assertEquals("success", action.prepareRelatorioMotivoDemissao());
    }

    public void testRelatorioMotivoDemissaoListaTrue() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);

    	Cabecalho cabecalho = new Cabecalho("Relatório de Motivos de Desligamento", "", "", "", "", "", "", false);

    	Map<String, Object> parametros = new HashMap<String, Object>();
    	parametros.put("CABECALHO", cabecalho);

    	action.setListaColaboradores(true);
    	action.setAgruparPor("M");

    	MotivoDemissaoQuantidade motivoDemissaoQuantidade = new MotivoDemissaoQuantidade();

    	Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades = new ArrayList<MotivoDemissaoQuantidade>();
    	motivoDemissaoQuantidades.add(motivoDemissaoQuantidade);
    	
    	manager.expects(once()).method("getParametrosRelatorio").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(parametros));
    	colaboradorManager.expects(atLeastOnce()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(ColaboradorFactory.getCollection()));

    	assertEquals("success", action.relatorioMotivoDemissao());

    	action.setListaColaboradores(true);
    	action.setAgruparPor("N");
    	
    	manager.expects(once()).method("getParametrosRelatorio").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(parametros));
    	colaboradorManager.expects(atLeastOnce()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(motivoDemissaoQuantidades));
    	
    	assertEquals("successSemAgrupar", action.relatorioMotivoDemissao());
    	
    	action.setListaColaboradores(false);
    	action.setAgruparPor(null);
    	manager.expects(once()).method("getParametrosRelatorio").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(parametros));
    	colaboradorManager.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(motivoDemissaoQuantidades));
    	
    	assertEquals("successBasico", action.relatorioMotivoDemissao());
    	assertEquals("Relatório de Motivos de Desligamento", ((Cabecalho)action.getParametros().get("CABECALHO")).getTitulo());
    }

    public void testImprimeRelatorioMotivoDemissaoBasico() throws Exception
    {
    	MotivoDemissaoQuantidade motivoDemissaoQuantidade = new MotivoDemissaoQuantidade();
    	
    	Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades = new ArrayList<MotivoDemissaoQuantidade>();
    	motivoDemissaoQuantidades.add(motivoDemissaoQuantidade);
    	
    	colaboradorManager.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(motivoDemissaoQuantidades));

    	assertEquals("successBasico", action.imprimeRelatorioMotivoDemissaoBasico());
    }

    public void testImprimeRelatorioMotivoDemissaoBasicoException() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);

       	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	colaboradorManager.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(throwException(new Exception()));
    	
    	assertEquals("input", action.imprimeRelatorioMotivoDemissaoBasico());
    }

    public void testImprimeRelatorioMotivoDemissao() throws Exception
    {
    	MotivoDemissaoQuantidade motivoDemissaoQuantidade = new MotivoDemissaoQuantidade();
    	
    	Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades = new ArrayList<MotivoDemissaoQuantidade>();
    	motivoDemissaoQuantidades.add(motivoDemissaoQuantidade);
    	action.setAgruparPor("M");
    	colaboradorManager.expects(once()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(motivoDemissaoQuantidades));

    	assertEquals("success", action.imprimeRelatorioMotivoDemissao());
    }

    public void testImprimeRelatorioMotivoDemissaoException() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);

    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");

		colaboradorManager.expects(once()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(throwException(new Exception()));

    	assertEquals("input", action.imprimeRelatorioMotivoDemissao());
    }

    public void testGets() throws Exception
    {
    	action.setMotivoDemissao(new MotivoDemissao());
    	assertNotNull(action.getMotivoDemissao());

    	action.setMotivoDemissao(null);
    	assertNotNull(action.getMotivoDemissao());

    	action.getMotivoDemissaos();

    	action.setAreasCheck(new String[]{});
    	assertNotNull(action.getAreasCheck());

    	action.setEstabelecimentosCheck(new String[]{});
    	assertNotNull(action.getEstabelecimentosCheck());

    	action.setCargosCheck(new String[]{});
    	assertNotNull(action.getCargosCheck());

    	action.setDataFim(new Date());
    	assertNotNull(action.getDataFim());

    	action.setDataIni(new Date());
    	assertNotNull(action.getDataIni());

    	action.setListaColaboradores(true);
    	assertTrue(action.isListaColaboradores());

    	action.setAreasCheckList(null);
    	assertNull(action.getAreasCheckList());

    	action.setCargosCheckList(null);
    	assertNull(action.getCargosCheckList());

    	action.setEstabelecimentosCheckList(null);
    	assertNull(action.getEstabelecimentosCheckList());

    	assertNull(action.getColaboradores());
    }
}
