package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ExperienciaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;

public class ExperienciaDaoHibernateTest extends GenericDaoHibernateTest<Experiencia>
{
	private ExperienciaDao experienciaDao;
	private CandidatoDao candidatoDao;
	private ColaboradorDao colaboradorDao;
	private CargoDao cargoDao;

	public Experiencia getEntity()
	{
		Experiencia exp = new Experiencia();

		exp.setCandidato(null);
		exp.setDataAdmissao(new Date());
		exp.setDataDesligamento(new Date());
		exp.setEmpresa("emp");
		exp.setId(null);
		exp.setObservacao("obs");
		return exp;
	}

	public GenericDao<Experiencia> getGenericDao()
	{
		return experienciaDao;
	}

	public void setExperienciaDao(ExperienciaDao experienciaDao)
	{
		this.experienciaDao = experienciaDao;
	}

	public void testFindByCandidato()
	{
		Candidato candidato1 = CandidatoFactory.getCandidato();
		Candidato candidato2 = CandidatoFactory.getCandidato();

		candidato1.getEndereco().setUf(null);
		candidato1.getEndereco().setCidade(null);

		candidato2.getEndereco().setCidade(null);
		candidato2.getEndereco().setUf(null);

		candidato1 = candidatoDao.save(candidato1);
		candidato2 = candidatoDao.save(candidato2);

		Experiencia experiencia1 = ExperienciaFactory.getEntity();
		experiencia1.setCandidato(candidato1);
		experiencia1 = experienciaDao.save(experiencia1);

		Experiencia experiencia2 = ExperienciaFactory.getEntity();
		experiencia2.setCandidato(candidato1);
		experiencia2 = experienciaDao.save(experiencia2);

		Experiencia experiencia3 = ExperienciaFactory.getEntity();
		experiencia3.setCandidato(candidato2);
		experiencia3 = experienciaDao.save(experiencia3);

		Collection<Experiencia> experiencias = experienciaDao.findByCandidato(candidato1.getId());

		assertEquals(2, experiencias.size());

		experiencias = experienciaDao.findByCandidato(candidato2.getId());

		assertEquals(1, experiencias.size());
	}

	public void testFindInCandidatos()
	{
		Candidato candidato = CandidatoFactory.getCandidato();		
		candidato = candidatoDao.save(candidato);
		
		Experiencia experiencia = ExperienciaFactory.getEntity();
		experiencia.setCandidato(candidato);
		experiencia = experienciaDao.save(experiencia);
				
		Long[] candidatoIds = new Long[]{candidato.getId()};
		
		Collection<Experiencia> experiencias = experienciaDao.findInCandidatos(candidatoIds);
		
		assertEquals(1, experiencias.size());
	}

	public void testRemoveCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		Experiencia experiencia = ExperienciaFactory.getEntity();
		experiencia.setCandidato(candidato);
		experiencia = experienciaDao.save(experiencia);

		experienciaDao.removeCandidato(candidato);
		assertNull(experienciaDao.findByIdProjection(experiencia.getId()));
	}
	
	public void testRemoveColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		Experiencia experiencia = ExperienciaFactory.getEntity();
		experiencia.setColaborador(colaborador);
		experiencia = experienciaDao.save(experiencia);
		
		experienciaDao.removeColaborador(colaborador);
		assertNull(experienciaDao.findByIdProjection(experiencia.getId()));
	}
	
	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		Experiencia experiencia = ExperienciaFactory.getEntity();
		experiencia.setColaborador(colaborador);
		experiencia.setSalario(1200.0d);
		experiencia = experienciaDao.save(experiencia);
		
		Collection<Experiencia> experiencias = experienciaDao.findByColaborador(colaborador);
		
		assertEquals(1, experiencias.size());
		Experiencia resultado = ((Experiencia) experiencias.toArray()[0]);
		assertEquals(colaborador.getId(), resultado.getColaborador().getId());
		assertEquals(1200.0d, resultado.getSalario());
	}
	
	public void testFindByIdProjection()
	{
		Experiencia experiencia = ExperienciaFactory.getEntity();
		experienciaDao.save(experiencia);
		
		assertNotNull(experienciaDao.findByIdProjection(experiencia.getId()));
	}
	
	public void testDesvinculaCargo()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		Experiencia experiencia = ExperienciaFactory.getEntity();
		experiencia.setCargo(cargo);
		experiencia.setNomeMercado(null);
		experienciaDao.save(experiencia);
		
		Experiencia experienciaTeste = experienciaDao.findByIdProjection(experiencia.getId());
		
		assertEquals(cargo.getId(), experienciaTeste.getCargo().getId());
		assertNull(experienciaTeste.getNomeMercado());
		
		experienciaDao.desvinculaCargo(cargo.getId(), "Cargo");
		
		experienciaTeste = experienciaDao.findByIdProjection(experiencia.getId());
		
		assertNull(experienciaTeste.getCargo());
		assertEquals("Cargo", experienciaTeste.getNomeMercado());
	}
	
	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}
}
