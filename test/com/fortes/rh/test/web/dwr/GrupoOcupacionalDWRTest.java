package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.web.dwr.GrupoOcupacionalDWR;

public class GrupoOcupacionalDWRTest extends MockObjectTestCase
{
	private GrupoOcupacionalDWR grupoOcupacionalDWR;
	private Mock grupoOcupacionalManager;

	protected void setUp() throws Exception
	{
		grupoOcupacionalDWR = new GrupoOcupacionalDWR();

		grupoOcupacionalManager = new Mock(GrupoOcupacionalManager.class);
		grupoOcupacionalDWR.setGrupoOcupacionalManager((GrupoOcupacionalManager) grupoOcupacionalManager.proxy());
	}

	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		grupoOcupacionalManager.expects(once()).method("findAllSelect").with(eq(empresaId )).will(returnValue(new ArrayList<GrupoOcupacional>()));
		
		assertEquals(0, grupoOcupacionalDWR.getByEmpresa(empresaId).size());
	}
	
	public void testGetByEmpresaTodas()
	{
		Long empresaId = -1L;
		Collection<GrupoOcupacional> grupoOcupacionals = GrupoOcupacionalFactory.getCollection();
		
		grupoOcupacionalManager.expects(once()).method("findAll").will(returnValue(grupoOcupacionals));
		
		assertEquals(1, grupoOcupacionalDWR.getByEmpresa(empresaId).size());
	}
}
