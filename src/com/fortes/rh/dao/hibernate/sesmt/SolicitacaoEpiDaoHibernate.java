package com.fortes.rh.dao.hibernate.sesmt;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiVO;
import com.fortes.rh.util.LongUtil;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class SolicitacaoEpiDaoHibernate extends GenericDaoHibernate<SolicitacaoEpi> implements SolicitacaoEpiDao
{
	public SolicitacaoEpiVO findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, String situacaoSolicitacaoEpi, Long tipoEpi, String situacaoColaborador, Long[] estabelecimentoCheck, char ordem)
	{
		Query query = montaConsultaFind(false, empresaId, dataIni, dataFim, colaborador.getNome(), colaborador.getMatricula(), situacaoSolicitacaoEpi, tipoEpi, situacaoColaborador, estabelecimentoCheck, ordem);

		if(pagingSize != 0){
        	query.setFirstResult(((page - 1) * pagingSize));
        	query.setMaxResults(pagingSize);
        }
		
		Collection<Object[]> lista = query.list();
		Collection<SolicitacaoEpi> solicitacoes = new ArrayList<SolicitacaoEpi>();
		SolicitacaoEpi solicitacaoEpi = null;
		SolicitacaoEpiVO solicitacaoEpiVO = new SolicitacaoEpiVO();
		
		for (Object[] solicitacaoEpiAux : lista){
			solicitacaoEpiVO.setQtdSolicitacaoEpis(((BigInteger)(solicitacaoEpiAux[0])).intValue());
			solicitacaoEpi = new SolicitacaoEpi(new Long(solicitacaoEpiAux[1].toString()), (Date) solicitacaoEpiAux[2], solicitacaoEpiAux[3].toString(), 
					new Integer(solicitacaoEpiAux[5].toString()),new Integer(solicitacaoEpiAux[6].toString()), new Integer(solicitacaoEpiAux[7].toString()), 
					solicitacaoEpiAux[8].toString(), new Boolean(solicitacaoEpiAux[9].toString()),new Integer(solicitacaoEpiAux[10].toString()), (String)solicitacaoEpiAux[11]);
			
			solicitacoes.add(solicitacaoEpi);
		}

		solicitacaoEpiVO.setSolicitacaoEpis(solicitacoes);
		
		return solicitacaoEpiVO;
	}

	private Query montaConsultaFind(boolean count, Long empresaId, Date dataIni, Date dataFim, String nomeBusca, String matriculaBusca, String situacaoSolicitacaoEpi, Long tipoEpi, String situacaoColaborador, Long[] estabelecimentoCheck, char ordem)
	{
		StringBuilder sql = new StringBuilder("select sub2.*, c.nome, c.desligado, hc.status, hc.motivo ");
		sql.append("from ( ");
		sql.append("		select count(sub.id) OVER(), sub.* ");
		sql.append("		from ( ");
		sql.append("			select se.id as id, se.data, ca.nome as nomeCargo, se.colaborador_id, ");
		sql.append("  				(select sum(sei2.qtdSolicitado) from solicitacaoepi_item sei2 "); 
		sql.append("   					where sei2.solicitacaoepi_id = se.id "); 
		sql.append("	  			) as qtdSolicitado,  "); 
		sql.append("				coalesce(sum(seie.qtdEntregue), 0) as qtdEntregue, "); 
		sql.append("				coalesce(sum(seid.qtdDevolvida), 0) as qtdDevolvida ");
		sql.append("				from solicitacaoepi as se ");
		sql.append("				inner join solicitacaoepi_item as sei on sei.solicitacaoepi_id=se.id "); 
		sql.append("				left join solicitacaoepiitementrega seie on seie.solicitacaoepiitem_id=sei.id ");
		sql.append("				left join solicitacaoepiitemdevolucao seid on seid.solicitacaoepiitem_id=sei.id ");
		sql.append("				inner join epi ep on ep.id = sei.epi_id ");
		sql.append("				inner join cargo as ca on se.cargo_id=ca.id ");
		sql.append("				where se.empresa_id = :empresaId ");

		if (tipoEpi != null)
			sql.append("				and ep.tipoepi_id = :tipoEpi ");
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoCheck))
			sql.append("				and se.estabelecimento_id in (:estabelecimentoCheck)");
		
		if (dataIni != null && dataFim != null)
			sql.append("				and se.data between :dataIni and :dataFim ");

		sql.append("				group by se.id, se.data, ca.id, ca.nome, se.colaborador_id ");
		sql.append("		) as sub ");

		sql.append("		where 1=1 ");
		if (situacaoSolicitacaoEpi.equals(SituacaoSolicitacaoEpi.ENTREGUE))
			sql.append("		and sub.qtdSolicitado <= sub.qtdEntregue ");
		else if (situacaoSolicitacaoEpi.equals(SituacaoSolicitacaoEpi.ENTREGUE_PARCIALMENTE))
			sql.append("		and sub.qtdEntregue > 0 and sub.qtdEntregue < sub.qtdSolicitado ");
		else if (situacaoSolicitacaoEpi.equals(SituacaoSolicitacaoEpi.ABERTA))
			sql.append("		and sub.qtdEntregue = 0 ");
		else if (situacaoSolicitacaoEpi.equals(SituacaoSolicitacaoEpi.DEVOLVIDO))
			sql.append("		and (sub.qtdDevolvida != 0 and sub.qtdDevolvida = sub.qtdEntregue) ");
		else if (situacaoSolicitacaoEpi.equals(SituacaoSolicitacaoEpi.DEVOLVIDO_PARCIALMENTE))
			sql.append("		and sub.qtdDevolvida > 0 and  sub.qtdDevolvida < sub.qtdEntregue ");
		else if (situacaoSolicitacaoEpi.equals(SituacaoSolicitacaoEpi.SEM_DEVOLUCAO))
			sql.append("		and sub.qtdDevolvida = 0 and sub.qtdEntregue > 0 ");

		sql.append("		order by sub.data DESC ");
		sql.append("	) as sub2 ");
		sql.append("	inner join colaborador as c on c.id = sub2.colaborador_id ");
		sql.append("	inner join historicocolaborador as hc on hc.colaborador_id = c.id "); 
		sql.append("	where hc.data = (select max(hc2.data) from historicocolaborador as hc2 where hc2.colaborador_id = c.id) ");

		if (situacaoColaborador.equals(SituacaoColaborador.ATIVO)) 
			sql.append("	and c.desligado = false "); 
		else if (situacaoColaborador.equals(SituacaoColaborador.DESLIGADO)) 
			sql.append("	and c.desligado = true ");

		if (StringUtils.isNotBlank(matriculaBusca))
			sql.append("	and lower(c.matricula) like :matricula ");
		
		if (StringUtils.isNotBlank(nomeBusca))
			sql.append("	and lower(c.nome) like :nome ");

		if (ordem == 'D') {
			sql.append("order by sub2.data DESC, c.nome ASC ");
		} else {
			sql.append("order by c.nome ASC, sub2.data DESC ");
		}
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		
		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}

		if (StringUtils.isNotBlank(matriculaBusca))
			query.setString("matricula", "%" + matriculaBusca.toLowerCase() + "%");

		if (StringUtils.isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");

		if(LongUtil.arrayIsNotEmpty(estabelecimentoCheck))
			query.setParameterList("estabelecimentoCheck", estabelecimentoCheck, Hibernate.LONG);
		
		if (tipoEpi != null)
			query.setLong("tipoEpi", tipoEpi);
		
		query.setLong("empresaId", empresaId);

		return query;
	}

	public SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId)
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoEpi.class, "se");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("se.id"), "id");
		p.add(Projections.property("se.data"), "data");

		criteria.setProjection(p);

		criteria.add(Expression.eq("se.id", solicitacaoEpiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoEpi.class));

		return (SolicitacaoEpi) criteria.uniqueResult();
	}

	public Collection<SolicitacaoEpi> findVencimentoEpi(Long empresaId, Date data, boolean exibirVencimentoCA, Long[] tipoEPIIds, Long[] areasIds, Long[] estabelecimentoIds, char agruparPor)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct e.id as epiId, co.id as colabId, e.nome as epiNome, e.ativo as epiAtivo, co.nome as colabNome, ca.nome as cargoNome, se.data as solData, eh.validadeUso as epiHitData, ent.dataEntrega as entDataEntrega, ent.qtdEntregue as entQtdEntregue, eh.vencimentoCA as epiHitCA, item.id as solicitacaoEpiItemId ");
		sql.append("from SolicitacaoEpi se ");
		sql.append("join solicitacaoepi_item item on item.SolicitacaoEpi_id = se.id ");
		sql.append("join solicitacaoEpiItemEntrega ent on ent.solicitacaoepiitem_id = item.id ");
		sql.append("join epi e on e.id = item.epi_id ");
		sql.append("join colaborador co on co.id = se.colaborador_id ");
		sql.append("join cargo ca on ca.id = se.cargo_id ");
		sql.append("join historicoColaborador hc on hc.colaborador_id = co.id ");
		sql.append("left join epiHistorico eh on eh.id = ent.epiHistorico_id ");
		sql.append("where ((:data - ent.dataEntrega) >= eh.validadeUso ");

		if(exibirVencimentoCA)
			sql.append(" or :data >= eh.vencimentoCA ");
		
		sql.append(" ) and co.desligado = false and e.empresa_id = :empresaId ");
		
		if(tipoEPIIds != null && tipoEPIIds.length > 0)
			sql.append("   and e.tipoEPI_id in (:tipoEPIIds) ");

		if(areasIds != null && areasIds.length > 0)
			sql.append("   and hc.areaOrganizacional_id in (:areasIds) ");

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			sql.append("   and hc.estabelecimento_id in (:estabelecimentoIds) ");
		
		sql.append("and item.id not in ( ");
		sql.append("	SELECT distinct itensDevolvido.solicitacaoepiitem_id FROM ");
		sql.append("		(select sent.solicitacaoepiitem_id, sum(sent.qtdentregue) AS qtdentregue ");
		sql.append("		from solicitacaoepiitementrega sent ");
		sql.append("		group by sent.solicitacaoepiitem_id) as itensEntregues ");
		sql.append("		inner join ");
		sql.append("		(select sdev.solicitacaoepiitem_id, sum(sdev.qtddevolvida) as qtddevolvida ");
		sql.append("		from solicitacaoepiitemdevolucao sdev  ");
		sql.append("		group by sdev.solicitacaoepiitem_id) as itensDevolvido ");
		sql.append("		on itensDevolvido.solicitacaoepiitem_id =  itensEntregues.solicitacaoepiitem_id ");
		sql.append("	where itensDevolvido.qtddevolvida >= itensEntregues.qtdentregue ");
		sql.append(") ");
		
		if (agruparPor == 'E')
			sql.append("order by e.nome, co.nome, se.data, ent.dataEntrega ");
		else if (agruparPor == 'C')
			sql.append("order by co.nome, e.nome, se.data, ent.dataEntrega ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setDate("data", data);
		query.setLong("empresaId", empresaId);
		
		if(tipoEPIIds != null && tipoEPIIds.length > 0)
			query.setParameterList("tipoEPIIds", tipoEPIIds, Hibernate.LONG);
		
		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		Collection<Object[]> lista = query.list();
		Collection<SolicitacaoEpi> solicitacoes = new ArrayList<SolicitacaoEpi>();
		
		for (Object[] solicitacaoEpiAux : lista){
			solicitacoes.add(new SolicitacaoEpi(new Long(solicitacaoEpiAux[0].toString()), new Long(solicitacaoEpiAux[1].toString()), solicitacaoEpiAux[2].toString(),
					new Boolean(solicitacaoEpiAux[3].toString()), solicitacaoEpiAux[4].toString(), solicitacaoEpiAux[5].toString(),
					(Date) solicitacaoEpiAux[6], new Integer(solicitacaoEpiAux[7].toString()), (Date) solicitacaoEpiAux[8],
					new Integer(solicitacaoEpiAux[9].toString()), (Date) solicitacaoEpiAux[10], new Long(solicitacaoEpiAux[11].toString()))); 
		}
		
		return solicitacoes;
	}

	public Collection<SolicitacaoEpiItemEntrega> findEntregaEpi(Long empresaId, Date dataIni, Date dataFim, Long[] epiIds, Long[] areaIds, Long[] colaboradorCheck, char agruparPor, boolean exibirDesligados)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new SolicitacaoEpiItemEntrega(ent.id, ent.qtdEntregue, ent.dataEntrega, item.qtdSolicitado, e.nome, e.ativo, ca.nome, co.nome, co.desligado, a.nome, eh.vencimentoCA) ");
		hql.append("from SolicitacaoEpiItemEntrega ent ");
		hql.append("left join ent.epiHistorico eh ");
		hql.append("join ent.solicitacaoEpiItem item ");
		hql.append("join item.solicitacaoEpi s ");
		hql.append("join s.colaborador co ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("join hc.areaOrganizacional a ");
		hql.append("join s.cargo ca ");
		hql.append("join item.epi e ");
		hql.append("where e.empresa.id = :empresaId ");
		hql.append("and ent.dataEntrega >= :dataIni ");
		
		hql.append("and hc.data = ("); 
		hql.append("	select max(hc2.data) "); 
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :hoje and hc2.status = :status ");
		hql.append(" ) ");
		
		
		if (dataFim != null)
			hql.append("and ent.dataEntrega <= :dataFim ");
		
		if(epiIds != null && epiIds.length != 0)
			hql.append("and e.id in (:epiCheck) ");
		
		if(colaboradorCheck != null && colaboradorCheck.length != 0)
			hql.append("and co.id in (:colaboradorCheck) ");
		
		if(areaIds != null && areaIds.length > 0)
			hql.append("and a.id in (:areaIds) ");
		
		if (!exibirDesligados)
			hql.append("and co.desligado = false ");

		if (agruparPor == 'E')
			hql.append("order by e.nome,co.nome,s.data ");
		if (agruparPor == 'C')
			hql.append("order by co.nome,e.nome,s.data ");
			
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (dataFim != null)
			query.setDate("dataFim", dataFim);
		
		if(epiIds != null && epiIds.length != 0)
			query.setParameterList("epiCheck", epiIds, Hibernate.LONG);
		
		if (colaboradorCheck != null && colaboradorCheck.length != 0)
			query.setParameterList("colaboradorCheck", colaboradorCheck, Hibernate.LONG);
		
		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		return query.list();
	}
		
	public Collection<SolicitacaoEpiItemDevolucao> findDevolucaoEpi(Long empresaId, Date dataIni, Date dataFim, Long[] epiIds, Long[] areaIds, Long[] colaboradorCheck, char agruparPor, boolean exibirDesligados)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new SolicitacaoEpiItemDevolucao(seid.id as id, seid.dataDevolucao as dataDevolucao, seid.qtdDevolvida as qtdDevolvida, ");
		hql.append(" 										(select coalesce(sum(qtdEntregue), 0) from SolicitacaoEpiItemEntrega where solicitacaoEpiItem.id = sei.id) as totalEntregue, " );
		hql.append(" 										seid.observacao, ep.nome, ep.ativo, ca.nome, co.nome, co.desligado, cast(monta_familia_area(a.id), text) as areaNome ) ");
		
		hql.append("from SolicitacaoEpiItemDevolucao seid ");
		hql.append("join seid.solicitacaoEpiItem sei ");
		hql.append("join sei.solicitacaoEpi se ");
		hql.append("join se.colaborador co ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("join hc.areaOrganizacional a ");
		hql.append("join se.cargo ca ");
		hql.append("join sei.epi ep ");

		hql.append("where ep.empresa.id = :empresaId ");
		hql.append("and seid.dataDevolucao >= :dataIni ");
		hql.append("and hc.data = ("); 
		hql.append("	select max(hc2.data) "); 
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :hoje and hc2.status = :status ");
		hql.append(" ) ");
		
		if (dataFim != null)
			hql.append("and seid.dataDevolucao <= :dataFim ");
		
		if(epiIds != null && epiIds.length != 0)
			hql.append("and ep.id in (:epiCheck) ");
		
		if(colaboradorCheck != null && colaboradorCheck.length != 0)
			hql.append("and co.id in (:colaboradorCheck) ");
		
		if(areaIds != null && areaIds.length > 0)
			hql.append("and a.id in (:areaIds) ");
		
		if (!exibirDesligados)
			hql.append("and co.desligado = false ");

		if (agruparPor == 'E')
			hql.append("order by ep.nome,co.nome,se.data ");
		if (agruparPor == 'C')
			hql.append("order by co.nome,ep.nome,se.data ");
			
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (dataFim != null)
			query.setDate("dataFim", dataFim);
		
		if(epiIds != null && epiIds.length != 0)
			query.setParameterList("epiCheck", epiIds, Hibernate.LONG);
		
		if (colaboradorCheck != null && colaboradorCheck.length != 0)
			query.setParameterList("colaboradorCheck", colaboradorCheck, Hibernate.LONG);
		
		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		return query.list();
	}
	public Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, String situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador, Long[] estabelecimentoCheck, char ordem)
	{
		getSession().flush(); //Necessário para que nos testes a view enxergue os dados inseridos via hibernate 

		StringBuilder sql = new StringBuilder();
		sql.append("select sse.solicitacaoepiid, sse.empresaid, sse.estabelecimentoid, sse.estabelecimentonome, sse.colaboradorid, sse.colaboradormatricula, sse.colaboradornome, sse.colaboradordesligado, ");
		sql.append("       e.nome as epinome,  sse.solicitacaoepidata, sse.cargonome, sse.qtdsolicitado as qtdsolicitadototal, item.id as itemId, item.qtdsolicitado as qtdsolicitadoitem, mse.descricao, ");
		sql.append("       sse.qtdentregue, (select coalesce(sum(qtdentregue), 0) from solicitacaoepiitementrega where solicitacaoepiitem_id = item.id) as qtdentrgueitem, sse.solicitacaoepisituacaoentregue, ");
		sql.append("	   sse.qtddevolvida, (select coalesce(sum(qtddevolvida), 0) from solicitacaoepiitemdevolucao where solicitacaoepiitem_id = item.id) as qtddevolvidaitem, sse.solicitacaoepisituacaodevolvido, ");
		sql.append("	   te.descricao as descricaoTamanhoEpi ");
		sql.append("from situacaosolicitacaoepi sse ");
		sql.append("join solicitacaoepi_item item on item.solicitacaoepi_id = sse.solicitacaoepiid ");
		sql.append("left join motivosolicitacaoepi mse on mse.id = item.motivosolicitacaoepi_id ");
		sql.append("join epi e on item.epi_id = e.id ");
		sql.append("join colaborador c on sse.colaboradorid = c.id ");
		sql.append("left join tamanhoepi te on item.tamanhoepi_id = te.id ");
		
		sql.append("where sse.empresaid = :empresaId ");
		
		if (situacao.equals(SituacaoSolicitacaoEpi.ENTREGUE) || situacao.equals(SituacaoSolicitacaoEpi.ENTREGUE_PARCIALMENTE) ||  situacao.equals(SituacaoSolicitacaoEpi.ABERTA))
			sql.append("and sse.solicitacaoepisituacaoEntregue = :situacao ");
		else if (situacao.equals(SituacaoSolicitacaoEpi.DEVOLVIDO) || situacao.equals(SituacaoSolicitacaoEpi.DEVOLVIDO_PARCIALMENTE) ||  situacao.equals(SituacaoSolicitacaoEpi.SEM_DEVOLUCAO))
			sql.append("and sse.solicitacaoepisituacaoDevolvido = :situacao ");

		if (situacaoColaborador.equals(SituacaoColaborador.ATIVO)) {
			sql.append("and c.desligado = false "); 
		} else if (situacaoColaborador.equals(SituacaoColaborador.DESLIGADO)) {
			sql.append("and c.desligado = true "); 
		}

		if (dataIni != null && dataFim != null)
			sql.append("and sse.solicitacaoepidata between :dataIni and :dataFim ");
		
		if (tipoEpi != null)
			sql.append("and e.tipoepi_id = :tipoEpi ");
		
		if (colaborador != null)
		{
			if (StringUtils.isNotBlank(colaborador.getMatricula()))
				sql.append("and sse.colaboradorMatricula ilike :colaboradorMatricula ");

			if (StringUtils.isNotBlank(colaborador.getNome()))
				sql.append("and sse.colaboradorNome ilike :colaboradorNome ");
		}
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoCheck))
			sql.append("and sse.estabelecimentoid in (:estabelecimentoCheck) ");
		
		if (ordem == 'D') {
			sql.append("order by sse.solicitacaoepidata desc, sse.colaboradornome ");
		} else {
			sql.append("order by sse.colaboradornome, sse.solicitacaoepidata desc ");
		}
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		
		query.setLong("empresaId", empresaId);
		
		if (!situacao.equals(SituacaoSolicitacaoEpi.TODAS))
			query.setString("situacao", situacao);
		
		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}
		
		if (tipoEpi != null)
			query.setLong("tipoEpi", tipoEpi);
		
		if (colaborador != null)
		{
			if (StringUtils.isNotBlank(colaborador.getMatricula()))
				query.setString("colaboradorMatricula", "%" + colaborador.getMatricula() + "%");

			if (StringUtils.isNotBlank(colaborador.getNome()))
				query.setString("colaboradorNome", "%" + colaborador.getNome() + "%");
		}
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoCheck))
			query.setParameterList("estabelecimentoCheck", estabelecimentoCheck);
			
		Collection<Object[]> resultado = query.list();
		
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
		Collection<SolicitacaoEpiItemVO> lista = new ArrayList<SolicitacaoEpiItemVO>();
		Object[] obj;
		SolicitacaoEpiItemVO vo;
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			int countCampo = 0;
			
			obj = it.next();
			
			vo = new SolicitacaoEpiItemVO();
			vo.setSolicitacaoEpiId(((BigInteger) obj[countCampo]).longValue());
			vo.setEmpresaId(((BigInteger) obj[++countCampo]).longValue());
			vo.setEstabelecimentoId(((BigInteger) obj[++countCampo]).longValue());
			vo.setEstabelecimentoNome(((String) obj[++countCampo]));
			vo.setColaboradorId(((BigInteger) obj[++countCampo]).longValue());
			vo.setColaboradorMatricula(((String) obj[++countCampo]));
			vo.setColaboradorNome(((String) obj[++countCampo]));
			vo.setColaboradorDesligado(new Boolean(obj[++countCampo].toString()));
			vo.setEpiNome(((String) obj[++countCampo]));
			try {
				vo.setSolicitacaoEpiData(sDF.parse(obj[++countCampo].toString()));
			} catch (ParseException e) {e.printStackTrace();}
			vo.setCargoNome(((String) obj[++countCampo]));
			vo.setQtdSolicitadoTotal(new Integer(obj[++countCampo].toString()));
			vo.setItemId(((BigInteger) obj[++countCampo]).longValue());
			vo.setQtdSolicitadoItem(new Integer(obj[++countCampo].toString()));
			vo.setDescricaoMotivoSolicitacaoEpi((String) obj[++countCampo]);
			vo.setQtdEntregue(new Integer(obj[++countCampo].toString()));
			vo.setQtdEntregueItem((new Integer(obj[++countCampo].toString())));
			vo.setSolicitacaoEpiSituacao(obj[++countCampo].toString());
			vo.setQtdDevolvida(new Integer(obj[++countCampo].toString()));
			vo.setQtdDevolvidaItem((new Integer(obj[++countCampo].toString())));
			Object solicitacaoEpiSituacaoDevolucao = obj[++countCampo];
			vo.setSolicitacaoEpiSituacaoDevolucao(solicitacaoEpiSituacaoDevolucao!= null ? solicitacaoEpiSituacaoDevolucao.toString(): null);
			vo.setDescricaoTamanhoEpi(((String) obj[++countCampo]));
			
			lista.add(vo);
		}
		
		return lista;
	}

	public Collection<SolicitacaoEpi> findByColaboradorId(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoEpi.class, "se");
		criteria.createCriteria("se.colaborador", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("se.id"), "id");
		p.add(Projections.property("se.data"), "data");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.addOrder(Order.desc("se.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoEpi.class));

		return criteria.list();
	}
}