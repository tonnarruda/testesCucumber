package com.fortes.rh.test.web.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.web.chart.ResultadoPesquisa;

public class ResultadoPesquisaTest extends MockObjectTestCase
{
	private ResultadoPesquisa resultadoPesquisa;
	private Mock colaboradorRespostaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        resultadoPesquisa = new ResultadoPesquisa();

        colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);
        resultadoPesquisa.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());

    }

    protected void tearDown() throws Exception
    {
    	colaboradorRespostaManager = null;
        resultadoPesquisa = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", resultadoPesquisa.execute());
    }
    
    public void testGetSet() throws Exception
    {
    	String perguntaId = "1";
    	resultadoPesquisa.setIdPergunta(perguntaId);
    	assertEquals(perguntaId, resultadoPesquisa.getIdPergunta());

    	String pesquisaId = "1";
    	resultadoPesquisa.setIdPesquisa(pesquisaId);
    	assertEquals(pesquisaId, resultadoPesquisa.getIdPesquisa());
    	
    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	resultadoPesquisa.setRespostas(respostas);
    	assertEquals(respostas, resultadoPesquisa.getRespostas());
    	
    	int qtdComentarios = 2;
    	resultadoPesquisa.setQtdeComentario(qtdComentarios);
    	assertEquals(qtdComentarios, resultadoPesquisa.getQtdeComentario());
    	
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	resultadoPesquisa.setPergunta(pergunta);
    	assertEquals(pergunta, resultadoPesquisa.getPergunta());
    	
    	resultadoPesquisa.setPeriodoFim(new Date());
    	resultadoPesquisa.setPeriodoIni(new Date());
    }
    
    public void testGraficoObjetiva() throws Exception
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	resultadoPesquisa.setPergunta(pergunta);

    	resultadoPesquisa.setAreasIds("1,2");

    	Integer[] qtdRespostasA = {1, 2};
    	Integer[] qtdRespostasB = {2, 3};

    	List<Integer[]> qtdRespostas = new ArrayList<Integer[]>();
    	qtdRespostas.add(qtdRespostasA);
    	qtdRespostas.add(qtdRespostasB);

    	colaboradorRespostaManager.expects(once()).method("countRespostas").with(new Constraint[]{eq(pergunta.getId()), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(qtdRespostas));

    	assertEquals("success", resultadoPesquisa.graficoObjetiva());
    	assertNotNull(resultadoPesquisa.getChart());
    }
}