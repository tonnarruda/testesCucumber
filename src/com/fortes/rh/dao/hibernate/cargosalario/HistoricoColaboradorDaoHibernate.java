package com.fortes.rh.dao.hibernate.cargosalario;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

@SuppressWarnings("unchecked")
public class HistoricoColaboradorDaoHibernate extends GenericDaoHibernate<HistoricoColaborador> implements HistoricoColaboradorDao
{
	private static final int ANTERIOR = 2;
	private static final int PROXIMO = 1;

	public Collection<HistoricoColaborador> findPromocaoByColaborador(Long colaboradorId)
	{
		StringBuilder hql = new StringBuilder();

		montaSelectConstrutor(hql);
		montaFromAndJoin(hql);

		hql.append("where co.id = :colaboradorId ");
		hql.append("order by hc.data");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("colaboradorId", colaboradorId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);

		return query.list();
	}

	public HistoricoColaborador getHistoricoAtual(Long colaboradorId, int tipoBuscaHistoricoColaborador)
	{
		StringBuilder hql = new StringBuilder();

		montaSelectConstrutor(hql);

		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.ambiente as a ");
		hql.append("left join hc.funcao as f ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.indice as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = i.id and ih2.data <= :hoje ) ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join co.empresa as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh2.data) from FaixaSalarialHistorico fsh2 where fsh2.faixaSalarial.id = fs.id and fsh2.data <= :hoje and fsh2.status != :status) ");
		hql.append("left join fsh.indice as ifs ");
		hql.append("left join ifs.indiceHistoricos as ifsh with ifsh.data = (select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifs.id and ih3.data <= :hoje) ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join c.grupoOcupacional as go ");

		hql.append("where co.id = :colaboradorId ");
		hql.append("and  ");
		hql.append("   ( ");
		hql.append("     hc.data <= :hoje and hc.status != :statusHistColab ");

		if(tipoBuscaHistoricoColaborador == TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO)
			hql.append(" or hc.data = (select min(hc3.data) from HistoricoColaborador as hc3 where hc3.colaborador.id=co.id and hc3.data > :hoje ) ");

		hql.append("   ) ");

		hql.append("order by hc.data desc, hc.id desc");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setInteger("statusHistColab", StatusRetornoAC.CANCELADO);
		query.setMaxResults(1);

		return (HistoricoColaborador) query.uniqueResult();
	}
	
	public HistoricoColaborador getHistoricoContratacaoAguardando(Long colaboradorId)
	{
		StringBuilder hql = new StringBuilder();

		montaSelectConstrutor(hql);

		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.ambiente as a ");
		hql.append("left join hc.funcao as f ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.indice as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = i.id and ih2.data <= :hoje ) ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join co.empresa as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh2.data) from FaixaSalarialHistorico fsh2 where fsh2.faixaSalarial.id = fs.id and fsh2.data <= :hoje and fsh2.status != :status) ");
		hql.append("left join fsh.indice as ifs ");
		hql.append("left join ifs.indiceHistoricos as ifsh with ifsh.data = (select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifs.id and ih3.data <= :hoje) ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join c.grupoOcupacional as go ");
		hql.append("where co.id = :colaboradorId ");
		hql.append("and hc.data <= :hoje ");
		hql.append("and hc.status = :statusHistColab ");
		hql.append("and hc.motivo = :motivo ");
		hql.append("order by hc.data desc, hc.id desc");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setInteger("statusHistColab", StatusRetornoAC.AGUARDANDO);
		query.setString("motivo", MotivoHistoricoColaborador.CONTRATADO);
		query.setMaxResults(1);

		return (HistoricoColaborador) query.uniqueResult();
	}

	private void montaSelectConstrutor(StringBuilder hql)
	{
		hql.append("select new HistoricoColaborador(hc.id, hc.salario, hc.data, hc.gfip, hc.motivo, hc.quantidadeIndice, hc.tipoSalario, hc.status, ");
		hql.append("e.id, co.id, co.nomeComercial, co.nome, co.codigoAC, co.naoIntegraAc, co.dataDesligamento, i.id, i.nome, ih.valor, ao.id, ao.nome, a.id, a.nome, f.id, f.nome, fs.id, fs.nome, ");
		hql.append("c.id, c.nomeMercado, c.nome, go.id, go.nome, es.id, es.nome, fsh.valor, fsh.tipo, fsh.quantidade, ifsh.valor, ifs.id, ");
		hql.append("fs.codigoAC, ao.codigoAC, es.codigoAC, fsh.status ) ");
	}

	private void montaFromAndJoin(StringBuilder hql)
	{
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.ambiente as a ");
		hql.append("left join hc.funcao as f ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.indice as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = i.id and ih2.data <= hc.data) ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join co.empresa as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh2.data) from FaixaSalarialHistorico fsh2 where fsh2.faixaSalarial.id = fs.id and fsh2.data <= hc.data and fsh2.status != :status) ");
		hql.append("left join fsh.indice as ifs ");
		hql.append("left join ifs.indiceHistoricos as ifsh with ifsh.data = (select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifs.id and ih3.data <= hc.data) ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join c.grupoOcupacional as go ");
	}

	private Criteria montaFindByGrupoOcupacionalIds(Collection gruposIds, Long empresaId,Criteria criteria)
	{
		criteria.addOrder(Order.asc("hc.data"));
		criteria.createCriteria("hc.faixaSalarial","fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("hc.colaborador","col");
		criteria.createCriteria("col.empresa", "emp");

		criteria.add(Expression.in("c.grupoOcupacional.id", gruposIds));

		if(empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public Collection<HistoricoColaborador> findByCargosIds(int page, int pagingSize, Collection<Long> cargosIds, Long empresaId, Colaborador colaborador)
	{
		if(cargosIds == null || cargosIds.size() == 0)
			return new ArrayList();

		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.faixaSalarial","fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.colaborador","col", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional","ao", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("col.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("ao.id"), "colaboradorArea");//seta area no colaborador
		p.add(Projections.property("col.id"), "colaboradorId");
		p.add(Projections.property("col.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("col.nome"), "colaboradorNome");
		p.add(Projections.property("col.matricula"), "colaboradorMatricula");
		p.add(Projections.property("col.desligado"), "colaboradorDesligado");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("col.desligado", false));

		if(empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));

		if(cargosIds != null && !cargosIds.isEmpty())
			criteria.add(Expression.in("c.id", cargosIds));

		if (colaborador != null)
		{
			if(colaborador.getNome() != null && !colaborador.getNome().equals(""))
				criteria.add(Expression.like("col.nome", "%"+ colaborador.getNome() +"%").ignoreCase() );

			if(colaborador.getMatricula() != null && !colaborador.getMatricula().equals(""))
				criteria.add(Expression.like("col.matricula", "%"+ colaborador.getMatricula() +"%").ignoreCase() );
		}

		criteria.addOrder(Order.asc("hc.data"));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();

	}

	public Collection<HistoricoColaborador> findByGrupoOcupacionalIds(int page, int pagingSize, Collection<Long> gruposIds, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");

		if(gruposIds != null && gruposIds.size() > 0)
		{
			criteria = montaFindByGrupoOcupacionalIds(gruposIds, empresaId, criteria);
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);

			return criteria.list();
		}

		return new ArrayList();
	}

	public Collection<SituacaoColaborador> getPromocoes(Long[] areaIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim, Long... empresasIds)
	{
		Criteria criteria = getSession().createCriteria(SituacaoColaborador.class, "sc");
		criteria.createCriteria("sc.estabelecimento", "e");
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("sc.salario"), "salario");
		p.add(Projections.property("sc.cargo.id"), "projectionCargoId");
		p.add(Projections.property("sc.faixaSalarial.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("e.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("sc.areaOrganizacional.id"), "projectionAreaOrganizacionalId");
		p.add(Projections.property("sc.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("sc.data"), "data");
		criteria.setProjection(p);
		
		criteria.add(Expression.not(Expression.eq("sc.motivo", MotivoHistoricoColaborador.DISSIDIO)));
		criteria.add(Expression.not(Expression.eq("sc.motivo", MotivoHistoricoColaborador.SEM_MOTIVO)));
		criteria.add(Expression.in("e.empresa.id", empresasIds));
		
		if(areaIds != null && areaIds.length > 0)
			criteria.add(Expression.in("sc.areaOrganizacional.id", areaIds));
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("e.id", estabelecimentosIds));
		if (dataIni != null)
			criteria.add(Expression.ge("sc.data", dataIni));
		if (dataFim != null)
			criteria.add(Expression.le("sc.data", dataFim));

		criteria.addOrder(Order.asc("sc.colaborador.id"));
		criteria.addOrder(Order.asc("sc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SituacaoColaborador.class));
		
		return criteria.list();
	}
	
	public List<RelatorioPromocoes> getRelatorioPromocoes(Long[] areaIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim, Long... empresasIds)
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append("select rp.estabelecimentoId, rp.estabelecimentoNome, rp.areaorganizacional_id, monta_familia_area(rp.areaorganizacional_id) as areaNome, ");
		sql.append("	count(rp.vertical) as qtdVertical, ");
		sql.append("	count(rp.horizontal) as qtdHorizontal ");
		sql.append("from ( ");
		sql.append("  select *,");
		sql.append("	case when sc.promocao = 'VERTICAL' then sc.promocao end as vertical, ");
		sql.append("	case when sc.promocao = 'HORIZONTAL' then sc.promocao end as horizontal ");
		sql.append("  from ( ");
		sql.append("	select  ");
		sql.append("		data, ");
		sql.append("		e.id as estabelecimentoId, ");
		sql.append("		e.nome as estabelecimentoNome, ");
		sql.append("		areaorganizacional_id,  ");
		sql.append("		CASE WHEN colaborador_id = lag(colaborador_id) OVER (ORDER BY colaborador_id, data) and cargo_id != lag(cargo_id) OVER (ORDER BY colaborador_id, data) THEN ");
		sql.append("			'VERTICAL' ");
		sql.append("			ELSE ");
		sql.append("			 CASE WHEN colaborador_id = lag(colaborador_id) OVER (ORDER BY colaborador_id, data) and (faixasalarial_id != lag(faixasalarial_id) OVER (ORDER BY colaborador_id, data) or (salario != lag(salario) OVER (ORDER BY colaborador_id, data))) ");
		sql.append("			 THEN 'HORIZONTAL' ");
		sql.append("			 END ");
		sql.append("			END AS promocao ");
		sql.append("	from SituacaoColaborador ");
		sql.append("	join Estabelecimento e on estabelecimento_id=e.id ");
		sql.append("	where ");
		sql.append("		not motivo='D'  ");
		sql.append("		and not motivo='S' ");
		sql.append("		and e.empresa_id in ( ");
		sql.append("		    :empresaIds ");
		sql.append("		)  ");
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0) {
			sql.append("		and e.id in ( ");
			sql.append("		    :estabelecimentosIds ");
			sql.append("		)  ");
		}
		if(areaIds != null && areaIds.length > 0) {
			sql.append("		and areaorganizacional_id in ( ");
			sql.append("		    :areaIds ");
			sql.append("		)  ");
		}
		sql.append("	ORDER BY colaborador_id, data ");
		sql.append("  ) as sc ");
		sql.append("  where sc.promocao is not null ");
		if (dataFim != null)
			sql.append("		and sc.data<=:dataFim ");
		if (dataIni != null)
			sql.append("		and sc.data >= :dataIni ");
		sql.append(") as rp ");
		sql.append("group by rp.estabelecimentoId, rp.estabelecimentoNome, rp.areaorganizacional_id ");
		sql.append("order by rp.estabelecimentoNome, areaNome ");
		
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("empresaIds", empresasIds, Hibernate.LONG);
		
		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		if (dataIni != null)
			query.setDate("dataIni", dataIni);
		if (dataFim != null)
			query.setDate("dataFim", dataFim);
		
		
		Collection<Object[]> resultado = query.list();
		List<RelatorioPromocoes> lista = new ArrayList<RelatorioPromocoes>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();

			lista.add(new RelatorioPromocoes( ((BigInteger) res[0]).longValue(), 
												(String) res[1], 
												((BigInteger) res[2]).longValue(), 
												(String) res[3], 
												res[4] != null ? ((BigInteger) res[4]).intValue() : 0 ,
												res[5] != null ? ((BigInteger) res[5]).intValue() : 0  ));
		}

		return lista;
	}
	
	public List<SituacaoColaborador> getUltimasPromocoes(Long[] areaIds, Long[] estabelecimentosIds, Date data, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(SituacaoColaborador.class, "sc");
		criteria.createCriteria("sc.estabelecimento", "e");
		criteria.createCriteria("sc.colaborador", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("sc.salario"), "salario");
		p.add(Projections.property("sc.data"), "data");
		p.add(Projections.property("sc.cargo.id"), "projectionCargoId");
		p.add(Projections.property("sc.faixaSalarial.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("e.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("sc.areaOrganizacional.id"), "projectionAreaOrganizacionalId");
		p.add(Projections.property("c.id"), "projectionColaboradorId");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.matricula"), "projectionColaboradorMatricula");
		criteria.setProjection(p);
		
		criteria.add(Expression.le("sc.data", data));
		
		criteria.add(Expression.not(Expression.eq("sc.motivo", MotivoHistoricoColaborador.DISSIDIO)));
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));
		
		if(areaIds != null && areaIds.length > 0)
			criteria.add(Expression.in("sc.areaOrganizacioanl.id", areaIds));
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("e.id", estabelecimentosIds));

		criteria.addOrder(Order.asc("sc.colaborador.id"));
		criteria.addOrder(Order.asc("sc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SituacaoColaborador.class));
		
		return criteria.list();
	}

	public HistoricoColaborador findByIdProjectionMinimo(Long historicoColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.status"), "status");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.id", historicoColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return (HistoricoColaborador) criteria.uniqueResult();
	}

	public HistoricoColaborador getHistoricoAnterior(HistoricoColaborador historico)
	{
		return findHistorico(historico, ANTERIOR);
	}

	public HistoricoColaborador getHistoricoProximo(HistoricoColaborador historico)
	{
		return findHistorico(historico, PROXIMO);
	}

	private HistoricoColaborador findHistorico(HistoricoColaborador historico, int origem)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", historico.getColaborador().getId()));

		if(origem == PROXIMO)
			criteria.add(Expression.gt("hc.data", historico.getData()));
		else if(origem == ANTERIOR)
			criteria.add(Expression.lt("hc.data", historico.getData()));

		criteria.addOrder(Order.asc("hc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));
		criteria.setMaxResults(1);

		return (HistoricoColaborador) criteria.uniqueResult();
	}

	public Collection<HistoricoColaborador> findByColaboradorData(Long idColaborador, Date data)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
//		criteria.createCriteria("hc.colaborador", "c");
		criteria.setFetchMode("hc.colaborador", FetchMode.DEFAULT);

		criteria.add(Expression.eq("hc.colaborador.id", idColaborador));
		criteria.add(Expression.le("hc.data", data));
		criteria.addOrder(Order.asc("hc.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	public Collection<HistoricoColaborador> getHistoricosAtuaisByEstabelecimentoAreaGrupo(Long[] estabelecimentoIds, char filtrarPor, Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId, Date dataTabela)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new HistoricoColaborador(hc.id, hc.salario, hc.colaborador.id, a.id, a.nome, hc.funcao.id, hc.ambiente.id, hc.estabelecimento.id, co.nome, c.nomeMercado, fs.id, hc.tipoSalario) ");

		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join hc.areaOrganizacional as a ");

		hql.append("where co.empresa.id = :empresaId ");
		hql.append("and co.desligado = :desligado ");
		hql.append("and co.naoIntegraAc = :naoIntegraAc ");
		hql.append("and hc.tipoSalario = :tipoValor ");

		if(estabelecimentoIds.length > 0)
			hql.append("and hc.estabelecimento in ( :estabelecimentoIds ) ");

		if(filtrarPor == '1' && areaOrganizacionalIds.length > 0)//Area Organizacional
		{
			hql.append("and hc.areaOrganizacional.id in( :areaIds ) ");
		}
		else if(filtrarPor == '2' && grupoOcupacionalIds.length > 0)//Grupo Ocupacional
		{
			hql.append("and c.grupoOcupacional.id in( :grupoIds ) ");
		}

		hql.append("and hc.status = :status ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("from HistoricoColaborador as hc2 ");
		hql.append("where hc2.colaborador.id = co.id ");
		hql.append("and hc2.data <= :hoje and hc2.status = :status) ");
		hql.append("order by a.nome, co.nomeComercial ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", dataTabela);
		query.setLong("empresaId", empresaId);
		query.setBoolean("desligado", false);
		query.setBoolean("naoIntegraAc", false);
		query.setInteger("tipoValor", TipoAplicacaoIndice.VALOR);

		if(filtrarPor == '1' && areaOrganizacionalIds.length > 0)//Area Organizacional
		{
			query.setParameterList("areaIds", areaOrganizacionalIds, Hibernate.LONG);
		}
		else if(filtrarPor == '2' && grupoOcupacionalIds.length > 0)//Grupo Ocupacional
		{
			query.setParameterList("grupoIds", grupoOcupacionalIds, Hibernate.LONG);
		}

		if(estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId, Integer statusRetornoAC)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));
		
		if(statusRetornoAC != null)
			criteria.add(Expression.eq("hc.status", statusRetornoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}

	public HistoricoColaborador findByIdHQL(Long historicoColaboradorId)
	{
		StringBuilder hql = new StringBuilder();

		montaSelectConstrutor(hql);

		montaFromAndJoin(hql);

		hql.append("where hc.id = :historicoColaboradorId ");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("historicoColaboradorId", historicoColaboradorId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setMaxResults(1);

		return (HistoricoColaborador) query.uniqueResult();
	}

	public boolean setStatus(Long historicoColaboradorId, Boolean aprovado)
	{
		String hql = "update HistoricoColaborador set status = :aprovado where id = :historicoColaboradorId";

		int status = StatusRetornoAC.CANCELADO;
		if(aprovado)
			status = StatusRetornoAC.CONFIRMADO;

		Query query = getSession().createQuery(hql);
		query.setInteger("aprovado", status);
		query.setLong("historicoColaboradorId", historicoColaboradorId);

		return query.executeUpdate() == 1;
	}
	
	public void updateSituacaoByMovimentacao(String codigoEmpregado, String movimentacao, String codPessoalEstabOuArea, boolean atualizarTodasSituacoes, Long empresaId)
	{
		String hql = "update HistoricoColaborador set " + movimentacao +".id = ( select id from " + WordUtils.capitalize(movimentacao) + " where codigoAC = :codPessoalEstabOuArea and empresa.id = :empresaId ) " +
		" where colaborador.id = ( select id from Colaborador where codigoAC = :codigoEmpregado and empresa.id = :empresaId )";
		if (!atualizarTodasSituacoes) {
			hql += " and data = (select max(hc2.data) " +
			" from HistoricoColaborador as hc2 " +
			" where hc2.colaborador.id = ( select id from Colaborador where codigoAC = :codigoEmpregado and empresa.id = :empresaId ) " +
			" and hc2.data <= :date and hc2.status = :status)";
		}
		
		Query query = getSession().createQuery(hql);
		if (!atualizarTodasSituacoes) {
			query.setDate("date", new Date());
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		}
		query.setLong("empresaId", empresaId);
		query.setString("codigoEmpregado", codigoEmpregado);
		query.setString("codPessoalEstabOuArea", codPessoalEstabOuArea);

		query.executeUpdate();
	}

	public String findColaboradorCodigoAC(Long historicoColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.id", historicoColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (String) criteria.uniqueResult();
	}

	public HistoricoColaborador findByIdProjection(Long historicoColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "co", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("co.empresa", "emp", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo","ca", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.indice", "i", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.funcao", "fu", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.ambiente", "am", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.salario"), "salario");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.motivo"), "motivo");
		p.add(Projections.property("hc.gfip"), "gfip");
		p.add(Projections.property("hc.quantidadeIndice"), "quantidadeIndice");
		p.add(Projections.property("hc.tipoSalario"), "tipoSalario");
		p.add(Projections.property("hc.candidatoSolicitacao"), "candidatoSolicitacao");
		p.add(Projections.property("hc.status"), "status");
		p.add(Projections.property("hc.reajusteColaborador.id"), "projectionReajusteColaboradorId");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");
		p.add(Projections.property("fs.id"), "faixaSalarialId");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("ca.nomeMercado"), "cargoNomeMercado");
		p.add(Projections.property("hc.funcao.id"), "funcaoId");
		p.add(Projections.property("fu.nome"), "funcaoNome");
		p.add(Projections.property("hc.ambiente.id"), "ambienteId");
		p.add(Projections.property("am.nome"), "ambienteNome");
		p.add(Projections.property("e.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");
		p.add(Projections.property("ao.id"), "areaId");
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.property("co.id"), "colaboradorId");
		p.add(Projections.property("co.nome"), "colaboradorNome");
		p.add(Projections.property("co.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("co.dataAdmissao"), "colaboradorDataAdmissao");
		p.add(Projections.property("co.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("co.naoIntegraAc"), "projectionColaboradorNaoIntegraAc");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.id", historicoColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return (HistoricoColaborador) criteria.uniqueResult();
	}

	public Long findReajusteByHistoricoColaborador(Long historicoColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.reajusteColaborador.id"));

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.id", historicoColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Long) criteria.uniqueResult();
	}

	public Collection<HistoricoColaborador> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.reajusteColaborador", "rc");
		criteria.createCriteria("rc.tabelaReajusteColaborador", "tr");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.codigoAC"), "colaboradorCodigoAC");

		criteria.setProjection(p);

		criteria.add(Expression.eq("tr.id", tabelaReajusteColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}

	public Collection<HistoricoColaborador> findPendenciasByHistoricoColaborador(Long empresaId, Integer... statusAc)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.status"), "status");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("c.dataAdmissao"), "colaboradorDataAdmissao");
		p.add(Projections.property("ca.nome"), "cargoNome");
		p.add(Projections.property("ca.nomeMercado"), "cargoNomeMercado");

		criteria.setProjection(p);

		criteria.add(Expression.in("hc.status", statusAc));
		criteria.add(Expression.eq("c.naoIntegraAc", false));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}

	public Collection<HistoricoColaborador> findHistoricoAprovado(Long historicoColaboradorId, Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.add(Expression.ne("hc.id", historicoColaboradorId));
		criteria.add(Expression.eq("c.id", colaboradorId));
		criteria.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}

	public HistoricoColaborador findByIdProjectionHistorico(Long historicoColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo","ca", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.salario"), "salario");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.motivo"), "motivo");
		p.add(Projections.property("hc.gfip"), "gfip");
		p.add(Projections.property("hc.quantidadeIndice"), "quantidadeIndice");
		p.add(Projections.property("hc.tipoSalario"), "tipoSalario");
		p.add(Projections.property("hc.status"), "status");
		p.add(Projections.property("hc.indice.id"), "projectionIndiceId");
		p.add(Projections.property("hc.faixaSalarial.id"), "faixaSalarialId");
		p.add(Projections.property("hc.funcao.id"), "funcaoId");
		p.add(Projections.property("hc.ambiente.id"), "ambienteId");
		p.add(Projections.property("hc.estabelecimento.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("hc.areaOrganizacional.id"), "areaId");
		p.add(Projections.property("hc.reajusteColaborador.id"), "projectionReajusteColaboradorId");
		p.add(Projections.property("hc.candidatoSolicitacao"), "candidatoSolicitacao");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.solicitacao.id"), "colaboradorSolicitacaoId");
		p.add(Projections.property("c.candidato.id"), "colaboradorCandidatoId");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("fs.id"), "faixaSalarialId");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("ca.nomeMercado"), "cargoNomeMercado");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.id", historicoColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return (HistoricoColaborador) criteria.uniqueResult();
	}

	public HistoricoColaborador findByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("c.empresa", "e");

		criteria.add(Expression.eq("hc.data", data));
		criteria.add(Expression.eq("c.codigoAC", empregadoCodigoAC));
		criteria.add(Expression.eq("e.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("e.grupoAC", grupoAC));

		return (HistoricoColaborador)criteria.uniqueResult();
	}

	public HistoricoColaborador findAtualByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("c.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.gfip"), "gfip");
		p.add(Projections.property("hc.funcao.id"), "funcaoId");
		p.add(Projections.property("hc.ambiente.id"), "ambienteId");
		p.add(Projections.property("c.id"), "colaboradorId");
		criteria.setProjection(p);

		criteria.add(Expression.lt("hc.data", data));
		criteria.add(Expression.eq("c.codigoAC", empregadoCodigoAC));
		criteria.add(Expression.eq("e.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("e.grupoAC", grupoAC));
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("hc.data"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return (HistoricoColaborador)criteria.uniqueResult();
	}

	public HistoricoColaborador getPrimeiroHistorico(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.id"), "colaboradorId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));
		criteria.setMaxResults(1);
		criteria.addOrder(Order.asc("hc.data"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return (HistoricoColaborador)criteria.uniqueResult();
	}

	public void removeColaborador(Long colaboradorId)
	{
		String hql = "delete from HistoricoColaborador h where h.colaborador.id = :colaboradorId";

		Query query = getSession().createQuery(hql);
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}

	public HistoricoColaborador findHistoricoAdmissao(Long colaboradorId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new HistoricoColaborador(c.dataAdmissao, min(hc.data)) ");
		hql.append("from HistoricoColaborador hc join hc.colaborador c ");
		hql.append("where c.id = :colaboradorId ");
		hql.append("group by c.dataAdmissao ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		
		return (HistoricoColaborador)query.uniqueResult();
	}

	public Collection<HistoricoColaborador> findColaboradoresByTabelaReajusteData(Long tabelaReajusteColaboradorId, Date data)
	{
        DetachedCriteria subQuery = DetachedCriteria.forClass(ReajusteColaborador.class, "rc");
        ProjectionList pSub = Projections.projectionList().create();

        pSub.add(Projections.property("rc.colaborador.id"), "idColaborador");
        subQuery.setProjection(pSub);

        subQuery.add(Expression.eq("rc.tabelaReajusteColaborador.id", tabelaReajusteColaboradorId));
		
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");

		criteria.setProjection(p);

		criteria.add(Subqueries.propertyIn("c.id", subQuery));
		criteria.add(Expression.eq("hc.data", data));
		
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}

	public Collection<HistoricoColaborador> findByData(Long colaboradorId, Date data)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));
		criteria.add(Expression.eq("hc.data", data));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}

	public Collection<HistoricoColaborador> findByCargoEstabelecimento(Date dataHistorico, Long[] cargoIds, Long[] estabelecimentoIds, Date dataConsulta, Long[] areaOrganizacionalIds, Date dataAtualizacao, String vinculo, Long... empresasIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct new HistoricoColaborador(hc.id, co.id, co.nome, co.nomeComercial, co.dataAdmissao, co.codigoAC, co.vinculo, c.id, c.nome, fs.id, fs.nome, e.id, e.nome, emp.id, emp.nome, hc.salario, emp.acIntegra, hc.tipoSalario, hc.quantidadeIndice, i, fs, fsh, ih, ifs, ifsh, ao.id, cast(monta_familia_area(ao.id), text) as ao_nome ) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join hc.indice as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = i.id and ih2.data <= :data) ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join co.empresa as emp ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh2.data) from FaixaSalarialHistorico fsh2 where fsh2.faixaSalarial.id = fs.id and fsh2.data <= :data and fsh2.status != :statusFaixaSalarial) ");
		hql.append("left join fsh.indice as ifs ");
		hql.append("left join ifs.indiceHistoricos as ifsh with ifsh.data = (select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifs.id and ih3.data <= :data) ");
		hql.append("left join fs.cargo as c ");
		
		hql.append("where (co.dataDesligamento is null or co.dataDesligamento >= :data) ");
		hql.append("and hc.status = :status ");
		
		if(dataConsulta != null)
			hql.append("and co.dataAdmissao <= :dataConsulta ");
		
		if(cargoIds != null && cargoIds.length > 0)
			hql.append("and c.id in ( :cargoIds ) ");
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and e.id in ( :estabelecimentoIds ) ");
		
		if(areaOrganizacionalIds != null && areaOrganizacionalIds.length>0)
			hql.append(" and ao.id in (:areaOrganizacionalIds) ");

		if(dataAtualizacao != null )
			hql.append("and co.dataAtualizacao <= :dataAtualizacao ");

		if(empresasIds != null && empresasIds.length > 0 )
			hql.append("and co.empresa.id in (:empresasIds) ");

		if(vinculo != null && !vinculo.equals("") )
			hql.append("and co.vinculo = :vinculo ");

		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("order by emp.nome, c.nome, fs.nome, fs.id, co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", dataHistorico);
		query.setInteger("statusFaixaSalarial", StatusRetornoAC.CANCELADO);
		
		if(dataConsulta != null)
			query.setDate("dataConsulta", dataConsulta);
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if(areaOrganizacionalIds != null && areaOrganizacionalIds.length>0)
			query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, Hibernate.LONG);
		
		if(dataAtualizacao != null)
			query.setDate("dataAtualizacao", dataAtualizacao);

		if(empresasIds != null && empresasIds.length > 0)
			query.setParameterList("empresasIds", empresasIds);
		
		if(vinculo != null && !vinculo.equals("") )
			query.setString("vinculo", vinculo);

		return query.list();
	}

	public Collection<HistoricoColaborador> findImprimirListaFrequencia(Estabelecimento estabelecimento, Date votacaoIni, Date votacaoFim) 
	{

		StringBuilder hql = new StringBuilder();
		hql	.append("select new HistoricoColaborador(hc.id, c.nome,hc.areaOrganizacional.nome) ")
			.append("from HistoricoColaborador as hc ")
			.append("left join hc.colaborador as c ")
			.append("where c.dataAdmissao <= :votacaoFim ")
			.append("	and (c.desligado is false or (c.desligado is true and c.dataDesligamento between :votacaoIni and :votacaoFim)) ")
			.append("	and hc.status = :status ")
			.append("	and hc.estabelecimento.id = :estabelecimento ")
			.append("	and hc.data = (select max(hc2.data) from HistoricoColaborador as hc2 where hc2.colaborador.id = c.id ")
			.append("					and hc2.data <= :votacaoFim ")
			.append("					and hc2.status = :status) ")
			.append("	or hc.data is null ")
			.append("order by c.nome");
		
		Query query = getSession()
			.createQuery(hql.toString())
			.setParameter("estabelecimento", estabelecimento.getId())
			.setDate("votacaoIni", votacaoIni)
			.setDate("votacaoFim", votacaoFim)
			.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
		
	}

		
	/**
	 * busca por filtros e também pela origem (RH ou AC), se for especificado.
	 */
	public Collection<HistoricoColaborador> findByPeriodo(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao, char agruparPor, boolean imprimirDesligados) {

		StringBuilder sql = new StringBuilder();
		sql.append("select hc.id as hc_id, hc.data as hc_data, hc.motivo as hc_motivo, co.id as co_id, co.nome as co_nome, cg.nome as cg_nome, fs.nome as fs_nome, es.id as es_id, es.nome as es_nome, ao.id as ao_id, monta_familia_area(ao.id) as ao_nome, am.id as am_id, am.nome as am_nome, hc.tipoSalario as hc_tipoSalario, hc.salario as hc_salario, ");
		sql.append("hc.quantidadeIndice as hc_quantidadeIndice, hcih.valor as hcih_valor, fsh.tipo as fsh_tipo, fsh.valor as fsh_valor, fsh.quantidade as fsh_quantidade, fshih.valor as fshih_valor, hci.nome as hci_nome ");
		
		sql.append("from HistoricoColaborador hc ");
		sql.append("left join areaOrganizacional ao on hc.areaOrganizacional_id = ao.id ");
		sql.append("left join areaOrganizacional am on ao.areaMae_id = am.id ");
		sql.append("left join estabelecimento es on hc.estabelecimento_id = es.id ");
		sql.append("left join colaborador co on hc.colaborador_id = co.id ");
		sql.append("left join faixaSalarial fs on hc.faixaSalarial_id = fs.id ");
		sql.append("left join cargo cg on fs.cargo_id = cg.id ");
		
		sql.append("left join indice hci on hc.indice_id = hci.id ");
		sql.append("left join indiceHistorico hcih on hcih.indice_id = hci.id and hcih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice_id = hci.id and ih2.data <= :data) ");
		sql.append("left join faixaSalarialHistorico fsh on fsh.faixaSalarial_id = fs.id and fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial_id = fs.id and fsh3.data <= :data) ");
		sql.append("left join indice fshi on fsh.indice_id = fshi.id ");
		sql.append("left join indiceHistorico fshih on fshih.indice_id = fshi.id and fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice_id = fshi.id and ih4.data <= :data) ");
		
		sql.append("where ");
		sql.append("co.empresa_id = :empresaId ");
		sql.append("and	hc.data between :dataIni and :dataFim ");
		sql.append("and	es.id in (:estabelecimentosIds) ");
		if (areasIds != null && areasIds.length > 0)
			sql.append("and	ao.id in (:areasIds) ");
		
		if (origemSituacao.equals("RH"))
			sql.append("and hc.motivo != :motivo ");
		else if (origemSituacao.equals("AC"))
			sql.append("and hc.motivo = :motivo ");
		
		if (!imprimirDesligados) 
			sql.append("and co.desligado = false ");
		
		if (agruparPor == 'A') 
			sql.append("order by es.nome, ao_nome, hc.data desc");
		else if (agruparPor == 'M')
			sql.append("order by es.nome, hc.data desc, ao_nome");
		else if (agruparPor == 'C')
			sql.append("order by es.nome, co.nome, hc.data desc");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("data", new Date());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		if (areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if (StringUtils.isNotBlank(origemSituacao) && !origemSituacao.equals("T"))
			query.setString("motivo", MotivoHistoricoColaborador.IMPORTADO);
		
		
		Collection<Object[]> resultado = query.list();
		Collection<HistoricoColaborador> lista = new ArrayList<HistoricoColaborador>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();

			lista.add(new HistoricoColaborador(((BigInteger) res[0]).longValue(), 
												(Date) res[1], 
												(String) res[2], 
												((BigInteger) res[3]).longValue(), 
												(String) res[4], 
												(String) res[5], 
												(String) res[6], 
												((BigInteger) res[7]).longValue(), 
												(String) res[8], 
												((BigInteger) res[9]).longValue(), 
												(String) res[10], 
												res[11] != null ? ((BigInteger) res[11]).longValue() : null, 
												(String) res[12], 
												(Integer) res[13], 
												(Double) res[14], 
												(Double) res[15], 
												(Double) res[16], 
												(Integer) res[17], 
												(Double) res[18], 
												(Double) res[19], 
												(Double) res[20], 
												(String) res[21]));
		}

		return lista;
	}
	
	/**
	 * consulta usada a princípio para trazer função e ambiente, pode ser expandida 
	 */
	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("hc.areaOrganizacional", "ao", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.ambiente", "amb", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.funcao", "fun", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "ca");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.estabelecimento.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("hc.areaOrganizacional.id"), "areaId");
		p.add(Projections.property("fs.nome"), "faixaSalarialDescricao");
		p.add(Projections.property("ca.id"), "cargoId");
		p.add(Projections.property("ca.nome"), "cargoNome");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("fun.id"), "funcaoId");
		p.add(Projections.property("fun.nome"), "funcaoNome");
		p.add(Projections.property("amb.id"), "ambienteId");
		p.add(Projections.property("amb.nome"), "ambienteNome");
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.property("e.nome"), "estabelecimentoNome");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));
		criteria.addOrder(Order.asc("hc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}
	
	public boolean updateAmbienteEFuncao(HistoricoColaborador historicoColaborador)
	{
		String parametroAmbienteId = "null";
		String parametroFuncaoId = "null";
		
		if (historicoColaborador.getAmbiente().getId() != null)
			parametroAmbienteId = ":ambienteId";
		if (historicoColaborador.getFuncao().getId() != null)
			parametroFuncaoId = ":funcaoId";
		
		String update = "UPDATE HistoricoColaborador hc " +
						"SET hc.ambiente.id = " + parametroAmbienteId + ", hc.funcao.id = " + 
						parametroFuncaoId + " WHERE hc.id = :id";
		
		Query query = getSession().createQuery(update);
		
		if (historicoColaborador.getAmbiente().getId() != null)
			query.setLong("ambienteId", historicoColaborador.getAmbiente().getId());
		if (historicoColaborador.getFuncao().getId() != null)
			query.setLong("funcaoId", historicoColaborador.getFuncao().getId());
		
		query.setLong("id", historicoColaborador.getId());
		
		return query.executeUpdate() == 1;
	}

	public Collection<HistoricoColaborador> findHistoricoAdmitidos(Long empresaId, Date data)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new HistoricoColaborador(hc1.tipoSalario, hc1.salario, hc1.quantidadeIndice, " +
								" fs.id, fsh.tipo, fsh.valor, fsh.quantidade, fshih.valor, " +
								" hci.id, hcih.valor ) ");

		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join hc1.faixaSalarial as fs ");

		hql.append("left join hc1.indice as hci ");
		hql.append("left join hci.indiceHistoricos as hcih with hcih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = hci.id and ih2.data <= :data) ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data and (fsh3.status = 0 or fsh3.status = 1) ) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");

		hql.append("where ");
		hql.append("	hc1.status = :status and ( ");
		hql.append("		hc1.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data ");
		hql.append("			and hc2.status = :status ");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		hql.append("and co.empresa.id = :empresaId ");
		hql.append("and	co.desligado = false ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public void deleteSituacaoByMovimentoSalarial(Long movimentoSalarialId, Long empresaId)
	{
		String hql = "delete from HistoricoColaborador h where h.movimentoSalarialId = :movimentoSalarialId and " +
				"h.colaborador.id in (select c.id from Colaborador c  where c.empresa.id = :idEmpresa) ";

		Query query = getSession().createQuery(hql);
		query.setLong("movimentoSalarialId", movimentoSalarialId);
		query.setLong("idEmpresa", empresaId);

		query.executeUpdate();		
	}

	public void setMotivo(Long[] historicoColaboradorIds, String tipo) 
	{
		if(historicoColaboradorIds != null && historicoColaboradorIds.length > 0)
		{
			Query query = getSession().createQuery("update HistoricoColaborador set motivo = :tipo where id in (:historicoColaboradorIds)");
			query.setString("tipo", tipo);
			query.setParameterList("historicoColaboradorIds", historicoColaboradorIds);
			
			query.executeUpdate();	
		}
	}

	public Collection<HistoricoColaborador> findSemDissidioByDataPercentual(Date dataIni, Date dataFim, Double percentualDissidio, Long empresaId) 
	{	
		StringBuilder hql = new StringBuilder();

		montaSelectConstrutor(hql);
		montaFromAndJoin(hql);

		hql.append("where hc.data >= :dataIni ");
		
		if(dataFim != null)
			hql.append("and hc.data <= :dataFim ");
		
		hql.append("and co.empresa.id = :empresaId ");
		hql.append("and co.dataDesligamento is null ");
		hql.append("and hc.motivo != :motivo ");
		
		hql.append("order by co.nome, hc.data");

		Query query = getSession().createQuery(hql.toString());
		
		if(dataFim != null)
			query.setDate("dataFim", dataFim);
		
		query.setDate("dataIni", dataIni);
		query.setLong("empresaId", empresaId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setString("motivo", MotivoHistoricoColaborador.SEM_MOTIVO);

		return query.list();

	}

	public void ajustaMotivoContratado(Long colaboradorId) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set motivo = :promocao where colaborador.id = :colaboradorId and motivo = :contratado ");
		query.setString("promocao", MotivoHistoricoColaborador.PROMOCAO);
		query.setString("contratado", MotivoHistoricoColaborador.CONTRATADO);
		query.setLong("colaboradorId", colaboradorId);
		
		query.executeUpdate();
	}

	public void removeCandidatoSolicitacao(Long candidatoSolicitacaoId) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set candidatoSolicitacao.id = null where candidatoSolicitacao.id = :candidatoSolicitacaoId ");
		query.setLong("candidatoSolicitacaoId", candidatoSolicitacaoId);
		
		query.executeUpdate();
	}

	public void setaContratadoNoPrimeiroHistorico(Long colaboradorId) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set motivo = :contratado where data=(select min(data) from HistoricoColaborador where colaborador.id=:colaboradorId) " +
											  " and motivo <> :contratado and colaborador.id=:colaboradorId");

		query.setString("contratado", MotivoHistoricoColaborador.CONTRATADO);
		query.setLong("colaboradorId", colaboradorId);
		
		query.executeUpdate();
	}

	public void deleteHistoricoColaborador(Long[] colaboradorIds) throws Exception{
		if(colaboradorIds != null && colaboradorIds.length > 0)
		{
			String hql = "delete HistoricoColaborador where colaborador.id in (:colaboradorIds)";
			Query query = getSession().createQuery(hql);
	
			query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}

	public Collection<HistoricoColaborador> findByAreaGrupoCargo(Long[] empresaIds, Date dataHistorico, Long[] cargoIds, Long[] estabelecimentoIds, Long[] areaIds, Boolean areasAtivas, Long[] grupoOcupacionalIds, String vinculo)
	{

		StringBuilder hql = new StringBuilder();
		hql.append("select distinct new HistoricoColaborador(hc.id, co.id, co.nome, co.nomeComercial, co.dataAdmissao, co.vinculo, ao.id, ao.nome, ao.areaMae.id, c.id, c.nome, fs.id, fs.nome, go.id, go.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("inner join hc.areaOrganizacional as ao ");
		hql.append("inner join hc.estabelecimento as e ");
		hql.append("inner join hc.colaborador as co ");
		hql.append("inner join co.empresa as emp ");
		hql.append("inner join hc.faixaSalarial as fs ");
		hql.append("inner join fs.cargo as c ");
		hql.append("left join c.grupoOcupacional as go ");

		hql.append("where (co.dataDesligamento is null or co.dataDesligamento > :data) ");
		hql.append("and hc.status = :status ");

		if(cargoIds != null && cargoIds.length > 0)
			hql.append("and c.id in ( :cargoIds ) ");

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and e.id in ( :estabelecimentoIds ) ");

		if(areasAtivas != null)
			hql.append(" and ao.ativo = :areasAtivas ");
		
		if(areaIds != null && areaIds.length>0)
			hql.append(" and ao.id in (:areaOrganizacionalIds) ");

		if(grupoOcupacionalIds != null && grupoOcupacionalIds.length>0)
			hql.append(" and go.id in (:grupoOcupacionalIds) ");

		hql.append("and co.empresa.id in(:empresaIds) ");

		if(vinculo != null && !vinculo.equals("") )
			hql.append("and co.vinculo = :vinculo ");

		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("order by go.nome, co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", dataHistorico);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if(areasAtivas != null)
			query.setBoolean("areasAtivas", areasAtivas);

		if(areaIds != null && areaIds.length>0)
			query.setParameterList("areaOrganizacionalIds", areaIds, Hibernate.LONG);
		
		if(grupoOcupacionalIds != null && grupoOcupacionalIds.length>0)
			query.setParameterList("grupoOcupacionalIds", grupoOcupacionalIds, Hibernate.LONG);

		query.setParameterList("empresaIds", empresaIds);

		if(vinculo != null && !vinculo.equals("") )
			query.setString("vinculo", vinculo);

		return query.list();
	}

	public void deleteHistoricosAguardandoConfirmacaoByColaborador(Long... colaboradoresIds)
	{
		if(colaboradoresIds != null)
		{
			String hql = "delete HistoricoColaborador where colaborador.id in (:colaboradoresIds) and status = :status";
			Query query = getSession().createQuery(hql);
	
			query.setParameterList("colaboradoresIds", colaboradoresIds);
			query.setInteger("status", StatusRetornoAC.AGUARDANDO);
			query.executeUpdate();		
		}
		
	}

	public boolean existeHistoricoPorIndice(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "hc");
		criteria.createCriteria("hc.colaborador", "c");

		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("hc.tipoSalario", TipoAplicacaoIndice.INDICE));
		
		return ((Integer) criteria.uniqueResult()) > 0;
	}

	public void updateStatusAc(int statusRetornoAC, Long... id) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set status = :status where id in (:id) ");

		query.setInteger("status", statusRetornoAC);
		query.setParameterList("id", id);
		
		query.executeUpdate();
	}

	public Collection<HistoricoColaborador> findByEmpresaComHistorico(Long empresaId, Integer status, boolean dataSolDesligNotNull) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("hc.estabelecimento", "e");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.tipoSalario"), "tipoSalario");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.gfip"), "gfip");
		p.add(Projections.property("hc.salario"), "salario");
		p.add(Projections.property("hc.quantidadeIndice"), "quantidadeIndice");
		p.add(Projections.property("fs.codigoAC"), "faixaCodigoAC");
		p.add(Projections.property("ao.codigoAC"), "areaOrganizacionalCodigoAC");
		p.add(Projections.property("e.codigoAC"), "estabelecimentoCodigoAC");
		p.add(Projections.property("i.codigoAC"), "projectionIndiceCodigoAC");
		p.add(Projections.property("c.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("c.dataSolicitacaoDesligamentoAc"), "dataSolicitacaoDesligamento");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.status", status));
		criteria.add(Expression.eq("c.naoIntegraAc", false));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		if(dataSolDesligNotNull)
			criteria.add(Expression.isNotNull("c.dataSolicitacaoDesligamentoAc"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));

		return criteria.list();
	}	
	
	public void updateStatusAcByEmpresaAndStatusAtual(int novoStatusAC, int statusACAtual, Long... colaboradoresIds) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set status = :novoStatusAC where colaborador.id in (:colaboradoresIds) and status = :statusACAtual ");
		
		query.setInteger("novoStatusAC", novoStatusAC);
		query.setInteger("statusACAtual", statusACAtual);
		query.setParameterList("colaboradoresIds", colaboradoresIds);
		
		query.executeUpdate();
		
	}
	
	public void updateArea(Long areaIdMae, Long areaId) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set areaOrganizacional.id = :areaId where areaOrganizacional.id = :areaIdMae");
		
		query.setLong("areaIdMae", areaIdMae);
		query.setLong("areaId", areaId);
		
		query.executeUpdate();
	}

	public boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Date dataSegundoHistoricoIndice, Long indiceId) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.setProjection(Projections.count("data"));
				
		criteria.add(Expression.in("hc.status",new Integer[] {StatusRetornoAC.AGUARDANDO, StatusRetornoAC.CONFIRMADO}));
		criteria.add(Expression.eq("hc.tipoSalario", TipoAplicacaoIndice.INDICE));
		criteria.add(Expression.eq("hc.indice.id", indiceId));
		
		criteria.add(Expression.ge("hc.data", dataHistoricoExcluir));
		
		if(dataSegundoHistoricoIndice != null)
			criteria.add(Expression.lt("hc.data", dataSegundoHistoricoIndice));

		return ((Integer) criteria.uniqueResult()) > 0;
	}

	public HistoricoColaborador findHistoricoColaboradorByData(Long colaboradorId, Date data) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
							.setProjection(Projections.max("hc2.data"))
							.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
							.add(Restrictions.le("hc2.data", data))
							.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.funcao.id"), "funcaoId");
		p.add(Projections.property("c.dataAdmissao"), "colaboradorDataAdmissao");
		p.add(Projections.property("c.desligado"), "colaboradorDesligado");
		p.add(Projections.property("c.dataDesligamento"), "colaboradorDataDesligamento");
		
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.add(Property.forName("hc.data").eq(subQueryHc));	
		criteria.add(Expression.eq("hc.colaborador.id", colaboradorId));

		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaborador.class));
		return (HistoricoColaborador) criteria.uniqueResult();
	}
}