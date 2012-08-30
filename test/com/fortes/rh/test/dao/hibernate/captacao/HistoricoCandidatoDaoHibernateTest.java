package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoCandidatoDaoHibernateTest extends GenericDaoHibernateTest<HistoricoCandidato>
{
	private HistoricoCandidatoDao historicoCandidatoDao;
	private EmpresaDao empresaDao;
	private EtapaSeletivaDao etapaSeletivaDao;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private SolicitacaoDao solicitacaoDao;
	private CandidatoDao candidatoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private CargoDao cargoDao;
	private UsuarioDao usuarioDao;

	public HistoricoCandidato getEntity()
	{
		HistoricoCandidato historicoCandidato = HistoricoCandidatoFactory.getEntity();

		return historicoCandidato;
	}

	public GenericDao<HistoricoCandidato> getGenericDao()
	{
		return historicoCandidatoDao;
	}

	public void setHistoricoCandidatoDao(HistoricoCandidatoDao candidatoDao)
	{
		this.historicoCandidatoDao = candidatoDao;
	}

	public void testFindByCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setQuantidade(2);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		HistoricoCandidato historicoCandidato = new HistoricoCandidato();
		
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		Collection<HistoricoCandidato> historicos = historicoCandidatoDao.findByCandidato(candidato);

		assertEquals(1, historicos.size());
	}

	public void testFindByCandidatos()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidatoSolicitacao);

		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva = etapaSeletivaDao.save(etapaSeletiva);

		HistoricoCandidato historicoCandidato = new HistoricoCandidato();
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		Collection<HistoricoCandidato> historicos = historicoCandidatoDao.findByCandidato(candidatoSolicitacaos);

		assertEquals(1, historicos.size());

		//Quando a coleção vazia para não quebrar o "in" da consulta
		historicos = historicoCandidatoDao.findByCandidato(new ArrayList<CandidatoSolicitacao>());

		assertTrue(historicos.isEmpty());
	}

	public void testFindList()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		HistoricoCandidato historicoCandidato = new HistoricoCandidato();
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		Collection<HistoricoCandidato> historicos = historicoCandidatoDao.findList(candidatoSolicitacao);

		assertEquals(1, historicos.size());
	}
	
	public void testFindResponsaveis()
	{
		HistoricoCandidato historicoCandidato1 = new HistoricoCandidato();
		historicoCandidato1.setResponsavel("Francisco");
		historicoCandidatoDao.save(historicoCandidato1);
		
		HistoricoCandidato historicoCandidato2 = new HistoricoCandidato();
		historicoCandidato2.setResponsavel("Francisco");
		historicoCandidatoDao.save(historicoCandidato2);
		
		String[] resps = historicoCandidatoDao.findResponsaveis();
		
		assertTrue(resps.length >= 1);
	}
	
	public void testGetEventos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacaoDao.save(solicitacao);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setNome("joao da silva");
		candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva.setEmpresa(empresa);
		etapaSeletiva = etapaSeletivaDao.save(etapaSeletiva);
		
		HistoricoCandidato historicoCandidato1 = new HistoricoCandidato();
		historicoCandidato1.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato1.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato1.setResponsavel("Francisco");
		historicoCandidatoDao.save(historicoCandidato1);
		
		HistoricoCandidato historicoCandidato2 = new HistoricoCandidato();
		historicoCandidato2.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato2.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato2.setResponsavel("Francis");
		historicoCandidatoDao.save(historicoCandidato2);
		
		assertEquals(2, historicoCandidatoDao.getEventos("Fran", empresa.getId(), null, null).size());
	}

	public void testFindQtdParticipantes()
	{
		Date hoje = DateUtil.criarDataMesAno(01, 02, 2010);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao = solicitacaoDao.save(solicitacao);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva.setEmpresa(empresa);
		etapaSeletiva = etapaSeletivaDao.save(etapaSeletiva);

		HistoricoCandidato historicoCandidato = new HistoricoCandidato();
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato.setData(hoje);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		//fora da data
		HistoricoCandidato historicoCandidato2 = new HistoricoCandidato();
		historicoCandidato2.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato2.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato2.setData(DateUtil.criarAnoMesDia(1900, 1, 1));
		historicoCandidato2 = historicoCandidatoDao.save(historicoCandidato2);

		//sem etapa seletiva
		HistoricoCandidato historicoCandidato3 = new HistoricoCandidato();
		historicoCandidato3.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato3.setEtapaSeletiva(null);
		historicoCandidato3.setData(hoje);
		historicoCandidato3 = historicoCandidatoDao.save(historicoCandidato3);

		Collection<HistoricoCandidato> historicos = historicoCandidatoDao.findQtdParticipantes("2010", cargo.getId(), new Long[]{etapaSeletiva.getId()});

		assertEquals(1, historicos.size());
	}

	public void testFindByPeriodo()
	{
		Date hoje = DateUtil.criarDataMesAno(01, 01, 1980);
		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEncerrada(false);
		solicitacao.setSolicitante(solicitante);
		solicitacao = solicitacaoDao.save(solicitacao);

		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva = etapaSeletivaDao.save(etapaSeletiva);

		HistoricoCandidato historicoCandidato = new HistoricoCandidato();
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato.setData(hoje);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		HistoricoCandidato historicoCandidato2 = new HistoricoCandidato();
		historicoCandidato2.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato2.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato2.setData(DateUtil.criarAnoMesDia(1900, 1, 1));
		historicoCandidato2 = historicoCandidatoDao.save(historicoCandidato2);

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("dataIni", hoje);
		parametros.put("dataFim", hoje);

		Collection<HistoricoCandidato> historicos = historicoCandidatoDao.findByPeriodo(parametros);

		assertEquals(1, historicos.size());
	}
	
	public void testFindQtdAtendidos()
	{
		Date hoje = DateUtil.criarDataMesAno(01, 01, 1980);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(sol1);
		
		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(sol2);
		
		Candidato joao = CandidatoFactory.getCandidato();
		joao.setNome("joao");
		joao.setEmpresa(empresa);
		candidatoDao.save(joao);

		Candidato maria = CandidatoFactory.getCandidato();
		maria.setNome("maria");
		maria.setEmpresa(empresa);
		candidatoDao.save(maria);
		
		CandidatoSolicitacao candSolJoao = new CandidatoSolicitacao();
		candSolJoao.setCandidato(joao);
		candSolJoao.setSolicitacao(sol1);
		candidatoSolicitacaoDao.save(candSolJoao);

		CandidatoSolicitacao candSolMaria = new CandidatoSolicitacao();
		candSolMaria.setCandidato(maria);
		candSolMaria.setSolicitacao(sol2);
		candidatoSolicitacaoDao.save(candSolMaria);

		HistoricoCandidato historicoCandidato = new HistoricoCandidato();
		historicoCandidato.setCandidatoSolicitacao(candSolJoao);
		historicoCandidato.setData(hoje);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		HistoricoCandidato historicoCandidato2 = new HistoricoCandidato();
		historicoCandidato2.setCandidatoSolicitacao(candSolJoao);
		historicoCandidato2.setData(hoje);
		historicoCandidato2 = historicoCandidatoDao.save(historicoCandidato2);

		HistoricoCandidato historicoCandidato3 = new HistoricoCandidato();
		historicoCandidato3.setCandidatoSolicitacao(candSolMaria);
		historicoCandidato3.setData(hoje);
		historicoCandidato3 = historicoCandidatoDao.save(historicoCandidato3);
		
		Long[] solicitacaoIds = new Long[]{ sol1.getId() };

		int qtd1 = historicoCandidatoDao.findQtdAtendidos(empresa.getId(), solicitacaoIds, hoje, hoje);
		int qtd2 = historicoCandidatoDao.findQtdAtendidos(empresa.getId(), null, hoje, hoje);

		assertEquals("Considerando solicitação",1, qtd1);
		assertEquals("Não considerando solicitação", 2, qtd2);
	}

	public void testFindByIdProjection()
	{
		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva = etapaSeletivaDao.save(etapaSeletiva);

		HistoricoCandidato historicoCandidato = HistoricoCandidatoFactory.getEntity();
		historicoCandidato.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		HistoricoCandidato historicoCandidatoRetorno = historicoCandidatoDao.findByIdProjection(historicoCandidato.getId());

		assertEquals(historicoCandidato.getId(), historicoCandidatoRetorno.getId());
	}

	public void testUpdateAgenda()
	{
		Date hoje = new Date(); 
		
		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva = etapaSeletivaDao.save(etapaSeletiva);

		HistoricoCandidato historicoCandidato = HistoricoCandidatoFactory.getEntity();
		historicoCandidato.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato.setObservacao("");
		historicoCandidato.setData(null);
		historicoCandidato.setHoraIni("00:00");
		historicoCandidato.setHoraFim("00:00");
		historicoCandidatoDao.save(historicoCandidato);
		
		boolean retorno = historicoCandidatoDao.updateAgenda(historicoCandidato.getId(), hoje, "10:00", "10:30", "observacao");
		
		assertTrue(retorno);
		
		historicoCandidato = historicoCandidatoDao.findByIdProjection(historicoCandidato.getId());
		
		assertEquals(DateUtil.formataDataExtenso(hoje), DateUtil.formataDataExtenso(historicoCandidato.getData()));
		assertEquals("10:00", historicoCandidato.getHoraIni());
		assertEquals("10:30", historicoCandidato.getHoraFim());
		assertEquals("observacao", historicoCandidato.getObservacao());

		retorno = historicoCandidatoDao.updateAgenda(9898219201221L, hoje, "10:00", "10:30", "observacao");
		
		assertFalse(retorno);
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEtapaSeletivaDao(EtapaSeletivaDao etapaSeletivaDao)
	{
		this.etapaSeletivaDao = etapaSeletivaDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao)
	{
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

}