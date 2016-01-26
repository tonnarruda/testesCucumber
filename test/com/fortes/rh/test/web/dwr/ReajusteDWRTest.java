package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.dwr.ReajusteDWR;
import com.fortes.web.tags.Option;

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

		
		tabelaReajusteColaboradorManager = new Mock(TabelaReajusteColaboradorManager.class);
		reajusteDWR.setTabelaReajusteColaboradorManager((TabelaReajusteColaboradorManager) tabelaReajusteColaboradorManager.proxy());

		reajusteColaboradorManager = new Mock(ReajusteColaboradorManager.class);
		reajusteDWR.setReajusteColaboradorManager((ReajusteColaboradorManager) reajusteColaboradorManager.proxy());
		
		colaboradorManager = new Mock(ColaboradorManager.class);
		reajusteDWR.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
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
			reajusteDWR.verificaColaborador(tabelaReajusteColaborador.getId(), colaborador.getId(), true);
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
			reajusteDWR.verificaColaborador(tabelaReajusteColaborador.getId(), colaborador.getId(), false);
		}
		catch (Exception e)
		{
			exeption = e;
		}

		assertNotNull(exeption);
	}
	
	public void testFindRealinhamentosByPeriodo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		String dataIni = "1/1/2016";
		String dataFim = "31/1/2016";
		
		TabelaReajusteColaborador tabelaReajusteColaborador1 = TabelaReajusteColaboradorFactory.getEntity(1L);
		TabelaReajusteColaborador tabelaReajusteColaborador2 = TabelaReajusteColaboradorFactory.getEntity(2L);
		
		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradores = new ArrayList<TabelaReajusteColaborador>();
		tabelaReajusteColaboradores.add(tabelaReajusteColaborador1);
		tabelaReajusteColaboradores.add(tabelaReajusteColaborador2);
				
		tabelaReajusteColaboradorManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()),ANYTHING,ANYTHING).will(returnValue(tabelaReajusteColaboradores));
		
		Collection<Option> retorno = reajusteDWR.findRealinhamentosByPeriodo(empresa.getId(), dataIni, dataFim);
		
		assertEquals(2, retorno.size());
	}
}
