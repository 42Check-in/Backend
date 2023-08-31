package check_in42.backend.vocal.utils;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class FormIdList {
    private List<Long> formIds;
    private Map<Long, Integer> presenList;
}
