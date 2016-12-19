package com.fortes.rh.test.web.action.pesquisa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.EntrevistaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.action.pesquisa.QuestionarioListAction;
import com.fortes.web.tags.CheckBox;

public class QuestionarioListActionTest_Junit4
{
	private QuestionarioListAction action;
    private AreaOrganizacionalManager areaOrganizacionalManager;
    private EntrevistaManager entrevistaManager;
    private EstabelecimentoManager estabelecimentoManager;

    @Before
    public void setUp() throws Exception
    {
        action = new QuestionarioListAction();
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager(areaOrganizacionalManager);

        entrevistaManager = mock(EntrevistaManager.class);
        action.setEntrevistaManager(entrevistaManager);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager(estabelecimentoManager);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        
    }
    
    @Test
	public void testPrepareResultadoEntrevista()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.getENTREVISTA());
    	action.setQuestionario(questionario);

    	Entrevista entrevista1 = EntrevistaFactory.getEntity(1L);
    	entrevista1.setQuestionario(questionario);

    	Entrevista entrevista2 = EntrevistaFactory.getEntity(2L);
    	entrevista2.setQuestionario(questionario);

    	Collection<Entrevista> entrevistas = new ArrayList<Entrevista>();
    	entrevistas.add(entrevista1);
    	entrevistas.add(entrevista2);

    	when(entrevistaManager.findAllSelect(action.getEmpresaSistema().getId(), null)).thenReturn(entrevistas);

    	Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

    	when(areaOrganizacionalManager.populaCheckOrderDescricao(action.getEmpresaSistema().getId())).thenReturn(areasCheckList);
    	
    	assertEquals("Teste prepare resultado da entrevista", "success", action.prepareResultadoEntrevista());
    }
}