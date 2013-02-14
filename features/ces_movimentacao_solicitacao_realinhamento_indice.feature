# language: pt

Funcionalidade: Solicitação de Realinhamento por Índice

  Cenário: Reajuste Coletivo/Dissídio Índice
    Dado que eu esteja logado
    Dado que exista a tabela de reajuste "ajustar" na data "01/02/2013" aprovada "false" com o tipo de reajuste "I"
    Dado que exista um indice "indice" com historico

    Quando eu acesso o menu "C&S > Movimentações > Solicitação de Realinhamento > Índice"
    
    E eu devo ver "Solicitação de Realinhamento de Cargos e Salários por Índice"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    
    E eu seleciono "ajustar" de "Planejamento de Realinhamento"
    E eu seleciono "indice" de "Índice"
    E eu preencho "valorDissidio" com "2"
    E eu clico no botão "Gravar"
    
    Então eu devo ver o título "Solicitação de Realinhamento de Cargos e Salários por Índice"
    Então eu devo ver "Proposta de reajuste gravada com sucesso"
