# language: pt

Funcionalidade: Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência

  Cenário: Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência

    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "Administração"
    Dado que exista a área organizacional "Desenvolvimento"
    Dado que exista um modelo avaliacao desempenho "avaliacao experiencia"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Aval. Desempenho > Relatórios > Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência"
    Então eu devo ver o título "Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu seleciono "avaliacao experiencia" de "Modelo de Avaliação"
    E eu marco "Administração"
    E eu marco "Exibir comentários"

    E eu clico no botão "Relatorio"
    Então eu devo ver "Não existe pergunta para a avaliação ou aspecto(s)."

