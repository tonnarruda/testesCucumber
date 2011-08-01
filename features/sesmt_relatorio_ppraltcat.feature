# language: pt

Funcionalidade: Relatório de PPRA e LTCAT

  Cenário: Relatório de PPRA e LTCAT

    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Relatórios > PPRA e LTCAT"
    Então eu devo ver o título "PPRA e LTCAT"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert "Selecione o relatório a ser gerado." e clico no ok

    Então eu marco "Gerar PPRA"
    E eu marco "Gerar LTCAT"
    E eu clico no botão "Relatorio"

    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho o campo (JS) "data" com "29/07/2011"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
