package com.fortes.rh.web.action.externo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ExternoActionTest
{
	private ExternoAction externoAction = new ExternoAction();
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AnuncioManager anuncioManager;
	
	@Before
	public void setUp() throws Exception
    {
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		externoAction.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        
		anuncioManager = mock(AnuncioManager.class);
		externoAction.setAnuncioManager(anuncioManager);
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
    }

	@Test
	public void testPrepareListAnuncio() throws Exception
	{ 
		Anuncio anuncio1 = new Anuncio();
		Anuncio anuncio2 = new Anuncio();
		
		Collection<Anuncio> anuncios = Arrays.asList(anuncio1,anuncio2);
		
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharCandidatos(false);
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
		when(anuncioManager.findAnunciosSolicitacaoAberta(null)).thenReturn(anuncios);
		
		assertEquals(Action.SUCCESS, externoAction.prepareListAnuncio());
	}
	
	@Test
	public void testPrepareListAnuncioComSession() throws Exception
	{ 
		Anuncio anuncio1 = new Anuncio();
		Anuncio anuncio2 = new Anuncio();
		externoAction.setEmpresaId(1L);
		
		Collection<Anuncio> anuncios = Arrays.asList(anuncio1,anuncio2);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharCandidatos(true);
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
		when(anuncioManager.findAnunciosSolicitacaoAberta(1l)).thenReturn(anuncios);
		
		assertEquals(Action.SUCCESS, externoAction.prepareListAnuncio());
	}
	
}