package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class CursoDaoHibernateTest extends GenericDaoHibernateTest<Curso>
{
	private CursoDao cursoDao;
	private EmpresaDao empresaDao;
	private CertificacaoDao certificacaoDao;
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private TurmaDao turmaDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;

	public Curso getEntity()
	{
		Curso curso = new Curso();

		curso.setId(null);

		return curso;
	}
	public GenericDao<Curso> getGenericDao()
	{
		return cursoDao;
	}

	public void setCursoDao(CursoDao CursoDao)
	{
		this.cursoDao = CursoDao;
	}
	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}
	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void testGetCursosTerminados()
	{
		Empresa empresa1 = new Empresa();
		empresa1.setNome("empresa");
		empresa1.setCnpj("21212121212");
		empresa1.setRazaoSocial("empresa");
		empresa1 = empresaDao.save(empresa1);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Curso c1 = new Curso();
		c1.setId(null);
		c1.setEmpresa(empresa1);
		c1.setNome("curso a");
		cursoDao.save(c1);

		Curso c2 = new Curso();
		c2.setId(null);
		c2.setEmpresa(empresa1);
		c2.setNome("curso a");
		cursoDao.save(c2);

		Curso c3 = new Curso();
		c3.setId(null);
		c3.setEmpresa(empresa1);
		c3.setNome("curso a");
		cursoDao.save(c3);

		Curso c4 = new Curso();
		c4.setId(null);
		c4.setEmpresa(empresa2);
		c4.setNome("curso a");
		cursoDao.save(c4);

		Collection<Curso> cursos = cursoDao.findAllSelect(empresa1.getId());

		assertEquals(3, cursos.size());

		cursos = cursoDao.findAllSelect(empresa2.getId());

		assertEquals(1, cursos.size());

	}

	public void testFindByIdProjection()
	{
		Curso curso = CursoFactory.getEntity();
		curso = cursoDao.save(curso);

		assertEquals(curso, cursoDao.findByIdProjection(curso.getId()));

	}

	public void testFindByCertificacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);

		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setPercentualMinimoFrequencia(70D);
		curso1.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso1);

		Curso curso2 = CursoFactory.getEntity();
		cursoDao.save(curso2);

		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso1);
		cursos.add(curso2);

		Certificacao certificacao1 = CertificacaoFactory.getEntity();
		certificacao1.setEmpresa(empresa);
		certificacao1.setCursos(cursos);
		certificacaoDao.save(certificacao1);

		assertEquals(2, cursoDao.findByCertificacao(certificacao1.getId()).size());

	}

	public void testGetConteudoProgramatico()
	{
		Curso curso = CursoFactory.getEntity();
		curso.setConteudoProgramatico("Teste conteudo");
		curso = cursoDao.save(curso);

		assertEquals(curso.getConteudoProgramatico(), cursoDao.getConteudoProgramatico(curso.getId()));

	}

	public void testFindSomaCustoEHorasTreinamentos()
	{
    	Calendar dataDoisMesesDepois = Calendar.getInstance();
    	dataDoisMesesDepois.add(Calendar.MONTH, +2);
    	Calendar dataTresMesesDepois = Calendar.getInstance();
    	dataTresMesesDepois.add(Calendar.MONTH, +3);
    	Calendar dataQuatroMesesDepois = Calendar.getInstance();
    	dataQuatroMesesDepois.add(Calendar.MONTH, +4);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setCargaHoraria(20);
		cursoDao.save(curso1);
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		turma1.setCusto(200.0);
		turma1.setDataPrevIni(dataDoisMesesDepois.getTime());
		turma1.setDataPrevFim(dataTresMesesDepois.getTime());
		turmaDao.save(turma1);

		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa);
		curso2.setCargaHoraria(40);
		cursoDao.save(curso2);
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso2);
		turma2.setCusto(3512.69);
		turma2.setDataPrevIni(dataDoisMesesDepois.getTime());
		turma2.setDataPrevFim(dataTresMesesDepois.getTime());
		turmaDao.save(turma2);

		Turma turmaForaDaConsulta = TurmaFactory.getEntity();
		turmaForaDaConsulta.setCurso(curso2);
		turmaForaDaConsulta.setCusto(9582.00);
		turmaForaDaConsulta.setDataPrevIni(dataTresMesesDepois.getTime());
		turmaForaDaConsulta.setDataPrevFim(dataQuatroMesesDepois.getTime());
		turmaDao.save(turmaForaDaConsulta);

		IndicadorTreinamento result = cursoDao.findSomaCustoEHorasTreinamentos(dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), empresa.getId());
		assertNotNull(result);
		assertEquals(3712.69, result.getSomaCustos());
		assertEquals(new Integer(60), result.getSomaHoras());
	}

	public void testFindQtdColaboradoresInscritosTreinamentos()
	{
		Calendar dataDoisMesesDepois = Calendar.getInstance();
    	dataDoisMesesDepois.add(Calendar.MONTH, +2);
    	Calendar dataTresMesesDepois = Calendar.getInstance();
    	dataTresMesesDepois.add(Calendar.MONTH, +3);
    	Calendar dataQuatroMesesDepois = Calendar.getInstance();
    	dataQuatroMesesDepois.add(Calendar.MONTH, +4);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setCargaHoraria(20);
		cursoDao.save(curso1);
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		turma1.setCusto(200.0);
		turma1.setDataPrevIni(dataDoisMesesDepois.getTime());
		turma1.setDataPrevFim(dataTresMesesDepois.getTime());
		turmaDao.save(turma1);

		Turma turmaForaDaConsulta = TurmaFactory.getEntity();
		turmaForaDaConsulta.setCurso(curso1);
		turmaForaDaConsulta.setDataPrevIni(dataTresMesesDepois.getTime());
		turmaForaDaConsulta.setDataPrevFim(dataQuatroMesesDepois.getTime());
		turmaDao.save(turmaForaDaConsulta);

		ColaboradorTurma colaboradorInscrito1 = ColaboradorTurmaFactory.getEntity();
		colaboradorInscrito1.setCurso(curso1);
		colaboradorInscrito1.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorInscrito1);
		ColaboradorTurma colaboradorInscrito2 = ColaboradorTurmaFactory.getEntity();
		colaboradorInscrito2.setCurso(curso1);
		colaboradorInscrito2.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorInscrito2);

		ColaboradorTurma colaboradorForaDoPeriodo = ColaboradorTurmaFactory.getEntity();
		colaboradorForaDoPeriodo.setCurso(curso1);
		colaboradorForaDoPeriodo.setTurma(turmaForaDaConsulta);
		colaboradorTurmaDao.save(colaboradorForaDoPeriodo);

		Integer qtd = cursoDao.findQtdColaboradoresInscritosTreinamentos(dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), empresa.getId());
		assertNotNull(qtd);
		assertEquals(new Integer(2), qtd);
	}

	public void testFindSomaColaboradoresPrevistosTreinamentos()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesDepois = Calendar.getInstance();
    	dataDoisMesesDepois.add(Calendar.MONTH, +2);

    	Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setCargaHoraria(20);
		cursoDao.save(curso1);

		Curso cursoOutraEmpresa = CursoFactory.getEntity();
		cursoOutraEmpresa.setEmpresa(outraEmpresa);
		cursoOutraEmpresa.setCargaHoraria(20);
		cursoDao.save(cursoOutraEmpresa);

		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		turma1.setQtdParticipantesPrevistos(30);
		turma1.setDataPrevIni(hoje);
		turma1.setDataPrevFim(dataDoisMesesDepois.getTime());
		turmaDao.save(turma1);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso1);
		turma2.setQtdParticipantesPrevistos(170);
		turma2.setDataPrevIni(hoje);
		turma2.setDataPrevFim(dataDoisMesesDepois.getTime());
		turmaDao.save(turma2);

		Turma turmaOutraEmpresa = TurmaFactory.getEntity();
		turmaOutraEmpresa.setCurso(cursoOutraEmpresa);
		turmaOutraEmpresa.setQtdParticipantesPrevistos(195);
		turmaOutraEmpresa.setDataPrevIni(hoje);
		turmaOutraEmpresa.setDataPrevFim(dataDoisMesesDepois.getTime());
		turmaDao.save(turmaOutraEmpresa);

		Integer soma = cursoDao.findSomaColaboradoresPrevistosTreinamentos(hoje, dataDoisMesesDepois.getTime(), empresa.getId());

		assertEquals(new Integer(200), soma);
	}

	public void testCountTreinamentosRealizados()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Calendar dataEntreTresEDoisMesesAtras = Calendar.getInstance();
    	dataEntreTresEDoisMesesAtras.add(Calendar.MONTH, -3);
    	dataEntreTresEDoisMesesAtras.add(Calendar.WEEK_OF_MONTH, +1);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		cursoDao.save(curso1);

		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa);
		cursoDao.save(curso2);

		Turma turmaRealizada1 = TurmaFactory.getEntity();
		turmaRealizada1.setCurso(curso1);
		turmaRealizada1.setRealizada(true);
		turmaRealizada1.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaRealizada1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaRealizada1);

		Turma turmaRealizada2 = TurmaFactory.getEntity();
		turmaRealizada2.setCurso(curso2);
		turmaRealizada2.setRealizada(true);
		turmaRealizada2.setDataPrevIni(dataEntreTresEDoisMesesAtras.getTime());
		turmaRealizada2.setDataPrevFim(dataEntreTresEDoisMesesAtras.getTime());
		turmaDao.save(turmaRealizada2);

		Turma turmaNaoRealizada = TurmaFactory.getEntity();
		turmaNaoRealizada.setCurso(curso1);
		turmaNaoRealizada.setRealizada(false);
		turmaNaoRealizada.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaNaoRealizada.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaNaoRealizada);

		Turma turmaRealizadaForaDoPeriodo = TurmaFactory.getEntity();
		turmaRealizadaForaDoPeriodo.setCurso(curso1);
		turmaRealizadaForaDoPeriodo.setRealizada(true);
		turmaRealizadaForaDoPeriodo.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaRealizadaForaDoPeriodo.setDataPrevFim(hoje);
		turmaDao.save(turmaRealizadaForaDoPeriodo);

		assertEquals(new Integer(2), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa.getId(), true));

	}

	public void testCountTreinamentosNaoRealizados()
	{
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		cursoDao.save(curso1);

		Turma turmaRealizada = TurmaFactory.getEntity();
		turmaRealizada.setCurso(curso1);
		turmaRealizada.setRealizada(true);
		turmaRealizada.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaRealizada.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaRealizada);

		Turma turmaNaoRealizada = TurmaFactory.getEntity();
		turmaNaoRealizada.setCurso(curso1);
		turmaNaoRealizada.setRealizada(false);
		turmaNaoRealizada.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaNaoRealizada.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaNaoRealizada);

		assertEquals(new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa.getId(), false));

	}

	public void testFindComAvaliacao()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);

		Curso cursoComAvaliacao = CursoFactory.getEntity();
		cursoComAvaliacao.setEmpresa(empresa);
		cursoComAvaliacao.setAvaliacaoCursos(new ArrayList<AvaliacaoCurso>());
		cursoComAvaliacao.getAvaliacaoCursos().add(avaliacaoCurso);
		cursoDao.save(cursoComAvaliacao);

		Curso cursoSemAvaliacao = CursoFactory.getEntity();
		cursoSemAvaliacao.setEmpresa(empresa);
		cursoDao.save(cursoSemAvaliacao);

		Turma turmaForaDoPeriodo = TurmaFactory.getEntity();
		turmaForaDoPeriodo.setCurso(cursoComAvaliacao);
		turmaForaDoPeriodo.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaForaDoPeriodo.setDataPrevFim(hoje);
		turmaDao.save(turmaForaDoPeriodo);

		Turma turmaComAvaliacao1 = TurmaFactory.getEntity();
		turmaComAvaliacao1.setCurso(cursoComAvaliacao);
		turmaComAvaliacao1.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaComAvaliacao1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaComAvaliacao1);

		Turma turmaSemAvaliacao = TurmaFactory.getEntity();
		turmaSemAvaliacao.setCurso(cursoSemAvaliacao);
		turmaSemAvaliacao.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaSemAvaliacao.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaSemAvaliacao);

		assertEquals(1, cursoDao.findComAvaliacao(empresa.getId(), dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime()).size());
	}

	public void testFindByFiltro()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(empresa);
		cursoDao.save(curso);

		Curso cursoFiltroBusca = new Curso();
		cursoFiltroBusca.setNome("Direção");

		assertEquals(1, cursoDao.findByFiltro(1, 1000, cursoFiltroBusca, empresa.getId()).size());
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(empresa);
		cursoDao.save(curso);

		Curso cursoFiltroBusca = new Curso();
		cursoFiltroBusca.setNome("Filtro errado");

		assertEquals((Integer)0, cursoDao.getCount(cursoFiltroBusca, empresa.getId()));
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	public void setCertificacaoDao(CertificacaoDao certificacaoDao)
	{
		this.certificacaoDao = certificacaoDao;
	}
	public void setAvaliacaoCursoDao(AvaliacaoCursoDao avaliacaoCursoDao)
	{
		this.avaliacaoCursoDao = avaliacaoCursoDao;
	}
}