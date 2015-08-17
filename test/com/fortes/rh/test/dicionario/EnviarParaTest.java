package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.EnviarPara;

public class EnviarParaTest extends TestCase
{
	
	public void testChave()
	{
		assertEquals(21, EnviarPara.values().length);
		
		assertEquals(new Integer(0), EnviarPara.SELECIONAR_ENVIAR_PARA.getId());
		assertEquals(new Integer(1), EnviarPara.USUARIOS.getId());
		assertEquals(new Integer(2), EnviarPara.GESTOR_AREA.getId());
		assertEquals(new Integer(3), EnviarPara.CANDIDATO_NAO_APTO.getId());
		assertEquals(new Integer(4), EnviarPara.SOLICITANTE_SOLICITACAO.getId());
		assertEquals(new Integer(5), EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId());
		assertEquals(new Integer(6), EnviarPara.COLABORADOR.getId());
		assertEquals(new Integer(7), EnviarPara.RESPONSAVEL_RH.getId());
		assertEquals(new Integer(8), EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getId());
		assertEquals(new Integer(12), EnviarPara.RESPONSAVEL_TECNICO.getId());
		assertEquals(new Integer(13), EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId());
		assertEquals(new Integer(14), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId());
		assertEquals(new Integer(15), EnviarPara.RESPONSAVEL_LIMITE_CONTRATO.getId());
		assertEquals(new Integer(17), EnviarPara.COLABORADOR_AVALIADO.getId());
		assertEquals(new Integer(18), EnviarPara.COGESTOR_AREA.getId());
		assertEquals(new Integer(19), EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL.getId());
		assertEquals(new Integer(20), EnviarPara.APROVAR_REPROVAR_SOLICITACAO_DESLIGAMENTO.getId());
		assertEquals(new Integer(21), EnviarPara.SOLICITANTE_DESLIGAMENTO.getId());
		assertEquals(new Integer(22), EnviarPara.APLICAR_REALINHAMENTO.getId());
		assertEquals(new Integer(23), EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL_AND_GESTOR.getId());
		
		assertEquals(new Integer(99), EnviarPara.AVULSO.getId());
	}

	public void testDescricao()
	{
		assertEquals("Selecione...", EnviarPara.SELECIONAR_ENVIAR_PARA.getDescricao());
		assertEquals("Usuários", EnviarPara.USUARIOS.getDescricao());
		assertEquals("Gestor da área organizacional", EnviarPara.GESTOR_AREA.getDescricao());
		assertEquals("Cogestor da área organizacional", EnviarPara.COGESTOR_AREA.getDescricao());
		assertEquals("Candidatos não aptos", EnviarPara.CANDIDATO_NAO_APTO.getDescricao());
		assertEquals("Solicitante", EnviarPara.SOLICITANTE_SOLICITACAO.getDescricao());
		assertEquals("Avaliador", EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getDescricao());
		assertEquals("Colaborador", EnviarPara.COLABORADOR.getDescricao());
		assertEquals("Responsável do RH", EnviarPara.RESPONSAVEL_RH.getDescricao());
		assertEquals("Responsável do setor pessoal", EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getDescricao());
		assertEquals("Responsável Técnico", EnviarPara.RESPONSAVEL_TECNICO.getDescricao());
		assertEquals("Usuários com perfil de receber mensagem do Fortes Pessoal", EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getDescricao());
		assertEquals("Usuários com permissão de visualizar solicitação pessoal", EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getDescricao());
		assertEquals("Responsável pelo limite de colaboradores por cargo", EnviarPara.RESPONSAVEL_LIMITE_CONTRATO.getDescricao());
		assertEquals("Colaborador (Avaliado)", EnviarPara.COLABORADOR_AVALIADO.getDescricao());
		assertEquals("Usuários com perfil de aprovar/reprovar solicitação de pessoal", EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL.getDescricao());
		assertEquals("Usuários com perfil de aprovar/reprovar solicitação de desligamento", EnviarPara.APROVAR_REPROVAR_SOLICITACAO_DESLIGAMENTO.getDescricao());
		assertEquals("Avulso", EnviarPara.AVULSO.getDescricao());
	}

	public void testGetDescricaoById()
	{
		assertEquals("Selecione...", EnviarPara.getDescricaoById(0));
		assertEquals("Usuários", EnviarPara.getDescricaoById(1));
		assertEquals("Gestor da área organizacional", EnviarPara.getDescricaoById(2));
		assertEquals("Cogestor da área organizacional", EnviarPara.getDescricaoById(18));
		assertEquals("Candidatos não aptos", EnviarPara.getDescricaoById(3));
		assertEquals("Solicitante", EnviarPara.getDescricaoById(4));
		assertEquals("Avaliador", EnviarPara.getDescricaoById(5));
		assertEquals("Colaborador", EnviarPara.getDescricaoById(6));
		assertEquals("Responsável do RH", EnviarPara.getDescricaoById(7));
		assertEquals("Responsável do setor pessoal", EnviarPara.getDescricaoById(8));
		assertEquals("Responsável Técnico", EnviarPara.getDescricaoById(12));
		assertEquals("Usuários com perfil de receber mensagem do Fortes Pessoal", EnviarPara.getDescricaoById(13));
		assertEquals("Usuários com permissão de visualizar solicitação pessoal", EnviarPara.getDescricaoById(14));
		assertEquals("Responsável pelo limite de colaboradores por cargo", EnviarPara.getDescricaoById(15));
		assertEquals("Colaborador (Avaliado)", EnviarPara.getDescricaoById(17));
		assertEquals("Cogestor da área organizacional", EnviarPara.getDescricaoById(18));
		assertEquals("Usuários com perfil de aprovar/reprovar solicitação de pessoal", EnviarPara.getDescricaoById(19));
		assertEquals("Usuários com perfil de aprovar/reprovar solicitação de desligamento", EnviarPara.getDescricaoById(20));
		assertEquals("Avulso", EnviarPara.getDescricaoById(99));
	}
}
