# language: pt

Funcionalidade: Configuração do limite de Colaboradores por Cargo

  Cenário: Configurando o limite de Colaboradores por Cargo
    Dado que exista a área organizacional "geral"
    Dado que exista o cargo "Fazendeiro"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Configurações > Limite de Colab. por Cargo"
    Então eu devo ver o título "Configuração do limite de Colaboradores por Cargo"
    Então eu clico no botão "Inserir"

    Então eu devo ver o título "Configurar limite de Colaboradores por Cargo"
    Então eu preencho "Contrato" com "123456"
    E eu seleciono "geral" de "Área Organizacional"
    E eu seleciono "Fazendeiro" de "cargo"
    E eu clico "Adicionar Cargo"
    E eu preencho "quantidadeLimiteColaboradoresPorCargos[0].limite" com "2"

    E eu clico no botão "Gravar"
    Então eu devo ver "Configuração gravada com sucesso."