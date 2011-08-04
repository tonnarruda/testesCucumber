# language: pt

Funcionalidade: Relatório de Projeção Salarial

  Cenário: Relatório de Projeção Salarial
    Dado que eu esteja logado
    Dado que exista o estabelecimento "estabelecimento"
    Dado que exista a área organizacional "geral"
    Dado que exista a tabela de reajuste "reajuste" na data "28/07/2011" aprovada "false"

    Quando eu acesso o menu "C&S > Relatórios > Projeção Salarial"
    Então eu devo ver o título "Relatório de Projeção Salarial"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu preencho o campo (JS) "Data da Projeção" com "01/08/2011"
    E eu seleciono "reajuste" de "Considerar o Planejamento de Realinhamento"
    E eu marco "estabelecimento"
    E eu marco "geral"

    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."
