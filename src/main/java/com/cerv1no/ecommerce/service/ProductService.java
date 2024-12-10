package com.cerv1no.ecommerce.service;

import com.cerv1no.ecommerce.dto.ProductDto;
import com.cerv1no.ecommerce.exception.ResourceNotFoundException;
import com.cerv1no.ecommerce.mapper.ProductMapper;
import com.cerv1no.ecommerce.model.Product;
import com.cerv1no.ecommerce.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productMapper.toDto(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto, MultipartFile image) throws IOException {
        Product product = productMapper.toEntity(productDto);
        if (image != null && !image.isEmpty()) {
            String fileName = saveImage(image);
            product.setImageUrl("images/" + fileName);
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto, MultipartFile image) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        if (image != null && !image.isEmpty()) {
            String fileName = saveImage(image);
            product.setImageUrl("images/" + fileName);
        }
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id))
                throw new ResourceNotFoundException("Product not found");
        productRepository.deleteById(id);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return fileName;
    }
}
