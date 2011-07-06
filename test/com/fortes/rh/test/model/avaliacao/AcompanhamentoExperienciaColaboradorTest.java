package com.fortes.rh.test.model.avaliacao;

import junit.framework.TestCase;

import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.util.DateUtil;

public class AcompanhamentoExperienciaColaboradorTest extends TestCase {

	public void testPopulaAcompanhamentoExperienciaColaborador() 
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		
		AcompanhamentoExperienciaColaborador acompanhamento = new AcompanhamentoExperienciaColaborador("123456", "joao", "mecanico I", area, DateUtil.criarDataMesAno(01, 01, 2011));
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(01, 02, 2011), "75%");
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(15, 02, 2011), "85%");
		acompanhamento.addPeriodo(null, null);
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(01, 06, 2011), "80%");
		
		assertEquals("01/02/2011 (75%)", acompanhamento.getPeriodoColunaUm());
		assertEquals("15/02/2011 (85%)", acompanhamento.getPeriodoColunaDois());
		assertNull(acompanhamento.getPeriodoColunaTres());
		assertEquals("01/06/2011 (80%)", acompanhamento.getPeriodoColunaQuatro());
	}
}
