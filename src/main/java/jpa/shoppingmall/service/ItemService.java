package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Specialty;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    @Transactional
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findAllPaging(int offset, int limit) {
        return itemRepository.findPaging(offset, limit);
    }

    public Long getTotalCnt() {
        return itemRepository.getTotalCount();
    }


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void sampleData() {
        log.info("Add sample data");
        for (int i = 0; i < 250; i++) {
            Specialty specialty1 = createSpecialty("JPA"+i, 10, "기서", "123", 10000, "kim");
            itemRepository.save(specialty1);
            categoryService.save(specialty1.getId(), "specialty");
        }
    }

    private Specialty createSpecialty(String name, int stockQuantity, String origin, String prodGroup, int price, String seller) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialty.setStockQuantity(stockQuantity);
        specialty.setOrigin(origin);
        specialty.setProdGroup(prodGroup);
        specialty.setPrice(price);
        specialty.setSeller(seller);
        return specialty;
    }
}
