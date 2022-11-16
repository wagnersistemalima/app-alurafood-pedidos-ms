package br.com.alurafood.pedidos.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ItemDoPedidoRequestDTO {

    @NotNull(message = "quantidade não pode ser nula")
    @Positive(message = "quantidade deve ser positivo")
    private Integer quantidade;
    @NotBlank(message = "descricao não pode ser nula ou vazia")
    private String descricao;

    public ItemDoPedidoRequestDTO() {
    }

    public ItemDoPedidoRequestDTO(Integer quantidade, String descricao) {
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ItemDoPedidoRequestDTO{" +
                "quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
