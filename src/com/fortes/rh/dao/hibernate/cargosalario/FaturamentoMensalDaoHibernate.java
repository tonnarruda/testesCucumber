package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;

@SuppressWarnings("unchecked")
public class FaturamentoMensalDaoHibernate extends GenericDaoHibernate<FaturamentoMensal> implements FaturamentoMensalDao
{
	public Collection<FaturamentoMensal> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		criteria.createCriteria("f.estabelecimento", "e", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.mesAno"), "mesAno");
		p.add(Projections.property("f.valor"), "valor");
		p.add(Projections.property("f.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("e.nome"), "estabelecimentoNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("f.empresa.id", empresaId));
		criteria.addOrder(Order.asc("f.mesAno"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<FaturamentoMensal> findByPeriodo(Date inicio, Date fim, Long empresaId, Long[] estabelecimentosIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.mesAno"), "mesAno");
		p.add(Projections.property("f.valor"), "valor");
		p.add(Projections.property("f.empresa.id"), "projectionEmpresaId");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("f.mesAno", inicio, fim));		
		criteria.add(Expression.eq("f.empresa.id", empresaId));
	
		if(estabelecimentosIds!=null && estabelecimentosIds.length>0)
			criteria.add(Expression.in("f.estabelecimento.id", estabelecimentosIds));
		
		criteria.addOrder(Order.asc("f.mesAno"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Double somaByPeriodo(Date dataIni, Date dataFim, Long[] empresaIds) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		
		criteria.setProjection(Projections.sum("f.valor"));
		
		criteria.add(Expression.between("f.mesAno", dataIni, dataFim));		
		criteria.add(Expression.in("f.empresa.id", empresaIds));
		
		Double valor = (Double) criteria.uniqueResult();
		
		return valor == null ? 0.0 : valor;
	}

	public Boolean isExisteNaMesmaDataAndEstabelecimento(FaturamentoMensal faturamentoMensal) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		criteria.setProjection(p);
		
		if(faturamentoMensal.getId() != null)
			criteria.add(Expression.not(Expression.eq("f.id", faturamentoMensal.getId())));
		
		if(faturamentoMensal.getEstabelecimento() != null && faturamentoMensal.getEstabelecimento().getId() != null )
			criteria.add(Expression.eq("f.estabelecimento.id", faturamentoMensal.getEstabelecimento().getId()));

		criteria.add(Expression.eq("f.empresa.id", faturamentoMensal.getEmpresa().getId()));
		criteria.add(Expression.eq("f.mesAno", faturamentoMensal.getMesAno()));
		
		return criteria.list().size() > 0;
	}
}
