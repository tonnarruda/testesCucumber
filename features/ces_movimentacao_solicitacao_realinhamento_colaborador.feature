# language: pt

Funcionalidade: Solicitação de Realinhamento por Colaborador

  Cenário: Reajuste Coletivo/Dissídio
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a tabela de reajuste "ajustar" na data "01/02/2010" aprovada "false" com o tipo de reajuste "C"
    Dado que exista a tabela de reajuste "alinhar" na data "01/02/2012" aprovada "false" com o tipo de reajuste "C"
    Dado que exista a tabela de reajuste "bento aliamento" na data "01/02/2013" aprovada "false" com o tipo de reajuste "C"
    Dado que exista um colaborador "joao", da area "geral", com o cargo "motorista" e a faixa salarial "motorista I"
    Dado que exista um colaborador "maria", da area "desenvolvimento", com o cargo "programador" e a faixa salarial "nivel I"
    Dado que exista um colaborador "bento", da area "suporte", com o cargo "tecnico" e a faixa salarial "bento I"

    Quando eu acesso o menu "C&S > Movimentações > Solicitação de Realinhamento para Colaborador"
    Então eu devo ver o título "Solicitação de Realinhamento de Cargos & Salários para Colaborador"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    
    E eu seleciono "ajustar" de "Planejamento de Realinhamento"
    E eu seleciono "geral" de "Áreas Organizacionais"
    E eu seleciono "joao (joao)" de "Colaborador"
    Então eu devo ver o alert "A data da solicitação de realinhamento deve ser maior que a data de admissão do colaborador." e clico no ok

    E eu seleciono "alinhar" de "Planejamento de Realinhamento"
    E eu seleciono "desenvolvimento" de "Áreas Organizacionais"
    E eu seleciono "maria (maria)" de "Colaborador"

    E eu seleciono (JS) "-1" de "estabelecimentoProposta"
    E eu seleciono (JS) "-1" de "areaOrganizacionalProposta"
    E eu seleciono (JS) "-1" de "faixa"
    E eu seleciono (JS) "1" de "tipoSalario"
    E eu preencho o campo (JS) "" com "wwctrl_salarioProposto"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu seleciono "alinhar" de "Planejamento de Realinhamento"
    E eu seleciono "desenvolvimento" de "Áreas Organizacionais"
    E eu seleciono "maria (maria)" de "Colaborador"

    E eu seleciono (JS) "1" de "estabelecimentoProposta"
    E eu seleciono (JS) "2" de "areaOrganizacionalProposta"

    E eu seleciono (JS) "2" de "faixa"

    E eu seleciono (JS) "1" de "tipoSalario"
    E eu preencho o campo (JS) "1.000,00" com "wwctrl_salarioProposto"
    E eu preencho o campo (JS) "teste" com "observacao"

    E eu clico no botão "Gravar"
    Então eu devo ver "Solicitação de realinhamento incluída com sucesso"

    Dado que exista um indice "Indice I" com historico na data "01/01/2013" e valor "5000.00"

    E eu seleciono "bento aliamento" de "Planejamento de Realinhamento"
    E eu seleciono "suporte" de "Áreas Organizacionais"
    E eu seleciono "bento (bento)" de "Colaborador"

    E eu seleciono (JS) "-1" de "estabelecimentoProposta"
    E eu seleciono (JS) "-1" de "areaOrganizacionalProposta"
    E eu seleciono (JS) "3" de "faixa"
    E eu seleciono (JS) "2" de "tipoSalario"
    E eu seleciono (JS) "1" de "indice"
    E eu preencho o campo (JS) "3" com "quantidade"

    E eu clico no botão "Gravar"
    Então eu devo ver "Solicitação de realinhamento incluída com sucesso"




