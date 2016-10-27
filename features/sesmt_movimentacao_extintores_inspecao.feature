# language: pt

Funcionalidade: Inspeção de Extintores

  Cenário: Cadastro de Inspeção de Extintores
    Dado que exista um extintor localizado em "garagem"    
    
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Movimentações > Extintores > Inspeção"
    Então eu devo ver o título "Extintores - Inspeção"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Inspeção de Extintor"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Extintores - Inspeção"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Inspeção de Extintor"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "AG - 123" de "Extintor"
    E eu preencho "Empresa responsável" com "extintudo"
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu marco "Lacre"
    E eu marco "Manômetro"
    E eu marco "Sinalização Vertical"
    E eu marco "Alça"
    E eu marco "Gatilho"
    E eu marco "Outro"
    E eu preencho "outroMotivo" com "suporte"
    E eu preencho "Observações" com "urgente!!"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Inserir Inspeção de Extintor"
    E eu devo ver "Inspeção gravada com sucesso."
    Então eu clico no botão "Voltar"

    Entao eu clico em editar "28/07/2011"
    E eu devo ver o título "Editar Inspeção de Extintor"
    E o campo "Data" deve conter "28/07/2011"
    E eu preencho o campo (JS) "data" com "25/07/2011"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Extintores - Inspeção"

    Então eu clico em excluir "25/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "25/07/2011"

