package com.fortes.rh.dao.hibernate.sesmt;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.sesmt.OrdemDeServico;

public class OrdemDeServicoDaoHibernate extends GenericDaoHibernate<OrdemDeServico> implements OrdemDeServicoDao {
	
	public OrdemDeServico findOrdemServicoProjection(Long id) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("os.id"), "id");
		p.add(Projections.property("os.nomeColaborador"), "nomeColaborador");
		p.add(Projections.property("os.dataAdmisaoColaborador"), "dataAdmisaoColaborador");
		p.add(Projections.property("os.codigoCBO"), "codigoCBO");
		p.add(Projections.property("os.nomeFuncao"), "nomeFuncao");
		p.add(Projections.property("os.data"), "data");
		p.add(Projections.property("os.revisao"), "revisao");
		p.add(Projections.property("os.atividades"), "atividades");
		p.add(Projections.property("os.epis"), "epis");
		p.add(Projections.property("os.riscos"), "riscos");
		p.add(Projections.property("os.medidasPreventivas"), "medidasPreventivas");
		p.add(Projections.property("os.treinamentos"), "treinamentos");
		p.add(Projections.property("os.normasInternas"), "normasInternas");
		p.add(Projections.property("os.procedimentoEmCasoDeAcidente"), "procedimentoEmCasoDeAcidente");
		p.add(Projections.property("os.termoDeResponsabilidade"), "termoDeResponsabilidade");
		p.add(Projections.property("os.colaborador.id"), "colaboradorId");

		Criteria criteria = getSession().createCriteria(OrdemDeServico.class, "os");
		criteria.add(Expression.eq("os.id", id));
		
		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(OrdemDeServico.class));
		return (OrdemDeServico) criteria.uniqueResult();
	}
}
