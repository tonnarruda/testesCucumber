	package com.fortes.rh.dao.hibernate.captacao;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.model.type.File;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.captacao.dto.CandidatoDTO;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class CandidatoDaoHibernate extends GenericDaoHibernate<Candidato> implements CandidatoDao
{
    public Collection<Candidato> findByCPF(String cpf, Long empresaId, 	Long candidatoId, Boolean contratado) 
	{
    	Criteria criteria = getSession().createCriteria(Candidato.class, "c");
        criteria.createCriteria("c.empresa", "e");
        
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.id"), "id");
        p.add(Projections.property("c.nome"), "nome");
        p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
        p.add(Projections.property("c.contato.email"),"contatoEmail");
        p.add(Projections.property("c.dataCadastro"),"dataCadastro");
        p.add(Projections.property("c.senha"),"senha");
        p.add(Projections.property("e.nome"),"empresaNome");
        p.add(Projections.property("c.origem"),"origem");
        
        criteria.setProjection(p);
		criteria.add(Expression.eq("c.pessoal.cpf", cpf));

		if (candidatoId != null)
			criteria.add(Expression.ne("c.id", candidatoId));
		
		if (empresaId != null )
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if (contratado != null )
			criteria.add(Expression.eq("c.contratado", contratado));
		
		criteria.addOrder(Order.desc("c.id"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		
		return criteria.list();
	}

	public Candidato findByCandidatoId(Long id)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.blackList"), "blackList");
		p.add(Projections.property("c.disponivel"), "disponivel");
		p.add(Projections.property("c.contratado"), "contratado");
		p.add(Projections.property("c.origem"), "origem");
		p.add(Projections.property("c.dataCadastro"), "dataCadastro");
		p.add(Projections.property("c.dataAtualizacao"), "dataAtualizacao");

		p.add(Projections.property("c.pessoal.rg"),"pessoalRg");
		p.add(Projections.property("c.pessoal.dataNascimento"),"pessoalDataNascimento");

		criteria.setProjection(p);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return (Candidato) criteria.add(Expression.eq("c.id", id)).uniqueResult();
	}

	public Candidato findByIdProjection(Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria = criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		projectionFindById(p);

		p.add(Projections.property("c.dataCadastro"),"dataCadastro");
		p.add(Projections.property("c.blackList"),"blackList");
		p.add(Projections.property("c.observacaoBlackList"),"observacaoBlackList");
		p.add(Projections.property("c.contratado"),"contratado");
		p.add(Projections.property("c.ocrTexto"),"ocrTexto");
		p.add(Projections.property("c.disponivel"),"disponivel");
		p.add(Projections.property("c.origem"),"origem");
		p.add(Projections.property("c.senha"),"senha");
		p.add(Projections.property("c.comoFicouSabendoVagaQual"), "comoFicouSabendoVagaQual");
		p.add(Projections.property("c.examePalografico"), "examePalografico");
		p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
		p.add(Projections.property("c.candidatoCurriculos"),"candidatoCurriculos");
		p.add(Projections.property("e.id"), "empresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", candidatoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));

		return (Candidato) criteria.uniqueResult();
	}
	
	public Collection<Candidato> find(int page, int pagingSize, CandidatoDTO candidatoDTO, Long... empresasIds)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select can.id, can.nome, can.disponivel, can.contratado, can.dataCadastro, can.dataAtualizacao, can.indicadoPor, can.cpf, emp.id, exists(select cpf from colaborador where cpf=can.cpf and can.cpf is not null and can.cpf <> '') as jaFoiColaborador "); 
		Query query = montaListaCandidato(page, pagingSize, sql, candidatoDTO, empresasIds);
		
		List resultado = query.list();
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		Candidato cand;
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			cand = new Candidato();
			
			cand.setId(((BigInteger)res[0]).longValue());
			cand.setNome((String)res[1]);
			
			if((Boolean) res[2] != null)
				cand.setDisponivel((Boolean) res[2]);
			
			if((Boolean) res[3] != null)
				cand.setContratado((Boolean) res[3]);
			
			cand.setDataCadastro((Date) res[4]);
			cand.setDataAtualizacao((Date) res[5]);
			cand.setPessoalIndicadoPor((String) res[6]);
			cand.setPessoalCpf((String) res[7]);
			
			if((BigInteger)res[8] != null)
				cand.setEmpresaId(((BigInteger)res[8]).longValue());
			
			cand.setJaFoiColaborador((Boolean) res[9]);

			candidatos.add(cand);
		}
		
		return candidatos;
	}

	public Integer getCount(CandidatoDTO candidatoDTO, Long... empresasIds)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as total "); 
		
		Query query = montaListaCandidato(0, 0, sql, candidatoDTO, empresasIds);
		
		return (Integer) query.uniqueResult();
	}

	private Query montaListaCandidato(int page, int pagingSize, StringBuilder sql, CandidatoDTO candidatoDTO, Long... empresasIds) 
	{
		sql.append("from Candidato can ");
		sql.append("left join Empresa emp on can.empresa_id = emp.id ");
		sql.append("where true = true ");
				
		if(empresasIds != null && empresasIds.length > 0)
			sql.append("and can.empresa_id in (:empresasIds) ");
		
		if (candidatoDTO.isExibeExterno())
			sql.append("and can.origem = :origem ");
				
		if (!candidatoDTO.isExibeContratados())
			sql.append("and can.contratado = false ");

		if(StringUtils.isNotBlank(candidatoDTO.getNomeBusca()))
			sql.append("and normalizar(upper(can.nome)) like normalizar(:nomeBusca) ");
		
		if(StringUtils.isNotBlank(candidatoDTO.getCpfBusca()))
			sql.append("and can.cpf like :cpfBusca ");

		if(StringUtils.isNotBlank(candidatoDTO.getDddFoneFixo()))
			sql.append("and can.ddd = :ddd ");

		if(StringUtils.isNotBlank(candidatoDTO.getFoneFixo()))
			sql.append("and can.foneFixo like :foneFixo ");

		if(StringUtils.isNotBlank(candidatoDTO.getDddCelular()))
			sql.append("and can.dddCelular = :dddCelular ");
		
		if(StringUtils.isNotBlank(candidatoDTO.getFoneCelular()))
			sql.append("and can.foneCelular like :foneCelular ");
		
		if(candidatoDTO.getVisualizar() == 'D')
			sql.append("and can.disponivel = true ");
		else if(candidatoDTO.getVisualizar() == 'I')
			sql.append("and can.disponivel = false ");

		if(candidatoDTO.getDataIni() != null)
			sql.append("and can.dataAtualizacao >= :dataIni ");
	
		if(candidatoDTO.getDataFim() != null)
			sql.append("and can.dataAtualizacao <= :dataFim ");
		
		if(StringUtils.isNotBlank(candidatoDTO.getIndicadoPor()))
			sql.append("and normalizar(upper(can.indicadoPor)) like normalizar(:indicadoPor) ");

		if(StringUtils.isNotBlank(candidatoDTO.getObservacaoRH()))
			sql.append("and normalizar(upper(can.observacaoRH)) like normalizar(:observacaoRH) ");
		
		if (pagingSize != 0)
			sql.append(" order by can.nome ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
			
		if (candidatoDTO.isExibeExterno())
			query.setCharacter("origem", OrigemCandidato.EXTERNO);
		
		if(StringUtils.isNotBlank(candidatoDTO.getNomeBusca()))
			query.setString("nomeBusca", "%" + candidatoDTO.getNomeBusca().toUpperCase() + "%");
		
		if(StringUtils.isNotBlank(candidatoDTO.getCpfBusca()))
			query.setString("cpfBusca", "%" + candidatoDTO.getCpfBusca() + "%");

		if(StringUtils.isNotBlank(candidatoDTO.getDddFoneFixo()))
			query.setString("ddd", candidatoDTO.getDddFoneFixo());
		
		if(StringUtils.isNotBlank(candidatoDTO.getFoneFixo()))
			query.setString("foneFixo", "%" + candidatoDTO.getFoneFixo() + "%");
		
		if(StringUtils.isNotBlank(candidatoDTO.getDddCelular()))
			query.setString("dddCelular", candidatoDTO.getDddCelular());
		
		if(StringUtils.isNotBlank(candidatoDTO.getFoneCelular()))
			query.setString("foneCelular", "%" + candidatoDTO.getFoneCelular() + "%");
		
		if(candidatoDTO.getDataIni() != null)
			query.setDate("dataIni", candidatoDTO.getDataIni());
		
		if(candidatoDTO.getDataFim() != null)
			query.setDate("dataFim", candidatoDTO.getDataFim());
		
		if(StringUtils.isNotBlank(candidatoDTO.getIndicadoPor()))
			query.setString("indicadoPor", "%" + candidatoDTO.getIndicadoPor().toUpperCase() + "%");
		
		if(StringUtils.isNotBlank(candidatoDTO.getObservacaoRH()))
			query.setString("observacaoRH", "%" + candidatoDTO.getObservacaoRH().toUpperCase() + "%");
		
		if(empresasIds != null && empresasIds.length > 0)
			query.setParameterList("empresasIds", empresasIds);
		
		if (pagingSize != 0)
		{
			query.setFirstResult((page - 1) * pagingSize);
			query.setMaxResults(pagingSize);
		}
		else
			query.addScalar("total", Hibernate.INTEGER);
		
		return query;
	}

	public Collection<Candidato> findBusca(Map parametros, Long[] empresaId, Collection<Long> idsCandidatos, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar) throws Exception
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.experiencias", "ex", Criteria.LEFT_JOIN);
		
		if (isNotBlank((String)parametros.get("palavrasChaveOutrosCampos")))
			criteria.createCriteria("c.formacao", "f", Criteria.LEFT_JOIN);
		
		if (somenteSemSolicitacao)
			criteria.createCriteria("c.candidatoSolicitacaos", "cs", Criteria.LEFT_JOIN).add(Expression.isNull("cs.id"));
		
		ProjectionList p = Projections.projectionList().create();
		criteria = montaProjectionGroup(criteria, p);

		montaCriteriaFiltros(parametros, empresaId, criteria);

		if (idsCandidatos.size() > 0)
			criteria.add(Expression.not((Expression.in("c.id", idsCandidatos))));
		
		if(ordenar!=null && ordenar.equals("dataAtualizacao"))
			criteria.addOrder(Order.desc("c." + ordenar));
		else
			criteria.addOrder(Order.asc("c.nome"));
		
		if(qtdRegistros!=null)
			criteria.setMaxResults(qtdRegistros);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));

		return criteria.list();
	}

	private void montaCriteriaFiltros(Map parametros, Long[] empresaIds, Criteria criteria)
	{
	//	lista candidatos (disponivel = true , contratado = false)
		criteria.add(Expression.eq("c.disponivel", true));
		criteria.add(Expression.eq("c.contratado", false));
		criteria.add(Expression.eq("c.blackList", false));
		
		if (empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if(parametros.get("candidatosComExperiencia") != null && ((Long[])parametros.get("candidatosComExperiencia")).length > 0)
			criteria.add(Expression.in("c.id", (Long[])parametros.get("candidatosComExperiencia")));

		if(parametros.get("bairros") != null && ((String[])parametros.get("bairros")).length > 0)
			criteria.add(Expression.in("c.endereco.bairro", (String[])parametros.get("bairros")));

		if(parametros.get("areasIds")  != null && ((Long[])parametros.get("areasIds")).length > 0)
			criteria.createCriteria("c.areasInteresse", "a", Criteria.LEFT_JOIN).add(Expression.in("a.id", (Long[])parametros.get("areasIds")));
		
		if(parametros.get("areasFormacaoIds")  != null && ((Long[])parametros.get("areasFormacaoIds")).length > 0){
			if (!isNotBlank((String)parametros.get("palavrasChaveOutrosCampos")))
				criteria.createCriteria("c.formacao", "f", Criteria.LEFT_JOIN);
			
			criteria.createCriteria("f.areaFormacao", "aFormacao", Criteria.LEFT_JOIN).add(Expression.in("aFormacao.id", (Long[])parametros.get("areasFormacaoIds")));
		}

		if(parametros.get("cargosIds")  != null && ((Long[])parametros.get("cargosIds")).length > 0)
			criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(Expression.in("cg.id", (Long[])parametros.get("cargosIds")));

		if(parametros.get("cargosNomeMercado")  != null && ((String[])parametros.get("cargosNomeMercado")).length > 0)
			criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(
					Expression.sqlRestriction("cg5_.nomemercado || (CASE cg5_.ativo WHEN true THEN ' (Ativo)' ELSE ' (Inativo)' END) in ("  +  "'" + StringUtils.join((String[]) parametros.get("cargosNomeMercado"), "','") +  "'" + ")"));

		if(parametros.get("conhecimentosIds")  != null && ((Long[])parametros.get("conhecimentosIds")).length > 0)
			criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.id", (Long[])parametros.get("conhecimentosIds")));

		if(parametros.get("conhecimentosNomes")  != null && ((Long[])parametros.get("conhecimentosNomes")).length > 0)
			criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.nome", (String[]) parametros.get("conhecimentosNomes")));
		
		// Idioma
		if(parametros.get("idioma") != null)
		{
			Idioma idioma = new Idioma();
			idioma.setId((Long)parametros.get("idioma"));

			if(parametros.get("nivel") != null && !parametros.get("nivel").equals(""))
				criteria.createCriteria("c.candidatoIdiomas", "ci", Criteria.LEFT_JOIN).add(Expression.eq("ci.idioma", idioma)).add(Expression.eq("ci.nivel", parametros.get("nivel")));
			else
				criteria.createCriteria("c.candidatoIdiomas", "ci", Criteria.LEFT_JOIN).add(Expression.eq("ci.idioma", idioma));
		}

		//indicadoPor
		if(isNotBlank((String)parametros.get("indicadoPor")))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.indicadoPor) ilike  normalizar(?)", "%" + parametros.get("indicadoPor") + "%", Hibernate.STRING));

		//nomeBUsca
		if(isNotBlank((String)parametros.get("nomeBusca")))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + parametros.get("nomeBusca") + "%", Hibernate.STRING));

		//CPF
		if(isNotBlank((String)parametros.get("cpfBusca")))
			criteria.add(Expression.eq("c.pessoal.cpf",parametros.get("cpfBusca")));

		//dataINi
		if(parametros.get("dataCadIni") != null)
			criteria.add(Expression.ge("c.dataAtualizacao",parametros.get("dataCadIni")));

		if(parametros.get("dataCadFim") != null)
			criteria.add(Expression.le("c.dataAtualizacao",parametros.get("dataCadFim")));

		// Sexo
		String sexo = (String) parametros.get("sexo");

		if( isNotBlank(sexo) && !sexo.equals(Sexo.INDIFERENTE))
			criteria.add(Expression.eq("c.pessoal.sexo", sexo));

		// Deficiencia
		String deficiencia = ((String)parametros.get("deficiencia"));

		if( isNotBlank(deficiencia) && !deficiencia.equals(Deficiencia.SEM_DEFICIENCIA) && !deficiencia.equals("100"))
			criteria.add(Expression.eq("c.pessoal.deficiencia", deficiencia));
		if( isNotBlank(deficiencia) && deficiencia.equals("100"))//PARA BUSCA DE QUALQUER DEFICIENCIA
			criteria.add(Expression.ne("c.pessoal.deficiencia", Deficiencia.SEM_DEFICIENCIA));

		// Faixa Etaria
		String idadeMin = (String)parametros.get("idadeMin");
		String idadeMax = (String)parametros.get("idadeMax");

		if( isNotBlank(idadeMin) && !idadeMin.equals("0"))
		{
			int idade = Integer.parseInt((String) parametros.get("idadeMin"));
			Date hoje = new Date();
			Date data = new Date(hoje.getYear() - idade, hoje.getMonth(), hoje.getDay());
			criteria.add(Expression.le("c.pessoal.dataNascimento", data));
		}

		if( isNotBlank(idadeMax) && !idadeMax.equals("0"))
		{
			int idade = Integer.parseInt((String) parametros.get("idadeMax"));
			Date hoje = new Date();
			Date data = new Date(hoje.getYear() - idade, hoje.getMonth(), hoje.getDay());
			criteria.add(Expression.ge("c.pessoal.dataNascimento", data));
		}

		// Cidade
		if( parametros.get("cidadesIds") != null && ((Long[])parametros.get("cidadesIds")).length > 0)
			criteria.add(Expression.in("cd.id", (Long[]) parametros.get("cidadesIds")));

		// UF
		if( parametros.get("uf") != null)
			criteria.add(Expression.eq("c.endereco.uf.id", (Long) parametros.get("uf")));

		// Escolaridade
		if( isNotBlank((String)parametros.get("escolaridade")))
			criteria.add(Expression.ge("c.pessoal.escolaridade", parametros.get("escolaridade")));

		// Possui Veiculo
		if( parametros.get("veiculo") != null && (Character) parametros.get("veiculo") != 'I')
		{
			boolean veiculo = false;
			if (parametros.get("veiculo").equals('S'))
				veiculo = true;
			criteria.add(Expression.eq("c.socioEconomica.possuiVeiculo", veiculo));
		}

		if (isNotBlank((String)parametros.get("palavrasChaveOutrosCampos"))) {
			String tipo = (String) parametros.get("formas");
			
			Junction juncaoOr = Expression.disjunction();
			
			juncaoOr.add(montaTipoDeRestricaoDaPalavraChave(tipo, "f2_.curso", StringUtils.trimToEmpty((String) parametros.get("palavrasChaveOutrosCampos"))));
			juncaoOr.add(montaTipoDeRestricaoDaPalavraChave(tipo, "this_.cursos", StringUtils.trimToEmpty((String) parametros.get("palavrasChaveOutrosCampos"))));
			juncaoOr.add(montaTipoDeRestricaoDaPalavraChave(tipo, "ex1_.nomeMercado", StringUtils.trimToEmpty((String) parametros.get("palavrasChaveOutrosCampos"))));
			juncaoOr.add(montaTipoDeRestricaoDaPalavraChave(tipo, "this_.observacao", StringUtils.trimToEmpty((String) parametros.get("palavrasChaveOutrosCampos"))));
			
			criteria.add(juncaoOr);
		}
		
		// where titulo ocrtexto
		if (isNotBlank((String)parametros.get("palavrasChaveCurriculoEscaneado"))) {
			String tipo = (String) parametros.get("formas");

			criteria.add(montaTipoDeRestricaoDaPalavraChave(tipo, "this_.ocrTexto", (String) parametros.get("palavrasChaveCurriculoEscaneado")));
		}

	}

	private Junction montaTipoDeRestricaoDaPalavraChave(String tipo, String campoComparado, String palavraChave)
	{
		String TODAS_PALAVRAS = "1";
		String QUALQUER_PALAVRA = "2";
		String FRASE_EXATA = "3";
		
		
		if (tipo.equals(QUALQUER_PALAVRA))
		{
			Junction juncaoOr = Expression.disjunction();
			String[] palavrasExpressao = palavraChave.split(" ");

			for (int i = 0; i < palavrasExpressao.length; i++)
			{
				juncaoOr.add(Restrictions.sqlRestriction("normalizar("+campoComparado+") ilike  normalizar(?)", "%" + palavrasExpressao[i] + "%", Hibernate.STRING) );
			}
			
			return juncaoOr;

		} else { 
			Junction juncaoAnd = Expression.conjunction();

			if (tipo.equals(TODAS_PALAVRAS))
			{
				String[] palavrasExpressao = palavraChave.split(" ");

				for (int i = 0; i < palavrasExpressao.length; i++)
				{
					juncaoAnd.add(Restrictions.sqlRestriction("normalizar("+campoComparado+") ilike  normalizar(?)", "%" + palavrasExpressao[i] + "%", Hibernate.STRING) );
				}
			} 
			else if (tipo.equals(FRASE_EXATA))
			{
				juncaoAnd.add(Restrictions.sqlRestriction("normalizar("+campoComparado+") ilike  normalizar(?)", "%" + palavraChave.trim() + "%", Hibernate.STRING) );
			}
			
			return juncaoAnd;
		}
	}

	public Collection<Candidato> findCandidatosById(Long[] ids)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria = criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		projectionFindById(p);

		criteria.setProjection(p);

		criteria.add(Expression.in("c.id", ids));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));

		return criteria.list();
	}

	private void projectionFindById(ProjectionList p)
	{
		p.add(Projections.property("c.id"),"id");
		p.add(Projections.property("c.nome"),"nome");
		p.add(Projections.property("c.colocacao"),"colocacao");
		p.add(Projections.property("c.pretencaoSalarial"),"pretencaoSalarial");
		p.add(Projections.property("c.observacao"),"observacao");
		p.add(Projections.property("c.observacaoRH"),"observacaoRH");
		p.add(Projections.property("c.cursos"),"cursos");
		p.add(Projections.property("c.dataAtualizacao"),"dataAtualizacao");
		p.add(Projections.property("c.dataCadastro"), "dataCadastro");
		p.add(Projections.property("c.ocrTexto"), "ocrTexto");
		p.add(Projections.property("c.comoFicouSabendoVaga"), "comoFicouSabendoVaga");
		p.add(Projections.property("c.camposExtras.id"), "camposExtrasId");

		p.add(Projections.property("c.endereco.logradouro"),"enderecoLogradouro");
		p.add(Projections.property("c.endereco.numero"),"enderecoNumero");
		p.add(Projections.property("c.endereco.complemento"),"enderecoComplemento");
		p.add(Projections.property("c.endereco.bairro"),"enderecoBairro");
		p.add(Projections.property("uf.id"),"enderecoUfId");
		p.add(Projections.property("uf.sigla"),"sigla");
		p.add(Projections.property("cd.id"),"enderecoCidadeId");
		p.add(Projections.property("cd.nome"),"enderecoCidadeNome");
		p.add(Projections.property("c.endereco.cep"),"enderecoCep");

		p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
		p.add(Projections.property("c.pessoal.rg"),"pessoalRg");
		p.add(Projections.property("c.pessoal.rgOrgaoEmissor"),"pessoalRgOrgaoEmissor");
		p.add(Projections.property("c.pessoal.rgUf.id"),"pessoalRgUfId");
		p.add(Projections.property("c.pessoal.rgDataExpedicao"),"pessoalRgDataExpedicao");
		p.add(Projections.property("c.pessoal.naturalidade"),"pessoalNaturalidade");
		p.add(Projections.property("c.pessoal.pai"),"pessoalPai");
		p.add(Projections.property("c.pessoal.mae"),"pessoalMae");
		p.add(Projections.property("c.pessoal.conjuge"),"pessoalConjuge");
		p.add(Projections.property("c.pessoal.profissaoPai"),"pessoalProfissaoPai");
		p.add(Projections.property("c.pessoal.profissaoMae"),"pessoalProfissaoMae");
		p.add(Projections.property("c.pessoal.profissaoConjuge"),"pessoalProfissaoConjuge");
		p.add(Projections.property("c.pessoal.conjugeTrabalha"),"pessoalConjugeTrabalha");
		p.add(Projections.property("c.pessoal.parentesAmigos"),"pessoalParentesAmigos");
		p.add(Projections.property("c.pessoal.indicadoPor"),"pessoalIndicadoPor");
		p.add(Projections.property("c.pessoal.qtdFilhos"),"pessoalQtdFilhos");
		p.add(Projections.property("c.pessoal.sexo"),"pessoalSexo");
		p.add(Projections.property("c.pessoal.deficiencia"),"pessoalDeficiencia");
		p.add(Projections.property("c.pessoal.dataNascimento"),"pessoalDataNascimento");
		p.add(Projections.property("c.pessoal.escolaridade"),"pessoalEscolaridade");
		p.add(Projections.property("c.pessoal.estadoCivil"),"pessoalEstadoCivil");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitNumero"),"pessoalTitEleitNumero");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitZona"),"pessoalTitEleitZona");
		p.add(Projections.property("c.pessoal.tituloEleitoral.titEleitSecao"),"pessoalTitEleitSecao");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilNumero"),"pessoalCertMilNumero");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilTipo"),"pessoalCertMilTipo");
		p.add(Projections.property("c.pessoal.certificadoMilitar.certMilSerie"),"pessoalCertMilSerie");
		p.add(Projections.property("c.pessoal.ctps.ctpsNumero"),"pessoalCtpsNumero");
		p.add(Projections.property("c.pessoal.ctps.ctpsSerie"),"pessoalCtpsSerie");
		p.add(Projections.property("c.pessoal.ctps.ctpsDv"),"pessoalCtpsDv");
		p.add(Projections.property("c.pessoal.ctps.ctpsUf.id"),"pessoalCtpsUfId");
		p.add(Projections.property("c.pessoal.ctps.ctpsDataExpedicao"),"pessoalCtpsDataExpedicao");
		p.add(Projections.property("c.pessoal.pis"),"pessoalPis");

		p.add(Projections.property("c.contato.ddd"),"contatoDdd");
		p.add(Projections.property("c.contato.foneFixo"),"contatoFoneFixo");
		p.add(Projections.property("c.contato.dddCelular"),"contatoDddCelular");
		p.add(Projections.property("c.contato.foneCelular"),"contatoFoneCelular");
		p.add(Projections.property("c.contato.email"),"contatoEmail");
		p.add(Projections.property("c.contato.nomeContato"),"nomeContato");

		p.add(Projections.property("c.socioEconomica.pagaPensao"),"socioEconomicaPagaPensao");
		p.add(Projections.property("c.socioEconomica.quantidade"),"socioEconomicaQuantidade");
		p.add(Projections.property("c.socioEconomica.valor"),"socioEconomicaValor");
		p.add(Projections.property("c.socioEconomica.possuiVeiculo"),"socioEconomicaPossuiVeiculo");

		p.add(Projections.property("c.habilitacao.numeroHab"),"habilitacaoNumeroHab");
		p.add(Projections.property("c.habilitacao.registro"),"habilitacaoRegistro");
		p.add(Projections.property("c.habilitacao.emissao"),"habilitacaoEmissao");
		p.add(Projections.property("c.habilitacao.vencimento"),"habilitacaoVencimento");
		p.add(Projections.property("c.habilitacao.categoria"),"habilitacaoCategoria");
		p.add(Projections.property("c.habilitacao.ufHab.id"),"habilitacaoUFId");
	}

	public List getConhecimentosByCandidatoId(Long id)
	{
		Query query = getSession().createQuery("select co.nome from Candidato c join c.conhecimentos co where c.id = " + id);

		return query.list();
	}

	public Collection<Candidato> getCandidatosByCpf(String[] cpfs, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
		p.add(Projections.property("c.dataAtualizacao"), "dataAtualizacao");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.pessoal.cpf", cpfs));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return criteria.list();
	}

	public Candidato findCandidatoCpf(String cpf, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("c.contato.email"), "contatoEmail");
		p.add(Projections.property("c.senha"), "senha");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.pessoal.cpf", cpf));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));

		return (Candidato) criteria.uniqueResult();
	}

	public void updateSetContratado(Long candidatoId, Long empresaId)
	{
		String queryHQL = "update Candidato c set c.contratado = true, c.disponivel = false, c.empresa.id = :empresaId where c.id = :candidatoId";
		Query query = getSession().createQuery(queryHQL);
		
		query.setLong("candidatoId",candidatoId);
		query.setLong("empresaId",empresaId);
		
		query.executeUpdate();
	}

	public void updateBlackList(String observacao, boolean blackList, Long... candidatoIds)
	{
		String hql = "update Candidato set blackList = :blackList, observacaoBlackList = :observacaoBlackList, disponivel = :disponivel where id in (:ids)";

		Query query = getSession().createQuery(hql);

		query.setBoolean("blackList", blackList);
		query.setBoolean("disponivel", !blackList);
		query.setString("observacaoBlackList", observacao);
		query.setParameterList("ids", candidatoIds, Hibernate.LONG);

		query.executeUpdate();
	}

	public void atualizaSenha(Long id, String senha)
	{
		String hql = "update Candidato set senha = :senha where id = :id";

		Query query = getSession().createQuery(hql);

		query.setString("senha", senha);
		query.setLong("id", id);

		query.executeUpdate();
	}

	public List findConhecimentosByCandidatoId(Long candidatoId)
	{
		String hql = "select conhecimento.id, conhecimento.nome from Candidato candidato inner join candidato.conhecimentos conhecimento where candidato.id = :candidatoId";

		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);

		return query.list();
	}

	public List findCargosByCandidatoId(Long candidatoId)
	{
		String hql = "select cargo.id, cargo.nomeMercado from Candidato candidato inner join candidato.cargos cargo where candidato.id = :candidatoId";

		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);

		return query.list();
	}

	public List findAreaInteressesByCandidatoId(Long candidatoId)
	{
		String hql = "select areaInteresse.id, areaInteresse.nome from Candidato candidato inner join candidato.areasInteresse areaInteresse where candidato.id = :candidatoId";

		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);

		return query.list();

	}

	public void atualizaTextoOcr(Candidato candidato)
	{
		
		String hql = "update Candidato set ocrTexto = :texto where id = :id";

		Query query = getSession().createQuery(hql);

		String ocrTexto = ArquivoUtil.convertToLatin1Compatible(candidato.getOcrTexto().getBytes());
		query.setString("texto", ocrTexto);
		query.setLong("id", candidato.getId());

		query.executeUpdate();
	}

	public Collection<Candidato> getCandidatosByNome(String candidatoNome)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"),"nome");
		p.add(Projections.property("c.pessoal.cpf"),"cpf");
		p.add(Projections.property("e.nome"),"empresaNome");
		criteria.setProjection(p);

		if (candidatoNome != null && !candidatoNome.trim().equals(""))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + candidatoNome.trim() + "%", Hibernate.STRING) );

		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return criteria.list();
	}

	public Collection<Candidato> getCandidatosByExperiencia(Map parametros, Long[] empresaIds){
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.experiencias", "e", Criteria.LEFT_JOIN);
		Long[] experienciasIds = (Long[])parametros.get("experiencias");
		
		if (isNotBlank((String)parametros.get("palavrasChaveOutrosCampos")))
			criteria.createCriteria("c.formacao", "f", Criteria.LEFT_JOIN);
		
		criteria.createCriteria("e.cargo", "ca", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);

		criteria.setProjection(projectionGetCandidatosByExperiencia());
		
		if(experienciasIds != null &&  experienciasIds.length > 0)
			criteria.add(Expression.in("ca.id", experienciasIds));
		
		criteria.addOrder(Order.asc("c.id"));
		
		montaCriteriaFiltros(parametros, empresaIds, criteria);
		Collection<Candidato> result = new ArrayList<Candidato>();
		
		Collection lista = criteria.list();
		Candidato candidatoAnterior = null;

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();){
			Object[] array = it.next();
			
			Long experienciaId = (Long) array[0];
			Date dataAdmissao = (Date) array[1];
			Date dataDesligamento = (Date) array[2];
			Long candidatoId = (Long)array[3];
			Long cargoId = (Long) array[4];
			
			if(candidatoAnterior == null || !candidatoAnterior.getId().equals(candidatoId)){
				Candidato candidato = new Candidato(candidatoId, null);
				Collection<Experiencia> experiencias = new ArrayList<Experiencia>(); 
				
				experiencias.add(new Experiencia(experienciaId, dataAdmissao, dataDesligamento, cargoId));
				candidato.setExperiencias(experiencias);
				candidatoAnterior = candidato;
				result.add(candidatoAnterior);
			}else
				candidatoAnterior.getExperiencias().add(new Experiencia(experienciaId, dataAdmissao, dataDesligamento, cargoId));
		}

		return result;
	}

	private ProjectionList projectionGetCandidatosByExperiencia() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("c.id"),"projectionCandidatoId");
		p.add(Projections.property("ca.id"),"projectionCargoId");
		p.add(Projections.groupProperty("c.id"));
		p.add(Projections.groupProperty("ca.id"));
		p.add(Projections.groupProperty("ca.nome"));
		p.add(Projections.groupProperty("e.id"));
		p.add(Projections.groupProperty("e.dataAdmissao"));
		p.add(Projections.groupProperty("e.dataDesligamento"));
		return p;
	}

	public void updateSenha(Long candidatoId, String senha, String novaSenha)
	{
		String hql = "update Candidato set senha = :novaSenha where id = :id and senha = :senha";

		Query query = getSession().createQuery(hql);

		query.setString("novaSenha", novaSenha);
		query.setString("senha", senha);
		query.setLong("id", candidatoId);

		query.executeUpdate();
	}

	public Collection<AvaliacaoCandidatosRelatorio> findRelatorioAvaliacaoCandidatos(Date dataIni, Date dataFim, Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, char statusSolicitacao)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio(count(c.id), es.nome, hc.apto) ");
		hql.append("from Candidato c ");
		hql.append("join c.candidatoSolicitacaos cs ");
		hql.append("join cs.solicitacao sol ");
		hql.append("join sol.faixaSalarial fs ");
		hql.append("join cs.historicoCandidatos hc ");
		hql.append("join hc.etapaSeletiva es ");
		hql.append("where c.empresa.id = :empresaId ");
		hql.append("and hc.data between :dataIni and :dataFim ");

		if (statusSolicitacao == StatusSolicitacao.ABERTA)
			hql.append("and (sol.dataEncerramento is null or sol.dataEncerramento >= :dataFim) ");
		else if (statusSolicitacao == StatusSolicitacao.ENCERRADA)
			hql.append("and (sol.dataEncerramento is not null and sol.dataEncerramento <= :dataFim) ");
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and sol.estabelecimento.id in (:estabelecimentoIds) ");

		if (areaIds != null && areaIds.length > 0)
			hql.append("and sol.areaOrganizacional.id in (:areaIds) ");

		if (cargoIds != null && cargoIds.length > 0)
			hql.append("and fs.cargo.id in (:cargoIds) ");


		hql.append("group by hc.apto, es.nome order by es.nome ");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		if (cargoIds != null && cargoIds.length > 0)
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		return query.list();
	}
	
	public Collection<Candidato> findCandidatosIndicadosPor(Date dataIni, Date dataFim, Long[] empresasIds)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"),"nome");
		p.add(Projections.property("c.dataCadastro"),"dataCadastro");
		p.add(Projections.property("c.pessoal.indicadoPor"),"pessoalIndicadoPor");
		p.add(Projections.property("e.nome"),"empresaNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.ne("c.pessoal.indicadoPor", ""));
		if (empresasIds != null)
			criteria.add(Expression.in("c.empresa.id", empresasIds));
		
		criteria.add(Expression.between("c.dataCadastro", dataIni, dataFim));
		
		criteria.addOrder(Order.asc("c.pessoal.indicadoPor"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return criteria.list();
	}

	public Collection<Candidato> findByNomeCpf(Candidato candidato, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);
		
		if (empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));

		if(candidato != null && isNotBlank(candidato.getNome()))
			criteria.add(Expression.like("c.nome", "%"+ candidato.getNome() +"%").ignoreCase() );

		if(candidato != null && candidato.getPessoal() != null && isNotBlank(candidato.getPessoal().getCpf()))
			criteria.add(Expression.like("c.pessoal.cpf", "%"+ candidato.getPessoal().getCpf() +"%").ignoreCase() );

		criteria.add(Expression.eq("c.contratado", false));

		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));

		return criteria.list();
	}

	public void migrarBairro(String bairro, String bairroDestino)
	{
		String hql = "update Candidato set bairro = :bairroDestino where bairro = :bairro";

		Query query = getSession().createQuery(hql);
		query.setString("bairro", bairro);
		query.setString("bairroDestino", bairroDestino);

		query.executeUpdate();
	}

	public Collection<Candidato> findCandidatosForSolicitacao(String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, String[] cargosCheck, 
			String[] conhecimentosCheck, Collection<Long> candidatosJaSelecionados, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar, Long[] empresaIds, boolean todasEmpresasPermitidas)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.experiencias", "ex", Criteria.LEFT_JOIN);
		
		if (somenteSemSolicitacao)
			criteria.createCriteria("c.candidatoSolicitacaos", "cs", Criteria.LEFT_JOIN).add(Expression.isNull("cs.id"));
		
		ProjectionList p = Projections.projectionList().create();
		criteria = montaProjectionGroup(criteria, p);

		whereBusca(indicadoPor, nomeBusca, cpfBusca, escolaridade, uf, cidadesCheck, candidatosJaSelecionados, criteria);
		
		if(todasEmpresasPermitidas){
			if(cargosCheck != null && cargosCheck.length > 0){
				criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(
						Expression.sqlRestriction("cg5_.nomemercado || (CASE cg5_.ativo WHEN true THEN ' (Ativo)' ELSE ' (Inativo)' END) in ("  +  "'" + StringUtils.join(cargosCheck, "','") + "'" + ")"));
			}
			if(conhecimentosCheck != null && conhecimentosCheck.length > 0)
				criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.nome", conhecimentosCheck));
		}else{
			if(cargosCheck != null && cargosCheck.length > 0)
				criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(Expression.in("cg.id", StringUtil.stringToLong(cargosCheck)));

			if(conhecimentosCheck != null && conhecimentosCheck.length > 0)
				criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.id", StringUtil.stringToLong(conhecimentosCheck)));
		}
		
		criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if(qtdRegistros!=null)
			criteria.setMaxResults(qtdRegistros);
		
		if(ordenar!=null && ordenar.equals("dataAtualizacao"))
			criteria.addOrder(Order.desc("c." + ordenar));
		else
			criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return criteria.list();
	}

	private void whereBusca(String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, Collection<Long> candidatosJaSelecionados, Criteria criteria)
	{
		criteria.add(Expression.eq("c.disponivel", true));
		criteria.add(Expression.eq("c.contratado", false));
		criteria.add(Expression.eq("c.blackList", false));

		if(isNotBlank(indicadoPor))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.indicadoPor) ilike  normalizar(?)", "%" + indicadoPor + "%", Hibernate.STRING));

		if(isNotBlank(nomeBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));

		if(isNotBlank(cpfBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.cpf) ilike  normalizar(?)", "%" + cpfBusca + "%", Hibernate.STRING));

		if(isNotBlank(escolaridade))
			criteria.add(Expression.sqlRestriction("coalesce(cast(nullif(this_.escolaridade,'') as integer),0) >= ?", Integer.parseInt(escolaridade), Hibernate.INTEGER)); 
		
		if(cidadesCheck != null && cidadesCheck.length > 0)
			criteria.add(Expression.in("cd.id", cidadesCheck));

		if(uf != null)
			criteria.add(Expression.eq("uf.id", uf));

		if (candidatosJaSelecionados != null && candidatosJaSelecionados.size() > 0)
			criteria.add(Expression.not((Expression.in("c.id", candidatosJaSelecionados))));
	}

	private Criteria montaProjectionGroup(Criteria criteria, ProjectionList p)
	{
		criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		p.add(Projections.groupProperty("c.id"), "id");
		p.add(Projections.groupProperty("c.nome"), "nome");
		p.add(Projections.groupProperty("c.pessoal.cpf"), "pessoalCpf");
		p.add(Projections.groupProperty("c.dataAtualizacao"), "dataAtualizacao");
		p.add(Projections.groupProperty("c.origem"), "origem");
		p.add(Projections.groupProperty("c.pessoal.indicadoPor"),"pessoalIndicadoPor");
		p.add(Projections.groupProperty("cd.nome"), "candidatoCidade");
		p.add(Projections.groupProperty("c.pessoal.escolaridade"), "pessoalEscolaridade");
		p.add(Projections.groupProperty("uf.sigla"), "sigla");
		p.add(Projections.groupProperty("e.nome"), "projectionEmpresaNome");
		p.add(Projections.property("c.experiencias"), "experiencias");
		p.add(Projections.sqlProjection("(exists (select cs2.id from CandidatoSolicitacao cs2 where cs2.candidato_id=this_.id and cs2.triagem = false)) as inscritoSolicitacao",
        		new String []  {"inscritoSolicitacao"}, 
        		new Type[] {Hibernate.BOOLEAN}), "inscritoSolicitacao");
		p.add(Projections.sqlProjection("(exists(select col.cpf from Colaborador col where col.cpf=this_.cpf and this_.cpf is not null and this_.cpf <> '')) as jaFoiColaborador ", 
				new String []  {"jaFoiColaborador"}, 
				new Type[] {Hibernate.BOOLEAN}), "jaFoiColaborador");

		criteria.setProjection(p);
		return criteria;
	}

	/**
	 * Converte para thumbnail as fotos de todos os candidatos existentes no
	 * banco. <br />
	 * Este método ao fim da operação comita a transação e fecha a session do
	 * Hibernate. <br />
	 * http://docs.jboss.org/hibernate/core/3.3/reference/en/html/batch.html#batch-statelesssession
	 */
	public void converteTodasAsFotosParaThumbnail() {
		
		// TODO: melhorar isso com try-catch-finally e limpar diretório temporário (tomcat?) no final
		
		StatelessSession session = getSessionFactory().openStatelessSession();
		Transaction tx = session.beginTransaction();
		
		int count = 0;
		ScrollableResults candidatos = buscaTodosComFoto(session);
		while (candidatos.next()) {
			Candidato candidato = (Candidato) candidatos.get(0);
			logger.info("[#" + ++count + "] Candidato (id:" + candidato.getId() + ")");
			converteFotoDo(candidato, true);
			atualizaApenasAFoto(session, candidato.getId(), candidato.getFoto());
		}
		
		tx.commit();
		session.close();
		
	}

	/**
	 * Tenta converter a foto do usuário e em caso de erro espera um segundo e
	 * tenta novamente. Se ocorrer erro pela 2a vez então exibe o erro no log e
	 * pula para a próxima foto.
	 */
	private void converteFotoDo(Candidato candidato, boolean tentaNovamenteEmCasoDeErro) {
		try {
			candidato.converteFotoParaThumbnail();
		} catch (Exception e) {
			if (tentaNovamenteEmCasoDeErro) {
				esperaUmSegundo();
				converteFotoDo(candidato, false);
			} else
				logger.error("Erro ao converter foto do candidato (id:" + candidato.getId() + "): " + e.getMessage(), e);
		}
	}

	private void esperaUmSegundo() {
		try {
			Thread.sleep(1000); // espera 1s
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private ScrollableResults buscaTodosComFoto(StatelessSession session) {
		ScrollableResults candidatos = session
			.createQuery("select new Candidato(c.id, c.foto) from Candidato c where c.foto.size is not null order by size desc")
//			.setFirstResult(1)
//			.setMaxResults(22)
			.setFetchSize(30)
			.scroll(ScrollMode.FORWARD_ONLY);
		return candidatos;
	}
	
	private void atualizaApenasAFoto(StatelessSession session, Long id, File foto) {
		session.createQuery("update Candidato set foto.size = :size, foto.bytes = :bytes, foto.contentType = :type where id = :id")
			.setParameter("id", id)
			.setParameter("size", foto.getSize())
			.setParameter("bytes", foto.getBytes())
			.setParameter("type", foto.getContentType())
			.executeUpdate();
	}

	public String getSenha(Long id) 
	{
        Criteria criteria = getSession().createCriteria(Candidato.class, "c");
        
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.senha"),"senha");
        
        criteria.setProjection(p);
		criteria.add(Expression.eq("c.id", id));
        
		return (String) criteria.uniqueResult();
	}

	public void updateDisponivelAndContratadoByColaborador(boolean disponivel, boolean contratado, Long... colaboradoresIds) 
	{
		String hql = "update Candidato set disponivel = :disponivel, contratado = :contratado where id in (select candidato.id from Colaborador where id in (:colaboradorId))";

		Query query = getSession().createQuery(hql);
		query.setBoolean("disponivel", disponivel);
		query.setBoolean("contratado", contratado);
		query.setParameterList("colaboradorId", colaboradoresIds, Hibernate.LONG);
		
		query.executeUpdate();
	}
	
	public void updateDisponivel(boolean disponivel, Long candidatoId) 
	{
		String hql = "update Candidato set disponivel = :disponivel where id = :candidatoId";
		
		Query query = getSession().createQuery(hql);
		query.setBoolean("disponivel", disponivel);
		query.setLong("candidatoId", candidatoId);
		
		query.executeUpdate();
	}

	public Collection<Candidato> findQtdCadastradosByOrigem(Date dataIni, Date dataFim) 
	{
		StringBuilder hql = new StringBuilder("select new Candidato(e.nome, c.origem, count(c.origem)) ");
		hql.append("from Candidato c ");
		hql.append("join c.empresa e ");
		hql.append("where c.dataCadastro between :dataIni and :dataFim ");
		hql.append("group by c.origem, e.nome order by e.nome asc, count(c.origem) desc ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);

		return query.list();
	}

	public Collection<String> getComoFicouSabendoVagas() 
	{
        Criteria criteria = getSession().createCriteria(Candidato.class, "c");
        
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.comoFicouSabendoVaga"),"comoFicouSabendoVaga");
        criteria.setProjection(p);
        
        criteria.add(Expression.isNotNull("c.comoFicouSabendoVaga"));

        return criteria.list();
	}
	
	public Collection<ComoFicouSabendoVaga> countComoFicouSabendoVagas(Long empresaId, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.comoFicouSabendoVaga", "como", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.groupProperty("como.nome"), "nome"));
		p.add(Projections.alias(Projections.count("c.id"), "qtd"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.isNotNull("c.comoFicouSabendoVaga.id"));
		criteria.add(Expression.between("c.dataAtualizacao", dataIni, dataFim));
		
		criteria.addOrder(Order.desc("qtd"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ComoFicouSabendoVaga.class));
		
		return criteria.list();
	}

	public void updateExamePalografico(Candidato candidato) 
	{
		String queryHQL = "update Candidato c set c.examePalografico = :examePalografico where c.id = :candidatoId";

		Session session = getSession();
		Query query = session.createQuery(queryHQL);
		
		query.setLong("candidatoId",candidato.getId());
		query.setString("examePalografico",candidato.getExamePalografico());
		
		query.executeUpdate();
	}

	public Collection<Candidato> triagemAutomatica(Solicitacao solicitacao, Integer tempoExperiencia, Map<String, Integer> pesos, Integer percentualMinimo) 
	{
		double divisao = pesos.get("cargo");
		StringBuilder sql = new StringBuilder();
		
		sql.append("select distinct c.id, c.nome, c.dataNascimento, c.sexo, c.pretencaoSalarial, c.escolaridade, cid.nome as nomeCidade, e.sigla, ");
		sql.append("cast(coalesce(sum((extract('day' from coalesce(ex.datadesligamento, now()) - ex.dataadmissao ) / 365 )* 12), 0) as integer) as experiencia, (");
		
		Long cargoId = solicitacao.getFaixaSalarial().getCargo().getId();
		Long empresaId = solicitacao.getEmpresa().getId();
			
		if(tempoExperiencia != null && tempoExperiencia > 0 && pesos.containsKey("tempoExperiencia"))
		{
			sql.append("case when sum((extract('day' from coalesce(ex.datadesligamento, now()) - ex.dataadmissao ) / 365 )* 12)  >= :tempoExperiencia then :pesoTempoExperiencia else 0 end + ");
			divisao += pesos.get("tempoExperiencia");
		}
			
		if(solicitacao.getIdadeMinima() != null && solicitacao.getIdadeMinima() != 0 && solicitacao.getIdadeMaxima() != null && solicitacao.getIdadeMaxima() != 0 && pesos.containsKey("idade"))
		{
			sql.append("case when extract(year from age(c.dataNascimento)) between :idadeMinima and :idadeMaxima then :pesoIdade else 0 end + ");
			divisao += pesos.get("idade");
		}
		
		if(solicitacao != null && solicitacao.getCidade() != null && solicitacao.getCidade().getId() != null && pesos.containsKey("cidade"))
		{
			sql.append("CASE cid.id WHEN :cidadeId THEN :pesoCidade ELSE 0  END + ");
			divisao += pesos.get("cidade");
		}
		
		if(solicitacao != null && solicitacao.getEscolaridade() != null && !solicitacao.getEscolaridade().equals("") && pesos.containsKey("escolaridade"))
		{
			sql.append("CASE WHEN CAST(c.escolaridade AS integer) >= :escolaridade THEN :pesoEscolaridade ELSE 0 END + "); 
			divisao += pesos.get("escolaridade");
		}

		if(solicitacao != null && solicitacao.getRemuneracao() != null && solicitacao.getRemuneracao() > 0 && pesos.containsKey("pretensaoSalarial"))
		{
			sql.append("CASE when c.pretencaoSalarial <= :pretensaoSalarial THEN :pesoPretensaoSalarial*(CAST (c.pretencaoSalarial AS double precision)/:pretensaoSalarial) ELSE 0  END + "); 
			divisao += pesos.get("pretensaoSalarial");
		}

		if(solicitacao != null && solicitacao.getSexo() != "I" && pesos.containsKey("sexo"))
		{
			sql.append("CASE c.sexo WHEN :sexo THEN :pesoSexo ELSE 0 END + ");
			divisao += pesos.get("sexo");
		}

		sql.append("CASE when cc.cargos_id in (:cargoId) THEN :pesoCargo ELSE 0  END ");
		
		sql.append(" )/:divisao as compatibilidade,  "); 
		
		sql.append(" (exists (select cs.id from CandidatoSolicitacao cs where cs.candidato_id=c.id)) as inscritoSolicitacao, ");
		sql.append(" c.cpf ");
		
		sql.append("from candidato c ");
		sql.append("left join experiencia ex on ex.candidato_id=c.id and ex.cargo_id = :cargoId ");
		sql.append("left join candidato_cargo cc on cc.candidato_id = c.id and cc.cargos_id = :cargoId ");
		sql.append("left join estado e on e.id = c.uf_id ");
		sql.append("left join cidade as cid on cid.id = c.cidade_id ");
		sql.append("where c.blacklist = false ");
		sql.append("and c.empresa_id = :empresaId ");
		sql.append("group by c.id, c.nome, c.dataNascimento, c.sexo, c.pretencaoSalarial, c.escolaridade, cc.cargos_id, cid.id, cid.nome, e.sigla, c.cpf ");
		sql.append("order by compatibilidade desc, experiencia desc");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		if(tempoExperiencia != null && tempoExperiencia > 0 && pesos.containsKey("tempoExperiencia"))
		{
			query.setInteger("tempoExperiencia", tempoExperiencia);
			query.setInteger("pesoTempoExperiencia", pesos.get("tempoExperiencia"));
		}
			
		if(solicitacao.getIdadeMinima() != null && solicitacao.getIdadeMinima() != 0 && solicitacao.getIdadeMaxima() != null && solicitacao.getIdadeMaxima() != 0 && pesos.containsKey("idade"))
		{
			query.setInteger("idadeMinima", solicitacao.getIdadeMinima());
			query.setInteger("idadeMaxima", solicitacao.getIdadeMaxima());
			query.setInteger("pesoIdade", pesos.get("idade"));
		}
		
		if(solicitacao != null && solicitacao.getCidade() != null && solicitacao.getCidade().getId() != null && pesos.containsKey("cidade"))
		{
			query.setLong("cidadeId", solicitacao.getCidade().getId());
			query.setInteger("pesoCidade", pesos.get("cidade"));
		}
		
		if(solicitacao != null && solicitacao.getEscolaridade() != null && !solicitacao.getEscolaridade().equals("") && pesos.containsKey("escolaridade"))
		{
			query.setInteger("escolaridade", Integer.parseInt(solicitacao.getEscolaridade()));
			query.setInteger("pesoEscolaridade", pesos.get("escolaridade"));
		}

		if(solicitacao != null && solicitacao.getRemuneracao() != null && solicitacao.getRemuneracao() > 0 && pesos.containsKey("pretensaoSalarial"))
		{
			query.setDouble("pretensaoSalarial", solicitacao.getRemuneracao());
			query.setInteger("pesoPretensaoSalarial", pesos.get("pretensaoSalarial"));
		}
		
		if(solicitacao != null && solicitacao.getSexo() != "I" && pesos.containsKey("sexo"))
		{
			query.setString("sexo", solicitacao.getSexo());
			query.setInteger("pesoSexo", pesos.get("sexo"));
		}
		
		query.setLong("cargoId", cargoId);
		query.setLong("empresaId", empresaId);
		query.setInteger("pesoCargo", pesos.get("cargo"));
		
		query.setDouble("divisao", divisao);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<Candidato> lista = new ArrayList<Candidato>();
		Candidato cand;
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();

			if (((Double) res[9])*100 >= percentualMinimo)
			{
				cand = new Candidato();
				cand.setId(((BigInteger)res[0]).longValue());
				cand.setNome((String)res[1]);
				cand.setPessoalDataNascimento((Date) res[2]);
				cand.setPessoalSexo((Character) res[3]);
				cand.setPretencaoSalarial((Double) res[4]);
				cand.setPessoalEscolaridade((String) res[5]);
				cand.setEnderecoCidadeNome((String) res[6]);
				cand.setEnderecoUfSigla((String) res[7]);
				cand.setTempoExperiencia((Integer) res[8]);
				cand.setPercentualCompatibilidade(((Double) res[9])*100);
				cand.setInscritoSolicitacao((Boolean) res[10]);
				cand.setPessoalCpf((String) res[11]);
				lista.add(cand);
			}
		}

		return lista;	
	}

	public int findQtdCadastrados(Long empresaId, Date dataDe, Date dataAte) 
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("c.id"));
		
		criteria.setProjection(p);
		
		criteria.add(Expression.between("c.dataCadastro", dataDe, dataAte));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Integer) criteria.uniqueResult();
	}

	public void removeAreaInteresseConhecimentoCargo(Long candidatoId) 
	{
		String[] sqls = new String[]{
		"delete from candidato_areainteresse where candidato_id= " + candidatoId + "; ", 
		"delete from candidato_conhecimento where candidato_id= " + candidatoId + "; ", 
		"delete from candidato_cargo where candidato_id=" + candidatoId + "; "
		};
		
		JDBCConnection.executeQuery(sqls);
	}

	public Collection<Colaborador> findColaboradoresMesmoCpf(String[] candidatosCpfs) 
	{
		StringBuilder hql = new StringBuilder("select new Colaborador(col.id, col.nome, col.pessoal.cpf, col.dataDesligamento) ");
		hql.append("from Colaborador col ");
		hql.append("where col.pessoal.cpf in (:candidatosCpfs) ");
		hql.append(" and col.pessoal.cpf <> '' ");
		hql.append("order by col.nome");
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("candidatosCpfs", candidatosCpfs, Hibernate.STRING);
				
		return query.list();
	}

	public void deleteCargosPretendidos(Long... cargosIds)
	{
		if(!ArrayUtils.isEmpty(cargosIds))
		{
			String[] sql = new String[] {"delete from candidato_cargo where cargos_id in ("+StringUtils.join(cargosIds, ",")+");"};
			JDBCConnection.executeQuery(sql);
		}
	}

	public void inserirNonoDigitoCelular(Long[] ufIds) 
	{
		getSession().flush();
		
		String hql = "update Candidato set contato.foneCelular =  '9'||contato.foneCelular where endereco.uf.id in (:ufIds) and length(contato.foneCelular) = 8";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ufIds", ufIds, Hibernate.LONG);
		
		query.executeUpdate();
	}

	public boolean existeCamposExtras(Long camposExtrasId) 
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("c.camposExtras.id", camposExtrasId));

		return criteria.list().size() > 0;
	}
	
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AutoCompleteVO(c.id, c.nome ||  ' - CPF: ' || c.pessoal.cpf) ");
		hql.append("from Candidato as c ");
		
		hql.append(" where c.empresa.id = :empresaId and ( ");
		hql.append(" normalizar(upper(c.nome)) like normalizar(:descricao) ");
		hql.append(" or normalizar(upper(c.pessoal.cpf)) like normalizar(:descricao) ) ");
		
		hql.append(" order by c.nome");		

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setString("descricao", "%" + descricao.toUpperCase() + "%");
		
		return query.list();
	}

	public Collection<Candidato> getCandidatosByEtapaSeletiva(Long etapaSeletivaId) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs", Criteria.INNER_JOIN);
		criteria.createCriteria("cs.candidato", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.camposExtras", "ce", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		projectionFindById(p);
		p.add(Projections.property("ce.numero1"), "camposExtrasNumero1");
		p.add(Projections.property("ce.data1"), "camposExtrasData1");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("hc.etapaSeletiva.id", etapaSeletivaId));
		criteria.add(Expression.eq("hc.apto", Apto.SIM));
		criteria.add(Expression.eq("c.contratado", false));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		
		return criteria.list();
	}

	public Map<Long, Collection<String>> getFuncoesPretendidasByEtapaSeletiva(Long etapaSeletivaId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select candidato_id, cg.id, cg.nome from candidato_cargo cc ");
		sql.append("inner join cargo cg on cg.id = cc.cargos_id ");
		sql.append("where candidato_id in ( ");
		sql.append("	select distinct c.id ");
		sql.append("	from historicoCandidato hc ");
		sql.append("	inner join candidatosolicitacao cs on cs.id = hc.candidatosolicitacao_id ");
		sql.append("	inner join candidato c on c.id = cs.candidato_id ");
		sql.append("	where hc.etapaseletiva_id = :etapaSeletivaId ");
		sql.append("	and hc.apto = :apto ");
		sql.append(")   ");
		sql.append("order by cc.candidato_id ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("etapaSeletivaId", etapaSeletivaId);
		query.setCharacter("apto", Apto.SIM);
		
		Collection<Object[]> resultado = query.list();
		Map<Long, Collection<String>> funcoesPretendidasCandidato = new HashMap<Long, Collection<String>>();
		Long candidatoId;
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			candidatoId = ((BigInteger)res[0]).longValue();
			
			if(!funcoesPretendidasCandidato.containsKey(candidatoId))
				funcoesPretendidasCandidato.put(candidatoId, new ArrayList<String>());

			funcoesPretendidasCandidato.get(candidatoId).add((String) res[2]);
		}
		
		return funcoesPretendidasCandidato;
	}
	
	public Collection<Candidato> findPorEmpresaByCpfSenha(String cpf, String senha, Long empresaId){
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.id"), "id");
        p.add(Projections.property("c.nome"), "nome");
        p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
        p.add(Projections.property("c.contato.email"),"contatoEmail");
        p.add(Projections.property("c.dataCadastro"),"dataCadastro");
        p.add(Projections.property("c.senha"),"senha");
        p.add(Projections.property("e.nome"),"empresaNome");
        p.add(Projections.property("c.origem"),"origem");

        Criteria criteria = getSession().createCriteria(Candidato.class, "c");
        criteria.createCriteria("c.empresa", "e");
        
		criteria.add(Expression.eq("c.pessoal.cpf", cpf));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("c.senha", senha));
		
		criteria.setProjection(p);
		criteria.addOrder(Order.desc("c.id"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return criteria.list();
	}
}