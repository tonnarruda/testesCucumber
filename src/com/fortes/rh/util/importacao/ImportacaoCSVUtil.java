package com.fortes.rh.util.importacao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.dicionario.RegistrosDeSaude;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;


public class ImportacaoCSVUtil
{
	private String delimitador = ";"; 
	private Collection<ColaboradorAfastamento> afastamentos = new ArrayList<ColaboradorAfastamento>();
	private Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
	private Map<String, Epi> episMap = new HashMap<String, Epi>();
	
	public void importarCSV(File file, OpcaoImportacao opcao) throws Exception
	{
		importarCSV(file, opcao, false);
	}

	public void importarCSV(File file, OpcaoImportacao opcao, boolean pularTitulos) throws Exception
	{
		// ler arquivo
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String linha = "";
		
		if (pularTitulos)
			br.readLine();
		
		while(br.ready())
		{
			String[] campos = null;
			linha = br.readLine();
			linha = linha.replaceAll("\"","");
			
			if (linha != null && linha.indexOf(delimitador) != -1) 
			{
				campos = linha.split(Pattern.quote(delimitador), -1);
				setCampos(campos, opcao);
			}
		}
		
		validaExportacao(opcao); 
	}

	private void validaExportacao(OpcaoImportacao opcao) throws FortesException 
	{
		switch (opcao)
		{
			case AFASTAMENTOS_TRU: 
				if (afastamentos.size() == 0)
					throw new FortesException("Não foram encontradas linhas válidas. Verifique o arquivo.");
				break;
			case COLABORADORES_DADOS_PESSOAIS:
				if (colaboradores.size() == 0)
					throw new FortesException("Não foram encontradas linhas válidas. Verifique o arquivo.");
				break;
			case EPIS:
				if (episMap.isEmpty())
					throw new FortesException("Não foram encontradas linhas com dados de EPI válidos. Verifique o arquivo.");
				break;
		}
	}
	
	private void setCampos(String[] campos, OpcaoImportacao opcaoImportacao) throws Exception
	{
		switch (opcaoImportacao)
		{
			case AFASTAMENTOS_TRU: 
				setAfastamento(campos);
				break;
			case COLABORADORES_DADOS_PESSOAIS:
				setColaborador(campos);
				break;
			case EPIS:
				setEpi(campos);
				break;
		}
	}
	
	//Cód. Empregado;Nome Completo;Nome Comercial;Doença(Motivo Afastamento);Data Inicial;Data Final;Médico;CRM;CID;Observação
	private void setAfastamento(String[] campos) throws Exception
	{	
		// linhas válidas começam com o código do empregado. O resto é cabeçalho, etc.
		if (campos.length == 10 && StringUtils.isNumeric(campos[0]))
		{
			ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();
			colaboradorAfastamento.setColaboradorCodigoAC(StringUtil.formataCodigoAC(campos[0]));
			colaboradorAfastamento.getColaborador().setNome(campos[1]);
			colaboradorAfastamento.getColaborador().setNomeComercial(campos[2]);
			colaboradorAfastamento.setAfastamentoDescricao(campos[3]);
			
			colaboradorAfastamento.setInicio(DateUtil.montaDataByString(campos[4]));
			colaboradorAfastamento.setFim(DateUtil.montaDataByString(campos[5]));
			
			colaboradorAfastamento.setNomeProfissionalDaSaude(campos[6]);
			colaboradorAfastamento.setNumeroDoRegistroDeSaude(campos[7]);
			colaboradorAfastamento.setTipoRegistroDeSaude(RegistrosDeSaude.CRM);
			
			colaboradorAfastamento.setCid(campos[8]);
			colaboradorAfastamento.setObservacao(campos[9]);

			afastamentos.add(colaboradorAfastamento);
		}
	}
	
	//"Matricula;CPF;Endereço;Numero;Complemento Endereço;Cidade(ID);Estado(ID);Bairro;Cep;ddd;telefone;Celular;Grau Instrução(COD. DICIONARIO)"
	private void setColaborador(String[] campos) throws FortesException {
		
		if (campos.length >= 13 && campos[1] != null)
		{
			Colaborador colaborador = new Colaborador();
			Pessoal pessoal = new Pessoal();
			Endereco endereco = new Endereco();
			colaborador.setPessoal(pessoal);
			colaborador.setEndereco(endereco);
			colaborador.setContato(new Contato());
			
			colaborador.setMatricula(campos[0]);
			
			/*
			 * Ajuste:
			 * CSV da Vitória veio com alguns CPFs incompletos 
			 * (faltando 0's na frente.) 
			 * TODO comentar/apagar isso
			 */
			campos[1] = campos[1].trim();
			while (campos[1].length() < 11)
				campos[1] = "0" + campos[1];
			
			pessoal.setCpf(campos[1]);
			
			endereco.setLogradouro(campos[2]);
			endereco.setNumero(campos[3]);
			
			if (campos[4]!= null && campos[4].length() > 20 )
				endereco.setComplemento(campos[4].substring(0,20));
			else
				endereco.setComplemento(campos[4]);
			
			if (StringUtils.isNotBlank(campos[5]) && StringUtils.isNumeric(campos[5]))
			{
				colaborador.setEnderecoCidadeId(Long.parseLong(campos[5]));
				
				// ajustando DDD em branco. Fortaleza/Caucaia/Maracanaú (p/ Vitória) . Tiago
				if (StringUtils.isBlank(campos[9]) &&
						(campos[5].equals("931") || campos[5].equals("946") || campos[5].equals("991")))
				{
					campos[9] = "85";
				}
			}
			
			if (StringUtils.isNotBlank(campos[6]) && StringUtils.isNumeric(campos[6]))
				colaborador.setEnderecoUfId(Long.parseLong(campos[6]));
			
			
			if (campos[7]!= null && campos[7].length() > 20 )
				colaborador.setEnderecoBairro(campos[7].substring(0,20));
			else
				colaborador.setEnderecoBairro(campos[7]);
			
			colaborador.setEnderecoCep(campos[8]);
			
			colaborador.setContatoDdd(campos[9]);
			
			colaborador.setContatoFoneFixo(campos[10]);
			colaborador.setContatoCelular(campos[11]);
			
			// ajustando codigo da escolaridade (tem um '0' na frente)
			if (StringUtils.isNotBlank(campos[12]) && campos[12].length() == 1)
				campos[12] = "0" + campos[12];
			
			colaborador.setPessoalEscolaridade(campos[12]);
			
			colaboradores.add(colaborador);
		}
	}
	
	//Código EPI|#|Nome do EPI|#|Nome do Fabricante|#|Status|#|É Fardamento|#|Data do Histórico EPI|#|Data de Vencimento|#|Número do CA|#| Percentual de Atenuação do Risco|#|Período Recomendado de Uso|#|Código da Categoria|#|Nome da Categoria
	private void setEpi(String[] campos) throws Exception
	{	
		if (campos.length == 12 && StringUtils.isNumeric(campos[0]))
		{
			Epi epi;
			EpiHistorico epiHistorico;
			
			if (episMap.containsKey(campos[0]))
				epi = episMap.get(campos[0]);
			else
			{
				epi = new Epi();
				epi.setEpiHistoricos(new ArrayList<EpiHistorico>());
				epi.setCodigo(campos[0]);
				episMap.put(campos[0], epi);
			}
			
			epi.setNome(campos[1]);
			epi.setFabricante(campos[2]);
			epi.setAtivo(new Boolean(campos[3]));
			epi.setFardamento(new Boolean(campos[4]));
			epi.setTipoEPI(new TipoEPI(campos[10], campos[11]));
			
			epiHistorico = new EpiHistorico();
			epiHistorico.setData(DateUtil.montaDataByString(campos[5]));
			epiHistorico.setVencimentoCA(DateUtil.montaDataByString(campos[6]));
			epiHistorico.setCA(campos[7]);
			epiHistorico.setAtenuacao(campos[8]);
			epiHistorico.setValidadeUso(new Integer(campos[9]));
			epi.getEpiHistoricos().add(epiHistorico);
		}
	}

	public Collection<ColaboradorAfastamento> getAfastamentos() {
		return afastamentos;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setDelimitador(String delimitador) {
		this.delimitador = delimitador;
	}

	public Collection<Epi> getEpis() {
		return new ArrayList<Epi>( episMap.values() );
	}
}
