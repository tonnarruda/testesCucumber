# language: pt

Funcionalidade: Relatório de Prontuário

  Cenário: Relatório de Prontuário
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "SESMT > Relatórios > Prontuário"

    Então eu devo ver o título "Prontuário"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"

