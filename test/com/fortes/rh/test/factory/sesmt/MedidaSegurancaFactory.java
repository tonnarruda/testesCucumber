package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.MedidaSeguranca;

public class MedidaSegurancaFactory
{
	public static MedidaSeguranca getEntity()
	{
		MedidaSeguranca medidaSeguranca = new MedidaSeguranca();
		medidaSeguranca.setId(null);
		medidaSeguranca.setDescricao("Medida");
		return medidaSeguranca;
	}

	public static MedidaSeguranca getEntity(Long id)
	{
		MedidaSeguranca medidaSeguranca = getEntity();
		medidaSeguranca.setId(id);

		return medidaSeguranca;
	}

	public static Collection<MedidaSeguranca> getCollection()
	{
		Collection<MedidaSeguranca> medidaSegurancas = new ArrayList<MedidaSeguranca>();
		medidaSegurancas.add(getEntity());

		return medidaSegurancas;
	}
	
	public static Collection<MedidaSeguranca> getCollection(Long id)
	{
		Collection<MedidaSeguranca> medidaSegurancas = new ArrayList<MedidaSeguranca>();
		medidaSegurancas.add(getEntity(id));
		
		return medidaSegurancas;
	}
}
