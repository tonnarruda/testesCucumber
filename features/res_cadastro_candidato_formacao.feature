# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Formação do Candidato
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Candidato"
    E eu clico na aba "Formação Escolar"
    Quando eu aperto "inserirFormacao"
    E eu devo ver "Inserir Formação"
    Quando eu clico no botão "Gravar"
	Então eu devo ver o alert do confirmar e clico no ok
	E eu seleciono "Artes" de "Área de Formação"
	E eu preencho "Curso" com "java"
	E eu preencho "Instituição de ensino" com "unifor"
	E eu seleciono "Mestrado" de "Tipo"
	E eu seleciono "Incompleta" de "Situação"
	E eu preencho "Conclusão" com "2012"
    Então eu clico no botão "Gravar"
    E eu devo ver "Formação Escolar"
    E eu devo ver "java"
    
    Quando eu clico em editar "java"
	Então eu devo ver "Atualizar Formação"
	Então o campo "Curso" deve conter "java"
	E o campo "Instituição de ensino" deve conter "unifor"
	Então eu preencho "Curso" com "ruby"
	E eu preencho "Instituição de ensino" com "fa7"
	Então eu clico no botão "Gravar"
    E eu devo ver "Formação Escolar"
    E eu não devo ver "java"
    E eu devo ver "ruby"
    
    Então eu clico em excluir "ruby"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "ruby"