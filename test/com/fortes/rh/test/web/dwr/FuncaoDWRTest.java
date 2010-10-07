package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.web.dwr.FuncaoDWR;

public class FuncaoDWRTest extends MockObjectTestCase
{
	private FuncaoDWR funcaoDWR;
	private Mock funcaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		funcaoDWR = new FuncaoDWR();

		funcaoManager = new Mock(FuncaoManager.class);
		funcaoDWR.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
	}

	public void testGetFuncaoByFaixaSalarial()
	{
		Cargo cargo = CargoFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);

		Funcao funcao = FuncaoFactory.getEntity(1L);
		funcao.setCargo(cargo);

		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		funcaos.add(funcao);

		funcaoManager.expects(once()).method("findFuncaoByFaixa").with(ANYTHING).will(returnValue(funcaos));

		Map retorno = funcaoDWR.getFuncaoByFaixaSalarial(faixaSalarial.getId());

		assertEquals(2, retorno.size());
	}
}
