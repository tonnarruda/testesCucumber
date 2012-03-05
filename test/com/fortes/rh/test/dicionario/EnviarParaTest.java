package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.EnviarPara;

public class EnviarParaTest extends TestCase
{
	
	public void testChave()
	{
		assertEquals(16, EnviarPara.values().length);
		
		assertEquals(new Integer(0), EnviarPara.SELECIONAR_ENVIAR_PARA.getId());
		assertEquals(new Integer(1), EnviarPara.USUARIO.getId());
		assertEquals(new Integer(2), EnviarPara.GESTOR_AREA.getId());
		assertEquals(new Integer(3), EnviarPara.CANDIDATO_NAO_APTO.getId());
		assertEquals(new Integer(4), EnviarPara.SOLICITANTE_SOLICITACAO.getId());
		assertEquals(new Integer(5), EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId());
		assertEquals(new Integer(6), EnviarPara.COLABORADOR.getId());
		assertEquals(new Integer(7), EnviarPara.RESPONSAVEL_RH.getId());
		assertEquals(new Integer(8), EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getId());
		assertEquals(new Integer(9), EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getId());
		assertEquals(new Integer(10), EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getId());
		assertEquals(new Integer(11), EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getId());
		assertEquals(new Integer(12), EnviarPara.RESPONSAVEL_TECNICO.getId());
		assertEquals(new Integer(13), EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId());
		assertEquals(new Integer(14), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId());
		assertEquals(new Integer(99), EnviarPara.AVULSO.getId());
	}

	public void testDescricao()
	{
		assertEquals("Selecione...", EnviarPara.SELECIONAR_ENVIAR_PARA.getDescricao());
		assertEquals("Usuário", EnviarPara.USUARIO.getDescricao());
		assertEquals("Gestor da área organizacional", EnviarPara.GESTOR_AREA.getDescricao());
		assertEquals("Candidatos não aptos", EnviarPara.CANDIDATO_NAO_APTO.getDescricao());
		assertEquals("Solicitante", EnviarPara.SOLICITANTE_SOLICITACAO.getDescricao());
		assertEquals("Avaliador", EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getDescricao());
		assertEquals("Colaborador", EnviarPara.COLABORADOR.getDescricao());
		assertEquals("Responsável do RH", EnviarPara.RESPONSAVEL_RH.getDescricao());
		assertEquals("Responsável do setor pessoal", EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getDescricao());
		assertEquals("Usuários com permissão de receber emails de exames previstos", EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getDescricao());
		assertEquals("Usuários com permissão de gerenciador de mensagem por período de experiência", EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao());
		assertEquals("Usuários com permissão de receber mensagem por período de experiência", EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao());
		assertEquals("Responsável Tecnico", EnviarPara.RESPONSAVEL_TECNICO.getDescricao());
		assertEquals("Usuários com perfil de receber mensagem do AC Pessoal", EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getDescricao());
		assertEquals("Usuários com permissão de visualizar solicitação pessoal", EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getDescricao());
		assertEquals("Avulso", EnviarPara.AVULSO.getDescricao());
	}

	public void testGetDescricaoById()
	{
		assertEquals("Selecione...", EnviarPara.getDescricaoById(0));
		assertEquals("Usuário", EnviarPara.getDescricaoById(1));
		assertEquals("Gestor da área organizacional", EnviarPara.getDescricaoById(2));
		assertEquals("Candidatos não aptos", EnviarPara.getDescricaoById(3));
		assertEquals("Solicitante", EnviarPara.getDescricaoById(4));
		assertEquals("Avaliador", EnviarPara.getDescricaoById(5));
		assertEquals("Colaborador", EnviarPara.getDescricaoById(6));
		assertEquals("Responsável do RH", EnviarPara.getDescricaoById(7));
		assertEquals("Responsável do setor pessoal", EnviarPara.getDescricaoById(8));
		assertEquals("Usuários com permissão de receber emails de exames previstos", EnviarPara.getDescricaoById(9));
		assertEquals("Usuários com permissão de gerenciador de mensagem por período de experiência", EnviarPara.getDescricaoById(10));
		assertEquals("Usuários com permissão de receber mensagem por período de experiência", EnviarPara.getDescricaoById(11));
		assertEquals("Responsável Tecnico", EnviarPara.getDescricaoById(12));
		assertEquals("Usuários com perfil de receber mensagem do AC Pessoal", EnviarPara.getDescricaoById(13));
		assertEquals("Usuários com permissão de visualizar solicitação pessoal", EnviarPara.getDescricaoById(14));
		assertEquals("Avulso", EnviarPara.getDescricaoById(99));
	}

	
}
