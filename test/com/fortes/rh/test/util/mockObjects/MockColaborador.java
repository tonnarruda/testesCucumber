package com.fortes.rh.test.util.mockObjects;

import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.test.model.geral.EnderecoFactory;

public class MockColaborador
{
	public static Colaborador getColaborador()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial colaborador");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());

		colaborador.setEndereco(EnderecoFactory.getEntity());

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
}
