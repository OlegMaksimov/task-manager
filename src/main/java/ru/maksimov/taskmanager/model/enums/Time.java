package ru.maksimov.taskmanager.model.enums;

public enum Time {
    NINE_AM("09.00", 1),
    TEN_AM("10.00", 2),
    ELEVEN_AM("11.00", 3),
    TWELVE("12.00", 4),
    ONE("13.00", 5),
    TWO("14.00", 6),
    THREE("15.00", 7),
    FOUR("16.00", 8),
    FIVE("17.00", 9),
    SIX("18.00", 10),
    SEVEN("19.00", 11),
    EIGHT("20.00", 12),
    NINE("21.00", 13),
    TEN("22.00", 14),
    ELEVEN("23.00", 15);

    private String val;

    private Integer sort;

    Time(String val, Integer sort) {
        this.val = val;
        this.sort = sort;
    }

    public String getVal() {
        return val;
    }

    public Integer getSort() {
        return sort;
    }
}
