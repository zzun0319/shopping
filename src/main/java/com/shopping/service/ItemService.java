package com.shopping.service;

import com.shopping.controller.form.*;
import com.shopping.domain.Member;
import com.shopping.domain.items.Item;
import com.shopping.domain.items.Outer;
import com.shopping.domain.items.Pants;
import com.shopping.domain.items.Upper;
import com.shopping.exception.CannotSaleItemException;
import com.shopping.exception.NoSuchMemberException;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 컨트롤러에서 넘겨준 등록폼을 Item 엔티티로 변환하여 Insert
     * @param registerForm
     */
    @Transactional
    public void saveItem(ItemRegisterForm registerForm) {

        Optional<Member> om = memberRepository.findById(registerForm.getSalesmanId());
        Member salesman = om.orElseThrow(() -> new NoSuchMemberException("존재하지 않는 회원입니다."));

        if(!salesman.getSaleAvailable()){
            throw new CannotSaleItemException("상품 판매 허가가 나지 않았습니다.");
        }

        switch (registerForm.getDType()){
            case "U":
                saveUpper((UpperRegisterForm) registerForm, salesman);
                break;
            case "P":
                savePants((PantsRegisterForm) registerForm, salesman);
                break;
            case "O":
                saveOuter((OuterRegisterForm) registerForm, salesman);
                break;
        }

    }

    private void saveOuter(OuterRegisterForm registerForm, Member salesman) {
        OuterRegisterForm outerForm = registerForm;
        Item item = Outer.createOuter(outerForm.getName(), outerForm.getPrice(), outerForm.getStockQuantity(), salesman,
                outerForm.getTotalLength(), outerForm.getWeight(), outerForm.getArmLength());
        itemRepository.save(item);
    }

    private void savePants(PantsRegisterForm registerForm, Member salesman) {
        PantsRegisterForm pantsForm = registerForm;
        Item item = Pants.createPants(pantsForm.getName(), pantsForm.getPrice(), pantsForm.getStockQuantity(), salesman,
                pantsForm.getTotalLength(), pantsForm.getWaist(), pantsForm.getThighWidth());
        itemRepository.save(item);
    }

    private void saveUpper(UpperRegisterForm registerForm, Member salesman) {
        UpperRegisterForm upperForm = registerForm;
        Item item = Upper.createUpper(upperForm.getName(), upperForm.getPrice(), upperForm.getStockQuantity(), salesman,
                upperForm.getArmLength(), upperForm.getTotalLength(), upperForm.getShoulderWidth());
        itemRepository.save(item);
    }


}
