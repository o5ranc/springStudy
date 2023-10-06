package com.keduit.repository;

import com.keduit.constant.ItemSellStatus;
import com.keduit.dto.ItemSearchDTO;
import com.keduit.dto.MainItemDTO;
import com.keduit.dto.QMainItemDTO;
import com.keduit.entity.Item;
import com.keduit.entity.QItem;
import com.keduit.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory; //동적 쿼리를 생성하기 위해 JPAQueryFactory사용

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    // JPAQueryFactory의 생성자로 EntityManager 객체를 넣는다.

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }//상품 판매 상태 조건이 전체(null)일 경우 null리턴 결과값이 null이면 where절에서 해당 조건은 무시
    // : 상태 조건이 null값이 아닌 판매중 or 품절 이라면 해당 조건의 상품 조회

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        //searchBy의 값에 따라서 상품명, 상품 생성자의 아이디에 검색어를 포함하고 있는 상품 조회
        if (StringUtils.equals("itemNm", searchBy)) {
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
        // null 값을 반환함으로써 검색 조건이 필요 없는 경우에는 해당 부분을 무시하고,
        // 검색 조건이 필요한 경우에는 해당 검색 조건을 사용할 수 있도록 유연성을 제공
    }


    @Override
    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {

        /* 교과서 내용에 들어있는 코드*/
//        //queryFactory를 사용 쿼리를 생성.
//        QueryResults<Item> results = queryFactory
//                //Q도매인이 만들어준 item으로부터 선택
//                .selectFrom(QItem.item)
//                //BooleanExpression 반환하는 where 조건문들 ','단위를 넣어줄 경우 and조건으로 인식
//                .where(regDtsAfter(itemSearchDTO.getSearchDateType()), //기간 검색
//                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()), // 판매상태 검색
//                        searchByLike(itemSearchDTO.getSearchBy(), //검색 유형
//                                itemSearchDTO.getSearchQuery()))//조회할 검색어 저장할 변수
//                //id 역순
//                .orderBy(QItem.item.id.desc())
//                //offset 데이터를 가지고 올 시작 인덱스를 지정.
//                .offset(pageable.getOffset())
//                //limit 한번에 가지고 올 최대 갯수 지정.
//                .limit(pageable.getPageSize())
//                //fetchResults() 조회한 리스트 및 전체 개수를 포함하는 QueryResults를 반환.
//                .fetchResults();
//        List<Item> content =  results.getResults();
//        long total  = results.getTotal();
//        return new PageImpl<>(content, pageable, total);

        /*강사님이 작성하신 코드 (Wildcard.count 사용)*/
        List<Item> result = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
                        searchByLike(itemSearchDTO.getSearchBy(),
                                itemSearchDTO.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //result.size()하지 않고 다시 읽은 이유는
        //해당 페이지가 아닌 전체 total을 봐야 하기 때문에 생성함.
        long total = queryFactory
                .select(Wildcard.count)
                .from(QItem.item)
                //BooleanExpression 반환하는 where 조건문들 ','단위를 넣어줄 경우 and조건으로 인식
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()), //기간 검색
                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()), // 판매상태 검색
                        searchByLike(itemSearchDTO.getSearchBy(),
                                itemSearchDTO.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }


    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ?
                null : QItem.item.itemNm.like("%"+searchQuery+"%");
    }
    @Override
    public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDTO> results = queryFactory
                .select(new QMainItemDTO(
                        item.id,
                        item.itemNm,
                        item.itemDetail,
                        itemImg.imgUrl,
                        item.price)
                ).from(itemImg)
                //itemImg 엔티티와 item 엔티티를 조인
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                //itemNmLike라는 사용자 정의 메서드를 호출
                .where(itemNmLike(itemSearchDTO.getSearchQuery()))
                .orderBy(item.id.desc())
                //offset 페이지네이션 처리를 위해 결과에서 어느 위치부터 데이터를 가져올지 설정
                .offset(pageable.getOffset())
                //limit 한 페이지에서 가져올 데이터의 최대 개수를 설정
                .limit(pageable.getPageSize())
                //fetchResults : 실제 쿼리를 실행하고 결과를 가져옴. QueryResults 객체에 결과를 저장
                .fetchResults();

        List<MainItemDTO> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
