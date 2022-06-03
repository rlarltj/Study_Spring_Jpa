package jpa.shoppingmall.web;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageHandler {
    private boolean showPrev;
    private boolean showNext;

    private int page = 1;
    private int naviSize = 10;
    private Long totalCnt;  // 전체 물건 개수
    private int totalPage;  // 전체 페이지 수
    private int beginPage;
    private int endPage;

    public PageHandler() {
    }

    public PageHandler(Long totalCnt) {
        this.totalCnt = totalCnt;
        doPaging(totalCnt);
    }

    public void doPaging(Long totalCnt){
        this.totalCnt = totalCnt;

        totalPage = (int) Math.ceil(totalCnt / (double)naviSize);
        beginPage = page/naviSize*naviSize + 1;
        endPage = Math.min(beginPage + naviSize -1, totalPage);

        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }
}
