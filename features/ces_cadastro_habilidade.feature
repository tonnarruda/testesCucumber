# language: pt

Funcionalidade: Cadastrar Habilidades

  Cenário: Cadastro de Habilidades
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "Administração"
    Dado que exista a área organizacional "Administração > Desenvolvimento"

    Quando eu acesso o menu "C&S > Cadastros > Habilidades"
    Então eu devo ver o título "Habilidades"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Habilidade"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Habilidades"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Habilidade"
    E eu preencho "Nome" com "_Comunicação"
    E eu marco "Administração"
    E eu preencho "Observação" com "_Boa comunicação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Habilidades"
    E eu devo ver "_Comunicação"

    Entao eu clico em editar "_Comunicação"
    E eu devo ver o título "Editar Habilidade"
    E o campo "Nome" deve conter "_Comunicação"
    E eu preencho "Nome" com "_Proatividade"
    E eu desmarco "Administração"
    E eu marco "Administração > Desenvolvimento"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Habilidades"

    Então eu clico em excluir "_Proatividade"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Habilidade excluída com sucesso."
    E eu não devo ver "_Proatividade"