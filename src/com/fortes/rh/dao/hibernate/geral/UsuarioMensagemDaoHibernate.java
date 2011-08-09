package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.UsuarioMensagem;

public class UsuarioMensagemDaoHibernate extends GenericDaoHibernate<UsuarioMensagem> implements UsuarioMensagemDao
{
	@SuppressWarnings("unchecked")
	public Collection<UsuarioMensagem> listaUsuarioMensagem(Long usuarioId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioMensagem.class, "um");
		criteria.createCriteria("um.mensagem", "m",Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("um.id"), "id");
		p.add(Projections.property("um.lida"), "lida");
		p.add(Projections.property("m.remetente"), "projectionMensagemRemetente");
		p.add(Projections.property("m.data"), "projectionMensagemData");
		p.add(Projections.property("m.texto"), "projectionMensagemTexto");
		p.add(Projections.property("m.link"), "projectionMensagemLink");

		criteria.setProjection(p);

		criteria.add(Expression.eq("um.usuario.id", usuarioId));
		criteria.add(Expression.eq("um.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioMensagem.class));
		criteria.addOrder(Order.desc("m.data"));

		return criteria.list();
	}

	public UsuarioMensagem findByIdProjection(Long usuarioMensagemId , Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioMensagem.class, "um");
		criteria.createCriteria("um.mensagem", "m");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("um.id"), "id");
		p.add(Projections.property("um.lida"), "lida");
		p.add(Projections.property("m.id"), "projectionMensagemId");
		p.add(Projections.property("m.remetente"), "projectionMensagemRemetente");
		p.add(Projections.property("m.data"), "projectionMensagemData");
		p.add(Projections.property("m.texto"), "projectionMensagemTexto");
		p.add(Projections.property("um.usuario.id"), "projectionUsuarioId");
		p.add(Projections.property("um.empresa.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("um.id", usuarioMensagemId));
		criteria.add(Expression.eq("um.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioMensagem.class));

		return (UsuarioMensagem) criteria.uniqueResult();
	}

	public Boolean possuiMensagemNaoLida(Long usuarioId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioMensagem.class, "um");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("um.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("um.usuario.id", usuarioId));
		criteria.add(Expression.eq("um.lida", false));
		criteria.add(Expression.eq("um.empresa.id", empresaId));
		

		return criteria.list().size() > 0;
	}

	/**
	 * @param opção
	 * 				'A' - mensagem anterior
	 * 				'P' - próxima mensagem
	 */
	public Long findAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao)
	{
		Criteria criteria = getSession().createCriteria(UsuarioMensagem.class, "um");

		ProjectionList p = Projections.projectionList().create();
		//p.add(Projections.property("um.id"), "id");

		if (opcao == 'A' )
		{
			p.add(Projections.max("um.id"));
			criteria.add(Expression.lt("um.id", usuarioMensagemId));
		}
		else
		{
			p.add(Projections.min("um.id"));
			criteria.add(Expression.gt("um.id", usuarioMensagemId));
		}

		criteria.setProjection(p);
		Projections.groupProperty("um.id");

		criteria.add(Expression.eq("um.usuario.id", usuarioId));
		criteria.add(Expression.eq("um.empresa.id", empresaId));
		criteria.setMaxResults(1);

		return (Long) criteria.uniqueResult();
	}

}