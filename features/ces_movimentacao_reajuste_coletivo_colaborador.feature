# language: pt

Funcionalidade: Reajuste Coletivo/Dissídio Colaborador

  Cenário: Reajuste Coletivo/Dissídio Colaborador
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a tabela de reajuste "ajustar" na data "01/01/2013" aprovada "false" com o tipo de reajuste "C"
    Dado que exista o estabelecimento "centro"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "C&S > Movimentações > Reajuste Coletivo > Colaboradores"
    Então eu devo ver o título "Reajuste Coletivo/Dissídio"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "ajustar" de "Tabela de Reajuste"
    E eu marco "geral"
    E eu marco "centro"
    E eu preencho "valorDissidio" com "2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Planejamento de Realinhamento por Colaborador"
    E eu devo ver "ajustar"
    E eu devo ver "01/01/2013"
    E eu devo ver "Não existem promoções e reajustes a serem visualizados"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Planejamentos de Realinhamentos"