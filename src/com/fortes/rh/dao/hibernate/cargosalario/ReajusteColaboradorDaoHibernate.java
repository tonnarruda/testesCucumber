package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;

@Component
@SuppressWarnings("unchecked")
public class ReajusteColaboradorDaoHibernate extends GenericDaoHibernate<ReajusteColaborador> implements ReajusteColaboradorDao
{
	public Collection<ReajusteColaborador> findByIdEstabelecimentoAreaGrupo(Long tabelaReajusteColaboradorId, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, int filtraPor)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new ReajusteColaborador(rc.id, aa.id, fsa.nome, ca.nome, fsp.id, fsp.nome, fsp.codigoAC, ");
		hql.append("cp.nome, rc.tipoSalarioAtual, rc.tipoSalarioProposto, rc.salarioAtual, rc.salarioProposto, rc.observacao,  ");
		hql.append("c.id, c.nome, c.nomeComercial,c.codigoAC, c.naoIntegraAc, c.desligado, c.dataDesligamento, aa.id, aa.nome, ap.id, ap.nome, ap.codigoAC, trc.id, trc.data, ");
		hql.append("fsha.valor, fsha.tipo, fsha.quantidade, ");
		hql.append("fshp.valor, fshp.tipo, fshp.quantidade, ");
		hql.append("iha.valor, rc.quantidadeIndiceAtual, ");
		hql.append("ihp.valor, rc.quantidadeIndiceProposto, ");
		hql.append("ihfsha.valor, ihfshp.valor, ihfshp.id, ");
		hql.append("go.id, go.nome, ep.id, ep.nome, ep.codigoAC, ea.nome, fshp.id, ip.id, ip.nome, ip.codigoAC, ifshp.id, amp.id, fp.id, ia.nome) ");

		hql.append("from ReajusteColaborador as rc ");

		hql.append("left join rc.estabelecimentoProposto as ep ");
		hql.append("left join rc.estabelecimentoAtual as ea ");
		hql.append("left join rc.tabelaReajusteColaborador as trc ");
		hql.append("left join rc.colaborador as c ");
		hql.append("left join rc.areaOrganizacionalProposta as ap ");
		hql.append("left join rc.areaOrganizacionalAtual as aa ");

		hql.append("left join rc.ambienteProposto as amp ");
		hql.append("left join rc.funcaoProposta as fp ");

		hql.append("left join rc.indiceAtual as ia ");
		hql.append("left join ia.indiceHistoricos as iha with iha.data = (select max(iha2.data) from IndiceHistorico iha2 where iha2.indice.id = ia.id and iha2.data <= :hoje) ");

		hql.append("left join rc.indiceProposto as ip ");
		hql.append("left join ip.indiceHistoricos as ihp with ihp.data = (select max(ihp2.data) from IndiceHistorico ihp2 where ihp2.indice.id = ip.id and ihp2.data <= :hoje) ");

		hql.append("left join rc.faixaSalarialAtual as fsa ");
		hql.append("left join fsa.cargo as ca ");
		hql.append("left join fsa.faixaSalarialHistoricos as fsha with fsha.data = (select max(fsha2.data) from FaixaSalarialHistorico fsha2 where fsha2.faixaSalarial.id = fsa.id and fsha2.data <= :hoje and fsha2.status != :status) ");
		hql.append("left join fsha.indice as ifsha ");
		hql.append("left join ifsha.indiceHistoricos as ihfsha with ihfsha.data = (select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifsha.id and ih3.data <= :hoje) ");

		hql.append("left join rc.faixaSalarialProposta as fsp ");
		hql.append("left join fsp.cargo as cp ");
		hql.append("left join cp.grupoOcupacional as go ");
		hql.append("left join fsp.faixaSalarialHistoricos as fshp with fshp.data = (select max(fshp2.data) from FaixaSalarialHistorico fshp2 where fshp2.faixaSalarial.id = fsp.id and fshp2.data <= :hoje and fshp2.status != :status) ");
		hql.append("left join fshp.indice as ifshp ");
		hql.append("left join ifshp.indiceHistoricos as ihfshp with ihfshp.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = ifshp.id and ih4.data <= :hoje) ");

		hql.append("where trc.id = :tabelaReajusteColaboradorId ");

		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			hql.append("  and ep.id in(:estabelecimentoIds) ");

		if(filtraPor == 1)
		{
			if(areaIds != null && !areaIds.isEmpty())
				hql.append("  and ap.id in(:areaIds) ");
		}
		else if(filtraPor == 2)
		{
			if(grupoIds != null && !grupoIds.isEmpty())
				hql.append("  and go.id in(:grupoIds) ");
		}

		hql.append("order by c.nome");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaboradorId);
		query.setLong("status", StatusRetornoAC.CANCELADO);

		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, StandardBasicTypes.LONG);
		if(filtraPor == 1)
		{
			if(areaIds != null && !areaIds.isEmpty())
				query.setParameterList("areaIds", areaIds, StandardBasicTypes.LONG);
		}
		else if(filtraPor == 2)
		{
			if(grupoIds != null && !grupoIds.isEmpty())
				query.setParameterList("grupoIds", grupoIds, StandardBasicTypes.LONG);
		}

		return query.list();
	}

	public ReajusteColaborador findByIdProjection(Long reajusteColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ReajusteColaborador.class, "rc");
		criteria.createCriteria("rc.areaOrganizacionalProposta", "areaProposta", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.areaOrganizacionalAtual", "areaAtual", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.estabelecimentoAtual", "esa", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.estabelecimentoProposto", "esp", Criteria.LEFT_JOIN);

		criteria.createCriteria("rc.faixaSalarialAtual", "fsa", Criteria.LEFT_JOIN);
		criteria.createCriteria("fsa.cargo", "cargoa", Criteria.LEFT_JOIN);

		criteria.createCriteria("rc.faixaSalarialProposta", "fsp", Criteria.LEFT_JOIN);
		criteria.createCriteria("fsp.cargo", "cargop", Criteria.LEFT_JOIN);
		criteria.createCriteria("cargop.grupoOcupacional", "go", Criteria.LEFT_JOIN);

		criteria.createCriteria("rc.funcaoAtual", "funcaoAtual", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.ambienteAtual", "ambienteAtual", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.ambienteProposto", "ambienteProposto", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.tabelaReajusteColaborador", "tabelaReajuste", Criteria.LEFT_JOIN);

		criteria.createCriteria("rc.indiceProposto", "inp", Criteria.LEFT_JOIN);
		criteria.createCriteria("rc.indiceAtual", "ina", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rc.id"), "id");
		p.add(Projections.property("rc.observacao"), "observacao");
		p.add(Projections.property("rc.salarioAtual"), "salarioAtual");
		p.add(Projections.property("rc.salarioProposto"), "salarioProposto");
		p.add(Projections.property("rc.tipoSalarioAtual"), "tipoSalarioAtual");
		p.add(Projections.property("rc.tipoSalarioProposto"), "tipoSalarioProposto");
		p.add(Projections.property("rc.quantidadeIndiceProposto"), "quantidadeIndiceProposto");
		p.add(Projections.property("rc.funcaoProposta"), "funcaoProposta");
		p.add(Projections.property("rc.indiceAtual"), "indiceAtual");
		p.add(Projections.property("rc.quantidadeIndiceAtual"), "quantidadeIndiceAtual");
		p.add(Projections.property("inp.id"), "projectionIndicePropostoId");
		p.add(Projections.property("inp.nome"), "projectionIndicePropostoNome");
//		p.add(Projections.property("ina.id"), "projectionIndiceAtualId");
//		p.add(Projections.property("ina.nome"), "projectionIndiceAtualNome");
		p.add(Projections.property("areaProposta.id"), "areaOrganizacionalPropostaId");
		p.add(Projections.property("areaAtual.id"), "projectionAreaAtualId");
		p.add(Projections.property("c.id"), "idColaborador");
		p.add(Projections.property("c.nome"), "nomeColaborador");
		p.add(Projections.property("c.nomeComercial"), "nomeComercialProjection");

		p.add(Projections.property("fsa.id"), "projectionFaixaSalarialAtualId");
		p.add(Projections.property("fsa.nome"), "projectionFaixaSalarialAtualNome");
		p.add(Projections.property("fsp.id"), "projectionFaixaSalarialPropostaId");
		p.add(Projections.property("fsp.nome"), "projectionFaixaSalarialPropostaNome");
		p.add(Projections.property("cargoa.nome"), "projectionCargoAtualNome");
		p.add(Projections.property("cargop.nome"), "projectionCargoPropostoNome");
		p.add(Projections.property("go.id"), "projectionGrupoOcupacionalPropostoId");
		p.add(Projections.property("go.nome"), "projectionGrupoOcupacionalPropostoNome");
		p.add(Projections.property("esa.id"), "projectionEstabelecimentoAtualId");
		p.add(Projections.property("esa.nome"), "projectionEstabelecimentoAtualNome");
		p.add(Projections.property("esp.id"), "projectionEstabelecimentoPropostoId");
		p.add(Projections.property("esp.nome"), "projectionEstabelecimentoPropostoNome");

		p.add(Projections.property("funcaoAtual.id"), "projectionFuncaoAtualId");
		p.add(Projections.property("funcaoAtual.nome"), "projectionFuncaoAtualNome");
		p.add(Projections.property("ambienteAtual.id"), "projectionAmbienteAtualId");
		p.add(Projections.property("ambienteAtual.nome"), "projectionAmbienteAtualNome");
		p.add(Projections.property("ambienteProposto.id"), "projectionAmbientePropostoId");
		p.add(Projections.property("tabelaReajuste.id"), "tabelaReajusteColaboradorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("rc.id", reajusteColaboradorId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ReajusteColaborador.class));

		return (ReajusteColaborador) criteria.uniqueResult();
	}

	public void deleteByColaboradoresTabelaReajuste(Long[] colaboradorIds, Long tabelaReajusteColaboradorId)
	{
		String whereColaborador = "";
		if(colaboradorIds != null && colaboradorIds.length > 0)
			whereColaborador = "r.colaborador.id in ( :colaboradorIds) and";

		String queryHQL = "delete ReajusteColaborador r where " + whereColaborador + " r.tabelaReajusteColaborador.id = :tabelaReajusteColaboradorId";

		Query query = getSession().createQuery(queryHQL);
		if(colaboradorIds != null && colaboradorIds.length > 0)
			query.setParameterList("colaboradorIds", colaboradorIds, StandardBasicTypes.LONG);

		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaboradorId);

		query.executeUpdate();
	}

	public ReajusteColaborador getSituacaoReajusteColaborador(Long reajusteColaboradorId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new ReajusteColaborador(rc.id, rc.observacao, rc.salarioAtual, rc.salarioProposto, rc.tipoSalarioAtual, rc.tipoSalarioProposto, rc.quantidadeIndiceProposto, rc.quantidadeIndiceAtual, ");
		hql.append("trc.id,	co.id, co.nome, co.nomeComercial, ip.id, ip.nome, ihp.valor, ia.id, ia.nome, iha.valor, aop.id, aop.nome, aoa.id, aoa.nome, ap.id, ap.nome, aa.id, aa.nome, fp.id, fp.nome, fa.id, fa.nome, fsp.id, fsp.nome, ");
		hql.append("fsa.id, fsa.nome, cp.id, cp.nomeMercado, cp.nome, ca.id, ca.nomeMercado, ca.nome, gop.id, gop.nome, goa.id, goa.nome, esp.id, esp.nome, esa.id, esa.nome, fshp.valor, fsha.valor, fshp.tipo, fsha.tipo,  fshp.quantidade, fsha.quantidade, ifshp.valor, ifsha.valor) ");

		hql.append("from ReajusteColaborador as rc ");
		hql.append("left join rc.areaOrganizacionalProposta as aop ");
		hql.append("left join rc.areaOrganizacionalAtual as aoa ");
		hql.append("left join rc.ambienteProposto as ap ");
		hql.append("left join rc.ambienteAtual as aa ");
		hql.append("left join rc.funcaoProposta as fp ");
		hql.append("left join rc.funcaoAtual as fa ");
		hql.append("left join rc.estabelecimentoProposto as esp ");
		hql.append("left join rc.estabelecimentoAtual as esa ");
		hql.append("left join rc.indiceProposto as ip ");
		hql.append("left join ip.indiceHistoricos as ihp with ihp.data = (select max(ihp2.data) from IndiceHistorico ihp2 where ihp2.indice.id = ip.id and ihp2.data <= :hoje) ");
		hql.append("left join rc.indiceAtual as ia ");
		hql.append("left join ia.indiceHistoricos as iha with iha.data = (select max(iha2.data) from IndiceHistorico iha2 where iha2.indice.id = ia.id and iha2.data <= :hoje) ");
		hql.append("left join rc.colaborador as co ");
		hql.append("left join co.empresa as e ");
		hql.append("left join rc.faixaSalarialProposta as fsp ");
		hql.append("left join fsp.faixaSalarialHistoricos as fshp with fshp.data = (select max(fshp2.data) from FaixaSalarialHistorico fshp2 where fshp2.faixaSalarial.id = fsp.id and fshp2.data <= :hoje and fshp2.status != :status) ");
		hql.append("left join fshp.indice as ifsp ");
		hql.append("left join ifsp.indiceHistoricos as ifshp with ifshp.data = (select max(ihp3.data) from IndiceHistorico ihp3 where ihp3.indice.id = ifsp.id and ihp3.data <= :hoje) ");
		hql.append("left join fsp.cargo as cp ");
		hql.append("left join cp.grupoOcupacional as gop ");
		hql.append("left join rc.faixaSalarialAtual as fsa ");
		hql.append("left join fsa.faixaSalarialHistoricos as fsha with fsha.data = (select max(fsha2.data) from FaixaSalarialHistorico fsha2 where fsha2.faixaSalarial.id = fsa.id and fsha2.data <= :hoje and fsha2.status != :status) ");
		hql.append("left join fsha.indice as ifsa ");
		hql.append("left join ifsa.indiceHistoricos as ifsha with ifsha.data = (select max(iha3.data) from IndiceHistorico iha3 where iha3.indice.id = ifsa.id and iha3.data <= :hoje) ");
		hql.append("left join fsa.cargo as ca ");
		hql.append("left join ca.grupoOcupacional as goa ");
		hql.append("left join rc.tabelaReajusteColaborador as trc ");

		hql.append("where rc.id = :reajusteColaboradorId ");
		hql.append("order by rc.id desc");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("reajusteColaboradorId", reajusteColaboradorId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setMaxResults(1);

		return (ReajusteColaborador) query.uniqueResult();
	}

	public void updateFromHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		String queryHQL = "update ReajusteColaborador set faixaSalarialProposta.id = :faixaSalarialPropostaId , salarioProposto = :salarioProposto , areaOrganizacionalProposta.id = :areaOrganizacionalPropostaId " +
				", funcaoProposta.id = :funcaoPropostaId, ambienteProposto.id = :ambientePropostoId, estabelecimentoProposto.id = :estabelecimentoPropostoId " +
				", indiceProposto.id = :indicePropostoId, quantidadeIndiceProposto = :quantidadeIndiceProposto, tipoSalarioProposto = :tipoSalarioProposto " +
				" where id = :reajusteColaboradorId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("faixaSalarialPropostaId", historicoColaborador.getFaixaSalarial().getId());
		query.setLong("areaOrganizacionalPropostaId", historicoColaborador.getAreaOrganizacional().getId());
		query.setInteger("tipoSalarioProposto", historicoColaborador.getTipoSalario());
		query.setLong("reajusteColaboradorId", historicoColaborador.getReajusteColaborador().getId());
		query.setLong("estabelecimentoPropostoId", historicoColaborador.getEstabelecimento().getId());

		switch (historicoColaborador.getTipoSalario())
		{
			case TipoAplicacaoIndice.CARGO:
				query.setParameter("salarioProposto", null, StandardBasicTypes.DOUBLE);
				query.setParameter("indicePropostoId", null, StandardBasicTypes.LONG);
				query.setDouble("quantidadeIndiceProposto", 0.0);
				break;
			case TipoAplicacaoIndice.INDICE:
				query.setParameter("salarioProposto", null, StandardBasicTypes.DOUBLE);
				query.setLong("indicePropostoId", historicoColaborador.getIndice().getId());
				query.setDouble("quantidadeIndiceProposto", historicoColaborador.getQuantidadeIndice());
				break;
			case TipoAplicacaoIndice.VALOR:
				query.setDouble("salarioProposto", historicoColaborador.getSalario());
				query.setParameter("indicePropostoId", null, StandardBasicTypes.LONG);
				query.setDouble("quantidadeIndiceProposto", 0.0);
				break;
		}

		if (historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getId() != null)
			query.setLong("funcaoPropostaId", historicoColaborador.getFuncao().getId());
		else
			query.setParameter("funcaoPropostaId", null, StandardBasicTypes.LONG);

		if (historicoColaborador.getAmbiente() != null && historicoColaborador.getAmbiente().getId() != null)
			query.setLong("ambientePropostoId", historicoColaborador.getAmbiente().getId());
		else
			query.setParameter("ambientePropostoId", null, StandardBasicTypes.LONG);

		query.executeUpdate();
	}
}