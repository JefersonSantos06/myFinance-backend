package com.black.finance.rest.controller;

import com.black.finance.domain.entity.Banco;
import com.black.finance.exception.PrimaryKeyException;
import com.black.finance.exception.ResourceNotFoundException;
import com.black.finance.rest.dto.BancoDTO;
import com.black.finance.service.BancoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/bancos")
@RequiredArgsConstructor
public class BancoController {

    private final BancoService service;

    @PostMapping
    public ResponseEntity<BancoDTO> createBanco(@Valid @RequestBody BancoDTO dto){
        if (service.verificaBanco(dto.getBcCodigo())){
            throw new PrimaryKeyException(HttpStatus.BAD_REQUEST,"O Banco " + dto.getBcCodigo() + " ja esta cadastrado.");
        }
        return new ResponseEntity<>(service.createBanco(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "{bcCodigo}")
    public ResponseEntity<BancoDTO> getBancoByBcCodigo(@PathVariable String bcCodigo){
        return ResponseEntity.ok(service.getBancoByBcCodigo(bcCodigo));
    }

    @GetMapping
    public ResponseEntity<Page<BancoDTO>> getAllBancos(@PageableDefault(page = 0, size = 10, sort = "bcCodigo", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllBancos(pageable));
    }

    @DeleteMapping("{bcCodigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBanco(@PathVariable String bcCodigo){
        service.deleteByBcCodigo(bcCodigo);
    }

    @PutMapping("{bcCodigo}")
    public ResponseEntity<BancoDTO> update(@Valid @RequestBody BancoDTO dto, @PathVariable String bcCodigo){
        BancoDTO newDTO = service.update(dto, bcCodigo);

        return new ResponseEntity<>(newDTO, HttpStatus.OK);
    }



}
