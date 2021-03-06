package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.test.model.geral.EnderecoFactory;

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
		colaborador.setEndereco(EnderecoFactory.getEntity());
		colaborador.setContato(ContatoFactory.getEntity());
		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		Habilitacao habilitacao = new Habilitacao();
		habilitacao.setEmissao(new Date());
		colaborador.setHabilitacao(habilitacao);
		
		colaborador.setDependentes(null);
		return colaborador;
	}


	public static Colaborador getEntity(Long id)
	{
		Colaborador colaborador = getEntity();
		colaborador.setDataAdmissao(new Date());
		colaborador.setId(id);

		return colaborador;
	}
	
	public static Colaborador getEntity(Long id, Empresa empresa)
	{
		Colaborador colaborador = getEntity();
		colaborador.setId(id);
		colaborador.setEmpresa(empresa);
		return colaborador;
	}
	
	public static Colaborador getEntity(Long id, String nome)
	{
		Colaborador colaborador = getEntity(id);
		colaborador.setNome(nome);
		return colaborador;
	}
	
	public static Colaborador getEntity(Long id, String email, AreaOrganizacional area)
	{
		Colaborador colaborador = getEntity(id);
		colaborador.setContato(new Contato());
		colaborador.getContato().setEmail(email);
		colaborador.setAreaOrganizacional(area);
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
	
	public static Colaborador getEntity(Long id, String nome, String email, Date dataSolicitacaoDesligamento, String motivoDemissao, String observacaoDemissao, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional)
	{
		Colaborador colaborador = getEntity(id);
		colaborador.setNome(nome);
		colaborador.setEmailColaborador(email);
		colaborador.setDataSolicitacaoDesligamento(dataSolicitacaoDesligamento);
		colaborador.setMotivoDemissaoMotivo(motivoDemissao);
		colaborador.setObservacaoDemissao(observacaoDemissao);
		colaborador.setEstabelecimento(null);
		colaborador.setAreaOrganizacional(areaOrganizacional);
		
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
	
	public static Colaborador getEntity(String nome, Empresa empresa, Usuario usuario, String email)
	{
		Colaborador colaborador = getEntity();
		colaborador.setNome(nome);
		colaborador.setEmpresa(empresa);
		colaborador.setUsuario(usuario);
		colaborador.setEmailColaborador(email);
		
		return colaborador;
	}
	
	public static Colaborador getEntity(Boolean desligado, Date dataAdmissao, Date dataDesligamento){
		Colaborador colaborador = getEntity();
		colaborador.setDesligado(desligado);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setDataDesligamento(dataDesligamento);
		
		return colaborador;
	}
	
	public static Colaborador getEntity(String nome, Boolean desligado, String matricula){
		Colaborador colaborador = getEntity();
		colaborador.setNome(nome);
		colaborador.setDesligado(desligado);
		colaborador.setMatricula(matricula);
		return colaborador;
	}
	
	public static Colaborador getEntity(String codigoAC, Long id)
	{
		Colaborador colaborador = getEntity();
		colaborador.setId(id);
		colaborador.setCodigoAC(codigoAC);

		return colaborador;
	}

	public static Colaborador getEntity(Long id, String nome, String nomeMae, String nomePai, String conjuge, Empresa empresa)
	{
		Colaborador colaborador = getEntity(id);
		colaborador.setNome(nome);
		colaborador.setPessoalMae(nomeMae);
		colaborador.setPessoalPai(nomePai);
		colaborador.setPessoalConjuge(conjuge);
		colaborador.setEmpresa(empresa);

		return colaborador;
	}

	public static Colaborador getEntity(String nome) {
		Colaborador colaborador = getEntity();
		colaborador.setNome(nome);
		
		return colaborador;
	}
	
	public static Colaborador getEntity(Estado uf) {
		
		Colaborador colaborador = getEntity();
		
		colaborador.getEndereco().setUf(uf);
		colaborador.getPessoal().setQtdFilhos(5);
		colaborador.getPessoal().setRgUf(uf);
		colaborador.getHabilitacao().setEmissao(null);
		colaborador.getPessoal().getCtps().setCtpsNumero("123499");
		colaborador.getPessoal().getCtps().setCtpsDataExpedicao(new Date());
		
		return colaborador;
	}
}
