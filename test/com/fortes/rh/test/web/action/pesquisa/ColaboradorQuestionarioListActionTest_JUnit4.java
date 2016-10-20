package com.fortes.rh.test.web.action.pesquisa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.action.pesquisa.ColaboradorQuestionarioListAction;

public class ColaboradorQuestionarioListActionTest_JUnit4
{
	private ColaboradorQuestionarioListAction colaboradorQuestionarioAction;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorManager colaboradorManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private QuestionarioManager questionarioManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EstabelecimentoManager estabelecimentoManager;

	@Before
    public void setUp() throws Exception
    {
        colaboradorQuestionarioAction = new ColaboradorQuestionarioListAction();

        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
        colaboradorQuestionarioAction.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);

        colaboradorManager = mock(ColaboradorManager.class);
        colaboradorQuestionarioAction.setColaboradorManager(colaboradorManager);

        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        colaboradorQuestionarioAction.setColaboradorRespostaManager(colaboradorRespostaManager);

        questionarioManager = mock(QuestionarioManager.class);
        colaboradorQuestionarioAction.setQuestionarioManager(questionarioManager);

        empresaManager = mock(EmpresaManager.class);
        colaboradorQuestionarioAction.setEmpresaManager(empresaManager);

        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        colaboradorQuestionarioAction.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        colaboradorQuestionarioAction.setEstabelecimentoManager(estabelecimentoManager);
    }

	@Test
    public void testDelete() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario);
    	colaboradorQuestionario.setAvaliacao(new Avaliacao());

    	when(colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId())).thenReturn(colaboradorQuestionario);

    	assertEquals("success", colaboradorQuestionarioAction.delete());
    }
    
	@Test
    public void testDeleteSemAvaliacao() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario);

    	when(colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId())).thenReturn(colaboradorQuestionario);

    	assertEquals("success", colaboradorQuestionarioAction.delete());
    }
	
	@Test
    public void testDeleteTipoPesquisaRespondida() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.PESQUISA);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario.setRespondida(true);
    	colaboradorQuestionario.setQuestionario(questionario);
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario);

    	when(colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId())).thenReturn(colaboradorQuestionario);
    	
    	assertEquals("success", colaboradorQuestionarioAction.delete());
    	assertEquals("Não é possível excluir esse colaborador, pois o colaborador já respondeu essa pesquisa.", colaboradorQuestionarioAction.getActionMessages().toArray()[0]);
    }
}
