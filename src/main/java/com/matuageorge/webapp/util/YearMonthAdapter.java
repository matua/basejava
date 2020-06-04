package com.matuageorge.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {
    @Override
    public YearMonth unmarshal(String str) {
        return YearMonth.parse(str);
    }

    @Override
    public String marshal(YearMonth ym) {
        return ym.toString();
    }
}
