/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
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
		
		List<Empresa> retorno = query.list(); 
		
		return (retorno.size() > 0 ? retorno.get(0) : null);
	}

	public boolean getIntegracaoAC(Long id)
	{
		Query query = getSession().createQuery("select e.acIntegra from Empresa e where e.id = :id");
		query.setLong("id", id);
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
		criteria.createCriteria("e.cidade", "cid", Criteria.LEFT_JOIN);
		criteria.createCriteria("e.uf", "uf", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.razaoSocial"), "razaoSocial");
		p.add(Projections.property("e.cnpj"), "cnpj");
		p.add(Projections.property("e.cnae"), "cnae");
		p.add(Projections.property("e.acIntegra"), "acIntegra");
		p.add(Projections.property("e.controlarVencimentoCertificacaoPor"), "controlarVencimentoCertificacaoPor");
		p.add(Projections.property("e.grupoAC"), "grupoAC");
		p.add(Projections.property("e.codigoAC"), "codigoAC");
		p.add(Projections.property("e.exibirSalario"), "exibirSalario");
		p.add(Projections.property("e.solPessoalExibirSalario"), "solPessoalExibirSalario");
		p.add(Projections.property("e.solPessoalExibirColabSubstituido"), "solPessoalExibirColabSubstituido");
		p.add(Projections.property("e.solPessoalObrigarDadosComplementares"), "solPessoalObrigarDadosComplementares");
		p.add(Projections.property("e.solPessoalReabrirSolicitacao"), "solPessoalReabrirSolicitacao");
		p.add(Projections.property("e.turnoverPorSolicitacao"), "turnoverPorSolicitacao");
		p.add(Projections.property("e.logoUrl"), "logoUrl");
		p.add(Projections.property("e.campoExtraColaborador"), "campoExtraColaborador");
		p.add(Projections.property("e.campoExtraCandidato"), "campoExtraCandidato");
		p.add(Projections.property("e.mensagemModuloExterno"), "mensagemModuloExterno");
		p.add(Projections.property("e.emailRespLimiteContrato"), "emailRespLimiteContrato");
		p.add(Projections.property("e.imgAniversarianteUrl"), "imgAniversarianteUrl");
		p.add(Projections.property("e.mensagemCartaoAniversariante"), "mensagemCartaoAniversariante");
		p.add(Projections.property("e.emailRemetente"), "emailRemetente");
		p.add(Projections.property("e.emailRespRH"),"emailRespRH");
		p.add(Projections.property("e.formulaTurnover"), "formulaTurnover");
		p.add(Projections.property("e.formulaTurnover"), "formulaTurnover");
		p.add(Projections.property("cid.nome"), "projectionCidadeNome");
		p.add(Projections.property("uf.sigla"), "projectionUfSigla");
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
		JDBCConnection.executaTrigger(acao);
		
		String[] sqls = new String[]{
				"delete from gerenciadorcomunicacao_usuario where gerenciadorcomunicacao_id in (select id from gerenciadorcomunicacao where empresa_id = " + id + ");",
				"delete from gerenciadorcomunicacao where empresa_id = " + id + ";",
				"delete from faixasalarialhistorico where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "));",
				"delete from anuncio where solicitacao_id in (select id from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + ")));",
				"delete from historicocandidato where candidatosolicitacao_id in (select id from candidatosolicitacao where solicitacao_id in (select id from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "))));",
				"delete from candidatosolicitacao where solicitacao_id in (select id from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + ")));",
				"delete from solicitacao where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "));",
				"delete from solicitacao where empresa_id = " + id + ";",
				"update historicocolaborador set faixasalarial_id = null where faixasalarial_id in (select Id from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + "));",
				"delete from configuracaonivelcompetenciacolaborador where configuracaonivelcompetenciafaixasalarial_id in (select id from configuracaonivelcompetenciafaixasalarial where nivelcompetenciahistorico_id in (select id from nivelcompetenciahistorico where empresa_id = " + id + "));",
				"delete from configuracaonivelcompetenciafaixasalarial where nivelcompetenciahistorico_id in (select id from nivelcompetenciahistorico where empresa_id = " + id + ");",
				"delete from faixasalarial where cargo_id in (select ID from cargo where empresa_id = " + id + ");",
				"delete from cargo_areaformacao where cargo_id in (select id from cargo where empresa_id = " + id + ");",
				"delete from cargo_conhecimento where cargo_id in (select id from cargo where empresa_id = " + id + ");",
				"delete from candidato_cargo where cargos_id in (select id from cargo where empresa_id = " + id + ");",
				"delete from cargo where empresa_id = " + id + ";",
				"delete from ConfiguracaoCampoExtra where empresa_id = " + id + ";",
				"delete from candidato_conhecimento where conhecimentos_id in (select id from conhecimento where empresa_id = " + id + ");",
				"delete from cargo_conhecimento where conhecimentos_id in (select id from conhecimento where empresa_id = " + id + ");",
				"delete from conhecimento_areaorganizacional where conhecimentos_id in (select id from conhecimento where empresa_id = " + id + ");",
				"delete from configuracaonivelcompetenciacriterio where nivelcompetencia_id in (select id from  nivelcompetencia where empresa_id = " + id + ");",
				"delete from criterioavaliacaocompetencia where conhecimento_id in  (select id from  conhecimento where empresa_id = " + id + ");",
				"delete from conhecimento where empresa_id = " + id + ";",
				"delete from documentoanexo where etapaseletiva_id in (select id from etapaseletiva where empresa_id = " + id + ");",
				"delete from historicocandidato where etapaseletiva_id in (select id from etapaseletiva where empresa_id = " + id + ");",
				"delete from cargo_atitude where atitudes_id in (select id from atitude where empresa_id = " + id + ");",
				"delete from atitude_areaorganizacional where atitudes_id in (select id from atitude where empresa_id = " + id + ");",
				"delete from criterioavaliacaocompetencia where atitude_id in  (select id from  atitude where empresa_id = " + id + ");",
				"delete from atitude where empresa_id = " + id + ";",
				"delete from cargo_habilidade where habilidades_id in (select id from habilidade where empresa_id = " + id + ");",
				"delete from habilidade_areaorganizacional where habilidades_id in (select id from habilidade where empresa_id = " + id + ");",
				"delete from criterioavaliacaocompetencia where habilidade_id in  (select id from  habilidade where empresa_id = " + id + ");",
				"delete from habilidade where empresa_id = " + id + ";",
				"delete from epihistorico where epi_id in (select id from epi where empresa_id = " + id + ");",
				"delete from epi where empresa_id = " + id + ";",
				"delete from grupoocupacional where empresa_id = " + id + ";",
				"delete from tipoepi where empresa_id = " + id + ";",
				"delete from certificacao_curso where certificacaos_id in (select id from certificacao where empresa_id = " + id + ");",
				"delete from faixasalarial_certificacao where certificacaos_id in (select id from certificacao where empresa_id = " + id + ");",
				"delete from certificacao where empresa_id = " + id + ";",
				"delete from areainteresse_areaorganizacional where areasorganizacionais_id in (select id from areaorganizacional where empresa_id = " + id + ");",
				"delete from cargo_areaorganizacional where areasorganizacionais_id in (select id from areaorganizacional where empresa_id = " + id + ");",
				"update historicocolaborador set areaorganizacional_id = null where areaorganizacional_id in (select id from areaorganizacional where empresa_id = " + id + ");",
				"delete from areaorganizacional where empresa_id = " + id + ";",
				"delete from areainteresse where empresa_id = " + id + ";",
				"delete from auditoria where empresa_id = " + id + ";",
				"delete from formacao where candidato_id in (select id from candidato where empresa_id = " + id + ");",
				"delete from candidatocurriculo where candidato_id in (select id from candidato where empresa_id = " + id + ");",
				"delete from candidatoidioma where candidato_id in (select id from candidato where empresa_id = " + id + ");",
				"delete from candidato where empresa_id = " + id + ";",
				"delete from configuracaoimpressaocurriculo where empresa_id = " + id + ";",
				"delete from eleicao where estabelecimento_id in (select id from estabelecimento where empresa_id = " + id + ");",
				"update historicocolaborador set estabelecimento_id = null where estabelecimento_id in (select id from estabelecimento where empresa_id = " + id + ");",
				"delete from estabelecimento where empresa_id = " + id + ";",
				"delete from etapaseletiva where empresa_id = " + id + ";",
				"delete from usuarioempresa where empresa_id = " + id + ";",
				"delete from usuariomensagem where empresa_id = " + id + ";",
				"delete from historicoambiente where ambiente_id in (select id from ambiente where empresa_id = " + id + ");",
				"delete from historicocolaborador where ambiente_id in (select id from ambiente where empresa_id = " + id + ");",
				"delete from medicaorisco where ambiente_id in (select id from ambiente where empresa_id = " + id + ");",
				"delete from aspecto where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from participanteavaliacaodesempenho where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from configuracaocompetenciaavaliacaodesempenho where avaliacaodesempenho_id in (select id from avaliacaodesempenho where empresa_id = " + id + ");",
				"delete from avaliacaodesempenho where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from colaboradorquestionario where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from pergunta where avaliacao_id in (select id from avaliacao where empresa_id = " + id + ");",
				"delete from avaliacao where empresa_id = " + id + ";",
				"delete from historicobeneficio where beneficio_id in (select id from beneficio where empresa_id = " + id + ");",
				"delete from beneficio where empresa_id = " + id + ";",
				"delete from clinicaautorizada_exame where clinicaautorizada_id in (select id from clinicaautorizada where empresa_id = " + id + ");",
				"delete from examesolicitacaoexame where clinicaautorizada_id in (select id from clinicaautorizada where empresa_id = " + id + ");",
				"delete from clinicaautorizada where empresa_id = " + id + ";",
				"delete from cat where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorafastamento where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradoridioma where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorocorrencia where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from ocorrencia where empresa_id = " + id + ";",
				"delete from providencia where empresa_id = " + id + ";",
				"delete from historicoambiente_epc where epcs_id in (select id from epc where empresa_id = " + id + ");",
				"delete from epc where empresa_id = " + id + ";",
				"delete from configuracaonivelcompetencia where competencia_id in (select id from competencia where empresa_id = " + id + ");",
				"delete from composicaosesmt where empresa_id = " + id + ";",
				"delete from avaliacao where periodoexperiencia_id in (select id from periodoexperiencia where empresa_id = " + id + ");",
				"delete from colaboradorperiodoexperienciaavaliacao where periodoexperiencia_id in (select id from periodoexperiencia where empresa_id = " + id + ");",
				"delete from periodoexperiencia where empresa_id = " + id + ";",
				"delete from colaboradorquestionario where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id = " + id + "));",
				"delete from colaboradorturma where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id = " + id + "));",
				"delete from diaturma where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id = " + id + "));",
				"delete from turma_avaliacaoturma where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id = " + id + "));",
				"delete from turmatipodespesa where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id = " + id + "));",
				"delete from colaboradorquestionario where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaboradorturma where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from comissaoeleicao where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from comissaomembro where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from comissaoreuniaopresenca where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from dependente where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from experiencia where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from formacao where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from gastoempresa where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from gastoempresa where empresa_id = " + id + ";",
				"delete from historicocolaborador where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from historicocolaboradorbeneficio where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from prontuario where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from reajustecolaborador where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from solicitacaoepi where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from solicitacaoexame where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from solicitacaoexame where empresa_id = " + id + ";",
				"delete from colaboradorturma where curso_id in (select id from curso where empresa_id = " + id + ");",
				"delete from turmatipodespesa where turma_id in (select id from turma where empresa_id =  " + id + ");",
				"delete from turmatipodespesa where tipodespesa_id in (select id from tipodespesa where empresa_id =  " + id + ");",
				"delete from tipodespesa where empresa_id =  " + id + ";",
				"delete from colaboradorquestionario where turma_id in (select id from turma where empresa_id = " + id + ");",
				"delete from turma where curso_id in (select id from curso where empresa_id = " + id + ");",
				"delete from colaboradorturma where dnt_id in (select id from dnt where empresa_id = " + id + ");",
				"delete from dnt where empresa_id = " + id + ";",
				"delete from candidatoeleicao where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from comissao where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from comissaoeleicao where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from etapaprocessoeleitoral where eleicao_id in (select id from eleicao where empresa_id = " + id + ");",
				"delete from etapaprocessoeleitoral where empresa_id = " + id + ";",
				"delete from eleicao where empresa_id = " + id + ";",
				"delete from examesolicitacaoexame where exame_id in (select id from exame where empresa_id = " + id + ");",
				"delete from extintorinspecao where extintor_id in (select id from extintor where empresa_id = " + id + ");",
				"delete from extintormanutencao where extintor_id in (select id from extintor where empresa_id = " + id + ");",
				"delete from extintor where empresa_id = " + id + ";",
				"delete from gastoempresaitem where gasto_id in (select id from gasto where empresa_id = " + id + ");",
				"delete from gasto where grupogasto_id in (select id from grupogasto where empresa_id = " + id + ");",
				"delete from grupogasto where empresa_id = " + id + ";",
				"delete from gasto where empresa_id = " + id + ";",
				"delete from aspecto where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from avaliacaoturma where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from colaboradorquestionario where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from entrevista where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from fichamedica where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from pergunta where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from pesquisa where questionario_id in (select id from questionario where empresa_id = " + id + ");",
				"delete from questionario where empresa_id = " + id + ";",
				"delete from risco_epi where risco_id in (select id from risco where empresa_id = " + id + ");",
				"delete from riscoambiente where risco_id in (select id from risco where empresa_id = " + id + ");",
				"delete from riscomedicaorisco where risco_id in (select id from risco where empresa_id = " + id + ");",
				"delete from risco where empresa_id = " + id + ";",
				"delete from solicitacaoepi_item where solicitacaoepi_id in (select id from solicitacaoepi where empresa_id = " + id + ");",
				"delete from solicitacaoepi where empresa_id = " + id + ";",
				"delete from examesolicitacaoexame where solicitacaoexame_id in (select id from solicitacaoexame where empresa_id = " + id + ");",
				"delete from exame where empresa_id = " + id + ";",
				"delete from faturamentomensal where empresa_id = " + id + ";",
				"delete from empresabds where empresa_id = " + id + ";",
				"delete from engenheiroresponsavel where empresa_id = " + id + ";",
				"delete from reajustecolaborador where tabelareajustecolaborador_id in (select id from tabelareajustecolaborador where empresa_id = " + id + ");",
				"delete from tabelareajustecolaborador where empresa_id = " + id + ";",
				"delete from colaboradorturma where turma_id in (select id from turma where empresa_id = " + id + ");",
				"delete from diaturma where turma_id in (select id from turma where empresa_id = " + id + ");",
				"delete from confighistoriconivel where nivelcompetencia_id in (select id from  nivelcompetencia where empresa_id = " + id + ");",
				"delete from nivelcompetenciahistorico where empresa_id = " + id + ";",
				"delete from nivelcompetencia where empresa_id = " + id + ";",
				"delete from naturezalesao where empresa_id = " + id + ";",
				"delete from motivodemissao where empresa_id = " + id + ";",
				"delete from medicocoordenador where empresa_id = " + id + ";",
				"delete from obra where empresa_id = " + id + ";",
				"delete from fase where empresa_id = " + id + ";",
				"delete from medidaseguranca where empresa_id = " + id + ";",
				"delete from areavivencia where empresa_id = " + id + ";",
				"delete from certificacao_avaliacaopratica where avaliacoesPraticas_id in (select id from avaliacaoPratica where empresa_id = " + id + ");",
				"delete from colaboradorAvaliacaoPratica where avaliacaoPratica_id in ( select id from avaliacaoPratica where empresa_id = " + id + ");",
				"delete from avaliacaoPratica where empresa_id = " + id + ";",
				"delete from colaboradorCertificacao_colaboradorTurma where colaboradorCertificacao_id in (select id from colaboradorCertificacao where colaborador_id in (select id from colaborador where empresa_id = " + id + "));",
				"delete from colaboradorCertificacao where colaborador_id in (select id from colaborador where empresa_id = " + id + ");",
				"delete from colaborador where empresa_id = " + id + ";",
				"delete from empresa where id = " + id + ";"
		};
		
		JDBCConnection.executeQuery(sqls);
		
		acao = "ENABLE";
		JDBCConnection.executaTrigger(acao);
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
		p.add(Projections.property("e.controlarVencimentoCertificacaoPor"),"controlarVencimentoCertificacaoPor");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<Empresa> findEmpresasIntegradas()
	{
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("e.acIntegra", true));
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Empresa findEmailsEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		p.add(Projections.property("e.emailRemetente"),"emailRemetente");
		p.add(Projections.property("e.emailRespSetorPessoal"),"emailRespSetorPessoal");
		p.add(Projections.property("e.emailRespRH"),"emailRespRH");
		p.add(Projections.property("e.logoUrl"),"logoUrl");
		
		criteria.add(Expression.eq("e.id", empresaId));
		criteria.setProjection(Projections.distinct(p));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (Empresa) criteria.uniqueResult();
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
	
	public boolean checkEmpresaIntegradaAc(Long empresaId) 
	{
		Query query = getSession().createQuery("select e.acIntegra from Empresa e where e.acIntegra = true and e.id=:empresaId ");
		query.setLong("empresaId", empresaId);
		return !query.list().isEmpty();
	}

	public Collection<Empresa> findComCodigoAC() {
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		p.add(Projections.property("e.codigoAC"),"codigoAC");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.isNotNull("e.codigoAC"));
		criteria.add(Expression.ne("e.codigoAC", ""));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	}

	public boolean isControlaRiscoPorAmbiente(Long empresaId) 
	{
		Query query = getSession().createQuery("select e.controlaRiscoPor from Empresa e where e.id = :empresaId");
		query.setLong("empresaId", empresaId);
		
		return (Character) query.uniqueResult() == 'A';
	}

	public Empresa getCnae(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.cnae"), "cnae");
		p.add(Projections.property("e.cnae2"),"cnae2");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("e.id", empresaId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (Empresa) criteria.uniqueResult();
	}
	
	public void updateCodigoAC(Long empresaId, String codigoAC, String grupoAC) 
	{
		String hql = "update Empresa e set e.codigoAC = :codigoAC, e.grupoAC = :grupoAC where e.id = :empresaId";
		Query query = getSession().createQuery(hql);
		query.setString("codigoAC", codigoAC);
		query.setString("grupoAC", grupoAC);
		query.setLong("empresaId", empresaId);

		query.executeUpdate();
	}

	public Collection<Empresa> findByGruposAC(String... gruposAC) 
	{
		String hql = "select emp from Empresa as emp where emp.grupoAC in (:grupoAC) ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("grupoAC", gruposAC, Hibernate.STRING);
		
		return query.list();
	}
	
	public String getCodigoGrupoAC(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.grupoAC"), "grupoAC");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("e.id", empresaId));
		
		return (String) criteria.uniqueResult();
	}

	public boolean emProcessoExportacaoAC(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.processoExportacaoAC"), "processoExportacaoAC");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("e.id", empresaId));
		
		if(criteria.uniqueResult() == null)
			return false;
		
		return (Boolean) criteria.uniqueResult();
	}

	public void setProcessoExportacaoAC(Long empresaId, boolean processoExportacaoAC) 
	{
		String hql = "update Empresa e set e.processoExportacaoAC = :processoExportacaoAC where e.id = :empresaId";
		Query query = getSession().createQuery(hql);
		query.setBoolean("processoExportacaoAC", processoExportacaoAC);
		query.setLong("empresaId", empresaId);

		query.executeUpdate();
	}
}