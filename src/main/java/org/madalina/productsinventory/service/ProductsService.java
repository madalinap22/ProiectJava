package org.madalina.productsinventory.service;

import org.madalina.productsinventory.dto.Product;
import org.madalina.productsinventory.dtoDB.ProductDTO;
import org.madalina.productsinventory.repositories.ProductRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProductsService {

    private final ProductRepository productRepository;
    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

       return productDTO;
    }


    //lista statica de produse
    private static final List<Product> produse = new ArrayList<>();
    private static int contorProduse =5;


    static {
        produse.add(new Product(1,"Crema maini","Cosmetice", 30.5F,60.5F,LocalDate.of(2026,6,20),100,10));
        produse.add(new Product(2,"Sampon","Cosmetice", 20.5F, 50.5F,LocalDate.of(2027,6,20), 200,100));
        produse.add(new Product(3,"Crema fata","Dermatocosmetice", 80.5F,100.5F,LocalDate.of(2028,6,20), 350,150));
        produse.add(new Product(4,"Crema fata","Dermatocosmetice", 60.5F,90.5F,LocalDate.of(2024,4,20),400,200));

    }


//pt lista statica
//    public static List<Product> getProduse() {
//        return produse;
//    }

    public  List<Product> getProduseDB() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return produse.stream()
                .filter(product -> id == product.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produsul cu id-ul " + id +" nu a putut fi gasit."));
    }

    public boolean deleteProductByIdDB(int id) {
        if (productRepository.existsById(id)) { // daca produsul exista
            productRepository.deleteById(id); // stergere
            return true;
        } else {
            return false;
        }
    }


//    public Product createProduct(Product product) {
//        product.setId(contorProduse++);
//        produse.add(product);
//        return product;
//    }

//    public Product updateUser(Integer id, Product prod) {
//            Product prod1 = produse.stream()
//                    .filter(p -> id == p.getId())
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Produsul cu id-ul " + id +" nu a putut fi gasit."));
//            prod1.setName(prod.getName());
//            prod1.setCategorie(prod.getCategorie());
//            prod1.setPretAchizitie(prod.getPretAchizitie());
//            prod1.setPretVanzare(prod.getPretVanzare());
//            prod1.setBBD(prod.getBBD());
//            prod1.setCantitateAchizitionata(prod.getCantitateAchizitionata());
//            prod1.setCantitateVanduta(prod.getCantitateVanduta());
//            return prod1;
//    }

    public ProductDTO updateProductDB(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produsul cu id-ul " + id +" nu a putut fi gasit."));

        product.setName(productDTO.getName());
        product.setCategorie(productDTO.getCategorie());
        product.setPretAchizitie(productDTO.getPretAchizitie());
        product.setPretVanzare(productDTO.getPretVanzare());
        product.setBBD(productDTO.getBBD());
        product.setCantitateAchizitionata(productDTO.getCantitateAchizitionata());
        product.setCantitateVanduta(productDTO.getCantitateVanduta());

        productRepository.save(product);

        return productDTO;
    }

    //PT SUMAR

//    public String getTotalNumberOfProducts() {
//        int totalProducts= produse.size();
//        double totalValue=0;
//
//        for(Product produs:produse)
//        {
//            totalValue += produs.getPretAchizitie();
//        }
//        return "Numarul total al produselor aflate in inventar este: " + totalProducts +
//                ". Valoarea acestora este: "+totalValue+ " lei.";
//    }

    public String getTotalNumberOfProductsDB() {
        long totalProducts = productRepository.countTotalProducts();
        Double totalValue = null;


        double totalValueSafe = totalValue != null ? totalValue : 0.0;

        return "Numărul total al produselor aflate în inventar este: " + totalProducts +
                ". Valoarea acestora este: " + totalValueSafe + " lei.";
    }

    public String getProduseVandute() {
        int cantitateTotalaVanduta = 0;
        double valoareTotalaVanzari = 0;

        for (Product produs : produse) {
            cantitateTotalaVanduta += produs.getCantitateVanduta();
            valoareTotalaVanzari += produs.getPretVanzare() * produs.getCantitateVanduta();
        }

        return "Cantitatea totală vândută: " + cantitateTotalaVanduta +
                ". Valoarea totală a vânzărilor: " + valoareTotalaVanzari + " lei.";
    }

    public List<Product> getProduseSortateVanzari() {
        return produse.stream()
                .sorted(Comparator.comparingInt(Product::getCantitateVanduta).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Product> getBBDcritic() {
        final int zileExpirare = 60; // zile pana la expirare
        LocalDate dataLimita = LocalDate.now().plusDays(zileExpirare);
        return produse.stream()
                .filter(produs -> produs.getBBD().isBefore(dataLimita))
                .collect(Collectors.toList());
    }

    public List<Product> getStocCritic() {
        return produse.stream()
                .filter(produs -> produs.getCantitateAchizitionata() - produs.getCantitateVanduta() < 100)
                .collect(Collectors.toList());
    }


    public void createProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setCategorie(productDTO.getCategorie());
        product.setPretAchizitie(productDTO.getPretAchizitie());
        product.setPretVanzare(productDTO.getPretVanzare());
        product.setBBD(productDTO.getBBD());
        product.setCantitateAchizitionata(productDTO.getCantitateAchizitionata());
        product.setCantitateVanduta(productDTO.getCantitateVanduta());

        productRepository.save(product);

    }
}
