package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.ParametrosDoSistema;

public class ParametrosDoSistemaFactory
{
	public static ParametrosDoSistema getEntity()
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();

		parametrosDoSistema.setId(null);
		parametrosDoSistema.setAppUrl("url");
		parametrosDoSistema.setAppContext("contexto");
		parametrosDoSistema.setAppVersao("1");
		parametrosDoSistema.setEmailPort(0);
		parametrosDoSistema.setEnviarEmail(false);
		parametrosDoSistema.setAtualizadoSucesso(true);
		parametrosDoSistema.setPerfilPadrao(null);
		parametrosDoSistema.setCompartilharColaboradores(true);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setTelaInicialModuloExterno('L');
		parametrosDoSistema.setCamposColaboradorObrigatorio("");

		return parametrosDoSistema;
	}

	public static ParametrosDoSistema getEntity(Long id)
	{
		ParametrosDoSistema parametrosDoSistema = getEntity();
		parametrosDoSistema.setId(id);

		return parametrosDoSistema;
	}
}
