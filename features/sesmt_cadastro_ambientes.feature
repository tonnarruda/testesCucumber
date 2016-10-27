# language: pt

Funcionalidade: Ambientes

  Cenário: Cadastro de Ambientes
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Ambientes"
    Então eu devo ver o título "Ambientes"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Ambiente"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Ambientes"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Ambiente"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu preencho "Nome" com "garagem"
    E eu preencho o campo (JS) "A partir de" com "01/01/2011"
    E eu preencho "Descrição do Ambiente" com "teste"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Ambientes"
    Então eu devo ver "garagem"

    Entao eu clico em editar "garagem"
    E eu devo ver o título "Editar Ambiente"
    E o campo "Nome" deve conter "garagem"
    E eu preencho "Nome" com "patio"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Ambientes"
    Então eu devo ver "patio"

    Então eu clico em excluir "patio"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Ambiente excluído com sucesso."
    Então eu não devo ver na listagem "patio"












