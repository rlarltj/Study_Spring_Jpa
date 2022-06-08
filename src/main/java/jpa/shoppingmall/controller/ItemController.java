package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.*;
import jpa.shoppingmall.repository.MemberRepository;
import jpa.shoppingmall.service.CategoryService;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.web.ItemForm;
import jpa.shoppingmall.web.PageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("items/new")
    public String showItemFormV2(Model m, @RequestParam(required = true) String type) {
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
            items = categoryService.findItems(category, offset, limit);
            totalCnt = categoryService.getCount(category);
            log.info("해당 카테고리 아이템 개수 = {}", totalCnt);
        }
        PageHandler ph = new PageHandler(totalCnt, page);

        m.addAttribute("items", items);
        m.addAttribute("ph", ph);
        log.info("show Prev ={}, next={}", ph.isShowPrev(), ph.isShowNext());
        log.info("ph={}", ph);
        return "items/itemList";

    }

    @PostMapping("/items/new")
    public String saveItem(@Valid @ModelAttribute ItemForm form, BindingResult bindingResult, HttpServletRequest request, Model m) {
        if(bindingResult.hasErrors()){
            log.info("ex={}", bindingResult.getFieldErrors());

            m.addAttribute("type", form.getType());
            return "items/createItemForm";
        }
        log.info("item's type={}, location={}", form.getType(), form.getLocation());
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute("member");

        
        if(form.getType().equals("leisure")){
            Leisure leisure = new Leisure(form.getName(), form.getPrice(), form.getStockQuantity(),
            form.getLocation(), form.getTime(), member.getUsername());
            itemService.save(leisure);
            categoryService.save(leisure.getId(), "leisure");

        }else if(form.getType().equals("specialty")){
            Specialty specialty = new Specialty(form.getName(), form.getPrice(), form.getStockQuantity(),
                    form.getOrigin(), form.getProdGroup(), member.getUsername());

            itemService.save(specialty);
            categoryService.save(specialty.getId(), "specialty");
        }else{
            Tradition tradition = new Tradition(form.getName(), form.getPrice(), form.getStockQuantity(),
                    form.getArtist(), form.getLocation(), member.getUsername());

            itemService.save(tradition);
            categoryService.save(tradition.getId(), "tradition");
        }
        return "redirect:/";
    }

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
        item.setPrice(form.getPrice());

        itemService.save(item);

        return "redirect:/items";
    }

    @GetMapping("items/{itemId}")
    public String itemInfo(@PathVariable("itemId") Long itemId, Model m) {
        Item item = itemService.findOne(itemId);
        ItemForm form = new ItemForm();
        String type = categoryService.findOne(item.getId()).getCategory().getName();//카테고리 명

        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        if(type.equals("specialty")){
            Specialty specialty = (Specialty)item;
            form.setOrigin(specialty.getOrigin());
            form.setProdGroup(specialty.getProdGroup());
        }else if(type.equals("leisure")){
            Leisure leisure = (Leisure)item;
            form.setTime(leisure.getTime());
            form.setLocation(leisure.getLocation());
        }else{
            Tradition tradition = (Tradition)item;
            form.setArtist(tradition.getArtist());
            form.setLocation(tradition.getLocation());
        }

        m.addAttribute("type", type);
        m.addAttribute("itemForm", form);
        return "items/itemInfo";
    }
}
