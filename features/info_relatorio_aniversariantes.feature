# language: pt

Funcionalidade: Emissão de Relatório de Aniversariantes do Mes


  Cenário: Emitir Relatorio sem dados existentes
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Aniversariantes do mês"
         E eu clico no botão de class "btnRelatorio"
     Então eu devo ver "Não existem colaboradores para o filtro informado"
     

#------------------------------------------------------------------------------------------------------------

  Cenário: Emitir Relatorio com dados existentes
      Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Aniversariantes do mês"
         E eu seleciono "Janeiro" de "relatorioAniversariantes_mes"
         E eu clico no botão de class "btnRelatorio"
         E eu seleciono "Fevereiro" de "relatorioAniversariantes_mes"
         E eu clico no botão "Relatorio"         
     Então eu devo ver "Não existem colaboradores para o filtro informado"     


         
