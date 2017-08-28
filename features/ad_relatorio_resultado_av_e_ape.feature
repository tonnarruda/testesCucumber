# language: pt

Funcionalidade: Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência

  Cenário: Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "geral (Ativa)"
    Dado que exista um modelo avaliacao desempenho "avaliacao experiencia"

    Quando eu acesso o menu "Aval. Desempenho > Relatórios > Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência"
    Então eu devo ver o título "Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu seleciono "avaliacao experiencia" de "Modelo de Avaliação"
    E eu preencho o campo (JS) "periodoIni" com "01/01/2011"
    E eu preencho o campo (JS) "periodoIni" com "01/01/2011"
    E eu marco "geral"
    E eu marco "Exibir comentários"

    E eu clico no botão "Relatorio"
    Então eu devo ver "Não existe pergunta para a avaliação ou aspecto(s)."

