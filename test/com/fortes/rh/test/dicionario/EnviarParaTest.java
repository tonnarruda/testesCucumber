package com.fortes.rh.test.dicionario;

import java.util.HashMap;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.EnviarPara;

public class EnviarParaTest extends TestCase
{
	
	public void testChave()
	{
		assertEquals(13, EnviarPara.values().length);
		
		assertEquals(new Integer(1), EnviarPara.USUARIO.getId());
		assertEquals(new Integer(2), EnviarPara.GESTOR_AREA.getId());
		assertEquals(new Integer(3), EnviarPara.CANDIDATO_NAO_APTO.getId());
		assertEquals(new Integer(4), EnviarPara.SOLICITANTE_SOLICITACAO.getId());
		assertEquals(new Integer(5), EnviarPara.LIBERADOR_SOLICITACAO.getId());
		assertEquals(new Integer(6), EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId());
		assertEquals(new Integer(7), EnviarPara.COLABORADOR.getId());
		assertEquals(new Integer(8), EnviarPara.RESPONSAVEL_RH.getId());
		assertEquals(new Integer(9), EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getId());
		assertEquals(new Integer(10), EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getId());
		assertEquals(new Integer(11), EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getId());
		assertEquals(new Integer(12), EnviarPara.RESPONSAVEL_TECNICO.getId());
		assertEquals(new Integer(99), EnviarPara.AVULSO.getId());
	}

	public void testDescricao()
	{
		assertEquals("Usuário", EnviarPara.USUARIO.getDescricao());
		assertEquals("Gestor da área organizacional", EnviarPara.GESTOR_AREA.getDescricao());
		assertEquals("Candidatos não aptos", EnviarPara.CANDIDATO_NAO_APTO.getDescricao());
		assertEquals("Solicitante", EnviarPara.SOLICITANTE_SOLICITACAO.getDescricao());
		assertEquals("Liberador", EnviarPara.LIBERADOR_SOLICITACAO.getDescricao());
		assertEquals("Avaliador", EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getDescricao());
		assertEquals("Colaborador", EnviarPara.COLABORADOR.getDescricao());
		assertEquals("Responsável do RH", EnviarPara.RESPONSAVEL_RH.getDescricao());
		assertEquals("Usuários com perfil de receber emails de exames previstos", EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getDescricao());
		assertEquals("Usuários com perfil de gerenciador de mensagem por perído de experiência", EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao());
		assertEquals("Usuários com perfil de receber mensagem por perído de experiência", EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao());
		assertEquals("Responsável Tecnico", EnviarPara.RESPONSAVEL_TECNICO.getDescricao());
		assertEquals("Avulso", EnviarPara.AVULSO.getDescricao());
	}

	public void testGetDescricaoById()
	{
		assertEquals("Usuário", EnviarPara.getDescricaoById(1));
		assertEquals("Gestor da área organizacional", EnviarPara.getDescricaoById(2));
		assertEquals("Candidatos não aptos", EnviarPara.getDescricaoById(3));
		assertEquals("Solicitante", EnviarPara.getDescricaoById(4));
		assertEquals("Liberador", EnviarPara.getDescricaoById(5));
		assertEquals("Avaliador", EnviarPara.getDescricaoById(6));
		assertEquals("Colaborador", EnviarPara.getDescricaoById(7));
		assertEquals("Responsável do RH", EnviarPara.getDescricaoById(8));
		assertEquals("Usuários com perfil de receber emails de exames previstos", EnviarPara.getDescricaoById(9));
		assertEquals("Usuários com perfil de gerenciador de mensagem por perído de experiência", EnviarPara.getDescricaoById(10));
		assertEquals("Usuários com perfil de receber mensagem por perído de experiência", EnviarPara.getDescricaoById(11));
		assertEquals("Responsável Tecnico", EnviarPara.getDescricaoById(12));
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
	
	public void testSetSolicitante()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setSolicitante(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.SOLICITANTE_SOLICITACAO.getDescricao(), meioComunicacao.get(EnviarPara.SOLICITANTE_SOLICITACAO.getId()));
	}
	
	public void testSetLiberador()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setLiberador(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.LIBERADOR_SOLICITACAO.getDescricao(), meioComunicacao.get(EnviarPara.LIBERADOR_SOLICITACAO.getId()));
	}
	
	public void testSetAvaliador()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setAvaliadorAvaliacaoDesempenho(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getDescricao(), meioComunicacao.get(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId()));
	}
	
	public void testSetColaborador()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setColaborador(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.COLABORADOR.getDescricao(), meioComunicacao.get(EnviarPara.COLABORADOR.getId()));
	}

	public void testSetResponsavelRH()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setResponsavelRH(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.RESPONSAVEL_RH.getDescricao(), meioComunicacao.get(EnviarPara.RESPONSAVEL_RH.getId()));
	}
	
	public void testSetResponsavelTecnico()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setResponsavelTecnico(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.RESPONSAVEL_TECNICO.getDescricao(), meioComunicacao.get(EnviarPara.RESPONSAVEL_TECNICO.getId()));
	}
	
	public void testSetPerfilAutorizadoExamesPrevistos()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setPerfilAutorizadoExamesPrevistos(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getDescricao(), meioComunicacao.get(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getId()));
	}

	public void testSetGerenciadorMensagemPeriodoExpericencia()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setGerenciadorDeMensagemPeriodoExperiencia(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao(), meioComunicacao.get(EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getId()));
	}
	
	public void testSetRecebeMensagemPeriodoExperiencia()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		EnviarPara.setRecebeMensagemPeriodoExperiencia(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao(), meioComunicacao.get(EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getId()));
	}
	
}
