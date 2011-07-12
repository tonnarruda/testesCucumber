package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoManagerImpl;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.util.DateUtil;

public class AvaliacaoManagerTest extends MockObjectTestCase
{
	private AvaliacaoManagerImpl avaliacaoManager = new AvaliacaoManagerImpl();
	private Mock avaliacaoDao;
	private Mock perguntaManager;
	
	private Mock periodoExperienciaManager;
	private Mock parametrosDoSistemaManager;
	private Mock colaboradorManager;
	private Mock usuarioEmpresaManager;
	private Mock usuarioMensagemManager;
	private Mock respostaManager;
	private Mock colaboradorRespostaManager;
	private Mock questionarioManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        avaliacaoDao = new Mock(AvaliacaoDao.class);
        avaliacaoManager.setDao((AvaliacaoDao) avaliacaoDao.proxy());
        perguntaManager = new Mock(PerguntaManager.class);
        avaliacaoManager.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
        
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        avaliacaoManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        periodoExperienciaManager = new Mock(PeriodoExperienciaManager.class);
        avaliacaoManager.setPeriodoExperienciaManager((PeriodoExperienciaManager) periodoExperienciaManager.proxy());
        colaboradorManager = mock(ColaboradorManager.class);
        avaliacaoManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
        avaliacaoManager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());
        usuarioMensagemManager = mock(UsuarioMensagemManager.class);
        avaliacaoManager.setUsuarioMensagemManager((UsuarioMensagemManager) usuarioMensagemManager.proxy());
        
        respostaManager = mock(RespostaManager.class);
        avaliacaoManager.setRespostaManager((RespostaManager) respostaManager.proxy());
        colaboradorRespostaManager = mock (ColaboradorRespostaManager.class);
        avaliacaoManager.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());
        questionarioManager= mock(QuestionarioManager.class);
        avaliacaoManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Avaliacao> avaliacaos = AvaliacaoFactory.getCollection(1L);

		avaliacaoDao.expects(once()).method("findAllSelect").with(eq(empresaId), eq(null), eq(TipoModeloAvaliacao.DESEMPENHO), ANYTHING).will(returnValue(avaliacaos));
		assertEquals(avaliacaos, avaliacaoManager.findAllSelect(empresaId, null, TipoModeloAvaliacao.DESEMPENHO, null));
	}
	
	public void testFindPeriodoExperienciaIsNull()
	{
		Long empresaId = 1L;
		
		Collection<Avaliacao> avaliacaos = AvaliacaoFactory.getCollection(1L);
		
		avaliacaoDao.expects(once()).method("findPeriodoExperienciaIsNull").with(eq(TipoModeloAvaliacao.DESEMPENHO), eq(empresaId)).will(returnValue(avaliacaos));
		assertEquals(avaliacaos, avaliacaoManager.findPeriodoExperienciaIsNull(TipoModeloAvaliacao.DESEMPENHO, empresaId));
	}
	
	public void testGetQuestionarioRelatorio()
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setAvaliacao(avaliacao);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setAvaliacao(avaliacao);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(avaliacao.getId())).will(returnValue(perguntas));
    	perguntaManager.expects(atLeastOnce()).method("setAvaliadoNaPerguntaDeAvaliacaoDesempenho").with(ANYTHING, ANYTHING);

    	avaliacaoManager.getQuestionarioRelatorio(avaliacao);
    }
	
	public void testEnviaLembrete()
	{
		Calendar cincoDiasPraFrente = Calendar.getInstance();
		cincoDiasPraFrente.add(Calendar.DAY_OF_YEAR, +5);
		
		Collection<PeriodoExperiencia> periodos = new ArrayList<PeriodoExperiencia>();
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(2L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		periodoExperiencia.setEmpresa(empresa);
		periodoExperiencia.setDias(45);
		periodos.add(periodoExperiencia);
		
		Collection<Integer> dias = new ArrayList<Integer>();
		dias.add(5);
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		Colaborador colaborador = new Colaborador(1000L, "José Germano", "Zé Mano", "Tabelião", "I", 2L, "Área", 1L, "Mãe", empresa.getId());
		colaboradores.add(colaborador);
		
		String mensagem = "Período de Experiência: 5 dias para a Avaliação de 45 dias de José Germano.\n\n"
			+ "Lembrete de Avaliação de 45 dias do Período de Experiência.\n\n"
			+ "Colaborador: José Germano (Zé Mano) \n"
			+ "Cargo: Tabelião I\n"
			+ "Área: Mãe > Área\n"
			+ "Data da avaliação: " + DateUtil.formataDiaMesAno(cincoDiasPraFrente.getTime());
		
		periodoExperienciaManager.expects(once()).method("findAll").withNoArguments().will(returnValue(periodos));
		parametrosDoSistemaManager.expects(once()).method("getDiasLembretePeriodoExperiencia").withNoArguments().will(returnValue(dias));
		colaboradorManager.expects(once()).method("findAdmitidosHaDias").with(eq(40),eq(empresa)).will(returnValue(colaboradores));
		usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleAvaliacaoExperiencia").will(returnValue(new ArrayList<UsuarioEmpresa>()));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(eq(mensagem), ANYTHING, ANYTHING, ANYTHING);
		
		avaliacaoManager.enviaLembrete();
	}
	
	public void testgetPontuacaoMaximaDaPerformance()
	{
		avaliacaoDao.expects(once()).method("getPontuacaoMaximaDaPerformance").with(eq(1L)).will(returnValue(210));
		assertEquals(210, avaliacaoManager.getPontuacaoMaximaDaPerformance(1L).intValue());
	}
	
	public void testMontaResultado() throws Exception
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.SUBJETIVA);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.SUBJETIVA);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(resposta1);

    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);

    	ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorResposta2.setPergunta(pergunta2);

    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);
    	colaboradorRespostas.add(colaboradorResposta2);
    	
    	Collection<ColaboradorResposta> colabRespostas1 = Arrays.asList(colaboradorResposta1);
//    	Collection<ColaboradorResposta> colabRespostas2 = Arrays.asList(colaboradorResposta2);
    	
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(3L);
    	
    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));
    	colaboradorRespostaManager.expects(once()).method("findInPerguntaIds").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradorRespostas));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostasMultipla").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	
    	questionarioManager.expects(once()).method("countColaborador").with(eq(colaboradorRespostas)).will(returnValue(10));
    	
    	ResultadoQuestionario resultadoQuestionario1 = new ResultadoQuestionario();
    	resultadoQuestionario1.setColabRespostas(colabRespostas1);
    	Collection<ResultadoQuestionario> resultadoQuestionarios = new ArrayList<ResultadoQuestionario>();
    	resultadoQuestionarios.add(resultadoQuestionario1);
    	resultadoQuestionarios.add(new ResultadoQuestionario());
    	
    	questionarioManager.expects(once()).method("montaResultadosQuestionarios").will(returnValue(resultadoQuestionarios));
    	
    	Collection<ResultadoQuestionario> resultado = avaliacaoManager.montaResultado(perguntas, null, null, null, null, avaliacao);
    	assertEquals(2, resultado.size());
    	assertEquals(1, ((ResultadoQuestionario)resultado.toArray()[0]).getColabRespostas().size());
    }
	public void testMontaResultadoExceptionSemPerguntas() 
	{
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(3L);
		
		respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(new ArrayList<Resposta>()));
		colaboradorRespostaManager.expects(once()).method("findInPerguntaIds").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<ColaboradorResposta>()));
		Exception exception=null;
		
		try {
			avaliacaoManager.montaResultado(perguntas, null, null, null, null, avaliacao);
		} catch (Exception e) {
			exception=e;
		}
		
		assertEquals("Nenhuma pergunta foi respondida.", exception.getMessage());
	}
	
}
