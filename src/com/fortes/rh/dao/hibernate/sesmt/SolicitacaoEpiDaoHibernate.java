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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class SolicitacaoEpiDaoHibernate extends GenericDaoHibernate<SolicitacaoEpi> implements SolicitacaoEpiDao
{
	public Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacaoSolicitacaoEpi, Long tipoEpi, String situacaoColaborador)
	{
		Query query = montaConsultaFind(false, empresaId, dataIni, dataFim, colaborador.getNome(), colaborador.getMatricula(), situacaoSolicitacaoEpi, tipoEpi, situacaoColaborador);

		if(pagingSize != 0)
        {
        	query.setFirstResult(((page - 1) * pagingSize));
        	query.setMaxResults(pagingSize);
        }
		
		Collection<Object[]> lista = query.list();
		Collection<SolicitacaoEpi> solicitacoes = new ArrayList<SolicitacaoEpi>();
		SolicitacaoEpi solicitacaoEpi = null;
		
		for (Object[] o : lista) 
		{
			SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
			
			solicitacaoEpi = new SolicitacaoEpi();
			solicitacaoEpi.setId(new Long(o[0].toString()));
			solicitacaoEpi.setColaboradorNome(o[3].toString());
			solicitacaoEpi.setColaboradorStatus(new Integer(o[4].toString()));
			try {
				solicitacaoEpi.setData(sDF.parse(o[5].toString()));
			} catch (ParseException e) {e.printStackTrace();}
			solicitacaoEpi.setCargoNome(o[6].toString());
			solicitacaoEpi.setQtdEpiSolicitado(new Integer(o[7].toString()));
			solicitacaoEpi.setQtdEpiEntregue(new Integer(o[8].toString()));
			
			solicitacoes.add(solicitacaoEpi);
		}
		
		return solicitacoes;
	}

	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacaoSolicitacaoEpi, Long tipoEpi, String situacaoColaborador)
	{
		Query query = montaConsultaFind(true, empresaId, dataIni, dataFim, colaborador.getNome(), colaborador.getMatricula(), situacaoSolicitacaoEpi, tipoEpi, situacaoColaborador);
		return new Integer(query.uniqueResult().toString());
	}

	private Query montaConsultaFind(boolean count, Long empresaId, Date dataIni, Date dataFim, String nomeBusca, String matriculaBusca, char situacaoSolicitacaoEpi, Long tipoEpi, String situacaoColaborador)
	{
		StringBuilder sql = null;
		if (count)
			sql = new StringBuilder("select count(sub.id) ");
		else
			sql = new StringBuilder("select sub.* ");

		sql.append("from ( ");
		sql.append("select se.id as id, se.empresa_id, c.matricula, c.nome, hc.status, se.data, ca.nome as nomeCargo, (select sum(sei2.qtdSolicitado) from solicitacaoepi_item sei2 where sei2.solicitacaoepi_id = se.id) as qtdSolicitado, coalesce(sum(seie.qtdEntregue), 0) as qtdEntregue "); 
		sql.append("from solicitacaoepi as se ");
		sql.append("left join solicitacaoepi_item as sei on sei.solicitacaoepi_id=se.id "); 
		sql.append("left join solicitacaoepiitementrega seie on seie.solicitacaoepiitem_id=sei.id "); 
		sql.append("left join epihistorico ehist on ehist.id=seie.epihistorico_id "); 
		sql.append("left join epi e on e.id=ehist.epi_id "); 
		sql.append("left join colaborador as c on se.colaborador_id=c.id ");
		sql.append("left join historicocolaborador as hc on c.id=hc.colaborador_id "); 
		sql.append("left join cargo as ca on se.cargo_id=ca.id ");
		sql.append("where hc.data = (select max(hc2.data) from historicocolaborador as hc2 where hc2.colaborador_id = c.id) ");
		
		if (situacaoColaborador.equals(SituacaoColaborador.ATIVO)) {
			sql.append("and c.desligado = false "); 
		} else if (situacaoColaborador.equals(SituacaoColaborador.DESLIGADO)) {
			sql.append("and c.desligado = true "); 
		}
		
		if (tipoEpi != null)
			sql.append("and e.tipoepi_id = :tipoEpi ");

		sql.append("group by se.id, c.matricula, c.id, c.nome, hc.status, se.data, ca.id, ca.nome, se.empresa_id ");
		sql.append(") as sub ");
 
		sql.append("where sub.empresa_id = :empresaId ");

		if (situacaoSolicitacaoEpi == SituacaoSolicitacaoEpi.ENTREGUE)
			sql.append("and sub.qtdSolicitado <= sub.qtdEntregue ");
		else if (situacaoSolicitacaoEpi == SituacaoSolicitacaoEpi.ENTREGUE_PARCIALMENTE)
			sql.append("and sub.qtdEntregue > 0 and sub.qtdEntregue < sub.qtdSolicitado ");
		else if (situacaoSolicitacaoEpi == SituacaoSolicitacaoEpi.ABERTA)
			sql.append("and sub.qtdEntregue = 0 ");
		
		if (StringUtils.isNotBlank(matriculaBusca))
			sql.append("and lower(sub.matricula) like :matricula ");
		
		if (StringUtils.isNotBlank(nomeBusca))
			sql.append("and lower(sub.nome) like :nome ");

		
		if (dataIni != null && dataFim != null)
			sql.append("and sub.data between :dataIni and :dataFim ");

		if (!count)
			sql.append("order by sub.data DESC, sub.nome ASC ");
		
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
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct new SolicitacaoEpi(e.id, co.id, e.nome, co.nome, ca.nome, se.data, eh.validadeUso, ent.dataEntrega, ent.qtdEntregue, eh.vencimentoCA) ");
		hql.append("from SolicitacaoEpi se ");
		hql.append("join se.solicitacaoEpiItems item ");
		hql.append("join item.solicitacaoEpiItemEntregas ent ");
		hql.append("join item.epi e ");
		hql.append("join se.colaborador co ");
		hql.append("join se.cargo ca ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("left join ent.epiHistorico eh ");
		hql.append("where ((:data - ent.dataEntrega) >= eh.validadeUso ");

		if(exibirVencimentoCA)
			hql.append(" or :data >= eh.vencimentoCA ");
		
		hql.append(" ) and co.desligado = false and e.empresa.id = :empresaId ");
		
		if(tipoEPIIds != null && tipoEPIIds.length > 0)
			hql.append("   and e.tipoEPI.id in (:tipoEPIIds) ");

		if(areasIds != null && areasIds.length > 0)
			hql.append("   and hc.areaOrganizacional.id in (:areasIds) ");

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("   and hc.estabelecimento.id in (:estabelecimentoIds) ");
		
		if (agruparPor == 'E')
			hql.append("order by e.nome, co.nome, se.data ");
		else if (agruparPor == 'C')
			hql.append("order by co.nome, e.nome, se.data ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("empresaId", empresaId);
		
		if(tipoEPIIds != null && tipoEPIIds.length > 0)
			query.setParameterList("tipoEPIIds", tipoEPIIds, Hibernate.LONG);
		
		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<SolicitacaoEpiItemEntrega> findEntregaEpi(Long empresaId, Date dataIni, Date dataFim, Long[] epiIds, Long[] colaboradorCheck, char agruparPor)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new SolicitacaoEpiItemEntrega(ent.id, ent.qtdEntregue, ent.dataEntrega, item.qtdSolicitado, e.nome, ca.nome, co.nome, eh.vencimentoCA) ");
		hql.append("from SolicitacaoEpiItemEntrega ent ");
		hql.append("left join ent.epiHistorico eh ");
		hql.append("join ent.solicitacaoEpiItem item ");
		hql.append("join item.solicitacaoEpi s ");
		hql.append("join s.colaborador co ");
		hql.append("join s.cargo ca ");
		hql.append("join item.epi e ");
		hql.append("where co.desligado = false ");
		
		if (dataIni != null && dataFim != null)
			hql.append("and ent.dataEntrega between :dataIni and :dataFim ");
		
		if(epiIds != null && epiIds.length != 0)
			hql.append("and e.id in (:epiCheck) ");
		
		if(colaboradorCheck != null && colaboradorCheck.length != 0)
			hql.append("and co.id in (:colaboradorCheck) ");
		
		hql.append("and e.empresa.id = :empresaId ");

		if (agruparPor == 'E')
			hql.append("order by e.nome,co.nome,s.data ");
		if (agruparPor == 'C')
			hql.append("order by co.nome,e.nome,s.data ");
			
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		
		if(epiIds != null && epiIds.length != 0)
			query.setParameterList("epiCheck", epiIds, Hibernate.LONG);
		
		if (colaboradorCheck != null && colaboradorCheck.length != 0)
			query.setParameterList("colaboradorCheck", colaboradorCheck, Hibernate.LONG);

		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}
		
		return query.list();
	}
	
	public Collection<com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, char situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador)
	{
		getSession().flush(); //Necessário para que nos testes a view enxergue os dados inseridos via hibernate 

		StringBuilder sql = new StringBuilder();
		sql.append("select sse.solicitacaoepiid, sse.empresaid, sse.estabelecimentoid, sse.estabelecimentonome, sse.colaboradorid, sse.colaboradormatricula, sse.colaboradornome, e.nome as epinome,  sse.solicitacaoepidata, sse.cargonome, sse.qtdsolicitado as qtdsolicitadototal, item.id as itemId, item.qtdsolicitado as qtdsolicitadoitem, sse.qtdentregue, sse.solicitacaoepisituacao ");
		sql.append("from situacaosolicitacaoepi sse ");
		sql.append("join solicitacaoepi_item item on item.solicitacaoepi_id = sse.solicitacaoepiid ");
		sql.append("join epi e on item.epi_id = e.id ");
		sql.append("join colaborador c on sse.colaboradorid = c.id ");
		sql.append("where sse.empresaid = :empresaId ");
		
		if (situacao != SituacaoSolicitacaoEpi.TODAS)
			sql.append("and sse.solicitacaoepisituacao = :situacao ");

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
			if (colaborador.getMatricula() != null)
				sql.append("and lower(sse.colaboradorMatricula) like :colaboradorMatricula ");

			if (colaborador.getNome() != null)
				sql.append("and lower(sse.colaboradorNome) like :colaboradorNome ");
		}
		
		sql.append("order by sse.solicitacaoepidata desc, sse.colaboradornome ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		
		query.setLong("empresaId", empresaId);
		
		if (situacao != SituacaoSolicitacaoEpi.TODAS)
			query.setCharacter("situacao", situacao);
		
		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}
		
		if (tipoEpi != null)
			query.setLong("tipoEpi", tipoEpi);
		
		if (colaborador != null)
		{
			if (colaborador.getMatricula() != null)
				query.setString("colaboradorMatricula", "%" + colaborador.getMatricula() + "%");

			if (colaborador.getNome() != null)
				query.setString("colaboradorNome", "%" + colaborador.getNome() + "%");
		}
		
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
			vo.setEpiNome(((String) obj[++countCampo]));
			try {
				vo.setSolicitacaoEpiData(sDF.parse(obj[++countCampo].toString()));
			} catch (ParseException e) {e.printStackTrace();}
			vo.setCargoNome(((String) obj[++countCampo]));
			vo.setQtdSolicitadoTotal(new Integer(obj[++countCampo].toString()));
			vo.setItemId(((BigInteger) obj[++countCampo]).longValue());
			vo.setQtdSolicitadoItem(new Integer(obj[++countCampo].toString()));
			vo.setQtdEntregue(new Integer(obj[++countCampo].toString()));
			vo.setSolicitacaoEpiSituacao(obj[++countCampo].toString().charAt(0));
			
			lista.add(vo);
		}
		
		return lista;
	}
}