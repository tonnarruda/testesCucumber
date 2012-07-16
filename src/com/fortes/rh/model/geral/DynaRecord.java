/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;

public class DynaRecord implements Serializable, Cloneable
{
	private String campo1;
	private String campo2;
	private String campo3;
	private String campo4;
	private String campo5;
	private String campo6;
	private String campo7;
	private String campo8;
	private String campo9;
	private String campo10;
	private String campo11;
	private String campo12;
	private String campo13;
	private String campo14;
	private String campo15;
	private String tempoServico = "TESTE";
	private Colaborador colaborador;
	
	public DynaRecord(){}

	public String getCampo1() {
		return campo1;
	}

	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}

	public String getCampo2() {
		return campo2;
	}

	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}

	public String getCampo3() {
		return campo3;
	}

	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}

	public String getCampo4() {
		return campo4;
	}

	public void setCampo4(String campo4) {
		this.campo4 = campo4;
	}

	public String getCampo5() {
		return campo5;
	}

	public void setCampo5(String campo5) {
		this.campo5 = campo5;
	}

	public String getCampo6() {
		return campo6;
	}

	public void setCampo6(String campo6) {
		this.campo6 = campo6;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public static Object montaEval(String elementoPai, String atributo, int key, int valor) 
	{
		String path = elementoPai + "..reportElement.(@key==\"textField"+ key +"\").@" + atributo;
		return " xml." + path + " = \"" + valor + "\"; ";
	}

	public String getCampo7() {
		return campo7;
	}

	public void setCampo7(String campo7) {
		this.campo7 = campo7;
	}

	public String getCampo8() {
		return campo8;
	}

	public void setCampo8(String campo8) {
		this.campo8 = campo8;
	}

	public String getCampo9() {
		return campo9;
	}

	public void setCampo9(String campo9) {
		this.campo9 = campo9;
	}

	public String getCampo10() {
		return campo10;
	}

	public void setCampo10(String campo10) {
		this.campo10 = campo10;
	}

	public String getCampo11() {
		return campo11;
	}

	public void setCampo11(String campo11) {
		this.campo11 = campo11;
	}

	public String getCampo12() {
		return campo12;
	}

	public void setCampo12(String campo12) {
		this.campo12 = campo12;
	}

	public String getCampo13() {
		return campo13;
	}

	public void setCampo13(String campo13) {
		this.campo13 = campo13;
	}

	public String getCampo14() {
		return campo14;
	}

	public void setCampo14(String campo14) {
		this.campo14 = campo14;
	}

	public String getCampo15() {
		return campo15;
	}

	public void setCampo15(String campo15) {
		this.campo15 = campo15;
	}

	public String getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(String tempoServico) {
		this.tempoServico = tempoServico;
	}
}
