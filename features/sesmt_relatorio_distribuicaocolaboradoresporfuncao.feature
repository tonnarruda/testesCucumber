# language: pt

Funcionalidade: Relatório de Distribuição de Colaboradores por Função

  Cenário: Relatório de Distribuição de Colaboradores por Função

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > Distribuição de Colaboradores por Função"

    Então eu devo ver o título "Distribuição de Colaboradores por Função"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho o campo (JS) "data" com "29/01/2011"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para relatório"