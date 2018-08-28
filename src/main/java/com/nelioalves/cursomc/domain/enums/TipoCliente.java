package com.nelioalves.cursomc.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Física"), PESSOA_JURIDICA(2, "Pessoa Juridica");

	private Integer cod;
	private String descricao;

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoCliente tipoCliente : TipoCliente.values()) {
			if (cod.equals(tipoCliente.getCod())) {
				return tipoCliente;
			}
		}

		throw new IllegalArgumentException("Código tipo de cliente não encontrado: " + cod);

	}

}
