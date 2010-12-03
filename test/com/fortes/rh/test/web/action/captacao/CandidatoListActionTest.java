package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.IdiomaManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.CandidatoListAction;
import com.opensymphony.xwork.ActionContext;

public class CandidatoListActionTest extends MockObjectTestCase
{
	private CandidatoListAction action;
	private Mock manager;
	private Mock estadoManager;
	private Mock idiomaManager;
	private Mock cidadeManager;
	private Mock empresaManager;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CandidatoListAction();
        manager = new Mock(CandidatoManager.class);
        idiomaManager = new Mock(IdiomaManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        estadoManager = new Mock(EstadoManager.class);
        cidadeManager = mock(CidadeManager.class);

        action.setCandidatoManager((CandidatoManager) manager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setCidadeManager((CidadeManager) cidadeManager.proxy());
        action.setEstadoManager((EstadoManager) estadoManager.proxy());
        action.setIdiomaManager((IdiomaManager) idiomaManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepareInsertCurriculo() throws Exception
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);
    	action.setCandidato(candidato);

    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	idiomaManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Idioma>()));
    	manager.expects(once()).method("montaStringBuscaF2rh");

    	assertEquals(action.buscaF2rh(), "success");
    }
}