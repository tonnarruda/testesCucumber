# language: pt

Funcionalidade: Cadastrar Áreas de Formação

  Cenário: Cadastro de Áreas de Formação
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "R&S > Cadastros > Áreas de Formação"
     Então  eu clico no botão "Inserir"
         E eu preencho "nome" com "Administração"
         E eu clico no botão "Gravar"
     
#-------------------------------------------------------------------------------

  Cenário: Edição de Áreas de Formação
      Dado que exista a área de formação "Administração"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "R&S > Cadastros > Áreas de Formação"
     Então eu clico em editar "Administração"
         E eu preencho "nome" com "Administrativo Financeira"
         E eu clico no botão "Gravar"
          
#-------------------------------------------------------------------------------

  Cenário: Exclusão de Áreas de Formação
      Dado que exista a área de formação "Administração"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "R&S > Cadastros > Áreas de Formação"
     Então eu clico em excluir "Administração"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Área de Formação excluída com sucesso."         

#-------------------------------------------------------------------------------

  Cenário: Pesquisar de Áreas de Formação
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "R&S > Cadastros > Áreas de Formação"
     Então eu clico "Exibir Filtro"
         E eu preencho "Nome" com "Artes"     
         E eu clico no botão "Pesquisar"
     Então eu devo ver "Artes"
         E eu clico "Exibir Filtro"
         E eu preencho "Nome" com "Agro"     
         E eu clico no botão "Pesquisar"
     Então eu não devo ver "Artes"    