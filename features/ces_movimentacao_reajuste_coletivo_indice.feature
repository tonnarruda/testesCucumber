# language: pt

Funcionalidade: Reajuste Coletivo/Dissídio Índice

  Cenário: Reajuste Coletivo/Dissídio Índice
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a tabela de reajuste "ajustar" na data "01/02/2013" aprovada "false" com o tipo de reajuste "I"
    Dado que exista um indice "indice" com historico na data "01/01/2013" e valor "5000.00"

    Quando eu acesso o menu "C&S > Movimentações > Reajuste Coletivo > Índices"
    Então eu devo ver o título "Reajuste Coletivo/Dissídio"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "ajustar" de "Tabela de Reajuste"
    E eu marco "indice"
    E eu preencho "valorDissidio" com "2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Planejamento de Realinhamento por Índice"
    Então eu devo ver "indice"
    Então eu devo ver "5.100,00"
    E eu devo ver "ajustar"
    E eu devo ver "01/02/2013"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Planejamentos de Realinhamentos"