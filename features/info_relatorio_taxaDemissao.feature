# language: pt

Funcionalidade: Emissão de Relatório de Taxa de Demissão

  Cenário: Validar Campos Obrigatórios do Relatório de taxa de demissão
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Taxa de Demissão"
         E eu clico no botão "Relatorio"
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Validar quantidade de meses no período na Emissão do Relatório de taxa de demissão em PDF
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Taxa de Demissão"
         E eu preencho o campo (JS) "dataDe" com "01/2015"
         E eu preencho o campo (JS) "dataAte" com "12/2016"
         E eu clico no botão "Relatorio"
     Então eu devo ver "Não é permitido um período maior que 12 meses para a geração deste relatório"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Emissão do Relatório de taxa de demissão em PDF
      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que exista um colaborador "Mark Sloan", da area "Cirurgia Plastica", com o cargo "Médico Cirurgião" e a faixa salarial "IS"
      Dado que exista um colaborador "Alex Karev", da area "Pediatria", com o cargo "Interno" e a faixa salarial "I4"
      Dado que exista um colaborador "Miranda Bailey", da area "Cirurgia Geral - Chefia", com o cargo "Chefe de Cirurgia" e a faixa salarial "IR"
      Dado que exista um colaborador "Lexie Grey", da area "Cirurgia Geral", com o cargo "Interno do 1º Ano" e a faixa salarial "IF"
      Dado que exista um colaborador "Christina Yang", da area "Cirurgia Cardíaca", com o cargo "Cirurgiao Chefe" e a faixa salarial "IB"
      Dado que exista um colaborador "Owen Hunt", da area "Traumatologia", com o cargo "Chefe de Cirurgia Geral" e a faixa salarial "IA"
      
      Dado que eu desligue o colaborador "Mark Sloan" na data "25/04/2017" com motivo de deligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Lexie Grey" na data "31/03/2017" com motivo de deligamento "Redução de Quadro"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Taxa de Demissão"
         E eu preencho o campo (JS) "dataDe" com "01/2017"
         E eu preencho o campo (JS) "dataAte" com "06/2017"
         E eu clico no botão "Relatorio"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Emissão do Relatório de taxa de demissão utilizando filtros
      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que exista um colaborador "Mark Sloan", da area "Cirurgia Plastica", com o cargo "Médico Cirurgião" e a faixa salarial "IS"
      Dado que exista um colaborador "Alex Karev", da area "Pediatria", com o cargo "Interno" e a faixa salarial "I4"
      Dado que exista um colaborador "Miranda Bailey", da area "Cirurgia Geral - Chefia", com o cargo "Chefe de Cirurgia" e a faixa salarial "IR"
      Dado que exista um colaborador "Lexie Grey", da area "Cirurgia Geral", com o cargo "Interno do 1º Ano" e a faixa salarial "IF"
      Dado que exista um colaborador "Christina Yang", da area "Cirurgia Cardíaca", com o cargo "Cirurgiao Chefe" e a faixa salarial "IB"
      Dado que exista um colaborador "Owen Hunt", da area "Traumatologia", com o cargo "Chefe de Cirurgia Geral" e a faixa salarial "IA"
      
      Dado que eu desligue o colaborador "Mark Sloan" na data "25/04/2017" com motivo de deligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Lexie Grey" na data "31/03/2017" com motivo de deligamento "Redução de Quadro"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Taxa de Demissão"
         E eu preencho o campo (JS) "dataDe" com "01/2017"
         E eu preencho o campo (JS) "dataAte" com "06/2017"
         E eu marco "Pediatria"
         E eu marco "Traumatologia"
         E eu marco "Aprendiz" 
     Então eu clico no botão "Relatorio"
         E eu devo deslogar do sistema