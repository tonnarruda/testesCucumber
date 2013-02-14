# language: pt

Funcionalidade: Reajuste Coletivo/Dissídio Colaborador

  Cenário: Reajuste Coletivo/Dissídio
    Dado que eu esteja logado
    Dado que exista a tabela de reajuste "ajustar" na data "01/02/2010" aprovada "false" com o tipo de reajuste "C"
    Dado que exista um colaborador "joao", da area "geral", com o cargo "motorista" e a faixa salarial "motorista I"
    Quando eu acesso o menu "C&S > Movimentações > Solicitação de Realinhamento > Colaborador"
    Então eu devo ver o título "Solicitação de Realinhamento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "ajustar" de "Planejamento de Realinhamento"
    E eu seleciono "geral" de "Áreas Organizacionais"
    E eu seleciono "joao (joao)" de "Colaborador"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
