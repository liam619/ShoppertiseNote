package shoppertise.notes.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class GroupByTags {

    @Id
    private List<String> tags;
    private List<String> titles;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "GroupByTags [tags=" + tags + ", titles=" + titles + "]";
    }
}
