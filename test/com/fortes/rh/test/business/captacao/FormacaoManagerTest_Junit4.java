package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.FormacaoManagerImpl;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;

public class FormacaoManagerTest_Junit4
{
	private FormacaoManagerImpl formacaoManager = new FormacaoManagerImpl();
	private FormacaoDao formacaoDao;

	@Before
    public void setUp() throws Exception
    {
        formacaoDao = mock(FormacaoDao.class);
        formacaoManager.setDao(formacaoDao);
    }

	@Test
	public void testRetornaListaSemDuplicados(){
		Formacao formacao1 = FormacaoFactory.getEntity("Administração", "2017", "UFC", 'C', 'G', 1L);
		Formacao formacao2 = FormacaoFactory.getEntity("Administração", "2017", "UFC", 'C', 'G', 1L);
		
		Collection<Formacao> formacoes = Arrays.asList(formacao1, formacao2);
		Collection<Formacao> formacoesRetorno = formacaoManager.retornaListaSemDuplicados(formacoes);
		
		assertEquals(1, formacoesRetorno.size());
	}
}
