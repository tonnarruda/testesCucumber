package com.fortes.rh.test.model.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;

public class PppRelatorioTest extends TestCase
{
	public void testSetFatoresRiscosDistinctTest()
	{
		Collection<PppFatorRisco> fatoresRiscos = new ArrayList<PppFatorRisco>();
		
		Date dataInicio = new Date();
		Long riscoId = 1L;
		Long medicaoId = 2L;
		PppFatorRisco primeiro = new PppFatorRisco(dataInicio, riscoId, medicaoId, "primeiro");
		PppFatorRisco segundo = new PppFatorRisco(dataInicio, riscoId, medicaoId, "segundo");
		PppFatorRisco terceiro = new PppFatorRisco(dataInicio, riscoId, medicaoId, "terceiro");
		PppFatorRisco quarto = new PppFatorRisco(dataInicio, 3L, medicaoId, "quarto");
		
		fatoresRiscos.add(primeiro);
		fatoresRiscos.add(segundo);
		fatoresRiscos.add(terceiro);
		fatoresRiscos.add(quarto);
		
		PppRelatorio pppRelatorio = new PppRelatorio();
		pppRelatorio.setFatoresRiscosDistinct(fatoresRiscos);
		
		assertEquals(2, pppRelatorio.getFatoresRiscos().size());
		assertEquals("terceiro",((PppFatorRisco)pppRelatorio.getFatoresRiscos().toArray()[0]).getIntensidade());
	}
	
	public void testSetRespostas()
	{
		String[] respostas = new String[]{"S","S","S","N","N"};
		PppRelatorio pppRelatorio = new PppRelatorio();
		pppRelatorio.setRespostas(respostas);
		assertEquals("N", pppRelatorio.getResposta5());
	}
	
	public void testGetCnpjFormatado()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(32L);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCnpj("04525292");
		colaborador.setEmpresa(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(43L);
		String complementoCnpj = "0001";
		estabelecimento.setComplementoCnpj(complementoCnpj);
		
		PppRelatorio pppRelatorio = new PppRelatorio(colaborador, estabelecimento, new Date());
		
		assertEquals("04.525.292/0001-37",pppRelatorio.getCnpjFormatado());
		
	}
}
