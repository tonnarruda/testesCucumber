package com.fortes.rh.test.dao.hibernate.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
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
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.FiltroOrdemDeServico;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorJsonVO;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.json.ColaboradorJson;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ContatoFactory;
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
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorPeriodoExperienciaAvaliacaoFactory;
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
import com.fortes.rh.test.model.geral.EnderecoFactory;
import com.fortes.rh.test.util.mockObjects.MockCandidato;
import com.fortes.rh.test.util.mockObjects.MockColaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class ColaboradorDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Colaborador> 
{
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private TurmaDao turmaDao;
	@Autowired
	private ColaboradorTurmaDao colaboradorTurmaDao;
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private UsuarioEmpresaDao usuarioEmpresaDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private AvaliacaoDao avaliacaoDao;
	@Autowired
	private EstabelecimentoDao estabelecimentoDao;
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;
	@Autowired
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private GrupoOcupacionalDao grupoOcupacionalDao;
	@Autowired
	private MotivoDemissaoDao motivoDemissaoDao;
	@Autowired
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	@Autowired
	private ReajusteColaboradorDao reajusteColaboradorDao;
	@Autowired
	private AmbienteDao ambienteDao;
	@Autowired
	private FuncaoDao funcaoDao;
	@Autowired
	private BairroDao bairroDao;
	@Autowired
	private PerfilDao perfilDao;
	@Autowired
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	@Autowired
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	@Autowired
	private QuestionarioDao questionarioDao;
	@Autowired
	private CamposExtrasDao camposExtrasDao;
	@Autowired
	private GrupoACDao grupoACDao;
	@Autowired
	private MotivoSolicitacaoDao motivoSolicitacaoDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private OcorrenciaDao ocorrenciaDao;
	@Autowired
	private ColaboradorOcorrenciaDao colaboradorOcorrenciaDao;
	@Autowired
	private HistoricoFuncaoDao historicoFuncaoDao;
	@Autowired
	private EpiDao epiDao;
	@Autowired
	private SolicitacaoEpiDao solicitacaoEpiDao;
	@Autowired
	private ProvidenciaDao providenciaDao;
	@Autowired
	private PeriodoExperienciaDao periodoExperienciaDao;
	@Autowired
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	@Autowired
	private CursoDao cursoDao;
	@Autowired
	private DiaTurmaDao diaTurmaDao;
	@Autowired
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao; 
	@Autowired
	private AvaliacaoCursoDao avaliacaoCursoDao;
	@Autowired
	private EstadoDao estadoDao;

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
		colaborador.setEndereco(EnderecoFactory.getEntity());

		colaborador.setContato(ContatoFactory.getEntity());

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
	public GenericDao<Colaborador> getGenericDao() {
		return colaboradorDao;
	}

	@Override
	public void testFindById() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaA = faixaSalarialDao.save(FaixaSalarialFactory.getEntity());

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaA);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaA);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertNotNull(colaborador);
		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	@Test
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
	
	@Test
	public void testGetColaboradoresJsonVO() throws Exception 
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.getEndereco().setLogradouro("Rua A");
		colaboradorDao.save(colaborador1);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador1, new Date(), null, null, areaOrganizacional1, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity(1L);
		colaborador2.getEndereco().setLogradouro("Rua B");
		colaboradorDao.save(colaborador2);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(colaborador2, new Date(), null, null, areaOrganizacional2, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Collection<ColaboradorJsonVO> colaboradorJsonVOs = colaboradorDao.getColaboradoresJsonVO(new Long[]{areaOrganizacional1.getId()});
		
		assertEquals(1, colaboradorJsonVOs.size());
		assertEquals(colaborador1.getEndereco().getLogradouro()+", 00", ((ColaboradorJsonVO) colaboradorJsonVOs.toArray()[0]).getAddress());
	}
	
	@Test
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

		FaixaSalarial faixa = FaixaSalarialFactory.getEntity();
		faixa = faixaSalarialDao.save(faixa);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixa);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixa);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		colaborador.setNome("outro nome");

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertEquals(colaborador.getNome(), colaboradorRetorno.getNome());
	}

	@Test
	public void testUpdateDataDesligamentoByCodigo() throws Exception 
	{
		Empresa empresa = new Empresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getEntity();
		colaborador.setCodigoAC("010203");
		colaborador.setEmpresa(empresa);

		colaboradorDao.save(colaborador);

		assertTrue(colaboradorDao.desligaByCodigo(empresa, new Date(), "010203"));

		Colaborador colDesligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNotNull(colDesligado.getDataDesligamento());
		assertTrue(colDesligado.isDesligado());

		assertTrue(colaboradorDao.desligaByCodigo(empresa, null, "010203"));

		Colaborador colReligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNull(colReligado.getDataDesligamento());
		assertFalse(colReligado.isDesligado());

	}
	
	@Test
	public void testUpdateDataDesligamentoByCodigos() throws Exception 
	{
		Empresa empresa = new Empresa();
		empresa = empresaDao.save(empresa);
		
		Colaborador colaborador1 = getEntity();
		colaborador1.setCodigoAC("010203");
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getEntity();
		colaborador2.setCodigoAC("010204");
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		assertTrue(colaboradorDao.desligaByCodigo(empresa, new Date(), new String[]{"010203","010204"}));
		
		Colaborador colDesligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNotNull(colDesligado.getDataDesligamento());
		assertTrue(colDesligado.isDesligado());

		Colaborador colDesligado2 = colaboradorDao.findByCodigoAC("010204", empresa);
		assertNotNull(colDesligado2.getDataDesligamento());
		assertTrue(colDesligado2.isDesligado());
		
		assertTrue(colaboradorDao.desligaByCodigo(empresa, null, new String[]{"010203","010204"}));
		
		Colaborador colReligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNull(colReligado.getDataDesligamento());
		assertFalse(colReligado.isDesligado());

		Colaborador colReligado2 = colaboradorDao.findByCodigoAC("010204", empresa);
		assertNull(colReligado2.getDataDesligamento());
		assertFalse(colReligado2.isDesligado());
	}

	@Test
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
	
	@Test
	public void testFindAniversariantesByEmpresa() throws Exception 
	{
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);

		criaColaboradorPara(DateUtil.criarDataMesAno(07, 4, 1984), "jose@email.com", false, empresa);
		criaColaboradorPara(DateUtil.criarDataMesAno(07, 5, 1984), "chico@email.com", false, empresa);
		criaColaboradorPara(DateUtil.criarDataMesAno(25, 4, 1984), "maria@email.com", false, empresa);
		criaColaboradorPara(DateUtil.criarDataMesAno(07, 4, 1984), "", false, empresa);
		criaColaboradorPara(DateUtil.criarDataMesAno(07, 4, 1984), null, false, empresa);
		criaColaboradorPara(DateUtil.criarDataMesAno(07, 4, 1984), "joão@email.com", true, empresa);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findAniversariantesByEmpresa(empresa.getId(), 7, 4);
		
		assertEquals(1, colaboradores.size());
		assertEquals("jose@email.com", ((Colaborador)colaboradores.toArray()[0]).getContato().getEmail());
	}

	private void criaColaboradorPara(Date dataAniversario, String email, boolean desligado, Empresa empresa)
	{
		Colaborador colaborador = getEntity();
		colaborador.getPessoal().setDataNascimento(dataAniversario);
		colaborador.getContato().setEmail(email);
		colaborador.setDesligado(desligado);
		colaborador.setEmpresa(empresa);
		
		colaboradorDao.save(colaborador);
	}

	@Test
	public void testFindByUsuarioProjection() throws Exception 
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setNome("João Batista");
		usuarioDao.save(usuario);

		Colaborador colaborador = getEntity();
		colaborador.setNome("João");
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2.setNome("João Batista");
		usuario2.setAcessoSistema(false);
		usuarioDao.save(usuario2);

		Colaborador colaborador2 = getEntity();
		colaborador2.setNome("João");
		colaborador2.setUsuario(usuario2);
		colaboradorDao.save(colaborador2);

		Colaborador retorno = colaboradorDao.findByUsuarioProjection(usuario.getId(), null);
		assertEquals("João", retorno.getNome());
		assertEquals("João Batista", retorno.getUsuario().getNome());
		
		retorno = colaboradorDao.findByUsuarioProjection(usuario2.getId(), true);
		assertNull(retorno);
	}

	@Override
	public void testRemove() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		colaboradorDao.remove(colaborador);

		colaborador = getGenericDao().findById(colaborador.getId());

		assertNull(colaborador);
	}

	@Test
	public void testFindSemUsuarios() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa("empresa", "21212121212", "empresa"));
		Empresa empresa2 = empresaDao.save(EmpresaFactory.getEmpresa("empresa", "21212121212", "empresa"));

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
	
	@Test
	public void testFindComAnoDeEmpresa() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa_A = empresaDao.save(empresaDao.save(EmpresaFactory.getEmpresa("empresa", "", "empresa")));
		Empresa empresa_B = empresaDao.save(EmpresaFactory.getEmpresa());

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa_A);
		c1.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 2015));
		c1 = colaboradorDao.save(c1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa_A);
		c2.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 2015));
		c2 = colaboradorDao.save(c2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa_B);
		c3.setDataAdmissao(DateUtil.criarDataMesAno(02, 01, 2015));
		c3 = colaboradorDao.save(c3);

		Collection<Colaborador> colaboradores = colaboradorDao.findComAnoDeEmpresa(empresa_A.getId(), DateUtil.criarDataMesAno(01, 01, 2016));

		assertFalse(colaboradores.isEmpty());
		assertEquals(colaboradores.size(), 1);
		assertEquals(c1.getId(), ((Colaborador) colaboradores.toArray()[0]).getId());
	}

	@Test
	public void testFindSemUsuariosComUsuario() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());
		Empresa empresa2 = empresaDao.save(EmpresaFactory.getEmpresa());

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

	@Test
	public void testGetCount() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());

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
	@Test
	public void testFindList() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());

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
		Empresa empresa2 = empresaDao.save(EmpresaFactory.getEmpresa());

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

	@Test
	public void testFindColaboradorPesquisa() {
		AreaOrganizacional area1 = new AreaOrganizacional();
		area1.setNome("area 1");
		area1 = areaOrganizacionalDao.save(area1);

		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());
		Empresa empresa2 = empresaDao.save(EmpresaFactory.getEmpresa());

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

	@Test
	public void testFindByUsuario() {
		AreaOrganizacional area1 = new AreaOrganizacional();
		area1.setNome("area 1");
		area1 = areaOrganizacionalDao.save(area1);

		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());
		Empresa empresa2 = empresaDao.save(EmpresaFactory.getEmpresa());

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

	@Test
	public void testFindColaboradorUsuarioByCpf() {

		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());
		Empresa empresa2 = empresaDao.save(EmpresaFactory.getEmpresa());

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

	@Test
	public void testeFindbyCandidato() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());

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

		Colaborador colaboradorRetorno = colaboradorDao.findByCandidato(candidato.getId(), empresa.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	@Test
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

		assertNull(colaboradorDao.findByCandidato(candidato.getId(), empresa.getId()));
	}

	private Candidato getCandidato() 
	{
		return MockCandidato.getCandidato();
	}

	private Colaborador getColaborador() 
	{
		return MockColaborador.getColaborador();
	}
	
	@Test
	public void testCountSexo() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixa1.setCargo(cargo1);
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixa2.setCargo(cargo2);
		faixaSalarialDao.save(faixa2);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);// data
		saveColaborador('M', false, DateUtil.criarDataMesAno(03, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.ESTAGIO, area, faixa1, estabelecimento1);// data
																																				// superior
		
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.SOCIO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa2, estabelecimento2); //faixa de outro cargo
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2007), null, Vinculo.EMPREGO, area, faixa1, estabelecimento1); // desligado
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2009), null, Vinculo.EMPREGO, area, faixa1, estabelecimento1); // desligado após a período pesquisado
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), outraEmpresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);// outra empresa
		
		Collection<DataGrafico> data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{cargo1.getId()}, new String[]{Vinculo.EMPREGO});
		assertEquals(2, data.size());

		DataGrafico fem = (DataGrafico) data.toArray()[0];
		DataGrafico mas = (DataGrafico) data.toArray()[1];

		assertEquals("Feminino", fem.getLabel());
		assertEquals(new Float(2.0), new Float(fem.getData()));

		assertEquals("Masculino", mas.getLabel());
		assertEquals(new Float(1.0), new Float(mas.getData()));
		

		data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), null,  new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, null, new String[]{Vinculo.SOCIO});
		assertEquals(1, data.size());
		
		data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), null,  new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, null, new String[]{Vinculo.EMPREGO});
		assertEquals(2, data.size());
		
		data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()),  new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{}, null, new String[]{Vinculo.EMPREGO});
		assertEquals(2, data.size());
	}

	@Test
	public void testCountVinculo() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixa1.setCargo(cargo1);
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixa2.setCargo(cargo2);
		faixaSalarialDao.save(faixa2);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa2, estabelecimento2);// faixa de outro cargo
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento2);// data superior
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.APRENDIZ, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.ESTAGIO, area, faixa1, estabelecimento1);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2007), null, null, area, faixa1, estabelecimento1);// desligado
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2009), null, null, area, faixa1, estabelecimento2);// desligado
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), outraEmpresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixa1, estabelecimento1);// outra empresa

		assertEquals(2,colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento2.getId()}, null, null, null).size());
		assertEquals(3,colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId()}, null, null, null).size());

		assertEquals(4, colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), null, null, null, null).size());
		
		Collection<DataGrafico> data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{cargo1.getId()}, null);
		assertEquals(4, data.size());

		DataGrafico empregado = (DataGrafico) data.toArray()[0];
		DataGrafico aprendiz = (DataGrafico) data.toArray()[1];

		assertEquals(new Float(1.0), new Float(empregado.getData()));
		assertEquals(new Float(1.0), new Float(aprendiz.getData()));
		
		data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), null, null, new Long[]{area.getId()}, null, new String[]{Vinculo.EMPREGO});
		assertEquals(1, data.size());
		
		data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), null, null, new Long[]{area.getId()}, null, null);
		assertEquals(4, data.size());
	}

	@Test
	public void testCountEstadoCivil() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixa1.setCargo(cargo1);
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixa2.setCargo(cargo2);
		faixaSalarialDao.save(faixa2);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.ESTAGIO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa2, estabelecimento2); // faixa de outro cargo
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.ESTAGIO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixa1, estabelecimento1);

		Collection<DataGrafico> data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId()}, new Long[]{area.getId()}, new Long[]{cargo1.getId()}, new String[]{Vinculo.EMPREGO});
		assertEquals(4, data.size());

		DataGrafico casado = (DataGrafico) data.toArray()[0];
		DataGrafico divorciado = (DataGrafico) data.toArray()[1];
		DataGrafico solteiro = (DataGrafico) data.toArray()[2];
		DataGrafico viuvo = (DataGrafico) data.toArray()[3];

		assertEquals("Casado", casado.getLabel());
		assertEquals(new Float(4.0), new Float(casado.getData()));

		assertEquals("Divorciado", divorciado.getLabel());
		assertEquals(new Float(2.0), new Float(divorciado.getData()));

		assertEquals("Solteiro", solteiro.getLabel());
		assertEquals(new Float(2.0), new Float(solteiro.getData()));

		assertEquals("Viúvo", viuvo.getLabel());
		assertEquals(new Float(1.0), new Float(viuvo.getData()));

		data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{}, new Long[]{cargo1.getId()}, new String[]{Vinculo.EMPREGO});
		assertEquals(4, data.size());
		data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), null, null, new Long[]{area.getId()}, new Long[]{cargo1.getId()}, new String[]{Vinculo.EMPREGO});
		assertEquals(4, data.size());
	}

	@Test
	public void testCountFaixaEtaria() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);

		Cargo cargoFora = CargoFactory.getEntity();
		cargoDao.save(cargoFora);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);		
				
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, DateUtil.criarDataMesAno(31, 01, 1989), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, DateUtil.criarDataMesAno(31, 01, 1988), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, DateUtil.criarDataMesAno(31, 01, 1986), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, DateUtil.criarDataMesAno(23, 02, 1978), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);

		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, DateUtil.criarDataMesAno(18, 12, 1977), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, DateUtil.criarDataMesAno(18, 12, 1975), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento2);

		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, DateUtil.criarDataMesAno(01, 02, 1968), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento2);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, DateUtil.criarDataMesAno(31, 12, 1960), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);

		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, DateUtil.criarDataMesAno(31, 01, 1950), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);

		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, DateUtil.criarDataMesAno(18, 12, 1947), Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO, area, faixaSalarial, estabelecimento1);

		Collection<DataGrafico> data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, new String[]{Vinculo.EMPREGO});

		DataGrafico faixa1 = (DataGrafico) data.toArray()[0];
		DataGrafico faixa2 = (DataGrafico) data.toArray()[1];
		DataGrafico faixa3 = (DataGrafico) data.toArray()[2];
		DataGrafico faixa4 = (DataGrafico) data.toArray()[3];
		DataGrafico faixa5 = (DataGrafico) data.toArray()[4];
		DataGrafico faixa6 = (DataGrafico) data.toArray()[5];

		assertEquals("Até 19", faixa1.getLabel());
		assertEquals(new Float(1.0), new Float(faixa1.getData()));

		assertEquals("20 a 29", faixa2.getLabel());
		assertEquals(new Float(3.0), new Float(faixa2.getData()));

		assertEquals("30 a 39", faixa3.getLabel());
		assertEquals(new Float(1.0), new Float(faixa3.getData()));

		assertEquals("40 a 49", faixa4.getLabel());
		assertEquals(new Float(1.0), new Float(faixa4.getData()));

		assertEquals("50 a 59", faixa5.getLabel());
		assertEquals(new Float(1.0), new Float(faixa5.getData()));

		assertEquals("Acima de 60", faixa6.getLabel());
		assertEquals(new Float(1.0), new Float(faixa6.getData()));
		
		data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), null, new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, new String[]{Vinculo.EMPREGO});
		assertEquals(6, data.size());
		data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), Arrays.asList(empresa.getId()), null, new Long[]{area.getId()}, new Long[]{cargo.getId()}, new String[]{Vinculo.EMPREGO});
		assertEquals(6, data.size());
	}

	@Test
	public void testCountMotivoDemissao() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Cargo cargoFora = CargoFactory.getEntity();
		cargoDao.save(cargoFora);

		FaixaSalarial faixaSalarialFora = FaixaSalarialFactory.getEntity();
		faixaSalarialFora.setCargo(cargoFora);
		faixaSalarialDao.save(faixaSalarialFora);
		
		MotivoDemissao motivoFalta = new MotivoDemissao();
		motivoFalta.setMotivo("Falta");
		motivoDemissaoDao.save(motivoFalta);

		MotivoDemissao motivoPediuPraSair = new MotivoDemissao();
		motivoPediuPraSair.setMotivo("Pediu pra Sair");
		motivoDemissaoDao.save(motivoPediuPraSair);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('F', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2005), motivoFalta, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('M', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoFalta, null, area, faixaSalarialFora, estabelecimento1); // faixa de outro cargo
		saveColaborador('M', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoPediuPraSair, null, area, faixaSalarial, estabelecimento1);

		saveColaborador('M', true, new Date(), outraEmpresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoPediuPraSair, null, area, faixaSalarial, estabelecimento2);
		saveColaborador('M', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2003), motivoPediuPraSair, null, area, faixaSalarial, estabelecimento2);
		saveColaborador('M', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2010), motivoPediuPraSair, null, area, faixaSalarial, estabelecimento2);

		Collection<DataGrafico> motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId()}, null, null, 2);
		assertEquals(2, motivos.size());
		//passando area e cargo
		motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, 2);
		assertEquals(2, motivos.size());

		DataGrafico motivo1 = (DataGrafico) motivos.toArray()[0];
		DataGrafico motivo2 = (DataGrafico) motivos.toArray()[1];

		assertEquals("Falta", motivo1.getLabel());
		assertEquals(new Float(1.0), new Float(motivo1.getData()));

		assertEquals("Pediu pra Sair", motivo2.getLabel());
		assertEquals(new Float(1.0), new Float(motivo2.getData()));
		
		motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), null, new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, new Long[]{area.getId()}, null, 2);
		assertEquals(2, motivos.size());
	}

	@Test
	public void testCountAdmitidos() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Cargo cargoFora = CargoFactory.getEntity();
		cargoDao.save(cargoFora);

		FaixaSalarial faixaSalarialFora = FaixaSalarialFactory.getEntity();
		faixaSalarialFora.setCargo(cargoFora);
		faixaSalarialDao.save(faixaSalarialFora);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('F', true, DateUtil.criarDataMesAno(02, 03, 2005), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarialFora, estabelecimento1);
		saveColaborador('M', true, DateUtil.criarDataMesAno(02, 03, 2003), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento2);
		saveColaborador('F', true, DateUtil.criarDataMesAno(02, 03, 2011), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2004), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2009), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento2);

		int count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, new Long[]{estabelecimento1.getId()}, null, null, true);
		assertEquals(2, count);
		
		count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, new Long[]{estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, true);
		assertEquals(1, count);
	}

	@Test
	public void testCountDemitidos() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Cargo cargoFora = CargoFactory.getEntity();
		cargoDao.save(cargoFora);

		FaixaSalarial faixaSalarialFora = FaixaSalarialFactory.getEntity();
		faixaSalarialFora.setCargo(cargoFora);
		faixaSalarialDao.save(faixaSalarialFora);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('F', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2005), null, null, area, faixaSalarialFora, estabelecimento1);
		saveColaborador('M', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2003), null, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2011), null, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2004), null, null, area, faixaSalarial, estabelecimento2);
		saveColaborador('F', true, new Date(), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2009), null, null, area, faixaSalarial, estabelecimento2);

		int count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, new Long[]{estabelecimento1.getId()}, null, null, false);
		assertEquals(1, count);
		
		count = colaboradorDao.countAdmitidosDemitidosTurnover(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa, new Long[]{estabelecimento2.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, false);
		assertEquals(2, count);
	}

	private void saveColaborador(char sexo, boolean desligado, Date dataAdmissao, Empresa empresa, String estadoCivil, Date dataNascimento, char deficiencia, Date dataDesligamento,
			MotivoDemissao motivoDemissao, String vinculo, AreaOrganizacional area, FaixaSalarial faixaSalarial, Estabelecimento estabelecimento) {
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
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaboradorAtual);
	}

	@Test
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

	@Test
	public void testFindColaboradorByIdProjection() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorByIdProjection(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	@Test
	public void testDesligaColaborador() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataDesligamento(null);
		colaborador.setObservacaoDemissao("");
		colaborador.setDesligado(false);
		colaborador.setMotivoDemissao(null);
		colaborador.setDemissaoGerouSubstituicao(null);
		colaborador = colaboradorDao.save(colaborador);

		MotivoDemissao motivo = new MotivoDemissao();
		motivo.setMotivo("motivo");
		motivo = motivoDemissaoDao.save(motivo);

		Date dataDesligamento = new Date();
		colaboradorDao.desligaColaborador(true, dataDesligamento, "desligado", motivo.getId(), 'I', colaborador.getId());

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());

		assertEquals(true, colaboradorRetorno.isDesligado());
		assertEquals("desligado", colaboradorRetorno.getObservacaoDemissao());
		assertEquals(DateUtil.formataDiaMesAno(dataDesligamento), DateUtil.formataDiaMesAno(colaboradorRetorno.getDataDesligamento()));
		assertEquals(motivo.getId(), colaboradorRetorno.getMotivoDemissao().getId());
		assertEquals((Character)'I', colaboradorRetorno.getDemissaoGerouSubstituicao());
	}
	
	@Test
	public void testDesligaMultiplosColaboradores() {
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setDataDesligamento(null);
		colaborador1.setObservacaoDemissao("");
		colaborador1.setDesligado(false);
		colaborador1.setMotivoDemissao(null);
		colaborador1.setDemissaoGerouSubstituicao(null);
		colaborador1 = colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setDataDesligamento(null);
		colaborador2.setObservacaoDemissao("");
		colaborador2.setDesligado(false);
		colaborador2.setMotivoDemissao(null);
		colaborador1.setDemissaoGerouSubstituicao(null);
		colaborador2 = colaboradorDao.save(colaborador2);
		
		MotivoDemissao motivo = new MotivoDemissao();
		motivo.setMotivo("motivo");
		motivo = motivoDemissaoDao.save(motivo);
		
		Date dataDesligamento = new Date();
		colaboradorDao.desligaColaborador(true, dataDesligamento, "desligado", motivo.getId(), 'S', new Long[]{colaborador1.getId(), colaborador2.getId()});
		
		Colaborador colaboradorRetorno1 = colaboradorDao.findColaboradorById(colaborador1.getId());
		Colaborador colaboradorRetorno2 = colaboradorDao.findColaboradorById(colaborador2.getId());
		
		assertEquals(true, colaboradorRetorno1.isDesligado());
		assertEquals("desligado", colaboradorRetorno1.getObservacaoDemissao());
		assertEquals(DateUtil.formataDiaMesAno(dataDesligamento), DateUtil.formataDiaMesAno(colaboradorRetorno1.getDataDesligamento()));
		assertEquals(motivo.getId(), colaboradorRetorno1.getMotivoDemissao().getId());
		assertEquals((Character)'S', colaboradorRetorno1.getDemissaoGerouSubstituicao());
		
		
		assertEquals(true, colaboradorRetorno2.isDesligado());
		assertEquals("desligado", colaboradorRetorno2.getObservacaoDemissao());
		assertEquals(DateUtil.formataDiaMesAno(dataDesligamento), DateUtil.formataDiaMesAno(colaboradorRetorno2.getDataDesligamento()));
		assertEquals(motivo.getId(), colaboradorRetorno2.getMotivoDemissao().getId());
		assertEquals((Character)'S', colaboradorRetorno2.getDemissaoGerouSubstituicao());
	}

	@Test public void testFindByIdProjectionEmpresa() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	@Test public void testFindByNomeCpfMatricula() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity("1234", "teste", empresa, new Date(), null);
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador1, new Date(), null, null, null, null, null, StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(colaborador2, new Date(), null, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);

		Colaborador colaborador3 = ColaboradorFactory.getEntity(null, "Eva", empresa, new Date(), null);
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity(colaborador3, DateUtil.incrementaDias(new Date(), 1), null, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		assertEquals(0, colaboradorDao.findByNomeCpfMatricula(colaborador1, false, new String[]{"teste"}, null, new Long[]{empresa.getId()}).size());
		assertEquals(1, colaboradorDao.findByNomeCpfMatricula(colaborador1, false, null, null, new Long[]{empresa.getId()}).size());
		assertEquals(2, colaboradorDao.findByNomeCpfMatricula(null, false, new String[]{"Eva"}, null, new Long[]{empresa.getId()}).size());
		assertEquals(3, colaboradorDao.findByNomeCpfMatricula(null, false, null, null, new Long[]{empresa.getId()}).size());
		assertEquals(1, colaboradorDao.findByNomeCpfMatricula(null, false, null, StatusRetornoAC.CONFIRMADO, new Long[]{empresa.getId()}).size());
		assertEquals(1, colaboradorDao.findByNomeCpfMatricula(null, false, null, StatusRetornoAC.AGUARDANDO, new Long[]{empresa.getId()}).size());
	}

	@Test public void testFindByNomeCpfMatriculaComHistoricoComfirmado() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		// Colaborador 1
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("teste");
		colaborador1.setMatricula("1234");
		colaboradorDao.save(colaborador1);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional1);
		historicoColaborador1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1);

		// Colaborador 2
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setNome("teste");
		colaborador2.setMatricula("1234");
		colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// Colaborador 3
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(empresa);
		colaborador3.setPessoalCpf("12345678910");
		colaborador3.setNome("teste");
		colaborador3.setMatricula("1234");
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador3.setColaborador(colaborador3);
		historicoColaborador3.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador3.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		assertEquals(2, colaboradorDao.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador1, empresa.getId(), null).size());
		assertEquals(1, colaboradorDao.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador1, empresa.getId(), new Long[]{areaOrganizacional1.getId()}).size());
		assertEquals(0, colaboradorDao.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador3, empresa.getId(), null).size());
	}

	@Test public void testSetCodigoColaboradorAC() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(true, colaboradorDao.setCodigoColaboradorAC("codigo", colaborador.getId()));
	}

	@Test public void testFindByCodigoAC() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa));
	}

	@Test public void testFindByCodigoACUsuario() {
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

	@Test public void testFindByCodigoACEmpresaString() {
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

	@Test public void testFindByFuncaoAmbiente() {
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
	
	@Test public void testFindFuncaoAmbiente() {
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

	@Test public void testFindByAreaOrganizacionalIds() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador1 = saveColaborador(empresa, "Pedro Jose", "12e3456789", "000", new Date());
		Colaborador colaborador2 = saveColaborador(empresa, "Maria", "123", null, new Date());

		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);

		saveHistoricoColaborador(colaborador1, null, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador2, null, areaOrganizacional, faixaSalarial, DateUtil.setaMesPosterior(new Date()), StatusRetornoAC.CONFIRMADO);
		
		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional.getId());

		Colaborador colaboradorBusca = ColaboradorFactory.getEntity(1L);
		colaboradorBusca.setNome("dro j");
		colaboradorBusca.setMatricula("2E3");
		colaboradorBusca.getPessoal().setCpf("000");
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalIds(areaIds, null, null, 1, 10, colaboradorBusca, null, null, empresa.getId(), false, false, null);

		assertEquals(1, colaboradores.size());
		Colaborador retorno = (Colaborador) colaboradores.toArray()[0];
		assertEquals(faixaSalarial, retorno.getFaixaSalarial());
		assertEquals(cargo, retorno.getFaixaSalarial().getCargo());
		assertEquals(grupoOcupacional, retorno.getFaixaSalarial().getCargo().getGrupoOcupacional());
		assertEquals(areaOrganizacional, retorno.getAreaOrganizacional());
		assertEquals("Pedro Jose", retorno.getNome());

		// Teste findByAreaOrganizacionalIds
		Long[] areasIds = new Long[] { areaOrganizacional.getId() };
		colaboradores = colaboradorDao.findByAreaOrganizacionalIds(1, 10, areasIds, null, null, null, null, null, empresa.getId(), false, false, null);
		assertEquals(1, colaboradores.size());

		// Teste findByAreaOrganizacionalIds
		colaboradores = colaboradorDao.findByAreaOrganizacionalIds(areasIds);
		assertEquals(1, colaboradores.size());

		// Teste findByArea
		colaboradores = colaboradorDao.findByArea(areaOrganizacional);
		assertEquals(1, colaboradores.size());
	}

	@Test public void testFindByAreaOrganizacionalIdsPorPeriodoAdmissao() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setEmpresa(empresa);
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Colaborador jose = saveColaborador(empresa, "Jose", "12e3456789", null, DateUtil.criarDataMesAno(1, 1, 2011));
		Colaborador joao = saveColaborador(empresa, "Joao", "12e3456999", null, DateUtil.criarDataMesAno(7, 7, 2011));
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);

		saveHistoricoColaborador(jose, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2011), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(joao, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(7, 7, 2011), StatusRetornoAC.CONFIRMADO);

		Colaborador colaboradorBusca = new Colaborador();
		Date dataAdmissaoIni = DateUtil.criarDataMesAno(6, 6, 2011);
		Date dataAdmissaoFim = DateUtil.criarDataMesAno(8, 8, 2011);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalIds(Arrays.asList(areaOrganizacional.getId()), Arrays.asList(estabelecimento.getId()),null, null, null, colaboradorBusca, dataAdmissaoIni, dataAdmissaoFim, empresa.getId(), false, false, null);
		assertEquals(1, colaboradores.size());
		assertEquals(joao, colaboradores.toArray()[0]);
	}
	
	@Test public void testFindByAreaEstabelecimento() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());

		Colaborador colaborador = saveColaborador(empresa, "Junior", "", "", new Date());

		AreaOrganizacional areaOrganizacionalAtual = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacionalAntiga = saveAreaOrganizacional();

		Estabelecimento estabelecimentoAtual = saveEstabelecimento();
		Estabelecimento estabelecimentoAntigo = saveEstabelecimento();

		saveHistoricoColaborador(colaborador, estabelecimentoAtual, areaOrganizacionalAtual, null, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, estabelecimentoAntigo, areaOrganizacionalAntiga, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaEstabelecimento(areaOrganizacionalAtual.getId(), estabelecimentoAtual.getId());

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(areaOrganizacionalAtual, colaboradorDoBanco.getAreaOrganizacional());
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	@Test public void testFindByEstabelecimento() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = saveEstabelecimento();
		Estabelecimento estabelecimentoAntigo = saveEstabelecimento();

		saveHistoricoColaborador(colaborador, estabelecimentoAtual, null, null, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, estabelecimentoAntigo, null, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);

		Collection<Colaborador> colaboradores = colaboradorDao.findByEstabelecimento(new Long[] { estabelecimentoAtual.getId() });

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	@Test public void testGetCountAtivosByEstabelecimento() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = saveEstabelecimento();
		Estabelecimento estabelecimentoAntigo = saveEstabelecimento();

		saveHistoricoColaborador(colaborador, estabelecimentoAtual, null, null, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, estabelecimentoAntigo, null, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);

		assertEquals((Integer) 1, colaboradorDao.getCountAtivosByEstabelecimento(estabelecimentoAtual.getId()));
	}

	@Test public void testFindByIdHistoricoProjection() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setMatricula("123456");
		colaboradorDao.save(colaborador);
		
		Cargo cargo1 = saveCargo();
		FaixaSalarial faixaSalarial1 = saveFaixaSalarial(cargo1);

		Cargo cargo2 = saveCargo();
		FaixaSalarial faixaSalarial2 = saveFaixaSalarial(cargo2);

		saveHistoricoColaborador(colaborador, faixaSalarial1, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, faixaSalarial2, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
 
 		Colaborador colaboradorTmp = colaboradorDao.findByIdHistoricoProjection(colaborador.getId());
 
 		assertEquals(colaborador, colaboradorTmp);
 		assertEquals(colaborador.getMatricula(), colaboradorTmp.getMatricula());
		assertEquals(faixaSalarial1.getDescricao(), colaboradorTmp.getHistoricoColaborador().getFaixaSalarial().getDescricao());
	}

	@Test public void testFindByIdHistoricoAtualIds() {
		Colaborador colaborador = getColaborador();
		colaborador.setNome("TESTE");
		colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();

		Cargo cargo1 = saveCargo();
		FaixaSalarial faixaSalarial1 = saveFaixaSalarial(cargo1);

		Cargo cargo2 = saveCargo();
		FaixaSalarial faixaSalarial2 = saveFaixaSalarial(cargo2);
		
		saveHistoricoColaborador(colaborador, null, areaOrganizacional, faixaSalarial2, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, null, areaOrganizacional, faixaSalarial1, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		Collection<Long> ids = new ArrayList<Long>();
		ids.add(colaborador.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findByIdHistoricoAtual(ids);
		assertEquals(1, colaboradors.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradors.toArray()[0];
		assertEquals(colaborador.getNome(), colaboradorTmp.getNome());
		assertEquals(faixaSalarial1.getDescricao(), colaboradorTmp.getFaixaSalarial().getDescricao());
		assertEquals(areaOrganizacional, colaboradorTmp.getAreaOrganizacional());
	}

	@Test public void testFindColaboradorByDataHistorico() {
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Maria João");
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Analista de Sistemas");
		cargoDao.save(cargo1);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("Júnior");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo1);
		faixaSalarial2.setNome("Sênior");
		faixaSalarialDao.save(faixaSalarial2);
		
		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2014));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial2);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);
		
		Colaborador colaboradorComHistoricoAntigo = colaboradorDao.findColaboradorByDataHistorico(colaborador.getId(), historicoColaboradorAntigo.getData());
		Colaborador colaboradorComHistoricoAtual = colaboradorDao.findColaboradorByDataHistorico(colaborador.getId(), historicoColaboradorAtual.getData());
		Colaborador colaboradorFindDataAtual = colaboradorDao.findColaboradorByDataHistorico(colaborador.getId(), new Date());
		
		assertEquals(faixaSalarial1.getDescricao(), colaboradorComHistoricoAntigo.getFaixaSalarial().getDescricao());
		assertEquals(faixaSalarial2.getDescricao(), colaboradorComHistoricoAtual.getFaixaSalarial().getDescricao());
		assertEquals(faixaSalarial2.getDescricao(), colaboradorFindDataAtual.getFaixaSalarial().getDescricao());
	}
	
	@Test public void testFindByIdHistoricoAtual() {
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

	@Test public void testFindByAreasOrganizacionaisEstabelecimentos() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());

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

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity("A", empresa);
		estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity("B", empresa);
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

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreasOrganizacionaisEstabelecimentos(areasIds, estabelecimentosIds, "francisco", null);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(areaOrganizacionalAtual, colaboradorDoBanco.getAreaOrganizacional());
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}
	
	@Test public void testFindByAreaOrganizacionalEstabelecimento() 
	{
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador(empresa, "Alberto", new Date(), false, null, false);
		Colaborador colaborador2 = saveColaborador(empresa, "Beto", new Date(), true, new Date(), false);
		Colaborador colaborador3 = saveColaborador(empresa, "Carla", new Date(), false, new Date(), true);
		
		AreaOrganizacional areaOrganizacionalAtual = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacionalAntiga = saveAreaOrganizacional();
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacionalAtual.getId());
		
		Estabelecimento estabelecimentoAtual = saveEstabelecimento();
		Estabelecimento estabelecimentoAntigo = saveEstabelecimento();

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());
		
		saveHistoricoColaborador(colaborador, estabelecimentoAntigo, areaOrganizacionalAntiga, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, estabelecimentoAtual, areaOrganizacionalAtual, null, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		saveHistoricoColaborador(colaborador2, estabelecimentoAtual, areaOrganizacionalAtual, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		
		saveHistoricoColaborador(colaborador3, estabelecimentoAtual, areaOrganizacionalAtual, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> colaboradoresIngtegradosComAC = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.TODOS, null, true);
		Collection<Colaborador> colaboradoresAtivos = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.ATIVO, null, false);
		Collection<Colaborador> colaboradoresDesligados = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.DESLIGADO, null, false);
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.TODOS, null, false);
		
		assertEquals(3, colaboradores.size());
		assertEquals(2, colaboradoresIngtegradosComAC.size());
		
		Colaborador colaboradorAtivo = (Colaborador) colaboradoresAtivos.toArray()[0];
		
		assertEquals(2, colaboradoresAtivos.size());
		assertEquals(colaborador.getId(), colaboradorAtivo.getId());
		
		Colaborador colaboradorInativo = (Colaborador) colaboradoresDesligados.toArray()[0];
		
		assertEquals(1, colaboradoresDesligados.size());
		assertEquals(colaborador2.getId(), colaboradorInativo.getId());
	}
	
	@Test public void testFindByAreaOrganizacionalEstabelecimentoIntegradosComAC() 
	{
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, new Date(), false, null, false);
		Colaborador colaborador2 = saveColaborador("Samuel", empresa, new Date(), true, new Date(), false);
		
		AreaOrganizacional areaOrganizacionalAtual = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacionalAntiga = saveAreaOrganizacional();
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacionalAtual.getId());
		
		Estabelecimento estabelecimentoAtual = saveEstabelecimento();
		Estabelecimento estabelecimentoAntigo = saveEstabelecimento();
		
		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());
		
		saveHistoricoColaborador(colaborador, estabelecimentoAntigo, areaOrganizacionalAntiga, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador, estabelecimentoAtual, areaOrganizacionalAtual, null, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		saveHistoricoColaborador(colaborador2, estabelecimentoAtual, areaOrganizacionalAtual, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> colaboradoresAtivos = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.ATIVO, null, false);
		Collection<Colaborador> colaboradoresDesligados = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.DESLIGADO, null, false);
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.TODOS, null, false);
		
		assertEquals(2, colaboradores.size());
		
		Colaborador colaboradorAtivo = (Colaborador) colaboradoresAtivos.toArray()[0];
		
		assertEquals(1, colaboradoresAtivos.size());
		assertEquals(colaborador.getId(), colaboradorAtivo.getId());
		Colaborador colaboradorInativo = (Colaborador) colaboradoresDesligados.toArray()[0];
		
		assertEquals(1, colaboradoresDesligados.size());
		assertEquals(colaborador2.getId(), colaboradorInativo.getId());
	}
	
	@Test public void testFindByAreaOrganizacionalEstabelecimentoComNotUsuarioId() 
	{
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("João", empresa, usuario, "email@email.com");
		colaborador.setDesligado(false);
		colaboradorDao.save(colaborador);
		
		Colaborador colaborador2 = saveColaborador("Samuel", empresa, new Date(), true, new Date(), false);
		
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaOrganizacional.getId());
		
		Estabelecimento estabelecimento = saveEstabelecimento();

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimento.getId());
		
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, null, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colaborador2, estabelecimento, areaOrganizacional, null, DateUtil.criarDataMesAno(1, 1, 2007), StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalEstabelecimento(areasIds, estabelecimentosIds, SituacaoColaborador.TODOS, usuario.getId(), false);
		
		assertEquals(1, colaboradores.size());
		assertEquals(colaborador2.getId(), ((Colaborador) colaboradores.toArray()[0]).getId());
	}


	@Test public void testFindByCargoIdsEstabelecimentoIds() {
		Empresa empresa = new Empresa();
		empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setNome("francisco");
		colaboradorDao.save(colaborador);

		Cargo cargoAtual = CargoFactory.getEntity();
		cargoDao.save(cargoAtual);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
		faixaSalarialAtual.setCargo(cargoAtual);
		faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

		Collection<Long> cargosIds = new ArrayList<Long>();
		cargosIds.add(cargoAtual.getId());
		
		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity("A ", empresa );
		estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity("B ", empresa );
		estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, Calendar.getInstance().get(Calendar.YEAR)));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarialAtual);
		historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, Calendar.getInstance().get(Calendar.YEAR) - 1));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorFuturo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFuturo.setData(DateUtil.criarDataMesAno(1, 1, Calendar.getInstance().get(Calendar.YEAR) + 1));
		historicoColaboradorFuturo.setColaborador(colaborador);
		historicoColaboradorFuturo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorDao.save(historicoColaboradorFuturo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds, "franci", null);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	@Test public void testFindByGrupoOcupacionalIdsEstabelecimentoIds() {
		Empresa empresa = new Empresa();
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

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity(" A", empresa);
		estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity(" B", empresa);
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, Calendar.getInstance().get(Calendar.YEAR)));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarialAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, Calendar.getInstance().get(Calendar.YEAR) - 1));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorFuturo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFuturo.setData(DateUtil.criarDataMesAno(1, 1, Calendar.getInstance().get(Calendar.YEAR) + 1));
		historicoColaboradorFuturo.setColaborador(colaborador);
		historicoColaboradorFuturo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorFuturo = historicoColaboradorDao.save(historicoColaboradorFuturo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentosIds);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	@Test public void testFindByIdProjectionUsuario() throws Exception {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());

		assertNotNull(colaborador);
		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	@Test public void testFindAreaOrganizacionalByGruposOrAreas() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("estabelecimento ");
		estabelecimentoDao.save(estabelecimento );

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
		colaborador.setDataDesligamento(null);
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
		colaborador2.setDataDesligamento(DateUtil.criarDataMesAno(1, 1, 2010));
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

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, null, null, Sexo.FEMININO, null, null, null, SituacaoColaborador.ATIVO, null, null);

		assertEquals(1, colaboradors.size());
	}
	
	@Test public void testFindAreaOrganizacionalByAreasAndPeriodoDataAdmissao() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("estabelecimento" );
		estabelecimentoDao.save( estabelecimento);
		
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
		colaborador.setDataDesligamento(null);
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
//		colaborador2.setDataDesligamento(null);
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
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, null, null);
		
		assertEquals(1, colaboradors.size());
	}
	
	@Test public void testFindAreaOrganizacionalByAreasAndCargos() {
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
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
		colaborador1.setDataDesligamento(null);
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

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, null, null);
		
		assertEquals(1, colaboradors.size());
		assertEquals(colaborador1.getNome(), ((Colaborador)colaboradors.toArray()[0]).getNome());
	}
	
	@Test public void testFindAreaOrganizacionalByAreasAndEnviadosParaAC() 
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("estabelecimento" );
		estabelecimentoDao.save(estabelecimento);
		
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
		colaborador1.setCodigoAC("00001");
		colaborador1.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador1.setDataDesligamento(null);
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
		colaborador1.setCodigoAC("00002");
		colaborador2.setDataAdmissao(DateUtil.criarDataMesAno(05, 02, 2011));
		colaborador2.setDataDesligamento(null);
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

		Collection<Colaborador> retorno1 = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, todosColaboradores, null);
		Collection<Colaborador> retorno2 = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, colaboradoresNaoEnviadosParaAC, null);
		Collection<Colaborador> retorno3 = colaboradorDao.findAreaOrganizacionalByAreas(false, null, null, cargoIds, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, null, null, null, SituacaoColaborador.ATIVO, colaboradoresEnviadosParaAC, null);
		
		assertEquals(2, retorno1.size());
		
		assertEquals(1, retorno2.size());
		assertEquals(colaborador2.getNome(), ((Colaborador)retorno2.toArray()[0]).getNome());

		assertEquals(1, retorno3.size());
		assertEquals(colaborador1.getNome(), ((Colaborador)retorno3.toArray()[0]).getNome());
	}
	
	@Test public void testFindAreaOrganizacionalBySemDeficiencia()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("estabelecimento " );
		estabelecimentoDao.save( estabelecimento);
		
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
		colaborador.setDataDesligamento(null);
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
		colaborador2.setDataDesligamento(null);
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
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "3", null, null, SituacaoColaborador.ATIVO, null, null);
		
		assertEquals(1, colaboradors.size());
		assertEquals(colaborador2.getNome(), ((Colaborador)colaboradors.toArray()[0]).getNome());
	}
	
	@Test public void testFindAreaOrganizacionalByComDeficiencia()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(" estabelecimento ");
		estabelecimentoDao.save(estabelecimento );
		
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
		colaborador.setDataDesligamento(null);
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
		colaborador2.setDataDesligamento(null);
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
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "2", null, null, SituacaoColaborador.ATIVO, null, null);
		
		assertEquals(1, colaboradors.size());
		assertEquals(colaborador.getNome(), ((Colaborador)colaboradors.toArray()[0]).getNome());
	}
	
	@Test public void testFindAreaOrganizacionalBySemDeficienciaComDeficiencia()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity( "estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
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
		
		Collection<Colaborador> colaboradors1 = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, DateUtil.criarDataMesAno(31, 01, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "1", null, null, SituacaoColaborador.ATIVO, null, null);
		Collection<Colaborador> colaboradors2 = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null, DateUtil.criarDataMesAno(01, 02, 2011), DateUtil.criarDataMesAno(01, 02, 2012), null, "1", null, null, SituacaoColaborador.ATIVO, null, null);
		
		assertEquals("Admissao a partir de 31/01/11",3, colaboradors1.size());
		assertEquals("Admissao a partir de 01/02/11",2, colaboradors2.size());
	}

	@Test public void testFindAreaOrganizacionalByGruposOrAreasCamposExtras() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(" estabelecimento" );
		estabelecimentoDao.save(estabelecimento);

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
		camposExtras.setTextolongo1("textolongo1");
		camposExtras.setTextolongo2("textolongo2");

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

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, null, camposExtras, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null, null);
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

		colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, null, camposExtrasBusca, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null, null);
		assertEquals(1, colaboradors.size());

		// Parâmetros do find
		camposExtrasBusca = CamposExtrasFactory.getEntity();

		camposExtrasBusca.setData1Fim(DateUtil.criarDataMesAno(3, 3, 1000));
		camposExtrasBusca.setData2Fim(DateUtil.criarDataMesAno(4, 4, 2000));
		camposExtrasBusca.setData3Fim(DateUtil.criarDataMesAno(5, 5, 3000));

		camposExtrasBusca.setValor1Fim(20.0);
		camposExtrasBusca.setValor2Fim(30.0);

		camposExtrasBusca.setNumero1Fim(60);

		colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, null, camposExtrasBusca, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null, null);
		assertEquals(1, colaboradors.size());
	}

	@Test public void testFindColaboradoresMotivoDemissao() {
		prepareFindColaboradoresMotivo();

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataFim = DateUtil.criarDataMesAno(30, 12, 2007);

		Long[] estabelecimentoIds = new Long[] { estabelecimento1.getId() };
		Long[] areaIds = new Long[] { areaOrganizacional1.getId() };
		Long[] cargoIds = new Long[] { cargo1.getId() };

		Collection<Colaborador> retorno = colaboradorDao.findColaboradoresMotivoDemissao(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, "M", null);

		assertEquals(1, retorno.size());
	}

	@Test public void testFindColaboradoresMotivoDemissaoQuantidade() {
		prepareFindColaboradoresMotivo();

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataFim = DateUtil.criarDataMesAno(30, 12, 2007);

		Long[] estabelecimentoIds = new Long[] { estabelecimento1.getId() };
		Long[] areaIds = new Long[] { areaOrganizacional1.getId() };
		Long[] cargoIds = new Long[] { cargo1.getId() };

		List<Object[]> retorno = colaboradorDao.findColaboradoresMotivoDemissaoQuantidade(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, null);

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

	@Test public void testFindProjecaoSalarialByHistoricoColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("estabelecimento"));

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

	@Test public void testFindProjecaoSalarialByTabelaReajusteColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("estabelecimento" ));

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

	@Test public void testSetRespondeuEntrevista() {
		Colaborador colaborador = ColaboradorFactory.getEntity();

		colaborador = colaboradorDao.save(colaborador);

		colaboradorDao.setRespondeuEntrevista(colaborador.getId());
	}

	@Test public void testFindAllSelect() {
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
	
	@Test public void testFindAllSelectByIds() {
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
	
	@Test public void testFindComNotaDoCurso() 
	{
		testePorTipoAvaliacao(TipoAvaliacaoCurso.NOTA, true, 8.00);
		testePorTipoAvaliacao(TipoAvaliacaoCurso.PORCENTAGEM, true, 9.57);
		testePorTipoAvaliacao(TipoAvaliacaoCurso.AVALIACAO, true, 0.78);
		testePorTipoAvaliacao(TipoAvaliacaoCurso.NOTA, false, null);
	}

	private void testePorTipoAvaliacao(char tipoAvaliacaoCurso, boolean comResposta, Double nota)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaboradorDao.save(colaborador);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);

		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setTipo(tipoAvaliacaoCurso);
		avaliacaoCursoDao.save(avaliacaoCurso);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);

		if(comResposta){
			if(tipoAvaliacaoCurso == TipoAvaliacaoCurso.AVALIACAO){
				Avaliacao avaliacao = AvaliacaoFactory.getEntity();
				avaliacaoDao.save(avaliacao);
				
				avaliacaoCurso.setAvaliacao(avaliacao);
				avaliacaoCursoDao.save(avaliacaoCurso);
				
				ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
				colaboradorQuestionario.setColaborador(colaborador);
				colaboradorQuestionario.setTurma(turma);
				colaboradorQuestionario.setAvaliacaoCurso(avaliacaoCurso);
				colaboradorQuestionario.setAvaliacao(avaliacao);
				colaboradorQuestionario.setPerformance(nota);
				colaboradorQuestionarioDao.save(colaboradorQuestionario);
				
				nota = nota * 100; // Na consulta, quando o tipo é 'Avaliação', a performance(nota) é multiplicada por 100.
			} else {
				AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
				aproveitamentoAvaliacaoCurso.setColaboradorTurma(colaboradorTurma);
				aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
				aproveitamentoAvaliacaoCurso.setValor(nota);
				aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);
			}
		}
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<Colaborador> colaboradoresRetorno = colaboradorDao.findComNotaDoCurso(Arrays.asList(colaborador.getId()), turma.getId());
		Colaborador colaboradorRetorno = ((Colaborador)(colaboradoresRetorno.toArray())[0]);
		
		if(comResposta)
			assertEquals(nota, colaboradorRetorno.getNota().doubleValue(), 0);
		else
			assertNull(colaboradorRetorno.getNota());
	}
	
	@Test public void testReligaColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity();
		motivoDemissaoDao.save(motivoDemissao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataDesligamento(new Date());
		colaborador.setDataSolicitacaoDesligamento(new Date());
		colaborador.setDataSolicitacaoDesligamentoAc(new Date());
		colaborador.setDesligado(true);
		colaborador.setObservacaoDemissao("demissao");
		colaborador.setMotivoDemissao(motivoDemissao);
		colaboradorDao.save(colaborador);

		colaboradorDao.religaColaborador(colaborador.getId());
		Colaborador colaboradorReligado = colaboradorDao.findColaboradorById(colaborador.getId());
		
		assertNull("Data desligamento", colaboradorReligado.getDataDesligamento());
		assertNull("Data solicitação desligamento", colaboradorReligado.getDataSolicitacaoDesligamento());
		assertNull("Data solicitação desligamento AC", colaboradorReligado.getDataSolicitacaoDesligamentoAc());
		assertNull("Motivo demissão", colaboradorReligado.getMotivoDemissao().getId());
		assertEquals("Observação demissão", "", colaboradorReligado.getObservacaoDemissao());
		assertFalse("Está desligado", colaboradorReligado.isDesligado());
	}

	@Test public void testFindByIdDadosBasicos() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcao.setNome("Função");
		funcaoDao.save(funcao);
		
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
		historicoColaborador.setFuncao(funcao);
		historicoColaboradorDao.save(historicoColaborador);
		
		Colaborador colabRetorno = colaboradorDao.findByIdDadosBasicos(colaborador.getId(), StatusRetornoAC.CONFIRMADO);
		
		assertEquals(colaborador, colabRetorno);
		assertEquals("Função", colabRetorno.getFuncao().getNome());
	}
	
	@Test public void testFindColaboradoresByArea() 
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

	@Test public void testFindAllSelects() {
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

		assertEquals(2, colaboradorDao.findAllSelect(SituacaoColaborador.ATIVO, null, empresaIds).size());
		assertEquals(1, colaboradorDao.findAllSelect(SituacaoColaborador.DESLIGADO,null, empresaIds).size());
		assertEquals(3, colaboradorDao.findAllSelect(SituacaoColaborador.TODOS, null, empresaIds).size());
	}

	@Test public void testFindAllSelectsNotusuarioId() {
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);

		Empresa urbana = EmpresaFactory.getEmpresa();
		empresaDao.save(urbana);

		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setUsuario(usuario);
		colaborador1.setEmpresa(vega);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(urbana);
		colaboradorDao.save(colaborador2);

		Long[] empresaIds = new Long[] { vega.getId(), urbana.getId() };

		assertEquals(1, colaboradorDao.findAllSelect(SituacaoColaborador.ATIVO, usuario.getId(), empresaIds).size());
	}
	
	@Test public void testSetMatriculaColaborador() {
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

	@Test public void testSetMatriculaColaboradorPorId() {
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

	@Test public void testFindByCodigoACEmpresaCodigoAC() {
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

	@Test public void testFindColaboradorById() {
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

	@Test public void testUpdateInfoPessoais() {
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

	@Test public void testFindByUsuarioColaborador() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador.getId(), colaboradorDao.findByUsuario(usuario.getId()));
	}

	@Test public void testFindByUsuarioColaboradorNull() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(null, colaboradorDao.findByUsuario(15984441L));
	}

	@Test public void testFindByIdComHistorico() {
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarialA = FaixaSalarialFactory.getEntity();
		faixaSalarialA = faixaSalarialDao.save(faixaSalarialA);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarialA);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarialA);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 3000));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdComHistorico(colaborador.getId(), null);

		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	@Test public void testGetCountAtivos() {
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);

		Colaborador colaboradorAtivoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorAtivoDentroDaConsulta.setEmpresa(empresa);
		colaboradorAtivoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorAtivoDentroDaConsulta.setDesligado(false);
		colaboradorDao.save(colaboradorAtivoDentroDaConsulta);
		
		HistoricoColaborador historicoColaborador1 = new HistoricoColaborador();
		historicoColaborador1.setColaborador(colaboradorAtivoDentroDaConsulta);
		historicoColaborador1.setData(dataTresMesesAtras.getTime());
		historicoColaborador1.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaboradorDesligadoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorDesligadoDentroDaConsulta.setEmpresa(empresa);
		colaboradorDesligadoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorDesligadoDentroDaConsulta.setDesligado(true);
		colaboradorDesligadoDentroDaConsulta.setDataDesligamento(hoje);
		colaboradorDao.save(colaboradorDesligadoDentroDaConsulta);

		HistoricoColaborador historicoColaborador2 = new HistoricoColaborador();
		historicoColaborador2.setColaborador(colaboradorDesligadoDentroDaConsulta);
		historicoColaborador2.setData(dataTresMesesAtras.getTime());
		historicoColaborador2.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Colaborador colaboradorDesligadoForaDaConsulta = ColaboradorFactory.getEntity();
		colaboradorDesligadoForaDaConsulta.setEmpresa(empresa);
		colaboradorDesligadoForaDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorDesligadoForaDaConsulta.setDesligado(true);
		colaboradorDesligadoForaDaConsulta.setDataDesligamento(dataTresMesesAtras.getTime());
		colaboradorDao.save(colaboradorDesligadoForaDaConsulta);
		
		HistoricoColaborador historicoColaborador3 = new HistoricoColaborador();
		historicoColaborador3.setColaborador(colaboradorDesligadoForaDaConsulta);
		historicoColaborador3.setData(dataTresMesesAtras.getTime());
		historicoColaborador3.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Colaborador colaboradorAtivoDentroDaConsultaeOutroestabelecimento = ColaboradorFactory.getEntity();
		colaboradorAtivoDentroDaConsultaeOutroestabelecimento.setEmpresa(empresa);
		colaboradorAtivoDentroDaConsultaeOutroestabelecimento.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorAtivoDentroDaConsultaeOutroestabelecimento.setDesligado(false);
		colaboradorDao.save(colaboradorAtivoDentroDaConsultaeOutroestabelecimento);
		
		HistoricoColaborador historicoColaborador4 = new HistoricoColaborador();
		historicoColaborador4.setColaborador(colaboradorAtivoDentroDaConsultaeOutroestabelecimento);
		historicoColaborador4.setData(dataTresMesesAtras.getTime());
		historicoColaborador4.setEstabelecimento(estabelecimento2);
		historicoColaboradorDao.save(historicoColaborador4);

		assertEquals(new Integer(2), colaboradorDao.getCountAtivosQualquerStatus(dataDoisMesesAtras.getTime(), new Long[]{empresa.getId()}, null, new Long[]{estabelecimento1.getId()}));
		assertEquals(new Integer(1), colaboradorDao.getCountAtivosQualquerStatus(dataDoisMesesAtras.getTime(), new Long[]{empresa.getId()}, null, new Long[]{estabelecimento2.getId()}));

	}

	@Test public void testCountAdmitidosSemTurnover() {
		
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
				
		Colaborador colaboradorAtivoDentroDaConsulta = ColaboradorFactory.getEntity();
		colaboradorAtivoDentroDaConsulta.setEmpresa(empresa);
		colaboradorAtivoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
		colaboradorAtivoDentroDaConsulta.setDesligado(false);
		colaboradorDao.save(colaboradorAtivoDentroDaConsulta);
		
		HistoricoColaborador histColaboradorAtivoDentroDaConsulta = HistoricoColaboradorFactory.getEntity();
		histColaboradorAtivoDentroDaConsulta.setColaborador(colaboradorAtivoDentroDaConsulta);
		histColaboradorAtivoDentroDaConsulta.setStatus(StatusRetornoAC.CONFIRMADO);
		histColaboradorAtivoDentroDaConsulta.setData(dataTresMesesAtras.getTime());
		histColaboradorAtivoDentroDaConsulta.setEstabelecimento(estabelecimento);
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
		
		assertEquals(new Integer(2), colaboradorDao.countAdmitidosDemitidosTurnover(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa, null, null, null, true));
		assertEquals(new Integer(1), colaboradorDao.countAdmitidosDemitidosTurnover(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa, new Long[]{estabelecimento.getId()}, null, null, true));
	}

	@Test public void testCountAdmitidosComTurnover() {
		
		Calendar dataDoisMesesAtras = Calendar.getInstance();
		dataDoisMesesAtras.add(Calendar.MONTH, -2);
		Calendar dataTresMesesAtras = Calendar.getInstance();
		dataTresMesesAtras.add(Calendar.MONTH, -3);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setTurnoverPorSolicitacao(true);
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
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
		histColaboradorAtivoDentroDaConsulta.setEstabelecimento(estabelecimento);
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
		histColaboradorDentroDaConsulta2.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(histColaboradorDentroDaConsulta2);
		
		assertEquals(new Integer(1), colaboradorDao.countAdmitidosDemitidosTurnover(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa, new Long[]{estabelecimento.getId()}, null, null, true));
	}
	
	@Test public void testGetCountAtivosDataBase() {
		Date dataBase = DateUtil.criarDataMesAno(01, 02, 2011);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Cargo cargoFora = CargoFactory.getEntity();
		cargoDao.save(cargoFora);

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2001), null, null, area, faixaSalarial, estabelecimento1);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2012), null, null, area, faixaSalarial, estabelecimento2);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2012), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento2);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2012), empresa2, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null, area, faixaSalarial, estabelecimento1);

		assertEquals(2, colaboradorDao.getCountAtivos(dataBase, Arrays.asList(empresa.getId()), null, null, null));
		assertEquals(1, colaboradorDao.getCountAtivos(dataBase, Arrays.asList(empresa.getId()), new Long[]{estabelecimento1.getId()}, new Long[]{area.getId()}, null));
		assertEquals(1, colaboradorDao.getCountAtivos(dataBase, Arrays.asList(empresa.getId()), new Long[]{estabelecimento2.getId()}, null, new Long[]{cargo.getId()}));
		assertEquals(0, colaboradorDao.getCountAtivos(dataBase, Arrays.asList(empresa.getId()), null, null, new Long[]{cargoFora.getId()}));
		assertEquals(2, colaboradorDao.getCountAtivos(dataBase, new ArrayList<Long>(), new Long[]{estabelecimento1.getId(),estabelecimento2.getId()}, new Long[]{area.getId()}, null));
	}

	@Test public void testFindAniversariantes() {
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

	@Test public void testCountSemMotivos() {
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

		assertEquals(new Integer(1), colaboradorDao.countSemMotivos(null, null, null, DateUtil.criarDataMesAno(1, 1, 1980), DateUtil.criarDataMesAno(3, 1, 1980), null));
	}

	@Test public void testMigrarBairro() {
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

	@Test public void testFindEmailsDeColaboradoresByPerfis() {
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

	@Test public void testFindAdmitidosHaDiasPeriodoExperiencia() {
		Calendar trintaNoveDiasAtras = Calendar.getInstance();
		trintaNoveDiasAtras.add(Calendar.DAY_OF_YEAR, -39);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		
		Colaborador joseAvaliado = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), false, null, false);
		Colaborador maria = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), false, null, false);
		Colaborador colabDesligado = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), true, DateUtil.incrementaDias(new Date(), -1), false);
		
		saveHistoricoColaborador(joseAvaliado, estabelecimento, areaOrganizacional, null, DateUtil.criarDataMesAno(01, 01, 2005), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(maria, estabelecimento, areaOrganizacional, null, DateUtil.criarDataMesAno(01, 01, 2005), StatusRetornoAC.CONFIRMADO);
		saveHistoricoColaborador(colabDesligado, estabelecimento, areaOrganizacional, null, DateUtil.criarDataMesAno(01, 01, 2005), StatusRetornoAC.CONFIRMADO);
		
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperienciaDao.save(periodoExperiencia);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity('A', periodoExperiencia);
		avaliacaoDao.save(avaliacao);
		
		ColaboradorPeriodoExperienciaAvaliacao colabPeriodoExperienciaAvaliacaoJoseAvaliado = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joseAvaliado, periodoExperiencia, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_GESTOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabPeriodoExperienciaAvaliacaoJoseAvaliado);
		
		ColaboradorPeriodoExperienciaAvaliacao colabPeriodoExperienciaAvaliacaoJoseAvaliado2 = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joseAvaliado, periodoExperiencia, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabPeriodoExperienciaAvaliacaoJoseAvaliado2);
		
		ColaboradorPeriodoExperienciaAvaliacao colabPeriodoExperienciaAvaliacaoMariaJaRespondido = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(maria, periodoExperiencia, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_GESTOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabPeriodoExperienciaAvaliacaoMariaJaRespondido);
		
		ColaboradorPeriodoExperienciaAvaliacao colabDesligadoPeriodoExperienciaAvaliacao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colabDesligado, periodoExperiencia, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabDesligadoPeriodoExperienciaAvaliacao);
		
		ColaboradorQuestionario colaboradorQuestionarioJoseAvaliado = ColaboradorQuestionarioFactory.getEntity(joseAvaliado, null, avaliacao, null, false);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioJoseAvaliado);
		
		//Avaliação Desempenho
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		ColaboradorQuestionario colaboradorQuestionarioMariaAvalDesempenho = ColaboradorQuestionarioFactory.getEntity(maria, null, null, avaliacaoDesempenho, false);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMariaAvalDesempenho);
		
		//Avaliação Curso
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		ColaboradorQuestionario colaboradorQuestionarioMariaAvalTurma = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioMariaAvalTurma.setColaborador(maria);
		colaboradorQuestionarioMariaAvalTurma.setTurma(turma);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMariaAvalTurma);
		
		//Avaliação do Aluno
		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacao2.setTipoModeloAvaliacao('L');
		avaliacaoDao.save(avaliacao2);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setTipo(TipoAvaliacaoCurso.AVALIACAO);
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		ColaboradorQuestionario colaboradorQuestionarioMariaAvalAluno = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioMariaAvalAluno.setColaborador(maria);
		colaboradorQuestionarioMariaAvalAluno.setTurma(turma);
		colaboradorQuestionarioMariaAvalAluno.setQuestionario(questionario);
		colaboradorQuestionarioMariaAvalAluno.setAvaliacao(avaliacao2);
		colaboradorQuestionarioMariaAvalAluno.setAvaliacaoCurso(avaliacaoCurso);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMariaAvalAluno);
		
		//Entrevista de Desligamento (Mesmo cenário para pesquisa)
		ColaboradorQuestionario colaboradorQuestionarioEntrevistaDesligamento = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioEntrevistaDesligamento.setColaborador(colabDesligado);
		colaboradorQuestionarioEntrevistaDesligamento.setQuestionario(questionario);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioEntrevistaDesligamento);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection <Colaborador> colabsRetorno = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		Colaborador colabRetorno = (Colaborador) colabsRetorno.toArray()[0];
		Collection<Colaborador> colabs = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		
		assertEquals(avaliacao.getId(), colabRetorno.getAvaliacaoId());
		assertEquals(1, colabs.size());
		assertEquals(estabelecimento.getNome(), colabRetorno.getEstabelecimento().getNome());
	}
	
	@Test public void testFindAdmitidosHaDiasPeriodoExperienciaEmpresaComConfiguracaoNotificarSomentePeriodosConfigurados() {
		Calendar trintaNoveDiasAtras = Calendar.getInstance();
		trintaNoveDiasAtras.add(Calendar.DAY_OF_YEAR, -39);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNotificarSomentePeriodosConfigurados(true);
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		
		Colaborador joseAvaliado = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), false, null, false);
		saveHistoricoColaborador(joseAvaliado, estabelecimento, areaOrganizacional, null, trintaNoveDiasAtras.getTime(), StatusRetornoAC.CONFIRMADO);
		
		Colaborador maria = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), false, null, false);
		saveHistoricoColaborador(maria, estabelecimento, areaOrganizacional, null, trintaNoveDiasAtras.getTime(), StatusRetornoAC.CONFIRMADO);
		
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(empresa, 40, true);
		periodoExperienciaDao.save(periodoExperiencia);
		Avaliacao avaliacao = AvaliacaoFactory.getEntity('A', periodoExperiencia);
		avaliacaoDao.save(avaliacao);
		
		ColaboradorPeriodoExperienciaAvaliacao colabPeriodoExperienciaAvaliacaoJoseAvaliado = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joseAvaliado, periodoExperiencia, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_GESTOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabPeriodoExperienciaAvaliacaoJoseAvaliado);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection <Colaborador> colabsRetorno = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		Colaborador colabRetorno = (Colaborador) colabsRetorno.toArray()[0];
		Collection<Colaborador> colabs = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		
		assertEquals(avaliacao.getId(), colabRetorno.getAvaliacaoId());
		assertEquals(1, colabs.size());
	}
	
	@Test public void testFindAdmitidosHaDiasPeriodoExperienciaEmpresaSemConfiguracaoNotificarSomentePeriodosConfigurados() {
		Calendar trintaNoveDiasAtras = Calendar.getInstance();
		trintaNoveDiasAtras.add(Calendar.DAY_OF_YEAR, -39);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNotificarSomentePeriodosConfigurados(false);
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		
		Colaborador joseAvaliado = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), false, null, false);
		saveHistoricoColaborador(joseAvaliado, estabelecimento, areaOrganizacional, null, trintaNoveDiasAtras.getTime(), StatusRetornoAC.CONFIRMADO);
		
		Colaborador maria = saveColaborador("Samuel", empresa, trintaNoveDiasAtras.getTime(), false, null, false);
		saveHistoricoColaborador(maria, estabelecimento, areaOrganizacional, null, trintaNoveDiasAtras.getTime(), StatusRetornoAC.CONFIRMADO);
		
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(empresa, 40, true);
		periodoExperienciaDao.save(periodoExperiencia);
		Avaliacao avaliacao = AvaliacaoFactory.getEntity('A', periodoExperiencia);
		avaliacaoDao.save(avaliacao);
		
		ColaboradorPeriodoExperienciaAvaliacao colabPeriodoExperienciaAvaliacaoJoseAvaliado = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joseAvaliado, periodoExperiencia, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_GESTOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colabPeriodoExperienciaAvaliacaoJoseAvaliado);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection <Colaborador> colabsRetorno = colaboradorDao.findAdmitidosHaDias(40, empresa, periodoExperiencia.getId());
		assertEquals(2, colabsRetorno.size());
	}

	@Test public void testFindAdmitidosNoPeriodo() {
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

	@Test public void testCountAtivosPeriodo() 
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
	
	@Test 
	public void testCountAtivosPeriodoArea() 
	{
		Character agruparPor='A';
		Date dataContratacaoJoao = DateUtil.criarDataMesAno(01, 05, 2010);
		Date data_21_07_2011 = DateUtil.criarDataMesAno(21, 07, 2011);
		
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("Estabelecimento", vega);
		estabelecimentoDao.save(estabelecimento);
		
		Cargo cargo = CargoFactory.getEntity("Cargo");
		cargoDao.save(cargo);

		FaixaSalarial faixa = FaixaSalarialFactory.getEntity("Faixa 1", cargo);
		faixaSalarialDao.save(faixa);
		
		AreaOrganizacional areaA = AreaOrganizacionalFactory.getEntity();
    	areaA.setNome("areaA");
    	areaOrganizacionalDao.save(areaA);
    	
    	AreaOrganizacional areaB = AreaOrganizacionalFactory.getEntity();
    	areaB.setNome("areaB");
    	areaOrganizacionalDao.save(areaB);
    	
		Colaborador joao = montaColaboradorDoTestCountAtivo(vega, dataContratacaoJoao);
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(joao);
		hc1.setAreaOrganizacional(areaA);
		hc1.setData(dataContratacaoJoao);
		hc1.setFaixaSalarial(faixa);
		hc1.setEstabelecimento(estabelecimento);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		hc1 = historicoColaboradorDao.save(hc1);

		HashMap<Long, Double> totalAtivosArea = colaboradorDao.countAtivosPeriodoAreaOuCargo(data_21_07_2011, Arrays.asList(vega.getId()), Arrays.asList(estabelecimento.getId()),  Arrays.asList(areaA.getId(),areaB.getId()), Arrays.asList(cargo.getId()), null, null, false, null, false, agruparPor);
		assertEquals(1, totalAtivosArea.size());
		assertEquals(areaA.getId(), ((Long) totalAtivosArea.keySet().toArray()[0]));
		
		totalAtivosArea = colaboradorDao.countAtivosPeriodoAreaOuCargo(data_21_07_2011, Arrays.asList(vega.getId()), Arrays.asList(estabelecimento.getId()),  Arrays.asList(areaA.getId(),areaB.getId()), Arrays.asList(cargo.getId()), null, null, false, null, false, 'C');
		assertEquals(1, totalAtivosArea.size());
		assertEquals(cargo.getId(), ((Long) totalAtivosArea.keySet().toArray()[0]));
}
	@Test public void testCountAtivosPeriodoCargo() 
	{
		Character agruparPorCargo='C';
		Date dataContratacaoJoao = DateUtil.criarDataMesAno(01, 05, 2010);
		Date dataContratacaoPedro = DateUtil.criarDataMesAno(20, 05, 2011);
		
		Date data_21_07_2011 = DateUtil.criarDataMesAno(21, 07, 2011);
		
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);
		
		Cargo cargoA  = CargoFactory.getEntity(1l);
		cargoA.setNome("cargoA");
		cargoDao.save(cargoA);
		
		Cargo cargoB  = CargoFactory.getEntity(2l);
		cargoB.setNome("cargoB");
		cargoDao.save(cargoB);

		FaixaSalarial faixaCargoA = FaixaSalarialFactory.getEntity();
		faixaCargoA.setCargo(cargoA);
		faixaSalarialDao.save(faixaCargoA);
		
		FaixaSalarial faixaCargoB = FaixaSalarialFactory.getEntity();
		faixaCargoB.setCargo(cargoB);
		faixaSalarialDao.save(faixaCargoB);
		
		Colaborador joao = montaColaboradorDoTestCountAtivo(vega, dataContratacaoJoao);
		Colaborador pedro = montaColaboradorDoTestCountAtivo(vega, dataContratacaoPedro);
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(joao);
		hc1.setCargoId(cargoA.getId());
		hc1.setData(dataContratacaoJoao);
		hc1.setFaixaSalarial(faixaCargoA);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		hc1 = historicoColaboradorDao.save(hc1);
		
		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(pedro);
		hc2.setCargoId(cargoB.getId());
		hc2.setData(dataContratacaoPedro);
		hc2.setFaixaSalarial(faixaCargoB);
		hc2.setStatus(StatusRetornoAC.CONFIRMADO);
		hc2 = historicoColaboradorDao.save(hc2);
		
		HashMap<Long, Double> totalAtivosCargo = colaboradorDao.countAtivosPeriodoAreaOuCargo(data_21_07_2011, Arrays.asList(vega.getId()), null,null , Arrays.asList(cargoA.getId(),cargoB.getId()), null, null, false, null, false, agruparPorCargo);
		assertEquals(2, totalAtivosCargo.size());
	}
	
	@Test public void testCountAtivosPeriodoComAbsenteismo() 
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

	@Test public void testCountDesligados() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);

		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, longs, longs, longs, null, false, null);
		assertEquals(0, colaboradores.size());
	}
	
	@Test public void testCountDesligadosPorArea() {
		Character agruparPorArea= 'A';
		Date dataContratacaoJoao = DateUtil.criarDataMesAno(01, 05, 2010);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador joao = ColaboradorFactory.getEntity(true, dataContratacaoJoao, DateUtil.criarDataMesAno(29, 12, 2010));
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial= FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		AreaOrganizacional areaOrganizacional= AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(joao);
		hc1.setData(dataContratacaoJoao);
		hc1.setFaixaSalarial(faixaSalarial);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		hc1.setAreaOrganizacional(areaOrganizacional);
		hc1 = historicoColaboradorDao.save(hc1);

		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, null, Arrays.asList(areaOrganizacional.getId()), null, null, false, agruparPorArea);
		assertEquals(1, colaboradores.size());
	}
	@Test public void testCountDesligadosPorCargo() {
		Character agruparPorCargo= 'C';
		Date dataContratacaoJoao = DateUtil.criarDataMesAno(01, 05, 2010);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador joao = ColaboradorFactory.getEntity(true, dataContratacaoJoao, DateUtil.criarDataMesAno(29, 12, 2010));
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial= FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(joao);
		hc1.setData(dataContratacaoJoao);
		hc1.setFaixaSalarial(faixaSalarial);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		hc1 = historicoColaboradorDao.save(hc1);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, null, null, Arrays.asList(cargo.getId()), null, false, agruparPorCargo);
		assertEquals(1, colaboradores.size());
	}

	@Test public void testCountAdmitidosPeriodoTurnover() {
		Character semAgrupamento= 'S';
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, longs, longs, longs, Arrays.asList(Vinculo.EMPREGO), true, semAgrupamento);
		assertEquals(0, colaboradores.size());
	}
	
	@Test public void testCountAdmitidosPeriodoTurnoverArea() {
		Character agruparPorArea= 'A';
		Date dataContratacaoJoao = DateUtil.criarDataMesAno(01, 05, 2010);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setDataAdmissao(dataContratacaoJoao);
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial= FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		AreaOrganizacional areaOrganizacional= AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(joao);
		hc1.setData(dataContratacaoJoao);
		hc1.setFaixaSalarial(faixaSalarial);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		hc1.setAreaOrganizacional(areaOrganizacional);
		hc1 = historicoColaboradorDao.save(hc1);

		colaboradorDao.getHibernateTemplateByGenericDao().flush();
	
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, null, Arrays.asList(areaOrganizacional.getId()), null, Arrays.asList(Vinculo.EMPREGO), true, agruparPorArea);
		assertEquals(1, colaboradores.size());
	}
	@Test public void testCountAdmitidosPeriodoTurnoverCargo() {
		Character agruparPorCargo= 'C';
		Date dataContratacaoJoao = DateUtil.criarDataMesAno(01, 05, 2010);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setDataAdmissao(dataContratacaoJoao);
		joao.setEmpresa(empresa);
		colaboradorDao.save(joao);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial= FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(joao);
		hc1.setData(dataContratacaoJoao);
		hc1.setFaixaSalarial(faixaSalarial);
		hc1.setStatus(StatusRetornoAC.CONFIRMADO);
		hc1 = historicoColaboradorDao.save(hc1);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, null, null, Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO), true, agruparPorCargo);
		assertEquals(1, colaboradores.size());
	}

	@Test public void testCountAdmitidosPeriodoTurnoverSolicitacao() {
		Character semAgrupamento= 'S';
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setTurnoverPorSolicitacao(true);
		empresaDao.save(empresa);
		
		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa, longs, longs, longs, Arrays.asList(Vinculo.EMPREGO), true, semAgrupamento);
		assertEquals(0, colaboradores.size());
	}

	@Test public void testFindComAvaliacoesExperiencias() {
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

	@Test public void testFindAdmitidos() {
		Date dataAdmissao1 = DateUtil.montaDataByString("20/01/2010");
		Date dataAdmissao2 = DateUtil.montaDataByString("13/01/2011");

		Empresa empresa = saveEmpresa();
		Estabelecimento estabelecimento1 = saveEstabelecimento("Estabelecimento A", empresa);
		Estabelecimento estabelecimento2 = saveEstabelecimento("Estabelecimento B", empresa);

		AreaOrganizacional areaOrganizacional1 = saveAreaOrganizacional();		
		AreaOrganizacional areaOrganizacional2 = saveAreaOrganizacional();
		
		Colaborador colaborador1 = montaColaboradorDoTestCountAtivo(empresa, dataAdmissao1);
		Colaborador colaborador2 = montaColaboradorDoTestCountAtivo(empresa, dataAdmissao2);
		
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity("Aumento de quadro", true);
		motivoSolicitacaoDao.save(motivo);
		
		Solicitacao solicitacao = saveSolicitacao(motivo);
		Candidato candidato1 = saveCandidato();
		CandidatoSolicitacao candidatoSolicitacao1_1 = saveCandidatoSolicitacao(candidato1, solicitacao);
		CandidatoSolicitacao candidatoSolicitacao1_2 = saveCandidatoSolicitacao(candidato1, solicitacao);
		
		HistoricoColaborador historicoColaborador1_1 = saveHistoricoColaborador(colaborador1, estabelecimento1, areaOrganizacional1, dataAdmissao1, MotivoHistoricoColaborador.CONTRATADO, StatusRetornoAC.CONFIRMADO, candidatoSolicitacao1_1);
		saveHistoricoColaborador(colaborador1, estabelecimento2, areaOrganizacional2, DateUtil.criarDataMesAno(1, 2, 2011), MotivoHistoricoColaborador.PROMOCAO, StatusRetornoAC.CONFIRMADO, candidatoSolicitacao1_2);

		HistoricoColaborador historicoColaborador2_1 = saveHistoricoColaborador(colaborador2, estabelecimento2, areaOrganizacional1, dataAdmissao2, MotivoHistoricoColaborador.CONTRATADO, StatusRetornoAC.CONFIRMADO, null);
		saveHistoricoColaborador(colaborador2, estabelecimento1, areaOrganizacional2, DateUtil.criarDataMesAno(13, 2, 2011),  MotivoHistoricoColaborador.PROMOCAO, StatusRetornoAC.CONFIRMADO, null);
		
		Date dataIni = DateUtil.montaDataByString("19/05/2009");
		Date dataFim = DateUtil.montaDataByString("20/02/2011");
		
		Long[] areasIds = null;
		Long[] estabelecimentosIds = new Long[] { estabelecimento1.getId() };

		Collection<Colaborador> colaboradorRetornadoArray = colaboradorDao.findAdmitidos(Vinculo.EMPREGO, dataIni, dataFim, areasIds, estabelecimentosIds, false);
		assertEquals(1, colaboradorRetornadoArray.size());
		assertEquals(historicoColaborador1_1.getAreaOrganizacional().getId(), ((Colaborador)colaboradorRetornadoArray.toArray()[0]).getAreaOrganizacional().getId());
		
		estabelecimentosIds = new Long[] { estabelecimento1.getId(), estabelecimento2.getId() };
		
		colaboradorRetornadoArray = colaboradorDao.findAdmitidos(Vinculo.EMPREGO, dataIni, dataFim, areasIds, estabelecimentosIds, false);
		assertEquals(2, colaboradorRetornadoArray.size());
		
		Colaborador colaboradorRetornado1 = (Colaborador)colaboradorRetornadoArray.toArray()[0];
		Colaborador colaboradorRetornado2 = (Colaborador)colaboradorRetornadoArray.toArray()[1];
		
		assertEquals(historicoColaborador1_1.getAreaOrganizacional().getId(), colaboradorRetornado1.getAreaOrganizacional().getId());
		assertEquals(historicoColaborador2_1.getAreaOrganizacional().getId(), colaboradorRetornado2.getAreaOrganizacional().getId());
		
		assertEquals(solicitacao.getMotivoSolicitacao().getDescricao(), colaboradorRetornado1.getHistoricoColaborador().getCandidatoSolicitacao().getSolicitacao().getMotivoSolicitacao().getDescricao());
		assertNull(colaboradorRetornado2.getHistoricoColaborador().getCandidatoSolicitacao().getSolicitacao().getMotivoSolicitacao().getDescricao());
		assertEquals(solicitacao.getMotivoSolicitacao().isTurnover(), colaboradorRetornado1.getHistoricoColaborador().getCandidatoSolicitacao().getSolicitacao().getMotivoSolicitacao().isTurnover());
		assertFalse(colaboradorRetornado2.getHistoricoColaborador().getCandidatoSolicitacao().getSolicitacao().getMotivoSolicitacao().isTurnover());
		
		colaboradorRetornadoArray = colaboradorDao.findAdmitidos(Vinculo.ESTAGIO, dataIni, dataFim, areasIds, estabelecimentosIds, false);
		assertEquals(0, colaboradorRetornadoArray.size());
	}

	@Test 
	public void testFindHistoricoByColaboradors() {
		Colaborador colaborador1 = getColaborador();
		colaborador1.setNome("A TESTE");
		colaborador1.setNomeComercial("A TESTE");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("X Maria");
		colaborador2.setNomeComercial("X Maria");
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

	@Test public void testFindByCpf() {
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

	@Test public void testUpdateInfoPessoaisByCpf() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setPessoalCpf("26745534304");
		colaborador.setEmpresa(empresa);

		colaboradorDao.updateInfoPessoaisByCpf(colaborador, empresa.getId());
	}
	
	@Test public void testCountFormacaoEscolar() 
	{
		Date hoje = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setDataAdmissao(hoje);
		colaborador.setEmpresa(empresa);
		colaborador.setVinculo(Vinculo.EMPREGO);
		colaboradorDao.save(colaborador);
		
		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("Xica ");
		colaborador2.setDesligado(false);
		colaborador2.setDataAdmissao(hoje);
		colaborador2.setEmpresa(empresa);
		colaborador2.setVinculo(Vinculo.ESTAGIO);
		colaboradorDao.save(colaborador2);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setNome("Estabelecimento A");
		estabelecimento1.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento1);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
				
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setAreaOrganizacional(area);
		historicoColaboradorAtual1.setFaixaSalarial(faixaSalarial);
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(colaborador);
		historicoColaboradorAtual1.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaboradorAtual1);
		
		HistoricoColaborador historicoColaboradorAtual2 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual2.setAreaOrganizacional(area);
		historicoColaboradorAtual2.setFaixaSalarial(faixaSalarial);
		historicoColaboradorAtual2.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual2.setColaborador(colaborador2);
		historicoColaboradorAtual2.setEstabelecimento(estabelecimento1);
		historicoColaboradorDao.save(historicoColaboradorAtual2);
		
		Collection<Long> empresaIds = Arrays.asList(empresa.getId());
		
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, empresaIds, null, null, null, new String[]{Vinculo.EMPREGO}).size());
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, empresaIds, new Long[]{estabelecimento1.getId()}, new Long[]{area.getId()}, null, new String[]{Vinculo.EMPREGO}).size());
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, empresaIds, new Long[]{estabelecimento1.getId()}, null, new Long[]{cargo.getId()}, new String[]{Vinculo.EMPREGO}).size());
	
		assertEquals(1, colaboradorDao.countFormacaoEscolar(hoje, null, new Long[]{estabelecimento1.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, new String[]{Vinculo.EMPREGO}).size());
	}
	
	@Test public void testCountDeficiencia() 
	{
		Date hoje = new Date();
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		criaColaboradorHistoricoParaCountDeficiencia(hoje, empresa, estabelecimento, faixa, area);
		
		Collection<DataGrafico> graficos = colaboradorDao.countDeficiencia(hoje, Arrays.asList(empresa.getId()), null, null, null, new String[]{Vinculo.EMPREGO, Vinculo.ESTAGIO});
		
		assertEquals(2, graficos.size());
		
		assertEquals(new Deficiencia().get(Deficiencia.FISICA), ((DataGrafico) graficos.toArray()[0]).getLabel());
		assertEquals(new Float(2.0), new Float(((DataGrafico) graficos.toArray()[0]).getData()));
		
		assertEquals(new Deficiencia().get(Deficiencia.AUDITIVA), ((DataGrafico) graficos.toArray()[1]).getLabel());
		assertEquals(new Float(1.0), new Float(((DataGrafico) graficos.toArray()[1]).getData()));
		
		assertEquals(1, colaboradorDao.countDeficiencia(hoje, null, new Long[]{estabelecimento.getId()}, new Long[]{area.getId()}, null, new String[]{Vinculo.EMPREGO}).size());
		assertEquals(1, colaboradorDao.countDeficiencia(hoje, Arrays.asList(empresa.getId()), new Long[]{estabelecimento.getId()}, new Long[]{area.getId()}, null, new String[]{Vinculo.EMPREGO}).size());
	}

	private void criaColaboradorHistoricoParaCountDeficiencia(Date hoje, Empresa empresa, Estabelecimento estabelecimento1, FaixaSalarial faixa, AreaOrganizacional area)
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(false, hoje, empresa, Vinculo.EMPREGO, Deficiencia.AUDITIVA);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(false, hoje, empresa, Vinculo.ESTAGIO, Deficiencia.AUDITIVA);
		colaboradorDao.save(colaborador2);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity(false, hoje, empresa, Vinculo.ESTAGIO, Deficiencia.FISICA);
		colaboradorDao.save(colaborador3);
		
		Colaborador colaborador4 = ColaboradorFactory.getEntity(false, hoje, empresa, Vinculo.ESTAGIO, Deficiencia.FISICA);
		colaboradorDao.save(colaborador4);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador1, hoje, faixa, estabelecimento1, area, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity(colaborador3, hoje, faixa, estabelecimento1, area, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		HistoricoColaborador historicoColaborador4 = HistoricoColaboradorFactory.getEntity(colaborador4, hoje, faixa, estabelecimento1, area, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador4);
	}
	
	@Test public void testFindEmailsByPapel() 
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
		colaboradorDao.findByCandidato(colabLeo.getId(), 1L);
		
		String[] emails = colaboradorDao.findEmailsByPapel(usuarioEmpresaIds);
		
		assertEquals(1, emails.length);
	}

	@Test public void testFindParticipantesByAvaliacaoDesempenho() {
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

	@Test public void testFindColaboradorDeAvaliacaoDesempenhoNaoRespondida() 
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
	
	@Test public void testFindByQuestionarioNaoRespondido() {
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

	@Test public void testFindParticipantesDistinctByAvaliacaoDesempenho() {
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
	@Test public void testFindParticipantesDistinctComHistoricoByAvaliacaoDesempenhoTodasEmpresas() {
		Empresa empresa = EmpresaFactory.getEmpresa(1l);
		empresaDao.save(empresa);
		
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		avaliado.setEmpresa(empresa);
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
		
		colaboradorDao.findParticipantesDistinctComHistoricoByAvaliacaoDesempenhoTodasEmpresas(avaliacaoDesempenho.getId(), true, new Long[]{empresa.getId()}, null, null);
	}
	
	@Test public void testQtdDemitidosEm90Dias() 
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
	
	@Test public void testQtdAdmitidosPeriodo() 
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
	
	@Test public void testGetColaboradoresByTurmas() 
	{
		Date hoje = new Date();
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimentoA = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimentoA);
		
		Estabelecimento estabelecimentoB = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimentoB);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("Xica ");
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = new HistoricoColaborador();
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setData(hoje);
		historicoColaborador1.setEstabelecimento(estabelecimentoA);
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Xica");
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = new HistoricoColaborador();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setData(hoje);
		historicoColaborador2.setEstabelecimento(estabelecimentoB);
		historicoColaboradorDao.save(historicoColaborador2);

		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setCurso(curso);
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
		
		DiaTurma diaTurma = DiaTurmaFactory.getEntity();
		diaTurma.setTurma(turma);
		diaTurmaDao.save(diaTurma);

		assertEquals(new Integer(1), colaboradorDao.qtdTotalDiasDaTurmaVezesColaboradoresInscritos(null, null, null, new Long[]{curso.getId()}, null, new Long[]{estabelecimentoA.getId()}));
	}

	@Test public void testFindParentesByNome() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador julia = ColaboradorFactory.getEntity(null, "julia rodrigues_", "marcia rodrigues_", "julio medeiro_", null, empresa);
		colaboradorDao.save(julia);
		
		Colaborador roberta = ColaboradorFactory.getEntity(null, "roberta rodrigues_", "maria rodrigues_", "julio medeiro_", null, empresa);
		colaboradorDao.save(roberta);
		
		Colaborador joao = ColaboradorFactory.getEntity(null, "joao rodrigues_", "maria rodrigues_", "pedro rodrigues_", null, empresa);
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity(null, "maria rodrigues_", null, null, "geraldo rodrigues_", empresa);
		colaboradorDao.save(maria);

		Colaborador mariana = ColaboradorFactory.getEntity(null, "mariana rodrigues_", null, null, null, empresa);
		colaboradorDao.save(mariana);

		Colaborador pedro = ColaboradorFactory.getEntity(null, "pedro rodrigues_", null, null, "maria rodrigues_", empresa);
		colaboradorDao.save(pedro);
		
		assertEquals(0, colaboradorDao.findParentesByNome(joao.getId(),joao.getNome(), empresa.getId()).size());
		assertEquals(0, colaboradorDao.findParentesByNome(maria.getId(),maria.getPessoal().getConjuge(), empresa.getId()).size());
		assertEquals(1, colaboradorDao.findParentesByNome(pedro.getId(),joao.getPessoal().getPai(), empresa.getId()).size());
		assertEquals(3, colaboradorDao.findParentesByNome(maria.getId(),maria.getNome(), empresa.getId()).size());
		assertEquals(3, colaboradorDao.findParentesByNome(maria.getId(),maria.getNome(), null).size());
		assertEquals(1, colaboradorDao.findParentesByNome(julia.getId(),julia.getPessoal().getPai(), empresa.getId()).size());
		assertEquals(3, colaboradorDao.findParentesByNome(roberta.getId(),roberta.getPessoal().getMae(), null).size());
		assertTrue(colaboradorDao.findParentesByNome(maria.getId(),null, null).size() >= 4);
	}

	@Test public void testFindColabPeriodoExperiencia() 
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
		
		Collection<Colaborador> colaboradors = colaboradorDao.findColabPeriodoExperiencia(periodoIni, periodoFim, avaliacaoIds, areaIds, estabelecimentoIds, null, true, false, empresa.getId());

		assertEquals(1, colaboradors.size());
	}

	private Map<String, Object> setupFindCotmHistoricoFuturoSQL()
	 {
		 Map<String, Object> retorno =  new HashMap<String, Object>();

		 Cargo cargo = CargoFactory.getEntity();
		 cargoDao.save(cargo);
		 retorno.put("cargo", cargo);
		
		 FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(null, "I", cargo);
		 faixaSalarialDao.save(faixaSalarial);
		 retorno.put("faixaSalarial", faixaSalarial);

		 FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(null, "II", cargo);
		 faixaSalarialDao.save(faixaSalarial2);
		 retorno.put("faixaSalarial2", faixaSalarial2);
		
		 Empresa empresa = EmpresaFactory.getEmpresa(4L);
		 empresaDao.save(empresa);
		 retorno.put("empresa", empresa);
		
		 Colaborador joao = ColaboradorFactory.getEntity(null, "Joao", null, null, "00001", "99999888888", empresa);
		 colaboradorDao.save(joao);
		 retorno.put("joao", joao);
		 
		 Colaborador maria = ColaboradorFactory.getEntity(null, "Maria", null, null, "00002", "12321362391", empresa);
		 colaboradorDao.save(maria);
		 retorno.put("maria", maria);
		
		 Colaborador pedro = ColaboradorFactory.getEntity(null, "Pedro", null, null, null, "12321362392", empresa);
		 colaboradorDao.save(pedro);
		 retorno.put("pedro", pedro);

		 Colaborador ronaldo = ColaboradorFactory.getEntity(null, "Ronaldo", null, null, "00003", "12321362393", empresa);
		 colaboradorDao.save(ronaldo);
		 retorno.put("ronaldo", ronaldo);
		 
		 Colaborador tadeu = ColaboradorFactory.getEntity(null, "Tadeu", null, null, null, "12321362394", empresa);
		 colaboradorDao.save(tadeu);
		 retorno.put("tadeu", tadeu);
		
		 HistoricoColaborador historicoColaboradorJoao = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2008), joao, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorJoao);
		 retorno.put("historicoColaboradorJoao", historicoColaboradorJoao);
		
		 HistoricoColaborador historicoColaboradorMaria = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2020), maria, faixaSalarial2);
		 historicoColaboradorDao.save(historicoColaboradorMaria);
		 retorno.put("historicoColaboradorMaria", historicoColaboradorMaria);
		
		 HistoricoColaborador historicoColaboradorPedroAntigo = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2000), pedro, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorPedroAntigo);
		 retorno.put("historicoColaboradorPedroAntigo", historicoColaboradorPedroAntigo);
		
		 HistoricoColaborador historicoColaboradorPedroAtual = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2010), pedro, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorPedroAtual);
		 retorno.put("historicoColaboradorPedroAtual", historicoColaboradorPedroAtual);
		 
		 HistoricoColaborador historicoColaboradorRonaldoAtual = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2010), ronaldo, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorRonaldoAtual);
		 retorno.put("historicoColaboradorRonaldoAtual", historicoColaboradorRonaldoAtual);

		 HistoricoColaborador historicoColaboradorRonaldoFuturo1 = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2022), ronaldo, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorRonaldoFuturo1);
		 retorno.put("historicoColaboradorRonaldoFuturo1", historicoColaboradorRonaldoFuturo1);

		 HistoricoColaborador historicoColaboradorRonaldoFuturo2 = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2023), ronaldo, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorRonaldoFuturo2);
		 retorno.put("historicoColaboradorRonaldoFuturo2", historicoColaboradorRonaldoFuturo2);
		 
		 HistoricoColaborador historicoColaboradorTadeuFuturo1 = inicializaHistorico(DateUtil.criarDataMesAno(1, 1, 2022), tadeu, faixaSalarial);
		 historicoColaboradorDao.save(historicoColaboradorTadeuFuturo1);
		 retorno.put("historicoColaboradorTadeuFuturo1", historicoColaboradorTadeuFuturo1);
		 
		 return retorno;
	 }
	
	@Test public void testFindCotmHistoricoFuturoSQLCpfEmpresa()
	{
		Map<String, Object> objetos = setupFindCotmHistoricoFuturoSQL();
		
		historicoColaboradorDao.getHibernateTemplateByGenericDao().flush();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cpfBusca", "1232136239");
		parametros.put("empresaId", ((Empresa)objetos.get("empresa")).getId());

		assertEquals(4, colaboradorDao.findComHistoricoFuturoSQL(parametros, 0, 0, null).size());
	 }
	
	@Test public void testFindCotmHistoricoFuturoSQLCpfEmpresaFaixaSalarial()
	{
		Map<String, Object> objetos = setupFindCotmHistoricoFuturoSQL();
		
		historicoColaboradorDao.getHibernateTemplateByGenericDao().flush();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cpfBusca", "1232136239");
		parametros.put("empresaId", ((Empresa)objetos.get("empresa")).getId());
		parametros.put("faixaSalarialId", ((FaixaSalarial)objetos.get("faixaSalarial")).getId());
		
		assertEquals(3, colaboradorDao.findComHistoricoFuturoSQL(parametros, 0, 0, 1L).size());
	}
	
	@Test public void testFindCotmHistoricoFuturoSQLCodigoAC()
	{
		Map<String, Object> objetos = setupFindCotmHistoricoFuturoSQL();
		
		historicoColaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigoACBusca", ((Colaborador)objetos.get("maria")).getCodigoAC());
		parametros.put("empresaId", ((Empresa)objetos.get("empresa")).getId());
		
		Collection<Object> colaboradores = colaboradorDao.findComHistoricoFuturoSQL(parametros, 0, 0, null);
		
		Object[] colab = (Object[]) colaboradores.iterator().next();
		
		assertEquals(1, colaboradores.size());
		assertEquals(((Colaborador)objetos.get("maria")).getId(), (Long)((BigInteger)colab[0]).longValue());
	}
	
	@Test public void testFindByEstabelecimentoDataAdmissao()
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
		
		Colaborador joao = ColaboradorFactory.getEntity(1L, "joao", "joao", "001", null, null, null);
		joao.setDataAdmissao(hoje);
		joao.setEmpresa(fortes);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity(1L);
		historicoJoao.setColaborador(joao);
		historicoJoao.setEstabelecimento(matriz);
		historicoJoao.setData(hoje);
		historicoColaboradorDao.save(historicoJoao);
		
		Colaborador pedro = ColaboradorFactory.getEntity(2L, "pedro", "pedro", "002", null, null, null);
		pedro.setDataAdmissao(hoje);
		pedro.setEmpresa(ente);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoPedro = HistoricoColaboradorFactory.getEntity(2L);
		historicoPedro.setColaborador(pedro);
		historicoPedro.setEstabelecimento(matriz);
		historicoPedro.setData(hoje);
		historicoColaboradorDao.save(historicoPedro);

		Colaborador rui = ColaboradorFactory.getEntity(3L, "rui", "rui", "003", null, null, null);
		rui.setDataAdmissao(hoje);
		rui.setEmpresa(fortes);
		colaboradorDao.save(rui);
		
		HistoricoColaborador historicoRui = HistoricoColaboradorFactory.getEntity(3L);
		historicoRui.setColaborador(rui);
		historicoRui.setEstabelecimento(filial);
		historicoRui.setData(hoje);
		historicoColaboradorDao.save(historicoRui);
		
		Colaborador manoel = ColaboradorFactory.getEntity(4L, "manoel", "manoel", "004", null, null, null);
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
	
	@Test public void testFindColaboradoresByIds()
	{
		Estabelecimento matriz = EstabelecimentoFactory.getEntity(1L);
		estabelecimentoDao.save(matriz);
		Estabelecimento filial = EstabelecimentoFactory.getEntity(2L);
		estabelecimentoDao.save(filial);
		
		Date hoje = DateUtil.criarDataMesAno(29, 8, 2011);
		
		Colaborador joao = ColaboradorFactory.getEntity(1L, "joao", "joao", "001", null, null, null);
		joao.setDataAdmissao(hoje);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity(1L);
		historicoJoao.setColaborador(joao);
		historicoJoao.setEstabelecimento(matriz);
		historicoJoao.setData(hoje);
		historicoJoao.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoJoao);
		
		Colaborador pedro = ColaboradorFactory.getEntity(2L, "pedro", "pedro", "002", null, null, null);
		pedro.setDataAdmissao(hoje);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoPedro = HistoricoColaboradorFactory.getEntity(2L);
		historicoPedro.setColaborador(pedro);
		historicoPedro.setEstabelecimento(matriz);
		historicoPedro.setData(hoje);
		historicoPedro.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoPedro);
		
		Colaborador rui = ColaboradorFactory.getEntity(3L, "rui", "rui", "003", null, null, null);
		rui.setDataAdmissao(hoje);
		colaboradorDao.save(rui);
		
		HistoricoColaborador historicoRui = HistoricoColaboradorFactory.getEntity(3L);
		historicoRui.setColaborador(rui);
		historicoRui.setEstabelecimento(filial);
		historicoRui.setData(hoje);
		historicoRui.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoRui);
		
		Date dataPassado = DateUtil.criarDataMesAno(1, 1, 2011);
		
		Colaborador manoel = ColaboradorFactory.getEntity(4L, "manoel", "manoel", "004", null, null, null);
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

	@Test 
	public void testFindQtdVagasPreenchidas() {
		Date hoje = DateUtil.criarDataMesAno(29, 8, 2011);
		Empresa empresa = saveEmpresa();
		
		Estabelecimento estabelecimento1 = saveEstabelecimento();		
		Estabelecimento estabelecimento2 = saveEstabelecimento();
		
		AreaOrganizacional areaOrganizacional1 = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacional2 = saveAreaOrganizacional();
		
		Solicitacao solicitacao1 = saveSolicitacao(empresa, estabelecimento1, areaOrganizacional1, hoje, hoje);
		Solicitacao solicitacao2 = saveSolicitacao(empresa, estabelecimento2, areaOrganizacional2, hoje, hoje);
		
		Candidato candidatoPedro = saveCandidato();
		Candidato candidatoMaria = saveCandidato();
		Candidato candidatoJoao = saveCandidato();

		CandidatoSolicitacao candidatoSolicitacaoPedro = saveCandidatoSolicitacao(candidatoPedro, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacaoMaria = saveCandidatoSolicitacao(candidatoMaria, solicitacao1);
		CandidatoSolicitacao candidatoSolicitacaoJoao = saveCandidatoSolicitacao(candidatoJoao, solicitacao2);

		saveColaboradorComHistorico(empresa, "Pedro", hoje, estabelecimento1, areaOrganizacional1, StatusRetornoAC.CONFIRMADO, candidatoPedro, candidatoSolicitacaoPedro);
		saveColaboradorComHistorico(empresa, "Maria", hoje, estabelecimento1, areaOrganizacional1, StatusRetornoAC.CONFIRMADO, candidatoMaria, candidatoSolicitacaoMaria);
		saveColaboradorComHistorico(empresa, "Joao", hoje, estabelecimento2, areaOrganizacional1, StatusRetornoAC.CONFIRMADO, candidatoJoao, candidatoSolicitacaoJoao);
		
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
	
	@Test public void testFindSemCodigoAC() {
		
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

	@Test public void testDependenciasApagarColaboradorNoImportador()
	{
		String qtdTabelasComColaborador = JDBCConnection.executeQuery("select count(table_name) from information_schema.columns as col where col.column_name = 'colaborador_id' and col.table_schema = 'public' and is_updatable = 'YES';");
		/**se esse quebrar, provavelmente tem que inserir uma linha de delete no Importador colaboradorJDBC.java método removerColaborador(); **/
		assertEquals("27", qtdTabelasComColaborador);
	}

	@Test public void testAtualizaSolicitacaoDesligamento() 
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Colaborador solicitanteDemissao = ColaboradorFactory.getEntity();
		colaboradorDao.save(solicitanteDemissao);
		
		Date hoje = new Date(); 

		Exception exception = null;
		try {
			colaboradorDao.atualizaSolicitacaoDesligamento(null, hoje, null, null, null, solicitanteDemissao.getId(), colaborador.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	@Test public void testFindPendenciasSolicitacaoDesligamentoAC() 
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
	
	@Test public void testFindAdmitidosHaDiasSemEpi()
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

		HistoricoColaborador historicoColaborador1 = criaHistoricoColaborador(funcao, colaborador1);
		historicoColaboradorDao.save(historicoColaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDataAdmissao(quarentaUmDiasAtras.getTime());
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = criaHistoricoColaborador(funcao, colaborador2);
		historicoColaboradorDao.save(historicoColaborador2);
		
		assertEquals(1, colaboradorDao.findAdmitidosHaDiasSemEpi(Arrays.asList(40, 49), empresa.getId()).size());

	}

	private HistoricoColaborador criaHistoricoColaborador(Funcao funcao, Colaborador colaborador) {
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(null, colaborador, funcao, DateUtil.criarDataMesAno(01, 01, 2005));
		return historicoColaborador2;
	}
	
	@Test public void testFindAdmitidosHaDiasSemEpiComEpi()
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
		
		HistoricoColaborador historicoColaborador1 = criaHistoricoColaborador(funcao, colaborador1);
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDataAdmissao(quarentaUmDiasAtras.getTime());
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = criaHistoricoColaborador(funcao, colaborador2);
		historicoColaboradorDao.save(historicoColaborador2);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setColaborador(colaborador1);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		assertEquals(0, colaboradorDao.findAdmitidosHaDiasSemEpi(Arrays.asList(40, 49), empresa.getId()).size());
		
	}
	
	@Test public void testFindAguardandoEntregaEpi()
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
	
	@Test public void testFfindParaLembreteTerminoContratoTemporario()
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

	@Test public void testTriarColaboradorPorFaixasEspecificas() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Date data = DateUtil.criarDataMesAno(1, 1, 2012);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo");
		cargo.setAtivo(true);
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setNome("Faixa");
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
		
		Collection<Colaborador> colaboradores = colaboradorDao.triar(new Long[]{empresa1.getId(), empresa2.getId()}, null, null, null, null, new String[]{faixaSalarial.getDescricaoComStatus()}, new Long[]{}, null, false, true);
		assertEquals(2, colaboradores.size());
		
		colaboradores = colaboradorDao.triar(new Long[]{empresa1.getId()}, null, null, null, null, new String[]{String.valueOf(faixaSalarial.getId())}, new Long[]{}, null, false, false);
		assertEquals(1, colaboradores.size());
		
	}

	@Test public void testTriarColaboradorPorTodasAsFaixas() 
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
		
		Collection<Colaborador> colaboradores = colaboradorDao.triar(new Long[]{empresa1.getId()}, null, null, null, null, new String[]{}, new Long[]{}, null, false, true);
		assertEquals(1, colaboradores.size());		
	}
	
	@Test public void testCountOcorrencia() 
	{
		Date hoje = new Date();
		Date dataFim = DateUtil.incrementaMes(hoje, 1);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, hoje, false, null, false);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional area = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		saveHistoricoColaborador(colaborador, estabelecimento, area, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Falta");
		Ocorrencia ocorrencia2 = saveOcorrencia("Matou");

		saveColaboradorOcorrencia(colaborador, ocorrencia1, hoje, dataFim);
		saveColaboradorOcorrencia(colaborador, ocorrencia2, hoje, dataFim);
		
		Long[] estabelecimentosIds = {estabelecimento.getId()};
		Long[] areasIds = {area.getId()};
		Long[] cargosIds = {cargo.getId()};
		
		assertEquals(1, colaboradorDao.countOcorrencia(hoje, dataFim, Arrays.asList(empresa.getId()), estabelecimentosIds, areasIds, cargosIds, new Long[]{ocorrencia1.getId()}, 2).size());
		assertEquals(2, colaboradorDao.countOcorrencia(hoje, dataFim, Arrays.asList(empresa.getId()), new Long[]{estabelecimento.getId()}, areasIds, cargosIds, new Long[]{}, 2).size());
	}
	
	@Test public void testCountOcorrenciaComColaboradorDesligado() 
	{
		Date hoje = new Date();
		Date dataFim = DateUtil.incrementaMes(hoje, 1);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, hoje, true, DateUtil.incrementaMes(hoje, 2), false);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional area = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		saveHistoricoColaborador(colaborador, estabelecimento, area, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Falta");
		Ocorrencia ocorrencia2 = saveOcorrencia("Matou");

		saveColaboradorOcorrencia(colaborador, ocorrencia1, hoje, dataFim);
		saveColaboradorOcorrencia(colaborador, ocorrencia2, hoje, dataFim);
		
		Long[] estabelecimentosIds = {estabelecimento.getId()};
		Long[] areasIds = {area.getId()};
		Long[] cargosIds = {cargo.getId()};
		
		assertEquals(1, colaboradorDao.countOcorrencia(hoje, dataFim, Arrays.asList(empresa.getId()), estabelecimentosIds, areasIds, cargosIds, new Long[]{ocorrencia1.getId()}, 2).size());
		assertEquals(2, colaboradorDao.countOcorrencia(hoje, dataFim, Arrays.asList(empresa.getId()), new Long[]{estabelecimento.getId()}, areasIds, cargosIds, new Long[]{}, 2).size());
	}
	
	@Test 
	public void testCountOcorrenciaComDataInicioAntesDoPeriodoFiltrado() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, DateUtil.criarDataMesAno(1, 1, 2014), false, null, false);
		
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		
		saveHistoricoColaborador(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2014), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Falta");

		saveColaboradorOcorrencia(colaborador, ocorrencia1, DateUtil.criarDataMesAno(1, 1, 2015), DateUtil.criarDataMesAno(03,01,2015));
		saveColaboradorOcorrencia(colaborador, ocorrencia1, DateUtil.criarDataMesAno(03, 1, 2015), DateUtil.criarDataMesAno(03, 1, 2015) );
		
		Collection<DataGrafico> graficoOcorrencias = colaboradorDao.countOcorrencia(DateUtil.criarDataMesAno(03, 1, 2015), DateUtil.criarDataMesAno(03, 1, 2015), Arrays.asList(empresa.getId()), null, null, null, null, 2);
		assertEquals("Quantidade da mesma ocorrência no período filtrado", new Float(2.0), new Float(((DataGrafico) graficoOcorrencias.toArray()[0]).getData()));
	}
	
	@Test 
	public void testCountOcorrenciaComDataFimNula() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, DateUtil.criarDataMesAno(1, 1, 2014), false, null, false);
		
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		
		saveHistoricoColaborador(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2014), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Falta");

		saveColaboradorOcorrencia(colaborador, ocorrencia1, DateUtil.criarDataMesAno(1, 1, 2015), null);
		saveColaboradorOcorrencia(colaborador, ocorrencia1, DateUtil.criarDataMesAno(03, 1, 2015), null);
		
		Collection<DataGrafico> graficoOcorrencias = colaboradorDao.countOcorrencia(DateUtil.criarDataMesAno(03, 1, 2015), DateUtil.criarDataMesAno(03, 1, 2015), Arrays.asList(empresa.getId()), null, null, null, null, 2);
		assertEquals("Quantidade da mesma ocorrência no período filtrado", new Float(1.0), new Float(((DataGrafico) graficoOcorrencias.toArray()[0]).getData()));
	}

	@Test
	public void testCountOcorrenciaComDataInicioAntesEDataFimDespoisDoPeriodoFiltrado() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, DateUtil.criarDataMesAno(1, 1, 2014), false, null, false);
		
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		
		saveHistoricoColaborador(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2014), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Faltou Reunião");
		Ocorrencia ocorrencia2 = saveOcorrencia("Atraso");

		saveColaboradorOcorrencia(colaborador, ocorrencia1, DateUtil.criarDataMesAno(03, 01, 2015), DateUtil.criarDataMesAno(05, 01, 2015) );
		saveColaboradorOcorrencia(colaborador, ocorrencia2, DateUtil.criarDataMesAno(01, 01, 2015), DateUtil.criarDataMesAno(01,01,2015));
		saveColaboradorOcorrencia(colaborador, ocorrencia2, DateUtil.criarDataMesAno(04, 01, 2015), DateUtil.criarDataMesAno(04,01,2015));
		
		Collection<DataGrafico> graficoOcorrencias = colaboradorDao.countOcorrencia(DateUtil.criarDataMesAno(04, 1, 2015), DateUtil.criarDataMesAno(04, 1, 2015), Arrays.asList(empresa.getId()), null, null, null, null, 2);
		assertEquals("Quantidade de ocorrências diferentes no período filtrado", 2, graficoOcorrencias.size());
		assertEquals("Quantidade de ocorrências do tipo 'Atraso' no período filtrado", new Float(1.0), new Float(((DataGrafico) graficoOcorrencias.toArray()[0]).getData()));
		assertEquals("Quantidade de ocorrências do tipo 'Faltou Reunião' no período filtrado", new Float(1.0), new Float(((DataGrafico) graficoOcorrencias.toArray()[1]).getData()));
	}
	
	@Test 
	public void testCountProvidencia() 
	{
		Date hoje = new Date();
		Date dataFim = DateUtil.incrementaMes(hoje, 1);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, hoje, false, null, false);
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional area = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		saveHistoricoColaborador(colaborador, estabelecimento, area, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Falta");
		Ocorrencia ocorrencia2 = saveOcorrencia("Briga");
		
		Providencia providencia1 = saveProvidencia("Não faltar");
		Providencia providencia2 = saveProvidencia("Não brigar");
		
		saveColaboradorOcorrencia(colaborador, ocorrencia1, providencia1, hoje, dataFim);
		saveColaboradorOcorrencia(colaborador, ocorrencia2, providencia2, hoje, dataFim);
		
		assertEquals(1, colaboradorDao.countProvidencia(hoje, dataFim, Arrays.asList(empresa.getId()),  new Long[]{estabelecimento.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, new Long[]{ocorrencia1.getId()}, 2).size());
		assertEquals(2, colaboradorDao.countProvidencia(hoje, dataFim, Arrays.asList(empresa.getId()),  null, null, null, null, 2).size());
	}
	
	@Test 
	public void testCountProvidenciaOcorrenciComDataFimNula() 
	{
		Date hoje = new Date();
		Date dataFim = DateUtil.incrementaMes(hoje, 1);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, hoje, false, null, false);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		
		AreaOrganizacional area = saveAreaOrganizacional();
		
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		saveHistoricoColaborador(colaborador, estabelecimento, area, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Falta");
		Ocorrencia ocorrencia2 = saveOcorrencia("Briga");
		
		Providencia providencia1 = saveProvidencia("Não faltar");
		Providencia providencia2 = saveProvidencia("Não brigar");
		
		saveColaboradorOcorrencia(colaborador, ocorrencia1, providencia1, hoje, null);
		saveColaboradorOcorrencia(colaborador, ocorrencia2, providencia2, DateUtil.incrementaMes(hoje, 1), null);
		
		assertEquals(1, colaboradorDao.countProvidencia(hoje, dataFim, Arrays.asList(empresa.getId()),  new Long[]{estabelecimento.getId()}, new Long[]{area.getId()}, new Long[]{cargo.getId()}, new Long[]{ocorrencia1.getId()}, 2).size());
		assertEquals(2, colaboradorDao.countProvidencia(hoje, dataFim, Arrays.asList(empresa.getId()),  null, null, null, null, 2).size());
	}
	
	@Test public void testCountProvidencia1() 
	{
		Date dataAdmissao = DateUtil.criarDataMesAno(01, 01, 2016);
		Date dataDesligamento = DateUtil.criarDataMesAno(01, 04, 2016);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, dataAdmissao, true, dataDesligamento, false);
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		saveHistoricoColaborador(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2008), StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrencia1 = saveOcorrencia("Atestado");
		Ocorrencia ocorrencia2 = saveOcorrencia("Briga");
		
		Providencia providencia1 = saveProvidencia("Abonar Hora");
		Providencia providencia2 = saveProvidencia("Desligamento");
		
		saveColaboradorOcorrencia(colaborador, ocorrencia1, providencia1, DateUtil.criarDataMesAno(24, 02, 2016), DateUtil.criarDataMesAno(9, 03, 2016));
		saveColaboradorOcorrencia(colaborador, ocorrencia2, providencia2, DateUtil.criarDataMesAno(31, 03, 2016), DateUtil.criarDataMesAno(31, 03, 2016));
		
		Collection<DataGrafico> graficoProvidencias = colaboradorDao.countProvidencia(DateUtil.criarDataMesAno(01, 03, 2016), DateUtil.criarDataMesAno(31, 03, 2016), Arrays.asList(empresa.getId()), null, null, null, null, 2);
		
		assertEquals("Quantidade de providencias diferentes para o período filtrado", 2, graficoProvidencias.size());
		assertEquals("Quantidade de providencias do tipo 'Abonar Hora' para o período filtrado", new Float(1.0), new Float(((DataGrafico) graficoProvidencias.toArray()[0]).getData()));
		assertEquals("Abonar Hora", ((DataGrafico) graficoProvidencias.toArray()[0]).getLabel());
		assertEquals("Quantidade de providencias do tipo 'Desligamento' para o período filtrado", new Float(1.0), new Float(((DataGrafico) graficoProvidencias.toArray()[1]).getData()));
		assertEquals("Desligamento", ((DataGrafico) graficoProvidencias.toArray()[1]).getLabel());
	}
	
	
	@Test public void testHabilitacaoAVencer()
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
	
	@Test public void testCountDemitidosTempoServico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(a1);
		
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 3, 2012), null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 8, 2010), null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 5, 2011), DateUtil.criarDataMesAno(1, 11, 2011), null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 5, 2009), DateUtil.criarDataMesAno(1, 11, 2009), null, null, null, estabelecimento1, a1, fs1, null, null, false);
		
		Empresa empresaTurnover = EmpresaFactory.getEmpresa();
		empresaTurnover.setTurnoverPorSolicitacao(true);
		empresaDao.save(empresaTurnover);

		MotivoDemissao motivoDesligamento = new MotivoDemissao();
		motivoDesligamento.setTurnover(true);
		motivoDemissaoDao.save(motivoDesligamento);
		
		criarColaboradorHistorico(null, null, empresaTurnover, DateUtil.criarDataMesAno(1, 5, 2011), DateUtil.criarDataMesAno(1, 11, 2011), null, null, motivoDesligamento, estabelecimento, a1, fs1, null, null, false);
		
		Collection<TurnOver> retorno = colaboradorDao.countDemitidosTempoServico(empresa, DateUtil.criarDataMesAno(1, 1, 2010), DateUtil.criarDataMesAno(1, 1, 2013), Arrays.asList(estabelecimento.getId()), Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO));
		
		assertEquals(2, retorno.size());

		TurnOver t1 = ((TurnOver) retorno.toArray()[0]);
		TurnOver t2 = ((TurnOver) retorno.toArray()[1]);
		
		assertEquals(new Integer(2), t1.getQtdColaboradores());
		assertEquals(new Integer(6), t1.getTempoServico());
		
		assertEquals(new Integer(1), t2.getQtdColaboradores());
		assertEquals(new Integer(25), t2.getTempoServico());
		
		retorno = colaboradorDao.countDemitidosTempoServico(empresaTurnover, DateUtil.criarDataMesAno(1, 1, 2010), DateUtil.criarDataMesAno(1, 1, 2013), Arrays.asList(estabelecimento.getId()), Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO));

		assertEquals(1, retorno.size());
		
	}
	
	@Test public void testCountColaboradoresPorTempoServico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(a1);
		
		criarColaboradorHistorico(null, null, empresa, DateUtil.incrementaData(new Date(), Calendar.DAY_OF_MONTH, -15, true), null, null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.incrementaData(new Date(), Calendar.MONTH, -1, true), null, null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.incrementaData(new Date(), Calendar.MONTH, -5, true), null, null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.incrementaData(new Date(), Calendar.MONTH, -8, true), null, null, null, null, estabelecimento, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.incrementaData(new Date(), Calendar.MONTH, -10, true), null, null, null, null, estabelecimento1, a1, fs1, null, null, false);
		
		empresaDao.getHibernateTemplateByGenericDao().flush();
		
		int qtdColaboradoresPorTempoServico = colaboradorDao.countColaboradoresPorTempoServico(empresa, 1, 8, Arrays.asList(estabelecimento.getId()), Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO));
		
		assertEquals(3, qtdColaboradoresPorTempoServico);
	}
	
	@Test public void testFindDemitidosTurnover()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(a1);
		
		Estabelecimento e1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(e1);
		
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 3, 2012), null, null, null, e1, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 8, 2010), null, null, null, null, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 5, 2011), DateUtil.criarDataMesAno(1, 11, 2011), null, null, null, null, a1, fs1, null, null, false);
		criarColaboradorHistorico(null, null, empresa, DateUtil.criarDataMesAno(1, 5, 2009), DateUtil.criarDataMesAno(1, 11, 2009), null, null, null, null, a1, fs1, null, null, false);
		
		Empresa empresaTurnover = EmpresaFactory.getEmpresa();
		empresaTurnover.setTurnoverPorSolicitacao(true);
		empresaDao.save(empresaTurnover);
		
		MotivoDemissao md = new MotivoDemissao();
		md.setTurnover(true);
		motivoDemissaoDao.save(md);

		Candidato can1 = CandidatoFactory.getCandidato();
		candidatoDao.save(can1);
		
		criarColaboradorHistorico(null, null, empresaTurnover, DateUtil.criarDataMesAno(1, 5, 2011), DateUtil.criarDataMesAno(1, 11, 2011), null, null, md, null, a1, fs1, can1, null, false);
		
		Collection<Colaborador> retorno = colaboradorDao.findDemitidosTurnover(empresa, DateUtil.criarDataMesAno(1, 1, 2010), DateUtil.criarDataMesAno(1, 1, 2013), null, null, null, Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO), null);
		assertEquals("Todos do intervalo", 3, retorno.size());

		retorno = colaboradorDao.findDemitidosTurnover(empresa, DateUtil.criarDataMesAno(1, 1, 2010), DateUtil.criarDataMesAno(1, 1, 2013), new Integer[] {0}, new Integer[] {6}, null, Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO), null);
		assertEquals("Passando intervalos de tempo de servico", 2, retorno.size());

		retorno = colaboradorDao.findDemitidosTurnover(empresa, DateUtil.criarDataMesAno(1, 1, 2010), DateUtil.criarDataMesAno(1, 1, 2013), null, null, Arrays.asList(e1.getId()), Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO), null);
		assertEquals("Filtro por estabelecimento", 1, retorno.size());

		retorno = colaboradorDao.findDemitidosTurnover(empresaTurnover, DateUtil.criarDataMesAno(1, 1, 2010), DateUtil.criarDataMesAno(1, 1, 2013), null, null, null, Arrays.asList(a1.getId()), Arrays.asList(cargo.getId()), Arrays.asList(Vinculo.EMPREGO),null);
		assertEquals("Empresa com turnover por solicitacao", 1, retorno.size());
	}
	
	private void criarColaboradorHistorico(String nomeColaborador, String codigoAc, Empresa empresa, Date dataAdmissao, Date dataDesligamento, Date dataSolicitacaoDesligamento, Date dataSolicitacaoDesligamentoAC, MotivoDemissao motivoDemissao, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, FaixaSalarial faixaSalarial, Candidato candidato, Usuario usuario, boolean naoIntegraAC)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nomeColaborador);
		colaborador.setUsuario(usuario);
		colaborador.setCandidato(candidato);
		colaborador.setEmpresa(empresa);
		colaborador.setVinculo(Vinculo.EMPREGO);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setDataDesligamento(dataDesligamento);
		colaborador.setDataSolicitacaoDesligamento(dataSolicitacaoDesligamento);
		colaborador.setDataSolicitacaoDesligamentoAc(dataSolicitacaoDesligamentoAC);
		colaborador.setMotivoDemissao(motivoDemissao);
		colaborador.setCodigoAC(codigoAc);
		colaborador.setNaoIntegraAc(naoIntegraAC);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setData(dataAdmissao);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
	}
	
	@Test public void testFindByEmpresaAndStatusAC()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		a1.setNome("Mãe");
		areaOrganizacionalDao.save(a1);
		
		AreaOrganizacional a2 = AreaOrganizacionalFactory.getEntity();
		a2.setAreaMae(a1);
		a2.setNome("Filha");
		areaOrganizacionalDao.save(a2);
		
		Estabelecimento e1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(e1);
		
		criarColaboradorHistorico("Airton", null, empresa1, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 3, 2012), null, null, null, e1, a2, fs1, null, null, true);
		criarColaboradorHistorico("Bruna", null, empresa1, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 8, 2010), null, null, null, e1, a2, fs1, null, null, false);
		criarColaboradorHistorico("Chico", null, empresa2, DateUtil.criarDataMesAno(1, 5, 2011), DateUtil.criarDataMesAno(1, 11, 2011), null, null, null, null, a2, fs1, null, null, true);
		criarColaboradorHistorico("Demosval", null, empresa2, DateUtil.criarDataMesAno(1, 5, 2009), DateUtil.criarDataMesAno(1, 11, 2009), null, null, null, null, a2, fs1, null, null, true);
		
		Collection<Colaborador> colaboradoresNaoIntegraAC = colaboradorDao.findByEmpresaAndStatusAC(empresa1.getId(), null, null, StatusRetornoAC.CONFIRMADO, true, true, null, true, "c.nome");
		Collection<Colaborador> colaboradores = colaboradorDao.findByEmpresaAndStatusAC(empresa1.getId(), null, null, StatusRetornoAC.CONFIRMADO, true, false, null, true, "c.nome");

		assertEquals(1, colaboradoresNaoIntegraAC.size());
		assertEquals("Bruna", ((Colaborador)colaboradoresNaoIntegraAC.toArray()[0]).getNome());
		
		assertEquals(2, colaboradores.size());
		assertEquals("Airton", ((Colaborador)colaboradores.toArray()[0]).getNome());
		assertEquals("Bruna", ((Colaborador)colaboradores.toArray()[1]).getNome());
		assertEquals("Mãe > Filha", ((Colaborador)colaboradores.toArray()[0]).getAreaOrganizacional().getNome());
	}
	
	@Test public void testDesvinculaCandidato() 
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		Colaborador colaboradorRetorno = colaboradorDao.findByCandidato(candidato.getId(), null);
		assertNotNull(colaboradorRetorno);

		colaboradorDao.desvinculaCandidato(candidato.getId());
		colaboradorRetorno = colaboradorDao.findByCandidato(candidato.getId(), null);
		assertNull(colaboradorRetorno);
	}
	
	@Test public void testFindAguardandoDesligamento()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(a1);
		
		Estabelecimento e1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(e1);
		
		Long[] areas = new Long[]{a1.getId()};
		
		criarColaboradorHistorico("Demosval Pede Pra Sair", null, empresa1, DateUtil.criarDataMesAno(1, 5, 2009), null, DateUtil.criarDataMesAno(1, 11, 2010), null, null, e1, a1, fs1, null, null, false);
		criarColaboradorHistorico("Airton Desligado", null, empresa1, DateUtil.criarDataMesAno(1, 2, 2010), DateUtil.criarDataMesAno(1, 3, 2012), null, null, null, e1, a1, fs1, null, null, false);
		criarColaboradorHistorico("Bruna Aguardando no AC", null, empresa1, DateUtil.criarDataMesAno(1, 2, 2010), null, DateUtil.criarDataMesAno(1, 8, 2010), DateUtil.criarDataMesAno(5, 8, 2010), null, e1, a1, fs1, null, null, false);
		criarColaboradorHistorico("Chico de Outra Empresa", null, empresa2, DateUtil.criarDataMesAno(1, 5, 2011), null, DateUtil.criarDataMesAno(1, 11, 2011), null, null, e1, a1, fs1, null, null, false);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findAguardandoDesligamento(empresa1.getId(), areas, null);
		
		assertEquals(1, colaboradores.size());
		assertEquals("Demosval Pede Pra Sair", ((Colaborador)colaboradores.toArray()[0]).getNome());
	}
	
	@Test public void testRemoveComDependencias()
	{	
		Colaborador colaborador = ColaboradorFactory.getEntity(-1L);
		colaboradorDao.save(colaborador);
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(-1L);
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);
		
		colaboradorDao.removeComDependencias(-1L);
		assertNull(colaboradorDao.findEntidadeComAtributosSimplesById(-1L));
		
		String qtdTabelasComColaborador = JDBCConnection.executeQuery("SELECT COUNT(kcu.column_name) FROM information_schema.table_constraints AS tc INNER JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name INNER JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = 'FOREIGN KEY' AND ccu.table_name = 'colaborador'");
		assertEquals("Se esse quebrar, provavelmente tem que inserir uma linha de delete em ColaboradorDaoHibernate.removeComDependencias", "36", qtdTabelasComColaborador);
	}
	
	@Test public void testFindUsuarioByAreaEstabelecimento()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Estabelecimento estab1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estab1);
		
		Estabelecimento estab2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estab2);
		
		criarColaboradorHistorico("Colaborador 1", null, null, DateUtil.criarDataMesAno(1, 5, 2014), null, null, null, null, estab1, area1, null, null, usuario1, false);
		criarColaboradorHistorico("Colaborador 2", null, null, DateUtil.criarDataMesAno(1, 6, 2014), null, null, null, null, estab2, area2, null, null, usuario2, false);
		criarColaboradorHistorico("Colaborador 3", null, null, DateUtil.criarDataMesAno(1, 7, 2014), null, null, null, null, estab2, area2, null, null, null, false);
		
		Collection<Usuario> usuarios = colaboradorDao.findUsuarioByAreaEstabelecimento(new Long[] {area2.getId()}, new Long[] {estab2.getId()});
		
		assertEquals(1, usuarios.size());
		assertEquals(usuario2.getId(), ((Usuario)usuarios.toArray()[0]).getId());
	}
	
	@Test public void testFindColaboradoresByCodigoAC() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(a1);
		
		Estabelecimento e1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(e1);
		
		criarColaboradorHistorico("Demosval", "000012", empresa1, DateUtil.criarDataMesAno(1, 5, 2009), null, DateUtil.criarDataMesAno(1, 11, 2010), null, null, e1, a1, fs1, null, null, false);
		
		assertEquals(1, colaboradorDao.findColaboradoresByCodigoAC(empresa1.getId(), true, "000012").size());
		
		criarColaboradorHistorico("Xuxa", "000013", empresa1, DateUtil.criarDataMesAno(1, 5, 2009), null, DateUtil.criarDataMesAno(1, 11, 2010), null, null, e1, a1, fs1, null, null, false);
		criarColaboradorHistorico("Angelica", "000014", empresa2, DateUtil.criarDataMesAno(1, 5, 2009), null, DateUtil.criarDataMesAno(1, 11, 2010), null, null, e1, a1, fs1, null, null, false);
		
		assertEquals(2, colaboradorDao.findColaboradoresByCodigoAC(empresa1.getId(), true, new String[]{"000012","000013","000014"}).size());
	}
	
	@Test public void testFindByEstadosCelularOitoDigitos(){
		Estado estado = EstadoFactory.getEntity(50L);
		estadoDao.save(estado);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.getEndereco().setUf(estado);
		colaborador = colaboradorDao.save(colaborador);
		
		assertEquals(1, colaboradorDao.findByEstadosCelularOitoDigitos(new Long[]{estado.getId()}).size());
		
	}
	
	@Test public void testListColaboradorComDataSolDesligamentoAcAndCodigoAc()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial fs1 = FaixaSalarialFactory.getEntity();
		fs1.setCargo(cargo);
		faixaSalarialDao.save(fs1);
		
		AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(a1);
		
		Estabelecimento e1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(e1);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Demosval");
		colaborador.setEmpresa(empresa1);
		colaborador.setVinculo(Vinculo.EMPREGO);
		colaborador.setDataAdmissao(new Date());
		colaborador.setDataDesligamento(new Date());
		colaborador.setDataSolicitacaoDesligamentoAc(new Date());
		colaborador.setCodigoAC("000012");
		colaboradorDao.save(colaborador);
		
		Collection<Colaborador> colaboradores = colaboradorDao.listColaboradorComDataSolDesligamentoAC(empresa1.getId());
		assertEquals(1, colaboradores.size());
	}
	
	@Test public void testCountDemitidosPeriodo()
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataFim = DateUtil.getUltimoDiaMes(dataIni);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setVinculo(Vinculo.EMPREGO);
		colaborador1.setDataDesligamento(DateUtil.criarDataMesAno(10, 1, 2015));
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(dataIni);
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador1.setEstabelecimento(estabelecimento1);
		historicoColaborador1.setAreaOrganizacional(area);
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setVinculo(Vinculo.ESTAGIO);
		colaborador2.setDataDesligamento(DateUtil.criarDataMesAno(10, 1, 2015));
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(dataIni);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador2.setEstabelecimento(estabelecimento2);
		historicoColaborador2.setAreaOrganizacional(area);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Colaborador colaboradorForaDoPeriodoPassado = ColaboradorFactory.getEntity();
		colaboradorForaDoPeriodoPassado.setEmpresa(empresa);
		colaboradorForaDoPeriodoPassado.setVinculo(Vinculo.ESTAGIO);
		colaboradorForaDoPeriodoPassado.setDataDesligamento(DateUtil.criarDataMesAno(10, 2, 2015));
		colaboradorDao.save(colaboradorForaDoPeriodoPassado);
		
		HistoricoColaborador historicoColaboradorForaDoPeriodoPassado = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorForaDoPeriodoPassado.setData(dataIni);
		historicoColaboradorForaDoPeriodoPassado.setColaborador(colaboradorForaDoPeriodoPassado);
		historicoColaboradorForaDoPeriodoPassado.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorForaDoPeriodoPassado.setEstabelecimento(estabelecimento2);
		historicoColaboradorForaDoPeriodoPassado.setAreaOrganizacional(area);
		historicoColaboradorDao.save(historicoColaboradorForaDoPeriodoPassado);
		
		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity();
		motivoDemissao.setReducaoDeQuadro(true);
		motivoDemissaoDao.save(motivoDemissao);
		
		Colaborador colaboradorcomMotivoDesligamentoReducaoQuadro = ColaboradorFactory.getEntity();
		colaboradorcomMotivoDesligamentoReducaoQuadro.setEmpresa(empresa);
		colaboradorcomMotivoDesligamentoReducaoQuadro.setVinculo(Vinculo.ESTAGIO);
		colaboradorcomMotivoDesligamentoReducaoQuadro.setDataDesligamento(DateUtil.criarDataMesAno(15, 1, 2015));
		colaboradorcomMotivoDesligamentoReducaoQuadro.setMotivoDemissao(motivoDemissao);
		colaboradorDao.save(colaboradorcomMotivoDesligamentoReducaoQuadro);
		
		HistoricoColaborador historicoColaboradorcomMotivoDesligamentoReducaoQuadro = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorcomMotivoDesligamentoReducaoQuadro.setData(dataIni);
		historicoColaboradorcomMotivoDesligamentoReducaoQuadro.setColaborador(colaboradorcomMotivoDesligamentoReducaoQuadro);
		historicoColaboradorcomMotivoDesligamentoReducaoQuadro.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorcomMotivoDesligamentoReducaoQuadro.setEstabelecimento(estabelecimento2);
		historicoColaboradorcomMotivoDesligamentoReducaoQuadro.setAreaOrganizacional(area);
		historicoColaboradorDao.save(historicoColaboradorcomMotivoDesligamentoReducaoQuadro);
		
		assertEquals(new Integer(2), colaboradorDao.countDemitidosPeriodo(dataIni, dataFim, empresa.getId(), Arrays.asList(estabelecimento2.getId()), Arrays.asList(area.getId()), null, Arrays.asList(colaborador2.getVinculo()), false));
		assertEquals("Vinculos nulos", new Integer(3), colaboradorDao.countDemitidosPeriodo(dataIni, dataFim, empresa.getId(), Arrays.asList(estabelecimento1.getId(), estabelecimento2.getId()), Arrays.asList(area.getId()), null, null, false));
		assertEquals("Os dois Vinculos", new Integer(3), colaboradorDao.countDemitidosPeriodo(dataIni, dataFim, empresa.getId(), Arrays.asList(estabelecimento1.getId(), estabelecimento2.getId()), Arrays.asList(area.getId()), null, Arrays.asList(colaborador1.getVinculo(), colaborador2.getVinculo() ), false));
		assertEquals("Vinculos nulos e um único estabelecimento", new Integer(1), colaboradorDao.countDemitidosPeriodo(dataIni, dataFim, empresa.getId(), Arrays.asList(estabelecimento1.getId()), Arrays.asList(area.getId()), null, null, false));
		assertEquals("Colaborador com Motivo de desligamento de Redução de quadro", new Integer(1), colaboradorDao.countDemitidosPeriodo(dataIni, dataFim, empresa.getId(), null, null, null, null, true));
	}
	
	@Test public void testGetOcorrenciasByPeriodo(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Date dataAdmissao = DateUtil.criarDataMesAno(1, 01, 2015);
		Date dataHistorico = DateUtil.criarDataMesAno(1, 01, 2015);
		
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		Colaborador colaborador = saveColaborador("Samuel", empresa, dataAdmissao, true, new Date(), false);
		saveHistoricoColaborador(colaborador, faixaSalarial, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Ocorrencia ocorrenciaAtestadoMedico = saveOcorrencia("Atestado médico");
		Ocorrencia ocorrenciaFalta = saveOcorrencia("Falta");
		
		saveColaboradorOcorrencia(colaborador, ocorrenciaAtestadoMedico, DateUtil.criarDataMesAno(1, 01, 2016), DateUtil.criarDataMesAno(1, 02, 2016));
		saveColaboradorOcorrencia(colaborador, ocorrenciaFalta, DateUtil.criarDataMesAno(10, 01, 2016), DateUtil.criarDataMesAno(1, 03, 2016));
		
		Collection<Ocorrencia> ocorrencias = colaboradorDao.getOcorrenciasByPeriodo(DateUtil.criarDataMesAno(1, 01, 2016), DateUtil.criarDataMesAno(01, 02, 2016),  LongUtil.arrayLongToCollectionLong(new Long[]{empresa.getId()}), null, null, null, 20);
		
		assertEquals(2, ocorrencias.size());
		assertEquals("Atestado médico", ((Ocorrencia)ocorrencias.toArray()[0]).getDescricao());
	}
	
	@Test public void testGetOcorrenciasByPeriodoComOcorrenciaIniciandoAntesEFinalizandoDepoisDoPeriodoFiltrado(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Date dataAdmissao = DateUtil.criarDataMesAno(1, 01, 2014);
		Date dataHistorico = DateUtil.criarDataMesAno(1, 01, 2014);
		
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		Colaborador colaborador = saveColaborador("Samuel", empresa,dataAdmissao, false, null, false);
		
		saveHistoricoColaborador(colaborador, faixaSalarial, dataHistorico, StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrenciaAtestadoMedico = saveOcorrencia("Afastamento");
		
		saveColaboradorOcorrencia(colaborador, ocorrenciaAtestadoMedico, DateUtil.criarDataMesAno(1, 01, 2016), DateUtil.criarDataMesAno(1, 02, 2016));
		
		Collection<Ocorrencia> ocorrencias = colaboradorDao.getOcorrenciasByPeriodo(DateUtil.criarDataMesAno(15, 01, 2016), DateUtil.criarDataMesAno(31, 01, 2016),  LongUtil.arrayLongToCollectionLong(new Long[]{empresa.getId()}), null, null, null, 20);
		
		assertEquals(1, ocorrencias.size());
		assertEquals("Afastamento", ((Ocorrencia)ocorrencias.toArray()[0]).getDescricao());
	}
	
	@Test public void testGetOcorrenciasByPeriodoComOcorrenciaIniciandoAntesDoPeriodoFiltrado(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date dataAdmissao = DateUtil.criarDataMesAno(1, 01, 2014);
		Date dataHistorico = DateUtil.criarDataMesAno(1, 01, 2014);
		
		FaixaSalarial faixaSalarial = saveFaixaSalarial();
		Colaborador colaborador = saveColaborador("Samuel", empresa, dataAdmissao, false, null, false);
		
		saveHistoricoColaborador(colaborador, faixaSalarial, dataHistorico, StatusRetornoAC.CONFIRMADO);
		
		Ocorrencia ocorrenciaAtestadoMedico = saveOcorrencia("Falta");
		
		saveColaboradorOcorrencia(colaborador, ocorrenciaAtestadoMedico, DateUtil.criarDataMesAno(1, 01, 2016), DateUtil.criarDataMesAno(1, 02, 2016));
		
		Collection<Ocorrencia> ocorrencias = colaboradorDao.getOcorrenciasByPeriodo(DateUtil.criarDataMesAno(15, 01, 2016), DateUtil.criarDataMesAno(01, 02, 2016),  LongUtil.arrayLongToCollectionLong(new Long[]{empresa.getId()}), null, null, null, 20);
		
		assertEquals(1, ocorrencias.size());
		assertEquals("Falta", ((Ocorrencia)ocorrencias.toArray()[0]).getDescricao());
	}
	
	@Test public void testGetOcorrenciasByPeriodoComTodosOsFiltros(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Date dataAdmissao = DateUtil.criarDataMesAno(1, 01, 2015);
		Date dataHistorico = DateUtil.criarDataMesAno(1, 01, 2015);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Colaborador colaborador = saveColaborador("Samuel", empresa, dataAdmissao, true, DateUtil.criarDataMesAno(1, 04, 2016), false);
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, dataHistorico, StatusRetornoAC.CONFIRMADO);

		Ocorrencia ocorrenciaAtestadoMedico = saveOcorrencia("Atestado médico");
		Ocorrencia ocorrenciaFalta = saveOcorrencia("Falta");
		
		saveColaboradorOcorrencia(colaborador, ocorrenciaAtestadoMedico, DateUtil.criarDataMesAno(1, 01, 2016), DateUtil.criarDataMesAno(1, 02, 2016));
		saveColaboradorOcorrencia(colaborador, ocorrenciaFalta, DateUtil.criarDataMesAno(10, 01, 2016), DateUtil.criarDataMesAno(1, 03, 2016));
		
		Long[] estabelecimentosId = {estabelecimento.getId()};
		Long[] areaOrganizacionaisId = {areaOrganizacional.getId()};
		Long[] cargosId = {cargo.getId()};
		
		Collection<Ocorrencia> ocorrencias = colaboradorDao.getOcorrenciasByPeriodo(DateUtil.criarDataMesAno(1, 01, 2016), DateUtil.criarDataMesAno(01, 02, 2016),  LongUtil.arrayLongToCollectionLong(new Long[]{empresa.getId()}), estabelecimentosId, areaOrganizacionaisId, cargosId, 20);
		
		assertEquals(2, ocorrencias.size());
		assertEquals("Atestado médico", ((Ocorrencia)ocorrencias.toArray()[0]).getDescricao());
		assertEquals("Falta", ((Ocorrencia)ocorrencias.toArray()[1]).getDescricao());
	}
	
	@Test public void testExisteColaboradorAtivoTrue(){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setPessoalCpf("12345678912");
		colaboradorDao.save(colaborador);
		
		assertTrue(colaboradorDao.existeColaboradorAtivo("12345678912", new Date()));
	}
	
	@Test public void testExisteColaboradorAtivoCPFNaoConfere(){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setPessoalCpf("12345678912");
		colaboradorDao.save(colaborador);
		
		assertFalse(colaboradorDao.existeColaboradorAtivo("1234567892", new Date()));
	}
	
	@Test public void testExisteColaboradorAtivoComDataDesligamentoFuturaTrue(){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setPessoalCpf("12345678912");
		colaborador.setDataDesligamento(DateUtil.incrementaDias(new Date(), 2));
		colaboradorDao.save(colaborador);
		
		assertTrue(colaboradorDao.existeColaboradorAtivo("12345678912", new Date()));
	}
	
	@Test public void testExisteColaboradorAtivoComDataDesligamentoIgualAInformadaTrue(){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setPessoalCpf("12345678912");
		colaborador.setDataDesligamento(new Date());
		colaboradorDao.save(colaborador);
		
		assertTrue(colaboradorDao.existeColaboradorAtivo("12345678912", new Date()));
	}
	
	@Test public void testExisteColaboradorAtivoCoboradorDesligado(){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setPessoalCpf("12345678912");
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(1, 04, 2016));
		colaboradorDao.save(colaborador);
		
		assertFalse(colaboradorDao.existeColaboradorAtivo("12345678912", DateUtil.criarDataMesAno(02, 04, 2016)));
	}
	
	@Test public void testFindColaboradorComESemOrdemDeServico() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Colaborador colaborador = saveColaborador(empresa, "Francisco", "000122", "04404404433", new Date());
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(02, 04, 2016), 1);
		
		Colaborador colaboradorBusca = ColaboradorFactory.getEntity(1L, "cisc", null, "0012", null, "044", empresa);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaboradorBusca, DateUtil.criarDataMesAno(02, 04, 2016), faixaSalarial, estabelecimento, areaOrganizacional, null, null, StatusRetornoAC.CONFIRMADO);
		Long[] idsAreasDoGestor = {areaOrganizacional.getId()};

		Collection<Colaborador> colaboradores = colaboradorDao.findColaboradorComESemOrdemDeServico(colaboradorBusca, historicoColaborador, idsAreasDoGestor, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.TODOS, 0, 0);
		assertEquals(1, colaboradores.size());
	}
	
	@Test public void testFindColaboradorComESemOrdemDeServicoSemDadosDoHistorico() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Colaborador colaborador = saveColaborador(empresa, "Francisco", "000122", "04404404433", new Date());
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(02, 04, 2016), 1);
		
		Colaborador colaboradorBusca = ColaboradorFactory.getEntity(1L, "Francisco", null, "0012", null, "04404404433", empresa);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();

		Collection<Colaborador> colaboradores = colaboradorDao.findColaboradorComESemOrdemDeServico(colaboradorBusca, historicoColaborador, null, SituacaoColaborador.DESLIGADO, FiltroOrdemDeServico.TODOS, 1, 15);
		assertEquals(0, colaboradores.size());
	}
	
	@Test public void testFindColaboradorComESemOrdemDeServicoFiltrandoPelasAreasQueOGestorEResponsavel() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Long[] idsAreasDoGestor = {areaOrganizacional.getId()};
		
		Colaborador colaborador = saveColaborador(empresa, "Francisco", "000122", "04404404433", new Date());
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(02, 04, 2016), 1);
		
		Colaborador colaboradorBusca = ColaboradorFactory.getEntity(1L, "Francisco", null, "0012", null, "04404404433", empresa);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();

		Collection<Colaborador> colaboradores = colaboradorDao.findColaboradorComESemOrdemDeServico(colaboradorBusca, historicoColaborador, idsAreasDoGestor, SituacaoColaborador.TODOS, FiltroOrdemDeServico.SEM_ORDEM_DE_SERVICO, 0, 0);
		assertEquals(1, colaboradores.size());
	}
	
	@Test public void testFindColaboradorComESemOrdemDeServicoFiltrandoPelasAreasQueOGestorEResponsavelAreasSemColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Long[] idsAreasDoGestor = {2L};
		
		Colaborador colaborador = saveColaborador(empresa, "Francisco", "000122", "04404404433", new Date());
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(02, 04, 2016), 1);
		
		Colaborador colaboradorBusca = ColaboradorFactory.getEntity();
		colaboradorBusca.setEmpresa(empresa);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();

		Collection<Colaborador> colaboradores = colaboradorDao.findColaboradorComESemOrdemDeServico(colaboradorBusca, historicoColaborador, idsAreasDoGestor, SituacaoColaborador.TODOS, FiltroOrdemDeServico.COM_ORDEM_DE_SERVICO, 0, 0);
		assertEquals(0, colaboradores.size());
	}
	
	@Test public void testUpdateRespondeuEntrevistaDesligamento() 
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());
		assertFalse(colaboradorRetorno.isRespondeuEntrevista());
		
		colaboradorDao.updateRespondeuEntrevistaDesligamento(colaborador.getId(), true);
		
		colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());
		assertTrue(colaboradorRetorno.isRespondeuEntrevista());
	}
	
	@Test
	public void testGetColaboradoresJsonColabId(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Estabelecimento estabelecimento = saveEstabelecimento();
		
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, colaborador.getDataAdmissao(), StatusRetornoAC.CONFIRMADO);
		
		Collection<ColaboradorJson> colaboradoresJson = colaboradorDao.getColaboradoresJson(empresa.getCnpj(), colaborador.getId(), null);
		assertEquals(1, colaboradoresJson.size());
	}
	
	@Test
	public void testGetColaboradoresJsonColabMatricula(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setMatricula("123456");
		colaboradorDao.save(colaborador);
		
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Estabelecimento estabelecimento = saveEstabelecimento();
		
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, colaborador.getDataAdmissao(), StatusRetornoAC.CONFIRMADO);
		
		Collection<ColaboradorJson> colaboradoresJson = colaboradorDao.getColaboradoresJson(empresa.getCnpj(), null, colaborador.getMatricula());
		assertEquals(1, colaboradoresJson.size());
	}
	
	@Test
	public void testFindAniversariantesPorTempoDeEmpresa() {
		Empresa empresa = saveEmpresa();
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Colaborador colaborador = saveColaborador(empresa, "C1", "012345", "01234567890", DateUtil.criarDataMesAno(1, 2, 2015));
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2015), StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> retorno = colaboradorDao.findAniversariantesPorTempoDeEmpresa(2, false, new Long[]{empresa.getId()}, null, null);
		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testFindAniversariantesPorTempoDeEmpresaComTodosOsFiltros() {
		Empresa empresa = saveEmpresa();
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Colaborador colaborador = saveColaborador(empresa, "C1", "012345", "01234567890", DateUtil.criarDataMesAno(1, 2, 2014));
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2014), StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> retorno = colaboradorDao.findAniversariantesPorTempoDeEmpresa(0, true, new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()});
		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testFindAniversarioDeEmpresaSemAniversariantesNoMes() {
		Empresa empresa = saveEmpresa();
		Estabelecimento estabelecimento = saveEstabelecimento();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		Cargo cargo = saveCargo();
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo);
		
		Colaborador colaborador = saveColaborador(empresa, "C1", "012345", "01234567890", DateUtil.criarDataMesAno(1, 2, 2013));
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2013), StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> retorno = colaboradorDao.findAniversariantesPorTempoDeEmpresa(3, false, new Long[]{empresa.getId()}, new Long[]{}, new Long[]{});
		assertEquals(0, retorno.size());
	}

	@Test
	public void testFindByAreasIds(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setDesligado(true);
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador1, DateUtil.criarDataMesAno(1, 1, 2000), estabelecimento, areaOrganizacional);
		historicoColaborador1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(colaborador2, DateUtil.criarDataMesAno(1, 1, 2000), estabelecimento, areaOrganizacional);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Collection<Colaborador> retornoColab = colaboradorDao.findByAreasIds(areaOrganizacional.getId());
		
		assertEquals(1, retornoColab.size());
	}
	
	@Test
	public void testFindByEmpresaEstabelecimentoAndAreaOrganizacional(){
		Empresa empresa = saveEmpresa();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacional1 = saveAreaOrganizacional();
		Estabelecimento estabelecimento = saveEstabelecimento();
		Estabelecimento estabelecimento1 = saveEstabelecimento();
		
		saveColaboradorComHistorico(empresa, "Colab 1", new Date(), estabelecimento1, areaOrganizacional1, StatusRetornoAC.CONFIRMADO, null, null);
		Colaborador colaborador2 = saveColaboradorComHistorico(empresa, "Colab 2", new Date(), estabelecimento, areaOrganizacional, StatusRetornoAC.CONFIRMADO, null, null);
		
		Collection<Colaborador> colaboradoresRetorno = colaboradorDao.findByEmpresaEstabelecimentoAndAreaOrganizacional(new Long[]{empresa.getId()}, new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()}, SituacaoColaborador.ATIVO);
		assertEquals(1, colaboradoresRetorno.size());
		assertEquals(colaborador2.getNome(), colaboradoresRetorno.iterator().next().getNome());
	}
	
	@Test
	public void testFindColaboradoresQueNuncaRealizaramTreinamento() {
		Empresa empresa = saveEmpresa();
		AreaOrganizacional areaOrganizacional = saveAreaOrganizacional();
		AreaOrganizacional areaOrganizacional1 = saveAreaOrganizacional();
		Estabelecimento estabelecimento = saveEstabelecimento();
		Estabelecimento estabelecimento1 = saveEstabelecimento();
		
		Colaborador colaborador1 = saveColaboradorComHistorico(empresa, "Colab 1", new Date(), estabelecimento1, areaOrganizacional1, StatusRetornoAC.CONFIRMADO, null, null);
		Colaborador colaborador2 = saveColaboradorComHistorico(empresa, "Colab 2", new Date(), estabelecimento, areaOrganizacional, StatusRetornoAC.CONFIRMADO, null, null);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity(curso, true);
		turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador1, curso, turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		Long[] areasIds = new Long[]{areaOrganizacional.getId(), areaOrganizacional1.getId()};
		Long[] estabelecimentosIds = new Long[]{estabelecimento.getId(), estabelecimento1.getId()};
		
		Collection<Colaborador> colaboradoresSemTreinamento = colaboradorDao.findColaboradoresQueNuncaRealizaramTreinamento(new Long[]{empresa.getId()}, areasIds, estabelecimentosIds);
		assertEquals(1, colaboradoresSemTreinamento.size());
		assertEquals(colaborador2.getNome(), colaboradoresSemTreinamento.iterator().next().getNome());
	}
	
	private Empresa saveEmpresa(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		return empresa;
	}
	
	private FaixaSalarial saveFaixaSalarial(Cargo cargo){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		return faixaSalarial;
	}
	
	private FaixaSalarial saveFaixaSalarial(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		return faixaSalarial;
	}

	private  Estabelecimento saveEstabelecimento(){
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		return estabelecimento;
	}
	
	private  Estabelecimento saveEstabelecimento(String nome, Empresa empresa){
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(nome, empresa);
		estabelecimentoDao.save(estabelecimento);
		return estabelecimento;
	}
	
	private AreaOrganizacional saveAreaOrganizacional(){
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		return areaOrganizacional;
	}
	
	private Cargo saveCargo(){
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		return cargo;
	}
	
	private void saveHistoricoColaborador(Colaborador colaborador, FaixaSalarial faixaSalarial, Date dataHistorico, Integer status){
		HistoricoColaborador historicoColaborador = inicializaHistorico(dataHistorico, colaborador, faixaSalarial);
		historicoColaborador.setStatus(status);
		historicoColaboradorDao.save(historicoColaborador);
	}
	
	private void saveHistoricoColaborador(Colaborador colaborador, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, FaixaSalarial faixaSalarial, Date dataHistorico, Integer status){
		HistoricoColaborador historicoColaborador = inicializaHistorico(dataHistorico, colaborador, faixaSalarial);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setStatus(status);
		historicoColaboradorDao.save(historicoColaborador);
	}

	private Colaborador saveColaborador(String nome, Empresa empresa, Date dataAdmissao, boolean desligado, Date dataDesligamento, boolean naoIntegraAc) {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setEmpresa(empresa);
		colaborador.setNaoIntegraAc(naoIntegraAc);
		colaborador.setDesligado(desligado);
		colaborador.setDataDesligamento(dataDesligamento);
		colaboradorDao.save(colaborador);
		return colaborador;
	}
	
	private Colaborador saveColaborador(Empresa empresa, String nome, Date dataAdmissao, boolean desligado, Date dataDesligamento, boolean naoIntegraAc) {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nome);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setEmpresa(empresa);
		colaborador.setNaoIntegraAc(naoIntegraAc);
		colaborador.setDesligado(desligado);
		colaborador.setDataDesligamento(dataDesligamento);
		colaboradorDao.save(colaborador);
		return colaborador;
	}
	
	private Colaborador saveColaborador(Empresa empresa, String nome, String matricula, String cpf, Date admissao){
		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setNome(nome);
		colaborador.setMatricula(matricula);
		colaborador.setDataAdmissao(admissao);
		colaborador.setPessoalCpf(cpf);
		colaboradorDao.save(colaborador);
		return colaborador;
	}
	
	private Ocorrencia saveOcorrencia(String descricao){
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setDescricao(descricao);
		ocorrenciaDao.save(ocorrencia);
		return ocorrencia;
	}
	
	private ColaboradorOcorrencia saveColaboradorOcorrencia(Colaborador colaborador, Ocorrencia ocorrencia, Date dataInicio, Date dataFim){
		ColaboradorOcorrencia colaboradorOcorrencia = setColaboradorOcorrencia(colaborador, ocorrencia, dataInicio, dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);
		return colaboradorOcorrencia;
	}
	
	private ColaboradorOcorrencia saveColaboradorOcorrencia(Colaborador colaborador, Ocorrencia ocorrencia, Providencia providencia, Date dataInicio, Date dataFim){
		ColaboradorOcorrencia colaboradorOcorrencia = setColaboradorOcorrencia(colaborador, ocorrencia, dataInicio, dataFim);
		colaboradorOcorrencia.setProvidencia(providencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);
		return colaboradorOcorrencia;
	}
	
	private ColaboradorOcorrencia setColaboradorOcorrencia(Colaborador colaborador, Ocorrencia ocorrencia, Date dataInicio, Date dataFim){
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrencia.setDataIni(dataInicio);
		colaboradorOcorrencia.setDataFim(dataFim);
		return colaboradorOcorrencia;
	}
	
	private Providencia saveProvidencia(String descricao){
		Providencia providencia = ProvidenciaFactory.getEntity();
		providencia.setDescricao(descricao);
		providenciaDao.save(providencia);
		return providencia;
	}	
	
	private Solicitacao saveSolicitacao(Empresa empresa, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date dataSolicitacao, Date dataEncerramento){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(empresa, estabelecimento, areaOrganizacional, dataSolicitacao, dataEncerramento);
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}

	private Solicitacao saveSolicitacao(MotivoSolicitacao motivo){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setMotivoSolicitacao(motivo);
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}

	private Colaborador saveColaboradorComHistorico(Empresa empresa, String nome, Date dataAdmissao, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Integer status, Candidato candidato, CandidatoSolicitacao candidatoSolicitacao){
		Colaborador colaborador = ColaboradorFactory.getEntity(nome);
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		saveHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, dataAdmissao, MotivoHistoricoColaborador.CONTRATADO, status, candidatoSolicitacao);
		return colaborador;
	}
	
	private Candidato saveCandidato(){
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		return candidato;
	}
	
	private CandidatoSolicitacao saveCandidatoSolicitacao(Candidato candidato, Solicitacao solicitacao){
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, false);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		return candidatoSolicitacao;
	}
	
	private HistoricoColaborador saveHistoricoColaborador(Colaborador colaborador, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date dataAdmissao, String motivo, Integer status, CandidatoSolicitacao candidatoSolicitacao){
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, dataAdmissao, estabelecimento, areaOrganizacional);
		historicoColaborador.setMotivo(motivo);
		historicoColaborador.setStatus(status);
		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);
		return historicoColaborador;
	}
	
	@Test
	public void testFindByAdmitidos() {
		Empresa empresa = saveEmpresa();

		Colaborador colaborador1 = saveColaborador(empresa, "C1", "012345", "01234567890", DateUtil.criarDataMesAno(1, 2, 2013));
		colaboradorDao.save(colaborador1);
		Colaborador colaborador2 = saveColaborador(empresa, "C2", "012345", "01234567890", new Date());
		colaboradorDao.save(colaborador2);
		
		Collection<Colaborador> retorno = colaboradorDao.findByAdmitidos(new Date());
		assertTrue(retorno.size() >= 1);
	}
	
	@Test
	public void testFindDadosBasicosNotIds() {
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2017);
		Date data2 = DateUtil.criarDataMesAno(1, 2, 2017);
		
		Empresa empresa = saveEmpresa();

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Cargo cargo = CargoFactory.getEntity("Cargo");
		cargoDao.save(cargo);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity("I", cargo);
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity("II", cargo);
		faixaSalarialDao.save(faixa2);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		area2.setAreaMae(area1);
		areaOrganizacionalDao.save(area2);
		
		AreaOrganizacional area3 = AreaOrganizacionalFactory.getEntity();
		area3.setAreaMae(area1);
		areaOrganizacionalDao.save(area3);
		
		Colaborador colaborador1 = saveColaborador(empresa, "C1", "012345", "01234567890", data1);
		colaboradorDao.save(colaborador1);
		saveHistoricoColaborador(colaborador1, estabelecimento1, area2, faixa1, data1, StatusRetornoAC.CONFIRMADO);
		
		Colaborador colaborador2 = saveColaborador(empresa, "C2", "012346", "01234567890", data2);
		colaboradorDao.save(colaborador2);
		saveHistoricoColaborador(colaborador2, estabelecimento2, area3, faixa2, data2, StatusRetornoAC.CONFIRMADO);
		
		Colaborador colaborador3 = saveColaborador(empresa, "C3", "012347", "01234567890", data2);
		colaboradorDao.save(colaborador3);
		saveHistoricoColaborador(colaborador3, estabelecimento2, area3, faixa2, data2, StatusRetornoAC.AGUARDANDO);
		
		Colaborador colaborador4 = saveColaborador(empresa, "C4", "012347", "01234567890", data1);
		colaboradorDao.save(colaborador4);
		saveHistoricoColaborador(colaborador4, estabelecimento1, area2, faixa2, data1, StatusRetornoAC.CONFIRMADO);
		
		Set<Long> colabsIds = new HashSet<Long>();
		colabsIds.add(colaborador1.getId());
		
		Collection<Colaborador> retorno = colaboradorDao.findDadosBasicosNotIds(colabsIds, null, new Long[]{area3.getId()}, null, SituacaoColaborador.ATIVO, empresa.getId());
		assertEquals(1, retorno.size());
		assertEquals(colaborador2.getNome(), ((Colaborador)retorno.toArray()[0]).getNome());
		
		retorno = colaboradorDao.findDadosBasicosNotIds(colabsIds, new Long[]{colaborador4.getId()}, new Long[]{}, new Long[]{estabelecimento1.getId()}, SituacaoColaborador.TODOS, empresa.getId());
		assertEquals(1, retorno.size());
		assertEquals(colaborador4.getNome(), ((Colaborador)retorno.toArray()[0]).getNome());
	}
	
	@Test
	public void testUpdateVinculo() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L, "Empresa", "9999", "001");
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("999999", 2L);
		colaborador.setVinculo(Vinculo.TEMPORARIO);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		assertEquals(Vinculo.TEMPORARIO, colaboradorDao.findColaboradorById(colaborador.getId()).getVinculo());
		
		colaboradorDao.updateVinculo(Vinculo.EMPREGO, colaborador.getCodigoAC(), empresa.getCodigoAC(), empresa.getGrupoAC());
		
		assertEquals(Vinculo.EMPREGO, colaboradorDao.findColaboradorById(colaborador.getId()).getVinculo());
	}
	
	@Test
	public void testUpdateDddCelularAndUFHabilitacao() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L, "Empresa", "9999", "001");
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("999999", 2L);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		colaboradorDao.updateDddCelularAndUFHabilitacao(empresa.getId(), colaborador.getCodigoAC(), "85", "CE");
		Colaborador colaboradorDoBanco = colaboradorDao.findColaboradorById(colaborador.getId()); 
		
		assertEquals("85", colaboradorDoBanco.getContato().getDddCelular());
		assertEquals(new Long(1L), colaboradorDoBanco.getHabilitacao().getUfHab().getId());
	}
}