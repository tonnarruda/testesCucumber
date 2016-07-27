package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.OcorrenciaManagerImpl;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientOcorrencia;

public class OcorrenciaManagerTest extends MockObjectTestCaseManager<OcorrenciaManagerImpl> implements TesteAutomaticoManager
{
	Mock ocorrenciaDao = null;
	Mock acPessoalClientOcorrencia;
	Mock empresaManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		manager = new OcorrenciaManagerImpl();

		ocorrenciaDao = mock(OcorrenciaDao.class);
		manager.setDao((OcorrenciaDao) ocorrenciaDao.proxy());
		acPessoalClientOcorrencia = mock(AcPessoalClientOcorrencia.class);
		manager.setAcPessoalClientOcorrencia((AcPessoalClientOcorrencia)acPessoalClientOcorrencia.proxy());
		
		empresaManager = new Mock(EmpresaManager.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

	public void testRemoveByCodigoAC()
	{
		ocorrenciaDao.expects(once()).method("removeByCodigoAC").will(returnValue(true));
		manager.removeByCodigoAC("002", 1L);
	}

	public void testFindByCodigoAC()
	{
		ocorrenciaDao.expects(once()).method("findByCodigoAC").will(returnValue(new Ocorrencia()));
		manager.findByCodigoAC("002", "001", "XXX");
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
			manager.saveOrUpdate(ocorrencia, empresa);

			// update
			ocorrencia.setId(1L);
			acPessoalClientOcorrencia.expects(once()).method("criarTipoOcorrencia").will(returnValue("00123"));
			ocorrenciaDao.expects(once()).method("update");
			manager.saveOrUpdate(ocorrencia, empresa);
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
			manager.saveOrUpdate(ocorrencia, empresa);

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
			manager.remove(ocorrencia, empresa);
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
			manager.remove(ocorrencia, empresa);
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
			manager.saveFromAC(ocorrencia);

			ocorrenciaDao.expects(once()).method("findByCodigoAC").will(returnValue(null));
			ocorrenciaDao.expects(once()).method("save");
			manager.saveFromAC(ocorrencia);
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
		ocorrenciaDao.expects(atLeastOnce()).method("save");
		MockSpringUtil.mocks.put("empresaManager", empresaManager);
		empresaManager.expects(once()).method("findByIdProjection").with(eq(empresaOrigem.getId())).will(returnValue(empresaOrigem));
		
		manager.sincronizar(empresaOrigem.getId(), empresaDestino, null);
		
		assertEquals(empresaDestino.getId(), ocorrencia.getEmpresa().getId() );
	}

	private void setDadosOcorrencia(Ocorrencia ocorrencia, Empresa empresa)
	{
		ocorrencia.setCodigoAC("0001");
		empresa.setCodigoAC("0004");
		empresa.setAcIntegra(true);
		ocorrencia.setEmpresa(empresa);
	}

	public void testExecutaTesteAutomaticoDoManager() 
	{
		testeAutomatico(ocorrenciaDao);		
	}
}
