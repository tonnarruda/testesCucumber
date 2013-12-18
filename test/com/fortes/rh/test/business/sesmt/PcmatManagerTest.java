package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AreaVivenciaPcmatManager;
import com.fortes.rh.business.sesmt.AtividadeSegurancaPcmatManager;
import com.fortes.rh.business.sesmt.EpcPcmatManager;
import com.fortes.rh.business.sesmt.EpiPcmatManager;
import com.fortes.rh.business.sesmt.FasePcmatManager;
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
	private Mock fasePcmatManager;
	private Mock areaVivenciaPcmatManager;
	private Mock atividadeSegurancaPcmatManager;
	private Mock epiPcmatManager;
	private Mock epcPcmatManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        
        pcmatDao = new Mock(PcmatDao.class);
        pcmatManager.setDao((PcmatDao) pcmatDao.proxy());
        
        fasePcmatManager = new Mock(FasePcmatManager.class);
        pcmatManager.setFasePcmatManager((FasePcmatManager) fasePcmatManager.proxy());
        
        areaVivenciaPcmatManager = new Mock(AreaVivenciaPcmatManager.class);
        pcmatManager.setAreaVivenciaPcmatManager((AreaVivenciaPcmatManager) areaVivenciaPcmatManager.proxy());
        
        atividadeSegurancaPcmatManager = new Mock(AtividadeSegurancaPcmatManager.class);
        pcmatManager.setAtividadeSegurancaPcmatManager((AtividadeSegurancaPcmatManager) atividadeSegurancaPcmatManager.proxy());
        
        epiPcmatManager = new Mock(EpiPcmatManager.class);
        pcmatManager.setEpiPcmatManager((EpiPcmatManager) epiPcmatManager.proxy());
        
        epcPcmatManager = new Mock(EpcPcmatManager.class);
        pcmatManager.setEpcPcmatManager((EpcPcmatManager) epcPcmatManager.proxy());
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
	
	public void testClonar()
	{
		Long obraId = 1L;
		
		Pcmat pcmatOrigem = PcmatFactory.getEntity(1L);
		Pcmat pcmatDestino = PcmatFactory.getEntity(2L);
		
		pcmatDao.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(pcmatOrigem.getId())).will(returnValue(pcmatOrigem));
		pcmatDao.expects(once()).method("save").will(returnValue(pcmatDestino));
		fasePcmatManager.expects(once()).method("clonar").with(eq(pcmatOrigem.getId()), ANYTHING).isVoid();
		areaVivenciaPcmatManager.expects(once()).method("clonar").with(eq(pcmatOrigem.getId()), ANYTHING).isVoid();
		atividadeSegurancaPcmatManager.expects(once()).method("clonar").with(eq(pcmatOrigem.getId()), ANYTHING).isVoid();
		epiPcmatManager.expects(once()).method("clonar").with(eq(pcmatOrigem.getId()), ANYTHING).isVoid();
		epcPcmatManager.expects(once()).method("clonar").with(eq(pcmatOrigem.getId()), ANYTHING).isVoid();

		Exception ex = null;
		try {
			
			pcmatManager.clonar(pcmatOrigem.getId(), new Date(), obraId);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
}
