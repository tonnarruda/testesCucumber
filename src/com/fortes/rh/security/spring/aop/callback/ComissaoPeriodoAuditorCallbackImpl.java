package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;

import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class ComissaoPeriodoAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}
	
	public Auditavel atualiza(MetodoInterceptado metodo) throws Throwable {
		ComissaoPeriodo comissaoPeriodo = (ComissaoPeriodo) metodo.getParametros()[0];
		String[] comissaoMembroIds = (String[]) metodo.getParametros()[1];
		String[] funcaoComissaos = (String[]) metodo.getParametros()[2];
		String[] tipoComissaos = (String[]) metodo.getParametros()[3];
		
		metodo.processa();

		StringBuilder dados = new StringBuilder();
		dados.append("[DADOS ATUALIZADOS]");
		dados.append("\n\n Id: " + comissaoPeriodo.getId());
		dados.append("\n A partir de: " + comissaoPeriodo.getPeriodoFormatado());
		dados.append("\n Membros: [");
		
		Map<String, String> membros = getMembros(metodo, comissaoPeriodo.getId());
		
		for (int i = 0; i < comissaoMembroIds.length; i++) {
			dados.append("\n   Comissão Membro Id: " + comissaoMembroIds[i]);
			dados.append("\n   Nome: " + membros.get(comissaoMembroIds[i]));
			dados.append("\n   Função: " + FuncaoComissao.getInstance().get(funcaoComissaos[i]));
			dados.append("\n   Tipo: " + TipoMembroComissao.getInstance().get(tipoComissaos[i]));
			dados.append("\n");
		}
		dados.append("\n ]");
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), comissaoPeriodo.getPeriodoFormatado(), dados.toString());
	}
	
	private Map<String, String> getMembros(MetodoInterceptado metodo, Long comissaoPeriodoId) {
		ComissaoPeriodoManager comissaoPeriodoManager = (ComissaoPeriodoManager) metodo.getComponente();
		
		Collection<ComissaoMembro> comissaoMembros = comissaoPeriodoManager.findComissaoMembro(comissaoPeriodoId);
		
		final Map<String, String> membros = new HashMap<String, String>();
		IterableUtils.forEach(comissaoMembros, new Closure<ComissaoMembro>() {
			@Override
			public void execute(ComissaoMembro comissaoMembro) {
				membros.put(comissaoMembro.getId().toString(), comissaoMembro.getColaborador().getNome());
			}
		});
		return membros;
	}
}