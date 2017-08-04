package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.ComissaoPeriodoAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class ComissaoPeriodoAuditorCallbackImplTest {

	AuditorCallback callback;
	private ComissaoPeriodoManager comissaoPeriodoManager;
	
	@Before
	public void setUp() {
		comissaoPeriodoManager = mock(ComissaoPeriodoManager.class);
		callback = new ComissaoPeriodoAuditorCallbackImpl();
	}
	
	@Test
	public void testAtualiza() throws Throwable {
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity(1L);
		comissaoPeriodo.setaPartirDe(new Date());
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity("Francisco");
		Colaborador colaborador2 = ColaboradorFactory.getEntity("Maria");
		
		ComissaoMembro comissaoMembro1 = ComissaoMembroFactory.getEntity(comissaoPeriodo, colaborador1, FuncaoComissao.PRESIDENTE, TipoMembroComissao.INDICADO_CIPA);
		comissaoMembro1.setId(5L);
		ComissaoMembro comissaoMembro2 = ComissaoMembroFactory.getEntity(comissaoPeriodo, colaborador2, FuncaoComissao.SECRETARIO, TipoMembroComissao.INDICADO_EMPRESA);
		comissaoMembro2.setId(10L);
		Collection<ComissaoMembro> comissaoMembros = Arrays.asList(comissaoMembro1, comissaoMembro2);
		String[] comissaoMembroIds = {comissaoMembro1.getId().toString(), comissaoMembro2.getId().toString()};
		String[] funcoes = {FuncaoComissao.PRESIDENTE, FuncaoComissao.SECRETARIO};
		String[] tipos = {TipoMembroComissao.INDICADO_CIPA, TipoMembroComissao.INDICADO_EMPRESA};
		
		when(comissaoPeriodoManager.findComissaoMembro(comissaoPeriodo.getId())).thenReturn(comissaoMembros);

		MethodInvocation atualiza = new MethodInvocationDefault<ComissaoPeriodoManager>("atualiza", ComissaoPeriodoManager.class, new Object[]{comissaoPeriodo, comissaoMembroIds, funcoes, tipos}, comissaoPeriodoManager);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(atualiza));

		assertEquals("Período da Comissão(Membros)", auditavel.getModulo());
		assertEquals("Atualização", auditavel.getOperacao());
		assertEquals("Comissão da CIPA a partir de "  + DateUtil.formataDiaMesAno(new Date()), auditavel.getChave());
		
		String data = DateUtil.formataDiaMesAno(new Date());
		
		StringBuilder dados = new StringBuilder();
		dados.append("[DADOS ATUALIZADOS]\n");
        dados.append("\n");
		dados.append(" Id: 1\n");
		dados.append(" A partir de: "+data+"\n");
		dados.append(" Membros: [\n");
		dados.append("   Comissão Membro Id: 5\n");
		dados.append("   Nome: Francisco\n");
		dados.append("   Função: Presidente\n");
		dados.append("   Tipo: Indicado pela CIPA\n");
        dados.append("\n");
		dados.append("   Comissão Membro Id: 10\n");
		dados.append("   Nome: Maria\n");
		dados.append("   Função: Secretário\n");
		dados.append("   Tipo: Indicado pela Empresa\n");
        dados.append("\n");
		dados.append(" ]");
		
		assertEquals(dados.toString(), auditavel.getDados());
	}
}
