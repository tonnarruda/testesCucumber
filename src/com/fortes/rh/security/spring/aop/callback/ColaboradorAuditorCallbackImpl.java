package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class ColaboradorAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel insert(MetodoInterceptado metodo) throws Throwable 
	{
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{colaborador}, colaborador).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		Colaborador colaboradorAnterior = (Colaborador) carregaEntidade(metodo, colaborador);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{colaboradorAnterior}, colaborador).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		colaborador = carregaEntidade(metodo, colaborador);
		
		String dados = new DadosAuditados(new Object[]{colaborador}, null).gera();

		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel removeComDependencias(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = new Colaborador("", (Long) metodo.getParametros()[0]);
		colaborador = carregaEntidade(metodo, colaborador);
		
		String dados = new DadosAuditados(new Object[]{colaborador}, null).gera();

		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}

	public Auditavel desligaColaborador(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();
		Map<String, Object> desligamento;
		Collection<Map<String, Object>> desligamentos = new ArrayList<Map<String,Object>>();
		String colaboradoresDesligados = "";
		Long motivoDemissaoId = (Long) metodo.getParametros()[3];
		
		Long[] colaboradorIds = (Long[]) metodo.getParametros()[7];
		Colaborador colab;
		
		for (Long colaboradorId : colaboradorIds) 
		{
			colab = new Colaborador();
			colab.setId(colaboradorId);
			Colaborador colaborador = carregaEntidade(metodo, colab);
			
			desligamento = new LinkedHashMap<String, Object>();
			desligamento.put("Colaborador ID", colaborador.getId());
			desligamento.put("Colaborador", colaborador.getNome());
			desligamento.put("Data de desligamento", colaborador.getDataDesligamento());
			desligamento.put("Motivo de demissao ID", motivoDemissaoId);
			desligamento.put("Gera substituição", colaborador.getDescricaoDemissaoGerouSubstituicao());
			desligamento.put("Observacao", colaborador.getObservacaoDemissao());
			desligamentos.add(desligamento);
			
			colaboradoresDesligados += colaborador.getNome() + "  ";
		}

		String dados = new DadosAuditados(null, desligamentos.toArray()).gera();
			
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaboradoresDesligados, dados);
	}
	
	public Auditavel solicitacaoDesligamento(MetodoInterceptado metodo) throws Throwable{
		metodo.processa();
		
		Map<String, Object> desligamento;
		
		Collection<Map<String, Object>> solicitacaoDesligamento = new ArrayList<Map<String,Object>>();
		Date dataSolicitacao = (Date) metodo.getParametros()[0];
		String observacaoDemissao = (String) metodo.getParametros()[1];
		Long motivoDemissaoId = (Long) metodo.getParametros()[2];
		Character gerouSubstituicao = (Character) metodo.getParametros()[3];
				
		Long colaboradorId = (Long) metodo.getParametros()[5];
		Colaborador colab = new Colaborador();
		colab.setId(colaboradorId);
		Colaborador colaborador = carregaEntidade(metodo, colab);
		
		desligamento = inicializaDesligamento(dataSolicitacao, observacaoDemissao, motivoDemissaoId, gerouSubstituicao, colaborador);

		Long solicitanteDemissaoId = (Long) metodo.getParametros()[4];
		if (solicitanteDemissaoId != null) {
			Colaborador solicitDemissao;
			solicitDemissao = new Colaborador();
			solicitDemissao.setId(solicitanteDemissaoId);
			Colaborador solicitanteDemissao = carregaEntidade(metodo, solicitDemissao); 
			desligamento.put("Solicitante Id", solicitanteDemissao.getId());
			desligamento.put("Solicitante", solicitanteDemissao.getNome());
		}
		
		solicitacaoDesligamento.add(desligamento);
		
		String dados = new DadosAuditados(null, solicitacaoDesligamento.toArray()).gera();
			
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel reprovaSolicitacaoDesligamento(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();
		Map<String, Object> desligamento;
		Collection<Map<String, Object>> desligamentos = new ArrayList<Map<String,Object>>();
		
		Long colaboradorId = (Long) metodo.getParametros()[0];
		Colaborador colab = new Colaborador();
		colab.setId(colaboradorId);
		Colaborador colaborador = carregaEntidade(metodo, colab);
		
		desligamento = new LinkedHashMap<String, Object>();
		desligamento.put("Colaborador ID", colaborador.getId());
		desligamento.put("Colaborador", colaborador.getNome());
		desligamento.put("Data de cancelamento", new Date());
		desligamentos.add(desligamento);
		
		String dados = new DadosAuditados(null, desligamentos.toArray()).gera();
			
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}

	public Auditavel religaColaborador(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();

		Long colaboradorId = (Long) metodo.getParametros()[0];
		Colaborador colab = new Colaborador();
		colab.setId(colaboradorId);
		Colaborador colaborador = carregaEntidade(metodo, colab);
		
		Map<String, Object> dadosColab = new LinkedHashMap<String, Object>();
		dadosColab.put("Colaborador ID", colaborador.getId());
		dadosColab.put("Colaborador", colaborador.getNome());
		
		String dados = new DadosAuditados(null, dadosColab).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel desligaColaboradorAC(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();
		Map<String, Object> desligamento;
		Collection<Map<String, Object>> desligamentos = new ArrayList<Map<String,Object>>();
		String colabCodigoAcsDeslidos = "";
		Empresa empresa = (Empresa) metodo.getParametros()[0];
		Date dataDesligamento = (Date) metodo.getParametros()[1];
		String[] colaboradorCodigoAcs = (String[]) metodo.getParametros()[2];
		
		for (String colabCodigoAC : colaboradorCodigoAcs) 
		{
			desligamento = new LinkedHashMap<String, Object>();
			desligamento.put("Empresa codigoAC", empresa.getCodigoAC());
			desligamento.put("Colaborador codigoAC", colabCodigoAC);
			desligamento.put("Data Desligamento", dataDesligamento);
			desligamentos.add(desligamento);
			
			colabCodigoAcsDeslidos += colabCodigoAC + "  ";
		}

		String dados = new DadosAuditados(null, desligamentos.toArray()).gera();
			
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), empresa.getCodigoAC() + " " + colabCodigoAcsDeslidos, dados);
	}

	public Auditavel solicitacaoDesligamentoAc(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();

		Map<String, Object> desligamento;
		Collection<Map<String, Object>> solicitacaoDesligamento = new ArrayList<Map<String,Object>>();
		Date dataSolicitacao = (Date) metodo.getParametros()[0];
		String observacaoDemissao = (String) metodo.getParametros()[1];
		Long motivoDemissaoId = (Long) metodo.getParametros()[2];
		Character gerouSubstituicao = (Character) metodo.getParametros()[3];
				
		Long colaboradorId = (Long) metodo.getParametros()[4];
		Colaborador colab = new Colaborador();
		colab.setId(colaboradorId);
		Colaborador colaborador = carregaEntidade(metodo, colab);
		
		desligamento = inicializaDesligamento(dataSolicitacao, observacaoDemissao, motivoDemissaoId, gerouSubstituicao, colaborador);
		
		solicitacaoDesligamento.add(desligamento);
		
		String dados = new DadosAuditados(null, solicitacaoDesligamento.toArray()).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}

	private Map<String, Object> inicializaDesligamento(Date dataSolicitacao, String observacaoDemissao, Long motivoDemissaoId, Character gerouSubstituicao, Colaborador colaborador)
	{
		Map<String, Object> desligamento = new LinkedHashMap<String, Object>();
		desligamento.put("Colaborador ID", colaborador.getId());
		desligamento.put("Colaborador", colaborador.getNome());
		desligamento.put("Data de solicitacao", dataSolicitacao);
		desligamento.put("Observação", observacaoDemissao);
		desligamento.put("Motivo", motivoDemissaoId);
		desligamento.put("Gerou substituição", gerouSubstituicao.toString());
		
		return desligamento;
	}
	
	public Auditavel cancelarSolicitacaoDesligamentoAC(Colaborador colaborador, String obsservacao) throws Throwable 
	{
		Map<String, Object> solicitaDesligamento = new LinkedHashMap<String, Object>();
		solicitaDesligamento.put("Colaborador nome", colaborador.getNome());
		solicitaDesligamento.put("Data do cancelamento da solicitação de desligamento", DateUtil.criarDataMesAno(new Date()));
		solicitaDesligamento.put("Observação do cancelamento", obsservacao);
		
		String dados = new DadosAuditados(null, solicitaDesligamento).gera();
		
		return new AuditavelImpl("Colaborador", "Solicitação de desligamento cancelada", null, dados);
	}
	
	private Colaborador carregaEntidade(MetodoInterceptado metodo, Colaborador colaborador) {
		ColaboradorManager manager = (ColaboradorManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(colaborador.getId());
	}
}
