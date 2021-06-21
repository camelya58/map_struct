package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.Transaction;
import com.github.camelya58.map_struct.model.TransactionDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;


/**
 * In case when you need to convert one field to another using some manipulations
 * we need to convert it ourselves.
 * <p>
 * But the collection of the source type will be automatically converted to the list of the target type.
 */
@Mapper
public abstract class TransactionMapper {

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setUuid(transaction.getUuid());
        dto.setTotalInCents(transaction.getTotal()
                .multiply(new BigDecimal("100")).longValue());
        return dto;
    }

    public abstract List<TransactionDTO> toTransactionDTO(
            Collection<Transaction> transactions);
}
