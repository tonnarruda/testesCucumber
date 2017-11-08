package com.fortes.rh.test.business.sesmt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.ExameManagerImpl;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;

public class ExameManagerTest_JUnit4
{
	private ExameManagerImpl exameManager = new ExameManagerImpl();
	private ExameDao exameDao = null;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	@Before
	public void setUp() throws Exception
    {
        exameDao = mock(ExameDao.class);
        exameManager.setDao(exameDao);
        
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
        exameManager.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);
    }

	@Test
    public void testFindRelatorioExamesPrevistos() throws Exception
    {
    	Date hoje = new Date();
    	Calendar doisMesesAtras = Calendar.getInstance();
    	doisMesesAtras.add(Calendar.MONTH, -2);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	Collection<ExamesPrevistosRelatorio> colecao = new ArrayList<ExamesPrevistosRelatorio>();

    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Atual = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","","","",doisMesesAtras.getTime(),DateUtil.incrementaMes(new Date(), -1),1, "PERIODICO",1L,"EST");
    	colecao.add(examesPrevistosColaborador1Exame1Atual);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	when(exameDao.findExamesPeriodicosPrevistos(empresa.getId(), doisMesesAtras.getTime(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, false, false, false)).thenReturn(colecao);
    	
    	Collection<ExamesPrevistosRelatorio> resultado = null;

    	Exception exception = null;
    	try
    	{
    		resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), doisMesesAtras.getTime(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, 'N', false, false, false );
    	}
    	catch (ColecaoVaziaException e)
    	{
    		exception = e;
    	}

    	assertEquals(1, resultado.size());
    	assertNull(exception);
    }  
	
	@Test
    public void testFindRelatorioExamesPrevistosExibirExamesNaoRealizados() throws Exception
    {
    	Date hoje = new Date();
    	Date doisMesesAtras = DateUtil.incrementaMes(hoje, -2);
    	Date tresMesesAtras = DateUtil.incrementaMes(hoje, -3);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Atual = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","","","",doisMesesAtras,DateUtil.incrementaMes(hoje, -1),1, "PERIODICO",1L,"EST");
    	Collection<ExamesPrevistosRelatorio> colecao = new ArrayList<ExamesPrevistosRelatorio>();
    	colecao.add(examesPrevistosColaborador1Exame1Atual);
    	
    	ExamesPrevistosRelatorio examesPrevistosColaborador1ExameNaoRealizado = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","","","",tresMesesAtras,null,1, "PERIODICO",1L,"EST");
    	examesPrevistosColaborador1ExameNaoRealizado.setDataProximoExame(tresMesesAtras);
    	Collection<ExamesPrevistosRelatorio> examesNaoRealizados = new ArrayList<ExamesPrevistosRelatorio>();
    	examesNaoRealizados.add(examesPrevistosColaborador1ExameNaoRealizado);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	when(exameDao.findExamesPeriodicosPrevistos(empresa.getId(), doisMesesAtras, hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, false, false, false)).thenReturn(colecao);
    	when(exameDao.findExamesPeriodicosPrevistosNaoRealizados(empresa.getId(), doisMesesAtras, hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, false, false)).thenReturn(examesNaoRealizados);
    	
    	Collection<ExamesPrevistosRelatorio> resultado = null;

    	Exception exception = null;
    	try
    	{
    		resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), doisMesesAtras, hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, 'N', false, false, true );
    	}
    	catch (ColecaoVaziaException e)
    	{
    		exception = e;
    	}

    	assertEquals(2, resultado.size());
    	assertEquals(tresMesesAtras, ((ExamesPrevistosRelatorio) resultado.toArray()[0]).getDataProximoExame());
    	assertEquals("-", ((ExamesPrevistosRelatorio) resultado.toArray()[0]).getDataUltimoExame());
    	assertNull(exception);
    } 

	@Test
    public void testFindRelatorioExamesPrevistosAgruparPorArea() throws Exception{
    	boolean imprimirAfastados=true;
    	
    	Date hoje = new Date();
    	Calendar doisMesesAtras = Calendar.getInstance();
    	doisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar tresMesesAtras = Calendar.getInstance();
    	tresMesesAtras.add(Calendar.MONTH, -3);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1 = new ExamesPrevistosRelatorio(1L,1L,2L,"Cargo","","Colaborador1","","",tresMesesAtras.getTime(),tresMesesAtras.getTime(),1, "PERIODICO",1L,"EST");
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame2 = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","Colaborador1","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO",1L,"EST");
    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2 = new ExamesPrevistosRelatorio(2L,2L,2L,"Cargo","","Colaborador2","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO",1L,"EST");
    	Collection<ExamesPrevistosRelatorio> colecao = Arrays.asList(examesPrevistosColaborador1Exame1, examesPrevistosColaborador1Exame2, examesPrevistosColaborador2Exame2);
    	
    	examesPrevistosColaborador1Exame1.getAreaOrganizacional().setNome("Financeiro");
    	examesPrevistosColaborador1Exame2.getAreaOrganizacional().setNome("Financeiro");
    	examesPrevistosColaborador2Exame2.getAreaOrganizacional().setNome("Administração");
    	
    	Collection<ExamesPrevistosRelatorio> colecaoFiltrada = Arrays.asList(examesPrevistosColaborador1Exame1, examesPrevistosColaborador1Exame2,examesPrevistosColaborador2Exame2);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	when(exameDao.findExamesPeriodicosPrevistos(empresa.getId(), doisMesesAtras.getTime(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, imprimirAfastados, false, false)).thenReturn(colecao);
    	
    	Collection<ExamesPrevistosRelatorio> resultado = null;
    	
    	Exception exception = null;
    	try{
    		resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), doisMesesAtras.getTime(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, 'A', imprimirAfastados, false, false );
    	}catch (ColecaoVaziaException e){
    		exception = e;
    	}
    	
    	assertEquals(3, resultado.size());
    	assertEquals("Administração", resultado.iterator().next().getAreaOrganizacional().getNome());
    	assertNull(exception);
    }
    
	@Test
    public void testFindRelatorioExamesPrevistosAgruparPorEstabelecimento() throws Exception
    {
    	
    	boolean imprimirAfastados=true;
    	
    	Date hoje = new Date();
    	Calendar doisMesesAtras = Calendar.getInstance();
    	doisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar tresMesesAtras = Calendar.getInstance();
    	tresMesesAtras.add(Calendar.MONTH, -3);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	Collection<ExamesPrevistosRelatorio> colecao = new ArrayList<ExamesPrevistosRelatorio>();
    	
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1 = new ExamesPrevistosRelatorio(1L,1L,2L,"Cargo","","Colaborador1","","",tresMesesAtras.getTime(),tresMesesAtras.getTime(),1, "PERIODICO",1L,"Matriz");
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame2 = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","Colaborador1","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO",1L,"Matriz");
    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2 = new ExamesPrevistosRelatorio(2L,2L,2L,"Cargo","","Colaborador2","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO",1L,"Unidade");
    	colecao.add(examesPrevistosColaborador2Exame2);
    	colecao.add(examesPrevistosColaborador1Exame1);
    	colecao.add(examesPrevistosColaborador1Exame2);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	when(exameDao.findExamesPeriodicosPrevistos(empresa.getId(), doisMesesAtras.getTime(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, imprimirAfastados, false, false)).thenReturn(colecao);
    	Collection<ExamesPrevistosRelatorio> resultado = null;
    	
    	Exception exception = null;
    	try
    	{
    		resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), doisMesesAtras.getTime(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, 'A', imprimirAfastados, false, false );
    	}
    	catch (ColecaoVaziaException e)
    	{
    		exception = e;
    	}
    	
    	assertEquals(3, resultado.size());
    	assertEquals("Matriz", resultado.iterator().next().getEstabelecimento().getNome());
    	assertNull(exception);
    }
    
	@Test
    public void testFindRelatorioExamesPrevistosColecaoVaziaException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	when(exameDao.findExamesPeriodicosPrevistos(empresa.getId(), null, null, new Long[]{}, new Long[]{}, new Long[]{}, new Long[]{}, false, false, false)).thenReturn(new ArrayList<ExamesPrevistosRelatorio>());

		assertTrue(exameManager.findRelatorioExamesPrevistos(empresa.getId(), null, null, null, null, null,null, 'N', false, false, false).isEmpty());
    }
	
	
}