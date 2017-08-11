package com.fortes.rh.util.validacao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public class GerenciadorValidacao {
	private static List<Validacao> validacoes = new ArrayList<Validacao>();
	
	private static List<Validacao> getValidacoes(){
		if(validacoes.isEmpty()){
//			validacoes.add(new ValidacaoQuantidadeConstraints());
			validacoes.add(new ValidacaoVersao());
		}
		
		return validacoes;
	}
	
	public static List<String> valida(final ParametrosDoSistema parametrosDoSistema) {
		final List<String> mensagens = new ArrayList<String>();
		
		IterableUtils.forEach(getValidacoes(), new Closure<Validacao>() {

			@Override
			public void execute(Validacao validacao) {
				try {
					validacao.execute(parametrosDoSistema);
				} catch (FortesException e) {
					mensagens.add(e.getMessage());
				}
			}
			
		});		
		return mensagens;
	}
}
