# language: pt

Funcionalidade: Resultado de Fichas Médicas

  Cenário: Resultado de Fichas Médicas
    Dado que exista um modelo de ficha medica "admissional" com a pergunta "alérgico?"
    Dado que exista a área organizacional "administração"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > Resultado de Fichas Médicas"
    Então eu devo ver o título "Resultado da Ficha Médica"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu seleciono "admissional" de "Modelo de ficha médica"
    E eu espero 2 segundos
    E eu preencho o campo (JS) "periodoIni" com "01/01/2011"
    E eu preencho o campo (JS) "periodoFim" com "29/07/2011"

    Então eu marco "administração"
    E eu marco "alérgico?"
    E eu marco "Exibir todas as respostas"
    E eu marco "Exibir comentários"
    E eu marco "Agrupar perguntas por aspecto"
    
    Então eu clico no botão "Relatorio"
    E eu devo ver "Nenhuma pergunta foi respondida."