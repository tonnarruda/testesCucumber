# language: pt

Funcionalidade: Relatório de Colaboradores por Áreas Organizacionais

  Cenário: Relatório de Colaboradores por Áreas Organizacionais
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "estabelecimento"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "C&S > Relatórios > Colaboradores por Área Organizacional"
    Então eu devo ver o título "Colaboradores por Área Organizacional"
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."

    Então eu marco "estabelecimento"
    E eu marco "geral"
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."
