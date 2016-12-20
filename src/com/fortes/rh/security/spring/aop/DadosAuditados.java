package com.fortes.rh.security.spring.aop;

import java.util.Date;
import java.util.Set;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessorMatcher;
import net.sf.json.util.CycleDetectionStrategy;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.NaoAudita;

public class DadosAuditados {

	private JsonConfig jsonConfig;
	
	private Object resultado;
	private Object[] parametros;
	private String chave;
	
	private DadosAuditados() {
		this.jsonConfig = new JsonConfig(); 
//		this.jsonConfig.setExcludes(new String[]{"chaveParaAuditoria"}); // exclui atributos
		this.jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP); // evita referencia ciclica
		this.jsonConfig.setIgnoreJPATransient(true);
		this.jsonConfig.setIgnoreTransientFields(true);
		this.jsonConfig.addIgnoreFieldAnnotation(NaoAudita.class);
		this.jsonConfig.registerJsonValueProcessor(Date.class, new DateFormatJsonValueProcessor());
		this.jsonConfig.registerJsonValueProcessor(AbstractModel.class, new AbstractModelJsonValueProcessor());
		this.jsonConfig.setJsonValueProcessorMatcher(new JsonValueProcessorMatcher() {  
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Object getMatch(Class target, Set matches) {  
				for(Object match : matches) {  
					if(((Class) match).isAssignableFrom(target)) {  
						return match;  
					}  
				}  
				return null;  
			}
		});
	}
	
	public DadosAuditados(Object[] parametros, Object resultado) {
		this();
		this.parametros = parametros;
		this.resultado = resultado;
	}

	public DadosAuditados(Object[] parametros, Object resultado, String chave) {
		this();
		this.parametros = parametros;
		this.resultado = resultado;
		this.chave = chave;
	}
	
	/**
	 * Gera as informacoes necessarias para auditoria
	 */
	public String gera() {
		String parametros = this.getParametrosFormatados();
		String resultado = this.getResultadoFormatado();
		return concatena(parametros, resultado);
	}

	private String concatena(String parametros, String resultado) {
		if (!parametros.equals("") && !resultado.equals("")) {
			resultado = "\n\n" + resultado;
		}
		return (parametros + resultado);
	}
	/**
	 * Retorna os parametros passados para o método auditado.
	 */
	private String getParametrosFormatados() {
		// FIXME: percorrer todos os parâmetros
		String dados = "";
		if (parametros != null 
				&& parametros.length > 0) {
			dados += "[DADOS ANTERIORES]\n";
			dados += this.geraJson(parametros[0]);
		}
		return dados;
	}
	/**
	 * Gera Json
	 */
	private String geraJson(Object valor) {
		if (valor instanceof Long)
			return valor.toString();
		String retorno = "";
		try {
			retorno = JSONSerializer.toJSON(valor, jsonConfig).toString(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	/**
	 * Retorna o resultado retornado do método auditado.
	 */
	private String getResultadoFormatado() {
		String dados = "";
		if (resultado != null)
			 dados += "[DADOS ATUALIZADOS]\n" + this.geraJson(resultado);
		return dados;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
}
