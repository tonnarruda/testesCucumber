# language: pt

Funcionalidade: Ficha de EPI

  Cenário: Gerar de Ficha de EPI
    Dado que exista um colaborador "geraldo", da area "desenvolvimento", com o cargo "desenvolvedor" e a faixa salarial "I"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > EPI > Ficha de EPI"
    Então eu devo ver o título "Ficha de EPI"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"

    Então eu devo ver "Selecione um colaborador para popular as solicitações de EPI."
    E eu clico no botão "ImprimirPdf"
    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    Então eu devo ver "O colaborador selecionado não possui solicitação de EPI."
    E eu marco "Imprimir verso"
