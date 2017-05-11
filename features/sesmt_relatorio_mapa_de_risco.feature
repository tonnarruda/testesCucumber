# language: pt

Funcionalidade: Emissão do Relatório de Mapa de Risco

Estoria do Usuario:
  Eu como um analista de RH preciso emitir o relatório
  de Mapa de Risco dos Estabelecimentos da Empresa
  com o intuito de mapear onde existe o maior risco para
  a integridade fisica e a saude dos colaboradores.

  Cenário: Download do Relatorio de Mapa de Risco
    Dado que exista um ambiente "Desenvolvimento" com o risco "Ler"
    Dado que eu esteja logado com o usuário "SOS"
    Dado eu acesso o menu "SESMT > Cadastros > Ambientes"
       E eu clico em editar "Desenvolvimento"
       E eu clico em editar "25/07/2011"
       E eu seleciono (JS) "M" de "grauDeRisco1"
       E eu clico no botão "Gravar"

    Então eu acesso o menu "SESMT > Relatórios > Mapa de Risco"
        E eu marco "Empresa Padrão - Estabelecimento Padrão"
        E eu marco "Estabelecimento Padrão - Desenvolvimento"
        E eu clico no botão "Relatorio"
        E eu não devo ver "Não existem riscos vinculados ou o grau dos riscos não foi definido para os ambientes selecionados."
        E eu devo ver o título "Mapa de Risco"



  Cenário: Validação de mensagem quando o grau de Risco não esta configurado
    Dado que exista um ambiente "Desenvolvimento" com o risco "Ler"
    Dado que eu esteja logado com o usuário "SOS"

   Entao eu acesso o menu "SESMT > Relatórios > Mapa de Risco"
       E eu clico no botão "Relatorio"
       E eu devo ver o alert do confirmar e clico no ok
       E eu marco "Empresa Padrão - Estabelecimento Padrão"
       E eu marco "Estabelecimento Padrão - Desenvolvimento"
       E eu clico no botão "Relatorio"
       E eu devo ver "Não existem riscos vinculados ou o grau dos riscos não foi definido para os ambientes selecionados."










