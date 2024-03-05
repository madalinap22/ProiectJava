package org.madalina.productsinventory.service;

import org.madalina.productsinventory.entities.Category;
import org.madalina.productsinventory.entities.Product;
import org.madalina.productsinventory.dtoDB.ProductDTO;
import org.madalina.productsinventory.entities.Supplier;
import org.madalina.productsinventory.exceptions.InvalidExpDateException;
import org.madalina.productsinventory.exceptions.InvalidPriceException;
import org.madalina.productsinventory.exceptions.InvalidQuantityException;
import org.madalina.productsinventory.exceptions.ProductNotFoundException;
import org.madalina.productsinventory.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.madalina.productsinventory.repositories.SupplierRepository;
import org.madalina.productsinventory.repositories.CategoryRepository;


@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

    public ProductsService(ProductRepository productRepository, SupplierRepository supplierRepository,
                           CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDTO> getProduseDB() {
        // Obținerea tuturor produselor din baza de date
        List<Product> products = productRepository.findAll();

        // transform fiecare produs in ProductDTO
        return products.stream().map(product -> {  //expresie lambda care transforma fiecare obiect de tip Product in obiect de tip ProductDTO.
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            if (product.getCategory() != null) {
                dto.setCategoryId(product.getCategory().getId());
            }
            dto.setPretAchizitie(product.getPretAchizitie());
            dto.setPretVanzare(product.getPretVanzare());
            dto.setBBD(product.getBBD());
            dto.setCantitateAchizitionata(product.getCantitateAchizitionata());
            dto.setCantitateVanduta(product.getCantitateVanduta());
            // verific daca produsul are un furnizor asociat
            if (product.getSupplier() != null) {
                dto.setSupplierId(product.getSupplier().getId());
            }
            return dto;
        }).collect(Collectors.toList()); //transform un flux de obiecte (stream) intr-o listă
    }

    public ProductDTO getProductById2(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produsul cu ID-ul " + id + " nu a putut fi găsit."));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPretAchizitie(product.getPretAchizitie());
        productDTO.setPretVanzare(product.getPretVanzare());
        productDTO.setBBD(product.getBBD());
        productDTO.setCantitateAchizitionata(product.getCantitateAchizitionata());
        productDTO.setCantitateVanduta(product.getCantitateVanduta());
        productDTO.setSupplierId(product.getSupplier() != null ? product.getSupplier().getId() : null);
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }

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
        if (productDTO.getPretAchizitie() < 0 || productDTO.getPretVanzare() < 0) {
            throw new InvalidPriceException("Atentie! Pretul de achizitie/ pretul de vanzare trebuie sa fie pozitive.");
        }

        if (productDTO.getCantitateVanduta() > productDTO.getCantitateAchizitionata()) {
            throw new InvalidQuantityException("Atentie! Cantitatea vandută nu poate fi mai mare decat cantitatea achizitionata.");
        }

        if (productDTO.getBBD().isBefore(LocalDate.now())) {
            throw new InvalidExpDateException("Atentie! Data de expirare nu poate fi in trecut.");
        }

        Product product = new Product();

        product.setName(productDTO.getName());
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
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria cu id-ul " + productDTO.getCategoryId() + " nu a putut fi găsită."));
            product.setCategory(category);
        }
        productRepository.save(product);

    }

    public ProductDTO updateProductDB(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produsul cu id-ul " + id +" nu a putut fi gasit."));

        if (productDTO.getPretAchizitie() < 0 || productDTO.getPretVanzare() < 0) {
            throw new InvalidPriceException("Atentie! Pretul de achizitie/ pretul de vanzare trebuie sa fie pozitive.");
        }

        if (productDTO.getCantitateVanduta() > productDTO.getCantitateAchizitionata()) {
            throw new InvalidQuantityException("Atentie! Cantitatea vandută nu poate fi mai mare decat cantitatea achizitionata.");
        }

        if (productDTO.getBBD().isBefore(LocalDate.now())) {
            throw new InvalidExpDateException("Atentie! Data de expirare nu poate fi in trecut.");
        }

        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Furnizorul cu id-ul " + productDTO.getSupplierId() + " nu a putut fi gasit."));
            product.setSupplier(supplier); // Actualizeaza furnizorul produsului
        } else {
            product.setSupplier(null); // Dacă supplierId este null, inlatura asocierea cu un furnizor
        }
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()) //.longValue() pt long
                    .orElseThrow(() -> new RuntimeException("Categoria cu id-ul " + productDTO.getCategoryId() + " nu a putut fi găsită."));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }
        product.setName(productDTO.getName());
        product.setPretAchizitie(productDTO.getPretAchizitie());
        product.setPretVanzare(productDTO.getPretVanzare());
        product.setBBD(productDTO.getBBD());
        product.setCantitateAchizitionata(productDTO.getCantitateAchizitionata());
        product.setCantitateVanduta(productDTO.getCantitateVanduta());

        // Salvez produsul actualizat in baza de date
        Product savedProduct = productRepository.save(product);


        productDTO.setId(savedProduct.getId());  //actualizez ID-ul produsului in ProductDTO ca sa sa fie salvat in BD dupa actualizare

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
