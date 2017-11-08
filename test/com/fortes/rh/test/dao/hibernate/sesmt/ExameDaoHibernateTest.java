package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ClinicaAutorizadaFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.ExameSolicitacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.RealizacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.DateUtil;

public class ExameDaoHibernateTest extends GenericDaoHibernateTest<Exame>
{
	ExameDao exameDao = null;
	EmpresaDao empresaDao;
	HistoricoFuncaoDao historicoFuncaoDao;
	SolicitacaoExameDao solicitacaoExameDao;
	ExameSolicitacaoExameDao exameSolicitacaoExameDao;
	RealizacaoExameDao realizacaoExameDao;
	ClinicaAutorizadaDao clinicaAutorizadaDao;
	
	ColaboradorDao colaboradorDao;
	AreaOrganizacionalDao areaOrganizacionalDao;
	HistoricoColaboradorDao historicoColaboradorDao;
	EstabelecimentoDao estabelecimentoDao;
	FuncaoDao funcaoDao;
	CandidatoDao candidatoDao;
	CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	SolicitacaoDao solicitacaoDao;

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	@Override
	public Exame getEntity()
	{
		return new Exame();
	}

	@Override
	public GenericDao<Exame> getGenericDao()
	{
		return exameDao;
	}

	public void setExameDao(ExameDao exameDao)
	{
		this.exameDao = exameDao;
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Exame exame = new Exame();
		exame.setEmpresa(empresa);
		exame = exameDao.save(exame);

		Exame exameRetorno = exameDao.findByIdProjection(exame.getId());

		assertEquals(exame.getId(), exameRetorno.getId());
	}

	public void testFindByIdHistoricoFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Exame exame1 = new Exame();
		exame1.setEmpresa(empresa);
		exame1 = exameDao.save(exame1);

		Exame exame2 = new Exame();
		exame2.setEmpresa(empresa);
		exame2 = exameDao.save(exame2);

		Collection<Exame> exames = new ArrayList<Exame>();
		exames.add(exame1);
		exames.add(exame2);

		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setExames(exames);
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);

		Collection<Exame> examesRetorno = exameDao.findByHistoricoFuncao(historicoFuncao.getId());

		assertEquals(2, examesRetorno.size());
	}

	public void testFindBySolicitacaoExame()
	{
		Exame exame1 = new Exame();
		exame1 = exameDao.save(exame1);

		Exame exame2 = new Exame();
		exame1 = exameDao.save(exame2);

		Exame exame3 = new Exame();
		exame1 = exameDao.save(exame3);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExameDao.save(solicitacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		assertEquals(2, exameDao.findBySolicitacaoExame(solicitacaoExame.getId()).size());
	}

	public void testFindExamesPeriodicosPrevistos()
	{
		Date hoje = new Date();
		Date dataTresMesesAtras = DateUtil.incrementaMes(hoje, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity(null, "Colaborador 1");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity(null, "Colaborador 2");
		colaboradorDao.save(colaborador2);

		Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity("Estabelecimento 2");
		estabelecimentoDao.save(estabelecimento2);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		HistoricoColaborador historicoColaborador1Passado = HistoricoColaboradorFactory.getEntity(colaborador1, dataTresMesesAtras, estabelecimentoFora, areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador1Passado);

		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity(colaborador1, hoje, estabelecimento1, areaOrganizacional1);
		historicoColaboradorDao.save(historicoColaborador1Atual);

		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity(colaborador2, hoje, estabelecimento2, areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador2Atual);

		Exame examePeriodico1 = ExameFactory.getEntity(true, 6);
		exameDao.save(examePeriodico1);
		
		Exame examePeriodico2 = ExameFactory.getEntity(true, 12);
		exameDao.save(examePeriodico2);

		Exame exameFora = ExameFactory.getEntity(false, null);
		exameDao.save(exameFora);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity(empresa, colaborador1, null, DateUtil.incrementaMes(new Date(), -6));
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity(DateUtil.incrementaMes(new Date(), -6), ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);

		ExameSolicitacaoExame exameSolicitacaoExame1 = ExameSolicitacaoExameFactory.getEntity(examePeriodico1, solicitacaoExame1, realizacaoExame1, examePeriodico1.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity(empresa, colaborador2, null, DateUtil.incrementaMes(new Date(), -12));
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity(DateUtil.incrementaMes(new Date(), -12), ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = ExameSolicitacaoExameFactory.getEntity(examePeriodico2, solicitacaoExame2, realizacaoExame2, examePeriodico2.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		Long[] areaIds = {areaOrganizacional1.getId(), areaOrganizacional2.getId()};
		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimento2.getId()};

		Collection<ExamesPrevistosRelatorio> examesPrevistos = exameDao.findExamesPeriodicosPrevistos(empresa.getId(), dataTresMesesAtras, hoje, null, estabelecimentoIds, areaIds, null, false, false, false);

		assertEquals(2, examesPrevistos.size());
	}
	
	public void testFindExamesPeriodicosPrevistosNaoRealizados()
	{
		Date hoje = new Date();
		Date dataTresMesesAtras = DateUtil.incrementaMes(hoje, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity(null, "Colaborador 1");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity(null, "Colaborador 2");
		colaboradorDao.save(colaborador2);

		Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity("Estabelecimento 2");
		estabelecimentoDao.save(estabelecimento2);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		HistoricoColaborador historicoColaborador1Passado = HistoricoColaboradorFactory.getEntity(colaborador1, dataTresMesesAtras, estabelecimentoFora, areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador1Passado);

		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity(colaborador1, hoje, estabelecimento1, areaOrganizacional1);
		historicoColaboradorDao.save(historicoColaborador1Atual);

		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity(colaborador2, hoje, estabelecimento2, areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador2Atual);

		Exame examePeriodico1 = ExameFactory.getEntity(true, 6);
		exameDao.save(examePeriodico1);
		
		Exame examePeriodico2 = ExameFactory.getEntity(true, 12);
		exameDao.save(examePeriodico2);

		Exame exameFora = ExameFactory.getEntity(false, null);
		exameDao.save(exameFora);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity(empresa, colaborador1, null, DateUtil.incrementaMes(new Date(), -2));
		solicitacaoExameDao.save(solicitacaoExame1);

		ExameSolicitacaoExame exameSolicitacaoExame1 = ExameSolicitacaoExameFactory.getEntity(examePeriodico1, solicitacaoExame1, null, examePeriodico1.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity(empresa, colaborador2, null, DateUtil.incrementaMes(new Date(), -1));
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity(DateUtil.incrementaMes(new Date(), -12), ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = ExameSolicitacaoExameFactory.getEntity(examePeriodico2, solicitacaoExame2, realizacaoExame2, examePeriodico2.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		SolicitacaoExame solicitacaoExameFora = SolicitacaoExameFactory.getEntity(empresa, colaborador2, null, DateUtil.incrementaMes(new Date(), -12));
		solicitacaoExameDao.save(solicitacaoExameFora);

		ExameSolicitacaoExame exameSolicitacaoExameFora = ExameSolicitacaoExameFactory.getEntity(examePeriodico2, solicitacaoExameFora, null, examePeriodico2.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExameFora);

		Long[] areaIds = {areaOrganizacional1.getId(), areaOrganizacional2.getId()};
		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimento2.getId()};

		Collection<ExamesPrevistosRelatorio> examesPrevistos = exameDao.findExamesPeriodicosPrevistosNaoRealizados(empresa.getId(), dataTresMesesAtras, hoje, null, estabelecimentoIds, areaIds, null, false, false);

		assertEquals(2, examesPrevistos.size());
	}
	
	private Colaborador criaColaboradorComHistorico(Empresa empresa, String nomeColaborador, Date datahistorico, Estabelecimento estabelecimento, Integer status){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nomeColaborador);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, datahistorico, status);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);
		
		return colaborador;
	}
	
	public void testFindExamesRealizadosColaboradoresComExameAdmissionalRealizadoAntesDoPrimeiroHistorico()
	{
		Date hoje = new Date();
		Date dataDoisMesesAtras = DateUtil.incrementaMes(hoje, -2);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity("Estabelecimento 2");
		estabelecimentoDao.save(estabelecimento2);
		
		Colaborador colaborador1 = criaColaboradorComHistorico(empresa, "Colaborador UM Teste", dataDoisMesesAtras, estabelecimento1, StatusRetornoAC.CONFIRMADO);
		Colaborador colaborador2 = criaColaboradorComHistorico(empresa, "Colaborador DOIS Teste", hoje, estabelecimento2, StatusRetornoAC.CONFIRMADO);
		
		Exame exame1 = ExameFactory.getEntity(empresa);
		exameDao.save(exame1);

		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getSolitacaoExameColaborador(hoje, empresa, colaborador1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);
		montaSolicitacaoExame(solicitacaoExame1, dataDoisMesesAtras, ResultadoExame.ANORMAL.toString(), null, exame1);
		montaSolicitacaoExame(solicitacaoExame1, hoje, ResultadoExame.NORMAL.toString(), null, exame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getSolitacaoExameColaborador(hoje, empresa, colaborador2, MotivoSolicitacaoExame.ADMISSIONAL);
		solicitacaoExameDao.save(solicitacaoExame2);
		montaSolicitacaoExame(solicitacaoExame2, DateUtil.incrementaDias(hoje, -1), ResultadoExame.ANORMAL.toString(), null, exame2);

		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimento2.getId()};
		Long[] exameIds = {exame1.getId(), exame2.getId()};
		
		exameDao.getHibernateTemplateByGenericDao().flush();

		assertEquals(2, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),null, dataDoisMesesAtras, hoje, null, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.COLABORADOR.getChave(), false).size());
		assertEquals(1, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),"UM", dataDoisMesesAtras, hoje, null, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.COLABORADOR.getChave(), false).size());
	}
	
	public void testFindExamesRealizadosNaoInformado()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);

    	Colaborador colaborador1 = criaColaboradorComHistorico(empresa, "Colaborador 1", dataDoisMesesAtras.getTime(), estabelecimento, StatusRetornoAC.CONFIRMADO);

    	Exame exame1 = ExameFactory.getEntity(empresa);
    	exameDao.save(exame1);
    	Exame exame2 = ExameFactory.getEntity(empresa);
    	exameDao.save(exame2);

    	ClinicaAutorizada clinicaAutorizada = ClinicaAutorizadaFactory.getEntity("Clinica 1", "I");
    	clinicaAutorizadaDao.save(clinicaAutorizada);
    	
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getSolitacaoExameColaborador(hoje, empresa, colaborador1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);
		montaSolicitacaoExame(solicitacaoExame1, dataDoisMesesAtras.getTime(), ResultadoExame.NORMAL.toString(), clinicaAutorizada, exame1);
		montaSolicitacaoExame(solicitacaoExame1, hoje, ResultadoExame.ANORMAL.toString(), clinicaAutorizada, exame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getSolitacaoExameColaborador(hoje, empresa, colaborador1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExame2.setPeriodicidade(exame2.getPeriodicidade());
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setClinicaAutorizada(clinicaAutorizada);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		exameDao.getHibernateTemplateByGenericDao().flush();
		Collection<ExamesRealizadosRelatorio> examesRealizadosRelatorios = exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(), colaborador1.getNome(), dataDoisMesesAtras.getTime(), hoje, MotivoSolicitacaoExame.PERIODICO, null, clinicaAutorizada.getId(), null, null,TipoPessoa.COLABORADOR.getChave(), false);
		
		assertEquals(2, examesRealizadosRelatorios.size());
	}
	
	public void testFindExamesRealizadosCandidatos()
	{
		Date hoje = new Date();
		Date dataDoisMesesAtras = DateUtil.incrementaMes(hoje, -2);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Candidato candidato1 = CandidatoFactory.getCandidato("Candidato DOIS Teste");
    	candidatoDao.save(candidato1);
    	
    	Candidato candidato2 = CandidatoFactory.getCandidato("Candidato UM Teste");
    	candidatoDao.save(candidato2);
    	
    	Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(empresa, estabelecimento1, hoje);
    	solicitacaoDao.save(solicitacao);
    	
    	CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity(candidato1, solicitacao, DateUtil.criarDataMesAno(1, 2, 2015));
    	candidatoSolicitacaoDao.save(candidatoSolicitacao1);

    	CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity(candidato2, solicitacao, DateUtil.criarDataMesAno(2, 2, 2015));
    	candidatoSolicitacaoDao.save(candidatoSolicitacao2);
    	
		Exame exame1 = ExameFactory.getEntity(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity(empresa);
		exameDao.save(exame2);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity(null, hoje, empresa, null, candidato1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity(dataDoisMesesAtras, ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame1Fora = RealizacaoExameFactory.getEntity(hoje, ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1Fora);

		ExameSolicitacaoExame exameSolicitacaoExame1 = ExameSolicitacaoExameFactory.getEntity(exame1, solicitacaoExame1, realizacaoExame1, exame1.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame1Fora = ExameSolicitacaoExameFactory.getEntity(exame1, solicitacaoExame1, realizacaoExame1Fora, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1Fora);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity(null, hoje, empresa, null, candidato2, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity(hoje,ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = ExameSolicitacaoExameFactory.getEntity(exame2, solicitacaoExame2, realizacaoExame2, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimentoFora.getId()};
		Long[] exameIds = {exame1.getId(), exame2.getId()};
		
		exameDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(2, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),null, dataDoisMesesAtras, hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.CANDIDATO.getChave(), false).size());
		assertEquals(1, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),"UM", dataDoisMesesAtras, hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.CANDIDATO.getChave(), false).size());
	}

	public void testFindExamesRealizadosParaColaboradoresECandidatos()
	{
		// CANDIDATO
		Date hoje = new Date();
		Date dataDoisMesesAtras = DateUtil.incrementaMes(hoje, -2);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Candidato candidato1 = CandidatoFactory.getCandidato("Candidato DOIS Teste");
    	candidatoDao.save(candidato1);
    	
    	Candidato candidato2 = CandidatoFactory.getCandidato("Candidato UM Teste");
    	candidatoDao.save(candidato2);
    	
    	Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(empresa, estabelecimento1, hoje);
    	solicitacaoDao.save(solicitacao);
    	
    	CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity(candidato1, solicitacao, DateUtil.criarDataMesAno(1, 2, 2015));
    	candidatoSolicitacaoDao.save(candidatoSolicitacao1);

    	CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity(candidato2, solicitacao, DateUtil.criarDataMesAno(2, 2, 2015));
    	candidatoSolicitacaoDao.save(candidatoSolicitacao2);
    	
		Exame exame1 = ExameFactory.getEntity(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity(empresa);
		exameDao.save(exame2);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity(null, hoje, empresa, null, candidato1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity(dataDoisMesesAtras, ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame1Fora = RealizacaoExameFactory.getEntity(hoje, ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1Fora);

		ExameSolicitacaoExame exameSolicitacaoExame1 = ExameSolicitacaoExameFactory.getEntity(exame1, solicitacaoExame1, realizacaoExame1, exame1.getPeriodicidade());
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame1Fora = ExameSolicitacaoExameFactory.getEntity(exame1, solicitacaoExame1, realizacaoExame1Fora, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1Fora);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity(null, hoje, empresa, null, candidato2, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity(hoje,ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = ExameSolicitacaoExameFactory.getEntity(exame2, solicitacaoExame2, realizacaoExame2, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		// COLABORADOR

    	Colaborador colaborador1 = criaColaboradorComHistorico(empresa, "Colaborador UM Teste", dataDoisMesesAtras, estabelecimento1, StatusRetornoAC.CONFIRMADO);

		SolicitacaoExame solicitacaoExame1Colab = SolicitacaoExameFactory.getSolitacaoExameColaborador(hoje, empresa, colaborador1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1Colab);
		montaSolicitacaoExame(solicitacaoExame1Colab, dataDoisMesesAtras, ResultadoExame.NORMAL.toString(), null, exame1);
		montaSolicitacaoExame(solicitacaoExame1Colab, hoje, ResultadoExame.ANORMAL.toString(), null, exame1);
		
		SolicitacaoExame solicitacaoExame2Colab = SolicitacaoExameFactory.getSolitacaoExameColaborador(hoje, empresa, colaborador1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2Colab);

		ExameSolicitacaoExame exameSolicitacaoExame2Colab = new ExameSolicitacaoExame();
		exameSolicitacaoExame2Colab.setExame(exame2);
		exameSolicitacaoExame2Colab.setPeriodicidade(exame2.getPeriodicidade());
		exameSolicitacaoExame2Colab.setSolicitacaoExame(solicitacaoExame2Colab);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2Colab);
		
		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimentoFora.getId()};
		Long[] exameIds = {exame1.getId(), exame2.getId()};
		
		exameDao.getHibernateTemplateByGenericDao().flush();

		assertEquals(3, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),null, dataDoisMesesAtras, hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.TODOS.getChave(), false).size());
		assertEquals(2, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),"Candidato", dataDoisMesesAtras, hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.TODOS.getChave(), false).size());
		assertEquals(1, exameDao.findExamesRealizadosCandidatosAndColaboradores(empresa.getId(),"Colaborador", dataDoisMesesAtras, hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds,TipoPessoa.TODOS.getChave(), false).size());
	}

	public void testFindExamesRealizadosRelatorioResumidoComFiltros()
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2009); 
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2011); 
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity(empresa1);
		exameDao.save(exame2);
		
		ClinicaAutorizada clinica1 = new ClinicaAutorizada();
		clinica1.setNome("clinica de virgens");
		clinicaAutorizadaDao.save(clinica1);

		ClinicaAutorizada clinica2 = new ClinicaAutorizada();
		clinica2.setNome("clinica de loucos");
		clinicaAutorizadaDao.save(clinica2);
		
		SolicitacaoExame solicitacaoExameEmpresa1 = SolicitacaoExameFactory.getEntity(dataFim, empresa1, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExameEmpresa1);
		
		SolicitacaoExame solicitacaoExameEmpresa2 = SolicitacaoExameFactory.getEntity(dataFim, empresa2, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExameEmpresa2);
		
		montaSolicitacaoExame(solicitacaoExameEmpresa1, dataFim, ResultadoExame.NORMAL.toString(), clinica1, exame1);
		montaSolicitacaoExame(solicitacaoExameEmpresa2, DateUtil.incrementaDias(dataIni, 1), ResultadoExame.NORMAL.toString(), clinica2, exame1);
		montaSolicitacaoExame(solicitacaoExameEmpresa1, DateUtil.incrementaDias(dataIni, -1), ResultadoExame.ANORMAL.toString(), clinica1, exame2);
		montaSolicitacaoExame(solicitacaoExameEmpresa1, DateUtil.incrementaDias(dataFim, -1), ResultadoExame.NORMAL.toString(), clinica1, exame2);
		
		Long[] examesIds = new Long[]{exame1.getId(),exame2.getId()};
		
		assertEquals(2, exameDao.findExamesRealizadosRelatorioResumido(empresa1.getId(), dataIni, dataFim, clinica1, examesIds, ResultadoExame.NORMAL.toString()).size());
	}
	
	public void testFindExamesRealizadosRelatorioResumidoSemUtilizarFiltros()
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2009); 
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2011); 
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Exame exame1 = ExameFactory.getEntity(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity(empresa);
		exameDao.save(exame2);
		
		ClinicaAutorizada clinicaAutorizadaVirgem = new ClinicaAutorizada();
		clinicaAutorizadaVirgem.setNome("clinica de virgens");
		clinicaAutorizadaDao.save(clinicaAutorizadaVirgem);

		ClinicaAutorizada clinicaAutorizadaLoucos = new ClinicaAutorizada();
		clinicaAutorizadaLoucos.setNome("clinica de loucos");
		clinicaAutorizadaDao.save(clinicaAutorizadaLoucos);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(dataIni, empresa, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame);
		SolicitacaoExame solicitacaoExameFora = SolicitacaoExameFactory.getEntity(DateUtil.criarDataMesAno(01, 01, 2000), empresa, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExameFora);
		
		montaSolicitacaoExame(solicitacaoExame, DateUtil.criarDataMesAno(1, 11, 2010), ResultadoExame.ANORMAL.toString(), clinicaAutorizadaLoucos, exame1);
		montaSolicitacaoExame(solicitacaoExameFora, DateUtil.criarDataMesAno(01, 01, 2000), ResultadoExame.NORMAL.toString(), clinicaAutorizadaVirgem, exame1);
		montaSolicitacaoExame(solicitacaoExame, DateUtil.criarDataMesAno(1, 01, 2010), ResultadoExame.NORMAL.toString(), clinicaAutorizadaLoucos, exame2);
		
		assertEquals(2, exameDao.findExamesRealizadosRelatorioResumido(empresa.getId(), dataIni, dataFim, null, null, null).size());
	}

	private void montaSolicitacaoExame(SolicitacaoExame solicitacaoExame, Date dataRealizacaoExame, String resultadoExame, ClinicaAutorizada clinicaAutorizada, Exame exame){
		RealizacaoExame realizacaoExame = RealizacaoExameFactory.getEntity(dataRealizacaoExame, resultadoExame);
		realizacaoExameDao.save(realizacaoExame);
		
		ExameSolicitacaoExame exameSolicitacaoExame = ExameSolicitacaoExameFactory.getEntity(exame, solicitacaoExame, clinicaAutorizada, realizacaoExame, 12);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);
	}
	
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Exame auditivo = new Exame();
		auditivo.setNome("audítivo");
		auditivo.setEmpresa(empresa);
		exameDao.save(auditivo);

		Exame visual = new Exame();
		visual.setNome("visual");
		visual.setEmpresa(empresa);
		exameDao.save(visual);

		Exame audiovisual = new Exame();
		audiovisual.setNome("audiovisual");
		audiovisual.setEmpresa(empresa);
		exameDao.save(audiovisual);

		Exame exame = ExameFactory.getEntity();
		exame.setNome("audi");
		Integer qtdExames = exameDao.getCount(empresa.getId(), exame);

		assertEquals(new Integer(2), qtdExames);
	}
	
	public void testFindByNome()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Exame auditivo = new Exame();
		auditivo.setNome("audítivo");
		auditivo.setEmpresa(empresa);
		exameDao.save(auditivo);
		
		Exame visual = new Exame();
		visual.setNome("visual");
		visual.setEmpresa(empresa);
		exameDao.save(visual);
		
		Exame audiovisual = new Exame();
		audiovisual.setNome("audiovisual");
		audiovisual.setEmpresa(empresa);
		exameDao.save(audiovisual);
		
		Exame exame = ExameFactory.getEntity();
		exame.setNome("audi");
		Collection<Exame> exames = exameDao.find(0, 10, empresa.getId(), exame);
		
		assertEquals(2, exames.size());
	}
	
	public void testFindPriorizandoExameRelacionado() 
	{
		Date data1 = DateUtil.criarDataMesAno(01, 01, 2011);
		Date data2 = DateUtil.criarDataMesAno(02, 02, 2011);
	
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);

		Funcao funcao2 = FuncaoFactory.getEntity();
		funcaoDao.save(funcao2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFuncao(funcao);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(data1);
		historicoColaboradorDao.save(historicoColaborador);
		
		Exame exame1 = ExameFactory.getEntity();
		exame1.setEmpresa(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity();
		exame2.setEmpresa(empresa);
		exameDao.save(exame2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setExames(Arrays.asList(exame1));
		historicoFuncao.setData(data1);
		historicoFuncaoDao.save(historicoFuncao);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setFuncao(funcao2);
		historicoFuncao2.setExames(Arrays.asList(exame2));
		historicoFuncao2.setData(data2);
		historicoFuncaoDao.save(historicoFuncao2);

		Collection<Exame> colecao = exameDao.findPriorizandoExameRelacionado(empresa.getId(), colaborador.getId());
		assertTrue(colecao.size() >= 2);
	}
	
	public void testFindByEmpresaComAsoPadrao() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Exame exame1 = ExameFactory.getEntity();
		exame1.setEmpresa(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity();
		exame2.setAso(true);
		exameDao.save(exame2);
		
		Collection<Exame> colecao = exameDao.findByEmpresaComAsoPadrao(empresa.getId());
		assertTrue(colecao.size() >= 2);
	}
	
	public void testFindAsoPadrao() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Exame exame1 = ExameFactory.getEntity();
		exame1.setEmpresa(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity();
		exame2.setAso(true);
		exameDao.save(exame2);
		
		Collection<Exame> colecao = exameDao.findAsoPadrao();
		assertEquals(3, colecao.size());
	}
	
	public void setHistoricoFuncaoDao(HistoricoFuncaoDao historicoFuncaoDao)
	{
		this.historicoFuncaoDao = historicoFuncaoDao;
	}

	public void setExameSolicitacaoExameDao(ExameSolicitacaoExameDao exameSolicitacaoExameDao)
	{
		this.exameSolicitacaoExameDao = exameSolicitacaoExameDao;
	}

	public void setSolicitacaoExameDao(SolicitacaoExameDao solicitacaoExameDao)
	{
		this.solicitacaoExameDao = solicitacaoExameDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setRealizacaoExameDao(RealizacaoExameDao realizacaoExameDao)
	{
		this.realizacaoExameDao = realizacaoExameDao;
	}

	public void setClinicaAutorizadaDao(ClinicaAutorizadaDao clinicaAutorizadaDao) {
		this.clinicaAutorizadaDao = clinicaAutorizadaDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setCandidatoSolicitacaoDao(
			CandidatoSolicitacaoDao candidatoSolicitacaoDao) {
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}
}