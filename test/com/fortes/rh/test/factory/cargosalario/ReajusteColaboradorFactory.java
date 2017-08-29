package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Funcao;

public class ReajusteColaboradorFactory
{
	public static ReajusteColaborador getReajusteColaborador()
	{
		ReajusteColaborador reajusteColaborador = new ReajusteColaborador();

		reajusteColaborador.setId(null);
		reajusteColaborador.setFaixaSalarialAtual(null);
		reajusteColaborador.setFaixaSalarialProposta(null);
		reajusteColaborador.setSalarioAtual(22D);
		reajusteColaborador.setSalarioProposto(33D);
		reajusteColaborador.setTabelaReajusteColaborador(null);

		return reajusteColaborador;
	}

	public static ReajusteColaborador getReajusteColaborador(Long id)
	{
		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		reajusteColaborador.setId(id);

		return reajusteColaborador;
	}

	public static Collection<ReajusteColaborador> getCollection()
	{
		Collection<ReajusteColaborador> collection = new ArrayList<ReajusteColaborador>();
		collection.add(getReajusteColaborador());
		return collection;
	}
	
	public static ReajusteColaborador getReajusteColaborador(Colaborador colaborador,AreaOrganizacional areaOrganizacionalAtual,AreaOrganizacional areaOrganizacionalProposta,
			FaixaSalarial faixaSalarialAtual, FaixaSalarial faixaSalarialProposta, Funcao funcaoAtual, Funcao funcaoProposta, Integer tipoSalarioAtual, Integer tipoSalarioProposto,
			Double salarioAtual, Double salarioProposto) 
	{
		ReajusteColaborador reajusteColaborador = getReajusteColaborador(colaborador, areaOrganizacionalAtual, areaOrganizacionalProposta, faixaSalarialAtual, faixaSalarialProposta, tipoSalarioAtual, tipoSalarioProposto, salarioAtual, salarioProposto);
		reajusteColaborador.setFuncaoAtual(funcaoAtual);
		reajusteColaborador.setFuncaoProposta(funcaoProposta);
		return reajusteColaborador;
	}
	
	public static ReajusteColaborador getReajusteColaborador(Colaborador colaborador,AreaOrganizacional areaOrganizacionalAtual,AreaOrganizacional areaOrganizacionalProposta,
			FaixaSalarial faixaSalarialAtual, FaixaSalarial faixaSalarialProposta, Integer tipoSalarioAtual, Integer tipoSalarioProposto,
			Double salarioAtual, Double salarioProposto) 
	{
		ReajusteColaborador reajusteColaborador = getReajusteColaborador();

		reajusteColaborador.setColaborador(colaborador);
		reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacionalProposta);
		reajusteColaborador.setAreaOrganizacionalAtual(areaOrganizacionalAtual);
		reajusteColaborador.setFaixaSalarialAtual(faixaSalarialAtual);
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposta);
		reajusteColaborador.setTipoSalarioAtual(tipoSalarioAtual);
		reajusteColaborador.setSalarioAtual(salarioAtual);
		reajusteColaborador.setTipoSalarioProposto(tipoSalarioProposto);
		reajusteColaborador.setSalarioProposto(salarioProposto);

		return reajusteColaborador;
	}
}
