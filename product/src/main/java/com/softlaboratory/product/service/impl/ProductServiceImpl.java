package com.softlaboratory.product.service.impl;

import basedomain.utility.ResponseUtil;
import com.softlaboratory.product.repository.ProductRepository;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import product.dao.ProductDao;
import product.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Object> getAll() {
        log.info("Starting get all data.");

        log.debug("Fetch data with repository.");
        List<ProductDao> productDaoList = repository.findAll();

        log.debug("Converting to data transfer.");
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDao dao : productDaoList) {
            productDtoList.add(ProductDto.builder()
                            .id(dao.getId())
                            .name(dao.getName())
                            .description(dao.getDescription())
                            .price(dao.getPrice())
                            .stock(dao.getStock())
                    .build());
        }

        log.info("Get all data success.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), productDtoList);
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        log.info("Starting get data by id.");

        log.debug("Find data with repository.");
        Optional<ProductDao> dao = repository.findById(id);
        if (dao.isPresent()) {
            log.debug("Data {} is present, converting to data transfer.", id);
            ProductDto dto = modelMapper.map(dao.get(), ProductDto.class);

            log.info("Get data by id success.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), dto);
        }else {
            log.debug("Data with id {} not found.", id);

            log.info("Get data by id failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

    @Override
    public ResponseEntity<Object> create(ProductDto request) {
        log.info("Starting add new data.");

        log.debug("Converting request body to data access.");
        ProductDao productDao = ProductDao.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        log.debug("Save data with repository.");
        productDao = repository.save(productDao);

        log.debug("Convert result to data transfer.");
        ProductDto dto = modelMapper.map(productDao, ProductDto.class);

        log.info("Save new data success.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), dto);
    }

    @Override
    public ResponseEntity<Object> updateById(Long id, ProductDto request) {
        log.info("Starting update data by id.");

        log.debug("Find data with repository.");
        Optional<ProductDao> daoOld = repository.findById(id);
        if (daoOld.isPresent()) {
            log.debug("Data {} is present, convert request body to new data access.", id);
            ProductDao daoNew = daoOld.get();
            daoNew.setName(request.getName());
            daoNew.setDescription(request.getDescription());
            daoNew.setPrice(request.getPrice());
            daoNew.setStock(request.getStock());

            log.debug("Update data with repository.");
            repository.save(daoNew);

            log.debug("Convert result to data transfer.");
            ProductDto dto = modelMapper.map(daoNew, ProductDto.class);

            log.info("Update data by id success.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), dto);
        }else {
            log.debug("Data with id {} not found.", id);

            log.info("Get data by id failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) {
        log.info("Starting delete data by id.");

        log.debug("Find data with repository.");
        Optional<ProductDao> dao = repository.findById(id);
        if (dao.isPresent()) {
            log.debug("Data {} is present, deleting data with repository.", id);
            repository.deleteById(id);

            log.info("Delete data by id success.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null);
        }else {
            log.debug("Data with id {} not found.", id);

            log.info("Delete data by id failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

}
