package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.Mensagem;

public class MensagemDaoHibernate extends GenericDaoHibernate<Mensagem> implements MensagemDao
{

	public void removeMensagemDesligamento(Long colaboradorId) 
	{
		String  hql = " delete from UsuarioMensagem where mensagem_id in (select id from Mensagem where colaborador.id = :colaboradorId and tipo = :tipo) ";
		Query query = getSession().createQuery(hql);
		query.setLong("colaboradorId", colaboradorId);
		query.setCharacter("tipo", TipoMensagem.DESLIGAMENTO);
		query.executeUpdate();	
		
		hql = " delete from Mensagem as m where m.colaborador.id = :colaboradorId and m.tipo = :tipo ";
		query = getSession().createQuery(hql);
		query.setLong("colaboradorId", colaboradorId);
		query.setCharacter("tipo", TipoMensagem.DESLIGAMENTO);
		query.executeUpdate();
	}
}