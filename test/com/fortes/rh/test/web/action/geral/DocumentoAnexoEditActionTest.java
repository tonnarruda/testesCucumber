package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.TipoDocumentoManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.DocumentoAnexoEditAction;

public class DocumentoAnexoEditActionTest extends MockObjectTestCase
{
	private DocumentoAnexoEditAction action;
	private Mock manager;
	private Mock etapaSeletivaManager;
	private Mock tipoDocumentoManager;

	protected void setUp()
	{
		action = new DocumentoAnexoEditAction();
		manager = new Mock(DocumentoAnexoManager.class);
		etapaSeletivaManager = new Mock(EtapaSeletivaManager.class);
		tipoDocumentoManager = new Mock(TipoDocumentoManager.class);
		action.setDocumentoAnexoManager((DocumentoAnexoManager) manager.proxy());
		action.setEtapaSeletivaManager((EtapaSeletivaManager) etapaSeletivaManager.proxy());
		action.setTipoDocumentoManager((TipoDocumentoManager) tipoDocumentoManager.proxy());

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

	public void testPrepareInsertCandidato() throws Exception
	{
		tipoDocumentoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<TipoDocumento>()));
		etapaSeletivaManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<EtapaSeletiva>()));
		manager.expects(once()).method("getNome").with(ANYTHING, ANYTHING).will(returnValue("teste"));
		
		assertEquals("success", action.prepareInsertCandidato());
	}

	public void testPrepareInsertColaborador() throws Exception
	{
		tipoDocumentoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<TipoDocumento>()));
		manager.expects(once()).method("getNome").with(ANYTHING, ANYTHING).will(returnValue("teste"));
		assertEquals("success", action.prepareInsertColaborador());
	}

	public void testInsertCandidato() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		manager.expects(once()).method("inserirDocumentoAnexo").with(ANYTHING,ANYTHING,ANYTHING);

		assertEquals("success",action.insertCandidato());

	}

	public void testInsertCandidatoExcecao() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		manager.expects(once()).method("inserirDocumentoAnexo").with(ANYTHING,ANYTHING,ANYTHING).will(throwException(new Exception()));

		assertEquals("input",action.insertCandidato());

	}

	public void testUpdateCandidato() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		manager.expects(once()).method("atualizarDocumentoAnexo").with(ANYTHING,ANYTHING,ANYTHING);

		assertEquals("success",action.updateCandidato());
	}

	public void testUpdateCandidatoExcecao() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		manager.expects(once()).method("atualizarDocumentoAnexo").with(ANYTHING,ANYTHING,ANYTHING).will(throwException(new Exception()));

		assertEquals("input",action.updateCandidato());
	}

	public void testInsertColaborador() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		manager.expects(once()).method("inserirDocumentoAnexo").with(ANYTHING,ANYTHING,ANYTHING);

		assertEquals("success",action.insertColaborador());

	}

	public void testUpdateColaborador() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		manager.expects(once()).method("atualizarDocumentoAnexo").with(ANYTHING,ANYTHING,ANYTHING);

		assertEquals("success",action.updateColaborador());

	}

	public void testPrepareUpdateCandidato() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);
		documentoAnexo.setOrigem('Z');
		documentoAnexo.setOrigemId(1L);

		action.setDocumentoAnexo(documentoAnexo);

		tipoDocumentoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<TipoDocumento>()));
		manager.expects(once()).method("getNome").with(eq('Z'), eq(1L)).will(returnValue("bruno"));
		manager.expects(once()).method("findById").with(eq(documentoAnexo.getId())).will(returnValue(documentoAnexo));
		etapaSeletivaManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<EtapaSeletiva>()));

		assertEquals("success", action.prepareUpdateCandidato());
	}

	public void testPrepareUpdateColaborador() throws Exception
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);
		documentoAnexo.setOrigem('Z');
		documentoAnexo.setOrigemId(1L);

		action.setDocumentoAnexo(documentoAnexo);
		
		tipoDocumentoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<TipoDocumento>()));
		manager.expects(once()).method("getNome").with(eq('Z'), eq(1L)).will(returnValue("bruno"));
		manager.expects(once()).method("findById").with(eq(documentoAnexo.getId())).will(returnValue(documentoAnexo));

		assertEquals("success", action.prepareUpdateColaborador());
	}

	public void testGetsSets()
	{
		action.getDocumentoAnexo();
		action.setEtapaSeletivas(new ArrayList<EtapaSeletiva>());
		action.getEtapaSeletivas();
		action.setDocumento(new File());
		action.getDocumento();
		action.setNome("nome");
		action.getNome();
		action.setOrigemTmp('z');
		action.getOrigemTmp();
		action.setOrigemIdTmp(1L);
		action.getOrigemIdTmp();
		action.setSolicitacaoId(1L);
		action.getSolicitacaoId();
	}

}