# language: pt

Funcionalidade: Reajuste Coletivo/Dissídio

  Cenário: Reajuste Coletivo/Dissídio
    Dado que eu esteja logado
    Dado que exista a tabela de reajuste "ajustar" na data "01/02/2010" aprovada "false"
    Dado que exista o estabelecimento "centro"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "C&S > Movimentações > Reajuste Coletivo"
    Então eu devo ver o título "Reajuste Coletivo/Dissídio"
    E eu clico no botão "Aplicar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "ajustar" de "Tabela de Reajuste"
    E eu marco "geral"
    E eu marco "centro"
    E eu preencho "valorDissidio" com "2"
    E eu clico no botão "Aplicar"
    Então eu devo ver o título "Planejamento de Realinhamentos"
    E eu devo ver "ajustar"
    E eu devo ver "01/02/2010"
    E eu devo ver "Não existem Promoções e Reajustes a serem visualizadas!"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Planejamentos de Realinhamentos"





