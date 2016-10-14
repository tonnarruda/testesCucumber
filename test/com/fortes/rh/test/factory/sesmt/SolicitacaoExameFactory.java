package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.SolicitacaoExame;

public class SolicitacaoExameFactory
{
	public static SolicitacaoExame getEntity()
	{
		SolicitacaoExame solicitacaoExame = new SolicitacaoExame();

		solicitacaoExame.setData(new Date());
		solicitacaoExame.setOrdem(1);

		return solicitacaoExame;
	}

	public static SolicitacaoExame getEntity(Long id)
	{
		SolicitacaoExame solicitacaoExame = getEntity();
		solicitacaoExame.setId(id);
		

		return solicitacaoExame;
	}

	public static SolicitacaoExame getEntity(Empresa empresa, Colaborador colaborador, String motivo, Date data)
	{
		SolicitacaoExame solicitacaoExame = getEntity();
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExame.setColaborador(colaborador);
		solicitacaoExame.setMotivo(motivo);
		solicitacaoExame.setData(data);

		return solicitacaoExame;
	}
	
	public static Collection<SolicitacaoExame> getCollection()
	{
		SolicitacaoExame solicitacaoExame = getEntity();
		solicitacaoExame.setId(1L);
		Collection<SolicitacaoExame> solicitacaoExames = new ArrayList<SolicitacaoExame>();
		solicitacaoExames.add(solicitacaoExame);
		
		return solicitacaoExames;
	}
}
