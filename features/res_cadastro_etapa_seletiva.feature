# language: pt
Funcionalidade: Gerenciamento Cadastral da Etapa Seletiva

  Cenário: Cadastro completo de Etapa Seletiva                   

      Dado que eu esteja logado com o usuário "SOS"                
    Quando eu acesso o menu "R&S > Cadastros > Etapas Seletivas" 
     Então eu clico no botão "Inserir"                            
         E eu preencho "nome" com "Entrevista com a Chefia Imediata"  
         E eu clico no botão "Gravar"                                 
     Então eu devo ver o título "Etapas Seletivas"                

  #------------------------------------------------------------------------------------------------------------------------
  Cenário: Cadastro de Etapa Seletiva sem preencher o nome da etapa e/ou ordem 

      Dado que eu esteja logado com o usuário "SOS"                              
    Quando eu acesso o menu "R&S > Cadastros > Etapas Seletivas"               
     Então eu clico no botão "Inserir"                                          
         E eu clico no botão "Gravar"                                               
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok    
         E eu preencho "nome" com "Entrevista com a Chefia Imediata"                
         E eu preencho "ordem" com ""                                               
         E eu clico no botão "Gravar"                                               
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok    
         E eu preencho "ordem" com "1"                                              
         E eu clico no botão "Gravar"                                               
     Então eu devo ver o título "Etapas Seletivas"                              

  #------------------------------------------------------------------------------------------------------------------------

  Cenário: Edição do cadastro de Etapa Seletiva

      Dado que exista a etapa seletiva "Entrevista com a Chefia Imediata" 
      Dado que eu esteja logado com o usuário "SOS"                       
    Quando eu acesso o menu "R&S > Cadastros > Etapas Seletivas"        
     Entao eu clico em editar "Entrevista com a Chefia Imediata"         
         E eu preencho "nome" com "Entrevista com a Gerência"                 
         E eu clico no botão "Gravar"                                        
     Então eu devo ver o título "Etapas Seletivas"                       

  #------------------------------------------------------------------------------------------------------------------------
  Cenário: Exclusão do cadastro de Etapa Seletiva                       

      Dado que exista a etapa seletiva "Entrevista com a Chefia Imediata" 
      Dado que eu esteja logado com o usuário "SOS"                       
    Quando eu acesso o menu "R&S > Cadastros > Etapas Seletivas"        
     Entao eu clico em excluir "Entrevista com a Chefia Imediata"        
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Etapa Seletiva excluída com sucesso."            

  #------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do cadastro de Etapa Seletiva Associada a Solicitação de Pessoal

      Dado que exista o cargo "QA Engineer"                                                                       
      Dado que haja uma faixa salarial com id 1, nome "I", cargo_id 1                                             
      Dado que exista um candidato "Tony Blair"                                                                   
      Dado que exista uma solicitacao "Vaga para o Desenvolvimento" para área "Desenvolvimento" na faixa "I"
      Dado que exista a etapa seletiva "Entrevista com a Chefia Imediata"                                         
      Dado que eu esteja logado com o usuário "SOS"                                                               
    Quando eu acesso o menu "R&S > Movimentações > Solicitação de Pessoal"
     Então eu clico na linha "1" da imagem "Candidatos da Seleção"
         E eu clico no botão "Triagem"                                                                             
         E eu clico no botão "LimparFiltro"                                                                        
         E eu clico no botão "Pesquisar"                                                                           
         E eu devo ver "Tony Blair"                                                                                
         E eu marco "md"                                                                                           
         E eu clico no botão "InserirSelecionados"                                                                 
     Entao eu clico no botão "InserirEtapasEmGrupo"                                                            
         E eu marco o checkbox com name "candidatosCheck"                                                          
         E eu preencho "Data" com "25/05/2017"                                                                   
         E eu preencho "horaIni" com "08:00"                                                                     
         E eu preencho "horaFim" com "12:00"                                                                     
         E eu preencho "resp" com "Responsavel pelo RH"                                                          
         E eu clico no botão "Gravar"                                                                            
     Entao eu acesso o menu "R&S > Cadastros > Etapas Seletivas"                                             
         E eu clico em excluir "Entrevista com a Chefia Imediata"                                                    
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Entidade etapa seletiva possui dependências em etapas seletivas do candidato."
         E eu espero 10 segundos


