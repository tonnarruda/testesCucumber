package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;

public class HistoricoColaboradorFactory
{
	public static HistoricoColaborador getEntity()
	{
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();

		historicoColaborador.setId(null);
		historicoColaborador.setMotivo("C");
		historicoColaborador.setTipoSalario(3);
		historicoColaborador.setSalario(1D);
		historicoColaborador.setColaborador(null);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);

		return historicoColaborador;
	}

	public static HistoricoColaborador getEntity(Long id)
	{
		HistoricoColaborador historicoColaborador = getEntity();
		historicoColaborador.setId(id);
		
		return historicoColaborador;
	}
	
	public static HistoricoColaborador getEntity(Colaborador colaborador, Date data, FaixaSalarial faixaSalarial, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Funcao funcao, Ambiente ambiente)
	{
		HistoricoColaborador historicoColaborador = getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setFuncao(funcao);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setData(data);
		
		return historicoColaborador;
	}

	public static Collection<HistoricoColaborador> getCollection()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(getEntity());
		return historicoColaboradors;
	}

}
