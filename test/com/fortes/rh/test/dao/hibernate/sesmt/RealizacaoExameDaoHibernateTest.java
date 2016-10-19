package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.ExameSolicitacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.RealizacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.DateUtil;

public class RealizacaoExameDaoHibernateTest extends GenericDaoHibernateTest<RealizacaoExame>
{
	RealizacaoExameDao realizacaoExameDao;
	EmpresaDao empresaDao;
	ExameDao exameDao;
	SolicitacaoExameDao solicitacaoExameDao;
	ExameSolicitacaoExameDao exameSolicitacaoExameDao;
	EstabelecimentoDao estabelecimentoDao;
	HistoricoColaboradorDao historicoColaboradorDao;
	ColaboradorDao colaboradorDao;
	AmbienteDao ambienteDao;
	FuncaoDao funcaoDao;

	@Override
	public RealizacaoExame getEntity()
	{

		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setData(new Date());
		realizacaoExame.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExame.setObservacao("Observação");

		return realizacaoExame;
	}

	@Override
	public GenericDao<RealizacaoExame> getGenericDao()
	{
		return realizacaoExameDao;
	}

	public void setRealizacaoExameDao(RealizacaoExameDao realizacaoExameDao)
	{
		this.realizacaoExameDao = realizacaoExameDao;
	}

	public void testFindIdsBySolicitacaoExame()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExameDao.save(solicitacaoExame);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExameDao.save(solicitacaoExame2);

		RealizacaoExame realizacaoExame = RealizacaoExameFactory.getEntity();
		realizacaoExameDao.save(realizacaoExame);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExameDao.save(realizacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		assertEquals(1, realizacaoExameDao.findIdsBySolicitacaoExame(solicitacaoExame.getId()).size());
	}
	
	public void testFindRealizadosByColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);

		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setColaborador(colaborador);
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setColaborador(colaborador);
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setResultado(ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame2);
		
		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		assertEquals(1, realizacaoExameDao.findRealizadosByColaborador(empresa.getId(), colaborador.getId()).size());
	}

	public void testRemove()
	{
		RealizacaoExame realizacaoExame = RealizacaoExameFactory.getEntity();
		realizacaoExameDao.save(realizacaoExame);

		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExameDao.save(realizacaoExame2);

		Long[] realizacaoExameIds = new Long[]{realizacaoExame.getId(),realizacaoExame2.getId()};

		realizacaoExameDao.remove(realizacaoExameIds);

		assertNull(realizacaoExameDao.findById(realizacaoExame.getId(), null));
	}
	
	public void testMarcarComoNormal()
	{
		RealizacaoExame realizacaoExame = RealizacaoExameFactory.getEntity();
		realizacaoExameDao.save(realizacaoExame);
		
		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExameDao.save(realizacaoExame2);
		
		Collection<Long> realizacaoExameIds = new ArrayList<Long>();
		realizacaoExameIds.add(realizacaoExame.getId());
		realizacaoExameIds.add(realizacaoExame2.getId());
		
		realizacaoExameDao.marcarResultadoComoNormal(realizacaoExameIds);
	}

	public void testGetRelatorioExame()
	{
		Date hoje = new Date();
		Date inicio = DateUtil.incrementaAno(hoje, -1);
		Date seisMesesAtras = DateUtil.incrementaMes(hoje, -6);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Estabelecimento estabelecimento3 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento3);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador, inicio, null, estabelecimento1, null, null, null,StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(colaborador, DateUtil.incrementaDias(hoje, -10), null, estabelecimento2, null, null, null,StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity(colaborador, DateUtil.incrementaDias(hoje, 1), null, estabelecimento3, null, null, null,StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Exame exame = ExameFactory.getEntity();
		exameDao.save(exame);
		
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);
		
		Exame exame3 = ExameFactory.getEntity();
		exameDao.save(exame3);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(null, seisMesesAtras, null, colaborador, null, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame);

		SolicitacaoExame solicitacaoExameForaOutroEstabelecimento = SolicitacaoExameFactory.getEntity(null, seisMesesAtras, null, colaborador, null, MotivoSolicitacaoExame.ADMISSIONAL);
		solicitacaoExameDao.save(solicitacaoExameForaOutroEstabelecimento);
		
		SolicitacaoExame solicitacaoExameForaNaoRealizado = SolicitacaoExameFactory.getEntity(null, seisMesesAtras, null, colaborador, null, MotivoSolicitacaoExame.DEMISSIONAL);
		solicitacaoExameDao.save(solicitacaoExameForaNaoRealizado);

		RealizacaoExame realizacaoExame = RealizacaoExameFactory.getEntity(seisMesesAtras, ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame);

		RealizacaoExame realizacaoExameForaNaoRealizado = RealizacaoExameFactory.getEntity(seisMesesAtras, ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExameDao.save(realizacaoExameForaNaoRealizado);

		RealizacaoExame realizacaoExameForaOutroEstabelecimento = RealizacaoExameFactory.getEntity(DateUtil.incrementaDias(hoje, -10), ResultadoExame.NORMAL.toString());
		realizacaoExameDao.save(realizacaoExameForaOutroEstabelecimento);
		
		ExameSolicitacaoExame exameSolicitacaoExame = ExameSolicitacaoExameFactory.getEntity(exame, solicitacaoExame, realizacaoExame, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame2 = ExameSolicitacaoExameFactory.getEntity(exame2, solicitacaoExameForaNaoRealizado, realizacaoExameForaNaoRealizado, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		ExameSolicitacaoExame exameSolicitacaoExame3 = ExameSolicitacaoExameFactory.getEntity(exame3, solicitacaoExameForaOutroEstabelecimento, realizacaoExameForaOutroEstabelecimento, 0);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame3);
		
		Collection<Object[]> resultado = realizacaoExameDao.getRelatorioExame(estabelecimento1.getId(), inicio, hoje);
		
		assertEquals(2, resultado.size());
		assertEquals(MotivoSolicitacaoExame.ADMISSIONAL, ((Object[]) resultado.toArray()[0])[1].toString());
	}
	
	public void testFindQtdRealizados()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);

		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setColaborador(colaborador);
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setColaborador(colaborador);
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setResultado(ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExame1.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExame2.setData(DateUtil.criarDataMesAno(2, 1, 2011));
		realizacaoExameDao.save(realizacaoExame2);

		RealizacaoExame realizacaoExame3 = RealizacaoExameFactory.getEntity();
		realizacaoExame3.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExame3.setData(DateUtil.criarDataMesAno(3, 1, 2011));
		realizacaoExameDao.save(realizacaoExame3);
		
		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame3 = new ExameSolicitacaoExame();
		exameSolicitacaoExame3.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExame3.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame3.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame3);
		
		assertEquals(new Integer(2), realizacaoExameDao.findQtdRealizados(empresa.getId(), DateUtil.criarDataMesAno(1, 1, 2011), DateUtil.criarDataMesAno(30, 1, 2011)));
	}
	
	public void testFindQtdPorExame()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Exame exame1 = ExameFactory.getEntity();
		exame1.setNome("Exame 1");
		exame1.setEmpresa(empresa);
		exameDao.save(exame1);

		Exame exame2 = ExameFactory.getEntity();
		exame2.setNome("Exame 2");
		exame2.setEmpresa(empresa);
		exameDao.save(exame2);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setColaborador(colaborador);
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		RealizacaoExame realizacaoExame1 = RealizacaoExameFactory.getEntity();
		realizacaoExame1.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExame1.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		realizacaoExameDao.save(realizacaoExame1);
		
		RealizacaoExame realizacaoExame2 = RealizacaoExameFactory.getEntity();
		realizacaoExame2.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExame2.setData(DateUtil.criarDataMesAno(2, 1, 2011));
		realizacaoExameDao.save(realizacaoExame2);

		RealizacaoExame realizacaoExame3 = RealizacaoExameFactory.getEntity();
		realizacaoExame3.setResultado(ResultadoExame.NORMAL.toString());
		realizacaoExame3.setData(DateUtil.criarDataMesAno(3, 1, 2011));
		realizacaoExameDao.save(realizacaoExame3);

		RealizacaoExame realizacaoExame4 = RealizacaoExameFactory.getEntity();
		realizacaoExame4.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExame4.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		realizacaoExameDao.save(realizacaoExame4);
		
		RealizacaoExame realizacaoExame5 = RealizacaoExameFactory.getEntity();
		realizacaoExame5.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExame5.setData(DateUtil.criarDataMesAno(2, 1, 2011));
		realizacaoExameDao.save(realizacaoExame5);
		
		RealizacaoExame realizacaoExame6 = RealizacaoExameFactory.getEntity();
		realizacaoExame6.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExame6.setData(DateUtil.criarDataMesAno(3, 1, 2011));
		realizacaoExameDao.save(realizacaoExame6);
		
		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame1);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setExame(exame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame2);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

		ExameSolicitacaoExame exameSolicitacaoExame3 = new ExameSolicitacaoExame();
		exameSolicitacaoExame3.setRealizacaoExame(realizacaoExame3);
		exameSolicitacaoExame3.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame3.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame3);
		
		ExameSolicitacaoExame exameSolicitacaoExame4 = new ExameSolicitacaoExame();
		exameSolicitacaoExame4.setRealizacaoExame(realizacaoExame4);
		exameSolicitacaoExame4.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame4.setExame(exame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame4);
		
		ExameSolicitacaoExame exameSolicitacaoExame5 = new ExameSolicitacaoExame();
		exameSolicitacaoExame5.setRealizacaoExame(realizacaoExame5);
		exameSolicitacaoExame5.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame5.setExame(exame1);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame5);

		ExameSolicitacaoExame exameSolicitacaoExame6 = new ExameSolicitacaoExame();
		exameSolicitacaoExame6.setRealizacaoExame(realizacaoExame6);
		exameSolicitacaoExame6.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame6.setExame(exame2);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame6);
		
		realizacaoExameDao.findRealizadosByColaborador(empresa.getId(), colaborador.getId()); // só para realizar flush (Não usa session do Hibernate)
		Collection<Exame> exames = realizacaoExameDao.findQtdPorExame(empresa.getId(), DateUtil.criarDataMesAno(1, 1, 2011), DateUtil.criarDataMesAno(30, 1, 2011));
		
		assertEquals("Exame 2", ((Exame)exames.toArray()[0]).getNome());
		assertEquals(2, ((Exame)exames.toArray()[0]).getQtdNormal());
		assertEquals(1, ((Exame)exames.toArray()[0]).getQtdAnormal());
		
		assertEquals("Exame 1", ((Exame)exames.toArray()[1]).getNome());
		assertEquals(1, ((Exame)exames.toArray()[1]).getQtdNormal());
		assertEquals(2, ((Exame)exames.toArray()[1]).getQtdAnormal());
	}

	public void setSolicitacaoExameDao(SolicitacaoExameDao solicitacaoExameDao)
	{
		this.solicitacaoExameDao = solicitacaoExameDao;
	}

	public void setExameSolicitacaoExameDao(ExameSolicitacaoExameDao exameSolicitacaoExameDao)
	{
		this.exameSolicitacaoExameDao = exameSolicitacaoExameDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setExameDao(ExameDao exameDao) {
		this.exameDao = exameDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}
}