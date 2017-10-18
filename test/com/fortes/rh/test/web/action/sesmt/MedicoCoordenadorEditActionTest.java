package com.fortes.rh.test.web.action.sesmt;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.exception.ValidacaoAssinaturaException;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicoCoordenadorFactory;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.MedicoCoordenadorEditAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CheckListBoxUtil.class, ServletActionContext.class})
public class MedicoCoordenadorEditActionTest
{
	private MedicoCoordenadorEditAction action;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private EstabelecimentoManager estabelecimentoManager;

	@Before
	public void setUp() throws Exception
	{
		action = new MedicoCoordenadorEditAction();
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		medicoCoordenadorManager = mock(MedicoCoordenadorManager.class);
		action.setMedicoCoordenadorManager(medicoCoordenadorManager);

		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		PowerMockito.mockStatic(CheckListBoxUtil.class);
		PowerMockito.mockStatic(ServletActionContext.class);
	}

	@Test
	public void testExecute() throws Exception
	{
		Assert.assertEquals(Action.SUCCESS, action.execute());
	}

	@Test
	public void testPrepareInsert() throws Exception
	{
		when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);
		
		Assert.assertEquals(Action.SUCCESS, action.prepareInsert());
	}

	@Test
    public void testPrepareUpdateComMedico() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L);
    	medicoCoordenador.setEmpresa(action.getEmpresaSistema());
    	
    	action.setMedicoCoordenador(medicoCoordenador);

		when(medicoCoordenadorManager.findByIdProjection(medicoCoordenador.getId())).thenReturn(medicoCoordenador);
		when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);

    	Assert.assertEquals(Action.SUCCESS, action.prepareUpdate());
    }
	
	@Test
	public void testPrepareUpdateComEmpresaMedicoDiferente() throws Exception
	{
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L);
		medicoCoordenador.setEmpresa(EmpresaFactory.getEmpresa(2L));
		
		action.setMedicoCoordenador(medicoCoordenador);
		
		when(medicoCoordenadorManager.findByIdProjection(medicoCoordenador.getId())).thenReturn(medicoCoordenador);
		when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);
		
		Assert.assertEquals(Action.ERROR, action.prepareUpdate());
    	Assert.assertTrue(action.getActionWarnings().iterator().next().toString().startsWith("O médico solicitado não existe na empresa"));
	}
	
	@Test
	public void testPrepareUpdateComMedicoNulo() throws Exception
	{
		MedicoCoordenador medicoCoordenador = null;
		action.setMedicoCoordenador(medicoCoordenador);
		
		when(estabelecimentoManager.populaCheckBox(action.getEmpresaSistema().getId())).thenReturn(null);
		
    	Assert.assertEquals(Action.ERROR, action.prepareUpdate());
    	Assert.assertTrue(action.getActionWarnings().iterator().next().toString().startsWith("O médico solicitado não existe na empresa"));
	}

	@Test
	public void testInsertMedicoCoordenadorAlgunsEstabelecimentos() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos);
		medicoCoordenador.setId(1L);
		
		action.setMedicoCoordenador(medicoCoordenador);
		action.setEstabelecimentosCheck(estabelecimentosCheck);
		
		Assert.assertEquals(Action.SUCCESS, action.insert());
		Assert.assertTrue(action.getActionSuccess().iterator().next().toString().equals("Médico coordenador gravado com sucesso."));
		
		verify(medicoCoordenadorManager, times(1)).insere(medicoCoordenador, estabelecimentosCheck);
	}

	
	@Test
    public void testInsertException() throws Exception
    {
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
    	medicoCoordenador.setId(1L);
    	action.setMedicoCoordenador(medicoCoordenador);

    	doThrow(Exception.class).when(medicoCoordenadorManager).insere(medicoCoordenador, null);

		Assert.assertEquals(Action.INPUT, action.insert());
    	Assert.assertTrue(action.getActionErrors().iterator().next().toString().equals("Cadastro não pôde ser realizado."));
    }
	@Test
	public void testInsertValidacaoAssinaturaException() throws Exception
	{
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
		medicoCoordenador.setId(1L);
		action.setMedicoCoordenador(medicoCoordenador);
		
		doThrow(ValidacaoAssinaturaException.class).when(medicoCoordenadorManager).insere(medicoCoordenador, null);
		
		Assert.assertEquals(Action.INPUT, action.insert());
		Assert.assertTrue(action.getActionErrors().iterator().next().toString().equals("Cadastro não pôde ser realizado."));
	}

	@Test
    public void testUpdate() throws Exception
    {
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
    	medicoCoordenador.setId(1L);
    	
    	Boolean manterAssinatura = Boolean.TRUE;
    	action.setMedicoCoordenador(medicoCoordenador);
    	action.setManterAssinatura(Boolean.TRUE);

    	Assert.assertEquals(Action.SUCCESS, action.update());
    	Assert.assertEquals(action.getMedicoCoordenador(), medicoCoordenador);
		Assert.assertTrue(action.getActionSuccess().iterator().next().toString().equals("Médico coordenador atualizado com sucesso."));

    	verify(medicoCoordenadorManager, times(1)).atualiza(medicoCoordenador, null, manterAssinatura);
    }
	
	@Test
	public void testUpdateException() throws Exception
	{
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
		medicoCoordenador.setId(1L);
		
		Boolean manterAssinatura = Boolean.TRUE;
		action.setMedicoCoordenador(medicoCoordenador);
		action.setManterAssinatura(Boolean.TRUE);
		
		doThrow(Exception.class).when(medicoCoordenadorManager).atualiza(medicoCoordenador, null, manterAssinatura);
		
		Assert.assertEquals(Action.INPUT, action.update());
		Assert.assertTrue(action.getActionErrors().iterator().next().toString().equals("Atualização não pôde ser realizada."));
	}
	
	@Test
	public void testUpdateValidacaoAssinaturaException() throws Exception
	{
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(action.getEmpresaSistema(), new Date(), TipoEstabelecimentoResponsavel.TODOS, null);
		medicoCoordenador.setId(1L);
		
		Boolean manterAssinatura = Boolean.TRUE;
		action.setMedicoCoordenador(medicoCoordenador);
		action.setManterAssinatura(Boolean.TRUE);
		
		doThrow(ValidacaoAssinaturaException.class).when(medicoCoordenadorManager).atualiza(medicoCoordenador, null, manterAssinatura);
		
		Assert.assertEquals(Action.INPUT, action.update());
		Assert.assertTrue(action.getActionErrors().iterator().next().toString().equals("Atualização não pôde ser realizada."));
	}

	@Test
    public void testShowAssinatura() throws Exception
	{
    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	
    	File assinatura = new File();
    	assinatura.setContentType("image/jpeg");
    	assinatura.setBytes(new byte[]{2,3,127,4,43,3,31});
    	
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	ServletOutputStream outputStream = mock(ServletOutputStream.class);
    	
    	action.setMedicoCoordenador(medicoCoordenador);
    	
    	when(medicoCoordenadorManager.getAssinaturaDigital(medicoCoordenador.getId())).thenReturn(assinatura);
    	when(ServletActionContext.getResponse()).thenReturn(response);
    	when(response.getOutputStream()).thenReturn(outputStream);

    	Assert.assertEquals(Action.SUCCESS, action.showAssinatura());
	}
}