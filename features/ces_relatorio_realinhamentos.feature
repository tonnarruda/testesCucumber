# language: pt

Funcionalidade: Relatório de Planejamento de Realinhamentos

  Cenário: Funcionalidade: Relatório de  Realinhamentos
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "estabelecimento"
    Dado que exista a área organizacional "geral"
    Dado que exista a tabela de reajuste "reajuste" na data "22/02/2016" aprovada "true" com o tipo de reajuste "C"

    Quando eu acesso o menu "C&S > Relatórios > Realinhamentos"
    Então eu devo ver o título "Planejamento de Realinhamentos"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu preencho campo pelo id "inicio" com "01/01/2016"
    E eu saio do campo "inicio"
    E eu espero 1 segundo
    E eu seleciono "reajuste" de "Selecione o Planejamento"
    E eu marco "estabelecimento"
    E eu marco "geral"


    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."
