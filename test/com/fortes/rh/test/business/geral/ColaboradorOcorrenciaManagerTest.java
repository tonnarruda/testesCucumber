package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManagerImpl;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorOcorrencia;

public class ColaboradorOcorrenciaManagerTest extends MockObjectTestCase
{
	ColaboradorOcorrenciaManagerImpl colaboradorOcorrenciaManager = null;
	Mock colaboradorOcorrenciaDao = null;
	Mock transactionManager;
	Mock ocorrenciaManager;
	Mock colaboradorManager;
	Mock acPessoalClientColaboradorOcorrencia;

	protected void setUp() throws Exception
	{
		super.setUp();

		colaboradorOcorrenciaManager = new ColaboradorOcorrenciaManagerImpl();

		colaboradorOcorrenciaDao = mock(ColaboradorOcorrenciaDao.class);
		colaboradorOcorrenciaManager.setDao((ColaboradorOcorrenciaDao) colaboradorOcorrenciaDao.proxy());
		transactionManager = mock(PlatformTransactionManager.class);
		colaboradorOcorrenciaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		ocorrenciaManager = mock(OcorrenciaManager.class);
		colaboradorOcorrenciaManager.setOcorrenciaManager((OcorrenciaManager)ocorrenciaManager.proxy());
		colaboradorManager = mock(ColaboradorManager.class);
		colaboradorOcorrenciaManager.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
		acPessoalClientColaboradorOcorrencia = mock(AcPessoalClientColaboradorOcorrencia.class);
		colaboradorOcorrenciaManager.setAcPessoalClientColaboradorOcorrencia((AcPessoalClientColaboradorOcorrencia)acPessoalClientColaboradorOcorrencia.proxy());
	}

	public void testFindByColaborador()
	{
		Collection<ColaboradorOcorrencia> colecao = new ArrayList<ColaboradorOcorrencia>();
		colaboradorOcorrenciaDao.expects(once()).method("findByColaborador").will(returnValue(colecao));
		assertNotNull(colaboradorOcorrenciaManager.findByColaborador(1L));
	}

	public void testFindProjection()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setId(1L);

		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = new ArrayList<ColaboradorOcorrencia>();
		colaboradorOcorrencias.add(colaboradorOcorrencia);

		colaboradorOcorrenciaDao.expects(once()).method("findProjection").with(eq(0),eq(0),eq(colaborador.getId())).will(returnValue(colaboradorOcorrencias));

		Collection<ColaboradorOcorrencia> retorno = colaboradorOcorrenciaManager.findProjection(0, 0, colaborador.getId());

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjectionEmpresa()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setId(1L);

		colaboradorOcorrenciaDao.expects(once()).method("findByIdProjection").with(eq(colaboradorOcorrencia.getId())).will(returnValue(colaboradorOcorrencia));

		assertEquals(colaboradorOcorrencia, colaboradorOcorrenciaManager.findByIdProjection(colaboradorOcorrencia.getId()));
	}

	public void testSaveOcorrenciasFromAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Ocorrencia ocorrencia = new Ocorrencia();
		Colaborador colaborador = new Colaborador();
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = montaColaboradorOcorrencias(empresa, ocorrencia, colaborador);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		ocorrenciaManager.expects(atLeastOnce()).method("findByCodigoAC").with(ANYTHING,ANYTHING, ANYTHING).will(returnValue(ocorrencia));
		colaboradorManager.expects(atLeastOnce()).method("findByCodigoAC").with(ANYTHING,ANYTHING, ANYTHING).will(returnValue(colaborador));
		colaboradorOcorrenciaDao.expects(atLeastOnce()).method("save");
		transactionManager.expects(atLeastOnce()).method("commit").with(ANYTHING);

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.saveOcorrenciasFromAC(colaboradorOcorrencias);
		}
		catch(Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	public void testSaveOcorrenciasFromACException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Ocorrencia ocorrencia = new Ocorrencia();
		Colaborador colaborador = new Colaborador();
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = montaColaboradorOcorrencias(empresa, ocorrencia, colaborador);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		ocorrenciaManager.expects(atLeastOnce()).method("findByCodigoAC").with(ANYTHING,ANYTHING, ANYTHING).will(returnValue(ocorrencia));
		colaboradorManager.expects(atLeastOnce()).method("findByCodigoAC").with(ANYTHING,ANYTHING, ANYTHING).will(returnValue(colaborador));
		colaboradorOcorrenciaDao.expects(atLeastOnce()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		transactionManager.expects(atLeastOnce()).method("rollback").with(ANYTHING);

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.saveOcorrenciasFromAC(colaboradorOcorrencias);
		}
		catch(Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testRemoveFromAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Ocorrencia ocorrencia = new Ocorrencia();
		Colaborador colaborador = new Colaborador();
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = montaColaboradorOcorrencias(empresa, ocorrencia, colaborador);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		colaboradorOcorrenciaDao.expects(atLeastOnce()).method("findByDadosAC").withAnyArguments().will(returnValue(ColaboradorOcorrenciaFactory.getEntity(1L)));
		colaboradorOcorrenciaDao.expects(atLeastOnce()).method("remove").with(ANYTHING).isVoid();
		transactionManager.expects(atLeastOnce()).method("commit").with(ANYTHING);

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.removeFromAC(colaboradorOcorrencias);
		}
		catch(Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	public void testRemoveFromACException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Ocorrencia ocorrencia = new Ocorrencia();
		Colaborador colaborador = new Colaborador();
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = montaColaboradorOcorrencias(empresa, ocorrencia, colaborador);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		colaboradorOcorrenciaDao.expects(atLeastOnce()).method("findByDadosAC").withAnyArguments().will(returnValue(ColaboradorOcorrenciaFactory.getEntity(1L)));
		colaboradorOcorrenciaDao.expects(atLeastOnce()).method("remove").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		transactionManager.expects(atLeastOnce()).method("rollback").with(ANYTHING);

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.removeFromAC(colaboradorOcorrencias);
		}
		catch(Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	private Collection<ColaboradorOcorrencia> montaColaboradorOcorrencias(Empresa empresa, Ocorrencia ocorrencia, Colaborador colaborador)
	{
		ColaboradorOcorrencia colaboradorOcorrencia1 = new ColaboradorOcorrencia();
		ColaboradorOcorrencia colaboradorOcorrencia2 = new ColaboradorOcorrencia();

		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia1);
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia2);

		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = new ArrayList<ColaboradorOcorrencia>(2);
		colaboradorOcorrencias.add(colaboradorOcorrencia1);
		colaboradorOcorrencias.add(colaboradorOcorrencia2);
		return colaboradorOcorrencias;
	}
	
	public void testVerifyExistsMesmaData()
	{
		colaboradorOcorrenciaDao.expects(once()).method("verifyExistsMesmaData").will(returnValue(true));
		assertTrue(colaboradorOcorrenciaManager.verifyExistsMesmaData(1L, 1L, 1L, 1L, new Date()));
	}

	public void testSaveColaboradorOcorrencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		empresa.setCodigoAC("0004");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setCodigoAC("0001");
		ocorrencia.setIntegraAC(false);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("00002");
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);

		colaboradorOcorrenciaDao.expects(once()).method("save");

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveColaboradorOcorrenciaIntegrado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		empresa.setCodigoAC("0004");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setCodigoAC("0001");
		ocorrencia.setIntegraAC(true);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("00002");
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);
		
		acPessoalClientColaboradorOcorrencia.expects(once()).method("criarColaboradorOcorrencia").will(returnValue(true));
		colaboradorOcorrenciaDao.expects(once()).method("save");
		
		Exception exception = null;
		
		try
		{
			colaboradorOcorrenciaManager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testSaveColaboradorOcorrenciaException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		empresa.setCodigoAC("0004");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setCodigoAC("0001");
		ocorrencia.setIntegraAC(true);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("00002");
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);

		acPessoalClientColaboradorOcorrencia.expects(once()).method("criarColaboradorOcorrencia").will(throwException(new IntegraACException("")));

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testRemove()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(false);

		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setCodigoAC("0001");
		ocorrencia.setIntegraAC(false);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("00002");
		
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);
		
		colaboradorOcorrenciaDao.expects(once()).method("findByIdProjection").will(returnValue(colaboradorOcorrencia));
		colaboradorOcorrenciaDao.expects(once()).method("remove").isVoid();
		
		Exception exception = null;
		
		try
		{
			colaboradorOcorrenciaManager.remove(colaboradorOcorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testRemoveIntegradoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		empresa.setCodigoAC("0004");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setCodigoAC("0001");
		ocorrencia.setIntegraAC(true);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("00002");
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);

		colaboradorOcorrenciaDao.expects(once()).method("findByIdProjection").will(returnValue(colaboradorOcorrencia));
		acPessoalClientColaboradorOcorrencia.expects(once()).method("removerColaboradorOcorrencia").will(returnValue(true));
		colaboradorOcorrenciaDao.expects(once()).method("remove").isVoid();

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.remove(colaboradorOcorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testRemoveException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		empresa.setCodigoAC("0004");
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setCodigoAC("0001");
		ocorrencia.setIntegraAC(true);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("00002");
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);

		colaboradorOcorrenciaDao.expects(once()).method("findByIdProjection").will(returnValue(colaboradorOcorrencia));

		acPessoalClientColaboradorOcorrencia.expects(once()).method("removerColaboradorOcorrencia").will(throwException(new IntegraACException("")));

		Exception exception = null;

		try
		{
			colaboradorOcorrenciaManager.remove(colaboradorOcorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testMontaGraficoAbsenteismo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Absenteismo> retornoBD = new ArrayList<Absenteismo>();
		retornoBD.add(new Absenteismo("2011", "01", 0));
		retornoBD.add(new Absenteismo("2011", "02", 2));
		retornoBD.add(new Absenteismo("2011", "03", 0));
		retornoBD.add(new Absenteismo("2011", "04", 15));
		retornoBD.add(new Absenteismo("2011", "05", 19));
		
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(retornoBD));
		colaboradorManager.expects(atLeastOnce()).method("countAtivosPeriodo").will(returnValue(10));
		
		Collection<Object[]> absenteismos = colaboradorOcorrenciaManager.montaGraficoAbsenteismo("01/2011", "05/2011", empresa.getId());
		assertEquals(5, absenteismos.size());
		assertEquals(0.0, ((Object[])absenteismos.toArray()[0])[1]);
		assertEquals(0.01, ((Object[])absenteismos.toArray()[1])[1]);
		assertEquals(0.0714, ((Object[])absenteismos.toArray()[3])[1]);
	}
	
	public void testMontaAbsenteismo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Absenteismo> retornoBD = new ArrayList<Absenteismo>();
		retornoBD.add(new Absenteismo("2011", "01", 0));
		retornoBD.add(new Absenteismo("2011", "02", 2));
		retornoBD.add(new Absenteismo("2011", "03", 0));
		retornoBD.add(new Absenteismo("2011", "04", 15));
		retornoBD.add(new Absenteismo("2011", "05", 19));
		
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(retornoBD));
		colaboradorManager.expects(atLeastOnce()).method("countAtivosPeriodo").will(returnValue(10));
		
		Date dataIni = DateUtil.montaDataByString("02/01/2011");
		Date dataFim = DateUtil.montaDataByString("19/05/2011");
		
		Collection<Absenteismo> absenteismos = colaboradorOcorrenciaManager.montaAbsenteismo(dataIni, dataFim, empresa.getId(), null, null);
		assertEquals(5, absenteismos.size());
		
		Absenteismo absenteismoJan = (Absenteismo) absenteismos.toArray()[0];
		assertEquals("01", absenteismoJan.getMes());
		assertEquals(new Integer(0), absenteismoJan.getQtdTotalFaltas());
		assertEquals(0.0, absenteismoJan.getAbsenteismo());

		Absenteismo absenteismoFev = (Absenteismo) absenteismos.toArray()[1];
		assertEquals("02", absenteismoFev.getMes());
		assertEquals(new Integer(2), absenteismoFev.getQtdTotalFaltas());
		assertEquals(new Integer(10), absenteismoFev.getQtdAtivos());
		assertEquals(new Integer(20), absenteismoFev.getQtdDiasTrabalhados());
		assertEquals(0.01, absenteismoFev.getAbsenteismo());
		
		Absenteismo absenteismoAbr = (Absenteismo) absenteismos.toArray()[3];
		assertEquals("04", absenteismoAbr.getMes());
		assertEquals(new Integer(15), absenteismoAbr.getQtdTotalFaltas());
		assertEquals(new Integer(10), absenteismoAbr.getQtdAtivos());
		assertEquals(new Integer(21), absenteismoAbr.getQtdDiasTrabalhados());
		assertEquals(0.0714, absenteismoAbr.getAbsenteismo());
	}
	
	public void testMontaAbsenteismoException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(new ArrayList<Absenteismo>()));
		
		Date dataIni = new Date();
		Date dataFim = dataIni;
		Exception exception = null;
		
		try
		{
			colaboradorOcorrenciaManager.montaAbsenteismo(dataIni, dataFim, empresa.getId(), null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}

	private void setDadosOcorrenciaAC(Empresa empresa, Ocorrencia ocorrencia, Colaborador colaborador, ColaboradorOcorrencia colaboradorOcorrencia)
	{
		empresa.setCodigoAC("1");
		ocorrencia.setCodigoAC("111");
		colaborador.setCodigoAC("123");
		ocorrencia.setEmpresa(empresa);
		colaboradorOcorrencia.setDataIni(new Date());
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrencia.setColaborador(colaborador);
	}
}