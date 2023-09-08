package com.keduit.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SampleDTOList {
    List<SampleDTO> list;

    public SampleDTOList() {
        List<SampleDTO> list = new ArrayList<SampleDTO>();
    }



}