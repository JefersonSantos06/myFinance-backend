package com.black.finance.service.impl;

import com.black.finance.domain.entity.Banco;
import com.black.finance.domain.entity.Cartao;
import com.black.finance.domain.entity.Usuario;
import com.black.finance.domain.repository.Bancos;
import com.black.finance.domain.repository.Cartoes;
import com.black.finance.domain.repository.UserRepository;
import com.black.finance.exception.ResourceNotFoundException;
import com.black.finance.rest.dto.CartaoDTO;
import com.black.finance.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final Cartoes repository;

    private final Bancos bancosRep;

    private final UserRepository userRepository;

    
    @Override
    public CartaoDTO create(CartaoDTO dto) {
        Cartao entity = repository.save(mapToEntity(dto));
        return mapToDTO(entity);
    }

    @Override
    public CartaoDTO findById(Integer id){
        Cartao entity = getEntity(id);
        return mapToDTO(entity);
    }

    @Override
    public Page<CartaoDTO> findAll(Pageable pageable){
        Usuario usuario = getUser();
        Page<Cartao> page = repository.findAllByUsuario(usuario, pageable);
        Long totalElements = page.getTotalElements();

        return new PageImpl<CartaoDTO>(page.getContent()
                .stream()
                .map(x -> new CartaoDTO(
                        x.getId(),
                        x.getCartaoDesc(),
                        x.getDiaVencimento(),
                        x.getAnuidade(),
                        x.getBanco(),
                        x.getLimite(),
                        x.getLimiteUtilizado(),
                        x.getBandeira(),
                        x.getVencimentoCartao(),
                        x.getUlt4Digitos()
                )).collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public  CartaoDTO update(CartaoDTO dto, Integer id){
        Cartao entity = getEntity(id);
        BeanUtils.copyProperties(mapToEntity(dto), entity);
        entity.setId(id);
        return mapToDTO(repository.save(entity));
    }


    @Override
    public void delete(Integer id){
        Cartao entity = getEntity(id);
        repository.delete(entity);
    }


    private CartaoDTO mapToDTO(Cartao entity){
        return CartaoDTO.builder()
                .id(entity.getId())
                .cartaoDesc(entity.getCartaoDesc())
                .diaVencimento(entity.getDiaVencimento())
                .anuidade(entity.getAnuidade())
                .banco(entity.getBanco())
                .limite(entity.getLimite())
                .limiteUtilizado(entity.getLimiteUtilizado())
                .bandeira(entity.getBandeira())
                .vencimentoCartao(entity.getVencimentoCartao())
                .ult4Digitos(entity.getUlt4Digitos())
                .build();
    }

    private Cartao mapToEntity(CartaoDTO dto){
        Cartao entity = new Cartao();


        Usuario usuario = getUser();

        Banco bancoEntity = bancosRep.findByBcCodigo(dto.getBanco().getBcCodigo())
                .orElseThrow(() -> new ResourceNotFoundException("Banco","bcCodigo", dto.getBanco()));

        BeanUtils.copyProperties(dto, entity);
        entity.setBanco(bancoEntity);
        entity.setUsuario(usuario);
        return entity;
    }

    private Usuario getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", auth.getName()) );
        return usuario;
    }

    private Cartao getEntity(Integer id) {
        Usuario usuario = getUser();
        return repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Cartao", "id", id.toString()));
    }

}
