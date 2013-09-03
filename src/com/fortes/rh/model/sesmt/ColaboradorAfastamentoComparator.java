package com.fortes.rh.model.sesmt;

import java.util.Comparator;

public class ColaboradorAfastamentoComparator implements Comparator<ColaboradorAfastamento>
{
	private char ordenarPor;
	private boolean agruparPorArea;
	
	public ColaboradorAfastamentoComparator(char ordenarPor, boolean agruparPorArea) 
	{
		this.ordenarPor = ordenarPor;
		this.agruparPorArea = agruparPorArea;
	}

	public int compare(ColaboradorAfastamento c1, ColaboradorAfastamento c2) 
	{
		int i;
		
		if (agruparPorArea)
		{
			i = c1.getAreaDescricao().compareTo(c2.getAreaDescricao());
		    if (i != 0) return i;
		}

		if (ordenarPor == 'D')
		{
			i = c2.getQtdTotalDias() - c1.getQtdTotalDias();
			if (i != 0) return i;
		}
		
	    return c1.getColaborador().getNome().compareTo(c2.getColaborador().getNome());
	}

	public char getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(char ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public boolean isAgruparPorArea() {
		return agruparPorArea;
	}

	public void setAgruparPorArea(boolean agruparPorArea) {
		this.agruparPorArea = agruparPorArea;
	}
}