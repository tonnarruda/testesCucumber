# language: pt

Funcionalidade: Manutenção de Extintores

  Cenário: Cadastro de Manutenção de Extintores
    Dado que exista um extintor localizado em "garagem"    
    
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Movimentações > Extintores > Manutenção"
    Então eu devo ver o título "Extintores - Manutenção"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Manutenção de Extintor"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Extintores - Manutenção"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Manutenção de Extintor"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "AG - 123" de "Extintor"
    E eu preencho o campo (JS) "saida" com "28/07/2011"
    E eu preencho o campo (JS) "retorno" com "01/08/2011"
    E eu seleciono "Prazo de recarga vencido" de "Motivo"
    E eu marco "Recarga"
    E eu marco "Manômetro"
    E eu marco "Substituição de Gatilho"
    E eu marco "Válvula Completa"
    E eu marco "Mangote"
    E eu preencho "Observações" com "urgente!"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Inserir Manutenção de Extintor"
    E eu devo ver "Manutenção gravada com sucesso."
    Então eu clico no botão "Voltar"

    Entao eu clico em editar "28/07/2011"
    E eu devo ver o título "Editar Manutenção de Extintor"
    E o campo "saida" deve conter "28/07/2011"
    E eu preencho o campo (JS) "saida" com "25/07/2011"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Extintores - Manutenção"

    Então eu clico em excluir "25/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "25/07/2011"

