# language: pt

Funcionalidade: Cadastrar Áreas Organizacionais

  Cenário: Cadastro de Áreas Organizacionais
    Dado que eu esteja logado com o usuário "SOS"


    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
    Então eu devo ver o título "Áreas Organizacionais"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Área Organizacional"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Áreas Organizacionais"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Área Organizacional"
    E eu preencho "Nome" com "Area Mãe"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Áreas Organizacionais"
    E eu devo ver "Area Mãe"

    Entao eu clico em editar "Area Mãe"
    E eu devo ver o título "Editar Área Organizacional"
    E o campo "Nome" deve conter "Area Mãe"
    E eu preencho "Nome" com "Área Mãe 2"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Áreas Organizacionais"

    Então eu clico em excluir "Área Mãe 2"
    E eu devo ver o alert "Procedimento diferenciado para o usuário" e clico no ok
    Então eu devo ver "Área organizacional excluída com sucesso."
    E eu não devo ver "Área Mãe 2"