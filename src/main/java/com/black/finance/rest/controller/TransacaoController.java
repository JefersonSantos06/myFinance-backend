package com.black.finance.rest.controller;

import com.black.finance.domain.enums.TipoTransacao;
import com.black.finance.rest.dto.TransacaoDTO;
import com.black.finance.rest.dto.TransacaoResumoDTO;
import com.black.finance.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @PostMapping
    public ResponseEntity<TransacaoDTO> create(@Valid @RequestBody TransacaoDTO dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<TransacaoDTO> findById(@PathVariable Integer id,
                                                 @RequestParam(value = "tipoTransacao", required = false) TipoTransacao tipoTransacao){
        return ResponseEntity.ok(service.findById(id, tipoTransacao));
    }

    @GetMapping
    public ResponseEntity<Page<TransacaoDTO>> findAll(@RequestParam(value = "tipoTransacao", required = false) TipoTransacao tipoTransacao,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(tipoTransacao, pageable));
    }

    @GetMapping("/resumo")
    public ResponseEntity<Page<TransacaoResumoDTO>> findTransacaoByUsuarioNoMesX(@RequestParam(value = "mesAndAno") String mesAndAno,
                                                                                 @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findTransacaoByUsuarioNoMesX(mesAndAno, pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Transacao deletada com sucesso");
    }

    @PutMapping("{id}")
    public ResponseEntity<TransacaoDTO> update(@Valid @RequestBody TransacaoDTO dto, @PathVariable Integer id){
        TransacaoDTO newDto = service.update(dto, id);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }


}
