package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;

public class ConhecimentoDaoHibernateTest extends GenericDaoHibernateTest<Conhecimento>
{
	private ConhecimentoDao conhecimentoDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private AreaInteresseDao areaInteresseDao;
	private CandidatoDao candidatoDao;

	public Conhecimento getEntity()
	{
		Conhecimento conhecimento = new Conhecimento();
		conhecimento.setAreaOrganizacionals(null);
		conhecimento.setId(null);
		conhecimento.setNome("programador cobol");
		conhecimento.setEmpresa(null);
		return conhecimento;
	}

	public GenericDao<Conhecimento> getGenericDao()
	{
		return conhecimentoDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		Conhecimento ai1 = ConhecimentoFactory.getConhecimento();
		ai1.setNome("Conhecimento1");
		ai1.setEmpresa(empresa1);
		ai1 = conhecimentoDao.save(ai1);

		Conhecimento ai2 = ConhecimentoFactory.getConhecimento();
		ai2.setNome("Conhecimento2");
		ai2.setEmpresa(empresa1);
		ai2 = conhecimentoDao.save(ai2);

		Conhecimento ai3 = ConhecimentoFactory.getConhecimento();
		ai3.setNome("Conhecimento3");
		ai3.setEmpresa(empresa2);
		ai3 = conhecimentoDao.save(ai3);

		Collection<Conhecimento> retorno = conhecimentoDao.findAllSelect(new Long[]{empresa1.getId()});

		assertEquals("teste1", 2, retorno.size());
		assertEquals("teste2", ai2.getId(), retorno.toArray(new Conhecimento[]{})[1].getId());

		retorno = conhecimentoDao.findAllSelect(new Long[]{empresa2.getId()});
		assertEquals("teste3", 1, retorno.size());
		assertEquals("teste4", ai3.getId(), retorno.toArray(new Conhecimento[]{})[0].getId());
	}

	public void testFindByAreaOrganizacionalIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		area = areaOrganizacionalDao.save(area);

		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);

		Long[] areasIds = new Long[]{area.getId()};

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("Conhecimento");
		conhecimento.setEmpresa(empresa);
		conhecimento.setAreaOrganizacionals(areas);
		conhecimento = conhecimentoDao.save(conhecimento);

		Collection<Conhecimento> conhecimentos = conhecimentoDao.findByAreaOrganizacionalIds(areasIds, empresa.getId());

		assertEquals(1, conhecimentos.size());

		//Areas vazia
		areasIds = new Long[]{};
		conhecimentos = conhecimentoDao.findByAreaOrganizacionalIds(areasIds, empresa.getId());
		assertTrue(conhecimentos.isEmpty());
	}

	public void testFindSincronizarConhecimentos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("Conhecimento");
		conhecimento.setEmpresa(empresa);
		conhecimento.setObservacao("Observ");
		conhecimentoDao.save(conhecimento);

		assertEquals(1, conhecimentoDao.findSincronizarConhecimentos(empresa.getId()).size());
	}
	
	public void testFindByCandidato()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Conhecimento conhecimento1 = ConhecimentoFactory.getConhecimento();
		conhecimento1.setNome("Conhecimento 1");
		conhecimento1.setEmpresa(empresa);
		conhecimentoDao.save(conhecimento1);
		
		Conhecimento conhecimento2 = ConhecimentoFactory.getConhecimento();
		conhecimento2.setNome("Conhecimento 2");
		conhecimento2.setEmpresa(empresa);
		conhecimentoDao.save(conhecimento2);
		
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento1);
		conhecimentos.add(conhecimento2);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setConhecimentos(conhecimentos);
		candidatoDao.save(candidato);
		
		Collection<Conhecimento> retorno = conhecimentoDao.findByCandidato(candidato.getId());
		
		assertEquals(2, retorno.size());
		Conhecimento conhecimentoRetorno = (Conhecimento) retorno.toArray()[0];
		assertEquals(conhecimento1.getNome(), conhecimentoRetorno.getNome());
	}

	public void testFindByAreaInteresse()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		area = areaOrganizacionalDao.save(area);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);

		AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
		areaInteresse.setAreasOrganizacionais(areas);
		areaInteresse = areaInteresseDao.save(areaInteresse);

		Collection<AreaInteresse> areasInteresses = new ArrayList<AreaInteresse>();
		areasInteresses.add(areaInteresse);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("Conhecimento");
		conhecimento.setEmpresa(empresa);
		conhecimento.setAreaOrganizacionals(areas);
		conhecimento = conhecimentoDao.save(conhecimento);

		Long[] areasInteressesIds = new Long[]{areaInteresse.getId()};
		Collection<Conhecimento> conhecimentos = conhecimentoDao.findByAreaInteresse(areasInteressesIds, empresa.getId());

		assertEquals(1, conhecimentos.size());

		//Area de interesse vazia
		areasInteressesIds = new Long[]{};
		conhecimentos = conhecimentoDao.findByAreaInteresse(areasInteressesIds, empresa.getId());
		assertTrue(conhecimentos.isEmpty());
	}

	public void testFindByCargo()
	{
		Cargo cargo = CargoFactory.getEntity();

		Conhecimento conhecimento1 = getEntity();
		conhecimento1 = conhecimentoDao.save(conhecimento1);
		Conhecimento conhecimento2 = getEntity();
		conhecimento2 = conhecimentoDao.save(conhecimento2);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento1);
		conhecimentos.add(conhecimento2);

		cargo.setConhecimentos(conhecimentos);
		cargo = cargoDao.save(cargo);

		Collection<Conhecimento> conhecimentosRetorno = conhecimentoDao.findByCargo(cargo.getId());

		assertEquals(2, conhecimentosRetorno.size());
	}

	public void testFindByIdProjection()
	{
		Conhecimento conhecimento = getEntity();
		conhecimento = conhecimentoDao.save(conhecimento);

		assertEquals(conhecimento, conhecimentoDao.findByIdProjection(conhecimento.getId()));
	}

	public void testFindAllSelectDistinctNome()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);

		Conhecimento conhecimento1 = getEntity();
		conhecimento1.setNome("Java");
		conhecimento1.setEmpresa(empresa1);
		conhecimentoDao.save(conhecimento1);

		Conhecimento conhecimento2 = getEntity();
		conhecimento2.setNome("Java");
		conhecimento2.setEmpresa(empresa1);
		conhecimentoDao.save(conhecimento2);

		Conhecimento conhecimento3 = getEntity();
		conhecimento3.setNome("Java");
		conhecimento3.setEmpresa(empresa2);
		conhecimentoDao.save(conhecimento3);

		Conhecimento conhecimento4 = getEntity();
		conhecimento4.setNome("Hibernate");
		conhecimento4.setEmpresa(empresa2);
		conhecimentoDao.save(conhecimento4);

		Collection<Conhecimento> conhecimentos = conhecimentoDao.findAllSelectDistinctNome(new Long[]{});
		int cont = 0;
		for (Conhecimento conhecimento : conhecimentos)
		{
			if(conhecimento.getNome().equals("Java"))
				cont++;
		}
		
		assertEquals(1, cont);
	}
	
	public void testDeleteByAreaOrganizacional()
	{
		Exception exception = null;
		
		try {
			conhecimentoDao.deleteByAreaOrganizacional(new Long[] {11111111112L,1111111111113L});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao)
	{
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setAreaInteresseDao(AreaInteresseDao areaInteresseDao)
	{
		this.areaInteresseDao = areaInteresseDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

}
