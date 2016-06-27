package com.fortes.rh.dao.hibernate.sesmt;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.sesmt.OrdemDeServico;

public class OrdemDeServicoDaoHibernate extends GenericDaoHibernate<OrdemDeServico> implements OrdemDeServicoDao {
	
	public OrdemDeServico findOrdemServicoProjection(Long id) {
		Criteria criteria = getSession().createCriteria(OrdemDeServico.class, "os");
		criteria.add(Expression.eq("os.id", id));
		
		criteria.setProjection(montaProjection());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(OrdemDeServico.class));
		return (OrdemDeServico) criteria.uniqueResult();
	}

	private ProjectionList montaProjection() {
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
		p.add(Projections.property("os.informacoesAdicionais"), "informacoesAdicionais");
		p.add(Projections.property("os.termoDeResponsabilidade"), "termoDeResponsabilidade");
		p.add(Projections.property("os.impressa"), "impressa");
		p.add(Projections.property("os.colaborador.id"), "colaboradorId");
		p.add(Projections.property("os.nomeCargo"), "nomeCargo");
		p.add(Projections.property("os.nomeEmpresa"), "nomeEmpresa");
		p.add(Projections.property("os.empresaCnpj"), "empresaCnpj");
		p.add(Projections.property("os.nomeEstabelecimento"), "nomeEstabelecimento");
		p.add(Projections.property("os.estabelecimentoComplementoCnpj"), "estabelecimentoComplementoCnpj");
		p.add(Projections.property("os.estabelecimentoEndereco"), "estabelecimentoEndereco");
		return p;
	}

	public OrdemDeServico ultimaOrdemDeServico(Long colaboradorId) {
		DetachedCriteria subQueryOS = DetachedCriteria.forClass(OrdemDeServico.class, "os2")
				.setProjection(Projections.max("os2.revisao"))
				.add(Restrictions.eqProperty("os2.colaborador.id", "os.colaborador.id"));
		
		Criteria criteria = getSession().createCriteria(OrdemDeServico.class, "os");
		criteria.add(Property.forName("os.revisao").eq(subQueryOS));	
		criteria.add(Expression.eq("os.colaborador.id", colaboradorId));
		
		criteria.setProjection(Projections.distinct(montaProjection()));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(OrdemDeServico.class));
		return (OrdemDeServico) criteria.uniqueResult();
	}
	
	public OrdemDeServico findUltimaOrdemDeServicoImpressa(Long colaboradorId) {
		DetachedCriteria subQueryOS = DetachedCriteria.forClass(OrdemDeServico.class, "os2")
				.setProjection(Projections.max("os2.revisao"))
				.add(Restrictions.eqProperty("os2.colaborador.id", "os.colaborador.id"))
				.add(Expression.eq("impressa", true));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("os.id"), "id"); 
		p.add(Projections.property("os.colaborador.id"), "colaboradorId");
		p.add(Projections.property("os.impressa"), "impressa");
		p.add(Projections.property("os.data"), "data");
		p.add(Projections.property("os.revisao"), "revisao");
		
		Criteria criteria = getSession().createCriteria(OrdemDeServico.class, "os");
		criteria.add(Property.forName("os.revisao").eq(subQueryOS));	
		criteria.add(Expression.eq("os.colaborador.id", colaboradorId));
		
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(OrdemDeServico.class));
		return (OrdemDeServico) criteria.uniqueResult();
	}
}
