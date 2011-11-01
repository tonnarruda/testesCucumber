package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Mensagem;

public interface MensagemDao extends GenericDao<Mensagem> 
{
	void removeMensagemDesligamento(Long colaboradorId);
}