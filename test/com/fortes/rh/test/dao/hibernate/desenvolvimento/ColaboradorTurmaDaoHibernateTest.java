package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.PrioridadeTreinamentoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorTurmaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorTurma>
{
    private ColaboradorTurmaDao colaboradorTurmaDao;
    private CursoDao cursoDao;
    private TurmaDao turmaDao;
    private DiaTurmaDao diaTurmaDao;
    private ColaboradorPresencaDao colaboradorPresencaDao;
    private ColaboradorDao colaboradorDao;
    private HistoricoColaboradorDao historicoColaboradorDao;
    private AreaOrganizacionalDao areaOrganizacionalDao;
    private PrioridadeTreinamentoDao prioridadeTreinamentoDao;
    private DNTDao DNTDao;
    private EstabelecimentoDao estabelecimentoDao;
    private EmpresaDao empresaDao;
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private CertificacaoDao certificacaoDao;
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao;

    public ColaboradorTurma getEntity()
    {
        ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
        colaboradorTurma.setOrigemDnt(true);
        colaboradorTurma.setId(null);

        return colaboradorTurma;
    }

    public void testFindColaboradoresByCursoTurmaIsNull()
    {
        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setColaborador(colaborador);
        colaboradorTurma.setOrigemDnt(true);
        colaboradorTurma.setTurma(null);
        colaboradorTurma.setCurso(curso);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        assertEquals(1, colaboradorTurmaDao.findColaboradoresByCursoTurmaIsNull(curso.getId()).size());
    }

    public void testUpdateTurmaEPrioridade()
    {
        PrioridadeTreinamento prioridadeTreinamento = new PrioridadeTreinamento();
        prioridadeTreinamento = prioridadeTreinamentoDao.save(prioridadeTreinamento);

        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Turma turma = TurmaFactory.getEntity();
        turma.setCurso(curso);
        turma.setDescricao("TESTE");
        turma = turmaDao.save(turma);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setCurso(curso);
        colaboradorTurma.setColaborador(colaborador);
        colaboradorTurma.setTurma(null);
        colaboradorTurma.setPrioridadeTreinamento(null);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        colaboradorTurmaDao.updateTurmaEPrioridade(colaboradorTurma.getId(), turma.getId(), prioridadeTreinamento.getId());

        Collection<ColaboradorTurma> retorno = colaboradorTurmaDao.findByTurmaCurso(curso.getId());
        assertEquals(1, retorno.size());

        ColaboradorTurma colaboradorTurmaTeste = (ColaboradorTurma) retorno.toArray()[0];
        assertEquals(turma.getDescricao(), colaboradorTurmaTeste.getTurma().getDescricao());
    }

    public void testFindByDNTColaboradores()
    {
    	PrioridadeTreinamento prioridadeTreinamento = new PrioridadeTreinamento();
    	prioridadeTreinamento = prioridadeTreinamentoDao.save(prioridadeTreinamento);

    	Curso curso = CursoFactory.getEntity();
    	curso = cursoDao.save(curso);

    	Turma turma = TurmaFactory.getEntity();
    	turma.setCurso(curso);
    	turma = turmaDao.save(turma);

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador = colaboradorDao.save(colaborador);

    	DNT dnt = DntFactory.getEntity();
    	dnt = DNTDao.save(dnt);

    	ColaboradorTurma colaboradorTurma = getEntity();
    	colaboradorTurma.setCurso(curso);
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurma.setTurma(turma);
    	colaboradorTurma.setDnt(dnt);
    	colaboradorTurma.setPrioridadeTreinamento(prioridadeTreinamento);
    	colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador);

    	Collection<ColaboradorTurma> retorno = colaboradorTurmaDao.findByDNTColaboradores(dnt, colaboradores);
    	assertEquals(1, retorno.size());
    }
    
    public void testFindColaboradoresComEmailByTurma()
    {
    	Turma java = TurmaFactory.getEntity();
    	turmaDao.save(java);
    	
    	Colaborador joao = ColaboradorFactory.getEntity();
    	joao.setDesligado(false);
    	joao.setEmailColaborador("b@b.com.br");
    	colaboradorDao.save(joao);
    	
    	Colaborador maria = ColaboradorFactory.getEntity();
    	maria.setDesligado(true);
    	maria.setEmailColaborador("teste@b.com.br");
    	colaboradorDao.save(maria);
    	
    	Colaborador pedro = ColaboradorFactory.getEntity();
    	pedro.setDesligado(false);
    	pedro.setEmailColaborador("");
    	colaboradorDao.save(pedro);
    	
    	Colaborador debora = ColaboradorFactory.getEntity();
    	debora.setDesligado(false);
    	debora.setEmailColaborador(null);
    	colaboradorDao.save(debora);
    	
    	ColaboradorTurma colaboradorTurmaJoao = getEntity();
    	colaboradorTurmaJoao.setColaborador(joao);
    	colaboradorTurmaJoao.setTurma(java);
    	colaboradorTurmaDao.save(colaboradorTurmaJoao);
    	
    	ColaboradorTurma colaboradorTurmaMaria = getEntity();
    	colaboradorTurmaMaria.setColaborador(maria);
    	colaboradorTurmaMaria.setTurma(java);
    	colaboradorTurmaDao.save(colaboradorTurmaMaria);
    	
    	ColaboradorTurma colaboradorTurmaPedro = getEntity();
    	colaboradorTurmaPedro.setColaborador(pedro);
    	colaboradorTurmaPedro.setTurma(java);
    	colaboradorTurmaDao.save(colaboradorTurmaPedro);
    	
    	ColaboradorTurma colaboradorTurmaDebora = getEntity();
    	colaboradorTurmaDebora.setColaborador(debora);
    	colaboradorTurmaDebora.setTurma(java);
    	colaboradorTurmaDao.save(colaboradorTurmaDebora);
    	
    	Collection<ColaboradorTurma> retorno = colaboradorTurmaDao.findColaboradoresComEmailByTurma(java.getId());
    	assertEquals(1, retorno.size());
    }
    
    public void testFindEmpresaDoColaborador()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa = empresaDao.save(empresa);

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setEmpresa(empresa);
    	colaborador = colaboradorDao.save(colaborador);

    	ColaboradorTurma colaboradorTurma = getEntity();
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

    	assertEquals(empresa, colaboradorTurmaDao.findEmpresaDoColaborador(colaboradorTurma));
    }
    
    @SuppressWarnings("unused")
    public void testFindColaboradoresComCustoTreinamentos()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
    	
    	Colaborador joao = ColaboradorFactory.getEntity();
    	joao.setEmpresa(empresa);
    	colaboradorDao.save(joao);

    	Colaborador maria = ColaboradorFactory.getEntity();
    	maria.setEmpresa(empresa);
    	colaboradorDao.save(maria);
    	
    	Curso curso = CursoFactory.getEntity();
    	curso.setNome("java");
    	cursoDao.save(curso);
    	
    	Turma turmaNoPeriodo = montaTurma("turma I", DateUtil.criarDataMesAno(25, 07, 2011), DateUtil.criarDataMesAno(29, 07, 2011), true, 100.0);
    	Turma turmaNaoRealizada = montaTurma("turma Abandonada", DateUtil.criarDataMesAno(25, 07, 2011), DateUtil.criarDataMesAno(29, 07, 2011), false, 300.0);
    	Turma turmaForaPeriodo = montaTurma("turma Antiga", DateUtil.criarDataMesAno(25, 07, 2009), DateUtil.criarDataMesAno(29, 07, 2009), true, 100.0);
    	Turma turmaNoFuturo = montaTurma("turma no Futuro", DateUtil.criarDataMesAno(25, 07, 2012), DateUtil.criarDataMesAno(29, 07, 2012), true, 100.0);
    	
		ColaboradorTurma colaboradorTurmaMaria = montaColaboradorTurma(maria, curso, turmaNoPeriodo);
    	ColaboradorTurma colaboradorTurmaJoao = montaColaboradorTurma(joao, curso, turmaNoPeriodo);
    	ColaboradorTurma colaboradorTurmaJoaoNaoRealizada = montaColaboradorTurma(joao, curso, turmaNaoRealizada);
    	ColaboradorTurma colaboradorTurmaJoaoPassado = montaColaboradorTurma(joao, curso, turmaForaPeriodo);
    	ColaboradorTurma colaboradorTurmaJoaoFuturo = montaColaboradorTurma(joao, curso, turmaNoFuturo);
    	
    	Collection<ColaboradorTurma> colabTurmas = colaboradorTurmaDao.findColaboradoresComCustoTreinamentos(joao.getId(), DateUtil.criarDataMesAno(25, 07, 2011), DateUtil.criarDataMesAno(27, 07, 2011), null);	
    	assertEquals(2, colabTurmas.size());
    	
    	colabTurmas = colaboradorTurmaDao.findColaboradoresComCustoTreinamentos(joao.getId(), DateUtil.criarDataMesAno(25, 07, 2011), DateUtil.criarDataMesAno(27, 07, 2011), true);	
    	assertEquals(1, colabTurmas.size());
    	
    	ColaboradorTurma colabTurmaJoao = (ColaboradorTurma)colabTurmas.toArray()[0];
    	assertEquals(50.0, colabTurmaJoao.getCustoRateado());
    }

	private ColaboradorTurma montaColaboradorTurma(Colaborador colaborador, Curso curso, Turma turma) {
		ColaboradorTurma colaboradorTurma = getEntity();
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurma.setCurso(curso);
    	colaboradorTurma.setTurma(turma);
    	
		return colaboradorTurmaDao.save(colaboradorTurma);
	}

	private Turma montaTurma(String descricao, Date dataIni, Date dataFim, boolean realizada, Double custo) {
		Turma turma = TurmaFactory.getEntity();
    	turma.setDescricao(descricao);
    	turma.setDataPrevIni(dataIni);
    	turma.setDataPrevFim(dataFim);
    	turma.setCusto(custo);
    	turma.setRealizada(realizada);
    	
		return turmaDao.save(turma);
	}

    public void testUpdateColaboradorTurmaSetPrioridade()
    {
        PrioridadeTreinamento prioridadeTreinamento = new PrioridadeTreinamento();
        prioridadeTreinamento = prioridadeTreinamentoDao.save(prioridadeTreinamento);

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        colaboradorTurmaDao.updateColaboradorTurmaSetPrioridade(colaboradorTurma.getId(), prioridadeTreinamento.getId());

        assertEquals(colaboradorTurma.getId(), colaboradorTurmaDao.findById(colaboradorTurma.getId()).getId());
    }

    public void testFindByTurmaCurso()
    {
    	 Curso curso = CursoFactory.getEntity();
         curso = cursoDao.save(curso);

         Turma turma = TurmaFactory.getEntity();
         turma.setCurso(curso);
         turma = turmaDao.save(turma);

         Colaborador colaborador = ColaboradorFactory.getEntity();
         colaborador = colaboradorDao.save(colaborador);

         ColaboradorTurma colaboradorTurma = getEntity();
         colaboradorTurma.setColaborador(colaborador);
         colaboradorTurma.setTurma(turma);
         colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

         Collection<ColaboradorTurma> retorno = colaboradorTurmaDao.findByTurmaCurso(curso.getId());
         assertEquals(1, retorno.size());
    }

    public void testGetCount()
    {
    	Turma turma = TurmaFactory.getEntity();
    	turmaDao.save(turma);
    	
    	ColaboradorTurma colaboradorTurma1 = getEntity();
    	colaboradorTurma1.setTurma(turma);
    	colaboradorTurmaDao.save(colaboradorTurma1);

    	ColaboradorTurma colaboradorTurma2 = getEntity();
    	colaboradorTurma2.setTurma(turma);
    	colaboradorTurmaDao.save(colaboradorTurma2);
    	
    	assertEquals(new Integer(2), colaboradorTurmaDao.getCount(turma.getId(), null));
    }
    
    public void testGetCountComEmpresa()
    {
    	Empresa fortes = EmpresaFactory.getEmpresa(1L);
    	empresaDao.save(fortes);
    	
    	Empresa ente = EmpresaFactory.getEmpresa(2L);
    	empresaDao.save(ente);
    	
       	Turma turma = TurmaFactory.getEntity();
       	turma.setEmpresa(fortes);
    	turmaDao.save(turma);
    	
    	Colaborador joao = ColaboradorFactory.getEntity();
    	joao.setEmpresa(fortes);
    	colaboradorDao.save(joao);

    	Colaborador maria = ColaboradorFactory.getEntity();
    	maria.setEmpresa(ente);
    	colaboradorDao.save(maria);

    	ColaboradorTurma mariaTurma = getEntity();
    	mariaTurma.setColaborador(maria);
    	mariaTurma.setTurma(turma);
    	colaboradorTurmaDao.save(mariaTurma);
    	
    	ColaboradorTurma joaoTurma = getEntity();
    	joaoTurma.setColaborador(joao);
    	joaoTurma.setTurma(turma);
    	colaboradorTurmaDao.save(joaoTurma);
    	
    	assertEquals(new Integer(1), colaboradorTurmaDao.getCount(turma.getId(), fortes.getId()));
    }
    
    public void testFindColaboradorByTurma()
    {
    	Turma turma = TurmaFactory.getEntity();
    	turma = turmaDao.save(turma);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador = colaboradorDao.save(colaborador);
    	
    	ColaboradorTurma colaboradorTurma = getEntity();
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurma.setTurma(turma);
    	colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);
    	
    	Collection<ColaboradorTurma> retorno = colaboradorTurmaDao.findColaboradorByTurma(turma.getId());
    	assertEquals(1, retorno.size());
    }

    public void testFindByTurmaSemPresenca()
    {
    	Curso curso = CursoFactory.getEntity();
    	curso = cursoDao.save(curso);

    	Turma turma = TurmaFactory.getEntity();
    	turma.setCurso(curso);
    	turmaDao.save(turma);

    	DiaTurma diaTurma = DiaTurmaFactory.getEntity();
    	diaTurmaDao.save(diaTurma);

    	ColaboradorTurma colaboradorTurma = getEntity();
    	colaboradorTurma.setTurma(turma);
    	colaboradorTurmaDao.save(colaboradorTurma);

    	ColaboradorTurma colaboradorTurmaSemPresenca = getEntity();
    	colaboradorTurmaSemPresenca.setTurma(turma);
    	colaboradorTurmaDao.save(colaboradorTurmaSemPresenca);

    	ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity();
    	colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
    	colaboradorPresenca.setDiaTurma(diaTurma);
    	colaboradorPresencaDao.save(colaboradorPresenca);

    	Collection<ColaboradorTurma> retorno = colaboradorTurmaDao.findByTurmaSemPresenca(turma.getId(), diaTurma.getId());
    	assertEquals(1, retorno.size());
    }

    public void testFindByTurma()
    {
        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Turma turma = TurmaFactory.getEntity();
        turma.setCurso(curso);
        turma = turmaDao.save(turma);

        Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
        estabelecimentoDao.save(estabelecimento);
        
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

        Colaborador colaborador = ColaboradorFactory.getEntity();

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
        historicoColaborador.setColaborador(colaborador);
        historicoColaborador.setEstabelecimento(estabelecimento);
        historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2001));
        historicoColaborador.setAreaOrganizacional(areaOrganizacional);
        historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

        colaborador.setHistoricoColaborador(historicoColaborador);
        colaborador = colaboradorDao.save(colaborador);
        
        PrioridadeTreinamento prioridadeTreinamento = new PrioridadeTreinamento();
        prioridadeTreinamento = prioridadeTreinamentoDao.save(prioridadeTreinamento);

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setTurma(turma);
        colaboradorTurma.setColaborador(colaborador);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        Collection<ColaboradorTurma> retornos = colaboradorTurmaDao.findByTurma(turma.getId(), null, null, null, null);

        ColaboradorTurma colaboradorTurmaRetorno = (ColaboradorTurma) retornos.toArray()[0];

        assertEquals(colaboradorTurma.getId(), colaboradorTurmaRetorno.getId());
        assertEquals(areaOrganizacional.getNome(), colaboradorTurmaRetorno.getColaborador().getAreaOrganizacional().getNome());
    }

    public void testFindIdEstabelecimentosByTurma()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
    	
    	Curso curso = CursoFactory.getEntity();
    	cursoDao.save(curso);
    	
    	Turma turma = TurmaFactory.getEntity();
    	turma.setCurso(curso);
    	turmaDao.save(turma);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setEmpresa(empresa);
    	colaboradorDao.save(colaborador);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);
    	
    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setColaborador(colaborador);
    	historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2001));
    	historicoColaborador.setEstabelecimento(estabelecimento);
    	historicoColaborador = historicoColaboradorDao.save(historicoColaborador);
    	
    	ColaboradorTurma colaboradorTurma = getEntity();
    	colaboradorTurma.setTurma(turma);
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurmaDao.save(colaboradorTurma);
    	
    	assertEquals(1, colaboradorTurmaDao.findIdEstabelecimentosByTurma(turma.getId(),empresa.getId()).size());
    }

    public void testFindRelatorioSemIndicacaoDeTreinamento()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Colaborador colaboradorInscritoTurmaAtual = ColaboradorFactory.getEntity();
    	colaboradorInscritoTurmaAtual.setEmpresa(empresa);
    	colaboradorDao.save(colaboradorInscritoTurmaAtual);

    	Colaborador colaboradorInscritoTurmaDoisMesesAtras = ColaboradorFactory.getEntity();
    	colaboradorInscritoTurmaDoisMesesAtras.setNome("Colaborador 2");
    	colaboradorInscritoTurmaDoisMesesAtras.setEmpresa(empresa);
    	colaboradorDao.save(colaboradorInscritoTurmaDoisMesesAtras);

    	Colaborador colaboradorSemCurso = ColaboradorFactory.getEntity();
    	colaboradorSemCurso.setNome("Colaborador Sem Curso");
    	colaboradorSemCurso.setEmpresa(empresa);
    	colaboradorDao.save(colaboradorSemCurso);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);

    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional.setNome("Area 1");
    	areaOrganizacionalDao.save(areaOrganizacional);

    	HistoricoColaborador historicoColaboradorDoInscritoDoisMeses = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorDoInscritoDoisMeses.setColaborador(colaboradorInscritoTurmaDoisMesesAtras);
    	historicoColaboradorDoInscritoDoisMeses.setData(new Date());
    	historicoColaboradorDoInscritoDoisMeses.setAreaOrganizacional(areaOrganizacional);
    	historicoColaboradorDoInscritoDoisMeses.setEstabelecimento(estabelecimento);
    	historicoColaboradorDao.save(historicoColaboradorDoInscritoDoisMeses);

    	HistoricoColaborador historicoColaboradorDoInscritoAtual = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorDoInscritoAtual.setColaborador(colaboradorInscritoTurmaAtual);
    	historicoColaboradorDoInscritoAtual.setData(DateUtil.criarDataMesAno(1, 1, 1999));
    	historicoColaboradorDoInscritoAtual.setAreaOrganizacional(areaOrganizacional);
    	historicoColaboradorDoInscritoAtual.setEstabelecimento(estabelecimento);
    	historicoColaboradorDao.save(historicoColaboradorDoInscritoAtual);

    	HistoricoColaborador historicoColaboradorSemCurso = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorSemCurso.setColaborador(colaboradorSemCurso);
    	historicoColaboradorSemCurso.setData(DateUtil.criarDataMesAno(1, 1, 1999));
    	historicoColaboradorSemCurso.setAreaOrganizacional(areaOrganizacional);
    	historicoColaboradorSemCurso.setEstabelecimento(estabelecimento);
    	historicoColaboradorDao.save(historicoColaboradorSemCurso);

    	Date hoje = new Date();
    	Calendar dataDoisMesesAntes = Calendar.getInstance();
    	dataDoisMesesAntes.setTime(hoje);
    	dataDoisMesesAntes.add(Calendar.MONTH, -2);

    	Calendar dataUmMesAntes = Calendar.getInstance();
    	dataUmMesAntes.setTime(hoje);
    	dataUmMesAntes.add(Calendar.MONTH, -1);

    	Curso curso = CursoFactory.getEntity();
    	cursoDao.save(curso);

    	Turma turmaDoisMesesAtras = TurmaFactory.getEntity();
    	turmaDoisMesesAtras.setDataPrevFim(dataDoisMesesAntes.getTime());
    	turmaDao.save(turmaDoisMesesAtras);

    	Turma turmaAtual = TurmaFactory.getEntity();
    	turmaAtual.setDataPrevFim(hoje);
    	turmaDao.save(turmaAtual);

    	ColaboradorTurma colaboradorTurmaDoisMesesAtras = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurmaDoisMesesAtras.setColaborador(colaboradorInscritoTurmaDoisMesesAtras);
    	colaboradorTurmaDoisMesesAtras.setCurso(curso);
    	colaboradorTurmaDoisMesesAtras.setTurma(turmaDoisMesesAtras);
    	colaboradorTurmaDao.save(colaboradorTurmaDoisMesesAtras);

    	ColaboradorTurma colaboradorTurmaAtual = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurmaAtual.setColaborador(colaboradorInscritoTurmaAtual);
    	colaboradorTurmaAtual.setCurso(curso);
    	colaboradorTurmaAtual.setTurma(turmaAtual);
    	colaboradorTurmaDao.save(colaboradorTurmaAtual);


    	Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
    	Long[] areaIds = new Long[]{areaOrganizacional.getId()};

    	Collection<ColaboradorTurma> naoFazemCursoUmMesOuMais = colaboradorTurmaDao.findRelatorioSemIndicacaoDeTreinamento(empresa.getId(), areaIds, estabelecimentoIds, dataUmMesAntes.getTime());
    	assertEquals(2, naoFazemCursoUmMesOuMais.size());
    	assertTrue(naoFazemCursoUmMesOuMais.contains(colaboradorTurmaDoisMesesAtras));
    }

    public void testFiltroRelatorioMatriz()
    {
        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Turma turma = TurmaFactory.getEntity();
        turma.setCurso(curso);
        turma = turmaDao.save(turma);

        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

        Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
        estabelecimento = estabelecimentoDao.save(estabelecimento);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
        historicoColaborador.setColaborador(colaborador);
        historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2001));
        historicoColaborador.setAreaOrganizacional(areaOrganizacional);
        historicoColaborador.setEstabelecimento(estabelecimento);
        historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

        PrioridadeTreinamento prioridadeTreinamento = new PrioridadeTreinamento();
        prioridadeTreinamento.setSigla("sigla");
        prioridadeTreinamento = prioridadeTreinamentoDao.save(prioridadeTreinamento);

        DNT dnt = DntFactory.getEntity();
        dnt = DNTDao.save(dnt);

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setTurma(turma);
        colaboradorTurma.setColaborador(colaborador);
        colaboradorTurma.setDnt(dnt);
        colaboradorTurma.setOrigemDnt(true);
        colaboradorTurma.setPrioridadeTreinamento(prioridadeTreinamento);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        HashMap parametros = new HashMap();
        parametros.put("data", DateUtil.criarDataMesAno(01, 01, 2001));

        Collection<Long> areaIds = new ArrayList<Long>();
        areaIds.add(areaOrganizacional.getId());
        parametros.put("areas", areaIds);

        Collection<Long> estabelecimentoIds = new ArrayList<Long>();
        estabelecimentoIds.add(estabelecimento.getId());
        parametros.put("estabelecimentos", estabelecimentoIds);

        Collection<ColaboradorTurma> retornos = colaboradorTurmaDao.filtroRelatorioMatriz(parametros);

        ColaboradorTurma colaboradorTurmaRetorno = (ColaboradorTurma) retornos.toArray()[0];

        assertEquals(colaboradorTurma.getId(), colaboradorTurmaRetorno.getId());
    }

    public void testFiltroRelatorioPlanoTrei()
    {
        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Turma turma = TurmaFactory.getEntity();
        turma = turmaDao.save(turma);

        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

        Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
        estabelecimento = estabelecimentoDao.save(estabelecimento);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
        historicoColaborador.setColaborador(colaborador);
        historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2001));
        historicoColaborador.setAreaOrganizacional(areaOrganizacional);
        historicoColaborador.setEstabelecimento(estabelecimento);
        historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

        DNT dnt = DntFactory.getEntity();
        dnt = DNTDao.save(dnt);

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setTurma(turma);
        colaboradorTurma.setColaborador(colaborador);
        colaboradorTurma.setDnt(dnt);
        colaboradorTurma.setCurso(curso);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        HashMap parametros = new HashMap();
        parametros.put("dntId", dnt.getId());

        Collection<Long> areaIds = new ArrayList<Long>();
        areaIds.add(areaOrganizacional.getId());
        parametros.put("areas", areaIds);
        Collection<Long> estabelecimentoIds = new ArrayList<Long>();
        estabelecimentoIds.add(estabelecimento.getId());
        parametros.put("estabelecimentos", estabelecimentoIds);

        Collection<ColaboradorTurma> retornos = colaboradorTurmaDao.filtroRelatorioPlanoTrei(parametros);

        ColaboradorTurma colaboradorTurmaRetorno = (ColaboradorTurma) retornos.toArray()[0];

        assertEquals(colaboradorTurma.getId(), colaboradorTurmaRetorno.getId());

        parametros = new HashMap();
        parametros.put("dntId", dnt.getId());
        parametros.put("semPlano", true);
        parametros.put("colaboradorId", colaborador.getId());

        colaboradorTurma.setTurma(null);
        colaboradorTurmaDao.update(colaboradorTurma);

        retornos = colaboradorTurmaDao.filtroRelatorioPlanoTrei(parametros);

        colaboradorTurmaRetorno = (ColaboradorTurma) retornos.toArray()[0];

        assertEquals(colaboradorTurma.getId(), colaboradorTurmaRetorno.getId());
    }

    public void testUpdateColaboradorTurmaSetAprovacao()
    {
        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setAprovado(false);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        Exception exception = null;
        try
        {
            colaboradorTurmaDao.updateColaboradorTurmaSetAprovacao(colaboradorTurma.getId(), true);

        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
    }

	public void testFindCustoRateado()
    {
        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Turma turma = TurmaFactory.getEntity();
        turma.setCurso(curso);
        turma.setCusto(200D);
        turma = turmaDao.save(turma);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        ColaboradorTurma colaboradorTurma1 = getEntity();
        colaboradorTurma1.setTurma(turma);
        colaboradorTurma1.setColaborador(colaborador);
        colaboradorTurma1 = colaboradorTurmaDao.save(colaboradorTurma1);

        ColaboradorTurma colaboradorTurma2 = getEntity();
        colaboradorTurma2.setTurma(turma);
        colaboradorTurma2.setColaborador(colaborador);
        colaboradorTurma2 = colaboradorTurmaDao.save(colaboradorTurma2);

        List custos = colaboradorTurmaDao.findCustoRateado();
        for (Object custo : custos)
        {
            Object[] custoRateado = (Object[]) custo;
            if(turma.getId().equals(custoRateado[0]))
                assertEquals(100.0, custoRateado[1]);
        }
    }
	
	public void testFindAprovadosReprovados()
	{
		Date dataIni = DateUtil.montaDataByString("01/01/2008");
		Date dataFim = DateUtil.montaDataByString("01/01/2020");
		
		try {
			colaboradorTurmaDao.findAprovadosReprovados(dataIni, dataFim, null);	
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erro na consulta do SQL");
		}
	}
	
	public void testFindByColaboradorAndTurma2()
	{
		Curso curso = CursoFactory.getEntity();
		curso = cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turma.setCusto(200D);
		turma = turmaDao.save(turma);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Joao Jorge");
		colaborador.setMatricula("589RR12");
		colaborador = colaboradorDao.save(colaborador);
		
		Colaborador colaboradorBusca = ColaboradorFactory.getEntity();
		colaboradorBusca.setNome("jor");
		colaboradorBusca.setMatricula("89rr");
		
		ColaboradorTurma colaboradorTurma = getEntity();
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);
		
		assertEquals(colaboradorTurma, colaboradorTurmaDao.findByColaboradorAndTurma(turma.getId(), colaborador.getId()));
	}

    public void testFindByColaboradorAndTurma()
    {
        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Turma turma = TurmaFactory.getEntity();
        turma.setCurso(curso);
        turma.setCusto(200D);
        turma = turmaDao.save(turma);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador.setNome("Joao Jorge");
        colaborador.setMatricula("589RR12");
        colaborador = colaboradorDao.save(colaborador);

        Long[] colaboradoresIds = new Long[]{colaborador.getId()};
        Colaborador colaboradorBusca = ColaboradorFactory.getEntity();
        colaboradorBusca.setNome("jor");
        colaboradorBusca.setMatricula("89rr");

        ColaboradorTurma colaboradorTurma = getEntity();
        colaboradorTurma.setTurma(turma);
        colaboradorTurma.setCurso(curso);
        colaboradorTurma.setColaborador(colaborador);
        colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);

        Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaDao.findByColaboradorAndTurma(0, 100, colaboradoresIds, curso.getId(), colaboradorBusca);
        assertEquals(1, colaboradorTurmas.size());
    }

    public void testFindByColaboradorAndTurmaVazio()
    {
        Curso curso = CursoFactory.getEntity();
        curso = cursoDao.save(curso);

        Long[] colaboradoresIds = new Long[]{};

        assertTrue(colaboradorTurmaDao.findByColaboradorAndTurma(0, 100, colaboradoresIds, curso.getId(), null).isEmpty());
    }

    public void testFindRelatorioComTreinamento()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Colaborador colaboradorInscritoCurso = ColaboradorFactory.getEntity();
    	colaboradorInscritoCurso.setEmpresa(empresa);
    	colaboradorDao.save(colaboradorInscritoCurso);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);

    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional.setNome("Area 1");
    	areaOrganizacionalDao.save(areaOrganizacional);

    	HistoricoColaborador historicoColaboradorAtualDoInscrito = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorAtualDoInscrito.setColaborador(colaboradorInscritoCurso);
    	historicoColaboradorAtualDoInscrito.setData(DateUtil.criarDataMesAno(1, 1, 1999));
    	historicoColaboradorAtualDoInscrito.setAreaOrganizacional(areaOrganizacional);
    	historicoColaboradorAtualDoInscrito.setEstabelecimento(estabelecimento);
    	historicoColaboradorDao.save(historicoColaboradorAtualDoInscrito);

    	Curso curso = CursoFactory.getEntity();
    	cursoDao.save(curso);

    	Turma turma = TurmaFactory.getEntity();
    	turmaDao.save(turma);

    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setColaborador(colaboradorInscritoCurso);
    	colaboradorTurma.setCurso(curso);
    	colaboradorTurma.setTurma(turma);
    	colaboradorTurmaDao.save(colaboradorTurma);

    	Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
    	Long[] areaIds = new Long[]{areaOrganizacional.getId()};
    	Long[] colaboradorTurmaIds = new Long[]{colaboradorTurma.getId()};

    	Collection<ColaboradorTurma> colaboradoresComTreinamento = colaboradorTurmaDao.findRelatorioComTreinamento(empresa.getId(), curso, areaIds, estabelecimentoIds, colaboradorTurmaIds);
    	assertEquals(1, colaboradoresComTreinamento.size());
    }
   
    public void testFindRelatorioSemTreinamento()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Colaborador colaboradorInscritoCurso = ColaboradorFactory.getEntity();
    	colaboradorInscritoCurso.setEmpresa(empresa);
    	colaboradorDao.save(colaboradorInscritoCurso);

    	Colaborador colaboradorNaoInscritoCurso = ColaboradorFactory.getEntity();
    	colaboradorNaoInscritoCurso.setNome("Colab 2");
    	colaboradorNaoInscritoCurso.setEmpresa(empresa);
    	colaboradorDao.save(colaboradorNaoInscritoCurso);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);

    	AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional1.setNome("Area 1");
    	areaOrganizacionalDao.save(areaOrganizacional1);

    	AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional2.setNome("Area 2");
    	areaOrganizacionalDao.save(areaOrganizacional2);

    	HistoricoColaborador historicoColaboradorAtualDoInscrito = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorAtualDoInscrito.setColaborador(colaboradorInscritoCurso);
    	historicoColaboradorAtualDoInscrito.setData(DateUtil.criarDataMesAno(1, 1, 1999));
    	historicoColaboradorAtualDoInscrito.setAreaOrganizacional(areaOrganizacional1);
    	historicoColaboradorDao.save(historicoColaboradorAtualDoInscrito);

    	HistoricoColaborador historicoColaboradorPassadoNaoInscrito  = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorPassadoNaoInscrito.setData(DateUtil.criarDataMesAno(1, 1, 1999));
    	historicoColaboradorPassadoNaoInscrito.setColaborador(colaboradorNaoInscritoCurso);
    	historicoColaboradorPassadoNaoInscrito.setAreaOrganizacional(areaOrganizacional1);
    	historicoColaboradorDao.save(historicoColaboradorPassadoNaoInscrito);

    	HistoricoColaborador historicoColaboradorAtualNaoInscrito = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorAtualNaoInscrito.setData(new Date());
    	historicoColaboradorAtualNaoInscrito.setColaborador(colaboradorNaoInscritoCurso);
    	historicoColaboradorAtualNaoInscrito.setAreaOrganizacional(areaOrganizacional2);
    	historicoColaboradorAtualNaoInscrito.setEstabelecimento(estabelecimento);
    	historicoColaboradorDao.save(historicoColaboradorAtualNaoInscrito);

    	HistoricoColaborador historicoColaboradorFuturoNaoInscrito = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorFuturoNaoInscrito.setData(DateUtil.criarDataMesAno(1, 11, 2056));
    	historicoColaboradorFuturoNaoInscrito.setColaborador(colaboradorNaoInscritoCurso);
    	historicoColaboradorFuturoNaoInscrito.setAreaOrganizacional(areaOrganizacional2);
    	historicoColaboradorDao.save(historicoColaboradorFuturoNaoInscrito);

    	Curso curso = CursoFactory.getEntity();
    	cursoDao.save(curso);

    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setColaborador(colaboradorInscritoCurso);
    	colaboradorTurma.setCurso(curso);
    	colaboradorTurmaDao.save(colaboradorTurma);

    	Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
    	Long[] areaIds = new Long[]{areaOrganizacional2.getId()};

    	Collection<ColaboradorTurma> colaboradoresSemTreinamento = colaboradorTurmaDao.findRelatorioSemTreinamento(empresa.getId(), curso, areaIds, estabelecimentoIds);
    	assertEquals(1, colaboradoresSemTreinamento.size());

    	ColaboradorTurma colaboradorSemTreinamento = (ColaboradorTurma) colaboradoresSemTreinamento.toArray()[0];
    	assertEquals(colaboradorNaoInscritoCurso, colaboradorSemTreinamento.getColaborador());
    	assertEquals(areaOrganizacional2, colaboradorSemTreinamento.getColaborador().getAreaOrganizacional());
    }

    public void testFindRelatorioHistoricoTreinamentos()
    {
    	Date hoje = new Date();
    	Calendar dataUmMesAntes = Calendar.getInstance();
    	dataUmMesAntes.setTime(hoje);
    	dataUmMesAntes.add(Calendar.MONTH, -1);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setNome("João Mamão");
    	colaborador.setEmpresa(empresa);
    	colaboradorDao.save(colaborador);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);

    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional.setNome("Area 1");
    	areaOrganizacionalDao.save(areaOrganizacional);

    	GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

    	HistoricoColaborador historicoColaboradorAtualDoInscrito = HistoricoColaboradorFactory.getEntity();
    	historicoColaboradorAtualDoInscrito.setColaborador(colaborador);
    	historicoColaboradorAtualDoInscrito.setData(hoje);
    	historicoColaboradorAtualDoInscrito.setAreaOrganizacional(areaOrganizacional);
    	historicoColaboradorDao.save(historicoColaboradorAtualDoInscrito);

		Curso cursoConcluido = CursoFactory.getEntity();
		cursoConcluido.setNome("Curso Teste");
    	cursoDao.save(cursoConcluido);
    	Turma turmaCursoConcluido = TurmaFactory.getEntity();
    	turmaCursoConcluido.setDescricao("Turma A");
    	turmaCursoConcluido.setDataPrevFim(dataUmMesAntes.getTime());
    	turmaCursoConcluido.setRealizada(true);
    	turmaDao.save(turmaCursoConcluido);

    	Curso cursoNaoConcluido = CursoFactory.getEntity();
    	cursoNaoConcluido.setNome("Curso Nao Concluido");
    	cursoDao.save(cursoNaoConcluido);
    	Turma turmaCursoNaoConcluido = TurmaFactory.getEntity();
    	turmaCursoNaoConcluido.setDescricao("Turma I");
    	turmaCursoNaoConcluido.setDataPrevFim(hoje);
    	turmaCursoNaoConcluido.setRealizada(false);
    	turmaDao.save(turmaCursoNaoConcluido);

    	ColaboradorTurma colaboradorTurmaNaoConcluido = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurmaNaoConcluido.setColaborador(colaborador);
    	colaboradorTurmaNaoConcluido.setCurso(cursoNaoConcluido);
    	colaboradorTurmaNaoConcluido.setTurma(turmaCursoNaoConcluido);
    	colaboradorTurmaDao.save(colaboradorTurmaNaoConcluido);

    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurma.setCurso(cursoConcluido);
    	colaboradorTurma.setTurma(turmaCursoConcluido);
    	colaboradorTurmaDao.save(colaboradorTurma);

    	Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaDao.findHistoricoTreinamentosByColaborador(empresa.getId(), colaborador.getId(), null, null);
    	assertEquals(1, colaboradorTurmas.size());
    }

	public void testFindColaboradoresSemAvaliacao()
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

		Turma turmaComAvaliacao1 = TurmaFactory.getEntity();
		turmaComAvaliacao1.setCurso(cursoComAvaliacao);
		turmaComAvaliacao1.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaComAvaliacao1.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaComAvaliacao1);

		ColaboradorTurma colaboradorTurmaComAvaliacao = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaComAvaliacao.setCurso(cursoComAvaliacao);
		colaboradorTurmaComAvaliacao.setTurma(turmaComAvaliacao1);
		colaboradorTurmaDao.save(colaboradorTurmaComAvaliacao);

		Curso cursoSemAvaliacao = CursoFactory.getEntity();
		cursoSemAvaliacao.setEmpresa(empresa);
		cursoDao.save(cursoSemAvaliacao);

		Turma turmaForaDoPeriodo = TurmaFactory.getEntity();
		turmaForaDoPeriodo.setCurso(cursoComAvaliacao);
		turmaForaDoPeriodo.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaForaDoPeriodo.setDataPrevFim(hoje);
		turmaDao.save(turmaForaDoPeriodo);

		Turma turmaSemAvaliacao = TurmaFactory.getEntity();
		turmaSemAvaliacao.setCurso(cursoSemAvaliacao);
		turmaSemAvaliacao.setDataPrevIni(dataTresMesesAtras.getTime());
		turmaSemAvaliacao.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turmaSemAvaliacao);

		ColaboradorTurma colaboradorTurmaSemAvaliacao = ColaboradorTurmaFactory.getEntity();
		colaboradorTurmaSemAvaliacao.setCurso(cursoSemAvaliacao);
		colaboradorTurmaSemAvaliacao.setTurma(turmaSemAvaliacao);
		colaboradorTurmaDao.save(colaboradorTurmaSemAvaliacao);

		assertEquals(1, colaboradorTurmaDao.findColaboradoresSemAvaliacao(empresa.getId(), dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime()).size());
	}
	
//	public void testFindColaboradoresCertificacoes()
//	{
//		assertNotNull(colaboradorTurmaDao.findAprovadosReprovados(1L, null, null, new Long[0], new Long[0], " e.nome, a.nome, co.nome, c.nome ", null));
//	}

	public void testFindAllSelectQuantidade()		
	{
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);
    	
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);		
		
		Turma turma = TurmaFactory.getEntity();
		turma.setEmpresa(empresa);
		turma.setDataPrevIni(dataTresMesesAtras.getTime());
		turma.setDataPrevFim(dataDoisMesesAtras.getTime());
		turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma1.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma1);

		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma2.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma2);
		
		assertEquals(new Integer(2), colaboradorTurmaDao.findQuantidade(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa.getId()));
		assertEquals(new Integer(0), colaboradorTurmaDao.findQuantidade(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(),1021L));
	}

    public GenericDao<ColaboradorTurma> getGenericDao()
    {
        return colaboradorTurmaDao;
    }

    public void setColaboradorTurmaDao(ColaboradorTurmaDao ColaboradorTurmaDao)
    {
        this.colaboradorTurmaDao = ColaboradorTurmaDao;
    }

    public void setCursoDao(CursoDao cursoDao)
    {
        this.cursoDao = cursoDao;
    }

    public void setTurmaDao(TurmaDao turmaDao)
    {
        this.turmaDao = turmaDao;
    }

    public void setColaboradorDao(ColaboradorDao colaboradorDao)
    {
        this.colaboradorDao = colaboradorDao;
    }


    public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
    {
        this.areaOrganizacionalDao = areaOrganizacionalDao;
    }

    public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
    {
        this.historicoColaboradorDao = historicoColaboradorDao;
    }

    public void setPrioridadeTreinamentoDao(PrioridadeTreinamentoDao prioridadeTreinamentoDao)
    {
        this.prioridadeTreinamentoDao = prioridadeTreinamentoDao;
    }

    public void setDNTDao(DNTDao dao)
    {
        DNTDao = dao;
    }

    public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
    {
        this.estabelecimentoDao = estabelecimentoDao;
    }

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setDiaTurmaDao(DiaTurmaDao diaTurmaDao)
	{
		this.diaTurmaDao = diaTurmaDao;
	}

	public void setColaboradorPresencaDao(ColaboradorPresencaDao colaboradorPresencaDao)
	{
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
	{
		this.grupoOcupacionalDao = grupoOcupacionalDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setAvaliacaoCursoDao(AvaliacaoCursoDao avaliacaoCursoDao)
	{
		this.avaliacaoCursoDao = avaliacaoCursoDao;
	}

	public void setCertificacaoDao(CertificacaoDao certificacaoDao) {
		this.certificacaoDao = certificacaoDao;
	}

	public void setAproveitamentoAvaliacaoCursoDao(AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao) {
		this.aproveitamentoAvaliacaoCursoDao = aproveitamentoAvaliacaoCursoDao;
	}

}