package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.geral.MotivoDemissaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.geral.MotivoDemissaoListAction;
import com.opensymphony.xwork.ActionContext;

public class MotivoDemissaoListActionTest
{
	private MotivoDemissaoListAction action;
	private MotivoDemissaoManager manager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	@Before
    public void setUp() throws Exception
    {
        action = new MotivoDemissaoListAction();
        manager = mock(MotivoDemissaoManager.class);
        colaboradorManager = mock(ColaboradorManager.class);
        empresaManager = mock(EmpresaManager.class);
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);

        action.setMotivoDemissaoManager(manager);
        action.setColaboradorManager(colaboradorManager);
        action.setEmpresaManager(empresaManager);
        action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	@Test
	public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

	@Test
    public void testList() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);
    	
       	MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity();
       	motivoDemissao.setMotivo("motivo");
       	motivoDemissao.setAtivo(true);
       	action.setMotivoDemissao(motivoDemissao);
       	
       	Collection<MotivoDemissao> motivosdemissoes = new ArrayList<MotivoDemissao>();
       	motivosdemissoes.add(motivoDemissao);
    	
    	when(manager.findMotivoDemissao(null, null, action.getEmpresaSistema().getId(), "motivo", true)).thenReturn(motivosdemissoes);
    	when(manager.findMotivoDemissao(1, 15, action.getEmpresaSistema().getId(), "motivo", true)).thenReturn(motivosdemissoes);
    	
    	assertEquals("success", action.list());
    	assertEquals(1, action.getMotivoDemissaos().size());
    }
	
	@Test
    public void testPrepareRelatorioMotivoDemissao() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);
    	
       	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(empresaManager.findEmpresasPermitidas(action.getCompartilharColaboradores(), action.getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_MOTIVO_DEMISSAO")).thenReturn(Arrays.asList(empresa));
    	
    	assertEquals("success", action.prepareRelatorioMotivoDemissao());
    }

	@Test
    public void testRelatorioMotivoDemissaoListaTrue() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);

    	Cabecalho cabecalho = new Cabecalho("Relatório de Motivos de Desligamento", "", "", "", "", "", "", false);

    	Map<String, Object> parametros = new HashMap<String, Object>();
    	parametros.put("CABECALHO", cabecalho);

    	action.setListaColaboradores(true);
    	action.setAgruparPor("M");

    	MotivoDemissaoQuantidade motivoDemissaoQuantidade = new MotivoDemissaoQuantidade();

    	Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades = new ArrayList<MotivoDemissaoQuantidade>();
    	motivoDemissaoQuantidades.add(motivoDemissaoQuantidade);
    	
    	when(manager.getParametrosRelatorio(action.getEmpresaSistema(), action.getDataIni(), action.getDataFim(), new Long[]{}, new Long[]{}, action.getParametros())).thenReturn(parametros);
    	when(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, action.getDataIni(), action.getDataFim(), "M", null)).thenReturn(ColaboradorFactory.getCollection());
    	
    	assertEquals("success", action.relatorioMotivoDemissao());

    	action.setListaColaboradores(true);
    	action.setAgruparPor("N");
    	
    	when(manager.getParametrosRelatorio(action.getEmpresaSistema(), action.getDataIni(), action.getDataFim(), new Long[]{}, new Long[]{}, action.getParametros())).thenReturn(parametros);
    	when(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, action.getDataIni(), action.getDataFim(), "N", null)).thenReturn(ColaboradorFactory.getCollection());
    	
    	assertEquals("successSemAgrupar", action.relatorioMotivoDemissao());
    	
    	action.setListaColaboradores(false);
    	action.setAgruparPor(null);
    	
    	when(manager.getParametrosRelatorio(action.getEmpresaSistema(), action.getDataIni(), action.getDataFim(), new Long[]{}, new Long[]{}, action.getParametros())).thenReturn(parametros);
    	when(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, action.getDataIni(), action.getDataFim(), null, null)).thenReturn(ColaboradorFactory.getCollection());
    	
    	assertEquals("successBasico", action.relatorioMotivoDemissao());
    	assertEquals("Relatório de Motivos de Desligamento", ((Cabecalho)action.getParametros().get("CABECALHO")).getTitulo());
    }

	@Test
    public void testImprimeRelatorioMotivoDemissaoBasico() throws Exception
    {
    	MotivoDemissaoQuantidade motivoDemissaoQuantidade = new MotivoDemissaoQuantidade();
    	
    	Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades = new ArrayList<MotivoDemissaoQuantidade>();
    	motivoDemissaoQuantidades.add(motivoDemissaoQuantidade);

    	when(colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(new Long[]{}, new Long[]{}, new Long[]{}, null, null, null)).thenReturn(motivoDemissaoQuantidades);
    	
    	assertEquals("successBasico", action.imprimeRelatorioMotivoDemissaoBasico());
    }

	@Test
    public void testImprimeRelatorioMotivoDemissaoBasicoException() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);

       	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	
    	when(colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(new Long[]{}, new Long[]{}, new Long[]{}, null, null, null)).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("","")));
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
    	when(empresaManager.findEmpresasPermitidas(true , action.getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_MOTIVO_DEMISSAO")).thenReturn(Arrays.asList(empresa));
    	
    	assertEquals("input", action.imprimeRelatorioMotivoDemissaoBasico());
    }

	@Test
    public void testImprimeRelatorioMotivoDemissao() throws Exception
    {
    	MotivoDemissaoQuantidade motivoDemissaoQuantidade = new MotivoDemissaoQuantidade();
    	
    	Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades = new ArrayList<MotivoDemissaoQuantidade>();
    	motivoDemissaoQuantidades.add(motivoDemissaoQuantidade);
    	action.setAgruparPor("M");
    	
    	when(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, action.getDataIni(), action.getDataFim(), "M", null)).thenReturn(ColaboradorFactory.getCollection());

    	assertEquals("success", action.imprimeRelatorioMotivoDemissao());
    }

	@Test
    public void testImprimeRelatorioMotivoDemissaoException() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);

    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		
    	when(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, action.getDataIni(), action.getDataFim(), null, null)).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("","")));
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
    	when(empresaManager.findEmpresasPermitidas(true , action.getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_MOTIVO_DEMISSAO")).thenReturn(Arrays.asList(empresa));
    	
    	assertEquals("input", action.imprimeRelatorioMotivoDemissao());
    }

    public void testGets() throws Exception
    {
    	action.setMotivoDemissao(new MotivoDemissao());
    	assertNotNull(action.getMotivoDemissao());

    	action.setMotivoDemissao(null);
    	assertNotNull(action.getMotivoDemissao());

    	action.getMotivoDemissaos();

    	action.setAreasCheck(new String[]{});
    	assertNotNull(action.getAreasCheck());

    	action.setEstabelecimentosCheck(new String[]{});
    	assertNotNull(action.getEstabelecimentosCheck());

    	action.setCargosCheck(new String[]{});
    	assertNotNull(action.getCargosCheck());

    	action.setDataFim(new Date());
    	assertNotNull(action.getDataFim());

    	action.setDataIni(new Date());
    	assertNotNull(action.getDataIni());

    	action.setListaColaboradores(true);
    	assertTrue(action.isListaColaboradores());

    	action.setAreasCheckList(null);
    	assertNull(action.getAreasCheckList());

    	action.setCargosCheckList(null);
    	assertNull(action.getCargosCheckList());

    	action.setEstabelecimentosCheckList(null);
    	assertNull(action.getEstabelecimentosCheckList());

    	assertNull(action.getColaboradores());
    }
}
