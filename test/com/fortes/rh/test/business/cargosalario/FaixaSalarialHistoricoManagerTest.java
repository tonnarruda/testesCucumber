package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.exception.FaixaJaCadastradaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class FaixaSalarialHistoricoManagerTest extends MockObjectTestCase
{
	FaixaSalarialHistoricoManagerImpl faixaSalarialHistoricoManager = null;
	Mock faixaSalarialHistoricoDao;
	Mock faixaSalarialManager;
	Mock indiceHistoricoManager;
	Mock indiceManager;
	Mock transactionManager;
	Mock acPessoalClientCargo = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		faixaSalarialHistoricoManager = new FaixaSalarialHistoricoManagerImpl();

		faixaSalarialHistoricoDao = new Mock(FaixaSalarialHistoricoDao.class);
		faixaSalarialHistoricoManager.setDao((FaixaSalarialHistoricoDao) faixaSalarialHistoricoDao.proxy());

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		MockSpringUtil.mocks.put("faixaSalarialManager", faixaSalarialManager);

		indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);
		faixaSalarialHistoricoManager.setIndiceHistoricoManager((IndiceHistoricoManager) indiceHistoricoManager.proxy());

		transactionManager = new Mock(PlatformTransactionManager.class);
		faixaSalarialHistoricoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

		acPessoalClientCargo = mock(AcPessoalClientCargo.class);
		faixaSalarialHistoricoManager.setAcPessoalClientCargo((AcPessoalClientCargo) acPessoalClientCargo.proxy());

		indiceManager = new Mock(IndiceManager.class);
		faixaSalarialHistoricoManager.setIndiceManager((IndiceManager) indiceManager.proxy());

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testSaveIndice() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC(null);

		Indice indice = IndiceFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setQuantidade(2.0);

		boolean salvaNoAC = true;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		indiceManager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getIndice().getId()));
		faixaSalarialHistoricoDao.expects(once()).method("saveOrUpdate").with(eq(faixaSalarialHistorico));
		faixaSalarialManager.expects(once()).method("findCodigoACById").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
		acPessoalClientCargo.expects(once()).method("criarFaixaSalarialHistorico").with(eq(faixaSalarialHistorico), eq(empresa));
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		faixaSalarialHistoricoManager.save(faixaSalarialHistorico, faixaSalarial, empresa, salvaNoAC);
	}

	public void testSaveValor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC(null);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(2.2);

		boolean salvaNoAC = false;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		faixaSalarialHistoricoDao.expects(once()).method("saveOrUpdate").with(eq(faixaSalarialHistorico));
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		faixaSalarialHistoricoManager.save(faixaSalarialHistorico, faixaSalarial, empresa, salvaNoAC);
	}

	public void testSaveValorException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC(null);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);

		boolean salvaNoAC = true;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		faixaSalarialHistoricoDao.expects(once()).method("saveOrUpdate").with(eq(faixaSalarialHistorico));
		faixaSalarialManager.expects(once()).method("findCodigoACById").with(eq(faixaSalarial.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exception = null;
		try
		{
			faixaSalarialHistoricoManager.save(faixaSalarialHistorico, faixaSalarial, empresa, salvaNoAC);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testUpdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC(null);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		faixaSalarialManager.expects(once()).method("findCodigoACById").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
		faixaSalarialHistoricoDao.expects(once()).method("update").with(eq(faixaSalarialHistorico));
		acPessoalClientCargo.expects(once()).method("criarFaixaSalarialHistorico").with(eq(faixaSalarialHistorico), eq(empresa));
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		faixaSalarialHistoricoManager.update(faixaSalarialHistorico, faixaSalarial, empresa);
	}

	public void testUpdateException()
	{
		Empresa empresa = null;

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC(null);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exception = null;
		try
		{
			faixaSalarialHistoricoManager.update(faixaSalarialHistorico, faixaSalarial, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	// TODO Melhorar teste para verificar se o valor atual do último histórico está correto
	public void testFindAllSelect()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricoAtual(indiceHistorico);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico1.setIndice(indice);
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(2L);
		faixaSalarialHistorico2.setIndice(indice);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico1);
		faixaSalarialHistoricos.add(faixaSalarialHistorico2);

		faixaSalarialHistoricoDao.expects(once()).method("findAllSelect").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarialHistoricos));

		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoManager.findAllSelect(faixaSalarial.getId());

		assertEquals(faixaSalarialHistoricos, retorno);
	}

	public void testFindByIdProjection()
	{
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);

		faixaSalarialHistoricoDao.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));

		assertEquals(faixaSalarialHistorico, faixaSalarialHistoricoManager.findByIdProjection(faixaSalarialHistorico.getId()));
	}

	public void testRemove() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		
		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico2.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);
		faixaSalarialHistoricos.add(faixaSalarialHistorico2);

		faixaSalarialHistoricoDao.expects(atLeastOnce()).method("findHistoricosByFaixaSalarialId").with(eq(faixaSalarial.getId()), eq(StatusRetornoAC.CONFIRMADO)).will(returnValue(faixaSalarialHistoricos));
		faixaSalarialHistoricoDao.expects(atLeastOnce()).method("findByIdProjection").withAnyArguments().will(returnValue(faixaSalarialHistorico2));
		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING);
		acPessoalClientCargo.expects(atLeastOnce()).method("deleteFaixaSalarialHistorico").with(ANYTHING, ANYTHING);
		
		remove(empresa, faixaSalarialHistorico2);
		removeException(empresa, faixaSalarialHistorico2);
		removeExceptionUnicoHistorico(empresa, faixaSalarialHistorico2);
	}
	

	private void remove(Empresa empresa, FaixaSalarialHistorico faixaSalarialHistorico2) throws Exception 
	{
		faixaSalarialHistoricoDao.expects(once()).method("remove").with(eq(faixaSalarialHistorico2.getId()));
		transactionManager.expects(once()).method("commit").withAnyArguments();

		faixaSalarialHistoricoManager.remove(faixaSalarialHistorico2.getId(), empresa, true);
	}

	private void removeException(Empresa empresa, FaixaSalarialHistorico faixaSalarialHistorico2) throws Exception
	{
		faixaSalarialHistoricoDao.expects(once()).method("remove").with(eq(faixaSalarialHistorico2.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(faixaSalarialHistorico2,""))));;
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exception = null;

		try
		{
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico2.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	private void removeExceptionUnicoHistorico(Empresa empresa, FaixaSalarialHistorico faixaSalarialHistorico2) throws Exception 
	{
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico2);
		
		faixaSalarialHistoricoDao.expects(atLeastOnce()).method("findHistoricosByFaixaSalarialId").with(ANYTHING, eq(StatusRetornoAC.CONFIRMADO)).will(returnValue(faixaSalarialHistoricos));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception exception = null;

		try
		{
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico2.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
		assertEquals("Não é permitido deletar o único histórico da faixa salarial confirmado.", exception.getMessage());
	}

	public void testVerifyDataUpdate()
	{
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		Date data = new Date();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		faixaSalarialHistoricoDao.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(data), eq(faixaSalarial.getId())).will(returnValue(true));

		assertEquals(true, faixaSalarialHistoricoManager.verifyData(faixaSalarialHistorico.getId(), data, faixaSalarial.getId()));
	}

	@SuppressWarnings("static-access")
	public void testFindUltimoHistoricoFaixaSalarialPorIndice()
	{
		TipoAplicacaoIndice tipoSalario = new TipoAplicacaoIndice();

		Indice indice = IndiceFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(tipoSalario.getIndice());
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setQuantidade(5.0);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		faixaSalarialHistoricoDao.expects(once()).method("findByFaixaSalarialId").with(ANYTHING).will(returnValue(faixaSalarialHistorico));

		Double valor = 150.00;

		indiceHistoricoManager.expects(once()).method("findUltimoSalarioIndice").with(ANYTHING).will(returnValue(valor));

		assertEquals(750.00, faixaSalarialHistoricoManager.findUltimoHistoricoFaixaSalarial(faixaSalarial.getId()));
	}

	@SuppressWarnings("static-access")
	public void testFindUltimoHistoricoFaixaSalarialPorValor()
	{
		TipoAplicacaoIndice tipoSalario = new TipoAplicacaoIndice();

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(tipoSalario.getValor());
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setValor(150.00);

		faixaSalarialHistoricoDao.expects(once()).method("findByFaixaSalarialId").with(ANYTHING).will(returnValue(faixaSalarialHistorico));

		assertEquals(150.00, faixaSalarialHistoricoManager.findUltimoHistoricoFaixaSalarial(faixaSalarial.getId()));

	}

	public void testFindUltimoHistoricoFaixaSalarialRetornaNulo()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(0);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		faixaSalarialHistoricoDao.expects(once()).method("findByFaixaSalarialId").with(ANYTHING).will(returnValue(faixaSalarialHistorico));

		faixaSalarialHistoricoManager.findUltimoHistoricoFaixaSalarial(faixaSalarial.getId());

	}

	public void testFindByPeriodoPorValor()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		Collection<FaixaSalarialHistorico> faixaSalarials = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarials.add(faixaSalarialHistorico);

		Date data = DateUtil.criarDataMesAno(01, 01, 2008);
		Date dataProxima = DateUtil.criarDataMesAno(01, 06, 2008);

		faixaSalarialHistoricoDao.expects(once()).method("findByPeriodo").with(eq(faixaSalarial.getId()), eq(dataProxima)).will(returnValue(faixaSalarials));

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = faixaSalarialHistoricoManager.findByPeriodo(faixaSalarial.getId(), data, dataProxima);
		assertEquals(1, faixaSalarialHistoricos.size());
	}

	public void testFindByPeriodoPorIndice()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		Date data1 = DateUtil.criarDataMesAno(01, 01, 2008);
		Date data2 = DateUtil.criarDataMesAno(01, 07, 2007);
		Date dataProxima = DateUtil.criarDataMesAno(01, 06, 2008);

		Indice indice = IndiceFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setData(dataProxima);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		Collection<FaixaSalarialHistorico> faixaSalarials = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarials.add(faixaSalarialHistorico);

		IndiceHistorico indiceHistorico1 = new IndiceHistorico();
		indiceHistorico1.setData(data1);

		IndiceHistorico indiceHistorico2 = new IndiceHistorico();
		indiceHistorico2.setData(data2);

		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(indiceHistorico1);
		indiceHistoricos.add(indiceHistorico2);

		faixaSalarialHistoricoDao.expects(once()).method("findByPeriodo").with(eq(faixaSalarial.getId()), eq(dataProxima)).will(returnValue(faixaSalarials));
		indiceHistoricoManager.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(indiceHistoricos));

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = faixaSalarialHistoricoManager.findByPeriodo(faixaSalarial.getId(), data1, dataProxima);
		assertEquals(2, faixaSalarialHistoricos.size());
	}

	public void testFindByPeriodoPorIndiceValor()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		Date data = DateUtil.criarDataMesAno(01, 01, 2008);
		Date dataProxima = DateUtil.criarDataMesAno(01, 06, 2008);

		Indice indice = IndiceFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setData(dataProxima);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico2.setData(dataProxima);
		faixaSalarialHistorico2.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);
		faixaSalarialHistoricos.add(faixaSalarialHistorico2);

		IndiceHistorico indiceHistorico = new IndiceHistorico();
		indiceHistorico.setData(data);

		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(indiceHistorico);

		faixaSalarialHistoricoDao.expects(once()).method("findByPeriodo").with(eq(faixaSalarial.getId()), eq(dataProxima)).will(returnValue(faixaSalarialHistoricos));
		indiceHistoricoManager.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(indiceHistoricos));

		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoManager.findByPeriodo(faixaSalarial.getId(), data, dataProxima);
		assertEquals(3, retorno.size());
	}

	public void testFindByGrupoData() throws Exception
	{
		Calendar data1 = Calendar.getInstance();
		data1.setTime(new Date());

		Calendar data2 = Calendar.getInstance();
		data2.setTime(new Date());
		data2.add(Calendar.DAY_OF_MONTH, 1);

		String[] grupoChecks = new String[]{"1"};
		Collection<Long> ids = new ArrayList<Long>();
		ids.add(1L);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial1);
		faixaSalarialHistorico1.setData(data1.getTime());

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial2);
		faixaSalarialHistorico2.setData(data2.getTime());

		faixaSalarialHistoricos.add(faixaSalarialHistorico1);
		faixaSalarialHistoricos.add(faixaSalarialHistorico2);

		faixaSalarialHistoricoDao.expects(once()).method("findByGrupoCargoAreaData").with(new Constraint[] {eq(ids), eq(new ArrayList<Long>()), eq(new ArrayList<Long>()), eq(data1.getTime()), eq(Boolean.FALSE), eq(1L), eq(null)}).will(returnValue(faixaSalarialHistoricos));

		assertNotNull(faixaSalarialHistoricoManager.findByGrupoCargoAreaData(grupoChecks, null, null, data1.getTime(), Boolean.FALSE, 1L, null));
	}

	public void testFindByGrupoDataException()
	{
		Date data = new Date();
		String[] grupoChecks = new String[]{"1"};
		Collection<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();

		faixaSalarialHistoricoDao.expects(once()).method("findByGrupoCargoAreaData").with(new Constraint[] {eq(ids), eq(new ArrayList<Long>()), eq(new ArrayList<Long>()), eq(data), eq(Boolean.FALSE), eq(1L), eq(null)}).will(returnValue(faixaSalarialHistoricos));

		Exception exception = null;
		try
		{
			faixaSalarialHistoricoManager.findByGrupoCargoAreaData(grupoChecks, null, null, data, Boolean.FALSE, 1L, null);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testVerifyHistoricoIndiceNaData()
	{
		Indice indice = IndiceFactory.getEntity(1L);

		Date date = new Date();

		indiceHistoricoManager.expects(once()).method("existeHistoricoAnteriorOuIgualDaData").with(eq(date),eq(indice.getId())).will(returnValue(true));

		assertTrue(faixaSalarialHistoricoManager.verifyHistoricoIndiceNaData(date, indice.getId()));
	}

	public void testSetStatus()
	{
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);

		faixaSalarialHistoricoDao.expects(once()).method("setStatus").with(eq(faixaSalarialHistorico.getId()),eq(true)).will(returnValue(true));

		assertEquals(true, faixaSalarialHistoricoManager.setStatus(faixaSalarialHistorico.getId(), true));
	}

	public void testFindPendenciasByFaixaSalarialHistorico()
	{
		Cargo cargo = CargoFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setStatus(2);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		faixaSalarialHistoricoDao.expects(once()).method("findPendenciasByFaixaSalarialHistorico").with(eq(1L)).will(returnValue(faixaSalarialHistoricos));

		Collection<PendenciaAC> retorno = faixaSalarialHistoricoManager.findPendenciasByFaixaSalarialHistorico(1L);

		assertEquals(1, retorno.size());
	}

	public void testRemoveByFaixas()
	{
		Long[] faixaSalarialIds = new Long[]{1L};

		faixaSalarialHistoricoDao.expects(once()).method("removeByFaixas").with(eq(faixaSalarialIds));

		faixaSalarialHistoricoManager.removeByFaixas(faixaSalarialIds);
	}
	
	public void testSincronizar()
	{
		Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		faixaSalarialIds.put(99L, 130L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);
		
		// clonar historicos das faixas 
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1000L);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
		
		FaixaSalarialHistorico faixaSalarialHistoricoDepoisDoSave = FaixaSalarialHistoricoFactory.getEntity(2000L);
		faixaSalarialHistoricoDepoisDoSave.setFaixaSalarial(faixaSalarialDestino);
		
		faixaSalarialHistoricoDao.expects(once()).method("findHistoricoAtual").with(eq(99L)).will(returnValue(faixaSalarialHistorico));
		faixaSalarialHistoricoDao.expects(once()).method("save").with(eq(faixaSalarialHistorico)).will(returnValue(faixaSalarialHistoricoDepoisDoSave));
		
		Exception ex = null;
		try {
			faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), EmpresaFactory.getEmpresa());
		} catch (FaixaJaCadastradaException e) {
			ex = e;
			e.printStackTrace();
		}
		
		assertNull(ex);
	}
	
	public void testSincronizarException()
	{
		Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		faixaSalarialIds.put(99L, 130L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);
		
		FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
		
		faixaSalarialHistoricoDao.expects(once()).method("findHistoricoAtual").with(eq(99L)).will(returnValue(null));
		
		Exception ex = null;
		try {
			faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), EmpresaFactory.getEmpresa());
		} catch (FaixaJaCadastradaException e) {
			ex = e;
			e.printStackTrace();
		}
		
		assertNotNull(ex);
	}

	public void testGetsSets()
	{
		faixaSalarialHistoricoManager.setAcPessoalClientCargo(null);
	}
	
	public void testExisteDependenciaComHistoricoIndiceComNenhumHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(new ArrayList<IndiceHistorico>()));
		
		boolean existeDependencia = faixaSalarialHistoricoManager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertFalse(existeDependencia);
	}
	
	public void testExisteDependenciaComHistoricoIndiceComUmHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(new IndiceHistorico());
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(indiceHistoricos));
		faixaSalarialHistoricoDao.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(dataHistoricoExcluir), NULL, eq(indiceId)) .will(returnValue(true));
		
		boolean existeDependencia = faixaSalarialHistoricoManager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertTrue(existeDependencia);
	}

	public void testExisteDependenciaComHistoricoIndiceComVariosHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		IndiceHistorico indiceHistorico2 = new IndiceHistorico();
		indiceHistorico2.setData(new Date());
		
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(new IndiceHistorico());
		indiceHistoricos.add(indiceHistorico2);
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(indiceHistoricos));
		faixaSalarialHistoricoDao.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(dataHistoricoExcluir), eq(indiceHistorico2.getData()), eq(indiceId)) .will(returnValue(true));
		
		boolean existeDependencia = faixaSalarialHistoricoManager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertTrue(existeDependencia);
	}
}