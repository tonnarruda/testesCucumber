package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.security.SecurityUtil;

@SuppressWarnings({ "serial", "rawtypes" })
public class OrigemAnexo extends LinkedHashMap
{
	public static final char LTCAT = 'A';
	public static final char PPRA = 'B';
	public static final char CANDIDATO = 'C';
	public static final char CANDIDATOEXTERNO = 'E';
	public static final char COLABORADOR = 'D';
	public static final char CURSO = 'U';
	public static final char SOLICITACAOPESSOAL = 'S';

	@SuppressWarnings("unchecked")
	public OrigemAnexo()
	{
		super();
		put(LTCAT, "LTCAT");
		put(PPRA, "PPRA");
		put(CANDIDATO, "AnexoCandidato");
		put(CANDIDATOEXTERNO, "CandidatoExterno");
		put(COLABORADOR, "AnexoColaborador");
		put(CURSO, "Curso");
		put(SOLICITACAOPESSOAL, "SolicitacaoPessoal");
		
	}
	
	public String diretorioOrigemAnexo(char origem)
	{
		switch (origem){
			case CANDIDATO:
				return "documentosCandidatos";
			case CANDIDATOEXTERNO:
				return "documentosCandidatos";
			case COLABORADOR:
				return "documentosColaboradores";
			case CURSO:
				return "anexosCursos";
			case SOLICITACAOPESSOAL:
				return "anexosSolicitacoesPessoais";
		}
		
		return "anexos";
	}
	
	public String getVoltarListAnexoByOrigem(Character origem, Long solicitacaoId){
		if(solicitacaoId != null)
			return "../../captacao/candidatoSolicitacao/list.action?solicitacao.id=" + solicitacaoId;
		
		switch (origem){
			case CANDIDATO:
				return "../../captacao/candidato/list.action";
			case CANDIDATOEXTERNO:
				return "../externo/prepareListAnuncio.action";	
			case COLABORADOR:
				return "../colaborador/list.action";
			case CURSO:
				return "../../desenvolvimento/curso/list.action";
			case SOLICITACAOPESSOAL:
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
				case CANDIDATO:
					role =  "ROLE_CAND_LIST_DOCUMENTOANEXO";
					break;
				case COLABORADOR:
					role =  "ROLE_COLAB_LIST_DOCUMENTOANEXO";
					break;
				case CURSO:
					role =  "ROLE_MOV_CURSO";
					break;
				case SOLICITACAOPESSOAL:
					role =  "ROLE_MOV_SOLICITACAO_DOCUMENTOS";
					break;
				case CANDIDATOEXTERNO:
					return true;
			}
		}

		if(!"".equals(role))
			return SecurityUtil.verifyRole(session, new String[]{role});
		else
			return false;
	}
}
