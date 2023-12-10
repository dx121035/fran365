// 작성자 : 임지호
package com.example.fran365.resource;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import com.example.fran365.stock.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3;


@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MemberService memberService;

    @Value("bucket-va1rkc")
    private String bucketName;

    @Override
    public List<Resource> readList() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource readDetail(Integer id) {
        Optional<Resource> or = resourceRepository.findById(id);

        return or.get();
    }

    @Override
    public void create(Resource resource, MultipartFile multipartFile) throws IOException {

        //aws file upload
        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

        file.delete();
        resource.setImage(filename);

        Member member = memberService.readDetailUsername();

        resource.setAddress(member.getAddress());

        resource.setWriter(member.getName());

        resource.setUsername(member.getUsername());

        resource.setCreateDate(LocalDateTime.now());

        resourceRepository.save(resource);
    }

    @Override
    public void update(Resource resource, MultipartFile multipartFile) throws IOException {

        Optional <Resource> or = resourceRepository.findById(resource.getId());
        Resource oldResource = or.get();

        if (multipartFile.isEmpty()) {
            // 파일이 넘어오지 않은 경우, 이미지를 업데이트하지 않고 객체 정보만 업데이트
            resource.setCreateDate(LocalDateTime.now());
            resource.setImage(oldResource.getImage());
            resourceRepository.save(resource);

        } else {
            // 파일이 넘어온 경우, 기존 이미지 삭제 및 새로운 이미지 업로드 후 객체 정보 업데이트



            // 기존 이미지 삭제
            String oldImage = oldResource.getImage();
            if (oldImage != null && !oldImage.isEmpty()) {
                amazonS3.deleteObject(bucketName, oldImage);
            }

            // 새로운 이미지 업로드
            File file = new File(multipartFile.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }

            String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

            file.delete();

            // 객체 정보 업데이트
            resource.setImage(filename);
            resource.setCreateDate(LocalDateTime.now());
            resourceRepository.save(resource);
        }

    }

    @Override
    public void delete(Integer id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public Page<Resource> getList(int page) {
        //게시물의 나열 순서가 최근 자료가 위로 올라오게 즉 역순으로 설정
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));


        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return resourceRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void updateProductStock(Integer id, int amount) {

        Resource resource = resourceRepository.findWithPessimisticLockById(id);

        if (resource.getAmount() >= amount) {
            resource.setAmount(resource.getAmount() - amount);
            resourceRepository.save(resource);

        } else {
            throw new RuntimeException("Not enough stock available");
        }

        if (resource.getAmount() == 0) {
            resourceRepository.delete(resource);
        }


    }

//    @Transactional
//    public void updateProductStock(Integer id, int amount) {
//        Optional<Resource> optionalResource = resourceRepository.findById(id);
//
//        if (optionalResource.isPresent()) {
//            Resource resource = optionalResource.get();
//
//            try {
//                resource.setAmount(resource.getAmount() - amount);
//
//                resourceRepository.saveAndFlush(resource);
//
//                if (resource.getAmount() <= 0) {
//                    resourceRepository.delete(resource);
//                }
//            } catch (ObjectOptimisticLockingFailureException e) {
//                System.out.println("동시성 이슈 발생!!!");
//                handleVersionConflict(resource, int amount)
//
//                // 롤백을 수행하기 위해 트랜잭션을 수동으로 롤백 상태로 설정
//
//
//
//            }
//        } else {
//            throw new RuntimeException("재고가 없습니다.");
//        }
//    }
//@Transactional
//public void handleVersionConflict(Resource resource, int amount) {
//    try {
//        // 버전 충돌이 발생한 경우, 새로운 버전의 Resource를 불러와서 amount를 갱신
//        Resource refreshedResource = resourceRepository.findById(resource.getId())
//                .orElseThrow(() -> new RuntimeException("Resource not found after version conflict"));
//
//        // 갱신된 amount를 적용
//        refreshedResource.setAmount(refreshedResource.getAmount() - amount);
//
//        // 재고가 음수가 되면 예외 발생
//        if (refreshedResource.getAmount() < 0) {
//            throw new VersionConflict("재고가 부족합니다.");
//        }
//
//        // 갱신된 Resource 저장
//        resourceRepository.save(refreshedResource);
//    } catch (Exception e) {
//        // 예외 처리
//        e.printStackTrace();
//        // 롤백이 발생하지 않도록 처리
//    }
}