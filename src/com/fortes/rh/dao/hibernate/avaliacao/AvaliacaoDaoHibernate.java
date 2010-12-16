package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;

@SuppressWarnings("unchecked")
public class AvaliacaoDaoHibernate extends GenericDaoHibernate<Avaliacao> implements AvaliacaoDao
{
	public Collection<Avaliacao> findAllSelect(Long empresaId, Boolean ativo, char modeloAvaliacao)
	{
		Criteria criteria = getSession().createCriteria(Avaliacao.class, "a");
		
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		
		boolean modeloDeDesempenho = TipoModeloAvaliacao.DESEMPENHO == modeloAvaliacao;
		if (modeloDeDesempenho)
			criteria.add(Expression.ne("a.tipoModeloAvaliacao", TipoModeloAvaliacao.SOLICITACAO));
		else
			criteria.add(Expression.eq("a.tipoModeloAvaliacao", modeloAvaliacao));
		
		if(ativo != null)
			criteria.add(Expression.eq("a.ativo", ativo));
		
		criteria.addOrder(Order.asc("a.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
	
	public Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId)
	{
		StringBuilder consulta = new StringBuilder();
		
		// Pontuações máximas de: Notas , Objetivas , Multipla Escolha
		
		consulta.append("select sum(p.notaMaxima * p.peso)   ");
		consulta.append("									, (select sum(r5.peso * p5.peso) ");
		consulta.append("										from Pergunta p5 ");
		consulta.append(" 										join p5.respostas r5 ");		 
		consulta.append(" 										where p5.avaliacao.id = :avaliacaoId ");
		consulta.append(" 										and p5.tipo = :tipoPerguntaObjetiva ");
		consulta.append(" 										and r5.peso = (select max(r2.peso) ");
		consulta.append(" 														from Resposta r2 ");
		consulta.append(" 														where r2.pergunta.id=p5.id) ");
		consulta.append(" 										)");
		consulta.append(" 									, (select sum(r6.peso * p6.peso) ");
		consulta.append("  	 									from Pergunta p6 	");
		consulta.append("  	 									join p6.respostas r6  	");
		consulta.append("  							 			where p6.avaliacao.id = :avaliacaoId ");
		consulta.append("  	 									and p6.tipo = :tipoPerguntaMultiplaEscolha) ");
		
		consulta.append(" 	from Pergunta p ");
		consulta.append(" 	left join p.respostas r ");
		consulta.append(" 	where p.avaliacao.id = :avaliacaoId ");
		consulta.append(" 	and p.tipo = :tipoPerguntaNota ");
		consulta.append(" 	and r.id = null ");
		consulta.append(" 	 ");

		
		Query q = getSession().createQuery(consulta.toString());
		
		q.setLong("avaliacaoId", avaliacaoId);
		q.setInteger("tipoPerguntaNota", TipoPergunta.NOTA);
		q.setInteger("tipoPerguntaObjetiva", TipoPergunta.OBJETIVA);
		q.setInteger("tipoPerguntaMultiplaEscolha", TipoPergunta.MULTIPLA_ESCOLHA);
	
		Object[] somas = (Object[]) q.uniqueResult();
		
		Integer pontuacaoMaximaPorNota = somas[0] == null ? 0 : (Integer)somas[0];
		Integer pontuacaoMaximaPorObjetiva = somas[1] == null ? 0 : (Integer)somas[1];
		Integer pontuacaoMaximaPorMultiplaEscolha = somas[2] == null ? 0 : (Integer)somas[2];
		
		Integer pontuacaoMaxima = pontuacaoMaximaPorNota + pontuacaoMaximaPorObjetiva + pontuacaoMaximaPorMultiplaEscolha;
		
		return pontuacaoMaxima;
	}
}