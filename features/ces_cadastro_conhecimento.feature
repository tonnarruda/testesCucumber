# language: pt

Funcionalidade: Cadastrar Conhecimentos

  Cenário: Validar Campos Obrigatórios no Cadastro de Conhecimentos
      Dado que eu esteja logado com o usuário "SOS"
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos"
     Então eu devo ver o título "Conhecimentos"
         E eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho "Nome" com "_Ruby"
         E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok

#---------------------------------------------------------------------------------

  Cenário: Cadastrar de Conhecimentos
      Dado que eu esteja logado com o usuário "SOS"
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos"
     Então eu devo ver o título "Conhecimentos"
         E eu clico no botão "Inserir"
         E eu preencho "Nome" com "_Ruby"
         E eu marco "Administração"
         E eu preencho "Observação" com "_Ruby super ferramenta"
         E eu clico no botão "Gravar"
    Então eu devo ver "Conhecimento cadastrado com sucesso."

#---------------------------------------------------------------------------------

  Cenário: Editar Cadastros de Conhecimentos
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
      Dado que exista um conhecimento "Pacote Office"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos"
     Entao eu clico em editar "Pacote Office"
         E eu preencho "Nome" com "Pacote Microsoft Office 2010"
         E eu marco "Administração"
         E eu clico no botão "Gravar"
     Então eu devo ver "Conhecimento atualizado com sucesso."   

#---------------------------------------------------------------------------------

  Cenário: Excluir Cadastros de Conhecimentos
      Dado que exista um conhecimento "Pacote Office"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos"
     Entao eu clico em excluir "Pacote Office"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Conhecimento excluído com sucesso." 

