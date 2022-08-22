package com.black.finance.service.impl;

import com.black.finance.domain.entity.*;
import com.black.finance.domain.enums.TipoPagamento;
import com.black.finance.domain.enums.TipoTransacao;
import com.black.finance.domain.repository.*;
import com.black.finance.exception.ResourceNotFoundException;
import com.black.finance.rest.dto.TransacaoDTO;
import com.black.finance.rest.dto.TransacaoResumoDTO;
import com.black.finance.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final Transacoes repository;

    private final UserRepository userRepository;

    private final Cartoes cartoesRepository;

    private final Contas contasRepository;

    private Usuario usuario;

    @Override
    public TransacaoDTO create(TransacaoDTO dto) {
        Transacao entity = mapToEntity(dto);

        if (Objects.isNull(entity.getDtEntSistema())){
            entity.setDtEntSistema(LocalDate.now());
        }

        if (Objects.isNull(dto.getDtTransacao())){
            entity.setDtTransacao(LocalDate.now());
        }else {
            entity.setDtTransacao(LocalDate.parse(dto.getDtTransacao()));
        }

        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public TransacaoDTO findById(Integer id, TipoTransacao tipoTransacao) {
        Transacao entity;

        if (Objects.isNull(tipoTransacao))
            entity = getEntity(id);
        else if (Objects.equals(tipoTransacao, TipoTransacao.RECEITA) || Objects.equals(tipoTransacao, TipoTransacao.DESPESA))
            entity = getEntity(id, tipoTransacao);
        else
            throw new ResourceNotFoundException("Tipo de transacao nao encontrada");

        return mapToDTO(entity);
    }

    public Page<TransacaoDTO> findAll(TipoTransacao tipoTransacao, Pageable pageable){
        usuario = getUsuario();
        Page<Transacao> page;

        if (Objects.isNull(tipoTransacao))
            page = repository.findAllByUsuario(usuario, pageable);
        else if (Objects.equals(tipoTransacao, TipoTransacao.RECEITA) || Objects.equals(tipoTransacao, TipoTransacao.DESPESA))
            page = repository.findAllByUsuarioAndTipoTransacao(usuario, tipoTransacao, pageable);
        else
            throw new ResourceNotFoundException("Tipo de transacao nao encontrada");

        long totalElements = page.getTotalElements();


        return new PageImpl<>(page.getContent()
                .stream()
                .map(x -> new TransacaoDTO(
                        x.getId(),
                        x.getDescricao(),
                        x.getValor(),
                        String.valueOf(x.getTipoTransacao()),
                        String.valueOf(x.getTipoPagamento()),
                        String.valueOf(x.getDtTransacao()),
                        x.getNumParcela(),
                        String.valueOf(x.getDtEntSistema()),
                        (Objects.isNull(x.getConta())? null : x.getConta().getId()),
                        (Objects.isNull(x.getCartao())? null : x.getCartao().getId())
                )).collect(Collectors.toList()), pageable, totalElements);
    }



    public Page<TransacaoResumoDTO> findTransacaoByUsuarioNoMesX(String mesAndAno, Pageable pageable){
        usuario = getUsuario();
        Page<Transacao> page;

        page = repository.pegaTransacaoPorUsuaioEMes(usuario.getId(), mesAndAno, pageable);

        long totalElements = page.getTotalElements();

        return new PageImpl<>(page.getContent()
                .stream()
                .map(x -> new TransacaoResumoDTO(
                        x.getId(),
                        x.getDescricao(),
                        x.getValor(),
                        String.valueOf(x.getTipoTransacao()),
                        String.valueOf(x.getTipoPagamento()),
                        String.valueOf(x.getDtTransacao()),
                        x.getNumParcela(),
                        String.valueOf(x.getDtEntSistema())
                )).collect(Collectors.toList()), pageable, totalElements);
    }



    public TransacaoDTO update(TransacaoDTO dto, Integer id){
        Transacao entity = getEntity(id);
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);

        if(dto.getCartaoID() != null) {
            Cartao cartao = getCartao(dto, usuario);
            entity.setCartao(cartao);
        }

        if(dto.getContaID() != null) {
            Conta conta = getConta(dto, usuario);
            entity.setConta(conta);
        }

        return mapToDTO(repository.save(entity));
    }

    public void delete(Integer id){
        Transacao entity = getEntity(id);
        repository.delete(entity);
    }




    private TransacaoDTO mapToDTO(Transacao entity){
        return TransacaoDTO.builder()
                .id(entity.getId())
                .descricao(entity.getDescricao())
                .valor(entity.getValor())
                .tipoTransacao(entity.getTipoTransacao().toString())
                .tipoPagamento(entity.getTipoPagamento().toString())
                .dtTransacao(Objects.nonNull(entity.getDtTransacao()) ?
                        entity.getDtTransacao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null)

                .numParcela(entity.getNumParcela())

                .dtEntSistema(Objects.nonNull(entity.getDtEntSistema()) ?
                        entity.getDtEntSistema().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null)

                .cartaoID(Objects.isNull(entity.getCartao()) ? null : entity.getCartao().getId())
                .contaID(Objects.isNull(entity.getConta()) ? null : entity.getConta().getId())
                .build();
    }

    private Transacao mapToEntity(TransacaoDTO dto){
        Transacao entity = new Transacao();
        usuario = getUsuario();

        BeanUtils.copyProperties(dto, entity);

        if(dto.getCartaoID() != null) {
            Cartao cartao = getCartao(dto, usuario);
            entity.setCartao(cartao);
        }

        if(dto.getContaID() != null) {
            Conta conta = getConta(dto, usuario);
            entity.setConta(conta);
        }

        entity.setTipoPagamento(TipoPagamento.valueOf(dto.getTipoPagamento()));
        entity.setTipoTransacao(TipoTransacao.valueOf(dto.getTipoTransacao()));
        entity.setUsuario(usuario);
        return entity;
    }

    private Cartao getCartao(TransacaoDTO dto, Usuario usuario) {
        return cartoesRepository.findByIdAndUsuario(dto.getCartaoID(), usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Cartao", "cartaoID", dto.getCartaoID()));
    }

    private Conta getConta(TransacaoDTO dto, Usuario usuario) {
        Conta conta = contasRepository.findByIdAndUsuario(dto.getCartaoID(), usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Conta", "contaID", dto.getCartaoID()));
        return conta;
    }

    private Usuario getUsuario() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username) );
    }

    private Transacao getEntity(Integer id, TipoTransacao tipoTransacao) {
        usuario = getUsuario();
        return  repository.findByIdAndUsuarioAndTipoTransacao(id, usuario, tipoTransacao)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao", "id", id) );
    }

    private Transacao getEntity(Integer id) {
        usuario = getUsuario();
        return  repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao", "id", id) );
    }

}
