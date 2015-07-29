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
import com.fortes.rh.test.factory.sesmt.ExameFactory;
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
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("Colaborador 1");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Colaborador 2");
		colaboradorDao.save(colaborador2);

		Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity();
		estabelecimentoFora.setNome("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setNome("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setNome("Estabelecimento 2");
		estabelecimentoDao.save(estabelecimento2);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setNome("Area 1");
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setNome("Area 2");
		areaOrganizacionalDao.save(areaOrganizacional2);

		HistoricoColaborador historicoColaborador1Passado = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1Passado.setColaborador(colaborador1);
		historicoColaborador1Passado.setEstabelecimento(estabelecimentoFora);
		historicoColaborador1Passado.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador1Passado.setData(dataTresMesesAtras.getTime());
		historicoColaboradorDao.save(historicoColaborador1Passado);

		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1Atual.setColaborador(colaborador1);
		historicoColaborador1Atual.setEstabelecimento(estabelecimento1);
		historicoColaborador1Atual.setAreaOrganizacional(areaOrganizacional1);
		historicoColaborador1Atual.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador1Atual);

		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2Atual.setColaborador(colaborador2);
		historicoColaborador2Atual.setEstabelecimento(estabelecimento2);
		historicoColaborador2Atual.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador2Atual.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador2Atual);

		Exame examePeriodico1 = ExameFactory.getEntity();
		examePeriodico1.setPeriodico(true);
		examePeriodico1.setPeriodicidade(6);
		exameDao.save(examePeriodico1);
		Exame examePeriodico2 = ExameFactory.getEntity();
		examePeriodico2.setPeriodico(true);
		examePeriodico2.setPeriodicidade(12);
		exameDao.save(examePeriodico2);
		Exame exameFora = ExameFactory.getEntity();
		exameFora.setPeriodico(false);
		exameDao.save(exameFora);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setColaborador(colaborador1);
		solicitacaoExame1.setData(DateUtil.incrementaMes(new Date(), -6));
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setData(DateUtil.incrementaMes(new Date(), -6));
		realizacaoExame1.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(examePeriodico1);
		exameSolicitacaoExame1.setPeriodicidade(examePeriodico1.getPeriodicidade());
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExame2.setData(DateUtil.incrementaMes(new Date(), -12));
		solicitacaoExame2.setColaborador(colaborador2);
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setData(DateUtil.incrementaMes(new Date(), -12));
		realizacaoExame2.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(examePeriodico2);
		exameSolicitacaoExame2.setPeriodicidade(examePeriodico2.getPeriodicidade());
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		Long[] areaIds = {areaOrganizacional1.getId(), areaOrganizacional2.getId()};
		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimento2.getId()};

		Collection<ExamesPrevistosRelatorio> examesPrevistos = exameDao.findExamesPeriodicosPrevistos(empresa.getId(), dataTresMesesAtras.getTime(), hoje, null, estabelecimentoIds, areaIds, null, false, false);

		assertEquals(2, examesPrevistos.size());
	}
	
	public void testFindExamesRealizadosColaboradores()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("Colaborador 1");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Colaborador 2");
		colaboradorDao.save(colaborador2);

		Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity();
		estabelecimentoFora.setNome("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setNome("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setNome("Estabelecimento 2");
		estabelecimentoDao.save(estabelecimento2);
		
		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1Atual.setColaborador(colaborador1);
		historicoColaborador1Atual.setEstabelecimento(estabelecimento1);
		historicoColaborador1Atual.setData(dataDoisMesesAtras.getTime());
		historicoColaboradorDao.save(historicoColaborador1Atual);

		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2Atual.setColaborador(colaborador2);
		historicoColaborador2Atual.setEstabelecimento(estabelecimento2);
		historicoColaborador2Atual.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador2Atual);
		
		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setColaborador(colaborador1);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame1.setData(hoje);
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setData(dataDoisMesesAtras.getTime());
		realizacaoExame1.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame1Fora = RealizacaoExameFactory.getEntity();
		realizacaoExame1Fora.setData(hoje);
		realizacaoExame1Fora.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1Fora);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame1Fora = new ExameSolicitacaoExame();
		exameSolicitacaoExame1Fora.setExame(exame1);
		exameSolicitacaoExame1Fora.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1Fora.setRealizacaoExame(realizacaoExame1Fora);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1Fora);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExame2.setColaborador(colaborador2);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame2.setData(hoje);
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setData(hoje);
		realizacaoExame2.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExame2.setPeriodicidade(exame2.getPeriodicidade());
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimento2.getId()};
		Long[] exameIds = {exame1.getId(), exame2.getId()};
		
		assertEquals(2, exameDao.findExamesRealizadosColaboradores(empresa.getId(),null, dataDoisMesesAtras.getTime(), hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds).size());
	}
	
	public void testFindExamesRealizadosCandidatos()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Candidato candidato1 = CandidatoFactory.getCandidato();
    	candidatoDao.save(candidato1);
    	
    	Candidato candidato2 = CandidatoFactory.getCandidato();
    	candidatoDao.save(candidato2);
    	
    	Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity();
		estabelecimentoFora.setNome("Est fora da consulta");
		estabelecimentoDao.save(estabelecimentoFora);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setNome("Estabelecimento 1");
		estabelecimentoDao.save(estabelecimento1);

    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
    	solicitacao.setEstabelecimento(estabelecimento1);
    	solicitacao.setData(hoje);
    	solicitacao.setEmpresa(empresa);
    	solicitacaoDao.save(solicitacao);
    	
    	CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
    	candidatoSolicitacao1.setSolicitacao(solicitacao);
    	candidatoSolicitacao1.setCandidato(candidato1);
    	candidatoSolicitacaoDao.save(candidatoSolicitacao1);

    	CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
    	candidatoSolicitacao2.setSolicitacao(solicitacao);
    	candidatoSolicitacao2.setCandidato(candidato2);
    	candidatoSolicitacaoDao.save(candidatoSolicitacao2);
    	
		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setCandidato(candidato1);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame1.setData(hoje);
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setData(dataDoisMesesAtras.getTime());
		realizacaoExame1.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame1Fora = RealizacaoExameFactory.getEntity();
		realizacaoExame1Fora.setData(hoje);
		realizacaoExame1Fora.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1Fora);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExame1.setPeriodicidade(exame1.getPeriodicidade());
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame1Fora = new ExameSolicitacaoExame();
		exameSolicitacaoExame1Fora.setExame(exame1);
		exameSolicitacaoExame1Fora.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1Fora.setRealizacaoExame(realizacaoExame1Fora);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1Fora);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExame2.setCandidato(candidato2);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame2.setData(hoje);
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setData(hoje);
		realizacaoExame2.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		Long[] estabelecimentoIds = {estabelecimento1.getId(), estabelecimentoFora.getId()};
		Long[] exameIds = {exame1.getId(), exame2.getId()};
		
		assertEquals(2, exameDao.findExamesRealizadosCandidatos(empresa.getId(),null, dataDoisMesesAtras.getTime(), hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.ANORMAL.toString(), null, exameIds, estabelecimentoIds).size());
	}

	public void testFindExamesRealizadosRelatorioResumido()
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2009); 
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2011); 
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Exame exame1 = ExameFactory.getEntity();
		exame1.setEmpresa(empresa);
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity();
		exame2.setEmpresa(empresa);
		exameDao.save(exame2);
		
		ClinicaAutorizada clinicaAutorizadaVirgem = new ClinicaAutorizada();
		clinicaAutorizadaVirgem.setNome("clinica de virgens");
		clinicaAutorizadaDao.save(clinicaAutorizadaVirgem);

		ClinicaAutorizada clinicaAutorizadaLoucos = new ClinicaAutorizada();
		clinicaAutorizadaLoucos.setNome("clinica de loucos");
		clinicaAutorizadaDao.save(clinicaAutorizadaLoucos);
		
		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setData(DateUtil.criarDataMesAno(1, 11, 2010));
		realizacaoExame1.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame1Fora = RealizacaoExameFactory.getEntity();
		realizacaoExame1Fora.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		realizacaoExame1Fora.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1Fora);
		
		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExame1.setClinicaAutorizada(clinicaAutorizadaLoucos);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame1Fora = new ExameSolicitacaoExame();
		exameSolicitacaoExame1Fora.setExame(exame1);
		exameSolicitacaoExame1Fora.setRealizacaoExame(realizacaoExame1Fora);
		exameSolicitacaoExame1Fora.setClinicaAutorizada(clinicaAutorizadaVirgem);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1Fora);
		
		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setData(DateUtil.criarDataMesAno(1, 01, 2010));
		realizacaoExame2.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExame2.setPeriodicidade(exame2.getPeriodicidade());
		exameSolicitacaoExame2.setClinicaAutorizada(clinicaAutorizadaLoucos);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		Long[] estabelecimentoIds = new Long[]{exame1.getId(),exame2.getId()};
		
		assertEquals(2, exameDao.findExamesRealizadosRelatorioResumido(empresa.getId(), dataIni, dataFim, clinicaAutorizadaLoucos, estabelecimentoIds).size());
	}

	
	public void testFindExamesRealizadosNaoInformado()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);
    	

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("Colaborador 1");
//		colaborador1.setHistoricoColaboradors(Arrays.asList(historicoColaborador));
		colaboradorDao.save(colaborador1);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setData(hoje);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador1);
		historicoColaboradorDao.save(historicoColaborador);

		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);

		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setColaborador(colaborador1);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame1.setData(hoje);
		solicitacaoExameDao.save(solicitacaoExame1);

		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setData(dataDoisMesesAtras.getTime());
		realizacaoExame1.setResultado(ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame1Fora = RealizacaoExameFactory.getEntity();
		realizacaoExame1Fora.setData(hoje);
		realizacaoExame1Fora.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame1Fora);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame1Fora = new ExameSolicitacaoExame();
		exameSolicitacaoExame1Fora.setExame(exame1);
		exameSolicitacaoExame1Fora.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1Fora.setRealizacaoExame(realizacaoExame1Fora);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1Fora);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExame2.setColaborador(colaborador1);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame2.setData(hoje);
		solicitacaoExameDao.save(solicitacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExame2.setPeriodicidade(exame2.getPeriodicidade());
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		assertEquals(2, exameDao.findExamesRealizadosColaboradores(empresa.getId(), null, dataDoisMesesAtras.getTime(), hoje, MotivoSolicitacaoExame.PERIODICO, ResultadoExame.NAO_REALIZADO.toString(), null, null, null).size());
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