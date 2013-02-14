# language: pt

Funcionalidade: Solicitação de Realinhamento por Faixa Salarial

  Cenário: Reajuste Coletivo/Dissídio Faixa Salarial
    Dado que eu esteja logado
    Dado que exista a tabela de reajuste "ajustar" na data "01/01/2013" aprovada "false" com o tipo de reajuste "F"
    Dado que exista o cargo "Desenvolvedor"
    Dado que exista a faixa salarial "faixa 1" no cargo "Desenvolvedor"
    Dado que exista um historico para a faixa salarial "faixa 1" na data "20/01/2013"

    Quando eu acesso o menu "C&S > Movimentações > Solicitação de Realinhamento > Faixa Salarial"

    Então eu devo ver o título "Solicitação de Realinhamento de Cargos e Salários por Faixa Salarial"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    
    E eu seleciono "ajustar" de "Planejamento de Realinhamento"
    E eu seleciono "Desenvolvedor" de "Cargo"
    E eu seleciono "Desenvolvedor faixa 1" de "Faixa Salarial"
    E eu preencho "valorDissidio" com "2"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Solicitação de Realinhamento de Cargos e Salários por Faixa Salarial"
    Então eu devo ver "Proposta de reajuste gravada com sucesso"
