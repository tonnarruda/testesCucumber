package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.business.geral.HistoricoColaboradorBeneficioManagerImpl;
import com.fortes.rh.dao.geral.HistoricoColaboradorBeneficioDao;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.factory.geral.HistoricoBeneficioFactory;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HistoricoColaboradorBeneficioManagerTest extends MockObjectTestCase
{
	private HistoricoColaboradorBeneficioManagerImpl manager = new HistoricoColaboradorBeneficioManagerImpl();
	private Mock historicoDao;
	private Mock historicoBeneficioManager;
	private Mock transactionManager = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        historicoDao = new Mock(HistoricoColaboradorBeneficioDao.class);
        historicoBeneficioManager = new Mock(HistoricoBeneficioManager.class);
        transactionManager = new Mock(PlatformTransactionManager.class);
        manager.setDao((HistoricoColaboradorBeneficioDao) historicoDao.proxy());
        manager.setHistoricoBeneficioManager((HistoricoBeneficioManager) historicoBeneficioManager.proxy());
        manager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }

	public void testFiltroRelatorioByColaborador() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("colaborador", colaborador);
		parametros.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2008));
		parametros.put("dataFim", DateUtil.criarDataMesAno(01, 04, 2008));

		Object[] b1 = new Object[7];
		b1[0] = "A1";
		b1[1] = "Beneficio1";
		b1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b1[3] = null;
		b1[4] = colaborador.getId();
		b1[5] = null;
		b1[6] = 220D;

		Object[] b2 = new Object[7];
		b2[0] = "A1";
		b2[1] = "Beneficio2";
		b2[2] = DateUtil.criarDataMesAno(01, 03, 2008);
		b2[3] = null;
		b2[4] = colaborador.getId();
		b2[5] = null;
		b2[6] = 100D;

		List listaBeneficios = new LinkedList();
		listaBeneficios.add(b1);
		listaBeneficios.add(b2);

		historicoBeneficioManager.expects(once()).method("getHistoricos").will(returnValue(new ArrayList<HistoricoBeneficio>()));
		historicoDao.expects(once()).method("filtroRelatorioByColaborador").with(eq(parametros)).will(returnValue(listaBeneficios));
		List historicosRetorno = manager.filtroRelatorioByColaborador(parametros);

		assertEquals(3, historicosRetorno.size());

	}

	public void testFiltroRelatorioByAreas() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		Colaborador colaborador2 = new Colaborador();
		colaborador2.setId(2L);
		colaborador2.setDataDesligamento(DateUtil.criarDataMesAno(15, 03, 2008));

		Colaborador colaborador3 = new Colaborador();
		colaborador3.setId(3L);
		colaborador3.setDataDesligamento(DateUtil.criarDataMesAno(15, 03, 2008));

		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("colaborador", null);
		parametros.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2008));
		parametros.put("dataFim", DateUtil.criarDataMesAno(01, 04, 2008));

		Object[] b0 = new Object[7];
		b0[0] = "A1";
		b0[1] = "Beneficio1";
		b0[2] = DateUtil.criarDataMesAno(01, 01, 2008);
		b0[3] = DateUtil.criarDataMesAno(01, 03, 2008);
		b0[4] = colaborador.getId();
		b0[5] = null;
		b0[6] = 220D;

		Object[] b1 = new Object[7];
		b1[0] = "A1";
		b1[1] = "Beneficio1";
		b1[2] = DateUtil.criarDataMesAno(01, 03, 2008);
		b1[3] = null;
		b1[4] = colaborador.getId();
		b1[5] = null;
		b1[6] = 220D;

		Object[] b2 = new Object[7];
		b2[0] = "A1";
		b2[1] = "Beneficio2";
		b2[2] = DateUtil.criarDataMesAno(01, 03, 2008);
		b2[3] = null;
		b2[4] = colaborador.getId();
		b2[5] = null;
		b2[6] = 100D;

		Object[] b3 = new Object[7];
		b3[0] = "A2";
		b3[1] = "Beneficio3";
		b3[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b3[3] = null;
		b3[4] = colaborador3.getId();
		b3[5] = colaborador3.getDataDesligamento();
		b3[6] = 100D;

		Object[] b4 = new Object[7];
		b4[0] = "A3";
		b4[1] = "Beneficio3";
		b4[2] = DateUtil.criarDataMesAno(01, 01, 2008);
		b4[3] = null;
		b4[4] = colaborador2.getId();
		b4[5] = colaborador2.getDataDesligamento();
		b4[6] = 220D;

		Object[] b5 = new Object[7];
		b5[0] = "A3";
		b5[1] = "Beneficio5";
		b5[2] = DateUtil.criarDataMesAno(01, 01, 2006);
		b5[3] = null;
		b5[4] = colaborador2.getId();
		b5[5] = colaborador2.getDataDesligamento();
		b5[6] = 220D;

		List listaBeneficios = new LinkedList();
		listaBeneficios.add(b0);
		listaBeneficios.add(b1);
		listaBeneficios.add(b2);
		listaBeneficios.add(b3);
		listaBeneficios.add(b4);
		listaBeneficios.add(b5);

		historicoBeneficioManager.expects(once()).method("getHistoricos").will(returnValue(new ArrayList<HistoricoBeneficio>()));
		historicoDao.expects(once()).method("filtroRelatorioByAreasEstabelecimentos").with(eq(parametros)).will(returnValue(listaBeneficios));
		List historicosRetorno = manager.filtroRelatorioByAreas(parametros);

		assertEquals(12, historicosRetorno.size());

	}

	public void testGetHistoricoByColaboradorData() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hc = new HistoricoColaboradorBeneficio();
		hc.setId(1L);
		hc.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc.setColaborador(colaborador);

		historicoDao.expects(once()).method("getHistoricoByColaboradorData").with(ANYTHING,ANYTHING).will(returnValue(hc));

		HistoricoColaboradorBeneficio hcbRetorno = manager.getHistoricoByColaboradorData(colaborador.getId(),hc.getData());

		assertEquals(hc.getId(), hcbRetorno.getId());
	}

	public void testFiltraBeneficioByPeriodo()
	{
		Object[] b1 = new Object[7];
		b1[0] = "A1";
		b1[1] = "Beneficio1";
		b1[2] = DateUtil.criarDataMesAno(01, 01, 2009);

		Object[] b2 = new Object[7];
		b2[0] = "A1";
		b2[1] = "Beneficio2";
		b2[2] = DateUtil.criarDataMesAno(01, 01, 2008);

		Object[] b3 = new Object[7];
		b3[0] = "A2";
		b3[1] = "Beneficio3";
		b3[2] = DateUtil.criarDataMesAno(01, 01, 2007);

		List listaBeneficios = new LinkedList();
		listaBeneficios.add(b1);
		listaBeneficios.add(b2);
		listaBeneficios.add(b3);

		List retorno = manager.filtraBeneficioByPeriodo(listaBeneficios, DateUtil.criarDataMesAno(01, 01, 2008), DateUtil.criarDataMesAno(01, 06, 2008));

		assertEquals(1, retorno.size());
		assertEquals(b2, retorno.toArray()[0]);
	}

	public void testClonarEntrePeriodos()
	{
		Object[] beneficio = new Object[7];
		beneficio[0] = "A1";
		beneficio[1] = "Beneficio1";
		beneficio[2] = DateUtil.criarDataMesAno(01, 01, 2008);

		List retorno = new ArrayList();
		Date dataFim = DateUtil.criarDataMesAno(01, 06, 2008);

		manager.clonarEntrePeriodos(beneficio, retorno, dataFim);

		assertEquals(5, retorno.size());
	}

	public void testClonarEntrePeriodosComDataAte()
	{
		Object[] beneficio = new Object[7];
		beneficio[0] = "A1";
		beneficio[1] = "Beneficio1";
		beneficio[2] = DateUtil.criarDataMesAno(01, 01, 2008);
		beneficio[3] = DateUtil.criarDataMesAno(01, 04, 2008);

		List retorno = new ArrayList();

		manager.clonarEntrePeriodos(beneficio, retorno, null);

		assertEquals(3, retorno.size());
	}

	public void testSetHistoricosEntrePeriodos()
	{
		Object[] b1 = new Object[7];
		b1[0] = "A1";
		b1[1] = "Beneficio1";
		b1[2] = DateUtil.criarDataMesAno(01, 01, 2008);
		b1[3] = DateUtil.criarDataMesAno(01, 05, 2008);

		Object[] b2 = new Object[7];
		b2[0] = "A1";
		b2[1] = "Beneficio2";
		b2[2] = DateUtil.criarDataMesAno(01, 03, 2008);
		b2[3] = null;

		List listaBeneficios = new LinkedList();
		listaBeneficios.add(b1);
		listaBeneficios.add(b2);

		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("dataFim", DateUtil.criarDataMesAno(01, 06, 2008));
		parametros.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2008));

		List retorno = manager.setHistoricosEntrePeriodos(listaBeneficios, parametros);

		assertEquals(7, retorno.size());
	}

	public void testGetValorBeneficio()
	{
		Beneficio beneficio = BeneficioFactory.getEntity();
		beneficio.setNome("B1");
		Beneficio beneficio2 = BeneficioFactory.getEntity();
		beneficio2.setNome("B2");

		HistoricoBeneficio hb1 = HistoricoBeneficioFactory.getEntity();
		hb1.setBeneficio(beneficio);
		hb1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hb1.setValor(100D);

		HistoricoBeneficio hb2 = HistoricoBeneficioFactory.getEntity();
		hb2.setBeneficio(beneficio);
		hb2.setData(DateUtil.criarDataMesAno(01, 04, 2008));
		hb2.setValor(50D);

		HistoricoBeneficio hb3 = HistoricoBeneficioFactory.getEntity();
		hb3.setBeneficio(beneficio);
		hb3.setData(DateUtil.criarDataMesAno(01, 06, 2008));
		hb3.setValor(80D);

		HistoricoBeneficio hb4 = HistoricoBeneficioFactory.getEntity();
		hb4.setBeneficio(beneficio2);
		hb4.setData(DateUtil.criarDataMesAno(01, 01, 2008));

		Collection<HistoricoBeneficio> historicoBeneficios = new ArrayList<HistoricoBeneficio>();
		historicoBeneficios.add(hb1);
		historicoBeneficios.add(hb2);
		historicoBeneficios.add(hb3);

		manager.setHistoricoBeneficios(historicoBeneficios);

		Double valor = manager.getValorBeneficio("B1", DateUtil.criarDataMesAno(01, 05, 2008));

		assertEquals(25D, valor);
	}

	public void testGetUltimoHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
		hcb.setId(1L);
		hcb.setColaborador(colaborador);

		historicoDao.expects(once()).method("getUltimoHistorico").with(eq(colaborador.getId())).will(returnValue(hcb));

		HistoricoColaboradorBeneficio retorno = manager.getUltimoHistorico(colaborador.getId());

		assertEquals(hcb.getId(), retorno.getId());
	}

	public void testUpdateDataAteUltimoHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
		hcb.setId(1L);
		hcb.setColaborador(colaborador);

		Date dataAte = new Date();


		Exception exception = null;

		try
		{
			historicoDao.expects(once()).method("updateDataAteUltimoHistorico").with(eq(hcb.getId()),eq(dataAte));
			manager.updateDataAteUltimoHistorico(hcb.getId(),dataAte);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);

		try
		{
			historicoDao.expects(once()).method("updateDataAteUltimoHistorico").with(eq(hcb.getId()),eq(dataAte)).will(throwException(exception));
			manager.updateDataAteUltimoHistorico(hcb.getId(),dataAte);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSaveHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
		hcb.setId(1L);
		hcb.setColaborador(colaborador);

		HistoricoColaboradorBeneficio hcb2 = new HistoricoColaboradorBeneficio();
		hcb2.setId(2L);
		hcb2.setData(new Date());
		hcb2.setColaborador(colaborador);

		Exception exception = null;

		try
		{
			historicoDao.expects(once()).method("getUltimoHistorico").with(eq(colaborador.getId())).will(returnValue(hcb));
			historicoDao.expects(once()).method("updateDataAteUltimoHistorico").with(eq(hcb.getId()),eq(hcb2.getData()));
			historicoDao.expects(once()).method("save").with(eq(hcb2)).will(returnValue(hcb2));
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
			transactionManager.expects(once()).method("commit").with(ANYTHING);
			manager.saveHistorico(hcb2);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveHistoricoCatch()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
		hcb.setId(1L);
		hcb.setColaborador(colaborador);

		HistoricoColaboradorBeneficio hcb2 = new HistoricoColaboradorBeneficio();
		hcb2.setId(2L);
		hcb2.setData(new Date());
		hcb2.setColaborador(colaborador);

		Exception exception = null;

		try
		{
			historicoDao.expects(once()).method("getUltimoHistorico").with(eq(colaborador.getId())).will(returnValue(hcb));
			historicoDao.expects(once()).method("updateDataAteUltimoHistorico").with(eq(hcb.getId()),eq(hcb2.getData()));
			historicoDao.expects(once()).method("save").with(eq(hcb2)).will(returnValue(null));
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
			transactionManager.expects(once()).method("rollback").with(ANYTHING);
			manager.saveHistorico(hcb2);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testDeleteHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
		hcb.setId(1L);
		hcb.setColaborador(colaborador);

		HistoricoColaboradorBeneficio hcb2 = new HistoricoColaboradorBeneficio();
		hcb2.setId(2L);
		hcb2.setData(new Date());
		hcb2.setColaborador(colaborador);

		Exception exception = null;

		try
		{
			historicoDao.expects(once()).method("getUltimoHistorico").with(eq(colaborador.getId())).will(returnValue(hcb));
			historicoDao.expects(once()).method("updateDataAteUltimoHistorico").with(eq(hcb.getId()),eq(null));
			historicoDao.expects(once()).method("remove").with(eq(hcb2));
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
			transactionManager.expects(once()).method("commit").with(ANYTHING);
			manager.deleteHistorico(hcb2);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testDeleteHistoricoCatch()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
		hcb.setId(1L);
		hcb.setColaborador(colaborador);

		HistoricoColaboradorBeneficio hcb2 = new HistoricoColaboradorBeneficio();
		hcb2.setId(2L);
		hcb2.setData(new Date());
		hcb2.setColaborador(colaborador);

		Exception exception = null;

		try
		{
			historicoDao.expects(once()).method("remove").with(eq(hcb2));
			historicoDao.expects(once()).method("getUltimoHistorico").with(eq(colaborador.getId())).will(returnValue(hcb));
			historicoDao.expects(once()).method("updateDataAteUltimoHistorico").with(eq(hcb.getId()),eq(null)).will(throwException(exception));
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
			transactionManager.expects(once()).method("rollback").with(ANYTHING);
			manager.deleteHistorico(hcb2);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
}
