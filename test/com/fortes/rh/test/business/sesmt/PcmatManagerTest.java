package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

import com.fortes.rh.business.sesmt.AreaVivenciaPcmatManager;
import com.fortes.rh.business.sesmt.AtividadeSegurancaPcmatManager;
import com.fortes.rh.business.sesmt.EpcPcmatManager;
import com.fortes.rh.business.sesmt.EpiPcmatManager;
import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.business.sesmt.PcmatManagerImpl;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.util.DateUtil;

public class PcmatManagerTest
{
	private PcmatManagerImpl pcmatManager = new PcmatManagerImpl();
	private PcmatDao pcmatDao;
	private FasePcmatManager fasePcmatManager;
	private AreaVivenciaPcmatManager areaVivenciaPcmatManager;
	private AtividadeSegurancaPcmatManager atividadeSegurancaPcmatManager;
	private EpiPcmatManager epiPcmatManager;
	private EpcPcmatManager epcPcmatManager;
	
	@Before
	public void setUp() throws Exception
    {
        pcmatDao = mock(PcmatDao.class);
        pcmatManager.setDao(pcmatDao);
        
        fasePcmatManager = mock(FasePcmatManager.class);
        pcmatManager.setFasePcmatManager(fasePcmatManager);
        
        areaVivenciaPcmatManager = mock(AreaVivenciaPcmatManager.class);
        pcmatManager.setAreaVivenciaPcmatManager(areaVivenciaPcmatManager);
        
        atividadeSegurancaPcmatManager = mock(AtividadeSegurancaPcmatManager.class);
        pcmatManager.setAtividadeSegurancaPcmatManager(atividadeSegurancaPcmatManager);
        
        epiPcmatManager = mock(EpiPcmatManager.class);
        pcmatManager.setEpiPcmatManager(epiPcmatManager);
        
        epcPcmatManager = mock(EpcPcmatManager.class);
        pcmatManager.setEpcPcmatManager(epcPcmatManager);
    }

	@Test
	public void findByObra()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		Obra obra = ObraFactory.getEntity();
		obra.setEmpresa(empresa);
		
		Pcmat pcmat = PcmatFactory.getEntity();
		
		Collection<Pcmat> pcmats = new ArrayList<Pcmat>();
		pcmats.add(pcmat);

		when(pcmatDao.findByObra(obra.getId())).thenReturn(pcmats);
		
		assertEquals(pcmats, pcmatManager.findByObra(obra.getId()));
	}
	
	@Test(expected=FortesException.class)
	public void validaDataMaiorQueUltimoHistoricoDoPcmat() throws FortesException
	{
		Obra obra = ObraFactory.getEntity(1L);
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		pcmat.setAPartirDe(new Date());
		
		when(pcmatDao.findUltimoHistorico(pcmat.getId(), obra.getId())).thenReturn(pcmat);
		
		pcmatManager.validaDataMaiorQueUltimoHistorico(pcmat.getId(), obra.getId(), pcmat.getAPartirDe());
		fail("FortesExceptio não foi lançada.");
		
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void validaDataMaiorQueUltimoHistoricoDoPcmat_() throws FortesException
	{
		Obra obra = ObraFactory.getEntity(1L);
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		pcmat.setAPartirDe(new Date());
		
		when(pcmatDao.findUltimoHistorico(pcmat.getId(), obra.getId())).thenReturn(pcmat);
		
	    thrown.expect(FortesException.class);
	    thrown.expectMessage("Somente é possível cadastrar um PCMAT após a data "+DateUtil.formataDiaMesAno(pcmat.getAPartirDe()) + ".");
	    thrown.expectMessage(JUnitMatchers.containsString(DateUtil.formataDiaMesAno(pcmat.getAPartirDe())));
	    
	    pcmatManager.validaDataMaiorQueUltimoHistorico(pcmat.getId(), obra.getId(), pcmat.getAPartirDe());
	}
		
	@Test
	public void clonar()
	{
		Long obraId = 1L;

		Pcmat pcmatOrigem = PcmatFactory.getEntity(1L);
		Pcmat pcmatDestino = PcmatFactory.getEntity(2L);

		when(pcmatDao.findEntidadeComAtributosSimplesById(pcmatOrigem.getId())).thenReturn(pcmatOrigem);
		when(pcmatDao.save(pcmatDestino)).thenReturn(pcmatDestino);

		Exception ex = null;
		try {

			pcmatManager.clonar(pcmatOrigem.getId(), new Date(), obraId);
		} catch (Exception e) {
			ex = e;
		}

		assertNull(ex);
	}
}
