package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.geral.DocumentoAnexoListAction;

public class DocumentoAnexoListActionTest extends MockObjectTestCase
{
	private DocumentoAnexoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new DocumentoAnexoListAction();
        manager = new Mock(DocumentoAnexoManager.class);
        action.setDocumentoAnexoManager((DocumentoAnexoManager) manager.proxy());

        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", action.execute());
    }

    public void testList() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setOrigem('C');
    	documentoAnexo.setOrigemId(1L);
    	action.setDocumentoAnexo(documentoAnexo);

    	manager.expects(once()).method("getDocumentoAnexoByOrigemId").with(eq('C'), eq(1L)).will(returnValue(new ArrayList<DocumentoAnexo>()));
    	manager.expects(once()).method("getNome").with(eq('C'), eq(1L)).will(returnValue("bruno"));

    	assertEquals("success", action.list());
    }

    public void testDeleteCandidato() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setId(1L);
    	documentoAnexo.setUrl("url");
    	documentoAnexo.setOrigem('Y');
    	documentoAnexo.setOrigemId(1L);

    	action.setDocumentoAnexo(documentoAnexo);

    	manager.expects(once()).method("findByIdProjection").with(eq(documentoAnexo.getId())).will(returnValue(documentoAnexo));
    	manager.expects(once()).method("deletarDocumentoAnexo").with(eq("documentosCandidatos"), eq(documentoAnexo));
    	manager.expects(once()).method("getDocumentoAnexoByOrigemId").with(eq('Y'), eq(1L)).will(returnValue(new ArrayList<DocumentoAnexo>()));
    	manager.expects(once()).method("getNome").with(eq('Y'), eq(1L)).will(returnValue("bruno"));
    	
    	assertEquals("success", action.deleteCandidato());
    }

    public void testDeleteCandidatoExcecao() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setId(1L);
    	documentoAnexo.setUrl("url");
    	documentoAnexo.setOrigem('Y');
    	documentoAnexo.setOrigemId(1L);

    	action.setDocumentoAnexo(documentoAnexo);

    	manager.expects(once()).method("findByIdProjection").with(eq(documentoAnexo.getId())).will(returnValue(documentoAnexo));
    	manager.expects(once()).method("deletarDocumentoAnexo").with(eq("documentosCandidatos"),eq(documentoAnexo)).will(throwException(new Exception()));
    	manager.expects(once()).method("getDocumentoAnexoByOrigemId").with(eq('Y'), eq(1L)).will(returnValue(new ArrayList<DocumentoAnexo>()));
    	manager.expects(once()).method("getNome").with(eq('Y'), eq(1L)).will(returnValue("bruno"));

    	assertEquals("success", action.deleteCandidato());
    }

    public void testDeleteColaborador() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setId(1L);
    	documentoAnexo.setUrl("url");
    	documentoAnexo.setOrigem('Y');
    	documentoAnexo.setOrigemId(1L);

    	action.setDocumentoAnexo(documentoAnexo);

    	manager.expects(once()).method("findByIdProjection").with(eq(documentoAnexo.getId())).will(returnValue(documentoAnexo));
    	manager.expects(once()).method("deletarDocumentoAnexo").with(eq("documentosColaboradores"),eq(documentoAnexo));
    	manager.expects(once()).method("getDocumentoAnexoByOrigemId").with(eq('Y'), eq(1L)).will(returnValue(new ArrayList<DocumentoAnexo>()));
    	manager.expects(once()).method("getNome").with(eq('Y'), eq(1L)).will(returnValue("bruno"));

    	assertEquals("success", action.deleteColaborador());
    }

    public void testGetsSets()
    {
    	action.getDocumentoAnexos();
    	action.getDocumentoAnexo();
    	action.setDocumentoAnexos(new ArrayList<DocumentoAnexo>());
    	action.setNome("nome");
    	action.getNome();
    	action.setSolicitacaoId(1L);
		action.getSolicitacaoId();
    }

}
