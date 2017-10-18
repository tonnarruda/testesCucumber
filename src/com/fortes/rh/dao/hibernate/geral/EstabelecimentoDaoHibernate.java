package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

@SuppressWarnings("unchecked")
public class EstabelecimentoDaoHibernate extends GenericDaoHibernate<Estabelecimento> implements EstabelecimentoDao
{
	/**
	 * @author Igo Coelho
	 * @param codigo do Fortes Pessoal
	 * @param id da Empresa do RH
	 * @since 09/06/2008
	 */
	public boolean remove(String codigo, Long id)
	{
		String hql = "delete from Estabelecimento e where e.codigoAC = :codigo and e.empresa.id = :id";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setLong("id", id);//tem que ser por ID, ta correto(CUIDADO: caso mude tem que verificar o grupoAC)
		int result = query.executeUpdate();
		return result == 1;
	}

	/**
	 * @author Igo Coelho
	 * @param codigo do Fortes Pessoal
	 * @param codigo da Empresa no Fortes Pessoal
	 * @since 11/06/2008
	 */
	public Estabelecimento findByCodigo(String codigo, String empCodigo, String grupoAC)
	{
		String hql = "select est from Estabelecimento est left join est.empresa emp where est.codigoAC = :codigo and emp.codigoAC = :empCodigo and emp.grupoAC = :grupoac";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setString("empCodigo", empCodigo);
		query.setString("grupoac", grupoAC);
		return (Estabelecimento) query.uniqueResult();
	}

	public Collection<Estabelecimento> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("emp.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.empresa.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return criteria.list();
	}
	
	public Collection<Estabelecimento> findByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.endereco.cidade", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("e.endereco.uf", "uf", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.endereco.logradouro"), "logradouro");
		p.add(Projections.property("e.endereco.numero"), "numero");
		p.add(Projections.property("e.endereco.complemento"), "complemento");
		p.add(Projections.property("e.endereco.bairro"), "bairro");
		p.add(Projections.property("e.endereco.cep"), "cep");
		p.add(Projections.property("uf.sigla"), "ufSigla");
		p.add(Projections.property("c.nome"), "cidadeNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));
		
		return criteria.list();
	}
	
	public Estabelecimento findEstabelecimentoCodigoAc(Long estabelecimentoId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.codigoAC"), "codigoAC");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", estabelecimentoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return (Estabelecimento) criteria.uniqueResult();
	}

	public Collection<Estabelecimento> findEstabelecimentos(Long[] estabelecimentoIds, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.codigoAC"), "codigoAC");
		
		criteria.setProjection(p);
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			criteria.add(Expression.in("e.id", estabelecimentoIds));
		
		if(empresaId != null)
			criteria.add(Expression.eq("e.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));
		
		return criteria.list();
	}

	public boolean verificaCnpjExiste(String complemento, Long id, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.complementoCnpj", complemento));
		criteria.add(Expression.eq("emp.id", empresaId));
		criteria.add(Expression.not(Expression.eq("e.id", id)));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return !criteria.list().isEmpty();

	}

	public Estabelecimento findEstabelecimentoByCodigoAc(String estabelecimentoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");

		criteria.setProjection(p);
		criteria.add(Expression.eq("e.codigoAC", estabelecimentoCodigoAC));
		criteria.add(Expression.eq("emp.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("emp.grupoAC", grupoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return (Estabelecimento) criteria.uniqueResult();
	}

	public Estabelecimento findComEnderecoById(Long estabelecimentoId)
	{
		String hql = "select est from Estabelecimento est where est.id = :estabelecimentoId";
		
		Query query = getSession().createQuery(hql);
		query.setLong("estabelecimentoId", estabelecimentoId);
		
		return (Estabelecimento) query.uniqueResult();
	}

	public Collection<Estabelecimento> findAllSelect(Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("emp.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);

		criteria.add(Expression.in("e.empresa.id", empresaIds));

		criteria.addOrder(Order.asc("emp.nome"));
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return criteria.list();
	}

	public Collection<Estabelecimento> findSemCodigoAC(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "es");
		criteria.createCriteria("es.empresa", "em");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.nome"), "nome");
		p.add(Projections.property("em.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.isNull("es.codigoAC"));
		
		if(empresaId != null)
			criteria.add(Expression.eq("em.id", empresaId));

		criteria.addOrder(Order.asc("em.nome"));
		criteria.addOrder(Order.asc("es.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	
	}

	public void updateCodigoAC(Long estabelecimentoId, String codigoAC) 
	{
		String hql = "update Estabelecimento e set e.codigoAC = :codigoAC where e.id = :estabelecimentoId";
		Query query = getSession().createQuery(hql);
		query.setString("codigoAC", codigoAC);
		query.setLong("estabelecimentoId", estabelecimentoId);

		query.executeUpdate();
	}

	public Collection<Estabelecimento> findByMedicoCoordenador(Long medicoCoordenadorId) {
		Criteria criteria = getSession().createCriteria(MedicoCoordenador.class, "mc");
		criteria.createCriteria("mc.estabelecimentos", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("mc.id", medicoCoordenadorId));
		criteria.addOrder(Order.asc("es.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<Estabelecimento> findByEngenheiroResponsavel(Long engenheiroResponsavelId) {
		Criteria criteria = getSession().createCriteria(EngenheiroResponsavel.class, "eng");
		criteria.createCriteria("eng.estabelecimentos", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("eng.id", engenheiroResponsavelId));
		criteria.addOrder(Order.asc("es.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
}