# language: pt

Funcionalidade: Relatório de EPIs Entregues

  Cenário: Relatório de EPIs Entregues
    Dado que exista o EPI "jaleco" da categoria "vestuario"
    Dado que exista o EPI "oculos" da categoria "visao"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "SESMT > Relatórios > EPI > EPIs Entregues"
    Então eu devo ver o título "EPIs Entregues"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho o campo (JS) "periodoIni" com "01/01/2011"
    Então eu preencho o campo (JS) "periodoFim" com "29/07/2011"
    E eu marco "jaleco"
    E eu marco "oculos"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Não existem EPIs a serem listados para os filtros informados."