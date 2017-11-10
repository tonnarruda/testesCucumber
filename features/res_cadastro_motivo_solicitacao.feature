# language: pt

Funcionalidade: Cadastrar Motivos de Solicitação

  Cenário: Cadastro de Motivos de Solicitação | Valida Campo Obrigatório
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Motivos de Solicitação de Pessoal"
         E eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu clico no botão "Cancelar"

  Cenário: Cadastro de Motivos de Solicitação
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Motivos de Solicitação de Pessoal"
        E eu clico no botão "Inserir"
        E eu preencho "Descrição" com "falta de mão de obra"
        E eu clico no botão "Gravar"    

  Cenário: Edição de Cadastro de Motivos de Solicitação
      Dado que exista o motivo da solicitacao "Aumento de Quadro"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Motivos de Solicitação de Pessoal"
     Entao eu clico em editar "Aumento de Quadro"
         E eu preencho "Descrição" com "Aumento de Quadro por Substituição"
         E eu clico no botão "Gravar"

  Cenário: Exclusão de Cadastro de Motivos de Solicitação
      Dado que exista o motivo da solicitacao "Aumento de Quadro"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Motivos de Solicitação de Pessoal"
     Entao eu clico em excluir "Aumento de Quadro"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Motivo de solicitação excluído com sucesso."

  Cenário: Exclusão de Cadastro de Motivos de Solicitação
      Dado que exista o motivo da solicitacao "Aumento de Quadro"
      Dado que exista uma solicitacao "Solicitação de Pessoal DEV" com o motivo "Aumento de Quadro"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Motivos de Solicitação de Pessoal"
     Entao eu clico em excluir "Aumento de Quadro"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Entidade motivo da solicitação de pessoal possui dependências em solicitação de pessoal."

