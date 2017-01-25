package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoColaboradorDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<HistoricoColaborador>
{
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	
	Empresa empresa;
	Colaborador colaborador;
	HistoricoColaborador historicoColaborador;

	public HistoricoColaborador getEntity() {
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setId(null);
		historicoColaborador.setData(new Date());
		historicoColaborador.setMotivo("p");
		historicoColaborador.setSalario(1D);
		historicoColaborador.setColaborador(null);

		return historicoColaborador;
	}

	public GenericDao<HistoricoColaborador> getGenericDao() {
		return historicoColaboradorDao;
	}
	
	@Test
	public void testSetMotivo() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historico.setColaborador(colaborador);
		historicoColaboradorDao.save(historico);

		historicoColaboradorDao.setMotivo(new Long[]{historico.getId()}, MotivoHistoricoColaborador.DISSIDIO);	
		
		assertEquals(MotivoHistoricoColaborador.DISSIDIO ,historicoColaboradorDao.findByIdProjectionHistorico(historico.getId()).getMotivo());
	}
	
	@Test
	public void testSetaContratadoNoPrimeiroHistorico() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador primeiro = HistoricoColaboradorFactory.getEntity();
		primeiro.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		primeiro.setColaborador(colaborador);
		primeiro.setData(DateUtil.criarDataMesAno(01, 02, 1999));
		historicoColaboradorDao.save(primeiro);
		
		HistoricoColaborador segundo = HistoricoColaboradorFactory.getEntity();
		segundo.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		segundo.setColaborador(colaborador);
		segundo.setData(DateUtil.criarDataMesAno(02, 05, 2001));
		historicoColaboradorDao.save(segundo);
		
		HistoricoColaborador terceiro = HistoricoColaboradorFactory.getEntity();
		terceiro.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
		terceiro.setColaborador(colaborador);
		terceiro.setData(DateUtil.criarDataMesAno(02, 05, 2005));
		historicoColaboradorDao.save(terceiro);
		
		historicoColaboradorDao.setaContratadoNoPrimeiroHistorico(colaborador.getId());	
		
		assertEquals(MotivoHistoricoColaborador.CONTRATADO ,historicoColaboradorDao.findByIdProjectionHistorico(primeiro.getId()).getMotivo());
		assertEquals(MotivoHistoricoColaborador.PROMOCAO ,historicoColaboradorDao.findByIdProjectionHistorico(segundo.getId()).getMotivo());
		assertEquals(MotivoHistoricoColaborador.DISSIDIO ,historicoColaboradorDao.findByIdProjectionHistorico(terceiro.getId()).getMotivo());
	}
	
	@Test
	public void testAjustaMotivoContratado() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoContratado = HistoricoColaboradorFactory.getEntity();
		historicoContratado.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		historicoContratado.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoContratado);
		
		HistoricoColaborador historicoDissidio = HistoricoColaboradorFactory.getEntity();
		historicoDissidio.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
		historicoDissidio.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoDissidio);
		
		historicoColaboradorDao.ajustaMotivoContratado(colaborador.getId());	
		
		assertEquals(MotivoHistoricoColaborador.PROMOCAO ,historicoColaboradorDao.findByIdProjectionHistorico(historicoContratado.getId()).getMotivo());
		assertEquals(MotivoHistoricoColaborador.DISSIDIO ,historicoColaboradorDao.findByIdProjectionHistorico(historicoDissidio.getId()).getMotivo());
	}
	
	@Test
	public void testGetPrimeiroHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador primeiroHistorico = saveHistoricoColaborador(colaborador, DateUtil.criarDataMesAno(01, 01, 2000));
		saveHistoricoColaborador(colaborador, DateUtil.criarDataMesAno(02, 02, 2009));

		assertEquals(primeiroHistorico, historicoColaboradorDao.getPrimeiroHistorico(colaborador.getId()));
	}

	@Test
	public void testFindByIdProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico1 = saveHistoricoColaborador(colaborador, DateUtil.incrementaDias(new Date(), -5));
		saveHistoricoColaborador(colaborador, new Date());
		assertEquals(historico1, historicoColaboradorDao.findByIdProjection(historico1.getId()));
	}

	@Test
	public void testFindByIdProjectionHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(historico, historicoColaboradorDao.findByIdProjectionHistorico(historico.getId()));
	}
	
	private HistoricoColaborador saveHistoricoColaborador(Colaborador colaborador, Date data) {
		HistoricoColaborador historico1 = HistoricoColaboradorFactory.getEntity();
		historico1.setColaborador(colaborador);
		historico1.setData(data);
		historico1 = historicoColaboradorDao.save(historico1);
		return historico1;
	}
}