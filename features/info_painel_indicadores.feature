# language: pt

Funcionalidade: Painel de Indicadores

  Cenário: Validar dados de Informações Sociais | Sem informações existentes |
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Painel de Indicadores"
     Então eu devo ver "Não existem dados para o gráfico."
     
#------------------------------------------------------------------------------------------------------------------------
@teste
  Cenário: Visualizar dados de Informações Sociais

      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que exista um colaborador "Mark Sloan", da area "Cirurgia Plastica", com o cargo "Médico Cirurgião" e a faixa salarial "IS"
      Dado que exista um colaborador "Alex Karev", da area "Pediatria", com o cargo "Interno" e a faixa salarial "I4"
      Dado que exista um colaborador "Miranda Bailey", da area "Cirurgia Geral - Chefia", com o cargo "Chefe de Cirurgia" e a faixa salarial "IR"
      Dado que exista um colaborador "Lexie Grey", da area "Cirurgia Geral", com o cargo "Interno do 1º Ano" e a faixa salarial "IF"
      Dado que exista um colaborador "Christina Yang", da area "Cirurgia Cardíaca", com o cargo "Cirurgiao Chefe" e a faixa salarial "IB"
      Dado que exista um colaborador "Owen Hunt", da area "Traumatologia", com o cargo "Chefe de Cirurgia Geral" e a faixa salarial "IA"
      
      Dado que eu desligue o colaborador "Mark Sloan" na data "25/04/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Lexie Grey" na data "31/03/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Painel de Indicadores"
         E eu clico "Exibir Filtro"
         E eu marco "Empresa Padrão - Cirurgiao Chefe (Ativo)"
         E eu clico no botão "Pesquisar"
         
#------------------------------------------------------------------------------------------------------------------------

  Cenário: Visualizar dados de Ocorrência/Absenteísmo

      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que exista um colaborador "Mark Sloan", da area "Cirurgia Plastica", com o cargo "Médico Cirurgião" e a faixa salarial "IS"
      Dado que exista um colaborador "Alex Karev", da area "Pediatria", com o cargo "Interno" e a faixa salarial "I4"
      Dado que exista um colaborador "Miranda Bailey", da area "Cirurgia Geral - Chefia", com o cargo "Chefe de Cirurgia" e a faixa salarial "IR"
      Dado que exista um colaborador "Lexie Grey", da area "Cirurgia Geral", com o cargo "Interno do 1º Ano" e a faixa salarial "IF"
      Dado que exista um colaborador "Christina Yang", da area "Cirurgia Cardíaca", com o cargo "Cirurgiao Chefe" e a faixa salarial "IB"
      Dado que exista um colaborador "Owen Hunt", da area "Traumatologia", com o cargo "Chefe de Cirurgia Geral" e a faixa salarial "IA"
      
      Dado que eu desligue o colaborador "Mark Sloan" na data "25/04/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Lexie Grey" na data "31/03/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Painel de Indicadores"
         E eu clico na aba "OCORRÊNCIAS/ABSENTEÍSMO"
     Então eu devo ver "Não existem dados para o gráfico."
         E eu marco "Empresa Padrão - Cirurgiao Chefe (Ativo)"
         E eu clico no botão "Pesquisar"
     Então eu devo ver "Não existem dados para o gráfico."  


#------------------------------------------------------------------------------------------------------------------------
@teste
  Cenário: Visualizar dados de Turnover

      Dado que exista um motivo de desligamento "Redução de Quadro"
      Dado que exista um motivo de desligamento "Sem Justa Causa"
      Dado que exista um colaborador "Mark Sloan", da area "Cirurgia Plastica", com o cargo "Médico Cirurgião" e a faixa salarial "IS"
      Dado que exista um colaborador "Alex Karev", da area "Pediatria", com o cargo "Interno" e a faixa salarial "I4"
      Dado que exista um colaborador "Miranda Bailey", da area "Cirurgia Geral - Chefia", com o cargo "Chefe de Cirurgia" e a faixa salarial "IR"
      Dado que exista um colaborador "Lexie Grey", da area "Cirurgia Geral", com o cargo "Interno do 1º Ano" e a faixa salarial "IF"
      Dado que exista um colaborador "Christina Yang", da area "Cirurgia Cardíaca", com o cargo "Cirurgiao Chefe" e a faixa salarial "IB"
      Dado que exista um colaborador "Owen Hunt", da area "Traumatologia", com o cargo "Chefe de Cirurgia Geral" e a faixa salarial "IA"
      
      Dado que eu desligue o colaborador "Mark Sloan" na data "25/04/2017" com motivo de desligamento "Redução de Quadro"
      Dado que eu desligue o colaborador "Lexie Grey" na data "31/03/2017" com motivo de desligamento "Sem Justa Causa"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Painel de Indicadores"
         E eu clico na aba "TURNOVER"
         E eu marco "Empresa Padrão - Médico Cirurgião (Ativo)"
     Então eu devo ver "Redução de Quadro – 50.00% (1)"
         E eu devo deslogar do sistema