package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.PcmatManagerImpl;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class PcmatManagerTest extends MockObjectTestCase
{
	private PcmatManagerImpl pcmatManager = new PcmatManagerImpl();
	private Mock pcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        
        pcmatDao = new Mock(PcmatDao.class);
        pcmatManager.setDao((PcmatDao) pcmatDao.proxy());
    }

	public void testFindByObra()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		Obra obra = ObraFactory.getEntity();
		obra.setEmpresa(empresa);
		
		Pcmat pcmat = PcmatFactory.getEntity();
		
		Collection<Pcmat> pcmats = new ArrayList<Pcmat>();
		pcmats.add(pcmat);

		pcmatDao.expects(once()).method("findByObra").with(eq(obra.getId())).will(returnValue(pcmats));
		
		assertEquals(pcmats, pcmatManager.findByObra(obra.getId()));
	}
}
