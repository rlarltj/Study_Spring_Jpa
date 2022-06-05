package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.*;
import jpa.shoppingmall.repository.MemberRepository;
import jpa.shoppingmall.service.CategoryService;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.web.BookForm;
import jpa.shoppingmall.web.ItemForm;
import jpa.shoppingmall.web.PageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberRepository memberRepository;
    private final CategoryService categoryService;
//    @GetMapping("/items/new")
    public String showItemForm(Model m) {
        BookForm bookForm = new BookForm();
        m.addAttribute("bookForm", bookForm);
        return "items/createItemForm";
    }

    @GetMapping("items/new")
    public String showItemFormV2(Model m, @RequestParam String type) {
        log.info("item type={}", type);

        ItemForm itemForm = new ItemForm();

        m.addAttribute("itemForm", itemForm);
        m.addAttribute("type", type);
        return "items/createItemForm";
    }


    @GetMapping("/items/category")
    public String showCateList() {

        return "items/itemCategoryForm";
    }

//    @GetMapping("/items")
//    public String showItemListV2(Model m, @RequestParam(defaultValue = "1") int page,
//                                 @RequestParam String category){
//
//    }
    @GetMapping("/items")
    public String showItemList(Model m, @RequestParam(defaultValue = "1") int page,
                               @RequestParam(required = false) String category) {
        List<Item> items;
        Long totalCnt;

        int offset = (page-1) * 10;
        int limit = 10;

        log.info("카테고리 쿼리스트링 ={}", category);
        if(" ".equals(category) || category == null ){
            items = itemService.findAllPaging(offset, limit);
            totalCnt = itemService.getTotalCnt();
        }
        else{
            items = categoryService.findItems(category);
            totalCnt = categoryService.getCount(category);
        }
        PageHandler ph = new PageHandler(totalCnt, page);

        m.addAttribute("items", items);
        m.addAttribute("ph", ph);
        log.info("show Prev ={}, next={}", ph.isShowPrev(), ph.isShowNext());
        log.info("ph={}", ph);
        return "items/itemList";

    }

    @PostMapping("/items/new")
    public String saveItem(@Valid ItemForm form, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            log.info("ex={}", bindingResult.getFieldErrors());
            return "items/createItemForm";
        }
        log.info("item's type={}, director={}", form.getType(), form.getDirector());
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute("member");

        
        if(form.getType().equals("movie")){
            Movie movie = new Movie(form.getName(), form.getPrice(), form.getStockQuantity(),
            form.getDirector(), form.getActor(), member.getUsername());
            itemService.save(movie);
            categoryService.save(movie.getId(), "movie");
        }else if(form.getType().equals("book")){
            Book book = new Book(form.getName(), form.getPrice(), form.getStockQuantity(),
                    form.getAuthor(), form.getIsbn(), member.getUsername());

            itemService.save(book);
            categoryService.save(book.getId(), "book");
        }else{
            Lp lp = new Lp(form.getName(), form.getPrice(), form.getStockQuantity(),
                    form.getArtist(), form.getEtc(), member.getUsername());

            itemService.save(lp);
            categoryService.save(lp.getId(), "lp");
        }
        return "redirect:/";
    }
//    @GetMapping("items/{itemId}/edit")
//    public String showEditForm(@PathVariable("itemId") Long itemId, Model m) {
//        Book item = (Book) itemService.findOne(itemId);
//        BookForm form = new BookForm();
//
//        form.setName(item.getName());
//        form.setStockQuantity(item.getStockQuantity());
//        form.setAuthor(item.getAuthor());
//        form.setIsbn(item.getIsbn());
//        form.setPrice(item.getPrice());
//
//        m.addAttribute("form", form);
//
//        return "items/updateItemForm";
//    }
    @GetMapping("items/{itemId}/edit")
    public String showEditForm(@PathVariable("itemId") Long itemId, Model m) {
        Item item = itemService.findOne(itemId);
        ItemForm form = new ItemForm();

        String categoryName = categoryService.findOne(item.getId()).getCategory().getName();//카테고리 명
//아이템의 타입 정보를 가져온다음 Model에 저장한다.
        //이후 타입에 따라 수정 폼을 th:if로 출력한다.
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        
        m.addAttribute("category", categoryName);
        m.addAttribute("form", form);

        return "items/updateItemForm";
    }
    @PostMapping("items/{itemId}/edit")
    public String editItem(@PathVariable("itemId") Long itemId, ItemForm form){
        Item item =  itemService.findOne(itemId);

        item.setName(form.getName());
        item.setStockQuantity(form.getStockQuantity());
//        item.setAuthor(form.getAuthor());
//        item.setIsbn(form.getIsbn());
        item.setPrice(form.getPrice());

        itemService.save(item);

        return "redirect:/items";
    }
}
