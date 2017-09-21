package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.DocumentoAnexoListAction;
import com.opensymphony.xwork.ActionContext;

public class DocumentoAnexoListActionTest
{
	private DocumentoAnexoListAction action;
	private DocumentoAnexoManager manager;

	@Before
    public void setUp() throws Exception
    {
        action = new DocumentoAnexoListAction();
        manager = mock(DocumentoAnexoManager.class);
        action.setDocumentoAnexoManager(manager);

    	Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
    }

	@After
    public void tearDown() throws Exception
    {
        manager = null;
        action = null;
    }
	
	private void validaRoleSecurity() {
		Map<String, String> roles = new HashMap<>();
		roles.put("ACEGI_SECURITY_CONTEXT", "ROLE_COLAB_LIST_DOCUMENTOANEXO");
		MockActionContext.getContext().setSession(roles);
		MockSecurityUtil.verifyRole = true;
	}

    @Test
    public void testList() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setOrigem('C');
    	documentoAnexo.setOrigemId(1L);
    	action.setDocumentoAnexo(documentoAnexo);
    	
    	action.setColaboradorId(1L);

    	validaRoleSecurity();
    	when(manager.getDocumentoAnexoByOrigemId('C', 1L)).thenReturn(new ArrayList<DocumentoAnexo>());

    	assertEquals("success", action.list());
    }

    @Test
    public void testDelete() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setId(1L);
    	documentoAnexo.setUrl("url");
    	documentoAnexo.setOrigem('D');
    	documentoAnexo.setOrigemId(1L);
    	action.setDocumentoAnexo(documentoAnexo);
    	
    	validaRoleSecurity();
    	when(manager.findByIdProjection(documentoAnexo.getId())).thenReturn(documentoAnexo);
    	when(manager.getDocumentoAnexoByOrigemId('Y', 1L)).thenReturn(new ArrayList<DocumentoAnexo>());
    	
    	assertEquals("success", action.delete());
    }

    @Test
    public void testDeleteExcecao() throws Exception
    {
    	DocumentoAnexo documentoAnexo = new DocumentoAnexo();
    	documentoAnexo.setId(1L);
    	documentoAnexo.setUrl("url");
    	documentoAnexo.setOrigem('Y');
    	documentoAnexo.setOrigemId(1L);

    	action.setDocumentoAnexo(documentoAnexo);

    	when(manager.findByIdProjection(documentoAnexo.getId())).thenReturn(documentoAnexo);
    	doThrow(Exception.class).when(manager).deletarDocumentoAnexo("documentosCandidatos", documentoAnexo);
    	when(manager.getDocumentoAnexoByOrigemId('Y', 1L)).thenReturn(new ArrayList<DocumentoAnexo>());

    	assertEquals("success", action.delete());
    }

    @Test
    public void testGetsSets()
    {
    	action.getDocumentoAnexos();
    	action.getDocumentoAnexo();
    	action.setDocumentoAnexos(new ArrayList<DocumentoAnexo>());
    	action.setSolicitacaoId(1L);
		action.getSolicitacaoId();
		action.setColaboradorId(1L);
		action.getColaboradorId();
		action.getTitulo();
		action.setVisualizar('C');
		action.getVisualizar();
		action.setEtapaSeletivaId(1L);
		action.getEtapaSeletivaId();
		action.getVoltar();
    }
}