package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

@SuppressWarnings("unchecked")
public class HistoricoColaboradorDaoHibernate extends GenericDaoHibernate<HistoricoColaborador> implements HistoricoColaboradorDao
{
	private static final int PROXIMO = 1;
	private static final int ANTERIOR = 2;

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
		hql.append("     hc.data <= :hoje and hc.status = :statusHistColab ");

		if(tipoBuscaHistoricoColaborador == TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO)
			hql.append(" or hc.data = (select min(hc3.data) from HistoricoColaborador as hc3 where hc3.colaborador.id=co.id and hc3.data > :hoje ) ");

		hql.append("   ) ");

		hql.append("order by hc.data desc, hc.id desc");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setInteger("statusHistColab", StatusRetornoAC.CONFIRMADO);
		query.setMaxResults(1);

		return (HistoricoColaborador) query.uniqueResult();
	}

	private void montaSelectConstrutor(StringBuilder hql)
	{
		hql.append("select new HistoricoColaborador(hc.id, hc.salario, hc.data, hc.gfip, hc.motivo, hc.quantidadeIndice, hc.tipoSalario, hc.status, ");
		hql.append("e.id, co.id, co.nomeComercial, co.nome, co.codigoAC, co.naoIntegraAc, i.id, i.nome, ih.valor, ao.id, ao.nome, a.id, a.nome, f.id, f.nome, fs.id, fs.nome, ");
		hql.append("c.id, c.nomeMercado, c.nome, go.id, go.nome, es.id, es.nome, fsh.valor, fsh.tipo, fsh.quantidade, ifsh.valor) ");
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

	public Collection<SituacaoColaborador> getPromocoes(Long[] areaIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim, Long empresaId)
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
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		
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
	
	public List<SituacaoColaborador> getUltimasPromocoes(Long[] areaIds, Long[] estabelecimentosIds, Date data, Long empresaId)
	{
		
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoColaborador.class, "hc");
		ProjectionList pSub = Projections.projectionList().create();
		
		pSub.add(Projections.max("hc.data"));
		subQuery.setProjection(pSub);
		
		subQuery.add(Expression.eqProperty("hc.colaborador.id", "sc.colaborador.id"));
		subQuery.add(Expression.le("hc.data", data));
		subQuery.add(Expression.not(Expression.eq("hc.motivo", MotivoHistoricoColaborador.DISSIDIO)));
		subQuery.add(Expression.not(Expression.eq("hc.status", StatusRetornoAC.CANCELADO)));

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
		p.add(Projections.property("c.matricula"), "projectionColaboradorMatricula");
		criteria.setProjection(p);
		
		criteria.add(Subqueries.propertyLe("sc.data", subQuery));
		
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

	public void atualizarHistoricoAnterior(HistoricoColaborador historico)
	{
		HistoricoColaborador historicoAnterior = getHistoricoAnterior(historico);

		if (historicoAnterior!= null && historicoAnterior.getId()!=null)
		{
			String hql = "update HistoricoColaborador set historicoAnterior.id = :historicoAnteriorId where id = :historicoId";

			Query query = getSession().createQuery(hql);

			query.setLong("historicoAnteriorId", historicoAnterior.getId());
			query.setLong("historicoId", historico.getId());

			query.executeUpdate();
		}
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

	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

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
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo","ca", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.salario"), "salario");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.motivo"), "motivo");
		p.add(Projections.property("hc.gfip"), "gfip");
		p.add(Projections.property("hc.quantidadeIndice"), "quantidadeIndice");
		p.add(Projections.property("hc.tipoSalario"), "tipoSalario");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");
		p.add(Projections.property("fs.id"), "faixaSalarialId");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("ca.nomeMercado"), "cargoNomeMercado");
		p.add(Projections.property("hc.historicoAnterior.id"), "historicoAnteriorId");
		p.add(Projections.property("hc.funcao.id"), "funcaoId");
		p.add(Projections.property("hc.ambiente.id"), "ambienteId");
		p.add(Projections.property("e.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("ao.id"), "areaId");
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.property("hc.status"), "status");
		p.add(Projections.property("co.id"), "colaboradorId");
		p.add(Projections.property("co.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("co.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("co.naoIntegraAc"), "projectionColaboradorNaoIntegraAc");
		p.add(Projections.property("hc.reajusteColaborador.id"), "projectionReajusteColaboradorId");

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

	public void updateHistoricoAnterior(Long historicoColaboradorId)
	{
		String hql = "update HistoricoColaborador hc set hc.historicoAnterior.id = (select hc1.historicoAnterior.id from HistoricoColaborador hc1 where hc1.id=:historicoColaboradorId) where hc.historicoAnterior.id = :historicoColaboradorId";

		Query query = getSession().createQuery(hql);

		query.setLong("historicoColaboradorId", historicoColaboradorId);

		query.executeUpdate();
	}

	public Collection<HistoricoColaborador> findPendenciasByHistoricoColaborador(Long empresaId)
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
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("c.dataAdmissao"), "colaboradorDataAdmissao");
		p.add(Projections.property("ca.nome"), "cargoNome");
		p.add(Projections.property("ca.nomeMercado"), "cargoNomeMercado");

		criteria.setProjection(p);

		criteria.add(Expression.ne("hc.status", StatusRetornoAC.CONFIRMADO));
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
		p.add(Projections.property("hc.historicoAnterior.id"), "historicoAnteriorId");
		p.add(Projections.property("hc.funcao.id"), "funcaoId");
		p.add(Projections.property("hc.ambiente.id"), "ambienteId");
		p.add(Projections.property("hc.estabelecimento.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("hc.areaOrganizacional.id"), "areaId");
		p.add(Projections.property("hc.reajusteColaborador.id"), "projectionReajusteColaboradorId");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
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

	public Collection<HistoricoColaborador> findByCargoEstabelecimento(Date dataHistorico, Long[] cargoIds, Long[] estabelecimentoIds, Date dataConsulta, Long[] areaOrganizacionalIds, Date dataAtualizacao, Long empresaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new HistoricoColaborador(hc.id, co.id, co.nome, co.nomeComercial, co.dataAdmissao, co.codigoAC, c.id, c.nome, fs.id, fs.nome, e.id, e.nome, emp.id, emp.nome, hc.salario, emp.acIntegra, hc.tipoSalario, hc.quantidadeIndice, i, fs, fsh, ih, ifs, ifsh) ");
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
		
		hql.append("where co.desligado = :desligado ");
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

		if(empresaId != null )
			hql.append("and co.empresa.id = :empresaId ");

		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("order by emp.nome, c.nome, fs.nome, co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", dataHistorico);
		query.setInteger("statusFaixaSalarial", StatusRetornoAC.CANCELADO);
		
		if(dataConsulta != null)
			query.setDate("dataConsulta", dataConsulta);
		
		query.setBoolean("desligado", false);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if(areaOrganizacionalIds != null && areaOrganizacionalIds.length>0)
			query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, Hibernate.LONG);
		
		if(dataAtualizacao != null)
			query.setDate("dataAtualizacao", dataAtualizacao);

		if(empresaId != null)
			query.setLong("empresaId", empresaId);

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
			.append("	and hc.data = (select max(hc2.data) from HistoricoColaborador as hc2 where hc2.colaborador.id = c.id ")
			.append("					and hc2.data <= :votacaoFim and hc2.estabelecimento.id = :estabelecimento ")
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
	public Collection<HistoricoColaborador> findByPeriodo(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao) {
		
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new HistoricoColaborador(hc.id, hc.data, hc.motivo, co.id, co.nome, cg.nome, fs.nome, es.id, es.nome, ao.id, ao.nome, am.id, am.nome, hc.tipoSalario, hc.salario, " +
				"hc.quantidadeIndice, hcih.valor, fsh.tipo, fsh.valor, fsh.quantidade, fshih.valor, hci.nome) ");
		
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join ao.areaMae as am ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");
		
		hql.append("left join hc.indice as hci ");
		hql.append("left join hci.indiceHistoricos as hcih with hcih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = hci.id and ih2.data <= :data) ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");
		
		hql.append("where ");
		hql.append("co.empresa.id = :empresaId ");
		hql.append("and	hc.data between :dataIni and :dataFim ");
		hql.append("and	es.id in (:estabelecimentosIds) ");
		if (areasIds != null && areasIds.length > 0)
			hql.append("and	ao.id in (:areasIds) ");
		
		if (origemSituacao.equals("RH"))
			hql.append("and hc.motivo != :motivo ");
		else if (origemSituacao.equals("AC"))
			hql.append("and hc.motivo = :motivo ");

		hql.append("order by es.nome,ao.nome,co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("data", new Date());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		if (areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if (StringUtils.isNotBlank(origemSituacao) && !origemSituacao.equals("T"))
			query.setString("motivo", MotivoHistoricoColaborador.IMPORTADO);
		
		return query.list();
	}
	
	/**
	 * consulta usada a princípio para trazer função e ambiente, pode ser expandida 
	 */
	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
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
		p.add(Projections.property("fun.id"), "funcaoId");
		p.add(Projections.property("fun.nome"), "funcaoNome");
		p.add(Projections.property("amb.id"), "ambienteId");
		p.add(Projections.property("amb.nome"), "ambienteNome");
		
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

	public Collection<HistoricoColaborador> findSemDissidioByDataPercentual(Date dataBase, Double percentualDissidio, Long empresaId) 
	{	
		StringBuilder hql = new StringBuilder();

		montaSelectConstrutor(hql);
		montaFromAndJoin(hql);

		hql.append("where hc.data >= :data ");
		hql.append("and co.empresa.id = :empresaId ");
		hql.append("and co.dataDesligamento is null ");
		hql.append("order by co.nome, hc.data");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("data", dataBase);
		query.setLong("empresaId", empresaId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);

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

	public void setaContratadoNoPrimeiroHistorico(Long colaboradorId) 
	{
		Query query = getSession().createQuery("update HistoricoColaborador set motivo = :contratado where data=(select min(data) from HistoricoColaborador where colaborador.id=:colaboradorId) " +
											  " and motivo <> :contratado and colaborador.id=:colaboradorId");

		query.setString("contratado", MotivoHistoricoColaborador.CONTRATADO);
		query.setLong("colaboradorId", colaboradorId);
		
		query.executeUpdate();
	}	
}