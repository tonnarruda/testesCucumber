package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("unchecked")
public class ColaboradorDaoHibernate extends GenericDaoHibernate<Colaborador> implements ColaboradorDao
{
	private static final int AREA_ORGANIZACIONAL = 1;

	private static final int GRUPO_OCUPACIONAL = 2;

	private static final int CARGO = 3;

	private static final int NO_MES = 0;

	private static final int ADMITIDOS = 1;

	private static final int DEMITIDOS = 2;

	private static final int MOTIVODEMISSAO = 1;

	private static final int MOTIVODEMISSAOQUANTIDADE = 2;

	/**
	 * Retorna a lista de colaboradores das áreas organizacionais informadas com
	 * opção do uso de paginação. A informação historica(Área, Faixa e Salário)
	 * é buscada do Histórico do colaborador e setada em campos transientes.
	 * 
	 * @author Igo Coelho
	 * @param colaborador
	 * @since 16/02/2008
	 * @param Coleção
	 *            com os ID das Áreas Organizacionais
	 * @param Número
	 *            da página
	 * @param Quantidade
	 *            de itens para paginação
	 * @return Coleção com os colaboradores das Áreas Organizacionais Informadas
	 */
	public Collection<Colaborador> findByAreaOrganizacionalIds(Collection<Long> areaOrganizacionalIds, Integer page, Integer pagingSize, Colaborador colaborador)
	{
		String whereNome = "";
		String whereMatricula = "";
		String whereAreaIds = "";

		if(areaOrganizacionalIds != null && !areaOrganizacionalIds.isEmpty())
			whereAreaIds = "and ao.id in (:ids) ";

		if(colaborador != null)
		{
			if(colaborador.getNome() != null && !colaborador.getNome().equals(""))
				whereNome = "and lower(co.nome) like :nome ";

			if(colaborador.getMatricula() != null && !colaborador.getMatricula().equals(""))
				whereMatricula = "and lower(co.matricula) like :matricula ";
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
		hql.append("where co.desligado = false ");
		hql.append("and hc.status = :status ");
		hql.append(whereAreaIds);
		hql.append(whereNome + whereMatricula);
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("from HistoricoColaborador as hc2 ");
		hql.append("where hc2.colaborador.id = co.id ");
		hql.append("and hc2.data <= :hoje and hc2.status = :status) order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(!whereAreaIds.equals(""))
			query.setParameterList("ids", areaOrganizacionalIds, Hibernate.LONG);

		if(!whereNome.equals(""))
			query.setString("nome", "%" + colaborador.getNome().toLowerCase() + "%");

		if(!whereMatricula.equals(""))
			query.setString("matricula", "%" + colaborador.getMatricula().toLowerCase() + "%");
		
		if(page != null && pagingSize != null)
		{
			query.setFirstResult(((page - 1) * pagingSize));
			query.setMaxResults(pagingSize);
		}

		return query.list();
	}

	/**
	 * @author Igo Coelho
	 * @since 16/02/2008
	 * @param page
	 *            : Número da página
	 * @param pagingSize
	 *            : Quantidade de itens para paginação
	 * @param ids
	 *            : Array de Long com os ID das Áreas Organizacionais
	 * @return Coleção com os colaboradores das Áreas Organizacionais Informadas
	 */
	public Collection<Colaborador> findByAreaOrganizacionalIds(int page, int pagingSize, Long[] ids, Colaborador colaborador)
	{
		Collection<Long> param = new HashSet<Long>();
		for (int i = 0; i < ids.length; i++)
			param.add(ids[i]);
		return findByAreaOrganizacionalIds(param, page, pagingSize, colaborador);
	}

	/**
	 * @author Igo Coelho
	 * @since 16/02/2008
	 * @param Array
	 *            de Long com os ID das Áreas Organizacionais
	 * @return Coleção com os colaboradores das Áreas Organizacionais Informadas
	 */
	public Collection<Colaborador> findByAreaOrganizacionalIds(Long[] ids)
	{
		Collection<Long> param = new HashSet<Long>();
		for (int i = 0; i < ids.length; i++)
			param.add(ids[i]);
		return findByAreaOrganizacionalIds(param, null, null, null);
	}

	/**
	 * @author Igo Coelho
	 * @since 16/02/2008
	 * @param Área
	 *            Organizacionai desejada
	 * @return Coleção com os colaboradores das Áreas Organizacionais Informadas
	 */
	public Collection<Colaborador> findByArea(AreaOrganizacional area)
	{
		Collection<Long> param = new HashSet<Long>();
		param.add(area.getId());
		return findByAreaOrganizacionalIds(param, null, null, null);
	}

	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario)
	{
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

	public Colaborador findColaboradorPesquisa(Long id, Long empresaId)
	{
		Colaborador colaborador = new Colaborador();

		StringBuilder hql = new StringBuilder();
		hql.append("select co.id, co.nomeComercial, ao.id, ao.nome, co.empresa.id ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where ");
		hql.append("	co.empresa.id = :empresaId ");
		hql.append("	and co.id = :id ");
		hql.append("	and hc.status = :status ");
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	)");
		hql.append("order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		query.setLong("id", id);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		Collection<Colaborador> colaboradors = new LinkedList<Colaborador>();
		Collection lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Colaborador colaboradorTmp = new Colaborador();
			colaboradorTmp.setId((Long) array[0]);
			colaboradorTmp.setNomeComercial((String) array[1]);
			colaboradorTmp.setAreaOrganizacionalId((Long) array[2]);
			colaboradorTmp.setAreaOrganizacionalNome((String) array[3]);
			colaboradorTmp.setEmpresaId((Long) array[4]);

			colaboradors.add(colaboradorTmp);
		}

		if(colaboradors != null && !colaboradors.isEmpty())
			colaborador = (Colaborador) colaboradors.toArray()[0];

		return colaborador;
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
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("c.cursos"), "cursos");
		p.add(Projections.property("c.desligado"), "desligado");
		p.add(Projections.property("c.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.vinculo"), "vinculo");
		p.add(Projections.property("c.naoIntegraAc"), "naoIntegraAc");
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
		p.add(Projections.property("ctpsUf.id"), "projectionCtpsUfId");
		p.add(Projections.property("ctpsUf.sigla"), "projectionCtpsUfSigla");
		p.add(Projections.property("c.habilitacao.numeroHab"), "projectionNumeroHabilitacao");
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
		p.add(Projections.property("c.empresa.id"), "empresaId");
		p.add(Projections.property("c.candidato.id"), "candidatoId");
		p.add(Projections.property("ci.id"), "enderecoCidadeId");
		p.add(Projections.property("ci.nome"), "enderecoCidadeNome");
		p.add(Projections.property("ci.codigoAC"), "projectionCidadeCodigoAC");
		p.add(Projections.property("m.id"), "projectionMotivoDemissaoId");
		p.add(Projections.property("m.motivo"), "motivoDemissaoMotivo");
		p.add(Projections.property("u.sigla"), "enderecoUfSigla");
		p.add(Projections.property("u.id"), "enderecoUfId");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.codigoAC"), "empresaCodigoAC");
		p.add(Projections.property("ce.id"), "projectionCamposExtrasId");
		p.add(Projections.property("ce.texto1"), "projectionTexto1");
		p.add(Projections.property("ce.texto2"), "projectionTexto2");
		p.add(Projections.property("ce.texto3"), "projectionTexto3");
		p.add(Projections.property("ce.texto4"), "projectionTexto4");
		p.add(Projections.property("ce.texto5"), "projectionTexto5");
		p.add(Projections.property("ce.texto6"), "projectionTexto6");
		p.add(Projections.property("ce.texto7"), "projectionTexto7");
		p.add(Projections.property("ce.texto8"), "projectionTexto8");
		p.add(Projections.property("ce.texto9"), "projectionTexto9");
		p.add(Projections.property("ce.texto10"), "projectionTexto10");
		p.add(Projections.property("ce.data1"), "projectionData1");
		p.add(Projections.property("ce.data2"), "projectionData2");
		p.add(Projections.property("ce.data3"), "projectionData3");
		p.add(Projections.property("ce.valor1"), "projectionValor1");
		p.add(Projections.property("ce.valor2"), "projectionValor2");
		p.add(Projections.property("ce.numero1"), "projectionNumero1");
		

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Colaborador findColaboradorByIdProjection(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("c.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("c.naoIntegraAc"), "naoIntegraAc");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.motivoDemissao"), "motivoDemissao");
		p.add(Projections.property("c.camposExtras.id"), "projectionCamposExtrasId");

		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");

		p.add(Projections.property("c.pessoal.rg"), "projectionRg");
		p.add(Projections.property("c.pessoal.dataNascimento"), "projectionDataNascimento");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Colaborador findByUsuario(Usuario usuario, Long empresaId)
	{
		Colaborador colaborador = new Colaborador();

		StringBuilder hql = new StringBuilder();
		hql.append("select co.id, ao.id ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where ");
		hql.append("	co.empresa.id = :empresaId ");
		hql.append("	and co.usuario.id = :id ");
		hql.append("	and hc.status = :status ");
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	)");
		hql.append("order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		query.setLong("id", usuario.getId());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		Collection<Colaborador> colaboradors = new LinkedList<Colaborador>();
		Collection lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Colaborador colaboradorTmp = new Colaborador();
			colaboradorTmp.setId((Long) array[0]);
			colaboradorTmp.setAreaOrganizacionalId((Long) array[1]);

			colaboradors.add(colaboradorTmp);
		}

		if(colaboradors != null && !colaboradors.isEmpty())
			colaborador = (Colaborador) colaboradors.toArray()[0];

		return colaborador;
	}

	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.usuario", "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
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

	public Colaborador findTodosColaboradorCpf(String cpf, Long empresaId, Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("c.pessoal.cpf", cpf));
		
		if (colaboradorId != null)
			criteria.add(Expression.not((Expression.eq("c.id", colaboradorId))));
		
		if (empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return (Colaborador) criteria.uniqueResult();
	}

	public Collection<Colaborador> findbyCandidato(Long candidatoId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.candidato.id", candidatoId));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId)
	{
		if(funcaoId == null || ambienteId == null)
			return null;

		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nomeComercial) "); 
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co "); 
		hql.append("where ");
		hql.append("		hc.funcao.id = :funcaoId ");
		hql.append("		and hc.status = :status ");
		hql.append("		and hc.ambiente.id = :ambienteId and "); 
		hql.append("		co.desligado = false ");
		hql.append("		and hc.data = ("); 
		hql.append("					select max(hc2.data) "); 
		hql.append("					from HistoricoColaborador as hc2 ");
		hql.append("					where hc2.colaborador.id = co.id ");
		hql.append("					and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("				 )");
		hql.append(" order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("funcaoId", funcaoId);
		query.setLong("ambienteId", ambienteId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public boolean setCodigoColaboradorAC(String codigo, Long id)
	{
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

	public Colaborador findByCodigoAC(String codigo, Empresa empresa)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("empresa", "e");

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

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa", empresa));
		criteria.add(Expression.eq("c.codigoAC", codigo));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	@Override
	public Colaborador findById(Long colaboradorId)
	{
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
		hql.append("select new Colaborador(co.id, co.nomeComercial, hc.areaOrganizacional.id, hc.estabelecimento.id) ");
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

	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nomeComercial, a.id, a.nome, am.id, am.nome, hc.estabelecimento.id, hc.estabelecimento.nome, faixa.id, faixa.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as a ");
		hql.append("left join hc.faixaSalarial as faixa ");
		hql.append("left join a.areaMae as am ");
		hql.append("	where a.id in (:areaIds)  ");
		hql.append("	and hc.estabelecimento.id in (:estabelecimentoIds) ");
		hql.append("	and co.desligado = :desligado ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");
		hql.append(" order by co.nomeComercial ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setBoolean("desligado", false);
		query.setParameterList("areaIds", areasOrganizacionaisIds, Hibernate.LONG);
		query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nomeComercial, a.id, a.nome, am.id, am.nome, hc.estabelecimento.id, hc.estabelecimento.nome) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as a ");
		hql.append("left join a.areaMae as am ");
		hql.append("	where ca.id in (:cargoIds)  ");
		hql.append("	and hc.estabelecimento.id in (:estabelecimentoIds) ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);
		query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nomeComercial, a.id, a.nome, am.id, am.nome, hc.estabelecimento.id, hc.estabelecimento.nome) ");
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
		hql.append("select new Colaborador(co.id, co.nomeComercial, hc.areaOrganizacional.id, hc.estabelecimento.id) ");
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
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("u.id"), "usuarioIdProjection");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Collection<Colaborador> findAreaOrganizacionalByAreas(Collection<Long> estabelecimentoIds, Collection<Long> areaOrganizacionalIds,
			CamposExtras camposExtras, boolean habilitaCampoExtra)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(es.nome,ao.id, ao.nome, re.nome, co.nome, cg.nome, fs.nome, emp.nome, " +
				"co.nomeComercial, co.matricula, co.dataAdmissao, co.dataDesligamento, co.vinculo, co.pessoal.estadoCivil,  " +
				"co.pessoal.escolaridade, co.pessoal.mae, co.pessoal.pai, co.pessoal.cpf, co.pessoal.pis, co.pessoal.rg,  " +
				"co.pessoal.rgOrgaoEmissor, co.pessoal.deficiencia, co.pessoal.rgDataExpedicao, co.pessoal.sexo,  " +
				"co.pessoal.dataNascimento, co.pessoal.conjuge, co.pessoal.qtdFilhos, co.habilitacao.numeroHab, co.habilitacao.emissao,  " +
				"co.habilitacao.vencimento, co.habilitacao.categoria, co.endereco.logradouro, co.endereco.complemento, co.endereco.numero,  " +
				"co.endereco.bairro, co.endereco.cep, co.contato.email, co.contato.foneCelular,	co.contato.foneFixo " );
				
				if(habilitaCampoExtra && camposExtras != null)
				{
					hql.append(" ,ce.texto1, ce.texto2, ce.texto3, ce.texto4, ce.texto5, ce.texto6, ce.texto7, ce.texto8, ce.texto9, ce.texto10, ");
					hql.append(" ce.data1, ce.data2, ce.data3, ce.valor1, ce.valor2, ce.numero1 ");
				}
				
		hql.append(" )  " );
		
		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("right join hc1.areaOrganizacional as ao ");
		hql.append("left join hc1.areaOrganizacional.responsavel as re ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join co.empresa as emp ");
		hql.append("left join hc1.faixaSalarial as fs ");
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
		hql.append("	and co.desligado = :desligado ");
		hql.append("	and hc1.status = :status ");

		if(estabelecimentoIds != null)
			hql.append(" and hc1.estabelecimento.id in (:estabelecimentoIds) ");

		if(areaOrganizacionalIds != null)
			hql.append(" and ao.id in (:areaOrganizacionalIds) ");

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
			if(StringUtils.isNotBlank(camposExtras.getTexto9()))
				hql.append(" and normalizar(upper(ce.texto9)) like normalizar(:texto9) ");
			if(StringUtils.isNotBlank(camposExtras.getTexto10()))
				hql.append(" and normalizar(upper(ce.texto10)) like normalizar(:texto10) ");

			montaQueryIntervalo(camposExtras.getData1(), camposExtras.getData1Fim(), "data1", hql);
			montaQueryIntervalo(camposExtras.getData2(), camposExtras.getData2Fim(), "data2", hql);
			montaQueryIntervalo(camposExtras.getData3(), camposExtras.getData3Fim(), "data3", hql);

			montaQueryIntervalo(camposExtras.getValor1(), camposExtras.getValor1Fim(), "valor1", hql);
			montaQueryIntervalo(camposExtras.getValor2(), camposExtras.getValor2Fim(), "valor2", hql);

			montaQueryIntervalo(camposExtras.getNumero1(), camposExtras.getNumero1Fim(), "numero1", hql);
		}

		// Ordenação
		hql.append(" order by es.nome, ao.nome, co.nome ");

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("hoje", new Date());
		query.setBoolean("desligado", false);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(estabelecimentoIds != null)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(areaOrganizacionalIds != null)
			query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, Hibernate.LONG);
		
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
			if(StringUtils.isNotBlank(camposExtras.getTexto9()))
				query.setString("texto9", "%" + camposExtras.getTexto9().toUpperCase() + "%");
			if(StringUtils.isNotBlank(camposExtras.getTexto10()))
				query.setString("texto10", "%" + camposExtras.getTexto10().toUpperCase() + "%");

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

	public Collection<Colaborador> findByAreaOrganizacional(Collection<Long> areaOrganizacionalIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.nome, co.id, e.id, e.nome) ");
		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("right join hc1.areaOrganizacional as ao ");
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
		hql.append("	and co.desligado = :desligado");

		hql.append(" and ao.id in (:areaOrganizacionalIds) ");
		hql.append(" order by e.nome, co.nomeComercial ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setBoolean("desligado", false);

		query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, Hibernate.LONG);

		return query.list();
	}

	public Colaborador findByIdProjectionEmpresa(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nitRepresentanteLegal"), "projectionNitRepresentanteLegal");
		p.add(Projections.property("e.representanteLegal"), "projectionRepresentanteLegal");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return (Colaborador) criteria.uniqueResult();
	}

	public Collection<Colaborador> findColaborador(Map parametros)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nomeComercial) ");
		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.areaOrganizacional as ao ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");
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

		Query query = filtroFindColaborador(hql, parametros);

		return query.list();
	}

	private Query filtroFindColaborador(StringBuilder hql, Map parametros)
	{
		Date dataIni = (Date) parametros.get("dataIni");
		Date dataFim = (Date) parametros.get("dataFim");

		Collection<Long> areasOrganizacionais = LongUtil.arrayStringToCollectionLong((String[]) parametros.get("areas"));
		Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong((String[]) parametros.get("estabelecimentos"));
		Collection<Long> cargos = LongUtil.arrayStringToCollectionLong((String[]) parametros.get("cargos"));

		Long empresaId = (Long) parametros.get("empresaId");

		int filtrarPor = (Integer) parametros.get("filtrarPor");
		int opcao = (Integer) parametros.get("opcao");

		if(opcao == NO_MES)
		{
			hql.append("	and	co.dataAdmissao <= :data ");
			hql.append("	and ( co.dataDesligamento is null ");
			hql.append("          or co.dataDesligamento > :data ) ");
		}

		if(opcao == ADMITIDOS)
		{
			hql.append("	and	co.dataAdmissao <= :dataFim ");
			hql.append("	and	co.dataAdmissao >= :dataIni ");
		}

		if(opcao == DEMITIDOS)
		{
			hql.append("	and	co.dataDesligamento <= :dataFim ");
			hql.append("	and	co.dataDesligamento >= :dataIni ");
		}

		if(empresaId != null)
			hql.append(" and co.empresa.id = :empresaId ");

		if(estabelecimentos != null && !estabelecimentos.isEmpty())
			hql.append(" and hc1.estabelecimento.id in (:estabelecimentoIds) ");

		if(filtrarPor == AREA_ORGANIZACIONAL && areasOrganizacionais.size() > 0)
			hql.append(" and ao.id in (:areaOrganizacionalIds) ");
		else if(filtrarPor == CARGO && cargos.size() > 0)
			hql.append(" and cg.id in (:cargoIds) ");

		// Ordenação
		hql.append(" order by es.nome, ao.nome, co.nome ");

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("hoje", new Date());

		if(opcao == NO_MES)
			query.setDate("data", dataFim);
		else
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}

		if(estabelecimentos != null && !estabelecimentos.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentos, Hibernate.LONG);
		if(empresaId != null)
			query.setLong("empresaId", empresaId);

		if(filtrarPor == AREA_ORGANIZACIONAL && areasOrganizacionais.size() > 0)
			query.setParameterList("areaOrganizacionalIds", areasOrganizacionais, Hibernate.LONG);
		else if(filtrarPor == CARGO && cargos.size() > 0)
			query.setParameterList("cargoIds", cargos, Hibernate.LONG);

		return query;
	}

	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim)
	{
		Query query = findColaboradores(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, MOTIVODEMISSAO);

		return query.list();
	}

	public List<Object[]> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim)
	{
		Query query = findColaboradores(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, MOTIVODEMISSAOQUANTIDADE);

		return query.list();
	}

	private Query findColaboradores(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, int origem)
	{

		StringBuilder hql = new StringBuilder();

		if(origem == MOTIVODEMISSAO)
			hql.append("select new Colaborador(co.id, co.nome, co.matricula, co.dataAdmissao, co.dataDesligamento, co.observacao, mo.motivo, cg.nome, fs.nome) ");
		else if(origem == MOTIVODEMISSAOQUANTIDADE)
			hql.append("select mo.motivo, count(mo.motivo) ");

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
		hql.append("			and hc2.data <= :hoje");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		hql.append("	and ( co.dataDesligamento between :dataIni and :dataFim )");

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append(" and es.id in (:estabelecimentoIds) ");

		if(areaIds != null && areaIds.length > 0)
			hql.append(" and ao.id in (:areaIds) ");

		if(cargoIds != null && cargoIds.length > 0)
			hql.append(" and cg.id in (:cargoIds) ");

		// Ordenação
		if(origem == MOTIVODEMISSAO)
			hql.append("order by mo.motivo, co.nomeComercial ");
		else if(origem == MOTIVODEMISSAOQUANTIDADE)
			hql.append(" group by mo.motivo order by mo.motivo  ");

		Query query = getSession().createQuery(hql.toString());

		// Parâmetros
		query.setDate("hoje", new Date());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if(cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		return query;
	}

	public boolean updateDataDesligamentoByCodigo(String codigoac, Empresa empresa, Date data)
	{
		String hql = "update Colaborador set dataDesligamento = :data, " +
					"desligado = :valor where codigoac = :codigo and empresa = :emp";

		Query query = getSession().createQuery(hql);
		query.setDate("data", data);
		query.setBoolean("valor", data != null ? true : false);
		query.setString("codigo", codigoac);
		query.setEntity("emp", empresa);
		int result = query.executeUpdate();

		return result == 1 ? true : false;
	}

	public void desligaColaborador(boolean desligado, Date dataDesligamento, String observacao, Long motivoDemissaoId, Long colaboradorId)
	{
		String hql = "update Colaborador set  desligado = :desligado, dataDesligamento = :data, observacao = :observacao, motivoDemissao.id = :motivoDemissaoId where id = :colaboradorId";

		Query query = getSession().createQuery(hql);

		query.setBoolean("desligado", desligado);
		query.setDate("data", dataDesligamento);
		query.setString("observacao", observacao);
		query.setLong("motivoDemissaoId", motivoDemissaoId);
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}

	public void religaColaborador(Long colaboradorId)
	{
		String hql = "update Colaborador set  desligado = :desligado, dataDesligamento = :data, observacao = :observacao, motivoDemissao.id = :motivoDemissaoId where id = :colaboradorId";

		Query query = getSession().createQuery(hql);

		query.setBoolean("desligado", false);
		query.setDate("data", null);
		query.setString("observacao", "");
		query.setParameter("motivoDemissaoId", null, Hibernate.LONG);
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}

	public Collection<Colaborador> findProjecaoSalarialByHistoricoColaborador(Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds,
			Collection<Long> cargoIds, String filtro, Long empresaId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.id, co.nome, es.nome, ao.id, ao.nome, fs.nome, ca.nome, hc.tipoSalario, hc.salario, ");
		hql.append("                       hc.quantidadeIndice, hc.status, hcih.valor, fsh.tipo, fsh.valor, fsh.quantidade, fsh.status, fshih.valor) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.indice as hci ");
		hql.append("left join hci.indiceHistoricos as hcih with hcih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = hci.id and ih2.data <= :data) ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ca.grupoOcupacional as go ");
		hql
				.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data and fsh3.status <> :statusCancelado) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");
		hql.append("	where ");
		hql.append("	   ( ");
		hql.append("	    	hc.data = (");
		hql.append("				select max(hc2.data) ");
		hql.append("				from HistoricoColaborador as hc2 ");
		hql.append("				where hc2.colaborador.id = co.id ");
		hql.append("					and hc2.data <= :data ");
		hql.append("					and hc2.status <> :statusCancelado");
		hql.append("			) ");
		hql.append("	 		or hc.data is null ");
		hql.append("	   ) ");
		hql.append("	and co.desligado = false ");
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

		hql.append("select new Colaborador(co.id, co.nome, es.nome, ao.id, ao.nome, fs.nome, ca.nome, rc.tipoSalarioProposto, rc.salarioProposto, ");
		hql.append("                       rc.quantidadeIndiceProposto, -1, ih.valor, fsh.tipo, fsh.valor, fsh.quantidade, fsh.status, fshih.valor) ");
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
				.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data and fsh3.status <> :statusCancelado) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");
		hql.append(" where ");
		hql.append("	trc.id = :tabelaReajusteColaboradorId ");
		hql.append("	and rc.colaborador.id = co.id ");
		hql.append("	and trc.aprovada = false ");
		hql.append("	and co.desligado = false ");
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
		query.setInteger("statusCancelado", StatusRetornoAC.CANCELADO);

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
		hql.append("			     and hc2.data <= :hoje " );
		
		if (statusRetornoAC !=null)
			hql.append("  				 and hc2.status = :statusHistColab ");
		
		hql.append("       ) or ");
		hql.append("          (select count(*) from HistoricoColaborador as hc3 where hc3.colaborador.id=co.id) = 1 ");
		hql.append("	   ) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("id", colaboradorId);
		query.setInteger("status", StatusRetornoAC.CANCELADO);
		
		if (statusRetornoAC !=null)
			query.setInteger("statusHistColab", StatusRetornoAC.CONFIRMADO);

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
		hql.append(" cursos = :cursos ,");
		hql.append(" observacao = :observacao");
		hql.append(" where id = :id");

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
		query.setString("cursos", colaborador.getCursos());
		query.setString("observacao", colaborador.getObservacao());
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

	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.codigoAC", empregadoCodigoAC));
		criteria.add(Expression.eq("e.codigoAC", empresaCodigoAC));

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

	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select c ");
		hql.append("from Colaborador as c ");
		hql.append("left join c.empresa as emp  ");
		hql.append("	where c.codigoAC = :codigoAC and emp.codigoAC = :empresaCodigoAC ");

		Query query = getSession().createQuery(hql.toString());
		query.setString("codigoAC", codigoAC);
		query.setString("empresaCodigoAC", empresaCodigoAC);

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
	public Collection<Colaborador> findAllSelect(Long... empresaIds)
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
		
		criteria.add(Expression.eq("c.desligado", false));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("nomeComercial"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return criteria.list();
	}

	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.id", colaboradorIds));
		criteria.add(Expression.eq("c.desligado", false));

		criteria.addOrder(Order.asc("nomeComercial"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Integer getCountAtivos(Date dataIni, Date dataFim, Long empresaId)
	{

		StringBuilder hql = new StringBuilder("select count(id) from Colaborador c ");
		hql.append("where c.empresa.id = :empresaId ");

		if(dataFim != null)
		{
			hql.append("and c.dataAdmissao < :dataFim ");
			hql.append("and (c.dataDesligamento = null or c.dataDesligamento > :dataFim) ");
		}

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);

		if(dataFim != null)
			query.setDate("dataFim", dataFim);

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

		hql.append("select new Colaborador(co.pessoal.dataNascimento, co.id, co.nome, co.nomeComercial, cg.nome, fs.nome, ao.nome, es.nome, ao.id) ");

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
		hql.append("	and (month(co.pessoal.dataNascimento) = :mes) ");
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
		query.setInteger("mes", mes);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findByCpf(String cpf, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		if(StringUtils.isNotBlank(cpf))
			criteria.add(Expression.eq("c.pessoal.cpf", cpf));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Long empresaId, Boolean somenteAtivos)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		if(colaborador != null && StringUtils.isNotBlank(colaborador.getNome()))
			criteria.add(Expression.like("c.nome", "%" + colaborador.getNome() + "%").ignoreCase());

		if(colaborador != null && StringUtils.isNotBlank(colaborador.getMatricula()))
			criteria.add(Expression.like("c.matricula", "%" + colaborador.getMatricula() + "%").ignoreCase());

		if(colaborador != null && colaborador.getPessoal() != null && StringUtils.isNotBlank(colaborador.getPessoal().getCpf()))
			criteria.add(Expression.like("c.pessoal.cpf", "%" + colaborador.getPessoal().getCpf() + "%").ignoreCase());

		if(somenteAtivos != null && somenteAtivos)
			criteria.add(Expression.eq("c.desligado", false));

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

	public Colaborador findByIdDadosBasicos(Long id)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.matricula, co.dataAdmissao, ao, ca) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("	where co.id = :id ");
		hql.append("	and hc.status = :status ");
		hql.append("	and hc.data = (");
		hql.append("		select max(hc2.data) ");
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("		) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("id", id);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

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
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :hoje  and hc2.status = :status ) order by co.nomeComercial");

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Colaborador findByIdHistoricoAtual(Long colaboradorId)
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
		hql.append("and co.id = :colaboradorId ");
		hql.append("and hc.status = :status ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("				from HistoricoColaborador as hc2 ");
		hql.append("				where hc2.colaborador.id = co.id ");
		hql.append("				and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (Colaborador) query.uniqueResult();
	}

	public Integer countSemMotivos(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim)
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

	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, cg.nome, fs.nome, ao.id, ao.nome, am.id, am.nome, co.empresa.id) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join ao.areaMae as am ");
		hql.append("left join fs.cargo as cg ");
		hql.append("where ");
		hql.append("		hc.status = :status ");
		hql.append("		and hc.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("		) ");
		hql.append("and co.desligado = false ");
		hql.append("and co.empresa.id = :empresaId ");
		hql.append("and :hoje - co.dataAdmissao = :dias");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresa.getId());
		query.setDate("hoje", new Date());
		query.setInteger("dias", dias);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<Colaborador> findAdmitidos(Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos)
	{
		StringBuilder hql = new StringBuilder();
		hql
				.append("select new Colaborador(co.id, co.nome, co.nomeComercial, co.matricula, co.dataAdmissao, co.desligado, cg.nome, fs.nome, es.id, es.nome, ao.id, ao.nome, am.id, am.nome, hc1.tipoSalario, hc1.salario, "
						+ "hc1.quantidadeIndice, hcih.valor, fsh.tipo, fsh.valor, fsh.quantidade, fshih.valor) ");

		hql.append("from HistoricoColaborador as hc1 ");
		hql.append("left join hc1.areaOrganizacional as ao ");
		hql.append("left join ao.areaMae as am ");
		hql.append("left join hc1.estabelecimento as es ");
		hql.append("left join hc1.colaborador as co ");
		hql.append("left join hc1.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cg ");

		hql.append("left join hc1.indice as hci ");
		hql.append("left join hci.indiceHistoricos as hcih with hcih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice.id = hci.id and ih2.data <= :data) ");
		hql.append("left join fs.faixaSalarialHistoricos as fsh with fsh.data = (select max(fsh3.data) from FaixaSalarialHistorico fsh3 where fsh3.faixaSalarial.id = fs.id and fsh3.data <= :data) ");
		hql.append("left join fsh.indice as fshi ");
		hql.append("left join fshi.indiceHistoricos as fshih with fshih.data = (select max(ih4.data) from IndiceHistorico ih4 where ih4.indice.id = fshi.id and ih4.data <= :data) ");

		hql.append("where ");
		hql.append("		hc1.status = :status ");
		hql.append("		and (hc1.data = (");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ");
		hql.append("		) ");
		hql.append("	 	or hc1.data is null ");
		hql.append("	) ");
		hql.append("and	co.dataAdmissao between :dataIni and :dataFim ");
		hql.append("and	es.id in (:estabelecimentosIds) ");
		if(areasIds != null && areasIds.length > 0)
			hql.append("and	ao.id in (:areasIds) ");
		if(exibirSomenteAtivos)
			hql.append("and	co.desligado = false ");

		hql.append("order by es.nome,ao.nome,co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", new Date());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);

		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findByNomeCpfMatriculaAndResponsavelArea(Colaborador colaborador, Long empresaId, Long colaboradorLogadoId)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");
		ProjectionList pSub = Projections.projectionList().create();

		pSub.add(Projections.max("hc2.data"));
		subQuery.setProjection(pSub);

		subQuery.add(Restrictions.sqlRestriction("this0__.colaborador_id=c1_.id"));
		subQuery.add(Expression.le("hc2.data", new Date()));
		subQuery.add(Expression.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

		Criteria criteria = getSession().createCriteria(HistoricoColaborador.class, "hc");
		criteria.createCriteria("hc.colaborador", "c");
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.matricula"), "matricula");
		p.add(Projections.property("c.dataAdmissao"), "dataAdmissao");

		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("hc.data", subQuery));
		subQuery.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.desligado", false));

		if(colaboradorLogadoId != null)
			criteria.add(Expression.eq("ao.responsavel.id", colaboradorLogadoId));

		if(colaborador != null)
		{
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

	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");

		if(isAvaliado)
			criteria.createCriteria("cq.colaborador", "colab");
		else
			criteria.createCriteria("cq.avaliador", "colab");

		criteria.createCriteria("cq.avaliacaoDesempenho", "avDesempenho");// não
		// pode
		// ser
		// LEFT_JOIN

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.distinct(Projections.property("colab.id")), "id");
		p.add(Projections.property("colab.nome"), "nome");
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

	public Collection<Colaborador> findByColaboradores(Long[] colaboradorIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new HistoricoColaborador(co.id, co.nome, co.dataAdmissao, c.id, c.nome, fs.id, fs.nome, e.id, e.nome) ");

		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as c ");

		hql.append("where hc.status = :status ");
		hql.append("and co.id in (:colaboradorIds) ");

		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("order by c.nome, fs.nome, co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados)
	{
		// subQuery
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");
		ProjectionList pSub = Projections.projectionList().create();

		pSub.add(Projections.max("hc2.data"));
		subQuery.setProjection(pSub);

		subQuery.add(Restrictions.sqlRestriction("this0__.colaborador_id=c1_.id"));
		subQuery.add(Expression.le("hc2.data", new Date()));
		subQuery.add(Expression.eq("hc2.status", StatusRetornoAC.CONFIRMADO));

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
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.property("fs.nome"), "faixaSalarialNomeProjection");
		p.add(Projections.property("ca.nome"), "cargoNomeProjection");

		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("hc.data", subQuery));
		subQuery.add(Expression.eq("hc.status", StatusRetornoAC.CONFIRMADO));
		criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.desligado) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.areaOrganizacional as ao where ");
		hql.append("co.empresa.id = :empresaId ");

		if(nome != null && !nome.trim().equals(""))
			hql.append("and normalizar(upper(co.nome)) like normalizar(:nome) ");
		if(matricula != null && !matricula.trim().equals(""))
			hql.append("and normalizar(upper(co.matricula)) like normalizar(:matricula) ");
		if(areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in (:areaIds) ");

		hql.append("and hc.status = :status ");
		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador as hc2 where hc2.colaborador.id = co.id ");
		hql.append("and hc2.data <= :dataAtual  and hc2.status = :status ) ");
		hql.append("order by co.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataAtual", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(nome != null && !nome.trim().equals(""))
			query.setString("nome", "%" + nome.toUpperCase() + "%");
		if(matricula != null && !matricula.trim().equals(""))
			query.setString("matricula", "%" + matricula.toUpperCase() + "%");
		if(areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Integer qtdColaboradoresByTurmas(Collection<Long> turmaIds)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.turma", "t", Criteria.LEFT_JOIN);
		criteria.createCriteria("ct.colaborador", "c", Criteria.LEFT_JOIN);

		criteria.setProjection(Projections.rowCount());

		if(turmaIds != null && turmaIds.size() >= 1)
			criteria.add(Expression.in("t.id", turmaIds));

		return (Integer) criteria.uniqueResult();
	}

	public Collection<Object> findComHistoricoFuturoSQL(Map parametros, Integer pagingSize, Integer page)
	{
		String nomeBusca = (String) parametros.get("nomeBusca");
		String matriculaBusca = (String) parametros.get("matriculaBusca");
		Long empresaId = (Long) parametros.get("empresaId");
		Long areaBuscaId = (Long) parametros.get("areaId");
		Long estabelecimentoId = (Long) parametros.get("estabelecimentoId");
		Long cargoId = (Long) parametros.get("cargoId");
		Integer statusRetornoAC = (Integer) parametros.get("statusRetornoAC");
		String situacao = (String) parametros.get("situacao");

		String cpfBusca = (String) parametros.get("cpfBusca");
		if(cpfBusca != null && cpfBusca.equals("   .   .   -  "))
			cpfBusca = null;

		StringBuilder sql = new StringBuilder();

		sql.append("select colab_.id as colabId, colab_.nome as name, colab_.nomeComercial, colab_.matricula, colab_.desligado, colab_.dataAdmissao, colab_.cpf, colab_.usuario_id, colab_.dataDesligamento, md.motivo, colab_.respondeuEntrevista, colab_.candidato_id as candId ");
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
		sql.append("	where hc2.status = :status  ");
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
		sql.append("where ");
		sql.append("colab_.empresa_id = :empresaId ");
		sql.append("and hc.status = :status ");
		
		// Area
		if(areaBuscaId != null)
			sql.append("and hc.areaOrganizacional_id = :areaBuscaId ");
		// Cargo
		if(cargoId != null)
			sql.append("and fs.cargo_id = :cargoId ");
		// Area
		if(estabelecimentoId != null)
			sql.append("and hc.estabelecimento_id = :estabelecimentoId ");

		// Nome
		if(nomeBusca != null && !nomeBusca.trim().equals(""))
			sql.append("and normalizar(upper(colab_.nome)) like normalizar(:nomeBusca) ");
		// Matricula
		if(matriculaBusca != null && !matriculaBusca.trim().equals(""))
			sql.append("and upper(colab_.matricula) like :matriculaBusca ");
		// CPF
		if(cpfBusca != null && !cpfBusca.trim().equals(""))
			sql.append("and colab_.cpf like :cpfBusca ");

		if(situacao != null && !situacao.trim().equals("") && !situacao.trim().equals("T"))
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
		
		if(statusRetornoAC!=null)
			query.setInteger("status", statusRetornoAC);
		else
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
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

		return query.list();
	}

	public Collection<Colaborador> findColaboradoresEleicao(Long empresaId, Long estabelecimentosIds, Date data) {
	
		return null;
	}

	public Collection<Colaborador> findAdmitidosNoPeriodo(Date dataReferencia, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck) 
	 {
	  StringBuilder hql = new StringBuilder();
	  hql.append("select new Colaborador(co.id, co.nome, co.dataAdmissao, respArea.nome, cq.respondidaEm, av.id) ");
	  hql.append("from HistoricoColaborador as hc ");
	  hql.append("left join hc.colaborador as co ");
	  hql.append("left join co.colaboradorQuestionarios as cq ");
	  hql.append("left join cq.avaliacaoDesempenho as av ");
	  hql.append("left join hc.areaOrganizacional as ao ");
	  hql.append("left join ao.responsavel as respArea ");
	  hql.append("where ");
	  hql.append("  hc.data = (");
	  hql.append("   select max(hc2.data) ");
	  hql.append("   from HistoricoColaborador as hc2 ");
	  hql.append("   where hc2.colaborador.id = co.id ");
	  hql.append("   and hc2.data <= :dataReferencia and hc2.status = :status ");
	  hql.append("  ) ");
	  hql.append("and co.desligado = false ");
	  hql.append("and co.empresa.id = :empresaId ");
	  hql.append("and co.dataAdmissao <= :dataReferencia ");

	  if(areasCheck != null && areasCheck.length > 0) 
	   hql.append("and ao.id in (:areasCheck) ");

	  if(estabelecimentoCheck != null && estabelecimentoCheck.length > 0) 
	   hql.append("and hc.estabelecimento.id in (:estabelecimentoCheck) ");

	  Query query = getSession().createQuery(hql.toString());
	  query.setLong("empresaId", empresa.getId());
	  query.setDate("dataReferencia", dataReferencia);
	  query.setInteger("status", StatusRetornoAC.CONFIRMADO);

	  if(areasCheck != null && areasCheck.length > 0)
	   query.setParameterList("areasCheck", StringUtil.stringToLong(areasCheck));

	  if(estabelecimentoCheck != null && estabelecimentoCheck.length > 0)
	   query.setParameterList("estabelecimentoCheck", StringUtil.stringToLong(estabelecimentoCheck));   

	  return query.list();
	 }

	public Collection<Colaborador> findColabPeriodoExperiencia(Long empresaId, Date periodoIni, Date periodoFim, Long modeloAvaliacaoId, Long[] areasCheck, Long[] estabelecimentosCheck) 
	{
		StringBuilder hql = new StringBuilder();
		  hql.append("select new Colaborador(co.nome, co.nomeComercial, av.titulo, cq.respondidaEm, cq.performance, ad.titulo) ");
		  hql.append("from HistoricoColaborador as hc ");
		  hql.append("left join hc.colaborador as co ");
		  hql.append("left join co.colaboradorQuestionarios as cq ");
		  hql.append("left join cq.avaliacaoDesempenho as ad ");
		  hql.append("left join cq.avaliacao as av ");
		  hql.append("where ");
		  hql.append("  hc.data = (");
		  hql.append("   select max(hc2.data) ");
		  hql.append("   from HistoricoColaborador as hc2 ");
		  hql.append("   where hc2.colaborador.id = co.id ");
		  hql.append("   and hc2.data <= :dataAtual  ");
		  hql.append("  ) ");
		  hql.append("and co.desligado = false ");
		  hql.append("and co.empresa.id = :empresaId ");
		  hql.append("and cq.respondidaEm between :periodoIni and :periodoFim ");
		  hql.append("and cq.avaliacao.id = :modeloAvaliacaoId ");
		  hql.append("and cq.respondida = true ");
		  		  
		  if(areasCheck != null && areasCheck.length > 0) 
			  hql.append("and hc.areaOrganizacional.id in (:areasCheck) ");

		  if(estabelecimentosCheck != null && estabelecimentosCheck.length > 0) 
			  hql.append("and hc.estabelecimento.id in (:estabelecimentoCheck) ");

		  hql.append("order by cq.performance desc, co.nome asc ");
		  
		  Query query = getSession().createQuery(hql.toString());
		  query.setLong("empresaId", empresaId);
		  query.setLong("modeloAvaliacaoId", modeloAvaliacaoId);
		  query.setDate("periodoIni", periodoIni);
		  query.setDate("periodoFim", periodoFim);
		  query.setDate("dataAtual", new Date());

		  if(areasCheck != null && areasCheck.length > 0)
			  query.setParameterList("areasCheck", areasCheck);

		  if(estabelecimentosCheck != null && estabelecimentosCheck.length > 0)
			  query.setParameterList("estabelecimentoCheck", estabelecimentosCheck);   

		  return query.list();
	}

}