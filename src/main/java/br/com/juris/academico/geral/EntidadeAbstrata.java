package br.com.juris.academico.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public class EntidadeAbstrata implements Serializable {
	
	@Id
	@Column
	@GeneratedValue
	private Integer id;
	
	@Column(length=30, nullable=false)
	private String usuarioUltimaAlteracao;
	
	@Column(nullable=false)
	private Integer versao;
}
