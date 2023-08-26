package check_in42.backend.presentation.utils;

import lombok.Getter;

@Getter
public enum PresentationType {
    rush("rush"),
    piscine("piscine"),
    partnership("partnership"),
    conference("conference"),
    meet_up("meet_up"),
    event("event"),
    association("association"),
    hackathon("hackathon"),
    workshop("workshop"),
    challenge("challenge"),
    extern("extern");

    private String description;

    PresentationType(String description) {
        this.description = description;
    }
}
