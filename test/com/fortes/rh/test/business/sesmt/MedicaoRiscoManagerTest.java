package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.sesmt.MedicaoRiscoManagerImpl;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoMedicaoRiscoFactory;

public class MedicaoRiscoManagerTest extends MockObjectTestCase
{
	private MedicaoRiscoManagerImpl medicaoRiscoManager = new MedicaoRiscoManagerImpl();
	private Mock medicaoRiscoDao;
	private Mock riscoMedicaoRiscoManager;
	private Mock transactionManager = null;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        medicaoRiscoDao = new Mock(MedicaoRiscoDao.class);
        medicaoRiscoManager.setDao((MedicaoRiscoDao) medicaoRiscoDao.proxy());
        
        riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
        medicaoRiscoManager.setRiscoMedicaoRiscoManager((RiscoMedicaoRiscoManager) riscoMedicaoRiscoManager.proxy());
        
        transactionManager = new Mock(PlatformTransactionManager.class);
        medicaoRiscoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		Long ambienteId = 1L;
		
		Collection<MedicaoRisco> medicaoRiscos = MedicaoRiscoFactory.getCollection(1L);

		medicaoRiscoDao.expects(once()).method("findAllSelect").with(eq(empresaId),eq(ambienteId)).will(returnValue(medicaoRiscos));
		assertEquals(medicaoRiscos, medicaoRiscoManager.findAllSelect(empresaId, ambienteId));
	}
	
	public void testSave() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		String[] riscoIds = {"1","2"};
		String[] ltcatValues = {"Ltcat do risco 1", "LTCAT do risco 2"};
		String[] ppraValues = {"PPRA do risco 1", "PPRA do risco 2"};
		String[] tecnicaValues = {"técnica do risco 1", "técnica do risco 2"};
		String[] intensidadeValues = {"intensidade do risco 1", "intensidade do risco 2"};
		
		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit");
		
		riscoMedicaoRiscoManager.expects(once()).method("removeByMedicaoRisco").with(eq(null)).will(returnValue(true));
		
		medicaoRiscoDao.expects(once()).method("save").with(eq(medicaoRisco));
		
		medicaoRiscoManager.save(medicaoRisco, riscoIds, ltcatValues, ppraValues, tecnicaValues, intensidadeValues);
	}
	public void testSaveAtualizando() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		String[] riscoIds = {"1","2"};
		String[] ltcatValues = {"Ltcat do risco 1", "LTCAT do risco 2"};
		String[] ppraValues = {"PPRA do risco 1", "PPRA do risco 2"};
		String[] tecnicaValues = {"técnica do risco 1", "técnica do risco 2"};
		String[] intensidadeValues = {"intensidade do risco 1", "intensidade do risco 2"};
		
		riscoMedicaoRiscoManager.expects(once()).method("removeByMedicaoRisco").with(eq(1L)).will(returnValue(true));
		
		medicaoRiscoDao.expects(once()).method("update").with(eq(medicaoRisco));
		
		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit");
		
		medicaoRiscoManager.save(medicaoRisco, riscoIds, ltcatValues, ppraValues, tecnicaValues, intensidadeValues);
	}
	
	public void testGetTecnicasUtilizadas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<String> tecnicas = new ArrayList<String>();
		tecnicas.add("Técnica");
		tecnicas.add("Téc2");

		medicaoRiscoDao.expects(once()).method("findTecnicasUtilizadasDistinct").with(eq(empresa.getId())).will(returnValue(tecnicas));
		assertEquals("\"Técnica\",\"Téc2\"", medicaoRiscoManager.getTecnicasUtilizadas(empresa.getId()));
	}

	public void testGetTecnicasUtilizadasVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		medicaoRiscoDao.expects(once()).method("findTecnicasUtilizadasDistinct").with(eq(empresa.getId())).will(returnValue(new ArrayList<String>()));
		assertEquals("", medicaoRiscoManager.getTecnicasUtilizadas(empresa.getId()));
	}
	
	public void testPreparaRiscosDaMedicao()
	{
		Risco risco1 = RiscoFactory.getEntity(1L);
		risco1.setDescricao("Risco 1");
		Risco risco2 = RiscoFactory.getEntity(2L);
		risco2.setDescricao("Risco 2");
		
		Risco riscoNovo = RiscoFactory.getEntity(3L);
		riscoNovo.setDescricao("AAAAAAA");
		
		Collection<Risco> riscos = new ArrayList<Risco>(2);
		riscos.add(risco1);
		riscos.add(risco2);
		riscos.add(riscoNovo);
		
		RiscoMedicaoRisco riscoMedicaoRisco1 = RiscoMedicaoRiscoFactory.getEntity(1L);
		riscoMedicaoRisco1.setRisco(risco1);
		RiscoMedicaoRisco riscoMedicaoRisco2 = RiscoMedicaoRiscoFactory.getEntity(2L);
		riscoMedicaoRisco2.setRisco(risco2);
		
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(riscoMedicaoRisco1);
		riscoMedicaoRiscos.add(riscoMedicaoRisco2);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(12L);
		medicaoRisco.setRiscoMedicaoRiscos(riscoMedicaoRiscos);
		
		Collection<RiscoMedicaoRisco> resultado = medicaoRiscoManager.preparaRiscosDaMedicao(medicaoRisco, riscos);
		
		assertEquals(2, riscoMedicaoRiscos.size());
		assertEquals(3, resultado.size());
		assertEquals(riscoNovo.getDescricao(), ((RiscoMedicaoRisco) resultado.toArray()[0]).getRisco().getDescricao());
	}
}
