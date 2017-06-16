# language: pt

Funcionalidade: Emissão de Relatório de Absenteísmo


  Cenário: Emitir Relatorio Validando campos obrigatórios
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Absenteísmo"
         E eu clico no botão de class "btnRelatorio"
      Então eu devo ver o alert "Preencha os campos indicados." e clico no ok

#------------------------------------------------------------------------------------------------------------

  Cenário: Emitir Relatorio sem dados existentes
      Dado que exista uma ocorrência "Faltas"
      Dado que exista uma ocorrência "Atrasos"
      Dado que exista um colaborador "Ellen Pompeo", da area "DEV", com o cargo "DEV" e a faixa salarial "A"
      Dado que eu insira a ocorrencia "Faltas" para o colaborador  "Ellen Pompeo" na data inicial "01/05/2017"
      Dado que eu esteja logado com o usuário "SOS"   
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Absenteísmo"
         E eu preencho "dataDe" com "01/2017"
         E eu preencho "dataAte" com "05/2017"
         E eu marco "Estabelecimento Padrão" 
         E eu clico no botão de class "btnRelatorio"