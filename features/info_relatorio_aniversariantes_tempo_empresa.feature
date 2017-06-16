# language: pt

Funcionalidade: Emissão de Relatório de Aniversariantes por tempo de empresa


  Cenário: Emitir Relatorio sem dados existentes
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Aniversariantes por tempo de empresa"
         E eu seleciono "Janeiro" de "relatorioAniversariantesPorTempoDeEmpresa_mes"
         E eu clico no botão de class "btnRelatorio"
     Então eu devo ver "Não existe dados para os filtros informados"

#------------------------------------------------------------------------------------------------------------

  Cenário: Emitir Relatorio com dados existentes
      Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Aniversariantes por tempo de empresa"
         E eu seleciono "Julho" de "relatorioAniversariantesPorTempoDeEmpresa_mes"
         E eu clico no botão de class "btnRelatorio"