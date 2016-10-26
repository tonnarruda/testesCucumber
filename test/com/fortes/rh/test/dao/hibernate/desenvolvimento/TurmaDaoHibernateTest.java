package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;
import com.fortes.rh.test.factory.geral.TurmaTipoDespesaFactory;
import com.fortes.rh.util.DateUtil;

public class TurmaDaoHibernateTest extends GenericDaoHibernateTest<Turma>
{
	private TurmaDao turmaDao;
	private CursoDao cursoDao;
	private EmpresaDao empresaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private TurmaTipoDespesaDao turmaTipoDespesaDao;
	private TipoDespesaDao tipoDespesaDao;
	private ColaboradorPresencaDao colaboradorPresencaDao;
	private EstabelecimentoDao estabelecimentoDao;

	public Turma getEntity()
	{
		return TurmaFactory.getEntity();
	}
	public GenericDao<Turma> getGenericDao()
	{
		return turmaDao;
	}

	public void setTurmaDao(TurmaDao TurmaDao)
	{
		this.turmaDao = TurmaDao;
	}

	public void testGetTurmasFinalizadas()
	{
		Curso c1 = CursoFactory.getEntity();
		Curso c2 = CursoFactory.getEntity();

		c1 = cursoDao.save(c1);
		c2 = cursoDao.save(c2);

		Turma t1 = TurmaFactory.getEntity();
		t1.setRealizada(true);
		t1.setCurso(c1);

		Turma t2 = TurmaFactory.getEntity();
		t2.setRealizada(false);
		t2.setCurso(c1);

		Turma t3 = TurmaFactory.getEntity();
		t3.setRealizada(true);
		t3.setCurso(c2);

		t1 = turmaDao.save(t1);
		t2 = turmaDao.save(t2);
		t3 = turmaDao.save(t3);

		Collection<Turma> turmas = turmaDao.getTurmaFinalizadas(c1.getId());
		assertEquals(1, turmas.size());
	}

	@SuppressWarnings("deprecation")
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);

		Date dataPrevIni = new Date();
		Date dataPrevFim = dataPrevIni;
		dataPrevFim.setDate(dataPrevIni.getDate() + 1);

		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setCurso(curso);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turmaDao.save(turma);

		Turma turmaRetorno = turmaDao.findByIdProjection(turma.getId());

		assertEquals(turmaRetorno, turma);
	}

	public void testFindTurmas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa);
		cursoDao.save(curso);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso);
		turma1.setEmpresa(empresa);
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso);
		turma2.setEmpresa(empresa);
		turmaDao.save(turma2);
		
		Turma turma3 = TurmaFactory.getEntity();
		turma3.setCurso(curso);
		turma3.setEmpresa(empresa);
		turmaDao.save(turma3);
		
		Collection<Turma> turmas = turmaDao.findTurmas(1, 2, curso.getId(), null);
		
		assertEquals(2, turmas.size());
	}

	public void testFindByIdProjectionArray()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setEmpresa(empresa);
		turma1.setCurso(curso);
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setCurso(curso);
		turmaDao.save(turma2);
		
		Long[] ids = new Long[]{turma1.getId(), turma2.getId()};
		
		Collection<Turma> turmas = turmaDao.findByIdProjection(ids);
		
		assertEquals(2, turmas.size());
	}

	public void testFindByTurmasRelatorioInvestimento() 
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso);
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso);
		turmaDao.save(turma2);
		
		Long[] ids = new Long[]{curso.getId()};
		
		assertEquals(2, turmaDao.findByCursos(ids).size());
	}
	
	public void testFindByTurmasPeriodo() 
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma1 = criaTurmaAndColaboradorTurma(curso, "02/01/2009", true);
		Turma turma2 = criaTurmaAndColaboradorTurma(curso, "02/01/2010", true);
		Turma turma3 = criaTurmaAndColaboradorTurma(curso, "02/01/2011", true);
		Turma turma4 = criaTurmaAndColaboradorTurma(curso, "02/01/2010", false);
		Turma turma5 = criaTurma(curso, "02/01/2010", true);
		
		Long[] turmaIds = new Long[]{turma1.getId(), turma2.getId(), turma3.getId(), turma4.getId(), turma5.getId()};
		
		assertEquals(2, turmaDao.findByTurmasPeriodo(turmaIds, DateUtil.montaDataByString("01/01/2010"), DateUtil.montaDataByString("05/05/2010"), true).size());
	}
	
	public void testFindByTurmasPeriodoComDespesa() 
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma1 = criaTurmaAndColaboradorTurma(curso, "04/04/2010", true);
		
		TipoDespesa tipoDespesa1 = TipoDespesaFactory.getEntity();
		tipoDespesa1.setDescricao("Professor");
		tipoDespesaDao.save(tipoDespesa1);		
		
		TurmaTipoDespesa turmaTipoDespesa1 = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa1.setTurma(turma1);
		turmaTipoDespesa1.setTipoDespesa(tipoDespesa1);
		turmaTipoDespesa1.setDespesa(5000.0);
		turmaTipoDespesaDao.save(turmaTipoDespesa1);
		
		TipoDespesa tipoDespesa2 = TipoDespesaFactory.getEntity();
		tipoDespesa2.setDescricao("Coffee");
		tipoDespesaDao.save(tipoDespesa2);		
		
		TurmaTipoDespesa turmaTipoDespesa2 = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa2.setTurma(turma1);
		turmaTipoDespesa2.setTipoDespesa(tipoDespesa2);
		turmaTipoDespesa2.setDespesa(2200.0);
		turmaTipoDespesaDao.save(turmaTipoDespesa2);
		
		Long[] turmaIds = new Long[]{turma1.getId()};
		
		Collection<Turma> turmas = turmaDao.findByTurmasPeriodo(turmaIds, DateUtil.montaDataByString("01/01/2010"), DateUtil.montaDataByString("05/05/2010"), true);
		assertEquals(2, turmas.size());
		
		Turma turmaDespesa1 = (Turma) turmas.toArray()[0];
		assertEquals("Coffee", turmaDespesa1.getTipoDespesaDescricao());
		assertEquals(2200.0, turmaDespesa1.getDespesaPorTipo());
		
		Turma turmaDespesa2 = (Turma) turmas.toArray()[1];
		assertEquals("Professor", turmaDespesa2.getTipoDespesaDescricao());
		assertEquals(5000.0, turmaDespesa2.getDespesaPorTipo());
	}

	private Turma criaTurmaAndColaboradorTurma(Curso curso, String ini, boolean realizada) 
	{
		Turma turma = criaTurma(curso, ini, realizada);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);

		return turma;
	}
	
	private Turma criaTurma(Curso curso, String ini, boolean realizada) 
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turma.setDataPrevIni(DateUtil.montaDataByString(ini));
		turma.setRealizada(realizada);
		turmaDao.save(turma);
		return turma;
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setCurso(curso);
		turmaDao.save(turma2);
		
		Collection<Turma> turmas = turmaDao.findAllSelect(curso.getId());
		
		assertEquals(2, turmas.size());
	}
	
	public void testFindPlanosDeTreinamentoTemVazio()
	{
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Curso curso = CursoFactory.getEntity();
		curso = cursoDao.save(curso);
		
		Collection<Turma> turmas = turmaDao.findPlanosDeTreinamento(1, 10, curso.getId(), dataPrevIni, dataPrevFim, true, null);
		
		assertEquals(0, turmas.size());
	}
	
	public void testQuantidadeParticipantesPrevistos()
	{
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);
		
		Empresa empresa1 = new Empresa();
		empresaDao.save(empresa1);		
		
		Empresa empresa2 = new Empresa();
		empresaDao.save(empresa2);		
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setQtdParticipantesPrevistos(2);
		turma1.setDataPrevIni(dataTresMesesAtras.getTime());
		turma1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turma1.setEmpresa(empresa1);
		turmaDao.save(turma1);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setQtdParticipantesPrevistos(3);
		turma2.setDataPrevIni(dataTresMesesAtras.getTime());
		turma2.setDataPrevFim(dataDoisMesesAtras.getTime());
		turma2.setEmpresa(empresa1);
		turmaDao.save(turma2);
		
		Turma turma3 = TurmaFactory.getEntity();
		turma3.setQtdParticipantesPrevistos(3);
		turma3.setDataPrevIni(dataTresMesesAtras.getTime());
		turma3.setDataPrevFim(dataDoisMesesAtras.getTime());
		turma3.setEmpresa(empresa2);
		turmaDao.save(turma3);
		
		assertEquals("Empresa 1", new Integer(5), turmaDao.quantidadeParticipantesPrevistos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[] {empresa1.getId()}, null));
		assertEquals("Empresa 2", new Integer(3), turmaDao.quantidadeParticipantesPrevistos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[] {empresa2.getId()}, null));
		assertEquals("Empresa 1 e 2", new Integer(8), turmaDao.quantidadeParticipantesPrevistos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[] {empresa1.getId(), empresa2.getId()}, null));
		assertEquals("Empresa desconhecida", new Integer(0), turmaDao.quantidadeParticipantesPrevistos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[] {1021L}, null));
	}
	
	public void testFindPlanosDeTreinamento()
	{
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setCurso(curso);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setRealizada(true);
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setCurso(curso);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setRealizada(false);
		turmaDao.save(turma2);
		
		assertEquals("Apenas realizada", turma.getId(), ((Turma) turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), dataPrevIni, dataPrevFim, true, null).toArray()[0]).getId());
		assertEquals("Apenas não realizada", turma2.getId(), ((Turma) turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), dataPrevIni, dataPrevFim, false, null).toArray()[0]).getId());
		assertEquals("Apenas data ini. no filtro", 2, (turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), DateUtil.criarDataMesAno(01, 02, 2000), DateUtil.criarDataMesAno(01, 01, 2002), null, null).size()));
		assertEquals("Apenas data fin. no filtro", 2, (turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), DateUtil.criarDataMesAno(01, 12, 1999), DateUtil.criarDataMesAno(01, 01, 2000), null, null).size()));
		assertEquals("Data ini. e fin. fora do filtro", 2, (turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), DateUtil.criarDataMesAno(01, 12, 1999), DateUtil.criarDataMesAno(01, 01, 2002), null, null).size()));
		assertEquals("Data ini. e fin. antes do filtro", 0, (turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), DateUtil.criarDataMesAno(01, 11, 1999), DateUtil.criarDataMesAno(31, 12, 1999), null, null).size()));
		assertEquals("Data ini. e fin. depois do filtro", 0, (turmaDao.findPlanosDeTreinamento(1, 10,  curso.getId(), DateUtil.criarDataMesAno(02, 01, 2001), DateUtil.criarDataMesAno(31, 12, 2001), null, null).size()));
	}
	
	public void testCountPlanosDeTreinamento()
	{
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setCurso(curso);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setRealizada(true);
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setCurso(curso);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setRealizada(false);
		turmaDao.save(turma2);
		
		assertEquals(new Integer(1), turmaDao.countPlanosDeTreinamento( curso.getId(), dataPrevIni, dataPrevFim, true));
	}

	public void testUpdateRealizada() throws Exception
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Turma turma = TurmaFactory.getEntity();
		turma.setRealizada(true);
		turma.setEmpresa(empresa);
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		turmaDao.updateRealizada(turma.getId(), false);
		
		Turma retorno = turmaDao.findByIdProjection(turma.getId());
		assertEquals(false, retorno.getRealizada());
	}
	
	public void testUpdateCusto() throws Exception
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setCurso(curso);
		turma.setCusto(2.0);
		turmaDao.save(turma);
		
		turmaDao.updateCusto(turma.getId(), 55.66);
		
		Turma retorno = turmaDao.findByIdProjection(turma.getId());
		assertEquals(55.66, retorno.getCusto());
	}
	
	public void testfindByFiltro() throws Exception
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa1);
		cursoDao.save(curso);

		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa2);
		cursoDao.save(curso2);
		
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Turma turmaRealizada = TurmaFactory.getEntity();
		turmaRealizada.setDescricao("t1");
		turmaRealizada.setRealizada(true);
		turmaRealizada.setDataPrevIni(dataPrevIni);
		turmaRealizada.setDataPrevFim(dataPrevFim);
		turmaRealizada.setEmpresa(empresa1);
		turmaRealizada.setCurso(curso);
		turmaDao.save(turmaRealizada);
		
		Turma turmaNaoRealizada1 = TurmaFactory.getEntity();
		turmaNaoRealizada1.setDescricao("t2");
		turmaNaoRealizada1.setRealizada(false);
		turmaNaoRealizada1.setDataPrevIni(dataPrevIni);
		turmaNaoRealizada1.setDataPrevFim(dataPrevFim);
		turmaNaoRealizada1.setEmpresa(empresa1);
		turmaNaoRealizada1.setCurso(curso2);
		turmaDao.save(turmaNaoRealizada1);
		
		Turma turmaNaoRealizada2 = TurmaFactory.getEntity();
		turmaNaoRealizada2.setDescricao("t3");
		turmaNaoRealizada2.setRealizada(false);
		turmaNaoRealizada2.setDataPrevIni(dataPrevIni);
		turmaNaoRealizada2.setDataPrevFim(dataPrevFim);
		turmaNaoRealizada2.setEmpresa(empresa2);
		turmaNaoRealizada2.setCurso(curso);
		turmaDao.save(turmaNaoRealizada2);
		
		Date hoje = new Date();
		Turma turmaForaPeriodo = TurmaFactory.getEntity();
		turmaForaPeriodo.setDescricao("t4");
		turmaForaPeriodo.setRealizada(false);
		turmaForaPeriodo.setDataPrevIni(hoje);
		turmaForaPeriodo.setDataPrevFim(hoje);
		turmaForaPeriodo.setEmpresa(empresa1);
		turmaForaPeriodo.setCurso(curso);
		turmaDao.save(turmaForaPeriodo);
		
		Collection<Turma> turmasRealizadasE1 = turmaDao.findByFiltro(dataPrevIni, dataPrevFim, true, new Long[]{empresa1.getId()}, null);
		Collection<Turma> turmasNaoRealizadasE1 = turmaDao.findByFiltro(dataPrevIni, dataPrevFim, false, new Long[]{empresa1.getId()}, null);
		Collection<Turma> turmasNaoRealizadasE2 = turmaDao.findByFiltro(dataPrevIni, dataPrevFim, false, new Long[]{empresa2.getId()}, null);
		Collection<Turma> turmasNaoRealizadasTotal = turmaDao.findByFiltro(dataPrevIni, dataPrevFim, false, new Long[]{empresa1.getId(), empresa2.getId()}, null);
		
		assertEquals("Empresa 1", 1, turmasRealizadasE1.size());
		assertEquals("Empresa 1", 1, turmasNaoRealizadasE1.size());
		assertEquals("Empresa 2", 1, turmasNaoRealizadasE2.size());
		assertEquals("Empresa 1 e 2", 2, turmasNaoRealizadasTotal.size());
	}

	public void setCursoDao(CursoDao cursoDao)
	{
		this.cursoDao = cursoDao;
	}

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorio()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("fortes");
		empresa.setCnpj("65465");
		empresa.setRazaoSocial("fortes");

		empresa = empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setId(null);
		areaOrganizacional.setNome("area");
		areaOrganizacional.setEmpresa(empresa);

		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional areaOrganizacional2 = new AreaOrganizacional();
		areaOrganizacional2.setId(null);
		areaOrganizacional2.setNome("area2");
		areaOrganizacional2.setEmpresa(empresa);

		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Colaborador colaborador = ColaboradorFactory.getEntity();

		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador hc0 = HistoricoColaboradorFactory.getEntity();
		hc0.setData(DateUtil.criarDataMesAno(1, 3, 2006));
		hc0.setMotivo("p");
		hc0.setSalario(1D);
		hc0.setColaborador(colaborador);
		hc0.setAreaOrganizacional(areaOrganizacional);

		historicoColaboradorDao.save(hc0);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2 = colaboradorDao.save(colaborador2);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setData(DateUtil.criarDataMesAno(1, 4, 2006));
		hc1.setMotivo("p");
		hc1.setSalario(1D);
		hc1.setColaborador(colaborador2);
		hc1.setAreaOrganizacional(areaOrganizacional2);

		historicoColaboradorDao.save(hc1);

		Curso c1 = CursoFactory.getEntity();
		c1.setNome("curso1");

		Curso c2 = CursoFactory.getEntity();
		c2.setNome("curso2");

		c1 = cursoDao.save(c1);
		c2 = cursoDao.save(c2);

		Turma t1 = TurmaFactory.getEntity();
		t1.setCurso(c1);
		t1.setCusto(1000D);
		t1.setDataPrevFim(DateUtil.criarDataMesAno(5, 4, 2007));

		Turma t2 = TurmaFactory.getEntity();
		t2.setCurso(c1);
		t2.setCusto(500D);
		t2.setDataPrevFim(DateUtil.criarDataMesAno(8, 5, 2007));

		t1 = turmaDao.save(t1);
		t2 = turmaDao.save(t2);

		ColaboradorTurma ct1 = new ColaboradorTurma();
		ct1.setColaborador(colaborador);
		ct1.setTurma(t1);
		ct1 = colaboradorTurmaDao.save(ct1);

		ColaboradorTurma ct2 = new ColaboradorTurma();
		ct2.setColaborador(colaborador2);
		ct2.setTurma(t1);
		ct2 = colaboradorTurmaDao.save(ct2);

		ColaboradorTurma ct3 = new ColaboradorTurma();
		ct3.setColaborador(colaborador);
		ct3.setTurma(t2);
		ct3 = colaboradorTurmaDao.save(ct3);

		Collection<Long> areasId = new ArrayList<Long>();
		areasId.add(areaOrganizacional.getId());

		LinkedHashMap filtro = new LinkedHashMap();
		filtro.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2007));
		filtro.put("dataFim", DateUtil.criarDataMesAno(01, 06, 2007));
		filtro.put("areas", areasId);
		filtro.put("colaborador", null);

		List retorno = turmaDao.filtroRelatorioByAreas(filtro);

		assertEquals(2, retorno.size());

		filtro.put("colaborador", colaborador2);

		retorno = turmaDao.filtroRelatorioByColaborador(filtro);

		assertEquals(1, retorno.size());
	}

	public void testQuantidadeParticipantesPrevistoso()
	{
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa1);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa1);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma2);

		Turma turma3 = TurmaFactory.getEntity();
		turma3.setEmpresa(empresa2);
		turma3.setDataPrevIni(dataPrevIni);
		turma3.setDataPrevFim(dataPrevFim);
		turma3.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma3);
		
		assertEquals("Empresa 1", new Integer (4), turmaDao.quantidadeParticipantesPrevistos(dataPrevIni, dataPrevFim, new Long[]{empresa1.getId()}, null));
		assertEquals("Empresa 2", new Integer (2), turmaDao.quantidadeParticipantesPrevistos(dataPrevIni, dataPrevFim, new Long[]{empresa2.getId()}, null));
		assertEquals("Empresa 1 e 2", new Integer (6), turmaDao.quantidadeParticipantesPrevistos(dataPrevIni, dataPrevFim, new Long[]{empresa1.getId(), empresa2.getId()}, null));
	}
	
	public void testQuantidadeParticipantesPresentesFiltroEstabelecimento(){
		Date data = DateUtil.criarDataMesAno(01, 01, 1999);
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 02, 2000);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador c1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(c1);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);
		
		HistoricoColaborador hc1 = new HistoricoColaborador();
		hc1.setColaborador(c1);
		hc1.setData(data);
		hc1.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(hc1);
		
		Colaborador c2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(c2);
		
		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setColaborador(c2);
		hc2.setData(data);
		hc2.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(hc2);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma2);

		Turma turma3 = TurmaFactory.getEntity();
		turma3.setEmpresa(empresa);
		turma3.setDataPrevIni(dataPrevIni);
		turma3.setDataPrevFim(dataPrevFim);
		turma3.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma3);
		
		ColaboradorTurma ct1 = new ColaboradorTurma();
		ct1.setTurma(turma);
		ct1.setColaborador(c1);
		colaboradorTurmaDao.save(ct1);
		
		ColaboradorTurma ct2 = new ColaboradorTurma();
		ct2.setTurma(turma2);
		ct2.setColaborador(c1);
		colaboradorTurmaDao.save(ct2);
		
		ColaboradorTurma ct3 = new ColaboradorTurma();
		ct3.setTurma(turma3);
		ct3.setColaborador(c2);
		colaboradorTurmaDao.save(ct3);
		
		assertEquals("Nenhuma presenca", new Integer (0), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, new Long[]{estabelecimento.getId()}));
		
		ColaboradorPresenca cp11 = new ColaboradorPresenca();
		cp11.setColaboradorTurma(ct1);
		cp11.setPresenca(true);
		colaboradorPresencaDao.save(cp11);

		ColaboradorPresenca cp12 = new ColaboradorPresenca();
		cp12.setColaboradorTurma(ct1);
		cp12.setPresenca(true);
		colaboradorPresencaDao.save(cp12);

		assertEquals("Duas presencas na mesma turma", new Integer (1), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, new Long[]{estabelecimento.getId()}));
		
		ColaboradorPresenca cp13 = new ColaboradorPresenca();
		cp13.setColaboradorTurma(ct2);
		cp13.setPresenca(true);
		colaboradorPresencaDao.save(cp13);
		
		assertEquals("Presencas em duas turmas para o mesmo colaborador", new Integer (2), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, new Long[]{estabelecimento.getId()}));
		
		ColaboradorPresenca cp21 = new ColaboradorPresenca();
		cp21.setColaboradorTurma(ct3);
		cp21.setPresenca(true);
		colaboradorPresencaDao.save(cp21);
		
		assertEquals("Outro colaborador", new Integer (3), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, new Long[]{estabelecimento.getId()}));
	}
	
	public void testQuantidadeParticipantesPresentes(){
		Date data = DateUtil.criarDataMesAno(01, 01, 1999);
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 02, 2000);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador c1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(c1);
		
		HistoricoColaborador hc1 = new HistoricoColaborador();
		hc1.setColaborador(c1);
		hc1.setData(data);
		historicoColaboradorDao.save(hc1);
		
		Colaborador c2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(c2);
		
		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setColaborador(c2);
		hc2.setData(data);
		historicoColaboradorDao.save(hc2);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma2);

		Turma turma3 = TurmaFactory.getEntity();
		turma3.setEmpresa(empresa);
		turma3.setDataPrevIni(dataPrevIni);
		turma3.setDataPrevFim(dataPrevFim);
		turma3.setQtdParticipantesPrevistos(new Integer(2));
		turmaDao.save(turma3);
		
		ColaboradorTurma ct1 = new ColaboradorTurma();
		ct1.setTurma(turma);
		ct1.setColaborador(c1);
		colaboradorTurmaDao.save(ct1);
		
		ColaboradorTurma ct2 = new ColaboradorTurma();
		ct2.setTurma(turma2);
		ct2.setColaborador(c1);
		colaboradorTurmaDao.save(ct2);
		
		ColaboradorTurma ct3 = new ColaboradorTurma();
		ct3.setTurma(turma3);
		ct3.setColaborador(c2);
		colaboradorTurmaDao.save(ct3);
		
		assertEquals("Nenhuma presenca", new Integer (0), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, null));
		
		ColaboradorPresenca cp11 = new ColaboradorPresenca();
		cp11.setColaboradorTurma(ct1);
		cp11.setPresenca(true);
		colaboradorPresencaDao.save(cp11);

		ColaboradorPresenca cp12 = new ColaboradorPresenca();
		cp12.setColaboradorTurma(ct1);
		cp12.setPresenca(true);
		colaboradorPresencaDao.save(cp12);

		assertEquals("Duas presencas na mesma turma", new Integer (1), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, null));
		
		ColaboradorPresenca cp13 = new ColaboradorPresenca();
		cp13.setColaboradorTurma(ct2);
		cp13.setPresenca(true);
		colaboradorPresencaDao.save(cp13);
		
		assertEquals("Presencas em duas turmas para o mesmo colaborador", new Integer (2), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, null));
		
		ColaboradorPresenca cp21 = new ColaboradorPresenca();
		cp21.setColaboradorTurma(ct3);
		cp21.setPresenca(true);
		colaboradorPresencaDao.save(cp21);
		
		assertEquals("Outro colaborador", new Integer (3), turmaDao.quantidadeParticipantesPresentes(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null, null, null));
	}
	
	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Curso java = CursoFactory.getEntity();
		java.setEmpresa(empresa);
		cursoDao.save(java);
		
		Turma turmaA = TurmaFactory.getEntity();
		turmaA.setCurso(java);
		turmaA.setEmpresa(empresa);
		turmaDao.save(turmaA);
		
		Curso delphi = CursoFactory.getEntity();
		delphi.setEmpresa(empresa);
		cursoDao.save(delphi);

		Turma turmaC = TurmaFactory.getEntity();
		turmaC.setCurso(delphi);
		turmaC.setEmpresa(empresa);
		turmaDao.save(turmaC);
		
		Turma turmaB = TurmaFactory.getEntity();
		turmaB.setCurso(java);
		turmaB.setEmpresa(empresa);
		turmaDao.save(turmaB);

		Collection<Turma> turmas = turmaDao.findByEmpresaOrderByCurso(empresa.getId());

		assertEquals(3, turmas.size());
		
		Turma[] turmasArray = turmas.toArray(new Turma[3]);
		assertEquals(turmaA, turmasArray[0]);
		assertEquals(turmaC, turmasArray[2]);
	}
	
	public void testSomaCustosNaoDetalhados()
	{
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setCusto(15.50);
		turma.setRealizada(true);
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setCusto(13.75);
		turma2.setRealizada(true);
		turmaDao.save(turma2);

		Turma turma3 = TurmaFactory.getEntity();
		turma3.setEmpresa(empresa);
		turma3.setDataPrevIni(dataPrevIni);
		turma3.setDataPrevFim(dataPrevFim);
		turma3.setCusto(50.00);
		turma3.setRealizada(true);
		turmaDao.save(turma3);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity();
		tipoDespesaDao.save(tipoDespesa);		
		
		TurmaTipoDespesa turmaTipoDespesa = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa.setTurma(turma3);
		turmaTipoDespesa.setTipoDespesa(tipoDespesa);
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		
		Turma turmaOutraEmpresa = TurmaFactory.getEntity();
		turmaOutraEmpresa.setEmpresa(empresa2);
		turmaOutraEmpresa.setDataPrevIni(dataPrevIni);
		turmaOutraEmpresa.setDataPrevFim(dataPrevFim);
		turmaOutraEmpresa.setCusto(10.15);
		turmaOutraEmpresa.setRealizada(true);
		turmaDao.save(turmaOutraEmpresa);
		
		assertEquals("Empresa 1", 29.25, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null));
		assertEquals("Empresa 2",10.15, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{empresa2.getId()}, null));
		assertEquals("Empresa 1 e 2",39.40, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{empresa.getId(), empresa2.getId()}, null));
		assertEquals("Empresa desconhecida",0.0, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{999934L}, null));

//		assertEquals(29.25, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, empresa.getId()));
//		assertEquals(0.0, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, 999934L));
	}

	public void testSomaCustos()
	{
		Date dataPrevIni = DateUtil.criarDataMesAno(01, 01, 2000);
		Date dataPrevFim = DateUtil.criarDataMesAno(01, 01, 2001);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setDataPrevIni(dataPrevIni);
		turma.setDataPrevFim(dataPrevFim);
		turma.setCusto(15.50);
		turma.setRealizada(true);
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa);
		turma2.setDataPrevIni(dataPrevIni);
		turma2.setDataPrevFim(dataPrevFim);
		turma2.setCusto(13.75);
		turma2.setRealizada(true);
		turmaDao.save(turma2);
		
		Turma turma3 = TurmaFactory.getEntity();
		turma3.setEmpresa(empresa);
		turma3.setDataPrevIni(dataPrevIni);
		turma3.setDataPrevFim(dataPrevFim);
		turma3.setCusto(50.00);
		turma3.setRealizada(true);
		turmaDao.save(turma3);
				
		Turma turma4 = TurmaFactory.getEntity();
		turma4.setEmpresa(empresa2);
		turma4.setDataPrevIni(dataPrevIni);
		turma4.setDataPrevFim(dataPrevFim);
		turma4.setCusto(10.15);
		turma4.setRealizada(true);
		turmaDao.save(turma4);
		
		assertEquals("Empresa 1", 79.25, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{empresa.getId()}, null));
		assertEquals("Empresa 2",10.15, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{empresa2.getId()}, null));
		assertEquals("Empresa 1 e 2",89.40, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{empresa.getId(), empresa2.getId()}, null));
		assertEquals("Empresa desconhecida",0.0, turmaDao.somaCustosNaoDetalhados(dataPrevIni, dataPrevFim, new Long[]{999934L}, null));
	}
	
	public void testGetTurmasByCursoNotTurmaId(){
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma1 = TurmaFactory.getEntity(curso, true);
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity(curso, false);
		turmaDao.save(turma2);
		
		Turma turma3 = TurmaFactory.getEntity(curso, false);
		turmaDao.save(turma3);
		
		Collection<Turma> turmas = turmaDao.getTurmasByCursoNotTurmaId(curso.getId(), turma3.getId());
		assertEquals(2, turmas.size());
	}
	
	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}
	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}
	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}
	public void setTurmaTipoDespesaDao(TurmaTipoDespesaDao turmaTipoDespesaDao) {
		this.turmaTipoDespesaDao = turmaTipoDespesaDao;
	}
	public void setTipoDespesaDao(TipoDespesaDao tipoDespesaDao) {
		this.tipoDespesaDao = tipoDespesaDao;
	}
	public void setColaboradorPresencaDao(
			ColaboradorPresencaDao colaboradorPresencaDao) {
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}
	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}
}