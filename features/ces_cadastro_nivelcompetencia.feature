# language: pt

Funcionalidade: Cadastrar Nível de Competência

  Cenário: Cadastro de Nível de Competência
    Dado que eu esteja logado

    Quando eu acesso o menu "C&S > Cadastros > Níveis de Competência"
    Então eu devo ver o título "Níveis de Competência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Nível de Competência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Níveis de Competência"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Nível de Competência"
    E eu preencho "Ordem" com "1"
    E eu preencho "Descrição" com "nivel1"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"
    E eu devo ver "nivel1"

    Entao eu clico em editar "nivel1"
    E eu devo ver o título "Editar Nível de Competência"
    E o campo "Descrição" deve conter "nivel1"
    E eu preencho "Descrição" com "nivel01"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"

    Então eu clico em excluir "nivel01"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Nível de Competência excluído com sucesso."
    E eu não devo ver "nivel01"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "1"
    E eu preencho "Descrição" com "nivel1"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "2"
    E eu preencho "Descrição" com "nivel2"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "3"
    E eu preencho "Descrição" com "nivel3"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "4"
    E eu preencho "Descrição" com "nivel4"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "5"
    E eu preencho "Descrição" com "nivel5"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "6"
    E eu preencho "Descrição" com "nivel6"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "7"
    E eu preencho "Descrição" com "nivel7"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "8"
    E eu preencho "Descrição" com "nivel8"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "9"
    E eu preencho "Descrição" com "nivel9"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "10"
    E eu preencho "Descrição" com "nivel10"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"

    Então eu clico no botão "Inserir"
    E eu preencho "Ordem" com "11"
    E eu preencho "Descrição" com "nivel11"
    E eu clico no botão "Gravar"
    Então eu devo ver "Não é permitido cadastrar mais do que dez Níveis de Competência."