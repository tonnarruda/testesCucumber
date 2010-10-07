package com.fortes.rh.test.web.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.web.chart.ProgressaoColaborador;

public class ProgressaoColaboradorTest extends MockObjectTestCase
{
	private ProgressaoColaborador progressaoColaborador;
	private Mock historicoColaboradorManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        progressaoColaborador = new ProgressaoColaborador();

        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        progressaoColaborador.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
    }

    protected void tearDown() throws Exception
    {
    	historicoColaboradorManager = null;
        progressaoColaborador = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
    	historicoColaborador.setSalario(200.00);
    	historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
    	historicoColaborador.setData(new Date());
    	historicoColaborador.setColaborador(colaborador);
    	
    	Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
    	historicos.add(historicoColaborador);
    	
    	historicoColaboradorManager.expects(once()).method("progressaoColaborador").with(ANYTHING, ANYTHING).will(returnValue(historicos));

    	assertEquals("success", progressaoColaborador.execute());
    	assertNotNull(progressaoColaborador.getChart());

    	//implementado para dar cobertura
    	progressaoColaborador.setId(1L);
    }
    
}