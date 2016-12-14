package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.CursoLntDao;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.dao.desenvolvimento.ParticipanteCursoLntDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.StatusLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.util.DateUtil;

public class LntDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private LntDao lntDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private CursoLntDao cursoLntDao;
	@Autowired
	private CursoDao cursoDao;
	@Autowired
	private ParticipanteCursoLntDao participanteCursoLntDao;
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private ColaboradorTurmaDao colaboradorTurmaDao;

	public Lnt getEntity()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Lnt lnt = LntFactory.getEntity(null, "Primeira LNT", new Date(), new Date(), null);
		
		return lnt;
	}

	@Test
	public void testFindAllLnt(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Lnt lnt = LntFactory.getEntity(null, "Primeira LNT", new Date(), new Date(), null);
		lntDao.save(lnt);
		
		Lnt lntFiltro = LntFactory.getEntity("LNT", null);
		
		assertEquals(lnt.getId(), lntDao.findAllLnt(lntFiltro.getDescricao(), StatusLnt.TODOS, 0, 15).iterator().next().getId());
	}
	
	@Test
	public void testFindAllLntRetornaColecaoVazia(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Lnt lnt = LntFactory.getEntity(null, "Primeira LNT", new Date(), new Date(), null);
		lntDao.save(lnt);
		
		Lnt lntFiltro = LntFactory.getEntity("LNT", null);
		
		assertEquals(0, lntDao.findAllLnt(lntFiltro.getDescricao(), StatusLnt.EM_ANALISE, 0, 15).size());
	}
	
	@Test
	public void testFindLntEmAndamentoComDataInicioIgualAAtual()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Lnt lnt1 = LntFactory.getEntity(null, "LNT 1", DateUtil.incrementaDias(new Date(), -1), new Date(), null);
		lntDao.save(lnt1);

		Lnt lnt2 = LntFactory.getEntity(null, "LNT 2", new Date(), new Date(), null);
		lntDao.save(lnt2);
		
		Lnt lnt3 = LntFactory.getEntity(null, "LNT 3", DateUtil.incrementaDias(new Date(), 1), new Date(), null);
		lntDao.save(lnt3);
		
		Collection<Lnt> lnts = lntDao.findLntsNaoFinalizadas(new Date());
		
		assertEquals(1, lnts.size());
		assertEquals(lnt2.getId(), lnts.iterator().next().getId());
	}
	
	@Test
	public void testFinalizar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Lnt lnt = LntFactory.getEntity(null, "LNT 1", DateUtil.incrementaDias(new Date(), -1), DateUtil.incrementaDias(new Date(), -1), null);
		lntDao.save(lnt);

		assertEquals(StatusLnt.EM_ANALISE, lnt.getStatus());
		
		lntDao.finalizar(lnt.getId());
		
		assertEquals(StatusLnt.FINALIZADA, lntDao.findEntidadeComAtributosSimplesById(lnt.getId()).getStatus());
	}
	
	@Test
	public void testReabrir()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Lnt lnt = LntFactory.getEntity(null, "LNT 1", DateUtil.incrementaDias(new Date(), -1), DateUtil.incrementaDias(new Date(), -1), null);
		lnt.setDataFinalizada(new Date());
		lntDao.save(lnt);

		assertEquals(StatusLnt.FINALIZADA, lnt.getStatus());
		
		lntDao.reabrir(lnt.getId());
		
		assertEquals(StatusLnt.EM_ANALISE, lntDao.findEntidadeComAtributosSimplesById(lnt.getId()).getStatus());
	}
	
	@Test
	public void testFindLntsColaborador()
	{
		Lnt lnt1 = LntFactory.getEntity(null, "LNT 1", DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 2, 2016), DateUtil.incrementaDias(new Date(), 10));
		lntDao.save(lnt1);

		Lnt lnt2 = LntFactory.getEntity(null, "LNT 2", DateUtil.criarDataMesAno(1, 3, 2016), DateUtil.criarDataMesAno(1, 4, 2016), DateUtil.incrementaDias(new Date(), 10));
		lntDao.save(lnt2);
		
		Lnt lnt3 = LntFactory.getEntity(null, "LNT 3", DateUtil.criarDataMesAno(1, 5, 2016), DateUtil.criarDataMesAno(1, 6, 2016), null);
		lntDao.save(lnt3);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		CursoLnt cursoLnt1 = CursoLntFactory.getEntity(curso, lnt1);
		cursoLntDao.save(cursoLnt1);
		
		CursoLnt cursoLnt2 = CursoLntFactory.getEntity(curso, lnt2);
		cursoLntDao.save(cursoLnt2);
		
		CursoLnt cursoLnt3 = CursoLntFactory.getEntity(curso, lnt3);
		cursoLntDao.save(cursoLnt3);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(colaborador, areaOrganizacional, cursoLnt1);
		participanteCursoLntDao.save(participanteCursoLnt1);
		
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(colaborador, areaOrganizacional, cursoLnt2);
		participanteCursoLntDao.save(participanteCursoLnt2);
		
		ParticipanteCursoLnt participanteCursoLnt3 = ParticipanteCursoLntFactory.getEntity(colaborador, areaOrganizacional, cursoLnt3);
		participanteCursoLntDao.save(participanteCursoLnt3);
		
		Collection<Lnt> lnts = lntDao.findPossiveisLntsColaborador(curso.getId(), colaborador.getId());
		
		assertEquals(2, lnts.size());
		assertEquals(lnt2.getId(), lnts.iterator().next().getId());
	}
	
	@Test
	public void testFindLntColaboradorParticpa()
	{
		Lnt lnt = LntFactory.getEntity(null, "LNT", DateUtil.criarDataMesAno(1, 5, 2016), DateUtil.criarDataMesAno(1, 6, 2016), DateUtil.incrementaDias(new Date(), 10));
		lntDao.save(lnt);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(curso, lnt);
		cursoLntDao.save(cursoLnt);
		
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(colaborador, areaOrganizacional, cursoLnt);
		participanteCursoLntDao.save(participanteCursoLnt);
		
		Long lntId = lntDao.findLntColaboradorParticpa(curso.getId(), colaborador.getId());

		assertNull(lntId);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, null);
		colaboradorTurma.setCursoLnt(cursoLnt);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		lntId = lntDao.findLntColaboradorParticpa(curso.getId(), colaborador.getId());
		
		assertEquals(lnt.getId(), lntId);
	}
}