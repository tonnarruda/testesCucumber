package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
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
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.util.DateUtil;

public class CursoLntDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<CursoLnt>
{
	@Autowired
	private CursoLntDao cursoLntDao;
	@Autowired
	private CursoDao cursoDao;
	@Autowired
	private LntDao lntDao;
	@Autowired
	private ParticipanteCursoLntDao participanteCursoLntDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private ColaboradorTurmaDao colaboradorTurmaDao;

	private CursoLnt cursoLnt = CursoLntFactory.getEntity();
	
	@Override
	public GenericDao<CursoLnt> getGenericDao() {
		return cursoLntDao;
	}

	@Override
	public CursoLnt getEntity() {
		return cursoLnt;
	}
	
	@Test
	public void testGetAutoComplete(){
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Lnt lnt = LntFactory.getEntity(DateUtil.criarDataMesAno(1, 11, 2016),DateUtil.criarDataMesAno(10, 11, 2016));
		lntDao.save(lnt);
		
		Colaborador chavier = ColaboradorFactory.getEntity();
		chavier.setNome("Chavier");
		chavier.setEmpresa(empresa);
		colaboradorDao.save(chavier);
		
		Colaborador logan = ColaboradorFactory.getEntity();
		logan.setNome("Logan");
		logan.setEmpresa(empresa);
		colaboradorDao.save(logan);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(curso, lnt);
		cursoLntDao.save(cursoLnt);
		
		Collection<CursoLnt> retorno = cursoLntDao.findByLntId(lnt.getId());

		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testExistePerticipanteASerRelacionado(){
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Lnt lnt = LntFactory.getEntity(DateUtil.criarDataMesAno(1, 11, 2016),DateUtil.criarDataMesAno(10, 11, 2016));
		lntDao.save(lnt);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(curso, lnt);
		cursoLntDao.save(cursoLnt);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Colaborador chavier = ColaboradorFactory.getEntity(null, "Chavier");
		colaboradorDao.save(chavier);
		
		Colaborador logan = ColaboradorFactory.getEntity(null, "Logan");
		colaboradorDao.save(logan);
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(chavier, areaOrganizacional, cursoLnt);
		participanteCursoLntDao.save(participanteCursoLnt1);
		
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(logan, areaOrganizacional, cursoLnt);
		participanteCursoLntDao.save(participanteCursoLnt2);
		
		assertFalse(cursoLntDao.existePerticipanteASerRelacionado(cursoLnt.getId()));
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(null, chavier);
		colaboradorTurma1.setCursoLnt(cursoLnt);
		colaboradorTurmaDao.save(colaboradorTurma1);
		
		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity(null, logan);
		colaboradorTurma2.setCursoLnt(cursoLnt);
		colaboradorTurmaDao.save(colaboradorTurma2);
		
		colaboradorTurmaDao.getHibernateTemplateByGenericDao().flush();
		
//		/assertTrue(cursoLntDao.existePerticipanteASerRelacionado(cursoLnt.getId()));
	}
	
	@Test
	public void testUpdateNomeNovoCurso(){
		Curso curso = CursoFactory.getEntity();
		curso.setNome("Nome Curso");
		cursoDao.save(curso);
		
		Lnt lnt = LntFactory.getEntity(DateUtil.criarDataMesAno(1, 11, 2016),DateUtil.criarDataMesAno(10, 11, 2016));
		lntDao.save(lnt);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(curso, lnt);
		cursoLnt.setNomeNovoCurso("nomeNovoCurso");
		cursoLntDao.save(cursoLnt);

		Collection<CursoLnt> cursosLnt = cursoLntDao.findByLntId(lnt.getId());
		CursoLnt cursoLntRetorno = (CursoLnt) cursosLnt.toArray()[0];
		
		assertEquals(cursoLnt.getNomeNovoCurso(), cursoLntRetorno.getNomeNovoCurso());
		
		cursoLntDao.updateNomeNovoCurso(curso.getId(), curso.getNome());
		
		cursosLnt = cursoLntDao.findByLntId(lnt.getId());
		cursoLntRetorno = (CursoLnt) cursosLnt.toArray()[0];
		
		assertEquals(curso.getNome(), cursoLntRetorno.getNomeNovoCurso());
	}
	
	@Test
	public void testRemoveCursoId(){
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Lnt lnt = LntFactory.getEntity(DateUtil.criarDataMesAno(1, 11, 2016),DateUtil.criarDataMesAno(10, 11, 2016));
		lntDao.save(lnt);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(curso, lnt);
		cursoLntDao.save(cursoLnt);

		Collection<CursoLnt> cursosLnt = cursoLntDao.findByLntId(lnt.getId());
		CursoLnt cursoLntRetorno = (CursoLnt) cursosLnt.toArray()[0];
		
		assertEquals(curso.getId(), cursoLntRetorno.getCurso().getId());
		
		cursoLntDao.removeCursoId(curso.getId());
		
		cursosLnt = cursoLntDao.findByLntId(lnt.getId());
		cursoLntRetorno = (CursoLnt) cursosLnt.toArray()[0];
		
		assertNull(cursoLntRetorno.getCurso());
	}
}
