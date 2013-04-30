package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.pesquisa.AspectoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.RespostaQuestionarioVO;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorRespostaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorResposta>
{
	private ColaboradorRespostaDao colaboradorRespostaDao;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private PesquisaDao pesquisaDao;
	private QuestionarioDao questionarioDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private PerguntaDao perguntaDao;
	private RespostaDao respostaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private AspectoDao aspectoDao;
	private TurmaDao turmaDao;
	private CandidatoDao candidatoDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private AvaliacaoDao avaliacaoDao;

	public ColaboradorResposta getEntity()
	{
		ColaboradorResposta colaboradorResposta = new ColaboradorResposta();

		colaboradorResposta.setId(null);
		colaboradorResposta.setValor(0);
		colaboradorResposta.setComentario("teste");
		colaboradorResposta.setPergunta(null);
		colaboradorResposta.setResposta(null);
		colaboradorResposta.setColaboradorQuestionario(null);
		colaboradorResposta.setAreaOrganizacional(null);

		return colaboradorResposta;
	}

	public void testCountRespostas()
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setTexto("Voce foi criado com a avo?");
		pergunta.setQuestionario(questionario);
		pergunta = perguntaDao.save(pergunta);

		Resposta respostaA = RespostaFactory.getEntity();
		respostaA.setTexto("Sim");
		respostaA.setOrdem(1);
		respostaA.setPergunta(pergunta);
		respostaA = respostaDao.save(respostaA);

		Resposta respostaB = RespostaFactory.getEntity();
		respostaB.setTexto("Nao");
		respostaB.setOrdem(2);
		respostaB.setPergunta(pergunta);
		respostaB = respostaDao.save(respostaB);

		montaColaboradorResposta(questionario, pergunta, respostaA, null, areaOrganizacional, null, turma);
		montaColaboradorResposta(questionario, pergunta, respostaA, null, areaOrganizacional, null, turma);
		montaColaboradorResposta(questionario, pergunta, respostaB, null, areaOrganizacional, null, turma);

		List<Object[]> retornos = colaboradorRespostaDao.countRespostas(new Long[]{pergunta.getId()}, null, new Long[]{areaOrganizacional.getId()}, null, null, null, turma.getId(), null);

		assertEquals("Total de registros", 2, retornos.size());

		Object[] qtdRespostaA = (Object[]) retornos.get(0);
		//Array{ordem da pergunta, quantidade}
		assertEquals("I - Ordem pergunta", 1, qtdRespostaA[0]);
		assertEquals("I - Quantidade", 2, qtdRespostaA[1]);

		Object[] qtdRespostaB = (Object[]) retornos.get(1);
		assertEquals("II - Ordem pergunta", 2, qtdRespostaB[0]);
		assertEquals("II - Quantidade", 1, qtdRespostaB[1]);
	}
	
	public void testCountRespostasComAvaliacaoDesempenho()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		colaboradorRespostaDao.countRespostas(colaborador.getId(), avaliacaoDesempenho.getId());
	}
	
	public void testCountRespostasMultiplaComAvaliacaoDesempenho()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
		pergunta.setTexto("Quais características?");
		pergunta = perguntaDao.save(pergunta);
		
		Resposta respostaA = RespostaFactory.getEntity();
		respostaA.setTexto("A");
		respostaA.setOrdem(1);
		respostaA.setPergunta(pergunta);
		respostaA = respostaDao.save(respostaA);
		
		Resposta respostaB = RespostaFactory.getEntity();
		respostaB.setTexto("B");
		respostaB.setOrdem(2);
		respostaB.setPergunta(pergunta);
		respostaB = respostaDao.save(respostaB);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario.setRespondidaEm(new Date());
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta.setPergunta(pergunta);
		colaboradorResposta.setResposta(respostaA);
		colaboradorResposta = colaboradorRespostaDao.save(colaboradorResposta);
		
		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta2.setPergunta(pergunta);
		colaboradorResposta2.setResposta(respostaB);
		colaboradorResposta2 = colaboradorRespostaDao.save(colaboradorResposta2);
		
		ColaboradorResposta colaboradorResposta3 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta3.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta3.setPergunta(pergunta);
		colaboradorResposta3.setResposta(respostaB);
		colaboradorResposta3 = colaboradorRespostaDao.save(colaboradorResposta3);
		
		List<Object[]> retornos = colaboradorRespostaDao.countRespostasMultiplas(colaborador.getId(), avaliacaoDesempenho.getId());
		
		Object[] qtdRespostaA = (Object[]) retornos.get(0);
		//Array{ordem da pergunta, quantidade}
		assertEquals("I - Ordem pergunta", 1, qtdRespostaA[0]);
		assertEquals("I - Quantidade", 1, qtdRespostaA[1]);
		
		Object[] qtdRespostaB = (Object[]) retornos.get(1);
		assertEquals("II - Ordem pergunta", 2, qtdRespostaB[0]);
		assertEquals("II - Quantidade", 2, qtdRespostaB[1]);
	}
	
	public void testCountRespostasMultipla()
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);
		
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
		pergunta.setTexto("Voce foi criado com a avo?");
		pergunta.setQuestionario(questionario);
		pergunta = perguntaDao.save(pergunta);
		
		Resposta respostaA = RespostaFactory.getEntity();
		respostaA.setTexto("Sim");
		respostaA.setOrdem(1);
		respostaA.setPergunta(pergunta);
		respostaA = respostaDao.save(respostaA);
		
		Resposta respostaB = RespostaFactory.getEntity();
		respostaB.setTexto("Nao");
		respostaB.setOrdem(2);
		respostaB.setPergunta(pergunta);
		respostaB = respostaDao.save(respostaB);
		
		montaColaboradorResposta(questionario, pergunta, respostaA, null, areaOrganizacional, null, turma);
		montaColaboradorResposta(questionario, pergunta, respostaA, null, areaOrganizacional, null, turma);
		montaColaboradorResposta(questionario, pergunta, respostaB, null, areaOrganizacional, null, turma);
		
		List<Object[]> retornos = colaboradorRespostaDao.countRespostasMultiplas(new Long[]{pergunta.getId()}, null, new Long[]{areaOrganizacional.getId()}, null, null, null, turma.getId(), null);
		
		assertEquals("Total de registros", 2, retornos.size());
		
		Object[] qtdRespostaA = (Object[]) retornos.get(0);
		//Array{ordem da pergunta, quantidade}
		assertEquals("I - Ordem pergunta", 1, qtdRespostaA[0]);
		assertEquals("I - Quantidade", 2, qtdRespostaA[1]);
		
		Object[] qtdRespostaB = (Object[]) retornos.get(1);
		assertEquals("II - Ordem pergunta", 2, qtdRespostaB[0]);
		assertEquals("II - Quantidade", 1, qtdRespostaB[1]);
	}
	
	public void testCountRespostasRespondidaEm()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);
		
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setTexto("Voce foi criado com a avo?");
		pergunta.setQuestionario(questionario);
		pergunta.setTipo(TipoPergunta.OBJETIVA);
		pergunta = perguntaDao.save(pergunta);
		
		Resposta respostaA = RespostaFactory.getEntity();
		respostaA.setTexto("Sim");
		respostaA.setOrdem(1);
		respostaA.setPergunta(pergunta);
		respostaA = respostaDao.save(respostaA);
		
		Resposta respostaB = RespostaFactory.getEntity();
		respostaB.setTexto("Nao");
		respostaB.setOrdem(2);
		respostaB.setPergunta(pergunta);
		respostaB = respostaDao.save(respostaB);
		
		montaColaboradorResposta(questionario, pergunta, respostaA, null, areaOrganizacional, DateUtil.criarAnoMesDia(2008, 10, 10), null);
		montaColaboradorResposta(questionario, pergunta, respostaA, null, areaOrganizacional, DateUtil.criarAnoMesDia(2008, 11, 10), null);
		montaColaboradorResposta(questionario, pergunta, respostaB, null, areaOrganizacional, DateUtil.criarAnoMesDia(2008, 11, 10), null);
		
		List<Object[]> retornos = colaboradorRespostaDao.countRespostas(new Long[]{pergunta.getId()}, null, new Long[]{areaOrganizacional.getId()}, null, DateUtil.criarAnoMesDia(2008, 11, 10), DateUtil.criarAnoMesDia(2008, 11, 10), null, null);
		
		assertEquals("Total de registros", 2, retornos.size());
		
		Object[] qtdRespostaA = (Object[]) retornos.get(0);
		//Array{ordem da pergunta, quantidade}
		assertEquals("I - Ordem pergunta", 1, qtdRespostaA[0]);
		assertEquals("I - Quantidade", 1, qtdRespostaA[1]);
		assertEquals("I - Total de Respostas", 2, qtdRespostaA[4]);
		
		Object[] qtdRespostaB = (Object[]) retornos.get(1);
		assertEquals("II - Ordem pergunta", 2, qtdRespostaB[0]);
		assertEquals("II - Quantidade", 1, qtdRespostaB[1]);
	}

	public void testFindInPerguntaIds()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTexto("Voce foi criado com a avo?");
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTexto("Qual a nota do seu trabalho");
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Date data = new Date();
		montaColaboradorResposta(questionario, pergunta1, null, null, areaOrganizacional, data, null);
		montaColaboradorResposta(questionario, pergunta2, null, null, areaOrganizacional, data, null);

		Long[] perguntasIds = new Long[]{pergunta1.getId(), pergunta2.getId()};
		Long[] areasIds = new Long[]{areaOrganizacional.getId()};

		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findInPerguntaIds(perguntasIds, null, areasIds, null, null, data, null, questionario, null);
		assertEquals(2, colaboradorRespostas.size());
	}
	
	public void testFindInPerguntaIdsRespondidaEm()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTexto("Voce foi criado com a avo?");
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);
		
		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTexto("Qual a nota do seu trabalho");
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);
		
		montaColaboradorResposta(questionario, pergunta1, null, null, areaOrganizacional, DateUtil.criarAnoMesDia(2008, 9, 10), null);
		montaColaboradorResposta(questionario, pergunta2, null, null, areaOrganizacional, DateUtil.criarAnoMesDia(2008, 11, 10), null);
		
		Long[] perguntasIds = new Long[]{pergunta1.getId(), pergunta2.getId()};
		Long[] areasIds = new Long[]{areaOrganizacional.getId()};
		
		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findInPerguntaIds(perguntasIds, null, areasIds, null, DateUtil.criarAnoMesDia(2008, 10, 10), null, null, questionario, null);
		assertEquals(1, colaboradorRespostas.size());
	}
	
	public void testFindInPerguntaIdsEntrevista()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setTipo(TipoQuestionario.ENTREVISTA);
		questionarioDao.save(questionario);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTexto("Voce foi criado com a avo?");
		pergunta1.setQuestionario(questionario);
		perguntaDao.save(pergunta1);
		
		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTexto("Qual a nota do seu trabalho");
		pergunta2.setQuestionario(questionario);
		perguntaDao.save(pergunta2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataDesligamento(DateUtil.criarAnoMesDia(2008, 10, 11));
		colaboradorDao.save(colaborador);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(1, 12, 2012));
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setRespondidaEm(DateUtil.criarAnoMesDia(2009, 9, 10));
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta.setAreaOrganizacional(areaOrganizacional);
		colaboradorRespostaDao.save(colaboradorResposta);
		
		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findInPerguntaIds(null, null, null, null, DateUtil.criarAnoMesDia(2008, 10, 10), DateUtil.criarAnoMesDia(2008, 10, 12), null, questionario, null);
		assertEquals(1, colaboradorRespostas.size());
	}
	
	public void testFindByQuestionarioColaborador()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta = perguntaDao.save(pergunta);
		
		Resposta resposta = RespostaFactory.getEntity();
		resposta = respostaDao.save(resposta);
		
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setComentario("teste");
		colaboradorResposta.setValor(1);
		colaboradorResposta.setResposta(resposta);
		colaboradorResposta.setPergunta(pergunta);
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta = colaboradorRespostaDao.save(colaboradorResposta);
		
		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findByQuestionarioColaborador(questionario.getId(), colaborador.getId(), null);
		assertEquals(1, colaboradorRespostas.size());
	}
	
	public void testFindByColaboradorQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta = perguntaDao.save(pergunta);
		
		Resposta resposta = RespostaFactory.getEntity();
		resposta = respostaDao.save(resposta);
		
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setComentario("teste");
		colaboradorResposta.setValor(1);
		colaboradorResposta.setResposta(resposta);
		colaboradorResposta.setPergunta(pergunta);
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta = colaboradorRespostaDao.save(colaboradorResposta);
		
		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		assertEquals(1, colaboradorRespostas.size());
	}
	
	public void testFindByQuestionarioCandidato()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setCandidato(candidato);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta = perguntaDao.save(pergunta);
		
		Resposta resposta = RespostaFactory.getEntity();
		resposta = respostaDao.save(resposta);
		
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setComentario("teste");
		colaboradorResposta.setValor(1);
		colaboradorResposta.setResposta(resposta);
		colaboradorResposta.setPergunta(pergunta);
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta = colaboradorRespostaDao.save(colaboradorResposta);
		
		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findByQuestionarioCandidato(questionario.getId(), candidato.getId());
		assertEquals(1, colaboradorRespostas.size());
	}
	
	public void testRemoveByColaboradorQuestionario()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta = colaboradorRespostaDao.save(colaboradorResposta);

		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta2 = colaboradorRespostaDao.save(colaboradorResposta2);
		
		colaboradorRespostaDao.removeByColaboradorQuestionario(colaboradorQuestionario.getId());
		
		assertEquals(0, colaboradorRespostaDao.findRespostasColaborador(colaboradorQuestionario.getId(), null).size());
	}
	
	public void testRemoveByColaboradorQuestionarios()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta = colaboradorRespostaDao.save(colaboradorResposta);
		
		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta2 = colaboradorRespostaDao.save(colaboradorResposta2);
		
		colaboradorRespostaDao.removeByColaboradorQuestionario(new Long[]{colaboradorQuestionario.getId()});
		
		assertEquals(0, colaboradorRespostaDao.findRespostasColaborador(colaboradorQuestionario.getId(), null).size());
	}

	private ColaboradorResposta montaColaboradorResposta(Questionario questionario, Pergunta pergunta, Resposta resposta, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date respondidaEm, Turma turma)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(1, 12, 2012));
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setRespondidaEm(respondidaEm);
		colaboradorQuestionario.setTurma(turma);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta.setPergunta(pergunta);
		colaboradorResposta.setResposta(resposta);
		colaboradorResposta.setAreaOrganizacional(areaOrganizacional);
		colaboradorRespostaDao.save(colaboradorResposta);
		
		return colaboradorResposta;
	}

	public void testFindRespostasColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);

		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTexto("Voce foi criado com a avo?");
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTexto("Qual a nota do seu trabalho");
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		resposta1 = respostaDao.save(resposta1);

		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta2);
		resposta2 = respostaDao.save(resposta2);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta1.setAreaOrganizacional(areaOrganizacional);
		colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta1.setPergunta(pergunta1);
		colaboradorResposta1.setResposta(resposta1);
		colaboradorResposta1 = colaboradorRespostaDao.save(colaboradorResposta1);

		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setAreaOrganizacional(areaOrganizacional);
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta2.setPergunta(pergunta2);
		colaboradorResposta2.setResposta(resposta2);
		colaboradorResposta2 = colaboradorRespostaDao.save(colaboradorResposta2);

		boolean aplicarPorAspecto = true;

		Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaDao.findRespostasColaborador(colaboradorQuestionario.getId(), aplicarPorAspecto);
		assertEquals(2, colaboradorRespostas.size());

		aplicarPorAspecto = false;
		colaboradorRespostas = colaboradorRespostaDao.findRespostasColaborador(colaboradorQuestionario.getId(), aplicarPorAspecto);
		assertEquals(2, colaboradorRespostas.size());
	}
	
	public void testFindRespostasAvaliacaoDesempenho()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Aspecto aspecto = AspectoFactory.getEntity();
		aspectoDao.save(aspecto);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTexto("Voce foi criado com a avo?");
		pergunta1.setAvaliacao(avaliacao);
		perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTexto("Qual a nota do seu trabalho");
		pergunta2.setAvaliacao(avaliacao);
		perguntaDao.save(pergunta2);

		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		respostaDao.save(resposta1);

		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta2);
		respostaDao.save(resposta2);

		ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta1.setPergunta(pergunta1);
		colaboradorResposta1.setResposta(resposta1);
		colaboradorRespostaDao.save(colaboradorResposta1);

		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta2.setPergunta(pergunta2);
		colaboradorResposta2.setResposta(resposta2);
		colaboradorRespostaDao.save(colaboradorResposta2);

		colaboradorRespostaDao.findEntidadeComAtributosSimplesById(colaboradorResposta1.getId());
		
		Collection<RespostaQuestionarioVO> colaboradorRespostas = colaboradorRespostaDao.findRespostasAvaliacaoDesempenho(colaboradorQuestionario.getId());
		assertEquals(2, colaboradorRespostas.size());
	}
	
	public void testFindByAvaliadoAndAvaliacaoDesempenho()
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliado);
		
		Colaborador avaliador = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliador(avaliado);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTexto("Voce foi criado com a avo?");
		perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTexto("Qual a nota do seu trabalho");
		perguntaDao.save(pergunta2);

		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		respostaDao.save(resposta1);

		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta2);
		respostaDao.save(resposta2);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta1.setAreaOrganizacional(areaOrganizacional);
		colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta1.setPergunta(pergunta1);
		colaboradorResposta1.setResposta(resposta1);
		colaboradorResposta1 = colaboradorRespostaDao.save(colaboradorResposta1);

		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setAreaOrganizacional(areaOrganizacional);
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
		colaboradorResposta2.setPergunta(pergunta2);
		colaboradorResposta2.setResposta(resposta2);
		colaboradorResposta2 = colaboradorRespostaDao.save(colaboradorResposta2);
		
		ColaboradorResposta colaboradorResposta3 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta3.setAreaOrganizacional(areaOrganizacional);
		colaboradorResposta3.setColaboradorQuestionario(colaboradorQuestionario2);
		colaboradorResposta3.setPergunta(pergunta2);
		colaboradorResposta3.setResposta(resposta2);
		colaboradorResposta3 = colaboradorRespostaDao.save(colaboradorResposta3);
		
		boolean desconsiderarAutoAvaliacao = false;

		Collection<ColaboradorResposta> respostas1 = colaboradorRespostaDao.findByAvaliadoAndAvaliacaoDesempenho(avaliado.getId(), avaliacaoDesempenho.getId(), desconsiderarAutoAvaliacao);
		Collection<ColaboradorResposta> respostas2 = colaboradorRespostaDao.findByAvaliadoAndAvaliacaoDesempenho(avaliado.getId(), avaliacaoDesempenho.getId(), !desconsiderarAutoAvaliacao);

		assertEquals("Com auto avaliação", 3, respostas1.size());
		assertEquals("Sem auto avaliação", 2, respostas2.size());
	}

	public void testCountColaboradorAvaliacaoRespondida() 
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacao(avaliacao1);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacao(avaliacao1);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setAvaliacao(avaliacao2);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);

		ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario1);
		colaboradorResposta1 = colaboradorRespostaDao.save(colaboradorResposta1);

		assertEquals(new Integer(1), colaboradorRespostaDao.countColaboradorAvaliacaoRespondida(avaliacao1.getId()));
		assertEquals(new Integer(0), colaboradorRespostaDao.countColaboradorAvaliacaoRespondida(avaliacao2.getId()));
	}
	
	public GenericDao<ColaboradorResposta> getGenericDao()
	{
		return colaboradorRespostaDao;
	}

	public void setColaboradorRespostaDao(ColaboradorRespostaDao colaboradorRespostaDao)
	{
		this.colaboradorRespostaDao = colaboradorRespostaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setPerguntaDao(PerguntaDao perguntaDao)
	{
		this.perguntaDao = perguntaDao;
	}

	public void setRespostaDao(RespostaDao respostaDao)
	{
		this.respostaDao = respostaDao;
	}

	public void setPesquisaDao(PesquisaDao pesquisaDao)
	{
		this.pesquisaDao = pesquisaDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setAspectoDao(AspectoDao aspectoDao)
	{
		this.aspectoDao = aspectoDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao) {
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

}