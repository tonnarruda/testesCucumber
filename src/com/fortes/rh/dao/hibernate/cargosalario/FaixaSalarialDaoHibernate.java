package com.fortes.rh.dao.hibernate.cargosalario;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("unchecked")
public class FaixaSalarialDaoHibernate extends GenericDaoHibernate<FaixaSalarial> implements FaixaSalarialDao
{
	private static final int CARGO = 1;
	private static final int FAIXASALARIAL = 2;

	@Override
	public Collection<FaixaSalarial> findAll()
	{
		throw new NoSuchMethodError("Deve considerar a empresa na listagem das Faixas Salariais.");
	}

	@Override
	public Collection<FaixaSalarial> findAll(String[] orderBy)
	{
		throw new NoSuchMethodError("Deve considerar a empresa na listagem das Faixas Salariais.");
	}

	public FaixaSalarial findCodigoACById(Long id)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.codigoAC"), "codigoAC");
		p.add(Projections.property("f.nomeACPessoal"), "nomeACPessoal");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		criteria.setMaxResults(1);

		return (FaixaSalarial) criteria.uniqueResult();

	}

	public Collection<FaixaSalarial> findAllSelectByCargo(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("fs.nomeACPessoal"), "nomeACPessoal");
		p.add(Projections.property("fs.codigoAC"), "codigoAC");
		p.add(Projections.property("c.nome"), "nomeCargo");
		p.add(Projections.property("c.ativo"), "ativoCargo");
		criteria.setProjection(p);

		if (empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list();
	}

	public Collection<FaixaSalarial> findDistinctDescricao(Long[] empresaIds){
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.nome")), "nomeCargo");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("c.ativo"), "ativoCargo");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		
		return criteria.list();
	}
	
	
	public FaixaSalarial findByFaixaSalarialId(Long faixaSalarialId)
	{
		Criteria criteria = getCriteriaFaixaSalarial(faixaSalarialId, FAIXASALARIAL);
		criteria.setMaxResults(1);

		return (FaixaSalarial) criteria.uniqueResult();
	}

	public Collection<FaixaSalarial> findFaixaSalarialByCargo(Long cargoId)
	{
		Criteria criteria = getCriteriaFaixaSalarial(cargoId, CARGO);

		return criteria.list();
	}

	private Criteria getCriteriaFaixaSalarial(Long id, int origem)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "f");
		criteria.createCriteria("f.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");
		p.add(Projections.property("f.nomeACPessoal"), "nomeACPessoal");
		p.add(Projections.property("f.codigoAC"), "codigoAC");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		if(origem == CARGO)
		{
			criteria.add(Expression.eq("c.id", id));
			criteria.addOrder(Order.asc("c.nome"));
		}
		else if (origem == FAIXASALARIAL)
		{
			criteria.add(Expression.eq("f.id", id));
			criteria.addOrder(Order.asc("f.nome"));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria;
	}

	public FaixaSalarial findHistoricoAtual(Long faixaSalarialId, Date dataHistorico)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new FaixaSalarial(fs.id, hf.id, hf.data, hf.tipo, hf.valor, hf.quantidade, i.id, hi.valor, hi.data) ");
		hql.append("from FaixaSalarial fs ");
		hql.append("left join fs.faixaSalarialHistoricos hf with hf.data = (select max(hf2.data) ");
		hql.append("                                            from FaixaSalarialHistorico hf2 ");
		hql.append("                                           where hf2.faixaSalarial.id = fs.id ");
		hql.append("                                            and hf2.data <= :data ");
		hql.append("											and	hf2.status != :status) ");
		hql.append("left join hf.indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :data) ");
		hql.append("where fs.id = :faixaSalarialId ");
		hql.append("order by hf.data ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("data", dataHistorico);
		query.setLong("faixaSalarialId", faixaSalarialId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);

		return (FaixaSalarial) query.uniqueResult();
	}
	
	public Collection<FaixaSalarial> findComHistoricoAtual(Long[] faixasSalariaisIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new FaixaSalarial(fs.id, hf.id, hf.data, hf.tipo, hf.valor, hf.quantidade, i.id, hi.valor, hi.data) ");
		hql.append("from FaixaSalarial fs ");
		hql.append("left join fs.faixaSalarialHistoricos hf with hf.data = (select max(hf2.data) ");
		hql.append("                                            from FaixaSalarialHistorico hf2 ");
		hql.append("                                           where hf2.faixaSalarial.id = fs.id ");
		hql.append("                                            and hf2.data <= :hoje ");
		hql.append("											and	hf2.status = :status) ");
		hql.append("left join hf.indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :hoje) ");
		hql.append("where fs.id in (:faixasSalariaisIds) ");
		hql.append("order by fs.id ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setDate("hoje", new Date());
		query.setParameterList("faixasSalariaisIds", faixasSalariaisIds, Hibernate.LONG);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}
	
	public Collection<FaixaSalarial> findComHistoricoAtualByEmpresa(Long empresaId, boolean semCodigoAC)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new FaixaSalarial(c.id, c.nome, fs.id, fs.nome, hf.id, hf.data, hf.valor, hf.tipo) ");
		hql.append("from FaixaSalarial fs ");
		hql.append("inner join fs.cargo c ");
		hql.append("left join fs.faixaSalarialHistoricos hf with hf.data = (select max(hf2.data) ");
		hql.append("                                            from FaixaSalarialHistorico hf2 ");
		hql.append("                                           where hf2.faixaSalarial.id = fs.id ");
		hql.append("											and	hf2.status = :status) ");
		hql.append("where c.empresa.id = :empresaId ");
		
		if(semCodigoAC)
			hql.append("and fs.codigoAC is null ");
		
		hql.append("order by fs.id ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setLong("empresaId", empresaId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}

	public Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo, Long faixaInativaId)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresa.getId()));

		if(ativo != null)
		{
			if(faixaInativaId == null)
				criteria.add(Expression.eq("c.ativo", ativo));
			else
				criteria.add(Expression.or(Expression.eq("c.ativo", ativo), Expression.eq("fs.id", faixaInativaId)));
		}

		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list();
	}

	public void updateCodigoAC(String codigoAC, Long faixaSalarialId)
	{
		String hql = "update FaixaSalarial set codigoAC = :codigoAC where id = :faixaSalarialId";

		Query query = getSession().createQuery(hql);
		query.setString("codigoAC", codigoAC);
		query.setLong("faixaSalarialId", faixaSalarialId);

		query.executeUpdate();
	}

	public boolean verifyExistsNomeByCargo(Long cargoId, String faixaNome)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("fs.cargo.id", cargoId));
		criteria.add(Expression.ilike("fs.nome",faixaNome));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list().size() > 0;
	}

	public FaixaSalarial findFaixaSalarialByCodigoAc(String faixaCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "f");
		criteria.createCriteria("f.cargo", "car", Criteria.LEFT_JOIN);
		criteria.createCriteria("car.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("car.id"), "projectionCargoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.codigoAC", faixaCodigoAC));
		criteria.add(Expression.eq("emp.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("emp.grupoAC", grupoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		criteria.setMaxResults(1);

		return (FaixaSalarial) criteria.uniqueResult();
	}

	public void updateNomeECargo(Long faixaSalarialId, Long cargoId, String faixaNome)
	{
		String hql = "update FaixaSalarial set cargo.id = :cargoId, nome = :faixaNome where id = :faixaSalarialId";

		Query q = getSession().createQuery(hql);

		q.setLong("cargoId", cargoId);
		q.setString("faixaNome", faixaNome);
		q.setLong("faixaSalarialId", faixaSalarialId);

		q.executeUpdate();
	}

	public Collection<FaixaSalarial> findByCargo(Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("c.nome"), "nomeCargo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", cargoId));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		return criteria.list();
	}
	
	public Collection<FaixaSalarial> findByCargoComCompetencia(Long cargoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select fs.id as faixaId, fs.nome as faixaNome, ");
		sql.append("   case hf.tipo ");
		sql.append("      when "+TipoAplicacaoIndice.VALOR+" then hf.valor ");
		sql.append("      when "+TipoAplicacaoIndice.INDICE+" then hf.quantidade*hi.valor ");
		sql.append("      else 0.0 ");
		sql.append("   end as valor, ");
		sql.append("   comp.nome as compNome, nc.descricao, cncf.data ");
		sql.append("from faixasalarial fs "); 
		sql.append("left join faixaSalarialHistorico hf on hf.faixaSalarial_id = fs.id and hf.data = (select max(hf2.data) ");
		sql.append("                                                                                         from FaixaSalarialHistorico hf2 ");
		sql.append("                                                                                         where hf2.faixaSalarial_id = fs.id ");
		sql.append("                                                                                         and hf2.data <= current_date ");
		sql.append("																					     and hf2.status = 1) ");
		sql.append("left join indice i on i.id = hf.indice_id ");
		sql.append("left join indiceHistorico hi on hi.indice_id = i.id and hi.data = (select max(hi2.data) ");
		sql.append("                                                                         from IndiceHistorico hi2 ");
		sql.append("                                                                         where hi2.indice_id = i.id ");
		sql.append("                                                                         and hi2.data <= current_date) ");
		sql.append("left join configuracaonivelcompetenciafaixasalarial cncf on cncf.faixasalarial_id = fs.id and cncf.data = (select max(cncf2.data) ");
		sql.append("                                                                                         from configuracaonivelcompetenciafaixasalarial cncf2 ");
		sql.append("                                                                                         where cncf.faixasalarial_id = fs.id ");
		sql.append("                                                                                         and cncf.data <= current_date ) ");
		sql.append("   left join configuracaonivelcompetencia cnc on cnc.configuracaonivelcompetenciafaixasalarial_id = cncf.id"); 
		sql.append("   left join competencia comp on comp.id = cnc.competencia_id and comp.tipo = cnc.tipocompetencia "); 
		sql.append("   left join nivelcompetencia nc on nc.id = cnc.nivelcompetencia_id "); 
		sql.append("where  fs.cargo_id = :cargoId "); 
		sql.append("   and cnc.candidato_id is null "); 
		sql.append("   and cnc.configuracaoNivelCompetenciaColaborador_id is null "); 
		sql.append("order by fs.nome, comp.nome "); 
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());

		query.setLong("cargoId", cargoId);
		
		Collection<Object[]> faixasalarialObjects = query.list();
		
		Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
		
		Map<Long, FaixaSalarial> mapFaixa = new HashMap<Long, FaixaSalarial>();
		
		for (Object[] faixa : faixasalarialObjects) {
			
			Long id = ((BigInteger) faixa[0]).longValue();
			
			FaixaSalarial faixaSalarial;
			// Define a faixa salarial que tera uma competencia inserida, numa nova ou numa que ja havia sido criada.   
			if (mapFaixa.get(id) != null){
				// Faixa que ja tenha competencia inserida.
				faixaSalarial = mapFaixa.get(id); 
			} else {
				// Nova faixa
				faixaSalarial = new FaixaSalarial(); 
				faixaSalarial.setId(((BigInteger)faixa[0]).longValue());
				faixaSalarial.setNome((String)faixa[1]);
				faixaSalarial.setHistoricoFaixaValor((Double)faixa[2]);
				faixaSalarial.setCompetencias(new ArrayList<Competencia>());
				
				// Insere uma faixa salarial num map para que esta nao seja criada novamente.
				mapFaixa.put(faixaSalarial.getId(), faixaSalarial);
				
				// Insere faixa salarial na collection de retorno(inserida somente uma vez ao ser criada)
				faixaSalarials.add(faixaSalarial);
			}
			
			// Adiciona competência somente quando a mesma não for nula
			if ((String)faixa[3] != null) {
				NivelCompetencia nivelCompetencia = new NivelCompetencia();
				Competencia comp = new Competencia();
				comp.setNome((String)faixa[3]);
				nivelCompetencia.setDescricao((String)faixa[4]);
				comp.setNivelCompetencia(nivelCompetencia);

				faixaSalarial.getCompetencias().add(comp);
			}
			if ((Date)faixa[5] != null)
				faixaSalarial.setDataConfiguracaoNivelCompetenciaFaixaSalariall((Date)faixa[5]);
		}
		
		return faixaSalarials;
		
	}
	
	public Collection<FaixaSalarial> findByCargos(Long[] cargosIds) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new FaixaSalarial(fs.id, fs.nome, c.id, c.nome, hf.id, hf.data, hf.tipo, hf.valor, hf.quantidade, i.id, hi.valor, hi.data) ");
		hql.append("from FaixaSalarial fs ");
		hql.append("inner join fs.cargo c ");
		hql.append("left join fs.faixaSalarialHistoricos hf with hf.data = (select max(hf2.data) ");
		hql.append("                                            from FaixaSalarialHistorico hf2 ");
		hql.append("                                           where hf2.faixaSalarial.id = fs.id ");
		hql.append("                                            and hf2.data <= :hoje ");
		hql.append("											and	hf2.status = :status) ");
		hql.append("left join hf.indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :hoje) ");
		hql.append("where c.id in (:cargosIds) ");
		hql.append("order by c.nome, fs.nome ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public void updateAC(TCargo tCargo)
	{
		String hql = "update FaixaSalarial set nome = :faixaNome, nomeACPessoal = :faixaNomeAC where id = :id";

		Query q = getSession().createQuery(hql);

		q.setString("faixaNome", tCargo.getDescricao());
		q.setString("faixaNomeAC", tCargo.getDescricaoACPessoal());
		q.setLong("id", tCargo.getId());//tem que ser por ID, ta correto(CUIDADO: caso mude tem que verificar o grupoAC)

		q.executeUpdate();		
	}

	public TCargo[] getFaixasAC() 
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("c.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("fs.codigoAC"), "codigoAC");
		p.add(Projections.property("e.codigoAC"), "empresaCodigoAC");
		p.add(Projections.property("e.grupoAC"), "empresaGrupoAC");
		criteria.setProjection(p);

		criteria.add(Expression.ne("e.codigoAC", ""));
		criteria.add(Expression.ne("fs.codigoAC", ""));
		
		criteria.addOrder(Order.asc("e.codigoAC"));
		criteria.addOrder(Order.asc("fs.codigoAC"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));

		Collection<FaixaSalarial> faixas = criteria.list();
		TCargo[] tCargos = new TCargo[faixas.size()];
		int i = 0;

		for (FaixaSalarial faixa : faixas) 
			tCargos[i++] = new TCargo(faixa.getEmpresaCodigoAC(), faixa.getEmpresaGrupoAC(), faixa.getCodigoAC(), faixa.getNome());
		
		return tCargos;
	}

	public Collection<FaixaSalarial> findSemCodigoAC(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("c.empresa", "e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("fs.id"), "id");
		p.add(Projections.property("fs.nome"), "nome");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("c.nome"), "nomeCargo");
		

		criteria.setProjection(p);
		
		criteria.add(Expression.or(Expression.isNull("fs.codigoAC"), Expression.eq("fs.codigoAC","")));
		
		if(empresaId != null)
			criteria.add(Expression.eq("e.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("fs.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	
	}

	public String findCodigoACDuplicado(Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select fs.codigoAC from FaixaSalarial as fs "); 
		hql.append("inner join fs.cargo as c ");
		hql.append("where c.empresa.id = :empresaId and fs.codigoAC is not null and fs.codigoAC != '' ");
		hql.append("group by fs.codigoAC ");
		hql.append("having count(*) > 1 ");	
		hql.append("order by fs.codigoAC ");
	
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);

		return  StringUtil.converteCollectionToString(query.list());
	}
}