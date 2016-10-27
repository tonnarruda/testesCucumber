# language: pt

Funcionalidade: Cadastrar Forma Como Ficou Sabendo da Vaga

  Cenário: Cadastro de Como Ficou Sabendo da Vaga
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Como Ficou Sabendo da Vaga"
    Então eu devo ver o título "Como Ficou Sabendo da Vaga"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Como Ficou Sabendo da Vaga"
    E eu clico no botão "Gravar"
    E eu aperto "OK"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Como Ficou Sabendo da Vaga"

    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Como Ficou Sabendo da Vaga"
    E eu preencho "nome" com "_jornal"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Como Ficou Sabendo da Vaga"
    Então eu devo ver "jornal"
    E eu clico em editar "_jornal"
    E eu devo ver o título "Editar Como Ficou Sabendo da Vaga"
    E o campo "nome" deve conter "_jornal"
    E eu preencho "nome" com "_site"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Como Ficou Sabendo da Vaga"

    E eu clico em excluir "_site"
    Então eu devo ver "Confirma exclusão?"
    E eu aperto "OK"
    Então eu devo ver "Item excluído com sucesso."
    E eu não devo ver "_site"