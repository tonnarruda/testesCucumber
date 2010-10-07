package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.RiscoAmbienteDWR;
import com.ibm.icu.text.SimpleDateFormat;

public class RiscoAmbienteDWRTest extends MockObjectTestCase
{
	private RiscoAmbienteDWR riscoAmbienteDWR;
	private Mock riscoAmbienteManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		riscoAmbienteDWR = new RiscoAmbienteDWR();

		riscoAmbienteManager = new Mock(RiscoAmbienteManager.class);
		riscoAmbienteDWR.setRiscoAmbienteManager((RiscoAmbienteManager) riscoAmbienteManager.proxy());
	}
	
	public void testGetRiscosByAmbienteData() throws Exception
	{
		Collection<Risco> riscos = new ArrayList<Risco>();
		Risco risco = RiscoFactory.getEntity(50L);
		riscos.add(risco);
		
		Date data = DateUtil.criarDataMesAno(27, 01, 2010);
		Long ambienteId = 1L;
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData").with(eq(ambienteId), eq(data)).will(returnValue(riscos));
		
		Map<Object, Object> resultado = riscoAmbienteDWR.getRiscosByAmbienteData(ambienteId, new SimpleDateFormat().format(data));
		
		assertEquals(1,resultado.size());
	}
	
	public void testGetRiscosByAmbienteDataInvalida()
	{
		Exception ex = null;
		try
		{
			riscoAmbienteDWR.getRiscosByAmbienteData(1L, "31/31/2000");
		} 
		catch (Exception e) {
			ex=e;
		}
		
		assertNotNull(ex);
	}
}
