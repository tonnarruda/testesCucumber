package com.fortes.rh.test.util.validacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.validacao.ValidacaoQuantidadeConstraints;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class ValidacaoQuantidadeConstraintsTest 
{
	private ValidacaoQuantidadeConstraints validacaoQuantidadeConstraints;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	@Before
	public void setUp() throws Exception
    {
		validacaoQuantidadeConstraints = new ValidacaoQuantidadeConstraints();
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
				
    	PowerMockito.mockStatic(SpringUtil.class);
    	BDDMockito.given(SpringUtil.getBean("parametrosDoSistemaManager")).willReturn(parametrosDoSistemaManager);
    }
	
	@Test
	public void execute() {
		
		try {
			int qtdConstraintsCerta = 426;
			
			ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
			parametrosDoSistema.setQuantidadeConstraints(qtdConstraintsCerta);
			
			when(parametrosDoSistemaManager.getQuantidadeConstraintsDoBanco()).thenReturn(qtdConstraintsCerta);
			validacaoQuantidadeConstraints.execute(parametrosDoSistema );
			
			assertTrue(true);
		} catch (FortesException e) {
			fail("Erro na validação de quantidade constraints.");			
		}
	}
	
	@Test
	public void executeException() {
		try {
			int qtdConstraintsCerta = 426;
			int qtdConstraintsErrada = 425;

			ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
			parametrosDoSistema.setQuantidadeConstraints(qtdConstraintsCerta);
			
			when(parametrosDoSistemaManager.getQuantidadeConstraintsDoBanco()).thenReturn(qtdConstraintsErrada);
			validacaoQuantidadeConstraints.execute(parametrosDoSistema );
			
			fail("Erro na validação de quantidade constraints.");			
		} catch (FortesException e) {
			assertTrue(true);
			assertEquals("A estrutura do banco de dados está diferente da esperada. É de extrema importância que você entre em contato com a Fortes Tecnologia para que o problema seja analisado.", e.getMessage());
		}
		
		
	}
	
}
