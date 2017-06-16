# language: pt

Funcionalidade: Emissão de Relatório de Ocorrências

  Cenário: Validar Campos Obrigatórios do Relatório de ocorrências
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Ocorrências"
         E eu clico no botão "Relatorio"
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok

#------------------------------------------------------------------------------------------------------------

  Cenário: Relatório de ocorrências sem dados existentes
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Ocorrências"
         E eu preencho "dataIni" com "01/01/2015"
         E eu preencho "dataFim" com "12/01/2016"    
         E eu clico no botão "Relatorio"     
     Então eu devo ver "Não existem dados para o filtro informado."    

#------------------------------------------------------------------------------------------------------------

  Cenário: Emissão do Relatório de ocorrências filtrando por data
      Dado que exista uma ocorrência "Faltas"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Ocorrências"
         E eu preencho "dataIni" com "01/05/2017"
         E eu preencho "dataFim" com "12/05/2017"    
         E eu clico no botão "Relatorio"  

#------------------------------------------------------------------------------------------------------------

  Cenário: Emissão do Relatório de ocorrências filtrando por Colaborador Inativo
      Dado que exista uma ocorrência "Faltas"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Ocorrências"
         E eu preencho "dataIni" com "01/05/2017"
         E eu preencho "dataFim" com "12/05/2017"  
         E eu seleciono "Desligado" de "situacao" 
         E eu clico no botão "Relatorio"           
     Então eu devo ver "Não existem dados para o filtro informado." 

#------------------------------------------------------------------------------------------------------------

  Cenário: Emissão do Relatório de ocorrências filtrando por Ocorrencia não associada a empregados
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Atrasos"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Ocorrências"
         E eu preencho "dataIni" com "01/05/2017"
         E eu preencho "dataFim" com "12/05/2017"  
         E eu marco "Atrasos" 
         E eu clico no botão "Relatorio"           
     Então eu devo ver "Não existem dados para o filtro informado." 
  


















           