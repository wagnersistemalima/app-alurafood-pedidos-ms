package br.com.alurafood.pedidos.dto;

import java.math.BigDecimal;

public class PagamentoDTO {

    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private StatusPagamentoEnum status;
    private Long formaDePagamentoId;
    private Long pedidoId;

    public PagamentoDTO() {
    }

    public PagamentoDTO(Long id, BigDecimal valor, String nome, String numero, String expiracao, String codigo, StatusPagamentoEnum status, Long formaDePagamentoId, Long pedidoId) {
        this.id = id;
        this.valor = valor;
        this.nome = nome;
        this.numero = numero;
        this.expiracao = expiracao;
        this.codigo = codigo;
        this.status = status;
        this.formaDePagamentoId = formaDePagamentoId;
        this.pedidoId = pedidoId;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    public String getExpiracao() {
        return expiracao;
    }

    public String getCodigo() {
        return codigo;
    }

    public StatusPagamentoEnum getStatus() {
        return status;
    }

    public Long getFormaDePagamentoId() {
        return formaDePagamentoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setExpiracao(String expiracao) {
        this.expiracao = expiracao;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setStatus(StatusPagamentoEnum status) {
        this.status = status;
    }

    public void setFormaDePagamentoId(Long formaDePagamentoId) {
        this.formaDePagamentoId = formaDePagamentoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Override
    public String toString() {
        return "PagamentoDTO{" +
                "id=" + id +
                ", valor=" + valor +
                ", nome='" + nome + '\'' +
                ", numero='" + numero + '\'' +
                ", expiracao='" + expiracao + '\'' +
                ", codigo='" + codigo + '\'' +
                ", status=" + status +
                ", formaDePagamentoId=" + formaDePagamentoId +
                ", pedidoId=" + pedidoId +
                '}';
    }
}
