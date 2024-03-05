package org.madalina.productsinventory.dtoDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private int id;
    private String name;
    private String contactInfo;

    private Long totalQuantity;
    private Double totalValue;


}
