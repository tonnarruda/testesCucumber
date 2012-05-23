package com.fortes.rh.test.model.sesmt;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

import junit.framework.TestCase;

public class SolicitacaoExameTest extends TestCase
{

	public void testGetPessoaNomeComercialDoCandidato()
	{
		SolicitacaoExame solicitacaoExame = new SolicitacaoExame();
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setNome("João Pedro");
		solicitacaoExame.setCandidato(candidato);
		assertEquals("João Pedro (candidato)", solicitacaoExame.getPessoaNomeComercial());
	}
	
	public void testGetPessoaNomeComercialDoColaborador()
	{
		SolicitacaoExame solicitacaoExame = new SolicitacaoExame();
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		colaborador.setNome("João da Silva");
		colaborador.setNomeComercial("João");
		solicitacaoExame.setColaborador(colaborador);
		assertEquals("João da Silva (João)", solicitacaoExame.getPessoaNomeComercial());

		colaborador.setNome("João da Silva");
		colaborador.setNomeComercial(null);
		solicitacaoExame.setColaborador(colaborador);
		assertEquals("João da Silva", solicitacaoExame.getPessoaNomeComercial());
		
		colaborador.setNome("João da Silva");
		colaborador.setNomeComercial("");
		solicitacaoExame.setColaborador(colaborador);
		assertEquals("João da Silva", solicitacaoExame.getPessoaNomeComercial());
		
		colaborador.setNome("João da Silva");
		colaborador.setNomeComercial("João da Silva");
		solicitacaoExame.setColaborador(colaborador);
		assertEquals("João da Silva", solicitacaoExame.getPessoaNomeComercial());
	}
}
