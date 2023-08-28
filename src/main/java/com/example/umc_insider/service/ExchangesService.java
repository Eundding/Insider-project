package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.Category;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostExchangesReq;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.response.PostExchangesRes;
import com.example.umc_insider.repository.CategoryRepository;
import com.example.umc_insider.repository.GoodsRepository;
import com.example.umc_insider.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.umc_insider.repository.ExchangesRepository;
import com.example.umc_insider.domain.Exchanges;
import com.example.umc_insider.domain.Goods;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangesService {
    private ExchangesRepository exchangesRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private S3Service s3Service;

    @Autowired
    public ExchangesService(ExchangesRepository exchangesRepository, UserRepository userRepository, CategoryRepository categoryRepository, S3Service s3Service) {
        this.exchangesRepository = exchangesRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.s3Service = s3Service;
    }

    public Exchanges createNewExchangesInstance(PostExchangesReq postExchangesReq, MultipartFile file) {
        Users user = userRepository.findUsersById(postExchangesReq.getUserId());
        Category category = categoryRepository.findCategoryByCategoryId(postExchangesReq.getCategoryId());
        Exchanges exchanges = new Exchanges(postExchangesReq, user, category);

        exchangesRepository.save(exchanges);

        // S3에 이미지 업로드 및 URL 받기
        String imageUrl = s3Service.uploadExchangesS3(file, exchanges);

        // 이미지 URL 설정 후, 객체 업데이트
        exchanges.setImageUrl(imageUrl);

        exchangesRepository.save(exchanges);

        return exchanges;
    }

    // 모든 교환 조회
    public List<PostExchangesRes> getExchanges() throws BaseException{
        List<Exchanges> eList = exchangesRepository.findAllWithUsers();
        List<PostExchangesRes> response = eList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser().getId()))
                .collect(Collectors.toList());
        return response;
    }

    // 특정 교환 조회
    public List<PostExchangesRes> getExchangesByTitle(String title) throws BaseException{
        List<Exchanges> eList = exchangesRepository.findByTitleContaining(title);
        List<PostExchangesRes> response = eList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser().getId()))
                .collect(Collectors.toList());
        return response;
    }






}
