package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.ParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public class ParticipanteAvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<ParticipanteAvaliacaoDesempenho> implements ParticipanteAvaliacaoDesempenhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<Colaborador> findParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, e.id, ");
		hql.append("ao.id, ao.nome, fs.id, fs.nome, ca.id, ca.nome ) ");
		hql.append("from ParticipanteAvaliacaoDesempenho as p ");
		hql.append("inner join p.colaborador as co ");
		hql.append("inner join co.empresa as e ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where ");
		hql.append("  hc.data = (");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date  ");
		hql.append("  ) ");
		hql.append("and p.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");

		if(tipo != null)
			hql.append("and tipo = :tipo ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		if(tipo != null)
			query.setCharacter("tipo", tipo);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct new FaixaSalarial(fs.id, fs.nome, ca.nome) ");
		hql.append("from ParticipanteAvaliacaoDesempenho as p ");
		hql.append("inner join p.colaborador as co ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where ");
		hql.append("  hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date  ");
		hql.append("  ) ");
		hql.append("and p.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");
		hql.append("and tipo = :tipo ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setCharacter("tipo", ParticipanteAvaliacao.AVALIADO);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId) {
		StringBuilder hql = new StringBuilder();
		
//		select distinct hc.faixasalarial_id from participanteavaliacaodesempenho p
//		left join colaboradorquestionario cq on cq.avaliador_id = p.colaborador_id and cq.avaliacaodesempenho_id = p.avaliacaodesempenho_id
//		left join historicocolaborador hc on hc.colaborador_id = cq.colaborador_id
//		where p.avaliacaodesempenho_id = 29 and p.colaborador_id = 11
//		and hc.status = 1 and hc.data=( select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador_id=cq.colaborador_id and hc2.data<= now() and hc2.status=1 ) ;
		
		hql.append("select distinct new FaixaSalarial(fs.id, fs.nome, ca.nome) ");
		hql.append("from ColaboradorQuestionario as cq ");
//		hql.append("inner join ColaboradorQuestionario as cq with cq.avaliador.id = p.colaborador.id and cq.avaliacaoDesempenho.id = p.avaliacaoDesempenho.id ");
		hql.append("inner join cq.colaborador as co ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where ");
		hql.append("  hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date  ");
		hql.append("  ) ");
		hql.append("and cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");
		hql.append("and cq.avaliador.id = :avaliadorId ");
//		hql.append("and tipo = :tipo ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadorId", avaliadorId);
//		query.setCharacter("tipo", ParticipanteAvaliacao.AVALIADO);
		
		return query.list();
	}
	
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception {
		String hql = "delete from ParticipanteAvaliacaoDesempenho where ";
		if(participantes != null && participantes.length > 0)
			hql+="colaborador.id not in (:participantes) and ";
		hql+="avaliacaoDesempenho.id = :avaliacaoDesempenhoId ";
		if(tipo != null)
			hql+="and tipo = :tipo";
		
		Query query = getSession().createQuery(hql);
		if(participantes != null && participantes.length > 0)
			query.setParameterList("participantes", participantes);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		if(tipo != null)
			query.setCharacter("tipo", tipo);
		
		query.executeUpdate();
	}
}