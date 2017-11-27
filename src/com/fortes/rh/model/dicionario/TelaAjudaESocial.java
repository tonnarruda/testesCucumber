package com.fortes.rh.model.dicionario;


public class TelaAjudaESocial 
{
	public static final String EDICAO_RISCO = "EDICAO_RISCO";
	public static final String EDICAO_HISTORICO_FUNCAO = "EDICAO_HISTORICO_FUNCAO";
	public static final String EDICAO_FAIXA_SALARIAL = "EDICAO_FAIXA_SALARIAL";
	public static final String LISTAGEM_FAIXA_SALARIAL = "LISTAGEM_FAIXA_SALARIAL";
	public static final String EDICAO_CAT = "EDICAO_CAT";
	
	public static String versaoRh;
	
	public static String getMsgAjusdaEsocial(String telaAjuda, boolean empresaIntegradaEAderiuAoESocial){
		
		String msgAjuda = ""; 
		
		switch (telaAjuda){
			case EDICAO_RISCO:
				versaoRh = "1.1.185.217";
				msgAjuda = getEdicaoRisco();
				break;
			case EDICAO_HISTORICO_FUNCAO:
				versaoRh = "1.1.185.217";
				msgAjuda = getEdicaoHistoricoFuncao();
				break;
			case EDICAO_FAIXA_SALARIAL:
				if(!empresaIntegradaEAderiuAoESocial)
					return null;
				versaoRh = "1.1.184.216";
				msgAjuda = getEdicaoFaixaSalarial();
				break;
			case LISTAGEM_FAIXA_SALARIAL:
				if(!empresaIntegradaEAderiuAoESocial)
					return null;
				versaoRh = "1.1.184.216";
				msgAjuda = getListagemFaixaSalarial();
				break;
			case EDICAO_CAT:
				versaoRh = "1.1.190.220";
				msgAjuda = getEdicaoCat();
				break;
		}
		
		if(versaoRh == null)
			return null;
		
		return getMsgInicial() + msgAjuda;
	}
	
	public static String getMsgInicial(){
		return 	"Estamos nos adequando as exigências impostas pelo Governo Federal para atender as normas do eSocial.<br><br>"+
				"Desta forma, a partir da versão <strong>" + versaoRh + "</strong>, ";
	}
	
	public static String getEdicaoRisco(){
		 return "o cadastro de riscos passa a ter dois novos campos:<br><br>" + 
				"<strong>Tipo de risco eSocial:</strong> Classificação de riscos definida pelo eSocial na tabela 23 de seu leiaute.<br><br>"+
				"<strong>Fator de risco:</strong> Detalhamento dos riscos de acordo com a classificação do eSocial. Define todos os riscos que o colaborador " + 
				"poderá estar exposto.";
	}
	
	public static String getEdicaoHistoricoFuncao(){
		return  "o histórico da função passa a contemplar as informações de "+
				"<strong>Nome da Função</strong> e <strong>CBO</strong>.";
		
	}
	
	public static String getEdicaoFaixaSalarial(){
		return 	"a edição dos campos " + 
				"<strong>Descrição no Fortes Pessoal</strong> e <strong>CBO</strong> só poderão ser efetuadas pelo Fortes Pessoal.";
		
	}
	
	public static String getListagemFaixaSalarial(){
		return 	"a inclusão e exclusão das faixas salariais só poderão ser realizadas no Fortes Pessoal.";
	}
	
	public static String getEdicaoCat(){
		return "cat";
	}
}