# language: pt

Funcionalidade: Motivos de Desligamento

  Cenário: Cadastro de Motivos de Desligamento
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
          E eu clico no botão "Inserir"
          E eu preencho "Motivo" com "Sem Justa Causa"
          E eu clico no botão "Gravar"
      Então eu clico no botão "Inserir"
          E eu preencho "Motivo" com "Término de Contrato"
          E eu seleciono "Não" de "Ativo"
          E eu marco "Redução de Quadro"
          E eu clico no botão "Gravar"

#---------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Motivos de Desligamento  | Validar Campos Obrigatórios
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
          E eu clico no botão "Inserir"
          E eu clico no botão "Gravar"
      Então eu devo ver o alert do valida campos e clico no ok
          E eu seleciono "Não" de "Ativo"
          E eu marco "Redução de Quadro"
          E eu clico no botão "Gravar"
      Então eu devo ver o alert do valida campos e clico no ok
          E eu clico no botão "Cancelar"

#---------------------------------------------------------------------------------------------------------

  Cenário: Pesquisar de Motivos de desligamento Ativos
       Dado que exista um motivo de desligamento "Sem Justa Causa"
       Dado que exista um motivo de desligamento "Por Justa Causa"
       Dado que exista um motivo de desligamento inativo "Pedido de Demissao"
       Dado que exista um motivo de desligamento inativo "Termino do Contrato de 90 dias"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
          E eu clico "Exibir Filtro"
          E eu clico no botão "Pesquisar"
      Então eu não devo ver "Pedido de Demissao"
          E eu não devo ver "Termino do Contrato de 90 dias"

#---------------------------------------------------------------------------------------------------------

  Cenário: Pesquisar de Motivos de desligamento Inativos
       Dado que exista um motivo de desligamento "Sem Justa Causa"
       Dado que exista um motivo de desligamento "Por Justa Causa"
       Dado que exista um motivo de desligamento inativo "Pedido de Demissao"
       Dado que exista um motivo de desligamento inativo "Termino do Contrato de 90 dias"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
          E eu desmarco "Ativos"
          E eu clico no botão "Pesquisar"
      Então eu não devo ver "Sem Justa Causa"
          E eu não devo ver "Por Justa Causa"

#---------------------------------------------------------------------------------------------------------

  Cenário: Editar Cadastro de Motivos de Desligamento
       Dado que exista um motivo de desligamento "Sem Justa Causa"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
       Entao eu clico em editar "Sem Justa Causa"
          E eu preencho "Motivo" com "Sem Justa Causa"
          E eu clico no botão "Gravar"

#---------------------------------------------------------------------------------------------------------

  Cenário: Excluir Cadastro de Motivos de Desligamento sem vinculos com empregado
       Dado que exista um motivo de desligamento "Sem Justa Causa"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
       Entao eu clico em excluir "Sem Justa Causa"
          E eu devo ver o alert do confirmar exclusão e clico no ok
          E eu devo ver "Motivo de desligamento excluído com sucesso."

#---------------------------------------------------------------------------------------------------------

  Cenário: Excluir Cadastro de Motivos de Desligamento vinculados a empregados
       Dado que exista um motivo de desligamento "Sem Justa Causa"
       Dado que exista um colaborador "Ellen Pompeo", da area "Desenvolvimento", com o cargo "Product Owner" e a faixa salarial "A"
       Dado que eu desligue o colaborador "Ellen Pompeo" na data "29/05/2017" com motivo de deligamento "Sem Justa Causa"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
       Entao eu clico em excluir "Sem Justa Causa"
          E eu devo ver o alert do confirmar exclusão e clico no ok
          E eu devo ver "Entidade motivo de demissão possui dependências em colaborador."











