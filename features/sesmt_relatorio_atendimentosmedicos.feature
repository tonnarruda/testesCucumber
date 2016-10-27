# language: pt

Funcionalidade: Relatório de Atendimentos Médicos

  Cenário: Relatório de Atendimentos Médicos
    Dado que exista um medico coordenador "eufrasio"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > Atendimentos Médicos"
    Então eu devo ver o título "Atendimentos Médicos"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    E eu preencho o campo (JS) "dataIni" com "01/01/2011"
    E eu preencho o campo (JS) "dataFim" com "29/07/2011"
    Então eu seleciono "eufrasio" de "Médico"

    Então eu marco "Consulta comum"
    E eu marco "ASO Periódico"
    E eu marco "ASO Retorno ao Trabalho"
    E eu marco "ASO Mudança de Função"
    E eu marco "Solicitação de Exame"
    
    Então eu clico no botão "Relatorio"
    E eu devo ver "Não existe Atendimento Médico para os filtros informados."