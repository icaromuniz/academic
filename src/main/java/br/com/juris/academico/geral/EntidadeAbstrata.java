package br.com.juris.academico.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class EntidadeAbstrata implements Serializable {
	
	@Column(length=11, nullable=false)
	private String usuarioCriacao;
	
	@Column(nullable=false)
	private Date dataCriacao;
	
	@Version
	@Column(nullable=false)
	private Integer versao;
	
	public abstract Integer getId();
	
	public abstract void setId(Integer id);

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public String getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getId() != null && obj != null && this.getId().equals(((EntidadeAbstrata)obj).getId());
	}
}
