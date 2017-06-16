# language: pt

Funcionalidade: Aprovar/Reprovar Solicitação de Desligamento

 
  Cenário: Aprovar Solicitação de Desligamento de Colaborador
      Dado que exista um colaborador "Mark Sloan", da area "Cirurgia Plástica", com o cargo "Chefe de Cirurgia" e a faixa salarial "1A"
      Dado que exista um motivo de desligamento "Encerramento do Setor"
      Dado que a opção de solicitação de confirmação de desligamento para a empresa seja "true"    
      Dado que eu esteja logado com o usuário "SOS"
     Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
         E eu clico na linha "Mark Sloan" da imagem "Solicitar desligamento"
         E eu preencho "Data de Desligamento" com "31/05/2017"
         E eu seleciono "Encerramento do Setor" de "Motivo do Desligamento"
         E eu preencho "Observações" com "Pagar rescisão de imediato"
         E eu clico no botão "SolicitarDesligamento"
         E eu devo ver o alert "Confirma solicitação de desligamento?" e clico no ok
     Então eu devo ver "Solicitação de desligamento cadastrada com sucesso."  

     Então eu acesso o menu "Info. Funcionais > Movimentações > Aprovar/Reprovar Solicitações de Desligamento"
         E eu clico na linha "Mark Sloan" da imagem "Visualizar solicitação de desligamento"  
         E eu clico no botão "Voltar"
         E eu clico na linha "Mark Sloan" da imagem "Visualizar solicitação de desligamento"
         E eu clico no botão "Aprovar" 
         E eu devo ver o alert "Deseja realmente aprovar essa solicitação de desligamento?" e clico no ok
     Então eu devo ver "Colaborador desligado com sucesso." 

#------------------------------------------------------------------------------------------------------------------------------------------    
	
  Cenário: Reprovar Solicitação de Desligamento de Colaborador
      Dado que exista um colaborador "Carl Lightmann", da area "Consultoria FBI", com o cargo "Consultor Técnico" e a faixa salarial "1A"
      Dado que exista um motivo de desligamento "Encerramento do Setor"
      Dado que a opção de solicitação de confirmação de desligamento para a empresa seja "true"    
      Dado que eu esteja logado com o usuário "SOS"
     Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
         E eu clico na linha "Carl Lightmann" da imagem "Solicitar desligamento"
         E eu preencho "Data de Desligamento" com "31/05/2017"
         E eu seleciono "Encerramento do Setor" de "Motivo do Desligamento"
         E eu preencho "Observações" com "Pagar rescisão de imediato"
         E eu clico no botão "SolicitarDesligamento"
         E eu devo ver o alert "Confirma solicitação de desligamento?" e clico no ok
     Então eu devo ver "Solicitação de desligamento cadastrada com sucesso."  

     Então eu acesso o menu "Info. Funcionais > Movimentações > Aprovar/Reprovar Solicitações de Desligamento"
         E eu clico na linha "Carl Lightmann" da imagem "Visualizar solicitação de desligamento"  
         E eu clico no botão "Voltar"
         E eu clico na linha "Carl Lightmann" da imagem "Visualizar solicitação de desligamento"
         E eu clico no botão "Reprovar" 
         E eu devo ver o alert "Deseja realmente reprovar essa solicitação de desligamento?" e clico no ok
     Então eu devo ver "Solicitação de desligamento reprovada com sucesso." 
         E eu devo deslogar do sistema