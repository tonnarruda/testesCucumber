package com.fortes.rh.test.web.action.sesmt;

import java.util.Collection;
import java.util.HashSet;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.sesmt.AnexoManager;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.test.util.mockObjects.MockFileUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.web.action.sesmt.AnexoEditAction;
import com.opensymphony.webwork.ServletActionContext;

public class AnexoEditActionTest extends MockObjectTestCase
{

	private AnexoEditAction action;
	private Mock anexoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        anexoManager = new Mock(AnexoManager.class);

        action = new AnexoEditAction();
        action.setAnexoManager((AnexoManager)anexoManager.proxy());

        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
        Mockit.redefineMethods(FileUtil.class, MockFileUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
        anexoManager = null;
        action = null;
        super.tearDown();
    }


    public void testPrepareInsert() throws Exception
    {
    	Collection<Anexo> anexos = new HashSet<Anexo>();

    	Anexo anexo = new Anexo();
    	anexo.setId(1L);
    	anexo.setOrigemId(2L);
    	anexo.setOrigem(OrigemAnexo.LTCAT);

    	action.setAnexo(anexo);

    	anexos.add(anexo);

    	anexoManager.expects(once()).method("findById").with(eq(anexo.getId())).will(returnValue(anexo));
    	anexoManager.expects(once()).method("findByOrigem").with(eq(anexo.getOrigemId()), eq(anexo.getOrigem())).will(returnValue(anexos));

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getAnexos(), anexos);
    }


    public void testPrepareUpdate() throws Exception
    {
    	Anexo anexo = new Anexo();
    	anexo.setId(1L);
    	anexo.setOrigem(OrigemAnexo.LTCAT);

    	anexoManager.expects(once()).method("findById").with(eq(anexo.getId())).will(returnValue(anexo));

    	action.setAnexo(anexo);
    	assertEquals(action.prepareUpdate(), "success");


    	assertEquals("LTCAT", action.getAnexoDestino());
    }

    public void testInsert() throws Exception
    {
    	Anexo anexo = new Anexo();
    	anexo.setOrigem(OrigemAnexo.LTCAT);
    	action.setAnexo(anexo);

    	assertEquals("input",action.insert());

    	File arquivo = new File();
    	arquivo.setName("xxx.xxx");

		action.setArquivo(arquivo);

		anexoManager.expects(once()).method("gravaAnexo").with( eq(arquivo), eq(anexo)).will(returnValue(anexo));
		anexoManager.expects(once()).method("save").with(eq(anexo));
		anexoManager.expects(once()).method("getStringRetorno").with(eq(anexo.getOrigem())).will(returnValue("successLTCAT"));

    	assertEquals("successLTCAT", action.insert());
    }


    public void testUpdate() throws Exception
    {
    	Anexo anexo = new Anexo();
    	anexo.setId(1L);
    	anexo.setUrl("teste");
    	action.setAnexo(anexo);

    	anexoManager.expects(once()).method("populaAnexo").with(eq(anexo)).will(returnValue(anexo));
    	anexoManager.expects(once()).method("update").with(eq(anexo));
    	anexoManager.expects(once()).method("getStringRetorno").with(eq(anexo.getOrigem())).will(returnValue("successLTCAT"));

    	assertEquals("successLTCAT", action.update());
    }

    public void testVoltarList() throws Exception
    {
    	Anexo anexo = new Anexo();
    	anexo.setOrigem(OrigemAnexo.LTCAT);
    	action.setAnexo(anexo);
    	anexoManager.expects(once()).method("getStringRetorno").with(eq(anexo.getOrigem())).will(returnValue("successLTCAT"));

    	assertEquals("successLTCAT", action.voltarList());
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testGetSet() throws Exception
    {

    	Collection<Anexo> anexos = new HashSet<Anexo>();

    	Anexo anexo = new Anexo();
    	anexo.setId(1L);

    	Anexo anexo2 = new Anexo();
    	anexo2.setId(2L);

    	anexos.add(anexo);
    	anexos.add(anexo2);

    	action.getModel();
    	action.getArquivo();
    	action.getAnexos();

    	action.setAnexos(anexos);
    	action.setAnexo(null);
        assertTrue(action.getAnexo() instanceof Anexo);
    }
}
