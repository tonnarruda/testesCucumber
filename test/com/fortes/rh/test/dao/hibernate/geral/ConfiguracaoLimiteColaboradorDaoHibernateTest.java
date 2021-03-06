package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;

public class ConfiguracaoLimiteColaboradorDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoLimiteColaborador>
{
	private ConfiguracaoLimiteColaboradorDao configuracaoLimiteColaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private EmpresaDao empresaDao;

	@Override
	public ConfiguracaoLimiteColaborador getEntity()
	{
		return ConfiguracaoLimiteColaboradorFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoLimiteColaborador> getGenericDao()
	{
		return configuracaoLimiteColaboradorDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		ConfiguracaoLimiteColaborador contratoA = ConfiguracaoLimiteColaboradorFactory.getEntity();
		contratoA.setDescricao("contratoA");
		contratoA.setAreaOrganizacional(areaOrganizacional1);
		configuracaoLimiteColaboradorDao.save(contratoA);

		ConfiguracaoLimiteColaborador contratoX = ConfiguracaoLimiteColaboradorFactory.getEntity();
		contratoX.setDescricao("contratoY");
		contratoX.setAreaOrganizacional(areaOrganizacional2);
		configuracaoLimiteColaboradorDao.save(contratoX);
		
		Collection<ConfiguracaoLimiteColaborador> configuracaoLimiteColaboradors = configuracaoLimiteColaboradorDao.findAllSelect(empresa.getId());
		
		assertEquals(2, configuracaoLimiteColaboradors.size());
		assertEquals(contratoA, configuracaoLimiteColaboradors.toArray()[0]);
		
	}
	
	public void testFindIdAreas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		ConfiguracaoLimiteColaborador contratoA = ConfiguracaoLimiteColaboradorFactory.getEntity();
		contratoA.setDescricao("contratoA");
		contratoA.setAreaOrganizacional(areaOrganizacional1);
		configuracaoLimiteColaboradorDao.save(contratoA);
		
		ConfiguracaoLimiteColaborador contratoX = ConfiguracaoLimiteColaboradorFactory.getEntity();
		contratoX.setDescricao("contratoX");
		contratoX.setAreaOrganizacional(areaOrganizacional2);
		configuracaoLimiteColaboradorDao.save(contratoX);
		
		Collection<Long> areasIds = configuracaoLimiteColaboradorDao.findIdAreas(empresa.getId());
		
		assertEquals(2, areasIds.size());
	}
	

	public void testDeleteByAreaOrganizacional()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity();
		configuracaoLimiteColaboradorDao.save(configuracaoLimiteColaborador);
		
		Exception exception = null;
		try {
			configuracaoLimiteColaboradorDao.deleteByAreaOrganizacional(new Long[]{areaOrganizacional.getId()});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull("Não houve exceção", exception);
	}
	
	public void setConfiguracaoLimiteColaboradorDao(ConfiguracaoLimiteColaboradorDao configuracaoLimiteColaboradorDao)
	{
		this.configuracaoLimiteColaboradorDao = configuracaoLimiteColaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) 
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) 
	{
		this.empresaDao = empresaDao;
	}
	
}
