package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;

public class AreaInteresseDaoHibernateTest extends GenericDaoHibernateTest<AreaInteresse>
{
	private AreaInteresseDao areaInteresseDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private EmpresaDao empresaDao;

	public AreaInteresse getEntity()
	{
		AreaInteresse areaInteresse = new AreaInteresse();

		areaInteresse.setId(null);
		areaInteresse.setAreasOrganizacionais(null);
		areaInteresse.setNome("nome da area de formação");
		areaInteresse.setObservacao("observação");
		areaInteresse.setEmpresa(null);

		return areaInteresse;
	}

	public GenericDao<AreaInteresse> getGenericDao()
	{
		return areaInteresseDao;
	}

	public void setAreaInteresseDao(AreaInteresseDao areaInteresseDao)
	{
		this.areaInteresseDao = areaInteresseDao;
	}

	public void testFindAreasInteresseByAreaOrganizacional()
	{
		AreaOrganizacional ao = AreaOrganizacionalFactory.getEntity();
		ao = areaOrganizacionalDao.save(ao);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(ao);

		AreaInteresse ai1 = AreaInteresseFactory.getAreaInteresse();
		ai1.setAreasOrganizacionais(areaOrganizacionals);
		ai1 = areaInteresseDao.save(ai1);

		AreaInteresse ai2 = AreaInteresseFactory.getAreaInteresse();
		ai2.setAreasOrganizacionais(areaOrganizacionals);
		ai2 = areaInteresseDao.save(ai2);

		Collection<AreaInteresse> retorno = areaInteresseDao.findAreasInteresseByAreaOrganizacional(ao);

		assertEquals(2, retorno.size());
	}
	
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
		areaInteresse.setEmpresa(empresa);
		areaInteresse = areaInteresseDao.save(areaInteresse);

		assertEquals(areaInteresse, areaInteresseDao.findByIdProjection(areaInteresse.getId()));
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		AreaInteresse ai1 = AreaInteresseFactory.getAreaInteresse();
		ai1.setNome("AreaInteresse1");
		ai1.setEmpresa(empresa1);
		ai1 = areaInteresseDao.save(ai1);

		AreaInteresse ai2 = AreaInteresseFactory.getAreaInteresse();
		ai2.setNome("AreaInteresse2");
		ai2.setEmpresa(empresa1);
		ai2 = areaInteresseDao.save(ai2);

		AreaInteresse ai3 = AreaInteresseFactory.getAreaInteresse();
		ai3.setNome("AreaInteresse3");
		ai3.setEmpresa(empresa2);
		ai3 = areaInteresseDao.save(ai3);

		Collection<AreaInteresse> retorno = areaInteresseDao.findAllSelect(new Long[]{empresa1.getId()});

		assertEquals("teste1", 2, retorno.size());
		assertEquals("teste2", ai2.getId(), retorno.toArray(new AreaInteresse[]{})[1].getId());

		retorno = areaInteresseDao.findAllSelect(new Long[]{empresa2.getId()});
		assertEquals("teste3", 1, retorno.size());
		assertEquals("teste4", ai3.getId(), retorno.toArray(new AreaInteresse[]{})[0].getId());
	}
	
	public void testFindSincronizar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaInteresse ai1 = AreaInteresseFactory.getAreaInteresse();
		ai1.setNome("AreaInteresse1");
		ai1.setEmpresa(empresa);
		ai1.setObservacao("alkflsdkfsdçkfsdkfsdçfsdçfsdçkfsdçlfsdfsdfsdfsdfsd");
		ai1 = areaInteresseDao.save(ai1);
		
		Collection<AreaInteresse> resultado = areaInteresseDao.findSincronizarAreasInteresse(empresa.getId());
		
		assertEquals(ai1, (AreaInteresse) resultado.toArray()[0]);
	}
	
	public void testDeleteByAreaOrganizacional()
	{
		Exception exception = null;
		
		try {
			areaInteresseDao.deleteByAreaOrganizacional(new Long[] {11111111112L,1111111111113L});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
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