package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.dao.DataAccessException;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.model.captacao.Competencia;

public class CompetenciaDaoHibernate extends GenericDaoHibernate<Competencia> implements CompetenciaDao
{
	public boolean existeNome(String nome, Long competenciaId, Character tipo, Long empresaId) 
	{
		getSession().flush(); //Necessário para que nos testes a view Competencia enxergue os dados inseridos via hibernate 
		
		Criteria criteria = getSession().createCriteria(Competencia.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.tipo"), "tipo");
		criteria.setProjection(p);

		criteria.add(Expression.ilike("c.nome", nome));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if (competenciaId != null && tipo != null)
			criteria.add(Expression.not(Expression.and(Expression.eq("c.id", competenciaId), Expression.eq("c.tipo", tipo))));

		return criteria.list().size() > 0;
	}
	
	public Competencia findCompetencia(Long competenciaId, Character tipoCompetencia) {
		getSession().flush(); //Necessário para que nos testes a view Competencia enxergue os dados inseridos via hibernate 
		
		Criteria criteria = getSession().createCriteria(Competencia.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.tipo"), "tipo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", competenciaId));
		criteria.add(Expression.eq("c.tipo", tipoCompetencia));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Competencia.class));
		
		return (Competencia) criteria.uniqueResult();
	}

	@Override
	public void remove(Competencia entity) throws DataAccessException {
		throw new RuntimeException("Impossível remover elementos de uma view");
	}

	@Override
	public void remove(Long arg0) throws DataAccessException {
		throw new RuntimeException("Impossível remover elementos de uma view");
	}

	@Override
	public void remove(Long[] arg0) throws DataAccessException {
		throw new RuntimeException("Impossível remover elementos de uma view");
	}

	@Override
	public Competencia save(Competencia entity) {
		throw new RuntimeException("Impossível inserir elementos em uma view");
	}

	@Override
	public void saveOrUpdate(Collection<Competencia> entities) {
		throw new RuntimeException("Impossível alterar elementos em uma view");
	}

	@Override
	public void update(Competencia entity) {
		throw new RuntimeException("Impossível alterar elementos em uma view");
	}
}