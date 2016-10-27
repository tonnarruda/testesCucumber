# language: pt

Funcionalidade: Aplicar Planejamentos de Realinhamentos
    
  Cenário: Aplicação e Cancelamento de Planejamentos de Realinhamentos para Colaborador
    
    Dado que eu esteja logado com o usuário "SOS"

    Dado que exista a tabela de reajuste "Reajuste colaborador 1" na data "01/07/2011" aprovada "false" com o tipo de reajuste "C"
    Dado que exista a tabela de reajuste "Reajuste colaborador 2" na data "02/07/2011" aprovada "false" com o tipo de reajuste "C"
	Dado que exista um colaborador "Chico", da area "Geral", com o cargo "Desenvolvedor" e a faixa salarial "I"
    Dado que exista um reajuste para o colaborador "Chico" com a tabela de reajuste "Reajuste colaborador 1" com estabelecimento, area e faixa de id "2" com valor atual "100" e valor proposto "200"
    Dado que exista um reajuste para o colaborador "Chico" com a tabela de reajuste "Reajuste colaborador 2" com estabelecimento, area e faixa de id "3" com valor atual "100" e valor proposto "200"

    Quando eu acesso o menu "C&S > Movimentações > Planejamentos de Realinhamentos"
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico na ação "Visualizar Realinhamentos" de "Reajuste colaborador 1"
    Então eu devo ver o título "Planejamento de Realinhamento por Colaborador"
    Então eu devo ver "Reajuste colaborador 1"
	E eu clico no botão "Aplicar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu devo ver "Já existe(m) histórico(s) na data 01/07/2011 para o(s) colaborador(es) abaixo:"
    Então eu devo ver "- Chico"
	E eu clico no botão "Voltar"
	
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico na ação "Visualizar Realinhamentos" de "Reajuste colaborador 2"
    Então eu devo ver o título "Planejamento de Realinhamento por Colaborador"
    Então eu devo ver "Reajuste colaborador 2"
	E eu clico no botão "Aplicar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu devo ver "Reajuste aplicado com sucesso."
    
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico na ação "Cancelar Reajuste" de "Reajuste colaborador 2"
    E eu devo ver o alert do confirmar e clico no ok
    E eu devo ver "Cancelamento efetuado com sucesso."

  Cenário: Cadastro de Planejamentos de Realinhamentos para Faixa Salarial
    Dado que eu esteja logado com o usuário "SOS"
    
    Dado que exista a tabela de reajuste "Reajuste faixa salarial I" na data "01/01/2013" aprovada "false" com o tipo de reajuste "F"
    Dado que exista o cargo "Desenvolvedor"
    Dado que exista a faixa salarial "Faixa I" no cargo "Desenvolvedor"
    Dado que exista um historico para a faixa salarial "Faixa I" na data "01/01/2013"
    Dado que exista um reajuste para a faixa salarial "Faixa I" com a tabela de reajuste "Reajuste faixa salarial I" com valor atual "100" e valor proposto "200"
    
    Quando eu acesso o menu "C&S > Movimentações > Planejamentos de Realinhamentos"
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico na ação "Visualizar Realinhamentos" de "Reajuste faixa salarial I"
    Então eu devo ver o título "Planejamento de Realinhamento por Faixa Salarial"
    Então eu devo ver "Reajuste faixa salarial I"
	E eu clico no botão "Aplicar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu devo ver "Já existe(m) histórico(s) na data 01/01/2013 para a(s) faixa(s) salarial(is) abaixo:"
    Então eu devo ver "- Desenvolvedor Faixa I"
  
  Cenário: Cadastro de Planejamentos de Realinhamentos para Índice
    
    Dado que exista a tabela de reajuste "Reajuste índice I" na data "01/02/2013" aprovada "false" com o tipo de reajuste "I"
    Dado que exista um indice "Índice I" com historico na data "01/02/2013" e valor "5000.00"
    Dado que exista um reajuste para o indice "Índice I" com a tabela de reajuste "Reajuste índice I" com valor atual "100" e valor proposto "200"
    
    Quando eu acesso o menu "C&S > Movimentações > Planejamentos de Realinhamentos"
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico na ação "Visualizar Realinhamentos" de "Reajuste índice I"
    Então eu devo ver o título "Planejamento de Realinhamento por Índice"
    Então eu devo ver "Reajuste índice I"
	E eu clico no botão "Aplicar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu devo ver "Já existe(m) histórico(s) na data 01/02/2013 para o(s) índice(s) abaixo:"
    Então eu devo ver "- Índice I"