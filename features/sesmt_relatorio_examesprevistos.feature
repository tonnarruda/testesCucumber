# language: pt

Funcionalidade: Relatório de Exames Previstos

  Cenário: Relatório de Exames Previstos
    Dado que exista um colaborador "jackson", da area "desenvolvimento", com o cargo "desenvolvedor" e a faixa salarial "I"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > Exames > Exames Previstos"
    Então eu devo ver o título "Exames Previstos"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho o campo (JS) "data" com "29/07/2011"

    E eu marco "Avaliação Clínica e Anamnese Ocupacional"
    E eu marco "Exame de Aptidões Física e Mental"
    E eu marco "Estabelecimento Padrão"
    E eu marco "desenvolvimento"
    E eu marco "jackson"

    E eu seleciono "Área Organizacional" de "agruparPor"
    E eu marco "Não imprimir afastado(s)"
    E eu marco "Imprimir desligados"
    
    Entao eu clico no botão "Relatorio"
    E eu espero 1 segundos
    Então eu devo ver o título "Exames Previstos"