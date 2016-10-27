# language: pt

Funcionalidade: Manutenção de Extintores

  Cenário: Cadastro de Manutenção de Extintores
    Dado que exista um extintor localizado em "garagem"    
    
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Movimentações > Extintores > Troca de Localização"
    Então eu devo ver o título "Extintores - Troca de Localização"
    E eu clico no botão "TrocaExtintores"
    Então eu devo ver o título "Troca de Localização"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Extintores - Troca de Localização"

    Então eu clico no botão "TrocaExtintores"
    E eu devo ver o título "Troca de Localização"
    E eu marco "AG - 123 / garagem"
    E eu preencho o campo (JS) "dataHist" com "16/08/2013"
    E eu preencho o campo (JS) "hora" com "00:00"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu preencho "Localização" com "CASA DE MAQUINAS"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Extintores - Troca de Localização"
    E eu devo ver "CASA DE MAQUINAS"

    Entao eu clico em editar "16/08/2013"
    E eu devo ver o título "Edição de Histórico do Extintor AG - 123"
    E o campo "dataHist" deve conter "16/08/2013"
    E eu preencho o campo (JS) "dataHist" com "15/08/2013"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Extintores - Troca de Localização"
    E eu não devo ver "24/11/2011"

    Então eu clico em excluir "15/08/2013"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "15/08/2013"
    E eu devo ver "24/11/2011"
    E eu devo ver "garagem"

