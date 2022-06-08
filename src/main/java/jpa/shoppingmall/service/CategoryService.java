package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Category;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.ItemCategory;
import jpa.shoppingmall.repository.CategoryRepository;
import jpa.shoppingmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Transactional
    public Long save(Long itemId, String name) {
        Item item = itemRepository.findOne(itemId);

        Category category = Category.createCategory(name);
        ItemCategory itemCategory = ItemCategory.createCategoryItem(category, item);

        categoryRepository.save(itemCategory);
        return itemCategory.getId();
    }

    public Long getCount(String category){
        return categoryRepository.getTotalCount(category);
    }
    public ItemCategory findOne(Long itemId){
        return categoryRepository.findOne(itemId);
    }

    public List<Item> findItems(String category, int offset, int limit){
        return categoryRepository.findCateItems(category, offset, limit);
    }
}
