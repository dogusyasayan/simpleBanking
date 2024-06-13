package com.eteration.simplebanking.model.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposit_transaction")
public class DepositTransaction extends Transaction {

    @Column(name = "description")
    private String description;
}
