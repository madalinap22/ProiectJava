package org.madalina.productsinventory.dtoDB;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


//public record ProductDTO  //tip special de date (imutabil)
//        (
//            String name,
//            String categorie,
//            float pretAchizitie,
//            float pretVanzare,
//            LocalDate BBD,
//            int cantitateAchizitionata,
//            int cantitateVanduta) {}


@Data
//@Builder
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String categorie;
    private float pretAchizitie;
    private float pretVanzare;
    private LocalDate BBD;
    private int cantitateAchizitionata;
    private int cantitateVanduta;
    private Integer supplierId;
}
