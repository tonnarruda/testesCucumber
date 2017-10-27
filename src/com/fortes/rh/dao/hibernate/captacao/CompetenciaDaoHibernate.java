package com.fortes.rh.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.dicionario.CompetenciasConsideradas;
import com.fortes.rh.util.LongUtil;

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

	@SuppressWarnings("unchecked")
	public Collection<Competencia> findByAvaliacoesDesempenho(Long empresaId, Long[] avaliacoesDesempenhoIds, String competenciasConsideradas) {
		if(avaliacoesDesempenhoIds.length == 0)
			return new ArrayList<Competencia>();
		
		StringBuilder sqlBase = new StringBuilder();
		sqlBase.append("select distinct comp.id, comp.nome ");
		sqlBase.append("from ConfiguracaoNivelCompetencia cnc ");
		sqlBase.append("  inner join Competencia comp on comp.id = cnc.competencia_id and comp.tipo = cnc.tipocompetencia");
		sqlBase.append("  inner join ConfiguracaoNivelCompetenciaColaborador cncc on cncc.id = cnc.configuracaonivelcompetenciacolaborador_id ");
		sqlBase.append("  inner join ColaboradorQuestionario cq on cq.id = cncc.colaboradorquestionario_id ");

		StringBuilder sqlFinal = new StringBuilder();
		if(competenciasConsideradas.equals(CompetenciasConsideradas.TODAS)){
			sqlFinal.append(sqlBase);
			sqlFinal.append("where cq.avaliacaodesempenho_id in (:avaliacoesIds) ");
			sqlFinal.append("  and cq.respondidaparcialmente = false ");
			sqlFinal.append("  and comp.empresa_id = :empresaId ");
			sqlFinal.append("order by comp.nome ");
		} else {
			for (int i = 0; i < avaliacoesDesempenhoIds.length; i++){ 
				sqlFinal.append(sqlBase);
				sqlFinal.append("where cq.avaliacaodesempenho_id = " + avaliacoesDesempenhoIds[i]);
				sqlFinal.append("  and cq.respondidaparcialmente = false ");
				sqlFinal.append("  and comp.empresa_id = :empresaId ");
				
				if(i < avaliacoesDesempenhoIds.length - 1){
					sqlFinal.append("intersect ");
				}
			}
		}

		Query query = getSession().createSQLQuery(sqlFinal.toString());

		query.setLong("empresaId", empresaId);
		
		if(competenciasConsideradas.equals(CompetenciasConsideradas.TODAS))
			query.setParameterList("avaliacoesIds", avaliacoesDesempenhoIds);

		Collection<Object[]> lista = query.list();

		Collection<Competencia> competencias = new ArrayList<Competencia>();
		
		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();){
			Object[] obj = it.next();
			
			Competencia competencia = new Competencia();
			competencia.setId(LongUtil.bigIntegerToLong(obj[0], null));
			competencia.setNome((String) obj[1]);
			
			competencias.add(competencia);
		}
		
		return competencias;
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