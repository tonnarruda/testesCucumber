# language: pt

Funcionalidade: Cadastrar Grupo Ocupacional
 
  @dev
  Cenário: Cadastro de Grupo Ocupacional
    Dado que eu esteja logado
    Quando eu acesso o menu "C&S > Cadastros > Grupos Ocupacionais"
    Então eu devo ver o título "Grupos Ocupacionais"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Grupo Ocupacional"
    Quando eu preencho "nome" com "__GRUPO_DE_TESTE"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "__GRUPO_DE_TESTE"
    Quando eu clico em excluir "__GRUPO_DE_TESTE"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Grupo Ocupacional excluído com sucesso"  
    E eu não devo ver "__GRUPO_DE_TESTE"