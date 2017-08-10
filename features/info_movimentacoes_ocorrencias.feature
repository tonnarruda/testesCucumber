# language: pt

Funcionalidade: Movimentação de Ocorrências 

  Cenário: Associar Ocorrencia para colaborador |sem providencias | Empregado com Ocorrência apenas com data inicial |
      Dado que exista uma ocorrência "Faltas"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Ocorrências"
         E eu clico no botão "Pesquisar"
         E eu seleciono "Ellen Pompeo" de "Colaborador"
     Então eu clico no botão "Inserir"
         E eu seleciono "Faltas" de "Ocorrência"
         E eu preencho o campo (JS) "Data de Início" com "01/06/2017"
         E eu preencho "Observações" com "Faltou sem avisar."
     Então eu clico no botão "Gravar"  

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Validar Campos Obrigatórios
      Dado que exista uma ocorrência "Faltas"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Ocorrências"
         E eu clico no botão "Pesquisar"
         E eu seleciono "Ellen Pompeo" de "Colaborador"
     Então eu clico no botão "Inserir" 
         E eu clico no botão "Gravar" 
     Então eu devo ver o alert do confirmar e clico no ok
         E eu seleciono "Faltas" de "Ocorrência"
     Então eu clico no botão "Gravar"
         E eu devo ver o alert do confirmar e clico no ok       

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Associar Ocorrencia para colaborador com data anterior a data de admissão
      Dado que exista uma ocorrência "Faltas"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Ocorrências"
         E eu clico no botão "Pesquisar"
         E eu seleciono "Ellen Pompeo" de "Colaborador"
     Então eu clico no botão "Inserir"
         E eu seleciono "Faltas" de "Ocorrência"
         E eu preencho o campo (JS) "Data de Início" com "01/06/2011"
         E eu preencho "Observações" com "Faltou sem avisar."
     Então eu clico no botão "Gravar"
         E eu devo ver "Não é permitido inserir ocorrência antes da data da primeira situação do colaborador: (01/07/2011)"         
    
#------------------------------------------------------------------------------------------------------------------------    
  
  Cenário: Editar Ocorrencia Associada para colaborador 
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Atrasos"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Ocorrências"
         E eu clico no botão "Pesquisar"
         E eu seleciono "Ellen Pompeo" de "Colaborador"
     Então eu clico em editar "Faltas"
         E eu seleciono "Atrasos" de "Ocorrência"
         E eu preencho o campo (JS) "Data de Início" com "31/05/2017"
     Então eu clico no botão "Gravar"    
       
#------------------------------------------------------------------------------------------------------------------------

  Cenário: Excluir Ocorrencia Associada para colaborador 
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Atrasos"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Ocorrências"
         E eu clico no botão "Pesquisar"
         E eu seleciono "Ellen Pompeo" de "Colaborador"
     Então eu clico em excluir "Faltas"
         E eu devo ver o alert do confirmar e clico no ok
     Então eu devo ver "Ocorrência do colaborador removida com sucesso."   

#------------------------------------------------------------------------------------------------------------------------    
  
  Cenário: Inserir Mesma ocorrencia dentro de um período existente 
      Dado que exista uma ocorrência "Faltas"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" no período de "01/07/2017" a "30/07/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Ocorrências"
         E eu clico no botão "Pesquisar"
         E eu seleciono "Ellen Pompeo" de "Colaborador"
     Então eu clico no botão "Inserir"
         E eu seleciono "Faltas" de "Ocorrência"
         E eu preencho o campo (JS) "Data de Início" com "10/07/2017"
         E eu preencho "Observações" com "Faltou sem avisar."
     Então eu clico no botão "Gravar" 
     Então eu devo ver "A ocorrência não pôde ser gravada com as datas informadas."        