# language: pt

Funcionalidade: Relatório de Estatística de Divulgação da Vaga

  Cenário: Relatório Como Ficou Sabendo da Vaga
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Relatórios > Estatística de Divulgação da Vaga"
    Então eu devo ver o título "Estatística de Divulgação da Vaga"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho o campo (JS) "dataIni" com "02/02/2000"
    E eu preencho o campo (JS) "dataFim" com "05/05/2000"
