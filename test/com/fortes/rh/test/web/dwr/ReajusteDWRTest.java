package com.fortes.rh.test.web.dwr;

import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.dwr.ReajusteDWR;

public class ReajusteDWRTest extends MockObjectTestCase
{
	private ReajusteDWR reajusteDWR;
	private Mock reajusteColaboradorManager;
	private Mock tabelaReajusteColaboradorManager;
	private Mock colaboradorManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		reajusteDWR = new ReajusteDWR();

		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);

		reajusteColaboradorManager = new Mock(ReajusteColaboradorManager.class);
		reajusteColaboradorManager = new Mock(TabelaReajusteColaboradorManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);

		Map<String, Mock> mocks = new HashMap<String, Mock>();
        mocks.put("reajusteColaboradorManager", reajusteColaboradorManager);
        mocks.put("tabelaReajusteColaboradorManager", tabelaReajusteColaboradorManager);
        mocks.put("colaboradorManager", colaboradorManager);

        MockSpringUtil.mocks = mocks;
	}

	public void testVerificaColaboradorValidaTabela()
	{
		Exception exeption = null;
		try
		{
			reajusteDWR.verificaColaborador(-1L, null, true);
		}
		catch (Exception e)
		{
			exeption = e;
		}

		assertNotNull(exeption);
	}
	
	public void testCalculaSalarioHistorico() throws Exception
	{
		assertEquals("200,00", reajusteDWR.calculaSalarioHistorico("3", "1", "", "", "200,00", "18/03/2010"));
	}

	public void testVerificaColaboradorValidaColaborador()
	{
		Exception exeption = null;
		try
		{
			reajusteDWR.verificaColaborador(2L, -1L, true);
		}
		catch (Exception e)
		{
			exeption = e;
		}

		assertNotNull(exeption);
	}

	public void testVerificaColaboradorValidaColaboradorCodigoACNull()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Exception exeption = null;
		try
		{
			tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(tabelaReajusteColaborador));
			colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
			reajusteDWR.verificaColaborador(2L, 3L, true);
		}
		catch (Exception e)
		{
			exeption = e;
		}

		assertNotNull(exeption);
	}

	public void testVerificaColaboradorValidaColaboradorExistenteNaTabela()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Exception exeption = null;
		try
		{
			tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(tabelaReajusteColaborador));
			colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
			reajusteDWR.verificaColaborador(2L, 3L, false);
		}
		catch (Exception e)
		{
			exeption = e;
		}

		assertNotNull(exeption);
	}
}
