package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.web.dwr.ExtintorDWR;

public class ExtintorDWRTest extends MockObjectTestCase
{
	private ExtintorDWR extintorDWR;
	private Mock extintorManager;

	@Override
	protected void setUp() throws Exception
	{
		extintorDWR = new ExtintorDWR();

		extintorManager = new Mock(ExtintorManager.class);
		extintorDWR.setExtintorManager((ExtintorManager) extintorManager.proxy());
	}

	public void testGetExtintorByEstabelecimento()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		Collection<Extintor> extintors = new ArrayList<Extintor>();
		Extintor extintor = ExtintorFactory.getEntity(32L);
		extintor.setEstabelecimento(estabelecimento);
		extintors.add(extintor);

		extintorManager.expects(once()).method("findByEstabelecimento").will(returnValue(extintors));

		assertNotNull(extintorDWR.getExtintorByEstabelecimento("1", "Selecione..."));
		assertEquals(1, extintorDWR.getExtintorByEstabelecimento("", "").size());
	}
}
