# language: pt

Funcionalidade: Gerenciamento do Cadastro de Conhecimento

  Esquema do Cenario: Cadastro de Conhecimento
      Dado que exista a área organizacional "Administração"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos" 
     Então  eu clico no botão "Inserir"  
         E eu preencho "Nome" com <Conhecimento>
         E eu marco <Lotação>
         E eu clico "Adicionar comportamento"
         E eu preencho "Observação" com <Observação>
     Então eu clico no botão "Gravar"
         E eu devo ver <Mensagem>

Exemplos:
    | Conhecimento    | Observação                        | Lotação         | Mensagem                               |
    | ""              | ""                                | ""              | "Preencha os campos indicados."        | 
    | "Ruby on Rails" | "Necessário Orientação a Objetos" | "Administração" | "Conhecimento cadastrado com sucesso." | 

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

