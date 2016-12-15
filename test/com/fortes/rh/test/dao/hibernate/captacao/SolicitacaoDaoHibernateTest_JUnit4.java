package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
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
}