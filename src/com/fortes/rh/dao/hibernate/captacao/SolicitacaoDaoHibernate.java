/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 * Requisito: RFA016
 */
package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("unchecked")
public class SolicitacaoDaoHibernate extends GenericDaoHibernate<Solicitacao> implements SolicitacaoDao
{
	public Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, visualizar, liberaSolicitacao, empresaId, usuario, cargoId);

		return (Integer) criteria.list().get(0);
	}

	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.anuncio", "an", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.data"), "data");
		p.add(Projections.property("s.descricao"), "descricao");
		p.add(Projections.property("c.nome"), "nomeCargo");
		p.add(Projections.property("a.nome"), "nomeArea");
		p.add(Projections.property("us.nome"), "solicitanteNome");
		p.add(Projections.property("s.encerrada"), "encerrada");
		p.add(Projections.property("s.suspensa"), "suspensa");
		p.add(Projections.property("s.obsSuspensao"), "obsSuspensao");
		p.add(Projections.property("s.status"), "status");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("an.exibirModuloExterno"), "projectionAnuncioExibirModuloExterno");
		p.add(Projections.property("ms.descricao"), "projectionMotivoSolicitacaoDescricao");

		montaConsulta(criteria, visualizar, liberaSolicitacao, empresaId, usuario, cargoId);

		criteria.setProjection(p);
		criteria.addOrder(Order.desc("s.data"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId)
	{
		criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.solicitante", "us", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.motivoSolicitacao", "ms", Criteria.LEFT_JOIN);
		

		criteria.add(Expression.eq("s.empresa.id",empresaId));

		if (visualizar == 'E')
			criteria.add(Expression.eq("s.encerrada", true));
		else if(visualizar == 'A')
		{
			criteria.add(Expression.eq("s.encerrada", false));
			criteria.add(Expression.eq("s.suspensa", false));
		}
		else if(visualizar == 'S')
		{
			criteria.add(Expression.eq("s.encerrada", false));
			criteria.add(Expression.eq("s.suspensa", true));
		}

		if(usuario != null && usuario.getId() != null && !liberaSolicitacao)
			criteria.add(Expression.eq("s.solicitante.id", usuario.getId()));

		if(cargoId != null && cargoId != -1L)
			criteria.add(Expression.eq("c.id", cargoId));
	}

	public Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Character status, Boolean suspensa)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.solicitante", "us", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.data"), "data");
		p.add(Projections.property("s.descricao"), "descricao");
		p.add(Projections.property("s.encerrada"), "encerrada");
		p.add(Projections.property("s.quantidade"), "quantidade");
		p.add(Projections.property("c.nome"), "nomeCargo");
		p.add(Projections.property("a.nome"), "nomeArea");
		p.add(Projections.property("us.nome"), "solicitanteNome");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);

		if (encerrada != null)
			criteria.add(Expression.eq("s.encerrada", encerrada));
		if (status != null)
			criteria.add(Expression.eq("s.status", status));
		if (suspensa != null)
			criteria.add(Expression.eq("s.suspensa", suspensa));
		if (empresaId != null)
			criteria.add(Expression.eq("s.empresa.id", empresaId));

		criteria.addOrder(Order.desc("s.data"));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		return criteria.list();
	}

	public Solicitacao getValor(Long solcitacaoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select 	new Solicitacao(	");
		hql.append("						solicitacao.id, ");
		hql.append("						solicitacao.quantidade, ");
		hql.append("						solicitacao.data,  ");
		hql.append("						solicitacao.encerrada,  ");
		hql.append("						solicitacao.empresa.id,  ");
		hql.append("						historicoFaixaSalarial.valor, ");
		hql.append("						solicitacao.avaliacao.id, ");
		hql.append("						faixaSalarial.id, ");
		hql.append("						cargo.id, ");
		hql.append("						cargo.nome, ");
		hql.append("						areaOrganizacional.nome, ");
		hql.append("						usuario.nome) ");
		hql.append("from Solicitacao solicitacao ");
		hql.append("	left join solicitacao.solicitante usuario ");
		hql.append("	left join solicitacao.areaOrganizacional areaOrganizacional ");
		hql.append("	left join solicitacao.faixaSalarial faixaSalarial ");
		hql.append("	left join faixaSalarial.cargo cargo ");
		hql.append("	left join faixaSalarial.faixaSalarialHistoricos historicoFaixaSalarial with historicoFaixaSalarial.data = (select max(historicoFaixaSalarial2.data) ");
		hql.append("																								  from FaixaSalarialHistorico historicoFaixaSalarial2 ");
		hql.append("																								  where historicoFaixaSalarial2.faixaSalarial.id = faixaSalarial.id ");
		hql.append("																								  and historicoFaixaSalarial2.data <= :hoje and historicoFaixaSalarial2.status != :status");
		hql.append("																								  )");
		hql.append("	left join historicoFaixaSalarial.indice i ");
		hql.append("where solicitacao.id = :solicitacaoId");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("solicitacaoId", solcitacaoId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);

		return (Solicitacao) query.uniqueResult();
	}
	public Solicitacao findByIdProjection(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("ci.uf", "est", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.horarioComercial"), "horarioComercial");
		p.add(Projections.property("s.escolaridade"), "escolaridade");
		p.add(Projections.property("s.idadeMinima"), "idadeMinima");
		p.add(Projections.property("s.idadeMaxima"), "idadeMaxima");
		p.add(Projections.property("s.sexo"), "sexo");
		p.add(Projections.property("c.id"), "cargoId");
		p.add(Projections.property("a.id"), "projectionAreaId");
		p.add(Projections.property("ci.id"), "projectionCidadeId");
		p.add(Projections.property("est.id"), "projectionCidadeUf");
		criteria.setProjection(p);

		criteria.add(Expression.eq("s.id", solicitacaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		return (Solicitacao) criteria.uniqueResult();
	}
	public Solicitacao findByIdProjectionForUpdate(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.solicitante", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.empresa", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.motivoSolicitacao", "m", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.estabelecimento", "es", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.avaliacao", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.liberador", "l", Criteria.LEFT_JOIN);
		criteria.createCriteria("ci.uf", "est", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.descricao"), "descricao");
		p.add(Projections.property("s.horarioComercial"), "horarioComercial");
		p.add(Projections.property("s.escolaridade"), "escolaridade");
		p.add(Projections.property("s.idadeMinima"), "idadeMinima");
		p.add(Projections.property("s.idadeMaxima"), "idadeMaxima");
		p.add(Projections.property("s.sexo"), "sexo");
		p.add(Projections.property("s.quantidade"), "quantidade");
		p.add(Projections.property("s.vinculo"), "vinculo");
		p.add(Projections.property("s.remuneracao"), "remuneracao");
		p.add(Projections.property("s.infoComplementares"), "infoComplementares");
		p.add(Projections.property("s.data"), "data");
		p.add(Projections.property("s.liberador"), "liberador");
		p.add(Projections.property("s.observacaoLiberador"), "observacaoLiberador");
		p.add(Projections.property("s.status"), "status");
		p.add(Projections.property("s.encerrada"), "encerrada");
		p.add(Projections.property("s.suspensa"), "suspensa");
		p.add(Projections.property("s.obsSuspensao"), "obsSuspensao");
		p.add(Projections.property("s.dataEncerramento"), "dataEncerramento");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");
		p.add(Projections.property("m.id"), "projectionMotivoSolicitacaoId");
		p.add(Projections.property("m.descricao"), "projectionMotivoSolicitacaoDescricao");
		p.add(Projections.property("ao.id"), "projectionAreaId");
		p.add(Projections.property("ao.nome"), "nomeArea");
		p.add(Projections.property("c.nome"), "nomeCargo");
		p.add(Projections.property("c.id"), "projectionFaixaSalarialCargoId");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("u.id"), "projectionSolicitanteId");
		p.add(Projections.property("u.nome"), "solicitanteNome");
		p.add(Projections.property("u.login"), "solicitanteLogin");
		p.add(Projections.property("es.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("es.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("ci.id"), "projectionCidadeId");
		p.add(Projections.property("ci.nome"), "projectionCidadeNome");
		p.add(Projections.property("est.id"), "projectionCidadeUf");
		p.add(Projections.property("est.sigla"), "projectionCidadeUfSigla");
		p.add(Projections.property("a.id"), "projectionAvaliacaoId");
		p.add(Projections.property("a.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("l.nome"), "liberadorNome");
		p.add(Projections.property("l.id"), "liberadorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("s.id", solicitacaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		return (Solicitacao) criteria.uniqueResult();
	}

	public void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId)
	{
		String hql = "update Solicitacao set encerrada = :encerrar, dataEncerramento = :dataEncerramento where id = (:id)";

		Query query = getSession().createQuery(hql);

		query.setBoolean("encerrar", encerrar);
		query.setDate("dataEncerramento", dataEncerramento);
		query.setLong("id", solicitacaoId);

		query.executeUpdate();
	}

	public Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.avaliacao", "a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.descricao"), "descricao");
		p.add(Projections.property("a.id"), "projectionAvaliacaoId");
		p.add(Projections.property("ao.id"), "projectionAreaId");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("s.id", solicitacaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		return (Solicitacao) criteria.uniqueResult();
	}

	public void updateSuspendeSolicitacao(boolean suspender, String observacao, Long solicitacaoId)
	{
		String hql = "update Solicitacao set suspensa = :suspender, obsSuspensao = :obsSuspensao where id = (:id)";

		Query query = getSession().createQuery(hql);

		query.setBoolean("suspender", suspender);
		query.setString("obsSuspensao", observacao);
		query.setLong("id", solicitacaoId);

		query.executeUpdate();
	}
	
	public void updateStatusSolicitacao(Solicitacao solicitacao) 
	{
		String hql = "update Solicitacao set status = :status, observacaoLiberador = :observacaoLiberador, liberador.id = :liberadorId where id = :solicitacaoId";

		Query query = getSession().createQuery(hql);

		query.setCharacter("status", solicitacao.getStatus());
		query.setString("observacaoLiberador", solicitacao.getObservacaoLiberador());
		query.setLong("liberadorId", solicitacao.getLiberador().getId());
		query.setLong("solicitacaoId", solicitacao.getId());

		query.executeUpdate();
	}

	public void migrarBairro(Long bairroId, Long bairroDestinoId)
	{
		String hql = "update SolicitacaoBairro set bairros_id = :bairroDestinoId where bairros_id = :bairroId";

		Query query = getSession().createQuery(hql);

		query.setLong("bairroId", bairroId);
		query.setLong("bairroDestinoId", bairroDestinoId);

		query.executeUpdate();
	}
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos)
	{
		StringBuilder consulta = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga(sum(solicitacao.quantidade) as qtdVagas, estabelecimento.id, areaOrganizacional.id, cargo.id) ");
		consulta.append("from Solicitacao as solicitacao  ");
		consulta.append("left join solicitacao.areaOrganizacional as areaOrganizacional ");
		consulta.append("left join solicitacao.faixaSalarial.cargo as cargo ");
		consulta.append("left join solicitacao.estabelecimento as estabelecimento ");
		consulta.append("where ");
		consulta.append("	solicitacao.dataEncerramento >= :dataDe ");
		consulta.append("	and solicitacao.dataEncerramento <= :dataAte ");
		
		if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
            consulta.append("and areaOrganizacional.id in (:areasOrganizacionais) ");
                        
    	if (estabelecimentos != null && !estabelecimentos.isEmpty())
    		consulta.append("and estabelecimento.id in (:estabelecimentos) ");
		
		consulta.append("group by ");
		consulta.append("	estabelecimento.id,  ");
		consulta.append("	areaOrganizacional.id, ");
		consulta.append("	cargo.id ");
		consulta.append("order by ");
		consulta.append("	estabelecimento.id,  ");
		consulta.append("	areaOrganizacional.id, ");
		consulta.append("	cargo.id ");
		
		Query query = getSession().createQuery(consulta.toString());
        query.setDate("dataDe", dataDe);
        query.setDate("dataAte", dataAte);
        
        if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
			query.setParameterList("areasOrganizacionais", areasOrganizacionais);
		
    	if (estabelecimentos != null && !estabelecimentos.isEmpty())
    		query.setParameterList("estabelecimentos", estabelecimentos);
		
        return query.list();
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds)
	{
		StringBuilder consulta = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga(s.estabelecimento.id, s.areaOrganizacional.id, fs.cargo.id,count(co.solicitacao.id), coalesce(avg(s.dataEncerramento - s.data), 0)) ");
		consulta.append("from Colaborador co ");
		consulta.append("right join co.solicitacao s ");
		consulta.append("join s.faixaSalarial fs ");
		consulta.append("where ");
		consulta.append("	s.dataEncerramento >= :dataDe ");
		consulta.append("	and s.dataEncerramento <= :dataAte ");
		
		if (areasIds != null && !areasIds.isEmpty())
            consulta.append("	and s.areaOrganizacional.id in (:areasOrganizacionais) ");
                        
    	if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
    		consulta.append("	and s.estabelecimento.id in (:estabelecimentos) ");
		
		consulta.append("group by ");
		consulta.append(" s.estabelecimento.id, ");
		consulta.append(" s.areaOrganizacional.id, ");
		consulta.append(" fs.cargo.id ");
		consulta.append("order by ");
		consulta.append(" s.estabelecimento.id, ");
		consulta.append(" s.areaOrganizacional.id, ");
		consulta.append(" fs.cargo.id ");
		
		Query query = getSession().createQuery(consulta.toString());
        query.setDate("dataDe", inicio);
        query.setDate("dataAte", fim);
        
        if (areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areasOrganizacionais", areasIds);
		
    	if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
    		query.setParameterList("estabelecimentos", estabelecimentosIds);
		
        return query.list();
	}
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos)
	{
		StringBuilder consulta = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga( ");
		consulta.append("estabelecimento.id, areaOrganizacional.id, cargo.id, count(cs.candidato.id) as qtdCandidatos) ");
		consulta.append("from CandidatoSolicitacao cs ");
		consulta.append("left join cs.solicitacao solicitacao ");
		consulta.append("left join solicitacao.motivoSolicitacao ");
		consulta.append("left join solicitacao.areaOrganizacional as areaOrganizacional ");
		consulta.append("left join solicitacao.faixaSalarial.cargo as cargo ");
		consulta.append("left join solicitacao.estabelecimento as estabelecimento ");
		consulta.append("where ");
		consulta.append("solicitacao.dataEncerramento >= :dataDe ");
		consulta.append("and solicitacao.dataEncerramento <= :dataAte ");
		
        if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
        	consulta.append("and areaOrganizacional.id in (:areasOrganizacionais) ");
                            
        if (estabelecimentos != null && !estabelecimentos.isEmpty())
        	consulta.append("and estabelecimento.id in (:estabelecimentos) ");
        
        consulta.append("group by ");
		consulta.append(" estabelecimento.id, ");
		consulta.append(" areaOrganizacional.id, ");
		consulta.append(" cargo.id ");
		consulta.append("order by ");
		consulta.append(" estabelecimento.id, ");
		consulta.append(" areaOrganizacional.id, ");
		consulta.append(" cargo.id ");

		Query query = getSession().createQuery(consulta.toString());
		query.setDate("dataDe", dataDe);
		query.setDate("dataAte", dataAte);
	        
        if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
        	query.setParameterList("areasOrganizacionais", areasOrganizacionais);
                        
        if (estabelecimentos != null && !estabelecimentos.isEmpty())  
        	query.setParameterList("estabelecimentos", estabelecimentos);
	        
        return query.list();
    }
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao)
	{
		StringBuilder consulta = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga( ");
		consulta.append("estabelecimento.id, areaOrganizacional.id, cargo.id, motivo.id, motivo.descricao, count(solicitacao.id)) ");
		consulta.append("from Solicitacao solicitacao ");
		consulta.append("left join solicitacao.motivoSolicitacao as motivo ");
		consulta.append("join solicitacao.areaOrganizacional as areaOrganizacional ");
		consulta.append("join solicitacao.faixaSalarial.cargo as cargo ");
		consulta.append("join solicitacao.estabelecimento as estabelecimento ");
		consulta.append("where ");
		consulta.append("solicitacao.empresa.id = :empresaId ");
		consulta.append("and (solicitacao.data between :dataDe and :dataAte) ");
		
		if (statusSolicitacao == StatusSolicitacao.ABERTA)
			consulta.append("and (solicitacao.dataEncerramento is null or solicitacao.dataEncerramento >= :dataAte) ");
		else if (statusSolicitacao == StatusSolicitacao.ENCERRADA)
			consulta.append("and (solicitacao.dataEncerramento is not null and solicitacao.dataEncerramento <= :dataAte) ");
		
		if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
			consulta.append("	and areaOrganizacional.id in (:areasOrganizacionais) ");
                        
    	if (estabelecimentos != null && !estabelecimentos.isEmpty())
    		consulta.append("	and estabelecimento.id in (:estabelecimentos) ");
		
    	consulta.append("group by ");
    	consulta.append("   estabelecimento.id, ");
    	consulta.append("   estabelecimento.nome, ");
    	consulta.append("	areaOrganizacional.id, ");
    	consulta.append("	areaOrganizacional.nome, ");
    	consulta.append("   motivo.id, ");
    	consulta.append("   motivo.descricao, ");
    	consulta.append("	cargo.id, ");
    	consulta.append("	cargo.nome ");
    	consulta.append("order by ");
    	consulta.append("  estabelecimento.nome, ");
    	consulta.append("  areaOrganizacional.nome, ");
    	consulta.append("  motivo.id, ");
    	consulta.append("  motivo.descricao, ");
    	consulta.append("  cargo.nome ");

		Query query = getSession().createQuery(consulta.toString());
		query.setDate("dataDe", dataDe);
		query.setDate("dataAte", dataAte);
		query.setLong("empresaId", empresaId);
		
		if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
			query.setParameterList("areasOrganizacionais", areasOrganizacionais);
		
    	if (estabelecimentos != null && !estabelecimentos.isEmpty())
    		query.setParameterList("estabelecimentos", estabelecimentos);

		return query.list();
	}
	

	public Collection<Solicitacao> findAllByCandidato(Long candidatoId) 
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "cd");
		criteria.createCriteria("cd.candidatoSolicitacaos", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.descricao"), "descricao");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cd.id", candidatoId));
		
		criteria.addOrder(Order.asc("s.descricao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));
		
		return criteria.list();
	}

	public Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "f");
		criteria.createCriteria("f.cargo", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.sum("s.quantidade"), "qtdVagasAbertas"));
		
		p.add(Projections.alias(Projections.groupProperty("f.nome"), "nome"));
		p.add(Projections.alias(Projections.groupProperty("c.nome"), "nomeCargo"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("s.data", dataIni, dataFim));
		criteria.add(Expression.eq("s.suspensa", false));
		criteria.add(Expression.eq("s.encerrada", false));
		criteria.add(Expression.eq("s.status", StatusAprovacaoSolicitacao.APROVADO));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		criteria.addOrder(Order.desc("qtdVagasAbertas"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		
		return criteria.list();
	}

	public Collection<FaixaSalarial> findQtdContratadosFaixa(Long empresaId, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.solicitacao", "s");
		criteria.createCriteria("s.faixaSalarial", "f");
		criteria.createCriteria("f.cargo", "ca");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.count("c.id"), "qtdContratados"));
		p.add(Projections.alias(Projections.groupProperty("f.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("f.nome"), "nome"));
		p.add(Projections.alias(Projections.groupProperty("ca.nome"), "nomeCargo"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataAdmissao", dataIni, dataFim));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		criteria.addOrder(Order.desc("qtdContratados"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		
		return criteria.list();
	}
	
	public Collection<AreaOrganizacional> findQtdContratadosArea(Long empresaId, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.solicitacao", "s");
		criteria.createCriteria("s.areaOrganizacional", "a");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.count("c.id"), "qtdContratados"));
		p.add(Projections.alias(Projections.groupProperty("a.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("a.nome"), "nome"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataAdmissao", dataIni, dataFim));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		criteria.addOrder(Order.desc("qtdContratados"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		
		return criteria.list();
	}
	
	public Collection<MotivoSolicitacao> findQtdContratadosMotivo(Long empresaId, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.solicitacao", "s");
		criteria.createCriteria("s.motivoSolicitacao", "m");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.count("c.id"), "qtdContratados"));
		p.add(Projections.alias(Projections.groupProperty("m.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("m.descricao"), "descricao"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataAdmissao", dataIni, dataFim));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		criteria.addOrder(Order.desc("qtdContratados"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MotivoSolicitacao.class));
		
		return criteria.list();
	}
}
