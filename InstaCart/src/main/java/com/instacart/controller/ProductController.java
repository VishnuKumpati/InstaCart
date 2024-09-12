package com.instacart.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.instacart.entities.Category;
import com.instacart.entities.Product;
import com.instacart.entities.Retailer;
import com.instacart.repository.CategoryRepository;
import com.instacart.repository.RetailerRepository;
import com.instacart.response.ProductResponse;
import com.instacart.service.ProductService;
import java.util.ArrayList;
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
	private CategoryRepository categoryRepository;
    @Autowired
    private RetailerRepository retailerRepository;

    //adding products to database we are not taking Long productId; and  private BigDecimal productPriceAfterDiscount; 
    //while adding the product through form
    @PostMapping("/addproducts")
    public ResponseEntity<Product> addProduct(@RequestParam("productName") String productName,
    		@RequestParam("productBrand") String productBrand,
    		@RequestParam("productPrice") BigDecimal  productPrice,
    		@RequestParam("productDiscount")BigDecimal productDiscount,
    		@RequestParam("description")String description,
    		@RequestParam("countofproducts") int countofproducts,
    		@RequestParam("imagePath")String imagePath,
    		@RequestParam("categoryName")String categoryName,
    		@RequestParam("retailerId") Long retailerId){
    	
    	 // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.
		 // Ensure the category is not null in the product
//		
    	 // Find the category by name or create a new one
        Category category = categoryRepository.findByName(categoryName)
            .orElseGet(() -> {
                Category newCategory = new Category(categoryName);
                System.out.println("newCategory name is" + newCategory);
                return categoryRepository.save(newCategory);
            });
        System.out.println("categoryName "+category);
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found with id: " + retailerId));
//        product.setRetailer(retailer); 
    	Product product=new Product();
    	product.setProductName(productName);
    	product.setProductBrand(productBrand);
    	product.setProductPrice(productPrice);
    	product.setProductDiscount(productDiscount);
    	product.setDescription(description);
    	product.setCountofproducts(countofproducts);
    	product.setImagePath(imagePath);
    	product.setCategory(category);
    	product.setRetailer(retailer);
    	
    
    	
    	Product addedProduct=productService.addProduct(product);
    	return ResponseEntity.ok(addedProduct);
    	
    }
    
    @GetMapping("/browse/category/{category}")
    @ResponseBody
    public ResponseEntity<List<Product>> browseByCategory(@PathVariable("category") String category) {
        List<Product> allproducts = productService.getProductsByCategory(category);
        List<ProductResponse> productRespose=new ArrayList<>();
    	for(Product product:allproducts) {
    		ProductResponse response=new ProductResponse(product.getProductName(),product.getProductBrand(),product.getProductDiscount(),product.getProductPrice(),product.getRetailer().getName(),product.getProductPriceAfterDiscount(),product.getProductId(),product.getDescription(),product.getImagePath());
    		productRespose.add(response);
    	}
        return ResponseEntity.ok(allproducts );
    }
   
    @GetMapping("/getallproducts")
    public ModelAndView getAllProducts(){
    	List<Product> allproducts=productService.getAllProducts();
    	List<ProductResponse> productRespose=new ArrayList<>();
    	for(Product product:allproducts) {
    		System.out.println(product.getProductPriceAfterDiscount());
    		ProductResponse response=new ProductResponse(product.getProductName(),product.getProductBrand(),product.getProductDiscount(),product.getProductPrice(),product.getRetailer().getName(),product.getProductPriceAfterDiscount(),product.getProductId(),product.getDescription(),product.getImagePath());
    		System.out.println(response);
    		productRespose.add(response);
    	}
// return  ResponseEntity.ok(productRespose);
    
    	
    	ModelAndView mv=new ModelAndView();
		mv.addObject("productRespose", productRespose);
		mv.setViewName("displayallProducts");
		return mv;
    	
    }
//    @GetMapping("/getallproducts")
//    public ResponseEntity<List<ProductResponse>>  getAllProducts(){
//    	List<Product> allproducts=productService.getAllProducts();
//    	List<ProductResponse> productRespose=new ArrayList<>();
//    	for(Product product:allproducts) {
//    		ProductResponse response=new ProductResponse(product.getProductName(),product.getProductBrand(),product.getProductDiscount(),product.getProductPrice(),product.getRetailer().getName(),product.getProductPriceAfterDiscount(),product.getProductId(),product.getDescription(),product.getImagePath());
//    		productRespose.add(response);
//    	}
// return  ResponseEntity.ok(productRespose);
//    
//    	
//    	ModelAndView mv=new ModelAndView();
//		mv.addObject("allproducts", productRespose);
//		mv.setViewName("displayallProducts");
//		return mv;
//    	
//    }

    //updating the productInformation 
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
    		@RequestParam(value="productPrice",required = false) BigDecimal  productPrice,
    		@RequestParam(value="productDiscount",required = false)BigDecimal productDiscount,
    		@RequestParam(value="countofproducts",required = false) int countofproducts){
    	Product updateProduct=new Product();
    	
    	Product updatedProduct=productService.updateProduct(productId,productPrice,productDiscount,countofproducts);
    	return ResponseEntity.ok(updatedProduct);
    }
    
    @GetMapping("/getproductbyid/{productId}")
    public ResponseEntity<Product> getproductById(@PathVariable Long productId){
    	Product product=productService.getproductById(productId);
    	return ResponseEntity.ok(product);
    }
    
}
