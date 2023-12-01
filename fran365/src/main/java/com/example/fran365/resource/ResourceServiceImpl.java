// 작성자 : 임지호
package com.example.fran365.resource;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlatformTransactionManager transactionManager;

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
    public void create(Resource resource) {

        Member member = memberService.readDetailUsername();

        resource.setAddr(member.getAddr());

        resource.setWriter(member.getName());

        resource.setCreateDate(LocalDateTime.now());

        resourceRepository.save(resource);
    }

    @Override
    public void update(Resource resource) {

        resource.setCreateDate(LocalDateTime.now());

        resourceRepository.save(resource);

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

                }else {
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