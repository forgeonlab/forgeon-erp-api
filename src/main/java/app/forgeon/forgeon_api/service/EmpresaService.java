package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    // ✅ Listar todas as empresas
    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    // ✅ Buscar uma empresa por ID
    public Optional<Empresa> buscarPorId(UUID id) {
        return empresaRepository.findById(id);
    }

    // ✅ Criar ou atualizar empresa
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    // ✅ Atualizar (aproveitando o método acima)
    public Optional<Empresa> atualizar(UUID id, Empresa empresaAtualizada) {
        return empresaRepository.findById(id).map(existente -> {
            existente.setNome(empresaAtualizada.getNome());
            existente.setCnpj(empresaAtualizada.getCnpj());
            existente.setEmail(empresaAtualizada.getEmail());
            existente.setTelefone(empresaAtualizada.getTelefone());
            existente.setAtiva(empresaAtualizada.getAtiva());
            return empresaRepository.save(existente);
        });
    }

    // ✅ Deletar
    public boolean deletar(UUID id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
