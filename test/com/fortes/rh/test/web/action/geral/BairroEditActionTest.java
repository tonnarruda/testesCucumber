package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.BairroFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.BairroEditAction;

public class BairroEditActionTest extends MockObjectTestCase
{
	private BairroEditAction action;
	private Mock manager;
	private Mock estadoManager;
	private Mock cidadeManager;

	protected void setUp()
	{
		action = new BairroEditAction();
		manager = new Mock(BairroManager.class);
		estadoManager = new Mock(EstadoManager.class);
		cidadeManager = new Mock(CidadeManager.class);

		action.setBairroManager((BairroManager) manager.proxy());
		action.setEstadoManager((EstadoManager) estadoManager.proxy());
		action.setCidadeManager((CidadeManager) cidadeManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testPrepareInsert() throws Exception
    {
    	Collection<Estado> estados = new ArrayList<Estado>();
    	estadoManager.expects(once()).method("findAll").with(eq(new String[]{"sigla"})).will(returnValue(estados));
    	cidadeManager.expects(once()).method("findByEstado").with(ANYTHING).will(returnValue(new ArrayList<Cidade>()));
    	
    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Collection<Estado> estados = new ArrayList<Estado>();
    	estadoManager.expects(once()).method("findAll").with(eq(new String[]{"sigla"})).will(returnValue(estados));

    	Estado estado = new Estado();
    	estado.setId(1L);

    	Cidade cidade = new Cidade();
    	cidade.setUf(estado);

    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	bairro.setCidade(cidade);
    	action.setBairro(bairro);

    	cidadeManager.expects(once()).method("findByEstado").with(ANYTHING).will(returnValue(new ArrayList<Cidade>()));


    	manager.expects(once()).method("findById").with(eq(bairro.getId())).will(returnValue(bairro));
    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	Cidade cidade = new Cidade();
    	cidade.setId(1L);

    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	bairro.setCidade(cidade);
    	bairro.setNome("bairro");
    	action.setBairro(bairro);

    	manager.expects(once()).method("existeBairro").with(eq(bairro)).will(returnValue(false));
    	manager.expects(once()).method("save").with(eq(bairro));

    	assertEquals("success", action.insert());
    }

    public void testInsertExisteBairro() throws Exception
    {
    	Cidade cidade = new Cidade();
    	cidade.setId(1L);

    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	bairro.setCidade(cidade);
    	bairro.setNome("bairro");
    	action.setBairro(bairro);

    	manager.expects(once()).method("existeBairro").with(eq(bairro)).will(returnValue(true));
    	manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(bairro));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new LinkedList<Estado>()));
    	cidadeManager.expects(once()).method("findByEstado").with(ANYTHING).will(returnValue(new ArrayList<Cidade>()));
    	
    	assertEquals("input", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	Cidade cidade = new Cidade();
    	cidade.setId(1L);

    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	bairro.setCidade(cidade);
    	bairro.setNome("bairro");
    	action.setBairro(bairro);

    	manager.expects(once()).method("existeBairro").with(eq(bairro)).will(returnValue(false));
    	manager.expects(once()).method("update").with(eq(bairro));

    	assertEquals("success", action.update());
    }

    public void testUpdateExisteBairro() throws Exception
    {
    	Cidade cidade = new Cidade();
    	cidade.setId(1L);

    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	bairro.setCidade(cidade);
    	bairro.setNome("bairro");
    	action.setBairro(bairro);

    	manager.expects(once()).method("existeBairro").with(eq(bairro)).will(returnValue(true));
    	manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(bairro));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new LinkedList<Estado>()));
    	cidadeManager.expects(once()).method("findByEstado").with(ANYTHING).will(returnValue(new ArrayList<Cidade>()));
    	
    	assertEquals("input", action.update());
    }

    public void testPrepareMigrar() throws Exception
    {
    	Estado estado = EstadoFactory.getEntity(1L);
    	action.setEstado(estado);
    	
    	Cidade cidade = CidadeFactory.getEntity(1L);
    	action.setCidade(cidade);
    	
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new LinkedList<Estado>()));
    	cidadeManager.expects(once()).method("findByEstado").with(eq(estado)).will(returnValue(new LinkedList<Cidade>()));
    	manager.expects(once()).method("findByCidade").with(eq(cidade)).will(returnValue(new LinkedList<Bairro>()));
    	
    	assertEquals("success", action.prepareMigrar());
    	
    	assertNotNull(action.getEstados());
    	assertNotNull(action.getCidades());
    	assertNotNull(action.getBairros());
    }
    
    public void testMigrar() throws Exception
    {
    	Bairro bairro = BairroFactory.getEntity(1L);
    	Bairro bairroDestino = BairroFactory.getEntity(2L);
    	
    	action.setBairro(bairro);
    	action.setBairroDestino(bairroDestino);
    	
    	Estado estado = EstadoFactory.getEntity(1L);
    	action.setEstado(estado);
    	
    	Cidade cidade = CidadeFactory.getEntity(1L);
    	action.setCidade(cidade);

    	manager.expects(once()).method("migrarRegistros").with(eq(bairro), eq(bairroDestino)).isVoid();
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new LinkedList<Estado>()));
    	cidadeManager.expects(once()).method("findByEstado").with(eq(estado)).will(returnValue(new LinkedList<Cidade>()));
    	manager.expects(once()).method("findByCidade").with(eq(cidade)).will(returnValue(new LinkedList<Bairro>()));
    	
    	assertEquals("success", action.migrar());
    	assertNull(action.getBairro().getId());
    	assertNull(action.getBairroDestino());
    }
    
    public void testMigrarException() throws Exception
    {
    	Bairro bairro = BairroFactory.getEntity(1L);
    	Bairro bairroDestino = BairroFactory.getEntity(2L);
    	
    	action.setBairro(bairro);
    	action.setBairroDestino(bairroDestino);
    	
    	Estado estado = EstadoFactory.getEntity(1L);
    	action.setEstado(estado);
    	
    	Cidade cidade = CidadeFactory.getEntity(1L);
    	action.setCidade(cidade);
    	
    	manager.expects(once()).method("migrarRegistros").with(eq(bairro), eq(bairroDestino)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new LinkedList<Estado>()));
    	cidadeManager.expects(once()).method("findByEstado").with(eq(estado)).will(returnValue(new LinkedList<Cidade>()));
    	manager.expects(once()).method("findByCidade").with(eq(cidade)).will(returnValue(new LinkedList<Bairro>()));
    	
    	assertEquals("success", action.migrar());
    	assertNull(action.getBairro().getId());
    	assertNull(action.getBairroDestino());
    }
    
    public void testMigrarMesmoBairro() throws Exception
    {
    	Bairro bairro = BairroFactory.getEntity(1L);
    	Bairro bairroDestino = BairroFactory.getEntity(1L);
    	
    	action.setBairro(bairro);
    	action.setBairroDestino(bairroDestino);
    	
    	Estado estado = EstadoFactory.getEntity(1L);
    	action.setEstado(estado);
    	
    	Cidade cidade = CidadeFactory.getEntity(1L);
    	action.setCidade(cidade);
    	
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new LinkedList<Estado>()));
    	cidadeManager.expects(once()).method("findByEstado").with(eq(estado)).will(returnValue(new LinkedList<Cidade>()));
    	manager.expects(once()).method("findByCidade").with(eq(cidade)).will(returnValue(new LinkedList<Bairro>()));
    	
    	assertEquals("success", action.migrar());
    	assertNull(action.getBairro().getId());
    	assertNull(action.getBairroDestino());
    }
    
    public void testGetsSets() throws Exception
    {
    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	action.setBairro(bairro);
    	
    	action.getBairro();
    	action.setBairro(null);
    	action.getBairro();
    	
    	Estado estado = new Estado();
    	action.setEstado(estado);
    	
    	assertEquals(estado, action.getEstado());
    	
    	action.getEstados();
    	action.getModel();
    	action.getCidades();
    }
}