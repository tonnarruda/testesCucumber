# language: pt

Funcionalidade: Relatório de Planejamento de Realinhamentos

  Cenário: Funcionalidade: Relatório de  Realinhamentos
    Dado que eu esteja logado
    Dado que exista o estabelecimento "estabelecimento"
    Dado que exista a área organizacional "geral"
    Dado que exista a tabela de reajuste "reajuste" na data "28/07/2011" aprovada "false" com o tipo de reajuste "C"

    Quando eu acesso o menu "C&S > Relatórios > Realinhamentos"
    Então eu devo ver o título "Relatório de Planejamento de Realinhamentos"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu seleciono "reajuste" de "Selecione o Planejamento"
    E eu marco "estabelecimento"
    E eu marco "geral"

    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."
