package com.fortes.rh.util.importacao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;


public class ImportacaoCSVUtil
{
	private String delimitador = ";"; 
	private Collection<ColaboradorAfastamento> afastamentos = new ArrayList<ColaboradorAfastamento>();
	private Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
	
	public void importarCSV(File file, OpcaoImportacao opcao) throws Exception
	{
		// ler arquivo
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String linha = "";
		boolean naoAchouLinhaComAfastamento = true;
		
		while(br.ready())
		{
			String[] campos = null;
			linha = br.readLine();
			
			if (valida(linha)) 
			{
				campos = linha.split(delimitador);
				setCampos(campos, opcao);
				naoAchouLinhaComAfastamento = false;
			}
		}
		
		if(naoAchouLinhaComAfastamento)
			throw new Exception("Afastamentos não encontrados, verifique o arquivo CSV, linhas invalidas.");
	}
	
	private void setCampos(String[] campos, OpcaoImportacao opcaoImportacao) throws Exception
	{
		switch (opcaoImportacao)
		{
			case AFASTAMENTOS_TRU: 
				setAfastamento(campos);
			case COLABORADORES_DADOS_PESSOAIS:
				setColaborador(campos);
		}
	}
	
	//Cód. Empregado;Nome Completo;Nome de Escala;Doença;Data Inicial;Data Final;Médico;Descrição do Tipo;
	private void setAfastamento(String[] campos) throws Exception
	{	
		boolean naoAchouLinhaComAfastamento = true;
		// linhas válidas começam com o código do empregado. O resto é cabeçalho, etc.
		if (campos.length == 8 && StringUtils.isNumeric(campos[0]))
		{
			ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();
			colaboradorAfastamento.setColaboradorCodigoAC(StringUtil.formataCodigoAC(campos[0]));
			colaboradorAfastamento.getColaborador().setNome(campos[1]);
			colaboradorAfastamento.getColaborador().setNomeComercial(campos[2]);
			colaboradorAfastamento.setAfastamentoDescricao(campos[3]);
			
			colaboradorAfastamento.setInicio(DateUtil.montaDataByString(campos[4]));
			colaboradorAfastamento.setFim(DateUtil.montaDataByString(campos[5]));
			
			colaboradorAfastamento.setMedicoNome(campos[6]);
			colaboradorAfastamento.setObservacao(campos[7]);
			
			afastamentos.add(colaboradorAfastamento);
			naoAchouLinhaComAfastamento = false;
		}
		
		if(naoAchouLinhaComAfastamento)
			throw new Exception("Afastamentos não encontrados, verifique o arquivo CSV, nenhum afastamento encontrado.");
	}
	
	
	//"Matricula;CPF;Endereço;Numero;Complemento Endereço;Cidade(ID);Estado(ID);Bairro;Cep;ddd;telefone;Celular;Grau Instrução(COD. DICIONARIO)"
	private void setColaborador(String[] campos) {
		
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

	private boolean valida(String linha) {
		return linha != null && linha.indexOf(delimitador) != -1;
	}

	public Collection<ColaboradorAfastamento> getAfastamentos() {
		return afastamentos;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}
}
