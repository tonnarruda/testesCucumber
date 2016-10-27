# language: pt

Funcionalidade: Relatório de Descrição de Cargos

  Cenário: Relatório de Descrição de Cargos
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "estabelecimento"
    Dado que exista a área organizacional "geral"
    Dado que exista o cargo "cargo"
    Quando eu acesso o menu "C&S > Relatórios > Colaboradores por Cargo"
    Então eu devo ver o título "Colaboradores por Cargo"
    E eu clico no botão "Relatorio"
    Então eu devo ver "Não existem dados para o filtro informado."

    E eu marco "relatorioColaboradorCargo_exibColabAdmitido"
    E eu preencho "qtdMeses" com "12"
    E eu marco "relatorioColaboradorCargo_exibColabDesatualizado"
    E eu preencho "qtdMesesAtualizacao" com "2"
    E eu marco "estabelecimento"
    E eu marco "geral"
    E eu marco "cargo"

    E eu clico no botão "Relatorio"
    Então eu devo ver "Não existem dados para o filtro informado."

