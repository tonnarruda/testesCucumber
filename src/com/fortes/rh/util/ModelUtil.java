package com.fortes.rh.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fortes.model.AbstractModel;

import net.vidageek.mirror.dsl.Mirror;


public class ModelUtil
{
	/**
	 * Retorna o valor do método caso os gets anteriores não sejam nulos.
	 * @param model - Modelo no qual será feito o teste do caminho dos métodos.
	 * @param metodos - Caminho dos métodos no qual será testado se o valor é nulo. Cada nó de método get será testado.
	 * @param consideraBrancoComoNulo - Caso seja passado <b>true</b> e um dos retornos dos nós dos métodos seja vazio, este será considerado nulo.
	 * 
	 * @return Objeto contendo o valor do último <b>get</b>. Caso um dos nós dos métodos retorne nulo, o valor retornado será <b>null</b>. 
	 */
	public static Object getValor(AbstractModel model, String metodos, boolean consideraBrancoComoNulo) {
		
		String[] metodosGet = metodos.replaceAll("\\(\\)","").split("\\.");

		try {
			Object retorno = null;
			Object objeto = model;
			for (String metodo : metodosGet) {
				retorno = new Mirror().on(objeto).invoke().method(metodo).withoutArgs();
				if((retorno == null) || 
						(consideraBrancoComoNulo && retorno != null && retorno.toString().equals(""))) {
					return null;					
				}
				objeto = retorno;
			}
			
			return formataRetorno(retorno);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Object formataRetorno(Object retorno) {
		if(retorno != null && retorno instanceof Date){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(retorno);
		}
		return retorno;
	}
	
	/**
	 * Verifica se algum dos nós dos métodos <b>get</b> contidos no parâmetro <b>metodos</b> retorna um valor nulo.
	 * @param metodos - Caminho dos métodos no qual será testado se o valor é nulo.
	 * @param models - Um ou mais modelos podem ser avaliados para o mesmo caminho de métodos.
	 * 
	 * @return Retorna <b>true</b> caso algum dos métodos <b>get</b> contidos no parâmetro <b>metodos</b> retorne um valor nulo.
	 */

	public static boolean hasNull(String metodos, AbstractModel... models) {
		if (models == null)
			return Boolean.TRUE;
		
		for (AbstractModel model : models) {
			Object valor = getValor(model, metodos, Boolean.FALSE);
			if(valor == null)
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * Verifica se nenhum dos nós dos métodos <b>get</b> contidos no parâmetro <b>metodos</b> retorna um valor nulo.
	 * @param metodos - Caminho dos métodos no qual será testado se o valor é nulo.
	 * @param models - Um ou mais modelos podem ser avaliados para o mesmo caminho de métodos.
	 * 
	 * @return Retorna <b>true</b> caso nenhum dos métodos <b>get</b> contidos no parâmetro <b>metodos</b> retorne um valor nulo.
	 */
	
	public static boolean hasNotNull(String metodos, AbstractModel... models) {
		return !hasNull(metodos, models);
	}

}