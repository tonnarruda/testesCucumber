package com.fortes.rh.test.factory.sesmt;

import java.util.Date;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;

public class SolicitacaoEpiFactory
{
	public static SolicitacaoEpi getEntity()
	{
		SolicitacaoEpi solicitacaoEpi = new SolicitacaoEpi();
		solicitacaoEpi.setData(new Date());

		return solicitacaoEpi;
	}

	public static SolicitacaoEpi getEntity(Long id)
	{
		SolicitacaoEpi solicitacaoEpi = getEntity();
		solicitacaoEpi.setId(id);

		return solicitacaoEpi;
	}

	public static SolicitacaoEpi getEntity(Date dataIni, Colaborador colaborador, Empresa empresa, Estabelecimento estabelecimento, Cargo cargo) {
		SolicitacaoEpi solicitacaoEpi = getEntity();
		solicitacaoEpi.setData(dataIni);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpi.setEstabelecimento(estabelecimento);
		solicitacaoEpi.setCargo(cargo);

		return solicitacaoEpi;
	}
}
