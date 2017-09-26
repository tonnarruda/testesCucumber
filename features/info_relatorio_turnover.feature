# language: pt

Funcionalidade: Emissão de Relatório de Turnover


  Cenário: Emitir Relatorio Validando campos obrigatórios
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Turnover (rotatividade)"
         E eu clico no botão de class "btnRelatorio"
      Então eu devo ver o alert "Preencha os campos indicados." e clico no ok

#------------------------------------------------------------------------------------------------------------

  Cenário: Emitir Relatorio sem dados existentes
      Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que exista um colaborador "Tony Blair", da area "Desenvolvimento", com o cargo "Diretor de Produto" e a faixa salarial "II"
      Dado que exista um colaborador "Ellen Pompeo", da area "Suporte", com o cargo "Gerente de Suporte" e a faixa salarial "4F"
      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Ellen Pompeo" na data "25/04/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Tony Blair" na data "28/02/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Turnover (rotatividade)"
         E eu preencho "dataDe" com "01/2017"
         E eu preencho "dataAte" com "05/2017"
         E eu seleciono "Área Organizacional" de "Agrupar Por"
         E eu marco "Estabelecimento Padrão" 
         E eu clico no botão de class "btnRelatorio"
    