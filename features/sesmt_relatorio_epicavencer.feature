# language: pt

Funcionalidade: Relatório de EPIs com CA a Vencer

  Cenário: Relatório de EPIs com CA a Vencer
    Dado que exista o EPI "jaleco" da categoria "vestuario"
    Dado que exista o EPI "oculos" da categoria "visao"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "SESMT > Relatórios > EPI > EPIs com CA a Vencer"
    Então eu devo ver o título "EPIs com CA a Vencer"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho o campo (JS) "data" com "29/07/2011"
    E eu marco "vestuario"
    E eu marco "visao"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Não existem EPIs com CA a vencer até a data informada."