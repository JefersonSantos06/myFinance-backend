package com.black.finance.rest.controller;

import com.black.finance.rest.dto.CartaoDTO;
import com.black.finance.service.CartaoService;
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

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService service;

    @PostMapping
    public ResponseEntity<CartaoDTO> create(@Valid @RequestBody CartaoDTO dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CartaoDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CartaoDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @PutMapping("{id}")
    public ResponseEntity<CartaoDTO> update(@Valid @RequestBody CartaoDTO dto, @PathVariable Integer id){
        CartaoDTO newDto = service.update(dto, id);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

}
