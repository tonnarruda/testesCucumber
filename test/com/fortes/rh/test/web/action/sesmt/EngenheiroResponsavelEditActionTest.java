package com.fortes.rh.test.web.action.sesmt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.EngenheiroResponsavelFactory;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.EngenheiroResponsavelEditAction;
import com.opensymphony.xwork.Action;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CheckListBoxUtil.class)
public class EngenheiroResponsavelEditActionTest 
{
	private EngenheiroResponsavelEditAction action;
	private EngenheiroResponsavelManager engenheiroResponsavelManager;
	private EstabelecimentoManager estabelecimentoManager;
	
	@Before
	public void setUp() throws Exception
	{
		action = new EngenheiroResponsavelEditAction();
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		engenheiroResponsavelManager = Mockito.mock(EngenheiroResponsavelManager.class);
		action.setEngenheiroResponsavelManager(engenheiroResponsavelManager);
		
		estabelecimentoManager = Mockito.mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		PowerMockito.mockStatic(CheckListBoxUtil.class);
	}

	@Test
	public void testExecute() throws Exception
	{
		Assert.assertEquals(Action.SUCCESS, action.execute());
	}

	@Test
	public void testPrepareInsert() throws Exception
	{
		Mockito.when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);
		
		Assert.assertEquals(Action.SUCCESS, action.prepareInsert());
	}

	@Test
    public void testPrepareUpdateComEngenheiro() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = EngenheiroResponsavelFactory.getEntity(1L);
    	engenheiroResponsavel.setEmpresa(action.getEmpresaSistema());
    	
    	action.setEngenheiroResponsavel(engenheiroResponsavel);

		Mockito.when(engenheiroResponsavelManager.findByIdProjection(engenheiroResponsavel.getId())).thenReturn(engenheiroResponsavel);
		Mockito.when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);

    	Assert.assertEquals(Action.SUCCESS, action.prepareUpdate());
    }
	
	@Test
	public void testPrepareUpdateComEngenheiroNulo() throws Exception
	{
		EngenheiroResponsavel engenheiroResponsavel = null;
		action.setEngenheiroResponsavel(engenheiroResponsavel);
		
		Mockito.when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);
		
    	Assert.assertEquals(Action.ERROR, action.prepareUpdate());
    	Assert.assertTrue(action.getActionWarnings().iterator().next().toString().startsWith("O engenheiro solicitado não existe na empresa"));
		
	}

	@Test
    public void testInsertEngenheiroResponsavelTodosEstabelecimentos() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = EngenheiroResponsavelFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
    	engenheiroResponsavel.setId(1L);
    	action.setEngenheiroResponsavel(engenheiroResponsavel);

    	Mockito.when(engenheiroResponsavelManager.save(engenheiroResponsavel)).thenReturn(engenheiroResponsavel);

		Assert.assertEquals(Action.SUCCESS, action.insert());
    	Assert.assertTrue(action.getActionSuccess().iterator().next().toString().equals("Engenheiro responsável gravado com sucesso."));
    }
	
	@Test
	public void testInsertEngenheiroResponsavelAlgunsEstabelecimentos() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		
		action.setEstabelecimentosCheck(estabelecimentosCheck);
		
		EngenheiroResponsavel engenheiroResponsavel = EngenheiroResponsavelFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos);
		engenheiroResponsavel.setId(1L);
		action.setEngenheiroResponsavel(engenheiroResponsavel);
		
		Mockito.when(engenheiroResponsavelManager.save(engenheiroResponsavel)).thenReturn(engenheiroResponsavel);
    	Mockito.when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);
		
		Assert.assertEquals(Action.SUCCESS, action.insert());
		Assert.assertTrue(action.getActionSuccess().iterator().next().toString().equals("Engenheiro responsável gravado com sucesso."));
	}

	@Test
    public void testUpdateTodosEstabelecimentos() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = EngenheiroResponsavelFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
    	engenheiroResponsavel.setId(1L);
    	action.setEngenheiroResponsavel(engenheiroResponsavel);

    	Mockito.when(engenheiroResponsavelManager.save(engenheiroResponsavel)).thenReturn(engenheiroResponsavel);

		Assert.assertEquals(Action.SUCCESS, action.update());
    	Assert.assertTrue(action.getActionSuccess().iterator().next().toString().equals("Engenheiro responsável atualizado com sucesso."));
    }

	@Test
	public void testUpdateEngenheiroResponsavelAlgunsEstabelecimentos() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		
		action.setEstabelecimentosCheck(estabelecimentosCheck);
		
		EngenheiroResponsavel engenheiroResponsavel = EngenheiroResponsavelFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos);
		engenheiroResponsavel.setId(1L);
		action.setEngenheiroResponsavel(engenheiroResponsavel);
		
		Mockito.when(engenheiroResponsavelManager.save(engenheiroResponsavel)).thenReturn(engenheiroResponsavel);
    	Mockito.when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);
		
		Assert.assertEquals(Action.SUCCESS, action.update());
		Assert.assertTrue(action.getActionSuccess().iterator().next().toString().equals("Engenheiro responsável atualizado com sucesso."));
	}
}