package com.hotel.hotelchallengegradle.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hospedes")
public class Hospedes extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotBlank
	@Size(min=3, max=255)
	private String nome;
	
	@NotBlank
	@Size(min=3, max=255)
	private String documento;
	
	@NotBlank
	@Size(min=3, max=255)
	private String telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		documento = documento.replace(".", "");
		documento = documento.replace("-", "");
		documento = documento.replace("/", "");
		this.documento = documento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		telefone = telefone.replace("(", "");
		telefone = telefone.replace(")", "");
		telefone = telefone.replace("-", "");
		this.telefone = telefone;
	}
	
	
	
}
