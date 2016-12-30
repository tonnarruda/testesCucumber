package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class TurmaAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}
	
	public Auditavel removeCascade(MetodoInterceptado metodo) throws Throwable {
		
		Long turmaId = (Long) metodo.getParametros()[0];
		Turma turma = carregaEntidade(metodo, turmaId);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{turma}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), turma.getDescricao(), dados);
	}

	@SuppressWarnings("unchecked")
	public Auditavel salvarTurmaDiasCustosColaboradoresAvaliacoes(MetodoInterceptado metodo) throws Throwable {
		Turma turma = (Turma) metodo.getParametros()[0];
		String[] diasCheck = (String[]) metodo.getParametros()[1];
		String custos = (String) metodo.getParametros()[2];
		Collection<ColaboradorTurma> colaboradorTurmas = (Collection<ColaboradorTurma>) metodo.getParametros()[3];
		Long[] avaliacaoTurmasCheck = (Long[]) metodo.getParametros()[4];
		String[] horasIni = (String[]) metodo.getParametros()[5];
		String[] horasFim = (String[]) metodo.getParametros()[6];
		
		metodo.processa();

		StringBuilder dados = new StringBuilder();
		dados.append("[DADOS ATUALIZADOS]");
		dados.append("\n\n Id: " + turma.getId());
		dados.append("\n Descrição: " + turma.getDescricao());
		dados.append("\n Realizada: " + turma.getRealizadaFormatada());
		dados.append("\n Custo: " + turma.getCustoFormatado());
		montaCustoDetalhado(dados, custos);
		dados.append("\n Horário: " + turma.getHorario());
		dados.append("\n Instrutor: " + turma.getInstrutor());
		dados.append("\n Instituição: " + turma.getInstituicao());
		dados.append("\n Avaliações: " + (avaliacaoTurmasCheck !=null ? Arrays.toString(avaliacaoTurmasCheck) : "[]"));
		dados.append("\n Período: " + turma.getPeriodoFormatado());
		dados.append("\n Dias turma: [" + montaDias(diasCheck, horasIni, horasFim) + "\n ],");
		dados.append("\n Colaboradores da turma: [");
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
			dados.append("\n  Id: " + colaboradorTurma.getId());
			dados.append("\n  ColaboradorId: " + colaboradorTurma.getColaborador().getId());
			dados.append("\n");
		}
		dados.append("\n ]");
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), turma.getDescricao(), dados.toString());
	}
	
	private String montaDias(String[] diasCheck, String[] horasIni, String[] horasFim){
		int iterator = 0;
		String realizarTurmaPor = "";
		String dias= "";
		if(diasCheck !=null){
			while (iterator < diasCheck.length) {
				realizarTurmaPor = diasCheck[iterator].split(";")[1];
				if(realizarTurmaPor.equals("D")){
					dias+= "\n  Dia: " + diasCheck[iterator].split(";")[0];
					if(!horasIni[iterator].trim().isEmpty())
						dias+=" de " + horasIni[iterator] + " às " + horasFim[iterator];
					
					iterator++;
				}
				else{
					for (int i = iterator; (i < iterator + 3 && i < diasCheck.length) ; i++) {
						dias+= "\n  Dia: " + diasCheck[i].split(";")[0] + " Turno: " + diasCheck[i].split(";")[1];
						if(!horasIni[iterator].trim().isEmpty())
							 dias+= " de " + horasIni[i] + " às " + horasFim[i];
					}
					iterator = iterator + 3;
				}
			}
		}
		return dias;
	}
	
	@SuppressWarnings("unchecked")
	private StringBuilder montaCustoDetalhado(StringBuilder dados, String custos){
		dados.append("\n Custo detalhado: [" );
		if (custos != null && !custos.trim().isEmpty()) {
			Collection<TurmaTipoDespesa> despesas = new ArrayList<TurmaTipoDespesa>();
			despesas = (Collection<TurmaTipoDespesa>) StringUtil.simpleJSONtoArrayJava(custos, TurmaTipoDespesa.class);
			for (TurmaTipoDespesa turmaTipoDespesa : despesas) {
				dados.append("\n  TipoDespesaId: " + turmaTipoDespesa.getTipoDespesaId());
				dados.append("\n  Despesa: " + turmaTipoDespesa.getDespesa());
				dados.append("\n  ");
			}
		}
		dados.append(" ]," );
		return dados;
	}
	
	private Turma carregaEntidade(MetodoInterceptado metodo, Long turmaId) {
		TurmaManager manager = (TurmaManager) metodo.getComponente();
		return manager.findByIdProjection(turmaId);
	}
}