package br.com.juris.academico.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

@MappedSuperclass
@SuppressWarnings("serial")
public class EntidadeAbstrata implements Serializable {
	
	@Id
	@Column
	@SequenceGenerator(name="seq_pessoa_fisica", sequenceName="seq_pessoa_fisica")
	@GeneratedValue(generator="seq_pessoa_fisica", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(length=30, nullable=false)
	private String usuarioUltimaAlteracao;
	
	@Version
	@Column(nullable=false)
	private Integer versao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuarioUltimaAlteracao() {
		return usuarioUltimaAlteracao;
	}

	public void setUsuarioUltimaAlteracao(String usuarioUltimaAlteracao) {
		this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}
}
