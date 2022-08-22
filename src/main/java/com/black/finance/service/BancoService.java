package com.black.finance.service;

import com.black.finance.domain.entity.Banco;
import com.black.finance.rest.dto.BancoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BancoService {

    BancoDTO getBancoByBcCodigo(String bcCodigo);

    boolean verificaBanco(String bcCodigo);

    BancoDTO createBanco(BancoDTO dto);

    Page<BancoDTO> getAllBancos(Pageable pageable);

    void deleteByBcCodigo(String bcCodigo);

    BancoDTO update(BancoDTO dto, String bcCodigo);

}
