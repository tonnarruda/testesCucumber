package com.fortes.rh.dao.hibernate.geral;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.geral.Mensagem;

public class MensagemDaoHibernate extends GenericDaoHibernate<Mensagem> implements MensagemDao
{
	public void removeMensagensColaborador(Long colaboradorId, Character tipo) 
	{
		StringBuilder hql = new StringBuilder("delete from UsuarioMensagem where mensagem_id in (select id from Mensagem where colaborador.id = :colaboradorId ");
		if (tipo != null) 
			hql.append(" and tipo = :tipo ");
		hql.append(")");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		if (tipo != null)
			query.setCharacter("tipo", tipo);
		
		query.executeUpdate();	
		
		hql = new StringBuilder("delete from Mensagem as m where m.colaborador.id = :colaboradorId ");
		if (tipo != null)
			hql.append(" and m.tipo = :tipo ");
		
		query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		if (tipo != null)
			query.setCharacter("tipo", tipo);
		
		query.executeUpdate();
	}

	public void removeByAvaliacaoId(Long avaliacaoId) 
	{
		String queryHQL = "delete from UsuarioMensagem where mensagem_id in (select id from Mensagem m where m.avaliacao.id = :avaliacaoId) ";
		getSession().createQuery(queryHQL).setLong("avaliacaoId", avaliacaoId).executeUpdate();	

		queryHQL = "delete from Mensagem where avaliacao.id = :avaliacaoId";
		getSession().createQuery(queryHQL).setLong("avaliacaoId", avaliacaoId).executeUpdate();	
	}

	public void removerMensagensViculadasByColaborador(Long[] colaboradoresIds) {
		
		String[] sql = new String[] {"DELETE FROM usuariomensagem where mensagem_id in(SELECT id from mensagem 	where link like '%colaborador.id=%'" + 
										"and (cast(substring(link,'colaborador.id=([0-9]{1,9})')as integer)) in ("+StringUtils.join(colaboradoresIds, ",")+") "
										+ "and colaborador_id not in ("+StringUtils.join(colaboradoresIds, ",")+")); "};
		JDBCConnection.executeQuery(sql);
		
		sql = new String[] {"delete from mensagem where link like '%colaborador.id=%' and (cast(substring(link,'colaborador.id=([0-9]{1,9})')as integer)) in ("+StringUtils.join(colaboradoresIds, ",")+") "
				+ "and colaborador_id not in ("+StringUtils.join(colaboradoresIds, ",")+"); "};
		JDBCConnection.executeQuery(sql);
	}
}