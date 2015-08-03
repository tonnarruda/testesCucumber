package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.web.dwr.AmbienteDWR;

public class AmbienteDWRTest extends MockObjectTestCase
{
	private AmbienteDWR ambienteDWR;
	private Mock ambienteManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		ambienteDWR = new AmbienteDWR();

		ambienteManager = new Mock(AmbienteManager.class);
		ambienteDWR.setAmbienteManager((AmbienteManager) ambienteManager.proxy());
	}
	
	public void testGetAmbienteByEstabelecimento()
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		Ambiente ambiente = AmbienteFactory.getEntity(50L);
		ambiente.setNome("Teste");
		ambientes.add(ambiente);
		
		Long estabelecimentoId = 2L;
		
		ambienteManager.expects(once()).method("findByEstabelecimento").with(eq(new Long[]{estabelecimentoId})).will(returnValue(ambientes));
		
		Map<Object, Object> resultado = ambienteDWR.getAmbienteByEstabelecimento(estabelecimentoId); 
		
		assertEquals(ambiente.getNome(), resultado.get(ambiente.getId()));
	}
}
