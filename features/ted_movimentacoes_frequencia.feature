# language: pt

Funcionalidade: Cadastrar Frequência do Curso

  Cenário: Frequência
    Dado que exista um curso "tdd"
    Dado que exista uma turma "a1" para o curso "tdd"

    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Movimentações > Frequência"
    Então eu devo ver o título "Controle de Frequência"
    E eu seleciono "tdd" de "Curso"
    E eu clico no botão "Pesquisar"
    E eu devo ver "a1"
    E eu clico na linha "a1" da imagem "Lista de Freqüência"
    E eu devo ver o título "Lista de Frequência"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Controle de Frequência"