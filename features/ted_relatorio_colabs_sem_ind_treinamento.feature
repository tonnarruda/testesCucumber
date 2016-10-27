# language: pt

Funcionalidade: Relatório de Colaboradores Sem Indicação de Treinamento

  Cenário: Relatório de Colaboradores Sem Indicação de Treinamento
    Dado que eu esteja logado com o usuário "SOS"
    
    Dado que exista a área organizacional "trafego", filha de "operacional"

    Quando eu acesso o menu "T&D > Relatórios > Colaboradores sem Indicação de Trein."
    Então eu devo ver o título "Colaboradores Sem Indicação de Treinamento"

    E eu preencho "meses" com "30"
    E eu marco "Estabelecimento Padrão"
    E eu marco "trafego"

    E eu clico no botão "Relatorio"
