# language: pt

Funcionalidade: Emissão de Relatório de Desligamentos

  Cenário: Validar Campos Obrigatórios do Relatório de desligamentos
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Desligamentos"
         E eu clico no botão "Relatorio"
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok
         E eu preencho "dataIni" com "01/01/2015"
         E eu preencho "dataFim" com "12/01/2016"    
         E eu clico no botão "Relatorio" 
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok    
         E eu marco "Estabelecimento Padrão" 
         E eu clico no botão "Relatorio"       

#------------------------------------------------------------------------------------------------------------

  Cenário: Impressão do Relatório de desligamentos em PDF
      Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que exista um colaborador "Tony Blair", da area "Desenvolvimento", com o cargo "Diretor de Produto" e a faixa salarial "II"
      Dado que exista um colaborador "Ellen Pompeo", da area "Suporte", com o cargo "Gerente de Suporte" e a faixa salarial "4F"
      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Ellen Pompeo" na data "25/04/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Theresa May" na data "12/04/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Desligamentos" 
         E eu preencho "dataIni" com "01/04/2017"
         E eu preencho "dataFim" com "30/04/2017" 
         E eu marco "Estabelecimento Padrão"            
         E eu clico no botão "Relatorio"         