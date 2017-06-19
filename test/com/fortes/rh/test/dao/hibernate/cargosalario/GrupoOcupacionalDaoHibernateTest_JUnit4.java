package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;

public class GrupoOcupacionalDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<GrupoOcupacional>
{
	@Autowired 
	GrupoOcupacionalDao grupoOcupacionalDao;
	@Autowired 
	EmpresaDao empresaDao;
	@Autowired
	CargoDao cargoDao;
	@Autowired
	AreaOrganizacionalDao areaOrganizacionalDao;

	
	public GenericDao<GrupoOcupacional> getGenericDao()
	{
		return grupoOcupacionalDao;
	}
	
	public GrupoOcupacional getEntity()
	{
		GrupoOcupacional grupoOcupacional = new GrupoOcupacional();

		grupoOcupacional.setId(null);
		grupoOcupacional.setNome("nome da faixa");
		grupoOcupacional.setCargos(null);
		grupoOcupacional.setEmpresa(null);

		return grupoOcupacional;
	}

	@Test
	public void testGetCount()
	{
		Collection<GrupoOcupacional> lista =  grupoOcupacionalDao.find(new String[]{"empresa.id"}, new Object[] {1L});
		int retorno = grupoOcupacionalDao.getCount(1L);

		assertEquals(retorno, lista.size());
	}

	@Test
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional.setEmpresa(empresa);
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Collection<GrupoOcupacional> retorno = grupoOcupacionalDao.findAllSelect(1, 15, empresa.getId());

		assertEquals(1, retorno.size());
	}

	@Test
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional.setEmpresa(empresa);
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);
		
		assertEquals(grupoOcupacional, grupoOcupacionalDao.findByIdProjection(grupoOcupacional.getId()));
	}

	@Test
	public void testFindByEmpresasIds()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);
		
		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);
		
		GrupoOcupacional g1 = GrupoOcupacionalFactory.getGrupoOcupacional();
		g1.setNome("gerentes");
		g1.setEmpresa(emp1);
		grupoOcupacionalDao.save(g1);
		
		GrupoOcupacional g2 = GrupoOcupacionalFactory.getGrupoOcupacional();
		g2.setNome("coordenadores");
		g2.setEmpresa(emp2);
		grupoOcupacionalDao.save(g2);
		
		Collection<GrupoOcupacional> retorno;
		
		retorno = grupoOcupacionalDao.findByEmpresasIds(emp1.getId()); 
		
		assertEquals(1, retorno.size());
		assertEquals("gerentes", ((GrupoOcupacional) retorno.toArray()[0]).getNome());
		
		retorno = grupoOcupacionalDao.findByEmpresasIds(emp1.getId(), emp2.getId());
		
		assertEquals(2, retorno.size());
		assertEquals("coordenadores", ((GrupoOcupacional) retorno.toArray()[0]).getNome());
		assertEquals("gerentes", ((GrupoOcupacional) retorno.toArray()[1]).getNome());
	}
	
	@Test
	public void testFindAllSelectByAreasResponsavelCoresponsavel(){
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);
		
		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		GrupoOcupacional g1 = GrupoOcupacionalFactory.getGrupoOcupacional();
		g1.setNome("gerentes");
		g1.setEmpresa(emp1);
		grupoOcupacionalDao.save(g1);

		Cargo cargoG1 = CargoFactory.getEntity();
		cargoG1.setAreasOrganizacionais(Arrays.asList(area));
		cargoG1.setGrupoOcupacional(g1);
		cargoDao.save(cargoG1);
		
		GrupoOcupacional g2 = GrupoOcupacionalFactory.getGrupoOcupacional();
		g2.setNome("coordenadores");
		g2.setEmpresa(emp2);
		grupoOcupacionalDao.save(g2);
		
		Cargo cargoG2 = CargoFactory.getEntity();
		cargoG2.setAreasOrganizacionais(Arrays.asList(area));
		cargoG2.setGrupoOcupacional(g2);
		cargoDao.save(cargoG2);
		
		GrupoOcupacional g3 = GrupoOcupacionalFactory.getGrupoOcupacional();
		g3.setNome("desenvolvedores");
		g3.setEmpresa(emp1);
		g3.setCargos(new ArrayList<Cargo>());
		grupoOcupacionalDao.save(g3);
		
		Collection<GrupoOcupacional> retorno = grupoOcupacionalDao.findAllSelectByAreasResponsavelCoresponsavel(emp1.getId(), new Long[]{area.getId()}); 
		
		assertEquals(1, retorno.size());
		assertEquals("gerentes", ((GrupoOcupacional) retorno.toArray()[0]).getNome());
	}
}