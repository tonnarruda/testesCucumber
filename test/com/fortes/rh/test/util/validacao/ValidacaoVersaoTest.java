package com.fortes.rh.test.util.validacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.validacao.ValidacaoVersao;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArquivoUtil.class)
public class ValidacaoVersaoTest 
{
	private ValidacaoVersao validacaoVersao;
	
	@Before
	public void setUp() throws Exception
    {
		validacaoVersao = new ValidacaoVersao();
				
    	PowerMockito.mockStatic(ArquivoUtil.class);
    }
	
	@Test
	public void execute() {
		
		try {
			String versaoCerta = "1.1.181.213";
			
			ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
			parametrosDoSistema.setAppVersao(versaoCerta);
			
			when(ArquivoUtil.getVersao()).thenReturn(versaoCerta);
			validacaoVersao.execute(parametrosDoSistema );
			
			assertTrue(true);
		} catch (FortesException e) {
			fail("Erro na validação da versão.");			
		}
	}
	
	@Test
	public void executeException() {
		try {
			String versaoCerta = "1.1.181.213";
			String versaoErrada = "1.1.181.212";

			ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
			parametrosDoSistema.setAppVersao(versaoCerta);
			
			when(ArquivoUtil.getVersao()).thenReturn(versaoErrada);
			validacaoVersao.execute(parametrosDoSistema );
			
			fail("Erro na validação da versão.");			
		} catch (FortesException e) {
			assertTrue(true);
			assertEquals("A versão do banco de dados está incompatível com a versão da aplicação. Entre em contato com a Fortes Tecnologia.", e.getMessage());
		}
	}
}
