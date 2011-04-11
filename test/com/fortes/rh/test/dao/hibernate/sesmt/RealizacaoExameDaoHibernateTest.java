package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.fortes.rh.test.factory.sesmt.RealizacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;

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

	public void testGetRelatorioAnual()
	{
		Date hoje = new Date();
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.YEAR, -1);
		Calendar meio = Calendar.getInstance();
		meio.add(Calendar.MONTH, -6);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(inicio.getTime());
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);
		
		Exame exame = ExameFactory.getEntity();
		exameDao.save(exame);
		
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setData(meio.getTime());
		solicitacaoExame.setColaborador(colaborador);
		solicitacaoExame.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame);

		SolicitacaoExame solicitacaoExameFora = SolicitacaoExameFactory.getEntity();
		solicitacaoExameFora.setData(meio.getTime());
		solicitacaoExameFora.setColaborador(colaborador);
		solicitacaoExameFora.setMotivo(MotivoSolicitacaoExame.DEMISSIONAL);
		solicitacaoExameDao.save(solicitacaoExameFora);

		RealizacaoExame realizacaoExame = RealizacaoExameFactory.getEntity();
		realizacaoExame.setData(meio.getTime());
		realizacaoExame.setResultado(ResultadoExame.ANORMAL.toString());
		realizacaoExameDao.save(realizacaoExame);

		RealizacaoExame realizacaoExameFora = RealizacaoExameFactory.getEntity();
		realizacaoExameFora.setData(meio.getTime());
		realizacaoExameFora.setResultado(ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExameDao.save(realizacaoExameFora);

		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame2);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExameFora);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExameFora);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		Collection<Object[]> resultado = realizacaoExameDao.getRelatorioAnual(estabelecimento.getId(), inicio.getTime(), hoje);
		
		assertEquals(1, resultado.size());
		assertEquals(MotivoSolicitacaoExame.PERIODICO, ((Object[]) resultado.toArray()[0])[1].toString());
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