package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
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
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
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
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private DiaTurmaDao diaTurmaDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao; 
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao; 
	private EstabelecimentoDao estabelecimentoDao;

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
		
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setCargaHoraria(10*60);
		cursoDao.save(curso1);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		turma1.setCusto(200.0);
		turma1.setDataPrevIni(dataDoisMesesDepois.getTime());
		turma1.setDataPrevFim(dataTresMesesDepois.getTime());
		turma1.setRealizada(true);
		turmaDao.save(turma1);
		
		
		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa);
		curso2.setCargaHoraria(30*60);
		cursoDao.save(curso2);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso2);
		turma2.setCusto(3512.69);
		turma2.setDataPrevIni(dataDoisMesesDepois.getTime());
		turma2.setDataPrevFim(dataTresMesesDepois.getTime());
		turma2.setRealizada(true);
		turmaDao.save(turma2);
		
		
		Turma turmaForaDaConsulta = TurmaFactory.getEntity();
		turmaForaDaConsulta.setCurso(curso2);
		turmaForaDaConsulta.setCusto(9582.00);
		turmaForaDaConsulta.setDataPrevIni(dataQuatroMesesDepois.getTime());
		turmaForaDaConsulta.setDataPrevFim(dataQuatroMesesDepois.getTime());
		turmaForaDaConsulta.setRealizada(true);
		turmaDao.save(turmaForaDaConsulta);
		
		Double custoTotal = cursoDao.somaCustosTreinamentos(dataDoisMesesDepois.getTime(), dataTresMesesDepois.getTime(), new Long[]{empresa.getId()}, null);
		
		assertEquals("Custo das turmas", 3712.69, custoTotal);
	}
	
	public void testFindIndicadorHorasTreinamentos()
	{
		Date dataHistorico = DateUtil.criarDataMesAno(1, 2, 2014); 
    	Date dataDoisMesesDepois = DateUtil.criarDataMesAno(1, 4, 2014);
    	Date dataTresMesesDepois = DateUtil.criarDataMesAno(1, 5, 2014);
    	Date dataQuatroMesesDepois = DateUtil.criarDataMesAno(1, 6, 2014);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);
    	
    	Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento2);

		Colaborador colab1 = ColaboradorFactory.getEntity();
		colab1.setEmpresa(empresa);
		colaboradorDao.save(colab1);
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setData(dataHistorico);
		hc1.setColaborador(colab1);
		hc1.setAreaOrganizacional(area);
		hc1.setEstabelecimento(estabelecimento);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hc1);
		
		Colaborador colab2 = ColaboradorFactory.getEntity();
		colab2.setEmpresa(empresa);
		colaboradorDao.save(colab2);
		
		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setData(dataHistorico);
		hc2.setColaborador(colab2);
		hc2.setAreaOrganizacional(area);
		hc2.setEstabelecimento(estabelecimento);
		hc2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hc2);
		
		Colaborador colab3 = ColaboradorFactory.getEntity();
		colab3.setEmpresa(empresa);
		colaboradorDao.save(colab3);
		
		HistoricoColaborador hc3 = HistoricoColaboradorFactory.getEntity();
		hc3.setData(dataHistorico);
		hc3.setColaborador(colab3);
		hc3.setAreaOrganizacional(area);
		hc3.setEstabelecimento(estabelecimento);
		hc3.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hc3);
		
		Colaborador colab4 = ColaboradorFactory.getEntity();
		colab4.setEmpresa(empresa);
		colaboradorDao.save(colab4);
		
		HistoricoColaborador hc4 = HistoricoColaboradorFactory.getEntity();
		hc4.setData(dataHistorico);
		hc4.setColaborador(colab4);
		hc4.setAreaOrganizacional(area2);
		hc4.setEstabelecimento(estabelecimento2);
		hc4.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hc4);
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa);
		curso1.setCargaHoraria(10*60);
		cursoDao.save(curso1);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		turma1.setCusto(200.0);
		turma1.setDataPrevIni(dataDoisMesesDepois);
		turma1.setDataPrevFim(dataTresMesesDepois);
		turma1.setRealizada(true);
		turmaDao.save(turma1);

		ColaboradorTurma colabTurma1 = ColaboradorTurmaFactory.getEntity();
		colabTurma1.setColaborador(colab1);
		colabTurma1.setCurso(curso1);
		colabTurma1.setTurma(turma1);
		colaboradorTurmaDao.save(colabTurma1);
		
		ColaboradorTurma colabTurma2 = ColaboradorTurmaFactory.getEntity();
		colabTurma2.setColaborador(colab2);
		colabTurma2.setCurso(curso1);
		colabTurma2.setTurma(turma1);
		colaboradorTurmaDao.save(colabTurma2);
		
		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa);
		curso2.setCargaHoraria(30*60);
		cursoDao.save(curso2);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso2);
		turma2.setCusto(500.50);
		turma2.setDataPrevIni(dataDoisMesesDepois);
		turma2.setDataPrevFim(dataQuatroMesesDepois);
		turma2.setRealizada(true);
		turmaDao.save(turma2);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity();
		diaTurma1.setTurma(turma2);
		diaTurma1.setDia(DateUtil.criarDataMesAno(5, 4, 2014));
		diaTurmaDao.save(diaTurma1);

		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity();
		diaTurma2.setTurma(turma2);
		diaTurma2.setDia(DateUtil.criarDataMesAno(20, 4, 2014));
		diaTurmaDao.save(diaTurma2);
		
		DiaTurma diaTurma3 = DiaTurmaFactory.getEntity();
		diaTurma3.setTurma(turma2);
		diaTurma3.setDia(DateUtil.criarDataMesAno(5, 6, 2014));
		diaTurmaDao.save(diaTurma3);

		ColaboradorTurma colabTurma3 = ColaboradorTurmaFactory.getEntity();
		colabTurma3.setColaborador(colab3);
		colabTurma3.setCurso(curso2);
		colabTurma3.setTurma(turma2);
		colaboradorTurmaDao.save(colabTurma3);
		
		ColaboradorTurma colabTurma4 = ColaboradorTurmaFactory.getEntity();
		colabTurma4.setColaborador(colab4);
		colabTurma4.setCurso(curso1);
		colabTurma4.setTurma(turma1);
		colaboradorTurmaDao.save(colabTurma4);
		
		Turma turmaForaDaConsulta = TurmaFactory.getEntity();
		turmaForaDaConsulta.setCurso(curso2);
		turmaForaDaConsulta.setCusto(9582.00);
		turmaForaDaConsulta.setDataPrevIni(dataQuatroMesesDepois);
		turmaForaDaConsulta.setDataPrevFim(dataQuatroMesesDepois);
		turmaForaDaConsulta.setRealizada(true);
		turmaDao.save(turmaForaDaConsulta);

		IndicadorTreinamento result = cursoDao.findIndicadorHorasTreinamentos(dataDoisMesesDepois, dataTresMesesDepois, new Long[]{empresa.getId()}, new Long[]{area.getId()}, null, new Long[]{estabelecimento.getId()});
		
		assertEquals("Qtde de colaboradores com filtro", 3, (int)result.getQtdColaboradoresFiltrados());
		assertEquals("Qtde de colaboradores inscritos", 4, (int)result.getQtdColaboradoresInscritos());
		assertEquals("Carga horaria", 50.0, result.getSomaHoras());
		assertEquals("Carga horaria Ratiada", 40.0, result.getSomaHorasRatiada());
		assertEquals("Custos", Math.round(633.83), Math.round(result.getSomaCustos()));
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
		cursoDao.save(curso1);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setEmpresa(empresa);
		turma1.setCurso(curso1);
		turma1.setCusto(200.0);
		turma1.setDataPrevIni(dataDoisMesesDepois.getTime());
		turma1.setDataPrevFim(dataTresMesesDepois.getTime());
		turmaDao.save(turma1);

		Turma turmaForaDaConsulta = TurmaFactory.getEntity();
		turmaForaDaConsulta.setEmpresa(empresa);
		turmaForaDaConsulta.setCurso(curso1);
		turmaForaDaConsulta.setDataPrevIni(dataQuatroMesesDepois.getTime());
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
		turmaRealizadaForaDoPeriodo.setDataPrevIni(hoje);
		turmaRealizadaForaDoPeriodo.setDataPrevFim(dataDoisMesesDepois.getTime());
		turmaDao.save(turmaRealizadaForaDoPeriodo);

		assertEquals(new Integer(2), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa.getId()}, null, true));

	}

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
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa1);
		cursoDao.save(curso1);

		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa2);
		cursoDao.save(curso2);
		
		Turma turmaRealizada = TurmaFactory.getEntity();
		turmaRealizada.setCurso(curso1);
		turmaRealizada.setRealizada(true);
		turmaRealizada.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaRealizada.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaRealizada);

		Turma turmaNaoRealizada1 = TurmaFactory.getEntity();
		turmaNaoRealizada1.setCurso(curso1);
		turmaNaoRealizada1.setRealizada(false);
		turmaNaoRealizada1.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaNaoRealizada1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaNaoRealizada1);

		Turma turmaNaoRealizada2 = TurmaFactory.getEntity();
		turmaNaoRealizada2.setCurso(curso2);
		turmaNaoRealizada2.setRealizada(false);
		turmaNaoRealizada2.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaNaoRealizada2.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaNaoRealizada2);
		
		assertEquals("Treinamentos não realizados da empresa1", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId()}, null, false));
		assertEquals("Treinamentos realizados da empresa1", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId()}, null, true));
		assertEquals("Treinamentos não realizados da empresa2", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa2.getId()}, null, false));
		assertEquals("Treinamentos realizados da empresa2", new Integer(0), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa2.getId()}, null, true));
		assertEquals("Treinamentos não realizados das empresas", new Integer(2), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId(), empresa2.getId()}, null, false));
		assertEquals("Treinamentos realizados das empresas", new Integer(1), cursoDao.countTreinamentos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa1.getId(), empresa2.getId()}, null, true));

	}
	
	public void testFindIndicadorTotalHorasTreinamento()
	{
		Date dataInicio = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataFim = DateUtil.criarDataDiaMesAno("31/05/2015");

    	Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setNome("Curso 1");
		curso1.setCargaHoraria(90);
		curso1.setEmpresa(empresa1);
		cursoDao.save(curso1);
		
		Turma turmaRealizadaCurso1 = TurmaFactory.getEntity();
		turmaRealizadaCurso1.setDescricao("Turma1");
		turmaRealizadaCurso1.setCurso(curso1);
		turmaRealizadaCurso1.setRealizada(true);
		turmaRealizadaCurso1.setDataPrevIni(dataInicio);
		turmaRealizadaCurso1.setDataPrevFim(dataFim);
		turmaRealizadaCurso1.setEmpresa(empresa1);
		turmaDao.save(turmaRealizadaCurso1);
		
		Turma turmaNaoRealizadaCurso1 = TurmaFactory.getEntity();
		turmaNaoRealizadaCurso1.setDescricao("Turma2");
		turmaNaoRealizadaCurso1.setCurso(curso1);
		turmaNaoRealizadaCurso1.setRealizada(false);
		turmaNaoRealizadaCurso1.setDataPrevIni(dataInicio);
		turmaNaoRealizadaCurso1.setDataPrevFim(dataFim);
		turmaNaoRealizadaCurso1.setEmpresa(empresa1);
		turmaDao.save(turmaNaoRealizadaCurso1);
		
		turmaDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(new Integer(90), cursoDao.findCargaHorariaTotalTreinamento(new Long[]{curso1.getId()}, new Long[] {empresa1.getId()}, dataInicio, dataFim, true));
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
	
	public void testFindByEmpresaIdAndCursosId()
	{
		Empresa emp1 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp1);

		Empresa emp2 = EmpresaFactory.getEmpresa();
		empresaDao.save(emp2);

		Curso curso1 = CursoFactory.getEntity();
		curso1.setNome("Curso de direção");
		curso1.setEmpresa(emp1);
		cursoDao.save(curso1);
		
		Curso curso2 = CursoFactory.getEntity();
		curso2.setNome("Direção");
		curso2.setEmpresa(emp2);
		cursoDao.save(curso2);
		
		Collection<Curso> cursos = cursoDao.findByEmpresaIdAndCursosId(emp1.getId(), null);
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(null, curso1.getId());
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(emp1.getId(), curso1.getId());
		assertEquals(1, cursos.size());

		cursos = cursoDao.findByEmpresaIdAndCursosId(emp2.getId(), null);
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(emp2.getId(), curso2.getId());
		assertEquals(1, cursos.size());
		
		cursos = cursoDao.findByEmpresaIdAndCursosId(null, null);
		assertTrue(cursos.size() >= 2);
	}

	public void testSomaDespesasPorCurso() 
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2012);
		Date dataFim = DateUtil.criarDataMesAno(2, 1, 2012);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setEmpresa(empresa1);
		cursoDao.save(curso1);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setEmpresa(empresa1);
		turma1.setDataPrevIni(dataIni);
		turma1.setDataPrevFim(dataFim);
		turma1.setRealizada(true);
		turma1.setCurso(curso1);
		turma1.setCusto(150.0);
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa1);
		turma2.setDataPrevIni(dataIni);
		turma2.setDataPrevFim(dataFim);
		turma2.setRealizada(true);
		turma2.setCurso(curso1);
		turma2.setCusto(250.0);
		turmaDao.save(turma2);
		
		Curso curso2 = CursoFactory.getEntity();
		curso2.setEmpresa(empresa2);
		cursoDao.save(curso2);
		
		Turma turma3 = TurmaFactory.getEntity();
		turma3.setEmpresa(empresa2);	
		turma3.setDataPrevIni(dataIni);
		turma3.setDataPrevFim(dataFim);
		turma3.setRealizada(true);
		turma3.setCurso(curso2);
		turma3.setCusto(300.0);
		turmaDao.save(turma3);
		
		Collection<Curso> cursos = cursoDao.somaDespesasPorCurso(dataIni, dataFim, new Long[]{empresa1.getId(), empresa2.getId()},  null);
		Collection<Curso> cursosOutraEmpresa = cursoDao.somaDespesasPorCurso(dataIni, dataFim, new Long[]{1111111111111L}, null);
		
		assertEquals(2, cursos.size());
		assertEquals(400.0, ((Curso)cursos.toArray()[0]).getTotalDespesas());
		assertEquals(300.0, ((Curso)cursos.toArray()[1]).getTotalDespesas());
		assertEquals(0, cursosOutraEmpresa.size());
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
}