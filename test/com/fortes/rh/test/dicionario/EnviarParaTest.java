package com.fortes.rh.test.dicionario;

import java.util.HashMap;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.EnviarPara;

public class EnviarParaTest extends TestCase
{
	
	public void testChave()
	{
		assertEquals(8, EnviarPara.values().length);
		
		assertEquals(new Integer(1), EnviarPara.USUARIO.getId());
		assertEquals(new Integer(2), EnviarPara.GESTOR_AREA.getId());
		assertEquals(new Integer(3), EnviarPara.CANDIDATO_NAO_APTO.getId());
		assertEquals(new Integer(4), EnviarPara.SOLICITANTE_SOLICITACAO.getId());
		assertEquals(new Integer(5), EnviarPara.LIBERADOR_SOLICITACAO.getId());
		assertEquals(new Integer(6), EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId());
		assertEquals(new Integer(7), EnviarPara.COLABORADOR.getId());
		assertEquals(new Integer(99), EnviarPara.AVULSO.getId());
	}

	public void testDescricao()
	{
		assertEquals("Usuário", EnviarPara.USUARIO.getDescricao());
		assertEquals("Gestor da área organizacional", EnviarPara.GESTOR_AREA.getDescricao());
		assertEquals("Candidatos não aptos", EnviarPara.CANDIDATO_NAO_APTO.getDescricao());
		assertEquals("Avulso", EnviarPara.AVULSO.getDescricao());
	}

	public void testGetDescricaoById()
	{
		assertEquals("Usuário", EnviarPara.getDescricaoById(1));
		assertEquals("Gestor da área organizacional", EnviarPara.getDescricaoById(2));
		assertEquals("Candidatos não aptos", EnviarPara.getDescricaoById(3));
		assertEquals("Avulso", EnviarPara.getDescricaoById(99));
	}

	public void testSetAvulso()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setAvulso(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.AVULSO.getDescricao(), meioComunicacao.get(EnviarPara.AVULSO.getId()));
	}
	
	public void testSetUsuario()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setUsuario(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.USUARIO.getDescricao(), meioComunicacao.get(EnviarPara.USUARIO.getId()));
	}
	
	public void testSetCandidatoNaoApto()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setCandidatoNaoApto(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.CANDIDATO_NAO_APTO.getDescricao(), meioComunicacao.get(EnviarPara.CANDIDATO_NAO_APTO.getId()));
	}

}
