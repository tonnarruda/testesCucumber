# language: pt

Funcionalidade: Gerenciamento de Providencias

  Cenário: Associar Providencias aos Empregados |Sem ocorrência Cadastrada|

      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Providências"
     Então eu devo ver "Não existem providências a serem listadas para o filtro informado"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Associar Providencias aos Empregados

      Dado que exista um colaborador "Ellen Pompeo", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que exista uma ocorrência "Faltas"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Providências"
        E eu clico na ação "Inserir" de "Ellen Pompeo"
        E eu seleciono (JS) "1" de "providencia"
        E eu clico no botão "Gravar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Editar Providencias associadas aos Empregados

      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que exista uma providencia "Atrasos"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a providencia "Suspensao por ma conduta" na ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "31/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Providências"
        E eu clico na ação "Editar" de "Ellen Pompeo"
        E eu seleciono (JS) "2" de "providencia"
        E eu clico no botão "Gravar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Excluir Providencias associadas aos Empregados

      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a providencia "Suspensao por ma conduta" na ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "31/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Providências"
        E eu clico na ação "Editar" de "Ellen Pompeo"
        E eu seleciono (JS) "" de "providencia"
        E eu clico no botão "Gravar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Pesquisar Providencias associadas aos Empregados

      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Briga com outro Colaborador"
      Dado que exista uma providencia "Suspensao por ma conduta"
      Dado que exista uma providencia "Advertência Verbal"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a providencia "Suspensao por ma conduta" na ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "31/05/2017"
      Dado que eu insira a ocorrencia "Briga com outro Colaborador" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Providências"
     Então eu clico "Exibir Filtro"
         E eu preencho "Colaborador" com "Ellen"
         E eu clico no botão "Pesquisar"
         E eu devo ver "Faltas"
         E eu devo ver "Briga com outro Colaborador"
     Então eu seleciono (JS) "S" de "formBusca_comProvidencia"
         E eu clico no botão "Pesquisar"
         E eu não devo ver "Faltas"
     Então eu seleciono (JS) "C" de "formBusca_comProvidencia"
         E eu clico no botão "Pesquisar"
         E eu não devo ver "Briga com outro Colaborador"
     Então eu devo deslogar do sistema