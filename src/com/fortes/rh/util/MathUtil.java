package com.fortes.rh.util;
import java.text.DecimalFormat;
import java.util.Locale;

public class MathUtil {

	public static double calculaPMT(double PV, double vlrPercentual, int numParcelas)
	{
		vlrPercentual = vlrPercentual/100;

		double PMT = ((PV * vlrPercentual)/(1-(Math.pow((1+vlrPercentual),(numParcelas * -1))))) * (1/(1+vlrPercentual));
		DecimalFormat df = new DecimalFormat("0.00");

		return Double.parseDouble(df.format(PMT).replace(",", "."));
	}

	public static double round(double valor, int qtdCasasDecimais)
	{
		double fatorMultiplicador = Math.pow(10, qtdCasasDecimais);

		return Math.round( (valor * fatorMultiplicador)) / fatorMultiplicador;
	}

	public static double calculaDesconto(Double valorTabela, Double valorFinal)
	{
		Double desc = round(valorTabela - valorFinal, 2);

		return desc;
	}

	public static Float formataValor(Float valor)
	{
		DecimalFormat df = new DecimalFormat("0.00");

		return Float.parseFloat(df.format(valor).replace(",", "."));
	}
	
	public static String formataValor(Double valor)
	{
//		NumberFormat nf = new DecimalFormat("###,##0.00");
		DecimalFormat nf = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		nf.applyPattern("###,##0.00");
		return nf.format(valor);
	}

	public static Double calculaDissidio(char dissidioPor, Double valorDissidio, Double salarioAtual) throws Exception
	{
		Double salario = 0.0;
		//DissidioPor		1 = Porcentagem (%)		2 = Valor (R$)
		switch (dissidioPor)
		{
			case '1':
				salario = salarioAtual + (salarioAtual * (valorDissidio / 100));
				break;
			case '2':
				salario = salarioAtual + valorDissidio;
				break;
			default:
				throw new Exception();
		}

		return salario;
	}
	
	public static String calculaPorcentagem(Integer parteUm, Integer parteDois)
	{
		try
		{
			if(parteUm != null && parteDois != null)
			{
				Double valorUm = parteUm.doubleValue();
				Double valorDois = parteDois.doubleValue();
				
				Double total = valorUm + valorDois;
				return formataValor((valorUm / total) * 100.0) + " %";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String formataPercentual(Double numero) 
	{
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt","BR"));//new DecimalFormat("0.##");
		df.applyPattern("0.##");
		
		return df.format((numero * 100)) + "%";
	}
}
