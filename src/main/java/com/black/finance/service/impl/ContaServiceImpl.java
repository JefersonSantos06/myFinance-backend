package com.black.finance.service.impl;

import com.black.finance.domain.entity.Banco;
import com.black.finance.domain.entity.Conta;
import com.black.finance.domain.entity.Usuario;
import com.black.finance.domain.repository.Bancos;
import com.black.finance.domain.repository.Contas;
import com.black.finance.domain.repository.UserRepository;
import com.black.finance.exception.ResourceNotFoundException;
import com.black.finance.rest.dto.ContaDTO;
import com.black.finance.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final Contas repository;
    private final Bancos bancosRep;

    private final UserRepository userRepository;

    @Override
    public ContaDTO create(ContaDTO dto) {
        Conta entity = repository.save(mapToEntity(dto));
        return mapToDTO(entity);
    }

    @Override
    public ContaDTO findById(Integer id){
        Conta entity = getEntity(id);
        return mapToDTO(entity);
    }



    @Override
    public Page<ContaDTO> findAll(Pageable pageable){
        Usuario usuario = getUser();
        Page<Conta> page = repository.findAllByUsuario(usuario, pageable);
        Long totalElements = page.getTotalElements();

        return new PageImpl<ContaDTO>(page.getContent()
                .stream()
                .map(x -> new ContaDTO(
                        x.getId(),
                        x.getBanco().getBcCodigo(),
                        x.getAgencia(),
                        x.getConta(),
                        x.getContaDesc(),
                        x.getSaldoAtual(),
                        x.getValManutencao(),
                        x.getDiaCobManutencao()
                )).collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public  ContaDTO update(ContaDTO dto, Integer id){
        Conta entity = getEntity(id);
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        return mapToDTO(repository.save(entity));
    }


    @Override
    public void delete(Integer id){
        Conta entity = getEntity(id);
        repository.delete(entity);
    }


    private ContaDTO mapToDTO(Conta entity){
        return ContaDTO.builder()
                .id(entity.getId())
                .banco(entity.getBanco().getBcCodigo())
                .agencia(entity.getAgencia())
                .conta(entity.getConta())
                .contaDesc(entity.getContaDesc())
                .saldoAtual(entity.getSaldoAtual())
                .valManutencao(entity.getValManutencao())
                .diaCobManutencao(entity.getDiaCobManutencao())
                .build();
    }

    private Conta mapToEntity(ContaDTO dto){
        Conta entity = new Conta();

        Usuario usuario = getUser();

        Banco bancoEntity = bancosRep.findByBcCodigo(dto.getBanco())
                .orElseThrow(() -> new ResourceNotFoundException("Banco","bcCodigo", dto.getBanco()));

        BeanUtils.copyProperties(dto, entity);
        entity.setBanco(bancoEntity);
        entity.setUsuario(usuario);
        return entity;
    }

    private Conta getEntity(Integer id) {
        Usuario usuario = getUser();
        return repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Cartao", "id", id));
    }

    private Usuario getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username) );
        return usuario;
    }
}
