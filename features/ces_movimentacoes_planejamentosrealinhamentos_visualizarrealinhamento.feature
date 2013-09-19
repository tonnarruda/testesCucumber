# language: pt

Funcionalidade: Cadastrar Planejamentos de Realinhamentos

  Cenário: Cadastro de Planejamentos de Realinhamentos para Faixa Salarial
    Dado que eu esteja logado
    
    Dado que exista a tabela de reajuste "Reajuste faixa salarial" na data "01/01/2013" aprovada "false" com o tipo de reajuste "F"
    Dado que exista o cargo "Desenvolvedor"
    Dado que exista a faixa salarial "Faixa I" no cargo "Desenvolvedor"
    Dado que exista um historico para a faixa salarial "Faixa I" na data "01/01/2013"
    Dado que exista um reajuste para a faixa salarial "Faixa I" com a tabela de reajuste "Reajuste faixa salarial" com valor atual "100" e valor proposto "200"
    
    Quando eu acesso o menu "C&S > Movimentações > Planejamentos de Realinhamentos"
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico em visualizar realinhamentos "Reajuste faixa salarial"
    Então eu devo ver o título "Planejamento de Realinhamento por Faixa Salarial"
    Então eu devo ver "Reajuste faixa salarial"
	E eu clico no botão "Aplicar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu devo ver "Já existe(m) histórico(s) na data 01/01/2013 para a(s) faixa(s) salarial(is) abaixo:"
    Então eu devo ver "- Desenvolvedor Faixa I"
  
  Cenário: Cadastro de Planejamentos de Realinhamentos para Índice
    
    Dado que exista a tabela de reajuste "Reajuste índice" na data "01/02/2013" aprovada "false" com o tipo de reajuste "I"
    Dado que exista um indice "Índice I" com historico na data "01/02/2013" e valor "5000.00"
    Dado que exista um reajuste para o indice "Índice I" com a tabela de reajuste "Reajuste índice" com valor atual "100" e valor proposto "200"
    
    
    Quando eu acesso o menu "C&S > Movimentações > Planejamentos de Realinhamentos"
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico em visualizar realinhamentos "Reajuste índice"
    Então eu devo ver o título "Planejamento de Realinhamento por Índice"
    Então eu devo ver "Reajuste índice"
	E eu clico no botão "Aplicar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu devo ver "Já existe(m) histórico(s) na data 01/02/2013 para o(s) índice(s) abaixo:"
    Então eu devo ver "- Índice I"
