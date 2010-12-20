/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.dao.hibernate.geral;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.ArquivoUtil;

@SuppressWarnings("unchecked")
public class EmpresaDaoHibernate extends GenericDaoHibernate<Empresa> implements EmpresaDao
{
	public Empresa findByCodigo(String codigo)
	{
		Query query = getSession().createQuery("from Empresa e where e.codigoAC = :codigo");
		query.setString("codigo", codigo);
		return (Empresa) query.uniqueResult();
	}

	public boolean getIntegracaoAC(Long id)
	{
		Query query = getSession().createQuery("select e.acIntegra from Empresa e where e.id = :id");
		query.setLong("id", id);
		return (Boolean) query.uniqueResult();
	}

	public boolean findExibirSalarioById(Long empresaId)
	{
		Query query = getSession().createQuery("select e.exibirSalario from Empresa e where e.id = :id");
		query.setLong("id", empresaId);
		return (Boolean) query.uniqueResult();
	}

	public Collection<Empresa> verifyExistsCnpj(String cnpj)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.cnpj", cnpj));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public String findCidade(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");
		criteria.createCriteria("e.cidade", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.nome"), "projectionCidadeNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("e.id", id));
		
		return (String) criteria.uniqueResult();
	}
	
	public Collection<Empresa> findDistinctEmpresaByQuestionario(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.questionario", "q");
		criteria.createCriteria("cq.colaborador", "c");
		criteria.createCriteria("c.empresa", "emp");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("emp.id"),"id");
		p.add(Projections.property("emp.nome"),"nome");
		
		criteria.add(Expression.eq("q.id", questionarioId));
		
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("emp.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public Empresa findByIdProjection(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.acIntegra"), "acIntegra");
		p.add(Projections.property("e.mensagemModuloExterno"), "mensagemModuloExterno");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (Empresa) criteria.uniqueResult();
	}

	public Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String role)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.empresa", "e");
		criteria.createCriteria("ue.usuario", "u");
		criteria.createCriteria("ue.perfil", "p");
		criteria.createCriteria("p.papeis", "papel");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.eq("u.id", usuarioId));
		criteria.add(Expression.eq("papel.codigo", role));
		criteria.add(Expression.eq("u.acessoSistema", true));
		
		criteria.addOrder(Order.asc("e.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public void removeEmpresaPadrao(long id) 
	{		
		String[] sqls = new String[]{
				"delete from areainteresse_areaorganizacional where areasinteresse_id in (select id from areainteresse where empresa_id=" + id + ");",
				"delete from areainteresse where empresa_id=" + id + ";",
				"delete from auditoria where empresa_id = " + id + ";",
				"delete from cargo_areaorganizacional where cargo_id in (select ID from cargo where empresa_id = " + id + ");",
				"delete from faixasalarialhistorico where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "));",
				"delete from anuncio where solicitacao_id in (select id from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + ")));",
				"delete from historicocandidato where candidatosolicitacao_id in (select id from candidatosolicitacao where solicitacao_id in (select id from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "))));",
				"delete from candidatosolicitacao where solicitacao_id in (select id from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + ")));",
				"delete from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "));",
				"delete from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + ");",
				"delete from cargo_areaformacao where cargo_id in (select id from cargo where empresa_id = " + id + ");",
				"delete from cargo_conhecimento where cargo_id in (select id from cargo where empresa_id = " + id + ");",
				"delete from candidato_cargo where cargos_id in (select id from cargo where empresa_id = " + id + ");",
				"delete from cargo where empresa_id = " + id + ";",
				"delete from conhecimento_areaorganizacional where conhecimentos_id in (select id from conhecimento where empresa_id = " + id + ");",
				"delete from conhecimento where empresa_id = " + id + ";",
				"delete from epihistorico where epi_id in (select id from epi where empresa_id = " + id + ");",
				"delete from epi where empresa_id = " + id + ";",
				"delete from grupoocupacional where empresa_id = " + id + ";",
				"delete from ocorrencia where empresa_id = " + id + ";",
				"delete from tipoepi where empresa_id = " + id + ";",
				"delete from areainteresse_areaorganizacional where areasorganizacionais_id in (select id from areaorganizacional where empresa_id = " + id + ");",
				"delete from cargo_areaorganizacional where areasorganizacionais_id in (select id from areaorganizacional where empresa_id = " + id + ");",
				"delete from areaorganizacional where empresa_id = " + id + ";",
				"delete from areainteresse where empresa_id = " + id + ";",
				"delete from auditoria where empresa_id = " + id + ";",
				"delete from formacao where candidato_id in (select id from candidato where empresa_id = " + id + ");",
				"delete from candidatocurriculo where candidato_id in (select id from candidato where empresa_id = " + id + ");",
				"delete from candidatoidioma where candidato_id in (select id from candidato where empresa_id = " + id + ");",
				"delete from candidato where empresa_id = " + id + ";",
				"delete from configuracaoimpressaocurriculo where empresa_id = " + id + ";",
				"delete from estabelecimento where empresa_id = " + id + ";",
				"delete from etapaseletiva where empresa_id = " + id + ";",
				"delete from usuarioempresa where empresa_id = " + id + ";",
				"delete from empresa where id = " + id + ";"
		};
		
		JDBCConnection.executeQuery(sqls);
		
	}
}