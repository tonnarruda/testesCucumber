package com.fortes.rh.test.business.sesmt;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AgendaManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.PcmsoManagerImpl;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.PCMSO;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.util.DateUtil;

public class PcmsoManagerTest extends MockObjectTestCase
{
	private PcmsoManagerImpl pcmsoManager;
	private Mock agendaManager;
	private Mock areaOrganizacionalManager;
	private Mock funcaoManager;
	private Mock historicoFuncaoManager;
	private Mock estabelecimentoManager;
	private Mock empresaManager;

	protected void setUp() throws Exception
    {
        super.setUp();

        pcmsoManager = new PcmsoManagerImpl();
        agendaManager = new Mock(AgendaManager.class);
        funcaoManager = new Mock(FuncaoManager.class);
        historicoFuncaoManager = new Mock(HistoricoFuncaoManager.class);
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        empresaManager = mock(EmpresaManager.class);
        
        pcmsoManager.setAgendaManager((AgendaManager) agendaManager.proxy());
        pcmsoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        pcmsoManager.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
        pcmsoManager.setHistoricoFuncaoManager((HistoricoFuncaoManager) historicoFuncaoManager.proxy());
        pcmsoManager.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        pcmsoManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testMontaRelatorio()
    {
    	Exception excep = null;
    	try
		{
    		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
			Long empresaId=1L;
			
			estabelecimentoManager.expects(once()).method("findComEnderecoById").with(eq(estabelecimento.getId()));
			empresaManager.expects(once()).method("findByIdProjection").with(eq(empresaId));
			
			pcmsoManager.montaRelatorio(DateUtil.criarDataMesAno(01, 01, 2009), DateUtil.criarDataMesAno(01, 01, 2009), estabelecimento, empresaId, false,false,false,false,false,false,false);
		
		} catch (Exception e)
		{
			excep = e;
		}

		assertNull(excep);
    }
    
    public void testMontaRelatorioEpisColaboradorSemFuncao()
    {
    	Exception excep = null;
    	try
    	{
    		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    		Long empresaId=1L;
    		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
    		
    		Collection<String> nomes = new ArrayList<String>();
    		nomes.add("Joao");
    		nomes.add("Maria");
    		
    		estabelecimentoManager.expects(once()).method("findComEnderecoById").with(eq(estabelecimento.getId())).will(returnValue(estabelecimento));
			empresaManager.expects(once()).method("findByIdProjection").with(eq(empresaId));
    		
    		funcaoManager.expects(once()).method("findColaboradoresSemFuncao").with(eq(dataIni), eq(estabelecimento.getId())).will(returnValue(nomes));
    		pcmsoManager.montaRelatorio(dataIni, DateUtil.criarDataMesAno(01, 01, 2009), estabelecimento, empresaId, false, false, false, true, false, false, false);
    	} catch (Exception e)
    	{
    		excep = e;
    	}
    	
    	assertNotNull(excep);
    	assertEquals("Não existem funções para os colaboradores: Joao,Maria<br>", excep.getMessage());
    }
    
    public void testMontaRelatorioSemEpis()
    {
    	Exception excep = null;
    	try
    	{
    		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    		Long empresaId=1L;
    		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
    		
    		estabelecimentoManager.expects(once()).method("findComEnderecoById").with(eq(estabelecimento.getId())).will(returnValue(estabelecimento));
			empresaManager.expects(once()).method("findByIdProjection").with(eq(empresaId));
    		
    		funcaoManager.expects(once()).method("findColaboradoresSemFuncao").with(eq(dataIni), eq(estabelecimento.getId())).will(returnValue(new ArrayList<String>()));
    		funcaoManager.expects(once()).method("findFuncaoAtualDosColaboradores").with(eq(dataIni), eq(estabelecimento.getId())).will(returnValue(new ArrayList<Long>()));
    		historicoFuncaoManager.expects(once()).method("findEpis").with(ANYTHING, eq(dataIni)).will(returnValue(new ArrayList<HistoricoFuncao>()));
    		
    		pcmsoManager.montaRelatorio(dataIni, DateUtil.criarDataMesAno(01, 01, 2009), estabelecimento, empresaId, false, false, false, true, false, false, false);
    	} catch (Exception e)
    	{
    		excep = e;
    	}
    	
    	assertNotNull(excep);
    	assertEquals("Não existem históricos de funções até a data final do período informado.<br>", excep.getMessage());
    }

    public void testMontaRelatorioEpis()
    {
    	Exception excep = null;
    	try
    	{
    		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    		Long empresaId=1L;
    		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
    		
    		Collection<HistoricoFuncao> historicos = new ArrayList<HistoricoFuncao>();
    		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    		historicos.add(historicoFuncao);
    		
    		estabelecimentoManager.expects(once()).method("findComEnderecoById").with(eq(estabelecimento.getId())).will(returnValue(estabelecimento));
			empresaManager.expects(once()).method("findByIdProjection").with(eq(empresaId));
    		
    		funcaoManager.expects(once()).method("findColaboradoresSemFuncao").with(eq(dataIni), eq(estabelecimento.getId())).will(returnValue(new ArrayList<String>()));
    		funcaoManager.expects(once()).method("findFuncaoAtualDosColaboradores").with(eq(dataIni), eq(estabelecimento.getId())).will(returnValue(new ArrayList<Long>()));
    		historicoFuncaoManager.expects(once()).method("findEpis").with(ANYTHING, eq(dataIni)).will(returnValue(historicos));
    		
    		pcmsoManager.montaRelatorio(dataIni, DateUtil.criarDataMesAno(01, 01, 2009), estabelecimento, empresaId, false, false, false, true, false, false, false);
    	} catch (Exception e)
    	{
    		excep = e;
    	}
    	
    	assertNull(excep);
    }
    
    public void testMontaRelatorioExceptionDataInvalida()
    {
    	Exception excep = null;
    	try
		{
    		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
			Long empresaId=1L;
			pcmsoManager.montaRelatorio(DateUtil.criarDataMesAno(02, 02, 2009), DateUtil.criarDataMesAno(01, 01, 2009), estabelecimento, empresaId, true,true,true,true,true,true, false);
		} catch (Exception e)
		{
			excep = e;
		}

		assertNotNull(excep);
    }
    
    public void testMontaAgendaVazia() throws ColecaoVaziaException, ParseException
    {
    	Exception excep = null;
    	Collection<Agenda> agendas = new ArrayList<Agenda>();
    	Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
    	Date dataFim = DateUtil.criarDataMesAno(02, 02, 2009);
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	
		agendaManager.expects(once()).method("findByPeriodo").with(eq(dataIni), eq(dataFim), eq(null), eq(estabelecimento)).will(returnValue(agendas));
		assertEquals("Não existem dados da agenda para o período informado.<br>", pcmsoManager.montaAgenda(null, dataIni, dataFim, estabelecimento, true));
	
    }
    
    public void testMontaAgenda()
    {
    	Exception excep = null;
    	PCMSO pcmso = new PCMSO();
    	Collection<Agenda> agendas = AgendaFactory.getCollection(1L);
    	Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
    	Date dataFim = DateUtil.criarDataMesAno(02, 02, 2009);
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	
    	try
    	{
    		agendaManager.expects(once()).method("findByPeriodo").with(eq(dataIni), eq(dataFim), eq(null), eq(estabelecimento)).will(returnValue(agendas));
    		pcmsoManager.montaAgenda(pcmso, dataIni, dataFim, estabelecimento, true);
    	} catch (Exception e)
    	{
    		excep = e;
    		e.printStackTrace();
    	}
    	
    	assertNull(excep);
    	assertEquals(1, pcmso.getAgendas().size());
    }
    
    public void testMontaColaboradoresPorSetor() throws Exception
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	AreaOrganizacional areaOrganizacional = new AreaOrganizacional(1L, "Área 1", 5);
    	AreaOrganizacional areaOrganizacional2 = new AreaOrganizacional(2L, "Área 2", 30);
    	areas.add(areaOrganizacional);
    	areas.add(areaOrganizacional2);
    	
    	PCMSO pcmso = new PCMSO();
    	
    	Date data = new Date();
    	Long empresaId=2L;
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	areaOrganizacionalManager.expects(once()).method("findQtdColaboradorPorArea").with(eq(estabelecimento.getId()), eq(data)).will(returnValue(areas));
    	areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").will(returnValue(areas));
    	areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areas));
    	areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(areaOrganizacional2));
    	areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(areaOrganizacional));
    	pcmsoManager.montaColaboradoresPorSetor(pcmso, data, empresaId, estabelecimento, true);
    	
    	assertEquals(35, pcmso.getQtdTotalColaboradores().intValue());
    }
}