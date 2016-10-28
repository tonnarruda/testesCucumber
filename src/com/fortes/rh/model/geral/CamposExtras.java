package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.ConverterUtil;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="camposExtras_sequence", allocationSize=1)
public class CamposExtras extends AbstractModel implements Serializable
{
	@Column(length=250)
	private String texto1;	
	@Column(length=250)
	private String texto2;	
	@Column(length=250)
	private String texto3;
	@Column(length=250)
	private String texto4;
	@Column(length=250)
	private String texto5;
	@Column(length=250)
	private String texto6;
	@Column(length=250)
	private String texto7;
	@Column(length=250)
	private String texto8;
	private String textolongo1;
	private String textolongo2;
	@Temporal(TemporalType.DATE)
    private Date data1;
	@Temporal(TemporalType.DATE)
	private Date data2;
	@Temporal(TemporalType.DATE)
	private Date data3;

	private Double valor1;
	private Double valor2;
	
	private Integer numero1;

	@Transient
	private Date data1Fim;
	@Transient
	private Date data2Fim;
	@Transient
	private Date data3Fim;
	
	@Transient
	private Double valor1Fim;
	@Transient
	private Double valor2Fim;

	@Transient
	private Integer numero1Fim;
	
	public String getTexto1()
	{
		return texto1;
	}
	public void setTexto1(String texto1)
	{
		this.texto1 = texto1;
	}
	public String getTexto2()
	{
		return texto2;
	}
	public void setTexto2(String texto2)
	{
		this.texto2 = texto2;
	}
	public String getTexto3()
	{
		return texto3;
	}
	public void setTexto3(String texto3)
	{
		this.texto3 = texto3;
	}
	public Date getData1()
	{
		return data1;
	}

	public String getData1String()
	{
		return DateUtil.formataDiaMesAno(data1);
	}
	
	public void setData1(Date data1)
	{
		this.data1 = data1;
	}
	public Date getData2()
	{
		return data2;
	}
	public void setData2(Date data2)
	{
		this.data2 = data2;
	}
	public String getData2String()
	{
		return DateUtil.formataDiaMesAno(data2);
	}
	public Date getData3()
	{
		return data3;
	}
	public String getData3String()
	{
		return DateUtil.formataDiaMesAno(data3);
	}
	public void setData3(Date data3)
	{
		this.data3 = data3;
	}
	public Double getValor1()
	{
		return valor1;
	}
	public String getValor1String()
	{
		return (valor1 == null)?"":ConverterUtil.convertDoubleToString(valor1);
	}
	public void setValor1(Double valor1)
	{
		this.valor1 = valor1;
	}
	public Double getValor2()
	{
		return valor2;
	}
	public String getValor2String()
	{
		return (valor2 == null)?"":ConverterUtil.convertDoubleToString(valor2);
	}
	public void setValor2(Double valor2)
	{
		this.valor2 = valor2;
	}
	public Integer getNumero1()
	{
		return numero1;
	}
	public String getNumero1String()
	{
		return (numero1 == null)?"":String.valueOf(numero1);
	}
	public void setNumero1(Integer numero1)
	{
		this.numero1 = numero1;
	}
	public Date getData1Fim()
	{
		return data1Fim;
	}
	public void setData1Fim(Date data1Fim)
	{
		this.data1Fim = data1Fim;
	}
	public Date getData2Fim()
	{
		return data2Fim;
	}
	public void setData2Fim(Date data2Fim)
	{
		this.data2Fim = data2Fim;
	}
	public Date getData3Fim()
	{
		return data3Fim;
	}
	public void setData3Fim(Date data3Fim)
	{
		this.data3Fim = data3Fim;
	}
	public Double getValor1Fim()
	{
		return valor1Fim;
	}
	public void setValor1Fim(Double valor1Fim)
	{
		this.valor1Fim = valor1Fim;
	}
	public Double getValor2Fim()
	{
		return valor2Fim;
	}
	public void setValor2Fim(Double valor2Fim)
	{
		this.valor2Fim = valor2Fim;
	}
	public Integer getNumero1Fim()
	{
		return numero1Fim;
	}
	public void setNumero1Fim(Integer numero1Fim)
	{
		this.numero1Fim = numero1Fim;
	}
	public String getTexto4() {
		return texto4;
	}
	public void setTexto4(String texto4) {
		this.texto4 = texto4;
	}
	public String getTexto5() {
		return texto5;
	}
	public void setTexto5(String texto5) {
		this.texto5 = texto5;
	}
	public String getTexto6() {
		return texto6;
	}
	public void setTexto6(String texto6) {
		this.texto6 = texto6;
	}
	public String getTexto7() {
		return texto7;
	}
	public void setTexto7(String texto7) {
		this.texto7 = texto7;
	}
	public String getTexto8() {
		return texto8;
	}
	public void setTexto8(String texto8) {
		this.texto8 = texto8;
	}
	public String getTextolongo1() {
		return textolongo1;
	}
	public void setTextolongo1(String textolongo1) {
		this.textolongo1 = textolongo1;
	}
	public String getTextolongo2() {
		return textolongo2;
	}
	public void setTextolongo2(String textolongo2) {
		this.textolongo2 = textolongo2;
	}
}
