package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;

@Component
@SuppressWarnings("unchecked")
public class EleicaoDaoHibernate extends GenericDaoHibernate<Eleicao> implements EleicaoDao
{

	public Collection<Eleicao> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"eleicao");
		criteria.createCriteria("eleicao.estabelecimento", "estab", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("eleicao.id"), "id");
		p.add(Projections.property("eleicao.posse"), "posse");
		p.add(Projections.property("eleicao.votacaoIni"), "votacaoIni");
		p.add(Projections.property("eleicao.votacaoFim"), "votacaoFim");
		p.add(Projections.property("eleicao.descricao"), "descricao");
		p.add(Projections.property("eleicao.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("estab.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("estab.nome"), "projectionEstabelecimentoNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("eleicao.empresa.id", empresaId));

		criteria.addOrder( Order.desc("eleicao.posse") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void updateVotos(Eleicao eleicao)
	{
		String hql = "update Eleicao set qtdVotoBranco = :qtdBranco, qtdVotoNulo = :qtdNulo where id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", eleicao.getId());
		query.setInteger("qtdBranco", eleicao.getQtdVotoBranco());
		query.setInteger("qtdNulo", eleicao.getQtdVotoNulo());

		query.executeUpdate();
	}

	public Eleicao findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"eleicao");
		criteria.createCriteria("eleicao.estabelecimento", "estab", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("eleicao.id"), "id");
		p.add(Projections.property("eleicao.posse"), "posse");
		p.add(Projections.property("eleicao.votacaoIni"), "votacaoIni");
		p.add(Projections.property("eleicao.votacaoFim"), "votacaoFim");
		p.add(Projections.property("eleicao.descricao"), "descricao");
		p.add(Projections.property("eleicao.horarioVotacaoIni"), "horarioVotacaoIni");
		p.add(Projections.property("eleicao.horarioVotacaoFim"), "horarioVotacaoFim");
		p.add(Projections.property("eleicao.qtdVotoBranco"), "qtdVotoBranco");
		p.add(Projections.property("eleicao.qtdVotoNulo"), "qtdVotoNulo");
		p.add(Projections.property("eleicao.inscricaoCandidatoIni"), "inscricaoCandidatoIni");
		p.add(Projections.property("eleicao.inscricaoCandidatoFim"), "inscricaoCandidatoFim");
		p.add(Projections.property("eleicao.localInscricao"), "localInscricao");
		p.add(Projections.property("eleicao.localVotacao"), "localVotacao");
		p.add(Projections.property("eleicao.sindicato"), "sindicato");
		p.add(Projections.property("eleicao.apuracao"), "apuracao");
		p.add(Projections.property("eleicao.localApuracao"), "localApuracao");
		p.add(Projections.property("eleicao.horarioApuracao"), "horarioApuracao");
		p.add(Projections.property("eleicao.textoAtaEleicao"), "textoAtaEleicao");
		p.add(Projections.property("eleicao.textoEditalInscricao"), "textoEditalInscricao");
		p.add(Projections.property("eleicao.textoChamadoEleicao"), "textoChamadoEleicao");
		p.add(Projections.property("eleicao.textoSindicato"), "textoSindicato");
		p.add(Projections.property("eleicao.textoDRT"), "textoDRT");
		p.add(Projections.property("eleicao.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("estab.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("estab.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("estab.endereco.logradouro"), "projectionEstabelecimentoLogradouro");
		p.add(Projections.property("estab.endereco.numero"), "projectionEstabelecimentoNumero");
		p.add(Projections.property("estab.endereco.bairro"), "projectionEstabelecimentoBairro");
		criteria.setProjection(p);

		criteria.add(Expression.eq("eleicao.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Eleicao) criteria.uniqueResult();
	}

	public Collection<CandidatoEleicao> findImprimirResultado(Long eleicaoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new CandidatoEleicao(e.id, e.qtdVotoNulo, e.qtdVotoBranco, co.id, co.nome, co.nomeComercial, co.dataAdmissao, fu.nome, ce.qtdVoto, ce.eleito) ");
		hql.append("from Eleicao e ");
		hql.append("join e.candidatoEleicaos ce ");
		hql.append("join ce.candidato co ");
		hql.append("left join co.historicoColaboradors hc ");
		hql.append("left join hc.funcao fu ");
		hql.append("where e.id = :eleicaoId ");
		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 ");
		hql.append("				where hc2.colaborador.id = co.id ");
		hql.append("				and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("order by co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("eleicaoId", eleicaoId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}
}