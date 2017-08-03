package com.fortes.rh.test.business.cargosalario;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManagerImpl;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;
import com.fortes.rh.util.DateUtil;

public class FaturamentoMensalManagerTest extends MockObjectTestCase
{
	private FaturamentoMensalManagerImpl faturamentoMensalManager = new FaturamentoMensalManagerImpl();
	private Mock faturamentoMensalDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        faturamentoMensalDao = new Mock(FaturamentoMensalDao.class);
        faturamentoMensalManager.setDao((FaturamentoMensalDao) faturamentoMensalDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<FaturamentoMensal> faturamentoMensals = FaturamentoMensalFactory.getCollection(1L);

		faturamentoMensalDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(faturamentoMensals));
		assertEquals(faturamentoMensals, faturamentoMensalManager.findAllSelect(empresaId));
	}

	public void testFindByPeriodo()
	{
		Date inicio = DateUtil.criarDataMesAno(1, 2, 2000);
		Date fim = DateUtil.criarDataMesAno(1, 11, 2000);
		Long empresaId = 1L;
		
		FaturamentoMensal janeiro = FaturamentoMensalFactory.getEntity(1L);
		janeiro.setMesAno(inicio);
		janeiro.setValor(1000000.0);
		
		Collection<FaturamentoMensal> faturamentoMensals = Arrays.asList(janeiro);
		
		faturamentoMensalDao.expects(once()).method("findByPeriodo").with(eq(inicio), eq(fim),eq(empresaId)).will(returnValue(faturamentoMensals));
		faturamentoMensalDao.expects(once()).method("findAtual").with(eq(inicio),eq(empresaId)).will(returnValue(null));
		assertEquals(10, faturamentoMensalManager.findByPeriodo(inicio, fim, empresaId).size());
	}
}
