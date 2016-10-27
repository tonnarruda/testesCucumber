# language: pt

Funcionalidade: Extintor

  Cenário: Cadastro de Extintor
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Extintores"
    Então eu devo ver o título "Extintores"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Extintor"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Extintores"
    E eu clico no botão "Inserir"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "AP - Água Pressurizada (Classe A)" de "Tipo"
    E eu preencho "Localização" com "escada2"
    E eu preencho "Fabricante" com "extfogo"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Inserir Extintor"
    E eu devo ver "Extintor gravado com sucesso."

    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Extintores"
    E eu clico em editar "escada2"
    Então eu devo ver o título "Editar Extintor"
    E eu devo ver "escada2"
    
    Então eu clico em editar "escada2" 
    E o campo "Localização" deve conter "escada2"
    E eu preencho "Localização" com "escada3"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Editar Extintor"
    E eu devo ver "escada3"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Histórico para o Extintor AP"
    E eu preencho o campo (JS) "dataHist" com "24/11/2011"
    E eu preencho "Hora" com "08:00"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu preencho "Localização" com "escada4"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Editar Extintor"
    Então eu devo ver "escada3"
    E eu devo ver "escada4"
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Extintores"
    E eu clico em excluir "escada3"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "Extintores"
    E eu não devo ver "escada3"