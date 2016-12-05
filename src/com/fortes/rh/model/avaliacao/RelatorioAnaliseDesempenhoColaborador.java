package com.fortes.rh.model.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.ibm.icu.text.DecimalFormat;

public class RelatorioAnaliseDesempenhoColaborador
{
	private Colaborador avaliado;
	private Integer valorMaximoGrafico = 10;
	private Integer notaMinimaMediaGeralCompetencia = 0;
	private Collection<ResultadoCompetenciaColaborador> resultadosCompetenciaColaborador = new ArrayList<ResultadoCompetenciaColaborador>();
	@SuppressWarnings("unused")
	private Collection<ResultadoCompetencia> resultadoCompetenciaFinal = new ArrayList<ResultadoCompetencia>();
	private Collection<NivelCompetencia> niveisCompetencias = new ArrayList<NivelCompetencia>();
	LinkedHashMap<String, Integer> legendaMap = new LinkedHashMap<String, Integer>();
	private boolean existeAutoAvaliacao;
	private boolean existeOutrosAvaliadores;
	
	public static String AUTOAVALIACAO = "Auto-Avaliação-&RelatórioAnaliseDesempenho&";
	public static String OUTROSAVALIADORES = "Outros Avaliadores-&RelatórioAnaliseDesempenho&";
	public static String MEDIA = "Média-&RelatórioAnaliseDesempenho&";

	public Colaborador getAvaliado() {
		return avaliado;
	}
	public void setAvaliado(Colaborador avaliado) {
		this.avaliado = avaliado;
	}
	public Collection<ResultadoCompetenciaColaborador> getResultadosCompetenciaColaborador() {
		return resultadosCompetenciaColaborador;
	}
	public void setResultadosCompetenciaColaborador(Collection<ResultadoCompetenciaColaborador> resultadosCompetenciaColaborador) {
		this.resultadosCompetenciaColaborador = resultadosCompetenciaColaborador;
	}
	public Integer getValorMaximoGrafico() {
		return valorMaximoGrafico;
	}
	public void setValorMaximoGrafico(Integer valorMaximoGrafico) {
		this.valorMaximoGrafico = valorMaximoGrafico;
	}
	public Integer getNotaMinimaMediaGeralCompetencia() {
		return notaMinimaMediaGeralCompetencia;
	}
	public void setNotaMinimaMediaGeralCompetencia(Integer notaMinimaMediaGeralCompetencia) {
		this.notaMinimaMediaGeralCompetencia = notaMinimaMediaGeralCompetencia;
	}
	public Integer getTamanhoCollectionNiveisCompetencias(){
		return this.niveisCompetencias.size(); 
	}
	public Collection<NivelCompetencia> getNiveisCompetencias() {
		return niveisCompetencias;
	}
	public void setNiveisCompetencias(Collection<NivelCompetencia> niveisCompetencias) {
		this.niveisCompetencias = niveisCompetencias;
	}
	public Collection<ResultadoCompetencia> getResultadoCompetenciaFinal() {
		LinkedHashMap<String, Integer> mapQtdCompetenciaMaiorQueNotaMinima = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mapQtdCompetenciaExistente = new LinkedHashMap<String, Integer>();
		Integer acimaDaNotaMinimaMediaGeral = 0, totalDeResultados = 0;
		
		mapQtdCompetenciaMaiorQueNotaMinima.put(AUTOAVALIACAO, 0);
		mapQtdCompetenciaExistente.put(AUTOAVALIACAO, 0);
		
		for (ResultadoCompetenciaColaborador resultadoCompetenciaColaborador : resultadosCompetenciaColaborador) {
			for (ResultadoCompetencia resultadoCompetencia : resultadoCompetenciaColaborador.getResultadoCompetencias()) {
				if(resultadoCompetencia.getNome().equals(MEDIA)) 
					continue;

				totalDeResultados++;
				
				if(!mapQtdCompetenciaMaiorQueNotaMinima.containsKey(resultadoCompetencia.getNome()))
					mapQtdCompetenciaMaiorQueNotaMinima.put(resultadoCompetencia.getNome(), 0);
				
				if(!mapQtdCompetenciaExistente.containsKey(resultadoCompetencia.getNome()))
					mapQtdCompetenciaExistente.put(resultadoCompetencia.getNome(), 1);
				else
					mapQtdCompetenciaExistente.put(resultadoCompetencia.getNome(), mapQtdCompetenciaExistente.get(resultadoCompetencia.getNome()) + 1);
					
				if(resultadoCompetencia.getOrdem() >= notaMinimaMediaGeralCompetencia){
					mapQtdCompetenciaMaiorQueNotaMinima.put(resultadoCompetencia.getNome(), mapQtdCompetenciaMaiorQueNotaMinima.get(resultadoCompetencia.getNome()) + 1);
					acimaDaNotaMinimaMediaGeral++;
				}
			}
		}
		
		return montaResultadoCompetenciaRetorno(mapQtdCompetenciaMaiorQueNotaMinima, mapQtdCompetenciaExistente, acimaDaNotaMinimaMediaGeral,totalDeResultados);
	}
	
	private LinkedList<ResultadoCompetencia>  montaResultadoCompetenciaRetorno(LinkedHashMap<String, Integer> mapQtdCompetenciaMaiorQueNotaMinima, LinkedHashMap<String, Integer> mapQtdCompetenciaExistente, Integer acimaDaNotaMinimaMediaGeral, Integer totalDeResultados) throws NumberFormatException {
		ajustaPosicaoMap(mapQtdCompetenciaMaiorQueNotaMinima);

		LinkedList<ResultadoCompetencia> resultadoCompetenciaRetorno = new LinkedList<ResultadoCompetencia>();
		
		for (String nome : mapQtdCompetenciaMaiorQueNotaMinima.keySet()) {
			Double percentual = (mapQtdCompetenciaMaiorQueNotaMinima.get(nome).doubleValue() / mapQtdCompetenciaExistente.get(nome).doubleValue()) * 100;
			resultadoCompetenciaRetorno.add(new ResultadoCompetencia(legendaMap.get(nome), nome, Double.valueOf(new DecimalFormat("#.##").format(percentual).replace(",", "."))));
		}
		
		Double percentual = (acimaDaNotaMinimaMediaGeral.doubleValue()/totalDeResultados) * 100;
		resultadoCompetenciaRetorno.add(new ResultadoCompetencia(legendaMap.get(MEDIA), MEDIA, Double.valueOf(new DecimalFormat("#.##").format(percentual).replace(",", "."))));
		
		return resultadoCompetenciaRetorno;
	}
	
	private void ajustaPosicaoMap(LinkedHashMap<String, Integer> mapResultadoCompetenciaRetorno) {
		if(mapResultadoCompetenciaRetorno.get(AUTOAVALIACAO) == 0) 
			mapResultadoCompetenciaRetorno.remove(AUTOAVALIACAO);
		
		if(mapResultadoCompetenciaRetorno.containsKey(OUTROSAVALIADORES)){
			Integer resultadoOuros = mapResultadoCompetenciaRetorno.get(OUTROSAVALIADORES);
			mapResultadoCompetenciaRetorno.remove(OUTROSAVALIADORES);
			mapResultadoCompetenciaRetorno.put(OUTROSAVALIADORES, resultadoOuros);
		}
	}
	
	private static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
	    public int compare(String str1, String str2) {
	        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
	        if (res == 0) { res = str1.compareTo(str2);  }
	        return res;
	    }
	};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Collection<String> nomesDistinctsOrdenados() {
		Collection<String> nomes = new ArrayList<String>();
		for (ResultadoCompetenciaColaborador resultadoCompetenciaColaborador : resultadosCompetenciaColaborador) {
			for (ResultadoCompetencia resultadoCompetencia : resultadoCompetenciaColaborador.getResultadoCompetencias()) {
				if(!resultadoCompetencia.getNome().equals(AUTOAVALIACAO) && !resultadoCompetencia.getNome().equals(MEDIA) 
						&& !resultadoCompetencia.getNome().equals(OUTROSAVALIADORES) && !nomes.contains(resultadoCompetencia.getNome()))
					nomes.add(resultadoCompetencia.getNome());
				else if(resultadoCompetencia.getNome().equals(OUTROSAVALIADORES))
					existeOutrosAvaliadores = true;
				else if(resultadoCompetencia.getNome().equals(AUTOAVALIACAO))
					existeAutoAvaliacao = true;
					
			}
		}
		Collections.sort((List) nomes, ALPHABETICAL_ORDER);
		return nomes;
	}
	
	public void montaLegenda(){
		Collection<String> nomes = nomesDistinctsOrdenados();
		Integer contador = 1;
		
		if(existeAutoAvaliacao)
			legendaMap.put(AUTOAVALIACAO, contador++);
		
		for (String nome : nomes) {
			if(!legendaMap.containsKey(nome))
				legendaMap.put(nome, contador++);
		}
		
		if(existeOutrosAvaliadores)
			legendaMap.put(OUTROSAVALIADORES, contador++);
		
		legendaMap.put(MEDIA, contador++);
		
		for (ResultadoCompetenciaColaborador resultadoCompetenciaColaborador : resultadosCompetenciaColaborador) {
			for (ResultadoCompetencia resultadoCompetencia : resultadoCompetenciaColaborador.getResultadoCompetencias()) {
				resultadoCompetencia.setIdentificador(legendaMap.get(resultadoCompetencia.getNome()));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getNiveisCompetenciasDescricao() {
		String retorno = null;
		if (niveisCompetencias != null){
			List<String> ordens = (List<String>) CollectionUtils.collect(niveisCompetencias, new BeanToPropertyValueTransformer("descricao", true));
			retorno = StringUtils.join(ordens.toArray(), "#;#");
		}

		return StringUtils.defaultString(retorno);
	}
	
	@SuppressWarnings("unchecked")
	public String getNiveisCompetenciasOrdem() {
		String retorno = null;
		if (niveisCompetencias != null){
			List<String> ordens = (List<String>) CollectionUtils.collect(niveisCompetencias, new BeanToPropertyValueTransformer("ordemComPercentual", true));
			retorno = StringUtils.join(ordens.toArray(), "#;#");
		}
		
		return StringUtils.defaultString(retorno);
	}
	
	public String getLegenda() {
		String retorno = "";
		for(String nome : legendaMap.keySet())
			retorno += ResultadoCompetencia.getAlafabeto(legendaMap.get(nome)) + " - " + nome.replace("-&RelatórioAnaliseDesempenho&", "") + "\n";

		return retorno;
	}
}
