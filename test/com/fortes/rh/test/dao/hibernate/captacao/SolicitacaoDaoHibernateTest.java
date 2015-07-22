package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import com.fortes.rh.dao.geral.BairroDao;
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
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
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

public class SolicitacaoDaoHibernateTest extends GenericDaoHibernateTest<Solicitacao>
{
	private SolicitacaoDao solicitacaoDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private BairroDao bairroDao;
	private EstabelecimentoDao estabelecimentoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private MotivoSolicitacaoDao motivoSolicitacaoDao;
	private CandidatoDao candidatoDao;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private PausaPreenchimentoVagasDao pausaPreenchimentoVagasDao;
	
	public Solicitacao getEntity()
	{
		Solicitacao solicitacao = new Solicitacao();

		solicitacao.setAreaOrganizacional(null);
		solicitacao.setFaixaSalarial(null);
		solicitacao.setData(new Date());
		solicitacao.setEscolaridade("a");
		solicitacao.setIdadeMaxima(50);
		solicitacao.setIdadeMinima(15);
		solicitacao.setInfoComplementares("infor");
		solicitacao.setQuantidade(200);
		solicitacao.setRemuneracao(1500.00);
		solicitacao.setSexo("m");
		solicitacao.setVinculo("a");
		solicitacao.setSolicitante(null);
		solicitacao.setEncerrada(false);
		solicitacao.setEmpresa(null);
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

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;

		Solicitacao solicitacao = getEntity();
		solicitacao.setDescricao("Desenvolvedor");
		solicitacao.setEncerrada(true);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		assertEquals(new Integer(1), solicitacaoDao.getCount('E', empresa.getId(), null, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", 'T', null));
	}

	public void testGetCountComStatus()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacao = getEntity();
		solicitacao.setDescricao("Desenvolvedor");
		solicitacao.setEncerrada(true);
		solicitacao.setEmpresa(empresa);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setDescricao("Desenvolvedor");
		solicitacao2.setEncerrada(true);
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		solicitacao2 = solicitacaoDao.save(solicitacao2);
		
		assertEquals(new Integer(2), solicitacaoDao.getCount('E', empresa.getId(), null, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", 'T', null));
		assertEquals(new Integer(1), solicitacaoDao.getCount('E', empresa.getId(), null, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", StatusAprovacaoSolicitacao.APROVADO, null));
		assertEquals(new Integer(1), solicitacaoDao.getCount('E', empresa.getId(), null, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, "dese", StatusAprovacaoSolicitacao.ANALISE, null));
	}

	public void testFindAllByVisualizacaoEncerrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Long cargoId = -1L;
		Long areaOrganizacionalId = -1L;
		Long estabelecimentoId = -1L;
		Long motivoId = -1L;
		
		Solicitacao solicitacao = getEntity();
		solicitacao.setEncerrada(true);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'E', empresa.getId(), null, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null);

		assertEquals(1, solicitacaos.size());
	}

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
		
		Solicitacao solicitacao = getEntity();
		solicitacao.setEncerrada(false);
		solicitacao.setSuspensa(false);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null);

		assertEquals(1, solicitacaos.size());
	}
	
	public void testFindQtdVagasDisponiveis()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimentoFora);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		AreaOrganizacional areaOrganizacionalFora = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalFora);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("II");
		faixaSalarial2.setCargo(cargo);
		faixaSalarial2 = faixaSalarialDao.save(faixaSalarial2);
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = getEntity();
		solicitacao1.setEncerrada(false);
		solicitacao1.setSuspensa(false);
		solicitacao1.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao1.setData(hoje);
		solicitacao1.setEmpresa(empresa);		
		solicitacao1.setFaixaSalarial(faixaSalarial1);
		solicitacao1.setQuantidade(5);
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setEncerrada(false);
		solicitacao2.setSuspensa(false);
		solicitacao2.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao2.setData(hoje);
		solicitacao2.setEmpresa(empresa);		
		solicitacao2.setFaixaSalarial(faixaSalarial2);
		solicitacao2.setQuantidade(20);
		solicitacaoDao.save(solicitacao2);
		
		Solicitacao solicitacaoTesteEstabelecimento1 = getEntity();
		solicitacaoTesteEstabelecimento1.setEncerrada(false);
		solicitacaoTesteEstabelecimento1.setSuspensa(false);
		solicitacaoTesteEstabelecimento1.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoTesteEstabelecimento1.setData(hoje);
		solicitacaoTesteEstabelecimento1.setEmpresa(empresa);		
		solicitacaoTesteEstabelecimento1.setEstabelecimento(estabelecimentoFora);		
		solicitacaoTesteEstabelecimento1.setFaixaSalarial(faixaSalarial2);
		solicitacaoTesteEstabelecimento1.setQuantidade(2);
		solicitacaoDao.save(solicitacaoTesteEstabelecimento1);
		
		Solicitacao solicitacaoTesteEstabelecimento2 = getEntity();
		solicitacaoTesteEstabelecimento2.setEncerrada(false);
		solicitacaoTesteEstabelecimento2.setSuspensa(false);
		solicitacaoTesteEstabelecimento2.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoTesteEstabelecimento2.setData(hoje);
		solicitacaoTesteEstabelecimento2.setEmpresa(empresa);		
		solicitacaoTesteEstabelecimento2.setEstabelecimento(estabelecimento);		
		solicitacaoTesteEstabelecimento2.setFaixaSalarial(faixaSalarial1);
		solicitacaoTesteEstabelecimento2.setQuantidade(4);
		solicitacaoDao.save(solicitacaoTesteEstabelecimento2);
		
		Solicitacao solicitacaoTesteArea1 = getEntity();
		solicitacaoTesteArea1.setEncerrada(false);
		solicitacaoTesteArea1.setSuspensa(false);
		solicitacaoTesteArea1.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoTesteArea1.setData(hoje);
		solicitacaoTesteArea1.setEmpresa(empresa);		
		solicitacaoTesteArea1.setFaixaSalarial(faixaSalarial1);
		solicitacaoTesteArea1.setAreaOrganizacional(areaOrganizacional);
		solicitacaoTesteArea1.setQuantidade(6);
		solicitacaoDao.save(solicitacaoTesteArea1);
		
		Solicitacao solicitacaoTesteArea2 = getEntity();
		solicitacaoTesteArea2.setEncerrada(false);
		solicitacaoTesteArea2.setSuspensa(false);
		solicitacaoTesteArea2.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoTesteArea2.setData(hoje);
		solicitacaoTesteArea2.setEmpresa(empresa);		
		solicitacaoTesteArea2.setFaixaSalarial(faixaSalarial2);
		solicitacaoTesteArea2.setAreaOrganizacional(areaOrganizacionalFora);
		solicitacaoTesteArea2.setQuantidade(8);
		solicitacaoDao.save(solicitacaoTesteArea2);
		
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaIds = new Long[]{areaOrganizacional.getId()};
		Long[] solicitacaoIds1 = new Long[]{solicitacao1.getId()};
		Long[] solicitacaoIds2 = new Long[]{solicitacaoTesteArea1.getId()};
		
		Collection<FaixaSalarial> faixasSemSolicitacao = solicitacaoDao.findQtdVagasDisponiveis(empresa.getId(), null , null, null, hoje, hoje, 'S');
		Collection<FaixaSalarial> faixasComSolicitacao = solicitacaoDao.findQtdVagasDisponiveis(empresa.getId(), null , null, solicitacaoIds1, hoje, hoje, 'S');
		Collection<FaixaSalarial> faixasSemSolicitacaoComEstabelecimento = solicitacaoDao.findQtdVagasDisponiveis(empresa.getId(), estabelecimentoIds , null, null, hoje, hoje, 'S');
		Collection<FaixaSalarial> faixasComSolicitacaoComArea = solicitacaoDao.findQtdVagasDisponiveis(empresa.getId(), null , areaIds, solicitacaoIds2, hoje, hoje, 'S');
		
		FaixaSalarial retornoFaixasSemSolicitacao_1 = (FaixaSalarial) faixasSemSolicitacao.toArray()[0]; 
		FaixaSalarial retornoFaixasComSolicitacao = (FaixaSalarial) faixasComSolicitacao.toArray()[0]; 
		FaixaSalarial retornoFaixasSemSolicitacaoComEstabelecimento = (FaixaSalarial) faixasSemSolicitacaoComEstabelecimento.toArray()[0]; 
		FaixaSalarial retornoFaixasComSolicitacaoComArea = (FaixaSalarial) faixasComSolicitacaoComArea.toArray()[0]; 
		
		assertEquals("Todas as solicitações", 2, faixasSemSolicitacao.size());
		assertEquals("Vagas das solicitações da Faixa Salarial II", 30, retornoFaixasSemSolicitacao_1.getQtdVagasAbertas());
		
		assertEquals("Somente solicitação 1", 1, faixasComSolicitacao.size());
		assertEquals("Vagas da solicitação 1", 5, retornoFaixasComSolicitacao.getQtdVagasAbertas());
		
		assertEquals("Solicitação com estabelecimento", 1, faixasSemSolicitacaoComEstabelecimento.size());
		assertEquals("Vagas da solicitação com estabelecimento", 4, retornoFaixasSemSolicitacaoComEstabelecimento.getQtdVagasAbertas());
		
		assertEquals("Solicitação com área organizacional", 1, faixasComSolicitacaoComArea.size());
		assertEquals("Vagas da solicitação com área organizacional", 6, retornoFaixasComSolicitacaoComArea.getQtdVagasAbertas());
	}

	public void testFindQtdContratadosFaixa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixa1.setCargo(cargo);
		faixa1 = faixaSalarialDao.save(faixa1);

		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixa2.setCargo(cargo);
		faixa2 = faixaSalarialDao.save(faixa2);
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = getEntity();
		solicitacao1.setData(hoje);
		solicitacao1.setEmpresa(empresa);		
		solicitacao1.setFaixaSalarial(faixa1);
		solicitacao1.setEstabelecimento(estabelecimento);
		solicitacaoDao.save(solicitacao1);

		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setData(hoje);
		solicitacao2.setEmpresa(empresa);		
		solicitacao2.setFaixaSalarial(faixa2);
		solicitacao2.setAreaOrganizacional(areaOrganizacional);
		solicitacaoDao.save(solicitacao2);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setSolicitacao(solicitacao1);
		colaboradorDao.save(joao);

		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setSolicitacao(solicitacao1);
		colaboradorDao.save(maria);

		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setSolicitacao(solicitacao2);
		colaboradorDao.save(pedro);
		
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
	
	public void testFindQtdContratadosArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);

		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = getEntity();
		solicitacao1.setData(hoje);
		solicitacao1.setEmpresa(empresa);		
		solicitacao1.setAreaOrganizacional(area1);
		solicitacao1.setEstabelecimento(estabelecimento);
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setData(hoje);
		solicitacao2.setEmpresa(empresa);		
		solicitacao2.setAreaOrganizacional(area2);
		solicitacaoDao.save(solicitacao2);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setSolicitacao(solicitacao1);
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setSolicitacao(solicitacao1);
		colaboradorDao.save(maria);
		
		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setSolicitacao(solicitacao2);
		colaboradorDao.save(pedro);
		
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
	
	public void testFindQtdContratadosMotivo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		MotivoSolicitacao motivo1 = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacaoDao.save(motivo1);

		MotivoSolicitacao motivo2 = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacaoDao.save(motivo2);
		
		Date hoje = new Date();
		
		Solicitacao solicitacao1 = getEntity();
		solicitacao1.setData(hoje);
		solicitacao1.setEmpresa(empresa);		
		solicitacao1.setMotivoSolicitacao(motivo1);
		solicitacao1.setAreaOrganizacional(areaOrganizacional);
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setData(hoje);
		solicitacao2.setEmpresa(empresa);		
		solicitacao2.setEstabelecimento(estabelecimento);		
		solicitacao2.setMotivoSolicitacao(motivo2);
		solicitacaoDao.save(solicitacao2);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setSolicitacao(solicitacao1);
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setSolicitacao(solicitacao1);
		colaboradorDao.save(maria);
		
		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setSolicitacao(solicitacao2);
		colaboradorDao.save(pedro);
		
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

		Solicitacao solicitacao = getEntity();
		solicitacao.setSuspensa(false);
		solicitacao.setEncerrada(false);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante, estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), null, 'T', null);

		assertEquals(1, solicitacaos.size());
	}
	
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

		Solicitacao solicitacao = getEntity();
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

		Collection<Solicitacao> solicitacaos1 = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante, estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "Desenvolvedor", 'T', null);
		Collection<Solicitacao> solicitacaos2 = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante, estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "DESENV", 'T', null);
		Collection<Solicitacao> solicitacaos3 = solicitacaoDao.findAllByVisualizacao(1, 10,'A', empresa.getId(), solicitante, estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "Desenvolvedores", 'T', null);
		
		assertEquals(1, solicitacaos1.size());
		assertEquals(1, solicitacaos2.size());
		assertEquals(0, solicitacaos3.size());
	}

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

		Solicitacao solicitacao = getEntity();
		solicitacao.setEncerrada(false);
		solicitacao.setSuspensa(true);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null);

		assertEquals(1, solicitacaos.size());
	}
	
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
		
		Solicitacao solicitacao = getEntity();
		solicitacao.setEncerrada(false);
		solicitacao.setSuspensa(true);
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setEncerrada(false);
		solicitacao2.setSuspensa(true);
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setSolicitante(solicitante);
		solicitacao2.setStatus(StatusAprovacaoSolicitacao.REPROVADO);
		solicitacaoDao.save(solicitacao2);
		
		assertEquals(2, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, 'T', null).size());
		assertEquals(1, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, StatusAprovacaoSolicitacao.APROVADO, null).size());
		assertEquals(1, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, StatusAprovacaoSolicitacao.REPROVADO, null).size());
		assertEquals(0, solicitacaoDao.findAllByVisualizacao(1, 10,'S', empresa.getId(), solicitante, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, null, StatusAprovacaoSolicitacao.ANALISE, null).size());
	}

	public void testFindSolicitacaoList()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Solicitacao solicitacao = getEntity();
		solicitacao.setEncerrada(true);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao.setSuspensa(true);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findSolicitacaoList(empresa.getId(), true, StatusAprovacaoSolicitacao.APROVADO, true);

		assertEquals(1, solicitacaos.size());
	}

	public void testGetValor()
	{
		
		Solicitacao solicitacao = getEntity();
		solicitacaoDao.save(solicitacao);

		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidato1.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaa");
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

	public void testFindByIdProjectionAreaFaixaSalarial()
	{
		Solicitacao solicitacao = getEntity();
		solicitacao = solicitacaoDao.save(solicitacao);

		Solicitacao solicitacaoRetorno = solicitacaoDao.findByIdProjectionAreaFaixaSalarial(solicitacao.getId());

		assertEquals(solicitacao, solicitacaoRetorno);
	}

	public void testFindByIdProjection()
	{
		Solicitacao solicitacao = getEntity();
		solicitacao = solicitacaoDao.save(solicitacao);

		Solicitacao solicitacaoRetorno = solicitacaoDao.findByIdProjection(solicitacao.getId());

		assertEquals(solicitacao, solicitacaoRetorno);
	}

	public void testFindByIdProjectionForUpdate()
	{
		Usuario liberador = UsuarioFactory.getEntity();

		Solicitacao s1 = getEntity();
		s1.setLiberador(usuarioDao.save(liberador));
		s1.setHorarioComercial("8h às 18h");
		solicitacaoDao.save(s1);

		Solicitacao solicitacao = solicitacaoDao.findByIdProjectionForUpdate(s1.getId());

		assertEquals(s1, solicitacao);
		assertEquals(liberador, solicitacao.getLiberador());
	}

	public void testUpdateEncerraSolicitacao()
	{
		Date dataEncerramento = new Date();
		Solicitacao s1 = getEntity();
		s1.setDataEncerramento(null);
		s1.setEncerrada(false);
		s1 = solicitacaoDao.save(s1);

		solicitacaoDao.updateEncerraSolicitacao(true, dataEncerramento, s1.getId(), null);
		Solicitacao solicitacao = solicitacaoDao.findByIdProjectionForUpdate(s1.getId());

		assertEquals(s1, solicitacao);
		assertEquals(true, solicitacao.isEncerrada());
		assertNotNull(solicitacao.getDataEncerramento());

		solicitacaoDao.updateEncerraSolicitacao(false, null, s1.getId(), null);
		solicitacao = solicitacaoDao.findByIdProjectionForUpdate(s1.getId());

		assertEquals(s1, solicitacao);
		assertEquals(false, solicitacao.isEncerrada());
		assertNull(solicitacao.getDataEncerramento());
	}

	public void testUpdateSuspendeSolicitacao()
	{
		Solicitacao s1 = getEntity();
		s1.setSuspensa(false);
		s1 = solicitacaoDao.save(s1);

		solicitacaoDao.updateSuspendeSolicitacao(true, "suspender",  s1.getId());
		Solicitacao solicitacao = solicitacaoDao.findByIdProjectionForUpdate(s1.getId());

		assertEquals(s1, solicitacao);
		assertEquals(true, solicitacao.isSuspensa());

		solicitacaoDao.updateSuspendeSolicitacao(false, "liberar", s1.getId());
		solicitacao = solicitacaoDao.findByIdProjectionForUpdate(s1.getId());

		assertEquals(s1, solicitacao);
		assertEquals(false, solicitacao.isSuspensa());
	}

	public void testUpdateStatusSolicitacao()
	{
		Solicitacao solicitacaoSalva = getEntity();
		solicitacaoSalva.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoDao.save(solicitacaoSalva);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Solicitacao solicitacaoEnviada = getEntity();
		solicitacaoEnviada.setId(solicitacaoSalva.getId());
		solicitacaoEnviada.setStatus(StatusAprovacaoSolicitacao.REPROVADO);
		solicitacaoEnviada.setObservacaoLiberador("anulada");
		solicitacaoEnviada.setLiberador(usuario);
		
		solicitacaoDao.updateStatusSolicitacao(solicitacaoEnviada);
		Solicitacao solicitacao = solicitacaoDao.findByIdProjectionForUpdate(solicitacaoSalva.getId());
		
		assertEquals(StatusAprovacaoSolicitacao.REPROVADO, solicitacao.getStatus());
		assertEquals(solicitacaoEnviada.getObservacaoLiberador(), solicitacao.getObservacaoLiberador());
		assertEquals(usuario.getId(), solicitacao.getLiberador().getId());
	}
	
	public void testMigrarBairro()
	{
		Bairro bairro = new Bairro();
		bairro.setNome("velho");
		bairroDao.save(bairro);

		Bairro bairroDestino = new Bairro();
		bairroDestino.setNome("novo");
		bairroDao.save(bairroDestino);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);
		
		Solicitacao s1 = getEntity();
		s1.setBairros(bairros);
		s1 = solicitacaoDao.save(s1);
		
		solicitacaoDao.migrarBairro(bairro.getId(), bairroDestino.getId());
	}
	
	public void testGetIndicadorQtdVagas()
	{
		Date data = Calendar.getInstance().getTime();
		Date dataEncerramento = Calendar.getInstance().getTime();
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Solicitacao solicitacao = getEntity();
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setData(data);
		solicitacao.setDataEncerramento(dataEncerramento);
		solicitacao.setEmpresa(empresa);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setQuantidade(3);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setAreaOrganizacional(areaOrganizacional);
		solicitacao2.setEstabelecimento(estabelecimento);
		solicitacao2.setData(data);
		solicitacao2.setDataEncerramento(dataEncerramento);
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setFaixaSalarial(faixaSalarial);
		solicitacao2.setQuantidade(5);
		solicitacaoDao.save(solicitacao2);
		
		Collection<Long> areasOrganizacionais = new ArrayList<Long>();
		Collection<Long> estabelecimentos = new ArrayList<Long>();
		
		areasOrganizacionais.add(areaOrganizacional.getId());
		estabelecimentos.add(estabelecimento.getId());
		Long[] solicitacaoIds = new Long[]{solicitacao2.getId()};
		
		List<IndicadorDuracaoPreenchimentoVaga> resultadoSemSolicitacao = solicitacaoDao.getIndicadorQtdVagas(data, data, areasOrganizacionais, estabelecimentos, null);
		List<IndicadorDuracaoPreenchimentoVaga> resultadoComSolicitacao = solicitacaoDao.getIndicadorQtdVagas(data, data, areasOrganizacionais, estabelecimentos, solicitacaoIds);
		
		IndicadorDuracaoPreenchimentoVaga indicadorSemSolicitacao = resultadoSemSolicitacao.get(0);
		IndicadorDuracaoPreenchimentoVaga indicadorComSolicitacao = resultadoComSolicitacao.get(0);
		
		assertEquals("Sem solicitação especificada", 8, indicadorSemSolicitacao.getQtdVagas().intValue());
		assertEquals("Com solicitação especificada", 5, indicadorComSolicitacao.getQtdVagas().intValue());
	}
	
	public void testGetIndicadorMediaDiasPreenchimentoVagas()
	{
		Date data = DateUtil.criarDataMesAno(1, 3, 2010);
		Date dataEncerramento = DateUtil.criarDataMesAno(15, 3, 2010);
		
		Date dataPausa = DateUtil.criarDataMesAno(7, 3, 2010);
		Date dataReinicio = DateUtil.criarDataMesAno(10, 3, 2010);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Solicitacao solicitacao = getEntity();
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setData(data);
		solicitacao.setDataEncerramento(dataEncerramento);
		solicitacao.setEmpresa(empresa);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setQuantidade(1);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacao2 = getEntity();
		solicitacao2.setAreaOrganizacional(areaOrganizacional);
		solicitacao2.setEstabelecimento(estabelecimento);
		solicitacao2.setData(data);
		solicitacao2.setDataEncerramento(dataEncerramento);
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setFaixaSalarial(faixaSalarial);
		solicitacao2.setQuantidade(1);
		solicitacaoDao.save(solicitacao2);
		
		Solicitacao solicitacaoComPausa = getEntity();
		solicitacaoComPausa.setAreaOrganizacional(areaOrganizacional);
		solicitacaoComPausa.setEstabelecimento(estabelecimento);
		solicitacaoComPausa.setData(data);
		solicitacaoComPausa.setDataEncerramento(dataEncerramento);
		solicitacaoComPausa.setEmpresa(empresa);
		solicitacaoComPausa.setFaixaSalarial(faixaSalarial);
		solicitacaoComPausa.setQuantidade(1);
		solicitacaoDao.save(solicitacaoComPausa);
		
		PausaPreenchimentoVagas pausaPreenchimentoVagas = new PausaPreenchimentoVagas();
		pausaPreenchimentoVagas.setDataReinicio(dataReinicio);
		pausaPreenchimentoVagas.setDataPausa(dataPausa);
		pausaPreenchimentoVagas.setSolicitacao(solicitacaoComPausa);
		pausaPreenchimentoVagasDao.save(pausaPreenchimentoVagas);
		
		// contratado 6 dias após início da solicitação
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(7, 3, 2010));
		colaborador.setSolicitacao(solicitacao);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador hc = HistoricoColaboradorFactory.getEntity();
		hc.setColaborador(colaborador);
		hc.setData(data);
		historicoColaboradorDao.save(hc);
		
		// contratado 14 dias após início da solicitação
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(15, 3, 2010));
		colaborador2.setSolicitacao(solicitacao);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(colaborador2);
		hc2.setData(data);
		historicoColaboradorDao.save(hc2);

		// Aguardando confirmação
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setDataAdmissao(DateUtil.criarDataMesAno(15, 3, 2010));
		colaborador3.setSolicitacao(solicitacao);
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador hc3 = HistoricoColaboradorFactory.getEntity();
		hc3.setColaborador(colaborador3);
		hc3.setData(data);
		hc3.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(hc3);
		
		Collection<Long> areasIds = Arrays.asList(areaOrganizacional.getId());
		Collection<Long> estabelecimentosIds = Arrays.asList(estabelecimento.getId());
		Long[] solicitacaoIds = new Long[]{solicitacao2.getId()};
		Long[] solicitacaoIdComPausa = new Long[]{solicitacaoComPausa.getId()};
		
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresSemSolicitacao = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, null, null );
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresComSolicitacao = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, solicitacaoIds, null );
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresComPausa = solicitacaoDao.getIndicadorMediaDiasPreenchimentoVagas(dataEncerramento, dataEncerramento, areasIds, estabelecimentosIds, solicitacaoIdComPausa, null );
		
		assertEquals(1, indicadoresSemSolicitacao.size());
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga = (IndicadorDuracaoPreenchimentoVaga) indicadoresSemSolicitacao.toArray()[0];
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVagaComSolicitacao = (IndicadorDuracaoPreenchimentoVaga) indicadoresComSolicitacao.toArray()[0];
		IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVagaComPausa = (IndicadorDuracaoPreenchimentoVaga) indicadoresComPausa.toArray()[0];
		
		assertEquals("deve retornar a média de dias", 11.0, indicadorDuracaoPreenchimentoVaga.getMediaDias());
		assertEquals("deve retornar qtd contratados sem solicitação especificada", 2, indicadorDuracaoPreenchimentoVaga.getQtdContratados().intValue());
		assertEquals("deve retornar qtd contratados com solicitação especificada", 0, indicadorDuracaoPreenchimentoVagaComSolicitacao.getQtdContratados().intValue());
		assertEquals("deve retornar qtd contratados com solicitação especificada (com pausa)", 11.0, indicadorDuracaoPreenchimentoVagaComPausa.getMediaDias());
	}
	
	public void testGetIndicadorQtdCandidatos()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao = motivoSolicitacaoDao.save(motivoSolicitacao);

		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setFaixaSalarial(faixaSalarial);
		solicitacao1.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao1.setAreaOrganizacional(areaOrganizacional);
		solicitacao1.setEstabelecimento(estabelecimento);
		solicitacao1.setData(DateUtil.criarDataMesAno(05, 10, 1945));
		solicitacao1.setDataEncerramento(DateUtil.criarDataMesAno(06, 10, 1945));
		solicitacao1 = solicitacaoDao.save(solicitacao1);

		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setFaixaSalarial(faixaSalarial);
		solicitacao2.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao2.setAreaOrganizacional(areaOrganizacional);
		solicitacao2.setEstabelecimento(estabelecimento);
		solicitacao2.setData(DateUtil.criarDataMesAno(05, 10, 1945));
		solicitacao2.setDataEncerramento(DateUtil.criarDataMesAno(06, 10, 1945));
		solicitacao2 = solicitacaoDao.save(solicitacao2);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao1);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Date dataDe = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataAte = DateUtil.criarDataMesAno(01, 12, 1945);

		Collection<Long> estabelecimentos = new ArrayList<Long>();
		estabelecimentos.add(estabelecimento.getId());
		Collection<Long> areasOrganizacionais = new ArrayList<Long>();
		areasOrganizacionais.add(areaOrganizacional.getId());
		Long[] solicitacaoIds = new Long[]{solicitacao2.getId()};

		List<IndicadorDuracaoPreenchimentoVaga> retorno1 = solicitacaoDao.getIndicadorQtdCandidatos(dataDe, dataAte, areasOrganizacionais, estabelecimentos, null);
		List<IndicadorDuracaoPreenchimentoVaga> retorno2 = solicitacaoDao.getIndicadorQtdCandidatos(dataDe, dataAte, areasOrganizacionais, estabelecimentos, solicitacaoIds);

		assertEquals("Sem solicitação especificada", 1, retorno1.size());
		assertEquals("Sem solicitação especificada", 1, retorno1.get(0).getQtdCandidatos().intValue());
		assertEquals("Com solicitação especificada", 0, retorno2.size());
	}
	
	public void testFindMotivosPreenchimentoSolicitacao()
	{
		Date dataDe = DateUtil.criarDataMesAno(1, 9, 2010);
		Date dataAte = DateUtil.criarDataMesAno(1, 9, 2011);
		Date dataEntre = DateUtil.criarDataMesAno(1, 8, 2011);
		Date dataFutura = DateUtil.criarDataMesAno(1, 12, 2011);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Collection<Long> areasOrganizacionais = new ArrayList<Long>();
		areasOrganizacionais.add(areaOrganizacional.getId());

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Collection<Long> estabelecimentos = new ArrayList<Long>();
		estabelecimentos.add(estabelecimento.getId());

		Cargo motorista = CargoFactory.getEntity();
		motorista.setNome("motorista");
		motorista = cargoDao.save(motorista);
		
		Cargo cobrador = CargoFactory.getEntity();
		cobrador.setNome("cobrador");
		cobrador = cargoDao.save(cobrador);

		Cargo fiscal = CargoFactory.getEntity();
		fiscal.setNome("fiscal");
		fiscal = cargoDao.save(fiscal);

		FaixaSalarial faixaMotorista = FaixaSalarialFactory.getEntity();
		faixaMotorista.setCargo(motorista);
		faixaMotorista = faixaSalarialDao.save(faixaMotorista);

		FaixaSalarial faixaCobrador = FaixaSalarialFactory.getEntity();
		faixaCobrador.setCargo(cobrador);
		faixaCobrador = faixaSalarialDao.save(faixaCobrador);

		FaixaSalarial faixaFiscal = FaixaSalarialFactory.getEntity();
		faixaFiscal.setCargo(fiscal);
		faixaFiscal = faixaSalarialDao.save(faixaFiscal);

		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao = motivoSolicitacaoDao.save(motivoSolicitacao);

		Solicitacao solicitacaoAberta = SolicitacaoFactory.getSolicitacao();
		solicitacaoAberta.setEmpresa(empresa);
		solicitacaoAberta.setEstabelecimento(estabelecimento);
		solicitacaoAberta.setData(dataDe);
		solicitacaoAberta.setMotivoSolicitacao(motivoSolicitacao);
		solicitacaoAberta.setFaixaSalarial(faixaMotorista);
		solicitacaoAberta.setAreaOrganizacional(areaOrganizacional);
		solicitacaoAberta = solicitacaoDao.save(solicitacaoAberta);

		Solicitacao solicitacaoEncerrada = SolicitacaoFactory.getSolicitacao();
		solicitacaoEncerrada.setEmpresa(empresa);
		solicitacaoEncerrada.setEstabelecimento(estabelecimento);
		solicitacaoEncerrada.setData(dataDe);
		solicitacaoEncerrada.setDataEncerramento(dataEntre);
		solicitacaoEncerrada.setMotivoSolicitacao(motivoSolicitacao);
		solicitacaoEncerrada.setFaixaSalarial(faixaCobrador);
		solicitacaoEncerrada.setAreaOrganizacional(areaOrganizacional);
		solicitacaoEncerrada = solicitacaoDao.save(solicitacaoEncerrada);

		Solicitacao solicitacaoEncerradaFuturo = SolicitacaoFactory.getSolicitacao();
		solicitacaoEncerradaFuturo.setEmpresa(empresa);
		solicitacaoEncerradaFuturo.setEstabelecimento(estabelecimento);
		solicitacaoEncerradaFuturo.setData(dataDe);
		solicitacaoEncerradaFuturo.setDataEncerramento(dataFutura);
		solicitacaoEncerradaFuturo.setMotivoSolicitacao(motivoSolicitacao);
		solicitacaoEncerradaFuturo.setFaixaSalarial(faixaFiscal);
		solicitacaoEncerradaFuturo.setAreaOrganizacional(areaOrganizacional);
		solicitacaoEncerradaFuturo = solicitacaoDao.save(solicitacaoEncerradaFuturo);

		List<IndicadorDuracaoPreenchimentoVaga> todas = solicitacaoDao.getIndicadorMotivosSolicitacao(dataDe, dataFutura, areasOrganizacionais, estabelecimentos, empresa.getId(), StatusSolicitacao.TODAS, 'S', false);
		assertEquals(3, todas.size());

		List<IndicadorDuracaoPreenchimentoVaga> abertas = solicitacaoDao.getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresa.getId(), StatusSolicitacao.ABERTA, 'S', false);
		assertEquals(1, abertas.size());

		List<IndicadorDuracaoPreenchimentoVaga> encerradas = solicitacaoDao.getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresa.getId(), StatusSolicitacao.ENCERRADA, 'S', false);
		assertEquals(1, encerradas.size());
	}
	
	public void testFindAllByCandidato()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2 = solicitacaoDao.save(solicitacao2);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);

		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setCandidato(candidato);
		candidatoSolicitacao2.setSolicitacao(solicitacao2);
		candidatoSolicitacao2 = candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		Collection<Solicitacao> solicitacoes = solicitacaoDao.findAllByCandidato(candidato.getId());
		assertEquals(2, solicitacoes.size());
	}

	public void testFindByEmpresaEstabelecimentosAreas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setDescricao("Solicitação 1");
		solicitacao1.setEmpresa(empresa);
		solicitacao1.setEstabelecimento(estabelecimento1);
		solicitacao1.setAreaOrganizacional(areaOrganizacional1);
		solicitacao1 = solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setDescricao("Solicitação 2");
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setEstabelecimento(estabelecimento2);
		solicitacao2.setAreaOrganizacional(areaOrganizacional2);
		solicitacao2 = solicitacaoDao.save(solicitacao2);
		
		Collection<Solicitacao> solicitacoes = solicitacaoDao.findByEmpresaEstabelecimentosAreas(empresa.getId(), new Long[] {estabelecimento1.getId()}, new Long[] {areaOrganizacional1.getId()});
		String descricao1 = ((Solicitacao)solicitacoes.toArray()[0]).getDescricao();
		
		assertEquals(1, solicitacoes.size());
		assertEquals(solicitacao1.getDescricao(), descricao1);
	}
	
	public void testGetNomesColabSubstituidosSolicitacaoEncerrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setColaboradorSubstituido("abcd|;ANTONIO EUFRASIO DE MENEZES");
		solicitacao1.setEmpresa(empresa);
		solicitacao1.setEncerrada(true);
		solicitacao1 = solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setColaboradorSubstituido("abcd");
		solicitacao2.setEmpresa(empresa);
		solicitacao2.setEncerrada(true);
		solicitacao2 = solicitacaoDao.save(solicitacao2);
		
		Solicitacao solicitacaoNaoEcerrada = SolicitacaoFactory.getSolicitacao();
		solicitacaoNaoEcerrada.setColaboradorSubstituido("josé");
		solicitacaoNaoEcerrada.setEmpresa(empresa);
		solicitacaoNaoEcerrada.setEncerrada(false);
		solicitacaoNaoEcerrada = solicitacaoDao.save(solicitacaoNaoEcerrada);
		
		Collection<Solicitacao> nomesColabSubstituidos = solicitacaoDao.getNomesColabSubstituidosSolicitacaoEncerrada(empresa.getId());
		
		assertEquals(2, nomesColabSubstituidos.size());
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setBairroDao(BairroDao bairroDao)
	{
		this.bairroDao = bairroDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setMotivoSolicitacaoDao(MotivoSolicitacaoDao motivoSolicitacaoDao) {
		this.motivoSolicitacaoDao = motivoSolicitacaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao) {
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
	
	public void setPausaPreenchimentoVagasDao(PausaPreenchimentoVagasDao pausaPreenchimentoVagasDao){
		this.pausaPreenchimentoVagasDao = pausaPreenchimentoVagasDao;
	}

}
