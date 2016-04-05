package br.com.zup.xyinc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_poi")
public class PontoInteresse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private Long x;

	@NotNull
	private Long y;
	
	public PontoInteresse() {
		
	}
	
	public PontoInteresse(String nome, long x, long y) {
		this.nome = nome;
		this.x = x;
		this.y = y;
	}
	
	public PontoInteresse(Long id, String nome, long x, long y) {
		this.id = id;
		this.nome = nome;
		this.x = x;
		this.y = y;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getX() {
		return x;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}
}
