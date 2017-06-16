# language: pt

Funcionalidade: Emissão de Relatório de Admitidos


  Cenário: Emitir Relatorio Validando campos obrigatórios
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Admitidos"
         E eu clico no botão de class "btnRelatorio"
      Então eu devo ver o alert "Preencha os campos indicados." e clico no ok

#------------------------------------------------------------------------------------------------------------

  Cenário: Emitir Relatorio sem dados existentes
      Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Admitidos"
         E eu seleciono "Sócio" de "vinculo"
         E eu preencho "dataIni" com "01/05/2017"
         E eu preencho "dataFim" com "12/05/2017"
         E eu marco "Estabelecimento Padrão" 
         E eu clico no botão de class "btnRelatorio"
     Então eu devo ver "Não existem dados para o filtro informado."

#------------------------------------------------------------------------------------------------------------

  Cenário: Emitir Relatorio sem dados existentes
      Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Admitidos"
         E eu seleciono "Emprego" de "vinculo"
         E eu preencho "dataIni" com "01/05/2000"
         E eu preencho "dataFim" com "12/05/2017"
         E eu marco "Estabelecimento Padrão" 
         E eu clico no botão de class "btnRelatorio"
     Então eu não devo ver "Não existem dados para o filtro informado."     