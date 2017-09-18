package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.dao.captacao.PausaPreenchimentoVagasDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<Solicitacao>
{
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;
	@Autowired
	private EstabelecimentoDao estabelecimentoDao;
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private MotivoSolicitacaoDao motivoSolicitacaoDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private PausaPreenchimentoVagasDao pausaPreenchimentoVagasDao;

	public Solicitacao getEntity()
	{
		Solicitacao solicitacao = new Solicitacao();

		return solicitacao;
	}

	public GenericDao<Solicitacao> getGenericDao()
	{
		return solicitacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

	@Test
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;

		Solicitacao solicitacao = saveSolicitacao(empresa, "Desenvolvedor", true, new Date(), new Date());

		Long usuarioLogado = 1L;
		assertEquals(new Integer(1), solicitacaoDao.getCount('E', empresa.getId(), usuarioLogado, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", 'T', null, String.valueOf(solicitacao.getId()), null, null, true, null, null));
	}

	@Test
	public void testGetCountComStatus()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		saveSolicitacao(empresa, "Desenvolvedor", StatusAprovacaoSolicitacao.APROVADO, true, DateUtil.criarDataMesAno(01, 01, 2015), DateUtil.criarDataMesAno(01, 02, 2015));
		saveSolicitacao(empresa, "Desenvolvedor", StatusAprovacaoSolicitacao.ANALISE, true, DateUtil.criarDataMesAno(01, 01, 2015), DateUtil.criarDataMesAno(29, 01, 2015));
	
		Long usuarioLogadoId = 1L;
		
		assertEquals(new Integer(2), solicitacaoDao.getCount('E', empresa.getId(), usuarioLogadoId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", 'T', new Long[]{}, null, null, null, true, null, DateUtil.criarDataMesAno(01, 02, 2015)));
		assertEquals(new Integer(1), solicitacaoDao.getCount('E', empresa.getId(), usuarioLogadoId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", 'T', null, null, DateUtil.criarDataMesAno(01, 01, 2015), null, true, DateUtil.criarDataMesAno(01, 02, 2015), null));
		
		assertEquals(new Integer(1), solicitacaoDao.getCount('T', empresa.getId(), usuarioLogadoId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", StatusAprovacaoSolicitacao.APROVADO, null, null, null, null, true, DateUtil.criarDataMesAno(29, 01, 2015), DateUtil.criarDataMesAno(01, 02, 2015)));
		assertEquals(new Integer(0), solicitacaoDao.getCount('E', empresa.getId(), usuarioLogadoId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", StatusAprovacaoSolicitacao.APROVADO, null, null, new Date(), new Date(), true, null, null));
		
		assertEquals(new Integer(1), solicitacaoDao.getCount('E', empresa.getId(), usuarioLogadoId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", StatusAprovacaoSolicitacao.ANALISE, null, null, null, new Date(), true, null, null));
	}

	@Test
	public void testFindAllByVisualizacaoEncerrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		Long usuarioLogadoId = 1L;
		
		Solicitacao solicitacao = saveSolicitacao(empresa, "S1", true, DateUtil.criarDataMesAno(1, 2, 2016), new Date());
		solicitacao.setEncerrada(true);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'E', empresa.getId(), usuarioLogadoId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, String.valueOf(solicitacao.getId()), null, null, true, null, new Date());
		
		assertEquals(1, solicitacaos.size());
	}

	@Test
	public void testFindAllByVisualizacaoAberta()
	{
		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEncerrada(false);
		solicitacao.setSuspensa(false);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, null, new Date(), new Date(), true, null, null);

		assertEquals(1, solicitacaos.size());
	}
	
	@Test
	public void testFindAllByVisualizacaoInvivelParaGestorUsuarioGestorComPermisaoVerTodasSolicitacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("João", empresa, solicitante, null);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, colaborador,null, empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Solicitacao solicitacaoInvisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante, areaOrganizacional, true, false, false);
		solicitacaoInvisivelParaGestor = solicitacaoDao.save(solicitacaoInvisivelParaGestor);

		Solicitacao solicitacaoVisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante, areaOrganizacional, false, false, false);
		solicitacaoVisivelParaGestor = solicitacaoDao.save(solicitacaoVisivelParaGestor);
		
		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, 
				cargoId, motivoId, null, 'T', null, null, null, null, true, null, null);
		assertEquals(1, solicitacaos.size());
		assertEquals(solicitacaoVisivelParaGestor.getId(), solicitacaos.iterator().next().getId());
	}
	
	@Test
	public void testFindAllByVisualizacaoInvivelParaGestorUsuarioGestorSemPermisaoVerTodasSolicitacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("João", empresa, solicitante, null);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, colaborador, null, empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Long cargoId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacaoInvisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante, areaOrganizacional, true, false, false);
		solicitacaoInvisivelParaGestor = solicitacaoDao.save(solicitacaoInvisivelParaGestor);
		
		Solicitacao solicitacaoVisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante, areaOrganizacional, false, false, false);
		solicitacaoVisivelParaGestor = solicitacaoDao.save(solicitacaoVisivelParaGestor);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacional.getId(), 
				cargoId, motivoId, null, 'T', new Long[]{areaOrganizacional.getId()}, null, null, null, false, null, null);
		assertEquals(1, solicitacaos.size());
		assertEquals(solicitacaoVisivelParaGestor.getId(), solicitacaos.iterator().next().getId());
	}
	
	@Test
	public void testFindAllByVisualizacaoInvivelParaGestorUsuarioNaoGestorComPermisaoVerTodasSolicitacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Usuario solicitante2 = UsuarioFactory.getEntity();
		solicitante2 = usuarioDao.save(solicitante2);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacaoInvisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante, areaOrganizacional, true, false, true);
		solicitacaoInvisivelParaGestor = solicitacaoDao.save(solicitacaoInvisivelParaGestor);

		Solicitacao solicitacaoVisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante2, areaOrganizacional, false, false, true);
		solicitacaoVisivelParaGestor = solicitacaoDao.save(solicitacaoVisivelParaGestor);
		
		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, 
				motivoId, "", 'T', null, null, new Date(), new Date(), true, null, null);
		assertEquals(2, solicitacaos.size());
	}
	
	@Test
	public void testFindAllByVisualizacaoInvivelParaGestorUsuarioNaoGestorSemPermisaoVerTodasSolicitacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Usuario solicitante2 = UsuarioFactory.getEntity();
		solicitante2 = usuarioDao.save(solicitante2);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacaoInvisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante, areaOrganizacional, true, false, false);
		solicitacaoInvisivelParaGestor = solicitacaoDao.save(solicitacaoInvisivelParaGestor);
		
		Solicitacao solicitacaoVisivelParaGestor = SolicitacaoFactory.getSolicitacao(empresa, solicitante2, areaOrganizacional, false, true, false);
		solicitacaoInvisivelParaGestor.setDataEncerramento(DateUtil.criarDataMesAno(1, 2, 2016));
		solicitacaoVisivelParaGestor = solicitacaoDao.save(solicitacaoVisivelParaGestor);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, 
				motivoId, null, 'T', null, null, new Date(), new Date(), false, null, null);
		assertEquals(1, solicitacaos.size());
		
		solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'T', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, 
				motivoId, null, 'T', null, null, new Date(), new Date(), false, DateUtil.criarDataMesAno(31, 1, 2016), DateUtil.criarDataMesAno(1, 2, 2016));
		assertEquals(1, solicitacaos.size());
	}

	@Test
	public void testFindAllByVisualizacaoAbertaComCargo()
	{
		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao = motivoSolicitacaoDao.save(motivoSolicitacao);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setSuspensa(false);
		solicitacao.setEncerrada(false);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), null, 'T', null, null, null, null, false, null, null);

		assertEquals(1, solicitacaos.size());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testFindAllByVisualizacaoSolicitacaoDescricao()
	{
		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao = motivoSolicitacaoDao.save(motivoSolicitacao);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setSuspensa(false);
		solicitacao.setEmpresa(empresa);
		solicitacao.setEncerrada(false);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setDescricao("Desenvolvedor");
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos1 = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "Desenvolvedor", 'T', null, null, null, null, false, null, null);
		Collection<Solicitacao> solicitacaos2 = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "DESENV", 'T', null, String.valueOf(solicitacao.getId()), DateUtil.criarAnoMesDia(2015, 9, 01), new Date(), false, null, null);
		Collection<Solicitacao> solicitacaos3 = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "Desenvolvedores", 'T', null, null, null, null, false, null, null);
		
		assertEquals(1, solicitacaos1.size());
		assertEquals(1, solicitacaos2.size());
		assertEquals(0, solicitacaos3.size());
	}

	@Test
	public void testFindAllByVisualizacaoSuspensa()
	{
		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long cargoId = -1L;
		Long estabelecimentoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long motivoId = -1L;

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEncerrada(false);
		solicitacao.setSuspensa(true);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, String.valueOf(solicitacao.getId()), null, null, false, null, null);
		assertEquals(1, solicitacaos.size());

		Collection<Solicitacao> solicitacaosBucaComPeriodo = solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, String.valueOf(solicitacao.getId()), new Date(), null, false, null, null);
		assertEquals(1, solicitacaosBucaComPeriodo.size());
	}
	
	@Test
	public void testFindAllByVisualizacaoByCodigoAndData()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long solicitanteId = null;
		Long cargoId = null;
		Long estabelecimentoId = null;
		Long areaOrganizacionalId = null;
		Long motivoId = null;

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setEmpresa(empresa);
		solicitacao1.setData(DateUtil.criarDataMesAno(1, 1, 2014));
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setData(DateUtil.criarDataMesAno(1, 5, 2015));
		solicitacaoDao.save(solicitacao2);
		
		Solicitacao solicitacao3 = SolicitacaoFactory.getSolicitacao();
		solicitacao3.setEmpresa(empresa);
		solicitacao3.setData(DateUtil.criarDataMesAno(1, 8, 2015));
		solicitacaoDao.save(solicitacao3);
		
		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitanteId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, "", DateUtil.criarDataMesAno(1, 1, 2015), DateUtil.criarDataMesAno(1, 8, 2015), true, null, null);
		assertEquals(2, solicitacaos.size());
		
		solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitanteId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, String.valueOf(solicitacao2.getId()), DateUtil.criarDataMesAno(1, 1, 2015), DateUtil.criarDataMesAno(1, 8, 2015), true, null, null);
		assertEquals(1, solicitacaos.size());
	}
	
	@Test
	public void testFindAllByVisualizacaoComStatus()
	{
		Usuario solicitante = UsuarioFactory.getEntity();
		solicitante = usuarioDao.save(solicitante);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Long cargoId = -1L;
		Long estabelecimentoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEncerrada(false);
		solicitacao.setSuspensa(true);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setEncerrada(false);
		solicitacao2.setSuspensa(true);
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setSolicitante(solicitante);
		solicitacao2.setStatus(StatusAprovacaoSolicitacao.REPROVADO);
		solicitacaoDao.save(solicitacao2);
		
		assertEquals(2, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null, null, null, null, false, null, null).size());
		assertEquals(1, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, StatusAprovacaoSolicitacao.APROVADO, null, String.valueOf(solicitacao.getId()), new Date(), null, false, null, null).size());
		assertEquals(1, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, StatusAprovacaoSolicitacao.REPROVADO, null, String.valueOf(solicitacao2.getId()), null, new Date(), false, null, null).size());
		assertEquals(0, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante.getId(), estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, StatusAprovacaoSolicitacao.ANALISE, null, String.valueOf(solicitacao.getId()), new Date(), new Date(), false, null, null).size());
	}
	
	@Test
	public void testFindQtdContratadosFaixa(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento(null);
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional(null);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixa1 = saveFaixaSalarial(cargo);
		FaixaSalarial faixa2 = saveFaixaSalarial(cargo);
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = saveSolicitacao(empresa, hoje, estabelecimento, null, faixa1, null);
		Solicitacao solicitacao2 = saveSolicitacao(empresa, hoje, null, areaOrganizacional, faixa2, null);
		
		Candidato candidato1 = saveCandidato(empresa);
		Candidato candidato2 = saveCandidato(empresa);
		Candidato candidato3 = saveCandidato(empresa);

		CandidatoSolicitacao candidatoSolicitacao1 = saveCandidatoSolicitacao(candidato1, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacao2 = saveCandidatoSolicitacao(candidato2, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacao3 = saveCandidatoSolicitacao(candidato3, solicitacao2);

		saveColaboradorComHistorico(empresa, candidato1, candidatoSolicitacao1, new Date(), StatusRetornoAC.CONFIRMADO);
		saveColaboradorComHistorico(empresa, candidato2, candidatoSolicitacao2, new Date(), StatusRetornoAC.CONFIRMADO);
		saveColaboradorComHistorico(empresa, candidato3, candidatoSolicitacao3, new Date(), StatusRetornoAC.CONFIRMADO);

		
		Long[] solicitacaoIds = new Long[]{solicitacao1.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaIds = new Long[]{areaOrganizacional.getId()};
		
		Collection<FaixaSalarial> faixasSemSolicitacao = solicitacaoDao.findQtdContratadosFaixa(empresa.getId(), null, null, null, hoje, hoje);
		Collection<FaixaSalarial> faixasComSolicitacao = solicitacaoDao.findQtdContratadosFaixa(empresa.getId(), null, null, solicitacaoIds, hoje, hoje);
		Collection<FaixaSalarial> faixasComEstabelecimento = solicitacaoDao.findQtdContratadosFaixa(empresa.getId(), estabelecimentoIds, null, null, hoje, hoje);
		Collection<FaixaSalarial> faixasComAreaOrganizacional = solicitacaoDao.findQtdContratadosFaixa(empresa.getId(), null, areaIds, null, hoje, hoje);
		
		assertEquals(2, faixasSemSolicitacao.size());
		assertEquals(2, ((FaixaSalarial) (faixasSemSolicitacao.toArray()[0])).getQtdContratados());
		assertEquals(1, ((FaixaSalarial) (faixasSemSolicitacao.toArray()[1])).getQtdContratados());
		
		assertEquals(1, faixasComSolicitacao.size());
		assertEquals(2, ((FaixaSalarial) (faixasComSolicitacao.toArray()[0])).getQtdContratados());
		
		assertEquals(1, faixasComEstabelecimento.size());
		assertEquals(faixa1.getId(), ((FaixaSalarial) (faixasComEstabelecimento.toArray()[0])).getId());
		
		assertEquals(1, faixasComAreaOrganizacional.size());
		assertEquals(faixa2.getId(), ((FaixaSalarial) (faixasComAreaOrganizacional.toArray()[0])).getId());
	}
	
	@Test
	public void testFindQtdContratadosArea(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento(null);
		AreaOrganizacional area1 = saveAreaOrganizacional(null);
		AreaOrganizacional area2 = saveAreaOrganizacional(null);
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = saveSolicitacao(empresa, hoje, estabelecimento, area1, null, null);
		Solicitacao solicitacao2 = saveSolicitacao(empresa, hoje, null, area2, null, null);
		
		Candidato candidato1 = saveCandidato(empresa);
		Candidato candidato2 = saveCandidato(empresa);
		Candidato candidato3 = saveCandidato(empresa);

		CandidatoSolicitacao candidatoSolicitacao1 = saveCandidatoSolicitacao(candidato1, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacao2 = saveCandidatoSolicitacao(candidato2, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacao3 = saveCandidatoSolicitacao(candidato3, solicitacao2);
		
		saveColaboradorComHistorico(empresa, candidato1, candidatoSolicitacao1, new Date(), StatusRetornoAC.CONFIRMADO);
		saveColaboradorComHistorico(empresa, candidato2, candidatoSolicitacao2, new Date(), StatusRetornoAC.CONFIRMADO);
		saveColaboradorComHistorico(empresa, candidato3, candidatoSolicitacao3, new Date(), StatusRetornoAC.CONFIRMADO);
		
		Long[] solicitacaoIds = new Long[]{solicitacao1.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaIds = new Long[]{area2.getId()};
		
		Collection<AreaOrganizacional> contratados = solicitacaoDao.findQtdContratadosArea(empresa.getId(), null, null, null, hoje, hoje);
		Collection<AreaOrganizacional> contratadosPorSolicitacao = solicitacaoDao.findQtdContratadosArea(empresa.getId(), null, null, solicitacaoIds, hoje, hoje);
		Collection<AreaOrganizacional> contratadosPorEstabelecimento = solicitacaoDao.findQtdContratadosArea(empresa.getId(), estabelecimentoIds, null, null, hoje, hoje);
		Collection<AreaOrganizacional> contratadosPorAreaOrganizacional= solicitacaoDao.findQtdContratadosArea(empresa.getId(), null, areaIds, null, hoje, hoje);
		
		assertEquals(2, contratados.size());
		assertEquals(2, ((AreaOrganizacional) (contratados.toArray()[0])).getQtdContratados());
		assertEquals(1, ((AreaOrganizacional) (contratados.toArray()[1])).getQtdContratados());
		
		assertEquals(1, contratadosPorSolicitacao.size());
		assertEquals(2, ((AreaOrganizacional) (contratadosPorSolicitacao.toArray()[0])).getQtdContratados());
		
		assertEquals(1, contratadosPorEstabelecimento.size());
		assertEquals(area1.getId(), ((AreaOrganizacional) (contratadosPorEstabelecimento.toArray()[0])).getId());
		
		assertEquals(1, contratadosPorAreaOrganizacional.size());
		assertEquals(area2.getId(), ((AreaOrganizacional) (contratadosPorAreaOrganizacional.toArray()[0])).getId());
	}
	
	@Test
	public void testFindQtdContratadosMotivo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento(null);
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional(null);
		
		MotivoSolicitacao motivo1 = saveMotivoSolicitacao();
		MotivoSolicitacao motivo2 = saveMotivoSolicitacao();
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = saveSolicitacao(empresa, hoje, null, areaOrganizacional, null, motivo1);
		Solicitacao solicitacao2 = saveSolicitacao(empresa, hoje, estabelecimento, null, null, motivo2);
		
		Candidato candidato1 = saveCandidato(empresa);
		Candidato candidato2 = saveCandidato(empresa);
		Candidato candidato3 = saveCandidato(empresa);

		CandidatoSolicitacao candidatoSolicitacao1 = saveCandidatoSolicitacao(candidato1, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacao2 = saveCandidatoSolicitacao(candidato2, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacao3 = saveCandidatoSolicitacao(candidato3, solicitacao2);
		
		saveColaboradorComHistorico(empresa, candidato1, candidatoSolicitacao1, new Date(), StatusRetornoAC.CONFIRMADO);
		saveColaboradorComHistorico(empresa, candidato2, candidatoSolicitacao2, new Date(), StatusRetornoAC.CONFIRMADO);
		saveColaboradorComHistorico(empresa, candidato3, candidatoSolicitacao3, new Date(), StatusRetornoAC.CONFIRMADO);
		
		Long[] solicitacaoIds = new Long[]{solicitacao1.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaIds = new Long[]{areaOrganizacional.getId()};
		
		Collection<MotivoSolicitacao> motivosSemSolicitacao = solicitacaoDao.findQtdContratadosMotivo(empresa.getId(), null, null, null, hoje, hoje);
		Collection<MotivoSolicitacao> motivosComSolicitacao = solicitacaoDao.findQtdContratadosMotivo(empresa.getId(), null, null, solicitacaoIds, hoje, hoje);
		Collection<MotivoSolicitacao> motivosComEstabelecimento = solicitacaoDao.findQtdContratadosMotivo(empresa.getId(), estabelecimentoIds, null, null, hoje, hoje);
		Collection<MotivoSolicitacao> motivosComAreaOrganizacional = solicitacaoDao.findQtdContratadosMotivo(empresa.getId(), null, areaIds, null, hoje, hoje);
		
		assertEquals(2, motivosSemSolicitacao.size());
		assertEquals(2, ((MotivoSolicitacao) (motivosSemSolicitacao.toArray()[0])).getQtdContratados());
		assertEquals(1, ((MotivoSolicitacao) (motivosSemSolicitacao.toArray()[1])).getQtdContratados());
		
		assertEquals(1, motivosComSolicitacao.size());
		assertEquals(2, ((MotivoSolicitacao) (motivosComSolicitacao.toArray()[0])).getQtdContratados());
		
		assertEquals(1, motivosComEstabelecimento.size());
		assertEquals(motivo2.getId(), ((MotivoSolicitacao) (motivosComEstabelecimento.toArray()[0])).getId());
		
		assertEquals(1, motivosComAreaOrganizacional.size());
		assertEquals(motivo1.getId(), ((MotivoSolicitacao) (motivosComAreaOrganizacional.toArray()[0])).getId());
	}

	@Test
	public void testGetIndicadorMediaDiasPreenchimentoVagas() {
		Date data = DateUtil.criarDataMesAno(1, 3, 2010);
		Date dataEncerramento = DateUtil.criarDataMesAno(15, 3, 2010);
		
		Date dataPausa = DateUtil.criarDataMesAno(7, 3, 2010);
		Date dataReinicio = DateUtil.criarDataMesAno(10, 3, 2010);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		Estabelecimento estabelecimento = saveEstabelecimento(empresa);
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional(empresa);
		
		Solicitacao solicitacao1 = saveSolicitacao(data, dataEncerramento, empresa, faixaSalarial, estabelecimento, areaOrganizacional, 1);
		Solicitacao solicitacao2 = saveSolicitacao(data, dataEncerramento, empresa, faixaSalarial, estabelecimento, areaOrganizacional, 1);
		Solicitacao solicitacaoComPausa = saveSolicitacao(data, dataEncerramento, empresa, faixaSalarial, estabelecimento, areaOrganizacional, 1);
		
		Candidato candidato1 = saveCandidato(empresa);
		Candidato candidato2 = saveCandidato(empresa);
		Candidato candidato3 = saveCandidato(empresa);
		
		CandidatoSolicitacao candidatoSolicitacao = saveCandidatoSolicitacao(candidato1, solicitacao2);
		CandidatoSolicitacao candidatoSolicitacao2 = saveCandidatoSolicitacao(candidato2, solicitacaoComPausa);
		CandidatoSolicitacao candidatoSolicitacao3 = saveCandidatoSolicitacao(candidato3, solicitacao1);
		
		savePausaPreenchimentoVagas(dataPausa, dataReinicio, solicitacaoComPausa);
		// contratado 6 dias após início da solicitação
		saveColaboradorComHistorico(empresa, candidato1, candidatoSolicitacao, DateUtil.criarDataMesAno(7, 3, 2010), StatusRetornoAC.CONFIRMADO); 
		// contratado 14 dias após início da solicitação
		saveColaboradorComHistorico(empresa, candidato2, candidatoSolicitacao2, DateUtil.criarDataMesAno(15, 3, 2010), StatusRetornoAC.CONFIRMADO); 
		// Aguardando confirmação
		saveColaboradorComHistorico(empresa, candidato3, candidatoSolicitacao3, DateUtil.criarDataMesAno(15, 3, 2010), StatusRetornoAC.AGUARDANDO); 
		
		Collection<Long> areasIds = Arrays.asList(areaOrganizacional.getId());
		Collection<Long> estabelecimentosIds = Arrays.asList(estabelecimento.getId());
		Long[] solicitacaoIds = new Long[]{solicitacao2.getId()};
		Long[] solicitacaoIdComPausa = new Long[]{solicitacaoComPausa.getId()};
		
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresSemSolicitacao = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, null, null, false );
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresSemSolicitacaoComHistoricoFuturo = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, null, null, true );
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresComSolicitacao = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, solicitacaoIds, null, false );
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresComPausa = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, solicitacaoIdComPausa, null, false );
		
		assertEquals(1, indicadoresSemSolicitacao.size());
		
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga = (IndicadorDuracaoPreenchimentoVaga) indicadoresSemSolicitacao.toArray()[0];
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVagaComHistoricoFuturo = (IndicadorDuracaoPreenchimentoVaga) indicadoresSemSolicitacaoComHistoricoFuturo.toArray()[0];
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVagaComSolicitacao = (IndicadorDuracaoPreenchimentoVaga) indicadoresComSolicitacao.toArray()[0];
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVagaComPausa = (IndicadorDuracaoPreenchimentoVaga) indicadoresComPausa.toArray()[0];
		
		assertEquals("deve retornar a média de dias", new Double(11.0), indicadorDuracaoPreenchimentoVaga.getMediaDias());
		assertEquals("deve retornar qtd contratados sem solicitação especificada", 2, indicadorDuracaoPreenchimentoVaga.getQtdContratados().intValue());
		assertEquals("deve retornar qtd contratados sem solicitação especificada com histórico futuro", 3, indicadorDuracaoPreenchimentoVagaComHistoricoFuturo.getQtdContratados().intValue());
		assertEquals("deve retornar qtd contratados com solicitação especificada", 1, indicadorDuracaoPreenchimentoVagaComSolicitacao.getQtdContratados().intValue());
		assertEquals("deve retornar qtd contratados com solicitação especificada (com pausa)", new Double(11.0), indicadorDuracaoPreenchimentoVagaComPausa.getMediaDias());
	}
	
	@Test
	public void testGetValor()
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacaoDao.save(motivoSolicitacao);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setAreaOrganizacional(area);
		solicitacaoDao.save(solicitacao);

		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacao1.setSolicitacao(solicitacao);
		candidatoSolicitacao1.setStatus(StatusCandidatoSolicitacao.CONTRATADO);
		candidatoSolicitacaoDao.save(candidatoSolicitacao1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setSolicitacao(solicitacao);
		candidatoSolicitacao2.setStatus(StatusCandidatoSolicitacao.PROMOVIDO);
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		Candidato candidato3 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato3);
		
		CandidatoSolicitacao candidatoSolicitacao3 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao3.setSolicitacao(solicitacao);
		candidatoSolicitacao3.setStatus(StatusCandidatoSolicitacao.INDIFERENTE);
		candidatoSolicitacao3.setCandidato(candidato3);
		candidatoSolicitacaoDao.save(candidatoSolicitacao3);
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = Arrays.asList(candidatoSolicitacao1, candidatoSolicitacao2, candidatoSolicitacao3);
		
		solicitacao.setCandidatoSolicitacaos(candidatoSolicitacaos);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacaoRetorno = solicitacaoDao.getValor(solicitacao.getId());

		assertEquals(solicitacao, solicitacaoRetorno);
		assertEquals(2, solicitacaoRetorno.getQtdVagasPreenchidas().intValue());
	}

	private Solicitacao saveSolicitacao(Date dataSolicitacao, Date dataEncerramento, Empresa empresa, FaixaSalarial faixaSalarial, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Integer qtdVagas) {
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setData(dataSolicitacao);
		solicitacao.setDataEncerramento(dataEncerramento);
		solicitacao.setEmpresa(empresa);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setQuantidade(qtdVagas);
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
	
	
	private Solicitacao saveSolicitacao(Empresa empresa, String descricao, boolean encerrada, Date dataSolicitacao, Date dataEncerramento){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao(descricao);
		solicitacao.setEncerrada(encerrada);
		solicitacao.setEmpresa(empresa);
		solicitacao.setData(dataSolicitacao);
		solicitacao.setDataEncerramento(dataEncerramento);
		solicitacao = solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
	
	private Solicitacao saveSolicitacao(Empresa empresa, String descricao, char status, boolean encerrada, Date dataSolicitacao, Date dataEncerramento){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacao.setDescricao(descricao);
		solicitacao.setStatus(status);
		solicitacao.setEncerrada(encerrada);
		solicitacao.setData(dataSolicitacao);
		solicitacao.setDataEncerramento(dataEncerramento);
		solicitacao = solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
	
	private Solicitacao saveSolicitacao(Empresa empresa, Date date, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, FaixaSalarial faixa, MotivoSolicitacao motivoSolicitacao){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setData(date);
		solicitacao.setEmpresa(empresa);		
		solicitacao.setFaixaSalarial(faixa);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
	
	private FaixaSalarial saveFaixaSalarial(Cargo cargo){
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity();
		faixa.setCargo(cargo);
		faixa = faixaSalarialDao.save(faixa);
		return faixa;
	}
	
	private Candidato saveCandidato(Empresa empresa){
		Candidato candidato = CandidatoFactory.getCandidao(empresa);
		candidatoDao.save(candidato);
		return candidato;
	}
	
	private CandidatoSolicitacao saveCandidatoSolicitacao(Candidato candidato, Solicitacao solicitacao){
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, false);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		return candidatoSolicitacao;
	}
	
	private void saveColaboradorComHistorico(Empresa empresa, Candidato candidato, CandidatoSolicitacao candidatoSolicitacao, Date dataHistorico, Integer status){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaborador.setDataAdmissao(dataHistorico);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoColaborador.setData(dataHistorico);
		historicoColaborador.setStatus(status);
		historicoColaboradorDao.save(historicoColaborador);
	}
	
	private Estabelecimento saveEstabelecimento(Empresa empresa) {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		return estabelecimento;
	}
	
	private AreaOrganizacional saveAreaOrganizacional(Empresa empresa) {
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional);
		return areaOrganizacional;
	}
	
	private MotivoSolicitacao saveMotivoSolicitacao() {
		MotivoSolicitacao motivo1 = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacaoDao.save(motivo1);
		return motivo1;
	}
	
	private PausaPreenchimentoVagas savePausaPreenchimentoVagas(Date dataPausa, Date dataReinicio, Solicitacao solicitacao){
		PausaPreenchimentoVagas pausaPreenchimentoVagas = new PausaPreenchimentoVagas();
		pausaPreenchimentoVagas.setDataReinicio(dataReinicio);
		pausaPreenchimentoVagas.setDataPausa(dataPausa);
		pausaPreenchimentoVagas.setSolicitacao(solicitacao);
		pausaPreenchimentoVagasDao.save(pausaPreenchimentoVagas);
		return pausaPreenchimentoVagas;
	}
}