/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
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

@SuppressWarnings("unchecked")
public class EmpresaDaoHibernate extends GenericDaoHibernate<Empresa> implements EmpresaDao
{
	public Empresa findByCodigo(String codigo, String grupoAC)
	{
		String hql = "from Empresa e where e.codigoAC = :codigo ";
		if (StringUtils.isNotBlank(grupoAC))
			hql += " and e.grupoAC = :grupoAC ";
		
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		
		if (StringUtils.isNotBlank(grupoAC))
			query.setString("grupoAC", grupoAC);
		
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
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.acIntegra"), "acIntegra");
		p.add(Projections.property("e.turnoverPorSolicitacao"), "turnoverPorSolicitacao");
		p.add(Projections.property("e.logoUrl"), "logoUrl");
		p.add(Projections.property("e.campoExtraColaborador"), "campoExtraColaborador");
		p.add(Projections.property("e.campoExtraCandidato"), "campoExtraCandidato");
		p.add(Projections.property("e.mensagemModuloExterno"), "mensagemModuloExterno");
		p.add(Projections.property("e.emailRespLimiteContrato"), "emailRespLimiteContrato");
		p.add(Projections.property("e.imgAniversarianteUrl"), "imgAniversarianteUrl");
		p.add(Projections.property("e.mensagemCartaoAniversariante"), "mensagemCartaoAniversariante");
		p.add(Projections.property("e.enviarEmailAniversariante"), "enviarEmailAniversariante");
		p.add(Projections.property("e.emailRemetente"), "emailRemetente");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (Empresa) criteria.uniqueResult();
	}

	public Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String... roles)
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
		
		if(roles != null && roles.length > 0)
		{
	        Disjunction disjunction = Expression.disjunction();

	        for (String role : roles) 
	        	disjunction.add(Expression.eq("papel.codigo", role));
	        
	        criteria.add(disjunction);
		}
		
		criteria.add(Expression.eq("u.acessoSistema", true));
		
		criteria.addOrder(Order.asc("e.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public void removeEmpresaPadrao(long id) 
	{
		String acao = "DISABLE";
		JDBCConnection.executeQuery(new String[]{executaTrigger(acao)});
		
		String[] sqls = new String[]{
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
				"delete from ConfiguracaoCampoExtra where empresa_id = " + id + ";",
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
				"delete from empresa where id = " + id + ";",
				"delete from historicoambiente where ambiente_id in (select id from ambiente where empresa_id = " + id + ");",
				"delete from historicocolaborador where ambiente_id in (select id from ambiente where empresa_id = " + id + ");",
				"delete from medicaorisco where ambiente_id in (select id from ambiente where empresa_id = " + id + ");",
				"delete from aspecto where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from avaliacaodesempenho where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from colaboradorquestionario where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from pergunta where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from historicobeneficio where beneficio_id in (select id from beneficio where empresa_id = " + id + ");",
				"delete from clinicaautorizada_exame where clinicaautorizada_id in (select id from clinicaautorizada where empresa_id = " + id + ");",
				"delete from examesolicitacaoexame where clinicaautorizada_id in (select id from clinicaautorizada where empresa_id = " + id + ");",
				"delete from cat where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorafastamento where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradoridioma where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorocorrencia where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorquestionario where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorturma where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from comissaoeleicao where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from comissaomembro where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from comissaoreuniaopresenca where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from dependente where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from experiencia where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from formacao where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from gastoempresa where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from historicocolaborador where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from historicocolaboradorbeneficio where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from prontuario where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from reajustecolaborador where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from solicitacaoepi where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from solicitacaoexame where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorturma where curso_id in (select id from curso where empresa_id = " + id + ");",
				"delete from turma where curso_id in (select id from curso where empresa_id = " + id + ");",
				"delete from colaboradorturma where dnt_id in (select id from dnt where empresa_id = " + id + ");",
				"delete from candidatoeleicao where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from comissao where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from comissaoeleicao where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from etapaprocessoeleitoral where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from examesolicitacaoexame where exame_id in (select id from exame where empresa_id = " + id + ");",
				"delete from extintorinspecao where extintor_id in (select id from extintor where empresa_id = " + id + ");",
				"delete from extintormanutencao where extintor_id in (select id from extintor where empresa_id = " + id + ");",
				"delete from gastoempresaitem where gasto_id in (select id from gasto where empresa_id = " + id + ");",
				"delete from gasto where grupogasto_id in (select id from grupogasto where empresa_id = " + id + ");",
				"delete from aspecto where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from avaliacaoturma where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from colaboradorquestionario where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from entrevista where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from fichamedica where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from pergunta where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from pesquisa where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from risco_epi where risco_id in (select id from risco where empresa_id = " + id + ");",
				"delete from riscoambiente where risco_id in (select id from risco where empresa_id = " + id + ");",
				"delete from riscomedicaorisco where risco_id in (select id from risco where empresa_id = " + id + ");",
				"delete from solicitacaoepi_item where solicitacaoepi_id in (select id from solicitacaoepi where empresa_id = " + id + ");",
				"delete from examesolicitacaoexame where solicitacaoexame_id in (select id from solicitacaoexame where empresa_id = " + id + ");",
				"delete from exame where empresa_id = " + id + ";",
				"delete from faturamentomensal where empresa_id = " + id + ";",
				"delete from reajustecolaborador where tabelareajustecolaborador_id in (select id from tabelareajustecolaborador where empresa_id = " + id + ");",
				"delete from colaboradorquestionario where turma_id in (select id from turma where empresa_id = " + id + ");",
				"delete from colaboradorturma where turma_id in (select id from turma where empresa_id = " + id + ");",
				"delete from diaturma where turma_id in (select id from turma where empresa_id = " + id + ");",
				"delete from nivelcompetencia where empresa_id = " + id + ";",
				"delete from colaborador where empresa_id = " + id + ";",
				"delete from naturezalesao where empresa_id = " + id + ";"
		};
		
		JDBCConnection.executeQuery(sqls);
		
		acao = "ENABLE";
		JDBCConnection.executeQuery(new String[]{executaTrigger(acao)});
	}

	public Collection<Empresa> findTodasEmpresas()
	{
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		p.add(Projections.property("e.emailRemetente"),"emailRemetente");
		p.add(Projections.property("e.emailRespSetorPessoal"),"emailRespSetorPessoal");
		p.add(Projections.property("e.emailRespRH"),"emailRespRH");
		p.add(Projections.property("e.logoUrl"),"logoUrl");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<Empresa> findByCartaoAniversario()
	{
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		p.add(Projections.property("e.emailRemetente"),"emailRemetente");
		p.add(Projections.property("e.emailRespRH"),"emailRespRH");
		p.add(Projections.property("e.logoUrl"),"logoUrl");
		p.add(Projections.property("e.imgAniversarianteUrl"), "imgAniversarianteUrl");
		p.add(Projections.property("e.mensagemCartaoAniversariante"), "mensagemCartaoAniversariante");
		p.add(Projections.property("e.enviarEmailAniversariante"), "enviarEmailAniversariante");
		
		criteria.add(Expression.eq("e.enviarEmailAniversariante", true));
		criteria.add(Expression.not(Expression.eq("e.imgAniversarianteUrl", "")));
		
		criteria.setProjection(Projections.distinct(p));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	private String executaTrigger(String acao) 
	{
		String execTrigger = "select alter_trigger(table_name, '" + acao + "') FROM information_schema.constraint_column_usage " +
							" where table_schema='public' " +
							" and table_catalog='fortesrh' group by table_name;";

		return execTrigger;
	}

	public void updateCampoExtra(Long id, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato) 
	{
		String whereId = " where e.id = :empresaId ";
		if(id == null || id.equals(-1L))
			whereId = "";
		
		String hql = "update Empresa e set e.campoExtraColaborador = :campoExtraColaborador, e.campoExtraCandidato = :campoExtraCandidato " + whereId;
		Query query = getSession().createQuery(hql);
		query.setBoolean("campoExtraColaborador", habilitaCampoExtraColaborador);
		query.setBoolean("campoExtraCandidato", habilitaCampoExtraCandidato);

		if(id != null && !id.equals(-1L))
			query.setLong("empresaId", id);
		
		query.executeUpdate();
	}

	public boolean checkEmpresaCodACGrupoAC(Empresa empresa) {
		String hql = "select emp from Empresa emp where codigoac = :codAC and grupoac = :grupoAC and id <> :id";
		Query query = getSession().createQuery(hql);
		query.setString("codAC", empresa.getCodigoAC());
		query.setString("grupoAC", empresa.getGrupoAC());
		query.setLong("id", empresa.getId());
		
		Collection<Empresa> empresas = query.list();
		
		return empresas.size() > 0;
	}

	public boolean checkEmpresaIntegradaAc() {
		Query query = getSession().createQuery("select e.acIntegra from Empresa e where e.acIntegra = true");
		return !query.list().isEmpty();
	}

	public Collection<Empresa> findComCodigoAC() {
		Criteria criteria = getSession().createCriteria(Empresa.class);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("nome"),"nome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.isNotNull("codigoAC"));
		criteria.add(Expression.ne("codigoAC", ""));
		
		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	}
}