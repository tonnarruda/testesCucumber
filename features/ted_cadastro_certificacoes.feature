# language: pt

Funcionalidade: Cadastrar Certificações dos Cursos

  Cenário: Certificações dos Cursos
    Dado que exista um curso "tdd"
    
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Cadastros > Certificações"
    Então eu devo ver o título "Certificações"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Certificação"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Certificações"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Certificação"
    E eu preencho "Nome" com "cbts"
    E eu marco "tdd"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Certificações"
    E eu devo ver "cbts"

    Entao eu clico em editar "cbts"
    E eu devo ver o título "Editar Certificação"
    E o campo "Nome" deve conter "cbts"
    E eu preencho "Nome" com "tester"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Certificações"

    Então eu clico em excluir "tester"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Certificação excluída com sucesso."
    E eu não devo ver "tester"