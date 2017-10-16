package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.FatorDeRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.dicionario.GrupoRiscoESocial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.FatorDeRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class RiscoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Risco>
{
	@Autowired private RiscoDao riscoDao;
	@Autowired private EpiDao epiDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private FatorDeRiscoDao fatorDeRiscoDao;

	public Risco getEntity()
	{
		return RiscoFactory.getEntity();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindEpisByRisco()
	{
		Empresa empresa = null;

		Epi e1 = new Epi();
		e1.setNome("nome da epi");
		e1.setFabricante("TecToy");
		e1.setEmpresa(empresa);

		Epi e2 = new Epi();
		e2.setNome("nome da epi");
		e2.setFabricante("TecToy");
		e2.setEmpresa(empresa);

		e1 = epiDao.save(e1);
		e2 = epiDao.save(e2);

		Collection<Epi> episTest = new ArrayList<Epi>();
		episTest.add(e2);
		episTest.add(e1);

		Risco risco = new Risco();
		risco.setEpis(episTest);

		risco = riscoDao.save(risco);

		List<Epi> epis = riscoDao.findEpisByRisco(risco.getId());

		assertEquals(episTest.size(), epis.size());
	}
	
	@Test
	public void testFindAllSelect()
	{
		
		Epi epi = EpiFactory.getEntity();
		epiDao.save(epi);
		
		Epi epi2 = EpiFactory.getEntity();
		epiDao.save(epi2);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi);
		epis.add(epi2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Risco risco = RiscoFactory.getEntity();
		risco.setEmpresa(empresa);
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		Collection<Risco> colecao = riscoDao.findAllSelect(empresa.getId());
		
		assertEquals(1, colecao.size());
	}
	
	@Test
	public void testListRiscos(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Collection<FatorDeRisco> fatoresDeRisco =  fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ACIDENTE);
		FatorDeRisco fatorDeRisco1 =  fatoresDeRisco.iterator().next();
		
		Risco risco = RiscoFactory.getEntity("Risco 1", GrupoRisco.ACIDENTE, GrupoRiscoESocial.ACIDENTE, empresa,fatorDeRisco1);
		riscoDao.save(risco);
		
		Collection<Risco> riscos = riscoDao.listRiscos(1, 15, risco);
		assertEquals(1, riscos.size());
		assertEquals(risco.getId(), riscos.iterator().next().getId());
	}
	
	@Test
	public void testListRiscosSemGrupoRiscoESocialConfigurado(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Risco risco = RiscoFactory.getEntity("Risco 1", GrupoRisco.ACIDENTE, null, empresa,null);
		riscoDao.save(risco);
		
		Risco riscoConsulta = RiscoFactory.getEntity("", "", GrupoRiscoESocial.SEM_GRUPO_CONFIGURADO, empresa,null);
		
		Collection<Risco> riscos = riscoDao.listRiscos(0, 0, riscoConsulta);
		assertEquals(1, riscos.size());
		assertEquals(risco.getId(), riscos.iterator().next().getId());
	}
	
	@Test
	public void testListRiscosTodosOsGrupoRiscoESocial(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Risco risco = RiscoFactory.getEntity("Risco 1", GrupoRisco.ACIDENTE, null, empresa,null);
		riscoDao.save(risco);
		
		Risco riscoConsulta = RiscoFactory.getEntity("", "", "", empresa,null);
		
		Collection<Risco> riscos = riscoDao.listRiscos(0, 0, riscoConsulta);
		assertEquals(1, riscos.size());
		assertEquals(risco.getId(), riscos.iterator().next().getId());
	}
	
	@Test
	public void testCountRiscos(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Collection<FatorDeRisco> fatoresDeRisco =  fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ACIDENTE);
		FatorDeRisco fatorDeRisco1 =  fatoresDeRisco.iterator().next();
		
		Risco risco = RiscoFactory.getEntity("Risco 1", GrupoRisco.ACIDENTE, GrupoRiscoESocial.ACIDENTE, empresa,fatorDeRisco1);
		riscoDao.save(risco);
		
		assertEquals(new Integer(1), riscoDao.getCount(risco));
	}
	
	public GenericDao<Risco> getGenericDao()
	{
		return riscoDao;
	}
}