package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Pessoal;

public class ColaboradorFactory
{
	public static Colaborador getEntity()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial colaborador");
		colaborador.setDesligado(false);
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		colaborador.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		colaborador.setContato(contato);

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		colaborador.setDependentes(null);
		return colaborador;
	}


	public static Colaborador getEntity(Long id)
	{
		Colaborador colaborador = getEntity();
		colaborador.setId(id);

		return colaborador;
	}
	
	public static Colaborador getEntity(Long id, String nome, String nomeComercial, String matricula)
	{
		Colaborador colaborador = getEntity();
		colaborador.setId(id);
		colaborador.setNome(nome);
		colaborador.setNomeComercial(nomeComercial);
		colaborador.setMatricula(matricula);
		
		return colaborador;
	}

	public static Collection<Colaborador> getCollection()
	{
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(getEntity());

		return colaboradors;
	}

}
