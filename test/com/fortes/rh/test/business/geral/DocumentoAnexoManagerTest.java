package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.DocumentoAnexoManagerImpl;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.test.factory.geral.TipoDocumentoFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.util.ArquivoUtil;

public class DocumentoAnexoManagerTest extends MockObjectTestCase
{
	DocumentoAnexoManagerImpl documentoAnexoManager = null;
	Mock documentoAnexoDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		documentoAnexoManager = new DocumentoAnexoManagerImpl();

		documentoAnexoDao = new Mock(DocumentoAnexoDao.class);
		documentoAnexoManager.setDao((DocumentoAnexoDao) documentoAnexoDao.proxy());

		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
	}

	public void testGetDocumentoAnexoByOrigemId()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);
		documentoAnexo.setOrigem('Z');
		documentoAnexo.setOrigemId(1L);

		Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();
		documentoAnexos.add(documentoAnexo);

		documentoAnexoDao.expects(once()).method("getDocumentoAnexoByOrigemId").withAnyArguments().will(returnValue(documentoAnexos));

		assertEquals(documentoAnexos.size(), documentoAnexoManager.getDocumentoAnexoByOrigemId('Z',1L).size());
	}

	public void testAtualizaDocumentoAnexoSemDocumento()
	{
		File documento = null;

		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setEtapaSeletiva(new EtapaSeletiva());

		Exception excecao = null;

		try
		{
			documentoAnexoDao.expects(once()).method("update").with(ANYTHING);
			documentoAnexoManager.atualizarDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		 assertNull(excecao);
	}

	public void testAtualizaDocumentoAnexoComDocumento()
	{
		File documento = new File();

		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setEtapaSeletiva(new EtapaSeletiva());

		Exception excecao = null;

		try
		{
			documentoAnexoDao.expects(once()).method("update").with(ANYTHING);
			documentoAnexoManager.atualizarDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNull(excecao);
	}

	public void testAtualizaDocumentoAnexoSemDocumentoExcecao()
	{
		File documento = new File();
		DocumentoAnexo documentoAnexo = null;

		Exception excecao = null;

		try
		{
			documentoAnexoManager.atualizarDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}

		assertNotNull(excecao);
	}

	public void testAtualizaDocumentoAnexoComDocumentoETipoDocumento()
	{
		TipoDocumento tipoDocumento = TipoDocumentoFactory.getEntity();
		tipoDocumento.setDescricao("Certificado");
		
		File documento = new File();
		
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setEtapaSeletiva(new EtapaSeletiva());
		documentoAnexo.setTipoDocumento(tipoDocumento);

		Exception excecao = null;

		try
		{
			documentoAnexoDao.expects(once()).method("update").with(ANYTHING);
			documentoAnexoManager.atualizarDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNull(excecao);
	}
	
	public void testInserirDocumentoAnexo()
	{
		File documento = new File();

		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setEtapaSeletiva(new EtapaSeletiva());

		Exception excecao = null;

		try
		{
			documentoAnexoDao.expects(once()).method("save").with(ANYTHING);
			documentoAnexoManager.inserirDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNull(excecao);
	}

	public void testInserirDocumentoAnexoETipoDocumento()
	{
		File documento = new File();
		
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setEtapaSeletiva(new EtapaSeletiva());
		documentoAnexo.setTipoDocumento(new TipoDocumento());
		
		Exception excecao = null;
		
		try
		{
			documentoAnexoDao.expects(once()).method("save").with(ANYTHING);
			documentoAnexoManager.inserirDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNull(excecao);
	}

	public void testInserirDocumentoAnexoExcecao()
	{
		File documento = new File();
		DocumentoAnexo documentoAnexo = null;

		Exception excecao = null;

		try
		{
			documentoAnexoManager.inserirDocumentoAnexo("diretorio", documentoAnexo, documento);
		}
		catch (Exception e)
		{
			excecao = e;
		}

		assertNotNull(excecao);
	}

	public void testDeletarDocumentoAnexo()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);

		Exception excecao = null;

		try
		{
			documentoAnexoDao.expects(once()).method("remove").with(ANYTHING);
			documentoAnexoManager.deletarDocumentoAnexo("diretorio", documentoAnexo);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNull(excecao);
	}

	public void testDeletarDocumentoAnexoETipoDoc()
	{
		TipoDocumento tipoDocumento = TipoDocumentoFactory.getEntity();
		tipoDocumento.setDescricao("Certificado");
		
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setTipoDocumento(tipoDocumento);
		documentoAnexo.setId(1L);
		
		Exception excecao = null;
		
		try
		{
			documentoAnexoDao.expects(once()).method("remove").with(ANYTHING);
			documentoAnexoManager.deletarDocumentoAnexo("diretorio", documentoAnexo);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNull(excecao);
	}

	public void testDeletarDocumentoAnexoExcecao()
	{
		Exception excecao = null;

		try
		{
			documentoAnexoManager.deletarDocumentoAnexo("diretorio", null);
		}
		catch (Exception e)
		{
			excecao = e;
		}
		assertNotNull(excecao);
	}
	
	public void testFindByIdProjection()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);
		
		documentoAnexoDao.expects(once()).method("findByIdProjection").with(eq(documentoAnexo.getId())).will(returnValue(documentoAnexo));

		assertEquals(documentoAnexo, documentoAnexoManager.findByIdProjection(documentoAnexo.getId()));
	}
}
