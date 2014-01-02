package com.fortes.rh.model.geral;

import java.util.Comparator;

public class NoticiaComparator implements Comparator<Noticia>
{
	public int compare(Noticia n1, Noticia n2) 
	{
		int i = n1.getCriticidade().compareTo(n2.getCriticidade());

		if (i == 0)
			i = n2.getId().compareTo(n1.getId());
		
		return i;
	}
}