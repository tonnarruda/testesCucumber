package com.fortes.rh.test.model.sesmt;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.util.DateUtil;

public class ExamesPrevistosRelatorioTest extends TestCase
{
	Date dataExame = DateUtil.criarDataMesAno(01, 01,2009);
	Date dataProximoExame = DateUtil.criarDataMesAno(01, 02,2009);
	
	ExamesPrevistosRelatorio examesPrevistosRelatorio = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","Colaborador","Colab Comercial","Exame",dataExame,dataExame,1, "PERIODICO",1L,"EST");

	public void testGetDataProximoExame()
	{
		assertEquals(examesPrevistosRelatorio.getDataProximoExame(),dataProximoExame);
		assertEquals(examesPrevistosRelatorio.getDataProximoExameFmt(),"01/02/2009");
	}

	public void testGetDataUltimoExame()
	{
		assertEquals(examesPrevistosRelatorio.getDataUltimoExame(), "01/01/2009");
	}

	public void testGetTempoVencido()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -2);
		examesPrevistosRelatorio = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","Colaborador","Colab Comercial","Exame",calendar.getTime(),calendar.getTime(),1, "PERIODICO",1L,"EST");
		assertEquals(examesPrevistosRelatorio.getTempoVencido(), "1 mês");
	}

	public void testGetSet()
	{
		examesPrevistosRelatorio = new ExamesPrevistosRelatorio();
		examesPrevistosRelatorio = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","Maria","Colab Comercial","Audiometria",new Date(),new Date(), 1, "PERIODICO",1L,"EST");
		String colaboradorNome = "Jose";
		examesPrevistosRelatorio.setColaboradorNome(colaboradorNome);
		assertEquals("Jose", examesPrevistosRelatorio.getColaboradorNome());

		examesPrevistosRelatorio.getExameId();
		examesPrevistosRelatorio.getExameNome();
		examesPrevistosRelatorio.getColaboradorId();
		examesPrevistosRelatorio.setAdicionar(true);
		examesPrevistosRelatorio.getAdicionar();

		examesPrevistosRelatorio.getDataRealizacaoExame();

		examesPrevistosRelatorio = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","Maria","Colab Comercial","Audiometria",new Date(),null, 1, "PERIODICO",1L,"EST");
		examesPrevistosRelatorio.getDataUltimoExame();
		examesPrevistosRelatorio.getDataRealizacaoExame();
	}

}
