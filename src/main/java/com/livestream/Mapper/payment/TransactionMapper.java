package com.livestream.Mapper.payment;

import com.livestream.DTO.response.payment.TransactionResponse;
import com.livestream.Entity.payment.Transaction;
import com.livestream.Mapper.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TransactionMapper {
    TransactionResponse toTransactionResponse(Transaction transaction);
}
