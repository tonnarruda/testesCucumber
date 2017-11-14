# language: pt

Funcionalidade: Cartões para Acompanhamento do Período de Experiência

  Cenário: Cartões para Acompanhamento do Período de Experiência
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista um colaborador "joao", da area "trafego", com o cargo "motorista" e a faixa salarial "motorista I"
    Dado que exista um periodo de experiencia "30 dias" de 30 dias

    Quando eu acesso o menu "Aval. Desempenho > Relatórios > Cartões de Acompanhamento do Período de Experiência"
    Então eu devo ver o título "Cartões de Acompanhamento do Período de Experiência"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu preencho o campo (JS) "data" com "02/09/2010"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu marco "joao"
    E eu marco "30 dias"
