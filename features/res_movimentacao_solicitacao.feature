# language: pt

Funcionalidade: Movimentação Solicitações de Pessoal

  Cenário: Movimentação de Solicitações de Pessoal
    
    Dado que haja uma area organizacional com id 1, nome "Financeiro", ativo "true", empresa_id 1
    Dado que haja uma cargo com id 1, nome "Contador", nomemercado "Contador", empresa_id 1
    Dado que haja uma faixa salarial com id 1, nome "I", cargo_id 1
    Dado que haja uma faixa salarial historico com id 1, data "2011-06-01", faixasalarial_id 1, tipo 3, valor 500, status 1
    Dado que haja um motivo da solicitacao com id 1 e descricao "Aumento de quadro"
    
    Dado que exista um nivel de competencia "ruim"
    Dado que exista um nivel de competencia "regular"
    Dado que exista um nivel de competencia "bom"
    Dado que exista um historico de nivel de competencia na data "01/01/2010"
    Dado que exista uma configuracao de nivel de competencia com nivel "ruim" no historico do nivel de data "01/01/2010" na ordem 1
    Dado que exista uma configuracao de nivel de competencia com nivel "regular" no historico do nivel de data "01/01/2010" na ordem 2
    Dado que exista uma configuracao de nivel de competencia com nivel "bom" no historico do nivel de data "01/01/2010" na ordem 3
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista um candidato "Nikita"
    Dado que exista um bairro "Aldeota" na cidade de "Fortaleza"
    Dado que exista uma etapa seletiva "Entrevista"
    Dado que exista um conhecimento "Java"
	Dado que a obrigatoriedade dos dados complementares da solicitação de pessoal seja "true"

    Quando eu acesso o menu "R&S > Cadastros > Modelos de Avaliação do Candidato"
         E eu devo ver o título "Modelos de Avaliação do Candidato"
         E eu clico no botão "Inserir"
         E eu preencho "Título" com "Avaliação do Aluno"
         E eu preencho "Observação" com "obs"
         E eu preencho "Percentual mínimo para aprovação" com "70"
         E eu clico no botão "Avancar"
         E eu devo ver o título "Inserir Pergunta da Avaliação"
         E eu preencho o campo (JS) "pergunta" com "Pergunta 1"
         E eu clico no botão "Gravar"
         E eu devo ver "Pergunta gravada com sucesso"
         E eu seleciono (JS) "tipo" de "4"
         E eu saio do campo "tipo"
         E eu preencho o campo (JS) "pergunta" com "Pergunta 2"
         E eu clico no botão "Gravar"
         E eu devo ver "Pergunta gravada com sucesso"
         E eu clico no botão "Voltar"
         E eu devo ver o título "Perguntas da Avaliação"
         E eu clico no botão "Voltar"
         E eu devo ver o título "Modelos de Avaliação do Candidato"

    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos"
    E  eu clico em editar "Java"
    E eu marco "Financeiro (Ativa)"
    E eu clico no botão "Gravar"

	Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
    E  eu clico em editar "Contador"
    E eu marco "Financeiro (Ativa)"
    E eu marco "Java"
    E eu clico no botão "Gravar"

	Quando eu clico na linha "Contador" da imagem "Faixas Salariais"
	E eu devo ver o título "Faixas Salariais"
	E eu clico na linha "I" da imagem "Níveis de Competência"
	E eu clico no botão "Inserir"
    E eu devo ver o título "Competências da Faixa Salarial"
    E eu marco "Java"
	E eu marco o checkbox com name "niveisCompetenciaFaixaSalariaisConhecimento[0].nivelCompetencia.id"
    E eu clico no botão "Gravar"
	Então eu clico no botão "Voltar"

    Quando eu acesso o menu "R&S > Movimentações > Solicitação de Pessoal"
    Então eu devo ver o título "Solicitação de Pessoal"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação de Pessoal"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Solicitação de Pessoal"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Solicitação de Pessoal"
    E eu preencho o campo (JS) "Data" com "10/06/2020"
    E eu preencho "Descrição" com "Vaga java"
    E eu preencho "Horário de Trabalho" com "8h às 18h"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Financeiro" de "Área Organizacional"
    E eu seleciono "Contador I" de "Cargo/Faixa"
    E eu seleciono "Aumento de quadro" de "Motivo da Solicitação"
    E eu marco "Avaliação do Aluno"
    E eu clico no botão "Gravar"

    Então eu devo ver o alert "Preencha os campos indicados." e clico no ok
    E eu preencho "Remuneração (R$)" com "1000"
    E eu seleciono "Mestrado completo" de "Escolaridade mínima"
    E eu preencho o campo (JS) "dataPrevIni" com "18"
    E eu preencho o campo (JS) "dataPrevFim" com "50"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Selecione..." de "Cidade"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu saio do campo "Cidade"
    E eu marco "Aldeota"
    E eu preencho "Informações Complementares" com "Informações Complementares"
    E eu clico no botão "Gravar"

    E eu devo ver o título "Solicitação de Pessoal"
    E eu devo ver "Vaga java"

    Então eu clico na linha "1" da imagem "Anunciar"
    E eu devo ver o título "Inserir Anúncio de Solicitação de Pessoal"
    E eu preencho "Título" com "Vaga java Externo"
    E eu preencho "Descrição" com "Vaga java Externo"
    E eu seleciono "Sim" de "Exibir no módulo externo"
    E eu clico no botão "EnviarPorEmail"
    E eu devo ver o título "Enviar Anúncio por E-mail"
    E eu preencho "Assunto" com "Vaga java Externo"
    E eu clico no botão "Enviar"
    E eu devo ver o alert "Nenhum Destinatário Informado." e clico no ok
    E eu preencho "Destinatário(s) Avulso(s) (Separe com ponto-e-vírgula)" com "samuelpinheiro@entetecnologia.com.br"
    E eu clico no botão "Enviar"
    E eu devo ver o título "Solicitação de Pessoal"
    Então eu clico na linha "1" da imagem "Anunciado"
    E eu devo ver o título "Editar Anúncio de Solicitação de Pessoal"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Solicitação de Pessoal"
    Então eu clico na linha "1" da imagem "Anunciado"
    E eu devo ver o título "Editar Anúncio de Solicitação de Pessoal"
    E eu clico no botão "EnviarPorEmail"
    E o campo "Assunto" deve conter "Vaga java Externo"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Editar Anúncio de Solicitação de Pessoal"
    E eu clico no botão "Gravar"

    Então eu clico na linha "1" da imagem "Suspender solicitação"
    E eu clico no botão "SuspenderSolicitacao"
    E eu devo ver o alert "Preencha os campos indicados." e clico no ok
    E eu preencho "Observações sobre a suspensão" com "Observações sobre a suspensão"
    E eu clico no botão "SuspenderSolicitacao"

    Entao eu clico "linkFiltro"
    E eu seleciono "Todas" de "Visualizar"
    E eu clico no botão "Pesquisar"

    Então eu clico na linha "1" da imagem "Alterar status (Em análise)"
    E eu espero 1 segundos
    E eu seleciono (JS) "A" de "statusSolicitcao"
    E eu preencho "Observações" com "Alterando status apar aprovada +++**** /// ///"
    E eu clico no botão "Gravar"

    Então eu clico na linha "1" da imagem "Alterar status (Aprovada)"
    E eu espero 1 segundos
    E eu seleciono (JS) "R" de "statusSolicitcao"
    E eu preencho "Observações" com "Alterando status para reprovada +++**** /// ///"
    E eu clico no botão "Gravar"

    Então eu clico na linha "1" da imagem "Alterar status (Reprovada)"
    E eu espero 1 segundos
    E eu seleciono (JS) "I" de "statusSolicitcao"
    E eu preencho "Observações" com "Alterando status para em análise +++**** /// ///"
    E eu clico no botão "Gravar"

    Então eu clico na linha "1" da imagem "Clonar"
    E eu devo ver o título "Inserir Solicitação de Pessoal"
    E eu preencho o campo (JS) "Data" com "11/06/2021"
    E eu preencho "Descrição" com "JavaScript"
    E eu preencho "Horário de Trabalho" com "8h às 18h"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Financeiro" de "Área Organizacional"
    E eu seleciono "Contador I" de "Cargo/Faixa"
    E eu seleciono "Aumento de quadro" de "Motivo da Solicitação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitação de Pessoal"
    E eu devo ver "Vaga java"
    E eu devo ver "JavaScript"
    E eu devo ver "11/06/2021"

    Então eu clico na linha "1" da imagem "Liberar solicitação. Observação: Observações sobre a suspensão"
    E eu clico na linha "1" da imagem "Suspender solicitação"
    E eu clico no botão de Id "btnCancelarSuspenderSolicitacao"

    Então eu clico na linha "1" da imagem "Candidatos da Seleção"
    E eu devo ver o título "Candidatos da Seleção"

    Então eu clico no botão "Triagem"
    E eu devo ver o título "Inserir Candidatos na Solicitação"
    E eu clico no botão "LimparFiltro"
    E eu clico no botão "Pesquisar"
    E eu devo ver "Nikita"
    E eu clico no botão "InserirSelecionados"
    E eu devo ver o alert "Nenhum candidato selecionado" e clico no ok
    E eu marco "md"
    E eu clico no botão "InserirSelecionados"
    E eu devo ver o título "Candidatos da Seleção"
    E eu devo ver "Área: Financeiro"
    E eu devo ver "Cargo: Contador"
    E eu devo ver "Solicitante: SOS"
    E eu devo ver "Vagas: 1"
    E eu devo ver "Nikita"
    
    Entao eu clico no botão "TriagemModuloExterno"
    E eu devo ver o título "Candidatos da Seleção - Triagem"
    E eu devo ver "Área: Financeiro"
    E eu devo ver "Cargo: Contador"
    E eu devo ver "Solicitante: SOS"
    E eu devo ver "Vagas: 1"
    E eu clico no botão "InserirSelecionados"
    E eu clico no botão "Voltar"

    Entao eu clico no botão "TransferirCandidatos"
    E eu clico no botão "Voltar"

    Entao eu clico no botão "TransferirCandidatos"
    E eu marco "md"
    E eu marco o checkbox com name "solicitacaoDestino.id"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Candidatos da Seleção"

    Entao eu clico no botão "Voltar"
    E eu devo ver o título "Solicitação de Pessoal"

    Então eu clico na linha "1" da imagem "Encerrar Solicitação"
    E eu preencho "Data de Encerramento" com "20/09/2013"
    E eu preencho "Observação" com "Solicitação"
    E eu clico no botão "EncerrarSolicitacao"

    Entao eu clico em editar "Vaga java"
    E eu devo ver o título "Editar Solicitação de Pessoal"
    E eu marco "Avaliação do Aluno"
    E o campo "Descrição" deve conter "Vaga java"
    E eu preencho "Descrição" com "Vaga java EE"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitação de Pessoal"

    Então eu clico na linha "Vaga java EE" da imagem "Candidatos da Seleção"

    Então eu clico na linha "Nikita" da imagem "Competências"
    E eu devo ver o título "Competências do Candidato"
    E eu devo ver "Candidato: Nikita"
    E eu marco "Java"
    E eu marco o checkbox com name "niveisCompetenciaFaixaSalariais[0].nivelCompetencia.id"
    E eu clico no botão "Gravar"
    E eu devo ver "Níveis de competência do candidato salvos com sucesso"
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Candidatos da Seleção"
    E eu clico na linha "Nikita" da imagem "Visualizar Currículo"

    Então eu devo ver o título "Candidatos da Seleção"
    E eu clico na linha "Nikita" da imagem "Documentos Anexos"
    E eu devo ver o título "Documentos do Candidato: Nikita"
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Novo Documento do Candidato: Nikita"
    E eu preencho "Descrição" com "Documento"
    E eu preencho "Observação" com "Documento para fazer amor"
    E eu preencho o campo (JS) "data" com "01/10/2013"
    E eu seleciono (JS) "fase" de "Entrevista"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Preencha os campos indicados" e clico no ok
    E eu clico no botão "Cancelar"
    E eu devo ver o título "Documentos do Candidato: Nikita"
    E eu clico no botão "Cancelar"
    E eu devo ver o título "Candidatos da Seleção"

    Então eu clico na linha "Nikita" da imagem "Histórico"
    E eu devo ver "Histórico do Candidato"
    E eu devo ver "Área: Financeiro"
    E eu devo ver "Cargo: Contador"
    E eu devo ver "Nikita"

    Então eu clico no botão "Inserir"
    E eu seleciono (JS) "fase" de "Entrevista"
    E eu preencho o campo (JS) "data" com "01/10/2023"
    E eu preencho o campo (JS) "horaIni" com "08:00"
    E eu preencho o campo (JS) "horaFim" com "10:00"
    E eu preencho "Responsável" com "Samuel"
    E eu seleciono (JS) "apto" de "Sim"
    E eu seleciono (JS) "indisp" de "Não"
    E eu preencho "Observação" com "Candidato ápto"
    E eu clico no botão "Gravar"

    Então eu devo ver "Histórico do Candidato"
    E eu devo ver "Entrevista"
    E eu devo ver "01/10/2023"

    Então eu clico na linha "Entrevista" da imagem "Editar"
    E o campo "Responsável" deve conter "Samuel"
    E o campo "Data" deve conter "01/10/2023"
    E eu clico no botão "Cancelar"
    E eu clico no botão "ImprimirPdf"
    E eu espero 2 segundos
    E eu devo ver "Histórico do Candidato"
    E eu clico na linha "Entrevista" da imagem "Excluir"
    E eu devo ver o alert "Confirma exclusão?" e clico no ok
    E eu devo ver "Histórico do Candidato"
    E eu clico no botão "Voltar"

    Então eu clico na linha "Nikita" da imagem "Avaliações da Solicitação"
    E eu clico na imagem com o título "Responder Avaliação"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Deseja realmente gravar o questionário sem responder todas as perguntas?" e clico no sim
    E eu devo ver o título "Candidatos da Seleção"

    Então eu clico na linha "Nikita" da imagem "Excluir"
    E eu devo ver o alert "Confirma exclusão?" e clico no ok
    E eu devo ver "Não existem candidatos para o filtro informado"
    E eu não devo ver "Nikita"
    E eu clico no botão "Voltar"

    Dado que a obrigatoriedade dos dados complementares da solicitação de pessoal seja "false"
    
    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Solicitação de Pessoal"
    E eu preencho o campo (JS) "Data" com "10/06/2011"
    E eu preencho "Descrição" com "Vaga java II"
    E eu preencho "Horário de Trabalho" com "8h às 18h"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Financeiro" de "Área Organizacional"
    E eu seleciono "Contador I" de "Cargo/Faixa"
    E eu seleciono "Aumento de quadro" de "Motivo da Solicitação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitação de Pessoal"
    E eu devo ver "Vaga java II"
    