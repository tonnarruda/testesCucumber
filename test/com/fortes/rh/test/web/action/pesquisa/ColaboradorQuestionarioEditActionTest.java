package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.ColaboradorQuestionarioEditAction;
import com.fortes.web.tags.CheckBox;

public class ColaboradorQuestionarioEditActionTest extends MockObjectTestCase
{
	private ColaboradorQuestionarioEditAction action;
	private Mock colaboradorQuestionarioManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock colaboradorManager;
	private Mock cargoManager;
	private Mock grupoOcupacionalManager;
	private Mock questionarioManager;
	private Mock empresaManager;
	private Mock avaliacaoManager;
	private Mock colaboradorRespostaManager;
	private Mock perguntaManager;
	private Mock respostaManager;
	private Mock parametrosDoSistemaManager ;

	protected void setUp() throws Exception
	{
		super.setUp();
		colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);

		action = new ColaboradorQuestionarioEditAction();
		action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());

        cargoManager= new Mock(CargoManager.class);
        action.setCargoManager((CargoManager) cargoManager.proxy());

        grupoOcupacionalManager = new Mock(GrupoOcupacionalManager.class);
        action.setGrupoOcupacionalManager((GrupoOcupacionalManager) grupoOcupacionalManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        action.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());
        
        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        avaliacaoManager = mock(AvaliacaoManager.class);
        action.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
        
        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        action.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());
        
        perguntaManager = mock(PerguntaManager.class);
        action.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
        
        respostaManager = mock (RespostaManager.class);
        action.setRespostaManager((RespostaManager) respostaManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();

		colaboradorQuestionarioManager = null;
		action = null;

		super.tearDown();
	}

	public void testPrepareInsert() throws Exception
	{
		prepareMockPrepareInsert();

		assertEquals("success", action.prepareInsert());
		assertEquals("Empresa selecionada",	action.getEmpresaId(), action.getEmpresaSistema().getId());
	}

    public void testInsert() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	Long[] colaboradoresId = new Long[]{1L};
    	action.setColaboradoresId(colaboradoresId);

		colaboradorQuestionarioManager.expects(once()).method("save").with(eq(questionario), eq(colaboradoresId), ANYTHING);

    	assertEquals("success", action.insert());
    }

    public void testListFiltroAplicarPorParteTrue() throws Exception
    {
    	action.setPercentual("2");

    	String[] areasCheck = {"1","2"};
    	String[] cargosCheck = {"1","2"};
    	String[] estabelecimentosCheck = {"1","2"};

    	action.setAreasCheck(areasCheck);
    	action.setCargosCheck(cargosCheck);
    	action.setEstabelecimentosCheck(estabelecimentosCheck);

    	action.setFiltrarPor('1');
    	action.setCalcularPercentual(true);
    	action.setAplicarPorParte(true);

    	Collection<Colaborador> colaboradores = ColaboradorFactory.getCollection();

    	Colaborador colaborador = new Colaborador();
    	action.setColaborador(colaborador);
    	colaboradorManager.expects(once()).method("getColaboradoresByEstabelecimentoAreaGrupo").with(new Constraint[]{eq('1'), ANYTHING, ANYTHING, ANYTHING, eq(colaborador.getNome()), ANYTHING}).will(returnValue(colaboradores));
    	colaboradorQuestionarioManager.expects(once()).method("selecionaColaboradoresPorParte").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradores));

    	prepareMockPrepareInsert();

		assertEquals("success", action.listFiltro());
		assertEquals(1, action.getColaboradores().size());
    }

    public void testListFiltroAplicarPorParteFalse() throws Exception
    {
    	action.setPercentual("2");

    	String[] areasCheck = {"1","2"};
    	String[] cargosCheck = {"1","2"};
    	String[] estabelecimentosCheck = {"1","2"};

    	action.setAreasCheck(areasCheck);
    	action.setCargosCheck(cargosCheck);
    	action.setEstabelecimentosCheck(estabelecimentosCheck);

    	action.setFiltrarPor('1');
    	action.setCalcularPercentual(true);
    	action.setAplicarPorParte(false);

    	Collection<Colaborador> colaboradores = ColaboradorFactory.getCollection();
    	Colaborador colaborador = new Colaborador();
    	action.setColaborador(colaborador);
    	colaboradorManager.expects(once()).method("getColaboradoresByEstabelecimentoAreaGrupo").with(new Constraint[]{eq('1'), ANYTHING, ANYTHING, ANYTHING, eq(colaborador.getNome()),ANYTHING}).will(returnValue(colaboradores));
    	colaboradorQuestionarioManager.expects(once()).method("selecionaColaboradores").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradores));

    	//prepareInsert
    	prepareMockPrepareInsert();

    	assertEquals("success", action.listFiltro());
    	assertEquals(1, action.getColaboradores().size());
    }
    
    public void testListFiltroVazio() throws Exception
    {
    	action.setPercentual("");
    	
    	String[] areasCheck = {"1","2"};
    	String[] cargosCheck = {"1","2"};
    	String[] estabelecimentosCheck = {"1","2"};
    	
    	action.setAreasCheck(areasCheck);
    	action.setCargosCheck(cargosCheck);
    	action.setEstabelecimentosCheck(estabelecimentosCheck);
    	
    	action.setFiltrarPor('1');
    	action.setCalcularPercentual(true);
    	action.setAplicarPorParte(false);
    	
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	Colaborador colaborador = new Colaborador();
    	action.setColaborador(colaborador);
    	colaboradorManager.expects(once()).method("getColaboradoresByEstabelecimentoAreaGrupo").with(new Constraint[]{eq('1'), ANYTHING, ANYTHING, ANYTHING, eq(colaborador.getNome()),ANYTHING}).will(returnValue(colaboradores));
    	colaboradorQuestionarioManager.expects(once()).method("selecionaColaboradores").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradores));
    	
    	//prepareInsert
    	prepareMockPrepareInsert();
    	
    	assertEquals("success", action.listFiltro());
    	assertEquals(0, action.getColaboradores().size());
    }

    public void testListFiltroAplicarPercentual() throws Exception
    {
    	action.setPercentual("");

    	String[] areasCheck = {"1","2"};
    	String[] cargosCheck = {"1","2"};
    	String[] estabelecimentosCheck = {"1","2"};

    	action.setAreasCheck(areasCheck);
    	action.setCargosCheck(cargosCheck);
    	action.setEstabelecimentosCheck(estabelecimentosCheck);

    	action.setFiltrarPor('1');
    	action.setCalcularPercentual(true);
    	action.setAplicarPorParte(false);

    	Collection<Colaborador> colaboradores = ColaboradorFactory.getCollection();
    	Colaborador colaborador = new Colaborador();
    	action.setColaborador(colaborador);
    	colaboradorManager.expects(once()).method("getColaboradoresByEstabelecimentoAreaGrupo").with(new Constraint[]{eq('1'), ANYTHING, ANYTHING, ANYTHING, eq(colaborador.getNome()),ANYTHING}).will(returnValue(colaboradores));
    	colaboradorQuestionarioManager.expects(once()).method("selecionaColaboradores").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradores));

    	//prepareInsert
    	prepareMockPrepareInsert();

    	assertEquals("success", action.listFiltro());
    	assertEquals(1, action.getColaboradores().size());
    	assertEquals(false, action.isAplicarPorParte());
    	assertEquals(true, action.isCalcularPercentual());
    	assertEquals('1', action.getFiltrarPor());
    }

	private void prepareMockPrepareInsert()
	{
		Empresa empresa = action.getEmpresaSistema();
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);
    	
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	grupoOcupacionalManager.expects(once()).method("populaCheckOrderNome").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	cargoManager.expects(once()).method("populaCheckBox").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
	}
	
	public void testPrepareInsertAvaliacaoExperiencia()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(111L);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(AvaliacaoFactory.getEntity(10L));
		action.setColaboradorQuestionario(colaboradorQuestionario);
		
		// Perguntas e respostas
		
		Pergunta perguntaObj = new Pergunta();
		perguntaObj.setTipo(TipoPergunta.OBJETIVA);
		perguntaObj.setTexto("Como é o avaliado?");
		Resposta resposta = new Resposta();
		resposta.setTexto("bom");
		Resposta resposta2 = new Resposta();
		resposta2.setTexto("ruim");
		Pergunta perguntaMult = new Pergunta();
		perguntaMult.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
		perguntaMult.setTexto("Quais habilidades?");
		Resposta respostaMulti1 = new Resposta();
		respostaMulti1.setTexto("Karatê");
		Resposta respostaMulti2 = new Resposta();
		respostaMulti2.setTexto("Judô");
		Resposta respostaMulti3 = new Resposta();
		respostaMulti3.setTexto("Malandragem");
		
		Collection<Pergunta> perguntas = Arrays.asList(perguntaObj, perguntaMult);
		
		Collection<Resposta> respostasDaPerguntaObj = Arrays.asList(resposta,resposta2);
		Collection<Resposta> respostasDaPerguntaMulti = Arrays.asList(respostaMulti1,respostaMulti2,respostaMulti3);
		
		// Montando ColaboradorResposta do jeito que vem do banco:
		// um ColaboradorResposta para cada resposta múltipla escolha
		
		ColaboradorResposta colaboradorResposta1 = new ColaboradorResposta();
		colaboradorResposta1.setPergunta(perguntaObj);
		colaboradorResposta1.setResposta(resposta2);
		ColaboradorResposta colaboradorResposta2 = new ColaboradorResposta();
		colaboradorResposta2.setPergunta(perguntaMult);
		colaboradorResposta2.setResposta(respostaMulti2);
		ColaboradorResposta colaboradorResposta3 = new ColaboradorResposta();
		colaboradorResposta3.setPergunta(perguntaMult);
		colaboradorResposta3.setResposta(respostaMulti3);
		
		Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(colaboradorResposta1, colaboradorResposta2, colaboradorResposta3);
		
		colaboradorManager.expects(once()).method("findByIdProjectionEmpresa").will(returnValue(colaborador));
		avaliacaoManager.expects(once()).method("find").will(returnValue(new ArrayList<Avaliacao>()));
		colaboradorQuestionarioManager.expects(once()).method("populaQuestionario").will(returnValue(colaboradorRespostas));
		perguntaManager.expects(atLeastOnce()).method("setAvaliadoNaPerguntaDeAvaliacaoDesempenho").with(ANYTHING, eq(colaborador.getNome()));
		perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionarioAgrupadosPorAspecto").with(eq(10L), ANYTHING).will(returnValue(perguntas));
		respostaManager.expects(once()).method("findByPergunta").with(eq(perguntaObj.getId())).will(returnValue(respostasDaPerguntaObj));
		respostaManager.expects(once()).method("findByPergunta").with(eq(perguntaMult.getId())).will(returnValue(respostasDaPerguntaMulti));
		
		assertEquals("success", action.prepareInsertAvaliacaoExperiencia());
		
		assertEquals(2, action.getPerguntas().size());
		// 1 resposta para perg objetiva
		assertEquals(1,((Pergunta)action.getPerguntas().toArray()[0]).getColaboradorRespostas().size());
		// 2 respostas para perg multi
		assertEquals(2,((Pergunta)action.getPerguntas().toArray()[1]).getColaboradorRespostas().size());
	}
	public void testPrepareUpdateAvaliacaoExperiencia()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(111L);
		colaboradorQuestionario.setColaborador(colaborador);
		action.setColaboradorQuestionario(colaboradorQuestionario);
		
		colaboradorQuestionarioManager.expects(once()).method("findById").will(returnValue(colaboradorQuestionario));
		colaboradorManager.expects(once()).method("findByIdProjectionEmpresa").will(returnValue(colaborador));
		avaliacaoManager.expects(once()).method("find").will(returnValue(new ArrayList<Avaliacao>()));
		colaboradorRespostaManager.expects(once()).method("findByColaboradorQuestionario").will(returnValue(new ArrayList<ColaboradorResposta>()));
		
		assertEquals("success", action.prepareUpdateAvaliacaoExperiencia());
	}
	public void testInsertAvaliacaoExperiencia()
	{
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity(23L);
		Pergunta perguntaDaResp = PerguntaFactory.getEntity(33L);
		perguntaDaResp.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
		colaboradorResposta.setPergunta(perguntaDaResp);
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.addColaboradorResposta(colaboradorResposta);
		perguntas.add(pergunta);
		
		action.setPerguntas(perguntas);
		action.setColaboradorQuestionario(new ColaboradorQuestionario());
		action.setSolicitacao(new Solicitacao());
		
		colaboradorRespostaManager.expects(once()).method("save");
		assertEquals("success", action.insertAvaliacaoExperiencia());
	}
	public void testUpdateAvaliacaoExperiencia()
	{
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		action.setPerguntas(perguntas);
		action.setColaboradorQuestionario(new ColaboradorQuestionario());
		action.setSolicitacao(new Solicitacao());
		
		colaboradorRespostaManager.expects(once()).method("update");
		assertEquals("success", action.updateAvaliacaoExperiencia());
	}
	
	public void testPrepareResponderAvaliacaoDesempenho()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(111L);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliador(colaborador);
		colaboradorQuestionario.setAvaliacao(AvaliacaoFactory.getEntity(10L));
		colaboradorQuestionario.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity(1L));
		action.setColaboradorQuestionario(colaboradorQuestionario);
		
		colaboradorQuestionarioManager.expects(once()).method("findByIdProjection").will(returnValue(colaboradorQuestionario));
		colaboradorManager.expects(atLeastOnce()).method("findByIdProjectionEmpresa").with(ANYTHING).will(returnValue(colaborador));
		colaboradorQuestionarioManager.expects(once()).method("populaQuestionario").will(returnValue(new ArrayList<ColaboradorResposta>()));
		perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionarioAgrupadosPorAspecto").with(eq(10L), ANYTHING).will(returnValue(new ArrayList<Pergunta>()));
		
		assertEquals("success",action.prepareResponderAvaliacaoDesempenho());
		
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setRespondidaEm(new Date());
		
		colaboradorQuestionarioManager.expects(once()).method("findByIdProjection").will(returnValue(colaboradorQuestionario));
		colaboradorManager.expects(once()).method("findByIdProjectionEmpresa").will(returnValue(colaborador));
		colaboradorRespostaManager.expects(once()).method("findByColaboradorQuestionario").will(returnValue(new ArrayList<ColaboradorResposta>()));
		perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionarioAgrupadosPorAspecto").with(eq(10L), ANYTHING).will(returnValue(new ArrayList<Pergunta>()));
		
		assertEquals("success",action.prepareResponderAvaliacaoDesempenho());
	}
	
	public void testResponderAvaliacaoDesempenho()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(111L);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(AvaliacaoFactory.getEntity(10L));
		colaboradorQuestionario.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity(1L));
		action.setColaboradorQuestionario(colaboradorQuestionario);
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		action.setPerguntas(perguntas);
		
		action.setSolicitacao(new Solicitacao());
		
		colaboradorRespostaManager.expects(once()).method("update");
		assertEquals("success", action.updateAvaliacaoExperiencia());
	}

    public void testGets() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario.setId(1L);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);
    	assertEquals(questionario, action.getQuestionario());

    	assertEquals(0, action.getAreasCheckList().size());
    	assertEquals(0, action.getGruposCheckList().size());
    	assertEquals(0, action.getEstabelecimentosCheckList().size());
    	assertEquals(0, action.getCargosCheckList().size());

    	action.setGruposCheck(new String[]{"2"});
    	assertNotNull(action.getGruposCheck());

    	action.setQtdPercentual('1');
    	assertEquals('1', action.getQtdPercentual());

    	action.setQuantidade(1);
    	assertEquals(1, action.getQuantidade());

    	action.setPercentual("1");
    	assertEquals("1", action.getPercentual());
    	
    	assertNotNull(action.getTipoQuestionario());
    	
    	action.isExibirBotaoConcluir();
    	
    	action.setColaborador(null);
    	action.getColaborador();
    	action.getTipoPergunta();
    	action.getAvaliacaoExperiencia();
    	action.setAvaliacaoExperiencia(new Avaliacao());
    	action.getColaboradorQuestionario();
    	action.getAvaliacaoExperiencias();
    	
    }
}