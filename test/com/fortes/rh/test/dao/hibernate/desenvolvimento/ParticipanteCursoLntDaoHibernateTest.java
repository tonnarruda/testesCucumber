package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.CursoLntDao;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.dao.desenvolvimento.ParticipanteCursoLntDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class ParticipanteCursoLntDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private LntDao lntDao;
	@Autowired
	private CursoDao cursoDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private CursoLntDao cursoLntDao;
	@Autowired
	private ParticipanteCursoLntDao participanteCursoLntDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private EstabelecimentoDao estabelecimentoDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	
	@Test
	public void testFindByLntIdAndAreasParticipantesIdsAndEmpresasIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, DateUtil.criarDataMesAno(1, 1, 2000), estabelecimento, areaOrganizacional);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Lnt lnt = LntFactory.getEntity(new Date(), new Date());
		lntDao.save(lnt);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(null, "nomeNovoCurso");
		cursoLnt.setLnt(lnt);
		cursoLntDao.save(cursoLnt);
		
		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(colaborador, areaOrganizacional, cursoLnt);
		participanteCursoLntDao.save(participanteCursoLnt);
		
		Collection<ParticipanteCursoLnt> participanteCursoLnts = participanteCursoLntDao.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), new Long[]{areaOrganizacional.getId()}, new Long[]{empresa.getId()}, null);
		
		assertEquals(1, participanteCursoLnts.size());
		assertEquals(colaborador.getNome(), ((ParticipanteCursoLnt)participanteCursoLnts.toArray()[0]).getColaborador().getNome());
		assertEquals(cursoLnt.getNomeNovoCurso(), ((ParticipanteCursoLnt)participanteCursoLnts.toArray()[0]).getCursoLnt().getNomeNovoCurso());
	}

	@Test
	public void testFindByLnt(){
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Lnt lnt = LntFactory.getEntity(null, "descricaoLnt", new Date(), new Date(), null);
		lntDao.save(lnt);
		
		Curso curso = CursoFactory.getEntity(null, "nomeCurso");
		cursoDao.save(curso);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(curso, lnt);
		cursoLntDao.save(cursoLnt);
		
		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(colaborador, areaOrganizacional, cursoLnt);
		participanteCursoLntDao.save(participanteCursoLnt);
		
		Collection<ParticipanteCursoLnt> participanteCursoLnts = participanteCursoLntDao.findByLnt(lnt.getId());
		
		assertEquals(1, participanteCursoLnts.size());
	}
}