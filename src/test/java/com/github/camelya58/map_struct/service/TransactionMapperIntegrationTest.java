package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.Transaction;
import com.github.camelya58.map_struct.model.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionMapperIntegrationTest {

    private final TransactionMapper mapper =
            Mappers.getMapper(TransactionMapper.class);

    @Test
    public void givenTransactionToTransactionDTO() {
        Transaction entity = new Transaction();
        entity.setId(1L);
        entity.setUuid(UUID.randomUUID().toString());
        entity.setTotal(new BigDecimal("120"));
        TransactionDTO dto = mapper.toTransactionDTO(entity);

        assertEquals(entity.getId(), 1L);
        assertEquals(entity.getTotal()
                .multiply(new BigDecimal("100")).longValue(), dto.getTotalInCents());
        assertEquals(entity.getUuid(), dto.getUuid());
    }

    @Test
    public void givenTransactionListToTransactionDTOList() {
       var list = new ArrayList<Transaction>();
        for (int i = 100; i < 150; i+=10) {
            Transaction entity = new Transaction();
            entity.setId(i/10L);
            entity.setUuid(UUID.randomUUID().toString());
            entity.setTotal(BigDecimal.valueOf(i));
            list.add(entity);
        }
        List<TransactionDTO> dtoList = mapper.toTransactionDTO(list);

        assertEquals(list.get(0).getUuid(), dtoList.get(0).getUuid());
        assertEquals(list.size(), dtoList.size());
    }

}
