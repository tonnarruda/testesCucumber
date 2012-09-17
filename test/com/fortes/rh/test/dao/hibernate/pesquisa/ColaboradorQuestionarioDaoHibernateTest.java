package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaAvaliacaoTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.util.DateUtil;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class ColaboradorQuestionarioDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorQuestionario>
{
	private QuestionarioDao questionarioDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;
	private TurmaDao turmaDao;
	private ColaboradorRespostaDao colaboradorRespostaDao;
	private CandidatoDao candidatoDao;
	private AvaliacaoDao avaliacaoDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private SolicitacaoDao solicitacaoDao;
	private EstabelecimentoDao estabelecimentoDao;
	private PesquisaDao pesquisaDao;
	private UsuarioDao usuarioDao;
	private AvaliacaoTurmaDao avaliacaoTurmaDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private TurmaAvaliacaoTurmaDao turmaAvaliacaoTurmaDao;
	private ColaboradorPresencaDao colaboradorPresencaDao;

	public void setColaboradorRespostaDao(ColaboradorRespostaDao colaboradorRespostaDao)
	{
		this.colaboradorRespostaDao = colaboradorRespostaDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public ColaboradorQuestionario getEntity()
	{
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();

		colaboradorQuestionario.setId(null);
		colaboradorQuestionario.setColaborador(null);
		colaboradorQuestionario.setQuestionario(null);

		return colaboradorQuestionario;
	}

	public GenericDao<ColaboradorQuestionario> getGenericDao()
	{
		return colaboradorQuestionarioDao;
	}

	public void testFindByQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Collection<ColaboradorQuestionario> retorno = colaboradorQuestionarioDao.findByQuestionario(questionario.getId());

		assertEquals(1, retorno.size());
	}

	public void findByQuestionarioEmpresaRespondida() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Collection<ColaboradorQuestionario> retorno = colaboradorQuestionarioDao.findByQuestionarioEmpresaRespondida(questionario.getId(), true, empresa.getId());
		
		assertEquals(1, retorno.size());
	}
	
	public void testFindRespondidasBySolicitacao() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setNome("Maria da Silva");
		candidatoDao.save(candidato);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		ColaboradorQuestionario colaboradorQuestionario1 = new ColaboradorQuestionario();
		colaboradorQuestionario1.setRespondida(true);
		colaboradorQuestionario1.setCandidato(candidato);
		colaboradorQuestionario1.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = new ColaboradorQuestionario();
		colaboradorQuestionario2.setRespondida(false);
		colaboradorQuestionario2.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		Collection<Colaborador> retorno = colaboradorQuestionarioDao.findRespondidasBySolicitacao(solicitacao.getId());
		
		assertEquals(1, retorno.size());
		
		assertEquals(candidato.getNome(), ((Colaborador)retorno.toArray()[0]).getNome());
	}
	
	public void testFindByAvaliacaoRespondidas() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		ColaboradorQuestionario colaboradorQuestionario1 = new ColaboradorQuestionario();
		colaboradorQuestionario1.setRespondida(true);
		colaboradorQuestionario1.setCandidato(candidato);
		colaboradorQuestionario1.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = new ColaboradorQuestionario();
		colaboradorQuestionario2.setRespondida(false);
		colaboradorQuestionario2.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		Collection<ColaboradorQuestionario> retorno = colaboradorQuestionarioDao.findBySolicitacaoRespondidas(solicitacao.getId());
		
		assertEquals(1, retorno.size());
		
		assertEquals(candidato.getId(), ((ColaboradorQuestionario)retorno.toArray()[0]).getCandidato().getId());
	}

	public void testFindColaboradorHistoricoByQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

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
		historicoColaboradorAtual1.setColaborador(colaborador);
		historicoColaboradorAtual1.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorDao.save(historicoColaboradorAtual1);

		Collection<ColaboradorQuestionario> retorno = colaboradorQuestionarioDao.findColaboradorHistoricoByQuestionario(questionario.getId(), true, empresa.getId());

		assertEquals(1, retorno.size());
	}
	
	public void testFindByQuestionarioEmpresaRespondida() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabeleciemento =  EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabeleciemento);
		
		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(colaborador);
		historicoColaboradorAtual1.setEstabelecimento(estabeleciemento);
		
		historicoColaboradorDao.save(historicoColaboradorAtual1);
		
		Collection<ColaboradorQuestionario> retorno = colaboradorQuestionarioDao.findByQuestionarioEmpresaRespondida(questionario.getId(), true, empresa.getId());
		
		assertEquals(1, retorno.size());
	}

	public void testFindAvaliacaoExperienciaByColaborador() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacaoExperiencia);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		ColaboradorQuestionario colaboradorQuestionario1 = new ColaboradorQuestionario();
		colaboradorQuestionario1.setColaborador(colaborador);
		colaboradorQuestionario1.setAvaliacao(avaliacaoExperiencia);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		ColaboradorQuestionario colaboradorQuestionario2 = new ColaboradorQuestionario();
		colaboradorQuestionario2.setColaborador(colaborador);
		colaboradorQuestionario2.setAvaliacao(null);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);

		Collection<ColaboradorQuestionario> retorno = colaboradorQuestionarioDao.findAvaliacaoByColaborador(colaborador.getId(), false);

		assertEquals(1, retorno.size());
	}

	public void testFindByQuestionarioColaborador() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario retorno = colaboradorQuestionarioDao.findByQuestionario(questionario.getId(), colaborador.getId(), null);

		assertEquals(colaboradorQuestionario, retorno);
	}

	public void testFindByQuestionarioCandidato() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setCandidato(candidato);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario retorno = colaboradorQuestionarioDao.findByQuestionarioCandidato(questionario.getId(), candidato.getId());

		assertEquals(colaboradorQuestionario, retorno);
	}

	public void testFindColaboradorComEntrevistaDeDesligamento()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setTipo(TipoQuestionario.getENTREVISTA());
		questionario = questionarioDao.save(questionario);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario retorno = colaboradorQuestionarioDao.findColaboradorComEntrevistaDeDesligamento(colaborador.getId());

		assertEquals(colaboradorQuestionario.getColaborador().getId(), retorno.getColaborador().getId());
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void testGets() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setQuestionario(questionario);

		assertEquals(questionario, colaboradorQuestionario.getQuestionario());

		colaboradorQuestionario.setProjectionColaboradorNomeComercial("teste");
		assertEquals("teste", colaboradorQuestionario.getColaborador().getNomeComercial());
	}

	public void testRemoveByColaboradorETurma()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setTurma(turma);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorRespostaDao.save(colaboradorResposta);

		colaboradorQuestionarioDao.removeByColaboradorETurma(colaborador.getId(), turma.getId());

		colaboradorQuestionario = getGenericDao().findById(colaboradorQuestionario.getId(), null);
		assertNull(colaboradorQuestionario);
	}
	
	public void testFindRespondidasByTurma()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		ColaboradorQuestionario colaboradorQuestionarioRespondida = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioRespondida.setColaborador(colaborador);
		colaboradorQuestionarioRespondida.setTurma(turma);
		colaboradorQuestionarioRespondida.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioRespondida);
		
		ColaboradorQuestionario colaboradorQuestionarioNaoRespondida = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioNaoRespondida.setColaborador(colaborador);
		colaboradorQuestionarioNaoRespondida.setTurma(turma);
		colaboradorQuestionarioNaoRespondida.setRespondida(false);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioNaoRespondida);

		Collection<ColaboradorQuestionario> respondidasPorTurma = colaboradorQuestionarioDao.findRespondidasByColaboradorETurma(null, turma.getId(), empresa.getId());

		assertEquals(1, respondidasPorTurma.size());
	}

	public void testFindFichaMedica()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setTipo(TipoQuestionario.getFICHAMEDICA());
		questionarioDao.save(questionario);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Collection<ColaboradorQuestionario> colaboradors = colaboradorQuestionarioDao.findFichasMedicas('C', null, null, null, null, null);
		assertTrue(colaboradors.size() > 0);
	}

	public void testFindFichaMedicaSemVinculo()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setTipo(TipoQuestionario.getFICHAMEDICA());
		questionarioDao.save(questionario);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Collection<ColaboradorQuestionario> colaboradors = colaboradorQuestionarioDao.findFichasMedicas();
		assertTrue(colaboradors.size() > 0);
	}

	public void testFindByIdProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);

		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setExibeResultadoAutoavaliacao(true);
		avaliacaoDao.save(avaliacao);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario tmp = colaboradorQuestionarioDao.findByIdProjection(colaboradorQuestionario.getId());
		assertEquals(colaboradorQuestionario, tmp);
		assertNull(tmp.getCandidato().getId());
	}

	public void testFindByIdColaboradorCandidato()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("teste");
		colaboradorDao.save(colaborador);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario tmp = colaboradorQuestionarioDao.findByIdColaboradorCandidato(colaboradorQuestionario.getId());
		assertEquals(colaboradorQuestionario, tmp);
		assertNull(tmp.getCandidato().getId());
		assertEquals("teste", tmp.getColaborador().getNome());
	}

	public void testFindByColaboradorAndAvaliacaoDesempenho()
	{
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador ");
		colaboradorDao.save(avaliador);
		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		avaliador2.setNome("Avaliador2 ");
		colaboradorDao.save(avaliador2);

		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliador(avaliador2);
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);

		assertEquals(2, colaboradorQuestionarioDao.findByColaboradorAndAvaliacaoDesempenho(avaliado.getId(), avaliacaoDesempenho.getId(), true).size());
	}

	public void testGetCountParticipantesAssociados()
	{
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador ");
		colaboradorDao.save(avaliador);
		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		avaliador2.setNome("Avaliador2 ");
		colaboradorDao.save(avaliador2);

		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliador(avaliador2);
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);

		assertEquals(Integer.valueOf(2), colaboradorQuestionarioDao.getCountParticipantesAssociados(avaliacaoDesempenho.getId()));

		// Sem associados
		colaboradorQuestionario.setColaborador(null);
		colaboradorQuestionarioDao.update(colaboradorQuestionario);
		colaboradorQuestionario2.setColaborador(null);
		colaboradorQuestionarioDao.update(colaboradorQuestionario2);

		assertEquals(Integer.valueOf(0), colaboradorQuestionarioDao.getCountParticipantesAssociados(avaliacaoDesempenho.getId()));
	}

	public void testFindByAvaliacaoDesempenho()
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionarioFora = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioDao.save(colaboradorQuestionarioFora);

		assertEquals(1, colaboradorQuestionarioDao.findByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), null).size());
	}

	

	public void testFindRespondidasByAvaliacaoDesempenho()
	{
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador");
		colaboradorDao.save(avaliador);

		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(avaliador);
		historicoColaboradorDao.save(historicoColaboradorAtual1);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionarioRespondida = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioRespondida.setRespondida(true);
		colaboradorQuestionarioRespondida.setAvaliador(avaliador);
		colaboradorQuestionarioRespondida.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioRespondida);

		assertEquals(1, colaboradorQuestionarioDao.findRespondidasByAvaliacaoDesempenho(new Long[]{avaliador.getId()}, avaliacaoDesempenho.getId(), false).size());
	}
	
	public void testRemoveParticipantesSemAssociacao()
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliado);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		colaboradorQuestionarioDao.removeParticipantesSemAssociacao(avaliacaoDesempenho.getId());

		colaboradorQuestionario = getGenericDao().findById(colaboradorQuestionario.getId(), null);
		assertNull(colaboradorQuestionario);
	}
	
	public void testRemoveAssociadosSemResposta()
	{
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador");
		colaboradorDao.save(avaliador);
		
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2Fora = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2Fora.setAvaliador(avaliador);
		colaboradorQuestionario2Fora.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2Fora);

		ColaboradorQuestionario colaboradorQuestionarioRespondidaFora = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioRespondidaFora.setRespondida(true);
		colaboradorQuestionarioRespondidaFora.setAvaliador(avaliador);
		colaboradorQuestionarioRespondidaFora.setColaborador(avaliado);
		colaboradorQuestionarioRespondidaFora.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioRespondidaFora);
		
		colaboradorQuestionarioDao.removeAssociadosSemResposta(avaliacaoDesempenho.getId());
		
		assertEquals(2, colaboradorQuestionarioDao.findByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), null).size());
	}
	
	public void testRemoveByParticipante()
	{
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador");
		colaboradorDao.save(avaliador);
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		colaboradorQuestionarioDao.removeByParticipante(avaliacaoDesempenho.getId(), new Long[]{avaliado.getId()}, true);
	}
	
	public void testFindAvaliadosByAvaliador()
	{
		Date anoAnterior = DateUtil.incrementaAno(new Date(), -1);
		Date anoPosterior = DateUtil.incrementaAno(new Date(), 1);
		
		Colaborador avaliador = ColaboradorFactory.getEntity();
    	avaliador.setNome("Avaliador ");
    	colaboradorDao.save(avaliador);
    	
    	Colaborador avaliado = ColaboradorFactory.getEntity();
    	avaliado.setNome("Avaliado 1");
    	colaboradorDao.save(avaliado);
    	
    	Colaborador avaliado2 = ColaboradorFactory.getEntity();
    	avaliado2.setNome("Avaliado 2");
    	colaboradorDao.save(avaliado2);
    	
    	Colaborador avaliado3 = ColaboradorFactory.getEntity();
    	avaliado3.setNome("Avaliado 3");
    	colaboradorDao.save(avaliado3);
    	
    	AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
    	avaliacaoDesempenho.setLiberada(true);
    	avaliacaoDesempenho.setInicio(anoAnterior);
    	avaliacaoDesempenho.setFim(anoPosterior);
    	avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario.setAvaliador(avaliador);
    	colaboradorQuestionario.setColaborador(avaliado);
    	colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	colaboradorQuestionarioDao.save(colaboradorQuestionario);
    	
    	ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario2.setAvaliador(avaliador);
    	colaboradorQuestionario2.setColaborador(avaliado2);
    	colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	colaboradorQuestionarioDao.save(colaboradorQuestionario2);
    	
    	ColaboradorQuestionario colaboradorQuestionario3Respondida = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario3Respondida.setAvaliador(avaliador);
    	colaboradorQuestionario3Respondida.setColaborador(avaliado3);
    	colaboradorQuestionario3Respondida.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	colaboradorQuestionario3Respondida.setRespondida(true);
    	colaboradorQuestionarioDao.save(colaboradorQuestionario3Respondida);
    	
    	// todos os avaliados do avaliador.
    	assertEquals(3, colaboradorQuestionarioDao.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId(), null, true).size());
    	
    	// apenas não respondidas
    	assertEquals(2, colaboradorQuestionarioDao.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId(), false, true).size());
    	
    	// apenas respondidas
    	assertEquals(1, colaboradorQuestionarioDao.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId(), true, true).size());
	}
	
	public void testExcluirColaboradorQuestionarioByAvaliacaoDesempenho()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		assertEquals(2, colaboradorQuestionarioDao.findByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), null).size());
		
		colaboradorQuestionarioDao.excluirColaboradorQuestionarioByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		
		assertEquals(0, colaboradorQuestionarioDao.findByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), null).size());
	}
	
	public void testFindIdsExibidosNaPerformanceProfissional()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setTitulo("Questionario Dificil");
		questionario.setDataInicio(DateUtil.criarDataMesAno(01, 01, 2000));
		questionario.setDataFim(DateUtil.criarDataMesAno(01, 01, 2008));
		questionarioDao.save(questionario);
		
		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setExibirPerformanceProfissional(true);
		pesquisa.setQuestionario(questionario);
		pesquisaDao.save(pesquisa);
 		
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setNome("Avaliado");
    	colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Collection<ColaboradorQuestionario> pesquisas = colaboradorQuestionarioDao.findByColaborador(colaborador.getId());
		assertEquals(1, pesquisas.size());
	}
	
	public void testFindByColaboradorAndAvaliacaoDesempenhos()
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);
		
		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador");
		colaboradorDao.save(avaliador);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setPerformance(25.0);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliador(avaliador);
		colaboradorQuestionario2.setPerformance(35.0);
		colaboradorQuestionario2.setRespondida(true);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setColaborador(avaliado);
		colaboradorQuestionario3.setAvaliador(avaliado);
		colaboradorQuestionario3.setPerformance(60.0);
		colaboradorQuestionario3.setRespondida(true);
		colaboradorQuestionario3.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		boolean desconsiderarAutoAvaliacao = false;
		
		assertEquals("Com auto avaliação", 40.0, colaboradorQuestionarioDao.getMediaPeformance(avaliado.getId(), avaliacaoDesempenho.getId(), desconsiderarAutoAvaliacao));
		assertEquals("Sem auto avaliação", 30.0, colaboradorQuestionarioDao.getMediaPeformance(avaliado.getId(), avaliacaoDesempenho.getId(), !desconsiderarAutoAvaliacao));
	}
	
	public void testGetQtdavaliadores()
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Avaliador");
		colaboradorDao.save(avaliador);
		
		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		avaliador2.setNome("Avaliador");
		colaboradorDao.save(avaliador2);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliador(avaliador);
		colaboradorQuestionario2.setRespondida(true);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setColaborador(avaliado);
		colaboradorQuestionario3.setAvaliador(avaliador2);
		colaboradorQuestionario3.setRespondida(true);
		colaboradorQuestionario3.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		assertEquals(new Integer(2), colaboradorQuestionarioDao.getQtdavaliadores(avaliacaoDesempenho.getId()));
	}
	
	public void testFindByColaboradorAvaliacao()
	{
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("Joao");
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("Maria");
		colaboradorDao.save(maria);

		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(joao);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setColaborador(joao);
		colaboradorQuestionario2.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setColaborador(maria);
		colaboradorQuestionario3.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		assertEquals(colaboradorQuestionario2, colaboradorQuestionarioDao.findByColaboradorAvaliacao(joao.getId(), avaliacao.getId()));
		assertEquals(colaboradorQuestionario3, colaboradorQuestionarioDao.findByColaboradorAvaliacao(maria.getId(), avaliacao.getId()));
	}
	
	public void testFindQuestionarioByTurmaLiberadaPorUsuario()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario.setLiberado(true);
		questionarioDao.save(questionario);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity();
		avaliacaoTurma.setQuestionario(questionario);
		avaliacaoTurmaDao.save(avaliacaoTurma);

		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);

		TurmaAvaliacaoTurma turmaAvaliacaoTurma = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma.setTurma(turma);
		turmaAvaliacaoTurma.setAvaliacaoTurma(avaliacaoTurma);
		turmaAvaliacaoTurma.setLiberada(true);
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setPresenca(true);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		ColaboradorPresenca colaboradorPresenca2 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca2.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca2.setPresenca(true);
		colaboradorPresencaDao.save(colaboradorPresenca2);
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = colaboradorQuestionarioDao.findQuestionarioByTurmaLiberadaPorUsuario(usuario.getId());
		
		ColaboradorQuestionario colaboradorQuestionario = (ColaboradorQuestionario)colaboradorQuestionarios.toArray()[0];
		
		assertEquals(questionario.getId(), colaboradorQuestionario.getQuestionario().getId());
		assertEquals(colaborador.getId(), colaboradorQuestionario.getColaborador().getId());
		assertEquals(turma.getId(), colaboradorQuestionario.getTurma().getId());
		assertEquals(Boolean.FALSE, colaboradorQuestionario.getRespondida());
	}

	public void testremoveByCandidato() {
		Candidato joao = CandidatoFactory.getCandidato();
		joao.setNome("Joao");
		candidatoDao.save(joao);
		
		Candidato maria = CandidatoFactory.getCandidato();
		maria.setNome("Maria");
		candidatoDao.save(maria);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setCandidato(joao);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setCandidato(maria);
		colaboradorQuestionario2.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorRespostaDao.save(colaboradorResposta1);
		
		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorRespostaDao.save(colaboradorResposta2);
		
		assertEquals(colaboradorQuestionario.getId(), ( (ColaboradorQuestionario) colaboradorQuestionarioDao.findByIdColaboradorCandidato(colaboradorQuestionario.getId())).getId());
		assertEquals(colaboradorQuestionario2.getId(), ( (ColaboradorQuestionario) colaboradorQuestionarioDao.findByIdColaboradorCandidato(colaboradorQuestionario2.getId())).getId());
		
		colaboradorQuestionarioDao.removeByCandidato(joao.getId());
		
		assertNull(colaboradorQuestionarioDao.findByIdColaboradorCandidato(colaboradorQuestionario.getId()));
		assertEquals(colaboradorQuestionario2.getId(), ( (ColaboradorQuestionario) colaboradorQuestionarioDao.findByIdColaboradorCandidato(colaboradorQuestionario2.getId())).getId());
		
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao)
	{
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao)
	{
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setPesquisaDao(PesquisaDao pesquisaDao) {
		this.pesquisaDao = pesquisaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setAvaliacaoTurmaDao(AvaliacaoTurmaDao avaliacaoTurmaDao) {
		this.avaliacaoTurmaDao = avaliacaoTurmaDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao) {
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setTurmaAvaliacaoTurmaDao(TurmaAvaliacaoTurmaDao turmaAvaliacaoTurmaDao) {
		this.turmaAvaliacaoTurmaDao = turmaAvaliacaoTurmaDao;
	}

	public void setColaboradorPresencaDao(
			ColaboradorPresencaDao colaboradorPresencaDao) {
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}
}