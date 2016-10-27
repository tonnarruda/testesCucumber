# language: pt

Funcionalidade: Relatório de Afastamentos

  Cenário: Relatório de Afastamentos
    Dado que exista um colaborador "jackson", da area "desenvolvimento", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista um afastamento "doença"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "SESMT > Relatórios > Afastamentos > Afastamentos"
    Então eu devo ver o título "Afastamentos"

    Então eu preencho o campo (JS) "inicio" com "01/07/2011"
    Então eu preencho o campo (JS) "fim" com "29/07/2011"

    E eu marco "Estabelecimento Padrão"
    E eu marco "desenvolvimento"

    E eu preencho "Colaborador" com "jackson"
    E eu seleciono "doença" de "Motivo"
    E eu seleciono "Nome" de "Ordenar por"
    E eu seleciono "Afastados" de "INSS"
    E eu seleciono "CID" de "Agrupar por"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Não há afastamentos para o filtro informado."