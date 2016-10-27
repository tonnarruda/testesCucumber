# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Idiomas do Candidato
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Candidato"
    E eu clico na aba "Formação Escolar"
    
    Quando eu aperto "inserirIdioma"
    E eu devo ver "Inserir Idioma"
	E eu seleciono "Francês" de "Idioma"
	E eu seleciono "Avançado" de "Nível"
    Então eu clico no botão "Gravar"
    E eu devo ver "Idiomas"
    E eu devo ver "Francês"
    
    Quando eu aperto "inserirIdioma"
	E eu seleciono "Inglês" de "Idioma"
    Então eu clico no botão "Gravar"
    
    Quando eu aperto "inserirIdioma"
	E eu seleciono "Espanhol" de "Idioma"
    Então eu clico no botão "Gravar"

    Quando eu aperto "inserirIdioma"
	E eu seleciono "Italiano" de "Idioma"
    Então eu clico no botão "Gravar"

    Quando eu aperto "inserirIdioma"
	E eu seleciono "Alemão" de "Idioma"
    Então eu clico no botão "Gravar"
    
    Então eu clico em excluir "Italiano"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "Italiano"
    
    Quando eu aperto "inserirIdioma"
	Então eu devo ver "Inserir Idioma"