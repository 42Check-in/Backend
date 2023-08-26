package check_in42.backend.presentation.utils;

import lombok.Getter;

@Getter
public enum PresentationTime {
    ZERO("15분"),
    ONE("30분"),
    TWO("45분"),
    THREE("1시간"),
    FOUR("1시간 이상");

    private String description;

    PresentationTime(String description) {
        this.description = description;
    }


}
