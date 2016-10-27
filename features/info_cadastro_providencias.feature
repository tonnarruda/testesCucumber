# language: pt

Funcionalidade: Providências

  Cenário: Cadastro de Providências
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
    Então eu devo ver o título "Providências"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Providência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Providências"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "não faltar"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Providências"
    Então eu devo ver "não faltar"

    Entao eu clico em editar "não faltar"
    E eu devo ver o título "Editar Providência"
    E o campo "Descrição" deve conter "não faltar"
    E eu preencho "Descrição" com "evitar acidente"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Providências"
    Então eu não devo ver na listagem "não faltar"
    Então eu devo ver "evitar acidente"

    Então eu clico em excluir "evitar acidente"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Providência excluída com sucesso."
    Então eu não devo ver na listagem "evitar acidente"












