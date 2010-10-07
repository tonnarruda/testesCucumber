package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManagerImpl;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

public class EngenheiroResponsavelManagerTest extends MockObjectTestCase
{
	private EngenheiroResponsavelManagerImpl engenheiroResponsavelManager = new EngenheiroResponsavelManagerImpl();
	private Mock engenheiroResponsavelDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        engenheiroResponsavelDao = new Mock(EngenheiroResponsavelDao.class);
        engenheiroResponsavelManager.setDao((EngenheiroResponsavelDao) engenheiroResponsavelDao.proxy());
    }

	public void testFindByIdProjection() throws Exception
	{
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setId(1L);

		engenheiroResponsavelDao.expects(once()).method("findByIdProjection").with(eq(engenheiroResponsavel.getId())).will(returnValue(engenheiroResponsavel));

		assertEquals(engenheiroResponsavel, engenheiroResponsavelManager.findByIdProjection(engenheiroResponsavel.getId()));
	}
	
	public void testGetEngenheirosAteData()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		Calendar depois = Calendar.getInstance();
		depois.add(Calendar.MONTH, +2);
		
		EngenheiroResponsavel engenheiroResponsavel1 = new EngenheiroResponsavel();
		engenheiroResponsavel1.setCrea("123");
		engenheiroResponsavel1.setNome("João Adalberto Jr.");
		engenheiroResponsavel1.setInicio(antes.getTime());
		engenheiroResponsavel1.setFim(depois.getTime());
		
		EngenheiroResponsavel engenheiroResponsavel2 = new EngenheiroResponsavel();
		engenheiroResponsavel2.setCrea("555");
		engenheiroResponsavel2.setNome("Maria Joaquina Silva");
		engenheiroResponsavel2.setInicio(hoje.getTime());
		engenheiroResponsavel2.setFim(null);
		
		EngenheiroResponsavel engenheiroResponsavelFora = new EngenheiroResponsavel();
		engenheiroResponsavelFora.setCrea("555");
		engenheiroResponsavelFora.setNome("Amaral Gomes");
		engenheiroResponsavelFora.setInicio(depois.getTime());
		engenheiroResponsavelFora.setFim(depois.getTime());
		
		Collection<EngenheiroResponsavel> engenheiroResponsavels = new ArrayList<EngenheiroResponsavel>();
		engenheiroResponsavels.add(engenheiroResponsavel1);
		engenheiroResponsavels.add(engenheiroResponsavel2);
		engenheiroResponsavels.add(engenheiroResponsavelFora);
		
		engenheiroResponsavelDao.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(engenheiroResponsavels));
		
		Collection<EngenheiroResponsavel> resultado = engenheiroResponsavelManager.getEngenheirosAteData(1L, hoje.getTime());
		
		assertEquals(2, resultado.size());
		
		EngenheiroResponsavel eng2 = ((EngenheiroResponsavel) resultado.toArray()[1]);
		assertEquals("Deve ter anulado a data FIM, pois ela é futura à data desejada, então fica em aberto", 
				null, eng2.getFim());
	}
}