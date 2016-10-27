# language: pt

Funcionalidade: Áreas de Vivência

  Cenário: Cadastro de Áreas de Vivência
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > PCMAT > Áreas de Vivência"

    Então eu devo ver o título "Áreas de Vivência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Área de Vivência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Áreas de Vivência"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "Sanitários"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Áreas de Vivência"
    Então eu devo ver "Sanitários"

    Entao eu clico em editar "Sanitários"
    E eu devo ver o título "Editar Área de Vivência"
    E o campo "Nome" deve conter "Sanitários"
    E eu preencho "Nome" com "Banheiros"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Áreas de Vivência"
    Então eu não devo ver na listagem "Sanitários"
    Então eu devo ver "Banheiros"

    Então eu clico em excluir "Banheiros"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Área de vivência excluída com sucesso."
    Então eu não devo ver na listagem "Banheiros"












