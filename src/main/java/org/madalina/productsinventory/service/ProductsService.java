package org.madalina.productsinventory.service;

import org.madalina.productsinventory.entities.Product;
import org.madalina.productsinventory.dtoDB.ProductDTO;
import org.madalina.productsinventory.entities.Supplier;
import org.madalina.productsinventory.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.madalina.productsinventory.repositories.SupplierRepository;


@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public ProductsService(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<ProductDTO> getProduseDB() {
        // Obținerea tuturor produselor din baza de date
        List<Product> products = productRepository.findAll();

        // Transformarea fiecărui produs în ProductDTO
        return products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setCategorie(product.getCategorie());
            dto.setPretAchizitie(product.getPretAchizitie());
            dto.setPretVanzare(product.getPretVanzare());
            dto.setBBD(product.getBBD());
            dto.setCantitateAchizitionata(product.getCantitateAchizitionata());
            dto.setCantitateVanduta(product.getCantitateVanduta());
            // Setarea supplierId; verifică dacă produsul are un furnizor asociat
            if (product.getSupplier() != null) {
                dto.setSupplierId(product.getSupplier().getId());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public ProductDTO getProductById2(int id)
    {
        Product product = productRepository.getReferenceById(id);

        ProductDTO productDTO= new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setCategorie(product.getCategorie());
        productDTO.setPretAchizitie(product.getPretAchizitie());
        productDTO.setPretVanzare(product.getPretVanzare());
        productDTO.setBBD(product.getBBD());
        productDTO.setCantitateAchizitionata(product.getCantitateAchizitionata());
        productDTO.setCantitateVanduta(product.getCantitateVanduta());
        productDTO.setSupplierId(product.getSupplier() != null ? product.getSupplier().getId() : null);
       return productDTO;
    }


    public boolean deleteProductByIdDB(int id) {
        if (productRepository.existsById(id)) { // daca produsul exista
            productRepository.deleteById(id); // stergere
            return true;
        } else {
            return false;
        }
    }

    public void createProductDB(ProductDTO productDTO) {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setCategorie(productDTO.getCategorie());
        product.setPretAchizitie(productDTO.getPretAchizitie());
        product.setPretVanzare(productDTO.getPretVanzare());
        product.setBBD(productDTO.getBBD());
        product.setCantitateAchizitionata(productDTO.getCantitateAchizitionata());
        product.setCantitateVanduta(productDTO.getCantitateVanduta());
        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Furnizorul cu id-ul " + productDTO.getSupplierId() + " nu a putut fi gasit."));
            product.setSupplier(supplier);
        }
        productRepository.save(product);

    }

    public ProductDTO updateProductDB(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produsul cu id-ul " + id +" nu a putut fi gasit."));

        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Furnizorul cu id-ul " + productDTO.getSupplierId() + " nu a putut fi gasit."));
            product.setSupplier(supplier); // Actualizează furnizorul produsului
        } else {
            product.setSupplier(null); // Dacă supplierId este null, înlătură asocierea cu un furnizor
        }
        product.setName(productDTO.getName());
        product.setCategorie(productDTO.getCategorie());
        product.setPretAchizitie(productDTO.getPretAchizitie());
        product.setPretVanzare(productDTO.getPretVanzare());
        product.setBBD(productDTO.getBBD());
        product.setCantitateAchizitionata(productDTO.getCantitateAchizitionata());
        product.setCantitateVanduta(productDTO.getCantitateVanduta());

        // Salvează produsul actualizat în baza de date
        Product savedProduct = productRepository.save(product);

        // Asigură-te că setezi ID-ul produsului salvat în DTO pentru a fi returnat
        productDTO.setId(savedProduct.getId()); // Aici setezi ID-ul

        return productDTO;
    }


    //PT SUMAR
    public String getTotalNumberOfProductsDB() {
        long totalProducts = productRepository.countTotalProducts();
        Double totalValue = productRepository.sumTotalValue();

        double totalValueSafe = totalValue != null ? totalValue : 0.0;

        return "Numarul total al produselor aflate in inventar este: " + totalProducts +
                ". Valoarea acestora este: " + totalValueSafe + " lei.";
    }


    public String getProduseVanduteDB() {
        Integer cantitateTotalaVanduta = productRepository.getTotalCantitateVanduta();
        Double valoareTotalaVanzari = productRepository.getValoareTotalaVanzari();

        // verificare daca e null
        int totalCantitate = cantitateTotalaVanduta != null ? cantitateTotalaVanduta : 0;
        double totalValoare = valoareTotalaVanzari != null ? valoareTotalaVanzari : 0.0;

        return "Cantitatea totala vanduta este: " + totalCantitate +
                ". Valoarea totala a vanzarilor: " + totalValoare + " lei.";
    }


    public List<Product> getProduseSortateVanzariDB() {
        return productRepository.topCantitateVanduta();
    }


    public List<Product> getBBDcriticDB() {
        return productRepository.findBBDcritic();
    }


    public List<Product> getStocCriticDB() {
        return productRepository.findProduseCuStocCritic();
    }



}
