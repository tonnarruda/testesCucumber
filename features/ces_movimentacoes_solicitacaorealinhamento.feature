# language: pt

Funcionalidade: Cadastrar Solicitação de Realinhamento de Cargos & Salários

  Cenário: Cadastro de Solicitação de Realinhamento de Cargos & Salários
    Dado que eu esteja logado
    Quando eu acesso o menu "C&S > Movimentações > Solicitação de Realinhamento de C&S"
    Então eu devo ver o título "Solicitação de Realinhamento de Cargos & Salários"
    E eu seleciono "aumento de salario" de "Planejamento de Realinhamento"
    E eu seleciono "Administração > Desenvolvimento" de "Áreas Organizacionais"
    E eu seleciono "José Maria" de "Colaborador"
    E eu preencho "Observação" com "Teste de solicitação de realinhamento"
    E eu seleciono "Estabelecimento Padrão" de "estabelecimentoProposto"
    E eu seleciono "Administração > Desenvolvimento" de "areaOrganizacionalProposta"
    E eu seleciono "Analista Faixa II" de "faixa"
    E eu seleciono "Por índice" de "tipoSalario"
    E eu seleciono "Minimo" de "indice"
    E eu preencho "quantidade" com "2"
    Então eu clico no botão "Gravar"
    E eu devo ver "Solicitação de Realinhamento incluída com sucesso"