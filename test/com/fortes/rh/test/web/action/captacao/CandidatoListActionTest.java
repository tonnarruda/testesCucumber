package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.f2rh.Curriculo;
import com.fortes.f2rh.F2rhFacade;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.IdiomaManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.web.action.captacao.CandidatoListAction;

public class CandidatoListActionTest extends MockObjectTestCase
{
	private CandidatoListAction action;
	private Mock manager;
	private Mock estadoManager;
	private Mock idiomaManager;
	private Mock cidadeManager;
	private Mock empresaManager;
	private Mock f2rhFacade;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CandidatoListAction();
        manager = new Mock(CandidatoManager.class);
        idiomaManager = new Mock(IdiomaManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        estadoManager = new Mock(EstadoManager.class);
        cidadeManager = mock(CidadeManager.class);
        f2rhFacade = mock(F2rhFacade.class);

        action.setCandidatoManager((CandidatoManager) manager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setCidadeManager((CidadeManager) cidadeManager.proxy());
        action.setEstadoManager((EstadoManager) estadoManager.proxy());
        action.setIdiomaManager((IdiomaManager) idiomaManager.proxy());
        action.setF2rhFacade((F2rhFacade) f2rhFacade.proxy());
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
    	
    	f2rhFacade.expects(once()).method("buscarCurriculos").with(ANYTHING).will(returnValue(new ArrayList<Curriculo>()));
    	f2rhFacade.expects(once()).method("removeCandidatoInseridoSolicitacao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Curriculo>()));

    	assertEquals(action.buscaF2rh(), "success");
    }
}