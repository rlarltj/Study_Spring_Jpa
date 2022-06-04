package jpa.shoppingmall.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ItemForm {
    private Long id;

    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    @Min(message = "1,000원부터 입력 가능합니다.", value = 1000)
    @Max(message = "가격은 10,000,000원을 넘을 수 없습니다.", value = 10000000)
    private int price;

    @Min(message = "수량을 입력해주세요", value = 1)
    private int stockQuantity;

    private String type;
    // 도서
    private String author;
    private String isbn;
    
    // LP
    private String artist;
    private String etc;
    
    // 영화
    private String director;
    private String actor;
}
