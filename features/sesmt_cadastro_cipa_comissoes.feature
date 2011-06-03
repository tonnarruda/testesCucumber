# language: pt

Funcionalidade: Comissões

  Cenário: Cadastro de Comissões
    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Cadastros > CIPA > Comissões"
    Então eu devo ver o título "Comissões"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Comissão"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Comissões"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Comissão"
    E eu preencho a data "dataIni" com "01/02/2011"
    E eu preencho a data "dataFim" com "01/03/2011"
