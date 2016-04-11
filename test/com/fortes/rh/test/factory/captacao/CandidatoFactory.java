package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;

public class CandidatoFactory
{
	public static Candidato getCandidato()
	{
		Candidato candidato = new Candidato();

		candidato.setId(null);
		candidato.setAreasInteresse(null);
		candidato.setColocacao("1");
		candidato.setConhecimentos(null);

		SocioEconomica socioEconomica = new SocioEconomica();
		candidato.setSocioEconomica(socioEconomica);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		candidato.setContato(contato);

		candidato.setContratado(false);
		candidato.setCursos(null);
		candidato.setDisponivel(true);

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		candidato.setEndereco(endereco);

		candidato.setExperiencias(null);
		candidato.setFormacao(null);
		candidato.setNome("colaborador teste");
		candidato.setObservacao("obs");

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("11111111111");
		pessoal.setConjuge("Maria");
		pessoal.setNaturalidade("Palmacia");
		pessoal.setMae("Joana");
		pessoal.setPai("Roberto");
		pessoal.setQtdFilhos(0);
		candidato.setPessoal(pessoal);

		Habilitacao habilitacao = new Habilitacao();
		habilitacao.setCategoria("ABC");
		habilitacao.setEmissao(null);
		habilitacao.setVencimento(null);
		habilitacao.setRegistro("607583");
		habilitacao.setNumeroHab("123324235");
		candidato.setHabilitacao(habilitacao);

		candidato.setEmpresa(null);

		candidato.setPretencaoSalarial(1500.00);

		return candidato;
	}

	public static Candidato getCandidato(Long id) {
		Candidato candidato = getCandidato();
		candidato.setId(id);
		return candidato;
	}
	
	public static Candidato getCandidato(Long id, String nome){
		Candidato candidato = getCandidato(id);
		candidato.setNome(nome);
		return candidato;
	}
}
