# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Curriculo Digitado do Candidato
    Dado que eu esteja logado
	Dado que exista o cargo "baba"
	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "InserirCurriculoDigitado"
    Então eu devo ver o título "Currículo Digitado"

	Quando eu preencho "Nome" com "maria"
	E eu seleciono "Feminino" de "Sexo"
	E eu marco "baba"
	E eu seleciono "Temporário" de "Colocação"
	E eu preencho "Currículo" com "blah blah blah blah blah"
	Então eu clico no botão "Gravar"
	E eu devo ver "Currículo (maria) cadastrado com sucesso."
	
    Quando eu clico no botão "Cancelar"
    Então eu devo ver o título "Candidatos"
    E eu devo ver "maria"
    Quando eu clico no botão "Triagem"
    Então eu devo ver o título "Triagem de currículos"
   
    Quando eu clico "Busca Simples"
    E eu clico no botão "Pesquisar"
    Então eu devo ver "maria"
    E eu devo ver "Total de Candidatos: 1"
   
    Quando eu clico "Busca Avançada"
    E eu clico no botão "Pesquisar"
    Então eu devo ver "maria"
    E eu devo ver "Total de Candidatos: 1"

	#busca no F2rh de verdade
    Quando eu clico "Busca no F2rh"
    E eu clico no botão "Pesquisar"
    E eu espero 4 segundos
    Então eu devo ver "Fortaleza/CE"