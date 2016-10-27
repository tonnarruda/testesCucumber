# language: pt

Funcionalidade: Tipo do Documento

  Cenário: Cadastro de Tipo do Documento
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipo do Documento"
    Então eu devo ver o título "Tipo do Documento"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Tipo do Documento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Tipo do Documento"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "txt"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Tipo do Documento"
    Então eu devo ver "txt"

    Entao eu clico em editar "txt"
    E eu devo ver o título "Editar Tipo do Documento"
    E o campo "Descrição" deve conter "txt"
    E eu preencho "Descrição" com "doc"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Tipo do Documento"
    Então eu não devo ver na listagem "txt"
    Então eu devo ver "doc"

    Então eu clico em excluir "doc"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Tipo do Documento excluído com sucesso."
    Então eu não devo ver na listagem "doc"












