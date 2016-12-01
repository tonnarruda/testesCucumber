package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ClinicaAutorizada;

public class ClinicaAutorizadaFactory {
	
	public static ClinicaAutorizada getEntity(String nome, String tipo){
		ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
		clinicaAutorizada.setNome(nome);
		clinicaAutorizada.setTipo(tipo);
		return clinicaAutorizada;
	}

}
