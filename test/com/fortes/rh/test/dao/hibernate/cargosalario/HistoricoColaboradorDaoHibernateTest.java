package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.model.AbstractModel;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.MovimentacaoAC;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ContatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.model.geral.EnderecoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("deprecation")
public class HistoricoColaboradorDaoHibernateTest extends GenericDaoHibernateTest<HistoricoColaborador>
{
	private HistoricoColaboradorDao historicoColaboradorDao;
	private ColaboradorDao colaboradorDao;
	private EstabelecimentoDao estabelecimentoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private EmpresaDao empresaDao;
	private FaixaSalarialDao faixaSalarialDao;
	private CargoDao cargoDao;
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private ReajusteColaboradorDao reajusteColaboradorDao;
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	private AmbienteDao ambienteDao;
	private FuncaoDao funcaoDao;
	private IndiceDao indiceDao;
	private IndiceHistoricoDao indiceHistoricoDao;
	private GrupoACDao grupoACDao;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private CandidatoDao candidatoDao;
	private SolicitacaoDao solicitacaoDao;
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	
	Empresa empresa;
	Colaborador colaborador;
	HistoricoColaborador historicoColaborador;

	public HistoricoColaborador getEntity()
	{
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();

		historicoColaborador.setId(null);
		historicoColaborador.setData(new Date());
		historicoColaborador.setMotivo("p");
		historicoColaborador.setSalario(1D);
		historicoColaborador.setColaborador(null);

		return historicoColaborador;
	}

	public GenericDao<HistoricoColaborador> getGenericDao()
	{
		return historicoColaboradorDao;
	}

	public void testGetHistoricoAnterior()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador h0 = HistoricoColaboradorFactory.getEntity();
		h0.setData(DateUtil.criarAnoMesDia(2008, 03, 1));
		h0.setColaborador(colaborador);
		h0 = historicoColaboradorDao.save(h0);

		HistoricoColaborador h1 = HistoricoColaboradorFactory.getEntity();
		h1.setData(DateUtil.criarAnoMesDia(2008, 04, 1));
		h1.setColaborador(colaborador);
		h1 = historicoColaboradorDao.save(h1);

		HistoricoColaborador h2 = HistoricoColaboradorFactory.getEntity();
		h2.setData(DateUtil.criarAnoMesDia(2008, 06, 1));
		h2.setColaborador(colaborador);
		h2 = historicoColaboradorDao.save(h2);

		HistoricoColaborador h3 = HistoricoColaboradorFactory.getEntity();
		h3.setData(DateUtil.criarAnoMesDia(2008, 07, 1));
		h3.setColaborador(colaborador);
		h3 = historicoColaboradorDao.save(h3);

		HistoricoColaborador hRetorno = historicoColaboradorDao.getHistoricoAnterior(h1);
		assertEquals(h0.getId(), hRetorno.getId());

		HistoricoColaborador hRetorno2 = historicoColaboradorDao.getHistoricoAnterior(h0);
		assertEquals(null, hRetorno2);
	}

	public void testFindByColaboradorProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(1, historicoColaboradorDao.findByColaboradorProjection(colaborador.getId(), null).size());
	}
	
	public void testFindByColaboradorProjectionHistoricoConfirmado()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico.setStatus(StatusRetornoAC.CANCELADO);
		historicoColaboradorDao.save(historico);
		
		HistoricoColaborador historico2 = HistoricoColaboradorFactory.getEntity();
		historico2.setData(DateUtil.criarDataMesAno(1, 1, 2006));
		historico2.setColaborador(colaborador);
		historico2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historico2);

		assertEquals(2, historicoColaboradorDao.findByColaboradorProjection(colaborador.getId(), null).size());
		assertEquals(1, historicoColaboradorDao.findByColaboradorProjection(colaborador.getId(), StatusRetornoAC.CONFIRMADO).size());
	}
	
	public void testGetPromocoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador primeiro = HistoricoColaboradorFactory.getEntity();
		primeiro.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		primeiro.setColaborador(colaborador);
		primeiro.setData(DateUtil.criarDataMesAno(01, 02, 1999));
		primeiro.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(primeiro);
		
		HistoricoColaborador segundo = HistoricoColaboradorFactory.getEntity();
		segundo.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
		segundo.setColaborador(colaborador);
		segundo.setData(DateUtil.criarDataMesAno(02, 05, 2001));
		segundo.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(segundo);
		
		HistoricoColaborador terceiro = HistoricoColaboradorFactory.getEntity();
		terceiro.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		terceiro.setColaborador(colaborador);
		terceiro.setData(DateUtil.criarDataMesAno(02, 05, 2005));
		terceiro.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(terceiro);
		
		Collection<SituacaoColaborador> situacoes = historicoColaboradorDao.getPromocoes(null, null, DateUtil.criarDataMesAno(01, 01, 1999), DateUtil.criarDataMesAno(03, 05, 2005), empresa.getId());
		assertEquals(2, situacoes.size());
	}
	
	public void testGetRelatorioPromocoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento parajana = EstabelecimentoFactory.getEntity(1L);
		parajana.setEmpresa(empresa);
		estabelecimentoDao.save(parajana);
		
		AreaOrganizacional garagem = AreaOrganizacionalFactory.getEntity(1L);
		garagem.setNome("GARAGEM");
		areaOrganizacionalDao.save(garagem);
		
		AreaOrganizacional lavajato = AreaOrganizacionalFactory.getEntity(2L);
		garagem.setNome("LAVAJATO");
		areaOrganizacionalDao.save(lavajato);
		
		Cargo cobrador = CargoFactory.getEntity(1L);
		cobrador.setNome("COBRADOR");
		cobrador.setEmpresa(empresa);
		cargoDao.save(cobrador);
		
		FaixaSalarial faixaUmCobrador = FaixaSalarialFactory.getEntity(1L);
		faixaUmCobrador.setNome("I");
		faixaUmCobrador.setCargo(cobrador);
		faixaSalarialDao.save(faixaUmCobrador);
		
		FaixaSalarial faixaDoisCobrador = FaixaSalarialFactory.getEntity(2L);
		faixaDoisCobrador.setNome("II");
		faixaDoisCobrador.setCargo(cobrador);
		faixaSalarialDao.save(faixaDoisCobrador);

		Cargo motorista = CargoFactory.getEntity(2L);
		motorista.setNome("MOTORISTA");
		motorista.setEmpresa(empresa);
		cargoDao.save(motorista);
		
		FaixaSalarial faixaAMotorista = FaixaSalarialFactory.getEntity(3L);
		faixaAMotorista.setNome("I");
		faixaAMotorista.setCargo(motorista);
		faixaSalarialDao.save(faixaAMotorista);
		
		Colaborador joao = ColaboradorFactory.getEntity(11L);
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity(22L);
		maria.setEmpresa(empresa);
		colaboradorDao.save(maria);
		
		HistoricoColaborador joaoCobradorFaixaUm = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(01, 02, 1998), faixaUmCobrador, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		joaoCobradorFaixaUm.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		joaoCobradorFaixaUm.setSalario(500.0);
		historicoColaboradorDao.save(joaoCobradorFaixaUm);
		
		HistoricoColaborador joaoCobradorFaixaUmAumentoSalario = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(02, 02, 2000), faixaUmCobrador, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		joaoCobradorFaixaUmAumentoSalario.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		joaoCobradorFaixaUmAumentoSalario.setSalario(600.0);
		historicoColaboradorDao.save(joaoCobradorFaixaUmAumentoSalario);

		HistoricoColaborador joaoCobradorFaixaDois = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(01, 04, 2000), faixaDoisCobrador, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		joaoCobradorFaixaDois.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		joaoCobradorFaixaDois.setSalario(500.0);
		historicoColaboradorDao.save(joaoCobradorFaixaDois);

		HistoricoColaborador joaoCobradorFaixaDoisAumentoSalario = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(02, 04, 2000), faixaDoisCobrador, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		joaoCobradorFaixaDoisAumentoSalario.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		joaoCobradorFaixaDoisAumentoSalario.setSalario(650.0);
		historicoColaboradorDao.save(joaoCobradorFaixaDoisAumentoSalario);
		
		HistoricoColaborador joaoCobradorFaixaDoisMudouArea = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(01, 05, 2000), faixaDoisCobrador, parajana, lavajato, null, null, StatusRetornoAC.CONFIRMADO);
		joaoCobradorFaixaDoisMudouArea.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		joaoCobradorFaixaDoisMudouArea.setSalario(650.0);
		historicoColaboradorDao.save(joaoCobradorFaixaDoisMudouArea);

		HistoricoColaborador joaoMotoristaFaixaA = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(06, 06, 2001), faixaAMotorista, parajana, lavajato, null, null, StatusRetornoAC.CONFIRMADO);
		joaoMotoristaFaixaA.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		joaoMotoristaFaixaA.setSalario(650.0);
		historicoColaboradorDao.save(joaoMotoristaFaixaA);
		
		
		HistoricoColaborador mariaCobradorFaixaUm = HistoricoColaboradorFactory.getEntity(maria, DateUtil.criarDataMesAno(01, 02, 2000), faixaUmCobrador, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		mariaCobradorFaixaUm.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		mariaCobradorFaixaUm.setSalario(500.0);
		historicoColaboradorDao.save(mariaCobradorFaixaUm);
		
		HistoricoColaborador mariaCobradorFaixaUmAumentoSalario = HistoricoColaboradorFactory.getEntity(maria, DateUtil.criarDataMesAno(02, 02, 2000), faixaAMotorista, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		mariaCobradorFaixaUmAumentoSalario.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		mariaCobradorFaixaUmAumentoSalario.setSalario(555.0);
		historicoColaboradorDao.save(mariaCobradorFaixaUmAumentoSalario);
		
		HistoricoColaborador mariaCobradorFaixaUmAumentoSalarioNovamente = HistoricoColaboradorFactory.getEntity(maria, DateUtil.criarDataMesAno(05, 05, 2000), faixaAMotorista, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		mariaCobradorFaixaUmAumentoSalarioNovamente.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		mariaCobradorFaixaUmAumentoSalarioNovamente.setSalario(600.0);
		historicoColaboradorDao.save(mariaCobradorFaixaUmAumentoSalarioNovamente);
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 1999);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2300);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		List<RelatorioPromocoes> relatorioPromocoes = historicoColaboradorDao.getRelatorioPromocoes(null, null, dataIni, dataFim, empresa.getId());
		assertEquals(2, relatorioPromocoes.size());
		
		assertEquals(4, relatorioPromocoes.get(0).getQtdHorizontal());
		assertEquals(0, relatorioPromocoes.get(1).getQtdHorizontal());

		assertEquals(1, relatorioPromocoes.get(0).getQtdVertical());
		assertEquals(1, relatorioPromocoes.get(1).getQtdVertical());
		
	}
	
	public void testUltimasPromocoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);

		Colaborador joao = ColaboradorFactory.getEntity();
		colaboradorDao.save(joao);
		
		Colaborador pedro = ColaboradorFactory.getEntity();
		colaboradorDao.save(pedro);
		
		HistoricoColaborador histPedro = HistoricoColaboradorFactory.getEntity();
		histPedro.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		histPedro.setColaborador(pedro);
		histPedro.setData(DateUtil.criarDataMesAno(01, 02, 2003));
		histPedro.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(histPedro);
		
		HistoricoColaborador primeiroJoao = HistoricoColaboradorFactory.getEntity();
		primeiroJoao.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		primeiroJoao.setColaborador(joao);
		primeiroJoao.setData(DateUtil.criarDataMesAno(01, 02, 1999));
		primeiroJoao.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(primeiroJoao);
		
		HistoricoColaborador segundoJoao = HistoricoColaboradorFactory.getEntity();
		segundoJoao.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		segundoJoao.setColaborador(joao);
		segundoJoao.setData(DateUtil.criarDataMesAno(02, 05, 2001));
		segundoJoao.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(segundoJoao);
		
		HistoricoColaborador terceiroJoao = HistoricoColaboradorFactory.getEntity();
		terceiroJoao.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		terceiroJoao.setColaborador(joao);
		terceiroJoao.setData(DateUtil.criarDataMesAno(02, 01, 2005));
		terceiroJoao.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(terceiroJoao);
		
		List<SituacaoColaborador> situacoes = historicoColaboradorDao.getUltimasPromocoes(null, null,  DateUtil.criarDataMesAno(02, 01, 2002), empresa.getId());
		assertEquals(2, situacoes.size());
		//ordem é importante para regra do manager
		assertEquals(primeiroJoao.getData(), situacoes.get(0).getData());
		assertEquals(segundoJoao.getData(), situacoes.get(1).getData());
	}
	
	public void testRemoveCandidatoSolicitacao()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		HistoricoColaborador historicoContratado = HistoricoColaboradorFactory.getEntity();
		historicoContratado.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoContratado.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoContratado);
		
		historicoColaboradorDao.removeVinculoCandidatoSolicitacao(candidatoSolicitacao.getId());	
		
		assertNull(historicoColaboradorDao.findByIdProjection(historicoContratado.getId()).getCandidatoSolicitacao());
	}
	
	public void testDeleteSituacaoByMovimentoSalarial()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("as32d1");
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setMovimentoSalarialId(2);
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);
		
		historicoColaboradorDao.deleteSituacaoByMovimentoSalarial(2L, empresa.getId());
		assertEquals(null, historicoColaboradorDao.findByIdProjectionMinimo(historico.getId()));
	}

	public void testFindByIdHQL()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(historico, historicoColaboradorDao.findByIdHQL(historico.getId()));
	}

	public void testFindPromocaoByColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setData(DateUtil.criarAnoMesDia(2008, 10, 12));
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(500.0);

		Indice indice = IndiceFactory.getEntity();

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setData(DateUtil.criarAnoMesDia(2008, 12, 12));
		faixaSalarialHistorico2.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico2.setIndice(indice);

		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2007, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2008, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.findPromocaoByColaborador(colaborador.getId());

		assertEquals(2, historicoColaboradores.size());
	}

	public void testFindSemDissidioByDataPercentual()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Date dataIni = DateUtil.criarDataMesAno(12, 2, 2008);
		Date dataFim = DateUtil.criarDataMesAno(2, 4, 2008);
		
		montaSaveHistoricoColaborador(DateUtil.criarDataMesAno(1, 3, 2008), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		montaSaveHistoricoColaborador(DateUtil.criarDataMesAno(1, 4, 2008), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		montaSaveHistoricoColaborador(DateUtil.criarDataMesAno(1, 5, 2008), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		
		Collection<HistoricoColaborador> retorno1 = historicoColaboradorDao.findSemDissidioByDataPercentual(dataIni, null, 2.0, empresa.getId());
		Collection<HistoricoColaborador> retorno2 = historicoColaboradorDao.findSemDissidioByDataPercentual(dataIni, dataFim, 2.0, empresa.getId());
		
		assertEquals("Sem data final", 3, retorno1.size());
		assertEquals("Com data final", 2, retorno2.size());
	}

//TODO NÃO APAGAR RELATORIO DE PROMOÇ~ES EM ESTUDO
//	public void testGetPromocoes()
//	{
//		Empresa empresa = EmpresaFactory.getEmpresa();
//		empresa = empresaDao.save(empresa);
//
//		Colaborador colaborador = ColaboradorFactory.getEntity();
//		colaborador.setEmpresa(empresa);
//		colaborador = colaboradorDao.save(colaborador);
//
//		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
//		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);
//
//		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
//		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);
//
//		HistoricoColaborador historicoColaborador = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2007, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
//		HistoricoColaborador historicoColaboradorAnterior = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2006, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
//
//		historicoColaborador.setHistoricoAnterior(historicoColaboradorAnterior);
//		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL);
//		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);
//
//		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.getPromocoes(new Long[]{areaOrganizacional1.getId()}, new Long[]{estabelecimento1.getId()}, DateUtil.criarDataMesAno(01, 02, 2007), DateUtil.criarDataMesAno(01, 02, 2008));
//
//		assertEquals(1, historicoColaboradores.size());
//	}

	public void testGetHistoricoAtual()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2007, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador2 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2008, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2020, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);

		HistoricoColaborador historicoColaboradore = historicoColaboradorDao.getHistoricoAtual(colaborador.getId(), TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);

		assertEquals(historicoColaborador2, historicoColaboradore);
	}
	
	public void testGetHistoricoContratacaoAguardando()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(10, 10, 2010));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador);

		HistoricoColaborador historicoColaboradore = historicoColaboradorDao.getHistoricoContratacaoAguardando(colaborador.getId());

		assertEquals(historicoColaborador.getId(), historicoColaboradore.getId());
	}

	public void testFindByCargosIdsComEmpresa()
	{
		Cargo cargo	= CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setNome("Pedro Paulo");
		colaborador.setMatricula("112Ae3456");
		colaborador.setDesligado(false);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Long> cargoIds = LongUtil.arrayStringToCollectionLong(new String[] {cargo.getId().toString()});

		Colaborador colaboradorBusca = ColaboradorFactory.getEntity();
		colaboradorBusca.setNome("ro pA");
		colaboradorBusca.setMatricula("2a");

		Collection<HistoricoColaborador> retorno1 = historicoColaboradorDao.findByCargosIds(1, 15, cargoIds, empresa.getId(), colaboradorBusca);
		Collection<HistoricoColaborador> retorno2 = historicoColaboradorDao.findByCargosIds(1, 15, new ArrayList<Long>(), empresa.getId(), colaboradorBusca);
		Collection<HistoricoColaborador> retorno3 = historicoColaboradorDao.findByCargosIds(1, 15, null, empresa.getId(), colaboradorBusca);

		assertEquals("cargoIds preenchido", 1, retorno1.size());
		assertEquals("cargoIds vazio", 0, retorno2.size());
		assertEquals("cargoIds nulo", 0, retorno3.size());
	}

	public void testFindByGrupoOcupacionalIds()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo	= CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDesligado(false);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Long> grupoOcupacionalIds = LongUtil.arrayStringToCollectionLong(new String[] {grupoOcupacional.getId().toString()});

		Collection<HistoricoColaborador> retorno1 = historicoColaboradorDao.findByGrupoOcupacionalIds(1, 15, grupoOcupacionalIds, empresa.getId());
		Collection<HistoricoColaborador> retorno2 = historicoColaboradorDao.findByGrupoOcupacionalIds(1, 15, new ArrayList<Long>(), empresa.getId());
		Collection<HistoricoColaborador> retorno3 = historicoColaboradorDao.findByGrupoOcupacionalIds(1, 15, null, empresa.getId());

		assertEquals("grupoOcupacionalIds preenchido", 1, retorno1.size());
		assertEquals("grupoOcupacionalIds vazio", 0, retorno2.size());
		assertEquals("grupoOcupacionalIds nulo", 0, retorno3.size());
	}

	@SuppressWarnings("unused")
	public void testGetHistoricosAtuaisByEstabelecimentoArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1 = colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2 = colaboradorDao.save(colaborador2);

		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(empresa);
		colaborador3.setNaoIntegraAc(true);
		colaborador3 = colaboradorDao.save(colaborador3);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setId(null);
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setId(null);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		Date data = DateUtil.criarAnoMesDia(2008, 03, 1);
		//Não apagar linhas com avisos
		HistoricoColaborador historicoColaborador6 = montaSaveHistoricoColaborador(data, colaborador3, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		//Salva os HistoricoColaborador com Colaborador 1
		HistoricoColaborador historicoColaborador1 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador1, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador2 = montaSaveHistoricoColaborador(data, colaborador1, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		//Não apagar linhas com avisos
		HistoricoColaborador historicoColaborador3 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2006, 03, 1), colaborador1, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);

		//Salva os HistoricoColaborador com Colaborador 2
		HistoricoColaborador historicoColaborador4 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador2, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador5 = montaSaveHistoricoColaborador(data, colaborador2, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.getHistoricosAtuaisByEstabelecimentoAreaGrupo(new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, '1', new Long[]{areaOrganizacional1.getId(), areaOrganizacional2.getId()}, null, empresa.getId(), data);

		assertEquals(2, historicoColaboradores.size());
		for (HistoricoColaborador historicoColaborador : historicoColaboradores)
		{
			assertTrue(historicoColaborador.getId().equals(historicoColaborador2.getId()) || historicoColaborador.getId().equals(historicoColaborador5.getId()));
		}
	}

	@SuppressWarnings("unused")
	public void testGetHistoricosAtuaisByEstabelecimentoGrupo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1 = colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2 = colaboradorDao.save(colaborador2);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Date data = DateUtil.criarAnoMesDia(2008, 03, 1);
		//Salva os HistoricoColaborador com Colaborador 1
		HistoricoColaborador historicoColaborador1 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador1, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador2 = montaSaveHistoricoColaborador(data, colaborador1, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador3 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2006, 03, 1), colaborador1, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);

		//Salva os HistoricoColaborador com Colaborador 2
		HistoricoColaborador historicoColaborador4 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador2, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador5 = montaSaveHistoricoColaborador(data, colaborador2, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.INDICE);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.getHistoricosAtuaisByEstabelecimentoAreaGrupo(new Long[]{estabelecimento.getId()}, '2', null, new Long[]{grupoOcupacional.getId()}, empresa.getId(), data);

		assertEquals(1, historicoColaboradores.size());
		assertTrue(((AbstractModel) historicoColaboradores.toArray()[0]).getId().equals(historicoColaborador2.getId()));
	}

	private HistoricoColaborador montaSaveHistoricoColaborador(Date data, Colaborador colaborador, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, FaixaSalarial faixaSalarial, int tipoSalario)
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(data);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setTipoSalario(tipoSalario);

		return historicoColaboradorDao.save(historicoColaborador);
	}

	public void testGetHistoricoProximo()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());
		colaborador.setEndereco(EnderecoFactory.getEntity());

		colaborador.setContato(ContatoFactory.getEntity());

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		colaborador.setAreaOrganizacional(null);
		colaborador.setDependentes(null);

		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador h0 = new HistoricoColaborador();
		h0.setId(null);
		h0.setData(DateUtil.criarAnoMesDia(2008, 03, 1));
		h0.setMotivo("p");
		h0.setSalario(1D);
		h0.setColaborador(colaborador);

		h0 = historicoColaboradorDao.save(h0);

		HistoricoColaborador h1 = new HistoricoColaborador();
		h1.setId(null);
		h1.setData(DateUtil.criarAnoMesDia(2008, 04, 1));
		h1.setMotivo("p");
		h1.setSalario(1D);
		h1.setColaborador(colaborador);

		h1 = historicoColaboradorDao.save(h1);


		HistoricoColaborador h2 = new HistoricoColaborador();
		h2.setId(null);
		h2.setData(DateUtil.criarAnoMesDia(2008, 06, 1));
		h2.setMotivo("p");
		h2.setSalario(1D);
		h2.setColaborador(colaborador);

		h2 = historicoColaboradorDao.save(h2);

		HistoricoColaborador h3 = new HistoricoColaborador();
		h3.setId(null);
		h3.setData(DateUtil.criarAnoMesDia(2008, 07, 1));
		h3.setMotivo("p");
		h3.setSalario(1D);
		h3.setColaborador(colaborador);

		historicoColaboradorDao.save(h3);

		HistoricoColaborador hRetorno = historicoColaboradorDao.getHistoricoProximo(h1);

		assertEquals(h2.getId(), hRetorno.getId());

		hRetorno = historicoColaboradorDao.getHistoricoProximo(h3);

		assertEquals(null, hRetorno);
	}


	public void testUpdateSituacaoByMovimentacao()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("0005");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC("0003");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setCodigoAC("0008");
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC("0004");
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setCodigoAC("0006");
		areaOrganizacional2.setEmpresa(empresa);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setCodigoAC("0001");
		colaborador1.setNaoIntegraAc(false);
		colaborador1 = colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setCodigoAC("0002");
		colaborador2.setNaoIntegraAc(false);
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaboradorColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorColaborador1.setData(DateUtil.criarAnoMesDia(2008, 04, 1));
		historicoColaboradorColaborador1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorColaborador1.setEstabelecimento(estabelecimento);
		historicoColaboradorColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorColaborador1.setColaborador(colaborador1);
		historicoColaboradorColaborador1 = historicoColaboradorDao.save(historicoColaboradorColaborador1);
		
		HistoricoColaborador historicoColaboradorColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorColaborador2.setEstabelecimento(estabelecimento2);
		historicoColaboradorColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorColaborador2 = historicoColaboradorDao.save(historicoColaboradorColaborador2);
		
		HistoricoColaborador historicoColaboradorColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorColaborador3.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorColaborador3.setEstabelecimento(estabelecimento);
		historicoColaboradorColaborador3.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorColaborador3 = historicoColaboradorDao.save(historicoColaboradorColaborador3);
		
		Exception exception = null;
		try {
			historicoColaboradorDao.updateSituacaoByMovimentacao(colaborador1.getCodigoAC(), (String) new MovimentacaoAC().get(MovimentacaoAC.AREA), areaOrganizacional2.getCodigoAC(), false, empresa.getId());
			historicoColaboradorDao.updateSituacaoByMovimentacao(colaborador2.getCodigoAC(), (String) new MovimentacaoAC().get(MovimentacaoAC.ESTABELECIMENTO), estabelecimento2.getCodigoAC(), true, empresa.getId());
			historicoColaboradorDao.getHibernateTemplateByGenericDao().flush();
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testSetStatus()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(true, historicoColaboradorDao.setStatus(historicoColaborador.getId(), true));

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorDao.findByIdProjectionMinimo(historicoColaborador.getId());
		assertEquals(StatusRetornoAC.CONFIRMADO, historicoColaboradorRetorno.getStatus());
	}

	public void testFindByIdProjectionMinimo()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorDao.findByIdProjectionMinimo(historicoColaborador.getId());
		assertEquals(historicoColaborador, historicoColaboradorRetorno);
	}

	public void testFindByAC()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("333AA11");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("1233FF55");
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Date data = new Date();

		HistoricoColaborador hc = HistoricoColaboradorFactory.getEntity();
		hc.setColaborador(colaborador);
		hc.setData(data);
		historicoColaboradorDao.save(hc);

		assertEquals(hc, historicoColaboradorDao.findByAC(data, colaborador.getCodigoAC(), empresa.getCodigoAC(), "XXX"));
	}

	public void testFindAtualByAC()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("333AA11");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("1233FF55");
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador hcAntigo = HistoricoColaboradorFactory.getEntity();
		hcAntigo.setColaborador(colaborador);
		hcAntigo.setData(DateUtil.criarDataMesAno(01, 01, 1998));
		historicoColaboradorDao.save(hcAntigo);

		HistoricoColaborador hcAtual = HistoricoColaboradorFactory.getEntity();
		hcAtual.setColaborador(colaborador);
		hcAtual.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(hcAtual);

		TSituacao situacao = new TSituacao();
		situacao.setData("01/02/2000");
		situacao.setEmpregadoCodigoAC(colaborador.getCodigoAC());
		situacao.setEmpresaCodigoAC(empresa.getCodigoAC());

		assertEquals(hcAtual, historicoColaboradorDao.findAtualByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), "XXX"));
	}

	public void testFindColaboradorCodigoAC()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("12345");
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals("12345", historicoColaboradorDao.findColaboradorCodigoAC(historicoColaborador.getId()));
		assertNull(historicoColaboradorDao.findColaboradorCodigoAC(21546532L));
	}

	public void testFindByColaboradorData()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(0, historicoColaboradorDao.findByColaboradorData(colaborador.getId(), DateUtil.criarDataMesAno(01, 01, 2007)).size());
		assertEquals(1, historicoColaboradorDao.findByColaboradorData(colaborador.getId(), DateUtil.criarDataMesAno(01, 01, 2009)).size());
	}

	public void testFindReajusteByHistoricoColaborador()
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(reajusteColaborador.getId(), historicoColaboradorDao.findReajusteByHistoricoColaborador(historicoColaborador.getId()));
	}
	
	public void testFindColaboradoresByTabelaReajusteData()
	{
		Colaborador colaboradorNaTabela = ColaboradorFactory.getEntity();
		colaboradorNaTabela.setNome("colaboradorNaTabela");
		colaboradorDao.save(colaboradorNaTabela);
		
		Colaborador colaboradorForaDaTabela = ColaboradorFactory.getEntity();
		colaboradorForaDaTabela.setNome("colaboradorForaDaTabela");
		colaboradorDao.save(colaboradorForaDaTabela);
		
		TabelaReajusteColaborador tabelaReajusteColaborador1 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador1.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador1);
		
		TabelaReajusteColaborador tabelaReajusteColaborador2 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador2.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador2);
		
		ReajusteColaborador reajusteColaborador1 = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador1.setColaborador(colaboradorNaTabela);
		reajusteColaborador1.setTabelaReajusteColaborador(tabelaReajusteColaborador1);
		reajusteColaboradorDao.save(reajusteColaborador1);

		ReajusteColaborador reajusteColaborador2 = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador2.setTabelaReajusteColaborador(tabelaReajusteColaborador2);
		reajusteColaboradorDao.save(reajusteColaborador2);
		
		HistoricoColaborador historicoColaboradorDaTabela = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorDaTabela.setColaborador(colaboradorNaTabela);
		historicoColaboradorDaTabela.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		historicoColaboradorDao.save(historicoColaboradorDaTabela);
		
		HistoricoColaborador historicoColaboradorForaDaTabela = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorForaDaTabela.setReajusteColaborador(reajusteColaborador2);
		historicoColaboradorForaDaTabela.setColaborador(colaboradorForaDaTabela);
		historicoColaboradorForaDaTabela.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		historicoColaboradorDao.save(historicoColaboradorForaDaTabela);
		
		Collection<HistoricoColaborador> retorno = historicoColaboradorDao.findColaboradoresByTabelaReajusteData(tabelaReajusteColaborador1.getId(), tabelaReajusteColaborador1.getData());
		assertEquals(1, retorno.size());
		assertEquals("colaboradorNaTabela", ((HistoricoColaborador)retorno.toArray()[0]).getColaborador().getNome());
	}

	public void testFindHistoricosByTabelaReajuste()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);
		historicoColaborador.setColaborador(colaborador);

		assertEquals(1, historicoColaboradorDao.findHistoricosByTabelaReajuste(tabelaReajusteColaborador.getId()).size());
	}

	public void testFindHistoricosByTabelaReajusteColaboradorNaoIntegrado()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNaoIntegraAc(false);
		colaborador1 = colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNaoIntegraAc(true);
		colaborador2 = colaboradorDao.save(colaborador2);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setReajusteColaborador(reajusteColaborador);
		historicoColaborador1 = historicoColaboradorDao.save(historicoColaborador1);
		historicoColaborador1.setColaborador(colaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setReajusteColaborador(reajusteColaborador);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);
		historicoColaborador2.setColaborador(colaborador2);

		assertEquals(2, historicoColaboradorDao.findHistoricosByTabelaReajuste(tabelaReajusteColaborador.getId()).size());
	}

	public void testFindPendenciasByHistoricoColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(20,2,2010));
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findPendenciasByHistoricoColaborador(empresa.getId(), StatusRetornoAC.AGUARDANDO);
		
		assertEquals(1, historicoColaboradors.size());
		
		HistoricoColaborador resultado1 = (HistoricoColaborador) historicoColaboradors.toArray()[0];
		
		assertEquals(colaborador.getDataAdmissao(), resultado1.getColaborador().getDataAdmissao());
	}
	
	public void testFindByCargoEstabelecimento()
	{
		// dado uma empresa
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);
		empresaDao.save(empresa);
		
		// dado um estabelecimento
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		// dado um cargo
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Desenvolvedor");
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setNome("Indice");
		indiceDao.save(indice);
		
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		indiceHistorico.setValor(12.00);
		indiceHistoricoDao.save(indiceHistorico);
		
		// dado uma faixa com seu historico
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("A");
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);

		FaixaSalarial faixaSalarialColab = FaixaSalarialFactory.getEntity();
		faixaSalarialColab.setNome("B");
		faixaSalarialColab.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarialColab);

		FaixaSalarial faixaSalarialColabIndice = FaixaSalarialFactory.getEntity();
		faixaSalarialColabIndice.setNome("C");
		faixaSalarialColabIndice.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarialColabIndice);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarialColab);
		faixaSalarialHistorico.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		faixaSalarialHistorico.setValor(875.99);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
		
		// dado uma area organizacional
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		// dado um colaborador
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Maria");
		colaborador.setEmpresa(empresa);
		colaborador.setVinculo("E");
		colaborador.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		colaborador.setDesligado(false);
		colaboradorDao.save(colaborador);

		Colaborador colaboradorFaixaSalarial = ColaboradorFactory.getEntity();
		colaboradorFaixaSalarial.setNome("colaboradorFaixaSalarial");
		colaboradorFaixaSalarial.setEmpresa(empresa);
		colaboradorFaixaSalarial.setVinculo("E");
		colaboradorFaixaSalarial.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		colaboradorFaixaSalarial.setDesligado(false);
		colaboradorDao.save(colaboradorFaixaSalarial);

		Colaborador colaboradorIndice = ColaboradorFactory.getEntity();
		colaboradorIndice.setNome("colaboradorIndice");
		colaboradorIndice.setEmpresa(empresa);
		colaboradorIndice.setVinculo("E");
		colaboradorIndice.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		colaboradorIndice.setDesligado(false);
		colaboradorDao.save(colaboradorIndice);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaborador1.setEstabelecimento(estabelecimento);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador1.setSalario(1000.00);
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaboradorFaixasalarial = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFaixasalarial.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaboradorFaixasalarial.setTipoSalario(TipoAplicacaoIndice.CARGO);
		historicoColaboradorFaixasalarial.setColaborador(colaboradorFaixaSalarial);
		historicoColaboradorFaixasalarial.setFaixaSalarial(faixaSalarialColab);
		historicoColaboradorFaixasalarial.setEstabelecimento(estabelecimento);
		historicoColaboradorFaixasalarial.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorFaixasalarial.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorFaixasalarial);

		HistoricoColaborador historicoColaboradorIndice = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorIndice.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaboradorIndice.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorIndice.setColaborador(colaboradorIndice);
		historicoColaboradorIndice.setFaixaSalarial(faixaSalarialColabIndice);
		historicoColaboradorIndice.setEstabelecimento(estabelecimento);
		historicoColaboradorIndice.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorIndice.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorIndice.setIndice(indice);
		historicoColaboradorIndice.setQuantidadeIndice(2.0);
		historicoColaboradorDao.save(historicoColaboradorIndice);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(10, 2, 2010));//esse é o atual mas ta com status = AGUARDANDO
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// quando
		Long[] cargoIds = new Long[]{cargo.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaOrganizacionalIds = new Long[]{areaOrganizacional.getId()};
		Date dataConsulta = new Date();
		
		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findByCargoEstabelecimento(DateUtil.criarDataMesAno(20, 2, 2010), cargoIds, estabelecimentoIds,  dataConsulta, areaOrganizacionalIds, null, null, empresa.getId());		
		assertEquals(3, historicoColaboradors.size());
		HistoricoColaborador resultado1 = (HistoricoColaborador) historicoColaboradors.toArray()[0];
		assertEquals("Desenvolvedor A", resultado1.getFaixaSalarial().getDescricao());
		assertEquals("Maria", resultado1.getColaborador().getNome());
		assertEquals(1000.00, resultado1.getSalarioCalculado());

		HistoricoColaborador resultadoColabFaixaSalarial = (HistoricoColaborador) historicoColaboradors.toArray()[1];
		assertEquals("colaboradorFaixaSalarial", resultadoColabFaixaSalarial.getColaborador().getNome());
		assertEquals(875.99, resultadoColabFaixaSalarial.getSalarioCalculado());

		HistoricoColaborador resultadoColabIndice = (HistoricoColaborador) historicoColaboradors.toArray()[2];
		assertEquals("colaboradorIndice", resultadoColabIndice.getColaborador().getNome());
		assertEquals(24.00, resultadoColabIndice.getSalarioCalculado());

		assertTrue(historicoColaboradorDao.findByCargoEstabelecimento(DateUtil.criarDataMesAno(20, 2, 1900), null, null, dataConsulta, null, null, "E", null).isEmpty());

		//desatualizados a partir de 01/07/2010 para tras
		Date dataAtualizacao = DateUtil.criarDataMesAno(1, 7, 2010);
		Collection<HistoricoColaborador> historicoColaboradorsDatAtaualizacao = historicoColaboradorDao.findByCargoEstabelecimento(DateUtil.criarDataMesAno(20, 2, 2010), cargoIds, estabelecimentoIds,  dataConsulta, areaOrganizacionalIds, dataAtualizacao, "E", null);
		
		assertEquals(3, historicoColaboradorsDatAtaualizacao.size());
	}

	public void testFindHistoricoAprovado()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador1 = historicoColaboradorDao.save(historicoColaborador1);

		Collection<HistoricoColaborador> retorno = historicoColaboradorDao.findHistoricoAprovado(historicoColaborador1.getId(), colaborador.getId());

		assertEquals(0, retorno.size());
	}
	
	public void testFindByData()
	{
		Date hoje = new Date();
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(historicoColaborador2);
		
		Collection<HistoricoColaborador> retorno = historicoColaboradorDao.findByData(colaborador.getId(), hoje);
		
		assertEquals(1, retorno.size());
	}

	public void testRemoveColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);

		historicoColaboradorDao.removeColaborador(colaborador.getId());
		assertTrue(historicoColaboradorDao.findByColaboradorProjection(colaborador.getId(), null).isEmpty());
	}
	
	public void testFindHistoricoAdmissao()
	{
		Date dataAdmissao = DateUtil.criarDataMesAno(18, 06, 2007);
		Date dataPrimeiroHistorico = DateUtil.criarDataMesAno(18, 06, 2009);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAdmissao(dataAdmissao);
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(dataPrimeiroHistorico);
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(new Date());
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador resultado = historicoColaboradorDao.findHistoricoAdmissao(colaborador.getId());
		
		assertTrue(DateUtil.equals(dataAdmissao, resultado.getColaborador().getDataAdmissao()));
		assertTrue(DateUtil.equals(dataPrimeiroHistorico, resultado.getData()));
	}
	
//	public void testFindByPeriodo()
//	{
//		Date dataIni = DateUtil.criarDataMesAno(01, 06, 2009);
//		Date dataFim = DateUtil.criarDataMesAno(01, 07, 2009);
//		Date dataHistorico = DateUtil.criarDataMesAno(18, 06, 2009);
//		
//		Empresa empresa = EmpresaFactory.getEmpresa();
//		empresaDao.save(empresa);
//		
//		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
//		estabelecimento = estabelecimentoDao.save(estabelecimento); 
//		
//		Colaborador colaboradorDesligado = ColaboradorFactory.getEntity();
//		colaboradorDesligado.setDesligado(true);
//		colaboradorDesligado.setEmpresa(empresa);
//		colaboradorDao.save(colaboradorDesligado);
//		
//		HistoricoColaborador historicoColaboradorDesligado = HistoricoColaboradorFactory.getEntity();
//		historicoColaboradorDesligado.setEstabelecimento(estabelecimento);
//		historicoColaboradorDesligado.setColaborador(colaboradorDesligado);
//		historicoColaboradorDesligado.setData(dataHistorico);
//		historicoColaboradorDesligado.setMotivo(MotivoHistoricoColaborador.IMPORTADO);
//		historicoColaboradorDao.save(historicoColaboradorDesligado);
//		
//		Colaborador colaborador = ColaboradorFactory.getEntity();
//		colaborador.setEmpresa(empresa);
//		colaboradorDao.save(colaborador);
//
//		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
//		historicoColaborador.setEstabelecimento(estabelecimento);
//		historicoColaborador.setColaborador(colaborador);
//		historicoColaborador.setData(dataHistorico);
//		historicoColaborador.setMotivo(MotivoHistoricoColaborador.IMPORTADO);
//		historicoColaboradorDao.save(historicoColaborador);
//		
//		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
//		historicoColaborador2.setEstabelecimento(estabelecimento);
//		historicoColaborador2.setColaborador(colaborador);
//		historicoColaborador2.setData(dataIni);
//		historicoColaborador2.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
//		historicoColaboradorDao.save(historicoColaborador2);
//		
//		Long[] estabelecimentosIds = new Long[]{estabelecimento.getId()};
//		Long[] areasIds = new Long[0];
//		String origemSituacao = "T"; // Qualquer origem (RH ou AC)
//		boolean imprimeDesligado = false; 
//		
//		Collection<HistoricoColaborador> resultado1 = historicoColaboradorDao.findByPeriodo(empresa.getId(), dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao, 'A', imprimeDesligado);
//		
//		assertEquals(2, resultado1.size());
//		
//		origemSituacao = "AC"; // origem  AC
//		Collection<HistoricoColaborador> resultado2 = historicoColaboradorDao.findByPeriodo(empresa.getId(), dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao, 'M', imprimeDesligado);
//		assertEquals(1, resultado2.size());
//		
//		imprimeDesligado = true; 
//		
//		Collection<HistoricoColaborador> resultado3 = historicoColaboradorDao.findByPeriodo(empresa.getId(), dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao, 'A', imprimeDesligado);
//		
//		assertEquals(2, resultado3.size());
//		
//	}
	
	public void testFindAllByColaboradorBuscandoAmbienteEFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 5, 2003));
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setAmbiente(ambiente);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(10, 8, 2005));
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setColaborador(colaborador);
		historicoColaborador3.setFuncao(funcao);
		historicoColaborador3.setAmbiente(ambiente);
		historicoColaborador3.setData(DateUtil.criarDataMesAno(22, 3, 2010));
		historicoColaborador3.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findAllByColaborador(colaborador.getId());
		assertEquals(3, historicoColaboradors.size());
	}
	
	public void testUpdateAmbienteEFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(new Date());
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		
		assertTrue(historicoColaboradorDao.updateAmbienteEFuncao(historicoColaborador));
	}
	
	public void testFindHistoricoAdmitidos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Date hoje = new Date();
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setData(hoje);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		assertEquals(1, historicoColaboradorDao.findHistoricoAdmitidos(empresa.getId(), hoje).size());
	}

	public void testDeleteHistoricoColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Date hoje = new Date();
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador);

		Exception exception = null;
		try {
			historicoColaboradorDao.deleteHistoricoColaborador(new Long[]{colaborador.getId()});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull("Não houve exceção", exception);
	}
	
	public void testFindByAreaGrupoCargo() 
	{
		// dado uma empresa
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		// dado um estabelecimento
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		GrupoOcupacional gerentes = GrupoOcupacionalFactory.getGrupoOcupacional();
		gerentes.setNome("Gerentes");
		grupoOcupacionalDao.save(gerentes);
		
		GrupoOcupacional operacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		operacional.setNome("Operacional");
		grupoOcupacionalDao.save(operacional);
		
		// dado um cargo
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Desenvolvedor");
		cargo.setGrupoOcupacional(gerentes);
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setNome("Desenvolvedor2");
		cargo2.setGrupoOcupacional(operacional);
		cargo2.setEmpresa(empresa);
		cargoDao.save(cargo2);
		
		// dado uma faixa com seu historico
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("A");
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);

		FaixaSalarial faixaSalarialColab = FaixaSalarialFactory.getEntity();
		faixaSalarialColab.setNome("B");
		faixaSalarialColab.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarialColab);

		FaixaSalarial faixaSalarialColab2 = FaixaSalarialFactory.getEntity();
		faixaSalarialColab2.setNome("C");
		faixaSalarialColab2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarialColab2);

		// dado uma area organizacional
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional areaOrganizacionalInativa = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalInativa.setAtivo(false);
		areaOrganizacionalDao.save(areaOrganizacionalInativa);
		
		// dado um colaborador
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("Maria");
		maria.setEmpresa(empresa);
		maria.setVinculo(Vinculo.EMPREGO);
		maria.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		maria.setDesligado(false);
		colaboradorDao.save(maria);

		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("Joao");
		joao.setEmpresa(empresa);
		joao.setVinculo(Vinculo.EMPREGO);
		joao.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		joao.setDesligado(false);
		colaboradorDao.save(joao);

		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setNome("Pedro");
		pedro.setEmpresa(empresa);
		pedro.setVinculo(Vinculo.EMPREGO);
		pedro.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		pedro.setDesligado(false);
		colaboradorDao.save(pedro);
		
		Colaborador josue = ColaboradorFactory.getEntity();
		josue.setNome("Josue");
		josue.setEmpresa(empresa);
		josue.setVinculo(Vinculo.EMPREGO);
		josue.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		josue.setDesligado(false);
		colaboradorDao.save(josue);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaborador.setColaborador(maria);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		HistoricoColaborador historicoColaboratdor1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboratdor1.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaboratdor1.setTipoSalario(TipoAplicacaoIndice.CARGO);
		historicoColaboratdor1.setColaborador(joao);
		historicoColaboratdor1.setFaixaSalarial(faixaSalarialColab);
		historicoColaboratdor1.setEstabelecimento(estabelecimento);
		historicoColaboratdor1.setAreaOrganizacional(areaOrganizacional);
		historicoColaboratdor1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboratdor1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaborador2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador2.setColaborador(pedro);
		historicoColaborador2.setFaixaSalarial(faixaSalarialColab2);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setData(DateUtil.criarDataMesAno(10, 2, 2010));//esse é o atual mas ta com status = AGUARDANDO
		historicoColaborador3.setColaborador(maria);
		historicoColaborador3.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		HistoricoColaborador historicoColaborador4 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador4.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaborador4.setColaborador(josue);
		historicoColaborador4.setFaixaSalarial(faixaSalarial);
		historicoColaborador4.setEstabelecimento(estabelecimento);
		historicoColaborador4.setAreaOrganizacional(areaOrganizacionalInativa);
		historicoColaborador4.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador4);
		
		// quando
		Long[] cargoIds = new Long[]{cargo.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaOrganizacionalIds = new Long[]{areaOrganizacional.getId(), areaOrganizacionalInativa.getId()};
		Long[] grupoOcupacionalIds = new Long[]{gerentes.getId()};
		String vinculo = Vinculo.EMPREGO;
		
		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findByAreaGrupoCargo(new Long[]{empresa.getId()}, DateUtil.criarDataMesAno(20, 2, 2010), cargoIds, estabelecimentoIds, areaOrganizacionalIds, null, grupoOcupacionalIds, vinculo);
		assertEquals(3, historicoColaboradors.size());
		
		HistoricoColaborador resultado1 = (HistoricoColaborador) historicoColaboradors.toArray()[0];
		assertEquals("Joao", resultado1.getColaborador().getNome());
		assertEquals("Desenvolvedor B", resultado1.getFaixaSalarial().getDescricao());
		assertEquals("Gerentes", resultado1.getFaixaSalarial().getCargo().getGrupoOcupacional().getNome());
		
		HistoricoColaborador resultado2 = (HistoricoColaborador) historicoColaboradors.toArray()[1];
		assertEquals("Josue", resultado2.getColaborador().getNome());
		assertEquals("Desenvolvedor A", resultado2.getFaixaSalarial().getDescricao());
		assertEquals("Gerentes", resultado2.getFaixaSalarial().getCargo().getGrupoOcupacional().getNome());

		HistoricoColaborador resultado3 = (HistoricoColaborador) historicoColaboradors.toArray()[2];
		assertEquals("Maria", resultado3.getColaborador().getNome());
		assertEquals("Desenvolvedor A", resultado3.getFaixaSalarial().getDescricao());
		assertEquals("Gerentes", resultado3.getFaixaSalarial().getCargo().getGrupoOcupacional().getNome());
		
		historicoColaboradors = historicoColaboradorDao.findByAreaGrupoCargo(new Long[]{empresa.getId()}, DateUtil.criarDataMesAno(20, 2, 2010), cargoIds, estabelecimentoIds, areaOrganizacionalIds, true, grupoOcupacionalIds, vinculo);
		assertEquals("Somente áreas ativas", 2, historicoColaboradors.size());
	}
	
	public void testDeleteHistoricosAguardandoConfirmacaoByColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Date hoje = new Date();
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador);

		historicoColaboradorDao.deleteHistoricosAguardandoConfirmacaoByColaborador(colaborador.getId());

		Collection<HistoricoColaborador> historicos = historicoColaboradorDao.findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"colaborador.id", "status"}, new Object[]{colaborador.getId(), StatusRetornoAC.AGUARDANDO});

		assertEquals(0, historicos.size());
	}
	
	public void testDeleteHistoricosAguardandoConfirmacaoByMultiplosColaboradores()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		Date hoje = new Date();
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador1.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador2.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador2);

		historicoColaboradorDao.deleteHistoricosAguardandoConfirmacaoByColaborador(new Long[]{colaborador1.getId(), colaborador2.getId()});

		Collection<HistoricoColaborador> historicos1 = historicoColaboradorDao.findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"colaborador.id", "status"}, new Object[]{colaborador1.getId(), StatusRetornoAC.AGUARDANDO});
		Collection<HistoricoColaborador> historicos2 = historicoColaboradorDao.findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"colaborador.id", "status"}, new Object[]{colaborador2.getId(), StatusRetornoAC.AGUARDANDO});

		assertEquals(0, historicos1.size());
		assertEquals(0, historicos2.size());
	}
	
	public void testExisteHistoricoPorIndice()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador h1 = HistoricoColaboradorFactory.getEntity();
		h1.setColaborador(colaborador);
		h1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaboradorDao.save(h1);
		
		assertFalse(historicoColaboradorDao.existeHistoricoPorIndice(empresa.getId()));
		
		HistoricoColaborador h2 = HistoricoColaboradorFactory.getEntity();
		h2.setColaborador(colaborador);
		h2.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorDao.save(h2);
		
		assertTrue(historicoColaboradorDao.existeHistoricoPorIndice(empresa.getId()));
	}
	
	public void testUpdateStatusAc()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		historicoColaboradorDao.updateStatusAc(StatusRetornoAC.AGUARDANDO, historicoColaborador.getId());

		Collection<HistoricoColaborador> historicos = historicoColaboradorDao.findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"colaborador.id", "status"}, new Object[]{colaborador.getId(), StatusRetornoAC.AGUARDANDO});

		assertEquals(1, historicos.size());
	}
	
	public void testUpdateArea()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setAreaMae(areaOrganizacional);
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		historicoColaboradorDao.updateArea(areaOrganizacional.getId(), areaOrganizacional2.getId());

		HistoricoColaborador historico = historicoColaboradorDao.findByIdProjection(historicoColaborador.getId());
		
		assertEquals(areaOrganizacional2.getId(), historico.getAreaOrganizacional().getId());
	}
	
	public void testFindByEmpresa()
	{
		inicializaColaboradorComHistorico();
		historicoColaborador.setStatus(StatusRetornoAC.PENDENTE);
		historicoColaboradorDao.save(historicoColaborador);
		
		Collection<HistoricoColaborador> historicos = historicoColaboradorDao.findByEmpresaComHistorico(empresa.getId(), StatusRetornoAC.PENDENTE, false);
		
		assertEquals(1, historicos.size());
	}
	
	public void testFindByEmpresaComDataSolicitacaoDesligamento()
	{
		inicializaColaboradorComHistorico();
		
		
		Date dataSolDesligamentoAC = DateUtil.criarDataMesAno(1, 1, 2015);
		colaborador.setDataSolicitacaoDesligamentoAc(dataSolDesligamentoAC);
		
		historicoColaborador.setStatus(StatusRetornoAC.PENDENTE);
		historicoColaboradorDao.save(historicoColaborador);
		
		Collection<HistoricoColaborador> historicos = historicoColaboradorDao.findByEmpresaComHistorico(empresa.getId(), StatusRetornoAC.PENDENTE, true);
		
		assertEquals(1, historicos.size());
		assertEquals(dataSolDesligamentoAC, ((HistoricoColaborador)historicos.toArray()[0]).getDataSolicitacaoDesligamento());
	}

	private Empresa inicializaColaboradorComHistorico() 
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		colaborador = ColaboradorFactory.getEntity();
		colaborador.setNaoIntegraAc(false);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setData(new Date());
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(1000.00);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		return empresa;
	}
	
	public void testUpdateStatusAcByEmpresaAndStatusAtual()
	{
		inicializaColaboradorComHistorico();
		historicoColaborador.setStatus(StatusRetornoAC.PENDENTE);
		historicoColaboradorDao.save(historicoColaborador);
		
		historicoColaboradorDao.updateStatusAcByEmpresaAndStatusAtual(StatusRetornoAC.PENDENTE, StatusRetornoAC.CONFIRMADO, colaborador.getId());
		
		HistoricoColaborador histRetorno = historicoColaboradorDao.findById(historicoColaborador.getId());
		
		assertEquals(StatusRetornoAC.PENDENTE, histRetorno.getStatus());
	}
	
	public void testExisteDependenciaComHistoricoIndiceExistindoUmHistoricoIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indiceDao.save(indice);
		
		Date dataHistoricoIndice = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoColaborador1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoColaborador2 = DateUtil.criarDataDiaMesAno("02/01/2015");
		
		inicializaColaboradorComHistorico();
		
		criaHistoricoColaborador(indice, dataHistoricoColaborador1, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CONFIRMADO, false);
		boolean existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice, null, indice.getId());
		
		assertTrue("Histórico do colaborador na mesma data que histórico do índice", existeDependencia);
		
		criaHistoricoColaborador(indice, dataHistoricoColaborador2, TipoAplicacaoIndice.INDICE, StatusRetornoAC.AGUARDANDO, false);
		existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice, null, indice.getId());
		
		assertTrue("Histórico do colaborador com data posterior à data do histórico do índice", existeDependencia);
	}
	
	public void testExisteDependenciaComHistoricoIndiceExistindoMaisDeUmHistoricoIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indiceDao.save(indice);
		
		Date dataHistoricoIndice1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoIndice2 = DateUtil.criarDataDiaMesAno("01/02/2015");
		Date dataHistoricoColaborador1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoColaborador2 = DateUtil.criarDataDiaMesAno("02/01/2015");
		
		inicializaColaboradorComHistorico();
		
		criaHistoricoColaborador(indice, dataHistoricoColaborador1, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CONFIRMADO, false);
		boolean existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoColaborador2, indice.getId());
		
		assertTrue("Histórico do colaborador na mesma data que histórico do índice", existeDependencia);
		
		criaHistoricoColaborador(indice, dataHistoricoColaborador2, TipoAplicacaoIndice.INDICE, StatusRetornoAC.AGUARDANDO, false);
		existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoIndice2, indice.getId());
		
		assertTrue("Histórico do colaborador com data posterior à data do histórico do índice", existeDependencia);
	}

	public void testExisteDependenciaComHistoricoIndiceComCondicoesInsatisfatorias()
	{
		Indice indice1 = IndiceFactory.getEntity();
		indiceDao.save(indice1);

		Indice indice2 = IndiceFactory.getEntity();
		indiceDao.save(indice2);

		Date dataHistoricoIndice1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoIndice2 = DateUtil.criarDataDiaMesAno("01/02/2015");
		Date dataHistoricoColaborador1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoColaborador2 = DateUtil.criarDataDiaMesAno("02/01/2015");

		inicializaColaboradorComHistorico();

		criaHistoricoColaborador(indice1, dataHistoricoColaborador1, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CANCELADO, false);
		boolean existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, null, indice1.getId());

		assertFalse("Histórico do colaborador cancelado", existeDependencia);

		criaHistoricoColaborador(indice1, dataHistoricoColaborador1, TipoAplicacaoIndice.VALOR, StatusRetornoAC.AGUARDANDO, false);
		existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoIndice2, indice1.getId());
		
		assertFalse("Histórico do colaborador por valor", existeDependencia);
		
		criaHistoricoColaborador(indice1, dataHistoricoColaborador2, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CONFIRMADO, false);
		existeDependencia = historicoColaboradorDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoIndice2, indice2.getId());
		
		assertFalse("Histórico do colaborador com outro índice", existeDependencia);
	}
	
	
	public void testHistoricoColaboradorByData() {
		Colaborador colaborador = ColaboradorFactory.getEntity(false, DateUtil.criarDataMesAno(1, 1, 2016), null);
		colaboradorDao.save(colaborador);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(null, colaborador, funcao, DateUtil.criarDataMesAno(1, 1, 2016));
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(null, colaborador, funcao, DateUtil.criarDataMesAno(1, 5, 2016));
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador historicoColaboradorRetornadoDoBanco = historicoColaboradorDao.findHistoricoColaboradorByData(colaborador.getId(), DateUtil.criarDataMesAno(1, 5, 2016));
		
		assertEquals(historicoColaborador2.getId(), historicoColaboradorRetornadoDoBanco.getId());
	}

	private void criaHistoricoColaborador(Indice indice, Date dataPrimeiroHistoricoIndice, int tipoSalario, int status, boolean criaNovoHistorico)
	{
		HistoricoColaborador historicoColaborador = null;
		if(criaNovoHistorico)
			historicoColaborador = HistoricoColaboradorFactory.getEntity();
		else
			historicoColaborador = this.historicoColaborador;
		
		historicoColaborador.setData(dataPrimeiroHistoricoIndice);
		historicoColaborador.setTipoSalario(tipoSalario);
		historicoColaborador.setIndice(indice);
		historicoColaborador.setStatus(status);
		historicoColaboradorDao.save(historicoColaborador);
	}
	
	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
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

	public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
	{
		this.grupoOcupacionalDao = grupoOcupacionalDao;
	}

	public void setReajusteColaboradorDao(ReajusteColaboradorDao reajusteColaboradorDao)
	{
		this.reajusteColaboradorDao = reajusteColaboradorDao;
	}

	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}

	public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao) {
		this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
	}

	public void setIndiceDao(IndiceDao indiceDao) {
		this.indiceDao = indiceDao;
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao) {
		this.indiceHistoricoDao = indiceHistoricoDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao) {
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}
}