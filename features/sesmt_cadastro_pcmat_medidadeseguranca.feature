# language: pt

Funcionalidade: Medidas de Segurança

  Cenário: Cadastro de Medidaa de Segurança
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > PCMAT > Medidas de Segurança"

    Então eu devo ver o título "Medidas de Segurança"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Medida de Segurança"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Medidas de Segurança"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "Usar Capacete com jugular"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Medidas de Segurança"
    Então eu devo ver "Usar Capacete com jugular"

    Entao eu clico em editar "Usar Capacete com jugular"
    E eu devo ver o título "Editar Medida de Segurança"
    E o campo "Descrição" deve conter "Usar Capacete com jugular"
    E eu preencho "Descrição" com "Usar Capacete com jugular e protetor auricular"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Medidas de Segurança"
    Então eu devo ver "Usar Capacete com jugular e protetor auricular"

    Então eu clico em excluir "Usar Capacete com jugular e protetor auricular"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Medida de segurança excluída com sucesso."
    Então eu não devo ver na listagem "Usar Capacete com jugular e protetor auricular"












