/*
 * autor: Moesio Medeiros
 * Data: 07/06/2006
 * Requisito: RFA013
 */
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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.model.type.File;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.util.ArquivoUtil;

@SuppressWarnings({ "deprecation", "unchecked" })
public class CandidatoDaoHibernate extends GenericDaoHibernate<Candidato> implements CandidatoDao
{
    public Candidato findByCPF(String cpf, Long empresaId, Long candidatoId, Boolean contratado, boolean verificaColaborador)
    {
    	Criteria criteria = getSession().createCriteria(Candidato.class, "c");
        
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.id"), "id");
        p.add(Projections.property("c.nome"), "nome");
        p.add(Projections.property("c.senha"),"senha");
        p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
        
        criteria.setProjection(p);
		criteria.add(Expression.eq("c.pessoal.cpf", cpf));

		if (candidatoId != null)
			criteria.add(Expression.not((Expression.eq("c.id", candidatoId))));
		
		if (empresaId != null )
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if (contratado != null )
			criteria.add(Expression.eq("c.contratado", contratado));

		if (verificaColaborador)
		{
			DetachedCriteria subQuery = DetachedCriteria.forClass(Colaborador.class, "col");
			
			ProjectionList pSub = Projections.projectionList().create();
			pSub.add(Projections.property("col.candidato.id"), "id");
			
			subQuery.setProjection(pSub);
			subQuery.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Subqueries.propertyNotIn("c.id", subQuery));
		}
		
		criteria.addOrder(Order.desc("c.id"));
		criteria.setMaxResults(1);
        
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));

		return (Candidato) criteria.uniqueResult();
    }
    
    public Collection<Candidato> findByCPF(String cpf, Long empresaId, 	Long candidatoId, Boolean contratado) 
	{
    	Criteria criteria = getSession().createCriteria(Candidato.class, "c");
        
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.id"), "id");
        p.add(Projections.property("c.nome"), "nome");
        p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
        
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

	public Collection<Candidato> find(int page, int pagingSize, String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("nome"), "nome");
		p.add(Projections.property("idF2RH"), "idF2RH");
		p.add(Projections.property("disponivel"), "disponivel");
		p.add(Projections.property("contratado"), "contratado");
		p.add(Projections.property("c.dataCadastro"), "dataCadastro");
		p.add(Projections.property("dataAtualizacao"), "dataAtualizacao");
		p.add(Projections.property("c.pessoal.indicadoPor"),"pessoalIndicadoPor");
		p.add(Projections.property("c.pessoal.cpf"),"pessoalCpf");
		p.add(Projections.property("e.id"), "empresaId");
		criteria.setProjection(p);
		
		montaProjectionFind(nomeBusca, cpfBusca, empresaId, indicadoPor, visualizar, dataIni, dataFim, observacaoRH, exibeContratados, exibeExterno, criteria);

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		return criteria.list();
	}

	private void montaProjectionFind(String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Criteria criteria)
	{
		criteria.createCriteria("c.empresa", "e");
		
		if (exibeExterno)
			criteria.add(Expression.eq("origem", OrigemCandidato.EXTERNO));
		
		if (!exibeContratados)
			criteria.add(Expression.eq("c.contratado", false));

		// Nome
		if(StringUtils.isNotBlank(nomeBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));
		// CPF
		if(StringUtils.isNotBlank(cpfBusca))
			criteria.add(Expression.like("c.pessoal.cpf", "%"+ cpfBusca +"%").ignoreCase() );
		//Visualizar disponiveis
		if(visualizar == 'D')
			criteria.add(Expression.eq("c.disponivel", true));
		else if(visualizar == 'I')
			criteria.add(Expression.eq("c.disponivel", false));

		if(dataIni != null)
			criteria.add(Expression.ge("c.dataAtualizacao", dataIni));
		if(dataFim != null)
			criteria.add(Expression.le("c.dataAtualizacao", dataFim));

		if (StringUtils.isNotBlank(indicadoPor))
        	criteria.add(Restrictions.sqlRestriction("normalizar(this_.indicadoPor) ilike  normalizar(?)", "%" + indicadoPor + "%", Hibernate.STRING));

		if (StringUtils.isNotBlank(observacaoRH))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.observacaoRH) ilike  normalizar(?)", "%" + observacaoRH + "%", Hibernate.STRING));

		criteria.add(Expression.eq("e.id", empresaId));
	}

	public Collection<Candidato> findBusca(Map parametros, Long empresaId, Collection<Long> idsCandidatos, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar) throws Exception
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		
		if (somenteSemSolicitacao)
			criteria.createCriteria("c.candidatoSolicitacaos", "cs", Criteria.LEFT_JOIN).add(Expression.isNull("cs.id"));
		
		criteria = criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.experiencias", "ex", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.dataAtualizacao"), "dataAtualizacao");
		p.add(Projections.property("c.origem"), "origem");
		p.add(Projections.property("c.pessoal.indicadoPor"),"pessoalIndicadoPor");
		p.add(Projections.property("cd.nome"), "candidatoCidade");
		p.add(Projections.property("c.pessoal.escolaridade"), "pessoalEscolaridade");
		p.add(Projections.property("uf.sigla"), "sigla");
		p.add(Projections.property("c.experiencias"), "experiencias");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");
		p.add(Projections.sqlProjection("(exists (select cs2.id from CandidatoSolicitacao cs2 where cs2.candidato_id=this_.id and cs2.triagem = false)) as inscritoSolicitacao", 
        		new String []  {"inscritoSolicitacao"}, 
        		new Type[] {Hibernate.BOOLEAN}), "inscritoSolicitacao");

		/**
		 * Toda propriedade informada na projection deve ser fornecida também no groupProperty.
		 *
		 * groupProperty usado para filrar apenas um resultado por nome do candidato, mesmo que ele tenha mais de um interesse em um cargo
		 * so aparecerá uma única vez no filtro
		 */
		p.add(Projections.groupProperty("c.id"));
		p.add(Projections.groupProperty("c.nome"));
		p.add(Projections.groupProperty("c.dataAtualizacao"));
		p.add(Projections.groupProperty("c.dataCadastro"), "dataCadastro");
		p.add(Projections.groupProperty("c.pessoal.escolaridade"));
		p.add(Projections.groupProperty("c.origem"));
		p.add(Projections.groupProperty("c.pessoal.indicadoPor"));
		p.add(Projections.groupProperty("cd.nome"));
		p.add(Projections.groupProperty("uf.sigla"));
		p.add(Projections.groupProperty("e.nome"));

		criteria.setProjection(p);

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

	public Integer getCount(Map parametros, long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria = criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.experiencias", "ex", Criteria.LEFT_JOIN);

		montaCriteriaFiltros(parametros, empresaId, criteria);

		criteria.setProjection(Projections.rowCount());

		return (Integer) criteria.list().get(0);
	}

	private void montaCriteriaFiltros(Map parametros, Long empresaId, Criteria criteria)
	{
	//	lista candidatos (disponivel = true , contratado = false)
		criteria.add(Expression.eq("c.disponivel", true));
		criteria.add(Expression.eq("c.contratado", false));
		criteria.add(Expression.eq("c.blackList", false));
		
		if (empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(parametros.get("candidatosComExperiencia") != null && ((Long[])parametros.get("candidatosComExperiencia")).length > 0)
			criteria.add(Expression.in("c.id", (Long[])parametros.get("candidatosComExperiencia")));

		if(parametros.get("bairros") != null && ((String[])parametros.get("bairros")).length > 0)
			criteria.add(Expression.in("c.endereco.bairro", (String[])parametros.get("bairros")));

		if(parametros.get("areasIds")  != null && ((Long[])parametros.get("areasIds")).length > 0)
			criteria.createCriteria("c.areasInteresse", "a", Criteria.LEFT_JOIN).add(Expression.in("a.id", (Long[])parametros.get("areasIds")));

		if(parametros.get("cargosIds")  != null && ((Long[])parametros.get("cargosIds")).length > 0)
			criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(Expression.in("cg.id", (Long[])parametros.get("cargosIds")));

		if(parametros.get("cargosNomes")  != null && ((String[])parametros.get("cargosNomes")).length > 0)
			criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(Expression.in("cg.nome", (String[]) parametros.get("cargosNomes")));

		if(parametros.get("conhecimentosIds")  != null && ((Long[])parametros.get("conhecimentosIds")).length > 0)
			criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.id", (Long[])parametros.get("conhecimentosIds")));

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

		// where titulo ocrtexto
		if (isNotBlank((String)parametros.get("palavrasChave"))) {
			String tipo = (String) parametros.get("formas");
			// 1:todas
			// 2:qualquer
			// 3:frase exata

			if (tipo.equals("1"))
			{
				String[] palavrasExpressao = ((String) parametros.get("palavrasChave")).split(" ");

				Junction juncaoTodas = Expression.conjunction();
				for (int i = 0; i < palavrasExpressao.length; i++)
				{
					juncaoTodas.add(Restrictions.sqlRestriction("normalizar(this_.ocrTexto) ilike  normalizar(?)", "%" + palavrasExpressao[i] + "%", Hibernate.STRING) );
				}
				criteria.add(juncaoTodas);
			}
			else if (tipo.equals("2"))
			{
				String[] palavrasExpressao = ((String) parametros.get("palavrasChave")).split(" ");

				Junction juncao = Expression.disjunction();
				for (int i = 0; i < palavrasExpressao.length; i++)
				{
					juncao.add(Restrictions.sqlRestriction("normalizar(this_.ocrTexto) ilike  normalizar(?)", "%" + palavrasExpressao[i].trim() + "%", Hibernate.STRING) );
				}
				
				criteria.add(juncao);

			}
			else if (tipo.equals("3"))
			{
				criteria.add(Restrictions.sqlRestriction("normalizar(this_.ocrTexto) ilike  normalizar(?)", "%" + parametros.get("palavrasChave").toString().trim() + "%", Hibernate.STRING) );
			}
		}

	}

	public Integer getCount(String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.setProjection(Projections.rowCount());

		montaProjectionFind(nomeBusca, cpfBusca, empresaId, indicadoPor, visualizar, dataIni, dataFim, observacaoRH, exibeContratados, exibeExterno, criteria);

		return (Integer) criteria.list().get(0);
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
		p.add(Projections.property("c.camposExtras"), "camposExtras");

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

	public Collection<Candidato> getCandidatosByExperiencia(Map parametros, Long empresa)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		criteria.createCriteria("c.experiencias", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("e.cargo", "ca", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);

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

		criteria.setProjection(p);

		criteria.add(Expression.in("ca.id", (Long[])parametros.get("experiencias")));

		criteria.addOrder(Order.asc("c.id"));

		Map param = new HashMap();
		param.putAll(parametros);
		param.remove("experiencias");

		montaCriteriaFiltros(param, empresa, criteria);

		Collection<Candidato> result = new ArrayList<Candidato>();
		
		Collection lista = criteria.list();
		Candidato candidatoAnterior = null;

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();

			if(candidatoAnterior == null || !candidatoAnterior.getId().equals((Long) array[3]))
			{
				Candidato candidato = new Candidato();
				candidato.setId((Long) array[3]);

				Experiencia experiencia = new Experiencia();
				experiencia.setId((Long) array[0]);
				experiencia.setDataAdmissao((Date) array[1]);
				experiencia.setDataDesligamento((Date) array[2]);
				experiencia.setCargoId((Long) array[4]);

				Collection<Experiencia> experiencias = new ArrayList<Experiencia>();
				experiencias.add(experiencia);

				candidato.setExperiencias(experiencias);

				candidatoAnterior = candidato;

				result.add(candidatoAnterior);

			}
			else
			{
				Experiencia experiencia = new Experiencia();
				experiencia.setId((Long) array[0]);
				experiencia.setDataAdmissao((Date) array[1]);
				experiencia.setDataDesligamento((Date) array[2]);
				experiencia.setCargoId((Long) array[4]);

				candidatoAnterior.getExperiencias().add(experiencia);
			}
		}

		return result;

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

	public Collection<Candidato> findCandidatosForSolicitacaoAllEmpresas(String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, String[] cargosCheck, String[] conhecimentosCheck, Collection<Long> candidatosJaSelecionados, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		
		if (somenteSemSolicitacao)
			criteria.createCriteria("c.candidatoSolicitacaos", "cs", Criteria.LEFT_JOIN).add(Expression.isNull("cs.id"));
		
		ProjectionList p = Projections.projectionList().create();
		criteria = montaProjectionGroup(criteria, p);

		whereBusca(indicadoPor, nomeBusca, cpfBusca, escolaridade, uf, cidadesCheck, candidatosJaSelecionados, criteria);

		if(cargosCheck != null && cargosCheck.length > 0)
			criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(Expression.in("cg.nomeMercado", cargosCheck));

		if(conhecimentosCheck != null && conhecimentosCheck.length > 0)
			criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.nome", conhecimentosCheck));

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

	public Collection<Candidato> findCandidatosForSolicitacaoByEmpresa(Long empresaId, String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, Long[] cargosCheck, Long[] conhecimentosCheck, Collection<Long> candidatosJaSelecionados, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar)
	{
		Criteria criteria = getSession().createCriteria(Candidato.class, "c");
		
		if (somenteSemSolicitacao)
			criteria.createCriteria("c.candidatoSolicitacaos", "cs", Criteria.LEFT_JOIN).add(Expression.isNull("cs.id"));
		
		ProjectionList p = Projections.projectionList().create();
		criteria = montaProjectionGroup(criteria, p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		whereBusca(indicadoPor, nomeBusca, cpfBusca, escolaridade, uf, cidadesCheck, candidatosJaSelecionados, criteria);

		if(cargosCheck != null && cargosCheck.length > 0)
			criteria.createCriteria("c.cargos", "cg", Criteria.LEFT_JOIN).add(Expression.in("cg.id", cargosCheck));

		if(conhecimentosCheck != null && conhecimentosCheck.length > 0)
			criteria.createCriteria("c.conhecimentos", "con", Criteria.LEFT_JOIN).add(Expression.in("con.id", conhecimentosCheck));

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
			criteria.add(Expression.sqlRestriction("cast(this_.escolaridade as integer) >= ?", Integer.parseInt(escolaridade), Hibernate.INTEGER)); 
		
		if(cidadesCheck != null && cidadesCheck.length > 0)
			criteria.add(Expression.in("cd.id", cidadesCheck));

		if(uf != null)
			criteria.add(Expression.eq("uf.id", uf));

		if (candidatosJaSelecionados != null && candidatosJaSelecionados.size() > 0)
			criteria.add(Expression.not((Expression.in("c.id", candidatosJaSelecionados))));
	}

	private Criteria montaProjectionGroup(Criteria criteria, ProjectionList p)
	{
		criteria = criteria.createCriteria("c.endereco.cidade", "cd", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.endereco.uf", "uf", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.experiencias", "ex", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.dataAtualizacao"), "dataAtualizacao");
		p.add(Projections.property("c.origem"), "origem");
		p.add(Projections.property("c.pessoal.indicadoPor"),"pessoalIndicadoPor");
		p.add(Projections.property("cd.nome"), "candidatoCidade");
		p.add(Projections.property("c.pessoal.escolaridade"), "pessoalEscolaridade");
		p.add(Projections.property("uf.sigla"), "sigla");
		p.add(Projections.property("c.experiencias"), "experiencias");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");
		p.add(Projections.sqlProjection("(exists (select cs2.id from CandidatoSolicitacao cs2 where cs2.candidato_id=this_.id)) as inscritoSolicitacao", 
        		new String []  {"inscritoSolicitacao"}, 
        		new Type[] {Hibernate.BOOLEAN}), "inscritoSolicitacao");

		p.add(Projections.groupProperty("c.id"));
		p.add(Projections.groupProperty("c.nome"));
		p.add(Projections.groupProperty("c.dataAtualizacao"));
		p.add(Projections.groupProperty("c.pessoal.escolaridade"));
		p.add(Projections.groupProperty("c.origem"));
		p.add(Projections.groupProperty("c.pessoal.indicadoPor"));
		p.add(Projections.groupProperty("cd.nome"));
		p.add(Projections.groupProperty("uf.sigla"));
		p.add(Projections.groupProperty("e.nome"));

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

	public void updateDisponivelAndContratadoByColaborador(boolean disponivel, boolean contratado, Long colaboradorId) 
	{
		String hql = "update Candidato set disponivel = :disponivel, contratado = :contratado where id=(select candidato.id from Colaborador where id = :colaboradorId)";

		Query query = getSession().createQuery(hql);
		query.setBoolean("disponivel", disponivel);
		query.setBoolean("contratado", contratado);
		query.setLong("colaboradorId", colaboradorId);
		
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
		
		sql.append(" (exists (select cs.id from CandidatoSolicitacao cs where cs.candidato_id=c.id)) as inscritoSolicitacao ");
		
		sql.append("from candidato c ");
		sql.append("left join experiencia ex on ex.candidato_id=c.id and ex.cargo_id = :cargoId ");
		sql.append("left join candidato_cargo cc on cc.candidato_id = c.id and cc.cargos_id = :cargoId ");
		sql.append("left join estado e on e.id = c.uf_id ");
		sql.append("left join cidade as cid on cid.id = c.cidade_id ");
		sql.append("where c.blacklist = false ");
		sql.append("and c.empresa_id = :empresaId ");
		sql.append("group by c.id, c.nome, c.dataNascimento, c.sexo, c.pretencaoSalarial, c.escolaridade, cc.cargos_id, cid.id, cid.nome, e.sigla ");
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

	public Collection<Colaborador> findColaboradoresMesmoCpf(Long[] candidatosIds) 
	{
		Collection<Colaborador> colaboradores = getSession().createQuery(
															"select new Colaborador(col.id, col.nome, col.pessoal.cpf, col.dataDesligamento, can.id, can.nome) " +
															"from Colaborador col, Candidato can " +
															"where col.pessoal.cpf = can.pessoal.cpf " +
															"and can.id in (:candidatosIds) " +
															"order by col.nome")
															.setParameterList("candidatosIds", candidatosIds, Hibernate.LONG)
															.list();
		
		return colaboradores;
	}
}