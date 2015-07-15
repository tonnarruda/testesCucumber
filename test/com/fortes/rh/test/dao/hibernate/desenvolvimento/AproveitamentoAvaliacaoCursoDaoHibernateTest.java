package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class AproveitamentoAvaliacaoCursoDaoHibernateTest extends GenericDaoHibernateTest<AproveitamentoAvaliacaoCurso>
{
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private TurmaDao turmaDao;
	private CursoDao cursoDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;

	public void setAproveitamentoAvaliacaoCursoDao(AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao)
	{
		this.aproveitamentoAvaliacaoCursoDao = aproveitamentoAvaliacaoCursoDao;
	}

	@Override
	public AproveitamentoAvaliacaoCurso getEntity()
	{
		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso.setId(null);
		return aproveitamentoAvaliacaoCurso;
	}

	@Override
	public GenericDao<AproveitamentoAvaliacaoCurso> getGenericDao()
	{
		return aproveitamentoAvaliacaoCursoDao;
	}

	public void testFindNotas()
	{
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaDao.save(colaboradorTurma);

		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso.setValor(5.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);

		Collection<AproveitamentoAvaliacaoCurso> aproveitamentos = aproveitamentoAvaliacaoCursoDao.findNotas(avaliacaoCurso.getId(), new Long[]{colaboradorTurma.getId()});

		assertEquals(1, aproveitamentos.size());
	}

	public void testFindByColaboradorTurmaAvaliacaoId()
	{
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaDao.save(colaboradorTurma);

		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso.setValor(5.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);

		AproveitamentoAvaliacaoCurso aproveitamento = aproveitamentoAvaliacaoCursoDao.findByColaboradorTurmaAvaliacaoId(colaboradorTurma.getId(), avaliacaoCurso.getId());
		assertNotNull(aproveitamento);
	}
	
	public void testFindByColaboradorCurso()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso1 = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso1.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCurso1.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso1.setValor(5.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso1);
		
		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso2 = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso2.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCurso2.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso2.setValor(55.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso2);
		
		Collection<AproveitamentoAvaliacaoCurso> notas = aproveitamentoAvaliacaoCursoDao.findByColaboradorCurso(colaborador.getId(), curso.getId());
		assertEquals(2, notas.size());
	}

	public void testFindAprovadoByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);

		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso1.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setMinimoAprovacao(30.0);
		avaliacaoCursoDao.save(avaliacaoCurso2);

		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);

		ColaboradorTurma colaboradorTurmaReprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaReprovado.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurmaReprovado);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoAprovado1 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso1, 50.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoAprovado1);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado2 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso1, 49.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado2);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado3 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso2, 30.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado3);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado4 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso2, 50.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado4);

		//Aprovados
		Collection<Long> ids = aproveitamentoAvaliacaoCursoDao.find(turma.getId(), 2, "T", true);
		assertEquals(1, ids.size());
		assertEquals(colaboradorTurmaAprovado.getId(), ids.toArray()[0]);

		//Reprovados
		ids = aproveitamentoAvaliacaoCursoDao.find(turma.getId(), 2, "T", false);
		assertEquals(1, ids.size());
		assertEquals(colaboradorTurmaReprovado.getId(), ids.toArray()[0]);
	}

	public void testRemoveByAvaliacao()
	{
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);

		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso1);

		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);

		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setCurso(curso);
		colaboradorTurmaDao.save(colaboradorTurma);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso1 = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso1.setAvaliacaoCurso(avaliacaoCurso1);
		aproveitamentoAvaliacaoCurso1.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso1);

		aproveitamentoAvaliacaoCursoDao.remove(curso.getId(), new String[]{avaliacaoCurso2.getId().toString()});

		assertEquals(null, aproveitamentoAvaliacaoCursoDao.findByColaboradorTurmaAvaliacaoId(colaboradorTurma.getId(), avaliacaoCurso1.getId()));
	}

	public void testRemoveByTurma()
	{
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);

		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso1);

		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);

		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setCurso(curso);
		colaboradorTurmaDao.save(colaboradorTurma);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso1 = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso1.setAvaliacaoCurso(avaliacaoCurso1);
		aproveitamentoAvaliacaoCurso1.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso1);

		aproveitamentoAvaliacaoCursoDao.removeByTurma(turma.getId());

		assertEquals(null, aproveitamentoAvaliacaoCursoDao.findByColaboradorTurmaAvaliacaoId(colaboradorTurma.getId(), avaliacaoCurso1.getId()));
	}

	public void testRemoveByColaboradorTurma()
	{
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);

		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso1);

		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);

		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setCurso(curso);
		colaboradorTurmaDao.save(colaboradorTurma);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso1 = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso1.setAvaliacaoCurso(avaliacaoCurso1);
		aproveitamentoAvaliacaoCurso1.setColaboradorTurma(colaboradorTurma);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso1);

		aproveitamentoAvaliacaoCursoDao.removeByColaboradorTurma(colaboradorTurma.getId());

		assertEquals(null, aproveitamentoAvaliacaoCursoDao.findByColaboradorTurmaAvaliacaoId(colaboradorTurma.getId(), avaliacaoCurso1.getId()));
	}
	
	public void testFindAprovadoByCursos()
	{
		Curso curso1 = CursoFactory.getEntity();
		cursoDao.save(curso1);

		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		turmaDao.save(turma1);

		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso1.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setMinimoAprovacao(30.0);
		avaliacaoCursoDao.save(avaliacaoCurso2);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma1);
		colaboradorTurmaAprovado.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);

		ColaboradorTurma colaboradorTurmaReprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaReprovado.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurmaReprovado);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoAprovado1 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso1, 50.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoAprovado1);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado2 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso1, 49.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado2);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado3 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso2, 30.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado3);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado4 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso2, 50.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado4);

		Long[] cursoIds = new Long[]{curso1.getId()};
		Collection<Long> ids = aproveitamentoAvaliacaoCursoDao.find(cursoIds, 2, true);
		assertEquals(1, ids.size());
	}
	public void testFindColaboradorTurmaSemQtdAvaliacao()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma);
		colaboradorTurmaAprovado.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);
		
		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurmaAprovado);
		aproveitamentoAvaliacaoCurso.setValor(20.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);

		int qtdAvaliacao = 0;
		boolean qtdAvaliacaoBoolean = false;
		
		Collection<ColaboradorTurma> colaboradorTurmas = aproveitamentoAvaliacaoCursoDao.findColaboradorTurma(turma.getCurso().getId(), qtdAvaliacao, "C", qtdAvaliacaoBoolean);
		assertEquals(1, colaboradorTurmas.size());
	}

	public void testFindColaboradorTurmaComQtdAvaliacao()
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma);
		colaboradorTurmaAprovado.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);
		
		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurmaAprovado);
		aproveitamentoAvaliacaoCurso.setValor(70.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);
		
		int qtdAvaliacao = 1;
		boolean qtdAvaliacaoBoolean = true;
		
		Collection<ColaboradorTurma> colaboradorTurmas = aproveitamentoAvaliacaoCursoDao.findColaboradorTurma(turma.getId(), qtdAvaliacao, "T", qtdAvaliacaoBoolean);
		assertEquals(1, colaboradorTurmas.size());
	}

	public void testFindColaboradores()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma);
		colaboradorTurmaAprovado.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setMinimoAprovacao(90.0);
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
		aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
		aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurmaAprovado);
		aproveitamentoAvaliacaoCurso.setValor(70.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);
		
		int qtdAvaliacao = 1;
		boolean aprovado = true;
		
		Collection<Long> colaboradorIds = aproveitamentoAvaliacaoCursoDao.findColaboradores(turma.getId(), qtdAvaliacao, "T", aprovado);
		assertEquals(1, colaboradorIds.size());
		
		//reprovados
		aprovado = false;
		aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso2);
		
		Collection<Long> colaboradorIds2 = aproveitamentoAvaliacaoCursoDao.findColaboradores(turma.getCurso().getId(), qtdAvaliacao, "C", aprovado);
		assertEquals(1, colaboradorIds2.size());
	}

	public void testFindReprovados()
	{
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso1.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setMinimoAprovacao(30.0);
		avaliacaoCursoDao.save(avaliacaoCurso2);

		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso1);
		avaliacaoCursos.add(avaliacaoCurso2);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso1);

		Turma turma1 = TurmaFactory.getEntity();
		turma1.setDataPrevIni(dataTresMesesAtras.getTime());
		turma1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turma1.setCurso(curso1);
		turmaDao.save(turma1);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma1);
		colaboradorTurmaAprovado.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);

		ColaboradorTurma colaboradorTurmaReprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaReprovado.setCurso(curso1);
		colaboradorTurmaReprovado.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurmaReprovado);

		ColaboradorTurma colaboradorTurmaReprovadoSemNota = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaReprovadoSemNota.setCurso(curso1);
		colaboradorTurmaReprovadoSemNota.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurmaReprovadoSemNota);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoColaboradorAprovado = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso1, 50.0);
		aproveitamentoAvaliacaoColaboradorAprovado.setAvaliacaoCurso(avaliacaoCurso1);
		aproveitamentoAvaliacaoColaboradorAprovado.setColaboradorTurma(colaboradorTurmaAprovado);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoColaboradorAprovado);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoColaboradorAprovado2 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso2, 30.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoColaboradorAprovado2);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado2 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso1, 49.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado2);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado4 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso2, 50.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado4);

		assertEquals(2, aproveitamentoAvaliacaoCursoDao.findReprovados(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa.getId()).size());
	}

	public void testFindAprovados()
	{
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso1.setMinimoAprovacao(50.0);
		avaliacaoCursoDao.save(avaliacaoCurso1);

		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setMinimoAprovacao(30.0);
		avaliacaoCursoDao.save(avaliacaoCurso2);

		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso1);
		avaliacaoCursos.add(avaliacaoCurso2);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso1);

		Turma turma1 = TurmaFactory.getEntity();
		turma1.setDataPrevIni(dataTresMesesAtras.getTime());
		turma1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turma1.setCurso(curso1);
		turmaDao.save(turma1);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		ColaboradorTurma colaboradorTurmaAprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaAprovado.setTurma(turma1);
		colaboradorTurmaAprovado.setColaborador(colaborador);
		colaboradorTurmaDao.save(colaboradorTurmaAprovado);

		ColaboradorTurma colaboradorTurmaReprovado = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaReprovado.setCurso(curso1);
		colaboradorTurmaReprovado.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurmaReprovado);

		ColaboradorTurma colaboradorTurmaReprovadoSemNota = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaReprovadoSemNota.setCurso(curso1);
		colaboradorTurmaReprovadoSemNota.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurmaReprovadoSemNota);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoColaboradorAprovado = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso1, 50.0);
		aproveitamentoAvaliacaoColaboradorAprovado.setAvaliacaoCurso(avaliacaoCurso1);
		aproveitamentoAvaliacaoColaboradorAprovado.setColaboradorTurma(colaboradorTurmaAprovado);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoColaboradorAprovado);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoColaboradorAprovado2 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaAprovado, avaliacaoCurso2, 30.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoColaboradorAprovado2);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado2 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso1, 49.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado2);

		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado4 = new AproveitamentoAvaliacaoCurso(colaboradorTurmaReprovado, avaliacaoCurso2, 50.0);
		aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCursoReprovado4);

		Collection<Long> idsComAvaliacao = cursoDao.findComAvaliacao(empresa.getId(), dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime());

		assertEquals(1, aproveitamentoAvaliacaoCursoDao.findAprovadosComAvaliacao(idsComAvaliacao, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime()).size());
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setAvaliacaoCursoDao(AvaliacaoCursoDao avaliacaoCursoDao)
	{
		this.avaliacaoCursoDao = avaliacaoCursoDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public void setCursoDao(CursoDao cursoDao)
	{
		this.cursoDao = cursoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}
