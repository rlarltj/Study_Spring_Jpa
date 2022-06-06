package jpa.shoppingmall.web;

import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class OrderForm {
    @NotNull(message = "회원을 선택해주세요.")
    private Long memberId;
    @NotNull(message = "상품을 선택해주세요.")
    private Long itemId;

    @NotNull(message = "상품을 선택해주세요.")
    @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
    private int count;
}
