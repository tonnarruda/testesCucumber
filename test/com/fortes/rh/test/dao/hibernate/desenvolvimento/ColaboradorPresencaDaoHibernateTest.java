package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;

public class ColaboradorPresencaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorPresenca>
{
	private ColaboradorPresencaDao colaboradorPresencaDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private TurmaDao turmaDao;
	private CursoDao cursoDao;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private DiaTurmaDao diaTurmaDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;

	public ColaboradorPresenca getEntity()
	{
		ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca();
		colaboradorPresenca.setId(null);
		colaboradorPresenca.setPresenca(true);

		return colaboradorPresenca;
	}

	public void testExistPresencaByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);

		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setTurma(turma);
		colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);

		Collection<ColaboradorPresenca> retornos = colaboradorPresencaDao.existPresencaByTurma(turma.getId());

		assertEquals(1, retornos.size());
	}
	
	public void testFindPresencaByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		Collection<ColaboradorPresenca> retornos = colaboradorPresencaDao.findPresencaByTurma(turma.getId());
		
		assertEquals(1, retornos.size());
	}
	
	public void testRemoveByColaboradorDiaTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		colaboradorPresencaDao.remove(diaTurma.getId(), colaboradorTurma.getId());
		
		assertEquals(new Integer(0), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
		
	}
	
	public void testRemoveByDiaTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		colaboradorPresencaDao.remove(diaTurma.getId(), null);
		
		assertEquals(new Integer(0), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
	}
//	
//	public void testSavePresencaDia()
//	{
//		DiaTurma diaTurma = new DiaTurma();
//		diaTurma.setDia(DateUtil.criarDataMesAno(01, 01, 1988));
//		diaTurma = diaTurmaDao.save(diaTurma);
//		
//		colaboradorPresencaDao.savePresencaDia(diaTurma.getId(), new Long[]{5011L, 5055L});
//		assertEquals(2, colaboradorPresencaDao.findByDiaTurma(diaTurma.getId()).size());
//	}
	
	public void testFindByDiaTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		assertEquals(1, colaboradorPresencaDao.findByDiaTurma(diaTurma.getId()).size());
	}
	
	public void testRemoveByColaboradorTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		Long[] colaboradorTurmaIds = new Long[]{colaboradorTurma.getId()};
		
		assertEquals(new Integer(1), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
		colaboradorPresencaDao.removeByColaboradorTurma(colaboradorTurmaIds);		
		assertEquals(new Integer(0), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
	}

	private ColaboradorTurma prepareColaboradorTurma(Turma turma)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);
		return colaboradorTurma;
	}

	public void testqtdDiaPresentesTurma() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresasParticipantes(Arrays.asList(new Empresa[]{empresa1}));
		cursoDao.save(curso1);

		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa2);
		cursoDao.save(curso2);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setRealizada(true);
		turma1.setCurso(curso1);
		turmaDao.save(turma1);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setRealizada(true);
		turma2.setCurso(curso2);
		turmaDao.save(turma2);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity();
		diaTurma1.setTurma(turma1);
		diaTurmaDao.save(diaTurma1);
		
		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity();
		diaTurma2.setTurma(turma2);
		diaTurmaDao.save(diaTurma2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(new Date());
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);
		
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		ColaboradorTurma colaboradorTurma2 = new ColaboradorTurma();
		colaboradorTurma2.setColaborador(colaborador);
		colaboradorTurma2.setTurma(turma2);
		colaboradorTurmaDao.save(colaboradorTurma2);
		
		ColaboradorPresenca colaboradorPresenca1 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca1.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca1.setDiaTurma(diaTurma1);
		colaboradorPresencaDao.save(colaboradorPresenca1);
		
		ColaboradorPresenca colaboradorPresenca2 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca2.setColaboradorTurma(colaboradorTurma2);
		colaboradorPresenca2.setDiaTurma(diaTurma2);
		colaboradorPresencaDao.save(colaboradorPresenca2);
		
		ColaboradorPresenca colaboradorPresenca3 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca3.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca3.setDiaTurma(diaTurma1);
		colaboradorPresencaDao.save(colaboradorPresenca3);
		
		assertEquals(new Integer(3), colaboradorPresencaDao.qtdDiaPresentesTurma(null, null, new Long[]{empresa1.getId(), empresa2.getId()}, new Long[]{curso1.getId(), curso2.getId()}, null, new Long[]{estabelecimento.getId()}));
		assertEquals(new Integer(0), colaboradorPresencaDao.qtdDiaPresentesTurma(null, null, new Long[]{empresa1.getId(), empresa2.getId()}, new Long[]{curso1.getId(), curso2.getId()}, null, new Long[]{0L}));
	}
	
	public void testQtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento est1 = EstabelecimentoFactory.getEntity();
		est1.setNome("Est-1");
		estabelecimentoDao.save(est1);
		
		Estabelecimento est2 = EstabelecimentoFactory.getEntity();
		est2.setNome("Est-2");
		estabelecimentoDao.save(est2);

		Curso curso = CursoFactory.getEntity();
		curso.setEmpresasParticipantes(Arrays.asList(new Empresa[]{empresa}));
		cursoDao.save(curso);

		Turma turma = TurmaFactory.getEntity();
		turma.setRealizada(true);
		turma.setCurso(curso);
		turmaDao.save(turma);

		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity();
		diaTurma1.setTurma(turma);
		diaTurmaDao.save(diaTurma1);
		
		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity();
		diaTurma2.setTurma(turma);
		diaTurmaDao.save(diaTurma2);
		
		Colaborador raimundo = ColaboradorFactory.getEntity();
		colaboradorDao.save(raimundo);
		
		HistoricoColaborador hCRaimundo = new HistoricoColaborador();
		hCRaimundo.setColaborador(raimundo);
		hCRaimundo.setEstabelecimento(est1);
		hCRaimundo.setData(new Date());
		historicoColaboradorDao.save(hCRaimundo);
		
		Colaborador jussara = ColaboradorFactory.getEntity();
		colaboradorDao.save(jussara);
		
		HistoricoColaborador hCJussara = new HistoricoColaborador();
		hCJussara.setColaborador(jussara);
		hCJussara.setEstabelecimento(est2);
		hCJussara.setData(new Date());
		historicoColaboradorDao.save(hCJussara);
		
		ColaboradorTurma raimundoTurma = new ColaboradorTurma();
		raimundoTurma.setColaborador(raimundo);
		raimundoTurma.setTurma(turma);
		colaboradorTurmaDao.save(raimundoTurma);
		
		ColaboradorTurma jussaraTurma = new ColaboradorTurma();
		jussaraTurma.setColaborador(jussara);
		jussaraTurma.setTurma(turma);
		colaboradorTurmaDao.save(jussaraTurma);
		
		ColaboradorPresenca colaboradorPresenca1 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca1.setColaboradorTurma(jussaraTurma);
		colaboradorPresenca1.setDiaTurma(diaTurma1);
		colaboradorPresencaDao.save(colaboradorPresenca1);
		
		ColaboradorPresenca colaboradorPresenca2 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca2.setColaboradorTurma(jussaraTurma);
		colaboradorPresenca2.setDiaTurma(diaTurma2);
		colaboradorPresencaDao.save(colaboradorPresenca2);
		
		ColaboradorPresenca colaboradorPresenca3 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca3.setColaboradorTurma(raimundoTurma);
		colaboradorPresenca3.setDiaTurma(diaTurma2);
		colaboradorPresencaDao.save(colaboradorPresenca3);
		
		assertEquals(new Integer(1), colaboradorPresencaDao.qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(diaTurma1.getId(), null));
		assertEquals(new Integer(2), colaboradorPresencaDao.qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(diaTurma2.getId(), null));
		assertEquals(new Integer(1), colaboradorPresencaDao.qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(diaTurma2.getId(), est1.getId()));
	}
	
	
	public GenericDao<ColaboradorPresenca> getGenericDao()
	{
		return colaboradorPresencaDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao ColaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = ColaboradorTurmaDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public void setColaboradorPresencaDao(ColaboradorPresencaDao colaboradorPresencaDao)
	{
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setDiaTurmaDao(DiaTurmaDao diaTurmaDao)
	{
		this.diaTurmaDao = diaTurmaDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public CursoDao getCursoDao() {
		return cursoDao;
	}

	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}

	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}


}