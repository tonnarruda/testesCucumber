# language: pt

Funcionalidade: Natureza de Lesão

  Cenário: Cadastro de Natureza da Lesão
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Natureza da Lesão"
    Então eu devo ver o título "Natureza da Lesão"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Natureza da Lesão"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Natureza da Lesão"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "Natureza 1"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Natureza da Lesão"
    Então eu devo ver " 1"

    Entao eu clico em editar ""
    E eu devo ver o título "Editar Natureza da Lesão"
    E o campo "Descrição" deve conter "Natureza 1"
    E eu preencho "Descrição" com "Natureza 2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Natureza da Lesão"
    Então eu devo ver "Natureza 2"

    Então eu clico em excluir "Natureza 2"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Natureza da Lesão excluída com sucesso."
    Então eu não devo ver na listagem "Natureza 1"












