package com.fortes.rh.test.model.geral;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.ReportColumn;

public class ReportColumnTest extends TestCase {

	public void setUp() {
	}
	
	public void testResizeColumns() 
	{
		Collection<ReportColumn> colunas = new ArrayList<ReportColumn>();
		colunas = ReportColumn.getColumns(false);
		
		Collection<String> colunasMarcadas = new ArrayList<String>();
		colunasMarcadas.add("nome");//150
		colunasMarcadas.add("matricula");//40
		colunasMarcadas.add("pessoal.pai");//150
		colunasMarcadas.add("pessoal.rg");//70
		colunasMarcadas.add("nomeComercial");//150
		
		Collection<ReportColumn> colunasMarcadasRedimensionadas = ReportColumn.resizeColumns(colunas, colunasMarcadas);
		
		assertEquals(5, colunasMarcadasRedimensionadas.size());
		assertEquals(216, ((ReportColumn)colunasMarcadasRedimensionadas.toArray()[0]).getSize());
		assertEquals(40, ((ReportColumn)colunasMarcadasRedimensionadas.toArray()[1]).getSize());
		assertEquals(216, ((ReportColumn)colunasMarcadasRedimensionadas.toArray()[2]).getSize());
		assertEquals(70, ((ReportColumn)colunasMarcadasRedimensionadas.toArray()[3]).getSize());
		assertEquals(216, ((ReportColumn)colunasMarcadasRedimensionadas.toArray()[4]).getSize());
	}

	public void testResizeColumnsResizeZero() 
	{
		Collection<ReportColumn> colunas = new ArrayList<ReportColumn>();
		colunas = ReportColumn.getColumns(true);
		
		Collection<String> colunasMarcadas = new ArrayList<String>();
		colunasMarcadas.add("pessoal.rg");//70
		
		Collection<ReportColumn> colunasMarcadasRedimensionadas = ReportColumn.resizeColumns(colunas, colunasMarcadas);
		
		assertEquals(1, colunasMarcadasRedimensionadas.size());
		assertEquals(70, ((ReportColumn)colunasMarcadasRedimensionadas.toArray()[0]).getSize());
	}
}
