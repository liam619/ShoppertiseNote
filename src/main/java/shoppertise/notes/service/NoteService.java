package shoppertise.notes.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.stereotype.Component;

import shoppertise.notes.model.GroupByTags;
import shoppertise.notes.model.Note;
import shoppertise.notes.repository.NoteRepository;

@Component
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /** Add new note **/
    public String createNote(Note note) {
        noteRepository.save(note);
        
        return note.getId() + " - " + note.getTitle();
    }
    
    /** Fetch all notes by page **/
    public Page<Note> getNotesByPage(int page, int pageSize, String sortBy) {
        
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).ascending());
        
        return noteRepository.findAll(paging);
    }

    /** Fetch all notes by group **/
    public List<GroupByTags> getNotesByGroup(String sortBy) {
        var sorting = (sortBy.equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        GroupOperation groupByTags = group("tags").push("title").as("titles"); 
        SortOperation sortOpBy     = sort(Sort.by(sorting, "_id"));
        Aggregation agg            = newAggregation(groupByTags, sortOpBy);
        
        AggregationResults<GroupByTags> groupResults = mongoTemplate.aggregate(agg, "note", GroupByTags.class);
        
        return groupResults.getMappedResults();
    }
    
    /** Fetch specific note **/
    public Note getNote(String title) {
        Optional<Note> note = noteRepository.findByTitle(title);
        
        if (note.isPresent()) {
            return note.get();
        }
        
        return null;
    }
    
    /** Update specific note **/
    public Note updateNote(String title, Note note) {
        Optional<Note> existNote = noteRepository.findByTitle(title);
        
        if (existNote.isPresent()) {
            Note n = existNote.get();
            n.setTitle(note.getTitle());
            n.setContent(note.getContent());
            n.setTags(note.getTags());
            
            return noteRepository.save(n);
        }
        
        return null;
    }
    
    /** Delete single note **/
    public boolean deleteNote(String title) {
        Optional<Note> existNote = noteRepository.findByTitle(title);
        
        if (existNote.isPresent()) {
            Note note = existNote.get();
            noteRepository.delete(note);
            
            return true;
        }
        
        return false;
    }
}
