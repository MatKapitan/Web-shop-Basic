package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.entity.OrderItem;
import com.matkap.Webshop_basics.entity.Product;
import com.matkap.Webshop_basics.exception.notFound.ProductNotFoundException;
import com.matkap.Webshop_basics.repository.OrderItemRepository;
import com.matkap.Webshop_basics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderItemRepository orderItemRepository;


    @Override
    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productToDto(product, null);
    }

    @Override
    public Page<ProductDto> getProducts(Pageable page) {
        Page<Product> products = productRepository.findAll(page);
        Page<ProductDto> productsDto = products.map(ProductDto::from);
        return productsDto;
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = productDtoToEntity(productDto);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> getProductsByOrderId(Long orderId, Pageable page) {
        Page<OrderItem> byOrderId = orderItemRepository.getByOrderId(orderId, page);
        Page<ProductDto> orderItemDto = byOrderId.map(orderItem -> {
            int quantity = orderItem.getQuantity();
            Product product = orderItem.getProduct();
            return ProductDto.from(product, quantity);
        });
//        for (OrderItem item : byOrderId){
//            Product product = item.getProduct();
//            int quantity = item.getQuantity();
//            products.add(productToDto(product,quantity));
//        }
        return orderItemDto;
    }

    @Override
    public void updateProduct(ProductDto productDto, Long product_id) {
        Product productToUpdate = productRepository.findById(product_id).orElseThrow(() -> new ProductNotFoundException(product_id));
        productToUpdate.setName(productDto.getName());
        productToUpdate.setAvailable(productDto.isAvailable());
        productToUpdate.setPriceEur(productDto.getPriceEur());
        productToUpdate.setDescription(productDto.getDescription());
        productRepository.save(productToUpdate);
    }

    public ProductDto productToDto(Product product, Integer quantity){
        ProductDto productDto = new ProductDto();
        productDto.setCode(product.getCode());
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setAvailable(product.is_available());
        productDto.setPriceEur(product.getPriceEur());
        productDto.setQuantity(quantity);
        return  productDto;
    }
    public Product productDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setCode(productDto.getCode());
        product.setId(productDto.getId());
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setAvailable(productDto.isAvailable());
        product.setPriceEur(productDto.getPriceEur());
        return product;
    }
}
