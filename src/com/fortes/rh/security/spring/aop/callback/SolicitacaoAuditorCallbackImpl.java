package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.util.SpringUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class SolicitacaoAuditorCallbackImpl implements AuditorCallback {
	
	private static final String VIRGULA_ESPACO = ", ";

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable {
		Solicitacao solicitacao = (Solicitacao) metodo.getParametros()[0];
		String[] emailsMarcados = (String[]) metodo.getParametros()[1];
		Long[] avaliacoesIds = (Long[]) metodo.getParametros()[2];
		metodo.processa();		
		
		Map<String, String> dadosAtualizados = new LinkedHashMap<String, String>();
		dadosAtualizados = geraDadosAtualizados(solicitacao, avaliacoesIds, emailsMarcados);
		
		String dados = new DadosAuditados(null, dadosAtualizados).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), solicitacao.getDescricao(), dados);
	}
	
	public Auditavel updateStatusSolicitacao(MetodoInterceptado metodo) throws Throwable {
		
		Solicitacao solicitacao = (Solicitacao) metodo.getParametros()[0];
		Solicitacao solicitacaoAnterior = (Solicitacao) carregaEntidade(metodo, solicitacao);
		metodo.processa();
		
		Map<String, String> dadosAnteriores = new LinkedHashMap<String, String>();
		dadosAnteriores.put("status", solicitacaoAnterior.getStatusFormatado());
		dadosAnteriores.put("dataStatus", solicitacaoAnterior.getDataStatusFormatada());

		if(solicitacaoAnterior.getLiberador().getId() != null)
			dadosAnteriores.put("liberadorId", solicitacaoAnterior.getLiberador().getId().toString());
		else
			dadosAnteriores.put("liberadorId", "");

		dadosAnteriores.put("liberadorNome", solicitacaoAnterior.getLiberador().getNome());
		dadosAnteriores.put("liberadorObservacao", solicitacaoAnterior.getObservacaoLiberador());

		Map<String, String> dadosAtualizados = new LinkedHashMap<String, String>();
		dadosAtualizados.put("status", String.valueOf(solicitacao.getStatusFormatado()));
		dadosAtualizados.put("dataStatus", String.valueOf(solicitacao.getDataStatusFormatada()));
		dadosAtualizados.put("liberadorId", solicitacao.getLiberador().getId().toString());
		dadosAtualizados.put("liberadorNome", solicitacao.getLiberador().getNome());
		dadosAtualizados.put("liberadorObservacao", solicitacao.getObservacaoLiberador());
		
		String dados = new DadosAuditados(new Object[]{dadosAnteriores}, dadosAtualizados).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), solicitacaoAnterior.getDescricaoFormatada(), dados);
	}
	
	public Auditavel encerraSolicitacao(MetodoInterceptado metodo) throws Throwable {
		return montaDadosEncerrarSolicitacao(metodo);
	}
	
	public Auditavel encerrarSolicitacaoAoPreencherTotalVagas(MetodoInterceptado metodo) throws Throwable {
		return montaDadosEncerrarSolicitacao(metodo);
	}
	
	private Auditavel montaDadosEncerrarSolicitacao(MetodoInterceptado metodo) throws Throwable {
		Solicitacao solicitacao = (Solicitacao) metodo.getParametros()[0];
		Solicitacao solicitacaoAnterior = (Solicitacao) carregaEntidade(metodo, solicitacao);
		metodo.processa();
		
		Map<String, String> dadosAtualizados = new LinkedHashMap<String, String>();
		dadosAtualizados.put("dataEncerramento", String.valueOf(solicitacao.getDataEncerramentoFormatada()));
		dadosAtualizados.put("observacao", String.valueOf(solicitacao.getObservacaoLiberador()));
		
		String dados = new DadosAuditados(new Object[]{}, dadosAtualizados).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), solicitacaoAnterior.getDescricaoFormatada(), dados);
	}
	
	public Auditavel updateSolicitacao(MetodoInterceptado metodo) throws Throwable {

		Solicitacao solicitacao = (Solicitacao) metodo.getParametros()[0];
		Solicitacao solicitacaoAnterior = (Solicitacao) carregaEntidade(metodo, solicitacao);

		SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager = (SolicitacaoAvaliacaoManager) SpringUtil.getBean("solicitacaoAvaliacaoManager");
		Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos =  solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacaoAnterior.getId(), null);
		
		Long[] avaliacoesIds = (Long[]) metodo.getParametros()[1];
		
		BairroManager bairroManager = (BairroManager) SpringUtil.getBean("bairroManager");
		Collection<Bairro> bairros = bairroManager.getBairrosBySolicitacao(solicitacaoAnterior.getId());
		
		metodo.processa();
		
		Map<String, String> dadosAnteriores = new LinkedHashMap<String, String>();
		dadosAnteriores.put("data", solicitacaoAnterior.getDataFormatada());
		dadosAnteriores.put("descricao", solicitacaoAnterior.getDescricao());
		dadosAnteriores.put("horarioComercial", solicitacaoAnterior.getHorarioComercial());
		dadosAnteriores.put("estabelecimentoId", solicitacaoAnterior.getEstabelecimento().getId().toString());
		dadosAnteriores.put("estabelecimentoNome", solicitacaoAnterior.getEstabelecimento().getNome());
		dadosAnteriores.put("areaOrganizacionalId", solicitacaoAnterior.getAreaOrganizacional().getId().toString());
		dadosAnteriores.put("areaOrganizacionalNome", solicitacaoAnterior.getAreaOrganizacional().getNome());
		dadosAnteriores.put("ambienteId", solicitacaoAnterior.getAmbiente() != null && solicitacaoAnterior.getAmbiente().getId() != null ? solicitacaoAnterior.getAmbiente().getId().toString() : "");
		dadosAnteriores.put("ambienteNome", solicitacaoAnterior.getAmbiente() != null && solicitacaoAnterior.getAmbiente().getNome() != null? solicitacaoAnterior.getAmbiente().getNome() : "");
		dadosAnteriores.put("faixaSalarialId", solicitacaoAnterior.getFaixaSalarial().getId().toString());
		dadosAnteriores.put("cargo/faixaSalarialNome", solicitacaoAnterior.getFaixaSalarial().getNome());
		dadosAnteriores.put("funcaoId", solicitacaoAnterior.getFuncao() != null && solicitacaoAnterior.getFuncao().getId() != null ? solicitacaoAnterior.getFuncao().getId().toString() : "");
		dadosAnteriores.put("funcaoNome", solicitacaoAnterior.getFuncao() != null && solicitacaoAnterior.getFuncao().getNome() != null ? solicitacaoAnterior.getFuncao().getNome() : "");
		
		if (solicitacaoAvaliacaos != null && !solicitacaoAvaliacaos.isEmpty()) {
			AvaliacaoManager avaliacaoManager = (AvaliacaoManager) SpringUtil.getBean("avaliacaoManager");
			StringBuilder avaliacoes = new StringBuilder();
			for (SolicitacaoAvaliacao solicitacaoAvaliacao : solicitacaoAvaliacaos) {
				avaliacoes.append(avaliacaoManager.findById(solicitacaoAvaliacao.getAvaliacao().getId()).getTitulo());
				avaliacoes.append(VIRGULA_ESPACO);
			}
			avaliacoes.delete(avaliacoes.length()-2, avaliacoes.length());
			dadosAnteriores.put("avaliacoes", avaliacoes.toString());
		}
		
		dadosAnteriores.put("quantidade", String.valueOf(solicitacaoAnterior.getQuantidade()));
		dadosAnteriores.put("motivoSolicitacaoId", solicitacaoAnterior.getMotivoSolicitacao().getId().toString());
		dadosAnteriores.put("motivoSolicitacaoDescricao", solicitacaoAnterior.getMotivoSolicitacao().getDescricao());
		dadosAnteriores.put("vinculo", solicitacaoAnterior.getVinculoDescricao());
		dadosAnteriores.put("remuneracao", solicitacaoAnterior.getRemuneracao() != null ? solicitacaoAnterior.getRemuneracaoFormatada() : "");
		dadosAnteriores.put("escolaridade", solicitacaoAnterior.getEscolaridade() != null ? solicitacaoAnterior.getEscolaridade() : "");
		dadosAnteriores.put("sexo", solicitacaoAnterior.getSexo());
		dadosAnteriores.put("idadeMinima", solicitacaoAnterior.getIdadeMinimaDesc() != null ? solicitacaoAnterior.getIdadeMinimaDesc() : "");
		dadosAnteriores.put("idadeMaxima", solicitacaoAnterior.getIdadeMaximaDesc() != null ? solicitacaoAnterior.getIdadeMaximaDesc() : "");
		dadosAnteriores.put("estado", solicitacaoAnterior.getCidade() != null && solicitacaoAnterior.getCidade().getUf() != null && solicitacaoAnterior.getCidade().getUf().getSigla() != null ? solicitacaoAnterior.getCidade().getUf().getSigla() : "");
		dadosAnteriores.put("cidade", solicitacaoAnterior.getCidade() != null && solicitacaoAnterior.getCidade().getNome() != null ? solicitacaoAnterior.getCidade().getNome() : "");
		
		if (bairros != null && !bairros.isEmpty()) {
			StringBuilder bairrosStr = new StringBuilder();
			for (Bairro bairro : bairros) {
				if (bairro.getId() != null ) {
					bairrosStr.append(bairroManager.findById(bairro.getId()).getNome());
					bairrosStr.append(VIRGULA_ESPACO);
				}
			}
			if (bairrosStr.length() > 0) {
				bairrosStr.delete(bairrosStr.length()-2, bairrosStr.length());
				dadosAnteriores.put("bairros", bairrosStr.toString());
			}
		}
		
		dadosAnteriores.put("infoComplementares", solicitacaoAnterior.getInfoComplementares() != null ? solicitacaoAnterior.getInfoComplementares() : "");
		dadosAnteriores.put("status", solicitacaoAnterior.getStatusFormatado());
		dadosAnteriores.put("dataStatus", solicitacaoAnterior.getDataStatusFormatada());
		dadosAnteriores.put("liberadorId", solicitacaoAnterior.getLiberador() != null && solicitacaoAnterior.getLiberador().getId() != null ? solicitacaoAnterior.getLiberador().getId().toString() : "");
		dadosAnteriores.put("liberadorNome", solicitacaoAnterior.getLiberador() != null && solicitacaoAnterior.getLiberador().getNome() != null ? solicitacaoAnterior.getLiberador().getNome() : "");
		dadosAnteriores.put("liberadorObservacao", solicitacaoAnterior.getObservacaoLiberador() != null ? solicitacaoAnterior.getObservacaoLiberador() : "");
		dadosAnteriores.put("Invisível para o gestor da área organizacional", solicitacaoAnterior.isInvisivelParaGestor()? "Sim" : "Não");

		//Dados atualizados 
		Map<String, String> dadosAtualizados = new LinkedHashMap<String, String>();
		dadosAtualizados = geraDadosAtualizados(solicitacao, avaliacoesIds, null);
		
		String dados = new DadosAuditados(new Object[]{dadosAnteriores}, dadosAtualizados).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), solicitacao.getDescricaoFormatada(), dados);
	}
	
	private Solicitacao carregaEntidade(MetodoInterceptado metodo, Solicitacao solicitacao) {
		SolicitacaoManager manager = (SolicitacaoManager) metodo.getComponente();
		return manager.findByIdProjectionForUpdate(solicitacao.getId());
	}
	
	private Map<String, String> geraDadosAtualizados(Solicitacao solicitacao, Long[] avaliacoesIds, String[] emailsMarcados) {
		Map<String, String> dadosAtualizados = new LinkedHashMap<String, String>();
		dadosAtualizados.put("data", solicitacao.getDataFormatada());
		dadosAtualizados.put("descricao", solicitacao.getDescricao());
		dadosAtualizados.put("horarioComercial", solicitacao.getHorarioComercial());

		dadosAtualizados.put("estabelecimentoId", solicitacao.getEstabelecimento().getId().toString());
		EstabelecimentoManager estabelecimentoManager = (EstabelecimentoManager) SpringUtil.getBean("estabelecimentoManager");
		dadosAtualizados.put("estabelecimentoNome", estabelecimentoManager.findById(solicitacao.getEstabelecimento().getId()).getNome());
		
		dadosAtualizados.put("areaOrganizacionalId", solicitacao.getAreaOrganizacional().getId().toString());
		AreaOrganizacionalManager areaOrganizacionalManager = (AreaOrganizacionalManager) SpringUtil.getBean("areaOrganizacionalManager");
		dadosAtualizados.put("areaOrganizacionalNome", areaOrganizacionalManager.findById(solicitacao.getAreaOrganizacional().getId()).getNome());
		
		dadosAtualizados.put("ambienteId", solicitacao.getAmbiente() != null && solicitacao.getAmbiente().getId() != null ? solicitacao.getAmbiente().getId().toString() : "");
		String ambienteNome = "";
		if (solicitacao.getAmbiente() != null && solicitacao.getAmbiente().getId() != null) {
			AmbienteManager ambienteManager = (AmbienteManager) SpringUtil.getBean("ambienteManager");
			ambienteNome = ambienteManager.findById(solicitacao.getAmbiente().getId()).getNome();
		}
		dadosAtualizados.put("ambienteNome", ambienteNome);

		dadosAtualizados.put("faixaSalarialId", solicitacao.getFaixaSalarial().getId().toString());
		FaixaSalarialManager faixaSalarialManager = (FaixaSalarialManager) SpringUtil.getBean("faixaSalarialManager");
		dadosAtualizados.put("cargo/faixaSalarialNome", faixaSalarialManager.findByFaixaSalarialId(solicitacao.getFaixaSalarial().getId()).getNomeDeCargoEFaixa());
		
		dadosAtualizados.put("funcaoId", solicitacao.getFuncao() != null && solicitacao.getFuncao().getId() != null ? solicitacao.getFuncao().getId().toString() : "");
		String funcaoNome = "";
		if (solicitacao.getFuncao() != null && solicitacao.getFuncao().getId() != null) {
			FuncaoManager funcaoManager = (FuncaoManager) SpringUtil.getBean("funcaoManager");
			funcaoNome = funcaoManager.findById(solicitacao.getFuncao().getId()).getNome();
		}
		dadosAtualizados.put("funcaoNome", funcaoNome);
		
		AvaliacaoManager avaliacaoManager = (AvaliacaoManager) SpringUtil.getBean("avaliacaoManager");
		if (avaliacoesIds != null && avaliacoesIds.length > 0) {
			List<Avaliacao> avaliacaos = (List<Avaliacao>) avaliacaoManager.findById(avaliacoesIds);
			StringBuilder avaliacoes = new StringBuilder();
			for (Avaliacao avaliacao : avaliacaos) {
				avaliacoes.append(avaliacao.getTitulo());
				avaliacoes.append(VIRGULA_ESPACO);
			}
			avaliacoes.delete(avaliacoes.length()-2, avaliacoes.length());
			dadosAtualizados.put("avaliacoes", avaliacoes.toString());
		}
		
		dadosAtualizados.put("quantidade", String.valueOf(solicitacao.getQuantidade()));
		
		dadosAtualizados.put("motivoSolicitacaoId", solicitacao.getMotivoSolicitacao().getId().toString());
		MotivoSolicitacaoManager motivoSolicitacaoManager = (MotivoSolicitacaoManager) SpringUtil.getBean("motivoSolicitacaoManager");
		dadosAtualizados.put("motivoSolicitacaoDescricao", motivoSolicitacaoManager.findById(solicitacao.getMotivoSolicitacao().getId()).getDescricao());
		
		if (emailsMarcados != null && emailsMarcados.length > 0) {
			StringBuilder emailsMarcadosStr = new StringBuilder();
			for (String email : emailsMarcados) {
				emailsMarcadosStr.append(email);
				emailsMarcadosStr.append(VIRGULA_ESPACO);
			}
			emailsMarcadosStr.delete(emailsMarcadosStr.length()-2, emailsMarcadosStr.length());
			dadosAtualizados.put("emails", emailsMarcadosStr.toString());
		}
		
		dadosAtualizados.put("vinculo", solicitacao.getVinculoDescricao());
		dadosAtualizados.put("remuneracao", solicitacao.getRemuneracao() != null ? solicitacao.getRemuneracaoFormatada() : "");
		dadosAtualizados.put("escolaridade", solicitacao.getEscolaridade() != null ? solicitacao.getEscolaridade() : "");
		dadosAtualizados.put("sexo", solicitacao.getSexo());
		dadosAtualizados.put("idadeMinima", solicitacao.getIdadeMinimaDesc() != null ? solicitacao.getIdadeMinimaDesc() : "");
		dadosAtualizados.put("idadeMaxima", solicitacao.getIdadeMaximaDesc() != null ? solicitacao.getIdadeMaximaDesc() : "");
		
		CidadeManager cidadeManager = (CidadeManager) SpringUtil.getBean("cidadeManager");
		String estadoSigla = "";
		String cidadeNome = "";
		if (solicitacao.getCidade() != null && solicitacao.getCidade().getId() != null) {
			Cidade cidade = cidadeManager.findByIdProjection(solicitacao.getCidade().getId());
			cidadeNome = cidade.getNome();
			estadoSigla = cidade.getUf() != null ? cidade.getUf().getSigla() : ""; 
		}
		dadosAtualizados.put("estado", estadoSigla);
		dadosAtualizados.put("cidade", cidadeNome);
		
		Collection<Bairro> bairros = solicitacao.getBairros();
		if (bairros != null && !bairros.isEmpty()) {
			BairroManager bairroManager = (BairroManager) SpringUtil.getBean("bairroManager");
			StringBuilder bairrosStr = new StringBuilder();
			for (Bairro bairro : bairros) {
				bairrosStr.append(bairroManager.findById(bairro.getId()).getNome());
				bairrosStr.append(VIRGULA_ESPACO);
			}
			bairrosStr.delete(bairrosStr.length()-2, bairrosStr.length());
			dadosAtualizados.put("bairros", bairrosStr.toString());
		}
		
		dadosAtualizados.put("infoComplementares", solicitacao.getInfoComplementares() != null ? solicitacao.getInfoComplementares() : "");
		dadosAtualizados.put("status", solicitacao.getStatusFormatado());
		dadosAtualizados.put("dataStatus", solicitacao.getDataStatusFormatada());
		dadosAtualizados.put("liberadorId", solicitacao.getLiberador() != null && solicitacao.getLiberador().getId() != null ? solicitacao.getLiberador().getId().toString() : "");
		dadosAtualizados.put("liberadorNome", solicitacao.getLiberador() != null && solicitacao.getLiberador().getNome() != null ? solicitacao.getLiberador().getNome() : "");
		dadosAtualizados.put("liberadorObservacao", solicitacao.getObservacaoLiberador() != null ? solicitacao.getObservacaoLiberador() : "");
		dadosAtualizados.put("Invisível para o gestor da área organizacional", solicitacao.isInvisivelParaGestor()? "Sim" : "Não");
		
		return dadosAtualizados;
	}
}
