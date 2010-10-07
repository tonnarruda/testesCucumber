package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;

@SuppressWarnings("unchecked")
public class ColaboradorOcorrenciaDaoHibernate extends GenericDaoHibernate<ColaboradorOcorrencia> implements ColaboradorOcorrenciaDao
{
	public Collection<ColaboradorOcorrencia> findByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("co.ocorrencia","o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("c.nomeComercial"),"colaboradorNomeComercial");
		p.add(Projections.property("o.descricao"), "ocorrenciaDescricao");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.dataFim"), "dataFim");
		p.add(Projections.property("co.observacao"), "observacao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("co.colaborador.id", colaboradorId));
		criteria.addOrder(Order.asc("co.dataIni"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		return criteria.list();
	}

	//TODO Milosa, falta pegar o historico atual
	public Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","o");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("c.historicoColaboradors","hc", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento","e", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional","a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("c.nomeComercial"),"colaboradorNomeComercial");
		p.add(Projections.property("o.descricao"), "ocorrenciaDescricao");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.dataFim"), "dataFim");
		p.add(Projections.property("co.observacao"), "observacao");

		p.add(Projections.property("o.pontuacao"), "ocorrenciaPontuacao");
		p.add(Projections.property("c.id"),"colaboradorId");
		p.add(Projections.property("c.nome"),"colaboradorNome");
		p.add(Projections.property("e.nome"),"estabelecimentoNome");
		p.add(Projections.property("a.nome"),"areaNome");

		criteria.setProjection(p);

		criteria.add(Expression.in("co.colaborador.id", colaboradorCheckLong));
		criteria.add(Expression.in("co.ocorrencia.id", ocorrenciaCheckLong));
		criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentoCheckLong));
		criteria.add(Expression.le("hc.data", new Date()));

		if(parametros.get("dataIni") != null)
			criteria.add(Expression.ge("co.dataIni",parametros.get("dataIni")));

		if(parametros.get("dataFim") != null)
			criteria.add(Expression.le("co.dataIni",parametros.get("dataFim")));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("c.id"));
		criteria.addOrder(Order.desc("hc.data"));
		criteria.addOrder(Order.asc("co.dataIni"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));

		return criteria.list();
	}

	public Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "c");
		criteria.createCriteria("c.ocorrencia","o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.dataIni"), "dataIni");
		p.add(Projections.property("c.dataFim"), "dataFim");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("o.descricao"), "projectionDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.colaborador.id", colaboradorId));
		criteria.addOrder(Order.asc("c.dataIni"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));

		// Se page e pagingSize = 0, chamada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	public ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","o");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("c.empresa","e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.observacao"), "observacao");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("e.codigoAC"), "projectionEmpresaCodigoAC");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("o.id"), "ocorrenciaId");
		p.add(Projections.property("o.codigoAC"), "projectionOcorrenciaCodigoAC");
		p.add(Projections.property("o.integraAC"), "projectionOcorrenciaIntegraAC");
		criteria.setProjection(p);

		criteria.add(Expression.eq("co.id", colaboradorOcorrenciaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		
		return (ColaboradorOcorrencia) criteria.uniqueResult();
	}

	public ColaboradorOcorrencia findByDadosAC(Date dataIni, String ocorrenciaCodigoAC, String colaboradorCodigoAC, String empresaCodigoAC)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.colaborador","colab");
		criteria.createCriteria("co.ocorrencia","oco");
		criteria.createCriteria("oco.empresa","e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.dataFim"), "dataFim");
		criteria.setProjection(p);

		criteria.add(Expression.eq("co.dataIni", dataIni));
		criteria.add(Expression.eq("oco.codigoAC", ocorrenciaCodigoAC));
		criteria.add(Expression.eq("colab.codigoAC", colaboradorCodigoAC));
		criteria.add(Expression.eq("e.codigoAC", empresaCodigoAC));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		return (ColaboradorOcorrencia) criteria.uniqueResult();
	}

	public boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni)
	{

		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","oco");
		criteria.createCriteria("oco.empresa","e");

		if (colaboradorOcorrenciaId != null)
			criteria.add(Expression.ne("co.id", colaboradorOcorrenciaId));

		criteria.add(Expression.eq("co.dataIni", dataIni));
		criteria.add(Expression.eq("co.ocorrencia.id", ocorrenciaId));
		criteria.add(Expression.eq("co.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("e.id", empresaId));

		criteria.setProjection(Projections.rowCount());

		boolean exists = ((Integer)criteria.uniqueResult()) > 0;
		return exists;
	}
}