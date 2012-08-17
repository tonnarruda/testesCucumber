package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;

public class GrupoOcupacionalDaoHibernateTest extends GenericDaoHibernateTest<GrupoOcupacional>
{
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private EmpresaDao empresaDao;

	public GrupoOcupacional getEntity()
	{
		GrupoOcupacional grupoOcupacional = new GrupoOcupacional();

		grupoOcupacional.setId(null);
		grupoOcupacional.setNome("nome da faixa");
		grupoOcupacional.setCargos(null);
		grupoOcupacional.setEmpresa(null);

		return grupoOcupacional;
	}

	public void testGetCount()
	{
		Collection<GrupoOcupacional> lista =  grupoOcupacionalDao.find(new String[]{"empresa.id"}, new Object[] {1L});
		int retorno = grupoOcupacionalDao.getCount(1L);

		assertEquals(retorno, lista.size());
	}

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

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional.setEmpresa(empresa);
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);
		
		assertEquals(grupoOcupacional, grupoOcupacionalDao.findByIdProjection(grupoOcupacional.getId()));
	}

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
	
	public GenericDao<GrupoOcupacional> getGenericDao()
	{
		return grupoOcupacionalDao;
	}

	public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
	{
		this.grupoOcupacionalDao = grupoOcupacionalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

}