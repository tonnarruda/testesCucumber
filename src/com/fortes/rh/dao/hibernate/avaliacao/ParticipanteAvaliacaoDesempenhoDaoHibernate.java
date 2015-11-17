package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;

public class ParticipanteAvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<ParticipanteAvaliacaoDesempenho> implements ParticipanteAvaliacaoDesempenhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<Colaborador> findParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, e.id, ");
		hql.append("ao.id, ao.nome, fs.id, fs.nome, ca.id, ca.nome ) ");
		hql.append("from ParticipanteAvaliacaoDesempenho as p ");
		hql.append("inner join p.colaborador as co ");
		hql.append("inner join co.empresa as e ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("inner join hc.faixaSalarial as fs ");
		hql.append("inner join fs.cargo as ca ");
		hql.append("inner join hc.areaOrganizacional as ao ");
		hql.append("where ");
		hql.append("  hc.data = (");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date  ");
		hql.append("  ) ");
		hql.append("and p.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");

		if(tipo != null)
			hql.append("and tipo = :tipo ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		if(tipo != null)
			query.setCharacter("tipo", tipo);
		
		return query.list();
	}
	
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception {
		String hql = "delete from ParticipanteAvaliacaoDesempenho where colaborador.id not in (:participantes) and avaliacaoDesempenho.id = :avaliacaoDesempenhoId and tipo = :tipo";
		
		Query query = getSession().createQuery(hql);
		query.setParameterList("participantes", participantes);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setCharacter("tipo", tipo);
		
		query.executeUpdate();
	}
}