package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.HistoricoBeneficioManagerImpl;
import com.fortes.rh.dao.geral.HistoricoBeneficioDao;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.test.factory.geral.HistoricoBeneficioFactory;

public class HistoricoBeneficioManagerTest extends MockObjectTestCase
{
	HistoricoBeneficioManagerImpl historicoBeneficioManager = null;
	Mock historicoBeneficioDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		historicoBeneficioManager = new HistoricoBeneficioManagerImpl();

		historicoBeneficioDao = new Mock(HistoricoBeneficioDao.class);
		historicoBeneficioManager.setDao((HistoricoBeneficioDao) historicoBeneficioDao.proxy());
	}

	public void testFindByHistoricoId()
	{
		HistoricoBeneficio historico = HistoricoBeneficioFactory.getEntity();
		historico.setId(1L);

		historicoBeneficioDao.expects(once()).method("findByHistoricoId").with(eq(historico.getId())).will(returnValue(historico));

		HistoricoBeneficio historicoBeneficio = historicoBeneficioManager.findByHistoricoId(historico.getId());

		assertEquals(historico.getId(), historicoBeneficio.getId());
	}

	public void testGetHistoricosPeriodo()
	{
		Collection<HistoricoBeneficio> historicoBeneficios = new ArrayList<HistoricoBeneficio>();

		historicoBeneficioDao.expects(once()).method("getHistoricos").will(returnValue(historicoBeneficios));

		Collection<HistoricoBeneficio> retorno = historicoBeneficioManager.getHistoricos();

		assertEquals(historicoBeneficios.size(), retorno.size());

	}
}
