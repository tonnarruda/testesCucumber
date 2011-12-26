package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.FuncaoListAction;

public class FuncaoListActionTest extends MockObjectTestCase
{
	private FuncaoListAction action;
	private Mock manager;
	private Mock cargoManager;
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;

    protected void setUp() throws Exception
    {
        action = new FuncaoListAction();
        manager = new Mock(FuncaoManager.class);
        cargoManager = new Mock(CargoManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        estabelecimentoManager = mock(EstabelecimentoManager.class);

        action.setFuncaoManager((FuncaoManager) manager.proxy());
        action.setCargoManager((CargoManager) cargoManager.proxy());
        action.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacionalManager.proxy());
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        cargoManager = null;
        action = null;
        colaboradorManager=null;
        areaOrganizacionalManager = null;
        Mockit.restoreAllOriginalDefinitions();
    }

    public void testDelete() throws Exception
    {
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);
    	action.setFuncao(funcao);

    	manager.expects(once()).method("removeFuncao").with(ANYTHING);

    	Cargo cargoTmp = new Cargo();
    	cargoTmp.setId(1L);

    	action.setCargoTmp(cargoTmp);

    	assertEquals(action.delete(), "success");
    }

    public void testList() throws Exception
    {
		Collection<Funcao> funcaos = new ArrayList<Funcao>();

		Cargo cargo = new Cargo();
		cargo.setId(2L);

		action.setCargoTmp(cargo);

		Funcao f1 = new Funcao();
		f1.setId(1L);
		f1.setCargo(cargo);

		Funcao f2 = new Funcao();
		f2.setId(2L);
		f2.setCargo(cargo);

		funcaos.add(f1);
		funcaos.add(f2);

		manager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(funcaos.size()));
		manager.expects(once()).method("findByCargo").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(funcaos));
		cargoManager.expects(once()).method("findByIdProjection").with(eq(cargo.getId())).will(returnValue(cargo));

    	assertEquals(action.list(), "success");
    	assertEquals(action.getFuncaos(), funcaos);
    	assertEquals(action.getCargoTmp(), cargo);

    	manager.verify();
    }

    public void testMudancaFuncao() throws Exception
    {
    	int totalSize = 1;
    	Colaborador colaborador = new Colaborador();
    	colaborador.setNome("Nome de teste");
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>(1);
    	colaboradores.add(colaborador);

    	AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
    	areaOrganizacional.setDescricao("Descrição de teste");
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>(1);
    	areas.add(areaOrganizacional);

    	colaboradorManager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(totalSize));
    	colaboradorManager.expects(once()).method("findList").with( ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradores));

    	areaOrganizacionalManager.expects(once()).method("findAllListAndInativa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));

    	assertEquals(action.mudancaFuncaoFiltro(), "success");
    }

    public void testPrepareRelatorioQtd()
    {
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<Estabelecimento>()));
    	assertEquals("success",action.prepareRelatorioQtdPorFuncao());
    	assertEquals(0, action.getEstabelecimentos().size());
    }
    
    public void testGerarRelatorioQtdPorFuncao()
    {
    	Collection<QtdPorFuncaoRelatorio> qtdPorFuncaos = new ArrayList<QtdPorFuncaoRelatorio>();
    	
    	qtdPorFuncaos.add(new QtdPorFuncaoRelatorio("Mecânico", 5, 0));
    	qtdPorFuncaos.add(new QtdPorFuncaoRelatorio("Motorista", 13, 1));
    	qtdPorFuncaos.add(new QtdPorFuncaoRelatorio("Auxiliar de RH", 1, 20));
    	
    	action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
    	action.setData(new Date());
    	
		manager.expects(once()).method("montaRelatorioQtdPorFuncao").with(eq(action.getEmpresaSistema()), eq(action.getEstabelecimento()), eq(action.getData())).will(returnValue(qtdPorFuncaos));
    	
    	assertEquals("success", action.gerarRelatorioQtdPorFuncao());
    	assertEquals(40 , action.getParametros().get("QTD_TOTAL"));
    }
    
    public void testGerarRelatorioQtdPorFuncaoException()
    {
    	manager.expects(once()).method("montaRelatorioQtdPorFuncao").will(throwException(new NullPointerException()));
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<Estabelecimento>()));
    	assertEquals("input", action.gerarRelatorioQtdPorFuncao());
    }
    
    public void testGetSet() throws Exception
    {
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);

    	Collection<Funcao> funcaos = new ArrayList<Funcao>();

    	action.setFuncao(funcao);
    	action.setFuncaos(funcaos);

    	assertEquals(action.getFuncao(), funcao);
    	assertEquals(action.getFuncaos(), funcaos);

    	funcao = null;
    	action.setFuncao(funcao);
    	assertTrue(action.getFuncao() instanceof Funcao);

    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	action.setColaboradors(colaboradors);

    	assertEquals(action.getColaboradors(), colaboradors);

    	String cpfBusca = "";
    	action.setCpfBusca(cpfBusca);

    	assertEquals(action.getCpfBusca(), cpfBusca);

    	String nomeBusca = "";
    	action.setNomeBusca(nomeBusca);

    	assertEquals(action.getNomeBusca(), nomeBusca);

    	AreaOrganizacional areaBusca = new AreaOrganizacional();
    	areaBusca.setId(1L);
    	action.setAreaBusca(areaBusca);

    	assertEquals(action.getAreaBusca(), areaBusca);

    	int page = 1;
    	action.setPage(page);

    	assertEquals(action.getPage(), page);

    	Integer totalSize = 1;
    	action.setTotalSize(totalSize);

    	assertEquals(action.getTotalSize(), totalSize.intValue());

    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	action.setAreas(areas);

    	assertEquals(action.getAreas(), areas);

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setId(1L);
    	action.setColaborador(colaborador);
    	assertEquals(action.getColaborador(), colaborador);

    	HistoricoColaborador historicoColaborador = new HistoricoColaborador();
    	action.setHistoricoColaborador(historicoColaborador);

    	assertEquals(action.getHistoricoColaborador(), historicoColaborador);
    	
    	action.getDataSource();
    }
}