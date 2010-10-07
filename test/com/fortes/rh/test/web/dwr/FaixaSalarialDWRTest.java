package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.web.dwr.FaixaSalarialDWR;

public class FaixaSalarialDWRTest extends MockObjectTestCase
{
	private FaixaSalarialDWR faixaSalarialDWR;
	private Mock faixaSalarialManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		faixaSalarialDWR = new FaixaSalarialDWR();

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		faixaSalarialDWR.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
	}

	public void testGetFaixas()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		cargo.setFaixaSalarials(new ArrayList<FaixaSalarial>());
		cargo.getFaixaSalarials().add(faixaSalarial);

		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCargo").with(eq(cargo.getId())).will(returnValue(cargo.getFaixaSalarials()));

		assertEquals(2, faixaSalarialDWR.getFaixas(cargo.getId().toString()).size());
	}

}
