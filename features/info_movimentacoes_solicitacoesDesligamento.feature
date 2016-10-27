# language: pt

Funcionalidade: Cadastrar Colaborador

  @dev
  Cenário: Cadastro de Colaborador
    Dado que exista um colaborador "Loira do Tchan", da area "Dança", com o cargo "Dançarina" e a faixa salarial "Tchan"
    Dado que exista um motivo de desligamento "Porque eu quero"
    Dado que a opção de solicitação de confirmação de desligamento para a empresa seja "true"
    Dado que eu esteja deslogado
    
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Info. Funcionais > Movimentações > Aprovar/Reprovar Solicitações de Desligamento"
    E eu não devo ver "Loira do Tchan"
    
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    E eu devo ver o título "Colaboradores"

    Então eu clico na linha "Loira do Tchan" da imagem "Solicitar desligamento"
    E eu devo ver o título "Solicitar Desligamento"
    E eu preencho "Data de Desligamento" com "13/09/2013"
    E eu seleciono "Porque eu quero" de "Motivo do Desligamento"
    E eu preencho "Observações" com "massa"
    E eu clico no botão "SolicitarDesligamento"
    E eu devo ver o alert "Confirma solicitação de desligamento?" e clico no ok

    Então eu devo ver o título "Colaboradores"
    E eu devo ver "Solicitação de desligamento cadastrada com sucesso."

    Então eu acesso o menu "Info. Funcionais > Movimentações > Aprovar/Reprovar Solicitações de Desligamento"
    E eu devo ver o título "Solicitações de Desligamento"
    E eu devo ver "Loira do Tchan"
    E eu clico na linha "Loira do Tchan" da imagem "Visualizar solicitação de desligamento"
    
    Então eu devo ver o título "Solicitação de Desligamento"
    E eu devo ver "Loira do Tchan"
    E eu clico no botão "Voltar"
    
    Então eu devo ver o título "Solicitações de Desligamento"
    E eu clico na linha "Loira do Tchan" da imagem "Visualizar solicitação de desligamento"

    Então eu devo ver o título "Solicitação de Desligamento"
    E eu devo ver "Loira do Tchan"
    E eu clico no botão "Reprovar"
    E eu devo ver o alert "Deseja realmente reprovar essa solicitação de desligamento?" e clico no ok

    Então eu devo ver o título "Solicitações de Desligamento"
    E eu devo ver "Solicitação de desligamento reprovada com sucesso."
    E eu não devo ver "Loira do Tchan"

    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    E eu devo ver o título "Colaboradores"
    E eu clico na linha "Loira do Tchan" da imagem "Solicitar desligamento"
    E eu devo ver o título "Solicitar Desligamento"
    E eu preencho "Data de Desligamento" com "13/09/2013"
    E eu seleciono "Porque eu quero" de "Motivo do Desligamento"
    E eu preencho "Observações" com "massa"
    E eu clico no botão "SolicitarDesligamento"
    E eu devo ver o alert "Confirma solicitação de desligamento?" e clico no ok
    E eu devo ver "Solicitação de desligamento cadastrada com sucesso."

    Então eu acesso o menu "Info. Funcionais > Movimentações > Aprovar/Reprovar Solicitações de Desligamento"
    E eu devo ver o título "Solicitações de Desligamento"
    E eu devo ver "Loira do Tchan"
    E eu clico na linha "Loira do Tchan" da imagem "Visualizar solicitação de desligamento"

    Então eu devo ver o título "Solicitação de Desligamento"
    E eu devo ver "Loira do Tchan"
    E eu clico no botão "Aprovar"
    E eu devo ver o alert "Deseja realmente aprovar essa solicitação de desligamento?" e clico no ok

    Então eu devo ver o título "Solicitações de Desligamento"
    E eu devo ver "Colaborador desligado com sucesso."
    E eu não devo ver "Loira do Tchan"

    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    E eu devo ver o título "Colaboradores"
    E eu devo ver "Não existem colaboradores a serem listados."
    E eu não devo ver "Loira do Tchan"
    E eu clico "Exibir Filtro"
    E eu seleciono "Desligado" de "Situação"
    E eu clico no botão "Pesquisar"
    E eu devo ver "Loira do Tchan (Desligado em 13/09/2013)"

    Então eu clico na linha "Loira do Tchan" da imagem "Colaborador já desligado"
    E eu devo ver o título "Colaborador Desligado"
    E eu devo ver "Loira do Tchan"
    E eu clico no botão "Voltar"

    Então eu clico na linha "Loira do Tchan" da imagem "Colaborador já desligado"
    E eu devo ver o título "Colaborador Desligado"
    E eu devo ver "Loira do Tchan"
    E eu preencho "Observações" com "A nova Loira do Tchan"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Confirmar a alteração do dados?" e clico no ok

    Então eu devo ver o título "Colaboradores"
    E eu devo ver "Solicitação de desligamento cadastrada com sucesso."

    Então eu clico na linha "Loira do Tchan" da imagem "Colaborador já desligado"
    E eu devo ver o título "Colaborador Desligado"
    E eu devo ver "Loira do Tchan"
    E eu clico no botão "ImprimirPdf"
    E eu espero 2 segundos
    E eu clico no botão "CancelarDesligamento"
    E eu devo ver o alert "Tem certeza que deseja cancelar o desligamento?" e clico no ok

    Então eu devo ver o título "Colaboradores"
    E eu devo ver "Colaborador religado com sucesso."
    E eu não devo ver "Loira do Tchan"
    E eu seleciono "Ativo" de "Situação"
    E eu clico no botão "Pesquisar"
    E eu devo ver "Loira do Tchan"

	Dado que a opção de solicitação de confirmação de desligamento para a empresa seja "false"
    E eu acesso o menu "Sair"
	
