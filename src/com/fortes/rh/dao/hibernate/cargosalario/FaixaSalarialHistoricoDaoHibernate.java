package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;

@SuppressWarnings("unchecked")
public class FaixaSalarialHistoricoDaoHibernate extends GenericDaoHibernate<FaixaSalarialHistorico> implements FaixaSalarialHistoricoDao
{
	public Collection<FaixaSalarialHistorico> findAllSelect(Long faixaSalarialId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new FaixaSalarialHistorico(fsh.id, fsh.data, fsh.status, fsh.tipo, fsh.valor, fsh.quantidade, i.id, i.nome, ih.valor, ih.data, ihatual.valor, fs.codigoAC) ");
		hql.append("from FaixaSalarialHistorico fsh ");
		hql.append("left join fsh.faixaSalarial fs ");
		hql.append("left join fsh.indice i ");
		hql.append("left join i.indiceHistoricos ih with ih.data = (select max(ih2.data) ");
		hql.append("                                                from IndiceHistorico ih2 ");
		hql.append("                                                where ih2.indice.id = i.id ");
		hql.append("                                                      and ih2.data <= fsh.data) ");
		hql.append("left join i.indiceHistoricos ihatual with ihatual.data = (select max(ih3.data) ");
		hql.append("                                                          from IndiceHistorico ih3 ");
		hql.append("                                                          where ih3.indice.id = i.id ");
		hql.append("                                                                and ih3.data <= :data) ");

		hql.append("where fs.id = :faixaId ");
		hql.append("order by fsh.data asc");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("faixaId", faixaSalarialId);
		query.setDate("data", new Date());

		return query.list();
	}
	
	public Collection<FaixaSalarialHistorico> findByEmpresaIdAndStatus(Long empresaId, Integer status)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new FaixaSalarialHistorico(fsh.id, fsh.data, fsh.tipo, fsh.valor, fsh.quantidade, i.codigoAC, fs.codigoAC) ");
		hql.append("from FaixaSalarialHistorico fsh ");
		hql.append("inner join fsh.faixaSalarial fs ");
		hql.append("inner join fs.cargo c ");
		hql.append("left join fsh.indice i ");
		hql.append("left join i.indiceHistoricos ih with ih.data = (select max(ih2.data) ");
		hql.append("                                                from IndiceHistorico ih2 ");
		hql.append("                                                where ih2.indice.id = i.id) ");
		
		hql.append("where c.empresa.id = :empresaId ");
		hql.append("and fsh.status = :status ");
		hql.append("order by fsh.data asc ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setInteger("status", status);

		return query.list();
	}
	
	public Collection<FaixaSalarialHistoricoVO> findAllComHistoricoIndice(Long faixaSalarialId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select faixas.* ");
		hql.append("from ");
		hql.append("	(select ");
		hql.append("		fsh.id, ");
		hql.append("		fsh.tipo, ");
		hql.append("   		fsh.quantidade, ");
		hql.append("   		fsh.status, ");
		hql.append("		fsh.data as datafaixa, ");
		hql.append("   		fsh.valor as valorfaixa, ");
		hql.append("		i.nome as nomeindice, ");
		hql.append("        ih.data as dataindice, ");
		hql.append("        (fsh.quantidade * ih.valor) as valorindice, ");
		hql.append("        (select data from faixasalarialhistorico where data > fsh.data and faixasalarial_id = fsh.faixasalarial_id order by data limit 1) as proximadatafaixa, ");
		hql.append("		(select data from indicehistorico where data <= fsh.data and indice_id = fsh.indice_id order by data desc limit 1) as dataindiceanterior ");
		hql.append("    from faixasalarialhistorico fsh ");
		hql.append("    	left join indicehistorico ih on fsh.indice_id = ih.indice_id ");
		hql.append("    	left join indice i on fsh.indice_id = i.id ");
		hql.append("    where fsh.faixasalarial_id = :faixaId ");
		hql.append("    order by fsh.data, ih.data) as faixas ");
		hql.append("where faixas.dataindice is null or (faixas.dataindice >= faixas.dataindiceanterior ");
		hql.append("and ((faixas.dataindice < faixas.proximadatafaixa) or faixas.proximadatafaixa is null)) ");
		
		Query query = getSession().createSQLQuery(hql.toString());
		query.setLong("faixaId", faixaSalarialId);

		List<Object[]> objetos = query.list();
		Collection<FaixaSalarialHistoricoVO> faixas = new ArrayList<FaixaSalarialHistoricoVO>();
		
		FaixaSalarialHistoricoVO vo;
		Object[] array;
		
		for (Iterator<Object[]> it = objetos.iterator(); it.hasNext();)
		{
			array = it.next();
			vo = new FaixaSalarialHistoricoVO();
			vo.setId(Long.parseLong(array[0].toString()));
			vo.setTipo((Integer) array[1]);
			vo.setQtdeIndice((Double) array[2]);
			vo.setStatus((Integer) array[3]);
			vo.setDataFaixa(new Date(((java.sql.Date)array[4]).getTime()));
			vo.setValorFaixa((Double) array[5]);
			vo.setNomeIndice((String) array[6]);
			
			if (array[7] != null)
				vo.setDataIndice(new Date(((java.sql.Date) array[7]).getTime()));
			
			vo.setValorIndice((Double) array[8]);
			
			if (array[9] != null)
				vo.setProximaDataFaixa(new Date(((java.sql.Date) array[9]).getTime()));
			
			if (array[10] != null)
				vo.setDataIndiceAnterior(new Date(((java.sql.Date) array[10]).getTime()));
			
			faixas.add(vo);
		}
		
		return faixas;
	}

	public FaixaSalarialHistorico findByIdProjection(Long faixaSalarialHistoricoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fsh");
		criteria.createCriteria("fsh.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("fsh.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fsh.id"), "id");
		p.add(Projections.property("fsh.data"), "data");
		p.add(Projections.property("fsh.tipo"), "tipo");
		p.add(Projections.property("fsh.valor"), "valor");
		p.add(Projections.property("fsh.quantidade"), "quantidade");
		p.add(Projections.property("fsh.status"), "status");
		p.add(Projections.property("fsh.reajusteFaixaSalarial"), "reajusteFaixaSalarial");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("fsh.id", faixaSalarialHistoricoId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (FaixaSalarialHistorico) criteria.uniqueResult();
	}

	public boolean verifyData(Long faixaSalarialHistoricoId, Date data, Long faixaSalarialId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		criteria.createCriteria("f.faixaSalarial", "fs", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");

		criteria.setProjection(p);

		if(faixaSalarialHistoricoId != null)
			criteria.add(Expression.not(Expression.eq("f.id", faixaSalarialHistoricoId)));

		criteria.add(Expression.eq("f.data", data));
		criteria.add(Expression.eq("fs.id", faixaSalarialId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		Collection<FaixaSalarialHistorico> lista = criteria.list();

		return lista.size() > 0;
	}

	public FaixaSalarialHistorico findByFaixaSalarialId(Long faixaSalarialId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		criteria.createCriteria("f.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("f.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.data"), "data");
		p.add(Projections.property("f.tipo"), "tipo");
		p.add(Projections.property("f.valor"), "valor");
		p.add(Projections.property("f.quantidade"), "quantidade");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("c.id"), "projectionCargoId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("fs.id", faixaSalarialId));
		criteria.add(Expression.le("f.data", new Date()));

		criteria.addOrder(Order.desc("f.data"));

		criteria.setMaxResults(1);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (FaixaSalarialHistorico) criteria.uniqueResult();
	}
	
	public Collection<FaixaSalarialHistorico> findHistoricosByFaixaSalarialId(Long faixaSalarialId, Integer statusRetornoAC)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		criteria.createCriteria("f.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("f.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.data"), "data");
		p.add(Projections.property("f.tipo"), "tipo");
		p.add(Projections.property("f.valor"), "valor");
		p.add(Projections.property("f.quantidade"), "quantidade");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("fs.nomeACPessoal"), "projectionFaixaSalarialNomeACPessoal");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("c.id"), "projectionCargoId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("fs.id", faixaSalarialId));
		criteria.add(Expression.le("f.data", new Date()));
		
		if(statusRetornoAC != null)
			criteria.add(Expression.eq("f.status",statusRetornoAC));

		criteria.addOrder(Order.desc("f.data"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<FaixaSalarialHistorico> findByPeriodo(Long faixaSalarialId, Date dataInicio, Date dataProxima, Date dataDesligamento)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new FaixaSalarialHistorico(fsh.id, fsh.data, fsh.status, fsh.tipo, fsh.valor, fsh.quantidade, i.id, i.nome, ih.valor, ih.data) ");
		hql.append("from FaixaSalarialHistorico fsh ");
		hql.append("left join fsh.faixaSalarial fs ");
		hql.append("left join fsh.indice i ");
		hql.append("left join i.indiceHistoricos ih with ih.data = (select max(ih2.data) ");
		hql.append("                                                from IndiceHistorico ih2 ");
		hql.append("                                                where ih2.indice.id = i.id ");
		hql.append("                                                      and ih2.data <= fsh.data) ");
		hql.append("where fs.id = :faixaId ");
		hql.append("and fsh.data > :dataInicio ");
		hql.append("and fsh.data <= :proxima ");
		if(dataDesligamento != null)
			hql.append("and fsh.data < :dataDesligamento ");
		hql.append("order by fsh.data ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("faixaId", faixaSalarialId);
		query.setDate("dataInicio", dataInicio);
		query.setDate("proxima", dataProxima);
		if(dataDesligamento != null)
			query.setDate("dataDesligamento", dataDesligamento);

		return query.list();
	}
	
	public Collection<FaixaSalarialHistorico> findByGrupoCargoAreaData(Collection<Long> grupoOcupacionals, Collection<Long> cargoIds, Collection<Long> areaIds, Date data, boolean ordemDataDescendente, Long empresaId, Boolean cargoAtivo)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct new FaixaSalarialHistorico(hf.id, hf.data, hf.tipo, hf.valor, hf.quantidade, i.id, i.nome, hi.valor, hi.data, c.id, c.nome, f.id, f.nome) ");
		hql.append("from FaixaSalarialHistorico hf ");
		hql.append("left join hf.faixaSalarial f ");
		hql.append("left join f.cargo c ");
		hql.append("left join c.areasOrganizacionais a ");
		hql.append("left join c.grupoOcupacional go ");
		hql.append("left join hf.indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                             from IndiceHistorico hi2 ");
		hql.append("                                             where hi2.indice.id = i.id ");
		hql.append("                                              and hi2.data <= hf.data) ");
		hql.append("where true = true ");
		
		if(data != null) {
			hql.append("and hf.data = (select max(fsh2.data)");
			hql.append("               from FaixaSalarialHistorico fsh2 ");
			hql.append("               where fsh2.faixaSalarial.id = f.id ");
			hql.append("               and fsh2.data <= :data) ");
		}

		if(grupoOcupacionals != null && !grupoOcupacionals.isEmpty())
			hql.append("and go.id in (:grupoOcupacionals) ");
		
		if(cargoIds != null && !cargoIds.isEmpty())
			hql.append("and c.id in (:cargoIds) ");

		if(cargoAtivo != null)
			hql.append("and c.ativo = :cargoAtivo ");
		
		if(areaIds != null && !areaIds.isEmpty())
			hql.append("and a.id in (:areaIds) ");
		
		if(empresaId != null)
			hql.append("and c.empresa.id = :empresaId ");

		hql.append("order by c.nome, f.nome, hf.data ");
		
		if(ordemDataDescendente)
			hql.append("desc ");

		Query query = getSession().createQuery(hql.toString());

		if(data != null)
			query.setParameter("data", data);

		if(grupoOcupacionals != null && !grupoOcupacionals.isEmpty())
			query.setParameterList("grupoOcupacionals", grupoOcupacionals, Hibernate.LONG);
		
		if(cargoIds != null && !cargoIds.isEmpty())
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		if(cargoAtivo != null)
			query.setBoolean("cargoAtivo", cargoAtivo);
		
		if(areaIds != null && !areaIds.isEmpty())
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if(empresaId != null)
			query.setLong("empresaId", empresaId);

		return query.list();
	}

	public boolean setStatus(Long faixaSalarialHistoricoId, boolean aprovado)
	{
		String hql = "update FaixaSalarialHistorico set status = :aprovado where id = :faixaSalarialHistoricoId";

		int status = StatusRetornoAC.CANCELADO;
		if(aprovado)
			status = StatusRetornoAC.CONFIRMADO;

		Query query = getSession().createQuery(hql);
		query.setInteger("aprovado", status);
		query.setLong("faixaSalarialHistoricoId", faixaSalarialHistoricoId);

		int result = query.executeUpdate();

		return result == 1;
	}

	public void removeByFaixas(Long[] faixaSalarialIds)
	{
		String hql = "delete FaixaSalarialHistorico where faixaSalarial.id in (:faixaSalarialIds)";

		Query query = getSession().createQuery(hql);
		query.setParameterList("faixaSalarialIds", faixaSalarialIds, Hibernate.LONG);

		query.executeUpdate();
	}

	public Collection<FaixaSalarialHistorico> findPendenciasByFaixaSalarialHistorico(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");
		criteria.createCriteria("f.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");
		p.add(Projections.property("f.status"), "status");

		criteria.setProjection(p);

		criteria.add(Expression.not(Expression.eq("f.status", 1)));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Long findIdByDataFaixa(FaixaSalarialHistorico faixaSalarialHistorico)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.faixaSalarial.id", faixaSalarialHistorico.getFaixaSalarial().getId()));
		criteria.add(Expression.eq("f.data", faixaSalarialHistorico.getData()));

		return (Long) criteria.uniqueResult();
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception 
	{
		if(faixaIds != null && faixaIds.length > 0)
		{
			String hql = "delete FaixaSalarialHistorico where faixaSalarial.id in (:faixaIds)";
			Query query = getSession().createQuery(hql);
	
			query.setParameterList("faixaIds", faixaIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}

	public Collection<FaixaSalarialHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fsh");
		criteria.createCriteria("fsh.reajusteFaixaSalarial", "rfs", Criteria.INNER_JOIN);
		criteria.createCriteria("rfs.tabelaReajusteColaborador", "trs", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fsh.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("trs.id", tabelaReajusteColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public ReajusteFaixaSalarial findReajusteFaixaSalarial(Date faixasalarialHistoricoData, Long faixaSalarialId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fsh");
		criteria.createCriteria("fsh.reajusteFaixaSalarial", "rfs", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fsh.reajusteFaixaSalarial"), "reajusteFaixaSalarial");

		criteria.setProjection(p);

		criteria.add(Expression.eq("fsh.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.eq("fsh.data", faixasalarialHistoricoData));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (ReajusteFaixaSalarial) criteria.uniqueResult();
	}

	public FaixaSalarialHistorico findHistoricoAtual(Long faixaSalarialId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new FaixaSalarialHistorico(fsh.id, fsh.data, fsh.tipo, fsh.valor, fsh.quantidade, fsh.indice.id, fsh.faixaSalarial.id) ");
		hql.append("from FaixaSalarialHistorico as fsh ");
		hql.append("where fsh.faixaSalarial.id = :faixaId ");
		hql.append("and fsh.status = :status ");
		hql.append("and fsh.data = (select max(fsh2.data) ");
		hql.append("				from FaixaSalarialHistorico as fsh2 ");
		hql.append("				where fsh2.faixaSalarial.id = fsh.faixaSalarial.id ");
		hql.append("				and fsh2.data <= :hoje and fsh2.status = :status ) ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("faixaId", faixaSalarialId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (FaixaSalarialHistorico) query.uniqueResult();
	}

	public Collection<FaixaSalarialHistorico> findByTabelaReajusteIdData(Long tabelaReajusteId, Date data)
	{
        DetachedCriteria subQuery = DetachedCriteria.forClass(ReajusteFaixaSalarial.class, "rf");
        ProjectionList pSub = Projections.projectionList().create();

        pSub.add(Projections.property("rf.faixaSalarial.id"), "faixaSalarialId");
        subQuery.setProjection(pSub);

        subQuery.add(Expression.eq("rf.tabelaReajusteColaborador.id", tabelaReajusteId));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "hfs");
		criteria.createCriteria("hfs.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hfs.id"), "id");
		p.add(Projections.property("hfs.data"), "data");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("c.nome"), "projectionCargoNome");

		criteria.setProjection(p);

		criteria.add(Subqueries.propertyIn("fs.id", subQuery));
		criteria.add(Expression.eq("hfs.data", data));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public boolean existeHistoricoPorIndice(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fsh");
		criteria.createCriteria("fsh.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "c");

		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("fsh.tipo", TipoAplicacaoIndice.INDICE));
		
		return ((Integer) criteria.uniqueResult()) > 0;
	}

	public boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Date dataSegundoHistoricoIndice, Long indiceId)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarialHistorico.class, "fsh");
		criteria.setProjection(Projections.count("data"));
				
		criteria.add(Expression.in("fsh.status",new Integer[] {StatusRetornoAC.AGUARDANDO, StatusRetornoAC.CONFIRMADO}));
		criteria.add(Expression.eq("fsh.tipo", TipoAplicacaoIndice.INDICE));
		criteria.add(Expression.eq("fsh.indice.id", indiceId));

		criteria.add(Expression.ge("fsh.data", dataHistoricoExcluir));
		
		if(dataSegundoHistoricoIndice != null)
			criteria.add(Expression.lt("fsh.data", dataSegundoHistoricoIndice));

		return ((Integer) criteria.uniqueResult()) > 0;	
	}

	public boolean existeHistoricoConfirmadoByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId) {
		Criteria criteria = getSession().createCriteria(FaixaSalarialHistorico.class, "fsh");
        criteria.createCriteria("fsh.reajusteFaixaSalarial", "rfs");
        criteria.setProjection(Projections.count("id"));
        criteria.add(Expression.eq("fsh.status",StatusRetornoAC.CONFIRMADO));
        criteria.add(Expression.eq("rfs.tabelaReajusteColaborador.id", tabelaReajusteColaboradorId));
        return ((Integer) criteria.uniqueResult()) > 0;
	}
}