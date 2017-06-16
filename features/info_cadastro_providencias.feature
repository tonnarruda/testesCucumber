# language: pt

Funcionalidade: Tipos de Ocorrências

  Cenário: Cadastro de Tipos de Providencias |Validar Campos obrigatórios|
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
         E eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu clico no botão "Voltar"

  Cenário: Cadastro de Tipos de Providencias
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
         E eu clico no botão "Inserir"
         E eu preencho "Descrição" com "Suspensao por ma conduta"
         E eu clico no botão "Gravar"    


  Cenário: Editar Tipos de Providencias
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
     Entao eu clico em editar "Suspensao por ma conduta"
         E eu preencho "Descrição" com "Suspensao"
         E eu clico no botão "Gravar" 


  Cenário: Excluir Tipos de Providencias
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
     Entao eu clico em excluir "Suspensao por ma conduta"
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Providência excluída com sucesso."


  @teste
  Cenário: Excluir Tipos de Providencias Associados a empregados
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a providencia "Suspensao por ma conduta" na ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "31/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
     Entao eu clico em excluir "Suspensao por ma conduta"
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Não foi possível excluir esta providência."
