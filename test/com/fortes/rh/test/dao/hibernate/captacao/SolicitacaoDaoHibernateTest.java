package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
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
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
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
		
		Solicitacao solicitacao1 =SolicitacaoFactory.getSolicitacao(empresa, faixaSalarial1, false, false, StatusAprovacaoSolicitacao.APROVADO, 5,hoje);
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao(empresa, faixaSalarial2, false, false, StatusAprovacaoSolicitacao.APROVADO, 20, hoje);
		solicitacaoDao.save(solicitacao2);
		
		Solicitacao solicitacaoTesteEstabelecimento1 = SolicitacaoFactory.getSolicitacao(empresa, estabelecimentoFora, faixaSalarial2, false, false, StatusAprovacaoSolicitacao.APROVADO, 2, hoje);
		solicitacaoDao.save(solicitacaoTesteEstabelecimento1);
		
		Solicitacao solicitacaoTesteEstabelecimento2 = SolicitacaoFactory.getSolicitacao(empresa, estabelecimento, faixaSalarial1, false, false, StatusAprovacaoSolicitacao.APROVADO, 4, hoje);
		solicitacaoDao.save(solicitacaoTesteEstabelecimento2);
		
		Solicitacao solicitacaoTesteArea1 = SolicitacaoFactory.getSolicitacao(empresa, areaOrganizacional, faixaSalarial1, false, false, StatusAprovacaoSolicitacao.APROVADO, 6, hoje);
		solicitacaoDao.save(solicitacaoTesteArea1);
		
		Solicitacao solicitacaoTesteArea2 = SolicitacaoFactory.getSolicitacao(empresa, areaOrganizacionalFora, faixaSalarial2, false, false, StatusAprovacaoSolicitacao.APROVADO, 8, hoje);
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

	public void testFindSolicitacaoList()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setEncerrada(true);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao.setSuspensa(true);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		Collection<Solicitacao> solicitacaos = solicitacaoDao.findSolicitacaoList(empresa.getId(), true, StatusAprovacaoSolicitacao.APROVADO, true);

		assertEquals(1, solicitacaos.size());
	}

	public void testFindByIdProjectionAreaFaixaSalarial()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		Solicitacao solicitacaoRetorno = solicitacaoDao.findByIdProjectionAreaFaixaSalarial(solicitacao.getId());

		assertEquals(solicitacao, solicitacaoRetorno);
	}

	public void testFindByIdProjection()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		Solicitacao solicitacaoRetorno = solicitacaoDao.findByIdProjection(solicitacao.getId());

		assertEquals(solicitacao, solicitacaoRetorno);
	}

	public void testFindByIdProjectionForUpdate()
	{
		Usuario liberador = UsuarioFactory.getEntity();

		Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
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
		Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
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
		Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
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
		Solicitacao solicitacaoSalva = SolicitacaoFactory.getSolicitacao();
		solicitacaoSalva.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacaoDao.save(solicitacaoSalva);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Solicitacao solicitacaoEnviada = SolicitacaoFactory.getSolicitacao();
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
		
		Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
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
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setData(data);
		solicitacao.setDataEncerramento(dataEncerramento);
		solicitacao.setEmpresa(empresa);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setQuantidade(3);
		solicitacaoDao.save(solicitacao);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
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
	
	public void testCalculaIndicadorVagasPreenchidasNoPrazoRetornoZero()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Solicitacao solicitacaoComPrevisaoDeEncerramento = SolicitacaoFactory.getSolicitacao(empresa, new Date(), DateUtil.incrementaMes(new Date(), 1), 2);
		solicitacaoDao.save(solicitacaoComPrevisaoDeEncerramento);
		
		Solicitacao solicitacaoSemPrevisaoDeEncerramento = SolicitacaoFactory.getSolicitacao(empresa, new Date(), null, 1);
		solicitacaoDao.save(solicitacaoSemPrevisaoDeEncerramento);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		CandidatoSolicitacao candidatoSolicitacaoSolicitacaoSemPrevisaoDeEncerramento = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacaoSemPrevisaoDeEncerramento, new Date());
		candidatoSolicitacaoDao.save(candidatoSolicitacaoSolicitacaoSemPrevisaoDeEncerramento);
		
		Collection<Solicitacao> solicitacoes = solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(empresa.getId(), null, null, null, new Date(), DateUtil.incrementaMes(new Date(), 1));
		
		assertEquals(0.0, calculaPercentualVagasPreenchidaNoPrazo(solicitacoes));
	}
	
	public void testCalculaIndicadorVagasPreenchidaNoPrazoRetorno50PorCento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Solicitacao solicitacaoComPrevisaoDeEncerramento = SolicitacaoFactory.getSolicitacao(empresa, DateUtil.criarDataMesAno(new Date()), DateUtil.incrementaMes(new Date(), 1), 2);
		solicitacaoDao.save(solicitacaoComPrevisaoDeEncerramento);
		
		Solicitacao solicitacaoSemPrevisaoDeEncerramento = SolicitacaoFactory.getSolicitacao(empresa, DateUtil.criarDataMesAno(new Date()), null, 1);
		solicitacaoDao.save(solicitacaoSemPrevisaoDeEncerramento);
	
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		CandidatoSolicitacao candidatoSolicitacaoSolicitacaoSemPrevisaoDeEncerramento = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacaoSemPrevisaoDeEncerramento, DateUtil.criarDataMesAno(new Date()));
		candidatoSolicitacaoDao.save(candidatoSolicitacaoSolicitacaoSemPrevisaoDeEncerramento);
		
		CandidatoSolicitacao candidatoSolicitacaoSolicitacaoComPrevisaoDeEncerramento = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacaoComPrevisaoDeEncerramento, DateUtil.criarDataMesAno(new Date()));
		candidatoSolicitacaoDao.save(candidatoSolicitacaoSolicitacaoComPrevisaoDeEncerramento);
		
		candidatoSolicitacaoDao.getHibernateTemplateByGenericDao().flush();
		Collection<Solicitacao> solicitacoes =  solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(empresa.getId(), new Long[]{}, new Long[]{}, new Long[]{}, DateUtil.criarDataMesAno(new Date()), DateUtil.incrementaMes(new Date(), 1));
		
		assertEquals(50.0, calculaPercentualVagasPreenchidaNoPrazo(solicitacoes));
	}
	
	public void testCalculaIndicadorVagasPreenchidaNoPrazoRetorno100PorCento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("Estabelecimento", empresa);
		estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Area", true, empresa);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Solicitacao solicitacaoComPrevisaoDeEncerramento = SolicitacaoFactory.getSolicitacao(empresa, estabelecimento, areaOrganizacional, DateUtil.criarDataMesAno(new Date()), DateUtil.incrementaMes(new Date(), 1), 2);
		solicitacaoDao.save(solicitacaoComPrevisaoDeEncerramento);
		
		Solicitacao solicitacaoSemPrevisaoDeEncerramento = SolicitacaoFactory.getSolicitacao(empresa, estabelecimento, areaOrganizacional, DateUtil.criarDataMesAno(new Date()), null, 1);
		solicitacaoDao.save(solicitacaoSemPrevisaoDeEncerramento);
	
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		CandidatoSolicitacao candidatoSolicitacaoSolicitacaoSemPrevisaoDeEncerramento = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacaoSemPrevisaoDeEncerramento, DateUtil.criarDataMesAno(new Date()));
		candidatoSolicitacaoDao.save(candidatoSolicitacaoSolicitacaoSemPrevisaoDeEncerramento);
		
		CandidatoSolicitacao candidatoSolicitacaoSolicitacaoComPrevisaoDeEncerramento1 = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacaoComPrevisaoDeEncerramento, DateUtil.criarDataMesAno(new Date()));
		candidatoSolicitacaoDao.save(candidatoSolicitacaoSolicitacaoComPrevisaoDeEncerramento1);
		
		CandidatoSolicitacao candidatoSolicitacaoSolicitacaoComPrevisaoDeEncerramento2 = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacaoComPrevisaoDeEncerramento, DateUtil.incrementaMes(new Date(), 1));
		candidatoSolicitacaoDao.save(candidatoSolicitacaoSolicitacaoComPrevisaoDeEncerramento2);
		
		candidatoSolicitacaoDao.getHibernateTemplateByGenericDao().flush();
		
		Long[] establementosIds = new Long[]{estabelecimento.getId()};
		Long[] areasIds = new Long[]{areaOrganizacional.getId()};
		Long[] solicitacoesIds = new Long[]{solicitacaoComPrevisaoDeEncerramento.getId(), solicitacaoSemPrevisaoDeEncerramento.getId()};
	
		Collection<Solicitacao> solicitacoes = solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(empresa.getId(), establementosIds, areasIds, solicitacoesIds, DateUtil.criarDataMesAno(new Date()), DateUtil.incrementaMes(new Date(), 1));
		
		assertEquals(100.0, calculaPercentualVagasPreenchidaNoPrazo(solicitacoes));
	}

	private Double calculaPercentualVagasPreenchidaNoPrazo(Collection<Solicitacao> solicitacoes) {
		Double resultado = 0.0;
		
		for (Solicitacao solicitacao : solicitacoes) {
			if(solicitacao.getQtdVagasPreenchidas() >= solicitacao.getQuantidade())
				resultado += 1.0;
			else
				resultado += new Double(solicitacao.getQtdVagasPreenchidas()) / solicitacao.getQuantidade(); 
		}
		
		return resultado * 100.0;
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
