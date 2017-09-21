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

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.TipoDocumentoManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.geral.DocumentoAnexoEditAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class DocumentoAnexoEditActionTest
{
	private DocumentoAnexoEditAction action;
	private DocumentoAnexoManager manager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private TipoDocumentoManager tipoDocumentoManager;

	@Before
	public void setUp()
	{
		action = new DocumentoAnexoEditAction();
		manager = mock(DocumentoAnexoManager.class);
		etapaSeletivaManager = mock(EtapaSeletivaManager.class);
		tipoDocumentoManager = mock(TipoDocumentoManager.class);
		
		action.setDocumentoAnexoManager(manager);
		action.setEtapaSeletivaManager(etapaSeletivaManager);
		action.setTipoDocumentoManager(tipoDocumentoManager);

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
	}

	@After
	public void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	@Test
	public void testPrepareInsert() throws Exception
	{
		criaDocumentoAnexo();
		validaRoleSecurity();
		
		when(tipoDocumentoManager.findAll()).thenReturn(new ArrayList<TipoDocumento>());
		when(etapaSeletivaManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<EtapaSeletiva>());
		when(manager.getTituloEdit(action.getDocumentoAnexo().getOrigem(), action.getDocumentoAnexo().getOrigemId(), true)).thenReturn("Titulo Ok");
		when(manager.findById(action.getDocumentoAnexo().getId())).thenReturn(action.getDocumentoAnexo());
		
		assertEquals("success", action.prepare());
	}
	
	@Test
	public void testPrepareInsertSemAcesso() throws Exception
	{
		validaRoleSecurity();
		criaDocumentoAnexo();
		action.getDocumentoAnexo().setOrigem('X');
		
		assertEquals("success", action.prepare());
		assertEquals("Usuário sem permissão de acesso.", action.getActionMessages().toArray()[0]);
	}

	@Test
	public void testInsert() throws Exception
	{
		criaDocumentoAnexo();
		validaRoleSecurity();
		assertEquals("success",action.insert());
	}

	private void validaRoleSecurity() {
		Map<String, String> roles = new HashMap<>();
		roles.put("ACEGI_SECURITY_CONTEXT", "ROLE_COLAB_LIST_DOCUMENTOANEXO");
		MockActionContext.getContext().setSession(roles);
		MockSecurityUtil.verifyRole = true;
	}
	
	@Test
	public void testInsertException() throws Exception
	{
		criaDocumentoAnexo();
		doThrow(Exception.class).when(manager).inserirDocumentoAnexo("", action.getDocumentoAnexo(), null);

		assertEquals("input",action.insert());
	}

	private DocumentoAnexo criaDocumentoAnexo() {
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);
		documentoAnexo.setOrigem(OrigemAnexo.AnexoColaborador);
		documentoAnexo.setOrigemId(1L);
		action.setDocumentoAnexo(documentoAnexo);
		return documentoAnexo;
	}

	@Test
	public void testUpdate() throws Exception
	{
		criaDocumentoAnexo();
		validaRoleSecurity();
		assertEquals("success",action.update());
	}

	@Test
	public void testUpdateExcecao() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		doThrow(Exception.class).when(manager).atualizarDocumentoAnexo("", action.getDocumentoAnexo(), null);

		assertEquals("input",action.update());
	}

	@Test
	public void testPrepareUpdate() throws Exception
	{
		DocumentoAnexo documentoAnexo = criaDocumentoAnexo();
		validaRoleSecurity();

		when(tipoDocumentoManager.findAll()).thenReturn(new ArrayList<TipoDocumento>());
		when(manager.findById(documentoAnexo.getId())).thenReturn(documentoAnexo);
		when(etapaSeletivaManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<EtapaSeletiva>());
		when(manager.getTituloEdit(action.getDocumentoAnexo().getOrigem(), action.getDocumentoAnexo().getOrigemId(), true)).thenReturn("Titulo Ok");

		assertEquals("success", action.prepare());
	}

	@Test
	public void testShowDocumento() throws Exception{
		DocumentoAnexo documentoAnexo = criaDocumentoAnexo();
		validaRoleSecurity();
		documentoAnexo.setUrl("topo_img_right.jpg");
		
		when(manager.findById(documentoAnexo.getId())).thenReturn(documentoAnexo);
		
		assertEquals("success", action.showDocumento());
	}
	
	@Test
	public void testShowDocumentoSemPermissao() throws Exception{
		validaRoleSecurity();
		criaDocumentoAnexo();
		action.getDocumentoAnexo().setOrigem('X');
		
		assertEquals("success", action.showDocumento());
		assertEquals("Sem permissão de acesso.", action.getActionMessages().toArray()[0]);
	}
	
	@Test
	public void testGetsSets()
	{
		action.getDocumentoAnexo();
		action.setEtapaSeletivas(new ArrayList<EtapaSeletiva>());
		action.getEtapaSeletivas();
		action.setDocumento(new File());
		action.getDocumento();
		action.setSolicitacaoId(1L);
		action.getSolicitacaoId();
		action.setColaboradorId(1L);
		action.getColaboradorId();
		action.setPage(1);
		action.getPage();
		action.setEtapaSeletivaId(1L);
		action.getEtapaSeletivaId();
		action.setVisualizar('C');
		action.getVisualizar();
		action.setTipoDocumentos(null);
		action.getTipoDocumentos();
		action.getTitulo();
	}
}