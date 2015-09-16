package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManagerImpl;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class QuantidadeLimiteColaboradoresPorCargoManagerTest extends MockObjectTestCase
{
	private QuantidadeLimiteColaboradoresPorCargoManagerImpl quantidadeLimiteColaboradoresPorCargoManager = new QuantidadeLimiteColaboradoresPorCargoManagerImpl();
	private Mock quantidadeLimiteColaboradoresPorCargoDao;
	private Mock faixaSalarialManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        
        quantidadeLimiteColaboradoresPorCargoDao = new Mock(QuantidadeLimiteColaboradoresPorCargoDao.class);
        quantidadeLimiteColaboradoresPorCargoManager.setDao((QuantidadeLimiteColaboradoresPorCargoDao) quantidadeLimiteColaboradoresPorCargoDao.proxy());

        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        quantidadeLimiteColaboradoresPorCargoManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        quantidadeLimiteColaboradoresPorCargoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        quantidadeLimiteColaboradoresPorCargoManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
    }
	
	public void testSaveLimites() 
	{
		QuantidadeLimiteColaboradoresPorCargo qtd1 = new QuantidadeLimiteColaboradoresPorCargo();
		qtd1.setAreaOrganizacional(null);

		QuantidadeLimiteColaboradoresPorCargo qtd2 = new QuantidadeLimiteColaboradoresPorCargo();
		qtd2.setAreaOrganizacional(null);
		
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		qtds.add(qtd1);
		qtds.add(qtd2);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		quantidadeLimiteColaboradoresPorCargoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		
		quantidadeLimiteColaboradoresPorCargoManager.saveLimites(qtds, areaOrganizacional);
		
		assertEquals(new Long(1), qtd1.getAreaOrganizacional().getId());
	}
	
	public void testUpdateLimites() 
	{
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		quantidadeLimiteColaboradoresPorCargoDao.expects(once()).method("deleteByArea").with(ANYTHING);
		quantidadeLimiteColaboradoresPorCargoManager.updateLimites(qtds, areaOrganizacional);
	}
	
	public void testValidaLimite() 
	{
		Cargo cargo = CargoFactory.getEntity(4L);
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(2L);
		faixa.setCargo(cargo);

		Date data = DateUtil.criarDataMesAno(01, 02, 2011);
		
		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaFilha = AreaOrganizacionalFactory.getEntity(2L);
		areaFilha.setAreaMae(areaMae);
		
		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaMae);
		areasOrganizacionais.add(areaFilha);
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaMae.getId());
		areasIds.add(areaFilha.getId());
		
		QuantidadeLimiteColaboradoresPorCargo configuracaoLimite = new QuantidadeLimiteColaboradoresPorCargo();
		configuracaoLimite.setAreaOrganizacional(areaFilha);
		configuracaoLimite.setLimite(15);
		configuracaoLimite.setCargo(cargo);
		
		Empresa empresa = EmpresaFactory.getEmpresa(3L);
		
		faixaSalarialManager.expects(atLeastOnce()).method("findByFaixaSalarialId").with(eq(faixa.getId())).will(returnValue(faixa));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllSelectOrderDescricao").with(eq(empresa.getId()), eq(Boolean.TRUE), ANYTHING, ANYTHING).will(returnValue(areasOrganizacionais));
		quantidadeLimiteColaboradoresPorCargoDao.expects(once()).method("findLimite").with(eq(cargo.getId()), eq(areasIds)).will(returnValue(configuracaoLimite));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAreasPossiveis").with(eq(areasOrganizacionais), eq(configuracaoLimite.getAreaOrganizacional().getId())).will(returnValue(areasOrganizacionais));
		
		//no limite de colaboradores cadastrados
		colaboradorManager.expects(once()).method("countAtivosPeriodo").withAnyArguments().will(returnValue(15));
		Exception exception = null;
		try 
		{
			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(areaFilha.getId(), faixa.getId(), empresa.getId(), null);
		
		} catch (Exception e) 
		{exception = e;}
		
		assertNotNull("no limite", exception);

		//abaixo do limite de colaboradores cadastrados
		quantidadeLimiteColaboradoresPorCargoDao.expects(once()).method("findLimite").with(eq(cargo.getId()), eq(areasIds)).will(returnValue(configuracaoLimite));
		colaboradorManager.expects(once()).method("countAtivosPeriodo").withAnyArguments().will(returnValue(10));
		exception = null;
		try 
		{
			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(areaFilha.getId(), faixa.getId(), empresa.getId(), null);
			
		} catch (Exception e) 
		{exception = e;}
		
		assertNull("abaixo do limite", exception);

		//sem configuração de limite para este cargo e areas
		quantidadeLimiteColaboradoresPorCargoDao.expects(once()).method("findLimite").with(eq(cargo.getId()), eq(areasIds)).will(returnValue(null));
		exception = null;
		try 
		{
			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(areaFilha.getId(), faixa.getId(), empresa.getId(), null);
			
		} catch (Exception e) 
		{exception = e;}
		
		assertNull("sem limite", exception);
	}
}
