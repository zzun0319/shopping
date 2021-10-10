package com.shopping.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PantsRegisterForm extends ItemRegisterForm {

    private int totalLength;
    private int waist;
    private int thighWidth;
}
