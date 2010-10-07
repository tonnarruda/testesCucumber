package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.geral.BeneficioManagerImpl;
import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;

public class BeneficioManagerTest extends MockObjectTestCase
{
	private BeneficioManagerImpl beneficioManager = new BeneficioManagerImpl();
	private Mock beneficioDao = null;
	private Mock transactionManager;
	private Mock historicoBeneficioManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        beneficioDao = new Mock(BeneficioDao.class);
        transactionManager = new Mock(PlatformTransactionManager.class);
        historicoBeneficioManager = new Mock(HistoricoBeneficioManager.class);
        beneficioManager.setDao((BeneficioDao) beneficioDao.proxy());
        beneficioManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
        beneficioManager.setHistoricoBeneficioManager((HistoricoBeneficioManager) historicoBeneficioManager.proxy());
    }

    public void testGetBeneficiosByHistoricoColaborador()
    {
    	HistoricoColaboradorBeneficio hcb = new HistoricoColaboradorBeneficio();
    	hcb.setId(1L);

    	List<Long> beneficioIds = new ArrayList<Long>();
    	beneficioIds.add(1L);
    	beneficioIds.add(2L);
    	beneficioIds.add(3L);

    	beneficioDao.expects(once()).method("getBeneficiosByHistoricoColaborador").with(eq(hcb.getId())).will(returnValue(beneficioIds));

    	Collection<Beneficio> beneficios = beneficioManager.getBeneficiosByHistoricoColaborador(hcb.getId());

    	assertEquals(3, beneficios.size());
    	assertEquals(beneficioIds.get(0), ((Beneficio)beneficios.toArray()[0]).getId());
    	assertEquals(beneficioIds.get(2), ((Beneficio)beneficios.toArray()[2]).getId());

    	beneficioIds.clear();
    	beneficioDao.expects(once()).method("getBeneficiosByHistoricoColaborador").with(eq(hcb.getId())).will(returnValue(beneficioIds));

    	beneficios = beneficioManager.getBeneficiosByHistoricoColaborador(hcb.getId());

    	assertEquals(0, beneficios.size());
    }

    public void testFindBeneficioById()
    {
    	Beneficio beneficio = new Beneficio();
    	beneficio.setId(1L);
    	beneficio.setNome("beneficio");

    	beneficioDao.expects(once()).method("findBeneficioById").with(eq(beneficio.getId())).will(returnValue(beneficio));

    	Beneficio beneficioRetorno = beneficioManager.findBeneficioById(beneficio.getId());

    	assertEquals(beneficio.getId(), beneficioRetorno.getId());
    }

    public void testSaveHistoricoBeneficio() throws Exception
    {
    	HistoricoBeneficio historico = new HistoricoBeneficio();
		historico.setId(1L);

		Beneficio beneficio = new Beneficio();
		beneficio.setId(1L);

		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		beneficioDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(beneficio));
		historicoBeneficioManager.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(historico));

		beneficioManager.saveHistoricoBeneficio(beneficio, historico);

		beneficioManager.setDao(null);

		Exception exp = null;

		try{
			beneficioManager.saveHistoricoBeneficio(beneficio, historico);
		}catch (Exception e) {
			exp = e;
		}

		assertNotNull(exp);

    }
}
