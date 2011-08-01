# language: pt

Funcionalidade: Relatório de CATs

  Cenário: Relatório de CATs

    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Relatórios > CATs (Acidentes de Trabalho)"
    Então eu devo ver o título "CATs (Acidentes de Trabalho)"

    Então eu preencho o campo (JS) "inicio" com "01/01/2011"
    E eu preencho o campo (JS) "fim" com "29/07/2011"
    E eu marco "Estabelecimento Padrão"
    E eu preencho "Colaborador" com "marieta"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Não há CAT's para o filtro informado."