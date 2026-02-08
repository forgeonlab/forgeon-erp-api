package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.produto.*;
import app.forgeon.forgeon_api.enums.TipoMovimentacaoEstoque;
import app.forgeon.forgeon_api.exception.ProdutoNaoEncontradoException;
import app.forgeon.forgeon_api.exception.SkuDuplicadoException;
import app.forgeon.forgeon_api.mapper.*;
import app.forgeon.forgeon_api.model.*;
import app.forgeon.forgeon_api.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoProducaoRepository producaoRepository;
    private final ProdutoPrecoRepository precoRepository;

    private final ProdutoMapperManual produtoMapper;
    private final ProdutoProducaoMapperManual producaoMapper;
    private final ProdutoPrecoMapperManual precoMapper;

    private final CalculadoraPrecoService calculadoraPrecoService;

    @Transactional
    public ProdutoDetalhadoResponseDTO criarProduto(
            ProdutoCreateRequestDTO produtoDTO,
            ProdutoProducaoRequestDTO producaoDTO,
            List<ProdutoPrecoRequestDTO> precosDTO,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ){


    // 1️⃣ valida SKU
        if (produtoRepository.existsByEmpresaPublicIdAndSku(
                empresaPublicId, produtoDTO.sku())) {
            throw new SkuDuplicadoException(produtoDTO.sku());
        }

        // 2️⃣ cria produto
        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto.setEmpresaPublicId(empresaPublicId);
        produto.setCriadoPor(usuarioPublicId);

        produto = produtoRepository.save(produto);

        // 3️⃣ produção
        ProdutoProducao producao = producaoMapper.toEntity(producaoDTO);
        producao.setProduto(produto);
        producaoRepository.save(producao);

        // custo base (exemplo simples)
        BigDecimal custoBase = producao.getHorasProducao()
                .multiply(BigDecimal.valueOf(10)) // custo/hora (depois vira config)
                .add(producao.getFilamentoTotalGramas()
                        .multiply(BigDecimal.valueOf(0.08))); // custo/grama

        // 4️⃣ preços
        Produto finalProduto = produto;
        List<ProdutoPreco> precos = precosDTO.stream().map(dto -> {
            ProdutoPreco preco = precoMapper.toEntity(dto);
            preco.setProduto(finalProduto);

            BigDecimal precoFinal = calculadoraPrecoService.calcularPreco(
                    custoBase,
                    dto.porcentagemLucro(),
                    dto.porcentagemMarketplace()
            );

            preco.setPrecoSugerido(precoFinal);
            preco.setPrecoFinal(precoFinal);

            return precoRepository.save(preco);
        }).toList();

        // 5️⃣ retorno
        return new ProdutoDetalhadoResponseDTO(
                produtoMapper.toResponseDTO(produto),
                producaoMapper.toResponseDTO(producao),
                precos.stream().map(precoMapper::toResponseDTO).toList()
        );
    }

    @Transactional
    public ProdutoDetalhadoResponseDTO buscarPorPublicId(
            UUID publicId,
            UUID empresaPublicId
    ) {

        Produto produto = produtoRepository.findByPublicId(publicId)
                .filter(p -> p.getEmpresaPublicId().equals(empresaPublicId))
                .orElseThrow(() -> new ProdutoNaoEncontradoException(publicId));

        return montarDetalhado(produto);
    }


    @Transactional
    public List<ProdutoResponseDTO> listarPorEmpresa(UUID empresaPublicId) {

        return produtoRepository.findAllByEmpresaPublicId(empresaPublicId)
                .stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public ProdutoDetalhadoResponseDTO atualizarProduto(
            UUID publicId,
            ProdutoUpdateFullRequestDTO request,
            UUID empresaPublicId
    ) {

        Produto produto = produtoRepository.findByPublicId(publicId)
                .filter(p -> p.getEmpresaPublicId().equals(empresaPublicId))
                .orElseThrow(() -> new ProdutoNaoEncontradoException(publicId));

        // produção
        ProdutoProducao producao = produto.getProducao();
        producao.setHorasProducao(request.producao().horasProducao());
        producao.setFilamentoTotalGramas(request.producao().filamentoTotalGramas());

        // recalcula custo base
        BigDecimal custoBase = producao.getHorasProducao()
                .multiply(BigDecimal.valueOf(10))
                .add(producao.getFilamentoTotalGramas()
                        .multiply(BigDecimal.valueOf(0.08)));

        // remove preços antigos
        produto.getPrecos().clear();

        // cria novos preços
        List<ProdutoPreco> novosPrecos = request.precos().stream().map(dto -> {
            ProdutoPreco preco = precoMapper.toEntity(dto);
            preco.setProduto(produto);

            BigDecimal precoFinal = calculadoraPrecoService.calcularPreco(
                    custoBase,
                    dto.porcentagemLucro(),
                    dto.porcentagemMarketplace()
            );

            preco.setPrecoSugerido(precoFinal);
            preco.setPrecoFinal(precoFinal);
            return preco;
        }).toList();

        produto.getPrecos().addAll(novosPrecos);

        Produto salvo = produtoRepository.save(produto);

        return new ProdutoDetalhadoResponseDTO(
                produtoMapper.toResponseDTO(salvo),
                producaoMapper.toResponseDTO(salvo.getProducao()),
                salvo.getPrecos().stream()
                        .map(precoMapper::toResponseDTO)
                        .toList()
        );
    }

    private ProdutoDetalhadoResponseDTO montarDetalhado(Produto produto) {
        return new ProdutoDetalhadoResponseDTO(
                produtoMapper.toResponseDTO(produto),
                produto.getProducao() != null
                        ? producaoMapper.toResponseDTO(produto.getProducao())
                        : null,
                produto.getPrecos().stream()
                        .map(precoMapper::toResponseDTO)
                        .toList()
        );
    }

    public Produto buscarEntidadePorPublicId(UUID publicId, UUID empresaPublicId) {
        return produtoRepository.findByPublicId(publicId)
                .filter(p -> p.getEmpresaPublicId().equals(empresaPublicId))
                .orElseThrow(() -> new ProdutoNaoEncontradoException(publicId));
    }
}
