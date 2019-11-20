package com.talend.test.components.source;

import java.util.LinkedList;
import java.util.List;

public class CompanyInputSourceFixture {

    List<String> EXPECTED_DATA_STRING = createData();

    private List<String> createData() {
        List<String> lst = new LinkedList<>();
        lst.add("{\"city_id\":4887398,\"date\":1485703465,\"degree\":268.987}");
        lst.add("{\"city_id\":4887398,\"date\":1485730032,\"degree\":268.097}");
        lst.add("{\"city_id\":4887398,\"date\":1485755383,\"degree\":266.787}");
        lst.add("{\"city_id\":4887398,\"date\":1485780512,\"degree\":263.64}");
        return lst;
    }
}
