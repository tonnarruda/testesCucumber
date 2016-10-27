# language: pt

Funcionalidade: Cadastrar Índices

  Cenário: Cadastro de Índices
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
    Então eu devo ver o título "Índices"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Índice"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Índices"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Índice"
    E eu preencho "Nome" com "_Diretor"
    E eu preencho o campo (JS) "A partir de" com "01/01/2011"
    E eu preencho "Valor" com "17000"
    E eu clico no botão "Gravar"

    E eu devo ver o título "Índices"
    E eu clico em editar "_Diretor"
    E eu devo ver o título "Editar Índice"
    E o campo "Nome" deve conter "_Diretor"
    
    E eu clico em editar "01/01/2011"
    E eu devo ver o título "Editar Histórico do Índice"
    E o campo "Valor" deve conter "17.000"
    E eu preencho "Valor" com "20000"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Editar Índice"
    E o campo "Nome" deve conter "_Diretor"
    E eu preencho "Nome" com "_Consultor"
    E eu clico no botão "Gravar"

    E eu devo ver o título "Índices"
    E eu devo ver "_Consultor"
    E eu clico em editar "_Consultor"

    E eu devo ver o título "Editar Índice"
    Então eu clico no botão "Inserir"

    Então eu devo ver o título "Novo Histórico do Índice"
    E eu preencho o campo (JS) "A partir de" com "01/03/2011"
    E eu preencho "Valor" com "25000"
    Então eu clico no botão "Gravar"

    Então eu clico em excluir "01/01/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "01/01/2011"
    Então eu clico no botão "Gravar"

    Então eu devo ver o título "Índices"
    E eu clico em excluir "_Consultor"
    E eu devo ver o alert do confirmar exclusão e clico no ok

    Então eu devo ver "Índice excluído com sucesso."
    E eu não devo ver "_Consultor"