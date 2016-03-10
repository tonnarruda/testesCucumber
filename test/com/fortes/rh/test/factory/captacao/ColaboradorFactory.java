package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
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
		colaborador.setVinculo(Vinculo.EMPREGO);

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

		Habilitacao habilitacao = new Habilitacao();
		colaborador.setHabilitacao(habilitacao);
		
		colaborador.setDependentes(null);
		return colaborador;
	}


	public static Colaborador getEntity(Long id)
	{
		Colaborador colaborador = getEntity();
		colaborador.setId(id);

		return colaborador;
	}
	
	public static Colaborador getEntity(Long id, String nome, String nomeComercial, String matricula, String codigoAC, String cpf, Empresa empresa)
	{
		Colaborador colaborador = getEntity(id);
		colaborador.setNome(nome);
		colaborador.setNomeComercial(nomeComercial);
		colaborador.setMatricula(matricula);
		colaborador.setCodigoAC(codigoAC);
		colaborador.setPessoalCpf(cpf);
		colaborador.setEmpresa(empresa);
		colaborador.setContato(new Contato());
		
		return colaborador;
	}
	
	public static Colaborador getEntity(String matricula, String nome, Empresa empresa, Date dataAdmissao, Date dataDesligamento)
	{
		Colaborador colaborador = getEntity();
		colaborador.setMatricula(matricula);
		colaborador.setNome(nome);
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setDataDesligamento(dataDesligamento);
		
		return colaborador;
	}
	
	public static Colaborador getEntity(Boolean desligado, Date dataAdmissao, Empresa empresa, String vinculo, Character deficiencia)
	{
		Colaborador colaborador = getEntity();
		colaborador.setDesligado(desligado);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setEmpresa(empresa);
		colaborador.setVinculo(vinculo);
		colaborador.getPessoal().setDeficiencia(deficiencia);
		
		return colaborador;
	}

	public static Collection<Colaborador> getCollection()
	{
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(getEntity());

		return colaboradors;
	}
	
	public static Colaborador getEntity(Empresa empresa, Usuario usuario)
	{
		Colaborador colaborador = getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setUsuario(usuario);
		return colaborador;
	}
}
