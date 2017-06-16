# language: pt

Funcionalidade: Tipos de Ocorrências

  Cenário: Cadastro de Tipos de Ocorrências |Validar Campos obrigatórios|
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
         E eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu clico no botão "Cancelar"

  Cenário: Cadastro de Tipos de Ocorrências
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
         E eu clico no botão "Inserir"
         E eu preencho "Descrição" com "Falta"
         E eu preencho "Pontuação" com "5"
         E eu clico no botão "Gravar"    


  Cenário: Editar Tipos de Ocorrências
      Dado que exista uma ocorrência "Faltas"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
     Entao eu clico em editar "Faltas"
         E eu preencho "Descrição" com "Absenteísmo"
         E eu preencho "Pontuação" com "10"
         E eu desmarco "Exibir em performance profissional"
         E eu clico no botão "Gravar" 


  Cenário: Excluir Tipos de Ocorrências
      Dado que exista uma ocorrência "Faltas"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
     Entao eu clico em excluir "Faltas"
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Ocorrência excluída com sucesso."       


  Cenário: Excluir Tipos de Ocorrências Associados a empregados
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Atrasos"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"      
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
     Entao eu clico em excluir "Faltas"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Entao eu devo ver "A ocorreu não pode ser excluída pois possui dependência."  
    

  Cenário: Pesquisar Ocorrencias Cadastradas
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Atrasos"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
     Entao eu clico "Exibir Filtro"
         E eu preencho "ocorrencia.descricao" com "Atrasos" 
         E eu clico no botão "Pesquisar" 
         E eu não devo ver "Faltas"
     Entao eu preencho "ocorrencia.descricao" com "Faltas" 
         E eu clico no botão "Pesquisar" 
         E eu não devo ver "Atrasos"