package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.venda.VendaRequest;
import app.forgeon.forgeon_api.dto.venda.VendaResponse;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.model.Cliente;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.model.Venda;
import app.forgeon.forgeon_api.repository.ClienteRepository;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import app.forgeon.forgeon_api.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final EmpresaRepository empresaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public VendaService(VendaRepository vendaRepository,
                        EmpresaRepository empresaRepository,
                        ProdutoRepository produtoRepository,
                        ClienteRepository clienteRepository) {
        this.vendaRepository = vendaRepository;
        this.empresaRepository = empresaRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<VendaResponse> listarPorEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        return vendaRepository.findByEmpresa(empresa)
                .stream()
                .map(v -> new VendaResponse(
                        v.getId(),
                        v.getCliente().getNome(),
                        v.getProduto().getNome(),
                        v.getQuantidade(),
                        v.getValorTotal(),
                        v.getStatus(),
                        v.getData()
                ))
                .collect(Collectors.toList());
    }

    public VendaResponse criar(VendaRequest dto) {
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Venda venda = new Venda();
        venda.setEmpresa(empresa);
        venda.setProduto(produto);
        venda.setCliente(cliente);
        venda.setQuantidade(dto.getQuantidade());
        venda.setPrecoUnitario(dto.getPrecoUnitario());
        venda.setValorTotal(dto.getPrecoUnitario() * dto.getQuantidade());
        venda.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusVenda.PENDENTE);

        vendaRepository.save(venda);

        return new VendaResponse(
                venda.getId(),
                cliente.getNome(),
                produto.getNome(),
                venda.getQuantidade(),
                venda.getValorTotal(),
                venda.getStatus(),
                venda.getData()
        );
    }

    public VendaResponse atualizarStatus(Long id, StatusVenda novoStatus) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        venda.setStatus(novoStatus);
        vendaRepository.save(venda);

        return new VendaResponse(
                venda.getId(),
                venda.getCliente().getNome(),
                venda.getProduto().getNome(),
                venda.getQuantidade(),
                venda.getValorTotal(),
                venda.getStatus(),
                venda.getData()
        );
    }

    public void deletar(Long id) {
        vendaRepository.deleteById(id);
    }
}
