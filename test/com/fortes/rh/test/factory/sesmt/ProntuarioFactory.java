package com.fortes.rh.test.factory.sesmt;

import java.util.Date;

import com.fortes.rh.model.sesmt.Prontuario;

public class ProntuarioFactory
{
	public static Prontuario getEntity()
	{
		Prontuario prontuario = new Prontuario();

		prontuario.setData(new Date());
		prontuario.setDescricao("desc");

		return prontuario;
	}

	public static Prontuario getEntity(Long id)
	{
		Prontuario prontuario = getEntity();
		prontuario.setId(id);
		
		return prontuario;
	}
}
