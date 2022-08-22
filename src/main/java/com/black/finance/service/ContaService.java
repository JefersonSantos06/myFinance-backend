package com.black.finance.service;

import com.black.finance.domain.entity.Conta;
import com.black.finance.rest.dto.CartaoDTO;
import com.black.finance.rest.dto.ContaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContaService {

    ContaDTO create(ContaDTO dto);

    ContaDTO findById(Integer id);

    Page<ContaDTO> findAll(Pageable p);

    ContaDTO update(ContaDTO dto, Integer id);

    void delete(Integer id);
}
