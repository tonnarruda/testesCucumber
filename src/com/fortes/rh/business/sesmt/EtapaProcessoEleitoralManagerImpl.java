package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EtapaProcessoEleitoralDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;

@Component
public class EtapaProcessoEleitoralManagerImpl extends GenericManagerImpl<EtapaProcessoEleitoral, EtapaProcessoEleitoralDao> implements EtapaProcessoEleitoralManager
{
	@Autowired private EleicaoManager eleicaoManager;

	@Autowired
	EtapaProcessoEleitoralManagerImpl(EtapaProcessoEleitoralDao etapaProcessoEleitoralDao) {
		setDao(etapaProcessoEleitoralDao);
	}
	
	public Collection<EtapaProcessoEleitoral> findAllSelect(Long empresaId, Long eleicaoId)
	{
		String orderBy = (eleicaoId == null) ? "prazo" : "data";
		return getDao().findAllSelect(empresaId, eleicaoId, orderBy);
	}

	/**
	 * Clona as etapas padrão para uma eleição
	 */
	public void clonarEtapas(Long empresaId, Eleicao eleicao)
	{
		Collection<EtapaProcessoEleitoral> colecaoEtapasSemEleicao = getDao().findAllSelect(empresaId, null, "prazo");

		for (EtapaProcessoEleitoral etapaProcessoEleitoral : colecaoEtapasSemEleicao)
		{
			etapaProcessoEleitoral.setId(null);
			etapaProcessoEleitoral.setEleicao(eleicao);
			etapaProcessoEleitoral.setDataPrazoCalculada(eleicao.getPosse(), false);
			getDao().save(etapaProcessoEleitoral);
		}
	}

	public Eleicao findImprimirCalendario(Long eleicaoId)
	{
		Eleicao eleicao = eleicaoManager.findByIdProjection(eleicaoId);
		Collection<EtapaProcessoEleitoral> colecao = getDao().findAllSelect(null, eleicaoId, "prazo");
		eleicao.setEtapaProcessoEleitorals(colecao);
		return eleicao;
	}

	public void removeByEleicao(Long eleicaoId)
	{
		getDao().removeByEleicao(eleicaoId);
	}

	@Override
	public EtapaProcessoEleitoral save(EtapaProcessoEleitoral etapaProcessoEleitoral)
	{
		calcularDataPrazo(etapaProcessoEleitoral);
		return super.save(etapaProcessoEleitoral);
	}

	@Override
	public void update(EtapaProcessoEleitoral etapaProcessoEleitoral)
	{
		calcularDataPrazo(etapaProcessoEleitoral);
		super.update(etapaProcessoEleitoral);
	}

	/**
	 * Recalcula o prazo da etapa. */
	private void calcularDataPrazo(EtapaProcessoEleitoral etapaProcessoEleitoral)
	{
		if(etapaProcessoEleitoral.getEleicao() != null)
		{
			Eleicao eleicao = eleicaoManager.findByIdProjection(etapaProcessoEleitoral.getEleicao().getId());
			etapaProcessoEleitoral.setDataPrazoCalculada(eleicao.getPosse(), true);
		}
	}
	
	public void updatePrazos(Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals, Date posse)
	{
		for (EtapaProcessoEleitoral etapaProcessoEleitoral : etapaProcessoEleitorals)
		{
			etapaProcessoEleitoral.setDataPrazoCalculada(posse, true);
			getDao().update(etapaProcessoEleitoral);
		}
	}
	
	public void gerarEtapasModelo(Empresa empresa) 
	{
		gerarEtapaModelo(empresa, "Edital de Convocação para eleição", "60 dias antes da posse", -60);
		gerarEtapaModelo(empresa, "Formação da Comissão Eleitoral", "55 dias antes do término do mandato", -55);
		gerarEtapaModelo(empresa, "Enviar cópia do Edital de Convocação ao sindicato", "5 dias após a convocação da eleição", -55);
		gerarEtapaModelo(empresa, "Início das inscrições dos candidatos", "20 dias antes da eleição", -50);
		gerarEtapaModelo(empresa, "Publicação do Edital de Inscrição de candidatos", "45 dias antes do término do mandato", -45);
		gerarEtapaModelo(empresa, "Término das inscrições dos candidatos", "6 dias antes da eleição", -36);
		gerarEtapaModelo(empresa, "Retirada do Edital de Inscrições", "Dia seguinte ao encerramento das inscrições", -35);
		gerarEtapaModelo(empresa, "Retirada do Edital de Convocação", "No dia da eleição", -30);
		gerarEtapaModelo(empresa, "Realização da eleição (votação)", "30 dias antes do término do mandato anterior", -30);
		gerarEtapaModelo(empresa, "Realização da apuração", "Mesmo dia da eleição", -30);
		gerarEtapaModelo(empresa, "Resultado da eleição - Ata da eleição", "1 dia após a apuração", -29);
		gerarEtapaModelo(empresa, "Curso para cipeiros (data mínima)", "Depois da eleição", -28);
		gerarEtapaModelo(empresa, "Comunicar ao sindicato o resultado e a data da posse", "15 dias após a eleição", -15);
		gerarEtapaModelo(empresa, "Curso para cipeiros (data máxima)", "Antes da posse", -2);
		gerarEtapaModelo(empresa, "Término do mandato anterior", "1 ano depois da posse do mandato anterior", 0);
		gerarEtapaModelo(empresa, "Realização da Posse - Ata de Posse de novos membros", "1º dia depois do mandato anterior", 0);
		gerarEtapaModelo(empresa, "Organização do Calendário de Reuniões Mensais", "Na reunião da posse", 0);
		gerarEtapaModelo(empresa, "Registro da CIPA na DRT", "Até 10 dias depois da posse", 5);		
	}
	
	private void gerarEtapaModelo(Empresa empresa, String nome, String prazoLegal, Integer prazo) {
		
		EtapaProcessoEleitoral etapaProcessoEleitoral = new EtapaProcessoEleitoral();
		etapaProcessoEleitoral.setId(null);
		etapaProcessoEleitoral.setEmpresa(empresa);
		etapaProcessoEleitoral.setNome(nome);
		etapaProcessoEleitoral.setPrazoLegal(prazoLegal);
		etapaProcessoEleitoral.setPrazo(prazo);
		
		getDao().save(etapaProcessoEleitoral);
	}
}