package com.black.finance.service.impl;

import com.black.finance.domain.entity.Banco;
import com.black.finance.domain.repository.Bancos;
import com.black.finance.exception.ResourceNotFoundException;
import com.black.finance.rest.dto.BancoDTO;
import com.black.finance.service.BancoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BancoServiceImpl implements BancoService {

    private final Bancos repository;

    @Override
    public BancoDTO createBanco(BancoDTO dto){
        Banco entity = repository.save(mapToEntity(dto));
        return mapToDTO(entity);
    }
    @Override
    public Page<BancoDTO> getAllBancos(Pageable pageable) {
        Page<Banco> page = repository.findAll(pageable);
        int totalElements = (int) page.getTotalElements();
        return new PageImpl<BancoDTO>(page.getContent()
                .stream()
                .map(x -> new BancoDTO(
                        x.getBcCodigo(),
                        x.getBancoNome()))
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public void deleteByBcCodigo(String bcCodigo) {
        Banco entity = repository.findByBcCodigo(bcCodigo)
                .orElseThrow(() -> new ResourceNotFoundException("Banco", "bcCodigo", bcCodigo));
        repository.delete(entity);
    }

    @Override
    public BancoDTO update(BancoDTO dto, String bcCodigo) {

        Banco entity = repository.findByBcCodigo(bcCodigo)
                .orElseThrow(() -> new ResourceNotFoundException("Banco", "bcCodigo", bcCodigo));

        entity.setBcCodigo(dto.getBcCodigo());
        entity.setBancoNome(dto.getBancoNome());

        return mapToDTO(repository.save(entity));
    }

    @Override
    public BancoDTO getBancoByBcCodigo(String bcCodigo) {
        Banco banco = repository.findByBcCodigo(bcCodigo)
                .orElseThrow(() -> new ResourceNotFoundException("Banco","bcCodigo", bcCodigo));
        return mapToDTO(banco);
    }

    @Override
    public boolean verificaBanco(String bcCodigo) {
        return repository.existsByBcCodigo(bcCodigo);
    }


    private BancoDTO mapToDTO(Banco entity){
        return BancoDTO.builder()
                .bcCodigo(entity.getBcCodigo())
                .bancoNome(entity.getBancoNome())
                .build();
    }
    private Banco mapToEntity(BancoDTO dto){
        Banco entity = new Banco();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
