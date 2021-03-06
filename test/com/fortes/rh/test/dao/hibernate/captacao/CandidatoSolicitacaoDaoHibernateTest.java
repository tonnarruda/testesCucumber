package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("unchecked")
public class CandidatoSolicitacaoDaoHibernateTest extends GenericDaoHibernateTest<CandidatoSolicitacao>
{
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private SolicitacaoDao solicitacaoDao;
	private CandidatoDao candidatoDao;
	private CidadeDao cidadeDao;
	private EstadoDao estadoDao;
	private HistoricoCandidatoDao historicoCandidatoDao;
	private EtapaSeletivaDao etapaSeletivaDao;
	private ColaboradorDao colaboradorDao;
	private AvaliacaoDao avaliacaoDao;
	private SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private EstabelecimentoDao estabelecimentoDao;
	private HistoricoColaboradorDao historicoColaboradorDao;

	private Candidato candidato;
	private Solicitacao solicitacao;
	private CandidatoSolicitacao candidatoSolicitacao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private MotivoSolicitacaoDao motivoSolicitacaoDao;

	public CandidatoSolicitacao getEntity()
	{
		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();

		candidatoSolicitacao.setId(null);
		candidatoSolicitacao.setTriagem(true);
		return candidatoSolicitacao;
	}

	public void testFindHistoricosByEtapaSolicitacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacaoDao.save(motivoSolicitacao);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEncerrada(false);
		solicitacao.setEmpresa(empresa);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacaoDao.save(solicitacao);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		EtapaSeletiva entrevista = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(entrevista);

		EtapaSeletiva redacao = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(redacao);

		EtapaSeletiva entrevistaComGerente = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(entrevistaComGerente);
		
		HistoricoCandidato historicoCandidatoEntrevista = new HistoricoCandidato();
		historicoCandidatoEntrevista.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoCandidatoEntrevista.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidatoEntrevista.setApto(Apto.SIM);
		historicoCandidatoEntrevista.setEtapaSeletiva(entrevista);
		historicoCandidatoDao.save(historicoCandidatoEntrevista);
		
		HistoricoCandidato historicoCandidatoRedacao = new HistoricoCandidato();
		historicoCandidatoRedacao.setData(DateUtil.criarDataMesAno(02, 02, 2002));
		historicoCandidatoRedacao.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidatoRedacao.setApto(Apto.SIM);
		historicoCandidatoRedacao.setEtapaSeletiva(redacao);
		historicoCandidatoDao.save(historicoCandidatoRedacao);

		HistoricoCandidato historicoCandidatoEntrevistaInicial = new HistoricoCandidato();
		historicoCandidatoEntrevistaInicial.setData(DateUtil.criarDataMesAno(31, 12, 2002));
		historicoCandidatoEntrevistaInicial.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidatoEntrevistaInicial.setApto(Apto.SIM);
		historicoCandidatoEntrevistaInicial.setEtapaSeletiva(entrevistaComGerente);
		historicoCandidatoDao.save(historicoCandidatoEntrevistaInicial);
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatosBySolicitacao(new Long[]{entrevistaComGerente.getId(), entrevista.getId(), redacao.getId()}, empresa.getId(),  StatusSolicitacao.TODAS, Apto.SIM, DateUtil.criarDataMesAno(01, 01, 2000), DateUtil.criarDataMesAno(02, 02, 2002));
		Collection<CandidatoSolicitacao> candidatoSolicitacaosSemData = candidatoSolicitacaoDao.getCandidatosBySolicitacao(new Long[]{entrevistaComGerente.getId(), entrevista.getId(), redacao.getId()}, empresa.getId(),  StatusSolicitacao.TODAS, Apto.SIM, null, null);
		Collection<CandidatoSolicitacao> candidatoSolicitacaosVazio = candidatoSolicitacaoDao.getCandidatosBySolicitacao(new Long[]{entrevistaComGerente.getId(), entrevista.getId(), redacao.getId()}, empresa.getId(),  StatusSolicitacao.TODAS, Apto.SIM, DateUtil.criarDataMesAno(01, 01, 2003), DateUtil.criarDataMesAno(02, 02, 2003));

		assertEquals("Participou de duas etapas no período", 2, candidatoSolicitacaos.size());
		assertEquals("Sem filtro de período. Traz todas as etapas em que ele participou", 3, candidatoSolicitacaosSemData.size());
		assertEquals("Período fora das etapas realizadas pelo candidato", 0, candidatoSolicitacaosVazio.size());
	}

	
	public GenericDao<CandidatoSolicitacao> getGenericDao()
	{
		return candidatoSolicitacaoDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao)
	{
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}
	
	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

	private CandidatoSolicitacao prepareFindCandidatoSolicitacao()
	{
		solicitacao = new Solicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		candidato = CandidatoFactory.getCandidato();
		candidato.getEndereco().setUf(null);
		candidato.getEndereco().setCidade(null);
		candidato.setContratado(false);
		candidato = candidatoDao.save(candidato);

		candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);

		return candidatoSolicitacao;
	}

	public void testUpdateTriagem()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao.setTriagem(false);
		candidatoSolicitacao.setApto(Apto.SIM);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Long[] ids = new Long[] {candidatoSolicitacao.getId()};
		
		candidatoSolicitacaoDao.updateTriagem(ids, true);

		CandidatoSolicitacao candidatoSolicitacaoRetorno = candidatoSolicitacaoDao.findByCandidatoSolicitacao(candidatoSolicitacao);
		assertTrue(candidatoSolicitacaoRetorno.isTriagem());

		candidatoSolicitacaoRetorno = candidatoSolicitacaoDao.findByCandidatoSolicitacao(CandidatoSolicitacaoFactory.getEntity(546546L));
		assertNull(candidatoSolicitacaoRetorno);
	}

	public void testGetCandidatoSolicitacaoByCandidato()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		CandidatoSolicitacao candidatoSolicitacaoRetorno = candidatoSolicitacaoDao.getCandidatoSolicitacaoByCandidato(candidato.getId());

		assertEquals(candidatoSolicitacao.getId(), candidatoSolicitacaoRetorno.getId());
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void testGetCandidatosBySolicitacao()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		ArrayList<Long> candidatoSolicitacaoIds = new ArrayList<Long>();
		candidatoSolicitacaoIds.add(candidatoSolicitacao.getId());

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatosBySolicitacao(solicitacao, candidatoSolicitacaoIds);
		assertEquals(0, candidatoSolicitacaos.size());
	}

	public void testFindCandidatoSolicitacaoById()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		CandidatoSolicitacao candidatoSolicitacaoRetorno = candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao.getId());
		assertEquals(candidatoSolicitacaoRetorno, candidatoSolicitacao);
	}

	public void testFindCandidatoSolicitacaoByIdArray()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.findCandidatoSolicitacaoById(new Long[]{candidatoSolicitacao.getId()});
		assertEquals(1, candidatoSolicitacaos.size());
	}

	public void testFindBySolicitacaoTriagem()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Endereco endereco = new Endereco();
		endereco.setUf(estado);
		endereco.setCidade(cidade);

		candidatoSolicitacao = prepareFindCandidatoSolicitacao();

		candidato.setEndereco(endereco);
		candidatoDao.update(candidato);

		candidatoSolicitacao.setTriagem(true);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.findBySolicitacaoTriagem(solicitacao.getId());
		assertEquals(1, candidatoSolicitacaos.size());
	}

	public void testFindByFiltroSolicitacaoTriagem()
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setEncerrada(false);
		solicitacao.setDescricao("teste");
		solicitacao.setId(1L);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao.setTriagem(true);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.findByFiltroSolicitacaoTriagem(true);
		assertNotNull(candidatoSolicitacaos);
	}
	
	public void testGetCandidatoSolicitacaoEtapaSeletiva()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);

		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(etapaSeletiva1);
		
		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(etapaSeletiva2);

		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidato1.setNome("cand1");
		candidatoDao.save(candidato1);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2.setNome("cand2");
		candidatoDao.save(candidato2);
		
		CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacao1.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao1);

		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacao2.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity();
		historicoCandidato1.setEtapaSeletiva(etapaSeletiva1);
		historicoCandidato1.setCandidatoSolicitacao(candidatoSolicitacao1);
		historicoCandidatoDao.save(historicoCandidato1);
		
		HistoricoCandidato historicoCandidato2 = HistoricoCandidatoFactory.getEntity();
		historicoCandidato2.setEtapaSeletiva(etapaSeletiva2);
		historicoCandidato2.setCandidatoSolicitacao(candidatoSolicitacao1);
		historicoCandidatoDao.save(historicoCandidato2);
		
		HistoricoCandidato historicoCandidatoSol2 = HistoricoCandidatoFactory.getEntity();
		historicoCandidatoSol2.setEtapaSeletiva(etapaSeletiva1);
		historicoCandidatoSol2.setCandidatoSolicitacao(candidatoSolicitacao2);
		historicoCandidatoDao.save(historicoCandidatoSol2);
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatoSolicitacaoEtapasEmGrupo(solicitacao.getId(), etapaSeletiva1.getId());
		assertEquals(2, candidatoSolicitacaos.size());
	}

	public void testGetCount()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		assertEquals(new Integer(1), candidatoSolicitacaoDao.getCount(solicitacao.getId(), null, null, null, true, null, null, null, true));
	}

	public void testFindCandidatosAptosMover()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Solicitacao solicitacao2 = new Solicitacao();
		solicitacao2 = solicitacaoDao.save(solicitacao);

		CandidatoSolicitacao candidatoSolicitacao2 = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao2.setSolicitacao(solicitacao2);
		candidatoSolicitacao2 = candidatoSolicitacaoDao.save(candidatoSolicitacao2);

		Collection<Long> candidatoSolicitacaos = candidatoSolicitacaoDao.findCandidatosIdsAptosMover(new Long[]{candidatoSolicitacao.getId(),candidatoSolicitacao2.getId()}, solicitacao);
		assertEquals(1, candidatoSolicitacaos.size());
	}

	public void testUpdateSolicitacaoCandidatos()
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao.setSolicitacao(null);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Collection<Long> candidatoSolicitacaosIds = new ArrayList<Long>();
		candidatoSolicitacaosIds.add(candidatoSolicitacao.getId());

		candidatoSolicitacaoDao.updateSolicitacaoCandidatos(solicitacao, candidatoSolicitacaosIds);
		CandidatoSolicitacao retorno = candidatoSolicitacaoDao.findByCandidatoSolicitacao(candidatoSolicitacao);

		assertEquals(solicitacao.getId(), retorno.getSolicitacao().getId());
	}

	public void testFindNaoAptos()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		HistoricoCandidato historicoCandidato = HistoricoCandidatoFactory.getEntity();
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato.setApto(Apto.NAO);
		historicoCandidato = historicoCandidatoDao.save(historicoCandidato);

		Collection<CandidatoSolicitacao> candidatoNaoAptos = candidatoSolicitacaoDao.findNaoAptos(solicitacao.getId());

		assertEquals(1, candidatoNaoAptos.size());
	}

	public void testGetCandidatosPorSolicitacao()
	{
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidato1 = candidatoDao.save(candidato1);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2 = candidatoDao.save(candidato2);

		Solicitacao solicitacao = new Solicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacao1.setSolicitacaoId(solicitacao.getId());

		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacao2.setSolicitacaoId(solicitacao.getId());

		candidatoSolicitacao1 = candidatoSolicitacaoDao.save(candidatoSolicitacao1);

		candidatoSolicitacao2 = candidatoSolicitacaoDao.save(candidatoSolicitacao2);

		Collection<Long> retorno = candidatoSolicitacaoDao.getCandidatosBySolicitacao(solicitacao.getId());

		assertEquals(2, retorno.size());
	}

	public void testSetStatusByColaborador()
	{
		candidatoSolicitacao = prepareFindCandidatoSolicitacao();
		candidatoSolicitacao.setStatus(StatusCandidatoSolicitacao.CONTRATADO);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidatoSolicitacao.getCandidato());
		colaboradorDao.save(colaborador);
		
		candidatoSolicitacaoDao.setStatusByColaborador(StatusCandidatoSolicitacao.INDIFERENTE, colaborador.getId());
		candidatoSolicitacao = candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao.getId());
		
		assertEquals(StatusCandidatoSolicitacao.INDIFERENTE, candidatoSolicitacao.getStatus());
	}
	
	public void testRemoveByCandidato()
	{
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacao1.setSolicitacaoId(solicitacao.getId());
		candidatoSolicitacaoDao.save(candidatoSolicitacao1);
		
		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacao2.setSolicitacaoId(solicitacao.getId());
		candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		assertEquals(1, solicitacaoDao.findAllByCandidato(candidato1.getId()).size());
		assertEquals(1, solicitacaoDao.findAllByCandidato(candidato2.getId()).size());
		
		candidatoSolicitacaoDao.removeByCandidato(candidato1.getId());

		assertEquals(0, solicitacaoDao.findAllByCandidato(candidato1.getId()).size());
		assertEquals(1, solicitacaoDao.findAllByCandidato(candidato2.getId()).size());
	}
	
	public void testFindAvaliacoesCandidatoSolicitacao()
	{
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacaoDao.save(solicitacao);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);

		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao1 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao1.setSolicitacao(solicitacao);
		solicitacaoAvaliacao1.setAvaliacao(avaliacao1);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);

		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setSolicitacao(solicitacao);
		solicitacaoAvaliacao2.setAvaliacao(avaliacao2);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);
		
		CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacao1.setSolicitacaoId(solicitacao.getId());
		candidatoSolicitacaoDao.save(candidatoSolicitacao1);
		
		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacao2.setSolicitacaoId(solicitacao.getId());
		candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setCandidato(candidato1);
		colaboradorQuestionario.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		assertEquals("Uma avaliação respondida e uma não respondida", 2, candidatoSolicitacaoDao.findAvaliacoesCandidatoSolicitacao(solicitacao.getId(), candidato1.getId()).size());
		assertEquals("Duas avaliações não respondidas", 2, candidatoSolicitacaoDao.findAvaliacoesCandidatoSolicitacao(solicitacao.getId(), candidato2.getId()).size());
	}
	
	public void testFindColabParticipantesDaSolicitacaoByResponsavelArea()
	{
		Candidato candidatoDentro = saveCandidato();
		Candidato candidatoFora = saveCandidato();
		
		AreaOrganizacional areaOrganizacional1 = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacional2 = saveAreaOrganizacional();
		
		Cargo cargo = CargoFactory.getEntity(null, "Cargo Nome");
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity("Faixa Nome", cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("Estabeleciemnto Nome");
		estabelecimentoDao.save(estabelecimento);

		Colaborador colaborador1 = saveColaboradorComCandidatoEHistorico("Colab 1", candidatoDentro, DateUtil.criarDataMesAno(1, 1, 2016), faixaSalarial, estabelecimento, areaOrganizacional1,  StatusRetornoAC.CONFIRMADO );
		saveColaboradorComCandidatoEHistorico("Colab 2", candidatoFora, DateUtil.criarDataMesAno(1, 1, 2016), faixaSalarial, estabelecimento, areaOrganizacional2, StatusRetornoAC.CONFIRMADO);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao("Solicitacao", areaOrganizacional1, faixaSalarial, estabelecimento, false);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacaoEcerrada = SolicitacaoFactory.getSolicitacao("SolicitacaoEncerrada", areaOrganizacional2, faixaSalarial, estabelecimento, true);
		solicitacaoDao.save(solicitacaoEcerrada);
		
		saveCandidatoSolicitacao(candidatoDentro, solicitacao, StatusAprovacaoSolicitacao.APROVADO);
		saveCandidatoSolicitacao(candidatoDentro, solicitacaoEcerrada, StatusAprovacaoSolicitacao.APROVADO);
		saveCandidatoSolicitacao(candidatoFora, solicitacao, StatusAprovacaoSolicitacao.APROVADO);
		saveCandidatoSolicitacao(candidatoFora, solicitacaoEcerrada, StatusAprovacaoSolicitacao.APROVADO);
		
		Collection<CandidatoSolicitacao> solicitacoes = candidatoSolicitacaoDao.findColaboradorParticipantesDaSolicitacaoByAreas(new Long[]{areaOrganizacional1.getId()}, null, null, 'T', null, null);
		
		assertEquals(1, solicitacoes.size());
		assertEquals(colaborador1.getNome(), ((CandidatoSolicitacao)solicitacoes.toArray()[0]).getColaboradorNome());
		assertEquals(solicitacao.getDescricao(), ((CandidatoSolicitacao)solicitacoes.toArray()[0]).getSolicitacao().getDescricao());
		assertEquals(estabelecimento.getNome(), ((CandidatoSolicitacao)solicitacoes.toArray()[0]).getSolicitacao().getEstabelecimento().getNome());
		assertEquals(areaOrganizacional1.getNome(), ((CandidatoSolicitacao)solicitacoes.toArray()[0]).getSolicitacao().getAreaOrganizacional().getNome());
		assertEquals(faixaSalarial.getNomeDeCargoEFaixa(), ((CandidatoSolicitacao)solicitacoes.toArray()[0]).getSolicitacao().getFaixaSalarial().getNomeDeCargoEFaixa());
	}
	
	
	private Colaborador saveColaboradorComCandidatoEHistorico(String nomeColaborador, Candidato candidato, Date dataHistorico, FaixaSalarial faixaSalarial, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, int status ){
		Colaborador colaborador = ColaboradorFactory.getEntity(null, nomeColaborador);
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador, dataHistorico, faixaSalarial, estabelecimento, areaOrganizacional, null, null, status);
		historicoColaboradorDao.save(historicoColaborador1);
		return colaborador;
	}
	
	private Candidato saveCandidato(){
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		return candidato;
	}
	
	private AreaOrganizacional saveAreaOrganizacional(){
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		return areaOrganizacional;
	}
	
	private void saveCandidatoSolicitacao(Candidato candidato, Solicitacao solicitacao, char statusAprovacaoSolicitaca){
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, statusAprovacaoSolicitaca);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
	}
	
	public void setCidadeDao(CidadeDao cidadeDao)
	{
		this.cidadeDao = cidadeDao;
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}

	public void setHistoricoCandidatoDao(HistoricoCandidatoDao historicoCandidatoDao)
	{
		this.historicoCandidatoDao = historicoCandidatoDao;
	}

	public void setEtapaSeletivaDao(EtapaSeletivaDao etapaSeletivaDao)
	{
		this.etapaSeletivaDao = etapaSeletivaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao)
	{
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setSolicitacaoAvaliacaoDao(SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao)
	{
		this.solicitacaoAvaliacaoDao = solicitacaoAvaliacaoDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setMotivoSolicitacaoDao(MotivoSolicitacaoDao motivoSolicitacaoDao)
	{
		this.motivoSolicitacaoDao = motivoSolicitacaoDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
}
