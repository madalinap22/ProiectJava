package org.madalina.productsinventory.dtoDB;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplierDTO {
    private int id;
    private String name;
    private String contactInfo;

}
