package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.FuncaoListAction;
import com.fortes.web.tags.CheckBox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class,RelatorioUtil.class})
public class FuncaoListActionTest 
{
	private FuncaoListAction action;
	private FuncaoManager manager;
	private EstabelecimentoManager estabelecimentoManager;
	private HistoricoFuncaoManager historicoFuncaoManager;

	@Before
    public void setUp()
    {
        action = new FuncaoListAction();
        manager = mock(FuncaoManager.class);
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);

        action.setFuncaoManager(manager);
        action.setEstabelecimentoManager(estabelecimentoManager);
        action.setHistoricoFuncaoManager(historicoFuncaoManager);

        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(RelatorioUtil.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    @Test
    public void testDelete() throws Exception
    {
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);
    	action.setFuncao(funcao);

    	assertEquals(action.delete(), "success");
    }

    @Test
    public void testList() throws Exception
    {
		Funcao f1 = FuncaoFactory.getEntity(1L);
		Funcao f2 = FuncaoFactory.getEntity(2L);

		Collection<Funcao> funcaos = Arrays.asList(f1, f2);
		when(manager.getCount(action.getEmpresaSistema().getId(), action.getFuncao().getNome())).thenReturn(funcaos.size());
		when(manager.findByEmpresa(action.getPage(), action.getPagingSize(), action.getEmpresaSistema().getId(), action.getFuncao().getNome())).thenReturn(funcaos);

    	assertEquals(action.list(), "success");
    	assertEquals(action.getFuncaos(), funcaos);
    }

    @Test
    public void testPrepareRelatorioQtd()
    {
    	when(estabelecimentoManager.findAllSelect(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<Estabelecimento>());
    	assertEquals("success",action.prepareRelatorioQtdPorFuncao());
    	assertEquals(0, action.getEstabelecimentos().size());
    }
    
    @Test
    public void testGerarRelatorioQtdPorFuncao()
    {
    	Collection<QtdPorFuncaoRelatorio> qtdPorFuncaos = new ArrayList<QtdPorFuncaoRelatorio>();
    	
    	qtdPorFuncaos.add(new QtdPorFuncaoRelatorio(1L, "Mecânico", 5, 0));
    	qtdPorFuncaos.add(new QtdPorFuncaoRelatorio(2L, "Motorista", 13, 1));
    	qtdPorFuncaos.add(new QtdPorFuncaoRelatorio(3L, "Auxiliar de RH", 1, 20));
    	
    	action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
    	action.setData(new Date());
    	action.setTipoAtivo('T');
    	
		when(manager.getQtdColaboradorByFuncao(eq(action.getEmpresaSistema().getId()), eq(action.getEstabelecimento().getId()), eq(action.getData()), eq('T'))).thenReturn(qtdPorFuncaos);
    	
    	assertEquals("success", action.gerarRelatorioQtdPorFuncao());
    }
    
    @Test
    public void testGerarRelatorioQtdPorFuncaoException()
    {
    	action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
    	
    	doThrow(new NullPointerException()).when(manager).getQtdColaboradorByFuncao(anyLong(), anyLong(), any(Date.class), anyChar());
    	when(estabelecimentoManager.findAllSelect(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<Estabelecimento>());
    	
    	assertEquals("input", action.gerarRelatorioQtdPorFuncao());
    }
    
    @Test
	public void testPrepareRelatorioExamesPorFuncao()
	{
		when(manager.populaCheckBox()).thenReturn(new ArrayList<CheckBox>());
		assertEquals("success", action.prepareRelatorioExamesPorFuncao());
	}
    
	public void testRelatorioExamesPorFuncaoColecaoVazia()
    {
		when(historicoFuncaoManager.findByFuncoes(any(Date.class), any(Long[].class))).thenReturn(new ArrayList<Funcao>());
    	when(manager.populaCheckBox()).thenReturn(new ArrayList<CheckBox>());
    	assertEquals("input", action.relatorioExamesPorFuncao());
    	assertEquals("Não existem dados para o relatório", action.getActionMessages().iterator().next());
    }
    
	@Test
    public void testRelatorioExamesPorFuncaoException()
    {
		when(historicoFuncaoManager.findByFuncoes(any(Date.class), any(Long[].class))).thenReturn(null);
    	when(manager.populaCheckBox()).thenReturn(new ArrayList<CheckBox>());
    	assertEquals("input", action.relatorioExamesPorFuncao());
    	assertEquals("Erro ao gerar relatório.", action.getActionErrors().iterator().next());
    }
    
	@Test
    public void testRelatorioExamesPorFuncao()
    {
    	Collection<Funcao> funcoes = Arrays.asList(FuncaoFactory.getEntity(1L));
    	when(historicoFuncaoManager.findByFuncoes(any(Date.class), any(Long[].class))).thenReturn(funcoes);
    	assertEquals("success", action.relatorioExamesPorFuncao());
    }
    
	@Test
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