package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.DocumentoAnexo;

public class DocumentoAnexoDaoHibernate extends GenericDaoHibernate<DocumentoAnexo> implements DocumentoAnexoDao
{

	@SuppressWarnings("unchecked")
	public Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(char origem, Long origemId, Long origemCandidatoId)
	{
		Criteria criteria = getSession().createCriteria(DocumentoAnexo.class, "da");
		criteria.createCriteria("da.etapaSeletiva","es",Criteria.LEFT_JOIN);
		criteria.createCriteria("da.tipoDocumento","td",Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("da.id"), "id");
		p.add(Projections.property("da.descricao"), "descricao");
		p.add(Projections.property("da.data"), "data");
		p.add(Projections.property("da.observacao"), "observacao");
		p.add(Projections.property("da.url"), "url");
		p.add(Projections.property("da.origem"), "origem");
		p.add(Projections.property("da.origemId"), "origemId");
		p.add(Projections.property("td.descricao"), "projectionTipoDocumentoDescricao");
		p.add(Projections.property("es.nome"), "projectionEtapaSeletivaNome");
		criteria.setProjection(p);

		if (origem == OrigemAnexo.COLABORADOR && origemCandidatoId != null)
			criteria.add(Expression.or(
							Expression.and(Expression.eq("da.origem", OrigemAnexo.COLABORADOR), Expression.eq("da.origemId", origemId)), 
							Expression.or(
									Expression.and(Expression.eq("da.origem", OrigemAnexo.CANDIDATO), Expression.eq("da.origemId", origemCandidatoId)), 
									Expression.and(Expression.eq("da.origem", OrigemAnexo.CANDIDATOEXTERNO), Expression.eq("da.origemId", origemCandidatoId))
							)
						));
		else if (origem == OrigemAnexo.CANDIDATO)
			criteria.add(Expression.or(
							Expression.and(Expression.eq("da.origem", OrigemAnexo.CANDIDATO), Expression.eq("da.origemId", origemId)), 
							Expression.and(Expression.eq("da.origem", OrigemAnexo.CANDIDATOEXTERNO), Expression.eq("da.origemId", origemId))
						));
		else
			criteria.add(Expression.and(Expression.eq("da.origem", origem), Expression.eq("da.origemId", origemId)));
		
		criteria.addOrder(Order.asc("da.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(DocumentoAnexo.class));

		return criteria.list();
	}

	public DocumentoAnexo findByIdProjection(Long documentoAnexoId)
	{
		Criteria criteria = getSession().createCriteria(DocumentoAnexo.class, "da");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("da.id"), "id");
		p.add(Projections.property("da.url"), "url");
		p.add(Projections.property("da.origem"), "origem");
		p.add(Projections.property("da.origemId"), "origemId");
		p.add(Projections.property("da.data"), "data");
		p.add(Projections.property("da.descricao"), "descricao");
		p.add(Projections.property("da.observacao"), "observacao");
		p.add(Projections.property("da.etapaSeletiva.id"), "projectionEtapaSeletivaId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("da.id", documentoAnexoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(DocumentoAnexo.class));
		
		return (DocumentoAnexo) criteria.uniqueResult();
	}
	
	public Collection<DocumentoAnexo> findByTurma(Long turmaId) 
	{
		Criteria criteria = getSession().createCriteria(Turma.class, "t");
		criteria.createCriteria("t.documentoAnexos", "tda");
		criteria.createCriteria("tda.documentoAnexos", "da");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("da.id"), "id");
		p.add(Projections.property("da.descricao"), "descricao");

		criteria.setProjection(Projections.distinct(p));
		criteria.add(Expression.eq("t.id", turmaId));

		criteria.addOrder(Order.asc("da.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}