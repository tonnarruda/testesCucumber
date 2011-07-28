# language: pt

Funcionalidade: Cadastrar Area Organizacional
 
  @dev
  Cenário: Cadastro de Organizacional
    Dado que eu esteja logado
    Dado que exista a área organizacional "Administração"

    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
    Então eu devo ver o título "Áreas Organizacionais"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Área Organizacional"
    Quando eu preencho "nome" com "__area_de_teste"
    E eu seleciono "Administração" de "Área Mãe"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "Administração > __area_de_teste"
    Quando eu clico em excluir "Administração > __area_de_teste"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Área Organizacional excluída com sucesso"  
    E eu não devo ver "Administração > __area_de_teste"  
