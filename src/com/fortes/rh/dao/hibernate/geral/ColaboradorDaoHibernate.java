package com.fortes.rh.dao.hibernate.geral;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.hibernate.util.OrderBySql;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.FiltroOrdemDeServico;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorJsonVO;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.json.ColaboradorJson;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings({"unchecked","rawtypes"})
public class ColaboradorDaoHibernate extends GenericDaoHibernate<Colaborador> implements ColaboradorDao
{
	private static final int AREA_ORGANIZACIONAL = 1;
	private static final int GRUPO_OCUPACIONAL = 2;
	@SuppressWarnings("unused")
	private static final int CARGO = 3;
	private static final int MOTIVODEMISSAO = 1;
	private static final int MOTIVODEMISSAOQUANTIDADE = 2;

	public Collection<Colaborador> findByAreaOrganizacionalIds(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentosIds, Collection<Long> cargosIds, Integer page, Integer pagingSize, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean comHistColaboradorFuturo, boolean somenteDesligados, Long notUsuarioId){
		String whereNome = "";
		String whereMatricula = "";
		String whereAreaIds = "";
		String wherecargosIds = "";
		String whereCPF = "";
		String whereEstabelecimentosIds = "";

		if(areaOrganizacionalIds != null && !areaOrganizacionalIds.isEmpty())
			whereAreaIds = "and ao.id in (:ids) ";
		
		if(cargosIds != null && !cargosIds.isEmpty())
			wherecargosIds = "and ca.id in (:cargosIds) ";

		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			whereEstabelecimentosIds = "and hc.estabelecimento.id in (:estabelecimentosIds) ";

		if(colaborador != null){
			if(colaborador.getNome() != null && !colaborador.getNome().equals(""))
				whereNome = "and lower(co.nome) like :nome ";

			if(colaborador.getMatricula() != null && !colaborador.getMatricula().equals(""))
				whereMatricula = "and lower(co.matricula) like :matricula ";

			if(colaborador.getPessoal() != null && StringUtils.isNotBlank(colaborador.getPessoal().getCpf()))
				whereCPF = "and lower(co.pessoal.cpf) like :cpf ";
		}

		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.matricula, co.desligado, co.naoIntegraAc, hc.salario, ");
		hql.append("ao.id, ao.nome, fs.id, fs.nome, ca.id, ca.nome, go.id, go.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ca.grupoOcupacional as go ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where co.desligado = :somenteDesligados ");
		
		if(empresaId != null)
			hql.append("and co.empresa.id = :empresaId ");
		
		if(dataAdmissaoIni != null)
			hql.append("and co.dataAdmissao >= :dataAdmissaoIni ");

		if(dataAdmissaoFim != null)
			hql.append("and co.dataAdmissao <= :dataAdmissaoFim ");
		
		if(notUsuarioId != null)
			hql.append("and ( co.usuario.id <> :notUsuarioId or co.usuario.id is null ) ");
		
		hql.append("and hc.status = :status ");
		hql.append(whereAreaIds);
		hql.append(wherecargosIds);
		hql.append(whereEstabelecimentosIds);
		hql.append(whereNome + whereMatricula + whereCPF );
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("from HistoricoColaborador as hc2 ");
		hql.append("where hc2.colaborador.id = co.id ");
		
		if(!comHistColaboradorFuturo)
			hql.append("and hc2.data <= :hoje ");
		
		hql.append("and hc2.status = :status) order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		
		if(empresaId != null)
			query.setLong("empresaId", empresaId);
		
		if(dataAdmissaoIni != null)
			query.setDate("dataAdmissaoIni", dataAdmissaoIni);
		
		if(dataAdmissaoFim != null)
			query.setDate("dataAdmissaoFim", dataAdmissaoFim);
		
		if(!comHistColaboradorFuturo)
			query.setDate("hoje", new Date());
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setBoolean("somenteDesligados", somenteDesligados);
		
		if(!whereAreaIds.equals(""))
			query.setParameterList("ids", areaOrganizacionalIds, Hibernate.LONG);
		
		if(!wherecargosIds.equals(""))
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		
		if(!whereEstabelecimentosIds.equals(""))
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);

		if(!whereCPF.equals(""))
			query.setString("cpf", "%" + colaborador.getPessoal().getCpf() + "%");
		
		if(!whereNome.equals(""))
			query.setString("nome", "%" + colaborador.getNome().toLowerCase() + "%");

		if(!whereMatricula.equals(""))
			query.setString("matricula", "%" + colaborador.getMatricula().toLowerCase() + "%");
		
		if(notUsuarioId != null)
			query.setLong("notUsuarioId", notUsuarioId);
		
		if(page != null && pagingSize != null)
		{
			query.setFirstResult(((page - 1) * pagingSize));
			query.setMaxResults(pagingSize);
		}

		return query.list();
	}

	public Collection<Colaborador> findByAreaOrganizacionalIds(Integer page, Integer pagingSize, Long[] areasIds, Long[] cargosIds, Long[] estabelecimentosIds, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean comHistColaboradorFuturo, boolean somenteDesligados, Long notUsuarioId)
	{
		Collection<Long> param = new HashSet<Long>();
		if(areasIds != null && areasIds.length > 0){
			for (int i = 0; i < areasIds.length; i++)
				param.add(areasIds[i]);			
		}
		
		Collection<Long> param2 = new HashSet<Long>();
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0){
			for (int i = 0; i < estabelecimentosIds.length; i++)
				param2.add(estabelecimentosIds[i]);
		}
		
		Collection<Long> param3 = new HashSet<Long>();
		if(cargosIds != null && cargosIds.length > 0){
			for (int i = 0; i < cargosIds.length; i++)
				param3.add(cargosIds[i]);
		}
		
		return findByAreaOrganizacionalIds(param, param2, param3, page, pagingSize, colaborador, dataAdmissaoIni, dataAdmissaoFim, empresaId, comHistColaboradorFuturo, somenteDesligados, notUsuarioId);
	}

	public Collection<Colaborador> findByAreaOrganizacionalIds(Long[] ids)
	{
		Collection<Long> param = new HashSet<Long>();
		for (int i = 0; i < ids.length; i++)
			param.add(ids[i]);
		return findByAreaOrganizacionalIds(param, null, null, null, null, null, null, null, null, false, false, null);
	}

	public Collection<Colaborador> findByArea(AreaOrganizacional area)
	{
		Collection<Long> param = new HashSet<Long>();
		param.add(area.getId());
		return findByAreaOrganizacionalIds(param, null, null, null, null, null, null, null, null, false, false, null);
	}

	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario){
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.contato.email"), "emailColaborador");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));

		if(usuario != null && usuario.getId() != null)
		{
			Junction juncao = Expression.disjunction();
			juncao.add(Expression.isNull("c.usuario"));
			juncao.add(Expression.eq("c.usuario.id", usuario.getId()));
			criteria.add(juncao);
		} else
			criteria.add(Expression.isNull("c.usuario"));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}
	
	public Collection<Colaborador> findComAnoDeEmpresa(Long empresaId, Date data) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.contato.email, "); 
		hql.append(" ( date_part('year', cast(:data AS date) ) - date_part('year', co.dataAdmissao) ) ) "); 
		hql.append("from Colaborador as co ");
		hql.append("where ");
		hql.append("		co.desligado = false ");
		hql.append("		and co.empresa.id = :empresaId ");
		hql.append("		and date_part('day', co.dataAdmissao) = date_part('day', cast(:data AS date) ) ");
		hql.append("		and date_part('month', co.dataAdmissao) = date_part('month', cast(:data AS date) ) ");
		hql.append(" order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("empresaId", empresaId);

		return query.list();
	}
	
	public Integer getCount(Map parametros, int tipoBuscaHistorico)
	{
		Query query = montaConsulta(parametros, true, tipoBuscaHistorico);

		return (Integer) Integer.parseInt(query.uniqueResult().toString());
	}

	private Query montaConsulta(Map parametros, boolean count, int tipoBuscaHistorico)
	{
		String nomeBusca = (String) parametros.get("nomeBusca");
		String matriculaBusca = (String) parametros.get("matriculaBusca");
		Long empresaId = (Long) parametros.get("empresaId");
		Long areaBuscaId = (Long) parametros.get("areaId");
		Long estabelecimentoId = (Long) parametros.get("estabelecimentoId");
		Long cargoId = (Long) parametros.get("cargoId");
		String situacao = (String) parametros.get("situacao");

		String cpfBusca = (String) parametros.get("cpfBusca");
		if(cpfBusca != null && cpfBusca.equals("   .   .   -  "))
			cpfBusca = null;

		StringBuilder hql = new StringBuilder();

		if(count)
			hql.append("select count(*) ");
		else
			hql.append("select co.id, co.nome, co.nomeComercial, co.matricula, co.desligado, co.dataAdmissao, co.pessoal.cpf, us.id, co.dataDesligamento, md.motivo, co.respondeuEntrevista, cand.id ");

		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join co.usuario as us ");
		hql.append("left join co.candidato as cand ");
		hql.append("left join co.motivoDemissao as md ");
		hql.append("where co.empresa.id = :empresaId ");
		hql.append("and hc.status = :status ");

		// Nome
		if(nomeBusca != null && !nomeBusca.trim().equals(""))
			hql.append("and normalizar(upper(co.nome)) like normalizar(:nomeBusca) ");
		// Matricula
		if(matriculaBusca != null && !matriculaBusca.trim().equals(""))
			hql.append("and upper(co.matricula) like :matriculaBusca ");
		// CPF
		if(cpfBusca != null && !cpfBusca.trim().equals(""))
			hql.append("and co.pessoal.cpf like :cpfBusca ");
		// Area
		if(areaBuscaId != null)
			hql.append("and ao.id = :areaBuscaId ");
		// Cargo
		if(cargoId != null)
			hql.append("and ca.id = :cargoId ");
		// Area
		if(estabelecimentoId != null)
			hql.append("and es.id = :estabelecimentoId ");

		if(situacao != null && !situacao.trim().equals("") && !situacao.trim().equals("T"))
			hql.append("and co.desligado = :situacao ");

		hql.append("and ( ");
		hql.append("      hc.data = (select max(hc2.data) ");
		hql.append("                 from HistoricoColaborador as hc2 ");
		hql.append("                 where hc2.colaborador.id = co.id ");
		hql.append("                 and hc2.data <= :hoje and hc2.status = :status) ");

		if(tipoBuscaHistorico == TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO)
		{
			hql.append("      or ");
			hql.append("      (select count(*) from HistoricoColaborador as hc3 where hc3.colaborador.id=co.id) = 1 ");
		}

		hql.append("    ) ");

		if(!count)
			hql.append(" order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);

		// Nome
		if(nomeBusca != null && !nomeBusca.trim().equals(""))
			query.setString("nomeBusca", "%" + nomeBusca.toUpperCase() + "%");
		// Matricula
		if(matriculaBusca != null && !matriculaBusca.trim().equals(""))
			query.setString("matriculaBusca", "%" + matriculaBusca.toUpperCase() + "%");
		// CPF
		if(cpfBusca != null && !cpfBusca.trim().equals(""))
			query.setString("cpfBusca", "%" + cpfBusca + "%");
		// Area
		if(areaBuscaId != null)
			query.setLong("areaBuscaId", areaBuscaId);
		// Cargo
		if(cargoId != null)
			query.setLong("cargoId", cargoId);
		// Area
		if(estabelecimentoId != null)
			query.setLong("estabelecimentoId", estabelecimentoId);
		// Situação
		if(situacao != null && !situacao.trim().equals("") && !situacao.trim().equals("T"))
		{
			if(situacao.trim().equals("A"))
				query.setBoolean("situacao", false);
			if(situacao.trim().equals("D"))
				query.setBoolean("situacao", true);
		}

		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query;
	}

	public Collection<Colaborador> findList(int page, int pagingSize, Map parametros, int tipoBuscaHistorico)
	{
		Query query = montaConsulta(parametros, false, tipoBuscaHistorico);

		if(page != 0 && pagingSize != 0)
		{
			query.setFirstResult(((page - 1) * pagingSize));
			query.setMaxResults(pagingSize);
		}

		Collection<Colaborador> result = new LinkedList<Colaborador>();
		Collection lista = new LinkedList();
		lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Colaborador colaborador = new Colaborador();
			colaborador.setId((Long) array[0]);
			colaborador.setNome((String) array[1]);
			colaborador.setNomeComercial((String) array[2]);
			colaborador.setMatricula((String) array[3]);
			colaborador.setDesligado((Boolean) array[4]);
			colaborador.setDataAdmissao((Date) array[5]);
			colaborador.setPessoal(new Pessoal());
			colaborador.getPessoal().setCpf((String) array[6]);
			colaborador.setUsuario(new Usuario());
			colaborador.getUsuario().setId((Long) array[7]);
			colaborador.setDataDesligamento((Date) array[8]);
			colaborador.setMotivoDemissaoMotivo((String) array[9]);

			if(colaborador.isDesligado() == true)
				colaborador.setNome((String) array[1] + " (Desligado em " + DateUtil.formataDiaMesAno(colaborador.getDataDesligamento()) + ")");

			if(array[10] != null)
				colaborador.setRespondeuEntrevista((Boolean) array[10]);
			else
				colaborador.setRespondeuEntrevista(false);

			colaborador.setCandidato(new Candidato());
			colaborador.getCandidato().setId((Long) array[11]);

			result.add(colaborador);
		}

		return result;
	}

	public Colaborador findColaboradorPesquisa(Long id, Long empresaId){
		Colaborador colaborador = new Colaborador();
		StringBuilder hql = new StringBuilder();
		hql.append("select co.id, co.nomeComercial, ao.id, ao.nome, co.empresa.id, co.nome ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where co.empresa.id = :empresaId ");
		hql.append("	and co.id = :id and hc.status = :status ");
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		hql.append("order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		query.setLong("id", id);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> colaboradors = new LinkedList<Colaborador>();
		Collection lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();){
			Object[] array = it.next();
			colaboradors.add(new Colaborador((Long) array[0], (String) array[5], (String) array[1], null, null, (Long) array[2], (String) array[3], null, null, (Long) array[4], new Date()));
		}

		if(colaboradors != null && !colaboradors.isEmpty())
			colaborador = (Colaborador) colaboradors.toArray()[0];

		return colaborador;
	}

	private void montaMaxDataHistColab2ComParametrosHojeStatus(StringBuilder hql) {
		hql.append("	and hc.data = (select max(hc2.data) ");
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :hoje ");
		hql.append("	and hc2.status = :status) ");
	}

	public Colaborador findColaboradorById(Long id)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.endereco.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.camposExtras", "ce", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.motivoDemissao", "m", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.pessoal.ctps.ctpsUf", "ctpsUf", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.pessoal.rgUf", "rgUf", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		projectionDadosColaborador(p);
		projectionDadosPessoaisDoColaborador(p);
		projectionDadosHabilidadeEContatoColaborador(p);
		projectionDadosCamposExtrasColaborador(p);
		p.add(Projections.property("c.candidato.id"), "candidatoId");
		p.add(Projections.property("ci.id"), "enderecoCidadeId");
		p.add(Projections.property("ci.nome"), "enderecoCidadeNome");
		p.add(Projections.property("ci.codigoAC"), "projectionCidadeCodigoAC");
		p.add(Projections.property("m.id"), "projectionMotivoDemissaoId");
		p.add(Projections.property("m.motivo"), "motivoDemissaoMotivo");
		p.add(Projections.property("u.sigla"), "enderecoUfSigla");
		p.add(Projections.property("u.id"), "enderecoUfId");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");
		p.add(Projections.property("emp.codigoAC"), "empresaCodigoAC");
		p.add(Projections.property("emp.grupoAC"), "empresaGrupoAC");
		p.add(Projections.property("emp.acIntegra"), "empresaAcIntegra");
		p.add(Projections.property("emp.campoExtraColaborador"), "campoExtraColaborador");
		p.add(Projections.property("c.naoIntegraAc"), "naoIntegraAc");
		criteria.setProjection(p);
		criteria.add(Expression.eq("c.id", id));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	private void projectionDadosCamposExtrasColaborador(ProjectionList p) {
		p.add(Projections.property("ce.id"), "projectionCamposExtrasId");
		p.add(Projections.property("ce.texto1"), "projectionTexto1");
		p.add(Projections.property("ce.texto2"), "projectionTexto2");
		p.add(Projections.property("ce.texto3"), "projectionTexto3");
		p.add(Projections.property("ce.texto4"), "projectionTexto4");
		p.add(Projections.property("ce.texto5"), "projectionTexto5");
		p.add(Projections.property("ce.texto6"), "projectionTexto6");
		p.add(Projections.property("ce.texto7"), "projectionTexto7");
		p.add(Projections.property("ce.texto8"), "projectionTexto8");
		p.add(Projections.property("ce.textolongo1"), "projectionTextolongo1");
		p.add(Projections.property("ce.textolongo2"), "projectionTextolongo2");
		p.add(Projections.property("ce.data1"), "projectionData1");
		p.add(Projections.property("ce.data2"), "projectionData2");
		p.add(Projections.property("ce.data3"), "projectionData3");
		p.add(Projections.property("ce.valor1"), "projectionValor1");
		p.add(Projections.property("ce.valor2"), "projectionValor2");
		p.add(Projections.property("ce.numero1"), "projectionNumero1");
	}

	private void projectionDadosHabilidadeEContatoColaborador(ProjectionList p) {
		p.add(Projections.property("ctpsUf.id"), "projectionCtpsUfId");
		p.add(Projections.property("ctpsUf.sigla"), "projectionCtpsUfSigla");
		p.add(Projections.property("c.habilitacao.numeroHab"), "projectionNumeroHabilitacao");
		p.add(Projections.property("c.habilitacao.registro"), "projectionRegistroHabilitacao");
		p.add(Projections.property("c.habilitacao.emissao"), "projectionEmissaoHabilitacao");
		p.add(Projections.property("c.habilitacao.vencimento"), "projectionVencimentoHabilitacao");
		p.add(Projections.property("c.habilitacao.categoria"), "projectionCategoriaHabilitacao");
		p.add(Projections.property("c.endereco.logradouro"), "enderecoLogradouro");
		p.add(Projections.property("c.endereco.complemento"), "enderecoComplemento");
		p.add(Projections.property("c.endereco.numero"), "enderecoNumero");
		p.add(Projections.property("c.endereco.bairro"), "enderecoBairro");
		p.add(Projections.property("c.endereco.cep"), "enderecoCep");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("c.contato.ddd"), "contatoDdd");
		p.add(Projections.property("c.contato.foneCelular"), "contatoCelular");
		p.add(Projections.property("c.contato.foneFixo"), "contatoFoneFixo");
	}

	private void projectionDadosPessoaisDoColaborador(ProjectionList p) {
		p.add(Projections.property("c.pessoal.estadoCivil"), "pessoalEstadoCivil");
		p.add(Projections.property("c.pessoal.escolaridade"), "pessoalEscolaridade");
		p.add(Projections.property("c.pessoal.mae"), "pessoalMae");
		p.add(Projections.property("c.pessoal.pai"), "pessoalPai");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.pessoal.pis"), "projectionPis");
		p.add(Projections.property("c.pessoal.rg"), "projectionRg");
		p.add(Projections.property("rgUf.id"), "projectionRgUfId");
		p.add(Projections.property("rgUf.sigla"), "projectionRgUfSigla");
		p.add(Projections.property("c.pessoal.rgOrgaoEmissor"), "projectionRgOrgaoEmissor");
		p.add(Projections.property("c.pessoal.deficiencia"), "projectionDeficiencia");
		p.add(Projections.property("c.pessoal.rgDataExpedicao"), "projectionRgDataExpedicao");
		p.add(Projections.property("c.pessoal.sexo"), "projectionSexo");
		p.add(Projections.property("c.pessoal.dataNascimento"), "projectionDataNascimento");
		p.add(Projections.property("c.pessoal.conjuge"), "pessoalConjuge");
		p.add(Projections.property("c.pessoal.qtdFilhos"), "pessoalQtdFilhos");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitNumero"), "projectionTituloNumero");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitZona"), "projectionTituloZona");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitSecao"), "projectionTituloSecao");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilNumero"), "projectionCertMilNumero");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilTipo"), "projectionCertMilTipo");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilSerie"), "projectionCertMilSerie");
		p.add(Projections.property("c.pessoal.ctps.ctpsNumero"), "projectionCtpsNumero");
		p.add(Projections.property("c.pessoal.ctps.ctpsSerie"), "projectionCtpsSerie");
		p.add(Projections.property("c.pessoal.ctps.ctpsDv"), "projectionCtpsDv");
		p.add(Projections.property("c.pessoal.ctps.ctpsDataExpedicao"), "projectionCtpsDataExpedicao");
	}

	private void projectionDadosColaborador(ProjectionList p) {
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("c.cursos"), "cursos");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.observacaoDemissao"), "observacaoDemissao");
		p.add(Projections.property("c.demissaoGerouSubstituicao"), "demissaoGerouSubstituicao");
		p.add(Projections.property("c.vinculo"), "vinculo");
		p.add(Projections.property("c.naoIntegraAc"), "naoIntegraAc");
		p.add(Projections.property("c.respondeuEntrevista"), "respondeuEntrevista");
	}

	public Colaborador findColaboradorByIdProjection(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("c.dataSolicitacaoDesligamento"), "dataSolicitacaoDesligamento");
		p.add(Projections.property("c.naoIntegraAc"), "naoIntegraAc");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.observacaoDemissao"), "observacaoDemissao");
		p.add(Projections.property("c.motivoDemissao"), "motivoDemissao");
		p.add(Projections.property("c.demissaoGerouSubstituicao"), "demissaoGerouSubstituicao");
		p.add(Projections.property("c.solicitanteDemissao.id"), "solicitanteDemissaoId");
		p.add(Projections.property("c.camposExtras.id"), "projectionCamposExtrasId");
		p.add(Projections.property("c.candidato.id"), "candidatoId");
		p.add(Projections.property("c.empresa.id"), "empresaId");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.pessoal.rg"), "projectionRg");
		p.add(Projections.property("c.pessoal.dataNascimento"), "projectionDataNascimento");
		p.add(Projections.property("c.contato.email"), "emailColaborador");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.id", colaboradorId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Colaborador findByUsuario(Usuario usuario, Long empresaId){
		StringBuilder hql = new StringBuilder();
		hql.append("select co.id, ao.id ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where co.empresa.id = :empresaId ");
		hql.append("	and co.usuario.id = :id ");
		hql.append("	and hc.status = :status ");
		
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		
		hql.append("order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		query.setLong("id", usuario.getId());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		Collection lista = query.list();
		Collection<Colaborador> colaboradors = new LinkedList<Colaborador>();
		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();){
			Object[] array = it.next();
			Colaborador colaboradorTmp = new Colaborador();
			colaboradorTmp.setId((Long) array[0]);
			colaboradorTmp.setAreaOrganizacionalId((Long) array[1]);
			colaboradors.add(colaboradorTmp);
		}
		Colaborador colaborador = new Colaborador();
		if(colaboradors != null && !colaboradors.isEmpty())
			colaborador = (Colaborador) colaboradors.toArray()[0];

		return colaborador;
	}

	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId){
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.usuario", "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("u.senha"), "usuarioSenha");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.pessoal.cpf", cpf));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));
		criteria.add(Expression.eq("u.acessoSistema", true));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Colaborador findByCandidato(Long candidatoId, Long empresaId){
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("emp.nome"), "empresaNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.candidato.id", candidatoId));
		if(empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId){
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.desligado) "); 
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co "); 
		hql.append("where ");
		hql.append("		hc.funcao.id = :funcaoId ");
		hql.append("		and hc.status = :status ");
		hql.append("		and hc.ambiente.id = :ambienteId and "); 
		hql.append("		co.desligado = false ");
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		hql.append(" order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("funcaoId", funcaoId);
		query.setLong("ambienteId", ambienteId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Colaborador findFuncaoAmbiente(Long colaboradorId){
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nomeComercial, func, amb) "); 
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co "); 
		hql.append("left join hc.ambiente as amb "); 
		hql.append("left join hc.funcao as func "); 
		hql.append("where ");
		hql.append("		co.id = :colaboradorId ");
		hql.append("		and hc.status = :status ");
		hql.append("		and hc.data = ("); 
		hql.append("					select max(hc2.data) "); 
		hql.append("					from HistoricoColaborador as hc2 ");
		hql.append("					where hc2.colaborador.id = co.id ");
		hql.append("					and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("				 )");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("colaboradorId", colaboradorId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return (Colaborador) query.uniqueResult();
	}

	public boolean setCodigoColaboradorAC(String codigo, Long id){
		String hql = "update Colaborador set codigoac = :codigo where id = :id";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setLong("id", id);
		int result = query.executeUpdate();
		return result == 1;
	}

	public boolean atualizarUsuario(Long colaboradorId, Long usuarioId)
	{
		getSession().flush();

		String hql = "update Colaborador set usuario.id = :usuarioId where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("usuarioId", usuarioId, Hibernate.LONG);
		query.setLong("id", colaboradorId);
		int result = query.executeUpdate();

		return result == 1;
	}
	
	public Colaborador findByCodigoAC(String codigoAC, Empresa empresa)
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.empresa", "e");
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("c.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.usuario.id"), "usuarioIdProjection");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("e.codigoAC"), "empresaCodigoAC");
		p.add(Projections.property("hc.areaOrganizacional.id"), "areaOrganizacionalId");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa", empresa));//tem que ser por ID, ta correto(CUIDADO: caso mude tem que verificar o grupoAC)
		criteria.add(Expression.eq("c.codigoAC", codigoAC));

		criteria.add(Expression.or(Expression.isNull("hc.data"), Subqueries.propertyEq("hc.data", subQueryHc)));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Colaborador findById(Long colaboradorId){
		Colaborador colaborador = new Colaborador();
		StringBuilder hql = new StringBuilder();
		montaSelectFindById(hql);

		hql.append("	where co.id = :id ");
		hql.append("	and hc.status = :statusHistColab ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :statusHistColab ");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("id", colaboradorId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		query.setInteger("statusHistColab", StatusRetornoAC.CONFIRMADO);

		colaborador = (Colaborador) query.uniqueResult();

		return colaborador;
	}

	private void montaSelectFindById(StringBuilder hql)
	{
		hql.append("select new Colaborador(co, hc, fs, fsh, ifs, ifsh, i, ih) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");

		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = ");
		hql.append("(select max(fsh2.data) from FaixaSalarialHistorico fsh2 where fsh2.faixaSalarial.id = fs.id and fsh2.data <= :hoje and fsh2.status != :status) ");

		hql.append("left join fsh.indice as ifs ");
		hql.append("left join ifs.indiceHistoricos as ifsh with ifsh.data = ");
		hql.append("(select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifs.id and ih3.data <= :hoje) ");

		hql.append("left join hc.indice as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = ");
		hql.append("(select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = i.id and ih4.data <= :hoje) ");
	}

	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, hc.areaOrganizacional.id, hc.estabelecimento.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("	where hc.areaOrganizacional.id = :areaId ");
		hql.append("	and hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("areaId", areaOrganizacionalId);
		query.setLong("estabelecimentoId", estabelecimentoId);

		return query.list();
	}

	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds, String colaboradorNome, Long empresaId){
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, a.id, a.nome, am.id, am.nome, hc.estabelecimento.id, hc.estabelecimento.nome, faixa.id, faixa.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as a ");
		hql.append("left join hc.faixaSalarial as faixa ");
		hql.append("left join a.areaMae as am ");
		hql.append(" where co.desligado = :desligado ");
		
		if(StringUtils.isNotBlank(colaboradorNome))
			hql.append(" and normalizar(upper(co.nome)) like normalizar(:colaboradorNome) ");
		if(areasOrganizacionaisIds != null && !areasOrganizacionaisIds.isEmpty())
			hql.append("	and a.id in (:areaIds) ");
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			hql.append("	and hc.estabelecimento.id in (:estabelecimentoIds) ");
		if(empresaId != null)
			hql.append("	and co.empresa.id = :empresaId ");
		
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		hql.append(" order by co.nomeComercial ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setBoolean("desligado", false);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(StringUtils.isNotBlank(colaboradorNome))
			query.setString("colaboradorNome", "%" + colaboradorNome.toUpperCase() + "%");
		if(areasOrganizacionaisIds != null && !areasOrganizacionaisIds.isEmpty())
			query.setParameterList("areaIds", areasOrganizacionaisIds, Hibernate.LONG);
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		if(empresaId != null)
			query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds, String colaboradorNome, Long empresaId){
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, a.id, a.nome, am.id, am.nome, hc.estabelecimento.id, hc.estabelecimento.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as a ");
		hql.append("left join a.areaMae as am ");
		hql.append(" where co.desligado = :desligado ");
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		if(StringUtils.isNotBlank(colaboradorNome))
			hql.append(" and normalizar(upper(co.nome)) like normalizar(:colaboradorNome) ");
		if(cargoIds != null && !cargoIds.isEmpty())
			hql.append(" and ca.id in (:cargoIds) ");
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			hql.append(" and hc.estabelecimento.id in (:estabelecimentoIds) ");
		if(empresaId != null)
			hql.append(" and co.empresa.id = :empresaId ");

		Query query = getSession().createQuery(hql.toString());
		query.setBoolean("desligado", false);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		if(StringUtils.isNotBlank(colaboradorNome))
			query.setString("colaboradorNome", "%" + colaboradorNome.toUpperCase() + "%");
		if(cargoIds != null && !cargoIds.isEmpty())
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		if(empresaId != null)
			query.setLong("empresaId", empresaId);
		
		return query.list();
	}

	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, a.id, a.nome, am.id, am.nome, hc.estabelecimento.id, hc.estabelecimento.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ca.grupoOcupacional as go ");
		hql.append("left join hc.areaOrganizacional as a ");
		hql.append("left join a.areaMae as am ");
		hql.append("	where go.id in (:grupoOcupacionalIds)  ");
		hql.append("	and hc.estabelecimento.id in (:estabelecimentoIds) ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setParameterList("grupoOcupacionalIds", grupoOcupacionalIds, Hibernate.LONG);
		query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, hc.areaOrganizacional.id, hc.estabelecimento.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("	where hc.estabelecimento.id in (:estabelecimentoIds) ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");
		hql.append(" order by co.nomeComercial ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Colaborador findByIdProjectionUsuario(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.usuario", "u", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("u.id"), "usuarioIdProjection");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentoIds,
			Collection<Long> areaOrganizacionalIds, Collection<Long> cargoIds, CamposExtras camposExtras, String order, Date dataAdmissaoIni, 
			Date dataAdmissaoFim, String sexo, String deficiencia, Integer[] tempoServicoIni, Integer[] tempoServicoFim, String situacao, Character enviadoParaAC, Long... empresasIds)
	{
		
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct new Colaborador(es.id, es.nome, ao.id, cast(monta_familia_area(ao.id), text) as col_3_0_, re.nome, co.id, co.nome, cg.nome, fs.nome, emp.id, emp.nome, emp.acIntegra, " +
				"co.nomeComercial, co.matricula, co.codigoAC, co.desligado, co.dataAdmissao, co.dataDesligamento, co.vinculo, co.naoIntegraAc, co.cursos, co.dataEncerramentoContrato, " +
				"co.pessoal.estadoCivil, co.pessoal.escolaridade, co.pessoal.mae, co.pessoal.pai, co.pessoal.cpf, co.pessoal.pis, co.pessoal.rg,  " +
				"co.pessoal.rgOrgaoEmissor, rgUf.sigla, co.pessoal.deficiencia, co.pessoal.rgDataExpedicao, co.pessoal.sexo,  " +
				"co.pessoal.dataNascimento, co.pessoal.conjuge, co.pessoal.qtdFilhos, co.pessoal.ctps.ctpsNumero, co.pessoal.ctps.ctpsSerie, co.pessoal.ctps.ctpsDv,  " +
				"co.habilitacao.numeroHab, co.habilitacao.emissao, co.habilitacao.vencimento, co.habilitacao.categoria, co.endereco.logradouro, co.endereco.complemento,  " +
				"co.endereco.numero, co.endereco.bairro, co.endereco.cep, co.contato.email, co.contato.foneCelular,	co.contato.foneFixo, fun.nome, amb.nome, " +
				"cidade.nome, uf.sigla, caf.inicio, caf.fim, cand.pessoal.indicadoPor, hc1.salario, hc1.tipoSalario, hc1.quantidadeIndice, i, fs, fsh, ih, ifs, ifsh " );
				
				if(habilitaCampoExtra && camposExtras != null)
				{
					hql.append(" ,ce.texto1, ce.texto2, ce.texto3, ce.texto4, ce.texto5, ce.texto6, ce.texto7, ce.texto8, ce.textolongo1, ce.textolongo2, ");
					hql.append(" ce.data1, ce.data2, ce.data3, ce.valor1, ce.valor2, ce.numero1 ");
				}
				
		hql.append(" )  " );
		
		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("right join hc1.areaOrganizacional as ao ");
		hql.append("left join hc1.areaOrganizacional.responsavel as re ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.funcao as fun ");
		hql.append("left join hc1.ambiente as amb ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join hc1.indice as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = i.id and ih2.data <= :hoje) ");
		hql.append("left join co.empresa as emp ");
		hql.append("left join co.endereco.cidade as cidade ");
		hql.append("left join co.endereco.uf as uf ");
		hql.append("left join co.pessoal.rgUf as rgUf ");
		hql.append("left join co.colaboradorAfastamento as caf ");
		hql.append("left join co.candidato as cand ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh2.data) from FaixaSalarialHistorico fsh2 where fsh2.faixaSalarial.id = fs.id and fsh2.data <= :hoje and fsh2.status != :statusFaixaSalarial) ");
		hql.append("left join fsh.indice as ifs ");
		hql.append("left join ifs.indiceHistoricos as ifsh with ifsh.data = (select max(ih3.data) from IndiceHistorico ih3 where ih3.indice.id = ifs.id and ih3.data <= :hoje) ");
		hql.append("left join hc1.faixaSalarial.cargo as cg ");
		hql.append("left join hc1.faixaSalarial.cargo.grupoOcupacional as go ");

		if(habilitaCampoExtra && camposExtras != null)
			hql.append("left join co.camposExtras as ce ");

		hql.append("where ");
		hql.append("	( ");
		hql.append("		hc1.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		hql.append("	and hc1.status = :status ");
		
		hql.append("	and (caf.inicio = ( ");
		hql.append("			select max(cf2.inicio) ");
		hql.append("			from ColaboradorAfastamento cf2 ");
		hql.append("			where co.id = cf2.colaborador.id) ");
		hql.append("	 	or caf.inicio is null)  ");
		
		if (situacao != null)
		{
			if (situacao.equals(SituacaoColaborador.ATIVO))
				hql.append("	and (co.dataDesligamento is null or co.dataDesligamento >= :hoje) ");
			else if (situacao.equals(SituacaoColaborador.DESLIGADO))
				hql.append("	and (co.dataDesligamento < :hoje) ");
		}
		
		if(sexo != null && !sexo.equals(Sexo.INDIFERENTE))
			hql.append("	and co.pessoal.sexo = :sexo ");
		
		// somente deficientes
		if(deficiencia != null && deficiencia.equals("2"))
			hql.append("    and co.pessoal.deficiencia <> '0'");
		
		// não deficientes
		if(deficiencia != null && deficiencia.equals("3"))
			hql.append("    and co.pessoal.deficiencia = '0'");
		
		if(empresasIds != null && empresasIds.length > 0 && empresasIds[0] != null)
			hql.append("	and emp.id in (:empresasIds) ");

		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			hql.append(" and hc1.estabelecimento.id in (:estabelecimentoIds) ");

		if(areaOrganizacionalIds != null && !areaOrganizacionalIds.isEmpty())
			hql.append(" and ao.id in (:areaOrganizacionalIds) ");

		if(cargoIds != null && !cargoIds.isEmpty())
			hql.append(" and cg.id in (:cargoIds) ");
		
		if(dataAdmissaoIni != null)
			hql.append(" and :dataAdmissaoIni <= co.dataAdmissao");

		if(dataAdmissaoFim != null)
			hql.append(" and co.dataAdmissao <= :dataAdmissaoFim  ");

		if(enviadoParaAC != null && !enviadoParaAC.equals('1'))
			if(enviadoParaAC.equals('2'))
				hql.append(" and co.naoIntegraAc = true ");
			else
				hql.append(" and co.naoIntegraAc = false ");
		
		if(habilitaCampoExtra && camposExtras != null)
		{
			if(StringUtils.isNotBlank(camposExtras.getTexto1()))
				hql.append(" and normalizar(upper(ce.texto1)) like normalizar(:texto1) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto2()))
				hql.append(" and normalizar(upper(ce.texto2)) like normalizar(:texto2) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto3()))
				hql.append(" and normalizar(upper(ce.texto3)) like normalizar(:texto3) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto4()))
				hql.append(" and normalizar(upper(ce.texto4)) like normalizar(:texto4) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto5()))
				hql.append(" and normalizar(upper(ce.texto5)) like normalizar(:texto5) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto6()))
				hql.append(" and normalizar(upper(ce.texto6)) like normalizar(:texto6) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto7()))
				hql.append(" and normalizar(upper(ce.texto7)) like normalizar(:texto7) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto8()))
				hql.append(" and normalizar(upper(ce.texto8)) like normalizar(:texto8) ");
			if(StringUtils.isNotBlank(camposExtras.getTextolongo1()))
				hql.append(" and normalizar(upper(ce.textolongo1)) like normalizar(:textolongo1) ");
			if(StringUtils.isNotBlank(camposExtras.getTextolongo2()))
				hql.append(" and normalizar(upper(ce.textolongo2)) like normalizar(:textolongo2) ");

			montaQueryIntervalo(camposExtras.getData1(), camposExtras.getData1Fim(), "data1", hql);
			montaQueryIntervalo(camposExtras.getData2(), camposExtras.getData2Fim(), "data2", hql);
			montaQueryIntervalo(camposExtras.getData3(), camposExtras.getData3Fim(), "data3", hql);

			montaQueryIntervalo(camposExtras.getValor1(), camposExtras.getValor1Fim(), "valor1", hql);
			montaQueryIntervalo(camposExtras.getValor2(), camposExtras.getValor2Fim(), "valor2", hql);

			montaQueryIntervalo(camposExtras.getNumero1(), camposExtras.getNumero1Fim(), "numero1", hql);
			
		}

		//Seleção de períodos de tempo de serviço
		if (tempoServicoIni != null && tempoServicoFim != null && tempoServicoIni.length == tempoServicoFim.length)
		{
			hql.append(" and ( ");
			
			for (int i = 0; i < tempoServicoIni.length; i++)
			{
				if (i != 0)
					hql.append(" or ");
				
				hql.append("(extract(year from age(current_date()+1, co.dataAdmissao))*12 + extract(month from age(current_date()+1, co.dataAdmissao)) + (extract(day from age(current_date()+1,co.dataAdmissao))/30.0) between " + tempoServicoIni[i] + " and " + tempoServicoFim[i] + ") ");
			}
			
			hql.append(" ) ");
		}

		// Ordenação
		if(StringUtils.isEmpty(order))
			hql.append(" order by es.nome, col_3_0_, co.nome ");
		else
			hql.append(" order by " + order);

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setInteger("statusFaixaSalarial", StatusRetornoAC.CANCELADO);
		
		if(sexo != null && !sexo.equals(Sexo.INDIFERENTE))
			query.setString("sexo", sexo);
		
		if(empresasIds != null && empresasIds.length > 0 && empresasIds[0] != null)
			query.setParameterList("empresasIds", empresasIds);

		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(areaOrganizacionalIds != null && !areaOrganizacionalIds.isEmpty())
			query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, Hibernate.LONG);

		if(cargoIds != null && !cargoIds.isEmpty())
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);
		
		if(dataAdmissaoIni != null)
			query.setDate("dataAdmissaoIni", dataAdmissaoIni);

		if(dataAdmissaoFim != null)
			query.setDate("dataAdmissaoFim", dataAdmissaoFim);
		
		if(habilitaCampoExtra && camposExtras != null)
		{
			if(StringUtils.isNotBlank(camposExtras.getTexto1()))
				query.setString("texto1", "%" + camposExtras.getTexto1().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto2()))
				query.setString("texto2", "%" + camposExtras.getTexto2().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto3()))
				query.setString("texto3", "%" + camposExtras.getTexto3().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto4()))
				query.setString("texto4", "%" + camposExtras.getTexto4().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto5()))
				query.setString("texto5", "%" + camposExtras.getTexto5().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto6()))
				query.setString("texto6", "%" + camposExtras.getTexto6().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto7()))
				query.setString("texto7", "%" + camposExtras.getTexto7().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto8()))
				query.setString("texto8", "%" + camposExtras.getTexto8().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTextolongo1()))
				query.setString("textolongo1", "%" + camposExtras.getTextolongo1().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTextolongo2()))
				query.setString("textolongo2", "%" + camposExtras.getTextolongo2().toUpperCase() + "%");

			setIntervaloData(camposExtras.getData1(), camposExtras.getData1Fim(), "data1", query);
			setIntervaloData(camposExtras.getData2(), camposExtras.getData2Fim(), "data2", query);
			setIntervaloData(camposExtras.getData3(), camposExtras.getData3Fim(), "data3", query);
			
			setIntervaloValor(camposExtras.getValor1(), camposExtras.getValor1Fim(), "valor1", query);
			setIntervaloValor(camposExtras.getValor2(), camposExtras.getValor2Fim(), "valor2", query);

			setIntervaloNumero(camposExtras.getNumero1(), camposExtras.getNumero1Fim(), "numero1", query);
		}

		return query.list();
	}

	private void montaQueryIntervalo(Object valorIni, Object valorFim, String campo, StringBuilder hql)
	{
		if(valorIni != null && valorFim != null)
			hql.append(" and ce." + campo + " between :" + campo + " and :" + campo + "Fim ");
		else if(valorIni != null)
			hql.append(" and ce." + campo + " >= :" + campo + " ");
		else if(valorFim != null)
			hql.append(" and ce." + campo + " <= :" + campo + "Fim ");
	}

	private void setIntervaloData(Date dataIni, Date dataFim, String dataValue, Query query)
	{
		if(dataIni != null && dataFim != null)
		{
			query.setDate(dataValue, dataIni);
			query.setDate(dataValue + "Fim", dataFim);
		} else if(dataIni != null)
			query.setDate(dataValue, dataIni);
		else if(dataFim != null)
			query.setDate(dataValue + "Fim", dataFim);
	}

	private void setIntervaloValor(Double valorIni, Double valorFim, String valorValue, Query query)
	{
		if(valorIni != null && valorFim != null)
		{
			query.setDouble(valorValue, valorIni);
			query.setDouble(valorValue + "Fim", valorFim);
		} else if(valorIni != null)
			query.setDouble(valorValue, valorIni);
		else if(valorFim != null)
			query.setDouble(valorValue + "Fim", valorFim);
	}

	private void setIntervaloNumero(Integer numeroIni, Integer numeroFim, String numeroValue, Query query)
	{
		if(numeroIni != null && numeroFim != null)
		{
			query.setInteger(numeroValue, numeroIni);
			query.setInteger(numeroValue + "Fim", numeroFim);
		} else if(numeroIni != null)
			query.setInteger(numeroValue, numeroIni);
		else if(numeroFim != null)
			query.setInteger(numeroValue + "Fim", numeroFim);
	}

	public Collection<Colaborador> findByAreaOrganizacionalEstabelecimento(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentoIds, String situacao, Long notUsuarioId, boolean consideraSoIntegradosComAC)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.nome, co.nomeComercial, co.id, e.id, e.nome, ao.id) ");
		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("right join hc1.areaOrganizacional as ao ");
		hql.append("right join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join co.empresa as e ");
		hql.append("where ");
		hql.append("	( ");
		hql.append("		hc1.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		
		if(situacao.equals(SituacaoColaborador.ATIVO) || situacao.equals(SituacaoColaborador.DESLIGADO))
			hql.append("	and co.desligado = :desligado");
		
		if(areaOrganizacionalIds != null && !areaOrganizacionalIds.isEmpty())
			hql.append(" and ao.id in (:areaOrganizacionalIds) ");
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			hql.append(" and es.id in (:estabelecimentoIds) ");
		if (notUsuarioId != null)
			hql.append(" and ( co.usuario.id <> :notUsuarioId or co.usuario.id is null )");
		if (consideraSoIntegradosComAC )
			hql.append(" and co.naoIntegraAc = false ");
			
		hql.append(" order by e.nome, co.nomeComercial, co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if(situacao.equals(SituacaoColaborador.ATIVO))
			query.setBoolean("desligado", false);
		else if(situacao.equals(SituacaoColaborador.DESLIGADO))
			query.setBoolean("desligado", true);
		if (notUsuarioId != null) 
			query.setLong("notUsuarioId", notUsuarioId);
			
		query.setDate("hoje", new Date());
		
		if(areaOrganizacionalIds != null && !areaOrganizacionalIds.isEmpty())
			query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, Hibernate.LONG);
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Colaborador findByIdProjectionEmpresa(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.candidato.id"), "candidatoId");
		p.add(Projections.property("c.naoIntegraAc"), "naoIntegraAc");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nitRepresentanteLegal"), "projectionNitRepresentanteLegal");
		p.add(Projections.property("e.representanteLegal"), "projectionRepresentanteLegal");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}
	
	public Collection<TurnOver> countAdmitidosDemitidosPeriodoTurnover(Date dataIni, Date dataFim, Empresa empresa, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, boolean isAdmitidos)
	{
		StringBuilder sql = new StringBuilder();
		String coluna = isAdmitidos ? "c.dataAdmissao" : "c.dataDesligamento";
		
		sql.append("select cast(date_part('month', " + coluna + ") as int) as mes, cast(date_part('year', " + coluna + ") as int) as ano, cast(count(distinct c.id) as double precision) as qtd from historicoColaborador as hc ");
		sql.append("join colaborador c on hc.colaborador_id = c.id ");
		sql.append("join faixaSalarial fs on hc.faixasalarial_id = fs.id ");
		
		if (empresa.isTurnoverPorSolicitacao())
		{
			if (isAdmitidos)
			{
				sql.append("inner join candidatosolicitacao cs on c.candidato_id = cs.candidato_id ");
				sql.append("inner join solicitacao s on cs.solicitacao_id = s.id ");
				sql.append("inner join motivosolicitacao ms on s.motivosolicitacao_id = ms.id ");
			}
			else
				sql.append("inner join motivodemissao md on c.motivodemissao_id = md.id ");
		}
		
		sql.append("where " + coluna + " between :dataIni and :dataFim "); 
		sql.append("and c.empresa_id= :empresaId ");
		sql.append("and hc.status = :status ");
		
		if(estabelecimentosIds != null && estabelecimentosIds.size() > 0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		if(areasIds != null && areasIds.size() > 0)
			sql.append("and hc.areaOrganizacional_id in (:areasIds) ");
		if(cargosIds != null && cargosIds.size() > 0)
			sql.append("and fs.cargo_id in (:cargosIds) ");
		if(vinculos != null && vinculos.size() > 0)
			sql.append("and c.vinculo in (:vinculos) ");
		
		sql.append("and hc.data = (select max(hc2.data) from historicoColaborador as hc2 ");
		sql.append("where hc2.data <= :dataFim ");
		sql.append("and c.id=hc2.colaborador_id and hc2.status = :status ) ");
		
		if (empresa.isTurnoverPorSolicitacao())
		{
			if (isAdmitidos)
				sql.append("and ms.turnover = true ");
			else
				sql.append("and md.turnover = true ");
		}
		
		sql.append("group by date_part('year', " + coluna + "), date_part('month', " + coluna + ") ");
		sql.append("order by date_part('year', " + coluna + "), date_part('month', " + coluna + ") ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresa.getId());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		
		if(estabelecimentosIds != null && estabelecimentosIds.size() > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		if(areasIds != null && areasIds.size() > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		if(cargosIds != null && cargosIds.size() > 0)
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		if(vinculos != null && vinculos.size() > 0)
			query.setParameterList("vinculos", vinculos, Hibernate.STRING);
		
		List resultado = query.list();
		
		Collection<TurnOver> turnOvers = new ArrayList<TurnOver>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			Date dataMesAno = DateUtil.criarDataMesAno(01, (Integer)res[0], (Integer)res[1]);
			
			TurnOver colabs = new TurnOver();
			if (isAdmitidos)
				colabs.setMesAnoQtdAdmitidos(dataMesAno, (Double)res[2]);
			else
				colabs.setMesAnoQtdDemitidos(dataMesAno, (Double)res[2]);
			
			turnOvers.add(colabs);
		}

		return turnOvers;
	}
	
	public Collection<TurnOver> countDemitidosTempoServico(Empresa empresa, Date dataIni, Date dataFim, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.geral.relatorio.TurnOver(count(co.id) as qtd, (extract(month from age(co.dataDesligamento, co.dataAdmissao)) + (extract(year from age(co.dataDesligamento, co.dataAdmissao)) * 12)) as tempoServico) from Colaborador co ");
		hql.append("	inner join co.historicoColaboradors hc ");
		hql.append("	inner join hc.faixaSalarial fs ");
		if (empresa.isTurnoverPorSolicitacao())
		{
			hql.append("	inner join co.motivoDemissao md ");
		}
		hql.append("	where co.empresa.id = :empresaId ");
		hql.append("	and (co.dataDesligamento between :dataIni and :dataFim)  ");
		hql.append("    and hc.data = ( ");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("		and hc2.status = :status ");
		hql.append("   ) ");

		if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			hql.append("and hc.estabelecimento.id in (:estabelecimentosIds) ");
		
		if (areasIds != null && !areasIds.isEmpty())
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");

		if (cargosIds != null && !cargosIds.isEmpty())
			hql.append("and fs.cargo.id in (:cargosIds) ");

		if (vinculos != null && !vinculos.isEmpty())
			hql.append("and co.vinculo in (:vinculos) ");

		if (empresa.isTurnoverPorSolicitacao()) {
			hql.append("and md.turnover = true ");
		}
		
		hql.append("group by (extract(month from age(co.dataDesligamento, co.dataAdmissao)) + (extract(year from age(co.dataDesligamento, co.dataAdmissao)) * 12)) "); 
		hql.append("order by (extract(month from age(co.dataDesligamento, co.dataAdmissao)) + (extract(year from age(co.dataDesligamento, co.dataAdmissao)) * 12)) "); 
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresa.getId());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		if (areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
			
		if (cargosIds != null && !cargosIds.isEmpty())
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		
		if (vinculos != null && !vinculos.isEmpty())
			query.setParameterList("vinculos", vinculos, Hibernate.STRING);
		
		return (Collection<TurnOver>) query.list();
	}
	
	public Integer countColaboradoresPorTempoServico(Empresa empresa, Integer tempoServicoIniEmMeses, Integer tempoServicoFimEmMeses, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos)
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select count(*) ");
		sql.append("from Colaborador as c ");
		sql.append("	inner join historicoColaborador as hc on hc.colaborador_id = c.id ");
		sql.append("	inner join faixaSalarial as fs on fs.id = hc.faixasalarial_id ");
		sql.append("where (extract(month from age(current_date, c.dataAdmissao)) + (extract(year from age(current_date, c.dataAdmissao)) * 12)) between :tempoServicoIniEmMeses and :tempoServicoFimEmMeses ");
		sql.append("   and c.desligado = false ");
		sql.append("   and c.empresa_id = :empresaId ");
		sql.append("   and hc.data = ( ");
		sql.append("		select max(hc2.data) ");
		sql.append("		from HistoricoColaborador as hc2 ");
		sql.append("		where hc2.colaborador_id = c.id ");
		sql.append("		and hc2.status = :status ");
		sql.append("   ) ");
		
		if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		
		if (areasIds != null && !areasIds.isEmpty())
			sql.append("and hc.areaOrganizacional_id in (:areasIds) ");
		
		if (cargosIds != null && !cargosIds.isEmpty())
			sql.append("and fs.cargo_id in (:cargosIds) ");
		
		if (vinculos != null && !vinculos.isEmpty())
			sql.append("and c.vinculo in (:vinculos) ");

		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("tempoServicoIniEmMeses", tempoServicoIniEmMeses);
		query.setInteger("tempoServicoFimEmMeses", tempoServicoFimEmMeses);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresa.getId());
		
		if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		if (areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if (cargosIds != null && !cargosIds.isEmpty())
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		
		if (vinculos != null && !vinculos.isEmpty())
			query.setParameterList("vinculos", vinculos, Hibernate.STRING);
		
		Collection count = query.list();

		return ((BigInteger) count.toArray()[0]).intValue();
	}
	
	public Collection<Colaborador> findDemitidosTurnover(Empresa empresa, Date dataIni, Date dataFim, Integer[] tempoServicoIni, Integer[] tempoServicoFim, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos) 
	{
		StringBuilder hql = new StringBuilder("select new Colaborador(co.id, co.nome, co.nomeComercial, co.dataAdmissao, co.dataDesligamento, (extract(month from age(co.dataDesligamento, co.dataAdmissao)) + (extract(year from age(co.dataDesligamento, co.dataAdmissao)) * 12)) as tempoServico) from Colaborador co ");
		hql.append("	inner join co.historicoColaboradors hc ");
		hql.append("	inner join hc.faixaSalarial fs ");
		if (empresa.isTurnoverPorSolicitacao())
		{
			hql.append("	inner join co.motivoDemissao md ");
		}
		hql.append("	where co.empresa.id = :empresaId ");
		hql.append("	and (co.dataDesligamento between :dataIni and :dataFim)  ");
		hql.append("    and hc.data = ( ");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("		and hc2.status = :status ");
		hql.append("   ) ");

		if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			hql.append("and hc.estabelecimento.id in (:estabelecimentosIds) ");

		if (areasIds != null && !areasIds.isEmpty())
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");

		if (cargosIds != null && !cargosIds.isEmpty())
			hql.append("and fs.cargo.id in (:cargosIds) ");

		if (vinculos != null && !vinculos.isEmpty())
			hql.append("and co.vinculo in (:vinculos) ");

		if (empresa.isTurnoverPorSolicitacao())
			hql.append("and md.turnover = true ");
		
		if (tempoServicoIni != null && tempoServicoFim != null && tempoServicoIni.length == tempoServicoFim.length)
		{
			hql.append(" and ( ");
			for (int i = 0; i < tempoServicoIni.length; i++)
			{
				hql.append(" (extract(month from age(co.dataDesligamento, co.dataAdmissao)) + (extract(year from age(co.dataDesligamento, co.dataAdmissao)) * 12)) between " + tempoServicoIni[i] + " and " + tempoServicoFim[i]);
				if (i != tempoServicoIni.length - 1)
					hql.append(" or ");	
			}
			hql.append(" ) ");
		}
		
		hql.append("order by (extract(month from age(co.dataDesligamento, co.dataAdmissao)) + (extract(year from age(co.dataDesligamento, co.dataAdmissao)) * 12)) "); 
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresa.getId());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);

		if (areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
			
		if (cargosIds != null && !cargosIds.isEmpty())
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		
		if (vinculos != null && !vinculos.isEmpty())
			query.setParameterList("vinculos", vinculos, Hibernate.STRING);
		
		return (Collection<Colaborador>) query.list();
	}
	
	public Integer countAtivosPeriodo(Date dataIni, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, Collection<Long> ocorrenciasIds, boolean consideraDataAdmissao, Long colaboradorId, boolean isAbsenteismo) 
	{
		StringBuilder hql = new StringBuilder("select count(co.id) from Colaborador co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		
		hql.append("	where ( co.dataDesligamento is null ");
		hql.append("          or co.dataDesligamento > :data ) ");//desligado no futuro
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			hql.append("	and co.empresa.id in (:empresaIds) ");
		
		if(colaboradorId != null)
			hql.append("	and co.id <> :colaboradorId ");
		if(consideraDataAdmissao)
			hql.append("	and co.dataAdmissao <= :data ");
		
		
		if(estabelecimentosIds != null && estabelecimentosIds.size() > 0)
			hql.append("	and hc.estabelecimento.id in (:estabelecimentosIds) ");
		if(areasIds != null && areasIds.size() > 0)
			hql.append("	and hc.areaOrganizacional.id in (:areasIds) ");
		if(cargosIds != null && cargosIds.size() > 0)
			hql.append("	and fs.cargo.id in (:cargosIds) ");
		if(vinculos != null && vinculos.size() > 0)
			hql.append("	and co.vinculo in (:vinculos) ");
		
		hql.append("	and hc.status = :status ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", dataIni);
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			query.setParameterList("empresaIds", empresaIds, Hibernate.LONG);
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(colaboradorId != null)
			query.setLong("colaboradorId", colaboradorId);
		
		if(estabelecimentosIds != null && estabelecimentosIds.size() > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		if(areasIds != null && areasIds.size() > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		if(cargosIds != null && cargosIds.size() > 0)
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		if(vinculos != null && vinculos.size() > 0)
			query.setParameterList("vinculos", vinculos, Hibernate.STRING);

		return (Integer) query.uniqueResult();
	}
	
	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String agruparPor, String vinculo)
	{
		Query query = findColaboradores(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, MOTIVODEMISSAO, agruparPor, vinculo);

		return query.list();
	}

	public List<Object[]> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String vinculo)
	{
		Query query = findColaboradores(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, MOTIVODEMISSAOQUANTIDADE, null, vinculo);

		return query.list();
	}

	private Query findColaboradores(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, int origem, String agruparPor, String vinculo)
	{

		StringBuilder hql = new StringBuilder();

		if(origem == MOTIVODEMISSAO)
			hql.append("select new Colaborador(co.id, co.nome, co.matricula, co.dataAdmissao, co.dataDesligamento, co.observacaoDemissao, co.vinculo, mo.id, mo.motivo, cg.nome, fs.nome, es.id, es.nome, ao.id, ao.nome, mo.turnover) ");
		else if(origem == MOTIVODEMISSAOQUANTIDADE)
			hql.append("select mo.motivo, mo.turnover, count(mo.motivo) ");

		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.areaOrganizacional as ao ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join co.motivoDemissao as mo ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");
		hql.append("where ");
		hql.append("	( ");
		hql.append("		hc1.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		hql.append("	and ( co.dataDesligamento between :dataIni and :dataFim )");

		if(vinculo != null && !vinculo.equals("") )
			hql.append(" and co.vinculo = :vinculo ");
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append(" and es.id in (:estabelecimentoIds) ");

		if(areaIds != null && areaIds.length > 0)
			hql.append(" and ao.id in (:areaIds) ");

		if(cargoIds != null && cargoIds.length > 0)
			hql.append(" and cg.id in (:cargoIds) ");

		// Ordenação
		if(origem == MOTIVODEMISSAO){
			if (agruparPor.equals("E")){
				hql.append("order by es.nome, co.nome ");
			} else if (agruparPor.equals("M")) {
				hql.append("order by mo.motivo, co.nome ");
			} else if (agruparPor.equals("A")) {
				hql.append("order by ao.nome, co.nome ");
			} else {
				hql.append("order by co.nome ");
			}
		} else if(origem == MOTIVODEMISSAOQUANTIDADE)
			hql.append(" group by mo.motivo, mo.turnover order by mo.motivo  ");

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("hoje", new Date());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(vinculo != null && !vinculo.equals("") )
			query.setString("vinculo", vinculo);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if(cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		return query;
	}

	public boolean desligaByCodigo(Empresa empresa, Date data, String... codigosAC)
	{
		String hql = "update Colaborador set dataDesligamento = :data, dataSolicitacaoDesligamentoAc = null, " +
					"desligado = :valor where codigoac in (:codigos) and empresa = :emp";

		Query query = getSession().createQuery(hql);
		query.setDate("data", data);
		query.setBoolean("valor", data != null ? true : false);
		query.setParameterList("codigos", codigosAC, Hibernate.STRING);
		query.setEntity("emp", empresa);
		int result = query.executeUpdate();

		return result > 0 ? true : false;
	}

	public void desligaColaborador(Boolean desligado, Date dataDesligamento, String observacaoDemissao, Long motivoDemissaoId, Character gerouSubstituicao, Long... colaboradoresIds)
	{
		StringBuffer hql = new StringBuffer("update Colaborador set  ");
		
		if(desligado != null && dataDesligamento != null)		
			hql.append("desligado = :desligado, dataDesligamento = :data, ");
		
		hql.append("observacaoDemissao = :observacaoDemissao, motivoDemissao.id = :motivoDemissaoId, demissaoGerouSubstituicao = :gerouSubstituicao where id in (:colaboradoresIds) ");

		Query query = getSession().createQuery(hql.toString());

		if(desligado != null && dataDesligamento != null)
		{
			query.setBoolean("desligado", desligado);
			query.setDate("data", dataDesligamento);
		}
		
		query.setString("observacaoDemissao", observacaoDemissao);
		query.setLong("motivoDemissaoId", motivoDemissaoId);
		query.setParameter("gerouSubstituicao", gerouSubstituicao, Hibernate.CHARACTER);
		query.setParameterList("colaboradoresIds", colaboradoresIds);

		query.executeUpdate();
	}

	public void religaColaborador(Long colaboradorId)
	{
		StringBuilder hql = new StringBuilder("update Colaborador set ");
		hql.append("    desligado = false, ");
		hql.append("    dataDesligamento = null, ");
		hql.append("    dataSolicitacaoDesligamento = null, ");
		hql.append("    dataSolicitacaoDesligamentoAC = null, ");
		hql.append("    observacaoDemissao = :observacaoDemissao, ");
		hql.append("    demissaoGerouSubstituicao = null, ");
		hql.append("    motivoDemissao.id = null ");
		hql.append("where id = :colaboradorId");
		
		Query query = getSession().createQuery(hql.toString());

		query.setString("observacaoDemissao", "");
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}

	public Collection<Colaborador> findProjecaoSalarialByHistoricoColaborador(Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds,
			Collection<Long> cargoIds, String filtro, Long empresaId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.id, co.nome, es.id, es.nome, ao.id, ao.nome, fs.nome, ca.nome, hc.tipoSalario, hc.salario, ");
		hql.append("                       hc.quantidadeIndice, hc.status, hcih.valor, fsh.tipo, fsh.valor, fsh.quantidade, fsh.status, fshih.valor, ao.areaMae.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.indice as hci ");
		hql.append("left join hci.indiceHistoricos as hcih with hcih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = hci.id and ih2.data <= :data) ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ca.grupoOcupacional as go ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data and fsh3.status = :statusConfirmado) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");
		hql.append("	where ");
		hql.append("	   ( ");
		hql.append("	    	hc.data = (");
		hql.append("				select max(hc2.data) ");
		hql.append("				from HistoricoColaborador as hc2 ");
		hql.append("				where hc2.colaborador.id = co.id ");
		hql.append("					and hc2.data <= :data ");
		hql.append("					and hc2.status = :statusConfirmado");
		hql.append("			) ");
		hql.append("	 		or hc.data is null ");
		hql.append("	   ) ");
		hql.append("	and ( date_trunc('month', co.dataDesligamento) >= date_trunc('month', cast(:data as date)) or co.dataDesligamento is null) ");
		hql.append("	and co.dataAdmissao <= :data");
		hql.append("	and (co.dataDesligamento >= :data or co.dataDesligamento is null) ");
		hql.append("	and co.empresa.id = :empresaId ");

		Query query = montaSelectProjecaoSalarial(hql, data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresaId);

		return query.list();
	}

	/*
	 * Este método está setando um valor fixo -1 para o valor que representa o
	 * status do HistoricoColaborador no construtor. A razão para isto é porque
	 * o ReajusteColaborador não possui a propriedade "status" e o construtor
	 * utilizado é o mesmo que o do método
	 * "findProjecaoSalarialByHistoricoColaborador" utiliza.
	 */
	public Collection<Colaborador> findProjecaoSalarialByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds,
			Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.id, co.nome, es.id, es.nome, ao.id, ao.nome, fs.nome, ca.nome, rc.tipoSalarioProposto, rc.salarioProposto, ");
		hql.append("                       rc.quantidadeIndiceProposto, -1, ih.valor, fsh.tipo, fsh.valor, fsh.quantidade, fsh.status, fshih.valor, ao.areaMae.id) ");
		hql.append("from TabelaReajusteColaborador as trc ");
		hql.append("left join trc.reajusteColaboradors as rc ");
		hql.append("left join rc.estabelecimentoProposto as es ");
		hql.append("left join rc.areaOrganizacionalProposta as ao ");
		hql.append("left join rc.colaborador as co ");
		hql.append("left join rc.indiceProposto as i ");
		hql.append("left join i.indiceHistoricos as ih with ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = i.id and ih2.data <= :data) ");
		hql.append("left join rc.faixaSalarialProposta as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ca.grupoOcupacional as go ");
		hql
				.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data and fsh3.status = :statusConfirmado) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");
		hql.append(" where ");
		hql.append("	trc.id = :tabelaReajusteColaboradorId ");
		hql.append("	and rc.colaborador.id = co.id ");
		hql.append("	and trc.aprovada = false ");
		hql.append("	and (co.dataDesligamento >= :data or co.dataDesligamento is null) ");
		hql.append("	and co.empresa.id = :empresaId ");

		Query query = montaSelectProjecaoSalarial(hql, data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresaId);

		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaboradorId);

		return query.list();
	}

	private Query montaSelectProjecaoSalarial(StringBuilder hql, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds,
			String filtro, Long empresaId)
	{
		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			hql.append(" and es.id in (:estabelecimentoIds) ");

		if(Integer.parseInt(filtro) == AREA_ORGANIZACIONAL && areaIds != null && !areaIds.isEmpty())
			hql.append(" and ao.id in (:areaIds) ");
		else if(Integer.parseInt(filtro) == GRUPO_OCUPACIONAL && grupoIds != null && !grupoIds.isEmpty())
		{
			hql.append(" and go.id in (:grupoIds) ");

			if(cargoIds != null && !cargoIds.isEmpty())
				hql.append(" and ca.id in (:cargoIds) ");
		}

		// Ordenação
		hql.append(" order by es.nome, ao.nome, co.nome ");

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("data", data);

		if(estabelecimentoIds != null && !estabelecimentoIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(Integer.parseInt(filtro) == AREA_ORGANIZACIONAL && areaIds != null && !areaIds.isEmpty())
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
		else if(Integer.parseInt(filtro) == GRUPO_OCUPACIONAL && grupoIds != null && !grupoIds.isEmpty())
		{
			query.setParameterList("grupoIds", grupoIds, Hibernate.LONG);

			if(cargoIds != null && !cargoIds.isEmpty())
				query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);
		}

		query.setLong("empresaId", empresaId);
		query.setInteger("statusConfirmado", StatusRetornoAC.CONFIRMADO);

		return query;
	}

	public void setRespondeuEntrevista(Long colaboradorId)
	{
		String hql = "update Colaborador set  respondeuEntrevista = :respondeuEntrevista where id = :colaboradorId";

		Query query = getSession().createQuery(hql);

		query.setBoolean("respondeuEntrevista", true);
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}

	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula)
	{
		String hql = "update Colaborador set matricula = :matricula where codigoac = :codigoac and empresa.id = :empresaId";

		Query query = getSession().createQuery(hql);

		query.setString("codigoac", codigoAC);
		query.setLong("empresaId", empresaId);
		query.setString("matricula", matricula);

		int result = query.executeUpdate();
		return result == 1;
	}

	public boolean setMatriculaColaborador(Long colaboradorId, String matricula)
	{
		String hql = "update Colaborador set matricula = :matricula where id = :colaboradorId";

		Query query = getSession().createQuery(hql);

		query.setLong("colaboradorId", colaboradorId);
		query.setString("matricula", matricula);

		int result = query.executeUpdate();
		return result == 1;
	}
	
	public Colaborador findByIdComHistorico(Long colaboradorId, Integer statusRetornoAC)
	{
		StringBuilder hql = new StringBuilder();

		montaSelectFindById(hql);

		hql.append("	where co.id = :id ");
		hql.append("	and (hc.data = ");
		hql.append("		  (select max(hc2.data) ");
		hql.append("		   from HistoricoColaborador as hc2 ");
		hql.append("		   where hc2.colaborador.id = co.id ");
		//hql.append("			     and hc2.data <= :hoje " );
		
		if (statusRetornoAC != null)
			hql.append("  				 and hc2.status = :statusHistColab ");
		
		hql.append("       ) or ");
		hql.append("          (select count(*) from HistoricoColaborador as hc3 where hc3.colaborador.id=co.id) = 1 ");
		hql.append("	   ) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("id", colaboradorId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		
		if (statusRetornoAC != null)
			query.setInteger("statusHistColab", statusRetornoAC);
		
		return (Colaborador) query.uniqueResult();
	}

	public void updateInfoPessoais(Colaborador colaborador)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("update Colaborador set ");
		hql.append(" endereco.logradouro = :logradouro ,");
		hql.append(" endereco.numero = :numero ,");
		hql.append(" endereco.complemento = :complemento ,");
		hql.append(" endereco.uf.id = :ufId ,");
		hql.append(" endereco.cidade.id = :cidadeId ,");
		hql.append(" endereco.bairro = :bairro ,");
		hql.append(" endereco.cep = :cep ,");
		hql.append(" contato.email = :email ,");
		hql.append(" contato.ddd = :ddd ,");
		hql.append(" contato.foneFixo = :foneFixo ,");
		hql.append(" contato.foneCelular = :foneCelular ,");
		hql.append(" pessoal.escolaridade = :escolaridade ,");
		hql.append(" pessoal.estadoCivil = :estadoCivil ,");
		hql.append(" pessoal.pai = :pai ,");
		hql.append(" pessoal.mae = :mae ,");
		hql.append(" pessoal.conjuge = :conjuge ,");
		hql.append(" pessoal.qtdFilhos = :qtdFilhos ,");
		hql.append(" cursos = :cursos ,");
		hql.append(" observacao = :observacao ,");
		hql.append(" dataAtualizacao = :dataAtualizacao , ");
		hql.append(" pessoal.rg = :rg , ");
		hql.append(" pessoal.rgOrgaoEmissor = :rgOrgaoEmissor , ");
		hql.append(" pessoal.rgUf.id = :rgUfId , ");
		hql.append(" pessoal.rgDataExpedicao = :rgDataExpedicao , ");
		hql.append(" habilitacao.numeroHab = :numeroHab , ");
		hql.append(" habilitacao.registro = :habilitacaoRegistro , ");
		hql.append(" habilitacao.emissao = :habilitacaoEmissao , ");
		hql.append(" habilitacao.vencimento = :habilitacaoVencimento , ");
		hql.append(" habilitacao.categoria = :habilitacaoCategoria , ");
		hql.append(" pessoal.tituloEleitoral.titEleitNumero = :titEleitNumero , ");
		hql.append(" pessoal.tituloEleitoral.titEleitZona = :titEleitZona , ");
		hql.append(" pessoal.tituloEleitoral.titEleitSecao = :titEleitSecao , ");
		hql.append(" pessoal.certificadoMilitar.certMilNumero = :certMilNumero , ");
		hql.append(" pessoal.certificadoMilitar.certMilTipo = :certMilTipo , ");
		hql.append(" pessoal.certificadoMilitar.certMilSerie = :certMilSerie , ");
		hql.append(" pessoal.ctps.ctpsNumero = :ctpsNumero , ");
		hql.append(" pessoal.ctps.ctpsSerie = :ctpsSerie , ");
		hql.append(" pessoal.ctps.ctpsDv = :ctpsDv , ");
		hql.append(" pessoal.ctps.ctpsUf.id = :ctpsUfId , ");
		hql.append(" pessoal.ctps.ctpsDataExpedicao = :ctpsDataExpedicao , ");
		hql.append(" pessoal.pis = :pis ");
		hql.append(" where id = :id ");

		Query query = getSession().createQuery(hql.toString());

		query.setString("logradouro", colaborador.getEndereco().getLogradouro());
		query.setString("numero", colaborador.getEndereco().getNumero());
		query.setString("complemento", colaborador.getEndereco().getComplemento());

		if(colaborador.getEndereco().getUf() != null && colaborador.getEndereco().getUf().getId() != null)
			query.setLong("ufId", colaborador.getEndereco().getUf().getId());
		else
			query.setParameter("ufId", null, Hibernate.LONG);

		if(colaborador.getEndereco().getCidade() != null && colaborador.getEndereco().getCidade().getId() != null)
			query.setLong("cidadeId", colaborador.getEndereco().getCidade().getId());
		else
			query.setParameter("cidadeId", null, Hibernate.LONG);

		query.setString("bairro", colaborador.getEndereco().getBairro());
		query.setString("cep", colaborador.getEndereco().getCep());
		query.setString("email", colaborador.getContato().getEmail());
		query.setString("ddd", colaborador.getContato().getDdd());
		query.setString("foneFixo", colaborador.getContato().getFoneFixo());
		query.setString("foneCelular", colaborador.getContato().getFoneCelular());
		query.setString("escolaridade", colaborador.getPessoal().getEscolaridade());
		query.setString("estadoCivil", colaborador.getPessoal().getEstadoCivil());
		query.setString("pai", colaborador.getPessoal().getPai());
		query.setString("mae", colaborador.getPessoal().getMae());
		query.setString("conjuge", colaborador.getPessoal().getConjuge());
		query.setInteger("qtdFilhos", colaborador.getPessoal().getQtdFilhos());
		query.setString("cursos", colaborador.getCursos());
		query.setString("observacao", colaborador.getObservacao());
		query.setDate("dataAtualizacao", colaborador.getDataAtualizacao());
		query.setString("rg", colaborador.getPessoal().getRg());
		query.setString("rgOrgaoEmissor", colaborador.getPessoal().getRgOrgaoEmissor());

		if(colaborador.getPessoal().getRgUf() != null && colaborador.getPessoal().getRgUf().getId() != null)
			query.setLong("rgUfId", colaborador.getPessoal().getRgUf().getId());
		else
			query.setParameter("rgUfId", null, Hibernate.LONG);
		
		query.setDate("rgDataExpedicao", colaborador.getPessoal().getRgDataExpedicao());
		query.setString("numeroHab", colaborador.getHabilitacao().getNumeroHab());
		query.setString("habilitacaoRegistro", colaborador.getHabilitacao().getRegistro());
		query.setDate("habilitacaoEmissao", colaborador.getHabilitacao().getEmissao());
		query.setDate("habilitacaoVencimento", colaborador.getHabilitacao().getVencimento());
		query.setString("habilitacaoCategoria", colaborador.getHabilitacao().getCategoria());
		query.setString("titEleitNumero", colaborador.getPessoal().getTituloEleitoral().getTitEleitNumero());
		query.setString("titEleitZona", colaborador.getPessoal().getTituloEleitoral().getTitEleitZona());
		query.setString("titEleitSecao", colaborador.getPessoal().getTituloEleitoral().getTitEleitSecao());
		query.setString("certMilNumero", colaborador.getPessoal().getCertificadoMilitar().getCertMilNumero());
		query.setString("certMilTipo", colaborador.getPessoal().getCertificadoMilitar().getCertMilTipo());
		query.setString("certMilSerie", colaborador.getPessoal().getCertificadoMilitar().getCertMilSerie());
		query.setString("ctpsNumero", colaborador.getPessoal().getCtps().getCtpsNumero());
		query.setString("ctpsSerie", colaborador.getPessoal().getCtps().getCtpsSerie());
		
		if(colaborador.getPessoal().getCtps().getCtpsDv() != null)
			query.setCharacter("ctpsDv", colaborador.getPessoal().getCtps().getCtpsDv());
		else{
			query.setCharacter("ctpsDv", ' ');
		}
		
		
		if(colaborador.getPessoal().getCtps().getCtpsUf() != null && colaborador.getPessoal().getCtps().getCtpsUf().getId() != null)
			query.setLong("ctpsUfId", colaborador.getPessoal().getCtps().getCtpsUf().getId());
		else
			query.setParameter("ctpsUfId", null, Hibernate.LONG);
		
		query.setDate("ctpsDataExpedicao", colaborador.getPessoal().getCtps().getCtpsDataExpedicao());
		query.setString("pis", colaborador.getPessoal().getPis());
		query.setLong("id", colaborador.getId());

		query.executeUpdate();
	}

	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("update Colaborador set ");
		hql.append(" matricula = :matricula ");
		if(StringUtils.isNotBlank(colaborador.getEndereco().getLogradouro()))
			hql.append(", endereco.logradouro = :logradouro ");
		if(StringUtils.isNotBlank(colaborador.getEndereco().getNumero()))
			hql.append(", endereco.numero = :numero ");
		if(StringUtils.isNotBlank(colaborador.getEndereco().getComplemento()))
			hql.append(", endereco.complemento = :complemento ");
		if(colaborador.getEndereco().getUf() != null && colaborador.getEndereco().getUf().getId() != null)
			hql.append(", endereco.uf.id = :ufId ");
		if(colaborador.getEndereco().getCidade() != null && colaborador.getEndereco().getCidade().getId() != null)
			hql.append(", endereco.cidade.id = :cidadeId ");
		if(StringUtils.isNotBlank(colaborador.getEndereco().getBairro()))
			hql.append(", endereco.bairro = :bairro ");
		if(StringUtils.isNotBlank(colaborador.getEndereco().getCep()))
			hql.append(", endereco.cep = :cep ");
		if(StringUtils.isNotBlank(colaborador.getContato().getDdd()))
			hql.append(", contato.ddd = :ddd ");
		if(StringUtils.isNotBlank(colaborador.getContato().getFoneFixo()))
			hql.append(", contato.foneFixo = :foneFixo ");
		if(StringUtils.isNotBlank(colaborador.getContato().getFoneCelular()))
			hql.append(", contato.foneCelular = :foneCelular ");
		if(StringUtils.isNotBlank(colaborador.getPessoal().getEscolaridade()))
			hql.append(", pessoal.escolaridade = :escolaridade ");

		hql.append(" where cpf = :cpf ");
		hql.append(" and empresa.id = :empresaId ");

		Query query = getSession().createQuery(hql.toString());

		if(StringUtils.isNotBlank(colaborador.getEndereco().getLogradouro()))
			query.setString("logradouro", colaborador.getEndereco().getLogradouro());
		if(StringUtils.isNotBlank(colaborador.getEndereco().getNumero()))
			query.setString("numero", colaborador.getEndereco().getNumero());
		if(StringUtils.isNotBlank(colaborador.getEndereco().getComplemento()))
			query.setString("complemento", colaborador.getEndereco().getComplemento());

		if(colaborador.getEndereco().getUf() != null && colaborador.getEndereco().getUf().getId() != null)
			query.setLong("ufId", colaborador.getEndereco().getUf().getId());

		if(colaborador.getEndereco().getCidade() != null && colaborador.getEndereco().getCidade().getId() != null)
			query.setLong("cidadeId", colaborador.getEndereco().getCidade().getId());

		query.setString("matricula", colaborador.getMatricula());

		if(StringUtils.isNotBlank(colaborador.getEndereco().getBairro()))
			query.setString("bairro", colaborador.getEndereco().getBairro());

		if(StringUtils.isNotBlank(colaborador.getEndereco().getCep()))
			query.setString("cep", colaborador.getEndereco().getCep());
		if(StringUtils.isNotBlank(colaborador.getContato().getDdd()))
			query.setString("ddd", colaborador.getContato().getDdd());
		if(StringUtils.isNotBlank(colaborador.getContato().getFoneFixo()))
			query.setString("foneFixo", colaborador.getContato().getFoneFixo());
		if(StringUtils.isNotBlank(colaborador.getContato().getFoneCelular()))
			query.setString("foneCelular", colaborador.getContato().getFoneCelular());
		if(StringUtils.isNotBlank(colaborador.getPessoal().getEscolaridade()))
			query.setString("escolaridade", colaborador.getPessoal().getEscolaridade());
		query.setString("cpf", colaborador.getPessoal().getCpf());
		query.setLong("empresaId", empresaId);

		return query.executeUpdate() == 1;
	}

	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.codigoAC", empregadoCodigoAC));
		criteria.add(Expression.eq("e.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("e.grupoAC", grupoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Long findByUsuario(Long usuarioId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.usuario.id", usuarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Long) criteria.uniqueResult();
	}
	
	public Colaborador findByUsuarioProjection(Long usuarioId, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.usuario", "u", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("u.nome"), "usuarioNomeProjection");
		p.add(Projections.property("u.acessoSistema"), "usuarioAcessoSistema");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("u.id", usuarioId));
		
		if(ativo != null)
			criteria.add(Expression.eq("u.acessoSistema", ativo));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return (Colaborador) criteria.uniqueResult();
	}

	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC, String grupoAC)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select c ");
		hql.append("from Colaborador as c ");
		hql.append("left join c.empresa as emp  ");
		hql.append("	where c.codigoAC = :codigoAC and emp.codigoAC = :empresaCodigoAC and emp.grupoAC = :grupoAC ");

		Query query = getSession().createQuery(hql.toString());
		query.setString("codigoAC", codigoAC);
		query.setString("empresaCodigoAC", empresaCodigoAC);
		query.setString("grupoAC", grupoAC);

		return (Colaborador) query.uniqueResult();
	}

	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));

		criteria.addOrder(Order.asc(ordenarPor));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}
	
	//a ordenação tem que ser empresa nome e colaborador nome, usado no DWR 
	public Collection<Colaborador> findAllSelect(String situacao, Long notUsuarioId, Long... empresaIds)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		criteria.setProjection(p);
		
		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if(situacao.equals(SituacaoColaborador.ATIVO))
			criteria.add(Expression.eq("c.desligado", false));
		else if(situacao.equals(SituacaoColaborador.DESLIGADO))
			criteria.add(Expression.eq("c.desligado", true));
		
		if(notUsuarioId != null)
			criteria.add(Expression.or(Expression.ne("c.usuario.id", notUsuarioId), Expression.isNull("c.usuario.id")));
			
		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("c.nomeComercial"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return criteria.list();
	}

	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.id", colaboradorIds));
		
		if (colabDesligado != null)
			criteria.add(Expression.eq("c.desligado", colabDesligado ));

		criteria.addOrder(Order.asc("nomeComercial"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<Colaborador> findComNotaDoCurso(Collection<Long> colaboradorIds, Long turmaId)
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select c.id, c.nome, c.nomeComercial, v_cn.nota, v_cn.qtdAvaliacoesCurso ");
		sql.append("from Colaborador as c ");
		sql.append("left join View_CursoNota v_cn on v_cn.colaborador_id = c.id and v_cn.turma_id = "+ turmaId+" ");
		sql.append("where c.id in (:colaboradorIds) ");
		sql.append("   and c.desligado = false ");

		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("colaboradorIds", colaboradorIds);
		
		Collection<Colaborador> colaboradores = new LinkedList<Colaborador>();
		Collection lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Colaborador colaborador = new Colaborador();
			colaborador.setId( ((BigInteger) array[0]).longValue() );
			colaborador.setNome((String) array[1]);
			colaborador.setNomeComercial((String) array[2]);
			if(array[3] != null && ((BigInteger)array[4]).intValue() == 1 ) // array[4] é qtdAvaliacoesCurso, e se tiver mais de uma avaliação para o curso a nota deve ficar nula.
				colaborador.setNota((BigDecimal.valueOf((Double)array[3])));

			colaboradores.add(colaborador);
		}

		return colaboradores;
	}
	
	public Integer getCountAtivosQualquerStatus(Date dataBase, Long[] empresaIds, Long[] areasIds, Long[] estabelecimentosIds)
	{
		StringBuilder hql = new StringBuilder("select count(c.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as c ");
		hql.append("where c.empresa.id in (:empresaIds) ");
		hql.append("		and hc.data = ( ");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = c.id ");
		hql.append("			and hc2.data <= :dataBase ");
		hql.append("		) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			hql.append("and hc.estabelecimento.id in (:estabelecimentosIds) ");
		
		if (dataBase != null)
		{
			hql.append("and c.dataAdmissao < :dataBase ");
			hql.append("and (c.dataDesligamento = null or c.dataDesligamento > :dataBase) ");
		}

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("empresaIds", empresaIds);

		if (dataBase != null)
			query.setDate("dataBase", dataBase);
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds);
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);

		return (Integer) query.uniqueResult();
	}

	public Integer countAdmitidosDemitidosTurnover(Date dataIni, Date dataFim, Empresa empresa, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, boolean isAdmitidos)
	{
		String campo = isAdmitidos ? "c.dataAdmissao" : "c.dataDesligamento";
		
		StringBuilder hql = new StringBuilder("select count(c.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as c ");
		hql.append("left join hc.faixaSalarial as fs ");
		
		if (empresa.isTurnoverPorSolicitacao())
		{
			if (isAdmitidos)
			{
				hql.append("inner join c.candidato ca ");
				hql.append("inner join ca.candidatoSolicitacaos cs ");
				hql.append("inner join cs.solicitacao s ");
				hql.append("inner join s.motivoSolicitacao ms ");
			}
			else
				hql.append("inner join c.motivoDemissao md ");
		}
		
		hql.append("where c.empresa.id = :empresaId ");
		
		subSelectHistoricoAtual(hql, estabelecimentosIds, areasIds, cargosIds);
		
		hql.append("and " + campo + " between :dataIni and :dataFim ");
		
		if (empresa.isTurnoverPorSolicitacao())
		{
			if (isAdmitidos)
				hql.append("and ms.turnover = true ");
			else
				hql.append("and md.turnover = true ");
		}

		Query query = getSession().createQuery(hql.toString());
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		if(LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);	
		if(LongUtil.arrayIsNotEmpty(cargosIds))
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);	
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresa.getId());
		query.setDate("data", dataFim);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);

		return (Integer) query.uniqueResult();
	}
	
	public int getCountAtivos(Date dataBase, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds) 
	{
		StringBuilder hql = new StringBuilder("select count(c.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("inner join hc.colaborador as c ");
		hql.append("inner join hc.faixaSalarial as fs ");
		hql.append("where (c.dataDesligamento is null or c.dataDesligamento >= :dataBase) ");
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			hql.append("and  c.empresa.id in (:empresaIds) ");
		
		hql.append("and c.dataAdmissao <= :dataBase ");

		subSelectHistoricoAtual(hql, estabelecimentosIds, areasIds, cargosIds);
		
		Query query = getSession().createQuery(hql.toString());
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);	
		if(LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);	
		if(LongUtil.arrayIsNotEmpty(cargosIds))
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);	
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setDate("data", dataBase);
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			query.setParameterList("empresaIds", empresaIds, Hibernate.LONG);
		
		query.setDate("dataBase", dataBase);

		return (Integer) query.uniqueResult();
	}

	public Integer getCountAtivosByEstabelecimento(Long estabelecimentoId)
	{
		StringBuilder hql = new StringBuilder("select count(co.id) from Colaborador co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("	where hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("	and co.desligado = false ");
		hql.append("	and hc.status = :status ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (Integer) query.uniqueResult();
	}

	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.pessoal.dataNascimento, co.id, co.matricula, co.nome, co.nomeComercial, cg.nome, fs.nome, ao.nome, es.nome, ao.id, co.endereco) ");

		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.areaOrganizacional as ao ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join co.motivoDemissao as mo ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");
		hql.append("where ");
		hql.append("	hc1.status = :status ");
		hql.append("	and (hc1.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("		and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("	or hc1.data is null) ");
		if (mes > 0) {
			hql.append("	and (month(co.pessoal.dataNascimento) = :mes) ");
		}
		hql.append("	and co.desligado = false ");

		if(empresaIds != null && empresaIds.length > 0)
			hql.append("	and co.empresa.id in (:empresaIds) ");

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append(" and es.id in (:estabelecimentoIds) ");

		if(areaIds != null && areaIds.length > 0)
			hql.append(" and ao.id in (:areaIds) ");

		hql.append("order by month(co.pessoal.dataNascimento), day(co.pessoal.dataNascimento)");

		Query query = getSession().createQuery(hql.toString());

		if(empresaIds != null && empresaIds.length > 0)
			query.setParameterList("empresaIds", empresaIds, Hibernate.LONG);

		query.setDate("hoje", new Date());
		if (mes > 0) {
			query.setInteger("mes", mes);
		}
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findByCpf(String cpf, Long empresaId, Long colaboradorId, Boolean desligado)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");

		criteria.setProjection(p);
		
		if (StringUtils.isNotBlank(cpf))
			criteria.add(Expression.eq("c.pessoal.cpf", cpf));
		
		if (empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));

		if (colaboradorId != null)
			criteria.add(Expression.ne("c.id", colaboradorId));

		if (desligado != null)
			criteria.add(Expression.eq("c.desligado", desligado));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Boolean somenteAtivos, String[] colabsNaoHomonimoHa, Integer statusRetornoAC, Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		
		if(statusRetornoAC != null){
			criteria.createCriteria("c.historicoColaboradors", "hc");
			DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), statusRetornoAC);
			criteria.add(Property.forName("hc.data").eq(subQueryHc));			
		}
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");

		criteria.setProjection(p);
		
		if(empresaIds != null && empresaIds.length > 0)//Quanto terminar de contemplar empresasPermitidas esse if não será mais necessário.
			criteria.add(Expression.in("c.empresa.id", empresaIds));

		if(colaborador != null && StringUtils.isNotBlank(colaborador.getNome()))
			criteria.add(Expression.like("c.nome", "%" + colaborador.getNome() + "%").ignoreCase());

		if(colaborador != null && StringUtils.isNotBlank(colaborador.getMatricula()))
			criteria.add(Expression.like("c.matricula", "%" + colaborador.getMatricula() + "%").ignoreCase());

		if(colaborador != null && colaborador.getPessoal() != null && StringUtils.isNotBlank(colaborador.getPessoal().getCpf()))
			criteria.add(Expression.like("c.pessoal.cpf", "%" + colaborador.getPessoal().getCpf() + "%").ignoreCase());

		if(somenteAtivos != null && somenteAtivos)
			criteria.add(Expression.eq("c.desligado", false));

		if(colabsNaoHomonimoHa != null && colabsNaoHomonimoHa.length > 0)
			criteria.add(Expression.not(Expression.in("c.nome", colabsNaoHomonimoHa)));

		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Colaborador findByIdHistoricoProjection(Long id)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.id, co.matricula, fs.nome, ca.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");

		hql.append("	where co.id = :id ");
		hql.append("	and ");
		hql.append("	hc.status = :status ");
		hql.append("	and (hc.data = ");
		hql.append("		  (select max(hc2.data) ");
		hql.append("		   from HistoricoColaborador as hc2 ");
		hql.append("		   where hc2.colaborador.id = co.id ");
		hql.append("			     and hc2.data <= :hoje and hc2.status = :status )");
		hql.append("        or ");
		hql.append("          (select count(*) from HistoricoColaborador as hc3 where hc3.colaborador.id=co.id) = 1 ");
		hql.append("	   ) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("id", id);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (Colaborador) query.uniqueResult();
	}

	public Colaborador findByIdDadosBasicos(Long id, Integer statusRetornoAC)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.matricula, co.dataAdmissao, co.dataDesligamento, hc.status, ao, ca, fs, e, hc.estabelecimento.id, fun.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.funcao as fun ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join co.empresa as e ");
		hql.append("	where co.id = :id ");
		
		if(statusRetornoAC != null)	
			hql.append("	and hc.status = :status ");
		
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		
		if(statusRetornoAC != null)	
			hql.append("		 and hc2.status = :status ");
		
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("id", id);
		
		if(statusRetornoAC != null)	
			query.setInteger("status", statusRetornoAC);

		return (Colaborador) query.uniqueResult();
	}
	
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, ");
		hql.append("ao.id, ao.nome, fs.nome, ca.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where co.desligado = false ");
		hql.append("and co.id in (:colaboradorIds) ");
		hql.append("and hc.status = :status ");
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		hql.append("	order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}
	
	public Colaborador findColaboradorByDataHistorico(Long colaboradorId, Date dataHistorico)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(c.id, c.nome, c.nomeComercial, e.id, ");
		hql.append("ao.id, ao.nome, fs.id, fs.nome, ca.id, ca.nome ) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as c ");
		hql.append("left join c.empresa as e ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where c.id = :id ");
		
		subSelectHistoricoAtual(hql, null, null, null);
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("id", colaboradorId);
		query.setDate("data", dataHistorico);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (Colaborador) query.uniqueResult();
	}
	

	public Colaborador findByIdHistoricoAtual(Long colaboradorId, boolean exibirSomenteAtivos)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.matricula, ");
		hql.append("ao.id, ao.nome, fs.nome, ca.nome, co.dataAdmissao, e.nome, co.contato.ddd, co.contato.foneFixo, co.contato.foneCelular) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where co.id = :colaboradorId ");
		if(exibirSomenteAtivos)
			hql.append("and	co.desligado = false ");
		hql.append("and hc.status = :status ");
		montaMaxDataHistColab2ComParametrosHojeStatus(hql);
		hql.append("order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (Colaborador) query.uniqueResult();
	}

	public Integer countSemMotivos(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String vinculo)
	{

		StringBuilder hql = new StringBuilder();
		hql.append("select count(co.id) ");
		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.areaOrganizacional as ao ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join co.motivoDemissao as mo ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");
		hql.append("where ");
		hql.append("		hc1.status = :status ");
		hql.append("		and ( hc1.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		hql.append("	and ( co.dataDesligamento between :dataIni and :dataFim )");
		hql.append("	and mo is null");

		if(vinculo != null && !vinculo.equals("") )
			hql.append(" and co.vinculo = :vinculo ");
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append(" and es.id in (:estabelecimentoIds) ");

		if(areaIds != null && areaIds.length > 0)
			hql.append(" and ao.id in (:areaIds) ");

		if(cargoIds != null && cargoIds.length > 0)
			hql.append(" and cg.id in (:cargoIds) ");

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("hoje", new Date());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(vinculo != null && !vinculo.equals("") )
			query.setString("vinculo", vinculo);
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if(cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		return (Integer) query.uniqueResult();
	}

	public void migrarBairro(String bairro, String bairroDestino)
	{
		String hql = "update Colaborador set bairro = :bairroDestino where bairro = :bairro";

		Query query = getSession().createQuery(hql);
		query.setString("bairro", bairro);
		query.setString("bairroDestino", bairroDestino);

		query.executeUpdate();
	}

	public Collection<String> findEmailsDeColaboradoresByPerfis(Long[] perfilIds, Long empresaId)
	{
		StringBuilder hql = new StringBuilder("select distinct co.contato.email from Colaborador co ");
		hql.append("join co.usuario u ");
		hql.append("join u.usuarioEmpresas ue ");
		hql.append("where ue.empresa.id = :empresaId ");
		hql.append("and ue.perfil.id in (:perfilIds) ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setParameterList("perfilIds", perfilIds);

		return query.list();
	}	

	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa, Long periodoExperienciaId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct co.id as coid, co.nome as conome, co.nomeComercial as conomecomercial, co.matricula as comatricula, ");
		sql.append("cg.nome as cgnome, fs.nome as fsnome, ao.id as aoid, ao.nome as aonome, am.id as amid, am.nome as amnome, ");
		sql.append("co.empresa_id as empresaid, es.nome as esnome, cpea.avaliacao_id as avalid, emp.nome as empnome, fun.nome as fnome, ");
		sql.append("ao.responsavel_id as responsavel, ao.coresponsavel_id as coresponsavel, co.usuario_id as usuarioId, co.email as coemail ");
		sql.append("from HistoricoColaborador as hc ");
		sql.append("inner join colaborador as co on hc.colaborador_id = co.id ");
		sql.append("left join faixaSalarial as fs on hc.faixaSalarial_id = fs.id ");
		sql.append("left join areaOrganizacional as ao on hc.areaOrganizacional_id = ao.id ");
		sql.append("left join funcao as fun on hc.funcao_id = fun.id "); 
		sql.append("left join empresa as emp on co.empresa_id = emp.id ");
		sql.append("left join estabelecimento as es on hc.estabelecimento_id = es.id ");
		sql.append("left join areaorganizacional as am on ao.areaMae_id = am.id ");
		sql.append("left join cargo as cg on fs.cargo_id = cg.id ");
		
		if(empresa.isNotificarSomentePeriodosConfigurados())
			sql.append("inner join colaboradorPeriodoExperienciaAvaliacao as cpea on cpea.colaborador_id = co.id ");
		else
			sql.append("left join colaboradorPeriodoExperienciaAvaliacao as cpea on cpea.colaborador_id = co.id ");
		
		sql.append("and cpea.periodoExperiencia_id = :periodoExperienciaId and cpea.tipo = 'G' ");
		sql.append(" where ");
		sql.append("		hc.status = :status ");
		sql.append("		and hc.data = (");
		sql.append("			select max(hc2.data) ");
		sql.append("			from HistoricoColaborador as hc2 ");
		sql.append("			where hc2.colaborador_id = co.id ");
		sql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		sql.append("		) ");
		sql.append("and (co.dataDesligamento >= current_date or co.dataDesligamento is null) ");
		sql.append("and co.empresa_id = :empresaId ");
		sql.append("and :hoje - (co.dataAdmissao - 1) = :dias ");
		sql.append("and co.id not in (select cq.colaborador_id from ColaboradorQuestionario cq join avaliacao av on cq.avaliacao_id = av.id  ");
		sql.append("					and av.tipoModeloAvaliacao = 'A' and av.periodoExperiencia_id = :periodoExperienciaId   ");
		sql.append("					where cq.avaliacaoDesempenho_id is null and cq.turma_id is null and cq.colaborador_id = co.id ");
		sql.append("						and (cpea.id is null and cq.avaliacao_id <> coalesce((select cpea2.avaliacao_id from ColaboradorPeriodoExperienciaAvaliacao as cpea2 ");
		sql.append("																							where cpea2.colaborador_id = co.id and cpea2.tipo ='C'" );
		sql.append(" 																							and cpea2.periodoExperiencia_id = :periodoExperienciaId),0)) ");
		sql.append("							or (cpea.avaliacao_id = cq.avaliacao_id)  ");
		sql.append("				) ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresa.getId());
		query.setLong("periodoExperienciaId", periodoExperienciaId); 
		query.setDate("hoje", new Date());
		query.setInteger("dias", dias);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> colaboradores = new LinkedList<Colaborador>();
		Collection lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Colaborador colaborador = new Colaborador(((BigInteger) array[0]).longValue(), 
					(String) array[1], (String) array[2], (String) array[3], 
					(String) array[4], (String) array[5], ((BigInteger) array[6]).longValue(), (String) array[7],
					( array[8] != null ? ((BigInteger) array[8]).longValue(): null), 
					(String) array[9], ((BigInteger) array[10]).longValue(), (String) array[11], 
					(array[12] != null ? ((BigInteger) array[12]).longValue(): null),
					(String) array[13], (String) array[14]); 
					
			colaborador.getAreaOrganizacional().setResponsavelId(( array[15] != null ? ((BigInteger) array[15]).longValue(): null));
			colaborador.getAreaOrganizacional().setCoResponsavelId(( array[16] != null ? ((BigInteger) array[16]).longValue(): null));
			colaborador.setUsuarioIdProjection(array[17] != null ? ((BigInteger) array[17]).longValue(): null);
			colaborador.setEmailColaborador((String) array[18]);
			colaboradores.add(colaborador);
		}

		return colaboradores;
	}

	public Collection<Colaborador> findAdmitidos(String vinculo, Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.matricula, co.dataAdmissao, co.desligado, cg.nome, fs.nome, es.id, es.nome, emp.nome, ao.id, ao.nome, am.id, am.nome, ms.descricao, ms.turnover) ");

		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.areaOrganizacional as ao ");
		hql.append("left join ao.areaMae as am ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join es.empresa as emp ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");
		hql.append("left join hc1.candidatoSolicitacao cs ");
		hql.append("left join cs.solicitacao as sol ");
		hql.append("left join sol.motivoSolicitacao as ms ");

		hql.append("where ");
		hql.append("	hc1.status = :status ");
		hql.append("	and hc1.motivo = :motivo ");
		hql.append("	and	co.dataAdmissao between :dataIni and :dataFim ");
		hql.append("	and	es.id in (:estabelecimentosIds) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			hql.append("and	ao.id in (:areasIds) ");
		
		if (exibirSomenteAtivos)
			hql.append("and	co.desligado = false ");

		if (!StringUtil.isBlank(vinculo))
			hql.append("and co.vinculo = :vinculo ");
		
		hql.append("order by emp.nome, es.nome,ao.nome,co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setString("motivo", MotivoHistoricoColaborador.CONTRATADO);
		
		query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);

		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);

		if (!StringUtil.isBlank(vinculo))
			query.setString("vinculo", vinculo);
		
		return query.list();
	}

	public Collection<Colaborador> findByNomeCpfMatriculaComHistoricoComfirmado(Colaborador colaborador, Long empresaId, Long[] areasIds){
		DetachedCriteria subQuery = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");

		criteria.setProjection(projectionfindByNomeCpfMatriculaComHistoricoComfirmado());
		criteria.add(Subqueries.propertyEq("hc.data", subQuery));
		criteria.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));

		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		if(colaborador != null){
			if(StringUtils.isNotBlank(colaborador.getNome()))
				criteria.add(Expression.like("c.nome", "%" + colaborador.getNome() + "%").ignoreCase());
			if(StringUtils.isNotBlank(colaborador.getMatricula()))
				criteria.add(Expression.like("c.matricula", "%" + colaborador.getMatricula() + "%").ignoreCase());
			if(colaborador.getPessoal() != null && StringUtils.isNotBlank(colaborador.getPessoal().getCpf()))
				criteria.add(Expression.like("c.pessoal.cpf", "%" + colaborador.getPessoal().getCpf() + "%").ignoreCase());
		}

		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	private ProjectionList projectionfindByNomeCpfMatriculaComHistoricoComfirmado() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.contato.ddd"), "contatoDdd");
		p.add(Projections.property("c.contato.foneCelular"), "contatoCelular");
		p.add(Projections.property("c.contato.foneFixo"), "contatoFoneFixo");
		return p;
	}

	public Collection<Colaborador> findColaboradorDeAvaliacaoDesempenhoNaoRespondida()
	{
		Date hoje = new Date();
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliador", "colab");
		criteria.createCriteria("colab.empresa", "e");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("colab.id")), "id");
		p.add(Projections.property("colab.contato.email"), "emailColaborador");
		p.add(Projections.property("ad.titulo"), "avaliacaoDesempenhoTitulo");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.emailRemetente"), "empresaEmailRemetente");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("colab.desligado", false));
		criteria.add(Expression.eq("ad.liberada", true));
		criteria.add(Expression.le("ad.inicio", hoje));
		criteria.add(Expression.ge("ad.fim", hoje));
		criteria.add(Expression.eq("cq.respondida", false));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida){
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "avDesempenho");// não pode ser LEFT_JOIN

		if(isAvaliado)
			criteria.createCriteria("cq.colaborador", "colab");
		else
			criteria.createCriteria("cq.avaliador", "colab");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("colab.id")), "id");
		p.add(Projections.property("colab.nome"), "nome");
		p.add(Projections.property("colab.nomeComercial"), "nomeComercial");
		p.add(Projections.property("colab.contato.email"), "emailColaborador");
		criteria.setProjection(p);

		criteria.add(Expression.eq("avDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("colab.desligado", false));

		if(respondida != null)
			criteria.add(Expression.eq("cq.respondida", respondida));

		criteria.addOrder(Order.asc("colab.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados, Long empresaId, Long[] areasIds, Long[] cargosIds)
	{
		// subQuery
		DetachedCriteria subQuery = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);
		
		// Query
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");

		if(isAvaliados)
			criteria.createCriteria("cq.colaborador", "c");
		else
			criteria.createCriteria("cq.avaliador", "c");

		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.property("fs.nome"), "faixaSalarialNomeProjection");
		p.add(Projections.property("ca.nome"), "cargoNomeProjection");

		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("hc.data", subQuery));
		subQuery.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));
		criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		
		if(empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("ao.id", areasIds));

		if(cargosIds != null && cargosIds.length > 0)
			criteria.add(Expression.in("ca.id", cargosIds));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId, String nomeComercial)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.desligado) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao where ");
		hql.append("co.empresa.id = :empresaId ");

		if(nome != null && !nome.trim().equals(""))
			hql.append("and normalizar(upper(co.nome)) like normalizar(:nome) ");
		if(nomeComercial != null && !nomeComercial.trim().equals(""))
			hql.append("and normalizar(upper(co.nomeComercial)) like normalizar(:nomeComercial) ");
		if(matricula != null && !matricula.trim().equals(""))
			hql.append("and normalizar(upper(co.matricula)) like normalizar(:matricula) ");
		if(areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in (:areaIds) ");

		hql.append("and hc.status = :status ");
		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador as hc2 where hc2.colaborador.id = co.id ");
		hql.append("and hc2.status = :status ) ");
		hql.append("order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(nome != null && !nome.trim().equals(""))
			query.setString("nome", "%" + nome.toUpperCase() + "%");
		if(nomeComercial != null && !nomeComercial.trim().equals(""))
			query.setString("nomeComercial", "%" + nomeComercial.toUpperCase() + "%");
		if(matricula != null && !matricula.trim().equals(""))
			query.setString("matricula", "%" + matricula.toUpperCase() + "%");
		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Integer qtdTotalDiasDaTurmaVezesColaboradoresInscritos(Date dataPrevIni, Date dataPrevFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds)
	{
		getSession().flush();
		 
		StringBuilder sql = new StringBuilder();
		sql.append("select cast(coalesce(sum(subTotal), 0) as integer) from ( ");
		sql.append("    select (count(ct.id) * dt.diasDaTurma)  as subTotal ");
		sql.append("    from ColaboradorTurma ct ");
		sql.append("    left outer join Colaborador c on ct.colaborador_id = c.id ");
		sql.append("    left outer join HistoricoColaborador hc on c.id=hc.colaborador_id ");
		sql.append("    left outer join Turma t on ct.turma_id = t.id ");
		sql.append("    left outer join Curso cs on cs.id = t.curso_id ");
		sql.append("    left outer join (select dt2.turma_id, count(dt2.id) as diasDaTurma from  DiaTurma dt2 group by turma_id ) as dt on dt.turma_id = t.id ");
		sql.append("    where hc.data = ( select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador_id = c.id and hc2.data <= current_date) ");
		
		if (dataPrevIni != null)
			sql.append("and t.dataPrevIni >= :dataPrevIni ");

		if (dataPrevFim != null)
			sql.append("and t.dataPrevFim <= :dataPrevFim "); 
				
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			sql.append("and t.curso_id in (:cursoIds) ");

		if (LongUtil.arrayIsNotEmpty(empresaIds))
			sql.append("and cs.empresa_id in (:empresaIds) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			sql.append("and hc.areaOrganizacional_id in (:areasIds) ");
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		
		sql.append("	group by dt.diasDaTurma ");
		sql.append(") as sub ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		if (dataPrevIni != null)
			query.setDate("dataPrevIni", dataPrevIni);

		if (dataPrevFim != null)
			query.setDate("dataPrevFim", dataPrevFim);
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			query.setParameterList("cursoIds", cursoIds, Hibernate.LONG);

		if (LongUtil.arrayIsNotEmpty(empresaIds))
			query.setParameterList("empresaIds", empresaIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		return (Integer) query.uniqueResult();
	}

	public Collection<Object> findComHistoricoFuturoSQL(Map parametros, Integer pagingSize, Integer page, Long usuarioLogadoId)
	{
		String codigoACBusca = (String) parametros.get("codigoACBusca");
		String nomeBusca = (String) parametros.get("nomeBusca");
		String nomeComercialBusca = (String) parametros.get("nomeComercialBusca");
		String matriculaBusca = (String) parametros.get("matriculaBusca");
		Long empresaId = (Long) parametros.get("empresaId");
		Long areaBuscaId = (Long) parametros.get("areaId");
		Long areasIdsPorResponsavel[] = (Long[]) parametros.get("areasIdsPorResponsavel");
		Long estabelecimentoId = (Long) parametros.get("estabelecimentoId");
		Long cargoId = (Long) parametros.get("cargoId");
		Long faixaSalarialId = (Long) parametros.get("faixaSalarialId");
		String situacao = (String) parametros.get("situacao");
		String cpfBusca = (String) parametros.get("cpfBusca");
		
		if(cpfBusca != null && cpfBusca.equals("   .   .   -  "))
			cpfBusca = null;

		StringBuilder sql = new StringBuilder();

		sql.append("select colab_.id as colabId, colab_.nome as name, colab_.nomeComercial, colab_.matricula, colab_.desligado, colab_.dataAdmissao, colab_.cpf, colab_.usuario_id, colab_.dataDesligamento, md.motivo, colab_.respondeuEntrevista, colab_.candidato_id as candId, colab_.naoIntegraAc, colab_.dataSolicitacaoDesligamento, colab_.dataSolicitacaoDesligamentoAc, a.id, hc.status, colab_.codigoAC, colab_.solicitanteDemissao_id as solicitanteId ");
		sql.append("from historicoColaborador hc ");
		sql.append("inner join ");
		sql.append("( ");
		sql.append("	select colab_.id as colabId, ");
		sql.append("	subJoinMaxData.maxData as datax ");
		sql.append("	from HistoricoColaborador hc ");
		sql.append("	left join colaborador colab_ ");
		sql.append("	on colab_.id = hc.colaborador_id ");
		sql.append("	left join ");
		sql.append("	( ");
		sql.append("		select hc3.colaborador_id as colabId2, ");
		sql.append("		max(hc3.data) as maxData ");
		sql.append("		from HistoricoColaborador hc3 ");
		sql.append("		where  ");
		sql.append("		hc3.data <= :hoje ");
		sql.append("		group by ");
		sql.append("		hc3.colaborador_id ");
		sql.append("	) subJoinMaxData  ");
		sql.append("on colabId2 = colab_.id ");
		sql.append("where ");
		sql.append("	colab_.empresa_id = :empresaId ");
		sql.append("	and subJoinMaxData.maxData is not null ");
		sql.append("union ");
		sql.append("	select ");
		sql.append("	colab_.id as colabId, ");
		sql.append("subJoinMaxData.maxData ");
		sql.append("from ");
		sql.append("HistoricoColaborador hc ");
		sql.append("left join ");
		sql.append("colaborador colab_ ");
		sql.append("on colab_.id = hc.colaborador_id ");
		sql.append("left join ");
		sql.append("( ");
		sql.append("	select ");
		sql.append("	hc2.colaborador_id as colabId1, ");
		sql.append("	count(hc2.id), ");
		sql.append("	max(hc2.data) as maxData ");
		sql.append("	from ");
		sql.append("	HistoricoColaborador hc2 ");
		sql.append("	where hc2.status in (:status)  ");
		sql.append("	group by ");
		sql.append("	hc2.colaborador_id ");
		sql.append("	having count(hc2.id) = 1 ");
		sql.append(") subJoinMaxData ");
		sql.append("on colabId1 = colab_.id ");
		sql.append("where ");
		sql.append("	colab_.empresa_id = :empresaId ");
		sql.append("	and subJoinMaxData.maxData is not null ");
		sql.append(") histAtual ");
		sql.append("on hc.data = histAtual.datax and histAtual.colabId = ");
		sql.append("hc.colaborador_id ");
		sql.append("left join ");
		sql.append("colaborador colab_ ");
		sql.append("on colab_.id = hc.colaborador_id ");
		sql.append("left join ");
		sql.append("faixaSalarial fs ");
		sql.append("on fs.id = hc.faixaSalarial_id ");
		sql.append("left join ");
		sql.append("motivoDemissao md ");
		sql.append("on md.id = colab_.motivoDemissao_id ");
		sql.append("left join ");
		sql.append("areaOrganizacional a ");
		sql.append("on a.id = hc.areaOrganizacional_id ");
		sql.append("where ");
		sql.append("colab_.empresa_id = :empresaId ");
		
		sql.append("and ( ( hc.status in (:status)  ");
		if(situacao != null && !situacao.trim().equals("") && !situacao.trim().equals("T") && !situacao.trim().equals("U"))
		{
			if(situacao.trim().equals("A"))
				sql.append("and hc.motivo != :motivoContratado ) or ( hc.motivo = :motivoContratado and hc.status = :statusConfirmado ");
		}
		sql.append(") ) ");
		
		if(areaBuscaId != null)
			sql.append("and hc.areaOrganizacional_id = :areaBuscaId ");
		if(cargoId != null)
			sql.append("and fs.cargo_id = :cargoId ");
		if(faixaSalarialId != null)
			sql.append("and fs.id = :faixaSalarialId ");
		if(estabelecimentoId != null)
			sql.append("and hc.estabelecimento_id = :estabelecimentoId ");
		if(areasIdsPorResponsavel != null)
			sql.append("and a.id in (:areasIdsPorResponsavel) ");

		// Nome
		if(nomeBusca != null && !nomeBusca.trim().equals(""))
			sql.append("and normalizar(upper(colab_.nome)) like normalizar(:nomeBusca) ");
		//NomeComercial
		if(nomeComercialBusca != null && !nomeComercialBusca.trim().equals(""))
			sql.append("and normalizar(upper(colab_.nomeComercial)) like normalizar(:nomeComercialBusca) ");
		// Codigo Fortes Pessoal
		if(codigoACBusca != null && !codigoACBusca.trim().equals(""))
			sql.append("and colab_.codigoAC like :codigoACBusca ");
		// Matricula
		if(matriculaBusca != null && !matriculaBusca.trim().equals(""))
			sql.append("and upper(colab_.matricula) like :matriculaBusca ");
		// CPF
		if(cpfBusca != null && !cpfBusca.trim().equals(""))
			sql.append("and colab_.cpf like :cpfBusca ");

		if(situacao != null && !situacao.trim().equals("") && situacao.equals("U")){
			sql.append("and colab_.dataSolicitacaoDesligamentoAc is not null ");
			if(usuarioLogadoId != null && !usuarioLogadoId.equals(1L)){
				sql.append("and ( colab_.usuario_id <> :usuarioLogadoId or colab_.usuario_id is null and (colab_.solicitanteDemissao_id = colab_.id or colab_.solicitanteDemissao_id is null)) ");
			}
		}
		else if(situacao != null && !situacao.trim().equals("") && !situacao.trim().equals("T"))
			sql.append("and colab_.desligado = :situacao ");

		sql.append(" order by name");

		if(page != 0 && pagingSize != 0)
		{
			sql.append(" limit " + pagingSize);
			sql.append(" offset " + ((page - 1) * pagingSize));
		}

		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		
		// Nome
		if(nomeBusca != null && !nomeBusca.trim().equals(""))
			query.setString("nomeBusca", "%" + nomeBusca.toUpperCase() + "%");
		// NomeComercial
		if(nomeComercialBusca != null && !nomeComercialBusca.trim().equals(""))
			query.setString("nomeComercialBusca", "%" + nomeComercialBusca.toUpperCase() + "%");
		// Código Fortes Pessoal
		if(codigoACBusca != null && !codigoACBusca.trim().equals(""))
			query.setString("codigoACBusca", "%" + codigoACBusca + "%");
		// Matricula
		if(matriculaBusca != null && !matriculaBusca.trim().equals(""))
			query.setString("matriculaBusca", "%" + matriculaBusca.toUpperCase() + "%");
		// CPF
		if(cpfBusca != null && !cpfBusca.trim().equals(""))
			query.setString("cpfBusca", "%" + cpfBusca + "%");

		if(areaBuscaId != null)
			query.setLong("areaBuscaId", areaBuscaId);
		if(cargoId != null)
			query.setLong("cargoId", cargoId);
		if(faixaSalarialId != null)
			query.setLong("faixaSalarialId", faixaSalarialId);
		if(estabelecimentoId != null)
			query.setLong("estabelecimentoId", estabelecimentoId);
		if(areasIdsPorResponsavel != null)
			query.setParameterList("areasIdsPorResponsavel", areasIdsPorResponsavel);
			
		if(situacao != null && !situacao.trim().equals("") && !situacao.trim().equals("T") && !situacao.trim().equals("U"))
		{
			if(situacao.trim().equals("A"))
			{
				query.setBoolean("situacao", false);
				Integer[] status = {StatusRetornoAC.CONFIRMADO,StatusRetornoAC.CANCELADO, StatusRetornoAC.AGUARDANDO};
				query.setInteger("statusConfirmado", StatusRetornoAC.CONFIRMADO);
				query.setString("motivoContratado", MotivoHistoricoColaborador.CONTRATADO);
				query.setParameterList("status", status);
			}
			if(situacao.trim().equals("D"))
			{
				query.setBoolean("situacao", true);
				query.setInteger("status", StatusRetornoAC.CONFIRMADO);
			}
			if(situacao.trim().equals("G"))
			{
				query.setBoolean("situacao", false);
				query.setInteger("status", StatusRetornoAC.AGUARDANDO);
			}
			if(situacao.trim().equals("C"))
			{
				query.setBoolean("situacao", false);
				query.setInteger("status", StatusRetornoAC.CANCELADO);
			}
			
		}else
		{
				Integer[] todosStatus = {StatusRetornoAC.CONFIRMADO,StatusRetornoAC.AGUARDANDO,StatusRetornoAC.CANCELADO};
				query.setParameterList("status", todosStatus);
		}
		
		if(situacao != null && !situacao.trim().equals("") && situacao.trim().equals("U") && usuarioLogadoId != null && !usuarioLogadoId.equals(1L)){
			query.setLong("usuarioLogadoId", usuarioLogadoId);
		}

		return query.list();
	}

	public Collection<Colaborador> findColaboradoresEleicao(Long empresaId, Long estabelecimentosIds, Date data) {
	
		return null;
	}

	public Collection<Colaborador> findColabPeriodoExperiencia(Date periodoIni, Date periodoFim, Long[] avaliacaoIds, Long[] areasCheck, Long[] estabelecimentosCheck, Long[] colaboradorsCheck, boolean considerarAutoAvaliacao, boolean agruparPorArea, Long... empresasIds) 
	{
		StringBuilder hql = new StringBuilder();
		  hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, aval.nome, cq.respondidaEm, cq.pesoAvaliador, cq.performance, cq.performanceNivelCompetencia, ad.anonima, ad.id, ad.titulo, emp.nome, ao.nome) ");
		  hql.append("from HistoricoColaborador as hc ");
		  hql.append("left join hc.colaborador as co ");
		  hql.append("left join hc.areaOrganizacional as ao ");
		  hql.append("left join co.colaboradorQuestionarios as cq ");
		  hql.append("left join cq.avaliacaoDesempenho as ad ");
		  hql.append("left join ad.avaliacao as a ");
		  hql.append("left join a.empresa as emp ");
		  hql.append("left join cq.avaliador as aval ");
		  hql.append("where ");
		  hql.append("  hc.data = (");
		  hql.append("   select max(hc2.data) ");
		  hql.append("   from HistoricoColaborador as hc2 ");
		  hql.append("   where hc2.colaborador.id = co.id ");
		  hql.append("   and hc2.data <= :dataAtual  ");
		  hql.append("  ) ");
		  hql.append("and co.desligado = false ");
		  
		  if (empresasIds != null && empresasIds.length > 0)
			  hql.append("and co.empresa.id in( :empresasIds ) ");
		  
		  hql.append("and cq.respondidaEm between :periodoIni and :periodoFim ");
		  hql.append("and ad.id in (:avaliacaoId) ");
		  hql.append("and cq.respondida = true ");
		  		  
		  if(areasCheck != null && areasCheck.length > 0) 
			  hql.append("and hc.areaOrganizacional.id in (:areasCheck) ");

		  if(estabelecimentosCheck != null && estabelecimentosCheck.length > 0) 
			  hql.append("and hc.estabelecimento.id in (:estabelecimentoCheck) ");

		  if(colaboradorsCheck != null && colaboradorsCheck.length > 0) 
			  hql.append("and co.id in (:colaboradorsCheck) ");

		  if(!considerarAutoAvaliacao) 
			  hql.append("and co.id <> aval.id ");

		  hql.append("order by ");

		  if(agruparPorArea)
			  hql.append("ao.nome, ");
		  
		  hql.append("co.nome, co.id, ad.titulo, ad.id, aval.nome ");//importante para relatorio

		  Query query = getSession().createQuery(hql.toString());
		  
		  if (empresasIds != null && empresasIds.length > 0)
			  query.setParameterList("empresasIds", empresasIds);
		  
		  query.setDate("periodoIni", periodoIni);
		  query.setDate("periodoFim", periodoFim);
		  query.setDate("dataAtual", new Date());

		  query.setParameterList("avaliacaoId", avaliacaoIds);

		  if(areasCheck != null && areasCheck.length > 0)
			  query.setParameterList("areasCheck", areasCheck);

		  if(estabelecimentosCheck != null && estabelecimentosCheck.length > 0)
			  query.setParameterList("estabelecimentoCheck", estabelecimentosCheck);   

		  if(colaboradorsCheck != null && colaboradorsCheck.length > 0) 
			  query.setParameterList("colaboradorsCheck", colaboradorsCheck);   

		  return query.list();
	}

	public void setCandidatoNull(Long idCandidato) 
	{
		String hql = "update Colaborador set candidato.id = null where candidato.id = :candidatoId";

		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", idCandidato);
		
		query.executeUpdate();

	}


	public Collection<Colaborador> findAdmitidosNoPeriodo(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Integer tempoDeEmpresa) 
	{		
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.matricula, co.dataAdmissao, fs.nome, ca.nome, ao.id, respArea.nome, ");
		
		if (periodoFim != null)
			hql.append(" cast((:periodoFim - co.dataAdmissao) + 1 as int), ");
		else
			hql.append(" cast((current_date - co.dataAdmissao) + 1 as int), ");
		
		hql.append(" ao.nome, aoMae.nome, e.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join ao.areaMae as aoMae ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ao.responsavel as respArea ");
		hql.append("where hc.data = (");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		
		if (periodoFim != null)
			hql.append("   and hc2.data <= :periodoFim ");
		
		hql.append("   and hc2.status = :status ");
		hql.append("  ) ");
		hql.append("and co.desligado = false ");
		hql.append("and co.empresa.id = :empresaId ");
		
		if(periodoFim != null && tempoDeEmpresa != null)
			hql.append("and (:periodoFim - co.dataAdmissao) + 1  <= :tempoDeEmpresa ");
		
		if (periodoIni != null)
			hql.append("and :periodoIni <= co.dataAdmissao  ");
		if (periodoFim != null)
			hql.append("and co.dataAdmissao <= :periodoFim ");
		
		if (areasCheck != null && areasCheck.length > 0)
			hql.append("and ao.id in (:areasCheck) ");

		if (estabelecimentoCheck != null && estabelecimentoCheck.length > 0)
			hql.append("and hc.estabelecimento.id in (:estabelecimentoCheck) ");

		hql.append("order by co.id ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresa.getId());
		
		if (periodoIni != null)
			query.setDate("periodoIni", periodoIni);
		
		if (periodoFim != null)
			query.setDate("periodoFim", periodoFim);
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(periodoFim != null && tempoDeEmpresa != null)
			query.setInteger("tempoDeEmpresa", tempoDeEmpresa);
		
		if (areasCheck != null && areasCheck.length > 0)
			query.setParameterList("areasCheck", StringUtil.stringToLong(areasCheck));

		if (estabelecimentoCheck != null && estabelecimentoCheck.length > 0)
			query.setParameterList("estabelecimentoCheck", StringUtil.stringToLong(estabelecimentoCheck));

		return query.list();
	}
	
	public Collection<Colaborador> findComAvaliacoesExperiencias(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, cq.respondidaEm, cq.performance, cast((cq.respondidaEm - co.dataAdmissao) + 1 as int), av.periodoExperiencia.id, av.titulo, avaliador.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join co.colaboradorQuestionarios as cq ");
		hql.append("left join cq.avaliador as avaliador ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("inner join cq.avaliacao as av ");
		hql.append("where hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		
		if (periodoFim != null)
			hql.append("   and hc2.data <= :periodoFim ");
		
		hql.append("   and hc2.status = :status ");
		hql.append("  ) ");
		hql.append("and co.desligado = false ");
		hql.append("and co.empresa.id = :empresaId ");
		
		if (periodoFim != null)
			hql.append("and co.dataAdmissao <= :periodoFim ");
		
		if (periodoIni != null)
			hql.append("and :periodoIni <= cq.respondidaEm ");
		if (periodoFim != null)
			hql.append("and cq.respondidaEm <= :periodoFim ");

		if (areasCheck != null && areasCheck.length > 0)
			hql.append("and ao.id in (:areasCheck) ");

		if (estabelecimentoCheck != null && estabelecimentoCheck.length > 0)
			hql.append("and hc.estabelecimento.id in (:estabelecimentoCheck) ");

		hql.append("order by co.id ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresa.getId());
		
		if (periodoIni != null)
			query.setDate("periodoIni", periodoIni);
		if (periodoFim != null)
			query.setDate("periodoFim", periodoFim);
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (areasCheck != null && areasCheck.length > 0)
			query.setParameterList("areasCheck", StringUtil.stringToLong(areasCheck));

		if (estabelecimentoCheck != null && estabelecimentoCheck.length > 0)
			query.setParameterList("estabelecimentoCheck", StringUtil.stringToLong(estabelecimentoCheck));

		return query.list();
	}

	private Criteria configuraCriteriaParaPainelDeIncadores(Criteria criteria, Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, boolean consideradaDataAdmissaoAndDesligado, String[] vinculos)
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(data, StatusRetornoAC.CONFIRMADO);

		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("hc.faixaSalarial", "fs");
		
		if(consideradaDataAdmissaoAndDesligado){
			criteria.add(Expression.le("c.dataAdmissao", data));
			criteria.add(Expression.or(Expression.isNull("c.dataDesligamento"), Expression.ge("c.dataDesligamento", data)));
		}
		criteria.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));

		if(LongUtil.isNotEmpty(empresaIds))
			criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));
		
		if(LongUtil.arrayIsNotEmpty(areasIds))
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		
		if(LongUtil.arrayIsNotEmpty(cargosIds))
			criteria.add(Expression.in("fs.cargo.id", cargosIds));
		
		if(StringUtil.arrayIsNotEmpty(vinculos))
			criteria.add(Expression.in("c.vinculo", vinculos));
		
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		
		return criteria;		
	}
	
	public Collection<DataGrafico> countSexo(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("c.pessoal.sexo"));
		projections.add(Projections.count("c.id"));
		criteria.setProjection(projections);
		
		criteria.add(Expression.in("c.pessoal.sexo", new String[] {"M","F"}));
		
		criteria.addOrder(Order.asc("c.pessoal.sexo"));
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		List resultado = configuraCriteriaParaPainelDeIncadores(criteria, data, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, vinculos).list();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			char sex = (Character) res[0];
			int qtd = (Integer) res[1];

			DataGrafico dataGrafico = null;
			
			if(sex == 'F')
				dataGrafico = new DataGrafico(null, "Feminino", qtd, "");
			else if(sex == 'M')
				dataGrafico = new DataGrafico(null, "Masculino", qtd, "");
			
			dataGraficos.add(dataGrafico);
		}
		
		return dataGraficos;
	}

	public Collection<DataGrafico> countEstadoCivil(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("c.pessoal.estadoCivil"));
		projections.add(Projections.count("c.id"));
		criteria.setProjection(projections);
		
		criteria.addOrder(Order.asc("c.pessoal.estadoCivil"));
		
		List resultado = configuraCriteriaParaPainelDeIncadores(criteria, data, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, vinculos).list();
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		int qtdCasado = 0;
		int qtdDivorciado = 0;
		int qtdSolteiro = 0;
		int qtdViuvo = 0;
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			String estadoCivil = (String) res[0];
			int qtd = (Integer) res[1];

			if(estadoCivil.equalsIgnoreCase(EstadoCivil.DIVORCIADO) || estadoCivil.equalsIgnoreCase(EstadoCivil.SEPARADO_JUDIALMENTE))
				qtdDivorciado += qtd;
			else if(estadoCivil.equalsIgnoreCase(EstadoCivil.SOLTEIRO) || estadoCivil.equalsIgnoreCase(EstadoCivil.UNIAO_ESTAVEL))
				qtdSolteiro += qtd;
			else if(estadoCivil.equalsIgnoreCase(EstadoCivil.VIUVO))
				qtdViuvo += qtd;
			else if(estadoCivil.equalsIgnoreCase(EstadoCivil.CASADO_COMUNHAO_PARCIAL) || estadoCivil.equalsIgnoreCase(EstadoCivil.CASADO_COMUNHAO_UNIVERSAL) || estadoCivil.equalsIgnoreCase(EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL) || estadoCivil.equalsIgnoreCase(EstadoCivil.CASADO_REGIME_TOTAL) || estadoCivil.equalsIgnoreCase(EstadoCivil.CASADO_SEPARACAO_DE_BENS))
				qtdCasado += qtd;
		}

		if(qtdCasado > 0)
			dataGraficos.add(new DataGrafico(null, "Casado", qtdCasado, ""));
		if(qtdDivorciado > 0)
			dataGraficos.add(new DataGrafico(null, "Divorciado", qtdDivorciado, ""));
		if(qtdSolteiro > 0)
			dataGraficos.add(new DataGrafico(null, "Solteiro", qtdSolteiro, ""));
		if(qtdViuvo > 0)
			dataGraficos.add(new DataGrafico(null, "Viúvo", qtdViuvo, ""));
		
		return dataGraficos;
	}

	public Collection<DataGrafico> countFormacaoEscolar(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("c.pessoal.escolaridade"));
		projections.add(Projections.count("c.id"));
		criteria.setProjection(projections);
		
		criteria.addOrder(Order.asc("c.pessoal.escolaridade"));
		
		List resultado = configuraCriteriaParaPainelDeIncadores(criteria, data, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, vinculos).list();
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		Escolaridade escolaridadeMap = new Escolaridade();

		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			dataGraficos.add(new DataGrafico(null, escolaridadeMap.get((String) res[0]), (Integer) res[1], ""));
		}
		
		return dataGraficos;
	}

	private void subSelectHistoricoAtual(StringBuilder hql, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds) 
	{
		hql.append("		and hc.status = :status ");
		hql.append("		and hc.data = ( ");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = c.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ");
		hql.append("		) ");
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			hql.append("and hc.estabelecimento.id in (:estabelecimentosIds) ");
			
		if(LongUtil.arrayIsNotEmpty(areasIds))
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");
		
		if(LongUtil.arrayIsNotEmpty(cargosIds))
			hql.append("and fs.cargo.id in (:cargosIds) ");
	}
	
	public Collection<DataGrafico> countFaixaEtaria(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.property("c.pessoal.dataNascimento"));
		criteria.setProjection(projections);
		
		List resultado = configuraCriteriaParaPainelDeIncadores(criteria, data, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, vinculos).list();

		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		int qtdFaixa1 = 0;
		int qtdFaixa2 = 0;
		int qtdFaixa3 = 0;
		int qtdFaixa4 = 0;
		int qtdFaixa5 = 0;
		int qtdFaixa6 = 0;
		
		for (Iterator<Object> it = resultado.iterator(); it.hasNext();)
		{
			Date dataNasc = (Date) it.next();

			int idade = DateUtil.calcularIdade(dataNasc, data);
			if(idade < 20)
				qtdFaixa1++;
			else if(idade >= 20 && idade < 30)
				qtdFaixa2++;
			else if(idade >= 30 && idade < 40)
				qtdFaixa3++;
			else if(idade >= 40 && idade < 50)
				qtdFaixa4++;
			else if(idade >= 50 && idade < 60)
				qtdFaixa5++;
			else if(idade >= 60)
				qtdFaixa6++;
		}
		if(qtdFaixa1 > 0)
			dataGraficos.add(new DataGrafico(null, "Até 19", qtdFaixa1, ""));
		if(qtdFaixa2 > 0)
			dataGraficos.add(new DataGrafico(null, "20 a 29", qtdFaixa2, ""));
		if(qtdFaixa3 > 0)
			dataGraficos.add(new DataGrafico(null, "30 a 39", qtdFaixa3, ""));
		if(qtdFaixa4 > 0)
			dataGraficos.add(new DataGrafico(null, "40 a 49", qtdFaixa4, ""));
		if(qtdFaixa5 > 0)
			dataGraficos.add(new DataGrafico(null, "50 a 59", qtdFaixa5, ""));
		if(qtdFaixa6 > 0)
			dataGraficos.add(new DataGrafico(null, "Acima de 60", qtdFaixa6, ""));
		
		return dataGraficos;
	}

	public Collection<DataGrafico> countDeficiencia(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("c.pessoal.deficiencia"));
		projections.add(Projections.count("c.id"));
		criteria.setProjection(projections);
		
		criteria.addOrder(Order.asc("c.pessoal.deficiencia"));
		
		List<Object[]> resultado = configuraCriteriaParaPainelDeIncadores(criteria, data, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, vinculos).list();
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();

		Deficiencia deficienciaMap = new Deficiencia();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			char deficiencia = (Character) res[0];
			int qtd = (Integer) res[1];
			
			if(new Deficiencia().containsKey(deficiencia))
				dataGraficos.add(new DataGrafico(null,deficienciaMap.get(deficiencia).toString(), qtd, ""));
		}
		return dataGraficos;
	}

	public Collection<DataGrafico> countMotivoDesligamento(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("c.motivoDemissao", "m");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("m.motivo"));
		projections.add(Projections.count("m.id"));
		criteria.setProjection(projections);
		
		criteria.add(Expression.between("c.dataDesligamento", dataIni, dataFim));
		
		criteria.addOrder(OrderBySql.sql("2 desc"));
		criteria.addOrder(Order.asc("m.motivo"));
		
		List<Object[]> resultado = configuraCriteriaParaPainelDeIncadores(criteria, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, false, null).list();
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			dataGraficos.add(new DataGrafico(null, (String)res[0], (Integer) res[1], ""));
		}
		
		return dataGraficos;
	}

	public Collection<DataGrafico> countColocacao(Date dataBase, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("c.vinculo"));
		projections.add(Projections.count("c.id"));
		criteria.setProjection(projections);
		
		criteria.addOrder(OrderBySql.sql("2 desc"));
		
		List<Object[]> resultado = configuraCriteriaParaPainelDeIncadores(criteria, dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, vinculos).list();
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		Vinculo vinculoMap = new Vinculo();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			String vinculo = (String) res[0];
			Integer qtd = (Integer) res[1];
			if(vinculo == null)
				dataGraficos.add(new DataGrafico(null, "Importado do AC", qtd , ""));
			else
				dataGraficos.add(new DataGrafico(null, (String)vinculoMap.get(vinculo), qtd, ""));
		}
		
		return dataGraficos;
	}
	
	private Criteria criteriaOcorrenciaByPeriodo(Date dataIni, Date dataFim, int qtdItens) {
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("c.colaboradorOcorrencia", "co", Criteria.LEFT_JOIN);
		criteria.createCriteria("co.ocorrencia", "o", Criteria.LEFT_JOIN);
		criteria.createCriteria("o.empresa", "e", Criteria.LEFT_JOIN);
		
		ProjectionList projections = Projections.projectionList().create();
		projections.add(Projections.groupProperty("o.descricao"), "descricao");
		projections.add(Projections.count("co.id"));
		projections.add(Projections.groupProperty("o.id"), "id");
		projections.add(Projections.groupProperty("e.nome"), "empresaNome");
		criteria.setProjection(projections);
		
		criteria.add(Expression.le("co.dataIni", dataFim));
		criteria.add(Expression.or(Expression.ge("co.dataFim", dataIni), Expression.ge("co.dataIni", dataIni)));
		
		criteria.addOrder(OrderBySql.sql("2 desc"));
		criteria.addOrder(Order.asc("o.descricao"));
		
		criteria.setMaxResults(qtdItens);
		
		return criteria;
	}
	
	public Collection<DataGrafico> countOcorrencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long[] ocorrenciasIds, int qtdItens) 
	{
		Criteria criteria = criteriaOcorrenciaByPeriodo(dataIni, dataFim, qtdItens);
		if (LongUtil.arrayIsNotEmpty(ocorrenciasIds))
			criteria.add(Restrictions.in("o.id", ocorrenciasIds));
		
		List<Object[]> resultado = configuraCriteriaParaPainelDeIncadores(criteria, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, false, null).list();
		
		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			String ocorrencia = (String) res[0];
			int qtd = (Integer) res[1];
		
			dataGraficos.add(new DataGrafico(null, ocorrencia, qtd, ""));
		}
		return dataGraficos;
	}
	
	public Collection<Ocorrencia> getOcorrenciasByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens) 
	{
		Criteria criteria = criteriaOcorrenciaByPeriodo(dataIni, dataFim, qtdItens);
		
		criteria = configuraCriteriaParaPainelDeIncadores(criteria, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, true, null);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ocorrencia.class));
		List<Ocorrencia> resultado = criteria.list();
		
		return resultado;
	}
	
	public Collection<DataGrafico> countProvidencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long[] ocorrenciasIds, int qtdItens) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("c.colaboradorOcorrencia", "co", Criteria.LEFT_JOIN);
		criteria.createCriteria("co.providencia", "p", Criteria.LEFT_JOIN);
		
		ProjectionList projections = Projections.projectionList().create()
				.add(Projections.groupProperty("p.descricao"))
				.add(Projections.count("co.id"));
		criteria.setProjection(projections);
		
		criteria.add(Expression.le("co.dataIni", dataFim));
		criteria.add(Expression.or(Expression.ge("co.dataFim", dataIni), Expression.ge("co.dataIni", dataIni)));

		if (LongUtil.arrayIsNotEmpty(ocorrenciasIds))
			criteria.add(Restrictions.in("co.ocorrencia.id", ocorrenciasIds));
		
		criteria.addOrder(OrderBySql.sql("2 desc"));
		criteria.addOrder(Order.asc("p.descricao"));
		
		criteria.setMaxResults(qtdItens);
		
		List<Object[]> resultado = configuraCriteriaParaPainelDeIncadores(criteria, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, false, null).list();

		Collection<DataGrafico> dataGraficos = new ArrayList<DataGrafico>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			String providencia = (String) res[0];
			int qtd = (Integer) res[1];
			
			dataGraficos.add(new DataGrafico(null, providencia, qtd, ""));
		}
		return dataGraficos;
	}

	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AutoCompleteVO(c.id, c.nome ||  ' - CPF: ' || c.pessoal.cpf ||  ' - ' || c.matricula ) ");
		hql.append("from Colaborador as c ");
		
		hql.append(" where c.empresa.id = :empresaId and ( ");
		hql.append(" normalizar(upper(c.nome)) like normalizar(:descricao) ");
		hql.append(" or normalizar(upper(c.nomeComercial)) like normalizar(:descricao) ");
		hql.append(" or normalizar(upper(c.matricula)) like normalizar(:descricao) ");
		hql.append(" or normalizar(upper(c.pessoal.cpf)) like normalizar(:descricao) ) ");
		
		hql.append(" order by c.nome");		

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setString("descricao", "%" + descricao.toUpperCase() + "%");
		
		return query.list();
	}
	
	public Collection<ColaboradorJsonVO> getColaboradoresJsonVO(Long[] areaOrganizacionalIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorJsonVO(c.endereco.logradouro || ', ' || c.endereco.numero, c.endereco.bairro, c.endereco.cep, cid.nome, ");
		hql.append("c.pessoal.cpf, c.contato.email, c.pessoal.sexo, c.nome, '(' || c.contato.ddd || ') ' || c.contato.foneFixo, uf.sigla, c.pessoal.rg, c.pessoal.escolaridade, c.pessoal.mae, c.pessoal.pis, ce.texto1, c.vinculo,  ");
		hql.append("c.pessoal.dataNascimento, ce.numero1, c.dataEncerramentoContrato, c.dataAdmissao, ca.nome, c.desligado) ");
		hql.append("from Colaborador as c ");
		hql.append("left join c.historicoColaboradors hc ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo ca ");
		hql.append("left join c.endereco.uf uf ");
		hql.append("left join c.endereco.cidade cid ");
		hql.append("left join c.camposExtras ce ");
		
		hql.append("where hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = c.id ");
		hql.append("			and hc2.data <= current_date ");
		hql.append("			and hc2.status = :status ");
		hql.append("		) ");
		hql.append("and hc.areaOrganizacional.id in (:areaOrganizacionalIds) ");
		
		hql.append(" order by c.nome");		
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}

	public Collection<Colaborador> findByAvaliacoes(Long... avaliacaoIds) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.colaboradorQuestionarios", "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.desligado"), "desligado");
		criteria.setProjection(p);

		criteria.add(Expression.in("ad.id", avaliacaoIds));
		criteria.add(Expression.isNotNull("cq.performance"));
		criteria.add(Expression.isNotNull("cq.avaliador.id"));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return criteria.list();
	}

	public Collection<Colaborador> findAniversariantesByEmpresa(Long empresaId, int dia, int mes) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(c.nome, c.contato.email, c.id) ");
		hql.append("from Colaborador as c ");
		hql.append("where c.empresa.id = :empresaId ");		
		hql.append("and date_part('day', c.pessoal.dataNascimento) = :dia ");		
		hql.append("and date_part('month', c.pessoal.dataNascimento) = :mes ");		
		hql.append("and c.desligado = :desligado ");
		hql.append("and c.contato.email <> '' ");
		
		Query query = getSession().createQuery(hql.toString());

		query.setDouble("dia", new Integer(dia));
		query.setDouble("mes", new Integer(mes));
		query.setLong("empresaId", empresaId);
		query.setBoolean("desligado", false);
	
		return query.list();		
	}

	public Collection<Colaborador> findByEstabelecimentoDataAdmissao(Long estabelecimentoId, Date dataAdmissao, Long empresaId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.desligado) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("where hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.status = :status ");
		hql.append("  ) ");
		hql.append("and co.desligado = false ");
		hql.append("and co.dataAdmissao >= :dataAdmissao ");
		hql.append("and co.empresa.id = :empresaId ");
		
		if (estabelecimentoId != null && !estabelecimentoId.equals(-1L))
			hql.append("and hc.estabelecimento.id = :estabelecimentoId ");

		hql.append("order by co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if (estabelecimentoId != null && !estabelecimentoId.equals(-1L))
			query.setLong("estabelecimentoId", estabelecimentoId);
		
		query.setLong("empresaId", empresaId);
		query.setDate("dataAdmissao", dataAdmissao);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}

	public Collection<Colaborador> findColaboradoresByIds(Long[] colaboradoresIds) 
	{
		String hoje = DateUtil.formataAnoMesDia(new Date());
        String cond = " hc1_.data = coalesce( "+
        		" (select max(hc2.data) "+
        		" from HistoricoColaborador hc2  "+
        		" where hc2.colaborador_id={alias}.id "+
        		"     and hc2.data<='"+ hoje +"'  "+
        		"     and hc2.status="+ StatusRetornoAC.CONFIRMADO +") ,  "+
        		" (select min(hc2.data) "+
        		" from HistoricoColaborador hc2  "+
        		" where hc2.colaborador_id={alias}.id "+
        		"     and hc2.data>'"+ hoje +"'  "+
        		"     and hc2.status="+ StatusRetornoAC.CONFIRMADO +") "+
        		" )"; 
        
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("ca.nome"), "cargoNomeProjection");
		
		criteria.add( Restrictions.sqlRestriction(cond));
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.in("c.id", colaboradoresIds));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public int findQtdVagasPreenchidas(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.countDistinct("c.id"));
		
		criteria.setProjection(p);
	
		criteria.add(Expression.between("s.dataEncerramento", dataIni, dataFim));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
	
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if(LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areaIds));
		
		if (LongUtil.arrayIsNotEmpty(solicitacaoIds)) 
			criteria.add(Expression.in("cs.solicitacao.id", solicitacaoIds));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Integer) criteria.uniqueResult();
	}

	public Collection<Colaborador> findSemCodigoAC(Long empresaId) {

		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.nome, co.nomeComercial, co.id, e.id, e.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("inner join co.empresa as e ");
		hql.append("where hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.status = :status ");
		hql.append("  ) ");
		hql.append("and co.codigoAC is null ");
		hql.append("and co.naoIntegraAc = :naoIntegraAc ");
		
		if (empresaId != null)
			hql.append("and co.empresa.id = :empresaId ");

		hql.append("order by e.nome, co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setBoolean("naoIntegraAc", false);
		if (empresaId != null)
			query.setLong("empresaId", empresaId);
		
		return query.list();
	}

	public Collection<Colaborador> findByQuestionarioNaoRespondido(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.colaborador", "c");

		montaProjection(criteria);

		criteria.add(Expression.eq("cq.questionario.id", questionarioId));
		criteria.add(Expression.eq("cq.respondida", false));
		criteria.add(Expression.isNull("cq.avaliacao.id"));
		criteria.add(Expression.isNull("cq.avaliacaoDesempenho.id"));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return criteria.list();
	}

	public int qtdDemitidosEm90Dias(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Date dataAte) 
	{
		StringBuilder hql = new StringBuilder("select count(co.id) from Colaborador co ");
		hql.append("	inner join co.historicoColaboradors hc ");
		hql.append("	where co.empresa.id = :empresaId ");
		hql.append("	and co.desligado = true ");
		hql.append("	and co.dataDesligamento between :dataIni and :dataFim ");
		hql.append("	and (co.dataDesligamento - co.dataAdmissao) <= :qtdDias ");
		hql.append("    and hc.data = ( ");
		hql.append("      select max(hc2.data) ");
		hql.append("      from HistoricoColaborador as hc2 ");
		hql.append("      where hc2.colaborador.id = co.id ");
		hql.append("      and hc2.status = :status ");
		hql.append("   ) ");
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			hql.append("and hc.estabelecimento.id in (:estabelecimentoIds) ");

		if (LongUtil.arrayIsNotEmpty(areaIds))
			hql.append("and hc.areaOrganizacional.id in (:areaIds) ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", DateUtil.incrementaDias(dataAte, -90));
		query.setDate("dataFim", dataAte);
		query.setInteger("qtdDias", 90);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(areaIds))
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
			
		return (Integer) query.uniqueResult();
	}

	public int qtdAdmitidosPeriodoEm90Dias(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Date dataAte) 
	{
		StringBuilder hql = new StringBuilder("select count(co.id) from Colaborador co ");
		hql.append("	inner join co.historicoColaboradors hc ");
		hql.append("	where co.empresa.id = :empresaId ");
		hql.append("	and co.dataAdmissao between :dataIni and :dataFim ");
		hql.append("    and hc.data = ( ");
		hql.append("      select max(hc2.data) ");
		hql.append("      from HistoricoColaborador as hc2 ");
		hql.append("      where hc2.colaborador.id = co.id ");
		hql.append("      and hc2.status = :status ");
		hql.append("   ) ");

		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			hql.append("and hc.estabelecimento.id in (:estabelecimentoIds) ");

		if (LongUtil.arrayIsNotEmpty(areaIds))
			hql.append("and hc.areaOrganizacional.id in (:areaIds) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", DateUtil.incrementaDias(dataAte, -90));
		query.setDate("dataFim", dataAte);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(areaIds))
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		return (Integer) query.uniqueResult();
	}

	public Collection<Colaborador> findParentesByNome(Long colaboradorId, String nome, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.endereco.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.conjuge"), "pessoalConjuge");
		p.add(Projections.property("c.pessoal.pai"), "pessoalPai");
		p.add(Projections.property("c.pessoal.mae"), "pessoalMae");
		p.add(Projections.property("c.endereco.logradouro"), "enderecoLogradouro");
		p.add(Projections.property("c.endereco.complemento"), "enderecoComplemento");
		p.add(Projections.property("c.endereco.numero"), "enderecoNumero");
		p.add(Projections.property("c.endereco.bairro"), "enderecoBairro");
		p.add(Projections.property("ci.nome"), "enderecoCidadeNome");
		p.add(Projections.property("u.sigla"), "enderecoUfSigla");
		p.add(Projections.property("c.contato.ddd"), "contatoDdd");
		p.add(Projections.property("c.contato.foneCelular"), "contatoCelular");
		p.add(Projections.property("c.contato.foneFixo"), "contatoFoneFixo");
		p.add(Projections.property("e.nome"), "empresaNome");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.desligado", false));
		
		if(colaboradorId != null)
			criteria.add(Restrictions.ne("c.id", colaboradorId));
		
		if(empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));

		if(StringUtils.isNotBlank(nome))
			criteria.add(Expression.or(
							Expression.or(Restrictions.sqlRestriction("normalizar({alias}.nome) ilike  normalizar(?)", nome, Hibernate.STRING), 
											Restrictions.sqlRestriction("normalizar({alias}.conjuge) ilike  normalizar(?)", nome, Hibernate.STRING)), 
							Expression.or(Restrictions.sqlRestriction("normalizar({alias}.pai) ilike  normalizar(?)", nome, Hibernate.STRING), 
											Restrictions.sqlRestriction("normalizar({alias}.mae) ilike  normalizar(?)", nome, Hibernate.STRING))
						));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}
	
	public String[] findEmailsByPapel(Collection<Long> usuarioEmpresaIds)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct c.email from usuarioempresa as ue ");
		sql.append("inner join usuario u on u.id = ue.usuario_id ");
		sql.append("inner join colaborador c on c.usuario_id = u.id ");
		sql.append("where ue.id in (:usuarioEmpresaIds) ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("usuarioEmpresaIds", usuarioEmpresaIds, Hibernate.LONG);
		
		return StringUtil.converteCollectionToArrayString(query.list());
	}

	public void atualizaSolicitacaoDesligamento(Date dataSolicitacaoDesligamento, Date dataSolicitacaoDesligamentoAC, String observacaoDemissao, Long motivoId, Character gerouSubstituicao, Long solicitanteDemissaoId, Long colaboradorId) 
	{
		String hql = "update Colaborador set dataSolicitacaoDesligamento = :dataSolicitacaoDesligamento, dataSolicitacaoDesligamentoAc = :dataSolicitacaoDesligamentoAc, " +
					"observacaoDemissao = :observacaoDemissao, demissaoGerouSubstituicao = :gerouSubstituicao, motivoDemissao.id = :motivoId, solicitanteDemissao.id = :solicitanteDemissaoId where id = :colaboradorId";
		
		Query query = getSession().createQuery(hql);
		query.setDate("dataSolicitacaoDesligamento", dataSolicitacaoDesligamento);
		query.setDate("dataSolicitacaoDesligamentoAc", dataSolicitacaoDesligamentoAC);
		query.setString("observacaoDemissao", observacaoDemissao);
		query.setParameter("gerouSubstituicao", gerouSubstituicao, Hibernate.CHARACTER);
		query.setParameter("motivoId", motivoId, Hibernate.LONG);
		query.setParameter("solicitanteDemissaoId", solicitanteDemissaoId, Hibernate.LONG);
		query.setLong("colaboradorId", colaboradorId);
		
		query.executeUpdate();
	}
	
	public Collection<Colaborador> findPendenciasSolicitacaoDesligamentoAC(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");

		criteria.setProjection(p);
		criteria.add(Expression.isNotNull("c.dataSolicitacaoDesligamentoAc"));
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<Colaborador> findAdmitidosHaDiasSemEpi(Collection<Integer> dias, Long empresaId)
	{
		DetachedCriteria subQuerySe = DetachedCriteria.forClass(SolicitacaoEpi.class, "se")
				.setProjection(Projections.id())
				.add(Restrictions.eqProperty("se.colaborador.id", "c.id"));
		
		DetachedCriteria subQueryHf = DetachedCriteria.forClass(HistoricoFuncao.class, "hf2")
				.setProjection(Projections.max("hf2.data"))
				.add(Restrictions.le("hf2.data", new Date()))
				.add(Restrictions.eqProperty("hf2.funcao.id", "f.id"));
		
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);

		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("hc.funcao", "f");
		criteria.createCriteria("f.historicoFuncaos", "hf");
		criteria.createCriteria("hf.epis", "e");
		montaProjection(criteria);

		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Property.forName("hf.data").eq(subQueryHf));
		criteria.add( Subqueries.notExists(subQuerySe) );
		criteria.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));
		criteria.add(Expression.sqlRestriction("(? - dataAdmissao) in ("+dias.toString().replaceAll("[\\[\\]]","") +")", new Date(), Hibernate.DATE));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}
	
	public Collection<Colaborador> findAguardandoEntregaEpi(Collection<Integer> diasLembrete, Long empresaId)
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);

		Criteria criteria = getSession().createCriteria(SolicitacaoEpi.class, "se");
		criteria.createCriteria("se.colaborador", "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		montaProjection(criteria);

		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Expression.eq("c.desligado", false));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.sqlRestriction("(? - {alias}.data) in ("+diasLembrete.toString().replaceAll("[\\[\\]]","") +")", new Date(), Hibernate.DATE));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	private void montaProjection(Criteria criteria) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.dataEncerramentoContrato"), "dataEncerramentoContrato");
		criteria.setProjection(p);
	}
	
	public Collection<Colaborador> triar(Long[] empresaIds, String escolaridade, String sexo, Date dataNascIni, Date dataNascFim, String[] faixasCheck, Long[] areasIds, Long[] competenciasIds, boolean exibeCompatibilidade, boolean opcaoTodasEmpresas) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador (co.id, co.nome, co.pessoal.dataNascimento, co.pessoal.sexo, co.pessoal.escolaridade, coalesce(sum(chn.ordem),0) as somaCompetencias) ");
		hql.append("from HistoricoColaborador hc ");
		hql.append("inner join hc.colaborador co  ");
		hql.append("inner join hc.faixaSalarial fs ");
		hql.append("inner join fs.cargo c ");
		hql.append("left join co.configuracaoNivelCompetenciaColaboradors cncc "); 
		hql.append("left join cncc.configuracaoNivelCompetencias cnc "); 
		hql.append("left join cnc.nivelCompetencia nc ");
		hql.append("left join nc.configHistoricoNiveis chn ");
		hql.append("left join chn.nivelCompetenciaHistorico nch ");
		if (competenciasIds != null && competenciasIds.length > 0)
			hql.append("with cnc.competenciaId in (:competenciasIds) ");
		
		hql.append("where hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador.id = co.id and hc2.status = :status) "); 
		hql.append("and co.desligado = false "); 
		hql.append("and (cncc.data = (select max(cncc2.data) from ConfiguracaoNivelCompetenciaColaborador cncc2 where cncc2.colaborador.id = co.id) or cncc.data is null) ");
		hql.append("and ( nch.data = (select max(data) from NivelCompetenciaHistorico where empresa.id = nc.empresa.id) or nch.data is null)");

		if (empresaIds != null && empresaIds.length > 0)
			hql.append("and co.empresa.id in(:empresaIds) ");
		if (sexo != null && !sexo.equals("I"))
			hql.append("and co.pessoal.sexo = :sexo "); 
		if (!StringUtil.isBlank(escolaridade))
			hql.append("and cast(co.pessoal.escolaridade as integer) >= :escolaridade "); 
		if (areasIds.length > 0)
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");
		if (faixasCheck != null && faixasCheck.length > 0){
			if(opcaoTodasEmpresas)
				hql.append("and c.nome || ' ' || fs.nome ||' ' || (CASE c.ativo WHEN true THEN '(Ativo)' ELSE '(Inativo)' END) in ( :faixasDescricaoComAtivo) ");
			else
				hql.append("and fs.id in (:faixasIds) ");
		}
		
		if (dataNascIni != null)
			hql.append("and co.pessoal.dataNascimento <= :dataNascIni ");
		if (dataNascFim != null)
			hql.append("and co.pessoal.dataNascimento >= :dataNascFim ");
		
		hql.append("group by co.id, co.nome, co.pessoal.dataNascimento, co.pessoal.sexo, co.pessoal.escolaridade ");
		
		if (exibeCompatibilidade)
			hql.append("order by coalesce(sum(chn.ordem), 0) desc, co.nome ");
		else
			hql.append("order by co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (competenciasIds != null && competenciasIds.length > 0)
			query.setParameterList("competenciasIds", competenciasIds);
		if (empresaIds != null && empresaIds.length > 0)
			query.setParameterList("empresaIds", empresaIds);
		if (sexo != null && !sexo.equals("I"))
			query.setString("sexo", sexo);
		if (!StringUtil.isBlank(escolaridade))
			query.setInteger("escolaridade", Integer.parseInt(escolaridade));
		if (areasIds.length > 0)
			query.setParameterList("areasIds", areasIds);
		
		if (faixasCheck != null && faixasCheck.length > 0){
			if(opcaoTodasEmpresas){
				query.setParameterList("faixasDescricaoComAtivo",faixasCheck);
			}
			else
				query.setParameterList("faixasIds", LongUtil.arrayStringToArrayLong(faixasCheck));
		}
		if (dataNascIni != null)
			query.setDate("dataNascIni", dataNascIni);
		if (dataNascFim != null)
			query.setDate("dataNascFim", dataNascFim);
		return query.list();
	}
	
	public Collection<Colaborador> findParaLembreteTerminoContratoTemporario(Collection<Integer> diasLembrete, Long empresaId)
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		montaProjection(criteria);
		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Expression.eq("c.desligado", false));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.sqlRestriction("({alias}.dataEncerramentoContrato - ?) in ("+diasLembrete.toString().replaceAll("[\\[\\]]","") +")", new Date(), Hibernate.DATE));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findHabilitacaAVencer(Collection<Integer> diasLembrete, Long empresaId)
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("c.empresa", "e");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("ao.areaMae", "am", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.habilitacao.vencimento"), "projectionVencimentoHabilitacao");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.property("am.id"), "areaOrganizacionalAreaMaeId");
		criteria.setProjection(p);

		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Expression.eq("c.desligado", false));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.sqlRestriction("({alias}.vencimento - ?) in ("+diasLembrete.toString().replaceAll("[\\[\\]]","") +")", new Date(), Hibernate.DATE));
		
		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();	
	}

	public Collection<Colaborador> findByEmpresaAndStatusAC(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, int statusAC, boolean semCodigoAc, boolean comNaoIntegraAC, String situacaoColaborador, boolean primeiroHistorico, String... order)
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");

		if(primeiroHistorico)
			subQueryHc.setProjection(Projections.min("hc2.data")); // Menor data
		else
			subQueryHc.setProjection(Projections.max("hc2.data")); // Maior data
		
		subQueryHc.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"));
		subQueryHc.add(Restrictions.eq("hc2.status", statusAC));

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("c.endereco.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("hc.estabelecimento", "e");
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.indice", "i", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca");
		criteria.createCriteria("c.pessoal.ctps.ctpsUf", "ctpsUf", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.pessoal.rgUf", "rgUf", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.vinculo"), "vinculo");
		p.add(Projections.property("c.pessoal.dataNascimento"), "projectionDataNascimento");
		p.add(Projections.property("c.pessoal.cpf"), "colaboradorCPF");
		p.add(Projections.property("c.pessoal.pis"), "projectionPis");
		p.add(Projections.property("c.pessoal.rg"), "projectionRg");
		p.add(Projections.property("c.pessoal.rgOrgaoEmissor"), "projectionRgOrgaoEmissor");
		p.add(Projections.property("c.pessoal.rgDataExpedicao"), "projectionRgDataExpedicao");
		p.add(Projections.property("rgUf.id"), "projectionRgUfId");
		p.add(Projections.property("rgUf.sigla"), "projectionRgUfSigla");
		p.add(Projections.property("c.pessoal.conjuge"), "pessoalConjuge");
		p.add(Projections.property("c.pessoal.pai"), "pessoalPai");
		p.add(Projections.property("c.pessoal.mae"), "pessoalMae");
		p.add(Projections.property("c.pessoal.sexo"), "projectionSexo");
		p.add(Projections.property("c.pessoal.escolaridade"), "pessoalEscolaridade");
		p.add(Projections.property("c.pessoal.estadoCivil"), "pessoalEstadoCivil");
		p.add(Projections.property("c.pessoal.deficiencia"), "projectionDeficiencia");
		p.add(Projections.property("c.contato.ddd"), "contatoDdd");
		p.add(Projections.property("c.contato.foneFixo"), "contatoFoneFixo");
		p.add(Projections.property("c.contato.foneCelular"), "contatoCelular");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("c.endereco.logradouro"), "enderecoLogradouro");
		p.add(Projections.property("c.endereco.complemento"), "enderecoComplemento");
		p.add(Projections.property("c.endereco.numero"), "enderecoNumero");
		p.add(Projections.property("c.endereco.bairro"), "enderecoBairro");
		p.add(Projections.property("c.endereco.cep"), "enderecoCep");
		p.add(Projections.property("u.sigla"), "enderecoUfSigla");
		p.add(Projections.property("u.id"), "enderecoUfId");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitNumero"), "projectionTituloNumero");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitZona"), "projectionTituloZona");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitSecao"), "projectionTituloSecao");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilNumero"), "projectionCertMilNumero");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilTipo"), "projectionCertMilTipo");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilSerie"), "projectionCertMilSerie");
		p.add(Projections.property("c.pessoal.ctps.ctpsNumero"), "projectionCtpsNumero");
		p.add(Projections.property("c.pessoal.ctps.ctpsSerie"), "projectionCtpsSerie");
		p.add(Projections.property("c.pessoal.ctps.ctpsDv"), "projectionCtpsDv");
		p.add(Projections.property("c.pessoal.ctps.ctpsDataExpedicao"), "projectionCtpsDataExpedicao");
		p.add(Projections.property("ctpsUf.id"), "projectionCtpsUfId");
		p.add(Projections.property("ctpsUf.sigla"), "projectionCtpsUfSigla");
		p.add(Projections.property("c.habilitacao.numeroHab"), "projectionNumeroHabilitacao");
		p.add(Projections.property("c.habilitacao.emissao"), "projectionEmissaoHabilitacao");
		p.add(Projections.property("c.habilitacao.vencimento"), "projectionVencimentoHabilitacao");
		p.add(Projections.property("c.habilitacao.categoria"), "projectionCategoriaHabilitacao");
		p.add(Projections.property("ci.id"), "enderecoCidadeId");
		p.add(Projections.property("ci.nome"), "enderecoCidadeNome");
		p.add(Projections.property("ci.codigoAC"), "projectionCidadeCodigoAC");
		p.add(Projections.property("hc.id"), "historicoColaboradorIdProjection");
		p.add(Projections.property("hc.salario"), "historicoColaboradorSalarioProjection");
		p.add(Projections.property("hc.data"), "historicoColaboradorDataProjection");
		p.add(Projections.property("hc.motivo"), "historicoColaboradorMotivoProjection");
		p.add(Projections.property("hc.gfip"), "historicoColaboradorGfipProjection");
		p.add(Projections.property("hc.quantidadeIndice"), "historicoColaboradorQuantidadeIndiceProjection");
		p.add(Projections.property("hc.tipoSalario"), "historicoColaboradorTipoSalarioProjection");
		p.add(Projections.property("hc.status"), "historicoColaboradorStatusProjection");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.property("ao.codigoAC"), "areaOrganizacionalCodigoAC");
		p.add(Projections.alias(Projections.sqlProjection("monta_familia_area(ao4_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome"));
		p.add(Projections.property("ca.id"), "cargoIdProjection");
		p.add(Projections.property("fs.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("fs.codigoAC"), "faixaSalarialCodigoACProjection");
		p.add(Projections.property("e.id"), "estabelecimentoIdProjection");
		p.add(Projections.property("e.codigoAC"), "estabelecimentoCodigoACProjection");
		p.add(Projections.property("i.codigoAC"), "indiceCodigoAC");
		p.add(Projections.property("e.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");
		criteria.setProjection(p);

		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(comNaoIntegraAC)
			criteria.add(Expression.eq("c.naoIntegraAc", false));
		
		if(SituacaoColaborador.ATIVO.equals(situacaoColaborador))
			criteria.add(Expression.eq("c.desligado", false));
		else if (SituacaoColaborador.DESLIGADO.equals(situacaoColaborador))
			criteria.add(Expression.eq("c.desligado", true));
		
		if(semCodigoAc)
			criteria.add(Expression.isNull("c.codigoAC"));
		
		if(!ArrayUtils.isEmpty(areasIds))
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		
		if(!ArrayUtils.isEmpty(estabelecimentosIds))
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));

		if(!ArrayUtils.isEmpty(order))
			for (String od : order) 
				criteria.addOrder(Order.asc(od));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();	
	}
		
	public void desvinculaCandidato(Long candidatoId) 
	{
		String hql = "update Colaborador set candidato.id = null where candidato.id = :candidatoId";
		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);
		
		query.executeUpdate();
	}

	public Collection<Colaborador> findAguardandoDesligamento(Long empresaId, Long[] areasIdsPorResponsavel, Long colaboradorId) 
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.empresa", "e");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.estabelecimento", "est");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("hc.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "ca");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.property("ca.id"), "cargoIdProjection");
		p.add(Projections.property("ca.nome"), "cargoNomeProjection");
		p.add(Projections.property("fs.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("fs.nome"), "faixaSalarialNomeProjection");
		p.add(Projections.property("est.id"), "estabelecimentoIdProjection");
		p.add(Projections.property("est.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("c.dataSolicitacaoDesligamento"), "dataSolicitacaoDesligamento");

		criteria.setProjection(p);
		criteria.add(Expression.eq("e.id", empresaId));
		criteria.add(Expression.isNotNull("c.dataSolicitacaoDesligamento"));
		criteria.add(Expression.isNull("c.dataSolicitacaoDesligamentoAc"));
		criteria.add(Expression.isNull("c.dataDesligamento"));
		
		if(areasIdsPorResponsavel != null)
			criteria.add(Expression.in("ao.id", areasIdsPorResponsavel));
		
		if(colaboradorId != null)
			criteria.add(Expression.not(Expression.eq("c.id", colaboradorId)));
		
		criteria.add(Expression.or(Expression.isNull("hc.data"), Subqueries.propertyEq("hc.data", subQueryHc)));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public void removeComDependencias(Long id) 
	{
		String[] hqls = new String[]{
				"UPDATE Candidato SET disponivel = true, contratado = false WHERE id IN (SELECT candidato.id FROM Colaborador WHERE id = :id)",
				"UPDATE CandidatoSolicitacao SET status = '" + StatusCandidatoSolicitacao.INDIFERENTE + "' WHERE candidato.id IN (SELECT candidato.id from Colaborador WHERE id = :id)",
				
				"DELETE FROM ParticipanteCursoLnt WHERE colaborador.id = :id",
				
				"UPDATE AreaOrganizacional SET responsavel.id = NULL WHERE responsavel.id = :id",
				"UPDATE AreaOrganizacional SET coResponsavel.id = NULL WHERE coResponsavel.id = :id",
				"DELETE FROM CandidatoEleicao WHERE candidato.id = :id",
				"DELETE FROM ColaboradorAfastamento WHERE colaborador.id = :id",
				"DELETE FROM ColaboradorIdioma WHERE colaborador.id = :id",
				"DELETE FROM ColaboradorOcorrencia WHERE colaborador.id = :id",
				"DELETE FROM ColaboradorPeriodoExperienciaAvaliacao WHERE colaborador.id = :id",
				"DELETE FROM ComissaoEleicao WHERE colaborador.id = :id",
				"DELETE FROM ComissaoMembro WHERE colaborador.id = :id",
				"UPDATE ComissaoPlanoTrabalho SET responsavel.id = NULL WHERE responsavel.id = :id",
				"UPDATE ComissaoPlanoTrabalho SET corresponsavel.id = NULL WHERE corresponsavel.id = :id",
				"DELETE FROM ComissaoReuniaoPresenca WHERE colaborador.id = :id",
				"DELETE FROM Dependente WHERE colaborador.id = :id",
				"DELETE FROM Prontuario WHERE colaborador.id = :id",
				
				"DELETE FROM Cat WHERE colaborador.id = :id",
				
				"DELETE FROM ColaboradorResposta WHERE colaboradorQuestionario.id IN (SELECT id FROM ColaboradorQuestionario WHERE avaliador.id = :id OR colaborador.id = :id)",
				"UPDATE ConfiguracaoNivelCompetenciaColaborador set colaboradorQuestionario.id = null WHERE  colaboradorQuestionario.id in(select id from ColaboradorQuestionario where avaliador.id = :id OR colaborador.id = :id) ",
				"DELETE FROM ColaboradorQuestionario WHERE avaliador.id = :id OR colaborador.id = :id",
				
				"DELETE FROM AproveitamentoAvaliacaoCurso WHERE colaboradorTurma.id IN (SELECT id FROM ColaboradorTurma WHERE colaborador.id = :id)",
				"DELETE FROM ColaboradorPresenca WHERE colaboradorTurma.id IN (SELECT id FROM ColaboradorTurma WHERE colaborador.id = :id)",
				"DELETE FROM ColaboradorTurma WHERE colaborador.id = :id",
				
				"delete from ConfiguracaoNivelCompetenciaCriterio where configuracaoNivelCompetencia.id in(Select id FROM ConfiguracaoNivelCompetencia WHERE configuracaoNivelCompetenciaColaborador.id IN (SELECT id FROM ConfiguracaoNivelCompetenciaColaborador WHERE colaborador.id = :id))",
				"DELETE FROM ConfiguracaoNivelCompetencia WHERE configuracaoNivelCompetenciaColaborador.id IN (SELECT id FROM ConfiguracaoNivelCompetenciaColaborador WHERE colaborador.id = :id)",
				"DELETE FROM ConfiguracaoNivelCompetenciaColaborador WHERE colaborador.id = :id",
				
				"DELETE FROM ConfiguracaoNivelCompetencia WHERE configuracaoNivelCompetenciaColaborador.id IN (SELECT id FROM ConfiguracaoNivelCompetenciaColaborador WHERE avaliador.id = :id)",
				"DELETE FROM ConfiguracaoNivelCompetenciaColaborador WHERE avaliador.id = :id",
				
				"UPDATE Experiencia SET colaborador.id = NULL WHERE colaborador.id = :id AND candidato.id IS NOT NULL",
				"DELETE FROM Experiencia WHERE colaborador.id = :id AND candidato.id IS NULL",

				"UPDATE Formacao SET colaborador.id = NULL WHERE colaborador.id = :id AND candidato.id IS NOT NULL",
				"DELETE FROM Formacao WHERE colaborador.id = :id AND candidato.id IS NULL",
				
				"DELETE FROM GastoEmpresaItem WHERE gastoEmpresa.id IN (SELECT id FROM GastoEmpresa WHERE colaborador.id = :id)",
				"DELETE FROM GastoEmpresa WHERE colaborador.id = :id",
				
				"DELETE FROM HistoricoColaboradorBeneficio WHERE colaborador.id = :id",
				
				"DELETE FROM UsuarioMensagem WHERE mensagem.id IN (SELECT id FROM Mensagem WHERE colaborador.id = :id)",
				"DELETE FROM Mensagem WHERE colaborador.id = :id",
				
				"DELETE FROM SolicitacaoEpiItemEntrega WHERE solicitacaoEpiItem.id IN (SELECT id FROM SolicitacaoEpiItem WHERE solicitacaoEpi.id IN (SELECT id FROM SolicitacaoEpi WHERE colaborador.id = :id))",
				"DELETE FROM SolicitacaoEpiItem WHERE solicitacaoEpi.id IN (SELECT id FROM SolicitacaoEpi WHERE colaborador.id = :id)",
				"DELETE FROM SolicitacaoEpi WHERE colaborador.id = :id",
				
				"DELETE FROM ExameSolicitacaoExame WHERE solicitacaoExame.id IN (SELECT id FROM SolicitacaoExame WHERE colaborador.id = :id)",
				"DELETE FROM SolicitacaoExame WHERE colaborador.id = :id",
				
				"UPDATE Colaborador SET solicitanteDemissao.id = NULL WHERE solicitanteDemissao.id = :id",
				"DELETE FROM HistoricoColaborador WHERE colaborador.id = :id",
				"DELETE FROM ReajusteColaborador WHERE colaborador.id = :id",
				
				"DELETE FROM ColaboradorAvaliacaoPratica where colaborador.id = :id ",
				"DELETE FROM ColaboradorCertificacao where colaborador.id = :id ",
				
				"DELETE FROM ParticipanteAvaliacaoDesempenho WHERE colaborador.id = :id",
				"DELETE FROM ConfiguracaoCompetenciaAvaliacaoDesempenho WHERE avaliador.id = :id",
				
				"DELETE FROM OrdemDeServico WHERE colaborador.id = :id",
				
				"DELETE FROM Colaborador WHERE id = :id"
		};
		
		for (String hql : hqls) 
		{
			getSession().createQuery(hql).setLong("id", id).executeUpdate();
		}
	}

	public Collection<Usuario> findUsuarioByAreaEstabelecimento(Long[] areasIds, Long[] estabelecimentosIds)
	{
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.usuario", "u");
		criteria.createCriteria("c.historicoColaboradors", "hc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "id");
		p.add(Projections.property("u.nome"), "nome");

		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		
		if(!ArrayUtils.isEmpty(areasIds))
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		
		if(!ArrayUtils.isEmpty(estabelecimentosIds))
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));
		
		criteria.add(Expression.eq("u.acessoSistema", true));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));

		return criteria.list();
	}

	private DetachedCriteria montaSubQueryHistoricoColaborador(Date data, Integer status)
	{
		return DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", data))
				.add(Restrictions.eq("hc2.status", status));
	}

	public Collection<Colaborador> findColaboradoresByCodigoAC(Long empresaId, boolean joinComHistorico, String... codigosACColaboradores) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		
		ProjectionList p = Projections.projectionList().create();

		if(joinComHistorico)
		{
			criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.LEFT_JOIN);

			DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", new Date()))
				.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

			criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
			
			p.add(Projections.property("hc.areaOrganizacional.id"), "areaOrganizacionalId");
		}

		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.empresa.id"), "empresaId");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("c.usuario.id"), "usuarioIdProjection");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.in("c.codigoAC", codigosACColaboradores));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}
	
	public int countColaboradoresComHistoricos(Long empresaId){
		StringBuilder hql = new StringBuilder();
		hql.append("select count( c.id ) from Colaborador c where c.desligado = false and c.id in (select hc.colaborador.id from HistoricoColaborador hc ) ");
		
		if(empresaId != null)
			hql.append("and c.empresa.id = :empresaId");
			
		Query query = getSession().createQuery(hql.toString());
		if(empresaId != null)
			query.setLong("empresaId", empresaId);
		
		return (Integer) query.uniqueResult();
	}

	public Colaborador findColaboradorComTodosOsDados(Long id) {
		return super.findById(id);
	}

	public Collection<Colaborador> findByEstadosCelularOitoDigitos(Long[] ufId) {

		String hql = "select c from Colaborador c where c.endereco.uf.id in (:ufId) and length(c.contato.foneCelular) = 8";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ufId", ufId);
		
		Collection<Colaborador> colaboradores = query.list(); 
		
		return colaboradores;
	}
	
	public void setDataSolicitacaoDesligamentoACByDataDesligamento(Long empresaId){
		Query query = getSession().createQuery("update Colaborador set dataSolicitacaoDesligamentoAc = dataDesligamento where codigoAC is null and empresa.id = :empresaId and dataDesligamento is not null");
		
		query.setLong("empresaId", empresaId);
		
		query.executeUpdate();
	}
	
	public Collection<Colaborador> listColaboradorComDataSolDesligamentoAC(Long empresaId){
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
			p.add(Projections.property("c.dataDesligamento"), "dataDesligamento")
					.add(Projections.property("c.id"), "id")
					.add(Projections.property("c.observacaoDemissao"), "observacaoDemissao")
					.add(Projections.property("c.demissaoGerouSubstituicao"), "demissaoGerouSubstituicao")
					.add(Projections.property("c.motivoDemissao.id"), "projectionMotivoDemissaoId");
					
			criteria.setProjection(p);

			criteria.add(Expression.isNotNull("c.codigoAC"));
			criteria.add(Expression.isNotNull("c.dataSolicitacaoDesligamentoAc"))
			.add(Expression.isNotNull("c.dataDesligamento"))
			.add(Expression.eq("c.empresa.id", empresaId));
			
			criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
			return criteria.list();
	}
	
	public Integer countDemitidosPeriodo(Date dataIni, Date dataFim, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, boolean reducaoDeQuadro) 
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", new Date()))
				.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "f", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.motivoDemissao", "m", Criteria.LEFT_JOIN);
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.countDistinct("c.id"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));
		criteria.add(Expression.between("c.dataDesligamento", dataIni, dataFim));

		if(reducaoDeQuadro)
			criteria.add(Expression.eq("m.reducaoDeQuadro", reducaoDeQuadro));
		if(estabelecimentosIds != null && estabelecimentosIds.size() > 0)
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));
		if(areasIds != null && areasIds.size() > 0)
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		if(cargosIds != null && cargosIds.size() > 0)
			criteria.add(Expression.in("f.cargo.id", cargosIds));
		if(vinculos != null && vinculos.size() > 0)
			criteria.add(Expression.in("c.vinculo", vinculos));
		
		return (Integer) criteria.uniqueResult();
	}

	public boolean existeColaboradorAtivo(String cpf, Date data) {
		
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"),"id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.pessoal.cpf", cpf));
		criteria.add(Expression.or(Expression.ge("c.dataDesligamento", data), Expression.isNull("c.dataDesligamento")));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list().size() > 0;
	}

	public Collection<Colaborador> findColaboradorComESemOrdemDeServico(Colaborador colaborador, HistoricoColaborador historicoColaborador, Long[] areaIds, String situacao, String filtroOrdemDeServico, int page, int pagingSize) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "co.id"))
				.add(Restrictions.le("hc2.data", new Date()))
				.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"),"id");
		p.add(Projections.property("co.nome"),"nome");
		p.add(Projections.property("co.pessoal.cpf"),"colaboradorCPF");
		p.add(Projections.property("co.dataDesligamento"),"dataDesligamento");
		p.add(Projections.property("hc.funcao.id"),"funcaoId");
		p.add(Projections.sqlProjection("(select (count(*) > 0) from historicocolaborador where colaborador_id = co1_.id and funcao_id is not null ) as temFuncao", new String[] {"temFuncao"}, new Type[] {Hibernate.BOOLEAN}), "temFuncao");
		
		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "co", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("co.ordensDeServico", "os", Criteria.LEFT_JOIN);
		
		criteria.add(Expression.eq("co.empresa.id", colaborador.getEmpresa().getId()));
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		
		if(colaborador.getNome() != null && !colaborador.getNome().trim().isEmpty())
			criteria.add(Expression.ilike("co.nome", colaborador.getNome(), MatchMode.ANYWHERE));
		
		if(colaborador.getMatricula() != null && !colaborador.getMatricula().trim().isEmpty())
			criteria.add(Expression.ilike("co.matricula", colaborador.getMatricula(), MatchMode.ANYWHERE));
		
		if(colaborador.getPessoal() != null && colaborador.getPessoal().getCpf() != null && !colaborador.getPessoal().getCpf().trim().isEmpty())
			criteria.add(Expression.ilike("co.pessoal.cpf", colaborador.getPessoal().getCpf(), MatchMode.ANYWHERE));
		
		if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getId() != null)
			criteria.add(Expression.eq("hc.estabelecimento.id", historicoColaborador.getEstabelecimento().getId()));
		
		if((historicoColaborador.getAreaOrganizacional() == null || historicoColaborador.getAreaOrganizacional().getId() == null) && (areaIds != null && areaIds.length > 0))
			criteria.add(Expression.in("hc.areaOrganizacional.id", areaIds));
		else if(historicoColaborador.getAreaOrganizacional() != null && historicoColaborador.getAreaOrganizacional().getId() != null)
			criteria.add(Expression.eq("hc.areaOrganizacional.id", historicoColaborador.getAreaOrganizacional().getId()));
		
		if(historicoColaborador.getFaixaSalarial() != null ){
			if(historicoColaborador.getFaixaSalarial().getId() != null)
				criteria.add(Expression.eq("hc.faixaSalarial.id", historicoColaborador.getFaixaSalarial().getId()));

			if(historicoColaborador.getFaixaSalarial().getCargo() != null  & historicoColaborador.getFaixaSalarial().getCargo().getId() != null)
				criteria.add(Expression.eq("fs.cargo.id", historicoColaborador.getFaixaSalarial().getCargo().getId()));
		}
		
		if(situacao.equals(SituacaoColaborador.ATIVO))
			criteria.add(Expression.eq("co.desligado", false));
		else if(situacao.equals(SituacaoColaborador.DESLIGADO))
			criteria.add(Expression.eq("co.desligado", true));
		
		if(filtroOrdemDeServico.equals(FiltroOrdemDeServico.COM_ORDEM_DE_SERVICO))
			criteria.add(Expression.isNotNull("os.id"));
		else if(filtroOrdemDeServico.equals(FiltroOrdemDeServico.SEM_ORDEM_DE_SERVICO))
			criteria.add(Expression.isNull("os.id"));
		
		if(page != 0 && pagingSize != 0)
		{
			criteria.setMaxResults(pagingSize);
			criteria.setFirstResult((page - 1)*pagingSize);
		}
		criteria.addOrder(Order.asc("co.nome"));
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		return criteria.list();
	}
	
	public Colaborador findComDadosBasicosParaOrdemDeServico(Long colaboradorId, Date dataOrdemDeServico){
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(dataOrdemDeServico, StatusRetornoAC.CONFIRMADO);

		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.estabelecimento", "est", Criteria.LEFT_JOIN);
		criteria.createCriteria("est.endereco.cidade", "cid", Criteria.LEFT_JOIN);
		criteria.createCriteria("est.endereco.uf", "uf", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.funcao", "f", Criteria.LEFT_JOIN);
		criteria.createCriteria("f.historicoFuncaos", "hf", Criteria.LEFT_JOIN);
		
		criteria.add(Property.forName("hc.data").eq(subQueryHc));	
		criteria.add(Expression.eq("c.id", colaboradorId));
		
		criteria.setProjection(Projections.distinct(montaProjectionOrdemDeServico()));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		return (Colaborador) criteria.uniqueResult();
	}

	private ProjectionList montaProjectionOrdemDeServico() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("f.nome"), "funcaoNome");
		p.add(Projections.property("f.id"), "funcaoId");
		p.add(Projections.property("fs.codigoCbo"), "faixaSalarialCodigoCbo");
		p.add(Projections.property("ca.nome"), "cargoNomeProjection");
		p.add(Projections.property("est.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("est.endereco.logradouro"), "estabelecimentoEnderecoLogradouro");
		p.add(Projections.property("est.endereco.complemento"), "estabelecimentoEnderecoComplemento");
		p.add(Projections.property("est.endereco.numero"), "estabelecimentoEnderecoNumero");
		p.add(Projections.property("est.endereco.cep"), "estabelecimentoEnderecoCep");
		p.add(Projections.property("est.endereco.bairro"), "estabelecimentoEnderecoBairro");
		p.add(Projections.property("cid.nome"), "estabelecimentoEnderecoCidadeNome");
		p.add(Projections.property("uf.sigla"), "estabelecimentoEnderecoUfSigla");
		p.add(Projections.property("est.complementoCnpj"), "estabelecimentoComplementoCNPJ");
		return p;
	}

	public void updateRespondeuEntrevistaDesligamento(Long colaboradorId, boolean respondeuEntrevistaDesligamento) {
		String hql = "update Colaborador set respondeuEntrevista = :respondeuEntrevistaDesligamento where id = :colaboradorId";
		Query query = getSession().createQuery(hql);
		query.setLong("colaboradorId", colaboradorId);
		query.setBoolean("respondeuEntrevistaDesligamento", respondeuEntrevistaDesligamento);
		query.executeUpdate();
	}

	public Collection<ColaboradorJson> getColaboradoresJson(String baseCnpj, Long colaboradorId, String colaboradorMatricula) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new com.fortes.rh.model.json.ColaboradorJson(c.id, c.nome, c.pessoal.dataNascimento, c.pessoal.sexo, c.pessoal.cpf, c.pessoal.rg, uf.sigla, cid.nome, c.endereco.cep, ");
		hql.append(" c.endereco.logradouro, c.endereco.numero, c.endereco.bairro, c.contato.email, c.contato.ddd, c.contato.foneFixo, c.pessoal.escolaridade, c.pessoal.mae, c.pessoal.pai, c.matricula, c.vinculo, ");
		hql.append("c.dataAdmissao, c.dataDesligamento, c.dataEncerramentoContrato, ca.nome ||' '|| fs.nome, cast(monta_familia_area(ao.id), text) as ao_nome, func.nome, e.id, e.nome, e.cnpj) ");

		hql.append("from Colaborador as c ");
		hql.append("inner join c.empresa e ");
		hql.append("inner join c.historicoColaboradors hc ");
		hql.append("inner join hc.areaOrganizacional ao ");
		hql.append("left join hc.funcao func ");
		hql.append("inner join hc.faixaSalarial fs ");
		hql.append("inner join fs.cargo ca ");
		hql.append("left join c.endereco.uf uf ");
		hql.append("left join c.endereco.cidade cid ");
		hql.append("where hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = c.id ");
		hql.append("			and hc2.data <= current_date ");
		hql.append("			and hc2.status = :status ");
		hql.append("		) ");
		
		if(baseCnpj != null && !baseCnpj.isEmpty())
			hql.append("and e.cnpj = :baseCnpj ");
		
		if(colaboradorId != null)
			hql.append("and c.id = :colaboradorId ");
		
		if(colaboradorMatricula != null && !"".equals(colaboradorMatricula))
			hql.append("and c.matricula = :colaboradorMatricula ");

		Query query = getSession().createQuery(hql.toString());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		if(baseCnpj != null && !baseCnpj.isEmpty())
			query.setString("baseCnpj", baseCnpj);

		if(colaboradorId != null)
			query.setLong("colaboradorId", colaboradorId);
		
		if(colaboradorMatricula != null && !"".equals(colaboradorMatricula))
			query.setString("colaboradorMatricula", colaboradorMatricula);
		
		return query.list();
	}
	
	public Collection<Colaborador> findByAreasIds(Long... areasIds)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.empresa", "e");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("hc.estabelecimento", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("es.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.alias(Projections.sqlProjection("monta_familia_area(ao3_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome"));
		
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Subqueries.propertyEq("hc.data", montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO)));
		criteria.add(Expression.in("ao.id", areasIds));
		criteria.add(Expression.eq("c.desligado", false));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<Colaborador> findAniversariantesPorTempoDeEmpresa(int mes, boolean agruparPorArea, Long[] empresaIds, Long[] estabelecimentoIds, Long[] areaIds) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(c.id, c.nome, c.nomeComercial, c.matricula, emp.id, emp.nome, est.id, est.nome, ca.nome, fs.nome, ao.id, cast(monta_familia_area(ao.id), text) as col_10_0_,  ");
		hql.append("c.dataAdmissao, extract(year from current_date()) - extract(year from c.dataAdmissao)) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("join hc.colaborador as c ");
		hql.append("join c.empresa as emp ");
		hql.append("join hc.estabelecimento as est ");
		hql.append("join hc.areaOrganizacional as ao ");
		hql.append("join hc.faixaSalarial as fs ");
		hql.append("join fs.cargo as ca ");
		hql.append("where c.empresa.id in (:empresasIds) ");
		hql.append("and c.desligado = :desligado ");
		hql.append("and extract(year from current_date()) - extract(year from c.dataAdmissao) > 0 ");
		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 ");
		hql.append("					where  hc2.colaborador.id = c.id ");
		hql.append("						   and hc2.status = :status ");
		hql.append("						   and hc2.data <= to_date( extract(day from c.dataAdmissao) || '-' || extract(month from c.dataAdmissao) || '-' || extract(year from current_date()), 'DD-MM-YYYY')) ");
		if(mes != 0)
			hql.append("and date_part('month', c.dataAdmissao) = :mes ");
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and est.id in(:estabelecimentosIds) ");
		if(areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in(:areasIds) ");
		
		if(agruparPorArea)
			hql.append("order by emp.nome, col_10_0_, month(c.dataAdmissao), day(c.dataAdmissao), c.nome ");
		else
			hql.append("order by month(c.dataAdmissao), day(c.dataAdmissao), c.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("empresasIds",empresaIds);
		query.setBoolean("desligado", false);
		if(mes != 0)
			query.setDouble("mes", new Integer(mes));
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentosIds",estabelecimentoIds);
		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areasIds",areaIds);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
	
		return query.list();	
	}	
	
	public Collection<Colaborador> findByEmpresaEstabelecimentoAndAreaOrganizacional(Long[] empresasIds, Long[] estabelecimentosIds, Long[] areasIds, String situacaoColaborador)
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");
		subQueryHc.setProjection(Projections.max("hc2.data"));	
		subQueryHc.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"));
		subQueryHc.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("hc.estabelecimento", "e");
		criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.alias(Projections.sqlProjection("monta_familia_area(ao2_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome"));
		p.add(Projections.property("e.id"), "estabelecimentoIdProjection");
		p.add(Projections.property("e.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");
		criteria.setProjection(p);

		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Expression.in("c.empresa.id", empresasIds));
		
		if (situacaoColaborador != null)
		{
			if (situacaoColaborador.equalsIgnoreCase(SituacaoColaborador.ATIVO))
				criteria.add(Expression.or(Expression.isNull("c.dataDesligamento"), Expression.gt("c.dataDesligamento", new Date())));
			else
				criteria.add(Expression.or(Expression.isNotNull("c.dataDesligamento"), Expression.le("c.dataDesligamento", new Date())));
		}
		
		if(!ArrayUtils.isEmpty(areasIds))
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		
		if(!ArrayUtils.isEmpty(estabelecimentosIds))
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));

		criteria.addOrder(Order.asc("emp.nome")).addOrder(Order.asc("e.nome")).addOrder(Order.asc("areaOrganizacionalNome")).addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();	
	}

	public Collection<Colaborador> findColaboradoresQueNuncaRealizaramTreinamento(Long[] empresasIds, Long[] areasIds, Long[] estabelecimentosIds) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");
		subQueryHc.setProjection(Projections.max("hc2.data"));	
		subQueryHc.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"));
		subQueryHc.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("hc.estabelecimento", "e");
		criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.id")), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.alias(Projections.sqlProjection("monta_familia_area(ao2_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome"));
		p.add(Projections.property("e.id"), "estabelecimentoIdProjection");
		p.add(Projections.property("e.nome"), "estabelecimentoNomeProjection");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");
		criteria.setProjection(p);

		criteria.add(Property.forName("hc.data").eq(subQueryHc));
		criteria.add(Expression.in("c.empresa.id", empresasIds));
		
		criteria.add(Expression.or(Expression.isNull("c.dataDesligamento"), Expression.gt("c.dataDesligamento", new Date())));
		
		if(!ArrayUtils.isEmpty(areasIds))
			criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
		
		if(!ArrayUtils.isEmpty(estabelecimentosIds))
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));
		
		criteria.add(criterionColaboradorTurma());

		criteria.addOrder(Order.asc("emp.nome")).addOrder(Order.asc("e.nome")).addOrder(Order.asc("areaOrganizacionalNome")).addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	private Criterion criterionColaboradorTurma(){
		StringBuilder sql = new StringBuilder("{alias}.id not in ( select distinct ct.colaborador_id from colaboradorturma as ct ");
		sql.append("			                           inner join turma as t on t.id = ct.turma_id and t.realizada ");
		sql.append("                            ) ");
		return Expression.sqlRestriction(sql.toString());
	}

	public Collection<Colaborador> findByAdmitidos(Date data) {
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.contato.email"), "emailColaborador");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("e.emailRemetente"), "empresaEmailRemetente");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.dataAdmissao", data));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}		
}