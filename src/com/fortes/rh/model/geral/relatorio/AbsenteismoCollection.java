package com.fortes.rh.model.geral.relatorio;

import java.util.ArrayList;
import java.util.Collection;

public class AbsenteismoCollection
{
	private Collection<Absenteismo> absenteismos = new ArrayList<Absenteismo>();

	public Double getMedia()
	{
		Double soma = 0D;
		for (Absenteismo absenteismo : absenteismos)
		{
			soma += absenteismo.getAbsenteismo();
		}

		return soma / absenteismos.size();
	}

	public Collection<Absenteismo> getAbsenteismos() {
		return absenteismos;
	}

	public void setAbsenteismos(Collection<Absenteismo> absenteismos) {
		this.absenteismos = absenteismos;
	}




}
