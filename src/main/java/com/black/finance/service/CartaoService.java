package com.black.finance.service;

import com.black.finance.domain.entity.Cartao;
import com.black.finance.rest.dto.CartaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartaoService {

    CartaoDTO create(CartaoDTO dto);

    CartaoDTO findById(Integer id);

    Page<CartaoDTO> findAll(Pageable p);

    CartaoDTO update(CartaoDTO dto, Integer id);

    void delete(Integer id);
}
