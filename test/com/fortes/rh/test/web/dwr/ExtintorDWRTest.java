package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.HistoricoExtintorFactory;
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
		extintors.add(extintor);

		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setEstabelecimento(estabelecimento);
		historico.setExtintor(extintor);
		
		extintorManager.expects(once()).method("findByEstabelecimento").with(eq(estabelecimento.getId()), eq(true)).will(returnValue(extintors));

		assertNotNull(extintorDWR.getExtintorByEstabelecimento("1", "Selecione..."));
		
		extintorManager.expects(once()).method("findByEstabelecimento").with(eq(null), eq(true)).will(returnValue(extintors));
		assertEquals(2, extintorDWR.getExtintorByEstabelecimento("", "").size());
	}
}
