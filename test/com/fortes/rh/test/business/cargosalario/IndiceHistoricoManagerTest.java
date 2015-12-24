package com.fortes.rh.test.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class IndiceHistoricoManagerTest extends MockObjectTestCase
{
	IndiceHistoricoManagerImpl indiceHistoricoManager = null;

	Mock indiceHistoricoDao;
	Mock historicoColaboradorManager;
	Mock faixaSalarialHistoricoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		indiceHistoricoManager = new IndiceHistoricoManagerImpl();

		indiceHistoricoDao = new Mock(IndiceHistoricoDao.class);
		indiceHistoricoManager.setDao((IndiceHistoricoDao) indiceHistoricoDao.proxy());

		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		
		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		MockSpringUtil.mocks.put("faixaSalarialHistoricoManager", faixaSalarialHistoricoManager);
		
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testFindAllSelect()
	{
		Indice indice = IndiceFactory.getEntity(1L);

		Collection<IndiceHistorico> indiceHistoricos = IndiceHistoricoFactory.getCollection();

		indiceHistoricoDao.expects(once()).method("findAllSelect").with(eq(indice.getId())).will(returnValue(indiceHistoricos));

		assertEquals(indiceHistoricos, indiceHistoricoManager.findAllSelect(indice.getId()));
	}

	public void testFindByIdProjection()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);

		indiceHistoricoDao.expects(once()).method("findByIdProjection").with(eq(indiceHistorico.getId())).will(returnValue(indiceHistorico));

		assertEquals(indiceHistorico, indiceHistoricoManager.findByIdProjection(indiceHistorico.getId()));
	}

	public void testVerifyDataUpdate()
	{
		Indice indice = IndiceFactory.getEntity(1L);
		Date data = new Date();
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);

		indiceHistoricoDao.expects(once()).method("verifyData").with(eq(indiceHistorico.getId()), eq(data), eq(indice.getId())).will(returnValue(true));

		assertEquals(true, indiceHistoricoManager.verifyData(indiceHistorico.getId(), data, indice.getId()));
	}

	public void testFindUltimoSalarioIndice()
	{
		Indice indice = IndiceFactory.getEntity(1L);

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
		indiceHistorico.setIndice(indice);
		indiceHistorico.setValor(150.00);

		Double valor = indiceHistorico.getValor();

		indiceHistoricoDao.expects(once()).method("findUltimoSalarioIndice").with(ANYTHING).will(returnValue(valor));

		assertEquals(150.00, indiceHistoricoManager.findUltimoSalarioIndice(indice.getId()));
	}

	public void testFindByPeriodo()
	{
		indiceHistoricoDao.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));

		Collection<IndiceHistorico> retorno = indiceHistoricoManager.findByPeriodo(1L, null, null, null);

		assertNull(retorno);
	}

	public void testRemoveOk()
	{
		Date data = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoDao.expects(once()).method("existeHistoricoAnteriorDaData").with(eq(data), eq(indiceId), eq(true)).will(returnValue(true));
		indiceHistoricoDao.expects(once()).method("remove").with(eq(data), eq(indiceId)).will(returnValue(true));
		
		boolean retorno = false;
		try {
			retorno = indiceHistoricoManager.remove(data, indiceId);
		} catch (FortesException e) {
		}
		
		assertTrue(retorno);
	}
	
	public void testRemoveSemDependencia()
	{
		Date data = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoDao.expects(once()).method("existeHistoricoAnteriorDaData").with(eq(data), eq(indiceId), eq(true)).will(returnValue(false));
		historicoColaboradorManager.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(data), eq(indiceId)).will(returnValue(false));
		faixaSalarialHistoricoManager.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(data), eq(indiceId)).will(returnValue(false));
		indiceHistoricoDao.expects(once()).method("remove").with(eq(data), eq(indiceId)).will(returnValue(true));
		
		boolean retorno = false;
		try {
			retorno = indiceHistoricoManager.remove(data, indiceId);
		} catch (FortesException e) {
		}
		
		assertTrue(retorno);
	}
	
	public void testRemoveComDependenciaEmHistoricoColaborador()
	{
		Date data = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoDao.expects(once()).method("existeHistoricoAnteriorDaData").with(eq(data), eq(indiceId), eq(true)).will(returnValue(false));
		historicoColaboradorManager.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(data), eq(indiceId)).will(returnValue(true));
		
		String mensagem = null;
		try {
			indiceHistoricoManager.remove(data, indiceId);
		} catch (FortesException e) {
			mensagem = e.getMessage();
		}
		
		assertEquals(mensagem, "O histórico deste índice não pode ser excluído, pois existe histórico de colaborador no RH que depende deste valor.");
	}
	
	public void testRemoveComDependenciaEmHistoricoFaixaSalarial()
	{
		Date data = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoDao.expects(once()).method("existeHistoricoAnteriorDaData").with(eq(data), eq(indiceId), eq(true)).will(returnValue(false));
		historicoColaboradorManager.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(data), eq(indiceId)).will(returnValue(false));
		faixaSalarialHistoricoManager.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(data), eq(indiceId)).will(returnValue(true));
		
		String mensagem = null;
		try {
			indiceHistoricoManager.remove(data, indiceId);
		} catch (FortesException e) {
			mensagem = e.getMessage();
		}
		
		assertEquals(mensagem, "O histórico deste índice não pode ser excluído, pois existe histórico de faixa salarial no RH que depende deste valor.");
	}
}