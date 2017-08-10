# language: pt

Funcionalidade: Triagem de Curriculum

  @dev
  Cenário: Triagem Simples de Curriculum
      Dado que exista um candidato "Ana Paula Santos"
      Dado que eu esteja logado com o usuário "SOS"	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
         E eu clico no botão "Triagem"
	     E eu clico "Triagem Simples"
         E eu clico no botão "Pesquisar"
     Então eu devo ver "Ana Paula Santos"
         E eu devo ver "Total de Candidatos: 1"
 
 #------------------------------------------------------------------------------------------------
 
  Cenário: Triagem Avançada de Curriculum
      Dado que exista um candidato "Ana Paula Santos"
      Dado que eu esteja logado com o usuário "SOS"	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
         E eu clico no botão "Triagem"
	     E eu clico "Triagem Avançada"
         E eu clico no botão "Pesquisar"
     Então eu devo ver "Ana Paula Santos"
         E eu devo ver "Total de Candidatos: 1"
 
 #------------------------------------------------------------------------------------------------