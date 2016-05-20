package com.fortes.rh.model.geral;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.util.DateUtil;

@Entity
public class ColaboradorJsonVO
{
	@Id
	private String id;
    
    private String address;
    private String burgh;
    private String cep;
    private String city;
    private String cpf;
    private String email;
    private String genre;
    private String name;
    private String phone;
    private String uf;
    private String rg;
    private String education;
    private String motherName;
    private String PIS;
    private String coren;
    private String effective;
    private String function;
    private String birth;
    private String registration;
    private String hiringEnd;
    private String hiringStart;
    private String status;

    public ColaboradorJsonVO()
    {
    }
    
    public ColaboradorJsonVO(String address, String burgh, String cep, String city, String cpf, String email, Character genre,
     String name, String phone, String uf, String rg, String education, String motherName, String PIS, String registration, String vinculo,
     Date birth, Integer coren, Date hiringEnd, Date hiringStart, String function, boolean desligado)
    {
    	this.address = address;
    	this.burgh = burgh;
    	this.cep = cep;
    	this.city = city;
    	this.cpf = cpf;
    	this.email = email;
    	this.genre = genre == null ? "" : new Sexo().get(genre.toString());
    	this.name = name;
    	this.phone = phone;
    	this.uf = uf;
    	this.rg = rg;
    	this.education = Escolaridade.getEscolaridade(education);
    	this.motherName = motherName;
    	this.PIS = PIS;
    	this.registration = registration;
    	this.effective = new Vinculo().get(vinculo);
    	this.function = function;
    	this.birth = DateUtil.formataDiaMesAno(birth);
    	this.coren = coren == null ? "" : coren.toString();
    	this.hiringEnd = DateUtil.formataDiaMesAno(hiringEnd);
    	this.hiringStart = DateUtil.formataDiaMesAno(hiringStart);
    	this.status = desligado ? "Inativo" : "Ativo";
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBurgh() {
		return burgh;
	}

	public void setBurgh(String burgh) {
		this.burgh = burgh;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getPIS() {
		return PIS;
	}

	public void setPIS(String pIS) {
		PIS = pIS;
	}

	public String getCoren() {
		return coren;
	}

	public void setCoren(String coren) {
		this.coren = coren;
	}

	public String getEffective() {
		return effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getHiringEnd() {
		return hiringEnd;
	}

	public void setHiringEnd(String hiringEnd) {
		this.hiringEnd = hiringEnd;
	}

	public String getHiringStart() {
		return hiringStart;
	}

	public void setHiringStart(String hiringStart) {
		this.hiringStart = hiringStart;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}