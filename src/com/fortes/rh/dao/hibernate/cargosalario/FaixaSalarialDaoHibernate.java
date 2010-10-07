package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;

@SuppressWarnings("unchecked")
public class FaixaSalarialDaoHibernate extends GenericDaoHibernate<FaixaSalarial> implements FaixaSalarialDao
{
	private static final int CARGO = 1;
	private static final int FAIXASALARIAL = 2;

	@Override
	public Collection<FaixaSalarial> findAll()
	{
		throw new NoSuchMethodError("Deve considerar a empresa na listagem das Faixas Salariais.");
	}

	@Override
	public Collection<FaixaSalarial> findAll(String[] orderBy)
	{
		throw new NoSuchMethodError("Deve considerar a empresa na listagem das Faixas Salariais.");
	}

	public FaixaSalarial findCodigoACById(Long id)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.codigoAC"), "codigoAC");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		criteria.setMaxResults(1);

		return (FaixaSalarial) criteria.uniqueResult();

	}

	public Collection<FaixaSalarial> findAllSelectByCargo(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("fs.nomeACPessoal"), "nomeACPessoal");
		p.add(Projections.property("fs.codigoAC"), "codigoAC");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list();
	}

	public FaixaSalarial findByFaixaSalarialId(Long faixaSalarialId)
	{
		Criteria criteria = getCriteriaFaixaSalarial(faixaSalarialId, FAIXASALARIAL);
		criteria.setMaxResults(1);

		return (FaixaSalarial) criteria.uniqueResult();
	}

	public Collection<FaixaSalarial> findFaixaSalarialByCargo(Long cargoId)
	{
		Criteria criteria = getCriteriaFaixaSalarial(cargoId, CARGO);

		return criteria.list();
	}

	private Criteria getCriteriaFaixaSalarial(Long id, int origem)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "f");
		criteria.createCriteria("f.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");
		p.add(Projections.property("f.nomeACPessoal"), "nomeACPessoal");
		p.add(Projections.property("f.codigoAC"), "codigoAC");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		if(origem == CARGO)
		{
			criteria.add(Expression.eq("c.id", id));
			criteria.addOrder(Order.asc("c.nome"));
		}
		else if (origem == FAIXASALARIAL)
			criteria.add(Expression.eq("f.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria;
	}

	public FaixaSalarial findHistoricoAtual(Long faixaSalarialId, Date dataHistorico)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new FaixaSalarial(fs.id, hf.id, hf.data, hf.tipo, hf.valor, hf.quantidade, i.id, hi.valor, hi.data) ");
		hql.append("from FaixaSalarial fs ");
		hql.append("left join fs.faixaSalarialHistoricos hf with hf.data = (select max(hf2.data) ");
		hql.append("                                            from FaixaSalarialHistorico hf2 ");
		hql.append("                                           where hf2.faixaSalarial.id = fs.id ");
		hql.append("                                            and hf2.data <= :data ");
		hql.append("											and	hf2.status != :status) ");
		hql.append("left join hf.indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :data) ");
		hql.append("where fs.id = :faixaSalarialId ");
		hql.append("order by hf.data ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("data", dataHistorico);
		query.setLong("faixaSalarialId", faixaSalarialId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);

		return (FaixaSalarial) query.uniqueResult();
	}

	public Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresa.getId()));

		if(ativo != null)
			criteria.add(Expression.eq("c.ativo", ativo));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list();
	}

	public void updateCodigoAC(String codigoAC, Long faixaSalarialId)
	{
		String hql = "update FaixaSalarial set codigoAC = :codigoAC where id = :faixaSalarialId";

		Query query = getSession().createQuery(hql);
		query.setString("codigoAC", codigoAC);
		query.setLong("faixaSalarialId", faixaSalarialId);

		query.executeUpdate();
	}

	public boolean verifyExistsNomeByCargo(Long cargoId, String faixaNome)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("fs.cargo.id", cargoId));
		criteria.add(Expression.ilike("fs.nome",faixaNome));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list().size() > 0;
	}

	public FaixaSalarial findFaixaSalarialByCodigoAc(String faixaCodigoAC, String empresaCodigoAC)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "f");
		criteria.createCriteria("f.cargo", "car", Criteria.LEFT_JOIN);
		criteria.createCriteria("car.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("car.id"), "projectionCargoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.codigoAC", faixaCodigoAC));
		criteria.add(Expression.eq("emp.codigoAC", empresaCodigoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		criteria.setMaxResults(1);

		return (FaixaSalarial) criteria.uniqueResult();
	}

	public void updateNomeECargo(Long faixaSalarialId, Long cargoId, String faixaNome)
	{
		String hql = "update FaixaSalarial set cargo.id = :cargoId, nome = :faixaNome where id = :faixaSalarialId";

		Query q = getSession().createQuery(hql);

		q.setLong("cargoId", cargoId);
		q.setString("faixaNome", faixaNome);
		q.setLong("faixaSalarialId", faixaSalarialId);

		q.executeUpdate();
	}

	public Collection<FaixaSalarial> findByCargo(Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", cargoId));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list();
	}

	public Collection<Long> findByCargos(Collection<Long> cargoIds)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		criteria.setProjection(p);
		
		criteria.add(Expression.in("c.id", cargoIds));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}

	public void updateAC(TCargo tCargo)
	{
		String hql = "update FaixaSalarial set nome = :faixaNome, nomeACPessoal = :faixaNomeAC where id = :id";

		Query q = getSession().createQuery(hql);

		q.setString("faixaNome", tCargo.getDescricao());
		q.setString("faixaNomeAC", tCargo.getDescricaoACPessoal());
		q.setLong("id", tCargo.getId());

		q.executeUpdate();		
	}
}