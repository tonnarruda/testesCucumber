package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.CatEditAction;

public class CatEditActionTest extends MockObjectTestCase
{
	private CatEditAction action;
	private Mock manager;
	private Mock colaboradorManager;
	private Mock estabelecimentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(CatManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);

        action = new CatEditAction();
        action.setCatManager((CatManager) manager.proxy());
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager)estabelecimentoManager.proxy());

        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setInicio(new Date());
        action.setFim(new Date());
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        colaboradorManager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	Cat cat = new Cat();
    	cat.setId(1L);

    	action.setCat(cat);

    	Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		action.setColaborador(colaborador);

    	manager.expects(once()).method("findById").with(eq(cat.getId())).will(returnValue(cat));

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getCat(), cat);
    	assertEquals(action.getColaborador(), colaborador);
    }

    public void testPrepareUpdate() throws Exception
    {
    	Cat cat = new Cat();
    	cat.setId(1L);

    	action.setCat(cat);

    	Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		action.setColaborador(colaborador);

    	manager.expects(once()).method("findById").with(eq(cat.getId())).will(returnValue(cat));

    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);

    	Cat cat = new Cat();
    	cat.setId(1L);
    	cat.setColaborador(colaborador);

    	action.setColaborador(colaborador);
    	action.setCat(cat);

    	manager.expects(once()).method("save").with(eq(cat));

    	assertEquals(action.insert(), "success");
    	assertEquals(action.getCat(), cat);
    }

    public void testUpdate() throws Exception
    {
    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);

    	Cat cat = new Cat();
    	cat.setId(1L);
    	cat.setColaborador(colaborador);

    	action.setColaborador(colaborador);
    	action.setCat(cat);

    	manager.expects(once()).method("update").with(eq(cat));

    	assertEquals(action.update(), "success");
    	assertEquals(action.getCat(), cat);
    }

    public void testDelete() throws Exception
    {
    	Cat cat = new Cat();
    	cat.setId(1L);
    	action.setCat(cat);

    	manager.expects(once()).method("remove").with(ANYTHING);

    	assertEquals(action.delete(), "success");
    }

    public void testListPeriodoInvalido() throws Exception
    {
    	Date hoje = new Date();
    	action.setFim(hoje);
    	action.setInicio(DateUtil.incrementaMes(hoje, 1));
    	
    	action.list();
    	assertEquals("Data final anterior à data inicial do período.", action.getActionErrors().toArray()[0]);
    }
    
    public void testList() throws Exception
    {
		Collection<Cat> cats = new ArrayList<Cat>();

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		Cat cat = new Cat();
		cat.setId(1L);
		cat.setColaborador(colaborador);

		Cat cat2 = new Cat();
		cat2.setId(2L);
		cat2.setColaborador(colaborador);

		cats.add(cat);
		cats.add(cat2);

		action.setColaborador(colaborador);
		action.setCats(cats);

		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Cat>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));

    	assertEquals(action.list(), "success");

    	manager.verify();
    }
    
    public void testFiltrarColaboradores() throws Exception
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf("493.034.123-87");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setPessoal(pessoal);
		action.setColaborador(colaborador);

		Collection<Colaborador> colecao = new ArrayList<Colaborador>();
		colecao.add(colaborador);

		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, eq(action.getEmpresaSistema().getId()), ANYTHING).will(returnValue(colecao));

		assertEquals("success", action.filtrarColaboradores());

	}
    
    public void testFiltrarColaboradoresSemColaborador() throws Exception
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf("493.034.123-87");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setPessoal(pessoal);
		action.setColaborador(colaborador);

		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, eq(action.getEmpresaSistema().getId()), ANYTHING).will(returnValue(new ArrayList<Colaborador>()));

		assertEquals("success", action.filtrarColaboradores());
		assertEquals("Nenhum colaborador para o filtro informado.", action.getActionMessages().toArray()[0]);
	}

    public void testGetSet() throws Exception
    {
    	action.setCat(null);
    	assertTrue(action.getCat() instanceof Cat);
    	action.getCats();
    	action.getInicio();
    	action.getFim();
    	action.setEstabelecimentosCheck(null);
    	action.getEstabelecimentosCheck();
    	action.getEstabelecimentosCheckList();
    	action.setEstabelecimentosCheckList(null);
    	action.setNomeBusca("");
    	action.getNomeBusca();
    	action.getColaboradors();
    }

    public void testPrepareRelatorioCats() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		manager.expects(once()).method("findAll");
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));

		assertEquals(action.prepareRelatorioCats(), "success");
	}
    
  //  Refatora 
//    public void testRelatorioCats() throws Exception
//    {
//    	action.setInicio(new Date());
//    	action.setFim(new Date());
//    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
//    	action.setParametros( RelatorioUtil.getParametrosRelatorio("CAT's", action.getEmpresaSistema(), "--"));
//    	
//    	manager.expects(once()).method("findRelatorioCats").with(new Constraint[]{eq(action.getEmpresaSistema().getId()),eq(action.getInicio()),eq(action.getFim()), ANYTHING, ANYTHING}).will(returnValue(new ArrayList<Cat>()));
//    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
//		manager.expects(once()).method("findAll");
//		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
//		
//    	assertEquals(action.relatorioCats(), "success");
//    }
    
}