# language: pt

Funcionalidade: Cadastrar Grupo Ocupacional

  Cenário: Cadastro de Grupo Ocupacional
    Dado que eu esteja logado
    Quando eu acesso o menu "C&S > Cadastros > Grupos Ocupacionais"
    Então eu devo ver o título "Grupos Ocupacionais"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Grupo Ocupacional"
    Quando eu preencho "nome" com "__grupo_de_teste"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "__grupo_de_teste"
    Quando eu clico em excluir "__grupo_de_teste"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Grupo Ocupacional excluído com sucesso"  
    E eu não devo ver "__grupo_de_teste"