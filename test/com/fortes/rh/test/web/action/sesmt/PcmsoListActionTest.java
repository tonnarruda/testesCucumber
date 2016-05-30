package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.PcmsoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PCMSO;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.PcmsoListAction;

public class PcmsoListActionTest extends MockObjectTestCase
{
	private PcmsoListAction action;
	private Mock pcmsoManager;
	private Mock estabelecimentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new PcmsoListAction();
		pcmsoManager = new Mock(PcmsoManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);

		action.setPcmsoManager((PcmsoManager) pcmsoManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	action = null;
        super.tearDown();
    }

    public void testPrepareRelatorio() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<Estabelecimento>()));
    	
    	assertEquals(action.prepareRelatorio(), "success");
    	assertNotNull(action.getEstabelecimentos());
    }
    
    public void testGeraRelatorio() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
    	action.setDataIni(dataIni);
    	Date dataFim = DateUtil.criarDataMesAno(02, 02, 2009);
    	action.setDataFim(dataFim);
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	action.setEstabelecimento(estabelecimento);

    	pcmsoManager.expects(once()).method("montaRelatorio").with(new Constraint[]{eq(dataIni), eq(dataFim), eq(estabelecimento), eq(empresa.getId()), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<PCMSO>()));
    	
    	assertEquals(action.gerarRelatorio(), "success");
    	assertNotNull(action.getDataSource());
    	assertEquals(dataIni, action.getDataIni());
    	assertEquals(dataFim, action.getDataFim());
    	assertEquals(estabelecimento, action.getEstabelecimento());
    	assertNotNull(action.getParametros());
    }
    
    public void testGeraRelatorioException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	pcmsoManager.expects(once()).method("montaRelatorio").will(throwException(new Exception()));
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<Estabelecimento>()));
    	
    	assertEquals(action.gerarRelatorio(), "input");
    }
    
    public void testGeraRelatorioColecaoVaziaException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	pcmsoManager.expects(once()).method("montaRelatorio").will(throwException(new ColecaoVaziaException("")));
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<Estabelecimento>()));
    	
    	assertEquals(action.gerarRelatorio(), "input");
    }
    
    public void testGetSets()
    {
    	action.isExibirAgenda();
    	action.setExibirAgenda(true);
    	action.isExibirDistColaboradorSetor();
    	action.setExibirDistColaboradorSetor(true);
    	action.isExibirRiscos();
    	action.setExibirRiscos(true);
    	action.isExibirEpis();
    	action.setExibirEpis(true);
    	action.isExibirExames();
    	action.setExibirExames(true);
    	action.isExibirAcidentes(); 
    	action.setExibirAcidentes(true);
    }
}