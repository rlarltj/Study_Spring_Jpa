package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Book;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
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

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void sampleData() {
        log.info("Add sample data");
        Book book1 = createBook("JPA", 10, "기서", "123", 10000);
        Book book2 = createBook("Spring", 20, "로드 존슨", "456", 30000);

        itemRepository.save(book1);
        itemRepository.save(book2);
    }

    private Book createBook(String name, int stockQuantity, String author, String isbn, int price) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);

        return book;
    }
}
