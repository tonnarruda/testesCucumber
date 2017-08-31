package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

@SuppressWarnings("unchecked")
public class RiscoMedicaoRiscoDaoHibernate extends GenericDaoHibernate<RiscoMedicaoRisco> implements RiscoMedicaoRiscoDao
{
	public boolean removeByMedicaoRisco(Long medicaoRiscoId) 
	{
		String hql = "delete from RiscoMedicaoRisco r where r.medicaoRisco.id=:id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", medicaoRiscoId);
		
		return query.executeUpdate() == 1;
	}

	public Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDoAmbiente(Long ambienteId, Date data) 
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new RiscoMedicaoRisco(rm.descricaoPpra, rm.descricaoLtcat, rm.tecnicaUtilizada, rm.intensidadeMedida, r.descricao, r.grupoRisco, r, m.data, ra.medidaDeSeguranca, ra.periodicidadeExposicao) ");
		hql.append("from RiscoMedicaoRisco rm ");
		hql.append("	join rm.risco r ");
		hql.append("	join rm.medicaoRisco m ");
		hql.append("	inner join m.ambiente a ");
		hql.append("	inner join a.historicoAmbientes ha ");
		hql.append("	left join ha.riscoAmbientes ra with ra.risco.id = r.id ");
		hql.append("where m.ambiente.id = :ambienteId ");
		hql.append("	and m.data = ( select max(m2.data) from MedicaoRisco m2 where m2.data <= :data and m2.ambiente.id = m.ambiente.id) ");
		hql.append("	and ha.data = ( select max(ha2.data) from HistoricoAmbiente ha2 where ha2.data <= :data and ha2.ambiente.id = m.ambiente.id) ");
		hql.append("order by r.descricao ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("ambienteId", ambienteId);

		return query.list();
	}
	
	public Collection<RiscoMedicaoRisco> findByRiscoAteData(Long riscoId, Long ambienteOuFuncaoId, Date dataFim, boolean controlaRiscoPorAmbiente)
	{
		StringBuilder hql = new StringBuilder("select new RiscoMedicaoRisco(rm.tecnicaUtilizada, rm.intensidadeMedida, m.id, m.data) ");
		hql.append("from RiscoMedicaoRisco rm ");
		hql.append("join rm.medicaoRisco m ");
		hql.append("where rm.risco.id = :riscoId ");
		
		if(controlaRiscoPorAmbiente)
			hql.append("and m.ambiente.id = :ambienteOuFuncaoId ");
		else
			hql.append("and m.funcao.id = :ambienteOuFuncaoId ");
		
		hql.append("and m.data < :dataFim ");
		hql.append("order by m.data desc ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("riscoId", riscoId);
		query.setLong("ambienteOuFuncaoId", ambienteOuFuncaoId);
		query.setDate("dataFim", dataFim);
		
		return query.list();
	}

	public MedicaoRisco findUltimaAteData(Long ambienteOuFuncaoId, Date historicoAmbienteOuFuncaoData, boolean controlaRiscoPorAmbiente)
	{
		Criteria criteria = getSession().createCriteria(MedicaoRisco.class, "m");
		
		if(controlaRiscoPorAmbiente)
			criteria.add(Expression.eq("m.ambiente.id", ambienteOuFuncaoId));
		else
			criteria.add(Expression.eq("m.funcao.id", ambienteOuFuncaoId));
		
		criteria.add(Expression.le("m.data", historicoAmbienteOuFuncaoData));
		criteria.addOrder(Order.desc("m.data"));
		
		criteria.setMaxResults(1);
		return (MedicaoRisco)criteria.uniqueResult();
	}

	public Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDaFuncao(Long funcaoId, Date data) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new RiscoMedicaoRisco(rm.descricaoPpra, rm.descricaoLtcat, rm.tecnicaUtilizada, rm.intensidadeMedida, r.descricao, r.grupoRisco, r, m.data, rf.medidaDeSeguranca, rf.periodicidadeExposicao) ");
		hql.append("from RiscoMedicaoRisco rm ");
		hql.append("	join rm.risco r ");
		hql.append("	join rm.medicaoRisco m ");
		hql.append("	inner join m.funcao f ");
		hql.append("	inner join f.historicoFuncaos hf ");
		hql.append("	left join hf.riscoFuncaos rf with rf.risco.id = r.id ");
		hql.append("where m.funcao.id = :funcaoId ");
		hql.append("	and m.data = ( select max(m2.data) from MedicaoRisco m2 where m2.data <= :data and m2.funcao.id = m.funcao.id) ");
		hql.append("	and hf.data = ( select max(hf2.data) from HistoricoFuncao hf2 where hf2.data <= :data and hf2.funcao.id = m.funcao.id) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("funcaoId", funcaoId);
		
		return query.list();
	}
}
