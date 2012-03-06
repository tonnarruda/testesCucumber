package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;

public interface GerenciadorComunicacaoDao extends GenericDao<GerenciadorComunicacao> 
{
	public Collection<GerenciadorComunicacao> findByOperacaoId(Integer operacaoId, Long empresaId);
	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao);

}
