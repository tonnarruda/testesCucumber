# language: pt

Funcionalidade: Inserir Curriculum Digitatado

  @dev
  Cenário: Validar Campos Obrigatórios
      Dado que eu esteja logado com o usuário "SOS"	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
         E eu clico no botão "InserirCurriculoDigitado"
	 Então eu clico no botão "Gravar"
	     E eu devo ver o alert do confirmar e clico no ok
     Então eu preencho "Nome" com "Ana Paula Santos"   
         E eu seleciono "Feminino" de "Sexo"
	 Então eu clico no botão "Gravar"
	     E eu devo ver o alert do confirmar e clico no ok	
 
 #------------------------------------------------------------------------------------------------

 Cenário: Cadastro de Curriculo Digitado do Candidato com mais de 5 Cargos Selecionados
      Dado que exista o cargo "Desenvolvedor Java"
      Dado que exista o cargo "Desenvolvedor Ruby"
      Dado que exista o cargo "Analista de Testes"
      Dado que exista o cargo "QA Engineer"
      Dado que exista o cargo "Gerente de Produto"
      Dado que exista o cargo "Líder de Equipe"
      Dado que eu esteja logado com o usuário "SOS"	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    E eu clico no botão "InserirCurriculoDigitado"

	Então eu preencho "Nome" com "Ana Paula Santos"
	E eu seleciono "Feminino" de "Sexo"
    E eu marco "Desenvolvedor Java"
    E eu marco "Desenvolvedor Ruby"
    E eu marco "Analista de Testes"
    E eu marco "QA Engineer"
    E eu marco "Gerente de Produto"
    E eu marco "Líder de Equipe"
	E eu seleciono "Temporário" de "Colocação"
	E eu preencho "Currículo" com "Curriculum Digitado do Empregado"
	Então eu clico no botão "Gravar"
	E eu devo ver o alert do confirmar e clico no ok

 #------------------------------------------------------------------------------------------------

 Cenário: Cadastro de Curriculo Digitado do Candidato com mais de 5 Cargos Selecionados
      Dado que exista o cargo "Desenvolvedor Java"
      Dado que exista o cargo "Desenvolvedor Ruby"
      Dado que exista o cargo "Analista de Testes"
      Dado que exista o cargo "QA Engineer"
      Dado que exista o cargo "Gerente de Produto"
      Dado que exista o cargo "Líder de Equipe"
      Dado que eu esteja logado com o usuário "SOS"	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
         E eu clico no botão "InserirCurriculoDigitado"
	 Então eu preencho "Nome" com "Ana Paula Santos"
         E eu seleciono "Feminino" de "Sexo"
         E eu marco "Analista de Testes"
         E eu marco "QA Engineer"
         E eu seleciono "Temporário" de "Colocação"
         E eu preencho "Currículo" com "Curriculum Digitado do Empregado"
 	Então eu clico no botão "Gravar"
         E eu devo ver "Currículo (Ana Paula Santos) cadastrado com sucesso."     

 #------------------------------------------------------------------------------------------------
  @dev1
  Cenário: Cadastro de Curriculo escaneado do Candidato existente em outra empresa
    Dado que exista uma empresa "Fortes"
    Dado que exista um candidato "Skye" na empresa "Fortes"
    Dado que exista o cargo "Analista de Sistemas Junior"
    Dado que eu esteja logado com o usuário "SOS"
	
	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
        E eu clico no botão "InserirCurriculoDigitado"
    Então eu preencho "Nome" com "Melinda May"    
        E eu marco "Analista de Sistemas Junior"
        E eu preencho o campo (JS) "CPF" com "060.607.223-34"
         E eu seleciono "Feminino" de "Sexo"
    Então eu preencho "Currículo" com "Currúculum Escaneado"
        E eu clico no botão "Gravar"
    Então eu devo ver "Currículo (Melinda May) cadastrado com sucesso."                     