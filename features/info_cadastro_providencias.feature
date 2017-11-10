# language: pt

Funcionalidade: Tipos de Providencias

  Esquema do Cenario: Cadastro de Providencias
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências" 
     Então eu clico no botão "Inserir" 
         E eu preencho "Descrição" com <Descrição>
         E eu clico no botão "Gravar"
         E eu devo ver <Mensagem>

Exemplos:
    | Descrição                  | Mensagem                        |
    | ""                         | "Preencha os campos indicados." | 
    | "Suspensao por ma conduta" | "Providências"                  | 

#-----------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Cadastro de Providencias
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que exista uma providencia "Advertência Verbal"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a providencia "Suspensao por ma conduta" na ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "31/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências" 
     Então eu clico em excluir <Providencia> 
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver <Mensagem>

Exemplos:
    | Providencia                | Mensagem                                     |
    | "Advertência Verbal"       | "Providência excluída com sucesso."          |
    | "Suspensao por ma conduta" | "Não foi possível excluir esta providência." |  

#-----------------------------------------------------------------------------------------------------------------------------       

  Cenário: Editar Tipos de Providencias
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Providências"
     Entao eu clico em editar "Suspensao por ma conduta"
         E eu preencho "Descrição" com "Suspensao"
         E eu clico no botão "Gravar" 