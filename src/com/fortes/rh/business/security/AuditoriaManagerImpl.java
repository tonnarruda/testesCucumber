package com.fortes.rh.business.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManager;
import com.fortes.business.GenericManagerImpl;
import com.fortes.model.AbstractModel;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.model.ws.TAuditoria;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidade;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.xwork.ActionContext;

public class AuditoriaManagerImpl extends GenericManagerImpl<Auditoria, AuditoriaDao> implements AuditoriaManager
{
	
	public Map findEntidade(Long empresaId)
	{
		return getDao().findEntidade(empresaId);
	}

	public Auditoria projectionFindById(Long id, Long empresaId)
	{
		return getDao().projectionFindById(id, empresaId);
	}

	public Integer getCount(Map parametros, Long empresaId)
	{
		return getDao().getCount(parametros, empresaId);
	}

	public Collection<Auditoria> list(int page, int pagingSize, Map parametros, Long empresaId)
	{
		return getDao().list(page, pagingSize, parametros, empresaId);
	}
	/* (non-Javadoc)
	 * @see com.fortes.rh.business.security.AuditoriaManager#audita(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void audita(String recurso, String operacao, String chave, String dados) {
		
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		Empresa empresa = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession());
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(usuarioLogado, empresa, recurso, operacao, chave, dados);
		
		this.getDao().save(auditoria);
	}

	public List<String> findOperacoesPeloModulo(String modulo) {
		return getDao().findOperacoesPeloModulo(modulo);
	}

	@SuppressWarnings("unchecked")
	public String getDetalhes(String dados) 
	{
		String entidade;
		String id;
		dados.replace("aaVEGA", "");
		while (dados.contains("\"chaveParaAuditoria\": \"\""))
		{
			String descricaoChave = " ";
//			Pattern patternEntidade = Pattern.compile("  \"(.+?)\":   [\\[|\\{]\\s*\"id\": (\\d+),\\s*\"chaveParaAuditoria\": \"\"", Pattern.DOTALL);
//			Pattern patternEntidade = Pattern.compile(".\"(.+)\":   \\{.*\n.*\"id\": (\\d+),.*\n.*\"chaveParaAuditoria\": \"\"");
			Pattern patternEntidade = Pattern.compile("  \"(.+?)\":   \\[.*?\"id\": (\\d+),\\s*\"chaveParaAuditoria\": \"\"", Pattern.DOTALL);
//			Pattern patternEntidade = Pattern.compile(".\"(.+)\":   \\{.*\n.*\"id\": (\\d+),.*\n.*\"chaveParaAuditoria\": \"\"");
//			Pattern patternEntidade = Pattern.compile(".\"(.+)\":   \\{.*\n.*\"id\": (\\d+),.*\n.*\"chaveParaAuditoria\": \"\"");
			Matcher matcherEntidade = patternEntidade.matcher(dados);
			
			if(matcherEntidade.find())
			{
				try {
					entidade = matcherEntidade.group(1);
					id = matcherEntidade.group(2);
					descricaoChave = findDescricaoChave(entidade, id);					
				} catch (Exception e) {}
				
				dados = dados.replaceFirst("\"chaveParaAuditoria\": \"\"", "\"chaveParaAuditoria\": \"" + descricaoChave +"\"");
			}

		}
		

//		for (String linha : linhas) 
//		{
//			Matcher matcherEntidade = patternEntidade.matcher(linha);
//			if(matcherEntidade.find())
//			{
//				System.out.println("#############");
//				System.out.println(matcherEntidade.group(1));
//				System.out.println("@@@@@@@@@@@@@");
//				Matcher matcherId = patternId.matcher(linha);
//				if(matcherId.find())
//				{
//					System.out.println(matcherId.group(1));
//					System.out.println("$$$$$$$$$$$$$");					
//				}
//			}
//			System.out.println(linha);
//		}
		return dados;
//		JSONObject json = JSONObject.fromObject(dados.replace("[DADOS ATUALIZADOS]", "").replace("[DADOS ANTERIORES]", "").replaceAll("\"", "\\\""));
//
//		AbstractModel entidade;
//		String chave;
//		String chaveParaAuditoria;
//		
//		//TODO fazer dicionario static
//		Map<String, String> normalizaEntidade = new HashMap<String, String>();
//		normalizaEntidade.put("areaOrganizacionals", "areaOrganizacional");
//		normalizaEntidade.put("faixaSalarials", "faixaSalarial");
//		normalizaEntidade.put("etapaProcessoEleitorals", "etapaProcessoEleitoral");
//		
//		for(Object no : json.keySet())
//		{
//			chave = (String) no;
//			try 
//			{
//				if(json.getJSONObject(chave).containsKey("id") && json.getJSONObject(chave).containsKey("chaveParaAuditoria"))
//				{
//					String bean = normalizaEntidade.containsKey(chave) ? normalizaEntidade.get(chave) : chave ;
//					
//					GenericManager<AbstractModel> manager = (GenericManager<AbstractModel>) SpringUtil.getBean(bean + "Manager");
//					entidade = (AbstractModel) manager.findEntidadeComAtributosSimplesById(json.getJSONObject(chave).getLong("id"));
//					ProcuraChaveNaEntidade chaveNaEntidade = new ProcuraChaveNaEntidade(entidade);
//					chaveParaAuditoria = chaveNaEntidade.procura();
//					
//					json.getJSONObject(chave).element("chaveParaAuditoria", chaveParaAuditoria);
//				}				
//			} catch (Exception e) {}				
//		}
//		
//		return json.toString(); 
	}

	@SuppressWarnings("unchecked")
	private String findDescricaoChave(String chave, String id) 
	{
		AbstractModel entidade;
		//TODO fazer dicionario static
		Map<String, String> normalizaEntidade = new HashMap<String, String>();
		normalizaEntidade.put("areaOrganizacionals", "areaOrganizacional");
		normalizaEntidade.put("faixaSalarials", "faixaSalarial");
		normalizaEntidade.put("etapaProcessoEleitorals", "etapaProcessoEleitoral");
		
		String bean = normalizaEntidade.containsKey(chave) ? normalizaEntidade.get(chave) : chave ;

		GenericManager<AbstractModel> manager = (GenericManager<AbstractModel>) SpringUtil.getBean(bean + "Manager");
		entidade = (AbstractModel) manager.findEntidadeComAtributosSimplesById(Long.parseLong(id));
		ProcuraChaveNaEntidade chaveNaEntidade = new ProcuraChaveNaEntidade(entidade);
		String chaveParaAuditoria = chaveNaEntidade.procura();
		
		if(StringUtils.isEmpty(chaveParaAuditoria))
			return " ";
		else
			return chaveParaAuditoria;
	}

	public void auditaCancelarContratacaoNoAC(Colaborador colaborador, String mensagem) 
	{
		Map<String, Object> cancelamentoContratacaoAC = new LinkedHashMap<String, Object>();
		cancelamentoContratacaoAC.put("Colaborador", colaborador.getNome());
		cancelamentoContratacaoAC.put("Mensagem", "Contratação cancelada no Fortes Pessoal. Obs: "+mensagem);
		
		String dados = new GeraDadosAuditados(null, cancelamentoContratacaoAC).gera();
		Empresa empresa = colaborador.getEmpresa();
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Cancel. Contrat.AC", colaborador.getNome(), dados);
		
		this.getDao().save(auditoria);
	}
	
	public void auditaConfirmacaoDesligamentoNoAC(Collection<Colaborador> colaboradores,Date dataDesligamento, Empresa empresa) 
	{
		Map<String, Object> desligamentoContratacaoAC = new LinkedHashMap<String, Object>();
		desligamentoContratacaoAC.put("Data desligamento:", DateUtil.formataDiaMesAno(dataDesligamento));
		desligamentoContratacaoAC.put("Colaborador(es)", "");
		
		for (Colaborador colaborador : colaboradores) 
			desligamentoContratacaoAC.put(colaborador.getCodigoAC(), colaborador.getNome());
		
		String dados = new GeraDadosAuditados(null, desligamentoContratacaoAC).gera();
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Desligamento no AC", "", dados);
		
		this.getDao().save(auditoria);
	}
	
	public void auditaCancelamentoSolicitacoNoAC(Colaborador colaborador,String mensagem) 
	{
		Map<String, Object> cancelamentoSolicitacaoAC = new LinkedHashMap<String, Object>();
		cancelamentoSolicitacaoAC.put("Colaborador", colaborador.getNome());
		cancelamentoSolicitacaoAC.put("Mensagem", "Cancelamento da solicitação de desligamento no Fortes Pessoal. Obs: "+mensagem);
		
		String dados = new GeraDadosAuditados(null, cancelamentoSolicitacaoAC).gera();
		Empresa empresa = colaborador.getEmpresa();
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Cancel.solicitação desligamento", colaborador.getNome(), dados);
		
		this.getDao().save(auditoria);
	}
	
	public void auditaRemoverEnpregadoFortesPessoal(Empresa empresa, TAuditoria tAuditoria, Colaborador colaborador) 
	{
		Map<String, Object> cancelamentoSolicitacaoAC = new LinkedHashMap<String, Object>();
		cancelamentoSolicitacaoAC.put("Colaborador", colaborador.getNome());
		cancelamentoSolicitacaoAC.put("Usuário Fortes Pessoal", tAuditoria.getUsuario());
		cancelamentoSolicitacaoAC.put("Módulo Fortes Pessoal", tAuditoria.getModulo());
		cancelamentoSolicitacaoAC.put("Operação Fortes Pessoal", tAuditoria.getOperacao());
		cancelamentoSolicitacaoAC.put("Mensagem", "Colaborador Removido através do Fortes Pessoal.");
		
		String dados = new GeraDadosAuditados(null, cancelamentoSolicitacaoAC).gera();

		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Remoção atravez do Fortes Pessoal.", colaborador.getNome(), dados);
		
		this.getDao().save(auditoria);
	}
}