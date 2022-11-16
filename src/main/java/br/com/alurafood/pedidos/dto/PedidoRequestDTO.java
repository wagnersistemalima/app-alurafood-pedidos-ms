package br.com.alurafood.pedidos.dto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class PedidoRequestDTO {

    @Valid
    private List<ItemDoPedidoRequestDTO> itens = new ArrayList<>();

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(List<ItemDoPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public List<ItemDoPedidoRequestDTO> getItens() {
        return itens;
    }

    @Override
    public String toString() {
        return "PedidoRequestDTO{" +
                "itens=" + itens +
                '}';
    }
}
