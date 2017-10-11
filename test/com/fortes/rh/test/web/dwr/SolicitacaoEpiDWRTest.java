package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.SolicitacaoEpiDWR;
import com.fortes.web.tags.CheckBox;

public class SolicitacaoEpiDWRTest
{
	private SolicitacaoEpiDWR solicitacaoEpiDWR;
	private SolicitacaoEpiManager solicitacaoEpiManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;

	@Before
	public void setUp() throws Exception
	{
		solicitacaoEpiDWR = new SolicitacaoEpiDWR();

		solicitacaoEpiManager = mock(SolicitacaoEpiManager.class);
		solicitacaoEpiDWR.setSolicitacaoEpiManager(solicitacaoEpiManager);
		
		solicitacaoEpiItemManager = mock(SolicitacaoEpiItemManager.class);
		solicitacaoEpiDWR.setSolicitacaoEpiItemManager(solicitacaoEpiItemManager);
	}
	
	@Test
	public void testValidaDataDevolucao()
	{
		String data = DateUtil.formataDiaMesAno(new Date());
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdASerDevolvida = null;
		Long solicitacaoEpiId = 2L;
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(solicitacaoEpiId);
		solicitacaoEpi.setData(new Date());
		
		when(solicitacaoEpiManager.findEntidadeComAtributosSimplesById(solicitacaoEpiId)).thenReturn(solicitacaoEpi);		
		when(solicitacaoEpiItemManager.validaDataDevolucao(DateUtil.criarDataDiaMesAno(data), solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, qtdASerDevolvida,solicitacaoEpi.getData())).thenReturn(null);
		
		assertNull(solicitacaoEpiDWR.validaDataDevolucao(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, qtdASerDevolvida, solicitacaoEpiId));
	}
	
	@Test
	public void testValidaDataDevolucaoDataInvalida()
	{
		String data = " /  /  ";
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdASerDevolvida = null;
		Long solicitacaoEpiId = 2L;
		
		assertNull(solicitacaoEpiDWR.validaDataDevolucao(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, qtdASerDevolvida, solicitacaoEpiId));
	}

	@Test
	public void testGetByColaboradorId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1l);
		
		SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity(2l);
		solicitacaoEpi1.setColaborador(colaborador);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity(2l);
		solicitacaoEpi2.setColaborador(colaborador);
		
		Collection<SolicitacaoEpi> solicitacoesEpis = Arrays.asList(solicitacaoEpi1, solicitacaoEpi2);
		
		when(solicitacaoEpiManager.findByColaboradorId(colaborador.getId())).thenReturn(solicitacoesEpis);

		Collection<CheckBox> checkBoxSolicitacoesEpis = solicitacaoEpiDWR.getByColaboradorId(colaborador.getId());
		
		assertEquals(solicitacoesEpis.size(),checkBoxSolicitacoesEpis.size());
		assertEquals(solicitacaoEpi1.getId().toString(),((CheckBox)checkBoxSolicitacoesEpis.toArray()[0]).getId());
		assertEquals(solicitacaoEpi2.getId().toString(),((CheckBox)checkBoxSolicitacoesEpis.toArray()[1]).getId());
	}
}
	