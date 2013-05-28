package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.MotivoDemissaoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorDaoHibernateTest extends GenericDaoHibernateTest<Colaborador> 
{
	private ColaboradorDao colaboradorDao;
	private TurmaDao turmaDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	private UsuarioEmpresaDao usuarioEmpresaDao;
	private CandidatoDao candidatoDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private AvaliacaoDao avaliacaoDao;
	private EstabelecimentoDao estabelecimentoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	private CargoDao cargoDao;
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private MotivoDemissaoDao motivoDemissaoDao;
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	private ReajusteColaboradorDao reajusteColaboradorDao;
	private AmbienteDao ambienteDao;
	private FuncaoDao funcaoDao;
	private BairroDao bairroDao;
	private PerfilDao perfilDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private QuestionarioDao questionarioDao;
	private CamposExtrasDao camposExtrasDao;
	private GrupoACDao grupoACDao;
	private MotivoSolicitacaoDao motivoSolicitacaoDao;
	private SolicitacaoDao solicitacaoDao;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private OcorrenciaDao ocorrenciaDao;
	private ColaboradorOcorrenciaDao colaboradorOcorrenciaDao;
	private HistoricoFuncaoDao historicoFuncaoDao;
	private EpiDao epiDao;
	private SolicitacaoEpiDao solicitacaoEpiDao;
	private ProvidenciaDao providenciaDao;
	private PeriodoExperienciaDao periodoExperienciaDao;
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	private ComissaoDao comissaoDao;
	private ComissaoPeriodoDao comissaoPeriodoDao;
	private ComissaoMembroDao comissaoMembroDao;

	private Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
	private Cargo cargo1 = CargoFactory.getEntity();
	private AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();

	public Colaborador getEntity() {
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());
		colaborador.setMatricula("00001");

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("123456789012345678901");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		colaborador.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		colaborador.setContato(contato);

		Pessoal pessoal = new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		colaborador.setAreaOrganizacional(null);
		colaborador.setDependentes(null);

		return colaborador;
	}

	@Override
	public void testFindById() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertNotNull(colaborador);
		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	public void testGetAutoComplete() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaboradorBuscaPorNome = ColaboradorFactory.getEntity(1L);
		colaboradorBuscaPorNome.setNome("amilos");
		colaboradorBuscaPorNome.setEmpresa(empresa);
		colaboradorBuscaPorNome = colaboradorDao.save(colaboradorBuscaPorNome);

		Colaborador colaboradorPorNomeComercial = ColaboradorFactory.getEntity(1L);
		colaboradorPorNomeComercial.setNomeComercial("milosaaaa");
		colaboradorPorNomeComercial.setEmpresa(empresa);
		colaboradorPorNomeComercial = colaboradorDao.save(colaboradorPorNomeComercial);

		Colaborador colaboradorBuscaPorMatricula = ColaboradorFactory.getEntity(1L);
		colaboradorBuscaPorMatricula.setMatricula("milosaa");
		colaboradorBuscaPorMatricula.setEmpresa(empresa);
		colaboradorBuscaPorMatricula = colaboradorDao.save(colaboradorBuscaPorMatricula);

		Colaborador colaboradorBuscaPorCPF = ColaboradorFactory.getEntity(1L);
		colaboradorBuscaPorCPF.getPessoal().setCpf("milos33");
		colaboradorBuscaPorCPF.setEmpresa(empresa);
		colaboradorBuscaPorCPF = colaboradorDao.save(colaboradorBuscaPorCPF);

		Colaborador colaboradorForaDaConsulta = ColaboradorFactory.getEntity(1L);
		colaboradorForaDaConsulta.setNome("babau");
		colaboradorForaDaConsulta.setEmpresa(empresa);
		colaboradorForaDaConsulta = colaboradorDao.save(colaboradorForaDaConsulta);

		assertEquals(4, colaboradorDao.getAutoComplete("milos", empresa.getId()).size());
	}
	
	public void testFindByAvaliacao() throws Exception 
	{
		Colaborador colaboradorMaria = ColaboradorFactory.getEntity(1L);
		colaboradorMaria.setNome("maria");
		colaboradorDao.save(colaboradorMaria);

		Colaborador colaboradorJorge = ColaboradorFactory.getEntity(1L);
		colaboradorJorge.setNome("jorge");
		colaboradorDao.save(colaboradorJorge);
		
		Colaborador avaliador = ColaboradorFactory.getEntity(1L);
		avaliador.setNome("avaliador");
		colaboradorDao.save(avaliador);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		AvaliacaoDesempenho avaliacaoDesempenhoFora = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenhoFora);
		
		ColaboradorQuestionario colaboradorQuestionarioJorge = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioJorge.setColaborador(colaboradorJorge);
		colaboradorQuestionarioJorge.setPerformance(97.0);
		colaboradorQuestionarioJorge.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioJorge.setAvaliador(avaliador);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioJorge);

		ColaboradorQuestionario colaboradorQuestionariosSemAvaliacao = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionariosSemAvaliacao.setColaborador(colaboradorJorge);
		colaboradorQuestionariosSemAvaliacao.setAvaliador(avaliador);
		colaboradorQuestionarioDao.save(colaboradorQuestionariosSemAvaliacao);
		
		ColaboradorQuestionario colaboradorQuestionarioMaria = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioMaria.setColaborador(colaboradorMaria);
		colaboradorQuestionarioMaria.setAvaliador(avaliador);
		colaboradorQuestionarioMaria.setPerformance(12.0);
		colaboradorQuestionarioMaria.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMaria);
		
		ColaboradorQuestionario colaboradorQuestionarioEmOutraAvaliacao = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioEmOutraAvaliacao.setColaborador(colaboradorJorge);
		colaboradorQuestionarioEmOutraAvaliacao.setAvaliador(avaliador);
		colaboradorQuestionarioEmOutraAvaliacao.setPerformance(20.0);
		colaboradorQuestionarioEmOutraAvaliacao.setAvaliacaoDesempenho(avaliacaoDesempenhoFora);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioEmOutraAvaliacao);
		
		assertEquals(2, colaboradorDao.findByAvaliacoes(avaliacaoDesempenho.getId()).size());
	}

	@Override
	public void testUpdate() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador.setNome("nome");
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		colaborador.setNome("outro nome");

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertEquals(colaborador.getNome(), colaboradorRetorno.getNome());
	}

	public void testUpdateDataDesligamentoByCodigo() throws Exception {
		Empresa empresa = new Empresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getEntity();
		colaborador.setCodigoAC("010203");
		colaborador.setEmpresa(empresa);

		colaboradorDao.save(colaborador);

		assertTrue(colaboradorDao.desligaByCodigo("010203", empresa, new Date()));

		Colaborador colDesligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNotNull(colDesligado.getDataDesligamento());
		assertTrue(colDesligado.isDesligado());

		assertTrue(colaboradorDao.desligaByCodigo("010203", empresa, null));

		Colaborador colReligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNull(colReligado.getDataDesligamento());
		assertFalse(colReligado.isDesligado());

	}

	public void testUpdateUsuarioColaborador() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador.setNome("nome");
		colaborador = colaboradorDao.save(colaborador);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		colaborador.setUsuario(usuario);

		colaboradorDao.atualizarUsuario(colaborador.getId(), usuario.getId());
		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());
		assertEquals(usuario.getId(), colaboradorRetorno.getUsuario().getId());

		colaboradorDao.atualizarUsuario(colaborador.getId(), null);
		colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());
		assertNull(colaboradorRetorno.getUsuario().getId());
	}
	
	public void testFindAniversariantesByEmpresa() throws Exception 
	{
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);

		Colaborador joao = getEntity();
		joao.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 4, 1984));
		joao.getContato().setEmail("email.com");
		joao.setDesligado(false);
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Colaborador jonas = getEntity();
		jonas.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 5, 1984));
		jonas.getContato().setEmail("email.com");
		jonas.setDesligado(false);
		jonas.setEmpresa(empresa);
		colaboradorDao.save(jonas);
		
		Colaborador paula = getEntity();
		paula.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(25, 4, 1984));
		paula.getContato().setEmail("email.com");
		paula.setDesligado(false);
		paula.setEmpresa(empresa);
		colaboradorDao.save(paula);
		
		Colaborador maria = getEntity();
		maria.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 4, 1984));
		maria.getContato().setEmail("");
		maria.setDesligado(false);
		maria.setEmpresa(empresa);
		colaboradorDao.save(maria);
		
		Colaborador pedro = getEntity();
		pedro.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 4, 1984));
		pedro.getContato().setEmail(null);
		pedro.setDesligado(false);
		pedro.setEmpresa(empresa);
		colaboradorDao.save(pedro);
		
		Colaborador marcos = getEntity();
		marcos.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 4, 1984));
		marcos.getContato().setEmail("asdf");
		marcos.setDesligado(true);
		marcos.setEmpresa(empresa);
		colaboradorDao.save(marcos);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findAniversariantesByEmpresa(empresa.getId(), 7, 4);
		assertEquals(1, colaboradores.size());
	}

	public void testFindByUsuarioProjection() throws Exception {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setNome("João Batista");
		usuarioDao.save(usuario);

		Colaborador colaborador = getEntity();
		colaborador.setNome("João");
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);

		Colaborador retorno = colaboradorDao.findByUsuarioProjection(usuario.getId());
		assertEquals("João", retorno.getNome());
		assertEquals("João Batista", retorno.getUsuario().getNome());
	}

	@Override
	public void testRemove() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		colaboradorDao.remove(colaborador);

		colaborador = getGenericDao().findById(colaborador.getId());

		assertNull(colaborador);
	}

	public void testFindSemUsuarios() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1 = colaboradorDao.save(c1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setUsuario(usuario);
		c2 = colaboradorDao.save(c2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa2);
		c3 = colaboradorDao.save(c3);

		Collection<Colaborador> colaboradores = colaboradorDao.findSemUsuarios(empresa.getId(), null);

		assertFalse(colaboradores.isEmpty());
		assertEquals(c1.getId(), ((Colaborador) colaboradores.toArray()[0]).getId());
	}

	public void testFindSemUsuariosComUsuario() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1 = colaboradorDao.save(c1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setUsuario(usuario);
		c2 = colaboradorDao.save(c2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa2);
		c3 = colaboradorDao.save(c3);

		Collection<Colaborador> colaboradores = colaboradorDao.findSemUsuarios(empresa.getId(), usuario);

		assertEquals(2, colaboradores.size());
	}

	// @SuppressWarnings("unchecked")
	// public void testGetCountSQL()
	// {
	// Empresa empresa = new Empresa();
	// empresa.setNome("empresa");
	// empresa.setCnpj("21212121212");
	// empresa.setRazaoSocial("empresa");
	// empresaDao.save(empresa);
	//
	// insereAmbiente(empresa);
	//
	// Map parametros = new HashMap();
	// parametros.put("empresaId", 4L);
	// parametros.put("areaId", null);
	//
	// Collection<Object> colaboradors =
	// colaboradorDao.findComHistoricoFuturoSQL(parametros, 5, 10);
	// assertEquals(27, colaboradors.size());
	//
	// parametros.put("nomeBusca", "");
	// parametros.put("cpfBusca", "1111");
	//
	// result = colaboradorDao.getCount(parametros,
	// TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	// assertEquals(1, result);
	//
	// parametros.put("nomeBusca", "Chi");
	// parametros.put("cpfBusca", "1111");
	//
	// result = colaboradorDao.getCount(parametros,
	// TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	// assertEquals(1, result);
	//
	// }

	public void testGetCount() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		insereAmbiente(empresa);

		Map<Object, Object> parametros = new HashMap<Object, Object>();
		parametros.put("nomeBusca", "chi");
		parametros.put("cpfBusca", "   .   .   -  ");
		parametros.put("empresaId", empresa.getId());
		parametros.put("areaId", null);
		parametros.put("matriculaBusca", "554");

		int result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(2, result);

		parametros.put("nomeBusca", "");
		parametros.put("cpfBusca", "1111");

		result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(1, result);

		parametros.put("nomeBusca", "Chi");
		parametros.put("cpfBusca", "1111");

		result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(1, result);
	}

	@SuppressWarnings("unchecked")
	public void testFindList() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		insereAmbiente(empresa);

		Map<Object, Object> parametros = new HashMap<Object, Object>();
		parametros.put("nomeBusca", "chi");
		parametros.put("cpfBusca", "");
		parametros.put("empresaId", empresa.getId());
		parametros.put("areaId", null);

		Collection<Colaborador> colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals("só chi", 2, colaboradores.size());

		parametros.put("nomeBusca", "");
		parametros.put("cpfBusca", "1111");

		colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals("só 1111", 1, colaboradores.size());

		parametros.put("nomeBusca", "Chi");
		parametros.put("cpfBusca", "1111");

		colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals("os 2", 1, colaboradores.size());

		colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);

		parametros.clear();

		assertEquals("Histórico futuro", 1, colaboradores.size());
	}

	private void insereAmbiente(Empresa empresa) {
		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		AreaOrganizacional ao = AreaOrganizacionalFactory.getEntity();
		ao = areaOrganizacionalDao.save(ao);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1.setNome("Chico");
		c1.setNomeComercial("Chico");
		c1.setMatricula("115544");
		c1.getPessoal().setCpf("11111111111");
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(ao);
		hc1.setSalario(1000D);
		hc1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc1.setMotivo("m");
		hc1 = historicoColaboradorDao.save(hc1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setNome("Chiquinho");
		c2.setNomeComercial("Chiquinho");
		c2.setMatricula("115544");
		c2.getPessoal().setCpf("22222222222");
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(ao);
		hc2.setSalario(1000D);
		hc2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc2.setMotivo("m");
		hc2 = historicoColaboradorDao.save(hc2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa);
		c3.setNome("Mazelento");
		c3.setNomeComercial("Mazelento");
		c3.getPessoal().setCpf("33333333333");
		c3 = colaboradorDao.save(c3);

		HistoricoColaborador hc3 = HistoricoColaboradorFactory.getEntity();
		hc3.setColaborador(c3);
		hc3.setAreaOrganizacional(ao);
		hc3.setSalario(1000D);
		hc3.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc3.setMotivo("m");
		hc3 = historicoColaboradorDao.save(hc3);

		Colaborador c4 = getColaborador();
		c4.setEmpresa(empresa2);
		c4.setNome("Chiquitita");
		c4.setNomeComercial("Chiquitita");
		c4.getPessoal().setCpf("11111111111");
		c4 = colaboradorDao.save(c4);

		HistoricoColaborador hc4 = HistoricoColaboradorFactory.getEntity();
		hc4.setColaborador(c4);
		hc4.setAreaOrganizacional(ao);
		hc4.setSalario(1000D);
		hc4.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc4.setMotivo("m");
		hc4 = historicoColaboradorDao.save(hc4);

		Colaborador c5 = getColaborador();
		c5.setEmpresa(empresa2);
		c5.setNome("Robertita");
		c5.setNomeComercial("Robertita");
		c5.getPessoal().setCpf("11111111111");
		c5 = colaboradorDao.save(c5);

		HistoricoColaborador hc5 = HistoricoColaboradorFactory.getEntity();
		hc5.setColaborador(c5);
		hc5.setAreaOrganizacional(ao);
		hc5.setSalario(1000D);
		hc5.setData(DateUtil.criarDataMesAno(01, 01, 3000));
		hc5.setMotivo("m");
		hc5 = historicoColaboradorDao.save(hc5);

	}

	public void testFindColaboradorPesquisa() {
		AreaOrganizacional area1 = new AreaOrganizacional();
		area1.setNome("area 1");
		area1 = areaOrganizacionalDao.save(area1);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1.setNome("Chico");
		c1.setNomeComercial("Chico");
		c1.getPessoal().setCpf("11111111111");
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(area1);
		hc1.setSalario(1000D);
		hc1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc1.setMotivo("m");
		hc1 = historicoColaboradorDao.save(hc1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa2);
		c2.setNome("Chiquinho");
		c2.setNomeComercial("Chiquinho");
		c2.getPessoal().setCpf("22222222222");
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(area1);
		hc2.setSalario(1000D);
		hc2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc2.setMotivo("m");
		hc2 = historicoColaboradorDao.save(hc2);

		Colaborador colaborador = colaboradorDao.findColaboradorPesquisa(c1.getId(), empresa.getId());

		assertEquals("equals", c1.getId(), colaborador.getId());

		colaborador = colaboradorDao.findColaboradorPesquisa(c1.getId(), empresa2.getId());

		assertNull("null", colaborador.getId());
	}

	public void testFindByUsuario() {
		AreaOrganizacional area1 = new AreaOrganizacional();
		area1.setNome("area 1");
		area1 = areaOrganizacionalDao.save(area1);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Usuario usuario1 = UsuarioFactory.getEntity();
		usuario1 = usuarioDao.save(usuario1);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1.setNome("Chico");
		c1.setNomeComercial("Chico");
		c1.getPessoal().setCpf("11111111111");
		c1.setUsuario(usuario);
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(area1);
		hc1.setSalario(1000D);
		hc1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc1.setMotivo("m");
		hc1 = historicoColaboradorDao.save(hc1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa2);
		c2.setNome("Chiquinho");
		c2.setNomeComercial("Chiquinho");
		c2.getPessoal().setCpf("22222222222");
		c2.setUsuario(usuario1);
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(area1);
		hc2.setSalario(1000D);
		hc2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc2.setMotivo("m");
		hc2 = historicoColaboradorDao.save(hc2);

		Colaborador colaborador = colaboradorDao.findByUsuario(usuario, empresa.getId());
		assertEquals("equals", c1.getId(), colaborador.getId());

		colaborador = colaboradorDao.findByUsuario(usuario, empresa2.getId());
		assertNull("null", colaborador.getId());
	}

	public void testFindColaboradorUsuarioByCpf() {

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario = usuarioDao.save(usuario);

		Usuario usuario1 = UsuarioFactory.getEntity();
		usuario1.setLogin("usuario 1");
		usuario1 = usuarioDao.save(usuario1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2.setLogin("usuario 2");
		usuario2.setAcessoSistema(true);
		usuario2 = usuarioDao.save(usuario2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1.setUsuario(usuario);
		c1.getPessoal().setCpf("11111111111");

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setUsuario(usuario1);
		c2.getPessoal().setCpf("11111111112");

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa2);
		c3.setUsuario(usuario2);
		c3.getPessoal().setCpf("11111111111");

		c1 = colaboradorDao.save(c1);
		c2 = colaboradorDao.save(c2);
		c3 = colaboradorDao.save(c3);

		Colaborador colaborador = colaboradorDao.findColaboradorUsuarioByCpf("11111111111", empresa2.getId());
		assertEquals("equals", c3.getId(), colaborador.getId());

		colaborador = colaboradorDao.findColaboradorUsuarioByCpf("11111111111", empresa.getId());
		assertEquals("equals2", c1.getId(), colaborador.getId());

	}

	public void testeFindbyCandidato() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa1");
		empresa.setCnpj("1111212215225");
		empresa.setRazaoSocial("razaoSocial1");
		empresa = empresaDao.save(empresa);

		Candidato candidato = getCandidato();
		candidato.setEmpresa(empresa);
		candidato = candidatoDao.save(candidato);

		Candidato candidato2 = getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2 = candidatoDao.save(candidato2);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setCandidato(candidato);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setEmpresa(empresa);
		colaborador2.setCandidato(candidato2);
		colaborador2 = colaboradorDao.save(colaborador2);

		Collection<Colaborador> colaboradors = colaboradorDao.findbyCandidato(candidato.getId(), empresa.getId());

		assertEquals(colaborador.getId(), ((Colaborador) colaboradors.toArray()[0]).getId());
	}

	public void testSetCandidatoNull() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa1");
		empresaDao.save(empresa);

		Candidato candidato = getCandidato();
		candidato.setEmpresa(empresa);
		candidatoDao.save(candidato);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);

		colaboradorDao.setCandidatoNull(candidato.getId());

		assertEquals(0, colaboradorDao.findbyCandidato(candidato.getId(), empresa.getId()).size());
	}

	private Candidato getCandidato() {
		Candidato candidato = new Candidato();
		candidato.setId(null);
		candidato.setAreasInteresse(null);
		candidato.setColocacao("1");
		candidato.setConhecimentos(null);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		candidato.setContato(contato);

		candidato.setContratado(false);
		candidato.setCursos(null);
		candidato.setDisponivel(true);

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		candidato.setEndereco(endereco);

		candidato.setExperiencias(null);
		candidato.setFormacao(null);
		candidato.setNome("colaborador teste");
		candidato.setObservacao("obs");

		Pessoal pessoal = new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		pessoal.setConjuge("Maria");
		pessoal.setNaturalidade("Palmácia");
		pessoal.setMae("Joana");
		pessoal.setPai("Roberto");
		pessoal.setQtdFilhos(0);
		candidato.setPessoal(pessoal);

		SocioEconomica socioEconomica = new SocioEconomica();
		socioEconomica.setPagaPensao(true);
		socioEconomica.setPossuiVeiculo(true);
		socioEconomica.setQuantidade(10);
		socioEconomica.setValor(1500D);
		candidato.setSocioEconomica(socioEconomica);

		Habilitacao habilitacao = new Habilitacao();
		habilitacao.setCategoria("ABC");
		habilitacao.setEmissao(null);
		habilitacao.setVencimento(null);
		habilitacao.setRegistro("607583");
		habilitacao.setNumeroHab("123324235");
		candidato.setHabilitacao(habilitacao);

		candidato.setPretencaoSalarial(1500.00);

		return candidato;
	}

	private Colaborador getColaborador() {
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial colaborador");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		colaborador.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		colaborador.setContato(contato);

		Pessoal pessoal = new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("000000");
		colaborador.setPessoal(pessoal);

		colaborador.setDependentes(null);
		return colaborador;
	}

	public void testCountSexo() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);// data
																																				// superior
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);// desligado
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), outraEmpresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);// outra
																																						// empresa
		Collection<DataGrafico> data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{area.getId()});
		assertEquals(2, data.size());

		DataGrafico fem = (DataGrafico) data.toArray()[0];
		DataGrafico mas = (DataGrafico) data.toArray()[1];

		assertEquals("Feminino", fem.getLabel());
		assertEquals(2.0, fem.getData());

		assertEquals("Masculino", mas.getLabel());
		assertEquals(1.0, mas.getData());

		data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), null, new Long[]{area.getId()});
		assertEquals(2, data.size());
		
		data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{});
		assertEquals(2, data.size());
	}

	public void testCountVinculo() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area);// data
																																							// superior
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.APRENDIZ, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.ESTAGIO, area);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);// desligado
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), outraEmpresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);// outra
																																						// empresa

		Collection<DataGrafico> data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), null);
		assertEquals(3, data.size());
		data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{area.getId()});
		assertEquals(3, data.size());

		DataGrafico empregado = (DataGrafico) data.toArray()[0];
		DataGrafico aprendiz = (DataGrafico) data.toArray()[1];

		assertEquals(2.0, empregado.getData());
		assertEquals(1.0, aprendiz.getData());
		
		data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), null, new Long[]{area.getId()});
		assertEquals(4, data.size());
	}

	public void testCountEstadoCivil() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);

		Collection<DataGrafico> data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{area.getId()});
		assertEquals(4, data.size());

		DataGrafico casado = (DataGrafico) data.toArray()[0];
		DataGrafico divorciado = (DataGrafico) data.toArray()[1];
		DataGrafico solteiro = (DataGrafico) data.toArray()[2];
		DataGrafico viuvo = (DataGrafico) data.toArray()[3];

		assertEquals("Casado", casado.getLabel());
		assertEquals(5.0, casado.getData());

		assertEquals("Divorciado", divorciado.getLabel());
		assertEquals(2.0, divorciado.getData());

		assertEquals("Solteiro", solteiro.getLabel());
		assertEquals(2.0, solteiro.getData());

		assertEquals("Viúvo", viuvo.getLabel());
		assertEquals(1.0, viuvo.getData());

		data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{});
		assertEquals(4, data.size());
		data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), null, new Long[]{area.getId()});
		assertEquals(4, data.size());
	}

	public void testCountFaixaEtaria() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, DateUtil.criarDataMesAno(31, 01, 1989), Deficiencia.SEM_DEFICIENCIA, null,
				null, null, area);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, DateUtil.criarDataMesAno(31, 01, 1988), Deficiencia.SEM_DEFICIENCIA, null,
				null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, DateUtil.criarDataMesAno(31, 01, 1986), Deficiencia.SEM_DEFICIENCIA,
				null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, DateUtil.criarDataMesAno(23, 02, 1978), Deficiencia.SEM_DEFICIENCIA, null, null, null, area);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, DateUtil.criarDataMesAno(18, 12, 1977), Deficiencia.SEM_DEFICIENCIA, null, null,
				null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, DateUtil.criarDataMesAno(18, 12, 1975), Deficiencia.SEM_DEFICIENCIA, null,
				null, null, area);

		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, DateUtil.criarDataMesAno(01, 02, 1968), Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, DateUtil.criarDataMesAno(31, 12, 1960), Deficiencia.SEM_DEFICIENCIA, null, null, null, area);

		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, DateUtil.criarDataMesAno(31, 01, 1950), Deficiencia.SEM_DEFICIENCIA, null, null, null, area);

		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, DateUtil.criarDataMesAno(18, 12, 1947), Deficiencia.SEM_DEFICIENCIA, null, null,
				null, area);

		Collection<DataGrafico> data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{area.getId()});

		DataGrafico faixa1 = (DataGrafico) data.toArray()[0];
		DataGrafico faixa2 = (DataGrafico) data.toArray()[1];
		DataGrafico faixa3 = (DataGrafico) data.toArray()[2];
		DataGrafico faixa4 = (DataGrafico) data.toArray()[3];
		DataGrafico faixa5 = (DataGrafico) data.toArray()[4];
		DataGrafico faixa6 = (DataGrafico) data.toArray()[5];

		assertEquals("Até 19", faixa1.getLabel());
		assertEquals(1.0, faixa1.getData());

		assertEquals("20 a 29", faixa2.getLabel());
		assertEquals(3.0, faixa2.getData());

		assertEquals("30 a 39", faixa3.getLabel());
		assertEquals(2.0, faixa3.getData());

		assertEquals("40 a 49", faixa4.getLabel());
		assertEquals(2.0, faixa4.getData());

		assertEquals("50 a 59", faixa5.getLabel());
		assertEquals(1.0, faixa5.getData());

		assertEquals("Acima de 60", faixa6.getLabel());
		assertEquals(1.0, faixa6.getData());
		
		data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), null, new Long[]{area.getId()});
		assertEquals(6, data.size());
		data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{area.getId()});
		assertEquals(6, data.size());
	}

	public void testCountMotivoDemissao() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);

		MotivoDemissao motivoFalta = new MotivoDemissao();
		motivoFalta.setMotivo("Falta");
		motivoDemissaoDao.save(motivoFalta);

		MotivoDemissao motivoPediuPraSair = new MotivoDemissao();
		motivoPediuPraSair.setMotivo("Pediu pra Sair");
		motivoDemissaoDao.save(motivoPediuPraSair);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2005), motivoFalta, null, area);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoFalta, null, area);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoPediuPraSair, null, area);

		saveColaborador('M', true, null, outraEmpresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoPediuPraSair, null, area);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2003), motivoPediuPraSair, null, area);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2010), motivoPediuPraSair, null, area);

		Collection<DataGrafico> motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), Arrays.asList(empresa.getId()), null, 2);
		assertEquals(2, motivos.size());
		//passando area
		motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), Arrays.asList(empresa.getId()), new Long[]{area.getId()}, 2);
		assertEquals(2, motivos.size());

		DataGrafico motivo1 = (DataGrafico) motivos.toArray()[0];
		DataGrafico motivo2 = (DataGrafico) motivos.toArray()[1];

		assertEquals("Falta", motivo1.getLabel());
		assertEquals(2.0, motivo1.getData());

		assertEquals("Pediu pra Sair", motivo2.getLabel());
		assertEquals(1.0, motivo2.getData());
		
		motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), null, new Long[]{area.getId()}, 2);
		assertEquals(2, motivos.size());
	}

	public void testCountAdmitidos() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('F', true, DateUtil.criarDataMesAno(02, 03, 2005), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', true, DateUtil.criarDataMesAno(02, 03, 2003), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', true, DateUtil.criarDataMesAno(02, 03, 2011), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2004), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2009), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);

		int count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, null, true);
		assertEquals(3, count);
		
		count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, new Long[]{area.getId()}, true);
		assertEquals(3, count);
	}

	public void testCountDemitidos() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2005), null, null, area);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2003), null, null, area);
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2011), null, null, area);
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2004), null, null, area);
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2009), null, null, area);

		int count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, null, false);
		assertEquals(3, count);
		count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, new Long[]{area.getId()}, false);
		assertEquals(3, count);
	}

	private void saveColaborador(char sexo, boolean desligado, Date dataAdmissao, Empresa empresa, String estadoCivil, Date dataNascimento, char deficiencia, Date dataDesligamento,
			MotivoDemissao motivoDemissao, String vinculo, AreaOrganizacional area) {
		Pessoal pessoal = new Pessoal();
		pessoal.setEstadoCivil(estadoCivil);
		pessoal.setSexo(sexo);
		pessoal.setDataNascimento(dataNascimento);
		pessoal.setDeficiencia(deficiencia);

		Colaborador colaborador = new Colaborador();
		colaborador.setDesligado(desligado);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setEmpresa(empresa);
		colaborador.setPessoal(pessoal);
		colaborador.setDataDesligamento(dataDesligamento);
		colaborador.setMotivoDemissao(motivoDemissao);
		colaborador.setVinculo(vinculo);

		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(area);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaboradorAtual);
	}

	public void testFindByIdSobrescrito() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(new Date());
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
		assertEquals(areaOrganizacional.getId(), colaboradorRetorno.getAreaOrganizacional().getId());
		assertEquals(areaOrganizacional.getNome(), colaboradorRetorno.getAreaOrganizacional().getNome());
	}

	public void testFindColaboradorByIdProjection() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorByIdProjection(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	public void testDesligaColaborador() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataDesligamento(null);
		colaborador.setObservacaoDemissao("");
		colaborador.setDesligado(false);
		colaborador.setMotivoDemissao(null);
		colaborador = colaboradorDao.save(colaborador);

		MotivoDemissao motivo = new MotivoDemissao();
		motivo.setMotivo("motivo");
		motivo = motivoDemissaoDao.save(motivo);

		Date dataDesligamento = new Date();
		colaboradorDao.desligaColaborador(true, dataDesligamento, "desligado", motivo.getId(), colaborador.getId());

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());

		assertEquals(true, colaboradorRetorno.isDesligado());
		assertEquals("desligado", colaboradorRetorno.getObservacaoDemissao());
		assertEquals(DateUtil.formataDiaMesAno(dataDesligamento), DateUtil.formataDiaMesAno(colaboradorRetorno.getDataDesligamento()));
		assertEquals(motivo.getId(), colaboradorRetorno.getMotivoDemissao().getId());
	}

	public void testFindByIdProjectionEmpresa() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	public void testFindByNomeCpfMatricula() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("teste");
		colaborador1.setMatricula("1234");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);

		assertEquals(1, colaboradorDao.findByNomeCpfMatricula(colaborador1, empresa.getId(), false).size());
		assertEquals(2, colaboradorDao.findByNomeCpfMatricula(null, empresa.getId(), false).size());
	}

	public void testFindByNomeCpfMatriculaAndResponsavelArea() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("teste");
		colaborador1.setMatricula("1234");
		colaboradorDao.save(colaborador1);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setResponsavel(colaborador1);
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional1);
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setNome("teste");
		colaborador2.setMatricula("1234");
		colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador2);

		assertEquals(2, colaboradorDao.findByNomeCpfMatriculaAndResponsavelArea(colaborador1, empresa.getId(), null).size());
		assertEquals(1, colaboradorDao.findByNomeCpfMatriculaAndResponsavelArea(colaborador1, empresa.getId(), new Long[]{areaOrganizacional1.getId()}).size());
	}

	public void testSetCodigoColaboradorAC() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(true, colaboradorDao.setCodigoColaboradorAC("codigo", colaborador.getId()));
	}

	public void testFindByCodigoAC() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa));
	}

	public void testFindByCodigoACUsuario() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa));
	}

	public void testFindByCodigoACEmpresaString() {
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("112233");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa.getCodigoAC(), "XXX"));
	}

	public void testFindByFuncaoAmbiente() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setNomeComercial("nomeTeste");
		colaborador = colaboradorDao.save(colaborador);

		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente = ambienteDao.save(ambiente);

		Funcao funcao = FuncaoFactory.getEntity();
		funcao = funcaoDao.save(funcao);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Colaborador> colaboradores = colaboradorDao.findByFuncaoAmbiente(funcao.getId(), ambiente.getId());
		assertEquals(1, colaboradores.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradores.toArray()[0];
		assertEquals(colaborador.getId(), colaboradorTmp.getId());
		assertEquals(colaborador.getNomeComercial(), colaboradorTmp.getNomeComercial());
	}

	public void testFindByFuncaoAmbienteNull() {
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente = ambienteDao.save(ambiente);

		Funcao funcao = FuncaoFactory.getEntity();
		funcao.setId(null);

		Collection<Colaborador> colaboradores = colaboradorDao.findByFuncaoAmbiente(funcao.getId(), ambiente.getId());
		assertEquals(null, colaboradores);
	}
	
	public void testFindFuncaoAmbiente() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setNomeComercial("nomeTeste");
		colaboradorDao.save(colaborador);

		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente.setNome("garagem");
		ambienteDao.save(ambiente);

		Funcao funcao = FuncaoFactory.getEntity();
		funcao.setNome("manobrista");
		funcaoDao.save(funcao);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		historicoColaboradorDao.save(historicoColaborador);

		Colaborador retorno = colaboradorDao.findFuncaoAmbiente(colaborador.getId());

		assertEquals(retorno.getId(), colaborador.getId());
		assertEquals(retorno.getNomeComercial(), colaborador.getNomeComercial());
		assertEquals(retorno.getAmbiente().getNome(), historicoColaborador.getAmbiente().getNome());
		assertEquals(retorno.getFuncao().getNome(), historicoColaborador.getFuncao().getNome());
	}

	public void testFindByAreaOrganizacionalIds() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador1 = getColaborador();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("Pedro Jose");
		colaborador1.setMatricula("12e3456789");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAntiga.setEmpresa(empresa);
		areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.setaMesPosterior(new Date()));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador2);

		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional.getId());

		Colaborador colaboradorBusca = ColaboradorFactory.getEntity(1L);
		colaboradorBusca.setNome("dro j");
		colaboradorBusca.setMatricula("2E3");
		colaboradorBusca.getPessoal().setCpf("000");
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalIds(areaIds, null, 1, 10, colaboradorBusca, null, null, empresa.getId(), false);

		assertEquals(1, colaboradores.size());
		Colaborador retorno = (Colaborador) colaboradores.toArray()[0];
		assertEquals(faixaSalarial, retorno.getFaixaSalarial());
		assertEquals(cargo, retorno.getFaixaSalarial().getCargo());
		assertEquals(grupoOcupacional, retorno.getFaixaSalarial().getCargo().getGrupoOcupacional());
		assertEquals(areaOrganizacional, retorno.getAreaOrganizacional());
		assertEquals("Pedro Jose", retorno.getNome());

		// Teste findByAreaOrganizacionalIds
		Long[] areasIds = new Long[] { areaOrganizacional.getId() };
		colaboradores = colaboradorDao.findByAreaOrganizacionalIds(1, 10, areasIds, null, null, null, null, areaOrganizacional.getEmpresa().getId(), false);
		assertEquals(1, colaboradores.size());

		// Teste findByAreaOrganizacionalIds
		colaboradores = colaboradorDao.findByAreaOrganizacionalIds(areasIds);
		assertEquals(1, colaboradores.size());

		// Teste findByArea
		colaboradores = colaboradorDao.findByArea(areaOrganizacional);
		assertEquals(1, colaboradores.size());
	}

	public void testFindByAreaOrganizacionalIdsPorPeriodoAdmissao() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setEmpresa(empresa);
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Colaborador jose = getColaborador();
		jose.setEmpresa(empresa);
		jose.setNome("jose");
		jose.setMatricula("12e3456789");
		jose.setDataAdmissao(DateUtil.criarDataMesAno(1, 1, 2011));
		colaboradorDao.save(jose);

		Colaborador joao = getColaborador();
		joao.setEmpresa(empresa);
		joao.setNome("joao");
		joao.setMatricula("12e3456999");
		joao.setDataAdmissao(DateUtil.criarDataMesAno(7, 7, 2011));
		colaboradorDao.save(joao);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		HistoricoColaborador historicoJose = HistoricoColaboradorFactory.getEntity();
		historicoJose.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		historicoJose.setColaborador(jose);
		historicoJose.setAreaOrganizacional(areaOrganizacional);
		historicoJose.setFaixaSalarial(faixaSalarial);
		historicoJose.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoJose);

		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity();
		historicoJoao.setData(DateUtil.criarDataMesAno(7, 7, 2011));
		historicoJoao.setColaborador(joao);
		historicoJoao.setAreaOrganizacional(areaOrganizacional);
		historicoJoao.setFaixaSalarial(faixaSalarial);
		historicoJoao.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoJoao);

		Colaborador colaboradorBusca = new Colaborador();
		Date dataAdmissaoIni = DateUtil.criarDataMesAno(6, 6, 2011);
		Date dataAdmissaoFim = DateUtil.criarDataMesAno(8, 8, 2011);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalIds(Arrays.asList(areaOrganizacional.getId()), Arrays.asList(estabelecimento.getId()),null, null, colaboradorBusca, dataAdmissaoIni, dataAdmissaoFim, empresa.getId(), false);
		assertEquals(1, colaboradores.size());
		assertEquals(joao, colaboradores.toArray()[0]);
	}
	
	public void testFindByAreaEstabelecimento() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAtual = areaOrganizacionalDao.save(areaOrganizacionalAtual);

		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setAreaOrganizacional(areaOrganizacionalAntiga);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaEstabelecimento(areaOrganizacionalAtual.getId(), estabelecimentoAtual.getId());

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(areaOrganizacionalAtual, colaboradorDoBanco.getAreaOrganizacional());
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByEstabelecimento() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByEstabelecimento(new Long[] { estabelecimentoAtual.getId() });

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testGetCountAtivosByEstabelecimento() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Integer count = colaboradorDao.getCountAtivosByEstabelecimento(estabelecimentoAtual.getId());
		assertEquals((Integer) 1, count);
	}

	public void testFindByIdHistoricoProjection() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setMatricula("123456");
		colaboradorDao.save(colaborador);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setFaixaSalarial(faixaSalarial2);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Colaborador colaboradorTmp = colaboradorDao.findByIdHistoricoProjection(colaborador.getId());

		assertEquals(colaborador, colaboradorTmp);
		assertEquals(colaborador.getMatricula(), colaboradorTmp.getMatricula());
		assertEquals(faixaSalarial1.getDescricao(), colaboradorTmp.getHistoricoColaborador().getFaixaSalarial().getDescricao());
	}

	public void testFindByIdHistoricoAtualIds() {
		Colaborador colaborador = getColaborador();
		colaborador.setNome("TESTE");
		colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setFaixaSalarial(faixaSalarial2);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Collection<Long> ids = new ArrayList<Long>();
		ids.add(colaborador.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findByIdHistoricoAtual(ids);
		assertEquals(1, colaboradors.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradors.toArray()[0];
		assertEquals(colaborador.getNome(), colaboradorTmp.getNome());
		assertEquals("Teste I", colaboradorTmp.getFaixaSalarial().getDescricao());
		assertEquals(areaOrganizacional, colaboradorTmp.getAreaOrganizacional());
	}
	public void testFindByIdHistoricoAtual() {
		Colaborador colaborador = getColaborador();
		colaborador.setNome("TESTE");
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);
		
		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);
		
		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setFaixaSalarial(faixaSalarial2);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);
		
		boolean exibirSomenteAtivos = true;
		
		Colaborador colaboradorResult = colaboradorDao.findByIdHistoricoAtual(colaborador.getId(), exibirSomenteAtivos);
		
		Colaborador colaboradorTmp = colaboradorResult;
		assertEquals(colaboradorResult.getNome(), colaboradorTmp.getNome());
		assertEquals("Teste I", colaboradorTmp.getFaixaSalarial().getDescricao());
		assertEquals(areaOrganizacional, colaboradorTmp.getAreaOrganizacional());
	}

	public void testFindByAreasOrganizacionaisEstabelecimentos() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setNome("francisco");
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("francisco");
		colaborador2.setEmpresa(empresa);
		colaborador2.setDesligado(true);
		colaborador2 = colaboradorDao.save(colaborador2);

		AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAtual = areaOrganizacionalDao.save(areaOrganizacionalAtual);

		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);

		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacionalAtual.getId());

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setAreaOrganizacional(areaOrganizacionalAntiga);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaborador2.setEstabelecimento(estabelecimentoAtual);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreasOrganizacionaisEstabelecimentos(areasIds, estabelecimentosIds, "francisco", null);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(areaOrganizacionalAtual, colaboradorDoBanco.getAreaOrganizacional());
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}
	
	public void testFindByAreaOrganizacionalEstabelecimento() 
	{
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setDesligado(false);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Colaborador colaborador2 = getColaborador();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDesligado(true);
		colaboradorDao.save(colaborador2);
		
		AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalAtual);
		
		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalAntiga);
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacionalAtual.getId());
		
		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimentoAtual);
		
		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimentoAntigo);
		
		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());
		
		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);
		
		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setAreaOrganizacional(areaOrganizacionalAntiga);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaborador2.setEstabelecimento(estabelecimentoAtual);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);
		
		Collection<Colaborador> colaboradoresAtivos = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.ATIVO);
		Collection<Colaborador> colaboradoresDesligados = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.DESLIGADO);
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.TODOS);
		
		assertEquals(2, colaboradores.size());
		
		Colaborador colaboradorAtivo = (Colaborador) colaboradoresAtivos.toArray()[0];
		
		assertEquals(1, colaboradoresAtivos.size());
		assertEquals(colaborador.getId(), colaboradorAtivo.getId());
		
		Colaborador colaboradorInativo = (Colaborador) colaboradoresDesligados.toArray()[0];
		
		assertEquals(1, colaboradoresDesligados.size());
		assertEquals(colaborador2.getId(), colaboradorInativo.getId());
	}

	public void testFindByCargoIdsEstabelecimentoIds() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setNome("francisco");

		colaborador = colaboradorDao.save(colaborador);

		Cargo cargoAtual = CargoFactory.getEntity();
		cargoAtual = cargoDao.save(cargoAtual);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
		faixaSalarialAtual.setCargo(cargoAtual);
		faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

		Collection<Long> cargosIds = new ArrayList<Long>();
		cargosIds.add(cargoAtual.getId());

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarialAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorFuturo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFuturo.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		historicoColaboradorFuturo.setColaborador(colaborador);
		historicoColaboradorFuturo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorFuturo = historicoColaboradorDao.save(historicoColaboradorFuturo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds, "franci", null);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByGrupoOcupacionalIdsEstabelecimentoIds() {
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
		grupoOcupacionalIds.add(grupoOcupacional.getId());

		Cargo cargoAtual = CargoFactory.getEntity();
		cargoAtual.setGrupoOcupacional(grupoOcupacional);
		cargoAtual = cargoDao.save(cargoAtual);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
		faixaSalarialAtual.setCargo(cargoAtual);
		faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarialAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorFuturo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFuturo.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		historicoColaboradorFuturo.setColaborador(colaborador);
		historicoColaboradorFuturo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorFuturo = historicoColaboradorDao.save(historicoColaboradorFuturo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentosIds);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByIdProjectionUsuario() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());

		assertNotNull(colaborador);
		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	public void testFindAreaOrganizacionalByGruposOrAreas() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Colaborador colaborador = getEntity();
		colaborador.getPessoal().setSexo('F');
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		// colaborador desligado
		Colaborador colaborador2 = getEntity();
		colaborador2.setDesligado(true);
		colaborador2 = colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);

		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();

		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		grupoOcupacionalIds.add(grupoOcupacional.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, null, null, Sexo.FEMININO, null, null, null, SituacaoColaborador.ATIVO, null);

		assertEquals(1, colaboradors.size());
	}
	
	public void testFindAreaOrganizacionalByAreasAndPeriodoDataAdmissao() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = getEntity();
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 2010));
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		// colaborador fora da data de admissao
		Colaborador colaborador2 = getEntity();
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
		
		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		grupoOcupacionalIds.add(grupoOcupacional.getId());
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, null);
		
		assertEquals(1, colaboradors.size());
	}
	
	public void testFindAreaOrganizacionalByAreasAndCargos() {
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setGrupoOcupacional(grupoOcupacional);
		cargo1 = cargoDao.save(cargo1);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);
		
		Colaborador colaborador1 = getEntity();
		colaborador1.setNome("Arnaldo");
		colaborador1.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador1 = colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador1);
		historicoColaborador.setFaixaSalarial(faixaSalarial1);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setGrupoOcupacional(grupoOcupacional);
		cargo2 = cargoDao.save(cargo2);
		
		FaixaSalarial faixaSalaria2 = FaixaSalarialFactory.getEntity();
		faixaSalaria2.setCargo(cargo2);
		faixaSalaria2 = faixaSalarialDao.save(faixaSalaria2);
		
		Colaborador colaborador2 = getEntity();
		colaborador2.setNome("Tibira");
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setFaixaSalarial(faixaSalaria2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// Parâmetros do find
		Collection<Long> cargoIds = new ArrayList<Long>();
		cargoIds.add(cargo1.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, null);
		
		assertEquals(1, colaboradors.size());
		assertEquals(colaborador1.getNome(), ((Colaborador)colaboradors.toArray()[0]).getNome());
	}
	
	public void testFindAreaOrganizacionalByAreasAndEnviadosParaAC() 
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1 = cargoDao.save(cargo1);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);
		
		Colaborador colaborador1 = getEntity();
		colaborador1.setNome("Arnaldo");
		colaborador1.setNaoIntegraAc(false);
		colaborador1.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador1 = colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador1);
		historicoColaborador.setFaixaSalarial(faixaSalarial1);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2 = cargoDao.save(cargo2);
		
		FaixaSalarial faixaSalaria2 = FaixaSalarialFactory.getEntity();
		faixaSalaria2.setCargo(cargo2);
		faixaSalaria2 = faixaSalarialDao.save(faixaSalaria2);
		
		Colaborador colaborador2 = getEntity();
		colaborador2.setNome("Tibira");
		colaborador2.setNaoIntegraAc(true);
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setFaixaSalarial(faixaSalaria2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// Parâmetros do find
		Collection<Long> cargoIds = new ArrayList<Long>();
		cargoIds.add(cargo1.getId());
		cargoIds.add(cargo2.getId());

		Character todosColaboradores = '1';
		Character colaboradoresNaoEnviadosParaAC = '2';
		Character colaboradoresEnviadosParaAC = '3';

		Collection<Colaborador> retorno1 = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, todosColaboradores);
		Collection<Colaborador> retorno2 = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, colaboradoresNaoEnviadosParaAC);
		Collection<Colaborador> retorno3 = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, colaboradoresEnviadosParaAC);
		
		assertEquals(2, retorno1.size());
		
		assertEquals(1, retorno2.size());
		assertEquals(colaborador2.getNome(), ((Colaborador)retorno2.toArray()[0]).getNome());

		assertEquals(1, retorno3.size());
		assertEquals(colaborador1.getNome(), ((Colaborador)retorno3.toArray()[0]).getNome());
	}
	
	public void testFindAreaOrganizacionalBySemDeficiencia()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador responsavel = getEntity();
		colaboradorDao.save(responsavel);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacionalDao.save(grupoOcupacional);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Pessoal pessoal = new Pessoal();
		pessoal.setDeficiencia(Deficiencia.FISICA);
		
		Colaborador colaborador = getEntity();
		colaborador.setNome("Joao");
		colaborador.setPessoal(pessoal);
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Pessoal pessoal2 = new Pessoal();
		pessoal2.setDeficiencia(Deficiencia.SEM_DEFICIENCIA);
		
		Colaborador colaborador2 = getEntity();
		colaborador2.setNome("Maria");
		colaborador2.setPessoal(pessoal2);
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
		
		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		grupoOcupacionalIds.add(grupoOcupacional.getId());
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "3", null, null, SituacaoColaborador.ATIVO, null);
		
		assertEquals(1, colaboradors.size());
		assertEquals(colaborador2.getNome(), ((Colaborador)colaboradors.toArray()[0]).getNome());
	}
	
	public void testFindAreaOrganizacionalByComDeficiencia()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);
		
		Pessoal pessoal = new Pessoal();
		pessoal.setDeficiencia(Deficiencia.AUDITIVA);
		
		Colaborador colaborador = getEntity();
		colaborador.setNome("Chico");
		colaborador.setPessoal(pessoal);
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		// colaborador fora da data de admissao
		Pessoal pessoal2 = new Pessoal();
		pessoal2.setDeficiencia(Deficiencia.SEM_DEFICIENCIA);
		
		Colaborador colaborador2 = getEntity();
		colaborador2.setNome("Marcia");
		colaborador2.setPessoal(pessoal2);
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 2011));
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
		
		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		grupoOcupacionalIds.add(grupoOcupacional.getId());
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "2", null, null, SituacaoColaborador.ATIVO, null);
		
		assertEquals(1, colaboradors.size());
		assertEquals(colaborador.getNome(), ((Colaborador)colaboradors.toArray()[0]).getNome());
	}
	
	public void testFindAreaOrganizacionalBySemDeficienciaComDeficiencia()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);
		
		Pessoal pessoal = new Pessoal();
		pessoal.setDeficiencia(Deficiencia.FISICA);
		
		Colaborador colaborador = getEntity();
		colaborador.setPessoal(pessoal);
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Colaborador colaborador2 = getEntity();
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador2 = colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Colaborador colaborador3 = getEntity();
		colaborador3.setDataAdmissao(DateUtil.criarDataMesAno(31, 01, 2011));
		colaborador3 = colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador3.setEstabelecimento(estabelecimento);
		historicoColaborador3.setColaborador(colaborador3);
		historicoColaborador3.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
		
		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		grupoOcupacionalIds.add(grupoOcupacional.getId());
		
		Collection<Colaborador> colaboradors1 = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, DateUtil.criarDataMesAno(31, 01, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "1", null, null, SituacaoColaborador.ATIVO, null);
		Collection<Colaborador> colaboradors2 = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "1", null, null, SituacaoColaborador.ATIVO, null);
		
		assertEquals("Admissao a partir de 31/01/11",3, colaboradors1.size());
		assertEquals("Admissao a partir de 01/02/11",2, colaboradors2.size());
	}

	public void testFindAreaOrganizacionalByGruposOrAreasCamposExtras() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		CamposExtras camposExtras = CamposExtrasFactory.getEntity();
		camposExtras.setTexto1("texto1");
		camposExtras.setTexto2("texto2");
		camposExtras.setTexto3("texto3");
		camposExtras.setTexto4("texto4");
		camposExtras.setTexto5("texto5");
		camposExtras.setTexto6("texto6");
		camposExtras.setTexto7("texto7");
		camposExtras.setTexto8("texto8");
		camposExtras.setTexto9("texto9");
		camposExtras.setTexto10("texto10");

		Date date1 = DateUtil.criarDataMesAno(1, 1, 1000);
		Date date2 = DateUtil.criarDataMesAno(2, 2, 2000);
		Date date3 = DateUtil.criarDataMesAno(3, 3, 3000);
		camposExtras.setData1(date1);
		camposExtras.setData2(date2);
		camposExtras.setData3(date3);

		camposExtras.setValor1(10.0);
		camposExtras.setValor2(20.0);

		camposExtras.setNumero1(55);
		camposExtrasDao.save(camposExtras);

		Colaborador colaborador = getEntity();
		colaborador.setCamposExtras(camposExtras);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();

		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, null, camposExtras, null, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null);
		assertEquals(1, colaboradors.size());

		CamposExtras camposExtrasBusca = new CamposExtras();

		camposExtrasBusca = CamposExtrasFactory.getEntity();
		camposExtrasBusca.setData1(DateUtil.criarDataMesAno(1, 1, 1000));
		camposExtrasBusca.setData2(DateUtil.criarDataMesAno(2, 2, 2000));
		camposExtrasBusca.setData3(DateUtil.criarDataMesAno(3, 3, 3000));

		camposExtrasBusca.setData1Fim(DateUtil.criarDataMesAno(3, 3, 1000));
		camposExtrasBusca.setData2Fim(DateUtil.criarDataMesAno(4, 4, 2000));
		camposExtrasBusca.setData3Fim(DateUtil.criarDataMesAno(5, 5, 3000));

		camposExtrasBusca.setValor1(5.0);
		camposExtrasBusca.setValor2(10.0);

		camposExtrasBusca.setValor1Fim(20.0);
		camposExtrasBusca.setValor2Fim(30.0);

		camposExtrasBusca.setNumero1(40);
		camposExtrasBusca.setNumero1Fim(60);

		colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, null, camposExtrasBusca, null, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null);
		assertEquals(1, colaboradors.size());

		// Parâmetros do find
		camposExtrasBusca = CamposExtrasFactory.getEntity();

		camposExtrasBusca.setData1Fim(DateUtil.criarDataMesAno(3, 3, 1000));
		camposExtrasBusca.setData2Fim(DateUtil.criarDataMesAno(4, 4, 2000));
		camposExtrasBusca.setData3Fim(DateUtil.criarDataMesAno(5, 5, 3000));

		camposExtrasBusca.setValor1Fim(20.0);
		camposExtrasBusca.setValor2Fim(30.0);

		camposExtrasBusca.setNumero1Fim(60);

		colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, null, camposExtrasBusca, null, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null);
		assertEquals(1, colaboradors.size());
	}

	public void testFindColaboradoresMotivoDemissao() {
		prepareFindColaboradoresMotivo();

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataFim = DateUtil.criarDataMesAno(30, 12, 2007);

		Long[] estabelecimentoIds = new Long[] { estabelecimento1.getId() };
		Long[] areaIds = new Long[] { areaOrganizacional1.getId() };
		Long[] cargoIds = new Long[] { cargo1.getId() };

		Collection<Colaborador> retorno = colaboradorDao.findColaboradoresMotivoDemissao(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, "M");

		assertEquals(1, retorno.size());
	}

	public void testFindColaboradoresMotivoDemissaoQuantidade() {
		prepareFindColaboradoresMotivo();

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataFim = DateUtil.criarDataMesAno(30, 12, 2007);

		Long[] estabelecimentoIds = new Long[] { estabelecimento1.getId() };
		Long[] areaIds = new Long[] { areaOrganizacional1.getId() };
		Long[] cargoIds = new Long[] { cargo1.getId() };

		List<Object[]> retorno = colaboradorDao.findColaboradoresMotivoDemissaoQuantidade(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim);

		assertEquals(1, retorno.size());
	}

	private void prepareFindColaboradoresMotivo() {
		estabelecimento1.setNome("estabelecimento");
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setNome("estabelecimento");
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);

		areaOrganizacional1.setResponsavel(responsavel);
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setResponsavel(responsavel);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		cargo1.setGrupoOcupacional(grupoOcupacional);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setGrupoOcupacional(grupoOcupacional);
		cargo2 = cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarial2 = faixaSalarialDao.save(faixaSalarial2);

		MotivoDemissao motivo = new MotivoDemissao();
		motivo.setMotivo("motivo");
		motivo = motivoDemissaoDao.save(motivo);

		// vem na consulta
		Colaborador c1 = ColaboradorFactory.getEntity();
		c1.setMotivoDemissao(motivo);
		c1.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 1945));
		c1.setDataDesligamento(DateUtil.criarDataMesAno(01, 06, 1945));
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(areaOrganizacional1);
		hc1.setEstabelecimento(estabelecimento1);
		hc1.setFaixaSalarial(faixaSalarial1);
		hc1 = historicoColaboradorDao.save(hc1);

		// não vem na consulta pois não tem data desligamento
		Colaborador c2 = ColaboradorFactory.getEntity();
		c2.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 1945));
		c2.setDataDesligamento(null);
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(areaOrganizacional1);
		hc2.setEstabelecimento(estabelecimento1);
		hc2.setFaixaSalarial(faixaSalarial2);
		hc2 = historicoColaboradorDao.save(hc2);

		// não vem na consulta pois a data de desligamento é depois do parametro
		// passado na consulta
		Colaborador c3 = ColaboradorFactory.getEntity();
		c3.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c3.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2008));
		c3 = colaboradorDao.save(c3);

		HistoricoColaborador hc3 = HistoricoColaboradorFactory.getEntity();
		hc3.setColaborador(c3);
		hc3.setAreaOrganizacional(areaOrganizacional1);
		hc3.setEstabelecimento(estabelecimento1);
		hc3.setFaixaSalarial(faixaSalarial2);
		hc3 = historicoColaboradorDao.save(hc3);

		// não vem na consulta pois tem um estabelecimento diferente
		Colaborador c4 = ColaboradorFactory.getEntity();
		c4.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c4.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2007));
		c4 = colaboradorDao.save(c4);

		HistoricoColaborador hc4 = HistoricoColaboradorFactory.getEntity();
		hc4.setColaborador(c4);
		hc4.setAreaOrganizacional(areaOrganizacional1);
		hc4.setEstabelecimento(estabelecimento2);
		hc4.setFaixaSalarial(faixaSalarial2);
		hc4 = historicoColaboradorDao.save(hc4);

		// não vem na consulta pois tem uma area diferente
		Colaborador c5 = ColaboradorFactory.getEntity();
		c5.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c5.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2007));
		c5 = colaboradorDao.save(c5);

		HistoricoColaborador hc5 = HistoricoColaboradorFactory.getEntity();
		hc5.setColaborador(c5);
		hc5.setAreaOrganizacional(areaOrganizacional2);
		hc5.setEstabelecimento(estabelecimento1);
		hc5.setFaixaSalarial(faixaSalarial2);
		hc5 = historicoColaboradorDao.save(hc5);

		// não vem na consulta pois tem um cargo diferente
		Colaborador c6 = ColaboradorFactory.getEntity();
		c6.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c6.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2007));
		c6 = colaboradorDao.save(c6);

		HistoricoColaborador hc6 = HistoricoColaboradorFactory.getEntity();
		hc6.setColaborador(c6);
		hc6.setAreaOrganizacional(areaOrganizacional1);
		hc6.setEstabelecimento(estabelecimento1);
		hc6.setFaixaSalarial(faixaSalarial2);
		hc6 = historicoColaboradorDao.save(hc6);
	}

	public void testFindProjecaoSalarialByHistoricoColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		estabelecimentoIds.add(estabelecimento.getId());

		Collection<Long> grupoIds = new ArrayList<Long>();
		grupoIds.add(grupoOcupacional.getId());

		Collection<Long> cargoIds = new ArrayList<Long>();
		cargoIds.add(cargo.getId());

		Collection<Colaborador> retorno = colaboradorDao.findProjecaoSalarialByHistoricoColaborador(new Date(), estabelecimentoIds, null, grupoIds, cargoIds, "2", empresa.getId());

		assertEquals(colaboradors.size(), retorno.size());
	}

	public void testFindProjecaoSalarialByTabelaReajusteColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("nome");
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacional);
		reajusteColaborador.setEstabelecimentoProposto(estabelecimento);
		reajusteColaborador.setColaborador(colaborador);
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarial);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		estabelecimentoIds.add(estabelecimento.getId());

		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional.getId());

		Collection<Colaborador> retorno = colaboradorDao.findProjecaoSalarialByTabelaReajusteColaborador(tabelaReajusteColaborador.getId(), new Date(), estabelecimentoIds, areaIds, null, null, "1",
				empresa.getId());

		assertEquals(colaboradors.size(), retorno.size());
	}

	public void testSetRespondeuEntrevista() {
		Colaborador colaborador = ColaboradorFactory.getEntity();

		colaborador = colaboradorDao.save(colaborador);

		colaboradorDao.setRespondeuEntrevista(colaborador.getId());
	}

	public void testFindAllSelect() {
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("B Colab 1");
		colaborador1.setEmpresa(empresa1);
		colaborador1 = colaboradorDao.save(colaborador1);

		HistoricoColaborador historicoColaborador1_1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1_1.setColaborador(colaborador1);
		historicoColaborador1_1.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaborador1_1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1_1);

		HistoricoColaborador historicoColaborador1_2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1_2.setColaborador(colaborador1);
		historicoColaborador1_2.setData(DateUtil.criarDataMesAno(01, 01, 2013));
		historicoColaborador1_2.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador1_2);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("A Colab 2");
		colaborador2.setEmpresa(empresa1);
		colaborador2 = colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador2_1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2_1.setColaborador(colaborador2);
		historicoColaborador2_1.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaborador2_1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2_1);
		
		HistoricoColaborador historicoColaborador2_2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2_2.setColaborador(colaborador2);
		historicoColaborador2_2.setData(DateUtil.criarDataMesAno(01, 01, 2013));
		historicoColaborador2_2.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador2_2);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setNome("Colab 3");
		colaborador3.setEmpresa(empresa1);
		colaborador3.setDesligado(true);
		colaborador3 = colaboradorDao.save(colaborador3);

		HistoricoColaborador historicoColaborador3_1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3_1.setColaborador(colaborador3);
		historicoColaborador3_1.setData(DateUtil.criarDataMesAno(01, 01, 2013));
		historicoColaborador3_1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador3_1);
		
		Colaborador colaborador4 = ColaboradorFactory.getEntity();
		colaborador4.setNome("Colab 4");
		colaborador4.setEmpresa(empresa1);
		colaborador4.setEmpresa(empresa2);
		colaborador4 = colaboradorDao.save(colaborador4);
		
		HistoricoColaborador historicoColaborador4_1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador4_1.setColaborador(colaborador4);
		historicoColaborador4_1.setData(DateUtil.criarDataMesAno(01, 01, 2013));
		historicoColaborador4_1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador4_1);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findAllSelect(empresa1.getId(), "nome");
		
		assertEquals(2, colaboradores.size());
		assertEquals(colaborador2.getNome(), ((Colaborador)colaboradores.toArray()[0]).getNome());
		assertEquals(colaborador1.getNome(), ((Colaborador)colaboradores.toArray()[1]).getNome());
	}
	
	public void testFindAllSelectByIds() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setDesligado(true);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDesligado(false);
		colaboradorDao.save(colaborador2);
		
		assertEquals(1, colaboradorDao.findAllSelect(Arrays.asList(colaborador1.getId(), colaborador2.getId()) , true).size());
	}

	public void testReligaColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataDesligamento(new Date());
		colaborador.setDesligado(true);
		colaborador.setObservacaoDemissao("demissao");
		colaboradorDao.save(colaborador);

		colaboradorDao.religaColaborador(colaborador.getId());
		Colaborador colabTmp = colaboradorDao.findColaboradorById(colaborador.getId());
		assertNull(colabTmp.getDataDesligamento());
		assertEquals("", colabTmp.getObservacaoDemissao());
		assertFalse(colabTmp.isDesligado());
	}

	public void testFindByIdDadosBasicos() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataDesligamento(new Date());
		colaborador.setDesligado(true);
		colaborador.setObservacaoDemissao("demissao");
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		assertEquals(colaborador, colaboradorDao.findByIdDadosBasicos(colaborador.getId(), StatusRetornoAC.CONFIRMADO));
	}
	
	public void testFindColaboradoresByArea() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataDesligamento(new Date());
		colaborador.setDesligado(true);
		colaborador.setObservacaoDemissao("demissao");
		colaborador.setNome("joao");
		colaborador.setMatricula("123456");
		colaborador.setNomeComercial("joao comercial");
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaborador.setAreaOrganizacional(area);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		
		historicoColaboradorDao.save(historicoColaborador);
		
		assertEquals(1, colaboradorDao.findColaboradoresByArea(new Long[]{area.getId()}, "joao", "123456", empresa.getId(), null).size());
		assertEquals(1, colaboradorDao.findColaboradoresByArea(null, null, null, empresa.getId(), "comercial").size());
		assertEquals(0, colaboradorDao.findColaboradoresByArea(null, null, null, empresa.getId(), "suporte").size());
	}

	public void testFindAllSelects() {
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);

		Empresa urbana = EmpresaFactory.getEmpresa();
		empresaDao.save(urbana);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(vega);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(urbana);
		colaboradorDao.save(colaborador2);

		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(vega);
		colaborador3.setDesligado(true);
		colaboradorDao.save(colaborador3);

		Long[] empresaIds = new Long[] { vega.getId(), urbana.getId() };

		assertEquals(2, colaboradorDao.findAllSelect(SituacaoColaborador.ATIVO, empresaIds).size());
		assertEquals(1, colaboradorDao.findAllSelect(SituacaoColaborador.DESLIGADO, empresaIds).size());
		assertEquals(3, colaboradorDao.findAllSelect(SituacaoColaborador.TODOS, empresaIds).size());
	}

	public void testSetMatriculaColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("1234567888");
		colaborador.setMatricula("");
		colaborador = colaboradorDao.save(colaborador);

		String matricula = "123456789";
		colaboradorDao.setMatriculaColaborador(empresa.getId(), colaborador.getCodigoAC(), matricula);

		Colaborador colaboradorTmp = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());
		assertEquals(matricula, colaboradorTmp.getMatricula());
	}

	public void testSetMatriculaColaboradorPorId() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setMatricula("");
		colaborador = colaboradorDao.save(colaborador);

		String matricula = "123456789";
		colaboradorDao.setMatriculaColaborador(colaborador.getId(), matricula);

		Colaborador colaboradorTmp = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());
		assertEquals(matricula, colaboradorTmp.getMatricula());
	}

	public void testFindByCodigoACEmpresaCodigoAC() {
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("fs444ADSF54");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("123ASDF55");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoACEmpresaCodigoAC(colaborador.getCodigoAC(), colaborador.getEmpresa().getCodigoAC(), "XXX"));
		assertNull(colaboradorDao.findByCodigoACEmpresaCodigoAC("998", "889", "YYY"));//o ac depende desse null
	}

	public void testFindColaboradorById() {
		Estado estado = EstadoFactory.getEntity(1L);
		estado.setSigla("CE");

		Habilitacao habilitacao = new Habilitacao();
		habilitacao.setNumeroHab("123");

		Endereco endereco = new Endereco();
		endereco.setCep("1234");

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.getPessoal().getCtps().setCtpsUf(estado);
		colaborador.setHabilitacao(habilitacao);
		colaborador.setEndereco(endereco);
		colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());
		assertEquals(colaborador, colaboradorRetorno);
		assertEquals(habilitacao.getNumeroHab(), colaboradorRetorno.getHabilitacao().getNumeroHab());
		assertEquals(endereco.getCep(), colaboradorRetorno.getEndereco().getCep());
	}

	public void testUpdateInfoPessoais() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.getEndereco().setLogradouro("logradouro");
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorUpdate = ColaboradorFactory.getEntity();
		colaboradorUpdate.setId(colaborador.getId());
		colaboradorUpdate.getEndereco().setLogradouro("teste");

		colaboradorDao.updateInfoPessoais(colaboradorUpdate);

		Colaborador colaboradorTmp = colaboradorDao.findColaboradorById(colaborador.getId());

		assertEquals("teste", colaboradorTmp.getEndereco().getLogradouro());
	}

	public void testFindByUsuarioColaborador() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador.getId(), colaboradorDao.findByUsuario(usuario.getId()));
	}

	public void testFindByUsuarioColaboradorNull() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(null, colaboradorDao.findByUsuario(15984441L));
	}

	public void testFindByIdComHistorico() {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 3000));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdComHistorico(colaborador.getId(), null);

		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	public void testGetCountAtivos() {
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaboradorAtivoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorAtivoDentroDaConsulta.setEmpresa(empresa);
		colaboradorAtivoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorAtivoDentroDaConsulta.setDesligado(false);
		colaboradorDao.save(colaboradorAtivoDentroDaConsulta);

		Colaborador colaboradorDesligadoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorDesligadoDentroDaConsulta.setEmpresa(empresa);
		colaboradorDesligadoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorDesligadoDentroDaConsulta.setDesligado(true);
		colaboradorDesligadoDentroDaConsulta.setDataDesligamento(hoje);
		colaboradorDao.save(colaboradorDesligadoDentroDaConsulta);

		Colaborador colaboradorDesligadoForaDaConsulta = ColaboradorFactory.getEntity();
		colaboradorDesligadoForaDaConsulta.setEmpresa(empresa);
		colaboradorDesligadoForaDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorDesligadoForaDaConsulta.setDesligado(true);
		colaboradorDesligadoForaDaConsulta.setDataDesligamento(dataTresMesesAtras.getTime());
		colaboradorDao.save(colaboradorDesligadoForaDaConsulta);

		assertEquals(new Integer(2), colaboradorDao.getCountAtivos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), new Long[]{empresa.getId()}));

	}
	
	public void testCountAdmitidosSemTurnover() {
		
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaboradorAtivoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorAtivoDentroDaConsulta.setEmpresa(empresa);
		colaboradorAtivoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorAtivoDentroDaConsulta.setDesligado(false);
		colaboradorDao.save(colaboradorAtivoDentroDaConsulta);
		
		HistoricoColaborador histColaboradorAtivoDentroDaConsulta = HistoricoColaboradorFactory.getEntity();
		histColaboradorAtivoDentroDaConsulta.setColaborador(colaboradorAtivoDentroDaConsulta);
		histColaboradorAtivoDentroDaConsulta.setStatus(StatusRetornoAC.CONFIRMADO);
		histColaboradorAtivoDentroDaConsulta.setData(dataTresMesesAtras.getTime());
		historicoColaboradorDao.save(histColaboradorAtivoDentroDaConsulta);
		
		Colaborador colaboradorDentroDaConsulta2 = ColaboradorFactory.getEntity();
		colaboradorDentroDaConsulta2.setEmpresa(empresa);
		colaboradorDentroDaConsulta2.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorDentroDaConsulta2.setDesligado(false);
		colaboradorDao.save(colaboradorDentroDaConsulta2);
		
		HistoricoColaborador histColaboradorDentroDaConsulta2 = HistoricoColaboradorFactory.getEntity();
		histColaboradorDentroDaConsulta2.setColaborador(colaboradorDentroDaConsulta2);
		histColaboradorDentroDaConsulta2.setStatus(StatusRetornoAC.CONFIRMADO);
		histColaboradorDentroDaConsulta2.setData(dataTresMesesAtras.getTime());
		historicoColaboradorDao.save(histColaboradorDentroDaConsulta2);
		
		assertEquals(new Integer(2), colaboradorDao.countAdmitidosDemitidosTurnover(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa, null, true));
	}

	public void testCountAdmitidosComTurnover() {
		
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setTurnoverPorSolicitacao(true);
		empresaDao.save(empresa);
		
		MotivoSolicitacao motivoTurnover = MotivoSolicitacaoFactory.getEntity();
		motivoTurnover.setDescricao("Substituição");
		motivoTurnover.setTurnover(true);
		motivoSolicitacaoDao.save(motivoTurnover);

		MotivoSolicitacao motivoSemTurnover = MotivoSolicitacaoFactory.getEntity();
		motivoSemTurnover.setDescricao("Aumento");
		motivoSemTurnover.setTurnover(false);
		motivoSolicitacaoDao.save(motivoSemTurnover);
		
		Candidato candidatoTurnover = CandidatoFactory.getCandidato();
		candidatoDao.save(candidatoTurnover);

		Candidato candidatoSemTurnover = CandidatoFactory.getCandidato();
		candidatoDao.save(candidatoSemTurnover);
		
		Solicitacao solicitacaoTurnover = SolicitacaoFactory.getSolicitacao();
		solicitacaoTurnover.setMotivoSolicitacao(motivoTurnover);
		solicitacaoDao.save(solicitacaoTurnover);
		
		Solicitacao solicitacaoSemTurnover = SolicitacaoFactory.getSolicitacao();
		solicitacaoSemTurnover.setMotivoSolicitacao(motivoSemTurnover);
		solicitacaoDao.save(solicitacaoSemTurnover);
		
		CandidatoSolicitacao candSolicTurnover = CandidatoSolicitacaoFactory.getEntity(); 
		candSolicTurnover.setCandidato(candidatoTurnover);
		candSolicTurnover.setSolicitacao(solicitacaoTurnover);
		candidatoSolicitacaoDao.save(candSolicTurnover);

		CandidatoSolicitacao candSolicSemTurnover = CandidatoSolicitacaoFactory.getEntity(); 
		candSolicSemTurnover.setCandidato(candidatoSemTurnover);
		candSolicSemTurnover.setSolicitacao(solicitacaoSemTurnover);
		candidatoSolicitacaoDao.save(candSolicSemTurnover);
		
		FaixaSalarial dentista01 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(dentista01);
		
		Colaborador colaboradorAtivoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorAtivoDentroDaConsulta.setEmpresa(empresa);
		colaboradorAtivoDentroDaConsulta.setCandidato(candidatoSemTurnover);
		colaboradorAtivoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorAtivoDentroDaConsulta.setDesligado(false);
		colaboradorDao.save(colaboradorAtivoDentroDaConsulta);
		
		HistoricoColaborador histColaboradorAtivoDentroDaConsulta = HistoricoColaboradorFactory.getEntity();
		histColaboradorAtivoDentroDaConsulta.setColaborador(colaboradorAtivoDentroDaConsulta);
		histColaboradorAtivoDentroDaConsulta.setStatus(StatusRetornoAC.CONFIRMADO);
		histColaboradorAtivoDentroDaConsulta.setData(dataTresMesesAtras.getTime());
		historicoColaboradorDao.save(histColaboradorAtivoDentroDaConsulta);
		
		Colaborador colaboradorDentroDaConsulta2 = ColaboradorFactory.getEntity();
		colaboradorDentroDaConsulta2.setEmpresa(empresa);
		colaboradorDentroDaConsulta2.setCandidato(candidatoTurnover);
		colaboradorDentroDaConsulta2.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorDentroDaConsulta2.setDesligado(false);
		colaboradorDao.save(colaboradorDentroDaConsulta2);
		
		HistoricoColaborador histColaboradorDentroDaConsulta2 = HistoricoColaboradorFactory.getEntity();
		histColaboradorDentroDaConsulta2.setColaborador(colaboradorDentroDaConsulta2);
		histColaboradorDentroDaConsulta2.setStatus(StatusRetornoAC.CONFIRMADO);
		histColaboradorDentroDaConsulta2.setData(dataTresMesesAtras.getTime());
		historicoColaboradorDao.save(histColaboradorDentroDaConsulta2);
		
		assertEquals(new Integer(1), colaboradorDao.countAdmitidosDemitidosTurnover(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa, null, true));
	}
	
	public void testGetCountAtivosDataBase() {
		Date dataBase = DateUtil.criarDataMesAno(01, 02, 2011);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2012), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2012), empresa2, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area);

		assertEquals(1, colaboradorDao.getCountAtivos(dataBase, Arrays.asList(empresa.getId()), null));
		assertEquals(1, colaboradorDao.getCountAtivos(dataBase, Arrays.asList(empresa.getId()), new Long[]{area.getId()}));
		assertEquals(1, colaboradorDao.getCountAtivos(dataBase, new ArrayList<Long>(), new Long[]{area.getId()}));
	}

	public void testFindAniversariantes() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresaDao.save(empresa);

		int mes = 2;

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(01, 01, 2000));
		colaboradorDao.save(colaborador);

		Colaborador colaboradorAniversariante = ColaboradorFactory.getEntity();
		colaboradorAniversariante.setEmpresa(empresa);
		colaboradorAniversariante.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(01, 02, 2000));
		colaboradorDao.save(colaboradorAniversariante);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(historicoColaborador);

		HistoricoColaborador historicoColaboradorAniversariante = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAniversariante.setColaborador(colaboradorAniversariante);
		historicoColaboradorAniversariante.setEstabelecimento(estabelecimento);
		historicoColaboradorAniversariante.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAniversariante.setData(DateUtil.criarDataMesAno(01, 01, 2001));
		historicoColaboradorDao.save(historicoColaboradorAniversariante);

		Collection<Colaborador> aniversariantes = colaboradorDao.findAniversariantes(new Long[] { empresa.getId() }, mes, null, new Long[] { estabelecimento.getId() });
		assertEquals(1, aniversariantes.size());
	}

	public void testCountSemMotivos() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDesligado(true);
		colaborador.setMotivoDemissao(null);
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(2, 1, 1980));
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 1977));
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(new Integer(1), colaboradorDao.countSemMotivos(null, null, null, DateUtil.criarDataMesAno(1, 1, 1980), DateUtil.criarDataMesAno(3, 1, 1980)));
	}

	public void testMigrarBairro() {
		Bairro bairro = new Bairro();
		bairro.setNome("bairro novo");
		bairroDao.save(bairro);

		Bairro bairroDestino = new Bairro();
		bairroDestino.setNome("novo bairro");
		bairroDao.save(bairroDestino);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Endereco endereco = new Endereco();
		endereco.setBairro(bairro.getNome());

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setEndereco(endereco);
		colaboradorDao.save(colaborador);

		colaboradorDao.migrarBairro(bairro.getNome(), bairroDestino.getNome());
		Colaborador colaboradorTmp = colaboradorDao.findColaboradorById(colaborador.getId());
		assertEquals(colaboradorTmp.getEndereco().getBairro(), bairroDestino.getNome());
	}

	//
	// public void testFindSemCurso()
	// {
	// Empresa empresa = EmpresaFactory.getEmpresa();
	// empresa = empresaDao.save(empresa);
	// Colaborador colaboradorSemCurso = ColaboradorFactory.getEntity();
	// colaboradorSemCurso.setEmpresa(empresa);
	// colaboradorSemCurso = colaboradorDao.save(colaboradorSemCurso);
	//
	// HistoricoColaborador historicoColaborador1 =
	// HistoricoColaboradorFactory.getEntity();
	// historicoColaborador1.setColaborador(colaboradorSemCurso);
	// HistoricoColaborador historicoColaborador2 =
	// HistoricoColaboradorFactory.getEntity();
	// historicoColaborador2.setColaborador(colaboradorSemCurso);
	//
	// AreaOrganizacional areaOrganizacionalAntiga =
	// AreaOrganizacionalFactory.getEntity();
	// areaOrganizacionalAntiga =
	// areaOrganizacionalDao.save(areaOrganizacionalAntiga);
	// AreaOrganizacional areaOrganizacionalAtual =
	// AreaOrganizacionalFactory.getEntity();
	// areaOrganizacionalAtual =
	// areaOrganizacionalDao.save(areaOrganizacionalAtual);
	//
	// Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
	// estabelecimento = estabelecimentoDao.save(estabelecimento);
	//
	// historicoColaborador1.setAreaOrganizacional(areaOrganizacionalAntiga);
	// historicoColaborador1.setEstabelecimento(estabelecimento);
	// historicoColaborador1.setData(DateUtil.criarDataMesAno(03, 02, 2005));
	//
	// historicoColaborador2.setAreaOrganizacional(areaOrganizacionalAtual);
	// historicoColaborador2.setEstabelecimento(estabelecimento);
	// historicoColaborador2.setData(DateUtil.criarDataMesAno(05, 02, 2009));
	//
	// historicoColaboradorDao.save(historicoColaborador1);
	// historicoColaboradorDao.save(historicoColaborador2);
	//
	// Collection<Colaborador> colaboradores = colaboradorDao.findSemCurso();
	// }
	// select ct.id, c.id, hc.areaorganizacional_id, hc.estabelecimento_id,
	// c.empresa_id, colab.nome, colab.matricula, t.descricao, t.dataprevini ,
	// t.dataprevfim, ct.aprovado
	// from colaboradorturma as ct
	// join turma as t on t.id = ct.turma_id
	// join curso as c on c.id = ct.curso_id
	// join colaborador as colab on colab.id = ct.colaborador_id
	// join historicocolaborador as hc on ct.colaborador_id=hc.colaborador_id
	// where hc.data = (select max(hc2.data) from historicocolaborador hc2 where
	// hc2.colaborador_id = ct.colaborador_id and hc2.data <= '2009-05-05')
	// and hc.areaorganizacional_id = 41
	// and hc.estabelecimento_id = 2
	// and c.empresa_id = 4
	// order by colab.nome
	//
	//
	// select colab.id, colab.nome from colaborador as colab
	// join historicocolaborador as hc on colab.id=hc.colaborador_id
	// where hc.data = (select max(hc2.data) from historicocolaborador hc2 where
	// hc2.colaborador_id = colab.id and hc2.data <= '2009-05-05')
	// and hc.areaorganizacional_id = 41
	// and hc.estabelecimento_id = 2
	// and colab.empresa_id = 4
	// order by colab.nome
	//
	//
	// select colab.id, colab.nome, colab.matricula, hc.areaorganizacional_id
	// from colaborador as colab
	// join historicocolaborador as hc on colab.id=hc.colaborador_id
	// left join colaboradorturma as ct on colab.id=ct.colaborador_id
	// left join curso as c on c.id = ct.curso_id
	// where hc.data = (select max(hc2.data) from historicocolaborador hc2 where
	// hc2.colaborador_id = colab.id and hc2.data <= '2009-05-05')
	// and hc.areaorganizacional_id = 41
	// and hc.estabelecimento_id = 2
	// and colab.empresa_id = 4
	// and (c.id != 15 or ct.id is null)
	// order by colab.nome

	public void testFindEmailsDeColaboradoresByPerfis() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(01, 01, 2000));
		colaborador.getContato().setEmail("miguel@ze.com.br");
		colaboradorDao.save(colaborador);

		Perfil perfil = new Perfil();
		perfilDao.save(perfil);

		assertEquals(0, colaboradorDao.findEmailsDeColaboradoresByPerfis(new Long[] { perfil.getId() }, empresa.getId()).size());
	}

	public void testFindAdmitidosHaDias() {
		Calendar quarentaDiasAtras = Calendar.getInstance();
		quarentaDiasAtras.add(Calendar.DAY_OF_YEAR, -40);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento");
		estabelecimentoDao.save(estabelecimento);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(quarentaDiasAtras.getTime());
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);

		Collection <Colaborador> colabsRetorno = colaboradorDao.findAdmitidosHaDias(40, empresa, 6L);
		Colaborador colabRetorno = (Colaborador) colabsRetorno.toArray()[0];
		
		assertEquals(1, colaboradorDao.findAdmitidosHaDias(40, empresa, 6L).size());
		assertEquals("Estabelecimento", colabRetorno.getEstabelecimento().getNome());
	}
	
	public void testFindAdmitidosHaDiasPeriodoExperiencia() {
		Calendar quarentaDiasAtras = Calendar.getInstance();
		quarentaDiasAtras.add(Calendar.DAY_OF_YEAR, -40);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(quarentaDiasAtras.getTime());
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);
		
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity();
		periodoExperienciaDao.save(periodoExperiencia);
		
		Collection <Colaborador> colabsRetorno = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		Colaborador colabRetorno = (Colaborador) colabsRetorno.toArray()[0];
		assertEquals(1, colabsRetorno.size());
		assertNull(colabRetorno.getAvaliacaoId());
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorPeriodoExperienciaAvaliacao colabPeriodoExperienciaAvaliacao = new ColaboradorPeriodoExperienciaAvaliacao();
		colabPeriodoExperienciaAvaliacao.setColaborador(colaborador);
		colabPeriodoExperienciaAvaliacao.setPeriodoExperiencia(periodoExperiencia);
		colabPeriodoExperienciaAvaliacao.setAvaliacao(avaliacao);
		colabPeriodoExperienciaAvaliacao.setTipo('G');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabPeriodoExperienciaAvaliacao);
		
		colabsRetorno = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		colabRetorno = (Colaborador) colabsRetorno.toArray()[0];
		
		assertEquals(avaliacao.getId(), colabRetorno.getAvaliacaoId());
		assertEquals(1, colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId()).size());
		assertEquals("Estabelecimento", colabRetorno.getEstabelecimento().getNome());
	}

	public void testFindAdmitidosNoPeriodo() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Date data = DateUtil.criarDataMesAno(01, 05, 2010);

		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity();
		areaMae.setNome("areaMae");
		areaOrganizacionalDao.save(areaMae);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		area.setNome("area");
		area.setAreaMae(areaMae);
		areaOrganizacionalDao.save(area);
		
		Colaborador colaborador = montaColaboradorDoTestCountAtivo(empresa, data);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(data);
		historicoColaborador.setAreaOrganizacional(area);
		historicoColaboradorDao.save(historicoColaborador);

		Collection<Colaborador> colaboradores = colaboradorDao.findAdmitidosNoPeriodo(DateUtil.criarDataMesAno(01, 06, 2009), DateUtil.criarDataMesAno(31, 05, 2010), empresa, null, null, null);
		Colaborador colabRetorno = (Colaborador) colaboradores.toArray()[0];
		assertEquals(1, colaboradores.size());
		assertEquals("areaMae > area", colabRetorno.getAreaOrganizacional().getDescricao());
		

		Colaborador colaboradorTmp = (Colaborador) colaboradores.toArray()[0];
		assertEquals(new Integer(31), colaboradorTmp.getDiasDeEmpresa());
	}

	public void testCountAtivosPeriodo() 
	{
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);

		Date data_21_07_2011 = DateUtil.criarDataMesAno(21, 07, 2011);

		FaixaSalarial dentista01 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(dentista01);

		Date data_01_05_2010 = DateUtil.criarDataMesAno(01, 05, 2010);

		Colaborador joao = montaColaboradorDoTestCountAtivo(vega, data_01_05_2010);
		montaHistoricoDoTestCountAtivo(data_01_05_2010, dentista01, joao);

		Colaborador ivanDesligadoNoDia = montaColaboradorDoTestCountAtivo(vega, data_01_05_2010);
		ivanDesligadoNoDia.setDataDesligamento(data_21_07_2011);
		montaHistoricoDoTestCountAtivo(data_01_05_2010, dentista01, ivanDesligadoNoDia);

		Date data_8_8_2011 = DateUtil.criarDataMesAno(8, 8, 2011);
		Colaborador pedroContratadoFuturo = montaColaboradorDoTestCountAtivo(vega, data_8_8_2011);
		montaHistoricoDoTestCountAtivo(data_8_8_2011, dentista01, pedroContratadoFuturo);

		Colaborador maria = montaColaboradorDoTestCountAtivo(vega, data_21_07_2011);
		montaHistoricoDoTestCountAtivo(data_21_07_2011, dentista01, maria);
		
		assertEquals(new Integer(2), colaboradorDao.countAtivosPeriodo(data_21_07_2011, Arrays.asList(vega.getId()), null, null, null, Arrays.asList(Vinculo.EMPREGO), null, false, null, false));
	}
	
	public void testCountAtivosPeriodoComAbsenteismo() 
	{
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);
		
		Date data_21_07_2011 = DateUtil.criarDataMesAno(21, 07, 2011);
		
		FaixaSalarial dentista01 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(dentista01);
		
		Date data_01_05_2010 = DateUtil.criarDataMesAno(01, 05, 2010);
		
		Colaborador joao = montaColaboradorDoTestCountAtivo(vega, data_01_05_2010);
		montaHistoricoDoTestCountAtivo(data_01_05_2010, dentista01, joao);
		criaColaboradorOcorrenciaComAbseteismo(joao);
		
		Colaborador ivanDesligadoNoDia = montaColaboradorDoTestCountAtivo(vega, data_01_05_2010);
		ivanDesligadoNoDia.setDataDesligamento(data_21_07_2011);
		montaHistoricoDoTestCountAtivo(data_01_05_2010, dentista01, ivanDesligadoNoDia);
		criaColaboradorOcorrenciaComAbseteismo(ivanDesligadoNoDia);
		
		Date data_8_8_2011 = DateUtil.criarDataMesAno(8, 8, 2011);
		Colaborador pedroContratadoFuturo = montaColaboradorDoTestCountAtivo(vega, data_8_8_2011);
		montaHistoricoDoTestCountAtivo(data_8_8_2011, dentista01, pedroContratadoFuturo);
		criaColaboradorOcorrenciaComAbseteismo(pedroContratadoFuturo);
		
		Colaborador maria = montaColaboradorDoTestCountAtivo(vega, data_21_07_2011);
		montaHistoricoDoTestCountAtivo(data_21_07_2011, dentista01, maria);
		criaColaboradorOcorrenciaComAbseteismo(maria);
		
		assertEquals(new Integer(2), colaboradorDao.countAtivosPeriodo(data_21_07_2011, Arrays.asList(vega.getId()), null, null, null, null, null, true, null, true));
	}

	private void criaColaboradorOcorrenciaComAbseteismo(Colaborador joao) 
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setAbsenteismo(true);
		ocorrenciaDao.save(ocorrencia);
		
		ColaboradorOcorrencia ocorrenciaJoao = ColaboradorOcorrenciaFactory.getEntity();
		ocorrenciaJoao.setOcorrencia(ocorrencia);
		ocorrenciaJoao.setColaborador(joao);
		colaboradorOcorrenciaDao.save(ocorrenciaJoao);
	}

	public void testCountAtivosTurnover() {
		
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);
		
		MotivoSolicitacao motivoTurnover = MotivoSolicitacaoFactory.getEntity();
		motivoTurnover.setDescricao("Substituição");
		motivoTurnover.setTurnover(true);
		motivoSolicitacaoDao.save(motivoTurnover);

		MotivoSolicitacao motivoSemTurnover = MotivoSolicitacaoFactory.getEntity();
		motivoSemTurnover.setDescricao("Aumento");
		motivoSemTurnover.setTurnover(false);
		motivoSolicitacaoDao.save(motivoSemTurnover);
		
		Candidato candidatoTurnover = CandidatoFactory.getCandidato();
		candidatoDao.save(candidatoTurnover);

		Candidato candidatoSemTurnover = CandidatoFactory.getCandidato();
		candidatoDao.save(candidatoSemTurnover);
		
		Solicitacao solicitacaoTurnover = SolicitacaoFactory.getSolicitacao();
		solicitacaoTurnover.setMotivoSolicitacao(motivoTurnover);
		solicitacaoDao.save(solicitacaoTurnover);
		
		Solicitacao solicitacaoSemTurnover = SolicitacaoFactory.getSolicitacao();
		solicitacaoSemTurnover.setMotivoSolicitacao(motivoSemTurnover);
		solicitacaoDao.save(solicitacaoSemTurnover);
		
		CandidatoSolicitacao candSolicTurnover = CandidatoSolicitacaoFactory.getEntity(); 
		candSolicTurnover.setCandidato(candidatoTurnover);
		candSolicTurnover.setSolicitacao(solicitacaoTurnover);
		candidatoSolicitacaoDao.save(candSolicTurnover);

		CandidatoSolicitacao candSolicSemTurnover = CandidatoSolicitacaoFactory.getEntity(); 
		candSolicSemTurnover.setCandidato(candidatoSemTurnover);
		candSolicSemTurnover.setSolicitacao(solicitacaoSemTurnover);
		candidatoSolicitacaoDao.save(candSolicSemTurnover);
		
		FaixaSalarial dentista01 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(dentista01);
		
		Date data_01_05_2010 = DateUtil.criarDataMesAno(01, 05, 2010);
		Date data_21_07_2011 = DateUtil.criarDataMesAno(21, 07, 2011);
		
		Colaborador joao = montaColaboradorDoTestCountAtivo(vega, data_01_05_2010);
		joao.setCandidato(candidatoTurnover);
		montaHistoricoDoTestCountAtivo(data_01_05_2010, dentista01, joao);
		
		Colaborador maria = montaColaboradorDoTestCountAtivo(vega, data_21_07_2011);
		maria.setCandidato(candidatoSemTurnover);
		montaHistoricoDoTestCountAtivo(data_21_07_2011, dentista01, maria);
		
		assertEquals(new Integer(1), colaboradorDao.countAtivosTurnover(data_21_07_2011, vega.getId(), null, null, null, Arrays.asList(Vinculo.EMPREGO), true));
	}

	private void montaHistoricoDoTestCountAtivo(Date dataContratacaoJoao, FaixaSalarial dentista01, Colaborador joao) {
		HistoricoColaborador histJoaoAtivo = HistoricoColaboradorFactory.getEntity();
		histJoaoAtivo.setColaborador(joao);
		histJoaoAtivo.setFaixaSalarial(dentista01);
		histJoaoAtivo.setData(dataContratacaoJoao);
		histJoaoAtivo.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(histJoaoAtivo);
	}

	private Colaborador montaColaboradorDoTestCountAtivo(Empresa vega, Date dataContratacaoJoao) {
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setEmpresa(vega);
		joao.setDataAdmissao(dataContratacaoJoao);
		joao.setVinculo(Vinculo.EMPREGO);
		colaboradorDao.save(joao);
		return joao;
	}

	public void testCountDesligados() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);

		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, longs, longs, longs, null, false);
		assertEquals(0, colaboradores.size());
	}

	public void testCountAdmitidosPeriodoTurnover() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, longs, longs, longs, Arrays.asList(Vinculo.EMPREGO), true);
		assertEquals(0, colaboradores.size());
	}

	public void testCountAdmitidosPeriodoTurnoverSolicitacao() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setTurnoverPorSolicitacao(true);
		empresaDao.save(empresa);
		
		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, longs, longs, longs, Arrays.asList(Vinculo.EMPREGO), true);
		assertEquals(0, colaboradores.size());
	}

	public void testFindComAvaliacoesExperiencias() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Date data = DateUtil.criarDataMesAno(01, 05, 2010);
		Date respondidaEm = DateUtil.criarDataMesAno(15, 05, 2010);

		Colaborador colaborador = montaColaboradorDoTestCountAtivo(empresa, data);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(data);
		historicoColaboradorDao.save(historicoColaborador);

		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);

		ColaboradorQuestionario colaboradorQuestionarioRespondeu = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioRespondeu.setColaborador(colaborador);
		colaboradorQuestionarioRespondeu.setRespondidaEm(respondidaEm);
		colaboradorQuestionarioRespondeu.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioRespondeu);

		ColaboradorQuestionario colaboradorQuestionarioPesquisa = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioPesquisa.setColaborador(colaborador);
		colaboradorQuestionarioPesquisa.setRespondidaEm(respondidaEm);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioPesquisa);

		Collection<Colaborador> colaboradores = colaboradorDao.findComAvaliacoesExperiencias(null, DateUtil.criarDataMesAno(01, 06, 2010), empresa, null, null);
		assertEquals(1, colaboradores.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradores.toArray()[0];
		assertEquals(new Integer(15), colaboradorTmp.getQtdDiasRespondeuAvExperiencia());
	}

	public void testFindAdmitidos() {
		Date dataAdmissao = DateUtil.montaDataByString("20/01/2010");
		Date dataAdmissaoFora = DateUtil.montaDataByString("13/03/2010");

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setNome("Estabelecimento A");
		estabelecimento1.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setNome("Estabelecimento B");
		estabelecimento2.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento2);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		Colaborador colaborador1 = montaColaboradorDoTestCountAtivo(empresa, dataAdmissao);

		Colaborador colaborador2 = montaColaboradorDoTestCountAtivo(empresa, dataAdmissaoFora);

		HistoricoColaborador historicoColaborador1_1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1_1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador1_1.setColaborador(colaborador1);
		historicoColaborador1_1.setEstabelecimento(estabelecimento1);
		historicoColaborador1_1.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador1_1 = historicoColaboradorDao.save(historicoColaborador1_1);
		
		HistoricoColaborador historicoColaborador1_2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1_2.setData(DateUtil.criarDataMesAno(1, 1, 2010));
		historicoColaborador1_2.setColaborador(colaborador1);
		historicoColaborador1_2.setEstabelecimento(estabelecimento2);
		historicoColaborador1_2.setAreaOrganizacional(areaOrganizacional1);
		historicoColaborador1_2 = historicoColaboradorDao.save(historicoColaborador1_2);

		HistoricoColaborador historicoColaborador2_1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2_1.setData(DateUtil.criarDataMesAno(1, 2, 2009));
		historicoColaborador2_1.setColaborador(colaborador2);
		historicoColaborador2_1.setEstabelecimento(estabelecimento2);
		historicoColaborador2_1.setAreaOrganizacional(areaOrganizacional1);
		historicoColaborador2_1 = historicoColaboradorDao.save(historicoColaborador2_1);

		HistoricoColaborador historicoColaborador2_2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2_2.setData(DateUtil.criarDataMesAno(1, 2, 2011));
		historicoColaborador2_2.setColaborador(colaborador2);
		historicoColaborador2_2.setEstabelecimento(estabelecimento1);
		historicoColaborador2_2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador2_2 = historicoColaboradorDao.save(historicoColaborador2_2);
		
		Date dataIni = DateUtil.montaDataByString("19/05/2009");
		Date dataFim = DateUtil.montaDataByString("20/02/2011");
		
		Long[] areasIds = null;
		Long[] estabelecimentosIds = new Long[] { estabelecimento1.getId() };

		Collection<Colaborador> colaboradorRetornado = colaboradorDao.findAdmitidos(dataIni, dataFim, areasIds, estabelecimentosIds, false);
		assertEquals(1, colaboradorRetornado.size());
		assertEquals(historicoColaborador1_1.getAreaOrganizacional().getId(), ((Colaborador)colaboradorRetornado.toArray()[0]).getAreaOrganizacional().getId());
		
		estabelecimentosIds = new Long[] { estabelecimento1.getId(), estabelecimento2.getId() };
		
		colaboradorRetornado = colaboradorDao.findAdmitidos(dataIni, dataFim, areasIds, estabelecimentosIds, false);
		assertEquals(2, colaboradorRetornado.size());
		assertEquals(historicoColaborador1_1.getAreaOrganizacional().getId(), ((Colaborador)colaboradorRetornado.toArray()[0]).getAreaOrganizacional().getId());
		assertEquals(historicoColaborador2_1.getAreaOrganizacional().getId(), ((Colaborador)colaboradorRetornado.toArray()[1]).getAreaOrganizacional().getId());
	}

	public void testFindHistoricoByColaboradors() {
		Colaborador colaborador1 = getColaborador();
		colaborador1.setNome("A TESTE");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("X Maria");
		colaboradorDao.save(colaborador2);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(colaborador1);
		historicoColaboradorAtual1.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorDao.save(historicoColaboradorAtual1);

		HistoricoColaborador historicoColaboradorAtual2 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual2.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual2.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual2.setColaborador(colaborador2);
		historicoColaboradorAtual2.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorDao.save(historicoColaboradorAtual2);

		HistoricoColaborador historicoColaboradorAntigo1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo1.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo1.setColaborador(colaborador1);
		historicoColaboradorDao.save(historicoColaboradorAntigo1);

		Collection<Long> ids = new ArrayList<Long>();
		ids.add(colaborador1.getId());
		ids.add(colaborador2.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findByIdHistoricoAtual(ids);
		assertEquals(2, colaboradors.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradors.toArray()[0];
		assertEquals(colaborador1.getNome(), colaboradorTmp.getNome());
		assertEquals("Teste I", colaboradorTmp.getFaixaSalarial().getDescricao());
		assertEquals(areaOrganizacional.getNome(), colaboradorTmp.getAreaOrganizacional().getNome());
	}

	public void testFindByCpf() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador1 = getColaborador();
		colaborador1.setNome("Xica ");
		colaborador1.setPessoalCpf("26745534304");
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("Xica");
		colaborador2.setPessoalCpf("26745534304");
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		Colaborador colaborador3 = getColaborador();
		colaborador3.setNome("Xica");
		colaborador3.setPessoalCpf("26745534304");
		colaborador3.setEmpresa(empresa);
		colaborador3.setDesligado(true);
		colaboradorDao.save(colaborador3);

		assertEquals("Todos", 3, colaboradorDao.findByCpf("26745534304", empresa.getId(), null, null).size());
		assertEquals("Excluindo desligados", 2, colaboradorDao.findByCpf("26745534304", empresa.getId(), null, false).size());
		assertEquals("Excluindo a si mesmo", 1, colaboradorDao.findByCpf("26745534304", empresa.getId(), colaborador2.getId(), false).size());
	}

	public void testUpdateInfoPessoaisByCpf() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setPessoalCpf("26745534304");
		colaborador.setEmpresa(empresa);

		colaboradorDao.updateInfoPessoaisByCpf(colaborador, empresa.getId());
	}
	
	public void testCountFormacaoEscolar() 
	{
		Date hoje = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setDataAdmissao(hoje);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setAreaOrganizacional(area);
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaboradorAtual1);
		
		Collection<Long> empresaIds = Arrays.asList(empresa.getId());
		
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, empresaIds, null).size());
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, empresaIds, new Long[]{area.getId()}).size());
	
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, null, new Long[]{area.getId()}).size());
	}
	
	public void testCountDeficiencia() 
	{
		Date hoje = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setDataAdmissao(hoje);
		colaborador.setEmpresa(empresa);
		colaborador.getPessoal().setDeficiencia(Deficiencia.AUDITIVA);
		colaboradorDao.save(colaborador);
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(area);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaboradorAtual);
		
		assertEquals(1, colaboradorDao.countDeficiencia(hoje, Arrays.asList(empresa.getId()), null).size());
		assertEquals(1, colaboradorDao.countDeficiencia(hoje, null, new Long[]{area.getId()}).size());
		assertEquals(1, colaboradorDao.countDeficiencia(hoje, Arrays.asList(empresa.getId()), new Long[]{area.getId()}).size());
	}
	
	public void testFindEmailsByPapel() 
	{
		Usuario teo = UsuarioFactory.getEntity();
		usuarioDao.save(teo);

		Colaborador colabTeo = ColaboradorFactory.getEntity();
		colabTeo.setUsuario(teo);
		colabTeo.setEmailColaborador("teste@teste.com");
		colaboradorDao.save(colabTeo);
		
		UsuarioEmpresa ueTeo = UsuarioEmpresaFactory.getEntity();
		ueTeo.setUsuario(teo);
		usuarioEmpresaDao.save(ueTeo);
		
		Usuario leo = UsuarioFactory.getEntity();
		usuarioDao.save(leo);

		Colaborador colabLeo = ColaboradorFactory.getEntity();
		colabLeo.setUsuario(leo);
		colabLeo.setEmailColaborador("teste@teste.com");
		colaboradorDao.save(colabLeo);
		
		UsuarioEmpresa ueLeo = UsuarioEmpresaFactory.getEntity();
		ueLeo.setUsuario(leo);
		usuarioEmpresaDao.save(ueLeo);
		
		Collection<Long> usuarioEmpresaIds = Arrays.asList(ueTeo.getId(), ueLeo.getId()); 
		
		//somente para startar a consulta em sql
		colaboradorDao.findbyCandidato(colabLeo.getId(), 1L);
		
		String[] emails = colaboradorDao.findEmailsByPapel(usuarioEmpresaIds);
		
		assertEquals(1, emails.length);
	}

	public void testFindParticipantesByAvaliacaoDesempenho() {
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador Carrasco");
		colaboradorDao.save(avaliador);

		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Pobre Coitado Que Será Demitido");
		colaboradorDao.save(avaliado);

		Colaborador avaliado2 = ColaboradorFactory.getEntity();
		avaliado2.setNome("Pobre Coitado 2");
		colaboradorDao.save(avaliado2);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionarioAvaliado = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioAvaliado.setColaborador(avaliado);
		colaboradorQuestionarioAvaliado.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioAvaliado);

		ColaboradorQuestionario colaboradorQuestionarioAvaliado2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioAvaliado2.setColaborador(avaliado2);
		colaboradorQuestionarioAvaliado2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioAvaliado2);

		// avaliados
		assertEquals(2, colaboradorDao.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, null).size());
		// avaliadores
		assertEquals(1, colaboradorDao.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, null).size());
	}

	public void testFindColaboradorDeAvaliacaoDesempenhoNaoRespondida() 
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2011);
		Date fim = DateUtil.incrementaMes(new Date(), 1);
		Date fim2 = DateUtil.incrementaMes(new Date(), -1);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setEmpresa(empresa);
		colab.setNome("colab");
		colab.setDesligado(false);
		colaboradorDao.save(colab);
		
		Colaborador colab2 = ColaboradorFactory.getEntity();
		colab2.setEmpresa(empresa);
		colab2.setNome("colab2");
		colab2.setDesligado(false);
		colaboradorDao.save(colab2);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("Avaliacao 1");
		avaliacaoDesempenho.setInicio(inicio);
		avaliacaoDesempenho.setFim(fim);
		avaliacaoDesempenho.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setTitulo("Avaliacao 2");
		avaliacaoDesempenho2.setInicio(inicio);
		avaliacaoDesempenho2.setFim(fim2);
		avaliacaoDesempenho2.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador((colab));
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario.setRespondida(false);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliador(colab);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario2.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setAvaliador(colab2);
		colaboradorQuestionario3.setAvaliacaoDesempenho(avaliacaoDesempenho2);
		colaboradorQuestionario3.setRespondida(false);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		Collection<Colaborador> colaboradors = colaboradorDao.findColaboradorDeAvaliacaoDesempenhoNaoRespondida();
		
		int qtdColabCorreto = 0;
		int qtdColabErrado = 0;
		for (Colaborador colaborador : colaboradors) {
			
			if (colaborador.getId().equals(colab.getId())) 
				qtdColabCorreto++;
			
			if (colaborador.getId().equals(colab2.getId())) 
				qtdColabErrado++;
		}
		
		assertEquals(1, qtdColabCorreto);
		assertEquals(0, qtdColabErrado);
	}
	
	public void testFindByQuestionarioNaoRespondido() {
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("Joao");
		colaboradorDao.save(joao);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(joao);
		colaboradorQuestionario.setRespondida(false);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setAvaliacaoDesempenho(null);
		colaboradorQuestionario.setAvaliacao(null);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionarioRespondido = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioRespondido.setColaborador(joao);
		colaboradorQuestionarioRespondido.setRespondida(true);
		colaboradorQuestionarioRespondido.setQuestionario(questionario);
		colaboradorQuestionarioRespondido.setAvaliacaoDesempenho(null);
		colaboradorQuestionarioRespondido.setAvaliacao(null);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioRespondido);
		
		assertEquals(1, colaboradorDao.findByQuestionarioNaoRespondido(questionario.getId()).size());
	}

	public void testFindParticipantesDistinctByAvaliacaoDesempenho() {
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(avaliado);
		historicoColaboradorDao.save(historicoColaboradorAtual1);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);

		assertEquals(1, colaboradorDao.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, null, null, null).size());
	}
	
	public void testQtdDemitidosEm90Dias() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date dataAte = DateUtil.montaDataByString("11/11/2010");
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Colaborador mariaNaoDesligada = ColaboradorFactory.getEntity();
		mariaNaoDesligada.setEmpresa(empresa);
		mariaNaoDesligada.setDesligado(false);
		colaboradorDao.save(mariaNaoDesligada);
		
		HistoricoColaborador historicoColaboradorMaria = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorMaria.setData(dataAte);
		historicoColaboradorMaria.setColaborador(mariaNaoDesligada);
		historicoColaboradorDao.save(historicoColaboradorMaria);
		
		assertEquals(0, colaboradorDao.qtdDemitidosEm90Dias(empresa.getId(), null, null, dataAte));
		
		Colaborador joaoDesligadoAntesDoPeriodo = ColaboradorFactory.getEntity();
		joaoDesligadoAntesDoPeriodo.setEmpresa(empresa);
		joaoDesligadoAntesDoPeriodo.setDesligado(true);
		joaoDesligadoAntesDoPeriodo.setDataDesligamento(DateUtil.montaDataByString("01/01/2000"));
		colaboradorDao.save(joaoDesligadoAntesDoPeriodo);
		
		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorJoao.setData(dataAte);
		historicoColaboradorJoao.setColaborador(joaoDesligadoAntesDoPeriodo);
		historicoColaboradorDao.save(historicoColaboradorJoao);
		
		assertEquals(0, colaboradorDao.qtdDemitidosEm90Dias(empresa.getId(), null, null, dataAte));
		
		Colaborador marciaDesligadoDepoisPeriodo = ColaboradorFactory.getEntity();
		marciaDesligadoDepoisPeriodo.setEmpresa(empresa);
		marciaDesligadoDepoisPeriodo.setDesligado(true);
		marciaDesligadoDepoisPeriodo.setDataDesligamento(DateUtil.montaDataByString("22/12/2022"));
		colaboradorDao.save(marciaDesligadoDepoisPeriodo);
		
		HistoricoColaborador historicoColaboradorMarcia = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorMarcia.setData(dataAte);
		historicoColaboradorMarcia.setColaborador(marciaDesligadoDepoisPeriodo);
		historicoColaboradorDao.save(historicoColaboradorMarcia);
		
		assertEquals(0, colaboradorDao.qtdDemitidosEm90Dias(empresa.getId(), null, null, dataAte));

		Colaborador pedroDesligadoNoPeriodoForaDoPeriodoDe90Dias = ColaboradorFactory.getEntity();
		pedroDesligadoNoPeriodoForaDoPeriodoDe90Dias.setEmpresa(empresa);
		pedroDesligadoNoPeriodoForaDoPeriodoDe90Dias.setDesligado(true);
		pedroDesligadoNoPeriodoForaDoPeriodoDe90Dias.setDataDesligamento(DateUtil.montaDataByString("20/01/2010"));
		colaboradorDao.save(pedroDesligadoNoPeriodoForaDoPeriodoDe90Dias);
		
		HistoricoColaborador historicoColaboradorPedro = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorPedro.setData(dataAte);
		historicoColaboradorPedro.setColaborador(pedroDesligadoNoPeriodoForaDoPeriodoDe90Dias);
		historicoColaboradorDao.save(historicoColaboradorPedro);
		
		
		Colaborador toinDesligadoNoPeriodo = ColaboradorFactory.getEntity();
		toinDesligadoNoPeriodo.setEmpresa(empresa);
		toinDesligadoNoPeriodo.setDesligado(true);
		toinDesligadoNoPeriodo.setDataDesligamento(DateUtil.montaDataByString("11/11/2010"));
		colaboradorDao.save(toinDesligadoNoPeriodo);
		
		HistoricoColaborador historicoColaboradorToin = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorToin.setData(dataAte);
		historicoColaboradorToin.setColaborador(toinDesligadoNoPeriodo);
		historicoColaboradorToin.setAreaOrganizacional(area1);
		historicoColaboradorToin.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaboradorToin);
		
		Colaborador bebelDesligadoNoPeriodo = ColaboradorFactory.getEntity();
		bebelDesligadoNoPeriodo.setEmpresa(empresa);
		bebelDesligadoNoPeriodo.setDesligado(true);
		bebelDesligadoNoPeriodo.setDataDesligamento(DateUtil.montaDataByString("20/09/2010"));
		colaboradorDao.save(bebelDesligadoNoPeriodo);
		
		HistoricoColaborador historicoColaboradorBebel = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorBebel.setData(dataAte);
		historicoColaboradorBebel.setColaborador(bebelDesligadoNoPeriodo);
		historicoColaboradorBebel.setAreaOrganizacional(area2);
		historicoColaboradorBebel.setEstabelecimento(estabelecimento2);
		historicoColaboradorDao.save(historicoColaboradorBebel);
		
		assertEquals("Sem considerar area nem estabelecimento", 2, colaboradorDao.qtdDemitidosEm90Dias(empresa.getId(), null, null, dataAte));
		assertEquals("Considerando estabelecimento", 1, colaboradorDao.qtdDemitidosEm90Dias(empresa.getId(), new Long[] { estabelecimento1.getId() }, null, dataAte));
		assertEquals("Considerando area organizacional", 1, colaboradorDao.qtdDemitidosEm90Dias(empresa.getId(), null, new Long[] { area1.getId() }, dataAte));
	}
	
	public void testQtdAdmitidosPeriodo() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Date dataAte = DateUtil.montaDataByString("11/11/2010");
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setEmpresa(empresa);
		maria.setDataAdmissao(DateUtil.montaDataByString("01/01/2000"));
		colaboradorDao.save(maria);
		
		HistoricoColaborador historicoColaboradorMaria = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorMaria.setData(dataAte);
		historicoColaboradorMaria.setColaborador(maria);
		historicoColaboradorMaria.setAreaOrganizacional(area1);
		historicoColaboradorMaria.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaboradorMaria);
		
		Colaborador bebel = ColaboradorFactory.getEntity();
		bebel.setEmpresa(empresa);
		bebel.setDesligado(true);
		bebel.setDataAdmissao(DateUtil.montaDataByString("22/12/2022"));
		colaboradorDao.save(bebel);
		
		HistoricoColaborador historicoColaboradorBebel = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorBebel.setData(dataAte);
		historicoColaboradorBebel.setColaborador(bebel);
		historicoColaboradorBebel.setAreaOrganizacional(area2);
		historicoColaboradorBebel.setEstabelecimento(estabelecimento2);
		historicoColaboradorDao.save(historicoColaboradorBebel);
		
		assertEquals(0, colaboradorDao.qtdAdmitidosPeriodoEm90Dias(empresa.getId(), null, null, dataAte));
		
		Colaborador joaoFora = ColaboradorFactory.getEntity();
		joaoFora.setEmpresa(empresa);
		joaoFora.setDataAdmissao(DateUtil.montaDataByString("20/01/2010"));
		colaboradorDao.save(joaoFora);
		
		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorJoao.setData(dataAte);
		historicoColaboradorJoao.setColaborador(joaoFora);
		historicoColaboradorJoao.setAreaOrganizacional(area1);
		historicoColaboradorJoao.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaboradorJoao);
		
		Colaborador marcia = ColaboradorFactory.getEntity();
		marcia.setEmpresa(empresa);
		marcia.setDataAdmissao(DateUtil.montaDataByString("01/10/2010"));
		colaboradorDao.save(marcia);
		
		HistoricoColaborador historicoColaboradorMarcia = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorMarcia.setData(dataAte);
		historicoColaboradorMarcia.setColaborador(marcia);
		historicoColaboradorMarcia.setAreaOrganizacional(area1);
		historicoColaboradorMarcia.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaboradorMarcia);
		
		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setEmpresa(empresa);
		pedro.setDataAdmissao(DateUtil.montaDataByString("11/11/2010"));
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoColaboradorPedro = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorPedro.setData(dataAte);
		historicoColaboradorPedro.setColaborador(pedro);
		historicoColaboradorPedro.setAreaOrganizacional(area2);
		historicoColaboradorPedro.setEstabelecimento(estabelecimento2);
		historicoColaboradorDao.save(historicoColaboradorPedro);
		
		assertEquals("Sem considerar area nem estabelecimento", 2, colaboradorDao.qtdAdmitidosPeriodoEm90Dias(empresa.getId(), null, null, dataAte));
		assertEquals("Considerando estabelecimento", 1, colaboradorDao.qtdAdmitidosPeriodoEm90Dias(empresa.getId(), new Long[] { estabelecimento1.getId() }, null, dataAte));
		assertEquals("Considerando area organizacional", 1, colaboradorDao.qtdAdmitidosPeriodoEm90Dias(empresa.getId(), null, new Long[] { area1.getId() }, dataAte));
	}
	
	public void testFindCodigoACDuplicado() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setEmpresa(empresa);
		maria.setCodigoAC("1111111");
		colaboradorDao.save(maria);
		
		Colaborador bebel = ColaboradorFactory.getEntity();
		bebel.setEmpresa(empresa);
		bebel.setCodigoAC("1111111");
		colaboradorDao.save(bebel);
		
		Colaborador xica = ColaboradorFactory.getEntity();
		xica.setEmpresa(empresa);
		xica.setCodigoAC("1111112");
		colaboradorDao.save(xica);
		
		Colaborador teteu = ColaboradorFactory.getEntity();
		teteu.setEmpresa(empresa);
		teteu.setCodigoAC("1111112");
		colaboradorDao.save(teteu);
		
		assertEquals("1111111,1111112", colaboradorDao.findCodigoACDuplicado(empresa.getId()));
	}

	public void testFindCodigoACDuplicadoVazio() 
	{
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);		
		assertEquals("", colaboradorDao.findCodigoACDuplicado(vega.getId()));//tem que ser vazio, utilizado na empresaManager
	}
	
	public void testGetColaboradoresByTurmas() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("Xica ");
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Xica");
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);

		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDescricao("teste");
		turma.setEmpresa(empresa);
		turmaDao.save(turma);

		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma.getId());

		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colaborador1);
		colaboradorTurmaDao.save(colaboradorTurma1);

		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity(2L);
		colaboradorTurma2.setTurma(turma);
		colaboradorTurma2.setColaborador(colaborador2);
		colaboradorTurmaDao.save(colaboradorTurma2);

		assertEquals(new Integer(2), colaboradorDao.qtdColaboradoresByTurmas(turmaIds));
	}

	public void testFindParentesByNome() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("joao rodrigues");
		joao.setPessoalMae("maria rodrigues");
		joao.setPessoalPai("pedro rodrigues");
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("maria rodrigues");
		maria.setEmpresa(empresa);
		maria.setPessoalConjuge("geraldo rodrigues");
		colaboradorDao.save(maria);

		Colaborador mariana = ColaboradorFactory.getEntity();
		mariana.setNome("mariana rodrigues");
		mariana.setEmpresa(empresa);
		colaboradorDao.save(mariana);

		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setPessoalConjuge("maria rodrigues");
		pedro.setNome("pedro rodrigues");
		pedro.setEmpresa(empresa);
		colaboradorDao.save(pedro);
		
		assertEquals(1, colaboradorDao.findParentesByNome("joao rodrigues", empresa.getId()).size());
		assertEquals(1, colaboradorDao.findParentesByNome("geraldo rodrigues", empresa.getId()).size());
		assertEquals(2, colaboradorDao.findParentesByNome("pedro rodrigues", empresa.getId()).size());
		assertEquals(3, colaboradorDao.findParentesByNome("maria rodrigues", empresa.getId()).size());
		assertEquals(3, colaboradorDao.findParentesByNome("maria rodrigues", null).size());
	}

	public void testFindColabPeriodoExperiencia() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Augusto ");
		avaliador.setDesligado(false);
		avaliador.setEmpresa(empresa);
		colaboradorDao.save(avaliador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("Area 1");
		areaOrganizacionalDao.save(areaOrganizacional);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.montaDataByString("14/01/2010"));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setEmpresa(empresa);
		avaliacao.setTitulo("avaliacao");
		avaliacaoDao.save(avaliacao);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("titulo");
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setPerformance(0.35);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setRespondidaEm(DateUtil.montaDataByString("15/10/2010"));
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Long[] estabelecimentoIds = new Long[] { estabelecimento.getId() };
		Long[] areaIds = new Long[] { areaOrganizacional.getId() };

		Date periodoIni = DateUtil.montaDataByString("14/08/2010");
		Date periodoFim = DateUtil.montaDataByString("16/12/2010");

		Long[] avaliacaoIds = new Long[]{avaliacaoDesempenho.getId()};
		
		Collection<Colaborador> colaboradors = colaboradorDao.findColabPeriodoExperiencia(empresa.getId(), periodoIni, periodoFim, avaliacaoIds, areaIds, estabelecimentoIds, null, true);

		assertEquals(1, colaboradors.size());
	}

	public void testFindComHistoricoFuturoSQL()
	 {
		 Cargo cargo = CargoFactory.getEntity();
		 cargo.setNome("Teste");
		 cargoDao.save(cargo);
		
		 FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		 faixaSalarial.setNome("I");
		 faixaSalarial.setCargo(cargo);
		 faixaSalarialDao.save(faixaSalarial);

		 FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		 faixaSalarial2.setNome("II");
		 faixaSalarial2.setCargo(cargo);
		 faixaSalarialDao.save(faixaSalarial2);
		
		 Empresa empresa = EmpresaFactory.getEmpresa(4L);
		 empresaDao.save(empresa);
		
		 Colaborador joao = inicializaColaborador("Joao ", "99999888888", empresa);
		 colaboradorDao.save(joao);
		
		 Colaborador maria = inicializaColaborador("Maria", "12321362391", empresa);
		 colaboradorDao.save(maria);
		
		 Colaborador pedro = inicializaColaborador("Pedro", "12321362391", empresa);
		 colaboradorDao.save(pedro);

		 Colaborador ronaldo = inicializaColaborador("ronaldo", "12321362391", empresa);
		 colaboradorDao.save(ronaldo);
		 
		 Colaborador tadeu = inicializaColaborador("tadeu", "12321362391", empresa);
		 colaboradorDao.save(tadeu);
		
		 HistoricoColaborador historicoColaboradorJoao = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2008), joao, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorJoao);
		
		 HistoricoColaborador historicoColaboradorMaria = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2020), maria, faixaSalarial2);
		 historicoColaboradorDao.save(historicoColaboradorMaria);
		
		 HistoricoColaborador historicoColaboradorPedroAntigo = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2000), pedro, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorPedroAntigo);
		
		 HistoricoColaborador historicoColaboradorPedroAtual = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2010), pedro, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorPedroAtual);
		 
		 HistoricoColaborador historicoColaboradorRonaldoAtual = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2010), ronaldo, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorRonaldoAtual);

		 HistoricoColaborador historicoColaboradorRonaldoFuturo1 = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2022), ronaldo, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorRonaldoFuturo1);

		 HistoricoColaborador historicoColaboradorRonaldoFuturo2 = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2023), ronaldo, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorRonaldoFuturo2);
		 
		 HistoricoColaborador historicoColaboradorTadeuFuturo1 = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2022), tadeu, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorTadeuFuturo1);
		
		 Map<String, Object> parametros = new HashMap<String, Object>();
		 parametros.put("cpfBusca", "12321362391");
		 parametros.put("empresaId", empresa.getId());
		 
		 historicoColaboradorDao.getHistoricoProximo(historicoColaboradorJoao);//miguel do SQL
		
		 assertEquals(4, colaboradorDao.findComHistoricoFuturoSQL(parametros, 0, 0).size());

		 parametros.put("faixaSalarialId", faixaSalarial.getId());
		 
		 assertEquals(3, colaboradorDao.findComHistoricoFuturoSQL(parametros, 0, 0).size());
	 }
	
	public void testFindByEstabelecimentoDataAdmissao()
	{
		Empresa fortes = EmpresaFactory.getEmpresa();
		empresaDao.save(fortes);
		Empresa ente = EmpresaFactory.getEmpresa();
		empresaDao.save(ente);
		
		Estabelecimento matriz = EstabelecimentoFactory.getEntity(1L);
		estabelecimentoDao.save(matriz);
		Estabelecimento filial = EstabelecimentoFactory.getEntity(2L);
		estabelecimentoDao.save(filial);
		
		Date hoje = DateUtil.criarDataMesAno(29, 8, 2011);
		
		Colaborador joao = ColaboradorFactory.getEntity(1L, "joao", "joao", "001");
		joao.setDataAdmissao(hoje);
		joao.setEmpresa(fortes);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity(1L);
		historicoJoao.setColaborador(joao);
		historicoJoao.setEstabelecimento(matriz);
		historicoJoao.setData(hoje);
		historicoColaboradorDao.save(historicoJoao);
		
		Colaborador pedro = ColaboradorFactory.getEntity(2L, "pedro", "pedro", "002");
		pedro.setDataAdmissao(hoje);
		pedro.setEmpresa(ente);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoPedro = HistoricoColaboradorFactory.getEntity(2L);
		historicoPedro.setColaborador(pedro);
		historicoPedro.setEstabelecimento(matriz);
		historicoPedro.setData(hoje);
		historicoColaboradorDao.save(historicoPedro);

		Colaborador rui = ColaboradorFactory.getEntity(3L, "rui", "rui", "003");
		rui.setDataAdmissao(hoje);
		rui.setEmpresa(fortes);
		colaboradorDao.save(rui);
		
		HistoricoColaborador historicoRui = HistoricoColaboradorFactory.getEntity(3L);
		historicoRui.setColaborador(rui);
		historicoRui.setEstabelecimento(filial);
		historicoRui.setData(hoje);
		historicoColaboradorDao.save(historicoRui);
		
		Colaborador manoel = ColaboradorFactory.getEntity(4L, "manoel", "manoel", "004");
		manoel.setDataAdmissao(DateUtil.criarDataMesAno(1, 1, 2011));
		manoel.setEmpresa(fortes);
		colaboradorDao.save(manoel);
		
		HistoricoColaborador historicoManoel = HistoricoColaboradorFactory.getEntity(4L);
		historicoManoel.setColaborador(manoel);
		historicoManoel.setEstabelecimento(matriz);
		historicoManoel.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		historicoColaboradorDao.save(historicoManoel);
		
		assertEquals(1, colaboradorDao.findByEstabelecimentoDataAdmissao(matriz.getId(), hoje, fortes.getId()).size());
	}
	
	public void testFindColaboradoresByIds()
	{
		Estabelecimento matriz = EstabelecimentoFactory.getEntity(1L);
		estabelecimentoDao.save(matriz);
		Estabelecimento filial = EstabelecimentoFactory.getEntity(2L);
		estabelecimentoDao.save(filial);
		
		Date hoje = DateUtil.criarDataMesAno(29, 8, 2011);
		
		Colaborador joao = ColaboradorFactory.getEntity(1L, "joao", "joao", "001");
		joao.setDataAdmissao(hoje);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity(1L);
		historicoJoao.setColaborador(joao);
		historicoJoao.setEstabelecimento(matriz);
		historicoJoao.setData(hoje);
		historicoJoao.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoJoao);
		
		Colaborador pedro = ColaboradorFactory.getEntity(2L, "pedro", "pedro", "002");
		pedro.setDataAdmissao(hoje);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoPedro = HistoricoColaboradorFactory.getEntity(2L);
		historicoPedro.setColaborador(pedro);
		historicoPedro.setEstabelecimento(matriz);
		historicoPedro.setData(hoje);
		historicoPedro.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoPedro);
		
		Colaborador rui = ColaboradorFactory.getEntity(3L, "rui", "rui", "003");
		rui.setDataAdmissao(hoje);
		colaboradorDao.save(rui);
		
		HistoricoColaborador historicoRui = HistoricoColaboradorFactory.getEntity(3L);
		historicoRui.setColaborador(rui);
		historicoRui.setEstabelecimento(filial);
		historicoRui.setData(hoje);
		historicoRui.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoRui);
		
		Date dataPassado = DateUtil.criarDataMesAno(1, 1, 2011);
		
		Colaborador manoel = ColaboradorFactory.getEntity(4L, "manoel", "manoel", "004");
		manoel.setDataAdmissao(dataPassado);
		colaboradorDao.save(manoel);
		
		HistoricoColaborador historicoManoel = HistoricoColaboradorFactory.getEntity(4L);
		historicoManoel.setColaborador(manoel);
		historicoManoel.setEstabelecimento(matriz);
		historicoManoel.setData(dataPassado);
		historicoManoel.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoManoel);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findColaboradoresByIds(new Long[] { joao.getId(), pedro.getId(), rui.getId(), manoel.getId() }); 
		assertEquals(4, colaboradores.size());
		
		Colaborador colabJoao = (Colaborador) colaboradores.toArray()[0];
		assertEquals("joao", colabJoao.getNome());
		assertEquals("001", colabJoao.getMatricula());
		assertEquals(hoje, colabJoao.getDataAdmissao());
	}

	public void testFindQtdVagasPreenchidas() {
		
		Date hoje = DateUtil.criarDataMesAno(29, 8, 2011);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao2);
		
		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setDataAdmissao(hoje);
		pedro.setSolicitacao(solicitacao1);
		pedro.setEmpresa(empresa);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoPedro = new HistoricoColaborador();
		historicoPedro.setData(hoje);
		historicoPedro.setColaborador(pedro);
		historicoPedro.setEstabelecimento(estabelecimento1);
		historicoPedro.setAreaOrganizacional(areaOrganizacional1);
		historicoPedro.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoPedro);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setDataAdmissao(hoje);
		maria.setSolicitacao(solicitacao1);
		maria.setEmpresa(empresa);
		colaboradorDao.save(maria);
		
		HistoricoColaborador historicoMaria = new HistoricoColaborador();
		historicoMaria.setData(hoje);
		historicoMaria.setColaborador(maria);
		historicoMaria.setEstabelecimento(estabelecimento1);
		historicoMaria.setAreaOrganizacional(areaOrganizacional1);
		historicoMaria.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoMaria);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setDataAdmissao(hoje);
		joao.setSolicitacao(solicitacao2);
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = new HistoricoColaborador();
		historicoJoao.setData(hoje);
		historicoJoao.setColaborador(joao);
		historicoJoao.setEstabelecimento(estabelecimento2);
		historicoJoao.setAreaOrganizacional(areaOrganizacional2);
		historicoJoao.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoJoao);
		
		Colaborador jose = ColaboradorFactory.getEntity();
		jose.setDataAdmissao(hoje);
		jose.setEmpresa(empresa);
		colaboradorDao.save(jose);
		
		HistoricoColaborador historicoJose = new HistoricoColaborador();
		historicoJose.setData(hoje);
		historicoJose.setColaborador(jose);
		historicoJose.setEstabelecimento(estabelecimento2);
		historicoJose.setAreaOrganizacional(areaOrganizacional2);
		historicoJose.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoJose);
		
		Long[] solicitacaoIds = new Long[]{solicitacao1.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento1.getId()};
		Long[] areaIds = new Long[]{areaOrganizacional2.getId()};
		
		int qtd1 = colaboradorDao.findQtdVagasPreenchidas(empresa.getId(), null, null, solicitacaoIds, hoje, hoje);
		int qtd2 = colaboradorDao.findQtdVagasPreenchidas(empresa.getId(), null, null, null, hoje, hoje);
		int qtd3 = colaboradorDao.findQtdVagasPreenchidas(empresa.getId(), estabelecimentoIds, null, null, hoje, hoje);
		int qtd4 = colaboradorDao.findQtdVagasPreenchidas(empresa.getId(), null, areaIds, null, hoje, hoje);
		
		assertEquals("Com solicitação especificada", 2, qtd1);
		assertEquals("Sem solicitação especificada", 3, qtd2);
		assertEquals("Com estabelecimento especificado", 2, qtd3);
		assertEquals("Com área organizacional especificada(solicitação not null)", 1, qtd4);
	}
	
	public void testFindSemCodigoAC() {
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Date data = DateUtil.criarDataMesAno(1, 1, 2012);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa1);
		colaborador1.setCodigoAC("1");
		colaborador1.setNaoIntegraAc(false);
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador hist1 = new HistoricoColaborador();
		hist1.setData(data);
		hist1.setColaborador(colaborador1);
		hist1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hist1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa1);
		colaborador2.setCodigoAC("");
		colaborador2.setNaoIntegraAc(true);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador hist2 = new HistoricoColaborador();
		hist2.setData(data);
		hist2.setColaborador(colaborador2);
		hist2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hist2);
		
		Colaborador colaborador5 = ColaboradorFactory.getEntity();
		colaborador5.setEmpresa(empresa1);
		colaborador5.setCodigoAC("");
		colaborador5.setNaoIntegraAc(false);
		colaboradorDao.save(colaborador5);
		
		HistoricoColaborador hist5 = new HistoricoColaborador();
		hist5.setData(data);
		hist5.setColaborador(colaborador5);
		hist5.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hist5);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(empresa1);
		colaborador3.setCodigoAC(null);
		colaborador3.setNaoIntegraAc(false);
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador hist3 = new HistoricoColaborador();
		hist3.setData(data);
		hist3.setColaborador(colaborador3);
		hist3.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hist3);
		
		Colaborador colaborador4 = ColaboradorFactory.getEntity();
		colaborador4.setEmpresa(empresa2);
		colaborador4.setNaoIntegraAc(false);
		colaborador4.setCodigoAC(null);
		colaboradorDao.save(colaborador4);
		
		HistoricoColaborador hist4 = new HistoricoColaborador();
		hist4.setData(data);
		hist4.setColaborador(colaborador4);
		hist4.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(hist4);

		Colaborador colaborador6 = ColaboradorFactory.getEntity();
		colaborador6.setEmpresa(empresa2);
		colaborador6.setNaoIntegraAc(false);
		colaborador6.setCodigoAC(null);
		colaboradorDao.save(colaborador6);
		
		HistoricoColaborador hist6 = new HistoricoColaborador();
		hist6.setData(data);
		hist6.setColaborador(colaborador6);
		hist6.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(hist6);
		
		assertEquals(2, colaboradorDao.findSemCodigoAC(empresa1.getId()).size());
		assertEquals(1, colaboradorDao.findSemCodigoAC(empresa2.getId()).size());
	}
	
	private HistoricoColaborador inicializaHistorico(Date data, Colaborador colaborador, FaixaSalarial faixaSalarial) 
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(data);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		return historicoColaborador;
	}

	private Colaborador inicializaColaborador(String nome, String cpf, Empresa empresa)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nome);
		colaborador.setEmpresa(empresa);
		colaborador.getPessoal().setCpf(cpf);
		
		return colaborador;
	}
	
	public void testDependenciasApagarColaboradorNoImportador()
	{
		String qtdTabelasComEmpresa = JDBCConnection.executeQuery("select count(table_name) from information_schema.columns as col where col.column_name = 'colaborador_id' and col.table_schema = 'public';");
		//se esse quebrar, provavelmente tem que inserir uma linha de delete no Importador colaboradorJDBC.java método removerColaborador();
		assertEquals("23", qtdTabelasComEmpresa);
	}

	public void testAtualizaDataSolicitacaoDesligamentoAc() 
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Date hoje = new Date(); 

		Exception exception = null;
		try {
			colaboradorDao.atualizaDataSolicitacaoDesligamentoAc(hoje, colaborador.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testRemoverMotivoDemissaoColaborador() 
	{
		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity();
		motivoDemissao.setMotivo("motivo");
		motivoDemissaoDao.save(motivoDemissao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setMotivoDemissao(motivoDemissao);
		colaboradorDao.save(colaborador);
		
		Exception exception = null;
		try {
			colaboradorDao.removerMotivoDemissaoColaborador(colaborador.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testFindPendenciasSolicitacaoDesligamentoAC() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setDataSolicitacaoDesligamentoAc(DateUtil.criarDataMesAno(01, 01, 2012));
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		assertEquals(1, colaboradorDao.findPendenciasSolicitacaoDesligamentoAC(empresa.getId()).size());
	}
	
	public void testFindAdmitidosHaDiasSemEpi()
	{
		Calendar quarentaDiasAtras = Calendar.getInstance();
		quarentaDiasAtras.add(Calendar.DAY_OF_YEAR, -40);

		Calendar quarentaUmDiasAtras = Calendar.getInstance();
		quarentaUmDiasAtras.add(Calendar.DAY_OF_YEAR, -50);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);

		Epi epi1 = EpiFactory.getEntity();
		epiDao.save(epi1);
		
		Epi epi2 = EpiFactory.getEntity();
		epiDao.save(epi2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setEpis(Arrays.asList(epi1, epi2));
		historicoFuncaoDao.save(historicoFuncao);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setDataAdmissao(quarentaDiasAtras.getTime());
		colaboradorDao.save(colaborador1);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setFuncao(funcao);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDataAdmissao(quarentaUmDiasAtras.getTime());
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setFuncao(funcao);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador2);
		
		assertEquals(1, colaboradorDao.findAdmitidosHaDiasSemEpi(Arrays.asList(40, 49), empresa.getId()).size());

	}
	
	public void testFindAdmitidosHaDiasSemEpiComEpi()
	{
		Calendar quarentaDiasAtras = Calendar.getInstance();
		quarentaDiasAtras.add(Calendar.DAY_OF_YEAR, -40);
		
		Calendar quarentaUmDiasAtras = Calendar.getInstance();
		quarentaUmDiasAtras.add(Calendar.DAY_OF_YEAR, -50);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		Epi epi1 = EpiFactory.getEntity();
		epiDao.save(epi1);
		
		Epi epi2 = EpiFactory.getEntity();
		epiDao.save(epi2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setEpis(Arrays.asList(epi1, epi2));
		historicoFuncaoDao.save(historicoFuncao);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setDataAdmissao(quarentaDiasAtras.getTime());
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setFuncao(funcao);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDataAdmissao(quarentaUmDiasAtras.getTime());
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setFuncao(funcao);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador2);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setColaborador(colaborador1);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		assertEquals(0, colaboradorDao.findAdmitidosHaDiasSemEpi(Arrays.asList(40, 49), empresa.getId()).size());
		
	}
	
	public void testFindAguardandoEntregaEpi()
	{
		Calendar cincoDiasAtras = Calendar.getInstance();
		cincoDiasAtras.add(Calendar.DAY_OF_YEAR, -5);
		
		Calendar tresDiasAtras = Calendar.getInstance();
		tresDiasAtras.add(Calendar.DAY_OF_YEAR, -3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador2);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setColaborador(colaborador1);
		solicitacaoEpi.setData(tresDiasAtras.getTime());
		solicitacaoEpiDao.save(solicitacaoEpi);

		SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi1.setColaborador(colaborador1);
		solicitacaoEpi1.setData(tresDiasAtras.getTime());
		solicitacaoEpiDao.save(solicitacaoEpi1);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setColaborador(colaborador2);
		solicitacaoEpi2.setData(cincoDiasAtras.getTime());
		solicitacaoEpiDao.save(solicitacaoEpi2);
		
		assertEquals(1, colaboradorDao.findAguardandoEntregaEpi(Arrays.asList(1, 3), empresa.getId()).size());
	}
	
	public void testFfindParaLembreteTerminoContratoTemporario()
	{
		Calendar cincoDiasAtras = Calendar.getInstance();
		cincoDiasAtras.add(Calendar.DAY_OF_YEAR, 5);
		
		Calendar tresDiasAtras = Calendar.getInstance();
		tresDiasAtras.add(Calendar.DAY_OF_YEAR, 3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setDataEncerramentoContrato(cincoDiasAtras.getTime());
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDataEncerramentoContrato(tresDiasAtras.getTime());
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador2);
		
		assertEquals(1, colaboradorDao.findParaLembreteTerminoContratoTemporario(Arrays.asList(1, 3), empresa.getId()).size());
	}

	public void testTriar() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Date data = DateUtil.criarDataMesAno(1, 1, 2012);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa1);
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador hist1 = new HistoricoColaborador();
		hist1.setData(data);
		hist1.setColaborador(colaborador1);
		hist1.setStatus(StatusRetornoAC.CONFIRMADO);
		hist1.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(hist1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa2);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador hist2 = new HistoricoColaborador();
		hist2.setData(data);
		hist2.setColaborador(colaborador2);
		hist2.setStatus(StatusRetornoAC.CONFIRMADO);
		hist2.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(hist2);
		
		Collection<Colaborador> colaboradores = colaboradorDao.triar(empresa1.getId(), null, null, null, null, new Long[]{}, new Long[]{}, null, false);
		
		assertEquals(1, colaboradores.size());
		
	}

	public void testCountOcorrencia() 
	{
		Date hoje = new Date();
		Date dataFim = DateUtil.incrementaMes(hoje, 1);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setDataAdmissao(hoje);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(area);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaboradorAtual);
		
		Ocorrencia ocorrencia1 = OcorrenciaFactory.getEntity();
		ocorrencia1.setDescricao("falta");
		ocorrenciaDao.save(ocorrencia1);
		
		Ocorrencia ocorrencia2 = OcorrenciaFactory.getEntity();
		ocorrencia2.setDescricao("matou");
		ocorrenciaDao.save(ocorrencia2);
		
		ColaboradorOcorrencia colaboradorOcorrencia1 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia1.setOcorrencia(ocorrencia1);
		colaboradorOcorrencia1.setColaborador(colaborador);
		colaboradorOcorrencia1.setDataIni(hoje);
		colaboradorOcorrencia1.setDataFim(dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia1);
		
		ColaboradorOcorrencia colaboradorOcorrencia2 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia2.setOcorrencia(ocorrencia2);
		colaboradorOcorrencia2.setColaborador(colaborador);
		colaboradorOcorrencia2.setDataIni(hoje);
		colaboradorOcorrencia2.setDataFim(dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia2);
		
		ColaboradorOcorrencia colaboradorOcorrencia3 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia3.setOcorrencia(ocorrencia2);
		colaboradorOcorrencia3.setColaborador(colaborador);
		colaboradorOcorrencia3.setDataIni(hoje);
		colaboradorOcorrencia3.setDataFim(dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia3);
		
		assertEquals(2, colaboradorDao.countOcorrencia(hoje, dataFim, Arrays.asList(empresa.getId()), null, 2).size());
	}
	
	public void testCountProvidencia() 
	{
		Date hoje = new Date();
		Date dataFim = DateUtil.incrementaMes(hoje, 1);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setDataAdmissao(hoje);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(area);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaboradorAtual);
		
		Ocorrencia ocorrencia1 = OcorrenciaFactory.getEntity();
		ocorrencia1.setDescricao("falta");
		ocorrenciaDao.save(ocorrencia1);
		
		Providencia providencia1 = ProvidenciaFactory.getEntity();
		providencia1.setDescricao("não faltar");
		providenciaDao.save(providencia1);
		
		Providencia providencia2 = ProvidenciaFactory.getEntity();
		providencia2.setDescricao("não gaziar");
		providenciaDao.save(providencia2);
		
		ColaboradorOcorrencia colaboradorOcorrencia1 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia1.setOcorrencia(ocorrencia1);
		colaboradorOcorrencia1.setColaborador(colaborador);
		colaboradorOcorrencia1.setProvidencia(providencia1);
		colaboradorOcorrencia1.setDataIni(hoje);
		colaboradorOcorrencia1.setDataFim(dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia1);
		
		ColaboradorOcorrencia colaboradorOcorrencia2 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia2.setOcorrencia(ocorrencia1);
		colaboradorOcorrencia2.setColaborador(colaborador);
		colaboradorOcorrencia2.setProvidencia(providencia2);
		colaboradorOcorrencia2.setDataIni(hoje);
		colaboradorOcorrencia2.setDataFim(dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia2);
		
		ColaboradorOcorrencia colaboradorOcorrencia3 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia3.setOcorrencia(ocorrencia1);
		colaboradorOcorrencia3.setColaborador(colaborador);
		colaboradorOcorrencia3.setProvidencia(providencia2);
		colaboradorOcorrencia3.setDataIni(hoje);
		colaboradorOcorrencia3.setDataFim(dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia3);
		
		assertEquals(2, colaboradorDao.countProvidencia(hoje, dataFim, Arrays.asList(empresa.getId()), null, 2).size());
	}
	
	
	public void testHabilitacaoAVencer()
	{
		Calendar umDiasDepois = Calendar.getInstance();
		umDiasDepois.add(Calendar.DAY_OF_YEAR, 1);
		
		Calendar doisDiasDepois = Calendar.getInstance();
		doisDiasDepois.add(Calendar.DAY_OF_YEAR, 2);
		
		Calendar tresDiasDepois = Calendar.getInstance();
		tresDiasDepois.add(Calendar.DAY_OF_YEAR, 3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaMae);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		area.setAreaMaeId(areaMae.getId());
		areaOrganizacionalDao.save(area);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.getHabilitacao().setVencimento(umDiasDepois.getTime());
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setAreaOrganizacional(area);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.getHabilitacao().setVencimento(doisDiasDepois.getTime());
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(area);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaboradorDao.save(historicoColaborador2);

		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(empresa);
		colaborador3.getHabilitacao().setVencimento(tresDiasDepois.getTime());
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setAreaOrganizacional(area);
		historicoColaborador3.setData(DateUtil.criarDataMesAno(1, 1, 2010));
		historicoColaborador3.setColaborador(colaborador3);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Collection<Colaborador> colaboradors = colaboradorDao.findHabilitacaAVencer(Arrays.asList(1, 2), empresa.getId());
		assertEquals(2, colaboradors.size());
		
		colaboradors = colaboradorDao.findHabilitacaAVencer(Arrays.asList(3), empresa.getId());
		assertEquals(colaborador3.getId(), ((Colaborador)colaboradors.toArray()[0]).getId());

	}
	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public GenericDao<Colaborador> getGenericDao()
	{
		return colaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
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

	public void setMotivoDemissaoDao(MotivoDemissaoDao motivoDemissaoDao)
	{
		this.motivoDemissaoDao = motivoDemissaoDao;
	}

	public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao)
	{
		this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
	}

	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setReajusteColaboradorDao(ReajusteColaboradorDao reajusteColaboradorDao)
	{
		this.reajusteColaboradorDao = reajusteColaboradorDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao)
	{
		this.ambienteDao = ambienteDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao)
	{
		this.funcaoDao = funcaoDao;
	}

	public void setBairroDao(BairroDao bairroDao)
	{
		this.bairroDao = bairroDao;
	}

	public void setPerfilDao(PerfilDao perfilDao)
	{
		this.perfilDao = perfilDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao)
	{
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setCamposExtrasDao(CamposExtrasDao camposExtrasDao)
	{
		this.camposExtrasDao = camposExtrasDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao)
	{
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao)
	{
		this.grupoACDao = grupoACDao;
	}

	public void setMotivoSolicitacaoDao(MotivoSolicitacaoDao motivoSolicitacaoDao)
	{
		this.motivoSolicitacaoDao = motivoSolicitacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao)
	{
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setOcorrenciaDao(OcorrenciaDao ocorrenciaDao)
	{
		this.ocorrenciaDao = ocorrenciaDao;
	}

	public void setColaboradorOcorrenciaDao(ColaboradorOcorrenciaDao colaboradorOcorrenciaDao)
	{
		this.colaboradorOcorrenciaDao = colaboradorOcorrenciaDao;
	}

	public QuestionarioDao getQuestionarioDao()
	{
		return questionarioDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setUsuarioEmpresaDao(UsuarioEmpresaDao usuarioEmpresaDao)
	{
		this.usuarioEmpresaDao = usuarioEmpresaDao;
	}

	
	public void setHistoricoFuncaoDao(HistoricoFuncaoDao historicoFuncaoDao)
	{
		this.historicoFuncaoDao = historicoFuncaoDao;
	}

	
	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}

	
	public void setSolicitacaoEpiDao(SolicitacaoEpiDao solicitacaoEpiDao)
	{
		this.solicitacaoEpiDao = solicitacaoEpiDao;
	}

	public void setProvidenciaDao(ProvidenciaDao providenciaDao) {
		this.providenciaDao = providenciaDao;
	}

	public void setPeriodoExperienciaDao(PeriodoExperienciaDao periodoExperienciaDao) {
		this.periodoExperienciaDao = periodoExperienciaDao;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaoDao(
			ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao) {
		this.colaboradorPeriodoExperienciaAvaliacaoDao = colaboradorPeriodoExperienciaAvaliacaoDao;
	}

	public void setComissaoDao(ComissaoDao comissaoDao) {
		this.comissaoDao = comissaoDao;
	}

	public void setComissaoMembroDao(ComissaoMembroDao comissaoMembroDao) {
		this.comissaoMembroDao = comissaoMembroDao;
	}

	public void setComissaoPeriodoDao(ComissaoPeriodoDao comissaoPeriodoDao) {
		this.comissaoPeriodoDao = comissaoPeriodoDao;
	}

}