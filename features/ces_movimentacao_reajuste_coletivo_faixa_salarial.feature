# language: pt

Funcionalidade: Reajuste Coletivo/Dissídio Faixa Salarial

  Cenário: Reajuste Coletivo/Dissídio Faixa Salarial
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a tabela de reajuste "ajustar" na data "01/01/2013" aprovada "false" com o tipo de reajuste "F"
    Dado que exista o cargo "Desenvolvedor"
    Dado que exista a faixa salarial "faixa 1" no cargo "Desenvolvedor"
    Dado que exista um historico para a faixa salarial "faixa 1" na data "20/01/2013"

    Quando eu acesso o menu "C&S > Movimentações > Reajuste Coletivo > Faixas Salariais"
    Então eu devo ver o título "Reajuste Coletivo/Dissídio"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "ajustar" de "Tabela de Reajuste"
    E eu marco "Desenvolvedor"
    E eu marco "faixa 1"
    E eu preencho "valorDissidio" com "2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Planejamento de Realinhamento por Faixa Salarial"
    Então eu devo ver "Desenvolvedor faixa 1"
    Então eu devo ver "510,00"
    E eu devo ver "ajustar"
    E eu devo ver "01/01/2013"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Planejamentos de Realinhamentos"