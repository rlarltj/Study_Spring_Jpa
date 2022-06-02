package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.Book;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.MemberRepository;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.session.SessionConst;
import jpa.shoppingmall.web.BookForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/items/new")
    public String showItemForm(Model m) {
        BookForm bookForm = new BookForm();
        m.addAttribute("bookForm", bookForm);
        return "items/createItemForm";
    }

    @GetMapping("/items")
    public String showItemList(Model m) {
        List<Item> items = itemService.findAll();
        m.addAttribute("items", items);
        return "items/itemList";
    }

    @PostMapping("/items/new")
    public String saveItem(@Valid BookForm form, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            log.info("ex={}", bindingResult.getFieldErrors());
            return "items/createItemForm";
        }

        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute("member");

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setSeller(member.getUsername());

        itemService.save(book);

        return "redirect:/";
    }

    @GetMapping("items/{itemId}/edit")
    public String showEditForm(@PathVariable("itemId") Long itemId, Model m) {
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();

        form.setName(item.getName());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setPrice(item.getPrice());

        m.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String editItem(@PathVariable("itemId") Long itemId, BookForm form){
        Book item = (Book) itemService.findOne(itemId);

        item.setName(form.getName());
        item.setStockQuantity(form.getStockQuantity());
        item.setAuthor(form.getAuthor());
        item.setIsbn(form.getIsbn());
        item.setPrice(form.getPrice());

        itemService.save(item);

        return "redirect:/items";
    }
}
