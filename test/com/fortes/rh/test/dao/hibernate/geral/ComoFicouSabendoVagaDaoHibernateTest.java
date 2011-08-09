package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ComoFicouSabendoVagaFactory;
import com.fortes.rh.util.DateUtil;

public class ComoFicouSabendoVagaDaoHibernateTest extends GenericDaoHibernateTest<ComoFicouSabendoVaga>
{
	private ComoFicouSabendoVagaDao comoFicouSabendoVagaDao;
	private CandidatoDao candidatoDao;

	@Override
	public ComoFicouSabendoVaga getEntity()
	{
		return ComoFicouSabendoVagaFactory.getEntity();
	}


	public void testeFindAllSemOutros()
	{
		comoFicouSabendoVagaDao.save(new ComoFicouSabendoVaga(2L, "f2rh"));
		comoFicouSabendoVagaDao.save(new ComoFicouSabendoVaga(3L, "rh"));
		
		assertEquals(comoFicouSabendoVagaDao.findAllSemOutros().size(), (comoFicouSabendoVagaDao.findAll().size()-1));
	}
	
	public void testeFindCandidatosComoFicouSabendoVaga()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2222L);
		
		ComoFicouSabendoVaga f2rh = ComoFicouSabendoVagaFactory.getEntity();
		f2rh.setNome("f2rh");
		comoFicouSabendoVagaDao.save(f2rh);

		ComoFicouSabendoVaga globo = ComoFicouSabendoVagaFactory.getEntity();
		globo.setNome("globo");
		comoFicouSabendoVagaDao.save(globo);
		
		Candidato adao = CandidatoFactory.getCandidato();
		adao.setNome("adao");
		adao.setComoFicouSabendoVaga(f2rh);
		adao.setEmpresa(empresa);
		adao.setDataAtualizacao(DateUtil.criarDataMesAno(01, 02, 2000));
		candidatoDao.save(adao);

		Candidato abraao = CandidatoFactory.getCandidato();
		abraao.setNome("abraao");
		abraao.setEmpresa(empresa);
		abraao.setComoFicouSabendoVaga(f2rh);
		abraao.setDataAtualizacao(DateUtil.criarDataMesAno(01, 02, 2008));
		candidatoDao.save(abraao);

		Candidato joao = CandidatoFactory.getCandidato();
		joao.setNome("joao");
		joao.setEmpresa(empresa);
		joao.setComoFicouSabendoVaga(globo);
		joao.setDataAtualizacao(DateUtil.criarDataMesAno(01, 03, 2000));
		candidatoDao.save(joao);

		Candidato raimundo = CandidatoFactory.getCandidato();
		raimundo.setNome("raimundo");
		raimundo.setEmpresa(empresa);
		raimundo.setComoFicouSabendoVaga(globo);
		raimundo.setDataAtualizacao(DateUtil.criarDataMesAno(25, 07, 2000));
		candidatoDao.save(raimundo);

		Candidato rubens = CandidatoFactory.getCandidato();
		rubens.setNome("rubens");
		rubens.setEmpresa(empresa);
		rubens.setComoFicouSabendoVaga(globo);
		rubens.setDataAtualizacao(DateUtil.criarDataMesAno(25, 12, 2000));
		candidatoDao.save(rubens);
		
//		Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas = ;
//		
//		ComoFicouSabendoVaga result = (ComoFicouSabendoVaga) comoFicouSabendoVagas.toArray()[1];
		
		assertNotNull(comoFicouSabendoVagaDao.findCandidatosComoFicouSabendoVaga(DateUtil.criarDataMesAno(01, 01, 2000), DateUtil.criarDataMesAno(01, 01, 2001), empresa.getId()));
	}
	
	@Override
	public GenericDao<ComoFicouSabendoVaga> getGenericDao()
	{
		return comoFicouSabendoVagaDao;
	}

	public void setComoFicouSabendoVagaDao(ComoFicouSabendoVagaDao comoFicouSabendoVagaDao)
	{
		this.comoFicouSabendoVagaDao = comoFicouSabendoVagaDao;
	}


	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	
	
	
}
