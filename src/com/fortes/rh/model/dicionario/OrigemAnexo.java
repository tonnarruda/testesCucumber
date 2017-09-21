package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.security.SecurityUtil;

@SuppressWarnings({ "serial", "rawtypes" })
public class OrigemAnexo extends LinkedHashMap
{
	public static final char LTCAT = 'A';
	public static final char PPRA = 'B';
	public static final char AnexoCandidato = 'C';
	public static final char AnexoCandidatoExterno = 'E';
	public static final char AnexoColaborador = 'D';
	public static final char Curso = 'U';
	public static final char SolicitacaoPessoal = 'S';

	@SuppressWarnings("unchecked")
	public OrigemAnexo()
	{
		super();
		put(LTCAT, "LTCAT");
		put(PPRA, "PPRA");
		put(AnexoCandidato, "AnexoCandidato");
		put(AnexoCandidatoExterno, "CandidatoExterno");
		put(AnexoColaborador, "AnexoColaborador");
		put(Curso, "Curso");
		put(SolicitacaoPessoal, "SolicitacaoPessoal");
		
	}
	
	public String diretorioOrigemAnexo(char origem)
	{
		switch (origem){
			case AnexoCandidato:
				return "documentosCandidatos";
			case AnexoCandidatoExterno:
				return "documentosCandidatos";
			case AnexoColaborador:
				return "documentosColaboradores";
			case Curso:
				return "anexosCursos";
			case SolicitacaoPessoal:
				return "anexosSolicitacoesPessoais";
		}
		
		return "anexos";
	}
	
	public String getVoltarListAnexoByOrigem(Character origem, Long solicitacaoId){
		if(solicitacaoId != null)
			return "../../captacao/candidatoSolicitacao/list.action?solicitacao.id=" + solicitacaoId;
		
		switch (origem){
			case AnexoCandidato:
				return "../../captacao/candidato/list.action";
			case AnexoCandidatoExterno:
				return "../externo/prepareListAnuncio.action";	
			case AnexoColaborador:
				return "../colaborador/list.action";
			case Curso:
				return "../../desenvolvimento/curso/list.action";
			case SolicitacaoPessoal:
				return "../../captacao/solicitacao/list.action";
		}
		
		return "";
	}
	
	public boolean possuiPermissao(Character origem, Long solicitacaoId, Map session) {
		
		String role = "";
		if(solicitacaoId != null)
			role = "ROLE_CAND_SOLICITACAO_DOCUMENTOANEXO";
		else{
			switch (origem){
				case AnexoCandidato:
					role =  "ROLE_CAND_LIST_DOCUMENTOANEXO";
					break;
				case AnexoColaborador:
					role =  "ROLE_COLAB_LIST_DOCUMENTOANEXO";
					break;
				case Curso:
					role =  "ROLE_MOV_CURSO";
					break;
				case SolicitacaoPessoal:
					role =  "ROLE_MOV_SOLICITACAO_ANEXAR";
					break;
				case AnexoCandidatoExterno:
					return true;
			}
		}

		if(!"".equals(role))
			return SecurityUtil.verifyRole(session, new String[]{role});
		else
			return false;
	}
}
