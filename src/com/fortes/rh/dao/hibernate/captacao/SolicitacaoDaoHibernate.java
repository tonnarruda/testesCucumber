/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 * Requisito: RFA016
 */
package com.fortes.rh.dao.hibernate.captacao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.LongUtil;

@Component
@SuppressWarnings("unchecked")
public class SolicitacaoDaoHibernate extends GenericDaoHibernate<Solicitacao> implements SolicitacaoDao
{
	public Integer getCount(char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, 
			String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, 
			boolean visualiazaTodasAsSolicitacoes, Date dataEncerramentoIni, Date dataEncerramentoFim)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, visualizar, empresaId, usuarioId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, descricaoBusca, statusBusca, areasIds, 
				codigoBusca, dataInicio, dataFim, visualiazaTodasAsSolicitacoes, dataEncerramentoIni, dataEncerramentoFim);

		return ((Long) criteria.uniqueResult()).intValue();
	}

	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, 
			Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, 
			boolean visualiazaTodasAsSolicitacoes, Date dataEncerramentoIni, Date dataEncerramentoFim)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.anuncio", "an", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.data"), "data");
		p.add(Projections.property("s.descricao"), "descricao");
		p.add(Projections.property("s.observacaoLiberador"), "observacaoLiberador");
		p.add(Projections.property("c.nome"), "nomeCargo");
		p.add(Projections.property("a.nome"), "nomeArea");
		p.add(Projections.property("us.nome"), "solicitanteNome");
		p.add(Projections.property("s.encerrada"), "encerrada");
		p.add(Projections.property("s.dataEncerramento"), "dataEncerramento");
		p.add(Projections.property("s.dataStatus"), "dataStatus");
		p.add(Projections.property("s.suspensa"), "suspensa");
		p.add(Projections.property("s.obsSuspensao"), "obsSuspensao");
		p.add(Projections.property("s.status"), "status");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("an.exibirModuloExterno"), "projectionAnuncioExibirModuloExterno");
		p.add(Projections.property("ms.descricao"), "projectionMotivoSolicitacaoDescricao");
		p.add(Projections.property("s.liberador"), "liberador");
		
		montaConsulta(criteria, visualizar, empresaId, usuarioId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, descricaoBusca, statusBusca, areasIds, 
				codigoBusca, dataInicio, dataFim, visualiazaTodasAsSolicitacoes, dataEncerramentoIni, dataEncerramentoFim);

		criteria.setProjection(p);
		criteria.addOrder(Order.desc("s.data"));
		criteria.addOrder(Order.desc("s.id"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, boolean visualiazaTodasAsSolicitacoes, Date dataEncerramentoIni, Date dataEncerramentoFim)
	{
		criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.solicitante", "us", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.motivoSolicitacao", "ms", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.liberador", "l", Criteria.LEFT_JOIN);

		criteria.add(Expression.eq("s.empresa.id",empresaId));
		montaCriterionInvisivelParaGestor(criteria, usuarioId, empresaId);
			
		if (visualizar == 'E'){
			criteria.add(Expression.eq("s.encerrada", true));
		}
		else if(visualizar == 'A'){
			criteria.add(Expression.eq("s.encerrada", false));
			criteria.add(Expression.eq("s.suspensa", false));
		}
		else if(visualizar == 'S'){
			criteria.add(Expression.eq("s.encerrada", false));
			criteria.add(Expression.eq("s.suspensa", true));
		}
		
		if(descricaoBusca != null && !descricaoBusca.equals(""))
			criteria.add(Expression.sqlRestriction("normalizar({alias}.descricao) ilike normalizar(?)", "%"+descricaoBusca+"%", StandardBasicTypes.STRING)); 

		if(codigoBusca != null && !codigoBusca.equals(""))
			criteria.add(Expression.sqlRestriction("cast({alias}.id as character varying) ilike (?)", "%"+codigoBusca+"%", StandardBasicTypes.STRING)); 
		
		if(statusBusca != 'T')
			criteria.add(Expression.eq("s.status", statusBusca)); 

		if(dataInicio != null)
			criteria.add(Expression.ge("s.data", dataInicio));
		if(dataFim != null)
			criteria.add(Expression.le("s.data", dataFim));

		if(dataEncerramentoIni != null)
			criteria.add(Expression.ge("s.dataEncerramento", dataEncerramentoIni));
		if(dataEncerramentoFim != null)
			criteria.add(Expression.le("s.dataEncerramento", dataEncerramentoFim));
		
		if(areasIds != null && areasIds.length > 0){
			Disjunction disjunction = Expression.disjunction();
			disjunction.add(Expression.eq("s.solicitante.id", usuarioId));
			disjunction.add(Expression.in("s.areaOrganizacional.id", areasIds));
			criteria.add(disjunction);
		}else if(usuarioId != null && !visualiazaTodasAsSolicitacoes)
			criteria.add(Expression.eq("s.solicitante.id", usuarioId));

		if(cargoId != null && !cargoId.equals(-1L))
			criteria.add(Expression.eq("c.id", cargoId));
		
		if(estabelecimentoId != null && !estabelecimentoId.equals(-1L))
			criteria.add(Expression.eq("e.id", estabelecimentoId));
		
		if(areaOrganizacionalId != null && !areaOrganizacionalId.equals(-1L))
			criteria.add(Expression.eq("a.id", areaOrganizacionalId));
		
		if(motivoId != null && !motivoId.equals(-1L))
			criteria.add(Expression.eq("s.motivoSolicitacao.id", motivoId));
	}

	private void montaCriterionInvisivelParaGestor(Criteria criteria, Long usuarioLogadoId, Long empresaId){
		StringBuilder sql = new StringBuilder();
		sql.append("(with areasId as(select a.id from areaOrganizacional a ");
		sql.append("								inner join colaborador c on ( c.id = a.responsavel_id or c.id = a.coResponsavel_id )"); 
		sql.append("								where c.usuario_id = ? and a.empresa_id = ? )");
		sql.append("	select not exists ( ");
		sql.append("					select * from areasId as a where a.id in ( ");
		sql.append("									with recursive areaorganizacional_recursiva AS ( ");
		sql.append("										select id, areamae_id from areaorganizacional"); 
		sql.append("										where id = this_.areaOrganizacional_id");
		sql.append("										union all ");
		sql.append("										select ao.id, ao.areamae_id from areaorganizacional ao"); 
		sql.append("										inner join areaorganizacional_recursiva ao_r on ao.id = ao_r.areamae_id ");
		sql.append("	 				 				)select id from areaorganizacional_recursiva ");
		sql.append("					) ");
		sql.append("	) ");
		sql.append(") ");

		Criterion criterion = Expression.sqlRestriction(sql.toString(), new Long[] {usuarioLogadoId, empresaId}, new Type[]{StandardBasicTypes.LONG, StandardBasicTypes.LONG});
		criteria.add(Expression.or(Expression.eq("s.invisivelParaGestor", false), Expression.conjunction().add(Expression.eq("s.invisivelParaGestor", true)).add(criterion)));
	}
	
	private Criteria montaCriteria()
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

		return criteria;
	}
	
	public Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Character status, Boolean suspensa)
	{
		Criteria criteria = montaCriteria();
		
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
		hql.append("						faixaSalarial.id, ");
		hql.append("						cargo.id, ");
		hql.append("						cargo.nome, ");
		hql.append("						areaOrganizacional.nome, ");
		hql.append("						usuario.nome, ");
		hql.append("						(select count(cs.status) from CandidatoSolicitacao cs where cs.status in (:contratado) and cs.solicitacao.id = solicitacao.id) ) ");
		hql.append("from Solicitacao solicitacao ");
		hql.append("	left join solicitacao.solicitante usuario ");
		hql.append("	left join solicitacao.areaOrganizacional areaOrganizacional ");
		hql.append("	left join solicitacao.faixaSalarial faixaSalarial ");
		hql.append("	left join faixaSalarial.cargo cargo ");
		hql.append("	left join faixaSalarial.faixaSalarialHistoricos historicoFaixaSalarial  ");
		hql.append("		with historicoFaixaSalarial.data = (select max(hfs.data) ");
		hql.append("											from FaixaSalarialHistorico hfs ");
		hql.append("											where hfs.faixaSalarial.id = faixaSalarial.id ");
		hql.append("												and hfs.data <= current_date and hfs.status != "+StatusRetornoAC.CANCELADO+") ");
		hql.append("	left join historicoFaixaSalarial.indice i ");
		hql.append("where solicitacao.id = :solicitacaoId");

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("contratado", Arrays.asList(StatusCandidatoSolicitacao.CONTRATADO, StatusCandidatoSolicitacao.PROMOVIDO), StandardBasicTypes.CHARACTER);
		query.setLong("solicitacaoId", solcitacaoId);

		return (Solicitacao) query.uniqueResult();
	}
	public Solicitacao findByIdProjection(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.ambiente", "amb", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.funcao", "fu", Criteria.LEFT_JOIN);
		criteria.createCriteria("ci.uf", "est", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.horarioComercial"), "horarioComercial");
		p.add(Projections.property("s.escolaridade"), "escolaridade");
		p.add(Projections.property("s.idadeMinima"), "idadeMinima");
		p.add(Projections.property("s.idadeMaxima"), "idadeMaxima");
		p.add(Projections.property("s.sexo"), "sexo");
		p.add(Projections.property("s.data"), "data");		
		p.add(Projections.property("e.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("c.id"), "cargoId");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("a.id"), "projectionAreaId");
		p.add(Projections.property("ci.id"), "projectionCidadeId");
		p.add(Projections.property("est.id"), "projectionCidadeUf");
		p.add(Projections.property("fu.id"), "projectionFuncaoId");
		p.add(Projections.property("amb.id"), "projectionAmbienteId");
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
		criteria.createCriteria("s.liberador", "l", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.ambiente", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.funcao", "f", Criteria.LEFT_JOIN);
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
		p.add(Projections.property("s.dataPrevisaoEncerramento"), "dataPrevisaoEncerramento");
		p.add(Projections.property("s.liberador"), "liberador");
		p.add(Projections.property("s.observacaoLiberador"), "observacaoLiberador");
		p.add(Projections.property("s.dataStatus"), "dataStatus");
		p.add(Projections.property("s.status"), "status");
		p.add(Projections.property("s.encerrada"), "encerrada");
		p.add(Projections.property("s.suspensa"), "suspensa");
		p.add(Projections.property("s.obsSuspensao"), "obsSuspensao");
		p.add(Projections.property("s.dataEncerramento"), "dataEncerramento");
		p.add(Projections.property("s.colaboradorSubstituido"), "colaboradorSubstituido");
		p.add(Projections.property("s.invisivelParaGestor"), "invisivelParaGestor");
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
		p.add(Projections.property("l.nome"), "liberadorNome");
		p.add(Projections.property("l.id"), "liberadorId");
		p.add(Projections.property("a.id"), "projectionAmbienteId");
		p.add(Projections.property("a.nome"), "projectionAmbienteNome");
		p.add(Projections.property("f.id"), "projectionFuncaoId");
		p.add(Projections.property("f.nome"), "projectionFuncaoNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("s.id", solicitacaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));

		return (Solicitacao) criteria.uniqueResult();
	}

	public void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId, String observacaoLiberador)
	{
		String hql = "update Solicitacao set encerrada = :encerrar, dataEncerramento = :dataEncerramento ";
		
		if(observacaoLiberador != null)
			hql += ", observacaoLiberador = :observacaoLiberador ";
		
		hql += " where id = (:id)";

		Query query = getSession().createQuery(hql);

		query.setBoolean("encerrar", encerrar);
		query.setDate("dataEncerramento", dataEncerramento);
		query.setLong("id", solicitacaoId);

		if(observacaoLiberador != null)
			query.setString("observacaoLiberador", observacaoLiberador);

		query.executeUpdate();
	}

	public Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.areaOrganizacional", "ao", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.descricao"), "descricao");
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
		String hql = "update Solicitacao set status = :status, observacaoLiberador = :observacaoLiberador, liberador.id = :liberadorId, dataStatus = :dataStatus where id = :solicitacaoId";

		Query query = getSession().createQuery(hql);

		query.setCharacter("status", solicitacao.getStatus());
		query.setString("observacaoLiberador", solicitacao.getObservacaoLiberador());
		query.setLong("liberadorId", solicitacao.getLiberador().getId());
		query.setLong("solicitacaoId", solicitacao.getId());
		query.setDate("dataStatus", solicitacao.getDataStatus());

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
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long[] solicitacaoIds)
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
    	
    	if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
    		consulta.append("and solicitacao.id in (:solicitacaoIds) ");
		
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
		
    	if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
    		query.setParameterList("solicitacaoIds", solicitacaoIds);
    	
        return query.list();
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long[] solicitacaoIds, Long empresaId, boolean considerarContratacaoFutura)
	{
		StringBuilder consulta = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga(s.estabelecimento.id, s.areaOrganizacional.id, fs.cargo.id,count(s.id), ");
		consulta.append("coalesce(avg(s.dataEncerramento - s.data),0) - coalesce(cast(sum(to_number(to_char(((case when (p.dataReinicio is null) then now() else p.dataReinicio end) - p.dataPausa), 'DDD'), '999')) as integer), 0)) ");
		consulta.append("from Colaborador co ");
		
		consulta.append("join co.historicoColaboradors hc ");
		consulta.append("join hc.candidatoSolicitacao cs ");
		consulta.append("join cs.solicitacao s ");
		
		consulta.append("left join s.pausasPreenchimentoVagas p ");
		consulta.append("join s.faixaSalarial fs ");
		consulta.append("where ");
		consulta.append("	s.dataEncerramento >= :dataDe ");
		consulta.append("	and s.dataEncerramento <= :dataAte ");
		
		if(!considerarContratacaoFutura)
			consulta.append("	and (hc.data = ( select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador.id = hc.colaborador.id and hc2.data <= :hoje and hc2.status = :status ) or hc.data is null) ");
		
		if (empresaId != null) 
    		consulta.append("	and co.empresa.id = :empresaId ");
		
		if (areasIds != null && !areasIds.isEmpty())
            consulta.append("	and s.areaOrganizacional.id in (:areasOrganizacionais) ");
                        
    	if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
    		consulta.append("	and s.estabelecimento.id in (:estabelecimentos) ");
    	
    	if (LongUtil.arrayIsNotEmpty(solicitacaoIds)) 
    		consulta.append("	and s.id in (:solicitacaoIds) ");
		
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
        
        if(!considerarContratacaoFutura){
        	query.setDate("hoje", new Date());
        	query.setInteger("status", StatusRetornoAC.CONFIRMADO);
        }
        
        if (empresaId != null) 
        	query.setLong("empresaId", empresaId);
        
        if (areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areasOrganizacionais", areasIds);
		
    	if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
    		query.setParameterList("estabelecimentos", estabelecimentosIds);
		
    	if (LongUtil.arrayIsNotEmpty(solicitacaoIds)) 
    		query.setParameterList("solicitacaoIds", solicitacaoIds);
    		
        return query.list();
	}
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long[] solicitacaoIds)
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
		consulta.append("and cs.triagem = false ");
		
        if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
        	consulta.append("and areaOrganizacional.id in (:areasOrganizacionais) ");
                            
        if (estabelecimentos != null && !estabelecimentos.isEmpty())
        	consulta.append("and estabelecimento.id in (:estabelecimentos) ");
        
        if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
        	consulta.append("and solicitacao.id in (:solicitacaoIds) ");
        	
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
	        
        if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
        	query.setParameterList("solicitacaoIds", solicitacaoIds);
        	
        return query.list();
    }
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao, char dataStatusAprovacaoSolicitacao, boolean indicadorResumido)
	{
		StringBuilder consulta = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga( ");
		
		if (!indicadorResumido)
			consulta.append("estabelecimento.nome, areaOrganizacional.id, cargo.nome, motivo.id, motivo.descricao, count(solicitacao.id)) ");
		else
			consulta.append("estabelecimento.nome, motivo.id, motivo.descricao, count(solicitacao.id)) ");
		
		consulta.append("from Solicitacao solicitacao ");
		consulta.append("left join solicitacao.motivoSolicitacao as motivo ");
		consulta.append("join solicitacao.areaOrganizacional as areaOrganizacional ");
		consulta.append("join solicitacao.faixaSalarial.cargo as cargo ");
		consulta.append("join solicitacao.estabelecimento as estabelecimento ");
		consulta.append("where ");
		consulta.append("solicitacao.empresa.id = :empresaId ");
		
		if (statusSolicitacao == StatusSolicitacao.ABERTA){
			if(dataStatusAprovacaoSolicitacao == StatusAprovacaoSolicitacao.APROVADO)
				consulta.append("and (solicitacao.dataStatus between :dataDe and :dataAte and solicitacao.status = 'A') and (solicitacao.dataEncerramento is null) ");//'A' de Aprovada
			else
				consulta.append("and (solicitacao.data between :dataDe and :dataAte) and (solicitacao.dataEncerramento is null) ");
		}else if (statusSolicitacao == StatusSolicitacao.ENCERRADA){
			consulta.append("and (solicitacao.dataEncerramento between :dataDe and :dataAte) ");
		}else{
			if(dataStatusAprovacaoSolicitacao == StatusAprovacaoSolicitacao.APROVADO)
				consulta.append("and ((solicitacao.dataStatus between :dataDe and :dataAte and solicitacao.status = 'A') ");//'A' de Aprovada
			else
				consulta.append("and ((solicitacao.data between :dataDe and :dataAte) ");
			
			consulta.append("or (solicitacao.dataEncerramento between :dataDe and :dataAte)) ");
		}
		
		if (areasOrganizacionais != null && !areasOrganizacionais.isEmpty())
			consulta.append("	and areaOrganizacional.id in (:areasOrganizacionais) ");
                        
    	if (estabelecimentos != null && !estabelecimentos.isEmpty())
    		consulta.append("	and estabelecimento.id in (:estabelecimentos) ");
		
    	consulta.append("group by ");
    	consulta.append("   estabelecimento.id, ");
    	consulta.append("   estabelecimento.nome, ");
    	
    	if (!indicadorResumido){
    		consulta.append("	areaOrganizacional.id, ");
    		consulta.append("	areaOrganizacional.nome, ");
		}
    	
    	consulta.append("   motivo.id, ");
    	consulta.append("   motivo.descricao ");

    	if (!indicadorResumido){
    		consulta.append(",	cargo.id, ");
    		consulta.append("	cargo.nome ");
    	}
    	
    	
    	consulta.append("order by ");
    	consulta.append("  estabelecimento.nome, ");
    	
    	if (!indicadorResumido)
    		consulta.append("  areaOrganizacional.nome, ");
    	
    	consulta.append("  motivo.id, ");
    	consulta.append("  motivo.descricao ");
    	
    	if (!indicadorResumido)
    		consulta.append(",  cargo.nome ");

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

	public Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim, char dataStatusAprovacaoSolicitacao) 
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "f");
		criteria.createCriteria("f.cargo", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.sum("s.quantidade"), "qtdVagasAbertas"));
		p.add(Projections.alias(Projections.groupProperty("f.nome"), "nome"));
		p.add(Projections.alias(Projections.groupProperty("c.nome"), "nomeCargo"));
		
		criteria.setProjection(p);
		
		if(dataStatusAprovacaoSolicitacao == StatusAprovacaoSolicitacao.APROVADO)
			criteria.add(Expression.between("s.dataStatus", dataIni, dataFim));
		else
			criteria.add(Expression.between("s.data", dataIni, dataFim));
		
		criteria.add(Expression.eq("s.status", StatusAprovacaoSolicitacao.APROVADO));
		criteria.add(Expression.eq("s.suspensa", false));
		criteria.add(Expression.eq("s.encerrada", false));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if (LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areaIds));
		
		if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
			criteria.add(Expression.in("s.id", solicitacaoIds));
		
		criteria.addOrder(Order.desc("qtdVagasAbertas"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		
		return criteria.list();
	}

	public Collection<FaixaSalarial> findQtdContratadosFaixa(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("s.faixaSalarial", "f");
		criteria.createCriteria("f.cargo", "ca");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.count("c.id"), "qtdContratados"));
		p.add(Projections.alias(Projections.groupProperty("f.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("f.nome"), "nome"));
		p.add(Projections.alias(Projections.groupProperty("ca.nome"), "nomeCargo"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataAdmissao", dataIni, dataFim));
		criteria.add(Expression.eq("hc.motivo", MotivoHistoricoColaborador.CONTRATADO));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if(LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areaIds));
		
		if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
			criteria.add(Expression.in("s.id", solicitacaoIds));
		
		criteria.addOrder(Order.desc("qtdContratados"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FaixaSalarial.class));
		
		return criteria.list();
	}
	
	public Collection<AreaOrganizacional> findQtdContratadosArea(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("s.areaOrganizacional", "a");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.count("c.id"), "qtdContratados"));
		p.add(Projections.alias(Projections.groupProperty("a.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("a.nome"), "nome"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataAdmissao", dataIni, dataFim));
		criteria.add(Expression.eq("hc.motivo", MotivoHistoricoColaborador.CONTRATADO));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if(LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("a.id", areaIds));
		
		if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
			criteria.add(Expression.in("s.id", solicitacaoIds));
		
		criteria.addOrder(Order.desc("qtdContratados"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		
		return criteria.list();
	}
	
	public Collection<MotivoSolicitacao> findQtdContratadosMotivo(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("s.motivoSolicitacao", "m");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.count("c.id"), "qtdContratados"));
		p.add(Projections.alias(Projections.groupProperty("m.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("m.descricao"), "descricao"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataAdmissao", dataIni, dataFim));
		criteria.add(Expression.eq("hc.motivo", MotivoHistoricoColaborador.CONTRATADO));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if(LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areaIds));
		
		if (LongUtil.arrayIsNotEmpty(solicitacaoIds))
			criteria.add(Expression.in("s.id", solicitacaoIds));
		
		criteria.addOrder(Order.desc("qtdContratados"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MotivoSolicitacao.class));
		
		return criteria.list();
	}

	public Collection<Solicitacao> findByEmpresaEstabelecimentosAreas(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds)
	{
		Criteria criteria = montaCriteria();
		
		criteria.add(Expression.eq("s.empresa.id", empresaId));

		if (LongUtil.arrayIsNotEmpty(areasIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areasIds));
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentosIds));
		
		criteria.addOrder(Order.asc("s.descricao"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	}

	public Collection<Solicitacao> getNomesColabSubstituidosSolicitacaoEncerrada(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.colaboradorSubstituido"), "colaboradorSubstituido");
		criteria.setProjection(p);

		criteria.add(Expression.eq("s.empresa.id", empresaId));
		criteria.add(Expression.eq("s.encerrada", true));
		criteria.add(Expression.isNotNull("s.colaboradorSubstituido"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Solicitacao.class));
		
		return criteria.list();
	}

	public Collection<Solicitacao> calculaIndicadorVagasPreenchidasNoPrazo(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] solicitacoesIds, Date dataDe, Date dataAte) {
		String addFiltros = "";
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			addFiltros += " and s.estabelecimento.id in (:estabelecimentosIds) ";
		if(areasIds != null && areasIds.length > 0)
			addFiltros += " and s.areaOrganizacional.id in (:areasIds) ";
		if(solicitacoesIds != null && solicitacoesIds.length > 0)
			addFiltros += " and s.id in (:solicitacoesIds) ";
		
		StringBuilder consulta = new StringBuilder("");
        consulta.append("select  new Solicitacao( s.id, s.quantidade, count(cs.id) ) ");
        consulta.append("from Solicitacao s ");
        consulta.append("left join s.candidatoSolicitacaos cs ");
        consulta.append("where ");
        consulta.append("    s.empresa.id = :empresaId ");
        consulta.append("    and s.dataPrevisaoEncerramento is not null ");
        consulta.append("    and s.data between :dataDe and :dataAte ");
        consulta.append(	 addFiltros);
        consulta.append("    group by s.id, s.quantidade ");
		
		Query query = getSession().createQuery(consulta.toString());
		query.setDate("dataDe", dataDe);
		query.setDate("dataAte", dataAte);
		query.setLong("empresaId", empresaId);

		if (estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		if (areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds);
		
		if(solicitacoesIds != null && solicitacoesIds.length > 0)
			query.setParameterList("solicitacoesIds", solicitacoesIds);
		
		return query.list();
	}
}
