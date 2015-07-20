package com.fortes.rh.test.web.dwr;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.test.factory.sesmt.ComissaoPlanoTrabalhoFactory;
import com.fortes.rh.web.dwr.ComissaoPlanoTrabalhoDWR;

public class ComissaoPlanoTrabalhoDWRTest extends MockObjectTestCase
{
	private ComissaoPlanoTrabalhoDWR comissaoPlanoTrabalhoDWR;
	private Mock comissaoPlanoTrabalhoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		comissaoPlanoTrabalhoDWR = new ComissaoPlanoTrabalhoDWR();

		comissaoPlanoTrabalhoManager = new Mock(ComissaoPlanoTrabalhoManager.class);
		comissaoPlanoTrabalhoDWR.setComissaoPlanoTrabalhoManager((ComissaoPlanoTrabalhoManager) comissaoPlanoTrabalhoManager.proxy());
	}

	public void testPrepareDados()
	{
		Long comissaoPlanoTrabalhoId = 1L;
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
		comissaoPlanoTrabalho.setResponsavel(new Colaborador());
		comissaoPlanoTrabalho.setCorresponsavel(new Colaborador());

		comissaoPlanoTrabalhoManager.expects(once()).method("findByIdProjection").with(eq(comissaoPlanoTrabalhoId)).will(returnValue(comissaoPlanoTrabalho));
		Exception exception = null;
		try
		{
			comissaoPlanoTrabalhoDWR.prepareDados(comissaoPlanoTrabalho.getId());
		}catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
	public void testPrepareDadosException()
	{
		Long comissaoPlanoTrabalhoId = 1L;
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
		comissaoPlanoTrabalho.setResponsavel(new Colaborador());

		comissaoPlanoTrabalhoManager.expects(once()).method("findByIdProjection").with(eq(comissaoPlanoTrabalhoId)).will(returnValue(null));
		Exception exception = null;
		try
		{
			comissaoPlanoTrabalhoDWR.prepareDados(comissaoPlanoTrabalho.getId());
		}catch (Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}
}
