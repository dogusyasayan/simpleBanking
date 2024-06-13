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
@Table(name = "bill_payment_transaction")
public class BillPaymentTransaction extends Transaction {

    @Column(name = "bill_number")
    private String billNumber;

    @Column(name = "biller_name")
    private String billerName;

}
