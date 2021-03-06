package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManagerImpl;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorOcorrencia;

public class ColaboradorOcorrenciaManagerTest extends MockObjectTestCaseManager<ColaboradorOcorrenciaManagerImpl> implements TesteAutomaticoManager
{
	Mock colaboradorOcorrenciaDao = null;
	Mock transactionManager;
	Mock ocorrenciaManager;
	Mock colaboradorManager;
	Mock colaboradorAfastamentoManager;
	Mock acPessoalClientColaboradorOcorrencia;
	Mock gerenciadorComunicacaoManager;
	Mock areaOrganizacionalManager;
	Mock usuarioManager;
	Mock usuarioEmpresaManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		manager = new ColaboradorOcorrenciaManagerImpl();

		colaboradorOcorrenciaDao = mock(ColaboradorOcorrenciaDao.class);
		manager.setDao((ColaboradorOcorrenciaDao) colaboradorOcorrenciaDao.proxy());
		transactionManager = mock(PlatformTransactionManager.class);
		manager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		ocorrenciaManager = mock(OcorrenciaManager.class);
		manager.setOcorrenciaManager((OcorrenciaManager)ocorrenciaManager.proxy());
		colaboradorManager = mock(ColaboradorManager.class);
		manager.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
		colaboradorAfastamentoManager = mock(ColaboradorAfastamentoManager.class);
		manager.setColaboradorAfastamentoManager((ColaboradorAfastamentoManager)colaboradorAfastamentoManager.proxy());
		acPessoalClientColaboradorOcorrencia = mock(AcPessoalClientColaboradorOcorrencia.class);
		manager.setAcPessoalClientColaboradorOcorrencia((AcPessoalClientColaboradorOcorrencia)acPessoalClientColaboradorOcorrencia.proxy());
		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		manager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager)gerenciadorComunicacaoManager.proxy());
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		manager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		usuarioManager = mock(UsuarioManager.class);
		manager.setUsuarioManager((UsuarioManager) usuarioManager.proxy());
		usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
		manager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());
	}

	public void testFindByColaborador()
	{
		Collection<ColaboradorOcorrencia> colecao = new ArrayList<ColaboradorOcorrencia>();
		colaboradorOcorrenciaDao.expects(once()).method("findByColaborador").will(returnValue(colecao));
		assertNotNull(manager.findByColaborador(1L));
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

		Collection<ColaboradorOcorrencia> retorno = manager.findProjection(0, 0, colaborador.getId());

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjectionEmpresa()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setId(1L);

		colaboradorOcorrenciaDao.expects(once()).method("findByIdProjection").with(eq(colaboradorOcorrencia.getId())).will(returnValue(colaboradorOcorrencia));

		assertEquals(colaboradorOcorrencia, manager.findByIdProjection(colaboradorOcorrencia.getId()));
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
			manager.saveOcorrenciasFromAC(colaboradorOcorrencias);
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
			manager.saveOcorrenciasFromAC(colaboradorOcorrencias);
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
			manager.removeFromAC(colaboradorOcorrencias);
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
			manager.removeFromAC(colaboradorOcorrencias);
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
		assertTrue(manager.verifyExistsMesmaData(1L, 1L, 1L, 1L, new Date(), null));
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
		colaboradorManager.expects(once()).method("findById").will(returnValue(colaborador));
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoOcorrenciaCadastrada");

		Exception exception = null;

		try
		{
			manager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
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
		colaboradorManager.expects(once()).method("findById").will(returnValue(colaborador));
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoOcorrenciaCadastrada");
		
		Exception exception = null;
		
		try
		{
			manager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
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

		colaboradorOcorrenciaDao.expects(once()).method("save");
		colaboradorManager.expects(once()).method("findById").will(returnValue(colaborador));
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoOcorrenciaCadastrada");
		acPessoalClientColaboradorOcorrencia.expects(once()).method("criarColaboradorOcorrencia").will(throwException(new IntegraACException("")));

		Exception exception = null;

		try
		{
			manager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
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
			manager.remove(colaboradorOcorrencia, empresa);
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
			manager.remove(colaboradorOcorrencia, empresa);
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
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(1L);
		colaboradorOcorrencia.setDataIni(new Date());
		setDadosOcorrenciaAC(empresa, ocorrencia, colaborador, colaboradorOcorrencia);

		colaboradorOcorrenciaDao.expects(once()).method("findByIdProjection").will(returnValue(colaboradorOcorrencia));

		acPessoalClientColaboradorOcorrencia.expects(once()).method("removerColaboradorOcorrencia").will(throwException(new IntegraACException("")));

		Exception exception = null;

		try
		{
			manager.remove(colaboradorOcorrencia, empresa);
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
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		
		Collection<Absenteismo> retornoBD = new ArrayList<Absenteismo>();
		retornoBD.add(new Absenteismo("2011", "01", 0));
		retornoBD.add(new Absenteismo("2011", "02", 2));
		retornoBD.add(new Absenteismo("2011", "03", 0));
		retornoBD.add(new Absenteismo("2011", "04", 15));
		retornoBD.add(new Absenteismo("2011", "05", 19));
		
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(retornoBD));
		colaboradorAfastamentoManager.expects(once()).method("countAfastamentosByPeriodo").will(returnValue(retornoBD));
		colaboradorManager.expects(atLeastOnce()).method("countAtivosPeriodo").will(returnValue(10));
		
		Collection<Object[]> absenteismos = manager.montaGraficoAbsenteismo("01/2011", "05/2011", Arrays.asList(empresa.getId()), Arrays.asList(estabelecimento.getId()), null, null, null);
		
		assertEquals(5, absenteismos.size());
		assertEquals(0.0, ((Object[])absenteismos.toArray()[0])[1]);
		assertEquals(2.0, ((Object[])absenteismos.toArray()[1])[1]);
		assertEquals(14.285714285714285, ((Object[])absenteismos.toArray()[3])[1]);
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
		colaboradorAfastamentoManager.expects(once()).method("countAfastamentosByPeriodo").will(returnValue(retornoBD));
		colaboradorManager.expects(atLeastOnce()).method("countAtivosPeriodo").will(returnValue(10));
		
		Date dataIni = DateUtil.montaDataByString("02/01/2011");
		Date dataFim = DateUtil.montaDataByString("19/05/2011");
		
		Collection<Absenteismo> absenteismos = manager.montaAbsenteismo(dataIni, dataFim, Arrays.asList(empresa.getId()), null, null, null, null, null, empresa);
		assertEquals(5, absenteismos.size());
		
		Absenteismo absenteismoJan = (Absenteismo) absenteismos.toArray()[0];
		assertEquals("01", absenteismoJan.getMes());
		assertEquals(new Integer(0), absenteismoJan.getQtdTotalFaltas());
		assertEquals(0.0, absenteismoJan.getAbsenteismo());

		Absenteismo absenteismoFev = (Absenteismo) absenteismos.toArray()[1];
		assertEquals("02", absenteismoFev.getMes());
		assertEquals(new Integer(4), absenteismoFev.getQtdTotalFaltas());
		assertEquals(new Integer(10), absenteismoFev.getQtdAtivos());
		assertEquals(new Integer(20), absenteismoFev.getQtdDiasTrabalhados());
		assertEquals(2.0, absenteismoFev.getAbsenteismo());
		
		Absenteismo absenteismoAbr = (Absenteismo) absenteismos.toArray()[3];
		assertEquals("04", absenteismoAbr.getMes());
		assertEquals(new Integer(30), absenteismoAbr.getQtdTotalFaltas());
		assertEquals(new Integer(10), absenteismoAbr.getQtdAtivos());
		assertEquals(new Integer(21), absenteismoAbr.getQtdDiasTrabalhados());
		assertEquals(14.285714285714285, absenteismoAbr.getAbsenteismo());
	}

	public void testMontaAbsenteismoConsiderandoSabadosEDomingos() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setConsiderarSabadoNoAbsenteismo(true);
		empresa.setConsiderarDomingoNoAbsenteismo(true);
		
		Collection<Absenteismo> retornoBD = new ArrayList<Absenteismo>();
		retornoBD.add(new Absenteismo("2011", "01", 0));
		retornoBD.add(new Absenteismo("2011", "02", 2));
		retornoBD.add(new Absenteismo("2011", "03", 0));
		retornoBD.add(new Absenteismo("2011", "04", 15));
		retornoBD.add(new Absenteismo("2011", "05", 19));
		
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(retornoBD));
		colaboradorAfastamentoManager.expects(once()).method("countAfastamentosByPeriodo").will(returnValue(retornoBD));
		colaboradorManager.expects(atLeastOnce()).method("countAtivosPeriodo").will(returnValue(10));
		
		Date dataIni = DateUtil.montaDataByString("02/01/2011");
		Date dataFim = DateUtil.montaDataByString("19/05/2011");
		
		Collection<Absenteismo> absenteismos = manager.montaAbsenteismo(dataIni, dataFim, Arrays.asList(empresa.getId()), null, null, null, null, null, empresa);
		assertEquals(5, absenteismos.size());
		
		Absenteismo absenteismoJan = (Absenteismo) absenteismos.toArray()[0];
		assertEquals("01", absenteismoJan.getMes());
		assertEquals(new Integer(0), absenteismoJan.getQtdTotalFaltas());
		assertEquals(0.0, absenteismoJan.getAbsenteismo());

		Absenteismo absenteismoFev = (Absenteismo) absenteismos.toArray()[1];
		assertEquals("02", absenteismoFev.getMes());
		assertEquals(new Integer(4), absenteismoFev.getQtdTotalFaltas());
		assertEquals(new Integer(10), absenteismoFev.getQtdAtivos());
		assertEquals(new Integer(28), absenteismoFev.getQtdDiasTrabalhados());
		assertEquals(1.4285714285714286, absenteismoFev.getAbsenteismo());
		
		Absenteismo absenteismoAbr = (Absenteismo) absenteismos.toArray()[3];
		assertEquals("04", absenteismoAbr.getMes());
		assertEquals(new Integer(30), absenteismoAbr.getQtdTotalFaltas());
		assertEquals(new Integer(10), absenteismoAbr.getQtdAtivos());
		assertEquals(new Integer(30), absenteismoAbr.getQtdDiasTrabalhados());
		assertEquals(10.0, absenteismoAbr.getAbsenteismo());
	}
	
	public void testDeleteOcorrencias()
	{
		Long[] ocorrenciaIds = new Long[] {1L, 2L};
		
		colaboradorOcorrenciaDao.expects(once()).method("deleteByOcorrencia").with(eq(ocorrenciaIds)) .isVoid();
		ocorrenciaManager.expects(once()).method("remove").isVoid();
		
		Exception exception = null;

		try
		{
			manager.deleteOcorrencias(ocorrenciaIds);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);

	}
	
	public void testFindColaboraesPermitidosByUsuario() 
	{
		Long[] areasIds = new Long[] {1L, 2L};
		Long empresaId = 2L;
		Usuario usuario = UsuarioFactory.getEntity(2L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		usuarioManager.expects(once()).method("isResponsavelOrCoResponsavel").with(eq(usuario.getId())).will(returnValue(true));
		usuarioEmpresaManager.expects(once()).method("containsRole").with(eq(usuario.getId()), eq(empresaId), eq("ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA")).will(returnValue(true));
		areaOrganizacionalManager.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").with(ANYTHING, ANYTHING).will(returnValue(areasIds));
		
		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(new Constraint[]{eq(null), eq(null), 
				eq(areasIds), eq(null), eq(null), eq(colaborador), eq(null), eq(null), eq(empresaId), eq(false), ANYTHING, eq(null)}).will(returnValue(colaboradores));
		
		assertEquals(colaboradores, manager.findColaboraesPermitidosByUsuario(usuario, colaborador, empresaId, false, true));
	}
	
	public void testBindColaboradorOcorrencias() throws Exception
	{
		TOcorrenciaEmpregado tOcorrenciaEmpregado = new TOcorrenciaEmpregado();
		tOcorrenciaEmpregado.setCodigo("33333");
		tOcorrenciaEmpregado.setEmpresa("11111");
		tOcorrenciaEmpregado.setCodigoEmpregado("22222");
		tOcorrenciaEmpregado.setData("01/01/2000");
		tOcorrenciaEmpregado.setObs("obs");
		tOcorrenciaEmpregado.setGrupoAC("XXX");
		
		TOcorrenciaEmpregado[] tcolaboradorOcorrencias = new TOcorrenciaEmpregado[]{tOcorrenciaEmpregado};
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = manager.bindColaboradorOcorrencias(tcolaboradorOcorrencias);
		assertEquals(1, colaboradorOcorrencias.size());
		
		for (ColaboradorOcorrencia colaboradorOcorrencia : colaboradorOcorrencias) 
		{
			assertEquals("11111", colaboradorOcorrencia.getOcorrencia().getEmpresa().getCodigoAC());
			assertEquals("XXX", colaboradorOcorrencia.getOcorrencia().getEmpresa().getGrupoAC());
			assertEquals("22222", colaboradorOcorrencia.getColaborador().getCodigoAC());
			assertEquals("01/01/2000", DateUtil.formataDate(colaboradorOcorrencia.getDataIni(), "dd/MM/yyyy"));
			assertEquals("01/01/2000", DateUtil.formataDate(colaboradorOcorrencia.getDataFim(), "dd/MM/yyyy"));
			assertEquals("obs", colaboradorOcorrencia.getObservacao());
		}
	}
	
	public void testFindColaboraesPermitidosByUsuarioRestringindoVisualizacaoDoGestor(){
		Long[] areasIds = new Long[] {1L, 2L};
		Long empresaId = 2L;
		Usuario usuario = UsuarioFactory.getEntity(2L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		usuarioManager.expects(once()).method("isResponsavelOrCoResponsavel").with(eq(usuario.getId())).will(returnValue(true));
		usuarioEmpresaManager.expects(once()).method("containsRole").with(eq(usuario.getId()), eq(empresaId), eq("ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA")).will(returnValue(false));
		areaOrganizacionalManager.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").with(ANYTHING, ANYTHING).will(returnValue(areasIds));
		
		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(new Constraint[]{eq(null), eq(null), 
				eq(areasIds), eq(null), eq(null), eq(colaborador), eq(null), eq(null), eq(empresaId), eq(false), ANYTHING, eq(usuario.getId())}).will(returnValue(colaboradores));
		
		assertEquals(colaboradores, manager.findColaboraesPermitidosByUsuario(usuario, colaborador, empresaId, false, true));
	}
	
	public void testMontaAbsenteismoConsiderandoQtdComoZero() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setConsiderarSabadoNoAbsenteismo(true);
		empresa.setConsiderarDomingoNoAbsenteismo(true);
		
		Collection<Absenteismo> retornoBD = new ArrayList<Absenteismo>();
		retornoBD.add(new Absenteismo("2011", "01", 0));
		
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(retornoBD));
		colaboradorAfastamentoManager.expects(once()).method("countAfastamentosByPeriodo").will(returnValue(retornoBD));
		colaboradorManager.expects(atLeastOnce()).method("countAtivosPeriodo").will(returnValue(0));
		
		Date dataIni = DateUtil.montaDataByString("02/01/2011");
		Date dataFim = DateUtil.montaDataByString("19/05/2011");
		
		Collection<Absenteismo> absenteismos = manager.montaAbsenteismo(dataIni, dataFim, Arrays.asList(empresa.getId()), null, null, null, null, null, empresa);
		assertEquals(1, absenteismos.size());
		
		Absenteismo absenteismoJan = (Absenteismo) absenteismos.toArray()[0];
		assertEquals("01", absenteismoJan.getMes());
		assertEquals(new Integer(0), absenteismoJan.getQtdTotalFaltas());
		assertEquals(0.0, absenteismoJan.getAbsenteismo());
	}
	
	public void testMontaAbsenteismoException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		colaboradorOcorrenciaDao.expects(once()).method("countFaltasByPeriodo").will(returnValue(new ArrayList<Absenteismo>()));
		colaboradorAfastamentoManager.expects(once()).method("countAfastamentosByPeriodo").will(returnValue(new ArrayList<Absenteismo>()));
		
		Date dataIni = new Date();
		Date dataFim = dataIni;
		Exception exception = null;
		
		try
		{
			manager.montaAbsenteismo(dataIni, dataFim, Arrays.asList(empresa.getId()), null, null, null, null, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}

	public void testFiltrarOcorrenciasSemRestrigirVisualizacaoGestor(){
		Long usuarioId = 2l;
		usuarioManager.expects(once()).method("isResponsavelOrCoResponsavel").with(eq(usuarioId)).will(returnValue(true));
		usuarioEmpresaManager.expects(once()).method("containsRole").with(eq(usuarioId), eq(null), eq("ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA")).will(returnValue(true));
		colaboradorOcorrenciaDao.expects(once()).method("findColaboradorOcorrencia").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(null)}).will(returnValue(new ArrayList<ColaboradorOcorrencia>()));

		Exception exception = null;
		try {
			manager.filtrarOcorrencias(null, null, null, null, null, null, null, false, 'O', SituacaoColaborador.TODOS, usuarioId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testFiltrarOcorrenciasRestringindoVisualizacaoDoGestor(){
		Long usuarioId = 2l;
		usuarioManager.expects(once()).method("isResponsavelOrCoResponsavel").with(eq(usuarioId)).will(returnValue(true));
		usuarioEmpresaManager.expects(once()).method("containsRole").with(eq(usuarioId), eq(null), eq("ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA")).will(returnValue(false));
		colaboradorOcorrenciaDao.expects(once()).method("findColaboradorOcorrencia").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(usuarioId)}).will(returnValue(new ArrayList<ColaboradorOcorrencia>()));

		Exception exception = null;
		try {
			manager.filtrarOcorrencias(null, null, null, null, null, null, null, false, 'O', SituacaoColaborador.TODOS, usuarioId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
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

	public void testExecutaTesteAutomaticoDoManager() 
	{
		testeAutomatico(colaboradorOcorrenciaDao);
	}
}