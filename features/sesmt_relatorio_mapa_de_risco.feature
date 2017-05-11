# language: pt

Funcionalidade: Relatório de Mapa de Risco

  Cenário: Relatório de Mapa de Risco
    Dado que exista um ambiente "Desenvolvimento" com o risco "Ler"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > Mapa de Risco"

    Então eu devo ver o título "Mapa de Risco"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do confirmar e clico no ok
    E eu marco "Empresa Padrão - Estabelecimento Padrão"
    E eu marco "Estabelecimento Padrão - Desenvolvimento"
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem riscos vinculados ou o grau dos riscos não foi definido para os ambientes selecionados."

    Então eu acesso o menu "SESMT > Cadastros > Ambientes"
    E eu clico em editar "Desenvolvimento"
    E eu clico em editar "25/07/2011"
    E eu seleciono (JS) "M" de "grauDeRisco1"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "SESMT > Relatórios > Mapa de Risco"
    Entao eu devo ver o título "Mapa de Risco"
    E eu marco "Empresa Padrão - Estabelecimento Padrão"
    E eu marco "Estabelecimento Padrão - Desenvolvimento"
    E eu clico no botão "Relatorio"
    E eu não devo ver "Não existem riscos vinculados ou o grau dos riscos não foi definido para os ambientes selecionados."
    E eu devo ver o título "Mapa de Risco"













