package com.fortes.rh.test.model.avaliacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(01, 02, 2011), "75%", "");
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(15, 02, 2011), "85%", "");
		acompanhamento.addPeriodo(null, null, null);
		acompanhamento.addPeriodo(null, null, "01/05/2011");
		
		assertEquals("01/02/2011 (75%)", acompanhamento.getPeriodoColunaUm());
		assertEquals("15/02/2011 (85%)", acompanhamento.getPeriodoColunaDois());
		assertNull(acompanhamento.getPeriodoColunaTres());
		assertEquals("Previsto: 01/05/2011", acompanhamento.getPeriodoColunaQuatro());
	}
		
	public void testPopulaAcompanhamentoExperienciaColaboradorSemUltimoPeriodo()
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		
		AcompanhamentoExperienciaColaborador acompanhamento = new AcompanhamentoExperienciaColaborador("456789", "pedro", "lavador I", area, DateUtil.criarDataMesAno(01, 01, 2011));
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(01, 02, 2011), "75%", "01/05/2011");
		acompanhamento.addPeriodo(null, null, "01/05/2011");
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(15, 02, 2011), "85%", "01/05/2011");
		
		assertEquals("01/02/2011 (75%)", acompanhamento.getPeriodoColunaUm());
		assertEquals("Previsto: 01/05/2011", acompanhamento.getPeriodoColunaDois());
		assertEquals("15/02/2011 (85%)", acompanhamento.getPeriodoColunaTres());
		assertNull(acompanhamento.getPeriodoColunaQuatro());
	}
	
	public void testPopulaAcompanhamentoExperienciaColaboradorComAvaliacaoETiulo()
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		
		AcompanhamentoExperienciaColaborador acompanhamento = new AcompanhamentoExperienciaColaborador("456789", "pedro", "lavador I", area, DateUtil.criarDataMesAno(01, 01, 2011));
		acompanhamento.addPeriodo(DateUtil.criarDataMesAno(01, 02, 2011), "75%", "01/05/2011","Avaliador 1", "Avaliação 1", "30 Dias");
		acompanhamento.addPeriodo(null, null, "01/05/2011","Avaliador 1", "Avaliação 1", "60 Dias");
		acompanhamento.addPeriodo(null, null, null, null, null, null);
		
		assertEquals("01/02/2011 (75%)", acompanhamento.getPeriodoColunaUm());
		assertEquals("Previsto: 01/05/2011", acompanhamento.getPeriodoColunaDois());
		assertNull(acompanhamento.getPeriodoColunaTres());
	}
	
	public void testGetAreaId()
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		AcompanhamentoExperienciaColaborador acompanhamento = new AcompanhamentoExperienciaColaborador("123456", "joao", "mecanico I", area, DateUtil.criarDataMesAno(01, 01, 2011));
		assertEquals(area.getId(), acompanhamento.getAreaOrganizacionalId());

		AcompanhamentoExperienciaColaborador acompanhamentoSemArea = new AcompanhamentoExperienciaColaborador("456789", "pedro", "lavador I", null, DateUtil.criarDataMesAno(01, 01, 2011));
		assertNull(acompanhamentoSemArea.getAreaOrganizacionalId());
	}
	
	public void testComparable()
	{
		AreaOrganizacional areaMecanica = AreaOrganizacionalFactory.getEntity(1L);
		areaMecanica.setNome("Mecânica");
		
		AreaOrganizacional areaLavagem = AreaOrganizacionalFactory.getEntity(2L);
		areaLavagem.setNome("Lavagem");
		
		AcompanhamentoExperienciaColaborador acompanhamentoJoao = new AcompanhamentoExperienciaColaborador("123456", "joao", "mecanico I", areaMecanica, DateUtil.criarDataMesAno(01, 01, 2011));
		AcompanhamentoExperienciaColaborador acompanhamentoPedro = new AcompanhamentoExperienciaColaborador("456789", "pedro", "lavador I", areaLavagem, DateUtil.criarDataMesAno(01, 01, 2011));
		AcompanhamentoExperienciaColaborador acompanhamentoAlmir = new AcompanhamentoExperienciaColaborador("123456", "almir", "mecanico II", areaMecanica, DateUtil.criarDataMesAno(01, 01, 2011));
		
		List<AcompanhamentoExperienciaColaborador> lista = new ArrayList<AcompanhamentoExperienciaColaborador>();
		lista.add(acompanhamentoJoao);
		lista.add(acompanhamentoPedro);
		lista.add(acompanhamentoAlmir);
		
		Collections.sort(lista);
		
		assertEquals(acompanhamentoPedro, lista.get(0));
		assertEquals(acompanhamentoAlmir, lista.get(1));
		assertEquals(acompanhamentoJoao, lista.get(2));
	}
}
