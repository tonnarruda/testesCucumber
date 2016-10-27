# language: pt

Funcionalidade: Relatório de Colaboradores por Grupo Ocupacional

  Cenário: Relatório de Colaboradores por Grupo Ocupacional
    
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "Matriz"
    Dado que haja uma area organizacional com id 1, nome "Financeiro", ativo "true", empresa_id 1
    Dado que haja uma area organizacional com id 2, nome "Almoxarifado", ativo "false", empresa_id 1
    
    Quando eu acesso o menu "C&S > Relatórios > Colaboradores por Grupo Ocupacional"
    Então eu devo ver o título "Colaboradores por Grupo Ocupacional"
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."

    Então eu marco "Matriz"
    E eu marco "Financeiro"
    
    Então eu seleciono "Ativos" de "listCheckBoxActiveareaOrganizacionalsCheck"
    E eu espero 1 segundo
    E eu devo ver "Financeiro"

    Então eu seleciono "Todos" de "listCheckBoxActiveareaOrganizacionalsCheck"
    E eu espero 1 segundo
    E eu devo ver "Financeiro"
    E eu devo ver "Almoxarifado"
    
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."