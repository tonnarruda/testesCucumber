# language: pt

Funcionalidade: Cadastrar Áreas Organizacionais

  Cenário: Cadastro de Áreas Organizacionais | Valida Campo Obrigatório
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
     Então eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok    

#-------------------------------------------------------------------------------------------------------------------------------------

Cenário: Cadastro de Áreas Organizacionais | Cadastro Completo Sem Área Mae
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
     Então eu clico no botão "Inserir"
         E eu preencho "Nome" com "Área Organizacional Teste"
         E eu clico no botão "Gravar"
     Então eu devo ver "Área Organizacional Teste"

#-------------------------------------------------------------------------------------------------------------------------------------

Cenário: Cadastro de Áreas Organizacionais | Cadastro Completo Com Área Mae
      Dado que exista a área organizacional "Área Organizacional Teste"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
     Então eu clico no botão "Inserir"
         E eu preencho "Nome" com "Área Organizacional Teste Filha"
         E eu seleciono "Área Organizacional Teste" de "Área Mãe"
         E eu clico no botão "Gravar"

#-------------------------------------------------------------------------------------------------------------------------------------
@teste
Cenário: Cadastro de Áreas Organizacionais | Cadastro de área filha com empregado vinculado a área mae
      Dado que exista um colaborador "João", da area "Area Mae", com o cargo "Dev" e a faixa salarial "A1"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
     Então eu clico no botão "Inserir"
         E eu preencho "Nome" com "Área Organizacional Teste Filha"
         E eu seleciono "Area Mae" de "Área Mãe"
         E eu clico no botão "Gravar" 
     Então eu devo ver o alert "A área organizacional mãe selecionada, possui" e clico no não  
         E eu devo ver "Não é possível cadastrar área organizacional cuja área mãe tenha colaboradores vinculados."          

#-------------------------------------------------------------------------------------------------------------------------------------

Cenário: Cadastro de Áreas Organizacionais | Editar
      Dado que exista a área organizacional "Área Organizacional Teste"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
     Entao eu clico em editar "Área Organizacional Teste"
         E eu preencho "Nome" com "Área Teste"
         E eu clico no botão "Gravar"

#-------------------------------------------------------------------------------------------------------------------------------------

Cenário: Cadastro de Áreas Organizacionais | Excluir
      Dado que exista a área organizacional "Área Organizacional Teste"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Áreas Organizacionais"
     Então eu clico em excluir "Área Organizacional Teste"
         E eu devo ver o alert "Procedimento diferenciado para o usuário" e clico no ok
     Então eu devo ver "Área organizacional excluída com sucesso."