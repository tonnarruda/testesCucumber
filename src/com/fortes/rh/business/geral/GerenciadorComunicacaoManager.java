package com.fortes.rh.business.geral;


import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;

public interface GerenciadorComunicacaoManager extends GenericManager<GerenciadorComunicacao>
{
	void executeEncerrarSolicitacao(Empresa empresa, Long solicitacaoId) throws Exception;
	void emailSolicitante(Solicitacao solicitacao, Empresa empresa, Usuario usuario);
	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao);
}
