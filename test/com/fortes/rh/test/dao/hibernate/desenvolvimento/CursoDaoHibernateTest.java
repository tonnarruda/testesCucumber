package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class CursoDaoHibernateTest extends GenericDaoHibernateTest<Curso>
{
	private CursoDao cursoDao;
	private EmpresaDao empresaDao;
	private CertificacaoDao certificacaoDao;
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private TurmaDao turmaDao;
	private ColaboradorDao colaboradorDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private ConhecimentoDao conhecimentoDao;
	private HabilidadeDao habilidadeDao;
	private AtitudeDao atitudeDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private DiaTurmaDao diaTurmaDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao; 
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao; 
	private EstabelecimentoDao estabelecimentoDao;
	private ColaboradorPresencaDao colaboradorPresencaDao;

	private Curso curso;
	private Empresa empresa;
	private Colaborador colaborador;
	private HistoricoColaborador historicoColaborador;
	private AreaOrganizacional areaOrganizacional;
	private Estabelecimento estabelecimento;
	
	public Curso getEntity()
	{
		Curso curso = new Curso();
		curso.setNome("Curso");
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
		c1.setEmpresa(empresa1);
		c1.setNome("curso a");
		cursoDao.save(c1);

		Curso c2 = new Curso();
		c2.setEmpresa(empresa1);
		c2.setNome("curso a");
		cursoDao.save(c2);

		Curso c3 = new Curso();
		c3.setEmpresa(empresa1);
		c3.setNome("curso a");
		cursoDao.save(c3);

		Curso c4 = new Curso();
		c4.setEmpresa(empresa2);
		c4.setNome("curso a");
		cursoDao.save(c4);

		Collection<Curso> cursos = cursoDao.findAllSelect(empresa1.getId());

		assertEquals(3, cursos.size());

		cursos = cursoDao.findAllSelect(empresa2.getId());

		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findAllSelect(-1L);
		assertTrue(cursos.size() >= 4);
		
		cursos = cursoDao.findAllSelect(null);
		assertTrue(cursos.size() >= 4);
	}

	public void testFindByIdProjection()
	{
		Curso curso = CursoFactory.getEntity();
		curso = cursoDao.save(curso);

		assertEquals(curso, cursoDao.findByIdProjection(curso.getId()));

	}
	
	public void testFindByIdProjectionByIds()
	{
		Curso curso1 = CursoFactory.getEntity();
		curso1 = cursoDao.save(curso1);
		
		Curso curso2 = CursoFactory.getEntity();
		curso2 = cursoDao.save(curso2);

		Curso curso3 = CursoFactory.getEntity();
		curso3 = cursoDao.save(curso3);
		Long[] cursoIds = new Long[]{ curso1.getId(), curso2.getId(), curso3.getId() };
		assertTrue(cursoDao.findByIdProjection(cursoIds).size() == 3);

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
	
	public void testSomaCustosTreinamentos()
	{
		Calendar dataDoisMesesDepois = Calendar.getInstance();
		dataDoisMesesDepois.add(Calendar.MONTH, +2);
		Calendar dataTresMesesDepois = Calendar.getInstance();
		dataTresMesesDepois.add(Calendar.MONTH, +3);
		Calendar dataQuatroMesesDepois = Calendar.getInstance();
		dataQuatroMesesDepois.add(Calendar.MONTH, +4);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		saveTurma(curso1, dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), true, 200.0); 
		
		Curso curso2 = saveCurso("curso 2", empresa, 30*60); 
		saveTurma(curso2, dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), true, 3512.69);
		
		/** Esta turma está fora da consulta devido o período que a mesma foi realizada*/
		saveTurma(curso2, dataQuatroMesesDepois.getTime(), dataQuatroMesesDepois.getTime(), true, 9582.00);
		
		Double custoTotal = cursoDao.somaCustosTreinamentos(dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), new Long[]{empresa.getId()}, null);
		assertEquals("Custo das turmas", 3712.69, custoTotal);

		custoTotal = cursoDao.somaCustosTreinamentos(dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), new Long[]{empresa.getId()}, new Long[]{curso1.getId()});
		assertEquals("Custo das turmas", 200.0, custoTotal);
	}
	
	public void testFindIndicadorHorasTreinamentos()
	{
		Date dataHistorico = DateUtil.criarDataMesAno(1, 2, 2014); 
    	Date dataDoisMesesDepois = DateUtil.criarDataMesAno(1, 4, 2014);
    	Date dataTresMesesDepois = DateUtil.criarDataMesAno(1, 5, 2014);
    	Date dataQuatroMesesDepois = DateUtil.criarDataMesAno(1, 6, 2014);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		AreaOrganizacional area2 = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();
    	Estabelecimento estabelecimento2 = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador3 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador4 = getColaboradorComHistorico(empresa, estabelecimento2, area2, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, dataDoisMesesDepois, dataTresMesesDepois, true, 200.0);
		
		Curso curso2 = saveCurso("curso 2", empresa, 30*60);
		Turma turma2 = saveTurma(curso2, dataDoisMesesDepois, dataQuatroMesesDepois, true, 500.50);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 4, 2014), null, null); 
		getDiaTurma(turma2, DateUtil.criarDataMesAno(20, 4, 2014), null, null);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 6, 2014), null, null);		

		saveColaboradorTurma(curso1, turma1, colaborador1); 
		saveColaboradorTurma(curso1, turma1, colaborador2);
		saveColaboradorTurma(curso2, turma2, colaborador3); 
		saveColaboradorTurma(curso1, turma1, colaborador4);
				
		IndicadorTreinamento result = cursoDao.findIndicadorHorasTreinamentos(dataDoisMesesDepois, dataTresMesesDepois, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId()}, new Long[]{area.getId()}, null);
		
		assertEquals("Qtde de colaboradores com filtro", 3, (int)result.getQtdColaboradoresFiltrados());
		assertEquals("Qtde de colaboradores inscritos", 4, (int)result.getQtdColaboradoresInscritos());
		assertEquals("Carga horaria", 50.0, result.getSomaHoras());
		assertEquals("Carga horaria Ratiada", 40.0, result.getSomaHorasRatiada());
		assertEquals("Custos", Math.round(633.83), Math.round(result.getSomaCustos()));
	}
	
	public void testFindIndicadorHorasTreinamentosSemSelecionarAreaEEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		AreaOrganizacional area2 = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();
    	Estabelecimento estabelecimento2 = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, DateUtil.criarDataMesAno(1, 2, 2014) , StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento2, area2, DateUtil.criarDataMesAno(1, 2, 2014), StatusRetornoAC.CONFIRMADO);

		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, DateUtil.criarDataMesAno(1, 4, 2014), DateUtil.criarDataMesAno(1, 5, 2014), true, 200.0);

		Curso curso2 = saveCurso("curso 2", empresa, 30*60);
		Turma turma2 = saveTurma(curso2, DateUtil.criarDataMesAno(1, 4, 2014), DateUtil.criarDataMesAno(1, 6, 2014), true, 500.0);

		saveColaboradorTurma(curso1, turma1, colaborador1); 
		saveColaboradorTurma(curso2, turma2, colaborador2);
				
		IndicadorTreinamento result = cursoDao.findIndicadorHorasTreinamentos(DateUtil.criarDataMesAno(1, 4, 2014), DateUtil.criarDataMesAno(1, 5, 2014), new Long[]{empresa.getId()}, null, null, new Long[]{curso1.getId(), curso2.getId()});
		
		assertEquals("Qtde de colaboradores com filtro", 2, (int)result.getQtdColaboradoresFiltrados());
		assertEquals("Qtde de colaboradores inscritos", 2, (int)result.getQtdColaboradoresInscritos());
		assertEquals("Carga horaria", 40.0, result.getSomaHoras());
		assertEquals("Carga horaria Ratiada", 40.0, result.getSomaHorasRatiada());
		assertEquals("Custos", Math.round(700.00), Math.round(result.getSomaCustos()));
	}
	
	public void testFindIndicadorHorasTreinamentosHistoricoAtualDoColaboradorDiferenteDeQuandoFezDoCurso()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		AreaOrganizacional area2 = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, DateUtil.criarDataMesAno(1, 2, 2014) , StatusRetornoAC.CONFIRMADO);
		getHistoricoColaborador(colaborador1, estabelecimento, area2, DateUtil.criarDataMesAno(1, 6, 2014), StatusRetornoAC.CONFIRMADO);
		
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento, area2, DateUtil.criarDataMesAno(1, 2, 2014) , StatusRetornoAC.CONFIRMADO);
		
		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, DateUtil.criarDataMesAno(1, 4, 2014), DateUtil.criarDataMesAno(1, 5, 2014), true, 200.0);

		saveColaboradorTurma(curso1, turma1, colaborador1);
		saveColaboradorTurma(curso1, turma1, colaborador2);
				
		IndicadorTreinamento result = cursoDao.findIndicadorHorasTreinamentos(DateUtil.criarDataMesAno(1, 4, 2014), DateUtil.criarDataMesAno(1, 5, 2014), new Long[]{empresa.getId()}, null, new Long[]{area2.getId()}, new Long[]{curso1.getId()});
		
		assertEquals("Qtde de colaboradores com filtro", 1, (int)result.getQtdColaboradoresFiltrados());
		assertEquals("Qtde de colaboradores inscritos", 2, (int)result.getQtdColaboradoresInscritos());
		assertEquals("Carga horaria", 10.0, result.getSomaHoras());
		assertEquals("Carga horaria Ratiada", 10.0, result.getSomaHorasRatiada());
		assertEquals("Custos", Math.round(100.00), Math.round(result.getSomaCustos()));
	}

	@SuppressWarnings("unused")
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
		
		Curso curso = saveCurso("curso", empresa, 0); 
		Turma turma1 = saveTurma(curso, dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), false, 200.0); 
		turma1.setEmpresa(empresa);
		turmaDao.update(turma1);
		
		ColaboradorTurma colaboradorInscrito1 = saveColaboradorTurma(curso, turma1, null); 
		ColaboradorTurma colaboradorInscrito2 = saveColaboradorTurma(curso, turma1, null);
		
		Turma turmaForaDaConsulta =  saveTurma(curso, dataQuatroMesesDepois.getTime(), dataQuatroMesesDepois.getTime(), false, 0.0);
		ColaboradorTurma colaboradorForaDoPeriodo = saveColaboradorTurma(curso, turmaForaDaConsulta, null); 

		Integer qtd = cursoDao.findQtdColaboradoresInscritosTreinamentos(dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), new Long[]{empresa.getId()}, null);
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

		Curso curso1 = saveCurso("curso 1", empresa, 20); 
		Curso cursoOutraEmpresa = saveCurso("curso 2", outraEmpresa, 20); 

		Turma turma1 = saveTurma(curso1, hoje, dataDoisMesesDepois.getTime(), true, 0.0);
		turma1.setQtdParticipantesPrevistos(30);
		turmaDao.update(turma1);

		Turma turma2 = saveTurma(curso1, hoje, dataDoisMesesDepois.getTime(), true, 0.0);
		turma2.setQtdParticipantesPrevistos(170);
		turmaDao.update(turma2);

		Turma turmaOutraEmpresa = saveTurma(cursoOutraEmpresa, hoje, dataDoisMesesDepois.getTime(), true, 0.0); 
		turmaOutraEmpresa.setQtdParticipantesPrevistos(195);
		turmaDao.update(turmaOutraEmpresa);

		Integer soma = cursoDao.findSomaColaboradoresPrevistosTreinamentos(hoje, dataDoisMesesDepois.getTime(), empresa.getId());
		assertEquals(new Integer(200), soma);
	}

	@SuppressWarnings("unused")
	public void testCountTreinamentosRealizados()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesDepois = Calendar.getInstance();
		dataDoisMesesDepois.add(Calendar.MONTH, 2);
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Calendar dataEntreTresEDoisMesesAtras = Calendar.getInstance();
    	dataEntreTresEDoisMesesAtras.add(Calendar.MONTH, -3);
    	dataEntreTresEDoisMesesAtras.add(Calendar.WEEK_OF_MONTH, +1);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso curso1 = saveCurso("curso 1", empresa, 0); 
		Curso curso2 = saveCurso("curso 2", empresa, 0); 

		Turma turmaRealizada1 = saveTurma(curso1, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), true, 0.0); 
		Turma turmaRealizada2 = saveTurma(curso2, dataEntreTresEDoisMesesAtras.getTime(), dataEntreTresEDoisMesesAtras.getTime(), true, 0.0);
		Turma turmaNaoRealizada = saveTurma(curso1, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), false, 0.0); 
		Turma turmaRealizadaForaDoPeriodo = saveTurma(curso1, hoje, dataDoisMesesDepois.getTime(), true, 0.0); 
		
		assertEquals(new Integer(2), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa.getId()}, null, true));
	}

	@SuppressWarnings("unused")
	public void testCountTreinamentosNaoRealizados()
	{
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Curso curso1 = saveCurso("curso 1", empresa1, 0); 
		Curso curso2 = saveCurso("curso 2", empresa2, 0); 
		
		Turma turmaRealizada = saveTurma(curso1, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), true, 0.0);
		Turma turmaNaoRealizada1 = saveTurma(curso1, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), false, 0.0); 
		Turma turmaNaoRealizada2 = saveTurma(curso2, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), false, 0.0); 
		
		assertEquals("Treinamentos não realizados da empresa1", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId()}, null, false));
		assertEquals("Treinamentos realizados da empresa1", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId()}, null, true));
		assertEquals("Treinamentos não realizados da empresa2", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa2.getId()}, null, false));
		assertEquals("Treinamentos realizados da empresa2", new Integer(0), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa2.getId()}, null, true));
		assertEquals("Treinamentos não realizados das empresas", new Integer(2), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId(), empresa2.getId()}, null, false));
		assertEquals("Treinamentos realizados das empresas", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId(), empresa2.getId()}, null, true));

	}
	
	public void testFindIndicadorTotalHorasTreinamentoTurmaRealizada()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");
		
		populaCargaInicialParaIndicadores(dataInicio, "Curso Básico", 90, dataInicio, dataFim, true);
		
		assertEquals(new Integer(90), cursoDao.findCargaHorariaTotalTreinamento(new Long[]{curso.getId()}, new Long[] {empresa.getId()}, null, null, dataInicio, dataFim, true));
	}
	
	public void testFindIndicadorTotalHorasTreinamentoNaorealizada()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");

		populaCargaInicialParaIndicadores(DateUtil.criarDataDiaMesAno("01/01/2014"), "Curso Básico", 90, dataInicio, dataFim, false);
		
		assertEquals(new Integer(0), cursoDao.findCargaHorariaTotalTreinamento(new Long[]{curso.getId()}, new Long[]{}, new Long[]{}, new Long[]{}, dataInicio, dataFim, true));
	}
	
	public void testFindIndicadorTotalHorasTreinamentoFiltroAreaAndEstabelecimento()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");

		populaCargaInicialParaIndicadores(DateUtil.criarDataDiaMesAno("01/01/2014"), "Curso Básico", 90, dataInicio, dataFim, true);
		
		assertEquals(new Integer(90), cursoDao.findCargaHorariaTotalTreinamento(new Long[]{curso.getId()}, null, new Long[] {this.estabelecimento.getId()}, new Long[] {this.areaOrganizacional.getId()}, dataInicio, dataFim, true));
	}
	
	public void testFindIndicadorTotalHorasTodosOsCursosArrayVazio()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");

		populaCargaInicialParaIndicadores(DateUtil.criarDataDiaMesAno("01/01/2014"), "Curso Básico", 90, dataInicio, dataFim, true);
		
		assertEquals(new Integer(90), cursoDao.findCargaHorariaTotalTreinamento(new Long[]{}, new Long[] {empresa.getId()}, new Long[]{}, new Long[]{}, dataInicio, dataFim, true));
	}
	
	public void testFindIndicadorTotalHorasTodosOsCursosArrayNulo()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");

		populaCargaInicialParaIndicadores(DateUtil.criarDataDiaMesAno("01/01/2014"), "Curso Básico", 90, dataInicio, dataFim, true);
		
		assertEquals(new Integer(90), cursoDao.findCargaHorariaTotalTreinamento(null, new Long[] {empresa.getId()}, new Long[]{}, new Long[]{}, dataInicio, dataFim, true));
	}
	
	public void testFindIndicadorTotalHorasTreinamentoColaboradorComVariosHistoricos()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");
		
		populaCargaInicialParaIndicadores(DateUtil.criarDataDiaMesAno("01/01/2014"), "Curso Básico", 90, dataInicio, dataFim, true);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		this.historicoColaborador = HistoricoColaboradorFactory.getEntity();
		this.historicoColaborador.setColaborador(this.colaborador);
		this.historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		this.historicoColaborador.setEstabelecimento(this.estabelecimento);
		this.historicoColaborador.setData(DateUtil.criarDataDiaMesAno("01/07/2015"));
		historicoColaboradorDao.save(this.historicoColaborador);
		
		assertEquals(new Integer(90), cursoDao.findCargaHorariaTotalTreinamento(new Long[]{curso.getId()}, null, new Long[] {this.estabelecimento.getId()}, new Long[] {this.areaOrganizacional.getId()}, dataInicio, dataFim, true));
	}
	
	@SuppressWarnings("unused")
	private void populaCargaInicialParaIndicadores(Date dataHistorico, String nomeCurso, Integer cargaHorariaCurso, Date dataInicioTurma, Date dataFimTurma, boolean turmaRealizada){
		this.empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		this.areaOrganizacional = getAreaOrganizacional();
		this.estabelecimento = getEstabelecimento();

		this.colaborador = getColaboradorComHistorico(empresa, estabelecimento, areaOrganizacional, dataHistorico, StatusRetornoAC.CONFIRMADO); 

		this.curso = saveCurso("curso", empresa, cargaHorariaCurso);
		this.setName(nomeCurso);
		cursoDao.update(this.curso);
				
		Turma turma = saveTurma(this.curso, dataInicioTurma, dataFimTurma, turmaRealizada, 0.0);
		turma.setEmpresa(this.empresa);
		turma.setDescricao("Turma 1");
		turmaDao.update(turma);
		
		ColaboradorTurma colaboradorTurmaTurmaRealizada = saveColaboradorTurma(this.curso, turma, this.colaborador); 
		turmaDao.getHibernateTemplateByGenericDao().flush();
	}
	
	@SuppressWarnings("unused")
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

		Curso cursoComAvaliacao = saveCurso("curso com avaliação", empresa, 0);
		cursoComAvaliacao.setAvaliacaoCursos(new ArrayList<AvaliacaoCurso>());
		cursoComAvaliacao.getAvaliacaoCursos().add(avaliacaoCurso);
		cursoDao.update(cursoComAvaliacao);

		Curso cursoSemAvaliacao = saveCurso("curso sem avaliação", empresa, 0); 

		Turma turmaForaDoPeriodo = saveTurma(cursoComAvaliacao, dataTresMesesAtras.getTime(), hoje, false, 0.0); 
		Turma turmaComAvaliacao1 = saveTurma(cursoComAvaliacao, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), false, 0.0); 
		Turma turmaSemAvaliacao = saveTurma(cursoSemAvaliacao, dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), false, 0.0); 

		assertEquals(1, cursoDao.findComAvaliacao(empresa.getId(), dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime()).size());
	}

	public void testFindByFiltro()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(emp1);
		cursoDao.save(curso);

		Curso cursoFiltroBusca = new Curso();
		cursoFiltroBusca.setNome("Direção");

		assertEquals(1, cursoDao.findByFiltro(1, 1000, cursoFiltroBusca, emp1.getId()).size());
	}
	
	public void testFindByFiltroEmpresasParticipantes()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);

		Collection<Empresa> empresasParticipantes = new ArrayList<Empresa>();
		empresasParticipantes.add(emp2);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(emp1);
		curso.setEmpresasParticipantes(empresasParticipantes);
		cursoDao.save(curso);

		Curso cursoFiltroBusca = new Curso();
		cursoFiltroBusca.setNome("Direção");
		
		assertEquals(1, cursoDao.findByFiltro(1, 1000, cursoFiltroBusca, emp1.getId()).size());
		assertEquals(1, cursoDao.findByFiltro(1, 1000, cursoFiltroBusca, emp2.getId()).size());
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(empresa);
		curso.setEmpresasParticipantes(Arrays.asList(empresa, empresa2));
		cursoDao.save(curso);

		Curso cursoFiltroBusca = new Curso();
		cursoFiltroBusca.setNome("Curso de direção");

		assertEquals((Integer)1, cursoDao.getCount(cursoFiltroBusca, empresa.getId()));
	}
	

	public void testiFindCursoSemTurma()
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
		
		Collection<Curso> cursos = cursoDao.findCursosSemTurma(empresa.getId());
		
		assertEquals(1, cursos.size());
	}
	
	public void testFindByConhecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso curso1 = CursoFactory.getEntity();
		cursoDao.save(curso1);

		Curso curso2 = CursoFactory.getEntity();
		cursoDao.save(curso2);

		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso1);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setCursos(cursos);
		conhecimentoDao.save(conhecimento);

		assertEquals(1, cursoDao.findByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO).size());
	}
	
	public void testFindByHabilidade()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);

		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setCursos(Arrays.asList(curso));
		habilidadeDao.save(habilidade);
		
		Collection<Curso> cursos = cursoDao.findByCompetencia(habilidade.getId(), TipoCompetencia.HABILIDADE); 
				
		assertEquals(1, cursos.size());
		assertEquals(curso.getId(), cursos.iterator().next().getId());
	}
	
	public void testFindByAtitude()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);

		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setCursos(Arrays.asList(curso));
		atitudeDao.save(atitude);
		
		Collection<Curso> cursos = cursoDao.findByCompetencia(atitude.getId(), TipoCompetencia.ATITUDE); 
		
		assertEquals(1, cursos.size());
		assertEquals(curso.getId(), cursos.iterator().next().getId());
	}
	
	public void testFindAllByEmpresasParticipantes()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);

		Collection<Empresa> empresasParticipantes = new ArrayList<Empresa>();
		empresasParticipantes.add(emp2);

		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(emp1);
		curso.setEmpresasParticipantes(empresasParticipantes);
		cursoDao.save(curso);

		assertEquals(1, cursoDao.findAllByEmpresasParticipantes(emp1.getId()).size());
		assertEquals(1, cursoDao.findAllByEmpresasParticipantes(emp2.getId()).size());
	}

	public void testExisteEmpresasNoCurso()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);

		Collection<Empresa> empresasParticipantes = new ArrayList<Empresa>();
		empresasParticipantes.add(emp2);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(emp1);
		curso.setEmpresasParticipantes(empresasParticipantes);
		cursoDao.save(curso);
		
		Curso cursoFiltroBusca = new Curso();
		cursoFiltroBusca.setNome("Direção");
		
		assertTrue(cursoDao.existeEmpresasNoCurso(emp1.getId(), curso.getId()));
		assertTrue(cursoDao.existeEmpresasNoCurso(emp2.getId(), curso.getId()));
		assertFalse(cursoDao.existeEmpresasNoCurso(33L, curso.getId()));
	}
	
	public void testFindEmpresasParticipantes()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);

		Collection<Empresa> empresasParticipantes = new ArrayList<Empresa>();
		empresasParticipantes.add(emp1);
		empresasParticipantes.add(emp2);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresasParticipantes(empresasParticipantes);
		cursoDao.save(curso);
		
		assertEquals(2, cursoDao.findEmpresasParticipantes(curso.getId()).size());
	}
	
	public void testFindEmpresaByCurso()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Curso curso = CursoFactory.getEntity();
		curso.setNome("Curso de direção");
		curso.setEmpresa(emp1);
		cursoDao.save(curso);
		
		assertEquals(emp1.getId(), cursoDao.findEmpresaByCurso(curso.getId()).getId());
	}
	
	public void testExisteAvaliacaoAlunoRespondidaComResposta()
	{
		Curso curso1 = criaCurso(TipoAvaliacaoCurso.NOTA, true, 6.0);
		Curso curso2 = criaCurso(TipoAvaliacaoCurso.PORCENTAGEM, true, 50.0);
		Curso curso3 = criaCurso(TipoAvaliacaoCurso.AVALIACAO, true, null);
		Curso curso4 = criaCurso(TipoAvaliacaoCurso.NOTA, true, 0.0);
		Curso curso5 = criaCurso(TipoAvaliacaoCurso.PORCENTAGEM, true, 0.0);
		
		boolean existeAvaliacaoAluno1Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso1.getId(), TipoAvaliacaoCurso.NOTA); 
		boolean existeAvaliacaoAluno2Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso2.getId(), TipoAvaliacaoCurso.PORCENTAGEM); 
		boolean existeAvaliacaoAluno3Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso3.getId(), TipoAvaliacaoCurso.AVALIACAO); 
		boolean existeAvaliacaoAluno4Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso4.getId(), TipoAvaliacaoCurso.NOTA); 
		boolean existeAvaliacaoAluno5Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso5.getId(), TipoAvaliacaoCurso.PORCENTAGEM); 
		
		assertTrue("Avaliação por nota com resposta", existeAvaliacaoAluno1Respondida);
		assertTrue("Avaliação por porcentagem com resposta", existeAvaliacaoAluno2Respondida);
		assertTrue("Avaliação por avaliação com resposta", existeAvaliacaoAluno3Respondida);
		assertFalse("Avaliação por nota com resposta 0", existeAvaliacaoAluno4Respondida);
		assertFalse("Avaliação por porcentagem com resposta 0", existeAvaliacaoAluno5Respondida);
	}

	public void testExisteAvaliacaoAlunoRespondidaSemResposta()
	{
		Curso curso6 = criaCurso(TipoAvaliacaoCurso.NOTA, false, null);
		Curso curso7 = criaCurso(TipoAvaliacaoCurso.PORCENTAGEM, false, null);
		Curso curso8 = criaCurso(TipoAvaliacaoCurso.AVALIACAO, false, null);
		
		boolean existeAvaliacaoAluno6Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso6.getId(), TipoAvaliacaoCurso.NOTA); 
		boolean existeAvaliacaoAluno7Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso7.getId(), TipoAvaliacaoCurso.PORCENTAGEM); 
		boolean existeAvaliacaoAluno8Respondida = cursoDao.existeAvaliacaoAlunoRespondida(curso8.getId(), TipoAvaliacaoCurso.AVALIACAO); 
		
		assertFalse("Avaliação por nota sem resposta", existeAvaliacaoAluno6Respondida);
		assertFalse("Avaliação por porcentagem sem resposta", existeAvaliacaoAluno7Respondida);
		assertFalse("Avaliação por avaliação sem resposta", existeAvaliacaoAluno8Respondida);
	}
	
	
	public void testFindByEmpresaIdAndCursosId()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);

		Curso curso1 = saveCurso("Curso de direção", emp1, 0);
		Curso curso2 = saveCurso("Direção", emp2, 0); 
		
		Collection<Curso> cursos = cursoDao.findByEmpresaIdAndCursosId(emp1.getId(), new Long[]{});
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(null, curso1.getId());
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(emp1.getId(), curso1.getId());
		assertEquals(1, cursos.size());

		cursos = cursoDao.findByEmpresaIdAndCursosId(emp2.getId(), new Long[]{});
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(emp2.getId(), curso2.getId());
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(null, new Long[]{});
		assertTrue(cursos.size() >= 2);
	}

	@SuppressWarnings("unused")
	public void testSomaDespesasPorCurso() 
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2012);
		Date dataFim = DateUtil.criarDataMesAno(2, 1, 2012);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Curso curso1 = saveCurso("Curso 1", empresa1, null); 
		Turma turma1 = saveTurma(curso1, dataIni, dataFim, true, 150.0); 
		Turma turma2 = saveTurma(curso1, dataIni, dataFim, true, 250.0); 

		Curso curso2 = saveCurso("Curso 2", empresa2, null); 
		Turma turma3 = saveTurma(curso2, dataIni, dataFim, true, 300.0); 
		
		Collection<Curso> cursos = cursoDao.somaDespesasPorCurso(dataIni, dataFim, new Long[]{empresa1.getId(), empresa2.getId()},  null);
		Collection<Curso> cursosOutraEmpresa = cursoDao.somaDespesasPorCurso(dataIni, dataFim, new Long[]{1111111111111L}, null);
		
		assertEquals(2, cursos.size());
		assertEquals(400.0, ((Curso)cursos.toArray()[0]).getTotalDespesas());
		assertEquals(300.0, ((Curso)cursos.toArray()[1]).getTotalDespesas());
		assertEquals(0, cursosOutraEmpresa.size());
	}
	
	public void testExistePresenca(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador(empresa);
		Curso curso = saveCurso("Curso Básico", empresa, 20);
		Turma turma = saveTurma(curso, new Date(), new Date(), true, 0.0);
		DiaTurma diaTurma = getDiaTurma(turma, new Date(), null, null);
		
		ColaboradorTurma colaboradorTurma = saveColaboradorTurma(curso, turma, colaborador);
		ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresenca.setPresenca(true);
		colaboradorPresencaDao.save(colaboradorPresenca);
		assertTrue(cursoDao.existePresenca(curso.getId()));
	}
	
	public void testExistePresencaFalse(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador(empresa);
		Curso curso1 = saveCurso("Curso Básico", empresa, 20);
		Turma turma1 = saveTurma(curso1, new Date(), new Date(), true, 0.0);
		DiaTurma diaTurma = getDiaTurma(turma1, new Date(), null, null);

		Curso curso2 = saveCurso("Curso Avançado", empresa, 20);
		Turma turma2 = saveTurma(curso2, new Date(), new Date(), false, 0.0);
		
		ColaboradorTurma colaboradorTurma = saveColaboradorTurma(curso1, turma1, colaborador);
		ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresenca.setPresenca(true);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		ColaboradorTurma colaboradorTurma2 = saveColaboradorTurma(curso2, turma2, colaborador);
		ColaboradorPresenca colaboradorPresenca2 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca2.setColaboradorTurma(colaboradorTurma2);
		colaboradorPresenca2.setDiaTurma(diaTurma);
		colaboradorPresenca2.setPresenca(true);
		colaboradorPresencaDao.save(colaboradorPresenca2);
		assertFalse(cursoDao.existePresenca(curso2.getId()));
	}

	public void testFindIndicadorTreinamentoCustos() {
		Date dataHistorico = DateUtil.criarDataMesAno(1, 2, 2014); 
    	Date dataMes4 = DateUtil.criarDataMesAno(1, 4, 2014);
    	Date dataMes5 = DateUtil.criarDataMesAno(1, 5, 2014);
    	Date dataMes6 = DateUtil.criarDataMesAno(1, 6, 2014);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();
		Estabelecimento estabelecimento2 = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento2, area, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, dataMes4, dataMes5, true, 200.0);
		
		Curso curso2 = saveCurso("curso 2", empresa, 30*60);
		Turma turma2 = saveTurma(curso2, dataMes4, dataMes6, true, 500.50);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 4, 2014), null, null); 
		getDiaTurma(turma2, DateUtil.criarDataMesAno(20, 4, 2014), null, null);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 6, 2014), null, null);		

		saveColaboradorTurma(curso1, turma1, colaborador1); 
		saveColaboradorTurma(curso1, turma1, colaborador2);
				
		IndicadorTreinamento result = cursoDao.findIndicadorTreinamentoCustos(dataMes4, dataMes5, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId()}, new Long[]{area.getId()}, null);
		
		assertEquals("Qtde de colaboradores filtrados", 1, (int)result.getQtdColaboradoresFiltrados());
		assertEquals("Qtde de colaboradores inscritos", 2, (int)result.getQtdColaboradoresInscritos());
		assertEquals("Carga horaria", 10.0, result.getSomaHoras());
		assertEquals("Custos", Math.round(100.00), Math.round(result.getSomaCustos()));
	}
	
	public void testFindIndicadorTreinamentoCustosComFiltroCurso() {
		Date dataHistorico = DateUtil.criarDataMesAno(1, 2, 2014); 
    	Date dataMes4 = DateUtil.criarDataMesAno(1, 4, 2014);
    	Date dataMes5 = DateUtil.criarDataMesAno(1, 5, 2014);
    	Date dataMes6 = DateUtil.criarDataMesAno(1, 6, 2014);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();
		Estabelecimento estabelecimento2 = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento2, area, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, dataMes4, dataMes5, true, 200.0);
		
		Curso curso2 = saveCurso("curso 2", empresa, 30*60);
		Turma turma2 = saveTurma(curso2, dataMes4, dataMes6, true, 500.50);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 4, 2014), null, null); 
		getDiaTurma(turma2, DateUtil.criarDataMesAno(20, 4, 2014), null, null);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 6, 2014), null, null);		

		saveColaboradorTurma(curso1, turma1, colaborador1); 
		saveColaboradorTurma(curso2, turma2, colaborador2);
				
		IndicadorTreinamento result = cursoDao.findIndicadorTreinamentoCustos(dataMes4, dataMes5, new Long[]{empresa.getId()}, null, null, new Long[]{curso1.getId(), curso2.getId()});
		
		assertEquals("Qtde de colaboradores filtrados", 2, (int)result.getQtdColaboradoresFiltrados());
		assertEquals("Qtde de colaboradores inscritos", 2, (int)result.getQtdColaboradoresInscritos());
		assertEquals("Carga horaria", 40.0, result.getSomaHoras());
		assertEquals("Custos", Math.round(701.00), Math.round(result.getSomaCustos()));
	}
	
	public void testFindQtdHorasRatiada() {
		Date dataHistorico = DateUtil.criarDataMesAno(1, 2, 2014); 
    	Date dataMes4 = DateUtil.criarDataMesAno(1, 4, 2014);
    	Date dataMes5 = DateUtil.criarDataMesAno(1, 5, 2014);
    	Date dataMes6 = DateUtil.criarDataMesAno(1, 6, 2014);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();
		Estabelecimento estabelecimento2 = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento2, area, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, dataMes4, dataMes5, true, 200.0);
		
		Curso curso2 = saveCurso("curso 2", empresa, 30*60);
		Turma turma2 = saveTurma(curso2, dataMes4, dataMes6, true, 500.50);
		
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 4, 2014), null, null); 
		getDiaTurma(turma2, DateUtil.criarDataMesAno(20, 4, 2014), null, null);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 6, 2014), null, null);
		getDiaTurma(turma1, DateUtil.criarDataMesAno(5, 4, 2014), "8:00", "11:00"); 
		getDiaTurma(turma1, DateUtil.criarDataMesAno(20, 4, 2014), "8:00", "11:00");
		getDiaTurma(turma1, DateUtil.criarDataMesAno(5, 6, 2014), "8:00", "11:00");	

		saveColaboradorTurma(curso1, turma1, colaborador1); 
		saveColaboradorTurma(curso2, turma2, colaborador1); 
		saveColaboradorTurma(curso2, turma2, colaborador2);
		
		colaboradorTurmaDao.getHibernateTemplateByGenericDao().flush();
		
		Double result = cursoDao.findQtdHorasRatiada(dataMes4, dataMes5, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{curso1.getId(), curso2.getId()}, false);
		assertEquals(40.0, result/60);
		
		result = cursoDao.findQtdHorasRatiada(dataMes4, dataMes5, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{curso1.getId(), curso2.getId()}, true);
		assertEquals(6.0, result/60);
	}

	public void testFindCargaHorariaTreinamentRatiada() {
		Date dataHistorico = DateUtil.criarDataMesAno(1, 2, 2014); 
    	Date dataMes1 = DateUtil.criarDataMesAno(1, 4, 2014);
    	Date dataMes2 = DateUtil.criarDataMesAno(1, 5, 2014);
    	Date dataMes3 = DateUtil.criarDataMesAno(1, 6, 2014);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = getAreaOrganizacional();
		
		Estabelecimento estabelecimento = getEstabelecimento();
		Estabelecimento estabelecimento2 = getEstabelecimento();

		Colaborador colaborador1 = getColaboradorComHistorico(empresa, estabelecimento, area, dataHistorico, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = getColaboradorComHistorico(empresa, estabelecimento2, area, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Curso curso1 = saveCurso("curso 1", empresa, 10*60);
		Turma turma1 = saveTurma(curso1, dataMes1, dataMes2, true, 200.0);
		
		Curso curso2 = saveCurso("curso 2", empresa, 30*60);
		Turma turma2 = saveTurma(curso2, dataMes1, dataMes3, true, 500.50);
		
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 4, 2014), null, null); 
		getDiaTurma(turma2, DateUtil.criarDataMesAno(20, 4, 2014), null, null);
		getDiaTurma(turma2, DateUtil.criarDataMesAno(5, 6, 2014), null, null);
		getDiaTurma(turma1, DateUtil.criarDataMesAno(5, 4, 2014), "8:00", "10:00"); 
		getDiaTurma(turma1, DateUtil.criarDataMesAno(20, 4, 2014), "8:00", "10:00");
		getDiaTurma(turma1, DateUtil.criarDataMesAno(5, 6, 2014), "8:00", "10:00");
		diaTurmaDao.getHibernateTemplateByGenericDao().flush();

		saveColaboradorTurma(curso1, turma1, colaborador1); 
		saveColaboradorTurma(curso1, turma1, colaborador2); 
		saveColaboradorTurma(curso2, turma2, colaborador2);
		
		colaboradorTurmaDao.getHibernateTemplateByGenericDao().flush();
		
		Integer result = cursoDao.findCargaHorariaTreinamentoRatiada(new Long[]{curso1.getId(), curso2.getId()}, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, dataMes1, dataMes2, true, false);
		assertEquals(20, result/60);
		
		result = cursoDao.findCargaHorariaTreinamentoRatiada(new Long[]{curso1.getId(), curso2.getId()}, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, dataMes1, dataMes2, true, true);
		assertEquals(4, result/60);
	}
	
	private Curso criaCurso(char tipoAvaliacaoCurso, boolean comResposta, Double valorResposta)
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		if(comResposta){
			if(tipoAvaliacaoCurso == TipoAvaliacaoCurso.AVALIACAO){
				Turma turma = TurmaFactory.getEntity();
				turma.setCurso(curso);
				turmaDao.save(turma);
				
				ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
				colaboradorQuestionario.setTurma(turma);
				colaboradorQuestionarioDao.save(colaboradorQuestionario);
			} else {
				ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
				colaboradorTurma.setCurso(curso);
				colaboradorTurmaDao.save(colaboradorTurma);
				
				AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
				aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurma);
				aproveitamentoAvaliacaoCurso.setValor(valorResposta);
				
				aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);
			}
		}
		return curso;
	}

	private Colaborador saveColaborador(Empresa empresa){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		return colaborador;
	}
	
	private Colaborador getColaboradorComHistorico(Empresa empresa, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date dataHistorico, int statusRetornoAC){
		Colaborador colaborador = saveColaborador(empresa);
		getHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, dataHistorico, statusRetornoAC);
		return colaborador;
	}
	
	private HistoricoColaborador getHistoricoColaborador(Colaborador colaborador, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date dataHistorico, int statusRetornoAC){
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setData(dataHistorico);
		hc1.setColaborador(colaborador);
		hc1.setAreaOrganizacional(areaOrganizacional);
		hc1.setEstabelecimento(estabelecimento);
		hc1.setStatus(statusRetornoAC);
		historicoColaboradorDao.save(hc1);
		return historicoColaborador;
	}
	
	private AreaOrganizacional getAreaOrganizacional(){
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		return area;
	}
	
	private Estabelecimento getEstabelecimento(){
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		return estabelecimento;
	}
	
	private DiaTurma getDiaTurma(Turma turma, Date data, String horaIni, String horaFim){
		DiaTurma diaTurma = DiaTurmaFactory.getEntity();
		diaTurma.setTurma(turma);
		diaTurma.setDia(data);
		diaTurma.setHoraIni(horaIni);
		diaTurma.setHoraFim(horaFim);
		diaTurmaDao.save(diaTurma);
		return diaTurma;
	}

	private Turma saveTurma(Curso curso, Date dataInicio, Date dataFim, boolean realizada, Double custo){
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turma.setCusto(custo);
		turma.setDataPrevIni(dataInicio);
		turma.setDataPrevFim(dataFim);
		turma.setRealizada(realizada);
		turmaDao.save(turma);
		return turma;
	}
	
	private Curso saveCurso(String nomeCurso, Empresa empresa, Integer cargaHoraria){
		Curso curso = CursoFactory.getEntity();
		curso.setNome(nomeCurso);
		curso.setEmpresa(empresa);
		curso.setCargaHoraria(cargaHoraria);
		cursoDao.save(curso);
		return curso;
	}
	
	private ColaboradorTurma saveColaboradorTurma(Curso curso, Turma turma, Colaborador colaborador){
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		return colaboradorTurma;
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

	public void setColaboradorDao(ColaboradorDao colaboradorDao) 
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao)
	{
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setDiaTurmaDao(DiaTurmaDao diaTurmaDao)
	{
		this.diaTurmaDao = diaTurmaDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setAproveitamentoAvaliacaoCursoDao(AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao)
	{
		this.aproveitamentoAvaliacaoCursoDao = aproveitamentoAvaliacaoCursoDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) 
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao) {
		this.habilidadeDao = habilidadeDao;
	}

	public void setAtitudeDao(AtitudeDao atitudeDao) {
		this.atitudeDao = atitudeDao;
	}

	public void setColaboradorPresencaDao(
			ColaboradorPresencaDao colaboradorPresencaDao) {
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}
}