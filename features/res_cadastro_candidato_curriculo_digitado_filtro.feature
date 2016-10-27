# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Curriculo Digitado do Candidato
    Dado que eu esteja logado com o usuário "SOS"
	Dado que exista o cargo "baba"
	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "InserirCurriculoDigitado"
    Então eu devo ver o título "Currículo Digitado"
    Quando eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok

	Quando eu preencho "Nome" com "maria"
	E eu seleciono "Feminino" de "Sexo"
	E eu marco "baba"
	E eu preencho o campo (JS) "Nascimento" com "01/06/2011"
	E eu preencho o campo (JS) "CPF" com "123.213.623-91"
	E eu seleciono "Temporário" de "Colocação"
	E eu preencho "Indicado Por" com "sousa"
	E eu preencho "Currículo" com "blah blah blah blah blah"
	Então eu clico no botão "Gravar"
	E eu devo ver "Currículo (maria) cadastrado com sucesso."
	
	Quando eu preencho "Nome" com "joao"
	E eu seleciono "Masculino" de "Sexo"
	E eu marco "baba"
	E eu preencho o campo (JS) "Nascimento" com "01/06/1990"
	E eu preencho o campo (JS) "CPF" com "749.539.765-92"
	E eu seleciono "Emprego" de "Colocação"
	E eu preencho "Indicado Por" com "pedro"
	E eu preencho "Currículo" com "blah blah blah blah blah"
	Então eu clico no botão "Gravar"
	E eu devo ver "Currículo (joao) cadastrado com sucesso."
	
    Quando eu clico no botão "Cancelar"
    Então eu devo ver o título "Candidatos"
    E eu devo ver "joao"
    E eu devo ver "maria"
    
    Então eu clico "Exibir Filtro"
   	E eu preencho "Nome" com "joao"
    E eu clico no botão "Pesquisar"
    Então eu devo ver "joao"
    E eu não devo ver "maria"

   	Então eu preencho o campo (JS) "CPF" com "123.213.623-91"
   	E eu preencho "Nome" com ""
    E eu clico no botão "Pesquisar"
    Então eu devo ver "maria"
    E eu não devo ver "joao"
    
    Então eu preencho o campo (JS) "CPF" com ""
   	E eu preencho "Indicado por" com "pedro"
    E eu clico no botão "Pesquisar"
    Então eu devo ver "joao"
    E eu não devo ver "maria"
    
    Então eu preencho "Nome" com "chico"
    E eu clico no botão "Pesquisar"
    Então eu não devo ver "joao"
    E eu não devo ver "maria"
    E eu devo ver "Não existem candidatos a serem listados"
    