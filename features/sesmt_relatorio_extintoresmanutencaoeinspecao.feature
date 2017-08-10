# language: pt

Funcionalidade: Relatório de Extintores - Manutenção e Inspeção

  Cenário: Relatório de Extintores - Manutenção e Inspeção

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > Extintores - Manutenção e Inspeção"
    Então eu devo ver o título "Extintores - Manutenção e Inspeção"
    E eu marco "Inspeção Vencida"
    E eu espero 1 segundo
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu preencho o campo (JS) "data" com "29/07/2011"
    E eu marco "Carga Vencida"
    E eu marco "Teste Hidrostático Vencido"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Nenhum extintor com prazo vencido."

#REFATORAR

#    Ajuste de inconsistência ao gerar relatório listando itens de extintores com data posterior a data do filtro de "Vencimentos até".

#TESTE MANUAL EXECUTADO COM SUCESSO