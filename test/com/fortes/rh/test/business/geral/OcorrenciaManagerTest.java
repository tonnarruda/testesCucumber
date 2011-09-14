package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.OcorrenciaManagerImpl;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.web.ws.AcPessoalClientOcorrencia;

public class OcorrenciaManagerTest extends MockObjectTestCase
{
	OcorrenciaManagerImpl ocorrenciaManager = null;
	Mock ocorrenciaDao = null;
	Mock acPessoalClientOcorrencia;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		ocorrenciaManager = new OcorrenciaManagerImpl();

		ocorrenciaDao = mock(OcorrenciaDao.class);
		ocorrenciaManager.setDao((OcorrenciaDao) ocorrenciaDao.proxy());
		acPessoalClientOcorrencia = mock(AcPessoalClientOcorrencia.class);
		ocorrenciaManager.setAcPessoalClientOcorrencia((AcPessoalClientOcorrencia)acPessoalClientOcorrencia.proxy());
	}

	public void testRemoveByCodigoAC()
	{
		ocorrenciaDao.expects(once()).method("removeByCodigoAC").will(returnValue(true));
		ocorrenciaManager.removeByCodigoAC("002", 1L);
	}

	public void testFindByCodigoAC()
	{
		ocorrenciaDao.expects(once()).method("findByCodigoAC").will(returnValue(new Ocorrencia()));
		ocorrenciaManager.findByCodigoAC("002", "001", "XXX");
	}

	public void testSaveOrUpdate()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setIntegraAC(true);
		setDadosOcorrencia(ocorrencia, empresa);

		Exception exception = null;
		try
		{
			//	save
			acPessoalClientOcorrencia.expects(once()).method("criarTipoOcorrencia").will(returnValue("00123"));
			ocorrenciaDao.expects(once()).method("save");
			ocorrenciaManager.saveOrUpdate(ocorrencia, empresa);

			// update
			ocorrencia.setId(1L);
			acPessoalClientOcorrencia.expects(once()).method("criarTipoOcorrencia").will(returnValue("00123"));
			ocorrenciaDao.expects(once()).method("update");
			ocorrenciaManager.saveOrUpdate(ocorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveOrUpdateException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setIntegraAC(true);
		setDadosOcorrencia(ocorrencia, empresa);

		Exception exception = null;
		try
		{
			// Caso: Metodo do cliente retornando codigo vazio
			acPessoalClientOcorrencia.expects(once()).method("criarTipoOcorrencia").will(returnValue(""));
			ocorrenciaManager.saveOrUpdate(ocorrencia, empresa);

		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testRemove()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		setDadosOcorrencia(ocorrencia, empresa);

		ocorrenciaDao.expects(once()).method("findById").will(returnValue(ocorrencia));
		acPessoalClientOcorrencia.expects(once()).method("removerTipoOcorrencia").will(returnValue(true));
		ocorrenciaDao.expects(once()).method("remove");

		Exception exception = null;

		try
		{
			ocorrenciaManager.remove(ocorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testRemoveException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		setDadosOcorrencia(ocorrencia, empresa);

		Exception exception = null;

		try
		{
			ocorrenciaDao.expects(once()).method("findById").will(returnValue(ocorrencia));
			acPessoalClientOcorrencia.expects(once()).method("removerTipoOcorrencia").will(returnValue(false));
			ocorrenciaManager.remove(ocorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSaveFromAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		setDadosOcorrencia(ocorrencia, empresa);

		Exception exception = null;
		try
		{
			ocorrenciaDao.expects(once()).method("findByCodigoAC").will(returnValue(new Ocorrencia()));
			ocorrenciaDao.expects(once()).method("update").isVoid();
			ocorrenciaManager.saveFromAC(ocorrencia);

			ocorrenciaDao.expects(once()).method("findByCodigoAC").will(returnValue(null));
			ocorrenciaDao.expects(once()).method("save");
			ocorrenciaManager.saveFromAC(ocorrencia);
		}
		catch (Exception e) { exception = e; }

		assertNull(exception);
	}

	public void testSincronizar() throws Exception
	{
		Empresa empresaOrigem = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		Collection<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		ocorrencias.add(ocorrencia);
		
		ocorrenciaDao.expects(once()).method("findSincronizarOcorrenciaInteresse").with(eq(empresaOrigem.getId())).will(returnValue(ocorrencias));
		ocorrenciaDao.expects(once()).method("save");
		ocorrenciaDao.expects(once()).method("update");
		
		ocorrenciaManager.sincronizar(empresaOrigem.getId(), empresaDestino.getId());
		
		assertEquals(empresaDestino.getId(), ocorrencia.getEmpresa().getId() );
	}

	private void setDadosOcorrencia(Ocorrencia ocorrencia, Empresa empresa)
	{
		ocorrencia.setCodigoAC("0001");
		empresa.setCodigoAC("0004");
		empresa.setAcIntegra(true);
		ocorrencia.setEmpresa(empresa);
	}
}
