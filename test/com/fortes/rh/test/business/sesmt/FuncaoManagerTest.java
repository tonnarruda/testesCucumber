package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.sesmt.FuncaoManagerImpl;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class,SpringUtil.class})
public class FuncaoManagerTest 
{
	private FuncaoManagerImpl funcaoManager = new FuncaoManagerImpl();
	private FuncaoDao funcaoDao;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private RiscoFuncaoManager riscoFuncaoManager;

	@Before
    public void setUp() throws Exception
    {
        funcaoDao = mock(FuncaoDao.class);
        funcaoManager.setDao(funcaoDao);
        
        historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
        riscoFuncaoManager = mock(RiscoFuncaoManager.class);
        
        PowerMockito.mockStatic(SpringUtil.class);
        PowerMockito.mockStatic(SecurityUtil.class);
        
        BDDMockito.given(SpringUtil.getBean("historicoFuncaoManager")).willReturn(historicoFuncaoManager);
        BDDMockito.given(SpringUtil.getBean("historicoColaboradorManager")).willReturn(historicoColaboradorManager);
        BDDMockito.given(SpringUtil.getBean("riscoFuncaoManager")).willReturn(riscoFuncaoManager);
    }
    
	@Test
    public void testGetCount()
	{
    	Collection<Funcao> funcaos = new ArrayList<Funcao>();
    	Long empresaId = 1L;
    	when(funcaoDao.getCount(empresaId, "")).thenReturn(funcaos.size());
    	assertEquals(funcaoManager.getCount(empresaId, "").intValue(), funcaos.size());
	}

	@Test
	public void testFindByEmpresaComPaginacao()throws Exception
	{
		Long empresaId = 1L;
		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		Funcao f1 = new Funcao();
		f1.setId(1L);

		Funcao f2 = new Funcao();
		f2.setId(2L);

		funcaos.add(f1);
		funcaos.add(f2);

		when(funcaoDao.findByEmpresa(eq(1), eq(15), eq(empresaId), eq(""))).thenReturn(funcaos);
		Collection<Funcao> retorno2 = funcaoManager.findByEmpresa(1, 15, empresaId, "");

		assertEquals(funcaos, retorno2);
	}

	@Test
	public void testFindByEmpresa()throws Exception
	{
		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		when(funcaoDao.findByEmpresa(eq(1L))).thenReturn(funcaos);
		Collection<Funcao> funcaoRetorno = funcaoManager.findByEmpresa(1L);
		assertEquals(funcaos, funcaoRetorno);
	}
	
	@Test
	public void testFindByIdProjection()
	{
		Funcao funcao = FuncaoFactory.getEntity(1L);
		
		when(funcaoDao.findByIdProjection(eq(funcao.getId()))).thenReturn(funcao);

		assertEquals(funcao, funcaoManager.findByIdProjection(funcao.getId()));
	}

	@Test
	public void testGetIdsFuncoes()throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setId(1L);
		f1.setNome("F1");

		Funcao f2 = new Funcao();
		f2.setId(2L);
		f2.setNome("F2");

		Collection<Long> colLong = new ArrayList<Long>();
		colLong.add(f1.getId());
		colLong.add(f2.getId());

		HistoricoColaborador hc1 = new HistoricoColaborador();
		hc1.setId(3L);
		hc1.setFuncao(f1);

		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setId(4L);
		hc2.setFuncao(f2);

		HistoricoColaborador hc3 = new HistoricoColaborador();
		hc3.setId(5L);
		hc3.setFuncao(f2);

		Collection<HistoricoColaborador> colhc = new ArrayList<HistoricoColaborador>();
		colhc.add(hc1);
		colhc.add(hc2);
		colhc.add(hc3);

		Collection<Long> idsRetorno = funcaoManager.getIdsFuncoes(colhc);
		assertEquals(colLong.size(),idsRetorno.size());
	}
	
	@Test
	public void testGetQtdColaboradorByFuncao()
	{
		Funcao funcao1 = FuncaoFactory.getEntity(1L);
		funcao1.setNome("Programador");

		Funcao funcao2 = FuncaoFactory.getEntity(2L);
		funcao2.setNome("Arquiteto");
		
		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		funcaos.add(funcao1);
		funcaos.add(funcao2);
		
		Date data = new Date();
		Collection<Object[]> retorno = new ArrayList<Object[]>();
		retorno.add(new Object[]{1L, "Motorista", 1, null});
		retorno.add(new Object[]{1L, "Motorista", null, 1});
		retorno.add(new Object[]{2L, "Manobrista", null, 1});
		
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(3L);
		
		when(funcaoDao.getQtdColaboradorByFuncao(eq(empresa.getId()), anyLong(), eq(data), eq('T'))).thenReturn(retorno);
		Collection<QtdPorFuncaoRelatorio> funcoesComTotalHomens_E_Mulheres = funcaoManager.getQtdColaboradorByFuncao(empresa.getId(), estabelecimento.getId(), data, 'T');
		assertEquals(2, funcoesComTotalHomens_E_Mulheres.size());
		
		QtdPorFuncaoRelatorio qtdPorFuncaoRelatorio1 = ((QtdPorFuncaoRelatorio)funcoesComTotalHomens_E_Mulheres.toArray()[0]);
		assertEquals((Long)1L, qtdPorFuncaoRelatorio1.getFuncaoId());
		assertEquals((Integer)1, qtdPorFuncaoRelatorio1.getQtdHomens());
		assertEquals((Integer)1, qtdPorFuncaoRelatorio1.getQtdMulheres());
		
		QtdPorFuncaoRelatorio qtdPorFuncaoRelatorio2 = ((QtdPorFuncaoRelatorio)funcoesComTotalHomens_E_Mulheres.toArray()[1]);
		assertEquals((Long)2L, qtdPorFuncaoRelatorio2.getFuncaoId());
		assertNull(qtdPorFuncaoRelatorio2.getQtdHomens());
		assertEquals((Integer)1, qtdPorFuncaoRelatorio2.getQtdMulheres());
	}
	
	@Test
    public void testPopulaCheckBox()
    {
    	Funcao fun1 = FuncaoFactory.getEntity(1L);
    	Funcao fun2 = FuncaoFactory.getEntity(2L);
    	
    	when(funcaoDao.findAll()).thenReturn(Arrays.asList(fun1, fun2));
    	assertEquals(2, funcaoManager.populaCheckBox().size());
    	
    }
	
	@Test
    public void testPopulaCheckBoxException()
    {
		doThrow(new ObjectNotFoundException("","")).when(funcaoDao).findAll();
    	assertEquals(0, funcaoManager.populaCheckBox().size());
    }
	
	@Test
	public void testRemoveFuncao() throws Exception
	{
		Funcao funcao = FuncaoFactory.getEntity(32L);
		
		HistoricoFuncao hist1 = new HistoricoFuncao();
		hist1.setId(1L);

		HistoricoFuncao hist2 = new HistoricoFuncao();
		hist2.setId(2L);
		
		when(historicoFuncaoManager.findByFuncao(funcao.getId())).thenReturn(Arrays.asList(hist1, hist2));
		
		funcaoManager.removeFuncao(funcao);
	}
	
	@Test
	public void testRemoveFuncaoException() 
	{
		Funcao funcao = FuncaoFactory.getEntity(32L);
		
		HistoricoFuncao hist1 = new HistoricoFuncao();
		hist1.setId(1L);

		HistoricoFuncao hist2 = new HistoricoFuncao();
		hist2.setId(2L);
		
		doThrow(new ObjectNotFoundException("","")).when(historicoFuncaoManager).findByFuncao(funcao.getId());
		
		Exception ex= null;
		
		try {
			funcaoManager.removeFuncao(funcao);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNotNull(ex);
	}
}