package com.fortes.rh.test.business.geral;

import static org.junit.Assert.*; 
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.CartaoManagerImpl;
import com.fortes.rh.dao.geral.CartaoDao;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.util.ArquivoUtil;

public class CartaoManagerTest
{
	private CartaoManagerImpl cartaoManager = new CartaoManagerImpl();
	private CartaoDao cartaoDao;

    @Before
	public void setUp() throws Exception{
    	cartaoDao = mock(CartaoDao.class);
    	cartaoManager.setDao(cartaoDao);
    	
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
	}
    
    @Test
    public void testFindByEmpresaIdAndTipo(){
    	Long empresaId = 1L;
    	String tipoCartao = TipoCartao.ANIVERSARIO;
    	
    	cartaoManager.findByEmpresaIdAndTipo(empresaId, tipoCartao);
    	verify(cartaoDao, times(1)).findByEmpresaIdAndTipo(eq(empresaId), eq(tipoCartao));
    } 
    
    @Test
    public void testFindByEmpresaId(){
    	Long empresaId = 1L;
    	
    	cartaoManager.findByEmpresaId(empresaId);
    	verify(cartaoDao, times(1)).findByEmpresaId(eq(empresaId));
    } 
    
    @Test
    public void testSaveImagemCartao()
	{
    	Exception erro = null;
    	File file = new File();
    	file.setName("Teste");
    	Cartao cartao = new Cartao();
    	cartao.setFile(file);
    	
    	try {
    		cartaoManager.saveImagemCartao(cartao, "logoEmpresas");
		} catch (Exception e) {
			erro = e;
			e.printStackTrace();
		}
    	assertNull("Fluxo de execução ok", erro);
	}
}