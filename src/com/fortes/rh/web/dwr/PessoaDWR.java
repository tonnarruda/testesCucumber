package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Pessoa;

@Component
@RemoteProxy(name="PessoaDWR")
public class PessoaDWR
{
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private CandidatoManager candidatoManager;

	@RemoteMethod
	public Collection<Pessoa> verificaCpfDuplicado(String cpf, Long empresaId, Long id) throws Exception
	{
		String cpfSemMascara = cpf.replaceAll("\\.", "").replaceAll("-", "").trim();
		Collection<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		if (!cpfSemMascara.equals("") && cpfSemMascara.length() == 11)
		{
			Collection<Candidato> candidatos = candidatoManager.findByCPF(cpfSemMascara, empresaId, id, false);
			for (Candidato candidato : candidatos)
				pessoas.add(new Pessoa(candidato.getId(), candidato.getNome()+" ("+candidato.getEmpresa().getNome()+")", TipoPessoa.CANDIDATO));
			
			Collection<Colaborador> colaboradores = colaboradorManager.findByCpf(cpfSemMascara, empresaId, id, false); 
			for (Colaborador colaborador : colaboradores)
				pessoas.add(new Pessoa(colaborador.getId(), colaborador.getNomeMatricula()+" ("+colaborador.getEmpresa().getNome()+")", TipoPessoa.COLABORADOR));
		}

		return pessoas;
	}
}